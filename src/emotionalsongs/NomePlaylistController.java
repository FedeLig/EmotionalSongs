/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Classe che gestisce l'inserimento del nome della playlist
 * @author Federico Ligas
 *
 */
public class NomePlaylistController extends Controller  implements Initializable {
	
	@FXML  private Button confermaNome, tornaAlMenu ; 
	
	@FXML private TextField nameField ; 
	
	private Login utente ; 
	
	/**
	 * Imposta il nome utente da usare
	 * @param utente
	 */
	public NomePlaylistController(Login utente) {
		this.utente = utente ; 
	}
	/**
	 * Viene chiamato (una e una sola volta) dal controller appena dopo la scena è stata "caricata" con successo 
     * e inizializza gli elementi che sono contenuti nella scena .
     * @param arg0 : Il path usato per risolvere i path relativi per l'oggetto "radice" o il valore null se il path non &egrave noto
     * @param arg1 : Le risorse usate per localizzare l'oggetto "radice" , o null se la radice non viene trovata 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		BooleanBinding condition  = (nameField.textProperty().isEmpty());
        confermaNome.disableProperty().bind(condition);
        
        tornaAlMenu.setOnAction(
        	event -> switchTo(event,"MenuUtente.fxml",new MenuUtenteController(utente)) ) ;
        
        confermaNome.setOnAction(
        	event -> {
        	String nomePlaylist = nameField.getText();
    		
    		if( checkNomePlaylist(nomePlaylist)) {
    			
    			Playlist playlist = new Playlist(nomePlaylist,utente.getUserName());
    			switchTo(event,"CreazionePlaylist.fxml",new CreazionePlaylistController(utente,playlist));
    			
    		}
    		else 
    			createAlert("Errore : hai già creato una \n  playlist con questo nome  ");
        	});
		
	}
	
	/**
	 * Verifica che non l'utente non abbia già creato una playlist
	 * con lo stesso nome
	 * @param nomePlaylist
	 * @return vero se il nome è unico, falso altrimenti
	 */
	public boolean checkNomePlaylist(String nomePlaylist ) {
	 
		boolean accettabile = true ; 
		
		List<Playlist> listaPlaylistUtente = utente.getUserPlaylists();
		
		int i = 0 ; 
		
		while(i < listaPlaylistUtente.size() && accettabile ) {
			if(listaPlaylistUtente.get(i).getNomePlaylist().equals(nomePlaylist))
			  accettabile = false ; 
			i++;
		}
		
		return accettabile ; 
		
	}
	
	
}