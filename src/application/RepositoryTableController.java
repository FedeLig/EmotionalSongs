package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class RepositoryTableController implements Initializable{
	
	@FXML
	public TableView<Song> tabellaMenuIniziale ;
	
	private Stage stage; 
	private Parent root;

	/* il  metodo Initialize viene chiamato (una e una sola volta)
       dal controller appena dopo la finestra è stata "caricata" con successo 
       e contiene la tabella che vogliamo visualizzare la prima volta  */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// il placeholder è la scritta che vediamo quando la tabella è vuota 
		tabellaMenuIniziale.setPlaceholder(new Label("La tabella è vuota"));
		
	}
	
	/* il metodo seguente prende come parametro il vettore risultato della ricerca nella repository
	 * e lo trasforma in una tabella dove ogni riga corrisponde ai dati di una canzone .
	 * L'Ultima colonna di ogni riga contiene un Hyperlink : 
	 * *DA IMPLEMENTARE* esso permette di visualizzare  le statistiche della canzone corrispondente  */
	
	public void UpdateTable(ActionEvent e ) throws NumberFormatException, IOException{
		
		ArrayList<Song> risultatiRicerca = new ArrayList<Song>();
		
        risultatiRicerca = Song.searchSong("testify");  
		
	 	Boolean PreviousTableIsNotEmpty = (!(tabellaMenuIniziale.getItems()).isEmpty()) ;
	 	
		/*condizione :  il risultato della ricerca è un vettore vuoto ? */
		 if ( risultatiRicerca.size() == 0 ) { 
			 
			 if(PreviousTableIsNotEmpty) {
				 
				    // togliamo i valori che erano presenti
			 		tabellaMenuIniziale.getItems().clear();
			 	    // togliamo le colonne che erano presenti 
			 		tabellaMenuIniziale.getColumns().clear(); 	 
			 }
			 
			 tabellaMenuIniziale.setPlaceholder(new Label("Nessuna canzone corrisponde ai criteri di ricerca"));
		 
		 }
		 else  {
			 
			/* converte ArrayList in una ObservableList : questa  permette ai listener di tenere traccia dei cambiamenti */
	        ObservableList<Song> data =  FXCollections.observableArrayList(risultatiRicerca);
		    
		    /* se la tabella precedente non era vuota basterà aggiornare i valori  
		     * altrimenti sarà necessario crearne una nuova */
		    if(PreviousTableIsNotEmpty) {
	 	
		    	// togliamo i valori che erano presenti
		 		tabellaMenuIniziale.getItems().clear();
		 	    // dice alla tabella da quali lista prendere i dati 
		 		tabellaMenuIniziale.setItems(data); 
		 		// aggiorna la tabella usando la lista di dati più recente
		 		tabellaMenuIniziale.refresh();
		 		
		 		
		    }
		    else { 
		    	
		    	// dice alla tabella da quali lista prendere i dati 
		    	tabellaMenuIniziale.setItems(data); 
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
    	
		TableColumn<Song,String> nameColumn = new TableColumn<>("nome");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("name"));
		tabellaMenuIniziale.getColumns().add(nameColumn);
		
		TableColumn<Song,String> authorColumn = new TableColumn<>("autore");
		authorColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("author"));
		tabellaMenuIniziale.getColumns().add(authorColumn);
		
		TableColumn<Song,String> albumColumn = new TableColumn<>("album");
		albumColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("album"));
		tabellaMenuIniziale.getColumns().add(albumColumn);
		
		TableColumn<Song,Integer> yearColumn = new TableColumn<>("anno");
		yearColumn.setCellValueFactory(new PropertyValueFactory<Song,Integer>("year"));
		tabellaMenuIniziale.getColumns().add(yearColumn);
		
		TableColumn<Song,Integer> durationColumn = new TableColumn<>("durata");
		durationColumn.setCellValueFactory(new PropertyValueFactory<Song,Integer>("duration"));
		tabellaMenuIniziale.getColumns().add(durationColumn);
	   
	    addHyperlinkToTable();
	    
	    /* nota : la scelta di aggiungere le colonne singoralmente è preferibile 
		    *  rispetto all' uso di addAll(colonna1 , ... colonnaN ) in quando questa
		    *  comporta l'uso di Array e Generics che il compilatore segnala con un warning
		    *  ( l'uso combinato di queste non è considerata una buona pratica )   */
	}
	
	private void addHyperlinkToTable() {
        
		/* parafrasando dalla documentazione della classe Cell : 
		 * di deafult le tabelle non contengono componenti grafiche ma si limitano a convertire in formato testuale i dati di una lista e a disporli nella tabella 
		 * secondo un modello assegnato. Però è possibile inserire in una cella un qualsiasi nodo e renderlo interattivo : per fare questo 
	       è necessario specializzare le celle che useremo ridefinendo l'implementazione della funzione di callback di 
	       CellFactory , che è la funzione che viene chiamata quando dobbiamo creare una nuova cella 
	       
           nota : Una funzione di callback o richiamo è una funzione che è passata come parametro in 
           un'altra funzione e solitamente è usata per gestire dei particolari eventi */
        
        // usiamo la wrapper class di void per inizializzare colonna 
		
        TableColumn<Song,Void> StatsColumn = new TableColumn<Song,Void>("StatsColumn");

        Callback<TableColumn<Song,Void>, TableCell<Song,Void>> cellFactory = new Callback<TableColumn<Song,Void>, TableCell<Song,Void>>() {

            @Override
            public TableCell<Song,Void> call(final TableColumn<Song,Void> param) {

                final TableCell<Song,Void> cell = new TableCell<Song,Void>(){

                    private final Hyperlink linkToStats = new Hyperlink("visualizza statistiche");
                    
                    {
                    /* all' interno di questa funzione lambda metteremo un metodo 
                    * crea un dialog che mostra le statistiche della canzone */ 
                        linkToStats.setOnAction((ActionEvent e) -> {
                            System.out.println("funziona");
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

        tabellaMenuIniziale.getColumns().add(StatsColumn);

    }
    
    public void switchToLogin(ActionEvent e ) throws IOException {
		
	    SwitchTo("/Login.fxml","login.css",e);
		
	}
    
    //metodo per il cambio di scena	
  	private void SwitchTo(String FileName,String cssname, ActionEvent e ) throws IOException{
  		
  		root = FXMLLoader.load(getClass().getResource(FileName));
  	    stage = (Stage)((Node)e.getSource()).getScene().getWindow();
  	    Scene scene = new Scene(root);
  	    String css = this.getClass().getResource(cssname).toExternalForm();
  	    scene.getStylesheets().add(css);
  	    
  	    
  	    stage.setResizable(false);
  		stage.sizeToScene();
  		stage.setTitle("Emotional Songs");
  	    stage.setScene(scene);
  	    stage.show();
  		
  	}
}