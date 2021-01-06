

public class Jugador extends Personaje{
	private int accionElegida;
	private Personaje PElegido;
	private Localizacion LElegida;
	private Objeto OElegido;
	private int indice;
	
	
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
	
	public int getIndice() {
		return indice;
	}
	public int getAccionElegida() {
		return accionElegida;
	}
	public Localizacion getLElegida() {
		return LElegida;
	}

	public int elegirAccion(boolean[] acciones) {
		return accionElegida;
	}


	public Personaje especificarPersonaje(Personaje[] disponibles) {
		return PElegido;
	}

	@Override
	public Localizacion especificarSala(Localizacion[] disponibles) {
		return LElegida;
	}

	@Override
	public Objeto especificarObjeto(Objeto[] disponibles) {
		return OElegido;
	}

}