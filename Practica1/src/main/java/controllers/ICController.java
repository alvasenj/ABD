package controllers;

import gui.InterfazCrucigrama;
import models.Compuesto;
import models.ConsultasCompuesto;
import models.Palabra;

public class ICController {

	private ConsultasCompuesto modelo;

	public ICController() {
		this.modelo = new ConsultasCompuesto();
	}

	public void addObserver(InterfazCrucigrama ventana) {
		this.modelo.addObserver(ventana);
	}

	public Compuesto[] getCompuesto(int idc) {
		return this.modelo.findByIDC(idc);
	}

	public Palabra getPalabra(int iDp) {
		return this.modelo.getPalabra(iDp);
	}
}
