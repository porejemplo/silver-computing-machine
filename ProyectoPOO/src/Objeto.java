public class Objeto {
    private String nombre;
    //los objetos pueden estar cogidos por algun personaje o sueltos
    private char estado;
    //al crear el primer objeto esta variable se pondra a 0
    private static int numObjetos = 0;

    //contar el numero de objetos creados
    static void nuevoObjeto(){
        numObjetos++; //se incrementa cada vez que creamos un objeto
    }

    //constructor sin parametros
    Objeto() {
        nombre = "";
        estado = 'x';
        nuevoObjeto();
    }

    //constructor con parametros
    Objeto(String n, char e){
        nombre = n;
        estado = e;
        nuevoObjeto();
    }

    //set
    public void setNombre(String txt){
        nombre = txt;
    }

    public void setEstado(char j){
        estado = j;
    }

    //Get
    public String getNombre(){
        return nombre;
    }

    public String getEstado(){
        String s;
        if (estado == 'c') {s = "Cogido por algun personaje";}
        else if (estado == 's') {s = "Suelto por el mapa";}
        else {s = "El estado del objeto es desconodido";}
        return s;
    }

    //dar objeto
    //coger objeto
    //soltar objeto
>>>>>>> da41500364cee6328579c5a32348af5d3b545f04
}
