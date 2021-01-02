
public class Ubicacion {
	private String nombre;
	//private String tipo; //Esto por si m�s adelante a�adimos los tipos de personajes
	private String lugar;
	//Uso gestor de archivos
	public Ubicacion() {
		this.lugar = "-";
		this.nombre = "-";
	}
	//Uso gestor de archivos
	public Ubicacion(String lugar) {
		this.lugar = lugar;
		this.nombre = "-";
	}
	
	public String getNombre() {
		return nombre;
	}
	public String getLugar() {
		return lugar;
	}
	/*public String getTipo(){
	 	return tipo;*/
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
