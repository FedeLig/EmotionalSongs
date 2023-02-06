package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *  Questa classe gestisce le interazione dell' utente con l'interfaccia definita da StatisticheCanzone.fxml .
 *  Permette all'utente di visualizzare le informazioni relative di una canzone che esso ha selezionato 
 *  , di vedere un prospetto riassuntivo delle emozioni che sono state associate alla canzone dagli utenti 
 *  e di accedere ad eventuali commenti che gli utenti hanno lasciato riguardo un emozione valutata 
 *  @see application.StatisticheCanzone.fxml 
 *  @author Ligas
 */
public class VisualizzaEmozioniController extends Controller implements Initializable {

	@FXML 
	private TableView<VisualizzaEmozioniDati> tabellaEmozioni ; 
	@FXML
	private TextArea infoCanzone ; 
	@FXML
	private Label commentHeader ; 
	
	private Song canzoneSelezionata ; 
	private ObservableList<VisualizzaEmozioniDati> listaEmozioni;
	private Login utente ; 
	private int iterazione = 0 ; 
	private int riga  = 0 ; 
	
	private boolean EsistonoEmozioniAssociate = false; 
	
	public VisualizzaEmozioniController(Login utente, Song canzoneSelezionata,ObservableList<VisualizzaEmozioniDati> listaEmozioni ) {
		
		this.utente = utente ; 
		this.canzoneSelezionata = canzoneSelezionata; 
		this.listaEmozioni = listaEmozioni ; 
		
		for(int i = 0 ; i < listaEmozioni.size() && !EsistonoEmozioniAssociate ; i++)
		   if ( listaEmozioni.get(i).getNumeUtentiAssociati()!= 0)
		        EsistonoEmozioniAssociate = true ;  
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// prendiamo i dati  della canzone 
		String info = "Titolo   :  "
				     + canzoneSelezionata.getName()
				     + "   Autore  :  "
				     + canzoneSelezionata.getAuthor() + "\n\n"
				     + "Album  :  "
				     + canzoneSelezionata.getAlbum()
				     + "   Anno     :  "
				     + canzoneSelezionata.getYear()
				     + "   Durata   :  "
				     + canzoneSelezionata.getDuration() ; 
		
		infoCanzone.setText(info);
		
		// condizione : listaEmozioni.size() != 0 
		if ( EsistonoEmozioniAssociate ) {
			
			tabellaEmozioni.setItems(listaEmozioni);
			createTable();
			
		}else {
		    tabellaEmozioni.setPlaceholder(new Label("Nessuna emozione associata"));
		}
		
	}
	
	public void switchToRicercaInRepository(ActionEvent event ) throws IOException {
		
		String indirizzoTabella = utente == null ? "/MenuIniziale.fxml" : "/MenuUtente.fxml"  ; 
		RicercaInRepositoryController controller = new RicercaInRepositoryController(utente,indirizzoTabella);
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/RicercaInRepository.fxml"));
		fxmlloader.setController(controller);
		setRoot(fxmlloader.load());
		changeScene(event);
		
	}
	
	/**
	 *  Chiude la finestra che contiene l'interfaccia grafica che la classe gestisce 
	 *  e ci riporta all' interfaccia grafica precedente 
	 */
	public void createTable() {
		
		TableColumn<VisualizzaEmozioniDati,String> nameColumn = new TableColumn<>("nome");
		nameColumn.setCellValueFactory(new PropertyValueFactory<VisualizzaEmozioniDati,String>("nome"));
		tabellaEmozioni.getColumns().add(nameColumn);
		
		TableColumn<VisualizzaEmozioniDati,String> descriptionColumn = new TableColumn<>("descrizione");
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<VisualizzaEmozioniDati,String>("descrizione"));
		tabellaEmozioni.getColumns().add(descriptionColumn);
		
		TableColumn<VisualizzaEmozioniDati,Integer> usersNumberColumn = new TableColumn<>("numero utenti associati");
		usersNumberColumn.setCellValueFactory(new PropertyValueFactory<VisualizzaEmozioniDati,Integer>("numeUtentiAssociati"));
		// la larghezza di default non renderebbe visibile totalmente il nome della colonna 
		usersNumberColumn.setMinWidth(140);
		tabellaEmozioni.getColumns().add(usersNumberColumn);
		
		TableColumn<VisualizzaEmozioniDati,Float> ratingAvgColumn = new TableColumn<>("media voti");
		ratingAvgColumn.setCellValueFactory(new PropertyValueFactory<VisualizzaEmozioniDati,Float>("mediaVoti"));
		tabellaEmozioni.getColumns().add(ratingAvgColumn);
		
		addHyperlinkToTable();
		
	}
	
	private void addHyperlinkToTable() {
	    // usiamo la wrapper class di void per inizializzare colonna 
		
	    TableColumn<VisualizzaEmozioniDati, Void> StatsColumn = new TableColumn<VisualizzaEmozioniDati, Void>("");

	    Callback<TableColumn<VisualizzaEmozioniDati, Void>, TableCell<VisualizzaEmozioniDati, Void>> cellFactory = new Callback<TableColumn<VisualizzaEmozioniDati, Void>, TableCell<VisualizzaEmozioniDati, Void>>() {

	        @Override
	        public TableCell<VisualizzaEmozioniDati, Void> call(final TableColumn<VisualizzaEmozioniDati, Void> param) {

	            final TableCell<VisualizzaEmozioniDati, Void> cell = new TableCell<VisualizzaEmozioniDati, Void>(){

	                private final Hyperlink linkToStats = new Hyperlink("commenti");
	                
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
	                    
	                    iterazione++;
	                    if(iterazione>3 && iterazione < 12) {
	                       riga++;
	                    }
	                
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
	                    	
	                    	if ( getTableView().getItems().get(getIndex()).getNumeUtentiAssociati() != 0 && listaEmozioni.get(riga).getListaCommenti().size() != 0 ) 
	                             setGraphic(linkToStats);
	                    }
	                }
	              };
	            
	            return cell;
	        }
	    };

	    // crea una CellValueFactory che contiene un hyperlink , per ogni riga della tabella 
	    StatsColumn.setCellFactory(cellFactory);

	    tabellaEmozioni.getColumns().add(StatsColumn);
		
	}

	 private void  onHyperLinkCliked (ActionEvent e, int indice ) throws IOException {
	     
		    FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/VisualizzaCommenti.fxml"));
			Parent parent = null;
			
			VisualizzaCommentiController controller = new VisualizzaCommentiController(canzoneSelezionata,listaEmozioni,indice);
			fxmlloader.setController(controller);
			try {
				parent = fxmlloader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Scene dialog = new Scene(parent);
	        Stage stage = new Stage();
	        
	        stage.setTitle("Emotional Songs");
	        stage.setResizable(false);
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setScene(dialog);
	        stage.showAndWait();
         
	}
}