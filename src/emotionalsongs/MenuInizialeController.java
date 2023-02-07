/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class MenuInizialeController extends Controller {

	@FXML
	private Button loginButton ; 
	@FXML 
	private Button toSearchButton ; 
	@FXML
	private Button RegisterButton ;
	/**
	 * Porta alla scena della ricerca 
	 * @param event : evento che scatena il metodo
	 */
	@FXML
    public void switchToRicercaRepository(ActionEvent e ) throws IOException {
		
		RicercaInRepositoryController controller = new RicercaInRepositoryController(null,"/MenuIniziale.fxml");
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/RicercaInRepository.fxml"));
		fxmlloader.setController(controller);
		setRoot(fxmlloader.load());
		changeScene(e);
	}
	
	/**
	 * Porta alla scena del login 
	 * @param event : evento che scatena il metodo
	 */
	@FXML
    public void switchToLogin(ActionEvent e ) throws IOException {
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/Login.fxml"));
		setRoot(fxmlloader.load());
		changeScene(e);
		
	}
	
	/**
	 * Porta alla scena della registrazione 
	 * @param event : evento che scatena il metodo
	 */
	@FXML
    public void switchToRegistrazione(ActionEvent e ) throws IOException {
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/Registrazione.fxml"));
		setRoot(fxmlloader.load());
		changeScene(e);
		
	}
	
	
}
