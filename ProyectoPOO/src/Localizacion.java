import java.util.ArrayList;

public class Localizacion {
    private String nombre;
    private ArrayList<Personaje> personajes = new ArrayList<Personaje>();

    // Constructor
    public Localizacion(String nombre) {
        setNombre(nombre);
    }

    // Get Set
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void addPersonaje(Personaje personaje) {
        personajes.add(personaje);
    }

    public void removePersonaje(Personaje personaje){
        int indice;
        for (indice = 0; indice < personajes.size(); indice++) {
            if (personajes.get(indice).equals(personaje))
                break;
        }
        personajes.remove(indice);
    }

    public static void main(String[] args) {
        Localizacion l = new Localizacion("cocina");
        Personaje p1 = new Personaje("Fernando");
        l.addPersonaje(new Personaje("Jorge"));
        l.addPersonaje(p1);
        l.addPersonaje(new Personaje("Natalia"));

        System.out.println(l.toString());
        l.removePersonaje(p1);
        System.out.println(l.toString());
    }

    // Funciones
    public String toString(){
        String s = "Localizacion: " + getNombre();
        for (int i = 0; i < personajes.size(); i++) {
            s += "\n\t" + String.valueOf(i) + "-" + personajes.get(i).getNombre();
        }
        return s;
    }

    public void InformarSala(){
        for (int i = 0; i < personajes.size(); i++) {
            System.out.println("Se ha informado a " + personajes.get(i).getNombre());
        }
    }
}