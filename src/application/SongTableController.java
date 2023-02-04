package application;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

abstract class SongTableController extends Controller implements Initializable{
	
	@FXML
	private TableView<Song> tabellaCanzoni;
	
	@FXML
	private  ObservableList<Song> songObservableList = FXCollections.observableArrayList();
	

	// ---------- INIZIO CONTROLLI PER MANIPOLARE TABELLA ----------------
	
	/* il metodo seguente prende come parametro il vettore risultato della ricerca nella repository
	 * e lo trasforma in una tabella dove ogni riga corrisponde ai dati di una canzone .
	 * L'Ultima colonna di ogni riga contiene un Hyperlink : */
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
		    	createTableFromList(data);
		    	
		    	
		    }
		     
		 } 
		 
	}	
	
	private void createTableFromList( ObservableList<Song> list ) {
		
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
         
		

	    /* nota : la scelta di aggiungere le colonne singoralmente è preferibile 
		    *  rispetto all' uso di addAll(colonna1 , ... colonnaN ) in quando questa
		    *  comporta l'uso di Array e Generics che il compilatore segnala con un warning
		    *  ( l'uso combinato di queste non è considerata una buona pratica )   */
	}

	/* parafrasando dalla documentazione della classe Cell : 
	 * di deafult le tabelle non contengono componenti grafiche ma si limitano a convertire in formato testuale i dati di una lista e a disporli nella tabella 
	 * secondo un modello assegnato. Però è possibile inserire in una cella un qualsiasi nodo e renderlo interattivo : per fare questo 
       è necessario specializzare le celle che useremo ridefinendo l'implementazione della funzione di callback di 
       CellFactory , che è la funzione che viene chiamata quando dobbiamo creare una nuova cella 
       
       nota : Una funzione di callback o richiamo è una funzione che è passata come parametro in 
       un'altra funzione e solitamente è usata per gestire dei particolari eventi */
	
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
	                    linkToStats.setOnAction((ActionEvent e) -> {
	                    	int indice ; 
	                    	getTableView().getItems().get(indice = getIndex());
	                    	try {
								onHyperLinkCliked(e,indice);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
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
	
	
	abstract protected void  onHyperLinkCliked (ActionEvent e , int indice ) throws FileNotFoundException, IOException;
	
	abstract protected String getTestoHyperLink() ;

	public ObservableList<Song> getSongObservableList() {
		return songObservableList;
	}

	public void setSongObservableList(ObservableList<Song> songObservableList) {
		this.songObservableList = songObservableList;
	}

	public TableView<Song> getTabellaCanzoni() {
		return tabellaCanzoni;
	}

	public void setTabellaCanzoni(TableView<Song> tabellaCanzoni) {
		this.tabellaCanzoni = tabellaCanzoni;
	}
	
}