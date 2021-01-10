import java.io.FileNotFoundException;
import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		GestorJuego gJuego;
		GestorArchivos ga = new GestorArchivos("AnexoI.txt", "AnexoII.txt");
		
		try {
			ga.comprobarFormato();
			gJuego = new GestorJuego(new Localizacion[ga.tamanoLista(0)],new Personaje[ga.tamanoLista(1)],new Objeto[ga.tamanoLista(2)], ga);
			ga.leerAnexos(gJuego.getListaSalas(), gJuego.getListaPersonajes(), gJuego.getListaObjetos());
			gJuego.empezarJuego();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			gJuego = new GestorJuego();
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (GestorArchivosException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			gJuego = new GestorJuego();
		}
		
	}

}
