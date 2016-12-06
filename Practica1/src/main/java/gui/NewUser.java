package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controllers.LoginController;
import observers.UserObserver;

public class NewUser extends JFrame implements ActionListener, UserObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoginController controlador = new LoginController();
	private JLabel nombre = new JLabel("Nombre de nuevo usuario: ");
	private JLabel contra = new JLabel("Contraseña: ");
	private JTextField usuario = new JTextField();
	private JPasswordField contrase = new JPasswordField();
	private JButton cancelar = new JButton("Cancelar");
	private JButton nuevo = new JButton("Nuevo Usuario");

	private String nombreUsuario = "";

	public NewUser() {
		super("Registrarse");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		this.cancelar.setText("Cancelar");
		this.nuevo.setText("Nuevo Usuario");
		this.nuevo.addActionListener(this);
		this.cancelar.addActionListener(this);
		controlador.addObserver(this);
		createInterface();

	}

	private void createInterface() {
		this.setLayout(new GridLayout(3, 2));
		this.add(nombre);
		this.add(usuario);
		this.add(contra);
		this.add(contrase);
		this.add(cancelar);
		this.add(nuevo);
	}

	@Override
	public void usuarioLogeado() {
		// TODO Auto-generated method stub

	}

	@Override
	public void usarioNoEncontrado() {
		// TODO Auto-generated method stub

	}

	@Override
	public void errorBaseDatos() {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cancelar")) {
			this.setVisible(false);
			@SuppressWarnings("unused")
			Login login = new Login();
		}
		if (e.getActionCommand().equals("Nuevo Usuario")) {
			this.nombreUsuario = this.usuario.getText();
			String contrasenya = new String(contrase.getPassword());
			if (!this.nombreUsuario.equalsIgnoreCase("")
					&& (!contrasenya.equalsIgnoreCase(""))) {
				this.controlador.newUser(this.nombreUsuario, contrasenya);
			} else {
				JOptionPane.showMessageDialog(null,
						"Debe introducir un nick y una contraseña");
				this.usuario.setText("");
				this.contrase.setText("");
			}
		}
	}

	@Override
	public void usuarioExistente() {
		JOptionPane.showMessageDialog(null, "Usuario ya existente");
		this.usuario.setText("");
		this.contrase.setText("");
	}

	@Override
	public void nuevoUsuario() {
		JOptionPane.showMessageDialog(null,
				"Usuario nuevo registrado. Bienvenido");
		this.setVisible(false);
		String contrasenya = new String(contrase.getPassword());
		this.controlador.buscarUsuario(this.nombreUsuario, contrasenya);
		@SuppressWarnings("unused")
		PrincipalWindow ventana = new PrincipalWindow(this.controlador);

		this.usuario.setText("");
		this.contrase.setText("");
	}

	@Override
	public void cambioContra() {
		// TODO Auto-generated method stub

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
