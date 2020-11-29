public class Personaje {
    private String Nombre;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public Personaje (String nombre){
        setNombre(nombre);
    }

    public Boolean equals(Personaje personaje){
        return this.getNombre() == personaje.getNombre();
    }
}
