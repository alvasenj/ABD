package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import observers.LoginObserver;

import controllers.LoginController;

public class Login extends JFrame implements ActionListener, LoginObserver {

	private static final long serialVersionUID = 1L;

	private LoginController controlador = new LoginController();
	private JLabel nombre = new JLabel("Nombre de usuario: ");
	private JLabel contra = new JLabel("Contraseña: ");
	private JTextField usuario = new JTextField();
	private JPasswordField contrase = new JPasswordField();
	private JButton aceptar = new JButton("Aceptar");
	private JButton nuevo = new JButton("Nuevo Usuario");

	private String nombreUsuario = "";

	public Login() {
		super("Bienvenido");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(300, 200);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		this.aceptar.setText("Aceptar");
		this.nuevo.setText("Nuevo Usuario");
		this.aceptar.addActionListener(this);
		this.nuevo.addActionListener(this);
		controlador.addObserver(this);
		createInterface();
	}

	private void createInterface() {
		this.setLayout(new GridLayout(3, 2));
		this.add(nombre);
		this.add(usuario);
		this.add(contra);
		this.add(contrase);
		this.add(aceptar);
		this.add(nuevo);

		this.contrase.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					nombreUsuario = usuario.getText();
					String contrasenya = new String(contrase.getPassword());
					controlador.buscarUsuario(nombreUsuario, contrasenya);
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Aceptar")) {
			this.nombreUsuario = usuario.getText();
			String contrasenya = new String(contrase.getPassword());

			this.controlador.buscarUsuario(this.nombreUsuario, contrasenya);
		}
		if (e.getActionCommand().equals("Nuevo Usuario")) {
			this.setVisible(false);
			@SuppressWarnings("unused")
			NewUser registro = new NewUser();
			// this.setVisible(true);
		}
	}

	@Override
	public void usuarioLogeado() {
		JOptionPane.showMessageDialog(null, "Usuario encontrado. Bienvenido");
		this.usuario.setText("");
		this.contrase.setText("");
		this.setVisible(false);
		@SuppressWarnings("unused")
		PrincipalWindow ventanaprinci = new PrincipalWindow(this.controlador);
	}

	@Override
	public void usarioNoEncontrado() {
		JOptionPane.showMessageDialog(null, "Usuario no encontrado");
		this.usuario.setText("");
		this.contrase.setText("");
	}

	@Override
	public void errorBaseDatos() {

	}

	@Override
	public void usuarioExistente() {
		JOptionPane.showMessageDialog(null, "Usuario ya existente");
		this.usuario.setText("");
		this.contrase.setText("");
	}

	@Override
	public void nuevoUsuario() {
	}

	@Override
	public void cambioContra() {
	}

	@Override
	public void crucigramaNoEncontrado() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cambioFecha() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cambioImagen() {
		// TODO Auto-generated method stub

	}


}
