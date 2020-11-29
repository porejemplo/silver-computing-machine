
public class GestorJuego {
	private Localizacion listaSalas[];
	private PersonajeI listaPersonajes[];
	private Objeto listaObjetos[];
	private int ronda = 0;
	private int siguienteAccion = 0;
	
	public GestorJuego(Localizacion salas[],PersonajeI personajes[],Objeto objetos[]) {
		listaSalas= salas;
		listaPersonajes = personajes;
		listaObjetos = objetos;
	}
	

	public Localizacion[] getListaSalas() {
		return listaSalas;
	}
	public PersonajeI[] getListaPersonajes() {
		return listaPersonajes;
	}
	public Objeto[] getListaObjetos() {
		return listaObjetos;
	}
	
	public void siguienteRonda() {
		ronda++;
		System.out.print("\nComienza la ronda "+ronda);
		for(int i = 0; i < listaPersonajes.length; i++) {
			siguienteAccion = pedirAccion(listaPersonajes[i],accionesPermitidas(listaPersonajes[i]));
			ejecutarAccion(listaPersonajes[i], siguienteAccion);
		}
	}
	
	public boolean[] accionesPermitidas(PersonajeI personaje) {
		boolean acciones[] = new boolean[6];
		return acciones;
	}
	
	public int pedirAccion(PersonajeI personaje, boolean[] acciones) {
		return personaje.elegirAccion(acciones);
	}
	
	public void ejecutarAccion(PersonajeI personaje, int accion) {
		switch(accion) {
		case 1: //Ir a localizacion
			cambiarSala(personaje, personaje.getSala(), personaje.especificarSala());
			break;
		case 2: //Pedir objeto
			break;
		case 3: //Dar objeto
			break;
		case 4: //Coger objeto
			cambiarObjeto(personaje.especificarObjeto(), personaje.getSala(), personaje);
			break;
		case 5: //Dejar objeto
			cambiarObjeto(personaje.especificarObjeto(), personaje, personaje.getSala());
			break;
		default:
			break;
		}
		
	}
	
	public void cambiarSala(PersonajeI personaje, Localizacion origen, Localizacion destino) {}
	public void cambiarObjeto(Objeto objeto, PersonajeI emisor, PersonajeI receptor) {}
	public void cambiarObjeto(Objeto objeto, PersonajeI emisor, Localizacion destino) {}
	public void cambiarObjeto(Objeto objeto, Localizacion origen, PersonajeI receptor) {}
}
