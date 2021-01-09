
public class Solicitud {
	private Personaje solicitante; //Personaje que pide
	private Personaje solicitado; //Personaje al que le piden
	private Objeto objetoPed; //Objeto pedido
	
	public Solicitud(Personaje solicitante, Personaje solicitado, Objeto objetoPed) {
		this.solicitante = solicitante;
		this.solicitado = solicitado;
		this.objetoPed = objetoPed;
	}
	public Personaje getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(Personaje solicitante) {
		this.solicitante = solicitante;
	}
	public Personaje getSolicitado() {
		return solicitado;
	}
	public void setSolicitado(Personaje solicitado) {
		this.solicitado = solicitado;
	}
	public Objeto getObjetoPed() {
		return objetoPed;
	}
	public void setObjetoPed(Objeto objetoPed) {
		this.objetoPed = objetoPed;
	}
	
	public static Personaje[] elaborarListaSolicitantes(Personaje solicitado, Solicitud solicitudes[], Personaje listaPersonajes[]) {
		int contador = 0;
		for(int i = 0; i < solicitudes.length || solicitudes[i]==null;i++) {
			for(int j=0; j < listaPersonajes.length; j++) {
				if(solicitudes[i].getSolicitante().equals(listaPersonajes[j])) contador++;
			}
		}
		Personaje listaSolicitantes[]= new Personaje[contador];
		for(int i = 0; i < contador; i++) {
			for(int j=0; j < listaPersonajes.length; j++) {
				if(solicitudes[i].getSolicitante().equals(listaPersonajes[j])) {
					listaSolicitantes[i]= listaPersonajes[j];
				}
			}
		}
		return listaSolicitantes;
	}
	
}
