/*Progetto di 
 * Edoardo Picazio 748815 VA
 * Federico Ligas 749063 VA
 */
package emotionalsongs;

import java.util.ArrayList;

/**
 *  Questa classe serve come riferimento per la tabella  contenuta in VisualizzaEmozioniController ,
 *  la quale contiene informazioni riguardanti le emozioni associate ad una particolare canzone .
 *  Essa descrive SOLO i tipi di dato delle colonne che verranno popolate dai dati  .
 *  Nota : la colonna non descritta contiene un elemento grafico definito in VisualizzaEmozioniController
 *  @see emotionalsongs.VisualizzaEmozioniController
 *  @author Ligas
 */
public class VisualizzaEmozioniDati {

	private String nome ; 
	private String descrizione ; 
	private Integer numeUtentiAssociati ; 
	private Float mediaVoti ; 
	private ArrayList<String> listaCommenti  = new ArrayList<String>() ; 

	/**
	 *  Costruttore di base 
	 *  @param nome : stringa che contiene il nome dell'emozione
	 *  @param descrizione : stringa che contiene la descrizione dell'emozione 
	 *  @param numUtentiAssociati : numero di utenti che hanno associato l' emozione alla canzone 
	 *  @param mediaVoti : media calcolata su tutte le valutazioni che gli utenti hanno dato all' emozione inserita
	 *  @author Ligas
	 */
	public VisualizzaEmozioniDati(String nome ,String descrizione ,Integer numUtentiAssociati , Float mediaVoti ,ArrayList<String> listaCommenti) {
		
		this.nome = nome ; 
		this.descrizione = descrizione ; 
		this.numeUtentiAssociati = numUtentiAssociati ; 
		this.mediaVoti = mediaVoti; 
		this.listaCommenti.addAll(listaCommenti) ;
		
	}
	/**
	 * costruttore alternativo
	 * @param emozione: numero dell'emozione
	 * @param numUtentiAssociati: numero di utenti che hanno partecipato alla votazione
	 * @param mediaVoti: media dei voti
	 */
	public VisualizzaEmozioniDati(int emozione,Integer numUtentiAssociati , Float mediaVoti) {
		switch(emozione) {
            
            case 1:
                this.nome = "Meraviglia";
                this.descrizione = "Sensazione di stupore o felicità";
                break;
            case 2:
                this.nome = "Solennità";
                this.descrizione = "sensazione di trascendenza, ispirazione. Brividi. ";
                break;
            case 3:
                this.nome = "Tenerezza";
                this.descrizione = "Sensualità, affetto, amore";
                break;
            case 4:
                this.nome = "Nostalgia";
                this.descrizione = "Sognante, melanconico, sentimentale";
                break;
            case 5:
                this.nome = "Calma";
                this.descrizione = "Rilassamento, serenità ";
                break;
            case 6:
                this.nome = "Potere";
                this.descrizione = "Sensazione di forza, eroismo, trionfo, energia ";
                break;
            case 7:
                this.nome = "Gioia";
                this.descrizione = "Voglia di ballare, eccitazione";
                break;
            case 8:
                this.nome = "Tensione";
                this.descrizione = "Nervosismo impazienza, irritazione ";
                break;
            case 9:
                this.nome = "Tristezza";
                this.descrizione = "Sesnsazione di depressione, sofferenza";
                break;
			
		}
		this.numeUtentiAssociati = numUtentiAssociati ; 
		this.mediaVoti = mediaVoti; 
		
	}
	/**
	 * permette di ottenere l'emozione
	 * @return
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * permette di ottenere la descrizione
	 * @return
	 */
	public String getDescrizione() {
		return descrizione;
	}
	/**
	 * permette di ottenere il numero di utenti che hanno votato
	 * @return
	 */
	public Integer getNumeUtentiAssociati() {
		return numeUtentiAssociati;
	}
	/**
	 * permette di ottenre la media dei voti
	 * @return
	 */
	public Float getMediaVoti() {
		return mediaVoti;
	}
	/**
	 * permette di ottenre la lista dei commenti
	 * @return
	 */
	public ArrayList<String> getListaCommenti() {
		
		return listaCommenti ;
	}

	
}