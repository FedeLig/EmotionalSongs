/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
/**
 * Classe che si occupa della gestione della scena descritta da RicercaAvanzata.fxml
 * , permette di cercare e aggiungere ad una playlist tutte le canzoni appartenti ad un album ,
 * o composte da un particolare autore 
 * @author Federico Ligas
 */
public class RicercaAvanzataController extends Controller implements Initializable{
	

     /**
	 * </>TabellaRicerca</> : tabella dei risultati della ricerca 
	 */
	@FXML
	private TableView<Song> TabellaRicerca ; 
	 /**
      * </>tabpane</> : struttura che ospita delle scene 
	  */
	@FXML
	private TabPane tabpane ; 
	/**
     * </>firstTab</> : prima scena contenuta ,</>secondTab</> : seconda scena contenuta 
	  */
	@FXML 
	private Tab firstTab, secondTab ;
	/**
     * </>authorField </> : area di testo per l 'autore ,</>albumField</> : area di testo per l'album 
	  */
	@FXML
	private TextField authorField , albumField; 
	/**
     * </>playlist</> : playlist a cui aggiungeremo le canzoni 
	 */
	private Playlist playlist ; 
	/**
     * </>opzioneDiRicerca</> : opzione possibili per la ricerca 
	 */
	private String opzioneDiRicerca ; 
	/**
     * </>opzioneDiRicerca</> : tipo di ricerca impostato 
	 */
	private int tipoRicerca  = 3  ; 
	/**
     * </>risultatiRicerca</> : risultati della ricerca 
	 */
	private ObservableList<Song> risultatiRicerca = FXCollections.observableArrayList();
	/**
     * </>listProperty</> : proprietà che collega il contenuto della lista dei dati alla tabella 
	 */
	private Property<ObservableList<Song>> listProperty ;
	/**
     * </>utente</> : utente corrente 
	 */
	private Login utente ; 
	
	/**
     * costruttore di base 
     * @param utente : utente corrente 
     * @param opzioneDiRicerca : opzione per la ricerca selezionata 
	 */
	public RicercaAvanzataController(Login utente,String opzioneDiRicerca ) { 
		
		this.utente = utente ; 
		this.opzioneDiRicerca = opzioneDiRicerca ; 
		listProperty = new SimpleObjectProperty<>(risultatiRicerca);
		
	}

	/**
	 * Viene chiamato (una e una sola volta) dal controller appena dopo la scena è stata "caricata" con successo 
     * e inizializza gli elementi che sono contenuti nella scena .
     * @param arg0 : Il path usato per risolvere i path relativi per l'oggetto "radice" o il valore null se il path non &egrave noto
     * @param arg1 : Le risorse usate per localizzare l'oggetto "radice" , o null se la radice non viene trovata 
	 */
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
	/**
     * aggiunge un link di testo ad ogni riga della tabella 
	 */
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
	                        	
								ArrayList<Song> listaRisultati = Song.searchSong(tipoRicerca,argRicerca) ; 
								
								for( Song risultato : listaRisultati) {
								    if(!(playlist.contiene(risultato))) {
								       playlist.aggiungiCanzone(risultato);
								    }
								}
								
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
	

	 /**
	 * porta alla scena per la creazione delle playlist 
	 * @param event : evento che scatena il metodo
	 * @throws IOException : il file non viene trovato 
	 */
    public void switchToCreazionePlaylist(ActionEvent e) throws IOException {
        
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/CreazionePlaylist.fxml"));
		CreazionePlaylistController controller = new CreazionePlaylistController(utente); 
		fxmlloader.setController(controller);
		controller.setPlaylist(playlist);
		setRoot(fxmlloader.load());
		changeScene(e);
		
    }
    /**
	 * Permette di ricerca un autore o un album per poi poter
	 * aggiungere tutte le canzoni di un autore o un album presente nei risultati 
	 * @param event : evento che scatena il metodo
	 * @throws IOException : il file non viene trovato 
	 * @throws NumberFormatException : 
	 */
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
    /**
     * 
     * @return restituisce i risulati della ricerca
     */
	public ObservableList<Song> getRisultatiRicerca() {
		return risultatiRicerca;
	}
	/**
	 * Permette di impostare i risultati della ricerca
	 * @param risultatiRicerca
	 */
	public void setRisultatiRicerca(ObservableList<Song> risultatiRicerca) {
		this.risultatiRicerca = risultatiRicerca;
	}
	/**
	 * permette di impostare la playlist
	 * @param playlist
	 */
	public void setPlaylist(Playlist playlist ) {
		this.playlist = playlist  ;
		
	}
	
	
}