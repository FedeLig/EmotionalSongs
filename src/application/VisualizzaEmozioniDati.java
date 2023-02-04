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
	public VisualizzaEmozioniDati(int emozione,Integer numUtentiAssociati , Float mediaVoti) {
		switch(emozione) {
            
            case 1:
                this.nome = "Amazement";
                this.descrizione = "Feeling of wonder or happiness";
                break;
            case 2:
                this.nome = "Solemnity";
                this.descrizione = "Feeling of transcendence, inspiration. Thrills. ";
                break;
            case 3:
                this.nome = "Tenderness";
                this.descrizione = "Sensuality, affect, feeling of love";
                break;
            case 4:
                this.nome = "Nostalgia";
                this.descrizione = "Dreamy, melancholic, sentimental feelings ";
                break;
            case 5:
                this.nome = "Calmness";
                this.descrizione = "Relaxation, serenity, meditativeness ";
                break;
            case 6:
                this.nome = "Power";
                this.descrizione = "Feeling strong, heroic, triumphant, energetic ";
                break;
            case 7:
                this.nome = "Joy";
                this.descrizione = "Feels like dancing, bouncy feeling, animated, amused ";
                break;
            case 8:
                this.nome = "Tension";
                this.descrizione = "Feeling Nervous, impatient, irritated ";
                break;
            case 9:
                this.nome = "Sadness";
                this.descrizione = "Feeling Depressed, sorrowful";
                break;
			
		}
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
