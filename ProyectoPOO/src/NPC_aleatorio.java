
public class NPC_aleatorio extends NPCs{

	public NPC_aleatorio(String nombre, Objeto objeto, Creencias creencia, Localizacion localizacion, Ubicacion objetivo) {
		super(nombre, objeto, creencia, localizacion, objetivo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int elegirAccion(boolean[] acciones) {
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
