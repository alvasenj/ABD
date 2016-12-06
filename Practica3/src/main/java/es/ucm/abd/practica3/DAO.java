package es.ucm.abd.practica3;

import java.io.InputStream;

import javax.xml.namespace.QName;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import net.xqj.exist.ExistXQDataSource;

public class DAO {

	private XQDataSource xqds = null;
	
	public DAO() throws XQException{
		
		this.xqds = new ExistXQDataSource();
		this.xqds.setProperty("serverName", "localhost");
		this.xqds.setProperty("port", "8899");
		this.xqds.setProperty("user", "admin");
		
	}
	
	public String consulta1() throws XQException{
		
		String resultado = "";
		InputStream is = Main.class.getResourceAsStream("/Eurovision2.xquery");
		XQConnection conn = this.xqds.getConnection();
		XQPreparedExpression xqpe = conn.prepareExpression(is);	
		xqpe.bindInt(new QName("anyo"), 2014, null);
		XQResultSequence rs = xqpe.executeQuery();
		
		while (rs.next())
			resultado = resultado + '\n' + rs.getItemAsString(null);
		conn.close();
		
		return resultado;
		
	}
	
	public String consulta2() throws XQException{
		
		String resultado = "";
		InputStream is = Main.class.getResourceAsStream("/Eurovision3.xquery");
		XQConnection conn = this.xqds.getConnection();
		XQPreparedExpression xqpe = conn.prepareExpression(is);	
		xqpe.bindInt(new QName("anyo"), 2014, null);
		XQResultSequence rs = xqpe.executeQuery();
		
		while (rs.next())
			resultado = resultado + '\n' + rs.getItemAsString(null);
		conn.close();
		
		return resultado;
		
	}
}
