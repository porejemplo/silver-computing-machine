import java.util.ArrayList;

public class Localizacion {
    private String nombre;
    private ArrayList<Personaje> personajes = new ArrayList<Personaje>();
    private Localizacion[] localizacionesAdyacentes;
    private ArrayList<Objeto> objetos = new ArrayList<Objeto>();

    // Constructor
    public Localizacion(String nombre, Localizacion[] localizacionesAdyacentes) {
        setNombre(nombre);
        setLocalizacionesAdyacentes(localizacionesAdyacentes);
    }

    // Get Set
    public Localizacion[] getLocalizacionesAdyacentes() {
        return localizacionesAdyacentes;
    }

    public void setLocalizacionesAdyacentes(Localizacion[] localizacionesAdyacentes) {
        this.localizacionesAdyacentes = localizacionesAdyacentes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Personaje> getPersonajes(){
        return personajes;
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

    /*public static void main(String[] args) {
        Localizacion l = new Localizacion("cocina");
        Personaje p1 = new Personaje("Fernando");
        l.addPersonaje(new Personaje("Jorge"));
        l.addPersonaje(p1);
        l.addPersonaje(new Personaje("Natalia"));

        System.out.println(l.toString());
        l.removePersonaje(p1);
        System.out.println(l.toString());
    }*/
    public ArrayList<Objeto> getObjetos(){
        return objetos;
    }

    public void addObjeto(Objeto objeto) {
        objetos.add(objeto);
    }

    public void removeObjeto(Objeto objeto){
        int indice;
        for (indice = 0; indice < objetos.size(); indice++) {
            if (objetos.get(indice).equals(objeto))
                break;
        }
        objetos.remove(indice);
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

    public Boolean equals(Localizacion localizacion){
        return this.nombre == localizacion.nombre;
    }
}