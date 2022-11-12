package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		
			Parent root = FXMLLoader.load(getClass().getResource("/ScenaDue.fxml"));
			Scene scene = new Scene(root);
			String css = this.getClass().getResource("login.css").toExternalForm();
			scene.getStylesheets().add(css);
			
			primaryStage.setResizable(false);
			primaryStage.sizeToScene();
			primaryStage.setTitle("Emotional Songs");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
