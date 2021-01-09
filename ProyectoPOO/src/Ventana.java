
import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame{
	
	private JTextArea creencias, historia;
	private JPanel panelOpciones, panelResumen, panelTitulo;
	private JScrollPane sbrText;
	private JLabel estado;
	private JButton botones[];
	
	//Constructor de la Interfaz
	public Ventana(String cosas[], GestorJuego gestor, Jugador jugador) {
		botones = new JButton[cosas.length];
		
		//Creamos los paneles
		panelOpciones = new JPanel();
		panelResumen = new JPanel();
		panelTitulo = new JPanel();
		
		//Dise�amos panelTitulo
		panelTitulo.setLayout(new FlowLayout());
		panelTitulo.add(new Label("Debes acabar en "+jugador.getObjetivo().getLugar()+" y tener "+ jugador.getObjetivo().getNombre()));
		if(jugador.getObjeto()==null) {
			estado= new JLabel("Estas en: " + jugador.getLocalizacion().getNombre()+" y tienes: -");
		}else {
			estado = new JLabel("Estas en: " + jugador.getLocalizacion().getNombre()+" y tienes: "+ jugador.getObjeto().getNombre());
		}
		panelTitulo.add(estado);
		
		//Dise�amos panelOpciones
		
		panelOpciones.setBackground(Color.gray);
		panelOpciones.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		panelOpciones.setLayout(new GridLayout(0,1));
		
		
		for(int i = 0; i < cosas.length; i++) {
			botones[i] = new JButton(cosas[i]);
			botones[i].setPreferredSize(new Dimension(200,50));
			botones[i].addActionListener(gestor);
			panelOpciones.add(botones[i]);
		}
		
		
		
		//Dise�amos panelResumen
		panelResumen.setBackground(Color.white);
		panelResumen.setLayout(new GridLayout(0,1));
		
		historia = new JTextArea("Historial de acciones de los personajes:\n"); //Esto ser� el historial de las acciones
		historia.setEditable(false);
		historia.setBackground(Color.LIGHT_GRAY);
		
		sbrText = new JScrollPane(historia, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		creencias = new JTextArea("Informacion disponible:\n" + jugador.getCreencias()); //Esto ser�n las creencias del jugador
		creencias.setPreferredSize(new Dimension(100,100));
		
		panelResumen.add(sbrText);
		panelResumen.add(creencias);
		
		//Construimos la ventana
		
		add(panelTitulo, BorderLayout.NORTH);
		add(panelOpciones, BorderLayout.EAST);
		add(panelResumen, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Decision Jugador");
		pack();
		setSize(500,500);
	}
	
	public Ventana(String cosas[], GestorJuego gestor, Jugador jugador, boolean disponibles[]) {
		this(cosas, gestor,jugador);
		cambiarBotones(cosas, gestor, disponibles);
	}
	public JTextArea getHistoria() {
		return historia;
	}
	public void cambiarBotones(Elemento cosas[], GestorJuego listener) {
		for(int i = 0; i < botones.length; i++) {
			panelOpciones.remove(botones[i]);
		}
		
		for(int i = 0; i<cosas.length; i++) {
			if(cosas[i]!=listener.getJugador()) {
				botones[i] = new JButton(cosas[i].getNombre());
				botones[i].setPreferredSize(new Dimension(200,50));
				botones[i].addActionListener(listener);
				panelOpciones.add(botones[i]);
			}
			
		}
		
		validate();
		repaint();
	}
	
	public void cambiarBotones(String cosas[], GestorJuego listener) {
		for(int i = 0; i < botones.length; i++) {
			panelOpciones.remove(botones[i]);
		}
		
		for(int i = 0; i < cosas.length; i++) {
				botones[i] = new JButton(cosas[i]);
				botones[i].setPreferredSize(new Dimension(200,50));
				botones[i].addActionListener(listener);
				panelOpciones.add(botones[i]);
			
		}
	
		validate();
		repaint();
	}
	public void cambiarBotones(String cosas[], GestorJuego gestor, boolean disponibles[]) { //Habilitar solo algunos de los botones
		cambiarBotones(cosas, gestor);
		for(int i = 0; i < disponibles.length || i < cosas.length; i++) {
			if(disponibles[i]==false) {
				botones[i].setEnabled(false);
			}
		}
	}
	public void actualizarVentana(Jugador jugador) { //A�adir la nueva informacion de cada turno
		
		panelResumen.remove(creencias);
		creencias = new JTextArea("Informacion disponible: \n" +jugador.getCreencias());
		panelResumen.add(creencias);
		
		panelTitulo.remove(estado);
		if(jugador.getObjeto()==null) {
			estado= new JLabel("Estas en: " + jugador.getLocalizacion().getNombre()+" y tienes: -");
		}else {
			estado = new JLabel("Estas en: " + jugador.getLocalizacion().getNombre()+" y tienes: "+ jugador.getObjeto().getNombre());
		}
		panelTitulo.add(estado);
		
		validate();
		repaint();
	}
}