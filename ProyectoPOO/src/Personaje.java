public abstract class Personaje implements PersonajeI{
    private String nombre;
    private Objeto objeto;
    private Creencia creencia;
    private Localizacion localizacion;
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Personaje (String nombre, Objeto objeto, Creencia creencia, Localizacion localizacion){
        setNombre(nombre);
        setObjeto(objeto);
        setCreencia(creencia);
        setLocalizacion(localizacion);
        localizacion.addPersonaje(this);
    }

    public Boolean equals(Personaje personaje){
        return this.getNombre() == personaje.getNombre();
    }

	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}

	public Creencia getCreencia() {
		return creencia;
	}

	public void setCreencia(Creencia creencia) {
		this.creencia = creencia;
	}

	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
	}

	//Para los hijos, tiene que devolver que accion va a hacer en formato numerico
	public abstract int elegirAccion(boolean[] acciones);

	//Para los hijos, Cuando le pregunten a que personaje quiere dar o recibir objeto que lo especifique
	public abstract Personaje especificarPersonaje();

	//Para los hijos, Cuando le pregunten a que sala se quiere mover que lo especifique
	public abstract Localizacion especificarSala();

	//Para los hijos, Cuando le pregunten que objeto quiere coger que lo especifique
	public abstract Objeto especificarObjeto();
}