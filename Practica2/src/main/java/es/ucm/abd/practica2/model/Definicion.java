package es.ucm.abd.practica2.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Definicion")
public class Definicion {

	@Id
	@Column(name = "IdD")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idD;

	@Column(name = "Enunciado", nullable = false)
	private String enunciado;

	@Column(name = "Answer", nullable = false)
	private String answer;

	@ElementCollection
	@OrderColumn(name = "Label")
	private String[] labels;

	@OneToMany(mappedBy = "definition", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Contiene> contienes;

	@Column(name = "Image")
	private byte[] image;
	
	protected Definicion(){}
	
	public Definicion(String answer, String enunciado, byte[] image,
			String[] labels ){
		this.idD = null;
		this.enunciado = enunciado;
		this.image = image;
		this.answer = answer;
		this.labels = labels;
		this.contienes = new LinkedList<Contiene>();
		;
	}

	public int getId() {
		return idD;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public byte[] getImage() {
		return image;
	}

	public String getAnswer() {
		return answer;
	}

	public String[] getLabels() {
		return labels;
	}

	public List<Contiene> getContiene() {
		return contienes;
	}

	public void setId(int id) {
		this.idD = id;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	public void setContiene(List<Contiene> contiene) {
		this.contienes = contiene;
	}

	@Override
	public String toString() {
		return "Definicion [id=" + idD + ", enunciado=" + enunciado + ", image="
				+ Arrays.toString(image) + ", answer=" + answer + ", labels="
				+ labels.toString() + ", crucigramas=" + contienes.toString()
				+ "]";
	}

}
