import java.util.Random;
public class NumeroAleatorioDecisiones {

		public static void main(String[] args) {
	        NumeroAleatorioDecisiones prueba = new NumeroAleatorioDecisiones();

	        System.out.println(String.valueOf(prueba.Accion(new boolean[]{false,true,false,true})));
	        System.out.println(String.valueOf(prueba.Accion(new boolean[]{false,true,false,true})));
	        System.out.println(String.valueOf(prueba.Accion(new boolean[]{false,true,false,true})));
	    }

	    public int Accion(boolean[] acciones){
	        int accion = 0;
	        int numeroDeTrues = 0;
	        // Se Recorre la lista de acciones ...
	        for (int i = 0; i < acciones.length; i++) {
	            // ... si la accion es true ...
	            if (acciones[i])
	                // ... se cuenta.
	                ++numeroDeTrues;
	        }
	        // se saca el numero aleatorio dentro del numero de trues.
	        int numeroAleatorio = new Random().nextInt(numeroDeTrues);
	        // Se recorre la lista de de acciones ...
	        for (int i = 0; i < acciones.length; i++) {
	            // ... si la accion es true ...
	            if (acciones[i]){
	                //... y si es 0 ..
	                if (numeroAleatorio==0){
	                    // ... se asigna el valor a la accion ...
	                    accion = i;
	                    // ... y se para la ejecucion para no ejecutarlo de mas ...
	                    break;
	                }
	                // ... se resta el numero aleatorio generado ...
	                --numeroAleatorio;
	            }
	        }
	        // Se debuelve la accion elegida.
	        return accion;
	    }

}
