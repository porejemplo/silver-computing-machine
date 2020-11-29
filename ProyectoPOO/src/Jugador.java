
public class Jugador extends Personaje{
	private String nombreJugador;
	   public Jugador(String nombre, Objeto objeto, Creencia creencia,Localizacion localizacion){
	        super(nombre, objeto, creencia,localizacion);
	    }
	public String getNombreJugador() {
		return nombreJugador;
	}
	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;
	}
}