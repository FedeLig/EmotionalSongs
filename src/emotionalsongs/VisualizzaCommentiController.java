/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Classe che serve a gestire la scena di visualizzazione dei commenti
 * @author Edoardo Picazio
 *
 */
public class VisualizzaCommentiController extends Controller implements Initializable {
   
	@FXML private TextArea areaCommenti ; 
	
	@FXML  private Button commentoPrecedente, commentoSuccessivo; 
	
	@FXML private Label utenteLabel , emozioneEVotoLabel;
	
	private ObservableList<VisualizzaEmozioniDati> listaEmozioni;
	
	private ArrayList<String> listaCommenti ;
	
	private SimpleIntegerProperty pos = new SimpleIntegerProperty(0);
	
	private String emozione ; 
	
    public VisualizzaCommentiController(ObservableList<VisualizzaEmozioniDati> listaEmozioni, int indice ) throws FileNotFoundException, IOException {
		
		this.listaEmozioni = listaEmozioni ;
		this.listaCommenti = listaEmozioni.get(indice).getListaCommenti() ;  
		emozione = Emozioni.values()[indice].getNome() ; 
		
	}
    /**
	 * Viene chiamato (una e una sola volta) dal controller appena dopo la scena è stata "caricata" con successo 
     * e inizializza gli elementi che sono contenuti nella scena .
     * @param arg0 : Il path usato per risolvere i path relativi per l'oggetto "radice" o il valore null se il path non &egrave noto
     * @param arg1 : Le risorse usate per localizzare l'oggetto "radice" , o null se la radice non viene trovata 
	 */
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
	/**
	 * mostra il commento successivo
	 * @param e
	 */
	public void nextComment(ActionEvent event ) {
		
        pos.set(pos.intValue()+1);
        String[] dati = new String[2] ; 
		dati = listaCommenti.get((pos.intValue())).split(",,") ; 
		utenteLabel.setText("Utente : " + dati[0]);
		emozioneEVotoLabel.setText("Emozione : " + emozione + "  Voto : " + dati[1]);
		areaCommenti.setText(dati[2]);
		
	}
	/**
	 * mostra il commento precedente
	 */
    public void previousComment(ActionEvent event ) {
		
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
