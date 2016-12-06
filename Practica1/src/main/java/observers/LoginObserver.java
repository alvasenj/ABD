package observers;

public interface LoginObserver {

	void usuarioLogeado();

	void usarioNoEncontrado();

	void errorBaseDatos();

	void usuarioExistente();

	void nuevoUsuario();

	void cambioContra();

	void crucigramaNoEncontrado();

	void cambioFecha();

	void cambioImagen();


}
