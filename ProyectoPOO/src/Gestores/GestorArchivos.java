package Gestores;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GestorArchivos {
	private File anexo1;
    private File anexo2;
    private Scanner s;

    // Constructor.
    public GestorArchivos(String anexo1, String anexo2){
        this.anexo1 = new File(anexo1);
        this.anexo2 = new File(anexo2);
    }
    
    public GestorArchivos(String anexo1){
        this.anexo1 = new File(anexo1);
    }
    
    
    public static void main(String[] args) {
        GestorArchivos ga = new GestorArchivos ("Anexo1.txt");
        ga.LeerAnexoI();
    }
    
    public void LeerAnexoI() {
    	try{
            Scanner s = new Scanner (anexo1);
            while (s.hasNextLine()) {
                String linea = s.nextLine();
                if(linea.charAt(0) == '<')
                    System.out.println("Tipo de Objeto: " + linea);
                else{
                    Scanner sl = new Scanner (linea);
                    sl.useDelimiter("\\s*(\\(|,|\\))\\s*");
                    System.out.println("\tNombre: " + sl.next());
                    while (sl.hasNext()){
                        System.out.println("\t\tContenido: " + sl.next());
                    }
                    sl.close();
                }
            }
            s.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
