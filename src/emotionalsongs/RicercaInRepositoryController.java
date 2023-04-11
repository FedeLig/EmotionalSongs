/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

/**
 * Classe che si occupa della gestione della scena descritta da RicercaInRepository.fxml
 * , quando è richiesto ricercare una canzone per poter visualizzare il prospetto riassuntivo delle emozioni degli utenti
 * nota : la scena RicercaInRepository.fxml può essere usata anche da RicercaInRepositoryController in altri contesti
 * @see RicercaInRepository.fxml
 * @author Federico Ligas
 */
public class RicercaInRepositoryController extends SearchSongTableController {
	
	String indirizzoTabellaPrecedente ; 
	Login utente ; 
	
	ArrayList<VisualizzaEmozioniDati> listaEmozioni ; 
	
	public RicercaInRepositoryController(Login utente, String indirizzoTabellaPrecedente) {
		this.utente = utente ;
		this.indirizzoTabellaPrecedente = indirizzoTabellaPrecedente ; 
	}

	/**
	 * torna alla scena precedente : che può essere il menu Iniziale o il menu Utente , 
	 * a seconda dell' esecuzione del programma
	 * @param event : evento che scatena il metodo
	 */
	public void tornaAlPrecedente( ActionEvent e ) throws IOException{
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(indirizzoTabellaPrecedente));
	    if(utente!= null) {
	       MenuUtenteController controller = new MenuUtenteController(utente);
	       fxmlloader.setController(controller);
	    }
	    setRoot(fxmlloader.load());
	    changeScene(e);
	    
	}
	
	@Override
	protected  String getTestoHyperLink() {
		return "visualizza emozioni"; 
	}
	@Override 
    protected void  onHyperLinkCliked (ActionEvent e ,int indice ) throws FileNotFoundException, IOException{
		
		Song canzoneSelezionata = getSongObservableList().get(indice) ; 
		listaEmozioni = new ArrayList<VisualizzaEmozioniDati>() ; 
		setEmotionData(canzoneSelezionata.getId());
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/VisualizzaEmozioni.fxml"));
		VisualizzaEmozioniController controller = new VisualizzaEmozioniController(utente,null,canzoneSelezionata,FXCollections.observableArrayList(listaEmozioni));
		fxmlloader.setController(controller);
		setRoot(fxmlloader.load());
	    changeScene(e);
		
	}
	
	private void setEmotionData(int indiceCanzone ) throws FileNotFoundException, IOException {
		this.listaEmozioni = Song.VisualizzaEmozioniBrano(indiceCanzone);
	}
	public void setIndirizzoTabellaPrecedente(String indirizzoTabellaPrecedente) {
		this.indirizzoTabellaPrecedente = indirizzoTabellaPrecedente ; 
	}
	/**
	 * permette di impostare l'utente
	 * @param utente
	 */
	public void setUtente(Login utente) {
		this.utente = utente ; 
		
	}

}