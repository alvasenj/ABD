package controllers;

import java.util.List;

import models.ConsultasCrucigrama;
import models.Resuelve;

import gui.Login;

public class CrosswordController {

	private ConsultasCrucigrama modelo = new ConsultasCrucigrama();

	public CrosswordController() {

	}

	public void addCroswordToUser(String nick, Object titulo) {
		this.modelo.addCroswordToUser(nick, titulo);
	}

	public List<Object> buscarCrucigrama(String titulo) {
		return modelo.findByTitulo(titulo);
	}

	public String getTitle(Object i) {
		return modelo.getTitle(i);
	}

	public void addObserver(Login login) {
		this.modelo.addObserver(login);
	}

	public String getDate(int id) {
		return this.modelo.getDate();
	}

	public void setTerminado(Resuelve resuelve) {
		this.modelo.setTerminado(resuelve);
	}
}
