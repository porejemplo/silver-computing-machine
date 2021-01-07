
public class Objeto implements Elemento{
    private String nombre;

    Objeto(String n){
        nombre = n;
    }
    public void setNombre(String txt){
        nombre = txt;
    }

    public String getNombre(){
        return nombre;
    }
    public Boolean equals(Objeto objeto){
        return this.nombre == objeto.getNombre();
    }
    
}