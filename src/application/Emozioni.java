package application;

/**
 *  Contiene la lista delle emozioni che l' utente può associare ad un canzone 
 *  e i metodi che ci permettono di ... 
 *  @author Ligas
 */
public enum Emozioni {

	AMAZAMENT("Amazement","Feeling of wonder or happiness"),
	SOLEMNITY("Solemnity","Feeling od transcendence, inspiration , Thrills"),
	TENDERNESS("Tenderness","Sensuality, affect, feeling of love"),
	NOSTALGIA("Nostalgia","Dreamy, melancholic ,sentimental feelings"),
	CALMNESS("Calmness","Relaxation, serenity , meditativeness"),
	POWER("Power","Feeling strong, heroic, triumphant, energetic"),
	JOY("Joy","Feels like dancing,bouncy feeling, animated, amused"),
	TENSION("Tension","Feeling Nervous, impatient ,irritated"),
	SADNESS("Sadness","Feeling Depressed, sorrowful");
	
	
	private final String nome ; 
	private final String descrizione ; 
	private static Emozioni[] listaEmozioni  = Emozioni.values();
	
	Emozioni(String nome, String descrizione) {
		this.nome = nome ; 
		this.descrizione = descrizione ; 
	}
	
	/** 
	 *  @return ritorna una stringa contenente il nome dell'emozione 
	 */
	public String getNome() {
		return nome ; 
	}
	/** 
	 *  @return ritorna una stringa contenente la descrizione dell'emozione : 
	 *  questa può essere una definizione , una spiegazione o un insieme di sinomini 
	 */
	public String getDescrizione() {
		return descrizione;
	}
	
	public static Emozioni[] getlistaEmozioni() {
		return listaEmozioni ; 
	}

	
}