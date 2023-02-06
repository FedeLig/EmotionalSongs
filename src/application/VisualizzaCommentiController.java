package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class VisualizzaCommentiController extends Controller implements Initializable {

	private Song canzoneSelezionata ; 
	private ObservableList<VisualizzaEmozioniDati> ListaEmozioni;
	private ArrayList<ArrayList<String>> listaCommenti ;
	private SimpleIntegerProperty pos = new SimpleIntegerProperty(0);
   
	@FXML
	private TextArea areaCommenti ; 
	@FXML   
	private Button commentoPrecedente; 
	@FXML   
	private Button commentoSuccessivo; 
	
    public VisualizzaCommentiController(Song canzoneSelezionata,ObservableList<VisualizzaEmozioniDati> listaEmozioni,ArrayList<ArrayList<String>>  listaCommenti ) throws FileNotFoundException, IOException {
		
		this.canzoneSelezionata = canzoneSelezionata; 
		this.ListaEmozioni = FXCollections.observableArrayList(listaEmozioni);
		//this.listaCommenti = Song.getEmotionsComment(canzoneSelezionata.getId()) ; 
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// cambiare condizione a pos != listaCommenti.get(indice).size() -1 
		BooleanBinding condizione1 = pos.isNotEqualTo(0);
		commentoSuccessivo.disableProperty().bind(condizione1);
		
		BooleanBinding condizione2 = pos.isEqualTo(0);
		commentoPrecedente.disableProperty().bind(condizione2);
		StringBuilder commento = new StringBuilder();
		
		commento.append("\n");
		for(int i = 0 ; i<256 ; i++ ) { 
			commento.append("a"); 
		}
		
		areaCommenti.setText(commento.toString());
		
	}
	
	public void nextComment(ActionEvent e ) {
		
        StringBuilder commento = new StringBuilder();
		
        commento.append("\n");
		for(int i = 0 ; i<256 ; i++ ) { 
			commento.append("b"); 
		}
		areaCommenti.setText(commento.toString());
		
		pos.set(1);
		
	}
	
    public void previousComment(ActionEvent e ) {
		
        StringBuilder commento= new StringBuilder();
		
        commento.append("\n");
		for(int i = 0 ; i<256 ; i++ ) { 
			commento.append("a"); 
		}
		areaCommenti.setText(commento.toString());
		pos.set(0);
		
	}

	public void switchToVisualizzaEmozioni(ActionEvent e ) throws IOException {
		
		//
	}
	
}
