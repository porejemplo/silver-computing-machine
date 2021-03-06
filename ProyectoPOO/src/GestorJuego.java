import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestorJuego implements ActionListener{
	private Ventana ventana;
	private int ronda = 0;
	private short acabado = 0;
	private Jugador jugador;
	private Localizacion listaSalas[];
	private Personaje listaPersonajes[];
	private Objeto listaObjetos[];
	private String opciones[] = {"Cambiar sala", "Pedir Objeto", "Dar Objeto", "Coger Objeto", "Dejar Objeto", "No hacer nada"};
	// La creencias del gestor del juego representan el estado actual del juego.
	private Creencias estadoJuego;
	//solicitudes almacena las peticiones de objetos de unos personajes a otros, caduca tras una ronda.
	private Solicitud solicitudes[];
	//Necesario para escribir en ficheros cuando acabe el juego.
	private GestorArchivos gArchivos;

	public GestorJuego(Localizacion[] salas, Personaje[] personajes, Objeto[] objetos, GestorArchivos gestor) {
		listaSalas = salas;
		listaPersonajes = personajes;
		listaObjetos = objetos;
		solicitudes = new Solicitud[personajes.length];
		gArchivos = gestor;
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
	public Jugador getJugador() {
		return jugador;
	}
	
	private void siguienteRonda() {
		if(acabado == listaPersonajes.length /*|| ronda>10*/) { //Descomentar para hacer una partida rapida y ver el final del juego
			finalizar();
		}else {
			acabado = 0;
			ronda++;
			solicitudes= new Solicitud[listaPersonajes.length]; //Borrar todas las solicitudes de la lista que no se hayan resuelto
			Personaje.continuarHistoria("Comienza la ronda: " +ronda+".\n");
			//El jugador debe ejecutar su accion primero para evitar que cambie el estado del mundo entre su eleccion y su accion
			ejecutarAccion(jugador, jugador.dameAccion(accionesPermitidas(jugador))); 
			for(int i = 0; i < listaPersonajes.length; i++) {
				if(!listaPersonajes[i].equals(jugador)) {
					ejecutarAccion(listaPersonajes[i], listaPersonajes[i].dameAccion(accionesPermitidas(listaPersonajes[i])));					
				}
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
		
		estadoJuego = new Creencias(aux, aux2); //Establecer las creencias
		
		//Encontrar al jugador
		for(int i = 0; i < listaPersonajes.length; i++) {	
			if(listaPersonajes[i] instanceof Jugador ) {
				jugador = (Jugador) listaPersonajes[i];
			}
		}
		//Mostrar a los personajes su situacion inicial
		for(int i = 0; i < listaPersonajes.length; i++) {
			mostrarSala(listaPersonajes[i], listaPersonajes[i].getLocalizacion());
		}
		//Crear y mostrar la ventana
		ventana = new Ventana(opciones,this, jugador, accionesPermitidas(jugador));
		ventana.setVisible(true);
	}
	
	private void finalizar() {
		//Guardar el estado del juego
		gArchivos.guardarEstadoJuego(this.toString());
		gArchivos.guardarHistoria(Personaje.getHistoria());
		//Cerrar la ventana
		ventana.dispose();
	}
	
	private boolean[] accionesPermitidas(Personaje personaje) {
		boolean acciones[] = new boolean[6];
		int i; //iterador
		
		//1. Cambiar de sala.Un personaje siempre puede decidir cambiarse de sala cambiarse de sala
		acciones[0]=true;
		
		//2. Pedir objeto. Si hay mas personajes en la sala aparte del que realiza la accion y no tiene ningun objeto, entoces puede pedir un objeto
		if(personaje.getLocalizacion().getPersonajes().size()>1 && personaje.getObjeto() == null) acciones[1] = true;
		
		//3. Dar objeto. Comprobar  si alguien le ha pedido un objeto a un personaje
		 if(personaje.leHanPedido(solicitudes)) {
			for(i = 0; i< solicitudes.length; i++) {
				if(personaje.getObjeto()!=null && solicitudes[i]!= null && personaje.equals(solicitudes[i].getSolicitado())&& personaje.getObjeto().equals(solicitudes[i].getObjetoPed())) {
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
	
	private void ejecutarAccion(Personaje personaje, int accion) {
		Objeto objeto;
		Personaje otroPersonaje;
		int i=0; //iteradores
		switch(accion) {
		case 0: //Ir a localizacion	
			Personaje.continuarHistoria(personaje.getNombre()+" decide cambiarse de sala.\n");
			cambiarSala(personaje, personaje.getLocalizacion(), personaje.especificarSala(personaje.getLocalizacion().getAdyacencias()));
			Personaje.continuarHistoria("Ahora se encuentra en "+personaje.getLocalizacion().getNombre()+".\n\n");
			break;
		case 1: //Pedir objeto
			
			for(i=0; solicitudes[i]!=null;i++);
			otroPersonaje = personaje.especificarPersonaje(personaje.getLocalizacion().getPersonajes().toArray(new Personaje[personaje.getLocalizacion().getPersonajes().size()]));
			objeto = personaje.especificarObjeto(listaObjetos);
			solicitudes[i]=new Solicitud(personaje, otroPersonaje ,objeto );
			Personaje.continuarHistoria(personaje.getNombre()+" decide pedir el objeto: "+objeto.getNombre()+" a "+otroPersonaje.getNombre()+".\n\n");
			break;
		case 2: //Dar objeto
			otroPersonaje=personaje.especificarPersonaje(Solicitud.elaborarListaSolicitantes(personaje, solicitudes, listaPersonajes));
			Personaje.continuarHistoria(personaje.getNombre()+" decide darle el objeto: "+personaje.getObjeto().getNombre()+" a "+otroPersonaje.getNombre()+".\n\n");
			cambiarObjeto(personaje, otroPersonaje);
			break;
		case 3: //Coger objeto
			objeto = personaje.especificarObjeto(personaje.getLocalizacion().getObjetos().toArray(new Objeto[personaje.getLocalizacion().getObjetos().size()]));
			Personaje.continuarHistoria(personaje.getNombre()+" decide coger el objeto: "+objeto.getNombre()+" del suelo de la sala " + personaje.getLocalizacion().getNombre()+".\n\n");
			cambiarObjeto(objeto, personaje.getLocalizacion(), personaje);
			break;
		case 4: //Dejar objeto
			Personaje.continuarHistoria(personaje.getNombre()+" decide dejar su objeto en el suelo de la sala " + personaje.getLocalizacion().getNombre()+".\n\n");
			cambiarObjeto(personaje, personaje.getLocalizacion());
			break;
		default: //No hacer nada
			Personaje.continuarHistoria(personaje.getNombre()+" decide no hacer nada.\n\n");
			acabado++;
			break;
		}
		
	}
	
	private void cambiarSala(Personaje personaje, Localizacion origen, Localizacion destino) {
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
		//Actualizar las creencias del personaje sobre la sala a la que llega
		mostrarSala(personaje,destino);
		
	}
	private void cambiarObjeto(Personaje emisor, Personaje receptor) {
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
	private void cambiarObjeto(Personaje emisor, Localizacion destino) {
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
	private void cambiarObjeto(Objeto objeto, Localizacion origen, Personaje receptor) {
		receptor.setObjeto(objeto);
		estadoJuego.cambiarCreencia(objeto, receptor);
		origen.removeObjeto(objeto);
		
		//Informar a la sala del cambio
		for(int i = 0; i < origen.getPersonajes().size(); i++){
		  	origen.getPersonajes().get(i).getCreencias().cambiarCreencia(objeto, receptor);
		  	if(origen.getPersonajes().get(i) instanceof Jugador) {
	  			ventana.getHistoria().append(receptor.getNombre()+" coge  " + receptor.getObjeto().getNombre() + " de " + origen.getNombre()+"\n");
	  		}
		}
		
	}
	
	private void mostrarSala(Personaje personaje, Localizacion destino) {
		//Actualizar las ubicaciones de los objetos que se encuentren en esa sala
		for(int i = 0; i < destino.getObjetos().size();i++) {
			personaje.getCreencias().cambiarCreencia(destino.getObjetos().get(i),destino);
		}
		//Actualizar las ubicaciones de los personajes que se ecnuentren en esa sala
		for(int i = 0; i < destino.getPersonajes().size();i++) {
			personaje.getCreencias().cambiarCreencia(destino.getPersonajes().get(i),destino);
		}
	}
	
	public String toString() {
		String estado ="";
		//A�adir la lista de localizaciones junto con sus adyacencias
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
		//A�adimos tambien la lista de personajes y objetos con sus ubicaciones
		estado += estadoJuego;
		
		return estado;
	}

	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		int encontrado = 0;
		int i;
		//Comprobar si el boton pulsado es del menu de acciones
		for(i=0; i < opciones.length; i++) {
			if(s.matches(opciones[i])) {
				jugador.setAccionElegida(i);
				encontrado = 1;
				break;
			}
		}
		switch(i) { //Cambiar la ventana en funcion del boton pulsado
		case 0: //Cambiar de localizacion
			ventana.cambiarBotones(jugador.getLocalizacion().getAdyacencias(), this);
			break;
		case 1: //Pedir un objeto a alguien 
			ventana.cambiarBotones(jugador.getLocalizacion().getPersonajes().toArray(new Personaje[jugador.getLocalizacion().getPersonajes().size()]), this);
			break;
		case 2: //Dar objeto
			ventana.cambiarBotones(Solicitud.elaborarListaSolicitantes(jugador, solicitudes, listaPersonajes), this);
			break;
		case 3: //Coger objeto
			ventana.cambiarBotones(jugador.getLocalizacion().getObjetos().toArray(new Objeto[jugador.getLocalizacion().getObjetos().size()]), this);
			break;
		case 4: //Dejar objeto
		case 5: //No hacer nada
			if(encontrado==1) {
				siguienteRonda();
			}
		break;
		}
		//Comprobar si el boton pulsado es de alguna de las ventanas de especificacion
		if(encontrado == 0) {
			for(i=0; i < listaPersonajes.length; i++) {
				if(s.matches(listaPersonajes[i].getNombre())) {
					jugador.setPElegido(listaPersonajes[i]);
					if(jugador.getAccionElegida()==1) { //Si pide un objeto, ademas de especificar personaje tendra que especificar dicho objeto
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
					encontrado = 2;
				}
			}
		}
		if(encontrado == 0) {
			for(i=0; i < listaObjetos.length; i++) {
				if(s.matches(listaObjetos[i].getNombre())) {
					jugador.setOElegido(listaObjetos[i]);
					encontrado = 2;
				}
			}
		}
		if(encontrado == 2) { //Si el boton pertenecia a la ventana de especificaciones, avanzar de ronda
			siguienteRonda();
		}
		
		
	}
}





