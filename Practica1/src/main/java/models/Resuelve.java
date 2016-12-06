package models;

public class Resuelve {

	private String nick;
	private int IDC;
	private boolean terminado;

	public Resuelve(String nick, int idc, boolean terminado) {
		this.nick = nick;
		this.IDC = idc;
		this.terminado = terminado;
	}

	public String getUser() {
		return this.nick;
	}

	public int getID() {
		return this.IDC;
	}

	public boolean getTerminado() {
		return this.terminado;
	}

	public void setTerminado() {
		this.terminado = true;
	}
}
