package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/*
 * Classe che si occupa della gestione della scena descritta da CreazionePlaylist.fxml,
 * gestisce la creazione delle playlist e contiene i controlli per tutte le operazioni effettuabili nella schermata "playlist"
 * @author Federico Ligas
 * @author Edoardo Picazio 
 */
public class CreazionePlaylistController extends SongTableController {

	/**
	 * </>RicercaCanzone</> : buttone che permette di cercare una canzone 
	 */
	@FXML
	private Button RicercaCanzone ;
	/**
	 * </>TornaAlMenu</> : buttone che fa tornare l'utente al menu utente
	 */
	@FXML
	private Button TornaAlMenu ;
	/**
	 * </>playlist</> : playlist che l'utente vuole creare
	 */
	private Playlist playlist ; 
	/**
	 * </>utente</> : utente corrente 
	 */
	private Login utente; 
	
	/**
	 * Costruttore di base 
	 * @param utente : utente che vuole creare la playlist 
	 */
	public CreazionePlaylistController(Login utente) {
		this.utente = utente; 
	}
	/**
	 * Viene chiamato (una e una sola volta) dal controller appena dopo la scena è stata "caricata" con successo 
     * e inizializza gli elementi che sono contenuti nella scena .
     * @param arg0 : Il path usato per risolvere i path relativi per l'oggetto "radice" o il valore null se il path non &egrave noto
     * @param arg1 : Le risorse usate per localizzare l'oggetto "radice" , o null se la radice non viene trovata 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		setSongObservableList(playlist.getListaCanzoni());
		
		if (getSongObservableList().isEmpty()) 
			getTabellaCanzoni().setPlaceholder(new Label("La playlist non contiene canzoni"));
		else 
			UpdateTable(getSongObservableList());
		
	}
	/**
	* Salva la playlist nel file Playlist.dati.csv se ritenuta accettabile
	* @param event : evento che scatena il metodo
	*/
    @FXML
	public void salvaPlaylist(ActionEvent e ) throws IOException {
		
    	// se la playlist non è vuota viene salvata 
		if(!(playlist.getListaCanzoni().isEmpty())) {
		   // registra la playlist 
		   playlist.RegistraPlaylist();
		   int i = 0  ;
		   // vengono calcolati gli indici delle canzoni presenti 
		   ArrayList<Integer> listaIndiciCanzoni = new ArrayList<Integer>() ;
		   while ( i < playlist.getListaCanzoni().size()  ) {
			     listaIndiciCanzoni.add(playlist.getListaCanzoni().get(i).getId());
			     i++ ; 
		   }
		   playlist.setListaIndiciCanzoni(listaIndiciCanzoni);
		   // la aggiunge alle playlist dell'utente corrente per mantenere 
		   // la coerenza dei dati durante l'esecuzione 
		   utente.addToUserplaylists(playlist);
		   createAlert("la playlist e' stata salvata");
		}
		else {
		   createAlert("Errore : la playlist è vuota");
		}
		
	}
    /**
     * porta alla scena per aggiungere tutte le canzoni scritte da un autore 
     * @param event : evento che scatena il metodo
     * @throws IOException : il file non viene trovato 
     */
    @FXML 
    public void switchToRicercaAvanzataAutore(ActionEvent e) throws IOException {
    	
    	RicercaAvanzataController controller = new RicercaAvanzataController(utente,"autore");
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/RicercaAvanzata.fxml"));
    	fxmlloader.setController(controller);
    	controller.setPlaylist(playlist);
    	setRoot(fxmlloader.load());
		changeScene(e);
    }
    /**
     * porta alla scena per aggiungere tutte le canzoni contenute in un album 
     * @param event : evento che scatena il metodo
     * @throws IOException : il file non viene trovato 
     */
    @FXML 
    public void switchToRicercaAvanzataAlbum(ActionEvent e) throws IOException {
    	
    	RicercaAvanzataController controller = new RicercaAvanzataController(utente,"album");
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/RicercaAvanzata.fxml"));
    	fxmlloader.setController(controller);
    	controller.setPlaylist(playlist);
    	setRoot(fxmlloader.load());
		changeScene(e);
    }
	@FXML
	 /**
     * porta alla scena per aggiungere canzoni alla playlist in fase di creazione 
     * @param event : evento che scatena il metodo
     * @throws IOException : il file non viene trovato 
     */
    public void switchToAddSong(ActionEvent e) throws IOException {
        
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/RicercaInRepository.fxml"));
		AddSongToPlaylistController controller = new AddSongToPlaylistController(utente);
		fxmlloader.setController(controller);
		setRoot(fxmlloader.load());
		controller = fxmlloader.getController();
		controller.setPlaylist(playlist);
		changeScene(e);
		
    }
	 /**
     * porta alla scena del menu utente 
     * @param event : evento che scatena il metodo
     * @throws IOException : il file non viene trovato 
     */
	@FXML 
	public void switchToMenuUtente(ActionEvent e) throws IOException { 
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/MenuUtente.fxml"));
		MenuUtenteController controller = new MenuUtenteController(utente);
	    fxmlloader.setController(controller);
	    playlist.svoutaListaCanzoni();
		setRoot(fxmlloader.load());
		changeScene(e);
		
		
	}
	 /**
     * Rimuove la canzone contenuta nella riga a cui appartiene il link di testo clickato
     * @param event : evento che scatena il metodo
     * @param indice : indice della riga che contiene il link di testo clickato 
     */
	@Override
	protected void onHyperLinkCliked(ActionEvent e ,int indice ) {
		
		getSongObservableList().remove(indice);
		UpdateTable(getSongObservableList());
	}
	/*
	 * ritorna il testo contenuto nell' Hyperlink contenuto nella tabella 
	 * @return  "elimina"
	 */
	@Override
	protected String getTestoHyperLink() {
		return "elimina"; 
	}
	/**
	 * imposta il valore della variabile </>playlist</> con la playlist in fase di creazione
	 * @param playlist : playlist dell' utente a cui dobbiamo aggiungere le canzoni
	 */
	public void setPlaylist( Playlist playlist ) {
		this.playlist = playlist ; 
	}
	
}
