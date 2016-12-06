package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.filechooser.FileNameExtensionFilter;




import com.toedter.calendar.JCalendar;

import observers.LoginObserver;
import controllers.LoginController;

public class UserInfo extends JFrame implements ActionListener, LoginObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nick;
	private JLabel contra;
	private JLabel fecha;
	private JLabel avatar;

	private JButton aceptar;
	private JButton cancelar;
	private JButton cambiar;

	private JPasswordField nuevaContra;
	private JButton nuevaFecha;
	private Date fechaElegida = null;
	private byte[] nuevaImagen = null;

	private LoginController controlador = new LoginController();

	public UserInfo(String nick) {
		super("Información de Usuario");
		this.setSize(350, 200);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setLayout(new GridLayout(4, 2));
		this.controlador.addObserver(this);
		this.nick = nick;

		createInterface();
	}

	private void createInterface() {
		this.contra = new JLabel("Nueva Contraseña: ");
		this.fecha = new JLabel("Nueva fecha de nacimiento: ");
		this.avatar = new JLabel("Nuevo avatar: ");

		this.nuevaContra = new JPasswordField();
		this.nuevaFecha = new JButton("Elegir fecha");
		this.cambiar = new JButton();
		this.aceptar = new JButton();
		this.cancelar = new JButton();
		this.cambiar.setText("Cambiar Avatar");
		this.aceptar.setText("Aceptar");
		this.cancelar.setText("Cancelar");
		this.nuevaFecha.setText("Elegir fecha");

		this.aceptar.addActionListener(this);
		this.cancelar.addActionListener(this);
		this.nuevaFecha.addActionListener(this);
		this.cambiar.addActionListener(this);

		this.add(contra);
		this.add(nuevaContra);
		this.add(fecha);
		this.add(nuevaFecha);
		this.add(avatar);
		this.add(cambiar);
		this.add(cancelar);
		this.add(aceptar);

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Aceptar")) {
			boolean cambio = false;
			String contrasenya = new String(nuevaContra.getPassword());
			if ((!contrasenya.equalsIgnoreCase(""))
					|| (this.fechaElegida != null)
					|| (this.nuevaImagen != null)) {
				cambio = true;
			}

			if (!contrasenya.equalsIgnoreCase("")) {
				this.controlador.modifyPassword(this.nick, contrasenya);
			}
			if (this.fechaElegida != null) {
				this.controlador.modifyDate(nick, this.fechaElegida);
			}
			if (this.nuevaImagen != null) {
				this.controlador.modifyImagen(nick, this.nuevaImagen);
			}

			if (!cambio) {
				JOptionPane.showMessageDialog(null,
						"¡Debe introducir algún cambio!");
			}
		}

		if (e.getActionCommand().equals("Elegir fecha")) {
			final JFrame ventana = new JFrame("Calendario");
			final JCalendar calendario = new JCalendar();
			JButton elegir = new JButton("Aceptar");

			ventana.add(calendario);
			ventana.add(elegir);
			ventana.setLayout(new FlowLayout());
			ventana.setSize(250, 250);
			ventana.setLocationRelativeTo(null);
			ventana.setVisible(true);

			elegir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Date fecha = calendario.getDate();
					fechaElegida = fecha;
					ventana.setVisible(false);
				}
			});
		}

		if (e.getActionCommand().equals("Cambiar Avatar")) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"JPG & PNG Images", "jpg", "png");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(getParent());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File archivoImagen = chooser.getSelectedFile();
				try {
					byte[] array = Files.readAllBytes(archivoImagen.toPath());
					if (array.length > 40960) {
						JOptionPane
								.showMessageDialog(null,
										"¡La imagen es demasiado grande, elija otra o recortela!");
					} else
						nuevaImagen = array;
				} catch (IOException e1) {
					System.out
							.println("Error en la carga de imagen de usuario");
				}
			}
		}
		if (e.getActionCommand().equals("Cancelar")) {
			this.setVisible(false);
		}

	}

	// CAMBIARLO
	public static String calcularFecha() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
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
	public void usuarioExistente() {
		// TODO Auto-generated method stub

	}

	@Override
	public void nuevoUsuario() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cambioContra() {
		JOptionPane.showMessageDialog(null, "¡Contraseña cambiada!");
		this.nuevaContra.setText("");

	}

	@Override
	public void crucigramaNoEncontrado() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cambioFecha() {
		JOptionPane
				.showMessageDialog(null,
						"¡Fecha de nacimiento cambiada! Reinicie la aplicación para ver los cambios.");
		this.nuevaContra.setText("");
	}

	@Override
	public void cambioImagen() {
		JOptionPane
				.showMessageDialog(null,
						"¡Imagen de usuaro cambiada! Reinicie la aplicación para ver los cambios.");
		this.nuevaContra.setText("");
	}

}
