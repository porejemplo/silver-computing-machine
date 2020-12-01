
public class GestorJuego {
	private Localizacion listaSalas[];
	private PersonajeI listaPersonajes[];
	private Objeto listaObjetos[];
	private Creencias certezas;
	private int ronda = 0;
	
	public GestorJuego(Localizacion salas[],PersonajeI personajes[],Objeto objetos[], Creencias estadoInicial) {
		listaSalas= salas;
		listaPersonajes = personajes;
		listaObjetos = objetos;
		certezas = estadoInicial;
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
			ejecutarAccion(listaPersonajes[i], pedirAccion(listaPersonajes[i],accionesPermitidas(listaPersonajes[i])));
		}
	}
	
	public boolean[] accionesPermitidas(PersonajeI personaje) { //NOMBRE.Filtrar que acciones puede y no puede hacer un personaje
		boolean acciones[] = new boolean[6];
		return acciones;
	}
	
	public int pedirAccion(PersonajeI personaje, boolean[] acciones) {
		return personaje.elegirAccion(acciones);
	}
	
	public void ejecutarAccion(PersonajeI personaje, int accion) {//NOMBRE.Hacer las acciones de cada personaje, falta completar
		switch(accion) {
		case 1: //Ir a localizacion
			cambiarSala(personaje, personaje.getLocalizacion(), personaje.especificarSala());
			break;
		case 2: //Pedir objeto
			break;
		case 3: //Dar objeto
			break;
		case 4: //Coger objeto
			cambiarObjeto(personaje.especificarObjeto(), personaje.getLocalizacion(), personaje);
			break;
		case 5: //Dejar objeto
			cambiarObjeto(personaje.especificarObjeto(), personaje, personaje.getLocalizacion());
			break;
		default:
			break;
		}
		
	}
	
	public void cambiarSala(PersonajeI personaje, Localizacion origen, Localizacion destino) {
		personaje.setLocalizacion(destino);
		/*for(int i = 0; i < origen.getPersonajes().length; i++){
		 * 		origen.getPersonajes()[i].getCreencias().cambiarCreencia(personaje,destino);
		 * }
		 */
		certezas.cambiarCreencia(personaje, destino);
		
	}
	public void cambiarObjeto(Objeto objeto, PersonajeI emisor, PersonajeI receptor) {
		emisor.setObjeto(null);
		receptor.setObjeto(objeto);
		/*for(int i = 0; i < origen.getPersonajes().length; i++){
		 * 		origen.getPersonajes()[i].getCreencias().cambiarCreencia(objeto, receptor);
		 * }
		 */
		certezas.cambiarCreencia(objeto, receptor);
	}
	public void cambiarObjeto(Objeto objeto, PersonajeI emisor, Localizacion destino) {
		emisor.setObjeto(null);
		//destino.addObjeto(objeto);
		/*for(int i = 0; i < origen.getPersonajes().length; i++){
		 * 		origen.getPersonajes()[i].getCreencias().cambiarCreencia(objeto, destino);
		 * }
		 */
		certezas.cambiarCreencia(objeto, destino);
	}
	public void cambiarObjeto(Objeto objeto, Localizacion origen, PersonajeI receptor) {
		receptor.setObjeto(objeto);
		//origen.removeObjeto(objeto);
		/*for(int i = 0; i < origen.getPersonajes().length; i++){
		 * 		origen.getPersonajes()[i].getCreencias().cambiarCreencia(objeto, receptor);
		 * }
		 */
		certezas.cambiarCreencia(objeto, receptor);
	}
}

//NOMBRE.Decidir cuando el juego se ha acabado

