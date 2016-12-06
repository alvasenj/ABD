package es.ucm.abd.practica2.dao;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import es.ucm.abd.practica2.model.Crucigrama;
import es.ucm.abd.practica2.model.Definicion;

public class DAO implements AbstractCrosswordDAO<Crucigrama, Definicion>{
	private SessionFactory factory;
	private Session sesion;
	private Transaction tx;
	
	public void setSessionFactory(SessionFactory f) {
		factory = f;
	}

	public int insertCrossword(Crucigrama crossword) {
		int i = 0;
		sesion = factory.openSession();
		tx = sesion.beginTransaction();
		try {
			i = (Integer) sesion.save(crossword);
			tx.commit();
		} catch (HibernateException he) {
			System.out.println("Error en la inserción");
			i = 0;
		}
		sesion.close();
		return i;
	}


	public void insertWord(Definicion word) {
		sesion = factory.openSession();
		tx = sesion.beginTransaction();
		try{
		sesion.save(word);
		tx.commit();
		} catch (HibernateException he) {
			System.out.println("Error en la inserción");
			System.out.println(he.getMessage());
		}
		sesion.close();
	}


	public Crucigrama findCrosswordById(int id) {
		sesion = factory.openSession();
		tx = sesion.beginTransaction();
		Crucigrama cruci = null;
		try{
			cruci =  (Crucigrama) sesion.get(Crucigrama.class, id);
		}catch (HibernateException he) {
			System.out.println("Error en la inserción");
		}
		sesion.close();
		return cruci;
	}

	@SuppressWarnings("unchecked")

	public List<Object[]> getCrosswordData(String str) {
		// //////SENTENCIA HQL///////
		sesion = factory.openSession();
		tx = sesion.beginTransaction();
		List<Object[]> lista = new LinkedList<Object[]>();
		List<Crucigrama> crucigramas = null;
		try {
			Query query = sesion
					.createQuery("FROM Crucigrama AS a WHERE a.title LIKE '%"
							+ str + "%'");
			crucigramas = query.list();
			
			for (int i = 0; i < crucigramas.size(); i++) {
				if (crucigramas.get(i) != null) {
					Object[] array = new Object[4];
					Crucigrama cruci = crucigramas.get(i);
					array[0] = cruci.getId();
					array[1] = cruci.getTitle();
					array[2] = cruci.getDate();
					
					  Query query2 = sesion.createQuery("SELECT b.crossword FROM Contiene AS b");
					  /*sesion.createQuery("SELECT b.crossword FROM Contiene AS b WHERE b.crossword_IdC = "
					  +cruci.getId()+"");*/
					array[3] = null;
					if (query2.list().size() > 0) {
						int contador = 0;
						for (int j = 0; j < query2.list().size(); j++) {
							Crucigrama contiene = (Crucigrama) query2.list().get(j);
							if (contiene.getId() == cruci.getId()) {
								contador++;
							}
						}
						array[3] = (long)contador;
					}
					lista.add(array);
				}
			}
		} catch (HibernateException he) {
			System.out.println("Error en la inserción");
			System.out.println(he.getMessage());
		}
		sesion.close();
		return lista;
	}


	public List<Definicion> findWordsByTags(String[] tags) {
		sesion = factory.openSession();
		tx = sesion.beginTransaction();
		List<Definicion> devueltos = new LinkedList<Definicion>();
		try{
			Query query = sesion
					.createQuery("FROM Definicion");
			@SuppressWarnings("unchecked")
			List<Definicion> definiciones = query.list();
			boolean[] nuevas = new boolean[definiciones.size()];
			int i = 0;
			boolean nuevo = false;
			while((i < query.list().size())){
				Definicion define = definiciones.get(i);
				int j = 0;
				while(j < tags.length){
					String[] etiquetas = define.getLabels();
					int k = 0;
					while(k < etiquetas.length){
						if(tags[j].equalsIgnoreCase(define.getLabels()[k])){
							k++;
							nuevo = false;
						} else {
							k++;
							nuevo = true;
						}
					}
				j++;	
				}
			
			nuevas[i] = nuevo;	
			i++;
			}
			
			for(int j = 0; j < nuevas.length; j++){
				if(!nuevas[j]){
					devueltos.add(definiciones.get(j));
				}
			}
			

		} catch (HibernateException he) {
			System.out.println("Error en la inserción");
			System.out.println(he.getMessage());
		}
		sesion.close();
		return devueltos;
	}


	public List<Definicion> getMatchingWords(CharConstraint[] constraints) {
		// TODO Auto-generated method stub
		return null;
	}

}
