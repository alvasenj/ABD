package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

public class DataAccessor {

	private DataSource ds;

	public DataAccessor(DataSource ds) {
		this.ds = ds;
	}

	public boolean insertRow(String tableName, String[] fields, Object[] values) {
		String sql = generateInsertStatement(tableName, fields);
		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			for (int i = 0; i < values.length; i++) {
				pst.setObject(i + 1, values[i]);
			}
			int numRows = pst.executeUpdate();
			return (numRows == 1);
		} catch (SQLException e) {
			System.out.println("¡Ya has insertado este elemento!");
			return false;
		}
	}

	private String generateInsertStatement(String tableName, String[] fields) {
		String fieldList = StringUtils.join(fields, ",");
		String[] marks = new String[fields.length];
		Arrays.fill(marks, "?");
		String markList = StringUtils.join(marks, ",");
		return "INSERT INTO " + tableName + " (" + fieldList + ") VALUES ("
				+ markList + ")";
	}

	public boolean updateRow(String tableName, String[] columns,
			String[] keyColumns, Object[] keyValues, Object[] newValues) {
		String sql = this.generateUpdateStatement(columns, keyColumns,
				tableName, keyValues, newValues);

		try (Connection con = this.ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {

			int columnasCambiadas = columnasCambiadas(newValues);
			int numComparaciones = numComparaciones(keyValues);
			int restador = 0;
			for (int i = 0; i < newValues.length; i++)
				if (newValues[i] != null) {
					pst.setObject(i + 1 - restador, newValues[i]);
				} else
					restador++;
			for (int i = 0; i < numComparaciones; i++)
				pst.setObject(i + 1 + columnasCambiadas, keyValues[i]);
			int numRows = pst.executeUpdate();
			return numRows == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private String generateUpdateStatement(String[] columns,
			String[] keyColumns, String tableName, Object[] keyValues,
			Object[] newValues) {
		int columnasCambiadas = columnasCambiadas(newValues);
		int numComparaciones = numComparaciones(keyValues);

		int restador = 0;
		String[] columnsWithMark = new String[columnasCambiadas];
		for (int i = 0; i < columns.length; i++) {
			if (newValues[i] != null) {
				columnsWithMark[i - restador] = columns[i] + " = ? ";
			} else
				restador++;
		}
		String[] conditions = new String[numComparaciones];
		for (int i = 0; i < keyColumns.length; i++) {
			if (keyValues[i] != null) {
				conditions[i] = keyColumns[i] + " = ? ";
			}
		}
		return "UPDATE " + tableName + " SET "
				+ StringUtils.join(columnsWithMark, " , ") + " WHERE "
				+ StringUtils.join(conditions, " AND ");
	}

	private int columnasCambiadas(Object[] cambiadas) {
		int columnasCambiadas = 0;
		for (int i = 0; i < cambiadas.length; i++) {
			if (cambiadas[i] != null) {
				columnasCambiadas++;
			}
		}
		return columnasCambiadas;
	}

	private int numComparaciones(Object[] comparaciones) {
		int numComparaciones = 0;
		for (int i = 0; i < comparaciones.length; i++) {
			if (comparaciones[i] != null) {
				numComparaciones++;
			}
		}
		return numComparaciones;
	}
}
