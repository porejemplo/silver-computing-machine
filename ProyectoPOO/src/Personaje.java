
public abstract class Personaje implements PersonajeI{
    private String nombre;
    private Objeto objeto;
    private Creencias creencias;
    private Localizacion localizacion;
    private Ubicacion objetivo;
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Personaje (String nombre, Objeto objeto, Creencias creencias, Localizacion localizacion, Ubicacion objetivo){
        setNombre(nombre);
        setObjeto(objeto);
        setCreencia(creencias);
        setLocalizacion(localizacion);
        setObjetivo(objetivo);
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

	public Creencias getCreencias() {
		return creencias;
	}

	public void setCreencia(Creencias creencia) {
		this.creencias = creencia;
	}

	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
	}
	public Ubicacion getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(Ubicacion objetivo) {
		this.objetivo = objetivo;
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