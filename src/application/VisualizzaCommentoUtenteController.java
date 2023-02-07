package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class VisualizzaCommentoUtenteController extends Controller implements Initializable {

	@FXML
	private TextArea areaCommento ; 
	
	private String commento ; 
	
	public VisualizzaCommentoUtenteController(String commento ) {
		
		this.commento = commento ; 
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		areaCommento.setText(commento);
		
	}
	
	/**
	 *  Chiude la finestra che contiene l'interfaccia grafica che la classe gestisce 
	 *  e ci riporta all' interfaccia grafica precedente 
	 *  @param event : azione che scatena l'esecuzione del metodo
	 */
	public void closeStage(ActionEvent event) {
		
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
        
    }

}
