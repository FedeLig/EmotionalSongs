/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

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
   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		super.initialize(arg0, arg1);
		goBackButton.setOnAction(
				event -> {
					 if(utente!= null) 
					       switchTo(event,indirizzoTabellaPrecedente,new MenuUtenteController(utente));
					    else
					       switchTo(event,indirizzoTabellaPrecedente,new MenuInizialeController());
				});
	}
	
	@Override
	protected  String getTestoHyperLink() {
		return "visualizza emozioni"; 
	}
	@Override 
    protected void  onHyperLinkCliked (ActionEvent event ,int indice ) throws FileNotFoundException, IOException{
		
		Song canzoneSelezionata = getSongObservableList().get(indice) ; 
		listaEmozioni = new ArrayList<VisualizzaEmozioniDati>() ; 
		setEmotionData(canzoneSelezionata.getId());
		
		switchTo(event,"VisualizzaEmozioni.fxml",new VisualizzaEmozioniController(utente,null,canzoneSelezionata,FXCollections.observableArrayList(listaEmozioni)));
		
	}
	
	private void setEmotionData(int indiceCanzone ) throws FileNotFoundException, IOException {
		this.listaEmozioni = Song.VisualizzaEmozioniBrano(indiceCanzone);
	}


}