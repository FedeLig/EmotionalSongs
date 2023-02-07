/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * Classe che si occupa della gestione della scena "Menu Utente"
 * @author Ligas
 */
public class MenuUtenteController extends Controller  {
	
	/**
	 * </>toSearchButton</> : bottone che porta alla ricerca in repository
	 */
	@FXML
	private Button toSearchButton ;
	/**
	 * </>newPlaylistButton</> : bottone che porta alla creazione di una nuova playlist 
	 */
	@FXML
	private Button newPlaylistButton ;
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
		
	/**
	 * Porta l'utente alla ricerca in repository 
	 *@param e : evento che scatena l'azione 
	 *@throws IOException : file non trovato 
     */
     @FXML
	 public void switchToRicercaRepository(ActionEvent e ) throws IOException {
			
		   RicercaInRepositoryController controller = new RicercaInRepositoryController(utente,"/MenuUtente.fxml");
		   FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/RicercaInRepository.fxml"));
		   fxmlloader.setController(controller);
		   setRoot(fxmlloader.load());
		   changeScene(e);
		   
     }
     /**
	  * Porta l'utente al primo passo della creazione delle playlist : l'inserimento del nome 
	  *@param e : evento che scatena l'azione 
	  *@throws IOException : file non trovato 
	  */
	 @FXML
	  public void switchToNomePlaylist(ActionEvent e ) throws IOException {
			
		  FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/NomePlaylist.fxml"));
		  NomePlaylistController controller = new NomePlaylistController(utente);
		  fxmlloader.setController(controller);
		  setRoot(fxmlloader.load());
		  changeScene(e);
			
	  }
	 /**
	  * Porta l'utente alla visualizzazione delle playlist 
	  *@param e : evento che scatena l'azione 
	  *@throws IOException : file non trovato 
	  */
	  @FXML
	   public void switchToVisualizzaPlaylist(ActionEvent e ) throws IOException {
			
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/SelezionaPlaylist.fxml"));
			SelezionaPlaylistController controller = new SelezionaPlaylistController(utente);
			fxmlloader.setController(controller); 
			setRoot(fxmlloader.load());
			changeScene(e);
			
	   }
	  /**
	   * Porta l'utente al menu inizale 
	   *@param e : evento che scatena l'azione 
	   *@throws IOException : file non trovato 
		*/
	   @FXML 
	  	public void switchToMenuIniziale(ActionEvent event )throws IOException {
	  		
	  		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/MenuIniziale.fxml"));
			setRoot(fxmlloader.load());
			changeScene(event);
			
		}
	
}
