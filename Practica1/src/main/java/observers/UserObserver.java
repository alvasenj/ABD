package observers;

public interface UserObserver extends LoginObserver {

	void usuarioLogeado();

	void usarioNoEncontrado();

	void errorBaseDatos();

	void usuarioExistente();

	void nuevoUsuario();
}
