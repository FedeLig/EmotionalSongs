/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * La classe playlist permette di creare nuove playlist e ottenere dati di quelle esistenti
 * @author Edoardo Picazio
 *
 */
public class Playlist {

	private String nomePlaylist, autore; 
	private ObservableList<Song> listaCanzoni;
	private ArrayList<Integer> listaIndiciPlaylist ;
	
	/**
	 * Costruttore che prende in input solo il nome della playlist e dell'utente
	 * i brani venogno aggiunti in seguito
	 * @param nomePlaylist
	 * @param userId
	 */
	public Playlist(String nomePlaylist, String userId) {
		
		this.nomePlaylist = nomePlaylist;
		this.autore = userId;
		this.listaCanzoni = FXCollections.observableArrayList();
	} 
	
	/**
	 * il costruttore di base crea una playlist contente una lista di canzoni che viene fornita in input
	 * @param nomePlaylist : titolo della playlist
	 * @param userId : nome utente del creatore della playlist
	 * @param listaCanzoni : lista dei brani contenuti nella playlist
	 * @throws IOException
	 */
	public Playlist(String nomePlaylist, String userId, ObservableList<Song> listaCanzoni) throws IOException {
	
		this.nomePlaylist = nomePlaylist;
		this.autore = userId;
		this.listaCanzoni = listaCanzoni;
		RegistraPlaylist();
	}
	/**
	 * Questo costruttore crea una palylist con tutti i brani dell'autore/album specificato in input
	 * @param nomePlaylist : nome della playlist
	 * @param userId : nome utente del creatore della playlist
	 * @param parametroRicerca : nome dell'artista/album
	 * @param tipoRicerca : 1 = autore, 2 = album
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public Playlist(String nomePlaylist, String userId, String parametroRicerca, int tipoRicerca) throws NumberFormatException, IOException {
		this.nomePlaylist = nomePlaylist;
		this.autore = userId;
		String[] argRicerca = {parametroRicerca};
		this.listaCanzoni = FXCollections.observableList(Song.searchSong(tipoRicerca + 2, argRicerca));
		RegistraPlaylist();
		
	}
	
    public Playlist(String nomePlaylist, ArrayList<Integer> listaIndiciPlaylist) {
		
		this.nomePlaylist = nomePlaylist;
		this.listaIndiciPlaylist = listaIndiciPlaylist ; 
	}
	
    public Playlist(String nomePlaylist) {
		
		this.nomePlaylist = nomePlaylist;
		this.listaCanzoni = FXCollections.observableArrayList();
	} 
    /**
     * @return nome autore
     */
	public String getAutore() {
		return autore;
	}
	/**
	 * permette di impostare il nome dell'autore
	 * @param autore
	 */
	public void setAutore(String autore) {
		this.autore = autore;
	}
	/**
	 * 
	 * @return nome della playlist
	 */
	public String getNomePlaylist() {
		return nomePlaylist;
	}
	/**
	 * permette di impostare il nome della playlist
	 * @param nomePlaylist
	 */
	public void setNomePlaylist(String nomePlaylist) {
		this.nomePlaylist = nomePlaylist;
	}
	/**
	 * 
	 * @return lista delle canzoni nella playlist
	 */
	public ObservableList<Song> getListaCanzoni() {
		return listaCanzoni;
	}
	/**
	 * permette di impostare la lista canzoni
	 * @param listaCanzoni
	 */
	public void setListaCanzoni(ObservableList<Song> listaCanzoni) {
		this.listaCanzoni = listaCanzoni;
	} 
	
	public void aggiungiCanzone(Song canzone) {
		listaCanzoni.add(canzone);
	}
	
	public void svoutaListaCanzoni() {
		
		listaCanzoni.clear();  
	}
	
	/**
	 * salva in memoria la playlist nel file Playlist.dati.csv
	 * @throws IOException
	 */
	public void RegistraPlaylist() throws IOException {
		String path, save  = String.format("%s,,%s",autore,nomePlaylist);
		Writer output;
		//creo la stringa che salverà la playlist sul file
		int i, length = listaCanzoni.size();
		for(i=0;i<length; i++)
			save += ",,"+listaCanzoni.get(i).getId();
		
		//apro il file in modalità append per aggiungere i dati di una canzone
        //per associare i dati alla canzone uso l'id (posizione nel file Songs.csv)
		path = getPath() + (File.separator + "Playlist.dati.csv");
        output = new BufferedWriter(new FileWriter(path, true));
        output.append(save + System.lineSeparator());
        output.close();
	}
	
	private static String getPath() {
    	String userDirectory = System.getProperty("user.dir");
        return (userDirectory + File.separator + "data");
    }
	
	/**
	 * restituisce gli id delle canzoni salvate nella raccolta
	 * @return
	 */
	public ArrayList<Integer> getlistaIndiciPlaylist(){
		return listaIndiciPlaylist ; 
	}

	public void setListaIndiciCanzoni(ArrayList<Integer> listaIndiciCanzoni) {
		
		listaIndiciPlaylist  = listaIndiciCanzoni ; 
		
	}
	/**
	 * verifica se una canzone è contenuta nella playlist
	 * @param canzoneDaInserire
	 * @return vero se la canzone è presente, falso altrimenti
	 */
	public boolean contiene(Song canzoneDaInserire ) {
		
		boolean canzoneContenuta = false ; 
		
		int i = 0 ; 
		while(!canzoneContenuta && i < listaCanzoni.size()) {
			
			if(listaCanzoni.get(i).getId() == canzoneDaInserire.getId())
               canzoneContenuta = true ; 
			i++; 
		}
		
		return canzoneContenuta;
	}
}