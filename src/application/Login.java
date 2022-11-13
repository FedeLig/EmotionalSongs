package application;

import java.util.*;
import java.io.*;

public class Login {
    
    private String userName, password, errore;
    private boolean logged;

    //Quando al costruttore si passa un array questo viene usato per fare la registrazione.
    public Login(String[] dati) throws IOException {    
        
        boolean check = true;
        List<String[]> users = getUsers();

        for(String[] user : users) {
            if(user[2] == dati[2]) {
                this.errore = "Codice fiscale già in uso!";
                check = false;
                break;
            }
            if(user[4] == dati[4]) {
                this.errore = "Indirizzo email già in uso!";
                check = false;
                break;
            }
            if(user[5] == dati[5]) {
                this.errore = "Nome utente già in uso!";
                check = false;
                break;
            }

        }

        if(check) {
            //Aggiungiamo il nome utente al file UtentiRegistrati.csv
            String newLine = String.format("%s,%s,%s,%s,%s,%s,%s",dati[0],dati[1],dati[2],dati[3],dati[4],dati[5],dati[6]);
            String path = getPath() + (File.separator + "UtentiRegistrati.csv");
            BufferedWriter output = new BufferedWriter(new FileWriter(path, true));
            output.append(newLine + System.lineSeparator());
            output.close();
            //segnamo che l'utente è loggato
            this.logged = true;
        }

    }

    //se si passano nome utente e password invece si effettua il login.
    public Login(String userName, String password) throws IOException {
        //cerco fra gli utenti una coppia corrispondente di userName e password.
        List<String[]> users = getUsers();

        for(String[] user : users) {
            if(userName.equals(user[0]) && password.equals(user[1])) {
                this.logged = true;
                this.userName = userName;
                this.password = password;
                break;
            }
        }

        if(!this.logged)
            System.out.println("\nAccesso fallito: nome utente o password errati.\n");

    }

    public boolean isLogged(){
        return this.logged;
    }

    private List<String[]> getUsers() throws IOException {
        //ottengo i dati di tutti gli utenti e li divido in un array per potervi accedere 
        //singolarmente.
        List<String[]> list = new ArrayList<String[]>();
        String line;
        String path = getPath() + (File.separator + "UtentiRegistrati.csv");
        BufferedReader br = new BufferedReader(new FileReader(path));

        while((line = br.readLine()) != null)
            list.add(line.split(","));

        list.remove(0);
        
        br.close();

        return list;
    }
    //per ottenere il filePath alla cartella /data (ogni OS)
    private String getPath() {
        //ottengo la directory del progetto
        String userDirectory = System.getProperty("user.dir");
        return (userDirectory + File.separator + "data");
        
        /* IL vecchio metodo sembra non funzionare più,
         * il percorso file si interrompe alla directory EmotionalSongs
         *  lo lascio comunque come commento
        String[] directories;
        //File.separator permette di creare percorsi su ogni OS
        try {
            directories = userDirectory.split(File.separator);
        }
        catch(PatternSyntaxException e) {
            //Su windows lo split per mezzo di file.separator non funziona
            directories = userDirectory.split("\\\\");
        }
        
        return (directories + File.separator + "data");
        //cambio cartella per aprire i file in \data
        
        directories[directories.length - 1] = "data";
        System.out.println(String.join(File.separator, directories));

        return String.join(File.separator, directories); */
    }
}
