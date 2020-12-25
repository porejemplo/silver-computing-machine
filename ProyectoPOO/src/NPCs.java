

public class NPCs extends Personaje{
	   public NPCs(String nombre, Objeto objeto, Creencias creencia,Localizacion localizacion, Ubicacion objetivo){
	        super(nombre, objeto, creencia,localizacion,objetivo);
	    }

	@Override
	public int elegirAccion(boolean[] acciones) {
		for( int i = 0; i < 6; i++) {
			if(acciones[i] == true) return i+1;
		}
		return 0;
	}

	@Override
	public Personaje especificarPersonaje() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Localizacion especificarSala() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Objeto especificarObjeto() {
		// TODO Auto-generated method stub
		return null;
	}
}
