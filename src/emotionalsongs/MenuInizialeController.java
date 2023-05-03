/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuInizialeController extends Controller {

	@FXML private Button loginButton , toSearchButton , RegisterButton ; 

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		toSearchButton.setOnAction(
				event -> switchTo(event,"RicercaInRepository.fxml",new RicercaInRepositoryController(null,"MenuIniziale.fxml"))  );
		
		loginButton.setOnAction(
				event -> switchTo(event,"Login.fxml",new LoginController() ));
		
		RegisterButton.setOnAction(
				event -> switchTo(event,"Registrazione.fxml",new RegistrazioneController() ));
		
	}
	
}
