/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class NomePlaylistController extends Controller  implements Initializable {
	@FXML 
	private Button confermaNome ;
	@FXML
	private Button tornaAlMenu ; 
	@FXML
	private TextField nameField ; 
	
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
		
	}
	
	/**
	 * Porta alla scena della creazione playlist 
	 * @param event : evento che scatena il metodo
	 */
	@FXML
    public void switchToCreazionePlaylist(ActionEvent e ) throws IOException {
		
		String nomePlaylist = nameField.getText();
		
		if( checkNomePlaylist(nomePlaylist)) {
			
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/CreazionePlaylist.fxml"));
			CreazionePlaylistController controller = new CreazionePlaylistController(utente); 
			fxmlloader.setController(controller);
			Playlist playlist = new Playlist(nomePlaylist,utente.getUserName());
			controller.setPlaylist(playlist);
			setRoot(fxmlloader.load());
			changeScene(e);
			
		}
		else 
			createAlert("Errore : hai già creato una \n  playlist con questo nome  ");
		
	}
	
	/**
	 * Porta alla scena del menu utente
	 * @param event : evento che scatena il metodo
	 */
	@FXML
    public void switchToMenuUtente(ActionEvent e ) throws IOException {

		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/MenuUtente.fxml"));
		MenuUtenteController controller = new MenuUtenteController(utente);
	    fxmlloader.setController(controller);
		setRoot(fxmlloader.load());
		changeScene(e);
		
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