package tests;

import java.util.List;

import mappers.CrosswordMapper;
import mappers.UserMapper;
import models.ConsultasCrucigrama;
import models.ConsultasUsuario;
import models.Crucigrama;
import models.Resuelve;
import models.Usuario;

@SuppressWarnings("unchecked")
public class CrosswordDAO {

	private UserMapper mapperUser = new UserMapper();
	private ConsultasUsuario usuario = new ConsultasUsuario();
	private ConsultasCrucigrama crucigrama = new ConsultasCrucigrama();
	CrosswordMapper mapperCruci = new CrosswordMapper();

	/**
	 * Aquí se debe inicializar el pool de conexiones, mediante la creación de
	 * un DataSource, que deberá ser asignado a la variable ds.
	 */
	public CrosswordDAO() {
	}

	/**
	 * Devuelve la contraseña del usuario cuyo nick se pasa como parámetro.
	 * Devuelve null si el usuario no existe.
	 */
	public String getPassword(String nick) {
		Usuario usuario = (Usuario) mapperUser.findById(nick);
		if (usuario != null)
			return usuario.getPassword();
		else
			return null;
	}

	/**
	 * Modifica la contraseña del usuario pasado como parámetro
	 */
	public void modifyPassword(String nick, String newPassword) {
		Usuario usuario = (Usuario) mapperUser.findById(nick);
		this.usuario.comprobarUsuario(nick, usuario.getPassword());
		this.usuario.modifyPassword(nick, newPassword);
	}

	/**
	 * Devuelve una lista de las claves de aquellos crucigramas cuyo título
	 * contenga str.
	 * 
	 * Si escogisteis una clave numérica para la tabla de crucigramas, se debe
	 * devolver una lista de Integer. Si escogisteis el título como clave, se
	 * debe devolver una lista de String. Si, por el contrario, escogisteis una
	 * clave compuesta, debéis crear una clase para almacenar los atributos de
	 * dicha clave.
	 */

	public List<Object> findCrosswordsByTitle(String str) {
		return (List<Object>) mapperCruci.findLike(str, "Titulo");
	}

	/**
	 * Devuelve el título del crucigrama cuya clave se pasa como parámetro.
	 */
	public String getCrosswordTitle(Object id) {
		int idCruci = 0;
		try {
			idCruci = ((Resuelve) id).getID();
		} catch (Exception e) {
			idCruci = ((Crucigrama) id).getID();
		}

		List<Object> lista = (List<Object>) mapperCruci.findById(idCruci);
		Crucigrama cruci = (Crucigrama) lista.get(0);
		return cruci.getTitulo();
	}

	/**
	 * Añade un crucigrama a la lista de crucigramas activos de un usuario.
	 * 
	 * El crucigrama se especifica mediante su clave
	 */
	public void addCrosswordToUser(String nick, Object crosswordId) {
		Object titulo = ((Crucigrama) crosswordId).getTitulo();
		crucigrama.addCroswordToUser(nick, titulo);
	}

	/**
	 * Devuelve la lista de identificadores de los crucigramas activos del
	 * usuario pasado como parámetro
	 */
	public List<Object> getCrosswordsOf(String nick) {
		return usuario.getCrosswordOf(nick);
	}

	/**
	 * Cierra el dataSource
	 */
	public void close() {
		// ((ComboPooledDataSource)ds).close();
	}
}
