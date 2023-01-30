package application;

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

abstract class SearchSongTableController extends SongTableController {
 
	@FXML
	private TabPane tabpane ; 
	@FXML 
	private Tab firstTab, secondTab ; 
	@FXML 
	private TextField  titleField ,authorField , yearField ; 
	
	@FXML
	protected ChoiceBox<String> sceltaFiltro = new ChoiceBox<String>();
	//filtroBrano ci permette di distinguere il metodo di ricerca che si sta usando
	//Se filtroBrano == false allora si sta usando la ricerca per anno e autore. 
	private int tipoRicerca = 1;
	@FXML 
	private Label filtroSelezionato ;
	
	@FXML
	protected Button goBackButton; 
	
    protected static ArrayList<String> listaOpzioni ;
    
	private static String PreviousOption = "  titolo" ;  
	
    // ----------  CONTROLLI PER SCELTA FILTRO  ----------------
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		listaOpzioni = new ArrayList<String>(Arrays.asList("  titolo","  autore e anno"));
		
		sceltaFiltro.setItems(FXCollections.observableArrayList(listaOpzioni));
		 
		sceltaFiltro.setValue("");
		
		sceltaFiltro.setOnAction(event -> { ChangeFilter(); });
		
		getTabellaCanzoni().setPlaceholder(new Label("La tabella è vuota"));
		
	}
	
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
