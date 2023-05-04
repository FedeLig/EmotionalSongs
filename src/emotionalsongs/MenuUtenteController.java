/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Classe che si occupa della gestione della scena "Menu Utente"
 * @author Federico Ligas
 */
public class MenuUtenteController extends Controller  {
	
	/**
	 * </>toSearchButton</> : bottone che porta alla ricerca in repository
	 * </>newPlaylistButton</> : bottone che porta alla creazione di una nuova playlist 
	 */
	@FXML private Button toSearchButton , newPlaylistButton , seePlaylistsButton , logoutButton ;
	
	/**
	 * </>utente</> : utente corrente 
	 */
	private Login utente ;
	
	/**
	 * costruttore di base 
	 *@param utente : utente corrente 
     */
	public MenuUtenteController(Login utente)  {
			
		this.utente = utente ; 
		
	}
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		toSearchButton.setOnAction(
				event -> switchTo(event,"RicercaInRepository.fxml",new RicercaInRepositoryController(utente,"MenuUtente.fxml")) );
		
		newPlaylistButton.setOnAction(
				event -> switchTo(event,"NomePlaylist.fxml",new NomePlaylistController(utente)) );
		
		seePlaylistsButton.setOnAction(
				event -> switchTo(event,"SelezionaPlaylist.fxml",new SelezionaPlaylistController(utente)) );
		
		logoutButton.setOnAction(
				event -> switchTo(event,"MenuIniziale.fxml",new MenuInizialeController()) );
	}
	
}
