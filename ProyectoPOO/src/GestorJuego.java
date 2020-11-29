
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
		}
	}
	public boolean[] accionesPermitidas(PersonajeI personaje) {
		boolean acciones[] = new boolean[10]; //numero de acciones
		return acciones;
	};
	public int pedirAccion(PersonajeI personaje, boolean[] acciones) {
		return personaje.elegirAccion(acciones);
	};
}
