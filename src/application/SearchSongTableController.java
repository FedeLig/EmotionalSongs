package application;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

abstract class SearchSongTableController extends SongTableController {
 
	@FXML
	private TabPane tabpane ; 
	@FXML 
	private Tab firstTab, secondTab ; 
	@FXML 
	protected Button searchButton ; 
	
	@FXML
	protected ChoiceBox<String> sceltaFiltro = new ChoiceBox<String>();
	@FXML 
	private Label filtroSelezionato ; 
	
	@FXML
	protected Button goBackButton; 
	
    protected static ArrayList<String> listaOpzioni ;
    
	private static String PreviousOption = "  titolo" ;  
	
    // ----------  CONTROLLI PER SCELTA FILTRO  ----------------
    
    protected void ChangeFilter() {
    	
    	sceltaFiltro.setValue("");
    	SingleSelectionModel<Tab> selectionModel  = tabpane.getSelectionModel();
    	
    	// nota : se non abbiamo selezionato il titolo , abbiamo selezionato autore e anno 
        if ((sceltaFiltro.getSelectionModel().getSelectedItem()).equals("  titolo") ) {
    	    
        	/* se titolo NON era l'opzione selezionata , 
        	   dobbiamo cambiare gli elementi della GUI collegati alle opzioni  */
        	if ( !(PreviousOption.equals("  titolo")) ) {
        		
        		filtroSelezionato.setText("titolo");
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
            	selectionModel.select(1);
            	firstTab.setDisable(true);
			    secondTab.setDisable(false);
			    PreviousOption  = "  autore e anno";
        	}
        	
        }
    	
    	
    }

}
