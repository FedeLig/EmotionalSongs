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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class SelezionaCanzoneController extends SongTableController {

	@FXML private Button TornaAlMenu , TornaAllePlaylist ; 
	
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
	 * @param userPlaylists 
	 */
	public SelezionaCanzoneController( Login utente , Playlist playlist, ObservableList<Playlist> userPlaylists ) {
		
		this.playlist = playlist; 
		this.utente = utente ; 
		if ( userPlaylists == null )
		   this.elencoPlaylistUtente = FXCollections.observableArrayList(utente.getUserPlaylists()) ; 
		else
		   this.elencoPlaylistUtente = userPlaylists;
		
		canzoniPlaylist = new ArrayList<Song>() ; 
		ArrayList<Integer> ListaIndiciCanzoni = playlist.getlistaIndiciPlaylist() ; 
		
		for(Integer indice : ListaIndiciCanzoni) {
			try {
				canzoniPlaylist.add(new Song(indice));
				
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			} 
		}
		
		try {
			canzoniValutate.addAll(Song.getEmozioniUtente(utente,ListaIndiciCanzoni));
		} catch (IOException e) {
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
		
		TornaAlMenu.setOnAction(
				event -> switchTo(event,"MenuUtente.fxml",new MenuUtenteController(utente)) );
		
		TornaAllePlaylist.setOnAction(
				event -> switchTo(event,"SelezionaPlaylist.fxml",new  SelezionaPlaylistController(utente)) );
		
	}
	@Override
	protected void addHyperlinkToTable() {
		 // usiamo la wrapper class di void per inizializzare colonna 
		
	    TableColumn<Song,Void> StatsColumn = new TableColumn<Song,Void>("");

	    Callback<TableColumn<Song,Void>, TableCell<Song,Void>> cellFactory = new Callback<TableColumn<Song,Void>, TableCell<Song,Void>>() {

	        @Override
	        public TableCell<Song,Void> call(final TableColumn<Song,Void> param) {

	            final TableCell<Song,Void> cell = new TableCell<Song,Void>(){

	                private final Hyperlink linkToInsert = new Hyperlink("inserisci emozioni");
	                private final Hyperlink linkToView = new Hyperlink("emozioni inserite");
	                private final Hyperlink linkToStats  = new Hyperlink(getTestoHyperLink());
	                
	                {
	                
	                    linkToInsert.setOnAction((ActionEvent event) -> {
	           
	                    	int indice = getIndex();
	                    	onHyperLinkCliked(event,indice);
	                    	// serve in modo che un hyperlink clickato non rimanga sottolineato
	                    	linkToInsert.setVisited(false);
	                    });
	                    
	                    linkToView.setOnAction((ActionEvent event) -> {
	         	           
	                    	int indice = getIndex();
	                    	
							try {
								switchToVisualizzaEmozioniUtente(event,indice);
							} catch (IOException exp) {
								// TODO Auto-generated catch block
								exp.printStackTrace();
							}
	                    	// serve in modo che un hyperlink clickato non rimanga sottolineato
	                    	linkToView.setVisited(false);
	                    });
	                    
	                    linkToStats.setOnAction((ActionEvent event) -> {
	                    	
	                    	int indice = getIndex();
	                    	try {
	                    		switchToVisualizzaEmozioni(event,indice);
							} catch (IOException exp) {
								// TODO Auto-generated catch block
								exp.printStackTrace();
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
	                    	TableRow<Song> row = getTableRow();
	                    	if ( row != null && !(row.isEmpty())) {
	                    		int indice = getIndex();
		                    	HBox hyperlinks = canzoniValutate.get(indice).equals(" ") ? new HBox(linkToInsert,linkToStats) 
		                    			                                                  : new HBox(linkToView,linkToStats) ;
		                        setGraphic(hyperlinks);
	                    	}
	                    	
	                    }
	                }

					
	              };
	            
	            return cell;
	        }
	    };

	    // crea una CellValueFactory che contiene un hyperlink , per ogni riga della tabella 
	    StatsColumn.setCellFactory(cellFactory);
	    StatsColumn.setMinWidth(300);

	    getTabellaCanzoni().getColumns().add(StatsColumn);
	    
	}
	@Override
	protected void onHyperLinkCliked(ActionEvent event, int indice) {
		
		Song CanzoneSelezionata = canzoniPlaylist.get(indice);
			
		switchTo(event,"InserisciEmozioni.fxml",new InserisciEmozioniController(utente,CanzoneSelezionata.getId(),playlist));
  
		
	}
	
	private void switchToVisualizzaEmozioniUtente(ActionEvent event , int indice ) throws IOException  {
		
		Song CanzoneSelezionata = canzoniPlaylist.get(indice);
		
		switchTo(event,"VisualizzaEmozioniUtente.fxml",new VisualizzaEmozioniUtenteController(utente,CanzoneSelezionata,canzoniValutate.get(indice),playlist));
		
	}

	@Override
	protected String getTestoHyperLink() {
		return "riassunto emozioni utenti" ;
	}
	

	private void switchToVisualizzaEmozioni(ActionEvent event, int indice) throws IOException {
		
		Song CanzoneSelezionata = canzoniPlaylist.get(indice);
		
		listaEmozioni = new ArrayList<VisualizzaEmozioniDati>() ; 
		setEmotionData(CanzoneSelezionata.getId());
		
		switchTo(event,"VisualizzaEmozioni.fxml",new VisualizzaEmozioniController(utente,playlist,CanzoneSelezionata,FXCollections.observableArrayList(listaEmozioni)));
		
	}
	
	
	private void setEmotionData(int indiceCanzone ) throws FileNotFoundException, IOException {
		this.listaEmozioni = Song.VisualizzaEmozioniBrano(indiceCanzone);
	}

	
}
