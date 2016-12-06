package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Compuesto;

public class CompuestoMapper extends AbstractMapper<Object, Object> {

	@Override
	protected String getTableName() {
		return "compuesto";
	}

	@Override
	protected String[] getColumnNames() {
		return new String[] { getKeyColumnName(), "IDP", "Orientacion", "X",
				"Y", "Puntuacion" };
	}

	@Override
	protected String getKeyColumnName() {
		return "IDC";
	}

	@Override
	protected Object buildObject(ResultSet rs) throws SQLException {
		List<Object> lista = new ArrayList<Object>();
		rs.beforeFirst();
		while (rs.next()) {
			int idc = rs.getInt(getKeyColumnName());
			int idp = rs.getInt("IDP");
			String orientacion = rs.getString("Orientacion");
			int x = rs.getInt("X");
			int y = rs.getInt("Y");
			int puntuacion = rs.getInt("Puntuacion");

			lista.add(new Compuesto(idc, idp, orientacion, x, y, puntuacion));
		}
		return lista;
	}

	@Override
	protected Object[] decomposeObject(Object objeto) {
		// TODO Auto-generated method stub
		return null;
	}

}
