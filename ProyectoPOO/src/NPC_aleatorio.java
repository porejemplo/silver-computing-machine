
public class NPC_aleatorio extends Personaje{
//Realiza todas sus acciones de forma aleatoria, salvo si ha cumplido sus objetivos
	
	public NPC_aleatorio(String nombre, Localizacion localizacion) {
		super (nombre, localizacion);
	}


	public int dameAccion(boolean[] acciones) {
		int num;
		if(super.getObjeto()!=null && super.getObjeto().getNombre().equals(super.getObjetivo().getNombre())&& super.getLocalizacion().getNombre().equals(super.getObjetivo().getLugar())) {
			return 5; //Si esta en su sala y tiene su objeto, no hace nada
		}
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
