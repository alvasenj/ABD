package mappers;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import models.Usuario;

public class UserMapper extends AbstractMapper<Object, Object> {

	public UserMapper() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return "usuario";
	}

	@Override
	protected String[] getColumnNames() {
		return new String[] { getKeyColumnName(), "Password", "Fecha", "Imagen" };
	}

	@Override
	protected String getKeyColumnName() {
		return "Nick";
	}

	@Override
	protected Object buildObject(ResultSet rs) throws SQLException {
		String nick = rs.getString("Nick");
		String password = rs.getString("Password");
		Date fecha = rs.getDate("Fecha");
		Blob foto = rs.getBlob("Imagen");

		byte[] fotoBytes = null;
		if (foto != null) {
			fotoBytes = foto.getBytes(1, (int) foto.length());
		}

		return new Usuario(nick, password, fecha, fotoBytes);
	}

	@Override
	protected Object[] decomposeObject(Object objeto) {
		Object[] lista = new Object[4];
		lista[0] = ((Usuario) objeto).getName();
		lista[1] = ((Usuario) objeto).getPassword();
		lista[2] = ((Usuario) objeto).getFecha();
		lista[3] = ((Usuario) objeto).getImagen();
		return lista;
	}
}
