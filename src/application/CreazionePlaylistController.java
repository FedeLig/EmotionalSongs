package application;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CreazionePlaylistController extends SongTableController {

	@FXML
	private Button RicercaCanzone ;
	@FXML
	private Button TornaAlMenu ;
	
	private Playlist playlist ; 
	
	/* il  metodo Initialize viene chiamato (una e una sola volta)
    dal controller appena dopo la finestra è stata "caricata" con successo 
    e contiene la tabella che vogliamo visualizzare la prima volta  */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		songObservableList = playlist.getListaCanzoni();
		
		if (songObservableList.isEmpty()) 
			tabellaCanzoni.setPlaceholder(new Label("La playlist non contiene canzoni"));
		else 
			UpdateTable(songObservableList);
		
	}

	@FXML
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
		setRoot(fxmlloader.load());
		changeScene(e);
		playlist.svoutaListaCanzoni();
		
	}

	@Override
	protected void onHyperLinkCliked(ActionEvent e ,int indice ) {
		
		songObservableList.remove(indice);
		UpdateTable(songObservableList);
	}

	@Override
	protected String getTestoHyperLink() {
		return "elimina"; 
	}
	
	public void setPlaylist( Playlist playlist ) {
		this.playlist = playlist ; 
	}
	
}
