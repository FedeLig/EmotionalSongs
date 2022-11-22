package application;

import java.io.IOException;
import java.util.List;

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
	
    /*campi del login */
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
	private Label errorLabel;
	@FXML 
	private TextField nameField ,surnameField,codiceFiscaleField ,nomeViaField,civicoField,capField,comuneField,provinciaField,emailField,newIdField;
	@FXML
	private PasswordField singInPasswordField;
	@FXML 
	private Button toSecondTabButton,toThirdTabButton,SubmitButton;
	@FXML
	private Hyperlink LinkToLogin;
	@FXML
	private TabPane tabpane;
	@FXML
	private Tab firstTab,secondTab,thirdTab; 
	
	/*Attributi di calcolo */
	private String errore;
	private String[] dati = new String[7];
	
	
    
    /* DI SEGUITO I METODI DI NAVIGAZIONE FRA LE TAB NELLA SCENA REGISTRAZIONE
     * Non servono solo a raccogliere i dati della registrazione,
     * si occupano di verificarne l'usabilità evitandoo:
     * - Campi vuoti
     * - L'uso della virgola(per non entrare in conflitto col separatore dei file csv
     * - L'uso di dati già presenti nel sistema*/ 

    // Metodo per passare dalla prima tab alla seconda
    // Verifica l'unicità del codice fiscale.
    public void changeToSecondTab(ActionEvent e ) throws IOException {
 
    	String name = nameField.getText();
    	String surname = surnameField.getText();
    	String cf = codiceFiscaleField.getText();
        //prima controlliamo che nessun campo sia stato lasciato vuoto.
    	if(inputCheck(name) && inputCheck(surname) && inputCheck(cf)) {
    	    //Poi si verifica l'unicità del codice fiscale.	
    		if(Login.checkCf(cf)) {
		
			    SingleSelectionModel<Tab> selectionModel  = tabpane.getSelectionModel();
			    selectionModel.select(1);
			    firstTab.setDisable(true);
			    secondTab.setDisable(false);
			    dati[0] = name;
			    dati[1] = surname;
			    dati[2] = cf;
                //Se i dati rispettano tutti i criteri cancelliamo la stringa
                //di errore in modo da cancellare eventuali messaggi.
                this.errore = "";
    		}
    		else 
    			this.errore = "Errore: il codice fiscale inserito è già in uso";
    	}
    	else 
    		this.errore = "Errore: non possono essere lasciati spazi vuoti o contenenti virgole";

        //Alla fine di ogni fase si aggiorna sempre la errorLabel
    	this.errorLabel.setText(errore);
	}
    //metodo per passare dalla seconda tab alla terza.
    //Al momento non sono presenti controlli particolari,
    //ma bisognerà verificare che i dati inseriti rientrino nei rispettivi domini
    //EX: il numero civico non può essere "a!"
    public void changeToThirdTab(ActionEvent e ) throws IOException {
        String via = nomeViaField.getText();
        String civico = civicoField.getText();
        String cap = capField.getText();
        String comune = comuneField.getText();
        String provincia= provinciaField.getText();

        if(inputCheck(via) && inputCheck(civico) && inputCheck(cap) && inputCheck(comune) && inputCheck(provincia)) {
            SingleSelectionModel<Tab> selectionModel  = tabpane.getSelectionModel();
            selectionModel.select(2);
            secondTab.setDisable(true);
            thirdTab.setDisable(false);

            String indirizzo = String.format("%s;%s;%s;%s;%s;",via,civico,cap,comune,provincia);
            dati[3] = indirizzo;

            this.errore = "";
        }	    
        else
    		this.errore = "Errore: non possono essere lasciati spazi vuoti o contenenti virgole";

        this.errorLabel.setText(this.errore);
	}
    
  //Il comando per l'ultima tab effettua la registrazione e cambia scena.
  //Qua si verificano il nome utente e la mail.
    public void singIn(ActionEvent e) throws IOException {
        
        String email = emailField.getText();
        String userId = newIdField.getText();
        String password = singInPasswordField.getText();

        if(inputCheck(email) && inputCheck(userId) && inputCheck(password)) {
            dati[4] = email ; 
            dati[5] = userId; 
            dati[6] = password; 
            Login login;
            //Struttura if-else per verificare ciascun errore in ordine.
            if(! Login.checkEmail(email))    
                this.errore = "Errore: email già in uso";
            else if(! Login.checkUserId(userId))
                this.errore = "Errore: userId già in uso";
            else{
                login = new Login(dati);
                this.errore = "";
                
                if(login.isLogged())
    	            SwitchTo("/ScenaDue.fxml","login.css",e);
            }
        }
        else
    		this.errore = "Errore: non possono essere lasciati spazi vuoti o contenenti virgole";
        this.errorLabel.setText(this.errore);
    }

/* FINE CONTROLLI DELLA SCENA DI REGISTRAZIONE   
 *--------------------------------------------------------------------------------------------------
 * CONTROLLI DELLA SCENA DI LOGIN */ 

    //Metodo per efettuare il login, semplicemente si prova a creare un istanza della classe Login
	public void Login(ActionEvent e ) throws IOException {
		
		String idUtente = idTextField.getText();
		String password = passwordField.getText(); 
		if(inputCheck(idUtente) && inputCheck(password)) {
	        Login login = new Login(idUtente,password); 
	        //Per vedere se il login è andato a buon fine usiamo il metodo isLogged
	        if(login.isLogged()) {
                loginErrorLabel.setText("");
	            SwitchTo("/ScenaDue.fxml","login.css",e);
            }
	        else 
	            loginErrorLabel.setText("Errore: credenziali errate");	 
		}
		else
			loginErrorLabel.setText("Errore: non possono essere lasciati spazi vuoti o contenenti virgole");	
	}
	
	public void switchToRegistrazione(ActionEvent e ) throws IOException {
		SwitchTo("/Registrazione.fxml","Registrazione.css",e);
	}
	
	public void switchToLogin(ActionEvent e ) throws IOException {
		
	    SwitchTo("/Login.fxml","login.css",e);
	    List<Song> list  = Song.searchSong("testify");  
        
        list.get(0).printSongData();
		
	}

/*FINE COTROLLI DI REGISTRAZIONE
 * -----------------------------------------------------------------------------------------
 *INIZIO METODI AUSILIARI */

    //metodo che verifica l'integrità dei dati inseriti(no vuoti e virgole)
	private boolean inputCheck(String textField) {
		if(textField.contains(",") || textField.isBlank())
			return false;	
		else
			return true;
	}

    
    //metodo per il cambio di scena	
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
