
public interface PersonajeI {
	
	public Localizacion getSala();
	public Objeto getObjeto();
	public String getNombre();
	
	public int elegirAccion(boolean[] acciones);
	public Personaje especificarPersonaje();
	public Localizacion especificarSala();
	public Objeto especificarObjeto();
}
