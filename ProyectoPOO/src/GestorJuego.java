import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestorJuego implements ActionListener{
	
	private Jugador jugador;
	private Localizacion listaSalas[];
	private Personaje listaPersonajes[];
	private Objeto listaObjetos[];
	String opciones[] = {"Cambiar sala", "Pedir Objeto", "Dar Objeto", "Coger Objeto", "Dejar Objeto", "No hacer nada"};
	// La creencias del gestor del juego representan el estado actual del juego
	private Creencias estadoJuego;
	private int ronda = 0;
	// Solicitudes es una matriz de adyacencia del grafo dirigido asociado a lospersonajes
	// Dos nodos conectados representan una solicitud de objeto de un personaje a otro
	private Objeto solicitudes[][];
	// La historia es un string que se va rellenando con las acciones de cada uno de los personajes.
	private short acabado = 0;
	public Ventana ventana; 

	public GestorJuego(Localizacion[] salas, Personaje[] personajes, Objeto[] objetos) {
		listaSalas = salas;
		listaPersonajes = personajes;
		listaObjetos = objetos;
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
	
	public String[] getOpciones() {
		return opciones;
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
	public void setEstado(Creencias certezas) {
		estadoJuego = certezas;
	}
	
	
	public void siguienteRonda() {
		if(acabado == listaPersonajes.length) {
			finalizar();
		}else {
			acabado = 0;
			ronda++;
			Personaje.continuarHistoria("Comienza la ronda: " +ronda+".\n");
			System.out.print("\nComienza la ronda "+ronda);
			//System.out.print(this);
			for(int i = 0; i < listaPersonajes.length; i++) {
				ejecutarAccion(listaPersonajes[i], listaPersonajes[i].dameAccion(accionesPermitidas(listaPersonajes[i])));
			}
			ventana.actualizarVentana(jugador);
			ventana.cambiarBotones(opciones, this, accionesPermitidas(jugador));
		}
	}
	
	public void empezarJuego() {
		
		//Almacenar el estado inicial del juego en formato creencias
		Ubicacion aux[]= new Ubicacion[listaPersonajes.length];
		Ubicacion aux2[]= new Ubicacion[listaObjetos.length];
		
		//Obtener la seccion de personajes
		for(int i = 0; i < listaPersonajes.length; i++) {
			aux[i]= new Ubicacion(listaPersonajes[i].getNombre(), listaPersonajes[i].getLocalizacion().getNombre());
		}
		
		//Obtener la seccion de objetos
		for(int i = 0; i < listaObjetos.length; i++) {
			aux2[i]= new Ubicacion(listaObjetos[i].getNombre());
		}
		for(int i = 0; i < listaSalas.length; i++) {
			for(int j = 0; j < listaObjetos.length; j++) {
				for(int k = 0; k < listaSalas[i].getObjetos().size();k++) {
					if(listaObjetos[j].equals(listaSalas[i].getObjetos().toArray()[k])) {
						aux2[j].setLugar(listaSalas[i].getNombre());
					}
				}
			}
		}
		for(int i = 0; i < listaPersonajes.length; i++) {
			for(int j = 0; j < listaObjetos.length; j++) {
				if(listaPersonajes[i].getObjeto() != null && listaObjetos[j].equals(listaPersonajes[i].getObjeto())) {
					aux2[j].setLugar(listaPersonajes[i].getNombre());
				}
			}
		}
		
		setEstado(new Creencias(aux, aux2)); //Establecer las creencias
		
		//Encontrar al jugador
		for(int i = 0; i < listaPersonajes.length; i++) {	
			if(listaPersonajes[i] instanceof Jugador ) {
				System.out.print("Hay jugador " + i +"\n");
				jugador = (Jugador) listaPersonajes[i];
			}
		}
		//Mostrar a los personajes su situacion inicial
		for(int i = 0; i < listaPersonajes.length; i++) {
			mostrarSala(listaPersonajes[i], listaPersonajes[i].getLocalizacion());
		}
		ventana = new Ventana(opciones,this, jugador.getCreencias(), accionesPermitidas(jugador));
		ventana.setVisible(true);
		
	}
	
	public boolean[] accionesPermitidas(Personaje personaje) { //NOMBRE.Filtrar que acciones puede y no puede hacer un personaje
		boolean acciones[] = new boolean[6];
		int i; //iterador
		
		//1. Cambiar de sala.Un personaje siempre puede decidir cambiarse de sala cambiarse de sala
		acciones[0]=true;
		
		//2. Pedir objeto. Si hay m�s personajes en la sala aparte del que realiza la accion, entoces puede pedir un objeto
		if(personaje.getLocalizacion().getPersonajes().size()>1) acciones[1] = true;
		System.out.print(personaje.getLocalizacion().getPersonajes().size());
		
		//3. Dar objeto. Comprobar en la matriz de solicitudes si alguien le ha pedido un objeto a jugador
		if(personaje.getObjeto()==null) {
			acciones[2]=false;
		}/*else {
			for(i=0; i < listaPersonajes.length || listaPersonajes[i].getNombre().equals(personaje.getNombre()) ; i++);
			
			//Comprobar si en su columna correspondiente de la matriz hay algun objeto (le han pedido un objeto)
			for(int j=0; j < listaPersonajes.length;j++) {
				if(solicitudes[j][i]!=null) {
					acciones[2]=true;
					break;
				}
			}
		}*/
		//Coseguir el �ndice del personaje en la lista
		/**/
		//4. Coger objeto. Si hay objetos sueltos en la sala, entonces puede coger uno de ellos
		if(personaje.getLocalizacion().getObjetos().size()!=0) acciones[3] = true;
		
		//5. Dejar objeto. Si el personaje lleva un objeto, puede decidir soltarlo
		if(personaje.getObjeto()!=null) acciones[4] = true;
		
		//6. No hacer nada.Un personaje siempre puede decidir no hacer nada
		acciones[5] = true;

		return acciones;
	}
	
	public void ejecutarAccion(Personaje personaje, int accion) {//NOMBRE.Hacer las acciones de cada personaje, falta completar
		Objeto objeto;
		Personaje otroPersonaje;
		int i=0,j=0; //iteradores
		switch(accion) {
		case 0: //Ir a localizacion
			System.out.println("Cambiar de sala");	
			Personaje.continuarHistoria(personaje.getNombre()+" decide cambiarse de sala.\n");
			cambiarSala(personaje, personaje.getLocalizacion(), personaje.especificarSala(personaje.getLocalizacion().getAdyacencias()));
			Personaje.continuarHistoria("Ahora se encuentra en "+personaje.getLocalizacion().getNombre()+".\n\n");
			break;
		case 1: //Pedir objeto
			objeto = personaje.especificarObjeto(listaObjetos);
			otroPersonaje= personaje.especificarPersonaje(personaje.getLocalizacion().getPersonajes().toArray(listaPersonajes));
			System.out.println("Pedir Objeto");
			Personaje.continuarHistoria(personaje.getNombre()+"decide perdirle a "+otroPersonaje.getNombre()+" el objeto: "+ objeto+".\n\n");
			
			
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
			Personaje.continuarHistoria(personaje.getNombre()+" decide darle a "+otroPersonaje.getNombre()+" su objeto.\n\n");
			cambiarObjeto(personaje, otroPersonaje);
			break;
		case 3: //Coger objeto
			System.out.println("Coger Objeto");
			objeto = personaje.especificarObjeto(personaje.getLocalizacion().getObjetos().toArray(listaObjetos));
			Personaje.continuarHistoria(personaje.getNombre()+" decide coger el objeto: "+objeto+" del suelo de la sala " + personaje.getLocalizacion().getNombre()+".\n\n");
			cambiarObjeto(objeto, personaje.getLocalizacion(), personaje);
			break;
		case 4: //Dejar objeto
			System.out.println("Dejar Objeto");
			Personaje.continuarHistoria(personaje.getNombre()+" decide dejar su objeto en el suelo de la sala " + personaje.getLocalizacion().getNombre()+".\n\n");
			cambiarObjeto(personaje, personaje.getLocalizacion());
			break;
		default:
			Personaje.continuarHistoria(personaje.getNombre()+" decide no hacer nada.\n\n");
			acabado++;
			System.out.println("Nada");
			break;
		}
		
	}
	
	public void cambiarSala(Personaje personaje, Localizacion origen, Localizacion destino) {
		personaje.setLocalizacion(destino);
		estadoJuego.cambiarCreencia(personaje, destino);
		origen.removePersonaje(personaje);
		destino.addPersonaje(personaje);
		
		
		//Informar a la sala origen del cambio
		for(int i = 0; i < origen.getPersonajes().size(); i++){
		  		origen.getPersonajes().get(i).getCreencias().cambiarCreencia(personaje,destino);
		  		if(origen.getPersonajes().get(i) instanceof Jugador) {
		  			ventana.getHistoria().append(personaje.getNombre()+" se cambia a la sala " + destino.getNombre()+"\n");
		  		}
		 }
		
		//Informar a la sala destino del cambio
				for(int i = 0; i < destino.getPersonajes().size(); i++){
				  		destino.getPersonajes().get(i).getCreencias().cambiarCreencia(personaje,destino);
				  		if(destino.getPersonajes().get(i) instanceof Jugador) {
				  			ventana.getHistoria().append(personaje.getNombre()+" ha llegado a " + destino.getNombre()+"\n");
				  		}
				 }
		mostrarSala(personaje,destino);
		
	}
	public void cambiarObjeto(Personaje emisor, Personaje receptor) {
		receptor.setObjeto(emisor.getObjeto());
		emisor.setObjeto(null);
		estadoJuego.cambiarCreencia(receptor.getObjeto(), receptor);
		
		//Informar a la sala del cambio
		for(int i = 0; i < emisor.getLocalizacion().getPersonajes().size(); i++){
		  	emisor.getLocalizacion().getPersonajes().get(i).getCreencias().cambiarCreencia(receptor.getObjeto(), receptor);
		  	if(emisor.getLocalizacion().getPersonajes().get(i) instanceof Jugador) {
	  			ventana.getHistoria().append(emisor.getNombre()+" le da a " + receptor.getNombre() + " su " + receptor.getObjeto().getNombre()+"\n");
	  		}
		}
	}
	public void cambiarObjeto(Personaje emisor, Localizacion destino) {
		estadoJuego.cambiarCreencia(emisor.getObjeto(), destino);
		destino.addObjeto(emisor.getObjeto());

		//Informar a la sala del cambio
		for(int i = 0; i < destino.getPersonajes().size(); i++){
		  	destino.getPersonajes().get(i).getCreencias().cambiarCreencia(emisor.getObjeto(), destino);
		  	if(emisor.getLocalizacion().getPersonajes().get(i) instanceof Jugador) {
	  			ventana.getHistoria().append(emisor.getNombre()+" deja en  " + destino.getNombre() + " su " + emisor.getObjeto().getNombre()+"\n");
	  		}
		}
		emisor.setObjeto(null);
		 
	}
	public void cambiarObjeto(Objeto objeto, Localizacion origen, Personaje receptor) {
		receptor.setObjeto(objeto);
		estadoJuego.cambiarCreencia(objeto, receptor);
		origen.removeObjeto(objeto);
		
		//Informar a la sala del cambio
		for(int i = 0; i < origen.getPersonajes().size(); i++){
		  	origen.getPersonajes().get(i).getCreencias().cambiarCreencia(objeto, receptor);
		  	if(origen.getPersonajes().get(i) instanceof Jugador) {
	  			ventana.getHistoria().append(receptor.getNombre()+" coge  " + receptor.getObjeto().getNombre() + " de " + origen.getNombre());
	  		}
		}
		
	}
	
	public void mostrarSala(Personaje personaje, Localizacion destino) {
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
		estado+=("<Localizaciones>\n") ;
		for(int i = 0; i < listaSalas.length; i++) {
			estado+=(listaSalas[i].getNombre()+"(");
			for(int j = 0; j < listaSalas[i].getAdyacencias().length; j++) {
				if(j!=listaSalas[i].getAdyacencias().length-1) {
					estado+=(listaSalas[i].getAdyacencias()[j].getNombre() + ",");
				}else {
					estado+=(listaSalas[i].getAdyacencias()[j].getNombre() + ")\n");
				}
				
			}
		}
		estado += estadoJuego;
		
		return estado;
	}

	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		int encontrado = 0;
		int i;
		for(i=0; i < opciones.length; i++) {
			if(s.matches(opciones[i])) {
				jugador.setAccionElegida(i);
				encontrado = 1;
				System.out.print("El jugador elige "+opciones[i]+"\n");
				break;
			}
		}
		switch(i) {
		case 0: //Cambiar de localizacion
			//System.out.print("Se va a cambiar a "+opciones[i]+"\n");
			ventana.cambiarBotones(jugador.getLocalizacion().getAdyacencias(), this);
			break;
		case 1: //Pedir un objeto a alguien
			ventana.cambiarBotones(listaPersonajes, this);
			break;
		case 2: //Dar objeto
			int j = 0;
			for(int k = 0; k < listaPersonajes.length; k++) { //Obtener el numero de solicitantes
				if(solicitudes[k][jugador.getIndice()]!=null) {
					j++;
				}
			}
			Personaje solicitantes[] = new Personaje[j];
			for(int k = 0; k < listaPersonajes.length; k++) {
				if(solicitudes[k][jugador.getIndice()]!=null) {
					solicitantes[k]=listaPersonajes[k];
				}
			}
			ventana.cambiarBotones(solicitantes, this);
			break;
		case 3: //Coger objeto
			//System.out.print(jugador.getLocalizacion().getObjetos().size());
			ventana.cambiarBotones(jugador.getLocalizacion().getObjetos().toArray(new Objeto[jugador.getLocalizacion().getObjetos().size()]), this);
			break;
		case 4: //Dejar objeto
		case 5: //No hacer nada
			if(encontrado==1) {
				System.out.print("Va a empezar la siguiente ronda");
				siguienteRonda();
			}
		break;
		}
		if(encontrado == 0) {
			for(i=0; i < listaPersonajes.length; i++) {
				if(s.matches(listaPersonajes[i].getNombre())) {
					jugador.setPElegido(listaPersonajes[i]);
					System.out.print("Elijo a " + listaPersonajes[i].getNombre());
					if(jugador.getAccionElegida()==1) {
						ventana.cambiarBotones(listaObjetos, this);
					}else {
						encontrado = 2;
					}
				}
			}
		}
		if(encontrado == 0) {
			for(i=0; i < listaSalas.length; i++) {
				if(s.matches(listaSalas[i].getNombre())) {
					
					jugador.setLElegida(listaSalas[i]);
					System.out.print("Elijo a " + jugador.getLElegida().getNombre());
					encontrado = 2;
				}
			}
		}
		if(encontrado == 0) {
			for(i=0; i < listaObjetos.length; i++) {
				//System.out.print("\n"+listaObjetos.length);
				if(s.matches(listaObjetos[i].getNombre())) {
					System.out.print("Elijo a " + listaObjetos[i].getNombre());
					jugador.setOElegido(listaObjetos[i]);
					encontrado = 2;
				}
			}
		}
		if(encontrado == 2) {
			System.out.print("Va a empezar la siguiente ronda");
			siguienteRonda();
		}
		
		
	}
}





