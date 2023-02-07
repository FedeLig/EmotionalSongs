package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

/**
 * Classe che si occupa della gestione della scena descritta da RicercaInRepository.fxml
 * , quando &egrave richiesto ricercare una canzone per aggiungerla ad una playlist
 * nota : la scena RicercaInRepository.fxml può essere usata anche da RicercaInRepositoryController in altri contesti
 * @author Federico Ligas
 */
public class AddSongToPlaylistController extends SearchSongTableController {
	
	/**
	 * </>playlist</> : playlist dell' utente a cui dobbiamo aggiungere le canzoni
	 */
	private Playlist playlist ; 
	/**
	 * </>utente</> : utente che ha effettuato il Login
	 */
	private Login utente ; 
	
	/**
	 * costruttore di base 
	 * @param utente : utente che ha effettuato il Login 
	 */
	public AddSongToPlaylistController(Login utente) {
		this.utente = utente ; 
	}

	/**
	 * torna alla scena precedente : rappresentata da CreazionePlaylist.fxml
	 * @param event : evento che scatena il metodo
	 */
	public void tornaAlPrecedente(ActionEvent e ) throws IOException {
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/CreazionePlaylist.fxml"));
		CreazionePlaylistController controller = new CreazionePlaylistController(utente); 
		fxmlloader.setController(controller);
		controller.setPlaylist(playlist);
		setRoot(fxmlloader.load());
		changeScene(e);
		
	}
	/**
	 * ritorna il testo contenuto del link di testo contenuto nella tabella 
	 * @return  "Aggiungi"
	 */
	@Override 
	protected   String getTestoHyperLink(){
		return "Aggiungi" ; 
	}
	/**
	 * Aggiunge la canzone della riga a cui appartiene il link di testo clickato
	 * @param e : evento che scatena il metodo
	 * @param indice : indice della riga che contiene il link di testo clickato 
	 * @throws IOException : 
	 */
	@Override 
    protected void  onHyperLinkCliked (ActionEvent e , int indice) throws IOException{
		
		// prende la canzone nella riga selezionata
		Song canzone = getSongObservableList().get(indice);
		
		// aggiunge la canzone alla playlist solo se non è già presente 
		if(playlist.contiene(canzone)) {
	       createAlert("Errore : La canzone e' gia' presente");
		}
		else{
		  playlist.aggiungiCanzone(canzone);
		  createAlert("La canzone e' stata aggiunta");
		}
		
	}
	/**
	 * imposta il valore della variabile </>playlist</> con la playlist in fase di creazione
	 * @param playlist : playlist dell' utente a cui dobbiamo aggiungere le canzoni
	 */
	public void setPlaylist( Playlist playlist ) {
		this.playlist = playlist ; 
	}
	
}