package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;

import models.Crucigrama;

import observers.LoginObserver;

import controllers.CrosswordController;

public class SearchCrossword extends JFrame implements ActionListener,
		LoginObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CrosswordController controlador = new CrosswordController();
	private JButton busqueda = new JButton("Búsqueda");
	private JButton agregar = new JButton("Añadir");
	private JTextField palabras = new JTextField();
	private JLabel crucigrama = new JLabel("Búscar Crucigrama: ");

	private JPanel panel1 = new JPanel();

	@SuppressWarnings({ "rawtypes" })
	private JList lista = new JList();
	private String nombreUsuario = "";
	private PrincipalWindow ventana;

	public SearchCrossword(String nombreUsuario, PrincipalWindow ventana) {
		super("Búsqueda de Crucigramas");
		this.busqueda.setText("Búsqueda");
		this.busqueda.addActionListener(this);
		this.agregar.setText("Añadir");
		this.agregar.addActionListener(this);
		this.nombreUsuario = nombreUsuario;
		this.ventana = ventana;
		createInterface();
	}

	private void createInterface() {
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		this.panel1.setLayout(new BorderLayout());
		this.panel1.add(this.crucigrama, BorderLayout.WEST);
		this.panel1.add(this.palabras, BorderLayout.CENTER);
		this.panel1.add(this.busqueda, BorderLayout.EAST);

		this.setLayout(new BorderLayout());
		this.add(this.panel1, BorderLayout.NORTH);
		this.add(this.lista, BorderLayout.CENTER);
		this.add(this.agregar, BorderLayout.SOUTH);

		buscarCrucis("");

		this.palabras.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					buscarCrucis(palabras.getText());
				}
			}
		});

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Búsqueda")) {
			buscarCrucis(this.palabras.getText());
		}
		if (e.getActionCommand().equals("Añadir")) {
			String nombrecrucigrama = (String) this.lista.getSelectedValue();
			if (nombrecrucigrama != null) {
				this.controlador.addCroswordToUser(this.nombreUsuario,
						nombrecrucigrama);
				this.ventana.tablaActualizada();
			} else {
				JOptionPane.showMessageDialog(null,
						"¡Debe seleccionar un crucigrama!");
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	private void buscarCrucis(String palabra) {
		ListModel model = lista.getModel();
		// Comprobamos que crucigramas existen con la palabra buscada.
		List<Object> listaBuscada = this.controlador.buscarCrucigrama(palabra);
		if (listaBuscada != null) {
			String[] titulos = new String[listaBuscada.size()];
			// Buscamos todo los titulos y los guardamos como string (pues son
			// titulos y sabemos que son);
			for (int i = 0; i < listaBuscada.size(); i++) {
				Crucigrama cruci = (Crucigrama) listaBuscada.get(i);
				titulos[i] = cruci.getTitulo();
			}
			// Sacamos todos los datos del modelo de la lista.
			DefaultListModel vacio = new DefaultListModel();
			for (int j = 0; j < titulos.length; j++) {
				vacio.add(j, titulos[j]);
			}
			this.lista.setModel(vacio);

		} else {
			crucigramaNoEncontrado();
		}
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
		// TODO Auto-generated method stub

	}

	@Override
	public void crucigramaNoEncontrado() {
		JOptionPane.showMessageDialog(null,
				"¡No hay crucigramas con la cadena " + this.palabras.getText()
						+ "!");
		this.palabras.setText("");
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
