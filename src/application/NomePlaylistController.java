package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class NomePlaylistController extends Controller  {
	@FXML 
	private Button confermaNome ;
	@FXML
	private Button tornaAlMenu ; 
	@FXML
	private TextField nameField ; 
	
	private Login utente ; 
	

	public NomePlaylistController(Login utente) {
		this.utente = utente ; 
	}

	@FXML
    public void switchToCreazionePlaylist(ActionEvent e ) throws IOException {
		
		String nomePlaylist = nameField.getText();
		
		// aggiungere altri controlli ? es. lunghezza , no 2 playlist con stesso nome ? 
		
		if( !(nomePlaylist.isEmpty()) ) {
			
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/CreazionePlaylist.fxml"));
			CreazionePlaylistController controller = new CreazionePlaylistController(utente); 
			fxmlloader.setController(controller);
			Playlist playlist = new Playlist(nomePlaylist,utente.getUserName());
			controller.setPlaylist(playlist);
			setRoot(fxmlloader.load());
			changeScene(e);
			
		}
		
	}
	
	@FXML
    public void switchToMenuUtente(ActionEvent e ) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/MenuUtente.fxml"));
		MenuUtenteController controller = new MenuUtenteController(utente);
	    fxmlloader.setController(controller);
		setRoot(fxmlloader.load());
		changeScene(e);
		
	}
	
	
}