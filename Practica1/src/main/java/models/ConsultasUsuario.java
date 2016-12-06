package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mappers.ConsultaAbs;
import mappers.ResuelveMapper;
import mappers.UserMapper;
import observers.LoginObserver;
import observers.PrincipalObserver;

public class ConsultasUsuario extends ConsultaAbs {

	private ArrayList<LoginObserver> observador;
	private UserMapper mapper = null;
	private ResuelveMapper resuelve = null;
	private Usuario usuario = null;
	private ArrayList<PrincipalObserver> observadorPrincipal;

	public ConsultasUsuario() {
		super();
		this.observador = new ArrayList<LoginObserver>();
		this.observadorPrincipal = new ArrayList<PrincipalObserver>();
		this.mapper = new UserMapper();
		this.resuelve = new ResuelveMapper();
	}

	public void addObserver(LoginObserver ventana) {
		this.observador.add(ventana);
	}

	public void addObserver(PrincipalObserver ventana){
		this.observadorPrincipal.add(ventana);
	}
	public boolean comprobarUsuario(String nick, String contra) {
		boolean encontrado = false;
		Usuario consulta = (Usuario) mapper.findById(nick);
		this.usuario = consulta;
		if (this.usuario != null) {
			if (contra.equalsIgnoreCase(this.usuario.getPassword())) {
				for (LoginObserver o : observador)
					o.usuarioLogeado();
				encontrado = true;
			}

			if (!encontrado) {
				for (LoginObserver o : observador)
					o.usarioNoEncontrado();
			}
		} else {
			for (LoginObserver o : observador)
				o.usarioNoEncontrado();
		}

		return encontrado;
	}

	public void modifyPassword(String nick, String contraNueva) {
		boolean encontrado = false;

		this.usuario = (Usuario) this.mapper.findById(nick);
		Usuario cambiado = new Usuario(this.usuario.getName(),
				this.usuario.getPassword(), this.usuario.getFecha(),
				this.usuario.getImagen());
		cambiado.setPassword(contraNueva);
		this.mapper.update(this.usuario, cambiado);

		encontrado = true;

		for (LoginObserver o : observador)
			o.cambioContra();
		this.usuario = cambiado;

		if (!encontrado) {
			for (LoginObserver o : observador)
				o.usarioNoEncontrado();
		}
	}

	public void modifyDate(String nick, Date calcularFecha) {
		boolean encontrado = false;

		this.usuario = (Usuario) this.mapper.findById(nick);
		Usuario cambiado = new Usuario(this.usuario.getName(),
				this.usuario.getPassword(), this.usuario.getFecha(),
				this.usuario.getImagen());
		cambiado.setDate(calcularFecha);
		this.mapper.update(this.usuario, cambiado);

		encontrado = true;

		for (LoginObserver o : observador)
			o.cambioFecha();
		this.usuario = cambiado;

		if (!encontrado) {
			for (LoginObserver o : observador)
				o.usarioNoEncontrado();
		}
	}

	public void modifyImagen(String nick, byte[] nuevaImagen) {
		boolean encontrado = false;

		this.usuario = (Usuario) this.mapper.findById(nick);
		Usuario cambiado = new Usuario(this.usuario.getName(),
				this.usuario.getPassword(), this.usuario.getFecha(),
				this.usuario.getImagen());
		cambiado.setImage(nuevaImagen);
		this.mapper.update(this.usuario, cambiado);

		encontrado = true;

		for (LoginObserver o : observador)
			o.cambioImagen();
		this.usuario = cambiado;

		if (!encontrado) {
			for (LoginObserver o : observador)
				o.usarioNoEncontrado();
		}
	}

	public void nuevoUsuario(String nombre, String contra) {
		boolean existe = comprobarUsuario(nombre, contra);

		if (existe) {
			for (LoginObserver o : observador)
				o.usuarioExistente();
		} else {
			this.usuario = new Usuario(nombre, contra, null, null);
			this.mapper.insert(this.usuario);
			for (LoginObserver o : observador)
				o.nuevoUsuario();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> getCrosswordOf(String nick) {
		List<Object> lista = new ArrayList<Object>();

		lista = (List<Object>) resuelve.findById(nick);

		return lista;
	}

	public Date getDate(String nick) {
		return this.usuario.getFecha();
	}

	public String getNombre() {
		return this.usuario.getName();
	}

	public String getPassword() {
		return this.usuario.getPassword();
	}

	public byte[] getImagen() {
		return this.usuario.getImagen();
	}

	public void cambiarPuntuacion(int puntuacion) {
		// TODO Auto-generated method stub
		this.usuario.cambiarPuntuacion(puntuacion);
		for (PrincipalObserver o : observadorPrincipal)
			o.cambiarPuntuacion();
	}

	public int getPuntuacion() {
		// TODO Auto-generated method stub
		return this.usuario.getPuntuacion();
	}

}
