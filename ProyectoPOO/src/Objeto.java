
public class Objeto implements Elemento{
    private String nombre;

    //constructor con parametros
    Objeto(String n){
        nombre = n;
    }

    public void setNombre(String txt){
        nombre = txt;
    }

    public String getNombre(){
        return nombre;
    }

}
