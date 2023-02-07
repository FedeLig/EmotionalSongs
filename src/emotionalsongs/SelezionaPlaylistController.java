/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class SelezionaPlaylistController extends Controller implements Initializable {

	@FXML
    private Button TornaAlMenu;

    @FXML
    private TableView<Playlist> tabellaPlaylist;
    
    @FXML
	protected  ObservableList<Playlist> UserPlaylists ;

    private Login utente; 
    /**
     * permette di impostare l'utente da usare per la ricerca delle playlist
     * @param utente
     */
    public SelezionaPlaylistController(Login utente) {
		this.utente = utente; 
		this.UserPlaylists = FXCollections.observableArrayList(utente.getUserPlaylists());
	}
    /**
	 * Viene chiamato (una e una sola volta) dal controller appena dopo la scena è stata "caricata" con successo 
     * e inizializza gli elementi che sono contenuti nella scena .
     * @param arg0 : Il path usato per risolvere i path relativi per l'oggetto "radice" o il valore null se il path non &egrave noto
     * @param arg1 : Le risorse usate per localizzare l'oggetto "radice" , o null se la radice non viene trovata 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	tabellaPlaylist.setPlaceholder(new Label("Nessuna playlist presente"));
    	
    	if(!( UserPlaylists.isEmpty() )) {
    		
    		populateTable(UserPlaylists);
    	}
		
	}
    /**
     * permette di popolare la tabella con delle playlist
     * @param list
     */
    public void populateTable (ObservableList<Playlist> list ) {
		
		TableColumn<Playlist,String> nameColumn = new TableColumn<>("nome");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Playlist,String>("nomePlaylist"));
		tabellaPlaylist.getColumns().add(nameColumn);
		
		addHyperlinkToTable();
		
    	tabellaPlaylist.setItems(list); 
    }
    
    private void addHyperlinkToTable() {
	    // usiamo la wrapper class di void per inizializzare colonna 
		
	    TableColumn<Playlist, Void> StatsColumn = new TableColumn<Playlist, Void>("");

	    Callback<TableColumn<Playlist, Void>, TableCell<Playlist, Void>> cellFactory = new Callback<TableColumn<Playlist, Void>, TableCell<Playlist, Void>>() {

	        @Override
	        public TableCell<Playlist, Void> call(final TableColumn<Playlist, Void> param) {

	            final TableCell<Playlist, Void> cell = new TableCell<Playlist, Void>(){

	                private final Hyperlink linkToStats = new Hyperlink("lista canzoni");
	                
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

	    tabellaPlaylist.getColumns().add(StatsColumn);
		
	}
    
    private void  onHyperLinkCliked (ActionEvent e, int indice ) throws IOException {
    	
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/SelezionaCanzone.fxml"));
    	Playlist playlistSelezionata = UserPlaylists.get(indice);
    	SelezionaCanzoneController controller = new SelezionaCanzoneController(utente,playlistSelezionata);
    	fxmlloader.setController(controller); 
    	controller.setElencoPlaylistUtente(UserPlaylists);
		setRoot(fxmlloader.load());
		changeScene(e);
		
		
    }
    /**
     * porta alla scena menu utente
     * @param e: evento scatenante
     * @throws IOException
     */
    @FXML
    public void switchToMenuUtente(ActionEvent e) throws IOException {

    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/MenuUtente.fxml"));
    	MenuUtenteController controller = new MenuUtenteController(utente);
    	fxmlloader.setController(controller);
		setRoot(fxmlloader.load());
		changeScene(e);
		
    }
    

}
