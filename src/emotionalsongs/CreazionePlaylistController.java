/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/*
 * Classe che permette di vedere le canzoni presenti nella playlist
 * che si sta creando , di andare a cercare una canzone per aggiungerla e 
 * di eliminare una canzone aggiunta precedentemente
 * @author Federico Ligas
 * @author Edoardo Picazio 
 */
public class CreazionePlaylistController extends SongTableController {

	/**
	 * </>RicercaCanzone</> : buttone che permette di cercare una canzone 
	 * </>TornaAlMenu</> : buttone che fa tornare l'utente al menu utente
	 */
	@FXML private Button RicercaCanzone, TornaAlMenu , tuttiBraniAlbum , tuttiBraniAutore , salvaPlaylistButton ;
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
	 * @param playlist : 
	 */
	public CreazionePlaylistController(Login utente, Playlist playlist) {
		this.utente = utente; 
		this.playlist = playlist;
	}
	/**
	 * Viene chiamato (una e una sola volta) dal controller appena dopo la scena è stata "caricata" con successo 
     * e inizializza gli elementi che sono contenuti nella scena .
     * @param arg0 : Il path usato per risolvere i path relativi per l'oggetto "radice" o il valore null se il path non &egrave noto
     * @param arg1 : Le risorse usate per localizzare l'oggetto "radice" , o null se la radice non viene trovata 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		RicercaCanzone.setOnAction(
				event -> switchTo(event,"RicercaInRepository.fxml",new AddSongToPlaylistController(utente,playlist)) ) ;
		
		TornaAlMenu.setOnAction(
		        event -> {
		        	playlist.svoutaListaCanzoni();
		        	switchTo(event,"MenuUtente.fxml",new MenuUtenteController(utente));
		        });
		
		tuttiBraniAlbum.setOnAction(
				event -> switchTo(event,"RicercaAvanzata.fxml",new RicercaAvanzataController(utente,"album",playlist)) )  ;
		
		tuttiBraniAutore.setOnAction(
				event -> switchTo(event,"RicercaAvanzata.fxml",new RicercaAvanzataController(utente,"autore",playlist)) )  ;
		
		salvaPlaylistButton.setOnAction(
					event -> {
						try {
							salvaPlaylist(event);
						} catch (IOException e) {
							e.printStackTrace();
						}} )  ;
		 
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
	public void salvaPlaylist(ActionEvent event ) throws IOException {
		
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
		   playlist.svoutaListaCanzoni();
       	   switchTo(event,"MenuUtente.fxml",new MenuUtenteController(utente));
		}
		else {
		   createAlert("Errore : la playlist è vuota");
		}
		
	}

	 /**
     * Rimuove la canzone contenuta nella riga a cui appartiene il link di testo clickato
     * @param event : evento che scatena il metodo
     * @param indice : indice della riga che contiene il link di testo clickato 
     */
	@Override
	protected void onHyperLinkCliked(ActionEvent event ,int indice ) {
		
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
	
	
}
