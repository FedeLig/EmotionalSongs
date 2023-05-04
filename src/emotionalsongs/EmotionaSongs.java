/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Classe che si occupa di iniziare il programma, avvia l'applicazione
 * presentando all'utente il menu iniziale.
 * @author Edoardo Picazio
 */
public class EmotionaSongs extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/MenuIniziale.fxml"));
			fxmlloader.setController(new MenuInizialeController());
			Parent root = fxmlloader.load();
			Scene scene = new Scene(root);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setResizable(false);
			primaryStage.sizeToScene();
			primaryStage.setTitle("Emotional Songs");
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception exp) {
			exp.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
