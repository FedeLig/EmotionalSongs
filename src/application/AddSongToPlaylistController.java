package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

public class AddSongToPlaylistController extends SearchSongTableController {
	
	private Playlist playlist ; 
	
	public void tornaAlPrecedente(ActionEvent e ) throws IOException {
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/CreazionePlaylist.fxml"));
		CreazionePlaylistController controller = new CreazionePlaylistController(); 
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
    protected void  onHyperLinkCliked (ActionEvent e , int indice){
		 
		//System.out.print(indice);
		Song canzone = getSongObservableList().get(indice);
		playlist.aggiungiCanzone(canzone);
		
	}
	
	public void setPlaylist( Playlist playlist ) {
		this.playlist = playlist ; 
	}
	
}