package application;

import java.util.*;
import java.io.*;

// nota : se Song non fosse public RepositoryTableController non funzionerebbe 
/**
 * Classe che gestisce le canzoni: permette di cercarle, ottenerne i dati
 * e valutare le emozioni che trasmettono.
 * @author Picazio
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
        String line="";
        String[] data  = getSongData(id);
        
        this.id = Integer.parseInt(data[0]);
        this.name = data[1];
        this.album = data[2];
        this.author = data[3];
        this.duration = (int)Float.parseFloat(data[4]);
        this.year = Integer.parseInt(data[5]);
    }
    
    // getter necessari per i controller delle tabelle 
    public int getId() {
    	return id;
    }
    public String getName() {
		return name;
	}

	public String getAuthor() {
		return author;
	}


	public String getAlbum() {
		return album;
	}

	public int getYear() {
		return year;
	}

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
        String line, searchLine;
        ArrayList<Song> songList = new ArrayList<Song>();

        String path = getPath() + (File.separator + "Songs.csv");
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
	        	//caso di rcerca per autore e anno
	        	//caso di ricerca per titolo
	        	String autore, anno, autoreCercato, annoCercato;
	        	//leggo riga per riga il dataset canzoni
	        	while((line = br.readLine()) != null) {
	        		//separo i dati
	        		data = line.split(",,");
	        		//assegno alle variabili i valori della ricerca (case Insensitive)
	        		autore = data[2].toLowerCase();
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
     * 
     * @param userId
     * @throws IOException
     */
    public void inserisciEmozioniBrano(String userId, String[] rating) throws IOException {
        boolean rated = false;
        String line, oldLine="", newLine = String.format("%d,,%s,,%s,,%s,,%s,,%s,,%s,,%s,,%s,,%s,,%s",this.id,userId,rating[0],rating[1],rating[2],rating[3],rating[4],rating[5],rating[6],rating[7],rating[8]); 
        String path = getPath() + (File.separator + "Emozioni.csv");
        //String[] data = new String[6];
        Writer output;
        //verifichiamo se questa canzone è già stata valutata o meno.
        BufferedReader br = new BufferedReader(new FileReader(path));
        while((line = br.readLine()) != null) {
            String[] data = line.split(",,");
            
            if(data[0].equals(Integer.toString(this.id)) && data[1].equals(userId)) {
                rated = true;
                oldLine = line;
                break;
            }
        }
        br.close();

        //se la canzone è già stata valutata rimpiaziamo la vecchia valutazione
        if(rated) {
            //Instantiating the Scanner class to read the file
            Scanner sc = new Scanner(new File(path));
            //instantiating the StringBuffer class
            StringBuffer buffer = new StringBuffer();
            //Reading lines of the file and appending them to StringBuffer
            while (sc.hasNextLine()) {
                buffer.append(sc.nextLine()+System.lineSeparator());
            }
            String fileContents = buffer.toString();
            //closing the Scanner object
            sc.close();

            //Replacing the old line with new line
            fileContents = fileContents.replaceAll(oldLine, newLine);
            //instantiating the FileWriter class
            FileWriter writer = new FileWriter(path);
            writer.append(fileContents);
            writer.flush();
            writer.close();

        }else {
            //apro il file in modalità append per aggiungere i dati di una canzone
            //per associare i dati alla canzone uso l'id (posizione nel file Songs.csv)
            output = new BufferedWriter(new FileWriter(path, true));
            output.append(newLine + System.lineSeparator());
            output.close();
        }
    }

    //per ottenere il filePath alla cartella /data (ogni OS)
    private static String getPath() {
    	String userDirectory = System.getProperty("user.dir");
        return (userDirectory + File.separator + "data");
    }	

    //funzione che preleva i dati della IDesima canzone nel dataset
    private String[] getSongData(int id) throws IOException, FileNotFoundException {
        
    	String path = getPath() + (File.separator + "Songs.csv"), line="";
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
    public List<String[]> getEmotionsData(int id) throws IOException, FileNotFoundException {
        boolean check = false;
        String path = getPath() + (File.separator + "Emozioni.csv"), line;
        String[] rating = new String[3];
        List<String[]> ratings = new ArrayList<String[]>();
        BufferedReader br = new BufferedReader(new FileReader(path));

        while((line = br.readLine()) != null) {
            rating = line.split(",,");

            if(rating[0].equals( Integer.toString(this.id))) {
                ratings.add(rating);
            }
        }
        br.close();

        return ratings;
    }

    /**
     * Stampa a schermo i dati di una canzone
     */
    public void printSongData() {
        String line = String.format("Nome: %s \nArtista: %s \n",this.name,this.author);
        System.out.print(line);
    }
    
    /**
     * Stampa a schermo i valori delle emozioni di una canzone, per ciascun utente che l'ha valutata
     * @throws IOException
     */
    public void printEmotionsData() throws IOException {
        int i;
        List<String[]> ratings = getEmotionsData(this.id);
        String[] rating = new String[3];
        String line;

        for(i=0;i<ratings.size();i++) {
            rating  = ratings.get(i);
            line = String.format("User:%s\nAmazement: %s\n",rating[1], rating[2]);
            System.out.println(line);
        }
    }

}
