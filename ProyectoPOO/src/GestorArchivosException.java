//Personaje sin objetivos

public class GestorArchivosException extends Exception {
    private static final long serialVersionUID = 1L;
    private int nLinea = -1;
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
    
    public GestorArchivosException(String archivo, String mensajeError){
    	super();
    	this.archivo = archivo;
    	this.mensajeError = mensajeError;
    }
    
    public String getMessage() {
    	if (nLinea==-1)
    		return "ERROR: " + mensajeError + "\nArchivo: " + archivo;
    	else
    		return "ERROR: " + mensajeError + "\nArchivo: " + archivo + "\tLinea: " + Integer.toString(nLinea);
    }
}