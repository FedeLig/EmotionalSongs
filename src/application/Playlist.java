package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Playlist {

	private String nomePlaylist ; 
	private ObservableList<Song> listaCanzoni ;
	
	
	public Playlist(String nomePlaylist) {
		
		this.nomePlaylist = nomePlaylist;
		this.listaCanzoni = FXCollections.observableArrayList();
	} 
	
	public Playlist(String nomePlaylist, ObservableList<Song> listaCanzoni) {
	
		this.nomePlaylist = nomePlaylist;
		this.listaCanzoni = listaCanzoni;
	}

	public String getNomePlaylist() {
		return nomePlaylist;
	}

	public void setNomePlaylist(String nomePlaylist) {
		this.nomePlaylist = nomePlaylist;
	}

	public ObservableList<Song> getListaCanzoni() {
		return listaCanzoni;
	}

	public void setListaCanzoni(ObservableList<Song> listaCanzoni) {
		this.listaCanzoni = listaCanzoni;
	} 
	
	public void svoutaListaCanzoni() {
		
		listaCanzoni.clear();  
	}
}