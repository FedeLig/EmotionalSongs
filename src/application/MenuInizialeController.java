package application;

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
	
	@FXML
    public void switchToRicercaRepository(ActionEvent e ) throws IOException {
		
		RicercaInRepositoryController controller = new RicercaInRepositoryController();
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/RicercaInRepository.fxml"));
		fxmlloader.setController(controller);
		setRoot(fxmlloader.load());
		controller.setIndirizzoTabellaPrecedente("/MenuIniziale.fxml");
		changeScene(e);
	}
	
	@FXML
    public void switchToLogin(ActionEvent e ) throws IOException {
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/Login.fxml"));
		setRoot(fxmlloader.load());
		changeScene(e);
		
	}
	
	@FXML
    public void switchToRegistrazione(ActionEvent e ) throws IOException {
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/Registrazione.fxml"));
		setRoot(fxmlloader.load());
		changeScene(e);
		
	}
	
	
}
