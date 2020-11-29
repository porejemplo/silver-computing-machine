public class Creencia {
    private String nombre;
    private String creencia;

    //constructor con parametros
    Creencia(String n. String e){
        nombre = n;
        creencia = e;
    }
    //constructor sin parametros
    Creencia(){
        nombre = "";
        creencia = "";
    }

    public void setNombre(String n){
        nombre = n;
    }
    public void setCreencia(String c){
        creencia = c;
    }
    public String getCreencia(){
        return creencia;
    }
    public String getNombre(){
        return nombre;
    }
}
