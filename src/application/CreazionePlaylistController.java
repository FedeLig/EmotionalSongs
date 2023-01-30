package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * La classe che si occupa di gestire la creazione delle playlist
 * contiene i controlli per tutte le operazioni effettuabili nella schermata "playlist"
 * @author Ligas
 *
 */
public class CreazionePlaylistController extends SongTableController {

	@FXML
	private Button RicercaCanzone ;
	@FXML
	private Button TornaAlMenu ;
	
	private Playlist playlist ; 
	
	/**
	 * il  metodo Initialize viene chiamato (una e una sola volta)
     * dal controller appena dopo la finestra è stata "caricata" con successo 
     * e contiene la tabella che vogliamo visualizzare la prima volta
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		setSongObservableList(playlist.getListaCanzoni());
		
		if (getSongObservableList().isEmpty()) 
			getTabellaCanzoni().setPlaceholder(new Label("La playlist non contiene canzoni"));
		else 
			UpdateTable(getSongObservableList());
		
	}
    @FXML
	public void salvaPlaylist(ActionEvent e ) throws IOException {
		
		// serve creare un nuovo oggetto playlist ? 
		
		if(!(playlist.getListaCanzoni().isEmpty())) {
		   playlist.RegistraPlaylist();
		   System.out.println("la playlist e' stata salvata");
		}
		else {
			System.out.println("la playlist è vuota");
		}
		
	}
    @FXML 
    public void switchToRicercaAvanzataAutore(ActionEvent e) throws IOException {
    	
    	RicercaAvanzataController controller = new RicercaAvanzataController("autore");
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/RicercaAvanzata.fxml"));
    	fxmlloader.setController(controller);
    	controller.setPlaylist(playlist);
    	setRoot(fxmlloader.load());
		changeScene(e);
    }
    
    @FXML 
    public void switchToRicercaAvanzataAlbum(ActionEvent e) throws IOException {
    	
    	RicercaAvanzataController controller = new RicercaAvanzataController("album");
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/RicercaAvanzata.fxml"));
    	fxmlloader.setController(controller);
    	controller.setPlaylist(playlist);
    	setRoot(fxmlloader.load());
		changeScene(e);
    }
	@FXML
	/**
	 * Cambia la scena in "ricerca canzone"
	 * @param e : evento "aggiungi canzone"
	 */
    public void switchToAddSong(ActionEvent e) throws IOException {
        
		AddSongToPlaylistController controller = new AddSongToPlaylistController();
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/RicercaInRepository.fxml"));
		fxmlloader.setController(controller);
		setRoot(fxmlloader.load());
		controller = fxmlloader.getController();
		controller.setPlaylist(playlist);
		changeScene(e);
		
    }
	
	@FXML 
	public void switchToMenuUtente(ActionEvent e) throws IOException { 
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/MenuUtente.fxml"));
		MenuUtenteController controller = new MenuUtenteController();
	    fxmlloader.setController(controller);
	    controller.setUserId(playlist.getAutore());
	    playlist.svoutaListaCanzoni();
		setRoot(fxmlloader.load());
		changeScene(e);
		
		
	}

	@Override
	protected void onHyperLinkCliked(ActionEvent e ,int indice ) {
		
		getSongObservableList().remove(indice);
		UpdateTable(getSongObservableList());
	}

	@Override
	protected String getTestoHyperLink() {
		return "elimina"; 
	}
	
	public void setPlaylist( Playlist playlist ) {
		this.playlist = playlist ; 
	}
	
}
