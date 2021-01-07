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
	private Solicitud solicitudes[];
	// La historia es un string que se va rellenando con las acciones de cada uno de los personajes.
	private short acabado = 0;
	public Ventana ventana; 

	public GestorJuego(Localizacion[] salas, Personaje[] personajes, Objeto[] objetos) {
		listaSalas = salas;
		listaPersonajes = personajes;
		listaObjetos = objetos;
		//solicitudes = new Objeto[personajes.length][personajes.length];
		solicitudes = new Solicitud[personajes.length];
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
			solicitudes= new Solicitud[listaPersonajes.length]; //Borrar todas las solicitudes de la lista que no se hayan resuelto
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
		
		//2. Pedir objeto. Si hay mas personajes en la sala aparte del que realiza la accion y no tiene ningun objeto, entoces puede pedir un objeto
		if(personaje.getLocalizacion().getPersonajes().size()>1 && personaje.getObjeto() == null) acciones[1] = true;
		//System.out.print(personaje.getLocalizacion().getPersonajes().size());
		
		//3. Dar objeto. Comprobar  si alguien le ha pedido un objeto a un personaje
		 if(personaje.leHanPedido(solicitudes)) {
			for(i = 0; i< solicitudes.length; i++) {
				if(personaje.equals(solicitudes[i].getSolicitado())&& personaje.getObjeto().equals(solicitudes[i].getObjetoPed())) {
					acciones[2]=true;
				}
			}
		}
		//4. Coger objeto. Si hay objetos sueltos en la sala y no lleva ninguno, entonces puede coger uno de ellos
		if(personaje.getLocalizacion().getObjetos().size()!=0 && personaje.getObjeto()==null) acciones[3] = true;
		
		//5. Dejar objeto. Si el personaje lleva un objeto, puede decidir soltarlo
		if(personaje.getObjeto()!=null) acciones[4] = true;
		
		//6. No hacer nada.Un personaje siempre puede decidir no hacer nada
		acciones[5] = true;

		return acciones;
	}
	
	public void ejecutarAccion(Personaje personaje, int accion) {
		Objeto objeto;
		Personaje otroPersonaje;
		int i=0,j=0; //iteradores
		switch(accion) {
		case 0: //Ir a localizacion
			System.out.println("Cambiar de sala" + personaje.getNombre() + "\n");	
			Personaje.continuarHistoria(personaje.getNombre()+" decide cambiarse de sala.\n");
			cambiarSala(personaje, personaje.getLocalizacion(), personaje.especificarSala(personaje.getLocalizacion().getAdyacencias()));
			Personaje.continuarHistoria("Ahora se encuentra en "+personaje.getLocalizacion().getNombre()+".\n\n");
			break;
		case 1: //Pedir objeto
			System.out.println("Pedir objeto"+ personaje.getNombre() + "\n");
			for(i=0; solicitudes[i]!=null;i++);
			otroPersonaje = personaje.especificarPersonaje(personaje.getLocalizacion().getPersonajes().toArray(new Personaje[personaje.getLocalizacion().getPersonajes().size()]));
			objeto = personaje.especificarObjeto(listaObjetos);
			solicitudes[i]=new Solicitud(personaje, otroPersonaje ,objeto );
			Personaje.continuarHistoria(personaje.getNombre()+" decide pedir el objeto: "+objeto+" a "+otroPersonaje.getNombre()+".\n\n");
			break;
		case 2: //Dar objeto
			otroPersonaje=personaje.especificarPersonaje(Solicitud.elaborarListaSolicitantes(personaje, solicitudes, listaPersonajes));
			Personaje.continuarHistoria(personaje.getNombre()+" decide darle el objeto: "+personaje.getObjeto().getNombre()+" a "+otroPersonaje.getNombre()+".\n\n");
			cambiarObjeto(personaje, otroPersonaje);
			break;
		case 3: //Coger objeto
			System.out.println("Coger Objeto"+ personaje.getNombre() + "\n");
			objeto = personaje.especificarObjeto(personaje.getLocalizacion().getObjetos().toArray(new Objeto[personaje.getLocalizacion().getObjetos().size()]));
			Personaje.continuarHistoria(personaje.getNombre()+" decide coger el objeto: "+objeto+" del suelo de la sala " + personaje.getLocalizacion().getNombre()+".\n\n");
			cambiarObjeto(objeto, personaje.getLocalizacion(), personaje);
			break;
		case 4: //Dejar objeto
			System.out.println("Dejar Objeto"+ personaje.getNombre() + "\n");
			Personaje.continuarHistoria(personaje.getNombre()+" decide dejar su objeto en el suelo de la sala " + personaje.getLocalizacion().getNombre()+".\n\n");
			cambiarObjeto(personaje, personaje.getLocalizacion());
			break;
		default:
			Personaje.continuarHistoria(personaje.getNombre()+" decide no hacer nada.\n\n");
			acabado++;
			System.out.println("Nada"+ personaje.getNombre() + "\n");
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
			ventana.cambiarBotones(jugador.getLocalizacion().getPersonajes().toArray(new Personaje[jugador.getLocalizacion().getPersonajes().size()]), this);
			break;
		case 2: //Dar objeto
			/*int j = 0;
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
			}*/
			//ventana.cambiarBotones(solicitantes, this);
			break;
		case 3: //Coger objeto
			//System.out.print(jugador.getLocalizacion().getObjetos().size());
			ventana.cambiarBotones(jugador.getLocalizacion().getObjetos().toArray(new Objeto[jugador.getLocalizacion().getObjetos().size()]), this);
			break;
		case 4: //Dejar objeto
		case 5: //No hacer nada
			if(encontrado==1) {
				System.out.print("\nVa a empezar la siguiente ronda\n");
				siguienteRonda();
			}
		break;
		}
		if(encontrado == 0) {
			for(i=0; i < listaPersonajes.length; i++) {
				if(s.matches(listaPersonajes[i].getNombre())) {
					jugador.setPElegido(listaPersonajes[i]);
					System.out.print("\nElijo a " + listaPersonajes[i].getNombre());
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
					System.out.print("\nElijo a " + jugador.getLElegida().getNombre());
					encontrado = 2;
				}
			}
		}
		if(encontrado == 0) {
			for(i=0; i < listaObjetos.length; i++) {
				//System.out.print("\n"+listaObjetos.length);
				if(s.matches(listaObjetos[i].getNombre())) {
					System.out.print("\nElijo a " + listaObjetos[i].getNombre());
					jugador.setOElegido(listaObjetos[i]);
					encontrado = 2;
				}
			}
		}
		if(encontrado == 2) {
			System.out.print("\nVa a empezar la siguiente ronda");
			siguienteRonda();
		}
		
		
	}
}





