/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.IOException;
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


/**
 * Classe che si occupa della gestione della scena descritta da InserisciCommento.fxml
 * , permette di inserire il commento per un 'emozione votata 
 * @author Federico Ligas
 */
public class InserisciCommentoController extends Controller implements Initializable {

	/**
	 * </>listaCommenti</> : c
	 */
	private String[] listaCommenti  ;
	/**
	 * </>indice</> : indice della riga selezionata nella tabella in cui si inseriscono le emozioni 
	 */
	private int indice ; 
	/**
	 * </>controllerPrecedente</> : controller della scena precedente 
	 */
	private InserisciEmozioniController controllerPrecedente ; 
	
	/**
	 * </>areaCommento</> : area di Testo che permette di inserire il commento
	 */
	@FXML 
	private TextArea areaCommento ; 
	/**
	 * </>caratteriRimasti</> : quanti caratteri possiamo ancora inserire nel commento
	 */
	@FXML
	private Label caratteriRimasti ; 
	
	/**
	 * costruttore di base 
	 * @param listaCommenti : indice della riga selezionata nella tabella in cui si inseriscono le emozioni 
	 * @param indice : indice della riga selezionata nella tabella in cui si inseriscono le emozioni 
	 * @param controllerPrecedente : controller della scena precedente 
	 */
	public InserisciCommentoController( String[] listaCommenti , int indice , InserisciEmozioniController controllerPrecedente ) {

		this.listaCommenti = listaCommenti ;
		this.indice = indice ; 
		this.controllerPrecedente = controllerPrecedente;
		
	}


     /**
	 * Viene chiamato (una e una sola volta) dal controller appena dopo la scena è stata "caricata" con successo 
     * e inizializza gli elementi che sono contenuti nella scena .
     * @param arg0 : Il path usato per risolvere i path relativi per l'oggetto "radice" o il valore null se il path non &egrave noto
     * @param arg1 : Le risorse usate per localizzare l'oggetto "radice" , o null se la radice non viene trovata 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		areaCommento.setText(listaCommenti[indice]);
		
		areaCommento.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 256 ? change : null));
		
		caratteriRimasti.textProperty().bind(new SimpleIntegerProperty(256).subtract(Bindings.length(areaCommento.textProperty())).asString());
		
	}
	/**
	 *  Salva il commento nella lista commenti se non è vuoto 
	 *  @param event : azione che scatena l'esecuzione del metodo
	 */
	public void salvaCommento(ActionEvent event ) throws IOException {
		
		String commentoInserito = areaCommento.getText() ; 
        String messaggio ; 
        
        if (commentoInserito.isBlank()) {
        	messaggio = "il commento non puo' essere vuoto" ; 
        	createAlert(messaggio);
        }
        else {
            messaggio = "Il commento e' stato salvato" ; 
		    listaCommenti[indice] = areaCommento.getText();
		    controllerPrecedente.setListaCommenti(listaCommenti);
		    createAlert(messaggio);
        }
		
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