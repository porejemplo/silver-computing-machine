import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GestorArchivos {
	private File anexo1;
    private File anexo2;
    private String nombreArchivo;
    
    final String regEncabezado = "<Localizaciones>|<Personajes>|<Objetos>|<Localización Personajes>|<Posesión Objetos>";
    final String regContenidoAnexoI = "^[\\wáéíóú+\s*]+\\(([\\wáéíóú+\s*]+,)*[\\wáéíóú+\s*]+\\)";
    final String regContenidoAnexoII = "^[\\wáéíóú+\s*]+(\\([\\wáéíóú+\s*]+\\))?";
    
    private Localizacion[] listaSalas;
    private Personaje[] listaPersonajes;
	private Objeto[] listaObjetos;
	
	// Utiles
	private int nLinea = 0;
	private int selector = 0;
	
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
    public GestorArchivos(){};
    public GestorArchivos(File fAnexoI, File fAnexoII) {
        this.anexo1 = fAnexoI;
        this.anexo2 = fAnexoII;
    }
    public GestorArchivos(String fAnexoI, String fAnexoII) {
        this.anexo1 = new File(fAnexoI);
        this.anexo2 = new File(fAnexoII);
    }
    
    public static void main(String[] args) {
        GestorArchivos ga = new GestorArchivos("Anexo1.txt", "AnexoII.txt");
        try{
            ga.leerAnexos();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (GestorArchivosException e) {
            e.printStackTrace();
        }
    }
    
    public void leerAnexos() throws FileNotFoundException, GestorArchivosException
    {
        Scanner s = new Scanner (anexo1);
        nombreArchivo = anexo1.getName();
        comprobarFormatoAnexoI(s);
        s = new Scanner (anexo1);
        crearListasAnexoI(s);
        s = new Scanner (anexo1);
        darValorListasAnexoI(s);
        s = new Scanner (anexo1);
        unirLocalizacionesAdyacentes(s);

        s = new Scanner (anexo2);
        nombreArchivo = anexo2.getName();
        comprobarFormatoAnexoII(s);
        s = new Scanner (anexo2);
        darValoresAnexoII(s);

        s.close();
    }
    
    private void comprobarFormatoAnexoI(Scanner s) throws GestorArchivosException
    {
        nLinea = 0;
        while (s.hasNextLine()) {
            ++nLinea;
            String linea = s.nextLine();
            if(linea.charAt(0) == '<'){
                if (!linea.matches(regEncabezado))
                    throw new GestorArchivosException(nLinea,nombreArchivo,"Formato");
            }
            else if (!linea.matches(regContenidoAnexoI)){
            	throw new GestorArchivosException(nLinea,nombreArchivo,"Formato");
            }
        }
    }

    private void comprobarFormatoAnexoII(Scanner s) throws GestorArchivosException
    {
        nLinea = 0;
        while (s.hasNextLine()) {
            ++nLinea;
            String linea = s.nextLine();
            if(linea.charAt(0) == '<'){
                if (!linea.matches(regEncabezado))
                	throw new GestorArchivosException(nLinea,nombreArchivo,"Formato");
            }
            else if (!linea.matches(regContenidoAnexoII)){
            	throw new GestorArchivosException(nLinea,nombreArchivo,"Formato");
            }
        }
    }
    
    private void crearListasAnexoI(Scanner s) {
    	selector = -1;
    	int contadorSalas = 0;
    	int contadorPersonajes = 0;
    	int contadorObjetos = 0;
        while (s.hasNextLine()) {
            String linea = s.nextLine();
            if(linea.charAt(0) == '<' || !s.hasNextLine()) {
            	if (linea.equals("<Localizaciones>"))
            		selector = 0;
            	else if (linea.equals("<Personajes>"))
            		selector = 1;
            	else if (linea.equals("<Objetos>"))
            		selector = 2;
            }
            else {
            	if (selector == 0)
            		contadorSalas++;
            	else if (selector == 1)
            		contadorPersonajes++;
            	else if (selector == 2)
            		contadorObjetos++;
            }
        }
        if (selector == 0)
    		contadorSalas++;
    	else if (selector == 1)
    		contadorPersonajes++;
    	else if (selector == 2)
    		contadorObjetos++;
        
        setListaSalas(new Localizacion[contadorSalas]);
    	setListaPersonajes(new Personaje[contadorPersonajes]);
    	setListaObjetos(new Objeto[contadorObjetos]);
    }
    
    private void darValorListasAnexoI(Scanner s) throws GestorArchivosException
    {
    	nLinea = 0;
    	selector = 0;
        while (s.hasNextLine()) {
        	nLinea++;
            String linea = s.nextLine();
            if(linea.charAt(0) == '<') {
            	if (linea.equals("<Localizaciones>"))
            		selector = 0;
            	else if (linea.equals("<Personajes>"))
            		selector = 1;
            	else
            		selector = 2;
            }
            else{
            	Scanner sl = new Scanner (linea);
                sl.useDelimiter("\\s*(\\(|,|\\))\\s*");
            	if(selector == 0) {
            		guardarLocalizacion(sl);
            	}
            	else if(selector == 1) {
            		guardarPersonaje(sl);
            	}
            	else if (selector == 2) {
            		guardarObjeto(sl);
            	}
                sl.close();
            }
        }
    }
        
    private void guardarLocalizacion(Scanner scanner) throws GestorArchivosException
    {
    	String nombreDeSala = scanner.next();
    	for (int i = 0; i <= listaSalas.length; i++) {
    		if (listaSalas[i] == null) {
    			listaSalas[i] = new Localizacion(nombreDeSala);
    			//Contar el numero de localizacione sdayacentes que tiene.
    			int cont = 0;
    			while (scanner.hasNext()){
    				cont++;
                    scanner.next();
                }
    			listaSalas[i].setLocalizacionesAdyacentes(new Localizacion[cont]);
    			break;
    		}
    		else if (listaSalas[i].getNombre() == nombreDeSala) {
    			throw new GestorArchivosException(nLinea,nombreArchivo,"sala repetida");
    		}
    	}
    }
    
    private void guardarPersonaje(Scanner scanner) throws GestorArchivosException
    {
    	String nombre = scanner.next();
    	for (int i = 0; i <= listaPersonajes.length; i++) {
    		if (listaPersonajes[i] == null) {
    			Localizacion localizacion = buscarSalaSeguro(scanner.next());
    			listaPersonajes[i] = new NPC_aleatorio(nombre, localizacion);
    			break;
    		}
    		else if (listaPersonajes[i].getNombre() == nombre)
    			throw new GestorArchivosException(nLinea,nombreArchivo,"personaje repetido");
    	}
    }
    
    private void guardarObjeto(Scanner scanner) throws GestorArchivosException
    {
    	String nombre = scanner.next();
    	for (int i = 0; i <= listaObjetos.length; i++) {
    		if (listaObjetos[i] == null) {
    			// Se crea el objeto
    			listaObjetos[i] = new Objeto(nombre);
    			// Se le asigna el objeto al jugador o localizacion corespondiente.
    			nombre = scanner.next();
    			Localizacion localizacion = buscarSala(nombre);
    			if (localizacion != null) {
    				localizacion.addObjeto(listaObjetos[i]);
    			}
    			else {
    				Personaje personaje = buscarPersonaje(nombre);
    				if (personaje != null) {
    					personaje.setObjeto(listaObjetos[i]);
        			}
    				else
    					throw new GestorArchivosException(nLinea,nombreArchivo,"referencia inexistente a " + nombre);
    			}
    			
    			break;
    		}
    		else if (listaObjetos[i].getNombre() == nombre)
    			throw new GestorArchivosException(nLinea,nombreArchivo,"objeto repetido");
    	}
    }
    
    private void unirLocalizacionesAdyacentes(Scanner s) throws GestorArchivosException
    {
    	nLinea = 0;
    	selector = 1;
		int contSalas = 0;
		int contSalasAdyacentes = 0;
        while (s.hasNextLine()) {
        	nLinea++;
            String linea = s.nextLine();
            if(linea.charAt(0) == '<') {
            	if (linea.equals("<Localizaciones>"))
            		selector = 0;
            	else
            		selector=1;
            }
            else if (selector == 0){
            	Scanner sl = new Scanner (linea);
                sl.useDelimiter("\\s*(\\(|,|\\))\\s*");
                sl.next();
                while (sl.hasNext()){
                	listaSalas[contSalas].getAdyacencias()[contSalasAdyacentes] = buscarSalaSeguro(sl.next());
                	contSalasAdyacentes++;
                }
                sl.close();
            	contSalas++;
            	contSalasAdyacentes = 0;
            }
        }
    }
    
    private void darValoresAnexoII(Scanner s) throws GestorArchivosException
    {
    	nLinea = 0;
		selector = 0;
        while (s.hasNextLine()) {
        	nLinea++;
            String linea = s.nextLine();
            if(linea.charAt(0) == '<') {
            	if (linea.equals("<Localización Personajes>"))
            		selector = 0;
            	else if (linea.equals("<Posesión Objetos>"))
            		selector = 1;
            }
            else{
            	Scanner sl = new Scanner (linea);
                sl.useDelimiter("\\s*(\\(|,|\\))\\s*");
            	if(selector == 0) {
            		guardarLocalizacionObjetivo(sl);
            	}
            	else if(selector == 1) {
            		guardarObjetoObjetivo(sl);
            	}
                sl.close();
            }
        }
    }
    
    private void guardarLocalizacionObjetivo(Scanner scanner) throws GestorArchivosException {
    	Personaje personaje = buscarPersonajeSeguro(scanner.next());
    	
    	if (personaje != null)
			personaje.setObjetivo(new Ubicacion(scanner.next()));
    }
    
    private void guardarObjetoObjetivo(Scanner scanner) throws GestorArchivosException {
    	String objeto = scanner.next();
    	Personaje personaje = null;
    	
    	if (scanner.hasNext()) {
    		personaje = buscarPersonajeSeguro(scanner.next());
        	
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
    	
    	return localizacion;
    }
    
    private Localizacion buscarSalaSeguro(String nombre) throws GestorArchivosException
    {
    	Localizacion localizacion = buscarSala(nombre);
    	if (localizacion == null)
    		throw new GestorArchivosException(nLinea,nombreArchivo,"referencia inexistente a " + nombre);
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
    	
    	return personaje;
    }
    
    private Personaje buscarPersonajeSeguro(String nombre) throws GestorArchivosException
    {
    	Personaje personaje = buscarPersonaje(nombre);
    	if (personaje == null)
    		throw new GestorArchivosException(nLinea,nombreArchivo,"referencia inexistente a " + nombre);
    	return personaje;
    }
}
