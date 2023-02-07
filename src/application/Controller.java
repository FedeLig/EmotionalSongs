package application;

import java.io.IOException;
import javafx.event.ActionEvent;
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
 * ovvero il gestore di un interfaccia , usano per passare portare l'utente a un'altra interfaccia
 * che contiene una funzionalità richiesta dall' utente 
 * @author Ligas 
 */
public class Controller {

	private Stage stage ;  
	private Parent root ;
	
	/**
	 * Permette di passare da un'interfaccia grafica(scena) ad un'altra 
	 * @param event : evento che scatena il metodo
	 */
    public void changeScene(ActionEvent event ) throws IOException{
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		    
		stage.setResizable(false);
		stage.sizeToScene();
		stage.setTitle("Emotional Songs");
			
		stage.setScene(scene);
		stage.show();
			
    }
    
    public void createAlert(String messaggio) throws IOException {
    	
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
    
	public Parent getRoot() {
		return root;
	}

	public void setRoot(Parent root) {
		this.root = root;
	}
}