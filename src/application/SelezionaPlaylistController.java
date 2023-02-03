package application;

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
    
    public SelezionaPlaylistController(Login utente) {
		this.utente = utente; 
		this.UserPlaylists = FXCollections.observableArrayList(utente.getUserPlaylists());
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	tabellaPlaylist.setPlaceholder(new Label("Nessuna playlist presente"));
    	
    	if(!( UserPlaylists.isEmpty() )) {
    		
    		populateTable(UserPlaylists);
    	}
		
	}
    
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
    @FXML
    public void switchToMenuUtente(ActionEvent e) throws IOException {

    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/MenuUtente.fxml"));
    	MenuUtenteController controller = new MenuUtenteController(utente);
    	fxmlloader.setController(controller);
		setRoot(fxmlloader.load());
		changeScene(e);
		
    }
    
    public ArrayList<Playlist> driver(){
    	
    	ArrayList<Playlist> playlistUtente = new ArrayList<Playlist>() ; 
    	
    	int  n = 10 ; 
    
    	for(int i=0 ; i<n ; i++ ) {
    		
    		StringBuilder str = new StringBuilder();
    		
    		for(int j=0 ; j<n ; j++ ) {
        		
        		str.append(((Integer)(new Random().nextInt(9 - 0 + 1) + 0)).toString()) ; 
        	}
        	
    		playlistUtente.add(new Playlist(str.toString()));
    		
    	}
    	
    	return playlistUtente ;

    }

}
