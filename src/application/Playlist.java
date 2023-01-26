package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Playlist {

	private String nomePlaylist, autore; 
	private ObservableList<Song> listaCanzoni;
	
	
	public Playlist(String nomePlaylist, String userId) {
		
		this.nomePlaylist = nomePlaylist;
		this.listaCanzoni = FXCollections.observableArrayList();
	} 
	
	public Playlist(String nomePlaylist, String userId, ObservableList<Song> listaCanzoni) throws IOException {
	
		this.nomePlaylist = nomePlaylist;
		this.listaCanzoni = listaCanzoni;
		String path, save  = String.format("%s,%s",nomePlaylist,userId);
		Writer output;
		//creo la stringa che salverà la palylist sul file
		int i, length = listaCanzoni.size();
		for(i=0;i<length; i++)
			save += listaCanzoni.get(i);
		
		//apro il file in modalità append per aggiungere i dati di una canzone
        //per associare i dati alla canzone uso l'id (posizione nel file Songs.csv)
		path = getPath() + (File.separator + "Playlist.dati.csv");
        output = new BufferedWriter(new FileWriter(path, true));
        output.append(save + System.lineSeparator());
        output.close();
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
	
	private static String getPath() {
    	String userDirectory = System.getProperty("user.dir");
        return (userDirectory + File.separator + "data");
    }
}