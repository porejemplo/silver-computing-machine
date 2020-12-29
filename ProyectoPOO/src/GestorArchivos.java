//package Gestores;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class GestorArchivos {
	private File anexo1;
    private File anexo2;
    private Scanner s;
    
    private Localizacion[] listaSalas;
    private Personaje[] listaPersonajes;
	private Objeto[] listaObjetos;
	
	// Getter y seters
	public Localizacion[] getListaSalas() {
		return listaSalas;
	}

	public void setListaSalas(Localizacion[] listaSalas) {
		this.listaSalas = listaSalas;
	}

	public Personaje[] getListaPersonajes() {
		return listaPersonajes;
	}

	public void setListaPersonajes(Personaje[] listaPersonajes) {
		this.listaPersonajes = listaPersonajes;
	}

	public Objeto[] getListaObjetos() {
		return listaObjetos;
	}

	public void setListaObjetos(Objeto[] listaObjetos) {
		this.listaObjetos = listaObjetos;
	}

    // Constructor.
    public GestorArchivos(String anexo1, String anexo2){
        this.anexo1 = new File(anexo1);
        this.anexo2 = new File(anexo2);
    }
    
    public GestorArchivos(String anexo1){
        this.anexo1 = new File(anexo1);
        crearListasAnexoI();
        darValorListasAnexoI();
    }
    
    
    public static void main(String[] args) {
        GestorArchivos ga = new GestorArchivos ("Anexo1.txt");
    }
    
    private void crearListasAnexoI() {
    	try {
    		int tipo = -1;
        	int cont = 0;
        	s = new Scanner (anexo1);
            while (s.hasNextLine()) {
                String linea = s.nextLine();
                if(linea.charAt(0) == '<' || !s.hasNextLine()) {
                	if (s.hasNextLine())
                		cont--;
                	
                	if (tipo == 0)
                		setListaSalas(new Localizacion[cont]);
                	else if (tipo == 1)
                		setListaPersonajes(new Personaje[cont]);
                	else if (tipo == 2)
                		setListaObjetos(new Objeto[cont]);
                	
                	cont = 0;
                	
                	if (linea.equals("<Localizaciones>"))
                		tipo = 0;
                	else if (linea.equals("<Personaje>"))
                		tipo = 1;
                	else if (linea.equals("<Objetos>"))
                		tipo = 2;
                }
                cont++;
            }
    	} catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    
    private void darValorListasAnexoI() {
    	try{
    		int i = 0;
            s = new Scanner (anexo1);
            while (s.hasNextLine()) {
                String linea = s.nextLine();
                if(linea.charAt(0) == '<') {
                	System.out.println("Tipo de Objeto: " + linea);
                	if (linea.equals("<Localizaciones>"))
                		i = 0;
                	else if (linea.equals("<Personaje>"))
                		i = 1;
                	else
                		i = 2;
                }
                else{
                	Scanner sl = new Scanner (linea);
                    sl.useDelimiter("\\s*(\\(|,|\\))\\s*");
                	if(i == 0) {
                		guardarLocalizacion(sl);
                	}
                	else if(i == 1) {
                		guardarPersonaje(sl);
                	}
                	else {
                		System.out.println("\tNombre: " + sl.next());
                        while (sl.hasNext()){
                            System.out.println("\t\tContenido: " + sl.next());
                        }
                	}
                    sl.close();
                }
            }
            s.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    
    
    
    private void guardarLocalizacion(Scanner scanner) {
    	for (int i = 0; i <= listaSalas.length; i++) {
    		if (listaSalas[i] == null) {
    			listaSalas[i] = new Localizacion(scanner.next());
    			//Contar el numero de localizacione sdayacentes que tiene.
    			int cont = 0;
    			while (scanner.hasNext()){
    				cont++;
                    System.out.println("\t\tContenido: " + scanner.next());
                }
    			listaSalas[i].setLocalizacionesAdyacentes(new Localizacion[cont]);
    			break;
    		}
    	}
    }
    
    private void guardarPersonaje(Scanner scanner) {
    	for (int i = 0; i <= listaPersonajes.length; i++) {
    		if (listaPersonajes[i] == null) {
    			String nombre = scanner.next();
    			Localizacion localizacion = scanner.next();
    			listaPersonajes[i] = new Personaje(nombre, localizacion);
    			break;
    		}
    	}
    }
    
    private Localizacion buscarSala(String nombre) {
    	int i = 0;
    	for(i = 0; i <= listaSalas.length; i++) {
    		if (listaSalas[i].getNombre().equals(nombre))
    			break;
    	}
    	
    	return listaSalas[i];
    }
}
