/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.util.*;
import java.io.*;

/**
 * Classe che gestisce le canzoni: permette di cercarle, ottenerne i dati
 * e valutare le emozioni che trasmettono.
 * @author Edoardo Picazio
 */
public class Song {

    private String name, author, album;
    private int year, duration, id;

    // costruttore default : utile ai controller delle tabelle 
    public Song(String name, String author, String album, int year, int duration, int id) {
		
		this.name = name;
		this.author = author;
		this.album = album;
		this.year = year;
		this.duration = duration;
		this.id = id;
	}

    /**
	 *il costruttore prende in input l'inidice della canzone nel dataset
     *e da lì ottiene i dati.
     *@param id : indice della canzone nel dataset
     */
    public Song(int id) throws IOException, NumberFormatException {

        this.id = id;
        String[] data  = getSongData(id);
        
        this.id = Integer.parseInt(data[0]);
        this.name = data[1];
        this.album = data[2];
        this.author = data[3];
        this.duration = (int)Float.parseFloat(data[4]);
        this.year = Integer.parseInt(data[5]);
    }
    
    // getter necessari per i controller delle tabelle 
    /**
     * permette di ottenere l'indice
     * @return
     */
    public int getId() {
    	return id;
    }
    /**
     * permette di ottenere il titolo
     * @return
     */
    public String getName() {
		return name;
	}
    /**
     * permette di ottenere l'autore
     * @return
     */
	public String getAuthor() {
		return author;
	}
	/**
     * permette di ottenere l'album
     * @return
     */
	public String getAlbum() {
		return album;
	}
	/**
     * permette di ottenere l'anno di uscita
     * @return
     */
	public int getYear() {
		return year;
	}
	/**
     * permette di ottenere la durata
     * @return
     */
	public int getDuration() {
		return duration;
	}
	
    //Funzione per la ricerca di una canzone:
    //cerca i risultati in base al titolo, nome dell'artista e dell'album.
	/**
	 * Data in input una stringa si occupa di restituire una lista di canzoni i cui attributi matchano
	 * i valori della stringa.
	 * @param tipoRicerca : 1=titolo, 2=autore e anno, 3=artista, 4=album
	 * @param input : valori della ricerca
	 * @return lista di canzoni corrispondenti alla ricerca
	 */
    public static ArrayList<Song> searchSong(int tipoRicerca, String[] input) throws IOException, NumberFormatException, NumberFormatException{
        String[] data = new String[6];
        String line;
        ArrayList<Song> songList = new ArrayList<Song>();

        String path = getPath() + (File.separator + "Canzoni.dati.csv");
        BufferedReader br = new BufferedReader(new FileReader(path));
        //leggo la prima riga che non contiene i dati di nessuna canzone
        br.readLine();
        //Raggruppo tutte le canzoni in cui appare il dato cercato
        switch(tipoRicerca) {
	        case 1:
	        	//caso di ricerca per titolo
	        	String titolo, titoloCercato;
	        	//leggo riga per riga il dataset canzoni
	        	while((line = br.readLine()) != null) {
	        		//separo i dati
	        		data = line.split(",,");
	        		//assegno alle variabili i valori della ricerca (case Insensitive)
	        		titolo = data[1].toLowerCase();
	        		titoloCercato = input[0].toLowerCase();
	        		if(titolo.contains(titoloCercato))
	        			//Se il titolo cercato è contenuto in quello della canzone la aggiungo alla lista dei risultati
	        			songList.add(new Song(Integer.parseInt(data[0])));
	        	}
	        	break;
	        
	        case 2:
	        	//caso di ricerca per autore e anno
	        	String autore, anno, autoreCercato, annoCercato;
	        	//leggo riga per riga il dataset canzoni
	        	while((line = br.readLine()) != null) {
	        		//separo i dati
	        		data = line.split(",,");
	        		//assegno alle variabili i valori della ricerca (case Insensitive)
	        		autore = data[3].toLowerCase();
	        		autoreCercato = input[0].toLowerCase();
	        		anno = data[5];
	        		annoCercato = input[1];
	        		if(autore.contains(autoreCercato) && anno.equals(annoCercato))
	        			//Se l'anno e l'autore cercati sono contenuti in quelloi della canzone la aggiungo alla lista dei risultati
	        			songList.add(new Song(Integer.parseInt(data[0])));
	        	}
	        	break;
	        case 3:
	        	//caso di ricerca per autore
	        	String autore1, autoreCercato1;
	        	//leggo riga per riga il dataset canzoni
	        	while((line = br.readLine()) != null) {
	        		//separo i dati
	        		data = line.split(",,");
	        		//assegno alle variabili i valori della ricerca (case Insensitive)
	        		autore1 = data[3].toLowerCase();
	        		autoreCercato1 = input[0].toLowerCase();
	        		if(autore1.contains(autoreCercato1))
	        			//Se il titolo cercato è contenuto in quello della canzone la aggiungo alla lista dei risultati
	        			songList.add(new Song(Integer.parseInt(data[0])));
	        	}
	        	
	        	break;
	        case 4:
	        	//caso di ricerca per album
	        	String album, albumCercato;
	        	//leggo riga per riga il dataset canzoni
	        	while((line = br.readLine()) != null) {
	        		//separo i dati
	        		data = line.split(",,");
	        		//assegno alle variabili i valori della ricerca (case Insensitive)
	        		album = data[2].toLowerCase();
	        		albumCercato = input[0].toLowerCase();
	        		if(album.contains(albumCercato))
	        			//Se il titolo cercato è contenuto in quello della canzone la aggiungo alla lista dei risultati
	        			songList.add(new Song(Integer.parseInt(data[0])));
	        	}
	        	
	        	break;
        }
        br.close();

        return(songList);
    }
    /**
     * permette di asseggnare una valutazione ad una canzone
     * @param utente: utente che ha fatto la valutazione
     * @param idCanzone: indice della canzone
     * @param listaVoti: lista dei voti assegnati
     * @param listaCommenti: lista dei commenti scritti
     * @throws IOException
     */
 	public static void RegistraEmozioni(Login utente , int idCanzone , int[] listaVoti , String[] listaCommenti) throws IOException {
 		
 		String path, save  = String.format("%s,,%d",utente.getUserName(),idCanzone);
 		Writer output;
 		//creo la stringa che salverà la playlist sul file
 		int i, length = listaVoti.length ;
 		for(i=0;i<length; i++) {
 			if(listaCommenti[i].equals(""))
 				listaCommenti[i] = " ";
 			save += ",,"+listaVoti[i]+",,"+listaCommenti[i];
 		}
 		
 		//apro il file in modalità append per aggiungere i dati di una canzone
         //per associare i dati alla canzone uso l'id (posizione nel file Emozioni.dati.csv)
 		path = getPath() + (File.separator + "Emozioni.dati.csv");
         output = new BufferedWriter(new FileWriter(path, true));
         output.append(save + System.lineSeparator());
         output.close();
         
         
 	}

    //funzione che preleva i dati della IDesima canzone nel dataset
    private String[] getSongData(int id) throws IOException, FileNotFoundException {
        
    	String path = getPath() + (File.separator + "Canzoni.dati.csv"), line="";
        BufferedReader br = new BufferedReader(new FileReader(path));

        for(int i=0;i<=id; i++)
            line = br.readLine();
        br.close();

        String[] values = line.split(",,");

        return values;
    }

    /**
     * funzione per ottenere le valutazione di ciascun utente riguardo una canzone dato l'id
     * @param id : indice del brano
     * @return array contenente ogni valutazione associata allo userId dell'utente che l'ha fatta
     */
    public static List<String[]> getEmotionsData(int id) throws IOException, FileNotFoundException {
        String path = getPath() + (File.separator + "Emozioni.dati.csv"), line;
        String[] rating = new String[19];
        List<String[]> ratings = new ArrayList<String[]>();
        BufferedReader br = new BufferedReader(new FileReader(path));

        while((line = br.readLine()) != null) {
            rating = line.split(",,");

            if(rating[1].equals( Integer.toString(id))) {
                ratings.add(rating);
            }
        }
        br.close();

        return ratings;
    }
    
    /**
     * funzione che aggrega le valutazioni di una canzone
     * @param id: id della canzone
     * @return restituisce una lista di oggetti VisualizzaEmozioniDati.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static ArrayList<VisualizzaEmozioniDati> getEmotionsResume(int id) throws FileNotFoundException, IOException {
    	ArrayList<VisualizzaEmozioniDati> listaDatiEmozioni = new ArrayList<VisualizzaEmozioniDati>();
    	List<String[]> valutazioni = getEmotionsData(id);
   
    	int emozione,valutazione,len = valutazioni.size(), numUtenti, voto;
    	float media = 0;
    	Emozioni[] emozioni = Emozioni.getlistaEmozioni() ; 
    	
    	//il ciclo legge più volte tutte le valutazioni, una volta per ciascuna emozione
    	for(emozione=0;emozione<9;emozione++) {
    		//inizializzo i dati
    		media=0;
    		numUtenti=0;
    		//per ogni funzione
    		for(valutazione = 0; valutazione<len;valutazione++) {
    			//Il valore emozione serve per il costruttore di VisualizzaEmozioniDati
    			//quindi lo adatto all'indice del voto nella valutazione.
    			voto = Integer.parseInt(valutazioni.get(valutazione)[(emozione * 2)+2]);
    			if(voto!=0) {
	    			media += voto;
	    			numUtenti++;
    			}
    		}
    		if(numUtenti!=0) 
    		  //calcolo la media
    		  media/=numUtenti;
   
    		listaDatiEmozioni.add(new VisualizzaEmozioniDati(emozioni[emozione].getNome(),emozioni[emozione].getDescrizione(),numUtenti,media));
    	}
    	
    	return listaDatiEmozioni;
    }
    /**
     * restituisce in forma aggregata i dati delle valutazioni di una canzone
     * @param id: indice della canzone
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static ArrayList<VisualizzaEmozioniDati> VisualizzaEmozioniBrano(int id ) throws IOException, FileNotFoundException  {
    	
        
        
        ArrayList<VisualizzaEmozioniDati> listaDatiEmozioni = new ArrayList<VisualizzaEmozioniDati>();
        
        String[] utenteEId = new String[1] ; 
        String[] votiECommenti = new String[18];
        
        int[] numUtenti = {0,0,0,0,0,0,0,0,0};
        float[] mediaVoti = {0,0,0,0,0,0,0,0,0};
        ArrayList<ArrayList<String>> listaCommenti = new ArrayList<ArrayList<String>>();
        for(int i = 0 ; i < 9 ; i++) {
        	listaCommenti.add(new ArrayList<String>());
        }
        
        String path = getPath() + (File.separator + "Emozioni.dati.csv"), line;
        BufferedReader br = new BufferedReader(new FileReader(path));
        
        // salta la prima riga 
        br.readLine();
        
        while((line = br.readLine()) != null) {

        	// trova l'indice che separa ( utente,,IdCanzone )  da voti e commenti
        	int indice = line.indexOf(",,", line.indexOf(",,") + 1 ); 
        	
        	utenteEId = line.substring(0,indice).split(",,");
        	votiECommenti = line.substring(indice+2).split(",,");
        	
        	if(id == Integer.parseInt(utenteEId[1])){
        	
        	   for(int i = 0 ; i< votiECommenti.length-1 ; i+=2) {
        		
        		   if(Integer.parseInt(votiECommenti[i]) != 0 ) {
        		      numUtenti[i/2]++ ; 
        		      mediaVoti[i/2]+= (float) Integer.parseInt(votiECommenti[i]);
        		      if(! ( votiECommenti[i+1].equals(" ")) )
        		         listaCommenti.get(i/2).add(utenteEId[0] + ",," + votiECommenti[i] + ",," + votiECommenti[i+1]);
        		    }
        		
        	   }
        	}
        	
            
        }
	     
        for(int i = 0 ; i< 9 ; i++) {
        	if(numUtenti[i]!=0) 
      		  //calcolo la media
      		  mediaVoti[i]/=numUtenti[i];
        	String nome = Emozioni.values()[i].getNome();
        	String descrizione = Emozioni.values()[i].getDescrizione() ; 
        	listaDatiEmozioni.add(new VisualizzaEmozioniDati(nome,descrizione,numUtenti[i],mediaVoti[i],listaCommenti.get(i))) ; 
        }
       
        br.close();

        return listaDatiEmozioni ; 
    }
    
    
    //per ottenere il filePath alla cartella /data (ogni OS)
    private static String getPath() {
    	String userDirectory = System.getProperty("user.dir");
        return (userDirectory + File.separator + "data");
    }
    /**
     * permette di ottenere le valutazioni effettuate da un utente
     * @param utente: utente da cercare
     * @param listaIndiciCanzoni: canzoni a cercare
     * @return
     * @throws IOException
     */
	public static ArrayList<String> getEmozioniUtente(Login utente, ArrayList<Integer> listaIndiciCanzoni) throws IOException {
		
		ArrayList<String> canzoniValutate = new ArrayList<String>();
		
		for(int i = 0 ; i< listaIndiciCanzoni.size() ; i++ ) {
			canzoniValutate.add(" ");
		}
		
		String[] utenteEId = new String[1] ; 
		String votiECommenti ; 
		
		String path = getPath() + (File.separator + "Emozioni.dati.csv"), line;
	    BufferedReader br = new BufferedReader(new FileReader(path));
	        
	    // salta la prima riga 
	    br.readLine();
	    
	        
	    while((line = br.readLine()) != null) {

	        // trova l'indice che separa ( utente,,IdCanzone )  da voti e commenti
	        int indice = line.indexOf(",,", line.indexOf(",,") + 1 ); 
	        	
	        utenteEId = line.substring(0,indice).split(",,");
	        votiECommenti = line.substring(indice+2);
	        	
	        if(utente.getUserName().equals(utenteEId[0]) && listaIndiciCanzoni.contains(Integer.valueOf(utenteEId[1]))) 
	           canzoniValutate.set(listaIndiciCanzoni.indexOf(Integer.parseInt(utenteEId[1])),votiECommenti);
	            
	    }
	    br.close();
		
		return canzoniValutate ;
	}	

    
}
