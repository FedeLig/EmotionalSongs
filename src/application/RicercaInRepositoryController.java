package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

public class RicercaInRepositoryController extends SearchSongTableController {
	
	/* il  metodo Initialize viene chiamato (una e una sola volta)
       dal controller appena dopo la finestra è stata "caricata" con successo 
       e contiene la tabella che vogliamo visualizzare la prima volta  */
	String indirizzoTabellaPrecedente ; 
	
	// serve solo se usato da un Utente
	String userId ; 
	
	public void tornaAlPrecedente( ActionEvent e ) throws IOException{
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(indirizzoTabellaPrecedente));
	    if(userId != null) {
	       MenuUtenteController controller = new MenuUtenteController();
	       fxmlloader.setController(controller);
	       controller.setUserId(userId);
	    }
	    setRoot(fxmlloader.load());
	    changeScene(e);
	    
	}
	
	@Override
	protected  String getTestoHyperLink() {
		return "statistiche"; 
	}
	@Override 
    protected void  onHyperLinkCliked (ActionEvent e ,int indice ){
		
		System.out.println("visualizza statistiche");
		
	}
	
	public void setIndirizzoTabellaPrecedente(String indirizzoTabellaPrecedente) {
		this.indirizzoTabellaPrecedente = indirizzoTabellaPrecedente ; 
	}
	
	public void setUserId(String userId) {
		this.userId = userId ; 
	}

}
