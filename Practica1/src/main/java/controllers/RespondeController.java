package controllers;

import gui.InterfazCrucigrama;
import models.ConsultasResponde;
import models.Responde;

public class RespondeController {

	private ConsultasResponde modelo;

	public RespondeController() {
		this.modelo = new ConsultasResponde();
	}

	public void addObserver(InterfazCrucigrama ventana) {
		this.modelo.addObserver(ventana);
	}

	public Responde[] findByUser(String nick, int idC) {
		return this.modelo.findByUser(nick, idC);
	}

	public void guardarRespuesta(Responde respuesta) {
		this.modelo.guardarRespuesta(respuesta);
	}
}
