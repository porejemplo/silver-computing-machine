import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class GestorArchivos {
	private File anexo1;
	private File anexo2;

	// Formato contra el que se van a comparar las lineas de los anexos.
	final String regEncabezado = "<Localizaciones>|<Personajes>|<Objetos>|<Localización Personajes>|<Posesión Objetos>";
	final String regContenidoAnexoI = "^[\\wáéíóú+\s*]+\\(([\\wáéíóú+\s*]+,)*[\\wáéíóú+\s*]+\\)";
	final String regContenidoAnexoII = "^[\\wáéíóú+\s*]+(\\([\\wáéíóú+\s*]+\\))?";

	// Utiles
	private String nombreArchivo; // Se utiliza para guardar el nombre del archivo que se esta utilizando para referenciarlo en los errores.
	private int nLinea = 0; // Se va a utilizar para guardar la linea que se esta utilizando e indicarlo en caso de error.
	private int selector = 0;

	// Constructores.
	public GestorArchivos() {
	};
	public GestorArchivos(File fAnexoI, File fAnexoII) {
		this.anexo1 = fAnexoI;
		this.anexo2 = fAnexoII;
	}
	public GestorArchivos(String fAnexoI, String fAnexoII) {
		this.anexo1 = new File(fAnexoI);
		this.anexo2 = new File(fAnexoII);
	}

	// Funciones
	public void comprobarFormato() throws FileNotFoundException, GestorArchivosException {
		Scanner s = new Scanner(anexo1); // Primero leemos el anexo 1
		nombreArchivo = anexo1.getName(); // luego se asigna el nombre del archivo para errores
		comprobarFormatoAnexoI(s); // y por ultimo se comprueba el anexo 1.

		s = new Scanner(anexo2); // Primero leemos el anexo 2
		nombreArchivo = anexo2.getName(); // luego se asigna el nombre del archivo para errores
		comprobarFormatoAnexoII(s); // por ultimo se comprueba el anexo 2.

		s.close();
	}

	// Funcion para saber el tamaño de cada lista(0-Localizaciones, 1-Personajes, 2-Objetos)
	public int tamanoLista(int seleccion) throws FileNotFoundException {
		selector = -1;
		int contador = 0;
		Scanner s = new Scanner(anexo1);
		nombreArchivo = anexo1.getName();
		while (s.hasNextLine()) {
			String linea = s.nextLine();
			if (linea.charAt(0) == '<') {
				if (linea.equals("<Localizaciones>"))
					selector = 0;
				else if (linea.equals("<Personajes>"))
					selector = 1;
				else if (linea.equals("<Objetos>"))
					selector = 2;
			} else {
				if (selector == seleccion) {
					contador++;
				}
			}
		}
		s.close();

		return contador;
	}

	// Lee los anexos y asigna los valores a las que se le pasan.
	public void leerAnexos(Localizacion lLocalizacion[], Personaje lPersonaje[], Objeto lObjeto[]) throws FileNotFoundException, GestorArchivosException {
		// Comenzamos con el anexoI
		Scanner s = new Scanner(anexo1);
		nombreArchivo = anexo1.getName();
		darValorListasAnexoI(s, lLocalizacion, lPersonaje, lObjeto);
		s = new Scanner(anexo1);
		unirLocalizacionesAdyacentes(s, lLocalizacion);

		crearCreencias(lPersonaje, lObjeto);

		// Sacamos los datos de los objetivos del anexoII
		s = new Scanner(anexo2);
		nombreArchivo = anexo2.getName();
		darValoresAnexoII(s, lPersonaje);

		s.close();
	}

	// Comprueba si el formato del anexoI es correcto, se el pasa el scaner del documento completo.
	private void comprobarFormatoAnexoI(Scanner s) throws GestorArchivosException {
		nLinea = 0;
		while (s.hasNextLine()) {
			++nLinea;
			String linea = s.nextLine();
			if (linea.charAt(0) == '<' || nLinea == 1) { // Si es la priera linea del documento o empieza por <
				if (!linea.matches(regEncabezado)) // se compara el formato de la linea con el de la cabecera.
					throw new GestorArchivosException(nLinea, nombreArchivo, "Formato");
			} else if (!linea.matches(regContenidoAnexoI)) { // Si no es un encabezado se mira si el formato es de contenido.
				throw new GestorArchivosException(nLinea, nombreArchivo, "Formato");
			}
		}
	}

	// Comprueba si el formato del anexoII es correcto, se el pasa el scaner del documento completo.
	private void comprobarFormatoAnexoII(Scanner s) throws GestorArchivosException {
		nLinea = 0;
		while (s.hasNextLine()) {
			++nLinea;
			String linea = s.nextLine();
			if (linea.charAt(0) == '<' || nLinea == 1) { // Si es la priera linea del documento o empieza por <
				if (!linea.matches(regEncabezado)) // se compara el formato de la linea con el de la cabecera.
					throw new GestorArchivosException(nLinea, nombreArchivo, "Formato");
			} else if (!linea.matches(regContenidoAnexoII)) { // Si no es un encabezado se mira si el formato es de contenido.
				throw new GestorArchivosException(nLinea, nombreArchivo, "Formato");
			}
		}
	}

	// Lee el AnexoI y comienza a guardar valores en las listas
	private void darValorListasAnexoI(Scanner s, Localizacion lLocalizacion[], Personaje lPersonaje[], Objeto lObjeto[]) throws GestorArchivosException {
		nLinea = 0;
		selector = 0;
		while (s.hasNextLine()) {
			nLinea++;
			String linea = s.nextLine();
			if (linea.charAt(0) == '<') {
				if (linea.equals("<Localizaciones>"))
					selector = 0;
				else if (linea.equals("<Personajes>"))
					selector = 1;
				else
					selector = 2;
			} else {
				Scanner sl = new Scanner(linea);
				sl.useDelimiter("\\s*(\\(|,|\\))\\s*");
				if (selector == 0) {
					guardarLocalizacion(sl, lLocalizacion);
				} else if (selector == 1) {
					guardarPersonaje(sl, lLocalizacion, lPersonaje);
				} else if (selector == 2) {
					guardarObjeto(sl, lLocalizacion, lPersonaje, lObjeto);
				}
				sl.close();
			}
		}
	}

	// Guarda el valor de la localizacion enun espacio vacio de la lista
	private void guardarLocalizacion(Scanner scanner, Localizacion lLocalizacion[]) throws GestorArchivosException {
		String nombreDeSala = scanner.next();
		// Se recorre el array de localizaciones ...
		for (int i = 0; i <= lLocalizacion.length; i++) {
			// ... y cuando encontramos una posicion vacia ...
			if (lLocalizacion[i] == null) {
				// ... crea una nueva sala y la guarda en esa posicion.
				lLocalizacion[i] = new Localizacion(nombreDeSala);
				// Aprobechamos para contar el numero de elementos que acompañan al nombre de la sala...
				int cont = 0;
				while (scanner.hasNext()) {
					cont++;
					scanner.next();
				}
				// ... para crear el array de localizaciones adyacentes.
				lLocalizacion[i].setLocalizacionesAdyacentes(new Localizacion[cont]);
				break;
			}
			// Si encontramos el nombre de la sala una segunda vez lanzamo una excepcion para informar de que la sala esta repetida.
			else if (lLocalizacion[i].getNombre() == nombreDeSala) {
				throw new GestorArchivosException(nLinea, nombreArchivo, "sala repetida");
			}
		}
	}

	// Guarda una sala del anexo en en array lista de personajes
	private void guardarPersonaje(Scanner scanner, Localizacion lLocalizacion[], Personaje lPersonaje[]) throws GestorArchivosException {
		String nombre = scanner.next();
		// Recorre el array de personajes
		for (int i = 0; i <= lPersonaje.length; i++) {
			// Si hay una poscion vacia creamos un nuevo jugador y lo guardamos.
			if (lPersonaje[i] == null) {
				Localizacion localizacion = buscarSalaSeguro(scanner.next(), lLocalizacion);
				if(nombre.equals("Jugador")) {
					lPersonaje[i]= new Jugador(nombre, localizacion);
				}else {
					lPersonaje[i] = new NPC_prueba(nombre, localizacion);
				}
				
				break;
			}
			// Si encontramos el nombre del personaje que queremos guardar lanzmaos una excepcion para informar de que el personaje esta repetido.
			else if (lPersonaje[i].getNombre() == nombre)
				throw new GestorArchivosException(nLinea, nombreArchivo, "personaje repetido");
		}
	}

	// Guarda un objeto del anexo en en array lista de personajes
	private void guardarObjeto(Scanner scanner, Localizacion lLocalizacion[], Personaje lPersonaje[], Objeto lObjeto[]) throws GestorArchivosException {
		String nombre = scanner.next();
		for (int i = 0; i <= lObjeto.length; i++) {
			// Si encontramos una posicion vacia guardamos el objeto
			if (lObjeto[i] == null) {
				lObjeto[i] = new Objeto(nombre);
				// Se le asigna el objeto al jugador o localizacion corespondiente.
				nombre = scanner.next();
				Localizacion localizacion = buscarSala(nombre, lLocalizacion);
				if (localizacion != null) {
					localizacion.addObjeto(lObjeto[i]);
				} else {
					Personaje personaje = buscarPersonaje(nombre, lPersonaje);
					if (personaje != null) {
						personaje.setObjeto(lObjeto[i]);
					} else// Si no se encuentra la posicion en el array personajes o localizacion se lanza una excepcion de referencia inexistente.
						throw new GestorArchivosException(nLinea, nombreArchivo, "referencia inexistente a " + nombre);
				}

				break;
			}
			// Si se encuntra el nombre del objeto que estamos buscando se lanza una excepcion para informar de que el objeto esta repetido.
			else if (lObjeto[i].getNombre() == nombre)
				throw new GestorArchivosException(nLinea, nombreArchivo, "objeto repetido");
		}
	}

	// Recorre el array de localizaciones por segunnda vez(necesita tener las localizaciones ya creadas) para unir las localizaciones adyacentes.
	private void unirLocalizacionesAdyacentes(Scanner s, Localizacion lLocalizacion[]) throws GestorArchivosException {
		nLinea = 0;
		selector = 1;
		int contSalas = 0; // utilizamos este contador para reconocer las salas.
		int contSalasAdyacentes = 0;
		while (s.hasNextLine()) {
			nLinea++;
			String linea = s.nextLine();
			if (linea.charAt(0) == '<') {
				if (linea.equals("<Localizaciones>"))
					selector = 0;
				else
					selector = 1;
			} else if (selector == 0) {
				Scanner sl = new Scanner(linea);
				sl.useDelimiter("\\s*(\\(|,|\\))\\s*");
				sl.next(); // Se descarta la primera entrada porque es el nombre de la propia sala
				while (sl.hasNext()) {
					lLocalizacion[contSalas].getAdyacencias()[contSalasAdyacentes] = buscarSalaSeguro(sl.next(), lLocalizacion);
					contSalasAdyacentes++;
				}
				sl.close();
				contSalas++;
				contSalasAdyacentes = 0;
			}
		}
	}

	// Lee el AnexoII y guarda los valores en la los arrays.
	private void darValoresAnexoII(Scanner s, Personaje lPersonaje[]) throws GestorArchivosException {
		nLinea = 0;
		selector = 0;
		while (s.hasNextLine()) {
			nLinea++;
			String linea = s.nextLine();
			if (linea.charAt(0) == '<') {
				if (linea.equals("<Localización Personajes>"))
					selector = 0;
				else if (linea.equals("<Posesión Objetos>"))
					selector = 1;
			} else {
				Scanner sl = new Scanner(linea);
				sl.useDelimiter("\\s*(\\(|,|\\))\\s*");
				if (selector == 0) {
					guardarLocalizacionObjetivo(sl, lPersonaje);
				} else if (selector == 1) {
					guardarObjetoObjetivo(sl, lPersonaje);
				}
				sl.close();
			}
		}
	}

	// Se el pasa el scanner de una localizacion objetivo y se lo guarda a un personaje.
	private void guardarLocalizacionObjetivo(Scanner scanner, Personaje lPersonaje[]) throws GestorArchivosException {
		Personaje personaje = buscarPersonajeSeguro(scanner.next(), lPersonaje);

		if (personaje != null)
			personaje.setObjetivo(new Ubicacion(scanner.next()));
	}

	// Se el pasa el scanner de un objeto objetivo y se lo guarda al personaje.
	private void guardarObjetoObjetivo(Scanner scanner, Personaje lPersonaje[]) throws GestorArchivosException {
		String objeto = scanner.next();

		// Se comprueba el objeto tiene despues un personaje alque ser asignado.
		if (scanner.hasNext()) {
			Personaje personaje = buscarPersonajeSeguro(scanner.next(), lPersonaje);

			if (personaje != null)
				personaje.getObjetivo().setNombre(objeto);
		}
	}

	// Crea las creencias vacias de cada jugador, guardando los nombres de todos los jugadores y las salas.
	private void crearCreencias(Personaje lPersonaje[], Objeto lObjeto[]) {
		for (int i = 0; i < lPersonaje.length; ++i) {
			// Se crea la lista de creencias indicando el tamaño de las listas
			lPersonaje[i].setCreencia(new Creencias(lPersonaje.length, lObjeto.length));
			// Se recorre el array de personajes y se va guardado el nombre sin el valor.
			for (int j = 0; j < lPersonaje.length; j++) {
				lPersonaje[i].getCreencias().getUbPersonajes()[j] = new Ubicacion();
				lPersonaje[i].getCreencias().getUbPersonajes()[j].setNombre(lPersonaje[j].getNombre());
			}
			for (int j = 0; j < lObjeto.length; j++) {
				lPersonaje[i].getCreencias().getUbObjetos()[j] = new Ubicacion();
				lPersonaje[i].getCreencias().getUbObjetos()[j].setNombre(lObjeto[j].getNombre());
			}
		}
	}

	// Funciones Buscar
	// Busca una sala sin lanzar excepcion.
	private Localizacion buscarSala(String nombre, Localizacion lLocalizacion[]) {
		Localizacion localizacion = null;
		for (int i = 0; i < lLocalizacion.length; i++) {
			if (lLocalizacion[i].getNombre().equals(nombre)) {
				localizacion = lLocalizacion[i];
				break;
			}
		}

		return localizacion;
	}

	// La misma funcion buscr sala pero lanzando una excepcion de referencia inexistente
	private Localizacion buscarSalaSeguro(String nombre, Localizacion lLocalizacion[]) throws GestorArchivosException {
		Localizacion localizacion = buscarSala(nombre, lLocalizacion);
		if (localizacion == null)
			throw new GestorArchivosException(nLinea, nombreArchivo, "referencia inexistente a " + nombre);
		return localizacion;
	}

	// Funcion buscar personaje
	private Personaje buscarPersonaje(String nombre, Personaje lPersonaje[]) {
		Personaje personaje = null;
		for (int i = 0; i < lPersonaje.length; i++) {
			if (lPersonaje[i].getNombre().equals(nombre)) {
				personaje = lPersonaje[i];
				break;
			}
		}

		return personaje;
	}

	// Funcion buscar presonaje pero lanzando una excepcion de referencia inexistente si no se encuentra.
	private Personaje buscarPersonajeSeguro(String nombre, Personaje lPersonaje[]) throws GestorArchivosException {
		Personaje personaje = buscarPersonaje(nombre, lPersonaje);
		if (personaje == null)
			throw new GestorArchivosException(nLinea, nombreArchivo, "referencia inexistente a " + nombre);
		return personaje;
	}

	//Guarda el estado del juego en el archivo anexo1
	public void guardarEstadoJuego(String string){
		try {
			FileWriter writer = new FileWriter(anexo1, false);
			writer.write(string);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Da formato de string a los array de datos del juego.
	public void guardarEstadoJuego (Localizacion lLocalizacion[], Personaje lPersonaje[], Objeto lObjeto[]){
		String sObjetos = "\n<Objetos>";
		String s = "<Localizaciones>";
		for(int i = 0; i<lLocalizacion.length; ++i)
		{
			s += "\n" + lLocalizacion[i].getNombre() + "(";
			for(int ii=0; ii<lLocalizacion[i].getAdyacencias().length; ++ii)
			{
				if(ii>0)
					s += ", ";
				s += lLocalizacion[i].getAdyacencias()[ii].getNombre();
			}
			for(int ii=0; ii<lLocalizacion[i].getObjetos().size(); ++ii)
			{
				sObjetos += "\n" + lLocalizacion[i].getObjetos().get(ii).getNombre() + "(" + lLocalizacion[i].getNombre() + ")";
			}
			s += ")";
		}
		s += "\n<Personajes>";
		for(int i = 0; i<lPersonaje.length; ++i)
		{
			s += "\n" + lPersonaje[i].getNombre() + "(" + lPersonaje[i].getLocalizacion().getNombre() + ")";
			if(lPersonaje[i].getObjeto() != null)
				sObjetos += "\n" + lPersonaje[i].getObjeto().getNombre() + "(" + lPersonaje[i].getNombre() + ")";
		}
		s += sObjetos;
		guardarEstadoJuego(s);
	}
}
