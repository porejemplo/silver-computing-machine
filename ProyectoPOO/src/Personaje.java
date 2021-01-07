
public abstract class Personaje implements Elemento, Accionable{
    private String nombre;
    private Objeto objeto;
    private Creencias creencias;
    private Localizacion localizacion;
    private Ubicacion objetivo;
    private static String historia ="";
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
	public static void continuarHistoria(String accion) {
		historia+=accion;
	}
	public boolean haPedido(Solicitud solicitudes[]) {
		for(int i = 0; i < solicitudes.length || solicitudes[i]!=null; i++) {
			if(solicitudes[i].getSolicitante().equals(this)) {
				return true;
			}
		}
		return false;
	} 
	public boolean leHanPedido(Solicitud solicitudes[]) {;
		for(int i = 0; i < solicitudes.length; ++i) {
			if(solicitudes[i] != null && solicitudes[i].getSolicitado().equals(this)) {
				return true;
			}
		}
		return false;
	} 

}