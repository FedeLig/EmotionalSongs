/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.util.Callback;

/**
 * classe che serve a gestire la visualizzazione delle emozioni inserite dall'utente
 * @author Edoardo Picazio
 *
 */
public class VisualizzaEmozioniUtenteController extends Controller implements Initializable {

    @FXML private TabPane  tabpane ; 
	
	@FXML private Tab firstTab , secondTab ;
	
	@FXML  private TableView<ArrayList<StringProperty>> tabellaEmozioni ; 
	
	@FXML private TextArea infoCanzone , areaCommento ; 
	
	@FXML private Button backToSongs , backToEmotions ; 
	
	private Song canzoneSelezionata ; 
	
	private Login utente ; 
	
	private Playlist playlist ; 
	
	private String[] voti = new String[9] ; 
	
	private String[] commenti = new String[9] ; 
	
	public VisualizzaEmozioniUtenteController(Login utente , Song canzoneSelezionata , String dati, Playlist playlist ) {
		this.utente = utente ; 
		this.canzoneSelezionata  = canzoneSelezionata ; 
		String[] votiECommenti = new String[17];
		votiECommenti = dati.split(",,");
		for(int i = 0 ; i < votiECommenti.length ; i++) {
			if( i % 2 == 0)
			  voti[i/2]= votiECommenti[i];
			else
		      commenti[(i-1)/2] = votiECommenti[i] ;
		}
		this.playlist = playlist ; 
		
	}
	/**
	 * Viene chiamato (una e una sola volta) dal controller appena dopo la scena è stata "caricata" con successo 
     * e inizializza gli elementi che sono contenuti nella scena .
     * @param arg0 : Il path usato per risolvere i path relativi per l'oggetto "radice" o il valore null se il path non &egrave noto
     * @param arg1 : Le risorse usate per localizzare l'oggetto "radice" , o null se la radice non viene trovata 
	 */
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
		
		
		backToSongs.setOnAction(
				event -> switchTo(event,"SelezionaCanzone.fxml",new SelezionaCanzoneController(utente,playlist,null)) );
				
		backToEmotions.setOnAction(
				event -> { 
				SingleSelectionModel<Tab> selectionModel  = tabpane.getSelectionModel();
				selectionModel.select(0);
				firstTab.setDisable(false);
			    secondTab.setDisable(true);	
			    
				}); 
		
		createTable();
		
		tabellaEmozioni.setItems(getData());
		
	}
	
	private void createTable() {
		
		TableColumn<ArrayList<StringProperty>, String>  nameColumn = new TableColumn<>("Emozione");
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().get(0));
		tabellaEmozioni.getColumns().add(nameColumn);
		
		TableColumn<ArrayList<StringProperty>, String> descriptionColumn = new TableColumn<>("Descrizione");
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().get(1));
		tabellaEmozioni.getColumns().add(descriptionColumn);
		
		TableColumn<ArrayList<StringProperty>, String> ratingColumn = new TableColumn<>("voto");
		ratingColumn.setCellValueFactory(cellData -> cellData.getValue().get(2));
		tabellaEmozioni.getColumns().add(ratingColumn);
		
		addHyperlinkToTable();
	}
	
	private ObservableList<ArrayList<StringProperty>> getData(){
		
		ObservableList<ArrayList<StringProperty>> datiTabella = FXCollections.observableArrayList();
		
		for (int indice = 0; indice < 9 ; indice++) {
	        ArrayList<StringProperty> row = new ArrayList<StringProperty>();
            
	        row.add(new SimpleStringProperty(Emozioni.getlistaEmozioni()[indice].getNome()));
	        row.add(new SimpleStringProperty(Emozioni.getlistaEmozioni()[indice].getDescrizione()));
	        row.add(new SimpleStringProperty(voti[indice]));

	        datiTabella.add(row);
	    }
		
		return datiTabella;
		
	}

	private void addHyperlinkToTable() {
	    // usiamo la wrapper class di void per inizializzare colonna 
		
	    TableColumn<ArrayList<StringProperty>, Void> commentColumn = new TableColumn<ArrayList<StringProperty>, Void>("");

	    Callback<TableColumn<ArrayList<StringProperty>, Void>, TableCell<ArrayList<StringProperty>, Void>> cellFactory = new Callback<TableColumn<ArrayList<StringProperty>, Void>, TableCell<ArrayList<StringProperty>, Void>>() {

	        @Override
	        public TableCell<ArrayList<StringProperty>, Void> call(final TableColumn<ArrayList<StringProperty>, Void> param) {

	            final TableCell<ArrayList<StringProperty>, Void> cell = new TableCell<ArrayList<StringProperty>, Void>(){

	                private final Hyperlink linkToComment= new Hyperlink("commento");
	                
	                {
	                /* all' interno di questa funzione lambda metteremo un metodo 
	                * crea un dialog che mostra le statistiche della canzone */ 
	                	linkToComment.setOnAction((ActionEvent event) -> {
	                		
	                    	int  indice = getIndex();
	                    	
							try {
								onHyperLinkCliked(event,indice);
							} catch (IOException exp) {
								exp.printStackTrace();
							}
							
	                    	// serve in modo che un hyperlink clickato non rimanga sottolineato
							linkToComment.setVisited(false);
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
	                    	
                            TableRow<ArrayList<StringProperty>> row = getTableRow();
	                    	
	                    	if ( row != null && !(row.isEmpty())) {
	                    		
	                    		int indice = getIndex();
		                    	
		                    	if(!(commenti[indice].equals(" "))) 
			                          setGraphic(linkToComment);
		                   
	                    	}
	                    	
	                    }
	                }};

	            return cell;
	        }
	    };

	    // crea una CellValueFactory che contiene un hyperlink , per ogni riga della tabella 
	    commentColumn.setCellFactory(cellFactory);
	    commentColumn.setMinWidth(150);
	    
	    tabellaEmozioni.getColumns().add(commentColumn);
		
	}
	
	private void  onHyperLinkCliked (ActionEvent e, int indice ) throws IOException {
	    
		SingleSelectionModel<Tab> selectionModel  = tabpane.getSelectionModel();
		selectionModel.select(1);
		firstTab.setDisable(true);
		secondTab.setDisable(false);
		
		areaCommento.setText(commenti[indice]);
     
    }

}
