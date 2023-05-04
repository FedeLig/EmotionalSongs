/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * Classe astratta che viene eredita dai controller che gestiscono tabelle di tipo </>Song</> , 
 * permette di crearle da una lista e aggiornarle quando tale lista cambia 
 * @author Federico Ligas
 */

abstract class SongTableController extends Controller {
	
	/**
	 * </>tabellaCanzoni</> : tabella che contiene delle canzoni 
	 */
	@FXML private TableView<Song> tabellaCanzoni;
	/**
	 * </>songObservableList</> : lista delle canzoni contenute nella tabella 
	 */
	@FXML private  ObservableList<Song> songObservableList = FXCollections.observableArrayList();
	
	
	/**
	 * Permette di aggiornare la tabella ai nuovi valori di una lista di canzoni 
	 * @param listaCanzoni : lista di canzoni da mettere nella tabella 
	 */
	protected void UpdateTable(ObservableList<Song> listaCanzoni ) {
		
	 	Boolean PreviousTableIsNotEmpty = (!(getTabellaCanzoni().getItems()).isEmpty()) ;
	 	
		/*condizione :  il risultato della ricerca è un vettore vuoto ? */
		 if ( listaCanzoni.size() == 0 ) { 
			 
			 if(PreviousTableIsNotEmpty) {
				 
				    // togliamo i valori che erano presenti
			 		getTabellaCanzoni().getItems().clear();
			 	    // togliamo le colonne che erano presenti 
			 		getTabellaCanzoni().getColumns().clear(); 	 
			 }
			 
		 	 getTabellaCanzoni().setPlaceholder(new Label("Nessuna canzone presente"));
		 
		 }
		 else  {
			 
			/* converte ArrayList in una ObservableList : questa  permette ai listener di tenere traccia dei cambiamenti */
	        ObservableList<Song> data =  FXCollections.observableArrayList(listaCanzoni);
		    
		    /* se la tabella precedente non era vuota basterà aggiornare i valori  
		     * altrimenti sarà necessario crearne una nuova */
		    if(PreviousTableIsNotEmpty) {
	 	
		    	// togliamo i valori che erano presenti
		 		getTabellaCanzoni().getItems().clear();
		 	    // dice alla tabella da quali lista prendere i dati 
		 		getTabellaCanzoni().setItems(data); 
		 		// aggiorna la tabella usando la lista di dati più recente
		 		getTabellaCanzoni().refresh();
		 		
		 		
		    }
		    else { 
		    	
		    	// dice alla tabella da quali lista prendere i dati 
		    	getTabellaCanzoni().setItems(data); 
		    	// crea una nuova tabella usando la lista di dati più recente
		    	createTable();
		    	
		    	
		    }
		     
		 } 
		 
	}	
	/**
	 * Crea una tabella di tipo </>Song</> 
	 */
	private void createTable() {
		
		/* sommariamente : 
	     * setCellValueFactory : specifica come popolare tutte le celle della colonna che la chiama  
		 * PropertyValueFactory : cerca nella 1° classe indicata nel tipo parametro il metodo 
		 * che ritorna un campo il cui nome è indicato nelle parantesi( nel nostro caso troverà il getter ) 
		 * che poi sarà usato per trovare i valori contenuti nelle singole celle della colonna  */ 
    	
		addHyperlinkToTable();
		
		TableColumn<Song,String> nameColumn = new TableColumn<>("nome");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("name"));
		getTabellaCanzoni().getColumns().add(nameColumn);
		
		TableColumn<Song,String> authorColumn = new TableColumn<>("autore");
		authorColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("author"));
		getTabellaCanzoni().getColumns().add(authorColumn);
		
		TableColumn<Song,String> albumColumn = new TableColumn<>("album");
		albumColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("album"));
		getTabellaCanzoni().getColumns().add(albumColumn);
		
		TableColumn<Song,Integer> yearColumn = new TableColumn<>("anno");
		yearColumn.setCellValueFactory(new PropertyValueFactory<Song,Integer>("year"));
		getTabellaCanzoni().getColumns().add(yearColumn);
		
		TableColumn<Song,Integer> durationColumn = new TableColumn<>("durata");
		durationColumn.setCellValueFactory(new PropertyValueFactory<Song,Integer>("duration"));
		getTabellaCanzoni().getColumns().add(durationColumn);
         
	}

	/* parafrasando dalla documentazione della classe Cell : 
	 * di deafult le tabelle non contengono componenti grafiche ma si limitano a convertire in formato testuale i dati di una lista e a disporli nella tabella 
	 * secondo un modello assegnato. Però è possibile inserire in una cella un qualsiasi nodo e renderlo interattivo : per fare questo 
       è necessario specializzare le celle che useremo ridefinendo l'implementazione della funzione di callback di 
       CellFactory , che è la funzione che viene chiamata quando dobbiamo creare una nuova cella 
       
       nota : Una funzione di callback o richiamo è una funzione che è passata come parametro in 
       un'altra funzione e solitamente è usata per gestire dei particolari eventi */
	/**
	 * aggiunge un link di testo per ogni riga della tabella 
	 */
	protected void addHyperlinkToTable() {
	    // usiamo la wrapper class di void per inizializzare colonna 
		
	    TableColumn<Song,Void> StatsColumn = new TableColumn<Song,Void>("");

	    Callback<TableColumn<Song,Void>, TableCell<Song,Void>> cellFactory = new Callback<TableColumn<Song,Void>, TableCell<Song,Void>>() {

	        @Override
	        public TableCell<Song,Void> call(final TableColumn<Song,Void> param) {

	            final TableCell<Song,Void> cell = new TableCell<Song,Void>(){

	                private final Hyperlink linkToStats = new Hyperlink(getTestoHyperLink());
	                
	                {
	                /* all' interno di questa funzione lambda metteremo un metodo 
	                * crea un dialog che mostra le statistiche della canzone */ 
	                    linkToStats.setOnAction((ActionEvent event) -> {
	                    	int indice  = getIndex();
	                    	try {
								onHyperLinkCliked(event,indice);
							} catch (IOException exp) {
								exp.printStackTrace();
							}
	                    	// serve in modo che un hyperlink clickato non rimanga sottolineato
	                    	linkToStats.setVisited(false);
	                    });
	                 }
	                
	                /* update item è un metodo che viene chiamato per ogni cella dopo
	                 *  che è stato determinato il tipo da inserire e colloca l'elemento 
	                    che la cella deve contenere  */
	                @Override
	                public void updateItem(Void item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (empty) {
	                        setGraphic(null);
	                    } else {
	                        setGraphic(linkToStats);
	                    }
	                }
	              };
	            
	            return cell;
	        }
	    };

	    // crea una CellValueFactory che contiene un hyperlink , per ogni riga della tabella 
	    StatsColumn.setCellFactory(cellFactory);

	    getTabellaCanzoni().getColumns().add(StatsColumn);
		
	}
	
	/**
	 * azione che il link di testo svolge quando viene clickato 
	 */
	abstract protected void  onHyperLinkCliked (ActionEvent e , int indice ) throws FileNotFoundException, IOException;
	/**
	 * testo contenuto dal link di testo della tabella 
	 */
	abstract protected String getTestoHyperLink() ;

	/**
	 * ritorna la lista di canzoni contenute nella tabella 
	 * @return lista di canzoni contenute nella tabella 
	 */
	public ObservableList<Song> getSongObservableList() {
		return songObservableList;
	}

	/**
	 * imposta la lista di canzoni contenute nella tabella
	 * @param lista di canzoni da mettere nella tabella 
	 */
	public void setSongObservableList(ObservableList<Song> songObservableList) {
		this.songObservableList = songObservableList;
	}
	/**
	 * ritorna la tabella delle canzoni 
	 * @return tabella delle canzoni 
	 */
	public TableView<Song> getTabellaCanzoni() {
		return tabellaCanzoni;
	}
	
	
}