import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GestorArchivos {
	private File anexo1;
    private File anexo2;
    private String nombreArchivo;
    
    // Formato contra el que se van a comparar las lineas de los anexos.
    final String regEncabezado = "<Localizaciones>|<Personajes>|<Objetos>|<Localización Personajes>|<Posesión Objetos>";
    final String regContenidoAnexoI = "^[\\wáéíóú+\s*]+\\(([\\wáéíóú+\s*]+,)*[\\wáéíóú+\s*]+\\)";
    final String regContenidoAnexoII = "^[\\wáéíóú+\s*]+(\\([\\wáéíóú+\s*]+\\))?";
    
    // Utiles
	private int nLinea = 0; // Se va a utilizar para guardar la linea que se esta utilizando e indicarlo en caso de error.
	private int selector = 0; 
	
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
    
    //Test Main
    /*public static void main(String[] args) {
    	Localizacion[] listaSalas;
        Personaje[] listaPersonajes;
    	Objeto[] listaObjetos;
        GestorArchivos ga = new GestorArchivos("Anexo1.txt", "AnexoII.txt");
        try{
        	ga.comprobarFormato();
        	listaSalas = new Localizacion[ga.tamanoLista(0)];
            listaPersonajes = new Personaje[ga.tamanoLista(1)];
        	listaObjetos = new Objeto[ga.tamanoLista(2)];
            ga.leerAnexos(listaSalas, listaPersonajes, listaObjetos);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (GestorArchivosException e) {
            e.printStackTrace();
        }
    }*/
    
    // Funciones
    public void comprobarFormato() throws FileNotFoundException, GestorArchivosException
    {
    	Scanner s = new Scanner (anexo1);
        nombreArchivo = anexo1.getName();
        comprobarFormatoAnexoI(s);
        
        s = new Scanner (anexo2);
        nombreArchivo = anexo2.getName();
        comprobarFormatoAnexoII(s);
        
        s.close();
    }
    
    // Funcion para saber el tamaño de cada lista
    public int tamanoLista(int seleccion) throws FileNotFoundException
    {
    	selector = -1;
    	int contador = 0;
    	Scanner s = new Scanner (anexo1);
        nombreArchivo = anexo1.getName();
        while (s.hasNextLine()) {
            String linea = s.nextLine();
            if(linea.charAt(0) == '<') {
            	if (linea.equals("<Localizaciones>"))
            		selector = 0;
            	else if (linea.equals("<Personajes>"))
            		selector = 1;
            	else if (linea.equals("<Objetos>"))
            		selector = 2;
            }
            else {
            	if (selector == seleccion) {
            		contador++;
            	}
            }
        }
        s.close();
        
        return contador;
    }
    
    public void leerAnexos(Localizacion lLocalizacion[], Personaje lPersonaje[], Objeto lObjeto[]) throws FileNotFoundException, GestorArchivosException
    {
    	// Comenzamos con el anexoI
        Scanner s = new Scanner (anexo1);
        nombreArchivo = anexo1.getName();
        darValorListasAnexoI(s, lLocalizacion, lPersonaje, lObjeto);
        s = new Scanner (anexo1);
        unirLocalizacionesAdyacentes(s, lLocalizacion);
        
        crearCreencias(lPersonaje, lObjeto);

        // Sacamos los datos de los objetivos del anexoII
        s = new Scanner (anexo2);
        nombreArchivo = anexo2.getName();
        //comprobarFormatoAnexoII(s);
        //s = new Scanner (anexo2);
        darValoresAnexoII(s, lPersonaje);

        s.close();
    }
    
    // Comprueba si el formato del anexoI es correcto, se el pasa el scaner del documento completo.
    private void comprobarFormatoAnexoI(Scanner s) throws GestorArchivosException
    {
        nLinea = 0;
        while (s.hasNextLine()) {
            ++nLinea;
            String linea = s.nextLine();
            if(linea.charAt(0) == '<' || nLinea==1){ //Comprueba si la linea es un encabezado o si es la primera del documento.
                if (!linea.matches(regEncabezado))
                    throw new GestorArchivosException(nLinea,nombreArchivo,"Formato");
            }
            else if (!linea.matches(regContenidoAnexoI)){ // Si no es un encabezado se mira si el formato es de contenido.
            	throw new GestorArchivosException(nLinea,nombreArchivo,"Formato");
            }
        }
    }

    // Comprueba si el formato del anexoII es correcto, se el pasa el scaner del documento completo.
    private void comprobarFormatoAnexoII(Scanner s) throws GestorArchivosException
    {
        nLinea = 0;
        while (s.hasNextLine()) {
            ++nLinea;
            String linea = s.nextLine();
            if(linea.charAt(0) == '<' || nLinea==1){ //Comprueba si la linea es un encabezado o si es la primera del documento.
                if (!linea.matches(regEncabezado))
                	throw new GestorArchivosException(nLinea,nombreArchivo,"Formato");
            }
            else if (!linea.matches(regContenidoAnexoII)){// Si no es un encabezado se mira si el formato es de contenido.
            	throw new GestorArchivosException(nLinea,nombreArchivo,"Formato");
            }
        }
    }
    
    // Lee el documento y comienza a guardar valores en las listas
    private void darValorListasAnexoI(Scanner s, Localizacion lLocalizacion[], Personaje lPersonaje[], Objeto lObjeto[]) throws GestorArchivosException
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
            		guardarLocalizacion(sl, lLocalizacion);
            	}
            	else if(selector == 1) {
            		guardarPersonaje(sl, lLocalizacion, lPersonaje);
            	}
            	else if (selector == 2) {
            		guardarObjeto(sl, lLocalizacion, lPersonaje, lObjeto);
            	}
                sl.close();
            }
        }
    }
        
    private void guardarLocalizacion(Scanner scanner, Localizacion lLocalizacion[]) throws GestorArchivosException
    {
    	String nombreDeSala = scanner.next();
    	for (int i = 0; i <= lLocalizacion.length; i++) {
    		if (lLocalizacion[i] == null) {
    			lLocalizacion[i] = new Localizacion(nombreDeSala);
    			//Contar el numero de localizacione sdayacentes que tiene.
    			int cont = 0;
    			while (scanner.hasNext()){
    				cont++;
                    scanner.next();
                }
    			lLocalizacion[i].setLocalizacionesAdyacentes(new Localizacion[cont]);
    			break;
    		}
    		else if (lLocalizacion[i].getNombre() == nombreDeSala) {
    			throw new GestorArchivosException(nLinea,nombreArchivo,"sala repetida");
    		}
    	}
    }
    
    private void guardarPersonaje(Scanner scanner, Localizacion lLocalizacion[], Personaje lPersonaje[]) throws GestorArchivosException
    {
    	String nombre = scanner.next();
    	for (int i = 0; i <= lPersonaje.length; i++) {
    		if (lPersonaje[i] == null) {
    			Localizacion localizacion = buscarSalaSeguro(scanner.next(), lLocalizacion);
    			lPersonaje[i] = new NPC_aleatorio(nombre, localizacion);
    			break;
    		}
    		else if (lPersonaje[i].getNombre() == nombre)
    			throw new GestorArchivosException(nLinea,nombreArchivo,"personaje repetido");
    	}
    }
    
    private void guardarObjeto(Scanner scanner, Localizacion lLocalizacion[], Personaje lPersonaje[], Objeto lObjeto[]) throws GestorArchivosException
    {
    	String nombre = scanner.next();
    	for (int i = 0; i <= lObjeto.length; i++) {
    		if (lObjeto[i] == null) {
    			// Se crea el objeto
    			lObjeto[i] = new Objeto(nombre);
    			// Se le asigna el objeto al jugador o localizacion corespondiente.
    			nombre = scanner.next();
    			Localizacion localizacion = buscarSala(nombre, lLocalizacion);
    			if (localizacion != null) {
    				localizacion.addObjeto(lObjeto[i]);
    			}
    			else {
    				Personaje personaje = buscarPersonaje(nombre, lPersonaje);
    				if (personaje != null) {
    					personaje.setObjeto(lObjeto[i]);
        			}
    				else
    					throw new GestorArchivosException(nLinea,nombreArchivo,"referencia inexistente a " + nombre);
    			}
    			
    			break;
    		}
    		else if (lObjeto[i].getNombre() == nombre)
    			throw new GestorArchivosException(nLinea,nombreArchivo,"objeto repetido");
    	}
    }
    
    private void unirLocalizacionesAdyacentes(Scanner s, Localizacion lLocalizacion[]) throws GestorArchivosException
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
                	lLocalizacion[contSalas].getAdyacencias()[contSalasAdyacentes] = buscarSalaSeguro(sl.next(), lLocalizacion);
                	contSalasAdyacentes++;
                }
                sl.close();
            	contSalas++;
            	contSalasAdyacentes = 0;
            }
        }
    }
    
    private void darValoresAnexoII(Scanner s, Personaje lPersonaje[]) throws GestorArchivosException
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
            		guardarLocalizacionObjetivo(sl, lPersonaje);
            	}
            	else if(selector == 1) {
            		guardarObjetoObjetivo(sl, lPersonaje);
            	}
                sl.close();
            }
        }
    }
    
    //Se el pasa el scanner de una localizacion objetivo y se lo guarda a un personaje.
    private void guardarLocalizacionObjetivo(Scanner scanner, Personaje lPersonaje[]) throws GestorArchivosException {
    	Personaje personaje = buscarPersonajeSeguro(scanner.next(), lPersonaje);
    	
    	if (personaje != null)
			personaje.setObjetivo(new Ubicacion(scanner.next()));
    }
    
  //Se el pasa el scanner de un objeto objetivo y se lo guarda al personaje.
    private void guardarObjetoObjetivo(Scanner scanner, Personaje lPersonaje[]) throws GestorArchivosException {
    	String objeto = scanner.next();
    	Personaje personaje = null;
    	
    	if (scanner.hasNext()) {
    		personaje = buscarPersonajeSeguro(scanner.next(), lPersonaje);
        	
        	if (personaje != null)
        		personaje.getObjetivo().setNombre(objeto);
    	}
    }
    
    private void crearCreencias(Personaje lPersonaje[], Objeto lObjeto[]) {
    	for(int i=0; i<lPersonaje.length; ++i) {
    		// Se crea la lista de creencias indicando el tamaño de las listas
    		lPersonaje[i].setCreencia(new Creencias(lPersonaje.length, lObjeto.length));
    		// Se recorre el array de personajes y se va guardado el nombre sin el valor.
    		for (int j=0; j<lPersonaje.length; j++) {
    			lPersonaje[i].getCreencias().getUbPersonajes()[j] = new Ubicacion();
    			lPersonaje[i].getCreencias().getUbPersonajes()[j].setNombre(lPersonaje[j].getNombre());
    		}
    		for (int j=0; j<lObjeto.length; j++) {
    			lPersonaje[i].getCreencias().getUbObjetos()[j] = new Ubicacion();
    			lPersonaje[i].getCreencias().getUbObjetos()[j].setNombre(lObjeto[j].getNombre());
    		}
    	}
    }
    
    //Funciones Buscar
    private Localizacion buscarSala(String nombre, Localizacion lLocalizacion[]) {
    	Localizacion localizacion = null;
    	for(int i = 0; i < lLocalizacion.length; i++) {
    		if (lLocalizacion[i].getNombre().equals(nombre)) {
    			localizacion = lLocalizacion[i];
    			break;
    		}
    	}
    	
    	return localizacion;
    }
    
    private Localizacion buscarSalaSeguro(String nombre, Localizacion lLocalizacion[]) throws GestorArchivosException
    {
    	Localizacion localizacion = buscarSala(nombre, lLocalizacion);
    	if (localizacion == null)
    		throw new GestorArchivosException(nLinea,nombreArchivo,"referencia inexistente a " + nombre);
    	return localizacion;
    }
    
    private Personaje buscarPersonaje(String nombre, Personaje lPersonaje[]) {
    	Personaje personaje = null;
    	for(int i = 0; i < lPersonaje.length; i++) {
    		if (lPersonaje[i].getNombre().equals(nombre)) {
    			personaje = lPersonaje[i];
    			break;
    		}
    	}
    	
    	return personaje;
    }
    
    private Personaje buscarPersonajeSeguro(String nombre, Personaje lPersonaje[]) throws GestorArchivosException
    {
    	Personaje personaje = buscarPersonaje(nombre, lPersonaje);
    	if (personaje == null)
    		throw new GestorArchivosException(nLinea,nombreArchivo,"referencia inexistente a " + nombre);
    	return personaje;
    }
}
