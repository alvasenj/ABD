package models;

import java.util.ArrayList;
import java.util.List;

import mappers.ConsultaAbs;
import mappers.CrosswordMapper;
import mappers.ResuelveMapper;

import observers.LoginObserver;

@SuppressWarnings("unchecked")
public class ConsultasCrucigrama extends ConsultaAbs {

	private ArrayList<LoginObserver> observador;
	private CrosswordMapper mapper = null;
	private ResuelveMapper resuelve = null;
	private Crucigrama crucigrama = null;

	public ConsultasCrucigrama() {
		super();
		this.observador = new ArrayList<LoginObserver>();
		this.resuelve = new ResuelveMapper();
		this.mapper = new CrosswordMapper();
	}

	public void addObserver(LoginObserver ventana) {
		this.observador.add(ventana);
	}

	public List<Object> findByTitulo(String titulo) {
		List<Object> lista = new ArrayList<Object>();
		boolean encontrado = false;
		try {

			lista = (List<Object>) mapper.findLike(titulo, "Titulo");
			encontrado = true;

			if (!encontrado) {
				for (LoginObserver o : observador)
					o.crucigramaNoEncontrado();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error en la consulta");
		}

		return lista;
	}

	public String getTitle(Object palabra) {
		boolean encontrado = false;
		String nombre = null;
		String busqueda = palabra.toString();
		// Si en palabra nos han pasado un ID de crucigrama
		try {
			int busquedaInt = Integer.parseInt(busqueda);
			if (busquedaInt > 0) {

				List<Object> lista = new ArrayList<Object>();
				lista = (List<Object>) mapper.findById(palabra);
				this.crucigrama = (Crucigrama) lista.get(0);
				encontrado = true;
				nombre = this.crucigrama.getTitulo();

				if (!encontrado) {
					for (LoginObserver o : observador)
						o.crucigramaNoEncontrado();
				}
			}

			// Si en palabra nos han pasado un titulo de crucigrama:
		} catch (NumberFormatException e) {
			if (busqueda != null) {
				System.out.println(palabra.toString());
				nombre = ((Crucigrama) this.mapper.findByTitulo(palabra))
						.getTitulo();

				if (!encontrado) {
					for (LoginObserver o : observador)
						o.crucigramaNoEncontrado();
				}
			}
		}

		return nombre;
	}

	public void addCroswordToUser(String nick, Object titulo) {
		boolean encontrado = false;

		Resuelve resuelve = new Resuelve(nick,
				getIDCrucigrama(titulo.toString()), false);
		this.resuelve.insert(resuelve);
		encontrado = true;

		if (!encontrado) {
			for (LoginObserver o : observador)
				o.usarioNoEncontrado();
		}

	}

	public int getIDCrucigrama(String titulo) {
		int ID = 0;
		boolean encontrado = false;

		List<Crucigrama> lista = (List<Crucigrama>) this.mapper
				.findByTitulo(titulo);
		encontrado = true;
		this.crucigrama = lista.get(0);
		ID = crucigrama.getID();
		if (!encontrado) {
			for (LoginObserver o : observador)
				o.crucigramaNoEncontrado();
		}

		return ID;
	}

	public String getDate() {
		return this.crucigrama.getDate().toString();
	}

	public void setTerminado(Resuelve resuelve) {
		Resuelve terminado = new Resuelve(resuelve.getUser(), resuelve.getID(),
				true);
		this.resuelve.update(resuelve, terminado);
		resuelve.setTerminado();

	}
}
