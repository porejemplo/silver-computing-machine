
public class NPC_avaricioso extends Personaje{


	public NPC_avaricioso(String nombre, Localizacion localizacion) {
		super(nombre, localizacion);
	}

	public int dameAccion(boolean[] acciones) {
		
		if(super.getObjeto().getNombre().equals(super.getObjetivo().getNombre())&& super.getLocalizacion().getNombre().equals(super.getObjetivo().getLugar())) {
			return 5; //Si esta en su sala y tiene su objeto, no hace nada
		}else if(acciones[4]==true&& super.getLocalizacion().getPersonajes().size()==1&&super.getObjeto().getNombre()!=super.getObjetivo().getNombre()) {
			return 4; //Esconde objetos, si puede dejar un objeto, no hay nadie en la sala y ademas, no es el objeto que el mismo esta buscando, lo deja
		}else if(acciones[3]==true) { 
			return 3; //si puede coger un objeto, se lo queda
		}else { 
			return 0; //si no, se cambia de sala
		}
	}

	public Personaje especificarPersonaje(Personaje[] disponibles) { 
		return disponibles[0];
	}

	public Localizacion especificarSala(Localizacion[] disponibles) {
		//Si puede moverse a una sala en la que cree que hay un objeto se mueve a ella
		for(int i = 0; i < disponibles.length; i++) {
			for(int j = 0; j < super.getCreencias().getUbObjetos().length;j++) {
				if(disponibles[i].getNombre().equals(super.getCreencias().getUbObjetos()[j].getLugar())) {
					return disponibles[i];
				}
			}
			
		}
		return disponibles[(int)Math.random()*disponibles.length];
	}

	public Objeto especificarObjeto(Objeto[] disponibles) {
		// Le vale el primer objeto que encuentre
		return disponibles[0];
	}

}
