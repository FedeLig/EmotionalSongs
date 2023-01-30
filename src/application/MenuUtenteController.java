package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * Classe che si occupa della gestione della scena "Menu Utente"
 * @author Ligas
 */
public class MenuUtenteController extends Controller  {
	
	@FXML
	private Button toSearchButton ;
	@FXML
	private Button newPlaylistButton ;
	
	private String userId ; 
	
	@FXML
    public void switchToRicercaRepository(ActionEvent e ) throws IOException {
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/RicercaInRepository.fxml"));
		RicercaInRepositoryController controller = new RicercaInRepositoryController();
		fxmlloader.setController(controller);
		controller.setUserId(userId);
		controller.setIndirizzoTabellaPrecedente("/MenuUtente.fxml");
		setRoot(fxmlloader.load());
		changeScene(e);
	}
	
	@FXML
    public void switchToNomePlaylist(ActionEvent e ) throws IOException {
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/NomePlaylist.fxml"));
		NomePlaylistController controller = new NomePlaylistController();
		fxmlloader.setController(controller);
		controller.setUserId(userId);
		setRoot(fxmlloader.load());
		changeScene(e);
		
	}
	
	public void setUserId(String userId) {
		this.userId = userId ; 
	}
	
}
