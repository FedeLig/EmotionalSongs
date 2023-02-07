package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

/**
 * Classe che si occupa della gestione della scena descritta da RicercaInRepository.fxml
 * , quando è richiesto ricercare una canzone per aggiungerla ad una playlist
 * nota : la scena RicercaInRepository.fxml può essere usata anche da RicercaInRepositoryController in altri contesti
 * @see RicercaInRepository.fxml
 * @author Ligas
 */
public class AddSongToPlaylistController extends SearchSongTableController {
	
	private Playlist playlist ; 
	private Login utente ; 
	
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
	
	@Override 
	protected   String getTestoHyperLink(){
		return "Aggiungi" ; 
	}
	@Override 
    protected void  onHyperLinkCliked (ActionEvent e , int indice) throws IOException{
		
		Song canzone = getSongObservableList().get(indice);
		if(playlist.contiene(canzone)) {
	       createAlert("Errore : La canzone e' gia' presente");
		}
		else{
		  playlist.aggiungiCanzone(canzone);
		  createAlert("La canzone e' stata aggiunta");
		}
		
	}
	
	public void setPlaylist( Playlist playlist ) {
		this.playlist = playlist ; 
	}
	
}