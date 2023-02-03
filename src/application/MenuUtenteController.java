package application;

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
	
	@FXML
	private Button toSearchButton ;
	@FXML
	private Button newPlaylistButton ;
	
	private Login utente ;
	
	// adattare in modo che prenda il login , togliere IOException 
	public MenuUtenteController(Login utente) throws IOException {
			
		this.utente = utente ; 
		
	}
		
	 /**
	 * Porta l'utente all'interfaccia che contiene la funzionalità
	 * di ricerca nella Repository di canzoni 
	 *@param e : evento che scatena l'azione 
	 *@throws IOException : cosa potrebbe accadere  
	 */
     @FXML
	 public void switchToRicercaRepository(ActionEvent e ) throws IOException {
			
		   RicercaInRepositoryController controller = new RicercaInRepositoryController();
		   FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/RicercaInRepository.fxml"));
		   fxmlloader.setController(controller);
		   controller.setUtente(utente);
		   controller.setIndirizzoTabellaPrecedente("/MenuUtente.fxml");
		   setRoot(fxmlloader.load());
		   changeScene(e);
     }
	 /**
	 * Porta l'utente all'interfaccia che contiene la funzionalità
	 * al primo passo della funzionalità di creazione delle playlist : la scelta del nome di essa 
	 *@param e : evento che scatena l'azione 
	 *@throws IOException : cosa potrebbe accadere 
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
	  * Porta l'utente all'interfaccia che contiene la funzionalità
	  * di visualizzazione delle playlist da lui create 
	  *@param e : evento che scatena l'azione 
	  *@throws IOException : cosa potrebbe accadere 
	  */
	  @FXML
	   public void switchToVisualizzaPlaylist(ActionEvent e ) throws IOException {
			
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/SelezionaPlaylist.fxml"));
			SelezionaPlaylistController controller = new SelezionaPlaylistController(utente);
			fxmlloader.setController(controller); 
			setRoot(fxmlloader.load());
			changeScene(e);
			
	   }
	   @FXML 
	  	public void switchToMenuIniziale(ActionEvent event )throws IOException {
	  		
	  		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/MenuIniziale.fxml"));
			setRoot(fxmlloader.load());
			changeScene(event);
			
		}
	
}
