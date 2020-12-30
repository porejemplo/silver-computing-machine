import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GestorArchivos {
	private File anexo1;
    private File anexo2;
    private Scanner s;
    
    private Localizacion[] listaSalas;
    private Personaje[] listaPersonajes;
	private Objeto[] listaObjetos;
	
	// Getter y seters
	public Localizacion[] getListaSalas() {
		return listaSalas;
	}

	public void setListaSalas(Localizacion[] listaSalas) {
		this.listaSalas = listaSalas;
	}

	public Personaje[] getListaPersonajes() {
		return listaPersonajes;
	}

	public void setListaPersonajes(Personaje[] listaPersonajes) {
		this.listaPersonajes = listaPersonajes;
	}

	public Objeto[] getListaObjetos() {
		return listaObjetos;
	}

	public void setListaObjetos(Objeto[] listaObjetos) {
		this.listaObjetos = listaObjetos;
	}

    // Constructor.
    public GestorArchivos(String anexo1, String anexo2){
        this.anexo1 = new File(anexo1);
        crearListasAnexoI();
        darValorListasAnexoI();
        unirLocalizacionesAdyacentes();
        this.anexo2 = new File(anexo2);
        darValoresAnexoII();
    }
    
    public GestorArchivos(String anexo1){
        this.anexo1 = new File(anexo1);
    }
    
    
    public static void main(String[] args) {
        GestorArchivos ga = new GestorArchivos ("Anexo1.txt", "AnexoII.txt");
        //System.out.println("Hola");
        for(int i = 0; i < ga.getListaPersonajes().length; i++) {
        	System.out.println(ga.getListaPersonajes()[i].toString());
        }
    }
    
    private void crearListasAnexoI() {
    	try {
    		int tipo = -1;
        	int cont = 0;
        	s = new Scanner (anexo1);
            while (s.hasNextLine()) {
                String linea = s.nextLine();
                if(linea.charAt(0) == '<' || !s.hasNextLine()) {
                	if (s.hasNextLine())
                		cont--;
                	
                	if (tipo == 0)
                		setListaSalas(new Localizacion[cont]);
                	else if (tipo == 1)
                		setListaPersonajes(new Personaje[cont]);
                	else if (tipo == 2)
                		setListaObjetos(new Objeto[cont]);
                	
                	cont = 0;
                	
                	if (linea.equals("<Localizaciones>"))
                		tipo = 0;
                	else if (linea.equals("<Personaje>"))
                		tipo = 1;
                	else if (linea.equals("<Objetos>"))
                		tipo = 2;
                }
                cont++;
            }
    	} catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    
    private void darValorListasAnexoI() {
    	try{
    		int i = 0;
            s = new Scanner (anexo1);
            while (s.hasNextLine()) {
                String linea = s.nextLine();
                if(linea.charAt(0) == '<') {
                	//System.out.println("Tipo de Objeto: " + linea);
                	if (linea.equals("<Localizaciones>"))
                		i = 0;
                	else if (linea.equals("<Personaje>"))
                		i = 1;
                	else
                		i = 2;
                }
                else{
                	Scanner sl = new Scanner (linea);
                    sl.useDelimiter("\\s*(\\(|,|\\))\\s*");
                	if(i == 0) {
                		guardarLocalizacion(sl);
                	}
                	else if(i == 1) {
                		guardarPersonaje(sl);
                	}
                	else if (i == 2) {
                		guardarObjeto(sl);
                	}
                    sl.close();
                }
            }
            s.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
        
    private void guardarLocalizacion(Scanner scanner) {
    	for (int i = 0; i <= listaSalas.length; i++) {
    		if (listaSalas[i] == null) {
    			listaSalas[i] = new Localizacion(scanner.next());
    			//Contar el numero de localizacione sdayacentes que tiene.
    			int cont = 0;
    			while (scanner.hasNext()){
    				cont++;
                    scanner.next();
                }
    			listaSalas[i].setLocalizacionesAdyacentes(new Localizacion[cont]);
    			break;
    		}
    	}
    }
    
    private void guardarPersonaje(Scanner scanner) {
    	for (int i = 0; i <= listaPersonajes.length; i++) {
    		if (listaPersonajes[i] == null) {
    			String nombre = scanner.next();
    			Localizacion localizacion = buscarSala(scanner.next());
    			listaPersonajes[i] = new NPC_aleatorio(nombre, localizacion);
    			break;
    		}
    	}
    }
    
    private void guardarObjeto(Scanner scanner) {
    	for (int i = 0; i <= listaObjetos.length; i++) {
    		if (listaObjetos[i] == null) {
    			// Se crea el objeto
    			listaObjetos[i] = new Objeto(scanner.next());
    			// Se le asigna el objeto al jugador o localizacion corespondiente.
    			String nombre = scanner.next();
    			Localizacion localizacion = buscarSala(nombre);
    			if (localizacion != null) {
    				localizacion.addObjeto(listaObjetos[i]);
    			}
    			else {
    				Personaje personaje = buscarPersonaje(nombre);
    				if (personaje != null) {
    					personaje.setObjeto(listaObjetos[i]);
        			}
    			}
    			
    			break;
    		}
    	}
    }
    
    private void unirLocalizacionesAdyacentes() {
    	try{
    		int caso = 1;
    		int contSalas = 0;
    		int contSalasAdyacentes = 0;
            s = new Scanner (anexo1);
            while (s.hasNextLine()) {
                String linea = s.nextLine();
                if(linea.charAt(0) == '<') {
                	//System.out.println("Tipo de Objeto: " + linea);
                	if (linea.equals("<Localizaciones>"))
                		caso = 0;
                	else
                		caso=1;
                }
                else if (caso == 0){
                	Scanner sl = new Scanner (linea);
                    sl.useDelimiter("\\s*(\\(|,|\\))\\s*");
                    sl.next();
                    while (sl.hasNext()){
                    	listaSalas[contSalas].getLocalizacionesAdyacentes()[contSalasAdyacentes] = buscarSala(sl.next());
                    	contSalasAdyacentes++;
                    }
                    sl.close();
                	contSalas++;
                	contSalasAdyacentes = 0;
                }
            }
            s.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    
    private void darValoresAnexoII() {
    	try{
    		int i = 0;
            s = new Scanner (anexo2);
            while (s.hasNextLine()) {
                String linea = s.nextLine();
                if(linea.charAt(0) == '<') {
                	//System.out.println("Tipo de Objeto: " + linea);
                	if (linea.equals("<Localización Personajes>"))
                		i = 0;
                	else if (linea.equals("<Posesión Objetos>"))
                		i = 1;
                }
                else{
                	Scanner sl = new Scanner (linea);
                    sl.useDelimiter("\\s*(\\(|,|\\))\\s*");
                    if (sl.hasNext()) {
	                	if(i == 0) {
	                		guardarLocalizacionObjetivo(sl);
	                	}
	                	else if(i == 1) {
	                		guardarObjetoObjetivo(sl);
	                	}
                    }
                    sl.close();
                }
            }
            s.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    
    private void guardarLocalizacionObjetivo(Scanner scanner) {
    	Personaje personaje = buscarPersonaje(scanner.next());
    	
    	if (personaje != null)
			personaje.setObjetivo(new Ubicacion(scanner.next()));
    }
    
    private void guardarObjetoObjetivo(Scanner scanner) {
    	String objeto = scanner.next();
    	Personaje personaje = null;
    	
    	if (scanner.hasNext()) {
    		personaje = buscarPersonaje(scanner.next());
        	
        	if (personaje != null)
        		personaje.getObjetivo().setNombre(objeto);
    	}
    }
    
    private Localizacion buscarSala(String nombre) {
    	Localizacion localizacion = null;
    	for(int i = 0; i < listaSalas.length; i++) {
    		if (listaSalas[i].getNombre().equals(nombre)) {
    			localizacion = listaSalas[i];
    			break;
    		}
    	}
    	if (localizacion == null) {
    		System.out.println("ERROR\nNo se encuentra Localizacion " + nombre);
    	}
    	
    	return localizacion;
    }
    
    private Personaje buscarPersonaje(String nombre) {
    	Personaje personaje = null;
    	for(int i = 0; i < listaPersonajes.length; i++) {
    		if (listaPersonajes[i].getNombre().equals(nombre)) {
    			personaje = listaPersonajes[i];
    			break;
    		}
    	}
    	if (personaje == null) {
    		System.out.println("ERROR\nNo se encuentra Personaje " + nombre);
    	}
    	
    	return personaje;
    }
}
