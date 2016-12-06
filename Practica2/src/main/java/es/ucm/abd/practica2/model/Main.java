package es.ucm.abd.practica2.model;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory factory = buildSessionFactory();
		Session session = factory.openSession();
		Transaction tr = session.beginTransaction();
	
		Crucigrama cruci1 = new Crucigrama("Crucigrama1", new Date());
		

		Definicion def1 = new Definicion("Automovil", "Coche",
				 null, new String[]{ "auto, viajar, conducir" });

		Definicion def2 = new Definicion("Vehiculo motorizado de dos ruedas",
				"Moto", null,  new String[] { "gp, competicion, velocidad" });
		
		Definicion def3 = new Definicion("Vehiculo infantil de tres ruedas",
				"Triciclo", null ,new String[] { "niño, aprender" });

		Definicion def4 = new Definicion("Vehiculo espacial", "Cohete",
				null, new String[] { "espacio, astronauta" });
		
		Contiene contiene = new Contiene(cruci1, def1, 2, 1,
				Orientation.HORIZONTAL);
		
		Contiene contiene2 = new Contiene(cruci1, def2, 1, 2,
				Orientation.VERTICAL);
		
		Contiene contiene3 = new Contiene(cruci1, def3, 4, 5,
				Orientation.VERTICAL);
		
		Contiene contiene4 = new Contiene(cruci1, def4, 4, 1,
				Orientation.HORIZONTAL);
		
		
		Contiene[] contienes = new Contiene[4];
		contienes[0] = contiene;
		contienes[1] = contiene2;
		contienes[2] = contiene3;
		contienes[3] = contiene4;
		
		cruci1.getContiene().add(contienes[0]);
//		cruci1.getContiene().add(contiene2);
//		cruci1.getContiene().add(contiene3);
//		cruci1.getContiene().add(contiene4);
		def1.getContiene().add(contiene);
		def2.getContiene().add(contiene2);
		def3.getContiene().add(contiene3);
		def4.getContiene().add(contiene4);
		
		
		
		session.save(def1);
		session.save(def2);
		session.save(def3);
		session.save(def4);
		session.save(cruci1);
		session.save(contiene);
		session.save(contiene2);
		session.save(contiene3);
		session.save(contiene4);
		
		

		/*
		Crucigrama cruci2 = new Crucigrama("Crucigrama2", calcularFecha());

		Definicion def5 = new Definicion(
				"Prenda superior masculina y femenina", null, "Camiseta",
				new String[] { "pecho, pendra, torso, ropa interior" });

		Definicion def6 = new Definicion("Prenda para la cabeza", null,
				"Gorro",
				new String[] { "cabeza, calvo, cubrir, abrigar, nieve" });

		Definicion def7 = new Definicion(
				"Complemento elegante, normalmente con traje", null, "Corbata",
				new String[] { "traje, camisa, clase, complemento masculino" });
		Definicion def8 = new Definicion(
				"Usado para proteger la cabeza en caso de caida", null,
				"Casco",
				new String[] { "Proteccion, motogp, formula1, cabeza" });

		

		
		/*
		Contiene contiene5 = new Contiene(cruci1, def1, 1, 3,
				Orientation.HORIZONTAL);
		Contiene contiene6 = new Contiene(cruci1, def2, 3, 1,
				Orientation.HORIZONTAL);
		Contiene contiene7 = new Contiene(cruci1, def3, 1, 3,
				Orientation.VERTICAL);
		Contiene contiene8 = new Contiene(cruci1, def4, 5, 2,
				Orientation.HORIZONTAL);*/

		
		tr.commit();
		session.close();
		factory.close();
	}

	private static SessionFactory buildSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.configure();
		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
		ssrb.applySettings(configuration.getProperties());
		StandardServiceRegistry registry = ssrb.build();
		return configuration.buildSessionFactory(registry);
	}



}
