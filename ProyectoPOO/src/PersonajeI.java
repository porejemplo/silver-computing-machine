
public interface PersonajeI {
	
	public Localizacion getLocalizacion();
	public Objeto getObjeto();
	public String getNombre();
	
	public void setLocalizacion(Localizacion sala);
	public void setObjeto(Objeto objeto);
	public void setNombre(String nombre);
	
	public int elegirAccion(boolean[] acciones);
	public Personaje especificarPersonaje();
	public Localizacion especificarSala();
	public Objeto especificarObjeto();
}
