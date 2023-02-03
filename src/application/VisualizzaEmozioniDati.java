package application;

/**
 *  Questa classe serve come riferimento per la tabella  contenuta in VisualizzaEmozioniController ,
 *  la quale contiene informazioni riguardanti le emozioni associate ad una particolare canzone .
 *  Essa descrive SOLO i tipi di dato delle colonne che verranno popolate dai dati  .
 *  Nota : la colonna non descritta contiene un elemento grafico definito in VisualizzaEmozioniController
 *  @see application.VisualizzaEmozioniController
 *  @author Ligas
 */
public class VisualizzaEmozioniDati {

	private String nome ; 
	private String descrizione ; 
	private Integer numeUtentiAssociati ; 
	private Float mediaVoti ; 

	/**
	 *  Costruttore di base 
	 *  @param nome : stringa che contiene il nome dell'emozione
	 *  @param descrizione : stringa che contiene la descrizione dell'emozione 
	 *  @param numUtentiAssociati : numero di utenti che hanno associato l' emozione alla canzone 
	 *  @param mediaVoti : media calcolata su tutte le valutazioni che gli utenti hanno dato all' emozione inserita
	 *  @author Ligas
	 */
	public VisualizzaEmozioniDati(String nome ,String descrizione ,Integer numUtentiAssociati , Float mediaVoti) {
		
		this.nome = nome ; 
		this.descrizione = descrizione;
		this.numeUtentiAssociati = numUtentiAssociati ; 
		this.mediaVoti = mediaVoti; 
		
	}

	public String getNome() {
		return nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public Integer getNumeUtentiAssociati() {
		return numeUtentiAssociati;
	}

	public Float getMediaVoti() {
		return mediaVoti;
	}

	
}