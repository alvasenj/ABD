package models;

import java.util.ArrayList;
import java.util.List;

import mappers.CompuestoMapper;
import mappers.ConsultaAbs;
import mappers.PalabraMapper;
import observers.ICObserver;

public class ConsultasCompuesto extends ConsultaAbs {

	private ArrayList<ICObserver> observador;
	private CompuestoMapper compuesto = null;
	private PalabraMapper palabra = null;
	private Compuesto[] arrayPalabras = null;

	public ConsultasCompuesto() {
		super();
		this.observador = new ArrayList<ICObserver>();
		this.compuesto = new CompuestoMapper();
		this.palabra = new PalabraMapper();
	}

	public void addObserver(ICObserver ventana) {
		this.observador.add(ventana);
	}

	public Compuesto[] findByIDC(int idc) {
		@SuppressWarnings("unchecked")
		List<Object> lista = (List<Object>) this.compuesto.findById(idc);

		if (lista != null) {
			this.arrayPalabras = new Compuesto[lista.size()];
			int i = 0;
			while (i < lista.size()) {
				this.arrayPalabras[i] = (Compuesto) lista.get(i);
				i++;
			}
		} else
			for (ICObserver o : observador)
				o.crucigramaNoEncontrado();

		return this.arrayPalabras;
	}

	public Palabra getPalabra(int iDp) {
		@SuppressWarnings("unchecked")
		List<Palabra> lista = (List<Palabra>) this.palabra.findById(iDp);
		Palabra palabra = (Palabra) lista.get(0);
		return palabra;
	}
}
