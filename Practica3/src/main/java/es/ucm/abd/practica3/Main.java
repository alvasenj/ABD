package es.ucm.abd.practica3;
import java.io.InputStream;
import javax.xml.namespace.QName;
import javax.xml.xquery.*;

import net.xqj.exist.ExistXQDataSource;

public class Main{
	
	public static void main(String[] args) throws XQException {		
		DAO dao = new DAO();
		MainWindow ventana = new MainWindow(dao);	
	}
}