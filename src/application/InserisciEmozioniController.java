package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Classe che si occupa della gestione della scena descritta da InserisciEmozioni.fxml
 * , permette all' utente di inserire le emozioni provate , votarle e eventualmente lo
 *  indirizza verso la scena per inserire un commento per un' emozione 
 * @author Federico Ligas
 */
public class InserisciEmozioniController extends Controller implements Initializable {
	/**
	 * </>tabellaEmozioni</> : tabella che contiene le emozioni , i voti e un link di testo ai commenti
	 */
	@FXML 
	private TableView<ArrayList<StringProperty>> tabellaEmozioni ; 
	/**
	 * </>data</> : dati della tabella 
	 */
	private ObservableList<ArrayList<StringProperty>> data = FXCollections.observableArrayList();
	/**
	 * </>idCanzone</> : identificatore della canzone a cui l'utente vuole associare delle emozioni
	 */
	private int idCanzone ; 
	/**
	 * </>commentoVisibile</> : lista di booleani che indica se il commento può essere inserito
	 * <p> i valori contenuti rappresentano le righe della tabella e assumono true se l'emozione è stata votata altrimenti false 
	 */
	private ArrayList<SimpleBooleanProperty> commentoVisibile ;
	/**
	 * </>rigaCommento</> : valore per contare le righe durante la creazione dei link di testo che reindirizzano nella tabella 
	 */
	private int rigaCommento = 0 ; 
	/**
	 * </>listaCommenti</> : lista dei commenti inserite
	 */
	private String[] listaCommenti ;
	/**
	 * </>playlist</> : playlist a cui appartiene la canzone da valutare 
	 */
	private Playlist playlist ; 
	/**
	 * </>listaVoti</> : lista dei voti inseriti 
	 */
	private int[] listaVoti ;
	/**
	 * </>utente</> : utente che sta inserendo l'emozione 
	 */
	private Login utente; 
	/**
	 * </>ControllerCorrente</> : controller che sta gestendo la scena 
	 */
	private InserisciEmozioniController controllerCorrente ; 
	
	/**
	 * costruttore di base 
	 */
	public InserisciEmozioniController() {
		
		listaVoti  = new int[9] ;
		listaCommenti = new String[9];
		// inizializziamo con i valori di default 
		for(int i=0;i<9;i++) {
			listaCommenti[i]  = "";
			listaVoti[i] = 0 ;
		}
		commentoVisibile = new ArrayList<SimpleBooleanProperty>();
		
	}
	

    /**
	 * Viene chiamato (una e una sola volta) dal controller appena dopo la scena è stata "caricata" con successo 
     * e inizializza gli elementi che sono contenuti nella scena .
     * @param arg0 : Il path usato per risolvere i path relativi per l'oggetto "radice" o il valore null se il path non &egrave noto
     * @param arg1 : Le risorse usate per localizzare l'oggetto "radice" , o null se la radice non viene trovata 
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		TableColumn<ArrayList<StringProperty>, String>  nameColumn = new TableColumn<>("Emozione");
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().get(0));
		tabellaEmozioni.getColumns().add(nameColumn);
		
		TableColumn<ArrayList<StringProperty>, String> descriptionColumn = new TableColumn<>("Descrizione");
		descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().get(1));
		tabellaEmozioni.getColumns().add(descriptionColumn);
		
		addButtonsToTable();
		addHyperlinkToTable();
		
		for (int r = 0; r < 9 ; r++) {
	        ArrayList<StringProperty> row = new ArrayList<StringProperty>();
            
	        row.add(new SimpleStringProperty(Emozioni.getlistaEmozioni()[r].getNome()));
	        row.add(new SimpleStringProperty(Emozioni.getlistaEmozioni()[r].getDescrizione()));

	        data.add(row);
	    }
		
		tabellaEmozioni.setItems(data);
		
		
	}
	/**
	 * aggiunge un link di testo alla tabella 
	 */
	private void addHyperlinkToTable() {
	    // usiamo la wrapper class di void per inizializzare colonna 
		
	    TableColumn<ArrayList<StringProperty>, Void> commentColumn = new TableColumn<ArrayList<StringProperty>, Void>("");

	    Callback<TableColumn<ArrayList<StringProperty>, Void>, TableCell<ArrayList<StringProperty>, Void>> cellFactory = new Callback<TableColumn<ArrayList<StringProperty>, Void>, TableCell<ArrayList<StringProperty>, Void>>() {

	        @Override
	        public TableCell<ArrayList<StringProperty>, Void> call(final TableColumn<ArrayList<StringProperty>, Void> param) {

	            final TableCell<ArrayList<StringProperty>, Void> cell = new TableCell<ArrayList<StringProperty>, Void>(){

	                private final Hyperlink linkToComment= new Hyperlink("Inserisci commento");
	                
	                {
	                /* all' interno di questa funzione lambda metteremo un metodo 
	                * crea un dialog che mostra le statistiche della canzone */ 
	                	linkToComment.setOnAction((ActionEvent e) -> {
	                    	int indice ; 
	                    	getTableView().getItems().get(indice = getIndex());
							try {
								onHyperLinkCliked(e,indice);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
	                    	// serve in modo che un hyperlink clickato non rimanga sottolineato
							linkToComment.setVisited(false);
	                    });
	                	
	                	commentoVisibile.add(new SimpleBooleanProperty(true));
	                	linkToComment.disableProperty().bind(commentoVisibile.get(rigaCommento));
	                	rigaCommento++;
	                	
	                 }
	                
	                /* update item è un metodo che viene chiamato per ogni cella dopo
	                 *  che è stato determinato il tipo da inserire e colloca l'elemento 
	                    che la cella deve contenere  */
	                @Override
	                public void updateItem(Void item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (empty) {
	                        setGraphic(null);
	                    } else {
	                        setGraphic(linkToComment);
	                    }
	                }};

	            return cell;
	        }
	    };

	    // crea una CellValueFactory che contiene un hyperlink , per ogni riga della tabella 
	    commentColumn.setCellFactory(cellFactory);

	    tabellaEmozioni.getColumns().add(commentColumn);
		
	}
	/**
	 * aggiunge i bottoni per votare l'emozione  alla tabella 
	 */
	private void addButtonsToTable() {
	    // usiamo la wrapper class di void per inizializzare colonna 
		
	    TableColumn<ArrayList<StringProperty>, Void> buttonsColumn = new TableColumn<ArrayList<StringProperty>, Void>("voto");

	    Callback<TableColumn<ArrayList<StringProperty>, Void>, TableCell<ArrayList<StringProperty>, Void>> cellFactory = new Callback<TableColumn<ArrayList<StringProperty>, Void>, TableCell<ArrayList<StringProperty>, Void>>() {

	        @Override
	        public TableCell<ArrayList<StringProperty>, Void> call(final TableColumn<ArrayList<StringProperty>, Void> param) {

	            final TableCell<ArrayList<StringProperty>, Void> cell = new TableCell<ArrayList<StringProperty>, Void>(){

	            	private final Button firstButton = new Button("1");
	            	private final Button secondButton = new Button("2");
	            	private final Button thirdButton = new Button("3");
	            	private final Button forthButton = new Button("4");
	            	private final Button fifthButton = new Button("5");
	            	
	            	private final HBox buttons = new HBox(firstButton,secondButton,thirdButton,forthButton,fifthButton);
	            	
	            	{
	            		// getChildren --> lista :: scorrere lista :: tutti setting per buttone ( compresa azione ) 
	            		double r= 12;
	            		Circle buttonShape = new Circle(r);
	            		ObservableList<Node> buttonslist =  buttons.getChildren(); 
	            	    int i = 0  ;
	            	    
	            	    
	            		for(i=0;i<5;i++) {

	            			Button button = ((Button)buttonslist.get(i)) ; 
	            			button.setShape(buttonShape);
	            			button.setMinSize(2*r, 2*r); 
	            			button.setMaxSize(2*r, 2*r);
	            			button.setStyle("-fx-background-color: transparent;");
	            			
	            			button.setOnAction(event -> {
	            				
	            				int indice ; 
		                    	getTableView().getItems().get(indice = getIndex());
		                    	Button buttoneClickato = (Button) event.getTarget();
		                    	HBox bottoniRiga = (HBox) buttoneClickato.getParent();
		                    	Integer votoInserito = Integer.valueOf(buttoneClickato.getText());
		                    	
		                    	
		                    	if(listaVoti[indice] == votoInserito) {
		                    	   button.setStyle("-fx-background-color: transparent;");
		                    	   listaVoti[indice] = 0 ; 
		                    	   listaCommenti[indice] = "" ; 
		                    	   commentoVisibile.get(indice+2).setValue(true);
		                    	}
		                    	else {
		                    		
		                    	   if(!(listaVoti[indice] == 0 )) {
		                    		   
		                    		   ObservableList<Node> buttonslistriga =  bottoniRiga.getChildren(); 
		                    		   Button bottonePrecedente = ((Button)buttonslistriga.get(listaVoti[indice]-1)) ;
		                    		   bottonePrecedente.setStyle("-fx-background-color: transparent;");
		                    		   
		                    	   }
		                    	   
		                    	   commentoVisibile.get(indice+2).setValue(false);
		                    	   button.setStyle("-fx-background-color: white;");
		                    	   listaVoti[indice] = votoInserito ; 
		                    	}	
	            				
		                    });
	            			
	            			
	            		}
	            		
	                    buttons.setSpacing(2);
	                }
	                
	                @Override
	                public void updateItem(Void item, boolean empty) {
	                    super.updateItem(item, empty);
	                    if (empty) {
	                        setGraphic(null);
	                    } else {
	                        setGraphic(buttons);
	                    }
	                }};

	            return cell;
	        }
	    };

	    // crea una CellValueFactory che contiene un hyperlink , per ogni riga della tabella 
	    buttonsColumn.setCellFactory(cellFactory);

	    tabellaEmozioni.getColumns().add(buttonsColumn);
		
	}
	    

     /**
	 * porta l'utente alla scena per inserire un commento quando un link di testo viene clickato
	 * @param e : evento che scatena il metodo
	 * @param indice : indice della riga che contiene l'Hyperlink clickato 
	 * @throws IOException : un file non viene trovato 
	 */
    private void onHyperLinkCliked(ActionEvent e, int indice) throws IOException {
    	
    	FXMLLoader nextFxmlloader = new FXMLLoader(getClass().getResource("/InserisciCommento.fxml"));
		InserisciCommentoController controller = new InserisciCommentoController(listaCommenti,indice,controllerCorrente);
		nextFxmlloader.setController(controller);
		Parent parent = null;
		
		try {
			parent = nextFxmlloader.load();
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
    /**
	 * imposta il valore della variabile </>playlist</> con la playlist che contiene la canzone che stiamo valutando
	 * @param playlist : playlist a cui appartiene la canzone da valutare 
	 */
	public void setPlaylist(Playlist playlist) {
		
		this.playlist = playlist ; 
		
	}

    /**
    * torna alla scena per selezionare le canzoni di una playlist dell'utente
    * @param e : evento che scatena il metodo
    */
	public void switchToSelezionaCanzone(ActionEvent e ) throws IOException {
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/SelezionaCanzone.fxml"));
		SelezionaCanzoneController controller = new SelezionaCanzoneController(utente,playlist);
		fxmlloader.setController(controller);
    	controller.setElencoPlaylistUtente(FXCollections.observableArrayList(utente.getUserPlaylists()));
		setRoot(fxmlloader.load());
		changeScene(e);
	}
	/**
	 * salva le emozioni inserite dall'utente nel file Emozioni.dati.csv 
	 * @param e : evento che scatena il metodo
	 * @throws IOException : il file non viene trovato 
	 */
	public void salvaEmozioni(ActionEvent e )throws IOException {
	
		boolean almenoUnVoto = false;
		for (int i = 0; i < listaVoti.length; i++){
		    if (listaVoti[i] != 0){
		    	almenoUnVoto  = true;
		        break;
		    }
		}
		if(almenoUnVoto) {
		  Song.RegistraEmozioni(utente,idCanzone,listaVoti,listaCommenti);
		  createAlert("Le emozioni sono state salvate");
          switchToSelezionaCanzone(e);
		}
		else
			createAlert("Errore : E' necesssario associare \n        almeno un emozione");
        
	}
	/**
	 * imposta il valore della variabile </>listaCommenti</> 
	 * @param listaCommenti : lista dei commenti inserite
	 */
	public void setListaCommenti(String[] listaCommenti) {
		
		this.listaCommenti = listaCommenti ;
	}

	/**
	 * imposta il valore della variabile </>idCanzone</> 
	 * @param idCanzone : identificatore della canzone a cui l'utente vuole associare delle emozioni
	 */
	public void setIdCanzone(int idCanzone) {
		this.idCanzone = idCanzone;
	}
	/**
	 * imposta il valore della variabile </>utente</> 
	 * @param utente : playlist dell' utente a cui dobbiamo aggiungere le canzoni
	 */
	public void setUtente(Login utente) {
		this.utente = utente; 
	}

	/**
	 * imposta il valore della variabile </>controllerCorrente</> 
	 * @param controllerCorrente : controller che sta gestendo la scena 
	 */
	public void setControllerCorrente(InserisciEmozioniController controllerCorrente) {
		this.controllerCorrente = controllerCorrente ; 
		
	}
	
	


	
}