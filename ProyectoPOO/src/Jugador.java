

public class Jugador extends Personaje{
	
	//Atributos en los que guardar las decisiones del jugador
	private int accionElegida;
	private Personaje PElegido;
	private Localizacion LElegida;
	private Objeto OElegido;	
	
	public Jugador(String nombre, Localizacion localizacion) {
		super(nombre, localizacion);
	}

	public void setAccionElegida(int accionElegida) {
		this.accionElegida = accionElegida;
	}

	public void setPElegido(Personaje pElegido) {
		PElegido = pElegido;
	}

	public void setLElegida(Localizacion lElegida) {
		LElegida = lElegida;
	}

	public void setOElegido(Objeto oElegido) {
		OElegido = oElegido;
	}
	public int getAccionElegida() {
		return accionElegida;
	}
	public Localizacion getLElegida() {
		return LElegida;
	}

	public int dameAccion(boolean[] acciones) { //Devuelve el valor especificado por el jugador a traves de la interfaz grafica
		return accionElegida;
	}


	public Personaje especificarPersonaje(Personaje[] disponibles) { //Devuelve el personaje especificado a traves de la interfaz grafica
		return PElegido;
	}

	public Localizacion especificarSala(Localizacion[] disponibles) {//Devuelve la localizacion especificada a traves de la interfaz grafica
		return LElegida;
	}

	public Objeto especificarObjeto(Objeto[] disponibles) {//Devuelve el objeto especificado a traves de la interfaz grafica
		return OElegido;
	}

}