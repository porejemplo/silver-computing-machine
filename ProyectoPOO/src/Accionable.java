
public interface Accionable {
		//Devolver que accion va a hacer en formato numerico
		public abstract int dameAccion(boolean[] acciones);

		//Cuando le pregunten a que personaje quiere dar o recibir objeto que lo especifique
		public abstract Personaje especificarPersonaje(Personaje disponibles[]);

		//Cuando le pregunten a que sala se quiere mover que lo especifique
		public abstract Localizacion especificarSala(Localizacion disponibles[]);

		//Cuando le pregunten que objeto quiere coger que lo especifique
		public abstract Objeto especificarObjeto(Objeto disponibles[]);
}
