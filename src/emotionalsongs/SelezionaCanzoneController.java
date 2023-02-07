/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class SelezionaCanzoneController extends SongTableController {

    private Playlist playlist ; 
	
	private ArrayList<Song> canzoniPlaylist ; 
	
	private ObservableList<Playlist> elencoPlaylistUtente; 
	
	private Login utente;
	
	private ArrayList<String> canzoniValutate = new ArrayList<String>() ;
	
	private ArrayList<VisualizzaEmozioniDati> listaEmozioni ; 
	/**
	 * permette di aggiungere delle canzoni a quelle valutate
	 * @param utente
	 * @param playlist
	 */
	public SelezionaCanzoneController( Login utente , Playlist playlist ) {
		
		this.playlist = playlist; 
		this.utente = utente ; 
		
		canzoniPlaylist = new ArrayList<Song>() ; 
		ArrayList<Integer> ListaIndiciCanzoni = playlist.getlistaIndiciPlaylist() ; 
		
		for(Integer indice : ListaIndiciCanzoni) {
			try {
				canzoniPlaylist.add(new Song(indice));
				
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		try {
			canzoniValutate.addAll(Song.getEmozioniUtente(utente,ListaIndiciCanzoni));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	/**
	 * Viene chiamato (una e una sola volta) dal controller appena dopo la scena è stata "caricata" con successo 
     * e inizializza gli elementi che sono contenuti nella scena .
     * @param arg0 : Il path usato per risolvere i path relativi per l'oggetto "radice" o il valore null se il path non &egrave noto
     * @param arg1 : Le risorse usate per localizzare l'oggetto "radice" , o null se la radice non viene trovata 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		setSongObservableList(FXCollections.observableArrayList(canzoniPlaylist));
		UpdateTable(getSongObservableList()); 
	}
	@Override
	protected void addHyperlinkToTable() {
		 // usiamo la wrapper class di void per inizializzare colonna 
		
	    TableColumn<Song,Void> StatsColumn = new TableColumn<Song,Void>("");

	    Callback<TableColumn<Song,Void>, TableCell<Song,Void>> cellFactory = new Callback<TableColumn<Song,Void>, TableCell<Song,Void>>() {

	        @Override
	        public TableCell<Song,Void> call(final TableColumn<Song,Void> param) {

	            final TableCell<Song,Void> cell = new TableCell<Song,Void>(){

	            	
	                private final Hyperlink linkToInsert = new Hyperlink(getTestoHyperLink());
	                private final Hyperlink linkToStats  = new Hyperlink("prospetto riassuntivo utenti");
	                
	                private final HBox hyperlinks = new HBox(linkToInsert,linkToStats) ; 
	                
	                {
	                
	                    linkToInsert.setOnAction((ActionEvent e) -> {
	           
	                    	int indice ; 
	                    	getTableView().getItems().get(indice = getIndex());
	                    	onHyperLinkCliked(e,indice);
	                    	// serve in modo che un hyperlink clickato non rimanga sottolineato
	                    	linkToInsert.setVisited(false);
	                    });
	                    
	                    linkToStats.setOnAction((ActionEvent e) -> {
	                    	
	                    	int indice ; 
	                    	getTableView().getItems().get(indice = getIndex());
	                    	try {
								switchToVisualizzaEmozioni(e,indice);
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
	                        setGraphic(hyperlinks);
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
	@Override
	protected void onHyperLinkCliked(ActionEvent e, int indice) {
		
		Song CanzoneSelezionata = canzoniPlaylist.get(indice);
		
		if((canzoniValutate.get(indice).equals(" "))) {
			
		    FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/InserisciEmozioni.fxml"));
	   
	        try {
			   setRoot(fxmlloader.load());
			   changeScene(e);
		    } catch (IOException e1) {
			   e1.printStackTrace();
		    }
	    
	        InserisciEmozioniController controller = fxmlloader.getController() ;
		    controller.setUtente(utente);
		    controller.setIdCanzone(CanzoneSelezionata.getId());
		    controller.setPlaylist(playlist);
		    controller.setControllerCorrente(controller);
		}
		else {
			
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/VisualizzaEmozioniUtente.fxml"));
			
			VisualizzaEmozioniUtenteController controller  = new VisualizzaEmozioniUtenteController(utente,CanzoneSelezionata,canzoniValutate.get(indice),playlist);
			fxmlloader.setController(controller);
			try {
				   setRoot(fxmlloader.load());
				   changeScene(e);
			    } catch (IOException e1) {
				   e1.printStackTrace();
		    }
			
		}
	}

	@Override
	protected String getTestoHyperLink() {
		return "Inserisci o visualizza emozione" ;
	}
	
	@FXML
	/**
	 * porta alla scena menu utente
	 * @param e:evento scatenante
	 * @throws IOException
	 */
    public void switchToMenuUtente(ActionEvent e) throws IOException {

    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/MenuUtente.fxml"));
    	MenuUtenteController controller = new MenuUtenteController(utente);
    	fxmlloader.setController(controller);
		setRoot(fxmlloader.load());
		changeScene(e);
		
    }

	@FXML 
	/**
	 * Porta alla scena di visualizzazione della playlist
	 * @param e: evento scatenante
	 * @throws IOException
	 */
	public void switchToVisualizzaPlaylist(ActionEvent e )throws IOException{
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/SelezionaPlaylist.fxml"));
		SelezionaPlaylistController controller = new  SelezionaPlaylistController(utente);
		fxmlloader.setController(controller); 
		setRoot(fxmlloader.load());
		changeScene(e);
		
	}
	/**
	 * Permette di impostare l'elenco delle canzoni
	 * @param elencoPlaylistUtente
	 */
	public void setElencoPlaylistUtente( ObservableList<Playlist> elencoPlaylistUtente ) {
		this.elencoPlaylistUtente =  elencoPlaylistUtente ; 
	}
	
	private void switchToVisualizzaEmozioni(ActionEvent e, int indice) throws IOException {
		
		Song CanzoneSelezionata = canzoniPlaylist.get(indice);
		
		listaEmozioni = new ArrayList<VisualizzaEmozioniDati>() ; 
		setEmotionData(CanzoneSelezionata.getId());
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/VisualizzaEmozioni.fxml"));
		VisualizzaEmozioniController controller = new VisualizzaEmozioniController(utente,playlist,CanzoneSelezionata,FXCollections.observableArrayList(listaEmozioni));
		fxmlloader.setController(controller); 
		setRoot(fxmlloader.load());
		changeScene(e);
		
	}
	
	private void setEmotionData(int indiceCanzone ) throws FileNotFoundException, IOException {
		this.listaEmozioni = Song.VisualizzaEmozioniBrano(indiceCanzone);
	}

	
}
