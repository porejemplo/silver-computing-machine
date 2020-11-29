
public class Ubicacion {
	private String nombre;
	//private String tipo; //Esto por si más adelante añadimos los tipos de personajes
	private String nombreSala;
	
	public Ubicacion(String personaje, String sala /*, String tipo*/) {
		nombre = personaje;
		/*this.tipo = tipo*/
		nombreSala = sala;
	}
	
	public String getNombre() {
		return nombre;
	}
	public String getSala() {
		return nombreSala;
	}
	/*public String getTipo(){
	 	return tipo;*/
	public void setNombre(String personaje) {
		nombre = personaje;
	}
	public void setSala(String sala) {
		nombreSala = sala;
	}
	
	
	public String toString() {
		return (nombre + "(" + nombreSala + ")");
	}
}
