package mappers;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public abstract class ConsultaAbs {
	protected DataSource ds = null;
	protected String className = "com.mysql.jdbc.Driver";

	public ConsultaAbs() {

		try {
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			cpds.setDriverClass("com.mysql.jdbc.Driver");
			cpds.setJdbcUrl("jdbc:mysql://localhost/practica1_508");
			cpds.setUser("UsuarioP1");
			cpds.setPassword("");

			cpds.setAcquireRetryAttempts(1);
			cpds.setAcquireRetryDelay(1);

			DataSource ds = cpds;
			this.ds = ds;
		} catch (Exception e) {
			System.out
					.println("Error en la base de datos, compruebe la conexión de la Base de Datos");
			System.exit(1);
		}
	}

	public void close() {
		((ComboPooledDataSource) ds).close();
	}

}
