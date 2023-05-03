/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Classe permette di ricercare una canzone e aggiungerla alla playlist 
 * @author Federico Ligas
 */
public class AddSongToPlaylistController extends SearchSongTableController {
	
	
	@FXML private Button goBackButton ; 
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
	 * @param playlist 
	 */
	public AddSongToPlaylistController(Login utente, Playlist playlist) {
		this.utente = utente ; 
		this.playlist = playlist;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		super.initialize(arg0, arg1);
		goBackButton.setOnAction(
				event -> switchTo(event,"CreazionePlaylist.fxml",new CreazionePlaylistController(utente,playlist)) );
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
	 * @param event : evento che scatena il metodo
	 * @param indice : indice della riga che contiene il link di testo clickato 
	 */
	@Override 
    protected void  onHyperLinkCliked (ActionEvent event , int indice) {
		
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

}