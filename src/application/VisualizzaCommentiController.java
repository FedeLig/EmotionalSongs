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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class VisualizzaCommentiController extends Controller implements Initializable {

	private Song canzoneSelezionata ; 
	private ObservableList<VisualizzaEmozioniDati> listaEmozioni;
	private ArrayList<String> listaCommenti ;
	private SimpleIntegerProperty pos = new SimpleIntegerProperty(0);
	private String emozione ; 
   
	@FXML
	private TextArea areaCommenti ; 
	@FXML   
	private Button commentoPrecedente; 
	@FXML   
	private Button commentoSuccessivo; 
	@FXML
	private Label utenteLabel , emozioneEVotoLabel;
	
    public VisualizzaCommentiController(Song canzoneSelezionata,ObservableList<VisualizzaEmozioniDati> listaEmozioni, int indice ) throws FileNotFoundException, IOException {
		
		this.canzoneSelezionata = canzoneSelezionata; 
		this.listaEmozioni = listaEmozioni ;
		this.listaCommenti = listaEmozioni.get(indice).getListaCommenti() ;  
		emozione = Emozioni.values()[indice].getNome() ; 
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		BooleanBinding condizione1 = pos.isEqualTo(listaCommenti.size()-1);
		commentoSuccessivo.disableProperty().bind(condizione1);
		
		BooleanBinding condizione2 = pos.isEqualTo(0);
		commentoPrecedente.disableProperty().bind(condizione2);
		
		String[] dati = new String[2] ; 
		dati = listaCommenti.get((pos.intValue())).split(",,") ; 
		utenteLabel.setText("Utente : " + dati[0]);
		emozioneEVotoLabel.setText("Emozione : " + emozione + "  Voto : " + dati[1]);
		areaCommenti.setText(dati[2]);
		
	}
	
	public void nextComment(ActionEvent e ) {
		
        pos.set(pos.intValue()+1);
        String[] dati = new String[2] ; 
		dati = listaCommenti.get((pos.intValue())).split(",,") ; 
		utenteLabel.setText("Utente : " + dati[0]);
		emozioneEVotoLabel.setText("Emozione : " + emozione + "  Voto : " + dati[1]);
		areaCommenti.setText(dati[2]);
		
	}
	
    public void previousComment(ActionEvent e ) {
		
    	pos.set(pos.intValue()-1);
    	String[] dati = new String[2] ; 
		dati = listaCommenti.get((pos.intValue())).split(",,") ; 
		utenteLabel.setText("Utente : " + dati[0]);
		emozioneEVotoLabel.setText("Emozione : " + emozione + "  Voto : " + dati[1]);
		areaCommenti.setText(dati[2]);
		
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
