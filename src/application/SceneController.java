package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SceneController {
	
	private Stage stage ; 
	private Scene scene ; 
	private Parent root;
	
    /* campi del Login */
	@FXML 
	private TextField idTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label loginErrorLabel,IdLabel,PasswordLabel;
	@FXML 
	private Button LoginButton; 
	
	
	public void Login(ActionEvent e ) throws IOException {
		
		String idUtenteInserito = idTextField.getText();
		String passwordInserita = passwordField.getText(); 
        Login login = new Login(idUtenteInserito,passwordInserita); 
        //Per vedere se il login è andato a buon fine usiamo il metodo isLogged
        if(login.isLogged()) 
            SwitchTo("/ScenaDue.fxml",e);
        else 
            loginErrorLabel.setText("errore : credenziali errate");	 
	}
	
	public void switchToLogin(ActionEvent e ) throws IOException {
		
	    SwitchTo("/Login.fxml",e);
		
	}
	
	private void SwitchTo(String FileName, ActionEvent e ) throws IOException{
		
		root = FXMLLoader.load(getClass().getResource(FileName));
	    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
	    scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
		
	}
}
