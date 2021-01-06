
public class NPC_prueba extends Personaje{

	public NPC_prueba(String nombre, Localizacion localizacion) {
		super(nombre, localizacion);
	}

	public int elegirAccion(boolean[] acciones) {
		return 0;
	}


	public Personaje especificarPersonaje(Personaje[] disponibles) {
		return disponibles[0];
	}


	public Localizacion especificarSala(Localizacion[] disponibles) {

		return disponibles[0];
	}


	public Objeto especificarObjeto(Objeto[] disponibles) {

		return disponibles[0];
	}

}
