package models;

import java.util.ArrayList;
import java.util.List;

import mappers.ConsultaAbs;
import mappers.RespondeMapper;
import observers.ICObserver;

public class ConsultasResponde extends ConsultaAbs {

	private ArrayList<ICObserver> observador;
	private RespondeMapper responde = null;
	private Responde[] arrayResponde = null;
	private String nick;
	private int idC;

	public ConsultasResponde() {
		super();
		this.observador = new ArrayList<ICObserver>();
		this.responde = new RespondeMapper();
	}

	public void addObserver(ICObserver ventana) {
		this.observador.add(ventana);
	}

	public Responde[] findByUser(String nick, int idC) {

		this.nick = nick;
		this.idC = idC;
		@SuppressWarnings("unchecked")
		List<Object> lista = (List<Object>) this.responde.findById(nick);

		if (lista != null) {
			this.arrayResponde = new Responde[lista.size()];
			int i = 0;
			while (i < lista.size()) {
				Responde respuesta = (Responde) lista.get(i);
				if (respuesta.getIdC() == idC) {
					this.arrayResponde[i] = (Responde) lista.get(i);
				}
				i++;
			}
		}

		return this.arrayResponde;
	}

	public void guardarRespuesta(Responde respuesta) {
		boolean repetido = false;
		if (this.arrayResponde != null) {
			for (int i = 0; i < this.arrayResponde.length; i++) {
				if (this.arrayResponde[i] != null) {
					if (respuesta.getRespuesta().equalsIgnoreCase(
							this.arrayResponde[i].getRespuesta())) {
						repetido = true;
					}
				}
			}
		}
		if (!repetido) {
			this.responde.insert(respuesta);
			this.arrayResponde = findByUser(this.nick, this.idC);
		}
	}

}
