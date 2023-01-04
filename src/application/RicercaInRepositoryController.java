package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;


public class RicercaInRepositoryController extends SearchSongTableController {
	
	/* il  metodo Initialize viene chiamato (una e una sola volta)
       dal controller appena dopo la finestra è stata "caricata" con successo 
       e contiene la tabella che vogliamo visualizzare la prima volta  */
	String indirizzoTabellaPrecedente ; 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	    
		listaOpzioni = new ArrayList<String>(Arrays.asList("  titolo","  autore e anno"));
		
		sceltaFiltro.setItems(FXCollections.observableArrayList(listaOpzioni));
		 
		sceltaFiltro.setValue("");
		
		sceltaFiltro.setOnAction(event -> { ChangeFilter(); });
		
		searchButton.setOnAction(event -> { 
			ArrayList<Song> risultatiRicerca = new ArrayList<Song>();
			try {
				risultatiRicerca = Song.searchSong("testify");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			songObservableList = FXCollections.observableArrayList(risultatiRicerca);
			UpdateTable(songObservableList); 
			
		});
		
		goBackButton.setOnAction(event -> { 
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(indirizzoTabellaPrecedente));
	    try {
	    	setRoot(fxmlloader.load());
	    	changeScene(event);
		} catch (IOException e) {
			e.printStackTrace();
		} });
		
		tabellaCanzoni.setPlaceholder(new Label("La tabella è vuota"));
		
	}
	
	@Override
	protected  String getTestoHyperLink() {
		return "statistiche"; 
	}
	@Override 
    protected void  onHyperLinkCliked (ActionEvent e ,int indice ){
		
		System.out.println("visualizza statistiche");
		
	}
	
	public void setIndirizzoTabellaPrecedente(String indirizzoTabellaPrecedente) {
		this.indirizzoTabellaPrecedente = indirizzoTabellaPrecedente ; 
	}
	

}
