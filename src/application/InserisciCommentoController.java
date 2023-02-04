package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

public class InserisciCommentoController extends Controller implements Initializable {

	private String[] listaCommenti  ;
	private int indice ; 
	private InserisciEmozioniController controllerPrecedente ; 
	
	@FXML 
	private TextArea areaCommento ; 
	@FXML
	private Label caratteriRimasti ; 
	
	public InserisciCommentoController( String[] listaCommenti , int indice , InserisciEmozioniController controllerPrecedente ) {

		this.listaCommenti = listaCommenti ;
		this.indice = indice ; 
		this.controllerPrecedente = controllerPrecedente;
		
	}

	// serve ? 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		areaCommento.setText(listaCommenti[indice]);
		
		areaCommento.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 256 ? change : null));
		
		caratteriRimasti.textProperty().bind(new SimpleIntegerProperty(256).subtract(Bindings.length(areaCommento.textProperty())).asString());
		
	}
	
	public void salvaCommento(ActionEvent event ) {
		
		listaCommenti[indice] = areaCommento.getText();
		controllerPrecedente.setListaCommenti(listaCommenti);
		closeStage(event);
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