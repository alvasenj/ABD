package models;

import java.util.Date;

public class Usuario {

	private String nick;
	private String password;
	private Date fecha;
	private byte[] imagen;
	private int puntuacion = 0;

	public Usuario(String nick, String password, Date fecha, byte[] imagen) {
		this.nick = nick;
		this.password = password;
		this.fecha = fecha;
		this.imagen = imagen;
	}

	public String getName() {
		return this.nick;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public byte[] getImagen() {
		return this.imagen;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String contraNueva) {
		this.password = contraNueva;
	}

	public void setDate(Date calcularFecha) {
		this.fecha = calcularFecha;

	}

	public void setImage(byte[] nuevaImagen) {
		this.imagen = nuevaImagen;
	}
	
	public int getPuntuacion(){
		return this.puntuacion;
	}
	
	public void cambiarPuntuacion(int puntos){
		this.puntuacion = this.puntuacion + puntos;
	}
}
