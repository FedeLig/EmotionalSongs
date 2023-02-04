package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SelezionaCanzoneController extends SongTableController {

    private Playlist playlist ; 
	
	private ArrayList<Song> canzoniPlaylist ; 
	
	private ObservableList<Playlist> elencoPlaylistUtente; 
	
	private Login utente; 

	public SelezionaCanzoneController( Login utente , Playlist playlist ) {
		
		this.playlist = playlist; 
		this.utente = utente ; 
		
		canzoniPlaylist = new ArrayList<Song>() ; 
		ArrayList<Integer> ListaIndiciCanzoni = playlist.getlistaIndiciPlaylist() ; 
		
		for(Integer indice : ListaIndiciCanzoni) {
			try {
				
				canzoniPlaylist.add(new Song(indice));
				
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		setSongObservableList(FXCollections.observableArrayList(canzoniPlaylist));
		UpdateTable(getSongObservableList()); 
	}

	@Override
	protected void onHyperLinkCliked(ActionEvent e, int indice) {
		
		Song CanzoneSelezionata = canzoniPlaylist.get(indice);
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/InserisciEmozioni.fxml"));
	   
	    try {
			setRoot(fxmlloader.load());
			changeScene(e);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
	    InserisciEmozioniController controller = fxmlloader.getController() ;
		controller.setUtente(utente);
		controller.setIdCanzone(CanzoneSelezionata.getId());
		controller.setPlaylist(playlist);
		controller.setControllerCorrente(controller);
		
	}

	
	@FXML
    public void switchToMenuUtente(ActionEvent e) throws IOException {

    	FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/MenuUtente.fxml"));
    	MenuUtenteController controller = new MenuUtenteController(utente);
    	fxmlloader.setController(controller);
		setRoot(fxmlloader.load());
		changeScene(e);
		
    }

	@FXML 
	public void switchToVisualizzaPlaylist(ActionEvent e )throws IOException{
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/SelezionaPlaylist.fxml"));
		SelezionaPlaylistController controller = new  SelezionaPlaylistController(utente);
		fxmlloader.setController(controller); 
		setRoot(fxmlloader.load());
		changeScene(e);
		
	}
	
	public void setElencoPlaylistUtente( ObservableList<Playlist> elencoPlaylistUtente ) {
		this.elencoPlaylistUtente =  elencoPlaylistUtente ; 
	}

	@Override
	protected String getTestoHyperLink() {
		return "Inserisci Emozione" ;
	}
}
