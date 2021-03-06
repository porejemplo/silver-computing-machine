import java.util.ArrayList;

public class Localizacion implements Elemento{
    private String nombre;
    private ArrayList<Personaje> personajes = new ArrayList<Personaje>();
    private Localizacion[] localizacionesAdyacentes;
    private ArrayList<Objeto> objetos = new ArrayList<Objeto>();

    // Constructor
    public Localizacion(String nombre) {
        this.nombre = nombre;
    }

    // Get Set
    public Localizacion[] getAdyacencias() {
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

    public Boolean equals(Localizacion localizacion){
        return this.nombre == localizacion.nombre;
    }
}