package models;

public class Responde {

	private String nick;
	private int idP;
	private int idC;
	private String fecha;
	private String respuesta;
	private boolean correcto;

	public Responde(String nick, int idC, int idP, String dateTime,
			String respuesta, boolean correcto) {
		this.nick = nick;
		this.idP = idP;
		this.idC = idC;
		this.fecha = dateTime;
		this.respuesta = respuesta;
		this.correcto = correcto;
	}

	public String getNick() {
		return nick;
	}

	public int getIdP() {
		return idP;
	}

	public int getIdC() {
		return idC;
	}

	public String getFecha() {
		return fecha;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public boolean isCorrecto() {
		return correcto;
	}
}
