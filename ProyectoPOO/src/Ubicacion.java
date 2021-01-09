
public class Ubicacion {
	private String nombre;
	private String lugar;
	
	public Ubicacion(String nombre, String lugar) {
		this.nombre= nombre;
		this.lugar = lugar;
	}
	//Uso gestor de archivos
	public Ubicacion() {
		this("-","-");
	}
	//Uso gestor de archivos
	public Ubicacion(String nombre) {
		this(nombre,"-");
	}
	
	public String getNombre() {
		return nombre;
	}
	public String getLugar() {
		return lugar;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	public String toString() {
		return (nombre + "(" + lugar + ")\n");
	}


}
