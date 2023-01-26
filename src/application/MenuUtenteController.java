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
	
	@FXML
    public void switchToRicercaRepository(ActionEvent e ) throws IOException {
		
		RicercaInRepositoryController controller = new RicercaInRepositoryController();
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/RicercaInRepository.fxml"));
		fxmlloader.setController(controller);
		setRoot(fxmlloader.load());
		controller.setIndirizzoTabellaPrecedente("/MenuUtente.fxml");
		changeScene(e);
	}
	
	@FXML
    public void switchToNomePlaylist(ActionEvent e ) throws IOException {
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/NomePlaylist.fxml"));
		setRoot(fxmlloader.load());
		changeScene(e);
		
	}
	
	
}
