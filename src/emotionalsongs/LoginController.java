/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Classe che permette ad utente si è registrato precedentemente
 * @author Federico Ligas
 * @author Edoardo Picazio 
 */
public class LoginController extends Controller implements Initializable {

	/**
	 * </>idTextField</> : area di testo dove inserire l'id utente ( username ) 
	 */
	@FXML private TextField idTextField;
	/**
	 * </>passwordField</> : area di testo dove inserire la password 
	 */
	@FXML private PasswordField passwordField;
	/**
	 * </>IdLabel</> : area di testo che riporta : "Id utente :", </>PasswordLabel</> : area di testo che riporta : "Password :"
	 */
	@FXML private Label IdLabel,PasswordLabel;
	/**
	 * </>LoginButton</> : buttone per accedere 
	 */
	@FXML private Button LoginButton , backToMenu ; 
	/**
	 * </>LinkToRegistrazione</> : link che rimanda alla registrazione 
	 */
	@FXML private Hyperlink LinkToRegistrazione;
	
	/**
	 * Viene chiamato (una e una sola volta) dal controller appena dopo la scena è stata "caricata" con successo 
     * e inizializza gli elementi che sono contenuti nella scena .
     * @param arg0 : Il path usato per risolvere i path relativi per l'oggetto "radice" o il valore null se il path non &egrave noto
     * @param arg1 : Le risorse usate per localizzare l'oggetto "radice" , o null se la radice non viene trovata 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		BooleanBinding condition  = (idTextField.textProperty().isEmpty())
                .or((passwordField.textProperty().isEmpty()));

        LoginButton.disableProperty().bind(condition);
        
        LoginButton.setOnAction(
        		event -> {
					try {
						Login(event);
					} catch (IOException exp) {
						exp.printStackTrace();
					}
				} )  ; 
        
		backToMenu.setOnAction(
				event -> switchTo(event,"MenuIniziale.fxml",new MenuInizialeController()) ) ; 
		
		LinkToRegistrazione.setOnAction(
				event -> switchTo(event,"Registrazione.fxml",new RegistrazioneController() ) ) ; 
		
	}
	 /**
     * Permette all 'utente registrato di accedere 
     * @param event : evento che scatena il metodo
     */
    public void Login(ActionEvent event ) throws IOException {
		
		String idUtente = idTextField.getText();
		String password = passwordField.getText(); 
		if(inputCheck(idUtente) && inputCheck(password)) {
	        Login login = new Login(idUtente,password); 
	        //Per vedere se il login è andato a buon fine usiamo il metodo isLogged
	        if(login.isLogged()) {
                //loginErrorLabel.setText("");
                login.setUserPlaylists(Login.getPlaylistsUtente(idUtente));
                switchTo(event,"MenuUtente.fxml",new MenuUtenteController(login));
            }
		    else 
	        	createAlert("Errore : credenziali errate");	 
		}
		else
			createAlert("Errore : non possono essere lasciati \n    spazi vuoti o contenenti virgole");		
			 
	}
	 /**
     * Controlla un input di testo inserito dall'utente in una casella di testo
     * @param textField : testo inserito nell' area dedicata 
     * @return true : accettabile , false : non accettabile 
     */
    private boolean inputCheck(String textField) {
		if(textField.contains(",") || textField.isBlank())
			return false;	
		else
			return true;
	}
	
	
}
