
public class Creencias {
	private Ubicacion ubicacionesPersonajes[];
	private Ubicacion ubicacionesObjetos[];
	
	public Creencias(Ubicacion personajes[], Ubicacion objetos[]) {
		ubicacionesPersonajes = personajes;
		ubicacionesObjetos = objetos;
	}
	//Uso gestor de archivos
	public Creencias(int nPersonajes, int nObjetos) {
		ubicacionesPersonajes = new Ubicacion[nPersonajes];
		ubicacionesObjetos = new Ubicacion[nObjetos];
	}
	
	public Ubicacion[] getUbPersonajes() {
		return ubicacionesPersonajes;
	}
	public Ubicacion[] getUbObjetos() {
		return ubicacionesObjetos;
	}
	//Un personaje se cambia de sala
	public void cambiarCreencia(Personaje personaje, Localizacion sala) {
		for(int i = 0; i < ubicacionesPersonajes.length; i++) {
			if(personaje.getNombre() == ubicacionesPersonajes[i].getNombre()) {
				ubicacionesPersonajes[i].setLugar(sala.getNombre());
			}
		}
	}
	//Un objeto pasa a estar en una sala
	public void cambiarCreencia(Objeto objeto, Localizacion sala) {
		for(int i = 0; i < ubicacionesPersonajes.length; i++) {
			if(objeto.getNombre() == ubicacionesObjetos[i].getNombre()) {
				ubicacionesObjetos[i].setLugar(sala.getNombre());
			}
		}
	}
	//Un objeto pasa a estar en un personaje
	public void cambiarCreencia(Objeto objeto, Personaje personaje) {
		for(int i = 0; i < ubicacionesPersonajes.length; i++) {
			if(objeto.getNombre() == ubicacionesObjetos[i].getNombre()) {
				ubicacionesObjetos[i].setLugar(personaje.getNombre());
			}
		}
	}
	public String toString() {
		String s = "";
		s+=("<Personajes>\n");
		for (int i = 0; i < ubicacionesPersonajes.length; i++) {
			s += ubicacionesPersonajes[i];
		}
		s+=("<Objetos>\n");
		for (int i = 0; i < ubicacionesObjetos.length; i++) {
			s +=  ubicacionesObjetos[i];
		}
		return s;
	}
	
}
