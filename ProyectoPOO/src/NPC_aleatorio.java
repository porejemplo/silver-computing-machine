
public class NPC_aleatorio extends Personaje{

	
	public NPC_aleatorio(String nombre, Localizacion localizacion) {
		super (nombre, localizacion);
	}

	@Override
	public int dameAccion(boolean[] acciones) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Personaje especificarPersonaje(Personaje[] disponibles) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Localizacion especificarSala(Localizacion[] disponibles) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Objeto especificarObjeto(Objeto[] disponibles) {
		// TODO Auto-generated method stub
		return null;
	}

}
