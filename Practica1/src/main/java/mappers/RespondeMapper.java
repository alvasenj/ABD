package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import models.Responde;

public class RespondeMapper extends AbstractMapper<Object, Object> {

	@Override
	protected String getTableName() {
		return "responde";
	}

	@Override
	protected String[] getColumnNames() {
		return new String[] { "NickU", "IDC", "IDP", "FechaHora", "Respuesta",
				"Correcto" };
	}

	@Override
	protected String getKeyColumnName() {
		return "NickU";
	}

	@Override
	protected Object buildObject(ResultSet rs) throws SQLException {
		List<Object> lista = new ArrayList<Object>();
		rs.beforeFirst();
		while (rs.next()) {
			String nick = rs.getString("NickU");
			int IDC = rs.getInt("IDC");
			int IDP = rs.getInt("IDP");
			Time fecha = rs.getTime("FechaHora");
			String respuesta = rs.getString("Respuesta");
			boolean correcto = rs.getBoolean("Correcto");
			lista.add(new Responde(nick, IDC, IDP, fecha.toString(), respuesta,
					correcto));
		}
		return lista;
	}

	@Override
	protected Object[] decomposeObject(Object objeto) {
		Object[] lista = new Object[6];
		lista[0] = ((Responde) objeto).getNick();
		lista[1] = ((Responde) objeto).getIdC();
		lista[2] = ((Responde) objeto).getIdP();
		lista[3] = ((Responde) objeto).getFecha();
		lista[4] = ((Responde) objeto).getRespuesta();
		lista[5] = ((Responde) objeto).isCorrecto();
		return lista;
	}

}
