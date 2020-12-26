import java.math.*;

public class NPC_listo extends NPCs{

	private String proposito=""; //Accion que estará buscando
	
	public NPC_listo(String nombre, Objeto objeto, Creencias creencia, Localizacion localizacion, Ubicacion objetivo) {
		super(nombre, objeto, creencia, localizacion, objetivo);
	}

	public int elegirAccion(boolean[] acciones) {
		
		//Si aun no tiene su objeto, lo primero que hará sera buscarlo
		if(!objeto.getNombre().equals(objetivo.getNombre())) {
			if(acciones[4]) { //Si puede dejar un objeto significa que tiene uno y querrá dejarlo
				return 4;
			}else {
				//Su proposito sera buscar su objeto
				for(int i = 0; i < creencias.getUbObjetos().length;i++) {
					if(objetivo.getNombre().equals(creencias.getUbObjetos()[i].getNombre())) {
						proposito = creencias.getUbObjetos()[i].getLugar();
					}
				}
				//Si no conoce la ubicacion de su objeto, se cambia de sala
				if(proposito.equals("")) {
					return 1;
				}
					
				//Cuando sepa cual es su proposito bucará si está en su misma sala 
				for(int i = 0; i < localizacion.getPersonajes().size();i++) {
					//Si su proposito coincide con el nombre de una persona en una sala le pedirá su objeto
					if(proposito.equals(localizacion.getPersonajes().get(i).getNombre()) && acciones[2]) {
						return 2;
					}
				}
			}
			//Si su proposito no es un personaje de la sala se cambiara de sala
			return 1;
		}else {
			//Define su proposito como su lugar objetivo
			proposito = objetivo.getLugar();
			//Si no se encuentra ahora mismo en su lugar proposito, decide cambiarse de sala
			if(!proposito.equals(localizacion.getNombre())) {
				return 1;
			}
		}
		//Si ha cumplido sus objetivos no hace nada
		return 5;
	}

	@Override
	public Personaje especificarPersonaje(Personaje disponibles[]) {
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
