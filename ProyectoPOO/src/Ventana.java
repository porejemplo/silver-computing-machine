
import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private JTextArea creencias, historia;
	private JPanel panelLateral, panelOpciones, panelResumen, panelTitulo, panelSala, items, puertas;
	private JScrollPane sbrText;
	private JLabel estado,titulo;
	private JLabel personajes[], objetos[], adyacencias[];
	private JButton botones[];
	
	//Constructor de la Interfaz
	public Ventana(String cosas[], GestorJuego gestor, Jugador jugador) {
		botones = new JButton[cosas.length];
		
		//Creamos los paneles
		panelOpciones = new JPanel();
		panelSala = new JPanel();
		panelResumen = new JPanel();
		panelTitulo = new JPanel();
		panelLateral = new JPanel();
		items = new JPanel();
		puertas = new JPanel();
		
		//Diseñamos panelTitulo
		panelTitulo.setLayout(new GridLayout(0,1));
		titulo = new JLabel("Debes acabar en "+jugador.getObjetivo().getLugar()+" y tener "+ jugador.getObjetivo().getNombre());
		titulo.setFont(new Font("Sans-Serif", Font.BOLD, 25));
		panelTitulo.add(titulo);
		if(jugador.getObjeto()==null) {
			estado= new JLabel("Estas en " + jugador.getLocalizacion().getNombre()+" y no tienes nada");
		}else {
			estado = new JLabel("Estas en " + jugador.getLocalizacion().getNombre()+" y tienes "+ jugador.getObjeto().getNombre());
			
		}
		estado.setFont(new Font("Sans-Serif", Font.BOLD, 25));
		panelTitulo.add(estado);
		
		
		//Diseñamos panelOpciones
		
		panelOpciones.setBackground(Color.gray);
		panelOpciones.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		panelOpciones.setLayout(new GridLayout(0,1));
		
		
		for(int i = 0; i < cosas.length; i++) {
			botones[i] = new JButton(cosas[i]);
			botones[i].setPreferredSize(new Dimension(200,50));
			botones[i].addActionListener(gestor);
			panelOpciones.add(botones[i]);
		}
		
		//Diseñamos panelSala
		panelSala.setBackground(Color.WHITE);
		panelSala.setBorder(BorderFactory.createTitledBorder("Informacion de la Sala"));
		panelSala.setLayout(new BorderLayout());
		personajes = new JLabel[jugador.getLocalizacion().getPersonajes().size()];
		objetos = new JLabel[jugador.getLocalizacion().getObjetos().size()];
		adyacencias = new JLabel[jugador.getLocalizacion().getAdyacencias().length];
		if(jugador.getLocalizacion().getPersonajes().size() >= jugador.getLocalizacion().getObjetos().size()) {
			items.setLayout(new GridLayout(2,jugador.getLocalizacion().getPersonajes().size()));
			
		}else {
			items.setLayout(new GridLayout(2,jugador.getLocalizacion().getObjetos().size()));
		}
		
		items.setBackground(Color.WHITE);
		puertas.setLayout(new GridLayout(0,1));
		puertas.setBackground(Color.WHITE);
		
		for(int i = 0; i <jugador.getLocalizacion().getPersonajes().size(); i++) {
			personajes[i] = new JLabel(jugador.getLocalizacion().getPersonajes().get(i).getNombre(),new ImageIcon("personaje.jpg"), JLabel.CENTER);
			personajes[i].setVerticalTextPosition(JLabel.BOTTOM);
			personajes[i].setHorizontalTextPosition(JLabel.CENTER);
			items.add(personajes[i]);
		}
		for(int i = 0; i <jugador.getLocalizacion().getObjetos().size(); i++) {
			objetos[i] = new JLabel(jugador.getLocalizacion().getObjetos().get(i).getNombre(),new ImageIcon("objeto.png"), JLabel.CENTER);
			objetos[i].setVerticalTextPosition(JLabel.BOTTOM);
			objetos[i].setHorizontalTextPosition(JLabel.CENTER);
			items.add(objetos[i]);
		}
		for(int i = 0; i <jugador.getLocalizacion().getAdyacencias().length; i++) {
			adyacencias[i] = new JLabel(jugador.getLocalizacion().getAdyacencias()[i].getNombre(),new ImageIcon("puerta.png"), JLabel.LEFT);
			puertas.add(adyacencias[i]);
		}
		
		panelSala.add(items, BorderLayout.CENTER);
		panelSala.add(puertas, BorderLayout.EAST);
		
		//Diseñamos panelLateral
		panelLateral.setLayout(new GridLayout(0,1));
		panelLateral.setPreferredSize(new Dimension(300,700));
		panelLateral.add(panelSala);
		panelLateral.add(panelOpciones);
		
		
		//Diseñamos panelResumen
		panelResumen.setBackground(Color.white);
		panelResumen.setLayout(new GridLayout(0,1));
		
		historia = new JTextArea("Historial de acciones de los personajes:\n"); //Esto será el historial de las acciones
		historia.setEditable(false);
		historia.setBackground(Color.LIGHT_GRAY);
		
		sbrText = new JScrollPane(historia, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		creencias = new JTextArea("Últimas ubicaciones conocidas:\n" + jugador.getCreencias()); //Esto serán las creencias del jugador
		creencias.setPreferredSize(new Dimension(100,100));
		
		panelResumen.add(sbrText);
		panelResumen.add(creencias);
		
		//Construimos la ventana
		
		add(panelTitulo, BorderLayout.NORTH);
		add(panelLateral, BorderLayout.EAST);
		add(panelResumen, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Decision Jugador");
		pack();
		setSize(700,700);
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
	public void actualizarVentana(Jugador jugador) { //Añadir la nueva informacion de cada turno
		
		panelResumen.remove(creencias);
		creencias = new JTextArea("Últimas ubicaciones conocidas: \n" +jugador.getCreencias());
		panelResumen.add(creencias);
		
		panelTitulo.remove(estado);
		if(jugador.getObjeto()==null) {
			estado= new JLabel("Estas en " + jugador.getLocalizacion().getNombre()+" y no tienes nada");
		}else {
			estado = new JLabel("Estas en " + jugador.getLocalizacion().getNombre()+" y tienes "+ jugador.getObjeto().getNombre());
		}
		estado.setFont(new Font("Sans-Serif", Font.BOLD, 25));
		panelTitulo.add(estado);
		
		panelSala.remove(items);
		if(jugador.getLocalizacion().getPersonajes().size() >= jugador.getLocalizacion().getObjetos().size()) {
			items.setLayout(new GridLayout(2,jugador.getLocalizacion().getPersonajes().size()));
		}else {
			items.setLayout(new GridLayout(2,jugador.getLocalizacion().getObjetos().size()));
		}
		for(int i = 0; i <personajes.length; i++) {
			items.remove(personajes[i]);
		}
		personajes = new JLabel[jugador.getLocalizacion().getPersonajes().size()];
		
		for(int i = 0; i <jugador.getLocalizacion().getPersonajes().size(); i++) {
			personajes[i] = new JLabel(jugador.getLocalizacion().getPersonajes().get(i).getNombre(),new ImageIcon("personaje.jpg"), JLabel.CENTER);
			personajes[i].setVerticalTextPosition(JLabel.BOTTOM);
			personajes[i].setHorizontalTextPosition(JLabel.CENTER);
			items.add(personajes[i]);
		}
		for(int i = 0; i <objetos.length; i++) {
			items.remove(objetos[i]);
		}
		objetos = new JLabel[jugador.getLocalizacion().getObjetos().size()];
		for(int i = 0; i <jugador.getLocalizacion().getObjetos().size(); i++) {
			objetos[i] = new JLabel(jugador.getLocalizacion().getObjetos().get(i).getNombre(),new ImageIcon("objeto.png"), SwingConstants.CENTER);
			objetos[i].setVerticalTextPosition(JLabel.BOTTOM);
			objetos[i].setHorizontalTextPosition(JLabel.CENTER);
			items.add(objetos[i]);
		}
		
		panelSala.add(items,BorderLayout.CENTER);
		
		panelSala.remove(puertas);
		
		for(int i = 0; i <adyacencias.length; i++) {
			puertas.remove(adyacencias[i]);
		}
		adyacencias = new JLabel[jugador.getLocalizacion().getAdyacencias().length];
		for(int i = 0; i <jugador.getLocalizacion().getAdyacencias().length; i++) {
			adyacencias[i] = new JLabel(jugador.getLocalizacion().getAdyacencias()[i].getNombre(),new ImageIcon("puerta.png"), JLabel.LEFT);
			puertas.add(adyacencias[i]);
		}
		
		panelSala.add(puertas,BorderLayout.EAST);
		
		
		validate();
		repaint();
	}
}