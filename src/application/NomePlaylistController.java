package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;


public class NomePlaylistController extends Controller  {
	@FXML 
	private Button confermaNome ;
	@FXML
	private Button tornaAlMenu ; 
	
	@FXML
    public void switchToCreazionePlaylist(ActionEvent e ) throws IOException {
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/CreazionePlaylist.fxml"));
		CreazionePlaylistController controller = new CreazionePlaylistController(); 
		fxmlloader.setController(controller);
		Playlist playlist = new Playlist("prova");
		controller.setPlaylist(playlist);
		setRoot(fxmlloader.load());
		changeScene(e);
		
	}
	
	@FXML
    public void switchToMenuUtente(ActionEvent e ) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/MenuUtente.fxml"));
		setRoot(fxmlloader.load());
		changeScene(e);
		
	}
	
	
}