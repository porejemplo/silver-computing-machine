
public class Jugador extends Personaje{
	   public Jugador(String nombre, Objeto objeto, Creencia creencia,Localizacion localizacion){
	        super(nombre, objeto, creencia,localizacion);
	    }

	@Override
	public int elegirAccion(boolean[] acciones) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Personaje especificarPersonaje() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Localizacion especificarSala() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Objeto especificarObjeto() {
		// TODO Auto-generated method stub
		return null;
	}
}