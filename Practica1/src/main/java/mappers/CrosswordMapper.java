package mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import models.Crucigrama;

public class CrosswordMapper extends AbstractMapper<Object, Object> {

	public CrosswordMapper() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		return "crucigrama";
	}

	@Override
	protected String[] getColumnNames() {
		return new String[] { getKeyColumnName(), "Titulo", "Fecha" };
	}

	@Override
	protected String getKeyColumnName() {
		return "ID";
	}

	@Override
	protected Object buildObject(ResultSet rs) throws SQLException {
		List<Object> lista = new ArrayList<Object>();
		rs.beforeFirst();
		while (rs.next()) {
			Integer id = rs.getInt(getKeyColumnName());
			String titulo = rs.getString("Titulo");
			Date fecha = rs.getDate("Fecha");
			lista.add(new Crucigrama(id, titulo, fecha));
		}
		return lista;
	}

	protected Object[] decomposeObject(Object objeto) {
		Object[] lista = new Object[3];
		lista[0] = ((Crucigrama) objeto).getID();
		lista[1] = ((Crucigrama) objeto).getTitulo();
		lista[2] = ((Crucigrama) objeto).getDate();
		return lista;
	}

	public Object findByTitulo(Object titulo) {
		String tableName = getTableName();
		String[] columnNames = getColumnNames();
		String keyColumnName = "Titulo";

		String sql = "SELECT " + StringUtils.join(columnNames, ", ") + " FROM "
				+ tableName + " WHERE " + keyColumnName + " = ?";
		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {

			pst.setObject(1, titulo);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return buildObject(rs);
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
