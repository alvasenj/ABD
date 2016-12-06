package es.ucm.abd.practica2.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Crucigrama")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Crucigrama {

	@Id
	@Column(name = "IdC")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idC;

	@Column(name = "Titulo", nullable = false, length = 50)
	private String title;

	@Column(name = "Fecha", nullable = false)
	private Date date;

	@OneToMany(mappedBy = "crossword", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Contiene> contiene;

	protected Crucigrama() {
	}

	public Crucigrama(String title, Date date) {
		this.idC = null;
		this.title = title;
		this.date = date;
		this.contiene = new LinkedList<Contiene>();
	}

	public int getId() {
		return idC;
	}

	public String getTitle() {
		return title;
	}

	public Date getDate() {
		return date;
	}

	public List<Contiene> getContiene() {
		return contiene;
	}

	public void setId(int id) {
		this.idC = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setContiene(List<Contiene> contiene) {
		this.contiene = contiene;
	}
	
	public void addContiene(Contiene array){
		this.contiene.add(array);
	}

	@Override
	public String toString() {
		return "Crucigrama [id=" + idC + ", title=" + title + ", date=" + date
				+ ", definiciones=" + contiene.toString() + "]";
	}
	
	public static String calcularFecha() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}
	
	public List<Object[]> getList(){
	List<Object[]> lista = new LinkedList<Object[]>();
	for(int i = 0; i < this.contiene.size();i++){
		Object[] objeto = new Object[4];
		Contiene contiene = this.contiene.get(i);
		objeto[0] = contiene.getDefinition().getAnswer();
		objeto[1] = contiene.getX();
		objeto[2] = contiene.getY();
		objeto[3] = contiene.getOrientation();
		lista.add(objeto);
	}
	return lista;
	}

}
