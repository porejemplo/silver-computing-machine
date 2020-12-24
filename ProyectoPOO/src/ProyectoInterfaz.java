import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class ProyectoInterfaz implements ActionListener{

	private JLabel label, infoJugador;
	private JPanel panel;
	private JFrame frame;
	private Boolean numero = true;
	private String info = "Alvaro la txupa"; // esto tiene que ser la informacion de lo que ha pasado en el turno
	
	//Constructor de la Interfaz
	public ProyectoInterfaz() {
		frame = new JFrame();
		//Los botones para cada accion posible
		JButton act1 = new JButton("accion1");
		JButton act2 = new JButton("accion2");
		JButton act3 = new JButton("accion3");
		JButton act4 = new JButton("accion4");
		JButton act5 = new JButton("accion5");
		JButton act6 = new JButton("accion6");
		//Escuchamos todos los botones
		act1.addActionListener(this);
		act2.addActionListener(this);
		act3.addActionListener(this);
		act4.addActionListener(this);
		act5.addActionListener(this);
		act6.addActionListener(this);
		//si una accion no es posible Deshabilitamos el boton correspondiente
		if(numero == false) {
			act1.setEnabled(false);
		}if(numero == false) {
			act2.setEnabled(false);
		}if(numero == false) {
			act3.setEnabled(false);
		}if(numero == false) {
			act4.setEnabled(false);
		}if(numero == false) {
			act5.setEnabled(false);
		}if(numero == false) {
			act6.setEnabled(false);
		}
		
		label = new JLabel("Accion elegida: ");
		infoJugador = new JLabel("Info: "+ info);
		//Editamos el panel a nuestro gusto
		panel = new JPanel();
		panel.setBackground(Color.gray);
		panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
		panel.setLayout(new GridLayout(0,1));
		panel.add(infoJugador);
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
		frame.setSize(400,400);
	}
	public void actionPerformed(ActionEvent e) {
		//Escuchamos lo que hacen los botones y le decimos que haga la accion correspondiente
		String s = e.getActionCommand();
		if(s.matches("accion1")) {
			label.setText("Accion: 1");
		}if(s.matches("accion2")) {
			label.setText("Accion: 2");
		}if(s.matches("accion3")) {
			label.setText("Accion: 3");
		}if(s.matches("accion4")) {
			label.setText("Accion: 4");
		}if(s.matches("accion5")) {
			label.setText("Accion: 5");
		}if(s.matches("accion6")) {
			label.setText("Accion: 6");
		}
	}
}