package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends Controller{

	@FXML 
	private TextField idTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label loginErrorLabel,IdLabel,PasswordLabel;
	@FXML 
	private Button LoginButton; 
	@FXML
	private Hyperlink LinkToRegistrazione;
	
    public void Login(ActionEvent e ) throws IOException {
		
		String idUtente = idTextField.getText();
		String password = passwordField.getText(); 
		if(inputCheck(idUtente) && inputCheck(password)) {
	        Login login = new Login(idUtente,password); 
	        //Per vedere se il login è andato a buon fine usiamo il metodo isLogged
	        if(login.isLogged()) {
                loginErrorLabel.setText("");
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/MenuUtente.fxml"));
                MenuUtenteController controller = new MenuUtenteController();
                fxmlloader.setController(controller);
        		controller.setUserId(idUtente);
        		setRoot(fxmlloader.load());
        		changeScene(e);
            }
	        else 
	            loginErrorLabel.setText("Errore: credenziali errate");	 
		}
		else
			loginErrorLabel.setText("Errore: non possono essere lasciati spazi vuoti o contenenti virgole");	
	}
	
	public void switchToRegistrazione(ActionEvent e ) throws IOException {
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/Registrazione.fxml"));
		setRoot(fxmlloader.load());
		changeScene(e);
	}
	
	private boolean inputCheck(String textField) {
		if(textField.contains(",") || textField.isBlank())
			return false;	
		else
			return true;
	}
}
