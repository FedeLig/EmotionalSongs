package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Classe che si occupa della gestione della scena descritta da RicercaInRepository.fxml
 * , quando è richiesto ricercare una canzone per poter visualizzare il prospetto riassuntivo delle emozioni degli utenti
 * nota : la scena RicercaInRepository.fxml può essere usata anche da RicercaInRepositoryController in altri contesti
 * @see RicercaInRepository.fxml
 * @author Ligas
 */
public class RicercaInRepositoryController extends SearchSongTableController {
	
	String indirizzoTabellaPrecedente ; 
	Login utente ; 
	
	ArrayList<VisualizzaEmozioniDati> listaEmozioni ; 
	ArrayList<ArrayList<String>> listaCommenti ;
	
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
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/VisualizzaEmozioni.fxml"));
		Parent parent = null;
		
		Song canzoneSelezionata = getSongObservableList().get(indice) ; 
		listaEmozioni = new ArrayList<VisualizzaEmozioniDati>() ;
		listaCommenti = new ArrayList<ArrayList<String>>() ; 
		setEmotionData(canzoneSelezionata.getId());
		
		VisualizzaEmozioniController controller = new VisualizzaEmozioniController(canzoneSelezionata,FXCollections.observableArrayList(listaEmozioni),listaCommenti);
		fxmlloader.setController(controller);
		try {
			parent = fxmlloader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene dialog = new Scene(parent);
        Stage stage = new Stage();
        
        stage.setTitle("Emotional Songs");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(dialog);
        stage.showAndWait();
		
	}
	
	// 
	private void setEmotionData(int indiceCanzone ) throws FileNotFoundException, IOException {
		this.listaEmozioni = Song.getEmotionsResume(indiceCanzone);
	}
	public void setIndirizzoTabellaPrecedente(String indirizzoTabellaPrecedente) {
		this.indirizzoTabellaPrecedente = indirizzoTabellaPrecedente ; 
	}

	public void setUtente(Login utente) {
		this.utente = utente ; 
		
	}

}