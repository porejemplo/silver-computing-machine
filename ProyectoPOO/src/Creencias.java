
public class Creencias {
	private Ubicacion ubicacionesPersonajes[];
	private Ubicacion ubicacionesObjetos[];
	
	public Creencias(Ubicacion personajes[], Ubicacion objetos[]) {
		ubicacionesPersonajes = personajes;
		ubicacionesObjetos = objetos;
	}
	
	public Ubicacion[] getUbPersonajes() {
		return ubicacionesPersonajes;
	}
	public Ubicacion[] getUbObjetos() {
		return ubicacionesObjetos;
	}
	public void cambiarCreencia(PersonajeI personaje, Localizacion sala) {
		for(int i = 0; i < ubicacionesPersonajes.length; i++) {
			if(personaje.getNombre() == ubicacionesPersonajes[i].getNombre()) {
				ubicacionesPersonajes[i].setLugar(sala.getNombre());
			}
		}
	}
	public void cambiarCreencia(Objeto objeto, Localizacion sala) {
		for(int i = 0; i < ubicacionesPersonajes.length; i++) {
			if(objeto.getNombre() == ubicacionesObjetos[i].getNombre()) {
				ubicacionesObjetos[i].setLugar(sala.getNombre());
			}
		}
	}
	public void cambiarCreencia(Objeto objeto, PersonajeI personaje) {
		for(int i = 0; i < ubicacionesPersonajes.length; i++) {
			if(objeto.getNombre() == ubicacionesObjetos[i].getNombre()) {
				ubicacionesObjetos[i].setLugar(personaje.getNombre());
			}
		}
	}
	
}
