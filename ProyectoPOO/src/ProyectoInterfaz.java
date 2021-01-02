import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.*;
import java.awt.*;

public class ProyectoInterfaz implements ActionListener{

	private JLabel label;
	private JPanel panel, panelLocalizacion, panelPedirObjeto, panelDarObjeto, panelCogerObjeto, panelDejarObjeto, panelNada;
	private JFrame frame;
	private JScrollPane sbrText;
	private JTextArea informacion;
	private static Boolean accionPosible[];
	private static Personaje arrayPersonajes[];
	private static Localizacion arrayLocalizacion[];
	private static Objeto arrayObjetoDisponible[];
	private JButton arrayBotonPersonajes[] = new JButton[arrayPersonajes.length];
	private JButton arrayBotonPersonajesPedir[] = new JButton[arrayPersonajes.length];
	private JButton arrayBotonLocalizacion[] = new JButton[arrayLocalizacion.length];
	private JButton arrayBotonObjetoDisponible[] = new JButton[arrayObjetoDisponible.length];
	private String info = "alvaro es gordo\n"; // esto tiene que ser la informacion de lo que ha pasado en el turno
	
	public void setAccionPosible(Boolean accionPosible[]) {
		ProyectoInterfaz.accionPosible = accionPosible;
	}
	
	public void setArrayPersonajes(Personaje arrayPersonajes[]) {
		ProyectoInterfaz.arrayPersonajes = arrayPersonajes;
	}
	public void setArrayLocalizaciones(Localizacion arrayLocalizacion[]) {
		ProyectoInterfaz.arrayLocalizacion = arrayLocalizacion;
	}
	//Constructor de la Interfaz
	public ProyectoInterfaz() {
		frame = new JFrame();
		informacion = new JTextArea(info);
		informacion.setEditable(false);
		informacion.setBackground(Color.LIGHT_GRAY);
		sbrText = new JScrollPane(informacion, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sbrText.setSize(600,600);
		//Los botones para cada accion posible
		JButton act1 = new JButton("Ir a localizacion");
		JButton act2 = new JButton("Pedir objeto a..");
		JButton act3 = new JButton("Dar objeto a..");
		JButton act4 = new JButton("Coger Objeto..");
		JButton act5 = new JButton("Dejar objeto");
		JButton act6 = new JButton("No hacer Nada");
		

		//Escuchamos todos los botones
		act1.addActionListener(this);
		act2.addActionListener(this);
		act3.addActionListener(this);
		act4.addActionListener(this);
		act5.addActionListener(this);
		act6.addActionListener(this);
		//si una accion no es posible Deshabilitamos el boton correspondiente
		if(accionPosible[0] == false) {
			act1.setEnabled(false);
		}if(accionPosible[1] == false) {
			act2.setEnabled(false);
		}if(accionPosible[2] == false) {
			act3.setEnabled(false);
		}if(accionPosible[3] == false) {
			act4.setEnabled(false);
		}if(accionPosible[4] == false) {
			act5.setEnabled(false);
		}if(accionPosible[5] == false) {
			act6.setEnabled(false);
		}
		
		label = new JLabel("Accion elegida: ");
		//Editamos el panel a nuestro gusto
		panel = new JPanel();
		panel.setBackground(Color.gray);
		panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		panel.setLayout(new GridLayout(0,1));
		panel.add(sbrText);
		panel.add(act1);
		panel.add(act2);
		panel.add(act3);
		panel.add(act4);
		panel.add(act5);
		panel.add(act6);
		panel.add(label);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Decision Jugador");
		frame.pack();
		frame.setVisible(true);
		frame.setSize(1000,1000);
		
		//panel para la cambiar de localizacion
		panelLocalizacion = new JPanel();
		panelLocalizacion.setBackground(Color.gray);
		panelLocalizacion.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		panelLocalizacion.setLayout(new GridLayout(0,1));
		JButton atrasLocalizacion = new JButton("Salir de Localizaciones");
		atrasLocalizacion.addActionListener(this);
		JLabel textoLocalizacion = new JLabel("¿A donde quieres ir?");
		panelLocalizacion.add(atrasLocalizacion);
		panelLocalizacion.add(textoLocalizacion);
		
		//panel para Pedir objeto a personaje
		panelPedirObjeto = new JPanel();
		panelPedirObjeto.setBackground(Color.gray);
		panelPedirObjeto.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		panelPedirObjeto.setLayout(new GridLayout(0,1));
		JButton atrasPedir = new JButton("Salir de Pedir");
		atrasPedir.addActionListener(this);
		JLabel textoPedirObjeto = new JLabel("¿A quien le quieres pedir el objeto?");
		panelPedirObjeto.add(atrasPedir);
		panelPedirObjeto.add(textoPedirObjeto);
		
		//panel para dar Objeto
		panelDarObjeto = new JPanel();
		panelDarObjeto.setBackground(Color.gray);
		panelDarObjeto.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		panelDarObjeto.setLayout(new GridLayout(0,1));
		JButton atrasDar = new JButton("Salir de Dar");
		JLabel textoDarObjeto = new JLabel("¿A quien se lo quieres dar?");
		atrasDar.addActionListener(this);
		panelDarObjeto.add(atrasDar);
		panelDarObjeto.add(textoDarObjeto);
		
		//panel para Coger objeto
		panelCogerObjeto = new JPanel();
		panelCogerObjeto.setBackground(Color.gray);
		panelCogerObjeto.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		panelCogerObjeto.setLayout(new GridLayout(0,1));
		JButton atrasCoger = new JButton("Salir de Coger");
		atrasCoger.addActionListener(this);
		JLabel textoCogerObjeto = new JLabel("¿Que objeto quieres coger?");
		panelCogerObjeto.add(atrasCoger);
		panelCogerObjeto.add(textoCogerObjeto);
		
		//panel para dejar objeto
		panelDejarObjeto = new JPanel();
		panelDejarObjeto.setBackground(Color.gray);
		panelDejarObjeto.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		panelDejarObjeto.setLayout(new GridLayout(0,1));
		
		//panel de no hacer nada
		panelNada = new JPanel();
		panelNada.setBackground(Color.gray);
		panelNada.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		panelNada.setLayout(new GridLayout(0,1));
		
	}
	public void actionPerformed(ActionEvent e) {
		//Escuchamos lo que hacen los botones y le decimos que haga la accion correspondiente
		//Esto es el panel panel
		String s = e.getActionCommand();
		
		if(s.matches("Salir de Localizaciones")) {
			volverAtrasLocalizacion();
		}if(s.matches("Salir de Pedir")) {
			volverAtrasPedir();
		}if(s.matches("Salir de Dar")) {
			volverAtrasDar();
		}if(s.matches("Salir de Coger")) {
			volverCogerObjeto();
		}
		
		//Acciones del Jugador
		if(s.matches("Ir a localizacion")) {
			irLocalizacion();
			if(s.matches("Jardin")) {
				//accion ha sido ir a Jardin
			}if(s.matches("Comedor")) {
				//Accion ha sido ir a Comedor
			}if(s.matches("Cocina")) {
				//Accion ha sido ir a Cocina
			}if(s.matches("Dormitorio")) {
				//Accion ha sido ir a Dormitorio
			}
		}if(s.matches("Pedir objeto a..")) {
			pedirObjeto();
			if(s.matches("Jorge")) {
				//Pide objeto a Jorge
			}if(s.matches("Esther")) {
				//Pide Objeto a Esther
			}if(s.matches("Maria")) {
				//Pide Objeto a Maria
			}
		}if(s.matches("Dar objeto a..")) {
			darObjeto();
			if(s.matches("Jorge")) {
				//Da objeto a Jorge
			}if(s.matches("Esther")) {
				//Da Objeto a Esther
			}if(s.matches("Maria")) {
				//Da Objeto a Maria
			}
		}if(s.matches("Coger Objeto..")) {
			cogerObjeto();
			if(s.matches("LLave")) {
				//Coge LLave
			}if(s.matches("Ordenador")) {
				//Coge Ordenador
			}if(s.matches("Cartera")) {
				//Coge Cartera
			}if(s.matches("Pan")) {
				//Coge Pan
			}
		}if(s.matches("Dejar objeto a")) {
			//tiene que dejar el objeto
		}if(s.matches("No hacer Nada")) {
			//tiene que hacer nada
		}
		
		frame.validate();
		frame.repaint();
	}
	
	public void addInfo(String s) {
		info.concat(s);
	}
	
	//funciones Para cada Accion
	public void irLocalizacion() {
		frame.remove(panel);
		for(int i = 0; i < arrayLocalizacion.length; i++) {
			arrayBotonLocalizacion[i] = new JButton(arrayLocalizacion[i].getNombre());
			arrayBotonLocalizacion[i].addActionListener(this);
			panelLocalizacion.add(arrayBotonLocalizacion[i]);
		}
		frame.setContentPane(panelLocalizacion);
	}
	public void pedirObjeto() {
		frame.remove(panel);
		for(int i = 0; i < arrayPersonajes.length; i++) {
			arrayBotonPersonajes[i] = new JButton(arrayPersonajes[i].getNombre());
			arrayBotonPersonajes[i].addActionListener(this);
			panelPedirObjeto.add(arrayBotonPersonajes[i]);
		}
		frame.setContentPane(panelPedirObjeto);
	}
	public void darObjeto() {
		frame.remove(panel);
		for(int i = 0; i < arrayPersonajes.length; i++) {
			arrayBotonPersonajesPedir[i] = new JButton(arrayPersonajes[i].getNombre());
			arrayBotonPersonajesPedir[i].addActionListener(this);
			panelDarObjeto.add(arrayBotonPersonajesPedir[i]);
		}
		frame.setContentPane(panelDarObjeto);
	}
	public void cogerObjeto() {
		frame.remove(panel);
		for(int i = 0; i < arrayObjetoDisponible.length; i++) {
			arrayBotonObjetoDisponible[i]= new JButton(arrayObjetoDisponible[i].getNombre());
			arrayBotonObjetoDisponible[i].addActionListener(this);
			panelCogerObjeto.add(arrayBotonObjetoDisponible[i]);
		}
		frame.setContentPane(panelCogerObjeto);
	}
	
	//funciones de los botones Atras
	public void volverAtrasLocalizacion() {
		for(int i = 0; i < arrayLocalizacion.length;i++) {
			panelLocalizacion.remove(arrayBotonLocalizacion[i]);
		}
		frame.remove(panelLocalizacion);
		frame.setContentPane(panel);
	}
	public void volverAtrasPedir() {
		for(int i = 0; i < arrayPersonajes.length; i++) {
			panelPedirObjeto.remove(arrayBotonPersonajes[i]);
		}
		frame.remove(panelPedirObjeto);
		frame.setContentPane(panel);
	}
	public void volverAtrasDar() {
		for(int i = 0; i< arrayPersonajes.length; i++) {
			panelPedirObjeto.remove(arrayBotonPersonajesPedir[i]);
		}
		frame.remove(panelDarObjeto);
		frame.setContentPane(panel);
	}
	public void volverCogerObjeto() {
		for(int i = 0; i < arrayObjetoDisponible.length; i++) {
			panelCogerObjeto.remove(arrayBotonObjetoDisponible[i]);
		}
		frame.remove(panelPedirObjeto);
		frame.setContentPane(panel);
	}
}