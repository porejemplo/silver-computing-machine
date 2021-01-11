import java.util.Random;

public class NPC_vago extends Personaje{
//Llegará a su sala destino y esperará hasta que el objeto que esta buscando le llegue a el
	public NPC_vago(String nombre, Localizacion localizacion) {
		super(nombre, localizacion);
		if(super.getObjetivo()!=null)System.out.println("Me llamo " +nombre+", y mi personalidad es vago\n");
		Personaje.continuarHistoria("Me llamo "+nombre+", y mi personalidad es vago\n");
	}

	public int dameAccion(boolean[] acciones) {
		//Si no esta en su objetivo, se mueve hasta encontrarla
		if(!super.getObjetivo().getLugar().equals(super.getLocalizacion().getNombre())) { 
			return 0;
		}else if(acciones[2]) {//Si alguien le ha pedido el objeto que lleva se lo da
			return 2; 
		}else if(acciones[4]&&super.getObjeto()!=null&& !super.getObjeto().getNombre().equals( super.getObjetivo().getNombre())) { 
			return 4;//Si puede dejar un objeto que lleve y que no quiera, lo deja
		}else if(acciones[1]) {//Si puede pedir un objeto lo pide
			return 1;
		}else if(acciones[3]) {//Si su objeto esta en el suelo de su sala lo coge
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
		Random random = new Random();
		return disponibles[random.nextInt(disponibles.length)];
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
