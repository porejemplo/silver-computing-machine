public abstract class Personaje{
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
}