import java.io.FileNotFoundException;
import java.io.IOException;

public class GestorJuego {
	private Localizacion listaSalas[];
	private Personaje listaPersonajes[];
	private Objeto listaObjetos[];
	// La creencias del gestor del juego representan el estado actual del juego
	private Creencias certezas;
	private int ronda = 0;
	// Solicitudes es una matriz de adyacencia del grafo dirigido asociado a los
	// personajes
	// Dos nodos conectados representan una solicitud de objeto de un personaje a
	// otro
	private Objeto solicitudes[][];
	// La historia ser� un string que se ir� rellenando con las acciones de cada uno
	// de los personajes.
	private String historia = "";
	private short acabado = 0;

	public GestorJuego(Localizacion[] salas, Personaje[] personajes, Objeto[] objetos, Creencias estadoInicial) {
		listaSalas = salas;
		listaPersonajes = personajes;
		listaObjetos = objetos;
		certezas = estadoInicial;
		solicitudes = new Objeto[personajes.length][personajes.length];
	}

	public GestorJuego() {
	};

	public Localizacion[] getListaSalas() {
		return listaSalas;
	}

	public Personaje[] getListaPersonajes() {
		return listaPersonajes;
	}

	public Objeto[] getListaObjetos() {
		return listaObjetos;
	}

	public void setListaSalas(Localizacion[] listaSalas) {
		this.listaSalas = listaSalas;
	}

	public void setListaPersonajes(Personaje[] listaPersonajes) {
		this.listaPersonajes = listaPersonajes;
	}

	public void setListaObjetos(Objeto[] listaObjetos) {
		this.listaObjetos = listaObjetos;
	}

	public static void main(String[] args) {
		// Anetes de ejecutar el juego
		GestorJuego gJuego = new GestorJuego();
		GestorArchivos ga = new GestorArchivos("AnexoI.txt", "AnexoII.txt");
		try {
			ga.comprobarFormato();
			gJuego.setListaSalas(new Localizacion[ga.tamanoLista(0)]);
			gJuego.setListaPersonajes(new Personaje[ga.tamanoLista(1)]);
			gJuego.setListaObjetos(new Objeto[ga.tamanoLista(2)]);
			ga.leerAnexos(gJuego.listaSalas, gJuego.listaPersonajes, gJuego.listaObjetos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (GestorArchivosException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * 
		 * Bucle de juego.
		 * 
		 */
		
		//Guardado de datos
		ga.guardarEstadoJuego(gJuego.listaSalas, gJuego.listaPersonajes, gJuego.listaObjetos);
	}
	
	public void siguienteRonda() {
		if(acabado == listaPersonajes.length) {
			finalizar();
		}else {
			ronda++;
			historia.concat("Comienza la ronda: " +ronda+".\n");
			System.out.print("\nComienza la ronda "+ronda);
			for(int i = 0; i < listaPersonajes.length; i++) {
				ejecutarAccion(listaPersonajes[i], pedirAccion(listaPersonajes[i],accionesPermitidas(listaPersonajes[i])));
			}
		}
	}
	
	public boolean[] accionesPermitidas(Personaje personaje) { //NOMBRE.Filtrar que acciones puede y no puede hacer un personaje
		boolean acciones[] = new boolean[6];
		int i; //iterador
		
		//1. Cambiar de sala.Un personaje siempre puede decidir cambiarse de sala cambiarse de sala
		acciones[0]=true;
		
		//2. Pedir objeto. Si hay m�s personajes en la sala aparte del que realiza la accion, entoces puede pedir un objeto
		if(personaje.getLocalizacion().getPersonajes().size()>1) acciones[1] = true;
		
		//3. Dar objeto. Comprobar en la matriz de solicitudes si alguien le ha pedido un objeto a jugador
		
		//Coseguir el �ndice del personaje en la lista
		for(i=0; i < listaPersonajes.length || personaje.getNombre()==listaPersonajes[i].getNombre() ; i++);
		
		//Comprobar si en su columna correspondiente de la matriz hay algun objeto (le han pedido un objeto)
		for(int j=0; j < listaPersonajes.length;j++) {
			if(solicitudes[j][i]!=null) {
				acciones[2]=true;
				break;
			}
		}
		//4. Coger objeto. Si hay objetos sueltos en la sala, entonces puede coger uno de ellos
		if(personaje.getLocalizacion().getObjetos().size()>0) acciones[3] = true;
		
		//5. Dejar objeto. Si el personaje lleva un objeto, puede decidir soltarlo
		if(personaje.getObjeto()!=null) acciones[4] = true;
		
		//6. No hacer nada.Un personaje siempre puede decidir no hacer nada
		acciones[5] = true;
		
		
		/*for(int i = 0; i < 6; i++) {
			if(acciones[i]==true) {
				System.out.println("True");
			}else if (acciones[i]==false) {
				System.out.println("False");
			}
		}
		System.out.println("\n");*/
		return acciones;
	}
	
	public int pedirAccion(Personaje personaje, boolean[] acciones) {
		return personaje.elegirAccion(acciones);
	}
	
	public void ejecutarAccion(Personaje personaje, int accion) {//NOMBRE.Hacer las acciones de cada personaje, falta completar
		Objeto objeto;
		Personaje otroPersonaje;
		int i=0,j=0; //iteradores
		switch(accion) {
		case 0: //Ir a localizacion
			System.out.println("Cambiar de sala");
			historia.concat("El personaje "+personaje+" decide cambiarse de sala.");
			cambiarSala(personaje, personaje.getLocalizacion(), personaje.especificarSala(personaje.getLocalizacion().getAdyacencias()));
			historia.concat("Ahora se encuentra en la sala "+personaje.getLocalizacion().getNombre()+".\n");
			break;
		case 1: //Pedir objeto
			objeto = personaje.especificarObjeto(listaObjetos);
			otroPersonaje= personaje.especificarPersonaje(personaje.getLocalizacion().getPersonajes().toArray(listaPersonajes));
			System.out.println("Pedir Objeto");
			historia.concat("El personaje "+personaje+"decide perdirle a "+otroPersonaje.getNombre()+" el objeto: "+ objeto+".\n");
			
			
			//Obtener el indice del personaje que pide, en a lista de personajes
			for(i=0; i < listaPersonajes.length || personaje.equals(listaPersonajes[i]) ; i++);
			
			//Obtener el indice del personaje al que le piden, en la lista de personajes
			for(j=0; j < listaPersonajes.length || otroPersonaje.equals(listaPersonajes[j]) ; j++);
			
			//Almacenar la solicitud en la matriz
			solicitudes[i][j]=objeto;
			
			break;
		case 2: //Dar objeto
			//Obtener indice del solicitado
			for(i=0; i < listaPersonajes.length || personaje.equals(listaPersonajes[i]) ; i++);
			//Obtener solicitantes 
			for(int k = 0; k < listaPersonajes.length; k++) {
				if(solicitudes[k][i]!=null) {
					j++;
				}
			}
			//Crear un array con todos los personajes que le hayan pedido un objeto la ronda anterior
			Personaje solicitantes[] = new Personaje[j];
			for(int k = 0; k < listaPersonajes.length; k++) {
				if(solicitudes[k][i]!=null) {
					solicitantes[k]=listaPersonajes[k];
				}
			}
			
			System.out.println("Dar Objeto");
			otroPersonaje = personaje.especificarPersonaje(solicitantes);
			historia.concat("El personaje "+personaje+" decide darle a "+otroPersonaje.getNombre()+" su objeto.\n");
			cambiarObjeto(personaje, otroPersonaje);
			break;
		case 3: //Coger objeto
			System.out.println("Coger Objeto");
			objeto = personaje.especificarObjeto(personaje.getLocalizacion().getObjetos().toArray(listaObjetos));
			historia.concat("El personaje "+personaje+" decide coger el objeto: "+objeto+" del suelo de la sala " + personaje.getLocalizacion()+".\n");
			cambiarObjeto(objeto, personaje.getLocalizacion(), personaje);
			break;
		case 4: //Dejar objeto
			System.out.println("Dejar Objeto");
			historia.concat("El personaje "+personaje+" decide dejar su objeto en el suelo de la sala " + personaje.getLocalizacion()+".\n");
			cambiarObjeto(personaje, personaje.getLocalizacion());
			break;
		default:
			historia.concat("El personaje "+personaje+" decide no hacer nada.\n");
			acabado++;
			System.out.println("Nada");
			break;
		}
		
	}
	
	public void cambiarSala(Personaje personaje, Localizacion origen, Localizacion destino) {
		personaje.setLocalizacion(destino);
		certezas.cambiarCreencia(personaje, destino);
		origen.removePersonaje(personaje);
		destino.addPersonaje(personaje);
		
		
		//Informar a la sala del cambio
		for(int i = 0; i < origen.getPersonajes().size(); i++){
		  		origen.getPersonajes().get(i).getCreencias().cambiarCreencia(personaje,destino);
		 }
		ensenarSala(personaje,destino);
		
	}
	public void cambiarObjeto(Personaje emisor, Personaje receptor) {
		receptor.setObjeto(emisor.getObjeto());
		emisor.setObjeto(null);
		certezas.cambiarCreencia(receptor.getObjeto(), receptor);
		
		//Informar a la sala del cambio
		for(int i = 0; i < emisor.getLocalizacion().getPersonajes().size(); i++){
		  	emisor.getLocalizacion().getPersonajes().get(i).getCreencias().cambiarCreencia(receptor.getObjeto(), receptor);
		}
	}
	public void cambiarObjeto(Personaje emisor, Localizacion destino) {
		certezas.cambiarCreencia(emisor.getObjeto(), destino);
		destino.addObjeto(emisor.getObjeto());

		//Informar a la sala del cambio
		for(int i = 0; i < destino.getPersonajes().size(); i++){
		  	destino.getPersonajes().get(i).getCreencias().cambiarCreencia(emisor.getObjeto(), destino);
		}
		emisor.setObjeto(null);
		 
	}
	public void cambiarObjeto(Objeto objeto, Localizacion origen, Personaje receptor) {
		receptor.setObjeto(objeto);
		certezas.cambiarCreencia(objeto, receptor);
		origen.removeObjeto(objeto);
		
		//Informar a la sala del cambio
		for(int i = 0; i < origen.getPersonajes().size(); i++){
		  	origen.getPersonajes().get(i).getCreencias().cambiarCreencia(objeto, receptor);
		}
		
	}
	
	public void ensenarSala(Personaje personaje, Localizacion destino) {
		//Actualizar las ubicaciones de los objetos que se encuentren en esa sala
		for(int i = 0; i < destino.getObjetos().size();i++) {
			personaje.getCreencias().cambiarCreencia(destino.getObjetos().get(i),destino);
		}
		//Actualizar las ubicaciones de los personajes que se ecnuentren en esa sala
		for(int i = 0; i < destino.getPersonajes().size();i++) {
			personaje.getCreencias().cambiarCreencia(destino.getPersonajes().get(i),destino);
		}
	}
	/*public int indexOf(Objeto objeto, Objeto Objetos[]) {
		int i = 0;
		for(; i < Objetos.length || objeto.equals(Objetos[i]);i++);
		return i;
	}
	public int indexOf(Personaje personaje, Personaje personajes[]) {
		int i = 0;
		for(; i < personajes.length || personaje.equals(personajes[i]);i++);
		return i;
	}*/
	
	public void finalizar() {
		//Terminar el juego, escribir en fichero, mostrar historia
	}
	
	public String toString() {
		String estado ="";
		estado.concat("<Localizaciones>\n") ;
		for(int i = 0; i < listaSalas.length; i++) {
			estado.concat(listaSalas[i].getNombre()+"(");
			for(int j = 0; j < listaSalas[i].getAdyacencias().length-1; j++) {
				estado.concat(listaSalas[i].getAdyacencias()[j].getNombre() + ",");
			}
			estado.concat(listaSalas[i].getAdyacencias()[listaSalas[i].getAdyacencias().length].getNombre() + ")\n");
		}
		estado += certezas;
		
		return estado;
	}
}





