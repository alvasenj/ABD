package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import mappers.CompuestoMapper;
import mappers.RespondeMapper;
import mappers.ResuelveMapper;
import models.Compuesto;
import models.Crucigrama;
import models.Responde;
import models.Resuelve;
import observers.PrincipalObserver;
import controllers.CrosswordController;
import controllers.LoginController;

public class PrincipalWindow extends JFrame implements ActionListener,
		PrincipalObserver {

	private static final long serialVersionUID = 1L;

	private LoginController controlador = new LoginController();
	private CrosswordController controladorCruci = new CrosswordController();

	private JButton abrirC = new JButton("Abrir Crucigrama");
	private JButton buscarC = new JButton("Búsqueda de crucigramas");

	private JLabel nombre = new JLabel();
	private JLabel edad = new JLabel("Edad: ");
	private JLabel puntuacion = new JLabel("Puntuación: ");
	private JButton imagen = new JButton("");
	private JPanel panelUser = new JPanel();
	private JPanel panelInfo = new JPanel();
	private JPanel panelCruc = new JPanel();
	private JPanel panelGuia;
	private JScrollPane panelDeTabla;

	JTabbedPane panelConFichas = new JTabbedPane();
	private JTable crucigramas;
	private String nombreUsuario = "";

	public PrincipalWindow(LoginController controlador) {
		super("Home");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(400, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		this.controlador = controlador;
		this.nombreUsuario = controlador.getNombre();

		this.abrirC.setText("Abrir Crucigrama");
		this.buscarC.setText("Búsqueda de crucigramas");
		this.abrirC.addActionListener(this);
		this.buscarC.addActionListener(this);

		createInterface();
	}

	public void createInterface() {
		this.panelInfo.setLayout(new GridLayout(3, 1));
		this.panelUser.setLayout(new GridLayout(1, 1));

		this.nombre.setHorizontalAlignment(JLabel.CENTER);
		this.edad.setHorizontalAlignment(JLabel.CENTER);
		this.puntuacion.setHorizontalAlignment(JLabel.CENTER);

		this.nombre.setText(this.nombreUsuario);
		String viejo = calcularFecha();
		this.edad.setText(viejo);

		this.panelInfo.add(this.nombre);
		this.panelInfo.add(this.edad);
		this.panelInfo.add(this.puntuacion);

		boolean grande = false;

		// Cargamos la imagen del usuario!
		java.net.URL url = null;
		url = PrincipalWindow.class.getResource("/img/usuario.png");
		if (url != null) {
			byte[] imagen = this.controlador.getImagen();

			if (imagen == null) {
				this.imagen.setIcon(new ImageIcon(PrincipalWindow.class
						.getResource("/img/usuario.png")));
			} else if (imagen.length > 40960) {
				this.imagen.setIcon(new ImageIcon(PrincipalWindow.class
						.getResource("/img/usuario.png")));
				grande = true;

			} else {
				ImageIcon fotoUsuario = new ImageIcon(imagen);
				Image prueba = fotoUsuario.getImage();
				prueba.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
				this.imagen.setIcon(new ImageIcon(prueba.getScaledInstance(120,
						120, java.awt.Image.SCALE_SMOOTH)));
			}
			this.imagen.setBounds(0, 0, 130, 134);
			this.imagen.setForeground(new Color(238, 238, 238));
		}

		this.imagen.setBorderPainted(false);
		this.imagen.setText(" ");
		this.imagen.addActionListener(this);
		this.imagen.setContentAreaFilled(false);
		imagen.setVerticalTextPosition(SwingConstants.CENTER);
		imagen.setHorizontalTextPosition(SwingConstants.CENTER);

		this.panelUser.add(imagen);
		this.panelUser.add(this.panelInfo);
		this.add(panelUser, BorderLayout.NORTH);

		panelGuia = new JPanel();
		this.buscarC.setText("Buscar");
		panelGuia.setLayout(new GridLayout(1, 2));
		panelGuia.add(abrirC);
		panelGuia.add(buscarC);

		actualizarTabla();

		panelDeTabla = new JScrollPane(this.crucigramas);

		this.panelCruc.setLayout(new BorderLayout());
		this.panelCruc.add(panelGuia, BorderLayout.SOUTH);
		this.panelCruc.add(panelDeTabla, BorderLayout.CENTER);

		panelConFichas.addTab("Crucigramas", null, this.panelCruc,
				"Crucigramas");
		panelConFichas.addTab("Amigos", null, new JPanel(), "Amigos");
		panelConFichas.addTab("Peticiones de ayuda", null, new JPanel(),
				"Peticiones de ayuda");
		panelConFichas.setVisible(true);
		this.add(this.panelConFichas, BorderLayout.CENTER);
		this.repaint();
		this.controlador.addObserver(this);
		cargarPuntuacion();
		if (grande) {
			imagenGrande();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Buscar")) {
			@SuppressWarnings("unused")
			SearchCrossword busqueda = new SearchCrossword(this.nombreUsuario,
					this);
		}
		if (e.getActionCommand().equals(" ")) {
			@SuppressWarnings("unused")
			UserInfo informacion = new UserInfo(this.nombreUsuario);
		}
		if (e.getActionCommand().equals("Abrir Crucigrama")) {
			try {
				int FilaElegida = this.crucigramas.getSelectedRow();
				if (FilaElegida >= 0) {
					String titulo = (this.crucigramas.getModel()).getValueAt(
							FilaElegida, 0).toString();
					List<Object> listaBuscada = this.controladorCruci
							.buscarCrucigrama(titulo);
					if (listaBuscada.get(0) != null) {
						Crucigrama cruciAbierto = (Crucigrama) listaBuscada
								.get(0);
						@SuppressWarnings("unused")
						InterfazCrucigrama cruci = new InterfazCrucigrama(
								cruciAbierto, this.controlador.getNombre(),
								this.controlador);
					} else
						noElegido();
				} else {
					noElegido();
				}
				// SABEMOS QUE ESTA MAL COGER ESTA EXCEPCION, PERO SOLO SE COGE
				// SI EL USUARIO NO HA ELEGIDO CRUCIGRAMAS
			} catch (NullPointerException f) {
				noElegido();
			}
		}

	}

	private void actualizarTabla() {
		List<Object> listaBuscada = this.controlador
				.getCrosswordOf(this.nombreUsuario);
		if (listaBuscada != null) {
			List<Resuelve> lista = new ArrayList<Resuelve>();
			String[][] nombres = new String[listaBuscada.size()][2];

			int j = 0;
			while (j < listaBuscada.size()) {
				lista.add((Resuelve) listaBuscada.get(j));
				j++;
			}

			if (listaBuscada.size() > 0) {
				for (int i = 0; i < listaBuscada.size(); i++) {
					nombres[i][0] = this.controladorCruci.getTitle(lista.get(i)
							.getID());
					nombres[i][1] = this.controladorCruci.getDate(lista.get(i)
							.getID());
				}
			}
			String[] titulos = { "Título", "Fecha" };
			this.crucigramas = new JTable(nombres, titulos);
		}

		this.panelDeTabla = new JScrollPane(this.crucigramas);

		this.panelCruc.removeAll();
		this.panelCruc.add(panelGuia, BorderLayout.SOUTH);
		this.panelCruc.add(panelDeTabla, BorderLayout.CENTER);
		this.repaint();
	}

	@Override
	public void tablaActualizada() {
		this.actualizarTabla();
	}

	private String calcularFecha() {
		String viejo = "Edad desconocida";
		Date fecha = this.controlador.getDate(this.nombreUsuario);
		if (fecha != null) {
			long i = fecha.getTime();
			Date date = new Date();
			long j = date.getTime();
			long calculo = j - i;
			long anios = calculo / (31536000L * 1000L);
			viejo = Long.toString(anios) + " años";
		}
		return viejo;
	}

	private void imagenGrande() {
		JOptionPane
				.showMessageDialog(null,
						"¡Imagen de usuario demasiado grande! Se cargará la imagen básica.");
	}

	private void noElegido() {
		JOptionPane.showMessageDialog(null,
				"¡No ha elegido ningún crucigrama para abrir!");
	}

	@Override
	public void cambiarPuntuacion() {
		// TODO Auto-generated method stub
		this.puntuacion.setText("Puntuacion: "
				+ this.controlador.getPuntuacion());
		this.revalidate();
	}

	@SuppressWarnings("unchecked")
	private void cargarPuntuacion() {
		RespondeMapper responde = new RespondeMapper();
		List<Object> lista = (List<Object>) responde.findById(this.nombre
				.getText());

		CompuestoMapper compuesto = new CompuestoMapper();
		ArrayList<Compuesto> array = new ArrayList<>();

		if (this.crucigramas != null && lista != null) {
			int crucigramasActivos = this.crucigramas.getModel().getRowCount();
			String[] titulos = new String[crucigramasActivos];
			for (int i = 0; i < crucigramasActivos; i++) {
				titulos[i] = (this.crucigramas.getModel()).getValueAt(i, 0)
						.toString();
			}

			for (int i = 0; i < crucigramasActivos; i++) {
				List<Object> listaAux = (List<Object>) this.controladorCruci
						.buscarCrucigrama(titulos[i]);
				int id = ((Crucigrama) listaAux.get(0)).getID();
				List<Object> lista2 = (List<Object>) compuesto.findById(id);
				for (int j = 0; j < lista2.size(); j++) {
					if (lista2.get(j) != null) {
						array.add((Compuesto) lista2.get(j));
					}
				}
			}

			for (int i = 0; i < lista.size(); i++) {
				Responde respuesta = (Responde) lista.get(i);
				if (respuesta != null) {
					if (respuesta.isCorrecto()) {
						for (int j = 0; j < array.size(); j++) {
							if (respuesta.getIdP() == array.get(j).getIDp()) {
								this.controlador.cambiarPuntuacion(array.get(j)
										.getPuntuacion());
							}
						}
					} else
						this.controlador.cambiarPuntuacion(-10);
				}
			}

		} else {
			this.puntuacion.setText("Puntuacion: 0");
		}
	}

}
