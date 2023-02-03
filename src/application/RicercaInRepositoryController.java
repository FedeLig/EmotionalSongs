package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Classe che si occupa della gestione della scena descritta da RicercaInRepository.fxml
 * , quando è richiesto ricercare una canzone per poter visualizzare il prospetto riassuntivo delle emozioni degli utenti
 * nota : la scena RicercaInRepository.fxml può essere usata anche da RicercaInRepositoryController in altri contesti
 * @see RicercaInRepository.fxml
 * @author Ligas
 */
public class RicercaInRepositoryController extends SearchSongTableController {
	
	String indirizzoTabellaPrecedente ; 
	Login utente ; 
	
	ArrayList<VisualizzaEmozioniDati> listaEmozioni ; 
	ArrayList<ArrayList<String>> listaCommenti ;
	
	/**
	 * torna alla scena precedente : che può essere il menu Iniziale o il menu Utente , 
	 * a seconda dell' esecuzione del programma
	 * @param event : evento che scatena il metodo
	 */
	public void tornaAlPrecedente( ActionEvent e ) throws IOException{
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(indirizzoTabellaPrecedente));
	    if(utente!= null) {
	       MenuUtenteController controller = new MenuUtenteController(utente);
	       fxmlloader.setController(controller);
	    }
	    setRoot(fxmlloader.load());
	    changeScene(e);
	    
	}
	
	@Override
	protected  String getTestoHyperLink() {
		return "visualizza emozioni"; 
	}
	@Override 
    protected void  onHyperLinkCliked (ActionEvent e ,int indice ){
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/VisualizzaEmozioni.fxml"));
		Parent parent = null;
		
		Song canzoneSelezionata = getSongObservableList().get(indice) ; 
		listaEmozioni = new ArrayList<VisualizzaEmozioniDati>() ;
		listaCommenti = new ArrayList<ArrayList<String>>() ; 
		setEmotionData(canzoneSelezionata.getId());
		
		VisualizzaEmozioniController controller = new VisualizzaEmozioniController(canzoneSelezionata,FXCollections.observableArrayList(listaEmozioni),listaCommenti);
		fxmlloader.setController(controller);
		try {
			parent = fxmlloader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene dialog = new Scene(parent);
        Stage stage = new Stage();
        
        stage.setTitle("Emotional Songs");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(dialog);
        stage.showAndWait();
		
	}
	
	// 
	private void setEmotionData(int indiceCanzone ) {
		
		//int[] numUtentiPerEmozione = new int[9];
		//float[] sommaVotiPerEmozione = new float[9]; 
		
		// idUtente,IdCanzone,voto1°Emozione,Commento1°Emozione,voto9°Emozione ecc.
		// se l'utente non inserisce un emozione o un commento si segna : ,,
		
		// scorre nel file Emozioni.dati.csv
		   // se l'idCanzone corrisponde :  
		   //     togli idCanzone 
		   //     togli e salva idUtente
		   //     String[18] dati = split per virgola 
		   //     for(i=0 ; i<18 ; i+=2)
		   //       // se l'emozione è stata votata
		   //       if(!(dati[i].equals("")))
		   //         numUtentiPerEmozione[i/2]++
		   //         sommaVotiPerEmozione[i/2] = dato[i].parseFloat() 
		   //         if(!(dati[i+1].equals("")))
		   //            // usare separatore diverso 
		   //            listaCommenti[i/2].add(idUtente + "," + dato[i+1]) 
		   //       
		
		
		//  boolean esistonoEmozioniAssociate = false; 
		//  int j = 0;
		
		// while ( j < numUtentiPerEmozione.length ){
		//   if(numUtentiPerEmozione[j] != 0 ) esistonoEmozioniAssociate  = true ; 
	    //  }
		// 	
		//  
		// if(esistonoEmozioniAssociate)
		for(int i=0 ; i<9 ; i++) {
		  
		   String nome = Emozioni.getlistaEmozioni()[i].getNome() ; 
		   String descrizione = Emozioni.getlistaEmozioni()[i].getDescrizione() ;
		   int numUtenti = i%2==0 ? 0 : 1 ; 
		   listaEmozioni.add(new VisualizzaEmozioniDati(nome,descrizione,numUtenti,(float) numUtenti));
		   
		}
		//  return esistonoEmozioniAssociate ; 
	}
	public void setIndirizzoTabellaPrecedente(String indirizzoTabellaPrecedente) {
		this.indirizzoTabellaPrecedente = indirizzoTabellaPrecedente ; 
	}

	public void setUtente(Login utente) {
		this.utente = utente ; 
		
	}

}
