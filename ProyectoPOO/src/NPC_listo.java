

public class NPC_listo extends Personaje{
//Tratara de alcanzar sus objetivos de forma optimizada
	public NPC_listo(String nombre, Localizacion localizacion) {
		super(nombre, localizacion);
		if(super.getObjetivo()!=null)System.out.println("Me llamo " +nombre+", y mi personalidad es listo\n");
		Personaje.continuarHistoria("Me llamo "+nombre+", y mi personalidad es listo\n");
	}

	private String proposito=""; //Accion que estar� buscando realizar
	

	public int dameAccion(boolean[] acciones) {
		//Si aun no tiene su objeto, lo primero que har� sera buscarlo
		if(super.getObjeto()!=null && !super.getObjeto().getNombre().equals(super.getObjetivo().getNombre())) {
			if(acciones[2]) {
				return 2; //Si tiene un objeto que no quiere y alguien se lo pide, se lo da
			}else if(acciones[4]) { //Si puede dejar un objeto significa que tiene uno y querr� dejarlo
				return 4;
			}else {
				//Su proposito sera buscar su objeto
				for(int i = 0; i < super.getCreencias().getUbObjetos().length;i++) {
					if(super.getObjetivo().getNombre().equals(super.getCreencias().getUbObjetos()[i].getNombre()) && !super.getObjetivo().getNombre().equals(super.getNombre())) {
						proposito = super.getCreencias().getUbObjetos()[i].getLugar();
					}
				}
				//Si no conoce la ubicacion de su objeto, se cambia de sala
				if(proposito.equals("")) {
					return 0;
				}
					
				//Cuando sepa cual es su proposito bucar� si est� en su misma sala 
				for(int i = 0; i < super.getLocalizacion().getPersonajes().size();i++) {
					//Si su proposito coincide con el nombre de una persona en una sala le pedir� su objeto
					if(proposito.equals(super.getLocalizacion().getPersonajes().get(i).getNombre()) && acciones[2]) {
						return 1;
					}
				}
			}
			//Si su proposito no es un personaje de la sala se cambiara de sala
			return 0;
		}else {
			//Define su proposito como su lugar objetivo
			proposito = super.getObjetivo().getLugar();
			//Si no se encuentra ahora mismo en su lugar proposito, decide cambiarse de sala
			if(!proposito.equals(super.getLocalizacion().getNombre())) {
				return 0;
			}
		}
		//Si ha cumplido sus objetivos no hace nada
		return 5;
	}

	
	public Personaje especificarPersonaje(Personaje disponibles[]) {
		//Obtener el personaje que coincide con su proposito
		for(int i = 0; i < disponibles.length; i++) {
			if(proposito.equals(disponibles[i].getNombre())){
				return disponibles[i];
			}
		}
		return disponibles[(int)(Math.random()*disponibles.length)];
	}

	public Localizacion especificarSala(Localizacion[] disponibles) {
		//Obtener el personaje que coincide con su proposito
		for(int i = 0; i < disponibles.length; i++) {
			if(proposito.equals(disponibles[i].getNombre())){
				return disponibles[i];
			}
		}
		return disponibles[(int)(Math.random()*disponibles.length)];
	}

	public Objeto especificarObjeto(Objeto[] disponibles) {
		//Obtener el objeto que esta buscando
		for(int i = 0; i < disponibles.length; i++) {
			if(super.getObjetivo().getNombre().equals(disponibles[i].getNombre())){
				return disponibles[i];
			}
		}
		return disponibles[(int)(Math.random()*disponibles.length)];
	}

}
