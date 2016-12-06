package es.ucm.abd.practica2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Contiene")
public class Contiene {
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne
	private Crucigrama crossword;
	
	@ManyToOne
	private Definicion definition;
	
	@Column(name = "x")
	private int x;
	
	@Column(name = "y")
	private int y;
	
	@Column(name = "Orientacion")
	private Orientation orientation;
	
	protected Contiene() {
	}
	
	public Contiene(Crucigrama crucigrama, Definicion definicion, int x, int y,
			Orientation orientation) {
		this.id = null;
		this.crossword = crucigrama;
		this.definition = definicion;
		this.x = x;
		this.y = y;
		this.orientation = orientation;
	}

	
	/*public Contiene(Crucigrama crucigrama, Definicion definicion, int x, int y,
			Orientation orientation, int idC, int idD) {
		this.crossword = crucigrama;
		this.definition = definicion;
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		this.crossword.setId(idC);
		this.definition.setId(idD);
	}*/

	public Crucigrama getCrossword() {
		return crossword;
	}

	public Definicion getDefinition() {
		return definition;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setCrossword(Crucigrama crossword) {
		this.crossword = crossword;
	}

	public void setDefinition(Definicion definition) {
		this.definition = definition;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	@Override
	public String toString() {
		return "Contiene [crossword=" + crossword + ", definition="
				+ definition + ", x=" + x + ", y=" + y + ", orientation="
				+ orientation + "]";
	}
	
	

	

}
