package mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import dao.DataAccessor;

public abstract class AbstractMapper<T, K> extends ConsultaAbs {

	protected abstract String getTableName();

	protected abstract String[] getColumnNames();

	protected abstract String getKeyColumnName();

	protected abstract T buildObject(ResultSet rs) throws SQLException;

	protected abstract Object[] decomposeObject(T objeto);

	private DataAccessor accesor = null;

	public AbstractMapper() {
		super();
		this.accesor = new DataAccessor(ds);
	}

	public T findById(K id) {
		String tableName = getTableName();
		String[] columnNames = getColumnNames();
		String keyColumnName = getKeyColumnName();

		String sql = "SELECT " + StringUtils.join(columnNames, ", ") + " FROM "
				+ tableName + " WHERE " + keyColumnName + " = ?";
		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {

			pst.setObject(1, id);

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

	public T findLike(K like, Object key) {
		String tableName = getTableName();
		String[] columnNames = getColumnNames();
		String keyColumnName = key.toString();

		String sql = "SELECT " + StringUtils.join(columnNames, ", ") + " FROM "
				+ tableName + " WHERE " + keyColumnName + " LIKE " + "'%"
				+ like + "%'";
		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {

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

	public void insert(T insertado) {
		this.accesor.insertRow(getTableName(), getColumnNames(),
				decomposeObject(insertado));
	}

	public void update(T antiguo, T modificado) {
		this.accesor.updateRow(getTableName(), getColumnNames(),
				getColumnNames(), decomposeObject(antiguo),
				decomposeObject(modificado));
	}

}
