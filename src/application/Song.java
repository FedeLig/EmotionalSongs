package application;

//Classe che istanzia l'oggetto canzone, permette di selezionare una canzone
//e di valutare le emozioni che trsamette

import java.util.*;
import java.io.*;
import java.nio.file.FileSystems;
import java.lang.NullPointerException;

class Song {

    private String name, author, album;
    private int year, duration, id;


    //il costruttore prende in input l'inidice della canzone nel dataset
    //e da lì ottiene i dati.
    public Song(int id) throws IOException, NumberFormatException {

        this.id = id;
        String line="";
        String[] data  = getSongData(id);
        
        this.id = Integer.parseInt(data[0]);
        this.name = data[1];
        this.author = data[2];
        this.album = data[3];
        this.duration = (int)Float.parseFloat(data[4]);
        this.year = Integer.parseInt(data[5]);
    }

    //Funzione per la ricerca di una canzzone:
    //cerca i risultati in base al titolo, nome dell'artista e dell'album.
    public static List<Song> searchSong(String name) throws IOException, NumberFormatException, NumberFormatException{
        name = name.toLowerCase();
        String[] data = new String[6];
        String line, searchLine;
        List<Song> songList = new ArrayList<Song>();

        String path = getPath() + (File.separator + "Songs.csv");
        System.out.println(path);
        BufferedReader br = new BufferedReader(new FileReader(path));
        //Raggruppo tutte le canzoni in cui appare il dato cercato
        while((line = br.readLine()) != null) {
            data = line.split(",,");
            if(! data[0].equals("id")) {
                searchLine = String.join("", data[1],data[2],data[3]).toLowerCase();
                if(searchLine.contains(name)) 
                    songList.add(new Song(Integer.parseInt(data[0])));
            }
            
        }
        br.close();

        return(songList);
    }

    public void inserisciEmozioniBrano(String userId) throws IOException {
        boolean rated = false;
        Scanner scan = new Scanner(System.in);
        String amazement = scan.nextLine();
        String line, oldLine="", newLine = String.format("%d,,%s,,%s",this.id,userId,amazement); 
        String path = getPath() + (File.separator + "Emozioni.csv");
        //String[] data = new String[6];
        Writer output;
        //verifichiamo se questa canzone è già stata valutata o meno.
        BufferedReader br = new BufferedReader(new FileReader(path));
        while((line = br.readLine()) != null) {
            String[] data = line.split(",,");
            
            if(data[0].equals(Integer.toString(this.id)) & data[1].equals(userId)) {
                rated = true;
                oldLine = line;
                break;
            }
        }
        br.close();

        //se la canzone è già stata valutata rimpiaziamo la vecchia valutazione[
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

    //funzione per ottenere le emozioni di una canzone dato l'id;
    private List<String[]> getEmotionsData(int id) throws IOException, FileNotFoundException {
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

    public void printSongData() {
        String line = String.format("Nome: %s \nArtista: %s \n",this.name,this.author);
        System.out.print(line);
    }

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
