/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 *  Questa classe gestisce le interazione dell' utente con l'interfaccia definita da StatisticheCanzone.fxml .
 *  Permette all'utente di visualizzare le informazioni relative di una canzone che esso ha selezionato 
 *  , di vedere un prospetto riassuntivo delle emozioni che sono state associate alla canzone dagli utenti 
 *  e di accedere ad eventuali commenti che gli utenti hanno lasciato riguardo un emozione valutata 
 *  @author Federico Ligas
 */
public class VisualizzaEmozioniController extends Controller implements Initializable {

	@FXML private TabPane  tabpane ; 
	
	@FXML private Tab firstTab , secondTab ;
	
	@FXML private TableView<VisualizzaEmozioniDati> tabellaEmozioni ; 
	
	@FXML private TextArea infoCanzone , areaCommenti ; 
	
	@FXML private Label emozioneEVotoLabel ; 
	
	@FXML private Button backToPrevious ,  previousComment , nextComment , backToEmotions ; 
	
	private Song canzoneSelezionata ; 
	
	private ObservableList<VisualizzaEmozioniDati> listaEmozioni;
	
	private Login utente ; 
	
	private Playlist playlist ; 
	
	private boolean EsistonoEmozioniAssociate = false; 
	
	private ArrayList<String> listaCommenti; 
	
	private SimpleIntegerProperty indiceCommento ;
	
	private String emozione ; 
	
	public VisualizzaEmozioniController(Login utente, Playlist playlist, Song canzoneSelezionata,ObservableList<VisualizzaEmozioniDati> listaEmozioni ) {
		
		this.utente = utente ; 
		this.playlist = playlist ; 
		this.canzoneSelezionata = canzoneSelezionata; 
		this.listaEmozioni = listaEmozioni ; 
		
		for(int i = 0 ; i < listaEmozioni.size() && !EsistonoEmozioniAssociate ; i++)
		   if ( listaEmozioni.get(i).getNumeUtentiAssociati()!= 0)
		        EsistonoEmozioniAssociate = true ;  
	}
	
	/**
	 * Viene chiamato (una e una sola volta) dal controller appena dopo la scena è stata "caricata" con successo 
     * e inizializza gli elementi che sono contenuti nella scena .
     * @param arg0 : Il path usato per risolvere i path relativi per l'oggetto "radice" o il valore null se il path non &egrave noto
     * @param arg1 : Le risorse usate per localizzare l'oggetto "radice" , o null se la radice non viene trovata 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
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
		
		if ( EsistonoEmozioniAssociate ) {
			
			tabellaEmozioni.setItems(listaEmozioni);
			createTable();
			
		}else {
		    tabellaEmozioni.setPlaceholder(new Label("Nessuna emozione associata"));
		}
		
		backToPrevious.setOnAction(
				event -> { 
					if(playlist == null ) {
						
						String indirizzoTabella = utente == null ? "MenuIniziale.fxml" : "MenuUtente.fxml"  ; 
						switchTo(event,"RicercaInRepository.fxml",new RicercaInRepositoryController(utente,indirizzoTabella));
					}
					else 
						switchTo(event,"SelezionaCanzone.fxml",new SelezionaCanzoneController(utente,playlist,null));
					
				});
		
		
		backToEmotions.setOnAction(
				event -> {
				SingleSelectionModel<Tab> selectionModel  = tabpane.getSelectionModel();
				selectionModel.select(0);
				firstTab.setDisable(false);
			    secondTab.setDisable(true);	
			    
				});
		
		nextComment.setOnAction(
				event ->{
				indiceCommento.set(indiceCommento.intValue()+1);
		        String[] dati = new String[2] ; 
				dati = listaCommenti.get((indiceCommento.intValue())).split(",,") ; 
				emozioneEVotoLabel.setText("Emozione : " + emozione + "  Voto : " + dati[1]);
				areaCommenti.setText(dati[2]);	
					
				});
		
		
		
		previousComment.setOnAction(
				event -> {
				indiceCommento.set(indiceCommento.intValue()-1);
		    	String[] dati = new String[2] ; 
				dati = listaCommenti.get((indiceCommento.intValue())).split(",,") ; 
				emozioneEVotoLabel.setText("Emozione : " + emozione + "  Voto : " + dati[1]);
				areaCommenti.setText(dati[2]);	
					
				});
		
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
		
	    TableColumn<VisualizzaEmozioniDati, Void> CommentsColumn = new TableColumn<VisualizzaEmozioniDati, Void>("");

	    Callback<TableColumn<VisualizzaEmozioniDati, Void>, TableCell<VisualizzaEmozioniDati, Void>> cellFactory = new Callback<TableColumn<VisualizzaEmozioniDati, Void>, TableCell<VisualizzaEmozioniDati, Void>>() {

	        @Override
	        public TableCell<VisualizzaEmozioniDati, Void> call(final TableColumn<VisualizzaEmozioniDati, Void> param) {

	            final TableCell<VisualizzaEmozioniDati, Void> cell = new TableCell<VisualizzaEmozioniDati, Void>(){

	                private final Hyperlink linkToComments = new Hyperlink("commenti");
	                
	                {
	                	 
	                /* all' interno di questa funzione lambda metteremo un metodo 
	                * crea un dialog che mostra le statistiche della canzone */ 
	                	linkToComments.setOnAction((ActionEvent event) -> {
	                    	
	                    	int indice = getIndex();
							try {
								onHyperLinkCliked(event,indice);
							} catch (IOException exp) {
								exp.printStackTrace();
							}
							
	                    	// serve in modo che un hyperlink clickato non rimanga sottolineato
							linkToComments.setVisited(false);
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
	                    	
	                    	TableRow<VisualizzaEmozioniDati> row = getTableRow();
	                    	
	                    	if ( row != null && !(row.isEmpty())) {
	                    		
	                    		int indice = row.getIndex(); 
		                    	
		                    	if ( getTableView().getItems().get(indice).getNumeUtentiAssociati() != 0 && listaEmozioni.get(indice).getListaCommenti().size() != 0 ) 
		                             setGraphic(linkToComments);
		                   
	                    	}
	                    	
	                    	
	                    }
	                }
	              };
	            
	            return cell;
	        }
	    };

	    CommentsColumn.setCellFactory(cellFactory);
	    CommentsColumn.setMinWidth(150);

	    tabellaEmozioni.getColumns().add(CommentsColumn);
		
	}

	 private void  onHyperLinkCliked (ActionEvent event, int indice ) throws IOException {
	     
		 listaCommenti = listaEmozioni.get(indice).getListaCommenti() ;  
		 indiceCommento = new SimpleIntegerProperty(0);
		 
		 BooleanBinding condizione1 = indiceCommento.isEqualTo(listaCommenti.size()-1);
		 nextComment.disableProperty().bind(condizione1);
		 
		 BooleanBinding condizione2 = indiceCommento.isEqualTo(0);
		 previousComment.disableProperty().bind(condizione2);
		 
	     SingleSelectionModel<Tab> selectionModel  = tabpane.getSelectionModel();
		 selectionModel.select(1);
		 firstTab.setDisable(true);
		 secondTab.setDisable(false);
		 
		 String[] dati = new String[2] ; 
		 dati = listaCommenti.get((indiceCommento.intValue())).split(",,") ;
		 emozione = Emozioni.values()[indice].getNome() ; 
		 emozioneEVotoLabel.setText("Emozione : " + emozione + "  Voto : " + dati[1]);
		 areaCommenti.setText(dati[2]);
	}
}