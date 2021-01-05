
public class GestorArchivosException extends Exception {
    private static final long serialVersionUID = 1L;
    private int nLinea = 0;
	private String archivo = "-";
	private String mensajeError = "-";
	
    public GestorArchivosException(){
        super();
    }

    public GestorArchivosException(String s){
        super(s);
    }
    
    public GestorArchivosException(int nLinea, String archivo, String mensajeError){
    	super();
    	this.nLinea = nLinea;
    	this.archivo = archivo;
    	this.mensajeError = mensajeError;
    }
    
    public String toString() {
    	return "ERROR: " + mensajeError + "\nArchivo: " + archivo + "\tLinea: " + Integer.toString(nLinea);
    }
}