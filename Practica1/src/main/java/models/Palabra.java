package models;

public class Palabra {

	private int ID;
	private String secuencia;
	private String enunciado;
	private byte[] imagen;

	public Palabra(int x, String secuencia, String enunciado, byte[] fotoBytes) {
		this.ID = x;
		this.secuencia = secuencia;
		this.enunciado = enunciado;
		this.imagen = fotoBytes;
	}

	public int getID() {
		return this.ID;
	}

	public String getSecuencia() {
		return this.secuencia;
	}

	public String getEnunciado() {
		return this.enunciado;
	}

	public byte[] getImagen() {
		return this.imagen;
	}

}
