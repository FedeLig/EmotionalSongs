package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
/*
 * @Author Ligas
 */
public class RicercaAvanzataController extends Controller implements Initializable{
	
	@FXML
	private TableView<Song> TabellaRicerca ; 
	@FXML
	private TabPane tabpane ; 
	@FXML 
	private Tab firstTab, secondTab ;
	
	@FXML
	private TextField authorField , albumField; 
	
	private Playlist playlist ; 
	private String opzioneDiRicerca ; 
	private int tipoRicerca  = 3  ; 
	private ObservableList<Song> risultatiRicerca = FXCollections.observableArrayList();
	private Property<ObservableList<Song>> listProperty ;
	private Login utente ; 
	
	public RicercaAvanzataController(Login utente,String opzioneDiRicerca ) { 
		
		this.opzioneDiRicerca = opzioneDiRicerca ; 
		listProperty = new SimpleObjectProperty<>(risultatiRicerca);
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
        SingleSelectionModel<Tab> selectionModel  = tabpane.getSelectionModel();
    	
    	// permette di modificare la tabella quando i risultati della ricerca cambiano
        TabellaRicerca.itemsProperty().bind(listProperty);
        
        TabellaRicerca.setPlaceholder(new Label("La tabella è vuota "));
        
        // nota : il deafult è la ricerca per autore
        if (opzioneDiRicerca.equals("album")){
      
            tipoRicerca = 4;
            selectionModel.select(1);
            firstTab.setDisable(true);
			secondTab.setDisable(false);  
        }
 
		
	}
	
	protected void addHyperlinkToTable() {
	    // usiamo la wrapper class di void per inizializzare colonna 
		
	    TableColumn<Song,Void> linkColumn = new TableColumn<Song,Void>("");

	    Callback<TableColumn<Song,Void>, TableCell<Song,Void>> cellFactory = new Callback<TableColumn<Song,Void>, TableCell<Song,Void>>() {

	        @Override
	        public TableCell<Song,Void> call(final TableColumn<Song,Void> param) {

	            final TableCell<Song,Void> cell = new TableCell<Song,Void>(){

	                private final Hyperlink link = new Hyperlink("Aggiungi tutte le canzoni");
	                
	                {
	                
	                    link.setOnAction((ActionEvent e) -> {
	                    	
	                    	int indice ; 
	                    	getTableView().getItems().get(indice = getIndex());
	                    	Song canzone = getRisultatiRicerca().get(indice);
	                    	String argomentoDiRicerca ;
	                        
							switch(tipoRicerca) {
							case 3 : 
							    argomentoDiRicerca = canzone.getAuthor();
							    break;
							case 4 : 
								argomentoDiRicerca = canzone.getAlbum();
								break;
							default:
								argomentoDiRicerca = "";
	                    	}
							String[] argRicerca = {argomentoDiRicerca};
							try {
	                        	
								playlist.aggiungiCanzoni(Song.searchSong(tipoRicerca,argRicerca));
								
							} catch (NumberFormatException | IOException e1) {
								e1.printStackTrace();
							}
							
							if(playlist.getListaCanzoni().isEmpty()) {
								System.out.println("ccc");
							}
	                    	// serve in modo che un hyperlink clickato non rimanga sottolineato
	                    	link.setVisited(false);
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
	                        setGraphic(link);
	                    }
	                }
	              };
	            
	            return cell;
	        }
	    };

	    // crea una CellValueFactory che contiene un hyperlink , per ogni riga della tabella 
	    linkColumn.setCellFactory(cellFactory);

	    TabellaRicerca.getColumns().add(linkColumn);
		
	}
	
    public void switchToCreazionePlaylist(ActionEvent e) throws IOException {
        
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/CreazionePlaylist.fxml"));
		CreazionePlaylistController controller = new CreazionePlaylistController(utente); 
		fxmlloader.setController(controller);
		controller.setPlaylist(playlist);
		setRoot(fxmlloader.load());
		changeScene(e);
		
    }
    
    public void ricercaAvanzata(ActionEvent e ) throws NumberFormatException, IOException {
    	
        String[] input = new String[1] ; 
		
		if( tipoRicerca == 3 ) {
			
			input[0] = authorField.getText();
		}
		else {
			
		   if ( tipoRicerca == 4 ){
			   
			input[0] = albumField.getText();

			
		   }
		}
		
		if(!(input[0].isEmpty())) {
			
			addHyperlinkToTable();
			
			risultatiRicerca.setAll(Song.searchSong(tipoRicerca, input));
			
			if (risultatiRicerca.isEmpty()) {
				
				TabellaRicerca.getColumns().clear();
				TabellaRicerca.setPlaceholder(new Label("Nessun "+opzioneDiRicerca+" trovato"));
			}
			else {
			
				if(tipoRicerca == 3) {
				
					TableColumn<Song,String> authorColumn = new TableColumn<>("autore");
					authorColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("author"));
					TabellaRicerca.getColumns().add(authorColumn);
				
				}else {
				
					TableColumn<Song,String> albumColumn = new TableColumn<>("album");
					albumColumn.setCellValueFactory(new PropertyValueFactory<Song,String>("album"));
					TabellaRicerca.getColumns().add(albumColumn);
	    		
				}
				
			}
			
			
		}
    }

	public ObservableList<Song> getRisultatiRicerca() {
		return risultatiRicerca;
	}

	public void setRisultatiRicerca(ObservableList<Song> risultatiRicerca) {
		this.risultatiRicerca = risultatiRicerca;
	}

	public void setPlaylist(Playlist playlist ) {
		this.playlist = playlist  ;
		
	}
	
	
}