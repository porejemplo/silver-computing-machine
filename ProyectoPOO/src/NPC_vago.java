
public class NPC_vago extends Personaje{

	public NPC_vago(String nombre, Localizacion localizacion) {
		super(nombre, localizacion);
	}

	public int dameAccion(boolean[] acciones) {
		//Si no esta en su objetivo, se mueve hasta encontrarla
		if(super.getObjetivo().getLugar()!=super.getLocalizacion().getNombre()) { 
			return 0;
		}else if(acciones[1]==true) {//Si puede pedir un objeto lo pide
			return 1;
		}else if(acciones[3]==true) {//Si su objeto esta en el suelo de su sala lo coge
			for(int i = 0; i < super.getLocalizacion().getObjetos().size();i++) {
				if(super.getLocalizacion().getObjetos().get(i).getNombre().equals(super.getObjetivo().getNombre())) {
					return 3;
				}
			}
		}
		return 5; //Si esta en su sala y no encuentra su objeto espera a que le llegue
	}
	public Personaje especificarPersonaje(Personaje[] disponibles) {
		//Le da igual a quien pedirle el objeto
		return disponibles[(int) Math.random()*disponibles.length];
	}

	public Localizacion especificarSala(Localizacion[] disponibles) {
		//Si puede moverse a su localizacion objetivo lo hace
		for(int i = 0; i < disponibles.length; i++) {
			if(disponibles[i].getNombre().equals(super.getObjetivo().getLugar())) {
				return disponibles[i];
			}
		}
		//Si no, sigue buscandola
		return disponibles[(int)Math.random()*disponibles.length];
	}

	public Objeto especificarObjeto(Objeto[] disponibles) {
		//Busca su objeto
		for(int i = 0; i < disponibles.length; i++) {
			if(disponibles[i].getNombre().equals(super.getObjetivo().getNombre())) {
				return disponibles[i];
			}
		}
		//Si no lo encuentra coge otro
		return disponibles[(int)Math.random()*disponibles.length];
	}

}