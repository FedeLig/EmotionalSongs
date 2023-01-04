package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

public class AddSongToPlaylistController extends SearchSongTableController {
	
	private Playlist playlist ; 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		listaOpzioni = new ArrayList<String>(Arrays.asList("  titolo","  autore e anno"));
		
		sceltaFiltro.setItems(FXCollections.observableArrayList(listaOpzioni));
		 
		sceltaFiltro.setValue("");
		
		sceltaFiltro.setOnAction(event -> { ChangeFilter(); });
		
		searchButton.setOnAction(event -> { 
			ArrayList<Song> risultatiRicerca = new ArrayList<Song>();
			try {
				risultatiRicerca = Song.searchSong("testify");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			songObservableList = FXCollections.observableArrayList(risultatiRicerca);
			UpdateTable(songObservableList);  
		});
		
		goBackButton.setOnAction(event -> {     
			
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/CreazionePlaylist.fxml"));
			CreazionePlaylistController controller = new CreazionePlaylistController(); 
			fxmlloader.setController(controller);
			controller.setPlaylist(playlist);
			try {
				setRoot(fxmlloader.load());
				changeScene(event);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
			
		
		tabellaCanzoni.setPlaceholder(new Label("La tabella è vuota"));
		
	}
	
	@Override 
	protected   String getTestoHyperLink(){
		return "Aggiungi" ; 
	}
	@Override 
    protected void  onHyperLinkCliked (ActionEvent e , int indice){
		 
		Song canzone = songObservableList.get(indice);
		playlist.getListaCanzoni().add(canzone);
		
	}
	
	public void setPlaylist( Playlist playlist ) {
		this.playlist = playlist ; 
	}
	
}