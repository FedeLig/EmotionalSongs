/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;


/**
 * Classe astratta ereditata dai Controller che permettono di cercare canzoni,
 * contiene degli elementi comuni a questi tipi di interfacce di ricerca 
 * @author Federico Ligas
 */
abstract class SearchSongTableController extends SongTableController {
 
	/**
     * </>tabpane </> : struttura che ospita delle scene 
	  */
	@FXML
	private TabPane tabpane ; 
	/**
     * </>firstTab</> : prima scena contenuta ,</>secondTab</> : seconda scena contenuta 
	  */
	@FXML 
	private Tab firstTab, secondTab ; 
	/**
     * </>titleField</> : area di testo per il titolo della canzone ,</>authorField</> : area di testo per il nome dell'autore ,</>yearField</> :  area di testo per l'anno della canzone
	  */
	@FXML 
	private TextField  titleField ,authorField , yearField ; 
	/**
     * </>sceltaFiltro</> : menu di scelta del filtro 
	  */
	@FXML
	protected ChoiceBox<String> sceltaFiltro = new ChoiceBox<String>();
	/**
     * </>tipoRicerca</> : indica il tipo di ricerca selezionata 
	  */
	private int tipoRicerca = 1;
	/**
     * </>filtroSelezionato</> : indica il tipo di filtro selezionato per la ricerca 
	  */
	@FXML 
	private Label filtroSelezionato ;
	/**
     * </>goBackButton</> : buttone per tornare indietro alla tabella precedente , </>searchButton</> : buttone per cercare 
	  */
	@FXML
	protected Button goBackButton , searchButton ;
	/**
     * </>listaOpzioni</> : lista delle opzioni possibile per la ricerca 
	 */
    protected static ArrayList<String> listaOpzioni ;
    /**
     * </>PreviousOption</> : opzione scelta precedentemente 
	 */
	private static String PreviousOption = "  titolo" ;  
	
    // ----------  CONTROLLI PER SCELTA FILTRO  ----------------
    
	/**
	 * Viene chiamato (una e una sola volta) dal controller appena dopo la scena è stata "caricata" con successo 
     * e inizializza gli elementi che sono contenuti nella scena .
     * @param arg0 : Il path usato per risolvere i path relativi per l'oggetto "radice" o il valore null se il path non &egrave noto
     * @param arg1 : Le risorse usate per localizzare l'oggetto "radice" , o null se la radice non viene trovata 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		listaOpzioni = new ArrayList<String>(Arrays.asList("  titolo","  autore e anno"));
		
		sceltaFiltro.setItems(FXCollections.observableArrayList(listaOpzioni));
		 
		sceltaFiltro.setValue("");
		
		sceltaFiltro.setOnAction(event -> { ChangeFilter(); });
		
		getTabellaCanzoni().setPlaceholder(new Label("La tabella è vuota"));
		
		yearField.setTextFormatter(new TextFormatter <> (change -> change.getControlNewText().matches("^[0-9]{0,4}") ? change : null));
		
	}
	/**
	 * permette di effettuare la ricerca di un brano in diverse modalità
	 * @param e: evento scatenante
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public void cercaBranoMusicale(ActionEvent e ) throws NumberFormatException, IOException {
		
		String[] input = new String[2] ; 
		
		if( tipoRicerca == 1 & !(titleField.getText().isEmpty())) {
			
			input[0] = titleField.getText();
		}
		else {
			
		   if ( tipoRicerca == 2 & !(authorField.getText().isEmpty()) & !(yearField.getText().isEmpty())){
			   
			input[0] = authorField.getText();
			input[1] = yearField.getText(); 
			
		   }
		}
		
		if(input[0] != null) {
		   setSongObservableList(FXCollections.observableList(Song.searchSong(tipoRicerca, input)));
		   UpdateTable(getSongObservableList()); 
		}
		
	}
	
    private void ChangeFilter() {
    	
    	sceltaFiltro.setValue("");
    	SingleSelectionModel<Tab> selectionModel  = tabpane.getSelectionModel();
    	
    	// nota : se non abbiamo selezionato il titolo , abbiamo selezionato autore e anno 
        if ((sceltaFiltro.getSelectionModel().getSelectedItem()).equals("  titolo") ) {
    	    
        	/* se titolo NON era l'opzione selezionata , 
        	   dobbiamo cambiare gli elementi della GUI collegati alle opzioni  */
        	if ( !(PreviousOption.equals("  titolo")) ) {
        		
        		filtroSelezionato.setText("titolo");
        		tipoRicerca = 1;
        		// cambiamo tab 
        		selectionModel.select(0);
        		firstTab.setDisable(false);
			    secondTab.setDisable(true);
			    // nota : l'opzione scelta sarà quella precedente nella prossima esecuzione del metodo
			    PreviousOption  = "  titolo"; 
        		
        	}
    		
    	}
        else {
        	
        	if ( !(PreviousOption.equals("  autore e anno")) ) {
        		
            	filtroSelezionato.setText("autore e anno");
            	tipoRicerca = 2;
            	selectionModel.select(1);
            	firstTab.setDisable(true);
			    secondTab.setDisable(false);
			    PreviousOption  = "  autore e anno";
        	}
        	
        }
    	
    	
    }
    
    public String getFiltroSelezionato() {
    	return filtroSelezionato.getText();
    }

}
