
public abstract class Personaje{
    protected String nombre;
    protected Objeto objeto;
    protected Creencias creencias;
    protected Localizacion localizacion;
    protected Ubicacion objetivo;
    
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
    
    public Personaje (String nombre, Localizacion localizacion) {
    	setNombre(nombre);
        setLocalizacion(localizacion);
        localizacion.addPersonaje(this);
    }

    public Boolean equals(Personaje personaje){
        return this.nombre == personaje.getNombre();
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
	
	public String toString() {
		String s = "Nombre: " + getNombre() + "\tLocalizacion: " + getLocalizacion().getNombre() + "\tObjeto: ";
		if (getObjeto() != null && (getObjeto().getNombre() != null || getObjeto().getNombre().length() > 0))
			s += getObjeto().getNombre();
		return s;
	}
	

	//Para los hijos, tiene que devolver que accion va a hacer en formato numerico
	public abstract int elegirAccion(boolean[] acciones);

	//Para los hijos, Cuando le pregunten a que personaje quiere dar o recibir objeto que lo especifique
	public abstract Personaje especificarPersonaje(Personaje disponibles[]);

	//Para los hijos, Cuando le pregunten a que sala se quiere mover que lo especifique
	public abstract Localizacion especificarSala(Localizacion disponibles[]);

	//Para los hijos, Cuando le pregunten que objeto quiere coger que lo especifique
	public abstract Objeto especificarObjeto(Objeto disponibles[]);
}