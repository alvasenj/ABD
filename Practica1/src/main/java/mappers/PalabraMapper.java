package mappers;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Palabra;

public class PalabraMapper extends AbstractMapper<Object, Object> {

	@Override
	protected String getTableName() {
		return "palabras";
	}

	@Override
	protected String[] getColumnNames() {
		return new String[] { "IDp", "Secuencia", "Enunciado", "Imagen" };
	}

	@Override
	protected String getKeyColumnName() {
		return "IDp";
	}

	@Override
	protected Object buildObject(ResultSet rs) throws SQLException {
		List<Object> lista = new ArrayList<Object>();
		rs.beforeFirst();
		while (rs.next()) {
			Integer id = rs.getInt(getKeyColumnName());
			String secuencia = rs.getString("Secuencia");
			String enunciado = rs.getString("Enunciado");
			Blob imagen = rs.getBlob("Imagen");

			byte[] fotoBytes = null;
			if (imagen != null) {
				fotoBytes = imagen.getBytes(1, (int) imagen.length());
			}
			lista.add(new Palabra(id, secuencia, enunciado, fotoBytes));
		}
		return lista;
	}

	@Override
	protected Object[] decomposeObject(Object objeto) {
		Object[] lista = new Object[4];
		lista[0] = ((Palabra) objeto).getID();
		lista[1] = ((Palabra) objeto).getSecuencia();
		lista[2] = ((Palabra) objeto).getEnunciado();
		lista[3] = ((Palabra) objeto).getImagen();
		return lista;
	}

}
