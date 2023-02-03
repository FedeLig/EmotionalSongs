package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

public class InserisciEmozioniController extends Controller implements Initializable {

	@FXML 
	private TableView<ArrayList<StringProperty>> tabellaEmozioni ; 
	
	private ObservableList<ArrayList<StringProperty>> data = FXCollections.observableArrayList();
	private int idCanzone ; 
	private ArrayList<SimpleBooleanProperty> commentoVisibile ;
	private int rigaCommento = 0 ; 
	private String[] listaCommenti ;
	private Playlist playlist ; 
	private int[] listaVoti ;
	private Login utente; 
	private InserisciEmozioniController controllerCorrente ; 
	
	public InserisciEmozioniController() {
		
		listaVoti  = new int[9] ;
		listaCommenti = new String[9];
		commentoVisibile = new ArrayList<SimpleBooleanProperty>();
		
	}
	

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
		                    	   listaCommenti[indice] = null ; 
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
	            				System.out.println(((Integer)listaVoti[indice]).toString());		
	            				
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
	
	public void setPlaylist(Playlist playlist) {
		
		this.playlist = playlist ; 
		
	}
	
	public void switchToSelezionaCanzone(ActionEvent e ) throws IOException {
		
		FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/SelezionaCanzone.fxml"));
		SelezionaCanzoneController controller = new SelezionaCanzoneController(utente,playlist);
		fxmlloader.setController(controller);
    	controller.setElencoPlaylistUtente(FXCollections.observableArrayList(utente.getUserPlaylists()));
		setRoot(fxmlloader.load());
		changeScene(e);
	}


	public void setListaCommenti(String[] listaCommenti) {
		
		this.listaCommenti = listaCommenti ;
	}


	public void setIdCanzone(int idCanzone) {
		this.idCanzone = idCanzone;
	}
	
	public void setUtente(Login utente) {
		this.utente = utente; 
	}


	public void setControllerCorrente(InserisciEmozioniController controllerCorrente) {
		this.controllerCorrente = controllerCorrente ; 
		
	}
	
	


	
}