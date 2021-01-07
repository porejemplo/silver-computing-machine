
public class NPC_aleatorio extends Personaje{

	
	public NPC_aleatorio(String nombre, Localizacion localizacion) {
		super (nombre, localizacion);
	}


	public int dameAccion(boolean[] acciones) {
		int num;
		while(true) {
			num = (int) Math.random()*6;
			if(acciones[num]==true) return num;
		}
	}


	public Personaje especificarPersonaje(Personaje[] disponibles) {
		return disponibles[(int)Math.random()*disponibles.length];
	}


	public Localizacion especificarSala(Localizacion[] disponibles) {
		return disponibles[(int)Math.random()*disponibles.length];
	}


	public Objeto especificarObjeto(Objeto[] disponibles) {
		return disponibles[(int)Math.random()*disponibles.length];
	}

}
