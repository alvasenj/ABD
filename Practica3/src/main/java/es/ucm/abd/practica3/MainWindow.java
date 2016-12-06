package es.ucm.abd.practica3;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.xml.xquery.XQException;

public class MainWindow extends JFrame {
	private DAO dao = null;

	private static final long serialVersionUID = 1L;
	private JComboBox combo = new JComboBox();
	private JTabbedPane panel = new JTabbedPane();
	private JPanel informacion = new JPanel();
	private JPanel puntuaciones = new JPanel();

	public MainWindow(DAO dao) {
		super("Práctica 3");
		this.dao = dao;
		createInterface();
	}

	private void createInterface() {

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(600, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setLayout(new BorderLayout());

		

		String[] anyos = { "2014", "2015" };
		combo = new JComboBox(anyos);
		this.add(combo, BorderLayout.NORTH);

		panel.addTab("Información", null, this.informacion, "Información");
		panel.addTab("Resultados", null, this.puntuaciones, "Resultados");
		panel.setVisible(true);
		this.add(this.panel, BorderLayout.CENTER);
		this.repaint();

		try {
			ArrayList<String> datos = extraerDatos1();
			for (int i = 0; i < datos.size(); i++) {
				datos.set(i, quitarEtiquetas(datos.get(i)));
			}
			System.out.println(datos);
		} catch (XQException e) {
			e.printStackTrace();
		}
		
		// tenemos que rellenar el combo con los diferentes años de las
		// ediciones

		// tenemos que "rellenar los paneles info y puntuacion y meterselos
		// al tabbedPane respectivamente en cada pestaña.

	}

	private ArrayList<String> extraerDatos1() throws XQException {
		String datos = dao.consulta1();
		ArrayList<String> listaResultados = new ArrayList<String>();
		ArrayList<String> clasificaciones = new ArrayList<String>();

		int i = 0;
		while (i < datos.length()) {
			String linea = "";
			while (i < datos.length() && datos.charAt(i) != '\n') {
				linea = linea + datos.charAt(i);
				i++;
			}
			i++;
			if (i != 0) {
				clasificaciones.add(linea);
			}
		}

		String clasificacion = "";
		for (int j = 0; j < clasificaciones.size(); j++) {
			if (!clasificaciones.get(j).equalsIgnoreCase("<clasificacion>\r")
					&& !clasificaciones.get(j).equalsIgnoreCase("")) {
				if (!clasificaciones.get(j).equalsIgnoreCase(
						"    </clasificacion>")) {
					clasificacion = clasificacion + clasificaciones.get(j)
							+ '\n';
				} else {
					listaResultados.add(clasificacion);
					clasificacion = "";
				}
			}

		}
		return listaResultados;
	}
	
	private String quitarEtiquetas(String clasificacion) {
		String sinEtiquetas = "";
		for (int i = 0; i < clasificacion.length(); i++) {
			if (clasificacion.charAt(i) == '<') {
				i++;
				while (clasificacion.charAt(i) != '>') {
					i++;
				}
				sinEtiquetas = sinEtiquetas + '\n';
			} else {
				sinEtiquetas = sinEtiquetas + clasificacion.charAt(i);
			}
		}
		return sinEtiquetas;
	}
}
