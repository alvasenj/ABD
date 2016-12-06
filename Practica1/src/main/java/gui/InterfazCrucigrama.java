package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import observers.ICObserver;
import controllers.CrosswordController;
import controllers.ICController;
import controllers.LoginController;
import controllers.RespondeController;
import models.Compuesto;
import models.Crucigrama;
import models.Palabra;
import models.Responde;
import models.Resuelve;
import models.Word;
import es.ucm.abd.crossword.CrosswordPanel;
import es.ucm.abd.crossword.CrosswordPanelEventListener;

import java.awt.Point;

public class InterfazCrucigrama extends JFrame implements ICObserver {

	private static final long serialVersionUID = 1L;
	private ICController controlador = new ICController();
	private RespondeController responde = new RespondeController();
	private CrosswordController controladorCrucigrama = new CrosswordController();
	private LoginController controladorUsuario = null;
	private JPanel panelPalabra = new JPanel();
	private String nick;
	private int cruciAbierto;
	final private CrosswordPanel<Word> panel;
	final List<Word> lista;
	private int respondidas = 0;
	private Compuesto[] compuesto;
	

	public InterfazCrucigrama(Crucigrama cruciAbierto, String nick, LoginController controladorUsuario) {
		// Inicializamos las variables necesarias
		this.cruciAbierto = cruciAbierto.getID();
		this.nick = nick;
		this.controladorUsuario = controladorUsuario;
		this.panelPalabra.setLayout(new BorderLayout());

		// Compuesto te devuelve un array de compuestos, de todas las palabras
		// del crucigrama que le hayamos pasado
		compuesto = this.controlador.getCompuesto(this.cruciAbierto);
		
		final Palabra[] palabras = new Palabra[compuesto.length];
		for (int i = 0; i < compuesto.length; i++) {
			palabras[i] = this.controlador.getPalabra(compuesto[i].getIDp());
		}

		// Creamos la lista inicial con tres palabras
		lista = new LinkedList<Word>();
		for (int i = 0; i < compuesto.length; i++) {
			final Word word = new Word(compuesto[i].getX(),
					compuesto[i].getY(), palabras[i].getSecuencia(),
					compuesto[i].getOrientacion());
			lista.add(word);
		}

		JScrollPane jScrollPane = new JScrollPane();
		this.add(jScrollPane, BorderLayout.CENTER);
		panel = new CrosswordPanel<Word>(jScrollPane, lista);
		jScrollPane.setViewportView(panel);

		// Trae todas las palabras de ese usuario para ese crucgrama
		Responde[] responde = this.responde.findByUser(this.nick,
				this.cruciAbierto);
		int contador = 0;
		if (responde != null) {
			for (int i = 0; i < responde.length; i++) {
				if (responde[i] != null) {
					contador++;
				}
			}

			if (contador == 0)
				contador = 1;
			Responde[] array = new Responde[contador];
			contador = 0;
			for (int i = 0; i < responde.length; i++) {
				if (responde[i] != null) {
					array[contador] = responde[i];
					contador++;
				}
			}

			cargarRespuestas(array);
		}

		// Registramos los manejadores de eventos del CrosswordPanel
		panel.addEventListener(new CrosswordPanelEventListener<Word>() {
			public void wordSelected(CrosswordPanel<Word> source, Word newWord) {
				Palabra palabra = null;
				Word word = null;
				if (newWord != null) {
					String nombre = newWord.getWord();
					for (int i = 0; i < palabras.length; i++) {
						if (nombre.equalsIgnoreCase(palabras[i].getSecuencia())) {
							palabra = palabras[i];
							word = lista.get(i);
						}
					}
					if (palabra != null) {
						quitar();
						panelPalabra.add(mostrarInfo(palabra, word));
						panelPalabra.revalidate();
						dibujar();
					}
				} else {
					quitar();
				}
			}

			public void cellSelected(CrosswordPanel<Word> source, Point newCell) {
				if (newCell != null) {
					quitar();
				} else {
					System.out.println("Deseleccionada celda");
				}
			}

			public void deselected(CrosswordPanel<Word> source) {
				System.out.println("Deselecci√≥n!");
			}
		});

		// A√±adimos un bot√≥n para mostrar las palabras del crucigrama
		JButton botonMostrar = new JButton("Mostrar palabras");
		botonMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < lista.size(); i++) {
					panel.showWord(lista.get(i));
				}
			}
		});

		this.setSize(600, 650);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.controlador.addObserver(this);
	}

	protected JPanel mostrarInfo(final Palabra palabra, final Word word) {
		JPanel panelInfo = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		byte[] imagen = null;
		JTextArea texto = new JTextArea();
		JLabel longitud = new JLabel();
		final JButton aceptar = new JButton("Aceptar");
		final JButton amigo = new JButton("Enviar a amigo");

		final JTextField escribe = new JTextField();

		panelInfo.setLayout(new BorderLayout());
		panel1.setLayout(new GridLayout(1, 2));
		panel2.setLayout(new GridLayout(1, 4));

		imagen = palabra.getImagen();
		if (imagen != null) {
			if (imagen.length < 65536) {
				JButton boton = new JButton();
				ImageIcon fotoPalabra = new ImageIcon(imagen);
				Image prueba = fotoPalabra.getImage();
				boton.setIcon(new ImageIcon(prueba.getScaledInstance(120, 120,
						java.awt.Image.SCALE_SMOOTH)));
				boton.setBounds(0, 0, 130, 134);
				boton.setBorderPainted(false);
				boton.setContentAreaFilled(false);
				boton.setVerticalTextPosition(SwingConstants.CENTER);
				boton.setHorizontalTextPosition(SwingConstants.CENTER);
				panel1.add(boton);
			}
		}
		String cadena = palabra.getEnunciado();
		texto.append("Pistas: " + '\n' + crearTexto(cadena));
		panel1.add(texto);

		longitud.setText(palabra.getSecuencia().length() + " letras: ");
		panel2.add(longitud);
		if (word.isMostrada()) {
			escribe.setEnabled(false);
			escribe.setText(word.getWord());
			aceptar.setEnabled(false);
			amigo.setEnabled(false);
		}
		panel2.add(escribe);

		aceptar.addActionListener(new ActionListener() {
			// Si pulsa el boton aceptar, comprobamos la palabra escrita
			public void actionPerformed(ActionEvent arg0) {
				String fecha = calcularFecha();
				if (escribe.getText().equalsIgnoreCase(palabra.getSecuencia())) {
					// AÒadimos la palabra al crucigrama, le damos la puntuacion
					// al usuario...
					Responde respuesta = new Responde(nick, cruciAbierto,
							palabra.getID(), fecha, escribe.getText(), true);

					responde.guardarRespuesta(respuesta);
					word.mostrada();
					escribe.setEnabled(false);
					aceptar.setEnabled(false);
					amigo.setEnabled(false);
					panel.showWord(word);
					respondidas++;

					for (int i = 0; i < compuesto.length; i++) {
						if (compuesto[i].getIDp() == palabra.getID()) {
							controladorUsuario.cambiarPuntuacion(compuesto[i]
									.getPuntuacion());
						}
					}
					comprobarTerminado();
				} else {
					Responde respuesta = new Responde(nick, cruciAbierto,
							palabra.getID(), fecha, escribe.getText(), false);
					responde.guardarRespuesta(respuesta);

					controladorUsuario.cambiarPuntuacion(-10);
					palabraIncorrecta();
				}
			}
		});

		panel2.add(aceptar);
		panel2.add(amigo);

		panelInfo.add(panel1, BorderLayout.NORTH);
		panelInfo.add(panel2, BorderLayout.SOUTH);

		return panelInfo;
	}

	protected void dibujar() {
		this.add(panelPalabra, BorderLayout.SOUTH);
		this.revalidate();
	}

	protected void quitar() {
		this.panelPalabra.removeAll();
		this.remove(this.panelPalabra);
		this.revalidate();
	}

	@Override
	public void crucigramaNoEncontrado() {
		JOptionPane.showMessageDialog(null, "Crucigrama no existente");
	}

	public void consultaRepetida() {
		JOptionPane.showMessageDialog(null, "°Ya has probado esa palabra!");
	}

	protected void palabraIncorrecta() {
		JOptionPane.showMessageDialog(null, "°Palabra incorrecta!");
	}

	private void cargarRespuestas(Responde[] findByUser) {
		// Comprobamos que el array de respuestas para ese usuario ese
		// crucigrama no sea nulo
		if ((findByUser != null) && (findByUser[0] != null)) {
			for (int i = 0; i < findByUser.length; i++) {
				// para cada respuesta de ese array comprobamos si es correcta o
				// no
				if (((Responde) findByUser[i]).isCorrecto()) {
					for (int j = 0; j < this.lista.size(); j++) {
						if (((Responde) findByUser[i]).getRespuesta()
								.equalsIgnoreCase(this.lista.get(j).getWord())) {
							this.lista.get(j).mostrada();
							this.panel.showWord(this.lista.get(j));
							this.respondidas++;
							comprobarTerminado();
						}
					}
				}
			}
		}
	}

	public static String calcularFecha() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	protected void comprobarTerminado() {
		if (this.respondidas == this.lista.size()) {
			JOptionPane.showMessageDialog(null,
					"°Has terminado este crucigrama, enhorabuena!");
			this.controladorCrucigrama.setTerminado(new Resuelve(this.nick,
					this.cruciAbierto, false));
		}

	}

	private String crearTexto(String cadena) {
		char[] prueba = new char[cadena.length()];
		for (int i = 0; i < cadena.length(); i++) {
			if (cadena.substring(i, i + 1).equalsIgnoreCase(",")) {
				prueba[i] = '\n';
			} else
				prueba[i] = cadena.charAt(i);
		}

		cadena = "";
		for (int i = 0; i < prueba.length; i++) {
			cadena = cadena + prueba[i];
		}
		return cadena;
	}

}
