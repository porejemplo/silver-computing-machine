import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) {
		// Anetes de ejecutar el juego
		GestorJuego gJuego;
		GestorArchivos ga = new GestorArchivos("AnexoI.txt", "AnexoII.txt");
		
		try {
			ga.comprobarFormato();
			gJuego = new GestorJuego(new Localizacion[ga.tamanoLista(0)],new Personaje[ga.tamanoLista(1)],new Objeto[ga.tamanoLista(2)]);
			ga.leerAnexos(gJuego.getListaSalas(), gJuego.getListaPersonajes(), gJuego.getListaObjetos());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			gJuego = new GestorJuego();
		} catch (GestorArchivosException e) {
			e.printStackTrace();
			gJuego = new GestorJuego();
		}
		System.out.print(gJuego);
		System.out.print(gJuego.getListaPersonajes()[0].getCreencias());
		/*
		 * 
		 * Bucle de juego.
		 * 
		 */
		
		//Guardado de datos
		//ga.guardarEstadoJuego(gJuego.getListaSalas(), gJuego.getListaPersonajes(), gJuego.getListaObjetos());
		
	}

}
