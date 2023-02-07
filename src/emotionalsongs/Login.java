/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.util.*;
import java.io.*;

/**
 * Classe c
 * @author Edoardo Picazio 
 */
public class Login {
	/**
	 * </>userName</> : nome dell' utente corrente , </>password</> : password dell' utente corrente , 
	 */
    private String userName, password;
    /**
	 * </>UserPlaylists</> : lista delle playlist dell'utente corrente 
	 */
    private List<Playlist> UserPlaylists ; 
    /**
	 * </>logged</> : indica se un utente è loggato 
	 */
    private boolean logged;

    /**
     * permette la registrazione di un nuovo utente.
     * @param dati : array di valori contenenti tutti i dati necessari alla registrazione
     */
    public Login(String[] dati) throws IOException {    
        //Aggiungiamo il nome utente al file UtentiRegistrati.csv
    	//L'ordine dei dati è: nome,cognome,cf,indirizzo,email,userId,password
        String newLine = String.format("%s,%s,%s,%s,%s,%s,%s",dati[0],dati[1],dati[2],dati[3],dati[4],dati[5],dati[6]);
        String path = getPath() + (File.separator + "UtentiRegistrati.dati.csv");
        BufferedWriter output = new BufferedWriter(new FileWriter(path, true));
        output.append(newLine + System.lineSeparator());
        output.close();
        //segnamo che l'utente è loggato
        this.logged = true;
    }
    
    /**
     * Permette di effettuare un login grazie usando le credenziali
     * @param userName : nome utente
     * @param password : password dell'account
     */
    public Login(String userName, String password) throws IOException {
        //cerco fra gli utenti una coppia corrispondente di userName e password.
        List<String[]> users = getUsers();

        for(String[] user : users) {
            if(userName.equals(user[5]) && password.equals(user[6])) {
                this.logged = true;
                this.userName = userName;
                this.password = password;
                break;
            }
        }
    }
    /* I tre metodi seguenti servono a verificare l'unicità di un atttributo.
     * Sono statici poichè il loro utilizzo è riferito a tutta la classe e non 
     * a una particolare istanza.
     * Si comportano tutti allo stesso modo: prendono come argomento un attributo
     * e verificano che non siano presenti doppioni nei dati salavati.
     */

	/**
     * Permette di verificare la validità del codice fiscale
     * @param cf : codice fiscale
     * @return vero se il codice è valido, falso altrimenti
     */
    public static boolean checkCf(String cf) throws IOException {
        boolean check = true;
        List<String[]> users = getUsers();

        for(String[] user : users) {
            if(user[2].equals(cf)) {
                check = false;
                break;
            }
        }
        return check; 
    }
    
    /**
     * Permette di verificare la validità dell'email
     * @param email : email utente
     * @return vero se l'email è valida, falso altrimenti
     */
    public static boolean checkEmail(String email) throws IOException {
        boolean check = true;
        List<String[]> users = getUsers();

        for(String[] user : users) {
            if(user[4].equals(email)) {
                check = false;
                break;
            }
        }
        return check; 
    }

    /**
     * Permette di verificare la validità dello userId
     * @param userId : codice utente
     * @return vero se il codice è valido, falso altrimenti
     */
    public static boolean checkUserId(String userId) throws IOException {
        boolean check = true;
        List<String[]> users = getUsers();

        for(String[] user : users) {
	        if(user[5].equals(userId)) {
	            check = false;
	            break;
	        }
        }
        return check;
    }

    // Il metodo isLogged è utile per verificare se una registrazione o un login
    // sono andati a buon fine.
    /**
     * Permette di verificare se il login/registrazione è andata a buon fine
     * @return vero se l'utente è loggato, falso altrimenti
     */
    public boolean isLogged() {
        return this.logged;
    }


    /**
     * Ritorna la lista delle playlist di un utente dato in input 
     * @param utente : utente di cui vogliamo trovare le playlist 
     * @return lista delle playlist di un utente 
     */
 	public static List<Playlist> getPlaylistsUtente(String userName) throws IOException {
 			
 		List<Playlist> playlistUtenteEsistenti = new ArrayList<Playlist>() ; 
 			
 		List<String[]> playlists = getPlaylists() ; 
 			
 		for(String[] playlist : playlists ) {
 				
 	        if(playlist[0].equals(userName)) {
 	            
 	           ArrayList<Integer> listaIndiciPlaylist = new ArrayList<Integer>() ; 
 	               
 	           for(int i = 2  ; i<=(playlist.length - 1) ; i++) {
 	               listaIndiciPlaylist.add(Integer.parseInt(playlist[i]));
 	           }
 	               
 	           playlistUtenteEsistenti.add(new Playlist(playlist[1],listaIndiciPlaylist)) ; 
 	        }
 	    }
 			
 		return playlistUtenteEsistenti ; 
 	}
 		
 	/**
     * Ritorna tutte le playlist contenute nel file  Playlist.dati.csv
     * @return lista di tutte playlist 
     */
 	private static List<String[]> getPlaylists() throws IOException {
 	      //ottengo i dati di tutti gli utenti e li divido in un array per potervi accedere singolarmente.
 	      List<String[]> list = new ArrayList<String[]>();
 	      String line;
 	      String path = getPath() + (File.separator + "Playlist.dati.csv");
 	      BufferedReader br = new BufferedReader(new FileReader(path));

 	      while((line = br.readLine()) != null)
 	      list.add(line.split(",,"));

 	      list.remove(0);
 	        
 	      br.close();

 	      return list;
 	 }
 	/**
     * Ritorna tutte gli utenti contenuti nel file  UtentiRegistrati.dati.csv
     * @return lista di tutte playlist 
     */
    private static List<String[]> getUsers() throws IOException {
        //ottengo i dati di tutti gli utenti e li divido in un array per potervi accedere 
        //singolarmente.
        List<String[]> list = new ArrayList<String[]>();
        String line;
        String path = getPath() + (File.separator + "UtentiRegistrati.dati.csv");
        BufferedReader br = new BufferedReader(new FileReader(path));

        while((line = br.readLine()) != null)
            list.add(line.split(","));

        list.remove(0);
        
        br.close();

        return list;
    }
    
    /**
     * Permette di ottenere il filePath alla cartella /data (ogni OS)
     */
    private static String getPath() {
        //ottengo la directory del progetto
        String userDirectory = System.getProperty("user.dir");
        return (userDirectory + File.separator + "data");
    }


    /**
	 * Ritorna la lista delle playlist dell' utente corrente 
	 * @return  lista delle playlist dell'utente corrente 
	 */
	public List<Playlist> getUserPlaylists() {
		return UserPlaylists;
	}

    /**
	 * imposta la lista delle playlist dell' utente corrente 
	 * @param userPlaylists :  lista delle playlist dell'utente corrente 
	 */
	public void setUserPlaylists(List<Playlist> userPlaylists) {
		UserPlaylists = userPlaylists;
	}

    /**
	 * Aggiunge una playlist in input alla lista delle playlist dell' utente 
	 * @param playlist 
	 */
	public void addToUserplaylists(Playlist playlist) {
		this.UserPlaylists.add(playlist);
	}

    /**
	 * ritorna l'identificatore dell' utente 
	 * @return lo username (  id utente ) 
	 */
	public String getUserName() {
		return userName;
	}


}
