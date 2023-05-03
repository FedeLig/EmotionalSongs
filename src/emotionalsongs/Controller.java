/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Questa classe astratta contiene il metodo che tutte le classi che rappresentano un Controller ,
 * ovvero il gestore di un interfaccia(scena) , usano per passare portare l'utente a un'altra interfaccia
 * che contiene una funzionalità richiesta dall' utente 
 * @author Federico Ligas 
 * @author Edoardo Picazio 
 */
abstract public class Controller implements Initializable {

	/**
	 * </>stage</> : contenitore di alto livello che contiene una scena 
	 */
	private Stage stage ;  
	/**
	 * </>root</> : il nodo "radice" della scena 
	 * <p> Gli elementi presenti una scena in JavaFX sono detti nodi , e sono organizzati in una gerarchia ad albero , 
	 * <p> il nodo radice è il nodo piu alto nella gerarchia e non ha padri 
	 */
	private Parent root ;
	
	 /**
	 * Permette di passare ad un'altra scena 
	 * @param event : evento scatenante  es. l'utente ha cliccato un bottone
	 * @param fileName : nome file che contiene fxml che descrive la scena 
	 * @param controller : istanza della classe estensione di Controller che gestisce la scena 
	 */
    public void switchTo(ActionEvent event , String fileName , Controller controller )  { 
    	
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/" + fileName ));
    	fxmlloader.setController(controller);
    	
		try {
			
			root = fxmlloader.load();
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			    
			stage.setResizable(false);
			stage.sizeToScene();
			stage.setTitle("Emotional Songs");
				
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException exp) {
			exp.printStackTrace();
		}   
    	
    }
    /**
	 * Crea un piccola finestra (Alert) che contiene un messaggio che si vuole comunicare all'utente
	 * @param messaggio : segnala un errore o il completamente di un'azione 
	 */
    public void createAlert(String messaggio) {
    	
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("");
    	alert.setHeaderText(null);
    	alert.setGraphic(null);
    	
    	DialogPane dialogPane = alert.getDialogPane();
    	dialogPane.setPrefWidth(300);
    	dialogPane.setPrefHeight(100);
    	dialogPane.getStylesheets().add(
    	   getClass().getResource("application.css").toExternalForm());
    	
    	HBox content = new HBox(new Label(messaggio) );
        content.setAlignment(Pos.BOTTOM_CENTER);
        
        alert.getDialogPane().setContent(content);
    	alert.showAndWait();
    	
    	
    }
 
}