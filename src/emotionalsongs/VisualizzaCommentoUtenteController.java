/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

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
	
	/**
	 * Viene chiamato (una e una sola volta) dal controller appena dopo la scena è stata "caricata" con successo 
     * e inizializza gli elementi che sono contenuti nella scena .
     * @param arg0 : Il path usato per risolvere i path relativi per l'oggetto "radice" o il valore null se il path non &egrave noto
     * @param arg1 : Le risorse usate per localizzare l'oggetto "radice" , o null se la radice non viene trovata 
	 */
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
