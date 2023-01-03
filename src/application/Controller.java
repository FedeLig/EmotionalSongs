package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {

	private Stage stage ;  
	private Parent root ;
	
    public void changeScene(ActionEvent e ) throws IOException{
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
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