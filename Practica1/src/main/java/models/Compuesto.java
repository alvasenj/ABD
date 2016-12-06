package models;

public class Compuesto {

	private int idc;
	private int idp;
	private String orientacion;
	private int x;
	private int y;
	private int puntuacion;

	public Compuesto(int idc, int idp, String orientacion, int x, int y,
			int puntuacion) {
		this.idc = idc;
		this.idp = idp;
		this.orientacion = orientacion;
		this.x = x;
		this.y = y;
		this.puntuacion = puntuacion;
	}

	public int getIDc() {
		return this.idc;
	}

	public int getIDp() {
		return this.idp;
	}

	public boolean getOrientacion() {
		boolean orientacion = false;
		if (this.orientacion.equalsIgnoreCase("Vertical")) {
			orientacion = true;
		} else
			orientacion = false;
		return orientacion;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getPuntuacion() {
		return this.puntuacion;
	}

}
