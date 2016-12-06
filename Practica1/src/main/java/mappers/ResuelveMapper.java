package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Resuelve;

public class ResuelveMapper extends AbstractMapper<Object, Object> {

	public ResuelveMapper() {
		super();
	}

	@Override
	protected String getTableName() {
		return "resuelve";
	}

	@Override
	protected String[] getColumnNames() {
		return new String[] { "NickUs", "IDC", "Finalizado" };
	}

	@Override
	protected String getKeyColumnName() {
		return "NickUs";
	}

	@Override
	protected Object buildObject(ResultSet rs) throws SQLException {
		List<Object> lista = new ArrayList<Object>();
		rs.beforeFirst();
		while (rs.next()) {
			String nick = rs.getString("NickUs");
			int IDC = rs.getInt("IDC");
			boolean finalizado = rs.getBoolean("Finalizado");
			lista.add(new Resuelve(nick, IDC, finalizado));
		}
		return lista;
	}

	@Override
	protected Object[] decomposeObject(Object objeto) {
		Object[] lista = new Object[3];
		lista[0] = ((Resuelve) objeto).getUser();
		lista[1] = ((Resuelve) objeto).getID();
		lista[2] = ((Resuelve) objeto).getTerminado();
		return lista;
	}

}
