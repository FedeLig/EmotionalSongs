package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SceneController {
	
	private Stage stage; 
	private Scene scene; 
	private Parent root;
	
    /* campi del login */
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
	
	/*campi della registrazione */
	@FXML 
	private TextField nameField ,surnameField,codiceFiscaleField ,nomeViaField,civicoField,capField,comuneField,provinciaField,emailField,newIdField;
	@FXML
	private PasswordField newPasswordField;
	@FXML 
	private Button toSecondTabButton,toThirdTabButton,SubmitButton;
	@FXML
	private Hyperlink LinkToLogin;
	@FXML
	private TabPane tabpane;
	@FXML
	private Tab firstTab,secondTab,thirdTab; 
	
	
	public void Login(ActionEvent e ) throws IOException {
		
		String idUtenteInserito = idTextField.getText();
		String passwordInserita = passwordField.getText(); 
        Login login = new Login(idUtenteInserito,passwordInserita); 
        //Per vedere se il login è andato a buon fine usiamo il metodo isLogged
        if(login.isLogged()) 
            SwitchTo("/ScenaDue.fxml","login.css",e);
        else 
            loginErrorLabel.setText("errore : credenziali errate");	 
	}
	
	public void switchToRegistrazione(ActionEvent e ) throws IOException {
		SwitchTo("/Registrazione.fxml","Registrazione.css",e);
	}
	
	public void switchToLogin(ActionEvent e ) throws IOException {
		
	    SwitchTo("/Login.fxml","login.css",e);
		
	}
	
    public void changeToSecondTab(ActionEvent e ) throws IOException {
		
	    SingleSelectionModel<Tab> selectionModel  = tabpane.getSelectionModel();
	    selectionModel.select(1);
	    firstTab.setDisable(true);
	    secondTab.setDisable(false);
	    
		
	}
    
    public void changeToThirdTab(ActionEvent e ) throws IOException {
		
    	SingleSelectionModel<Tab> selectionModel  = tabpane.getSelectionModel();
	    selectionModel.select(2);
	    secondTab.setDisable(true);
	    thirdTab.setDisable(false);
	    
	}
    
	
	private void SwitchTo(String FileName,String cssname, ActionEvent e ) throws IOException{
		
		root = FXMLLoader.load(getClass().getResource(FileName));
	    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
	    Scene scene = new Scene(root);
	    String css = this.getClass().getResource(cssname).toExternalForm();
	    scene.getStylesheets().add(css);
	    
	    
	    stage.setResizable(false);
		stage.sizeToScene();
		stage.setTitle("Emotional Songs");
	    stage.setScene(scene);
	    stage.show();
		
	}
}
