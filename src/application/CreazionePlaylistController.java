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

/**
 * La classe che si occupa di gestire la creazione delle playlist
 * contiene i controlli per tutte le operazioni effettuabili nella schermata "playlist"
 * @author Ligas
 *
 */
public class CreazionePlaylistController extends SongTableController {

	@FXML
	private Button RicercaCanzone ;
	@FXML
	private Button TornaAlMenu ;
	
	private Playlist playlist ; 
	private Login utente; 
	
	public CreazionePlaylistController(Login utente) {
		this.utente = utente; 
	}
	/**
	 * il  metodo Initialize viene chiamato (una e una sola volta)
     * dal controller appena dopo la finestra è stata "caricata" con successo 
     * e contiene la tabella che vogliamo visualizzare la prima volta
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		setSongObservableList(playlist.getListaCanzoni());
		
		if (getSongObservableList().isEmpty()) 
			getTabellaCanzoni().setPlaceholder(new Label("La playlist non contiene canzoni"));
		else 
			UpdateTable(getSongObservableList());
		
	}
    @FXML
	public void salvaPlaylist(ActionEvent e ) throws IOException {
		
		if(!(playlist.getListaCanzoni().isEmpty())) {
		   playlist.RegistraPlaylist();
		   int i = 0  ;
		   ArrayList<Integer> listaIndiciCanzoni = new ArrayList<Integer>() ;
		   while ( i < playlist.getListaCanzoni().size()  ) {
			     listaIndiciCanzoni.add(playlist.getListaCanzoni().get(i).getId());
			     i++ ; 
		   }
		   playlist.setListaIndiciCanzoni(listaIndiciCanzoni);
		   utente.addToUserplaylists(playlist);
		   createAlert("la playlist e' stata salvata");
		}
		else {
		   createAlert("Errore : la playlist è vuota");
		}
		
	}
    @FXML 
    public void switchToRicercaAvanzataAutore(ActionEvent e) throws IOException {
    	
    	RicercaAvanzataController controller = new RicercaAvanzataController(utente,"autore");
    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/RicercaAvanzata.fxml"));
    	fxmlloader.setController(controller);
    	controller.setPlaylist(playlist);
    	setRoot(fxmlloader.load());
		changeScene(e);
    }
    
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
	 * Cambia la scena in "ricerca canzone"
	 * @param e : evento "aggiungi canzone"
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
	
	@FXML 
	public void switchToMenuUtente(ActionEvent e) throws IOException { 
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/MenuUtente.fxml"));
		MenuUtenteController controller = new MenuUtenteController(utente);
	    fxmlloader.setController(controller);
	    playlist.svoutaListaCanzoni();
		setRoot(fxmlloader.load());
		changeScene(e);
		
		
	}

	@Override
	protected void onHyperLinkCliked(ActionEvent e ,int indice ) {
		
		getSongObservableList().remove(indice);
		UpdateTable(getSongObservableList());
	}

	@Override
	protected String getTestoHyperLink() {
		return "elimina"; 
	}
	
	public void setPlaylist( Playlist playlist ) {
		this.playlist = playlist ; 
	}
	
}
