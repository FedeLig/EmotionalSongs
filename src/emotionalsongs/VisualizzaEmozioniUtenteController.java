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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * classe che serve a gestire la visualizzazione delle emozioni inserite dall'utente
 * @author kurapica
 *
 */
public class VisualizzaEmozioniUtenteController extends Controller implements Initializable {

	@FXML 
	private TableView<ArrayList<StringProperty>> tabellaEmozioni ; 
	
	private ObservableList<ArrayList<StringProperty>> datiTabella = FXCollections.observableArrayList();
	
	@FXML
	private TextArea infoCanzone ; 
	
	private Song canzoneSelezionata ; 
	private Login utente ; 
	private Playlist playlist ; 
	private int iterazione = 0 ; 
	private int riga  = 0 ; 
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
		
		for (int r = 0; r < 9 ; r++) {
	        ArrayList<StringProperty> row = new ArrayList<StringProperty>();
            
	        row.add(new SimpleStringProperty(Emozioni.getlistaEmozioni()[r].getNome()));
	        row.add(new SimpleStringProperty(Emozioni.getlistaEmozioni()[r].getDescrizione()));
	        row.add(new SimpleStringProperty(voti[r]));

	        datiTabella.add(row);
	    }
		
		tabellaEmozioni.setItems(datiTabella);
		
	}
	

	private void addHyperlinkToTable() {
	    // usiamo la wrapper class di void per inizializzare colonna 
		
	    TableColumn<ArrayList<StringProperty>, Void> commentColumn = new TableColumn<ArrayList<StringProperty>, Void>("");

	    Callback<TableColumn<ArrayList<StringProperty>, Void>, TableCell<ArrayList<StringProperty>, Void>> cellFactory = new Callback<TableColumn<ArrayList<StringProperty>, Void>, TableCell<ArrayList<StringProperty>, Void>>() {

	        @Override
	        public TableCell<ArrayList<StringProperty>, Void> call(final TableColumn<ArrayList<StringProperty>, Void> param) {

	            final TableCell<ArrayList<StringProperty>, Void> cell = new TableCell<ArrayList<StringProperty>, Void>(){

	                private final Hyperlink linkToComment= new Hyperlink("Commento inserito");
	                
	                {
	                /* all' interno di questa funzione lambda metteremo un metodo 
	                * crea un dialog che mostra le statistiche della canzone */ 
	                	linkToComment.setOnAction((ActionEvent e) -> {
	                    	int indice ; 
	                    	getTableView().getItems().get(indice = getIndex());
	                    	
							try {
								onHyperLinkCliked(e,indice);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							
	                    	// serve in modo che un hyperlink clickato non rimanga sottolineato
							linkToComment.setVisited(false);
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
	                    	if(!(commenti[riga].equals(" "))) 
	                          setGraphic(linkToComment);
	                    }
	                }};

	            return cell;
	        }
	    };

	    // crea una CellValueFactory che contiene un hyperlink , per ogni riga della tabella 
	    commentColumn.setCellFactory(cellFactory);

	    tabellaEmozioni.getColumns().add(commentColumn);
		
	}
	
	private void  onHyperLinkCliked (ActionEvent e, int indice ) throws IOException {
	     
	    FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/VisualizzaCommentoUtente.fxml"));
		Parent parent = null;
		
		VisualizzaCommentoUtenteController controller = new VisualizzaCommentoUtenteController(commenti[indice]);
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
	/**
	 * porta alla scena di selezione canzone
	 * @param e: evento scatenante
	 * @throws IOException
	 */
	public void switchToSelezionaCanzone(ActionEvent e ) throws IOException {
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/SelezionaCanzone.fxml"));
    	SelezionaCanzoneController controller = new SelezionaCanzoneController(utente,playlist);
    	fxmlloader.setController(controller);
		setRoot(fxmlloader.load());
		changeScene(e);
		
	}


}
