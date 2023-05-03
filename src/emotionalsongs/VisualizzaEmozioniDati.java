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
 *  @author Federico Ligas
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
	 *  
	 */
    public VisualizzaEmozioniDati(String nome, String descrizione, int numUtentiAssociati, float mediaVoti) {
		
		this.nome = nome ; 
		this.descrizione = descrizione ; 
		this.numeUtentiAssociati = numUtentiAssociati ; 
		this.mediaVoti = mediaVoti; 
	}
	public VisualizzaEmozioniDati(String nome ,String descrizione ,Integer numUtentiAssociati , Float mediaVoti ,ArrayList<String> listaCommenti) {
		
		this(nome,descrizione,numUtentiAssociati.intValue(),mediaVoti.floatValue());
		this.listaCommenti.addAll(listaCommenti) ;
		
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
