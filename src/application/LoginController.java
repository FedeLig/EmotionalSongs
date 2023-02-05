package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends Controller implements Initializable {

	@FXML 
	private TextField idTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label IdLabel,PasswordLabel;
	@FXML 
	private Button LoginButton; 
	@FXML
	private Hyperlink LinkToRegistrazione;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		BooleanBinding condition  = (idTextField.textProperty().isEmpty())
                .or((passwordField.textProperty().isEmpty()));

        LoginButton.disableProperty().bind(condition);
        
		
	}
    public void Login(ActionEvent e ) throws IOException {
		
		String idUtente = idTextField.getText();
		String password = passwordField.getText(); 
		if(inputCheck(idUtente) && inputCheck(password)) {
	        Login login = new Login(idUtente,password); 
	        //Per vedere se il login è andato a buon fine usiamo il metodo isLogged
	        if(login.isLogged()) {
                //loginErrorLabel.setText("");
                login.setUserPlaylists(Login.getPlaylistsUtente(idUtente));
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/MenuUtente.fxml"));
                MenuUtenteController controller = new MenuUtenteController(login);
                fxmlloader.setController(controller);
        		setRoot(fxmlloader.load());
        		changeScene(e);
            }
		}
	        /*
	        else 
	            //loginErrorLabel.setText("Errore: credenziali errate");	 
		}
		else
			//loginErrorLabel.setText("Errore: non possono essere lasciati spazi vuoti o contenenti virgole");
			 * */	
			 
	}
	
    private boolean inputCheck(String textField) {
		if(textField.contains(",") || textField.isBlank())
			return false;	
		else
			return true;
	}
    
	public void switchToRegistrazione(ActionEvent e ) throws IOException {
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/Registrazione.fxml"));
		setRoot(fxmlloader.load());
		changeScene(e);
	}
	
    @FXML 
    public void switchToMenuIniziale(ActionEvent event )throws IOException {
  		
  		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/MenuIniziale.fxml"));
		setRoot(fxmlloader.load());
		changeScene(event);
		
	}
	
}
