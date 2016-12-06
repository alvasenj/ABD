package models;

import java.util.Date;

public class Crucigrama {

	private int ID;
	private String titulo;
	private Date fecha;

	public Crucigrama(int ID, String titulo, Date fecha) {
		this.ID = ID;
		this.titulo = titulo;
		this.fecha = fecha;
	}

	public int getID() {
		return this.ID;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public Date getDate() {
		return this.fecha;
	}
}
