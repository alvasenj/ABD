import java.util.Date;
import java.util.List;

import es.ucm.abd.practica2.dao.AbstractCrosswordDAO;
import es.ucm.abd.practica2.dao.DAO;
import es.ucm.abd.practica2.model.Contiene;
import es.ucm.abd.practica2.model.Crucigrama;
import es.ucm.abd.practica2.model.Definicion;
import es.ucm.abd.practica2.model.Orientation;


public class MyAbstractCrossword implements AbstractCrosswordFacade<Crucigrama, Definicion>{

	@Override
	public Crucigrama newCrossword(String title, Date date) {
		// TODO Auto-generated method stub
		return new Crucigrama(title, date);
	}

	@Override
	public Definicion newDefinition(String sequence, String hint, String[] tags) {
		// TODO Auto-generated method stub
		return new Definicion(sequence, hint, null, tags);
	}

	@Override
	public void addWordToCrossword(Crucigrama crossword, Definicion word,
			int row, int column, Orientation orientation) {
		Contiene contiene = new Contiene(crossword, word, row, column, orientation);
		crossword.addContiene(contiene);
	}
	
	@Override
	public String getAnswerOfWord(Definicion word) {
		// TODO Auto-generated method stub
		return word.getAnswer();
	}

	@Override
	public String[] getTagsOfWord(Definicion word) {
		// TODO Auto-generated method stub
		return word.getLabels();
	}

	@Override
	public String getHintOfWord(Definicion word) {
		// TODO Auto-generated method stub
		return word.getEnunciado();
	}

	@Override
	public String getTitleOfCrossword(Crucigrama crossword) {
		// TODO Auto-generated method stub
		return crossword.getTitle();
	}

	@Override
	public Date getDateOfCrossword(Crucigrama crossword) {
		// TODO Auto-generated method stub
		return crossword.getDate();
	}

	@Override
	public List<Object[]> getWordsOfCrossword(Crucigrama crossword) {
		// TODO Auto-generated method stub
		return crossword.getList();
	}

	@Override
	public AbstractCrosswordDAO<Crucigrama, Definicion> createDAO() {
		// TODO Auto-generated method stub
		return new DAO();
	}

}
