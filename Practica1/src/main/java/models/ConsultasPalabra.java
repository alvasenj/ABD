package models;

import java.util.ArrayList;
import java.util.List;

import mappers.ConsultaAbs;
import mappers.PalabraMapper;

public class ConsultasPalabra extends ConsultaAbs {

	private PalabraMapper mapper = null;
	private Palabra palabra = null;

	public ConsultasPalabra() {
		super();
		this.mapper = new PalabraMapper();
	}

	@SuppressWarnings("unchecked")
	public Palabra findWord(int idPalabra) {

		List<Palabra> lista = new ArrayList<Palabra>();
		lista = (List<Palabra>) this.mapper.findById(idPalabra);
		this.palabra = lista.get(0);

		return this.palabra;
	}

	public int getIDP() {
		return this.palabra.getID();
	}

	public String getSecuencia() {
		return this.getSecuencia();
	}

	public String getEnunciado() {
		return this.palabra.getEnunciado();
	}

	public byte[] getImagen() {
		return this.palabra.getImagen();
	}
}
