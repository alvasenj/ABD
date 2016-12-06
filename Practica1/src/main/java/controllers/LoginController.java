package controllers;

import java.util.Date;
import java.util.List;

import models.ConsultasUsuario;
import gui.Login;
import gui.NewUser;
import gui.PrincipalWindow;
import gui.UserInfo;

public class LoginController {

	private ConsultasUsuario modelo = new ConsultasUsuario();

	public LoginController() {
	}

	public void buscarUsuario(String nombre, String contra) {
		modelo.comprobarUsuario(nombre, contra);
	}

	public void newUser(String nombre, String contra) {
		modelo.nuevoUsuario(nombre, contra);
	}

	public List<Object> getCrosswordOf(String nick) {
		return modelo.getCrosswordOf(nick);
	}

	public void modifyPassword(String nick, String contraNueva) {
		this.modelo.modifyPassword(nick, contraNueva);
	}

	public void modifyDate(String nick, Date calcularFecha) {
		this.modelo.modifyDate(nick, calcularFecha);
	}

	public void modifyImagen(String nick, byte[] nuevaImagen) {
		this.modelo.modifyImagen(nick, nuevaImagen);
	}

	public void addObserver(Login login) {
		this.modelo.addObserver(login);
	}

	public void addObserver(NewUser newUser) {
		this.modelo.addObserver(newUser);
	}

	public void addObserver(UserInfo userInfo) {
		this.modelo.addObserver(userInfo);
	}

	public Date getDate(String nick) {
		return this.modelo.getDate(nick);
	}

	public String getNombre() {
		return this.modelo.getNombre();
	}

	public byte[] getImagen() {
		return this.modelo.getImagen();

	}

	public void cambiarPuntuacion(int puntuacion) {
		// TODO Auto-generated method stub
		this.modelo.cambiarPuntuacion(puntuacion);
	}
	
	public void addObserver(PrincipalWindow ventana){
		this.modelo.addObserver(ventana);
	}

	public int getPuntuacion() {
		// TODO Auto-generated method stub
		return this.modelo.getPuntuacion();
	}

}
