public class Personaje {
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Personaje (String nombre){
        setNombre(nombre);
    }

    public Boolean equals(Personaje personaje){
        return this.getNombre() == personaje.getNombre();
    }
}
