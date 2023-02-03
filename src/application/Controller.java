package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    
	public Parent getRoot() {
		return root;
	}

	public void setRoot(Parent root) {
		this.root = root;
	}
}