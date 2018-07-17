package application;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import dao.DatabaseAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Post;

public class MainController implements Initializable{
	
	private DatabaseAccess db;
	private Post postCaricato;
	private String testoFileCaricato;
	private String Autore;
	private File fileCaricato;
	private String estensione;
	
	@FXML
    private Button b_visualizzaPost;
	@FXML
    private Button b_search1;
    @FXML
    private Button b_search2;
    @FXML
    private TextField t_search1;
    @FXML
    private TextArea textPrev;
    @FXML
    private TextField t_search2;
    @FXML
    private Label l_inserisciFile;
    @FXML
    private ListView list;
    @FXML
    private Button b_caricaFile;
    @FXML
    private TextField t_inserimentoTItolo;
    @FXML
    private TextField t_inserimentoAutore;
    @FXML
    private Button b_aggiorna_elenco;
    private boolean fileSelezionato;
     
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			
			db = new DatabaseAccess();
			aggiornaElencoPost ();
			
			list.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
		        public void handle(MouseEvent event) {
					MultipleSelectionModel<String> selezione=list.getSelectionModel();
					if (selezione.getSelectedItem()!=null) {
						String item=selezione.getSelectedItem().toString().substring(0,list.getSelectionModel().getSelectedItem().toString().indexOf(" "));
						Long id = Long.parseLong(item);
						postCaricato = db.SearchById(id);
						String testo = null;
						
						testo = new String(postCaricato.getTesto().getBytes());
						
						textPrev.setText(testo);
						fileSelezionato=true;
					}

		        }
		    });
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}
	
	@FXML
    void loadHtmlPost(ActionEvent event) throws IOException, URISyntaxException {
		if (postCaricato==null || !fileSelezionato) 
			return;
		
		File theDir = new File("temporanei");

		// if the directory does not exist, create it
		if (!theDir.exists()) {
		    System.out.println("creating directory: " + theDir.getName());
		    boolean result = false;

		    try{
		        theDir.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        //handle it
		    }        
		    if(result) {    
		        System.out.println("DIR created");  
		    }
		}
		
		
		
		
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		File file = new File ("./temporanei/paginahtml.html");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write("<html><head><title>"  +postCaricato.getTitolo() +  " </title></head><style>textarea {font-size: 17px;width:100%;height:100%;}" + 
				"</style><body><textarea>"+postCaricato.getTesto() +"</textarea></body></html>");
		bw.close();
		
		if (Desktop.isDesktopSupported() ) {
			String path= s+"\\temporanei\\paginahtml.html";
			String url = path.replace("\\", "//");
			URI uri = new URI (url);
		    Desktop.getDesktop().browse(uri);
		    fileSelezionato=false;
		}
    }
 
	@FXML
    void EVENTO_AggiornaElencoPost(ActionEvent event) {
    	aggiornaElencoPost ();
 
    }
	 @FXML
	 void ItemClikkedInList(ActionEvent event) {
		 System.out.println("SI RIUSCITO");
	 }
	@FXML
    void cercaFile(ActionEvent event) throws IOException {

        //This line gets the Stage information
        Node node = ((Node)event.getSource());
        Stage window =(Stage) node.getScene().getWindow();
        
        list.setOrientation(Orientation.VERTICAL);
        testoFileCaricato =caricaFile(window);
        if (testoFileCaricato!=null) {
        	l_inserisciFile.setText(" File caricato perfettamente");
        	textPrev.setText(testoFileCaricato);
        }
        else
        	l_inserisciFile.setText(" Nessun file selezionato");
		//list.setCellFactory(ComboBoxListCell.forListView(items));

    }
	
	void aggiornaElencoPost () {
		ArrayList<Post> listaPost= db.recuperaTuttiPost();
		fileSelezionato=false;
		elaboraPostRecuperati(listaPost);
		
	}
	void elaboraPostRecuperati (ArrayList<Post> listaPost) {
		textPrev.setText("");
		if (listaPost==null)
			return;
			
		ArrayList<String> listaPostStringa = new ArrayList<>();
		ObservableList<String> items;
		for (Post post : listaPost) {
			String nuovoPostStringa =post.getId() + " "+ post.getTitolo() + " " + post.getData() + " BY " + post.getAutore(); 
			listaPostStringa.add(nuovoPostStringa);
		}
		items=FXCollections.observableArrayList (listaPostStringa);
		list.setItems(items);
		
	}
	@FXML
    void eliminaPostById(ActionEvent event) {
		if (postCaricato==null)
			return;
		db.ElimnaById(postCaricato.getId());
		postCaricato=null;
		aggiornaElencoPost();
		
				
    }
	@FXML
    void inserisciFileInDB(ActionEvent event) throws ParseException {
		String titolo=t_inserimentoTItolo.getText();
		String autore =t_inserimentoAutore.getText();
		
		if (testoFileCaricato==null || titolo=="" || autore=="" || fileCaricato!=null)
			l_inserisciFile.setText(" Campi incompleti");
		else {
			Calendar today = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
			if (estensione.equals("txt")) {
				if (db.inserisciFileInDB(testoFileCaricato,titolo,autore,sdf.format(today.getTime()))) {
					l_inserisciFile.setText(" File Salvato nel DataBase");
					textPrev.setText("Salvataggio avvenuto con successo");
					aggiornaElencoPost();
					testoFileCaricato=null;
				}
				else {
					l_inserisciFile.setText(" Errore Salvataggio nel Database o Autore non riconosciuto");
				}
			}
			if (estensione.equals("pdf")) {
				
				
				if (db.inserisciFileInDB(testoFileCaricato,titolo,autore,sdf.format(today.getTime()))) {
					l_inserisciFile.setText(" File Salvato nel DataBase");
					textPrev.setText("Salvataggio avvenuto con successo");
					aggiornaElencoPost();
					testoFileCaricato=null;
				}
				else {
					l_inserisciFile.setText(" Errore Salvataggio nel Database o Autore non riconosciuto");
				}
			
			}
			fileCaricato=null;
			estensione=null;
		}
    }
	
	
	
	String caricaFile (Stage window) throws IOException {
		
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt","*.pdf");
		fileChooser.getExtensionFilters().add(extFilter);
		
		fileCaricato = fileChooser.showOpenDialog(window);
		if (fileCaricato!=null) {
			estensione=fileCaricato.getName().substring(fileCaricato.getName().lastIndexOf(".")+1,fileCaricato.getName().length());
			
			if (estensione.equals("pdf")) {
				
				try (PDDocument document = PDDocument.load(fileCaricato)) {

		            document.getClass();

		            if (!document.isEncrypted()) {
					
		                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		                stripper.setSortByPosition(true);

		                PDFTextStripper tStripper = new PDFTextStripper();

		                String pdfFileInText = tStripper.getText(document);
		                //System.out.println("Text:" + st);

						// split by whitespace
		                String s = new String();
		                String lines[] = pdfFileInText.split("\\r?\\n");
		                for (int i=0;i<lines.length;i++)
		                	s=s+lines[i];
		                
		                // StringBuilder s = new 
		                byte[] encoded =  s.getBytes();
		                t_inserimentoTItolo.setText(fileCaricato.getName().substring(0, fileCaricato.getName().indexOf("."))); 
		                return new String(encoded, "UTF-8");
		            }

		        }

				/*
				byte[] encoded = Files.readAllBytes(Paths.get(fileCaricato.getPath()));
				t_inserimentoTItolo.setText(fileCaricato.getName().substring(0, fileCaricato.getName().indexOf(".")));
				return fileCaricato.getName();*/
			}
			else {
				byte[] encoded = Files.readAllBytes(Paths.get(fileCaricato.getPath()));
				t_inserimentoTItolo.setText(fileCaricato.getName().substring(0, fileCaricato.getName().indexOf(".")));
				return new String(encoded, "UTF-8");
			}
		}
		return null;
		
	}
	
	@FXML
    void SearchByTitle(ActionEvent event) {
		if (t_search1.getText().length()>0) {
			ArrayList <Post> listaPost=db.SearchByTitle(t_search1.getText());
			elaboraPostRecuperati (listaPost);
			
		}
    }
 
	
	
    @FXML
    void SearchByText(ActionEvent event) {
    	if (t_search2.getText().length()>0) {
			ArrayList <Post> listaPost=db.SearchByText(t_search2.getText());
			elaboraPostRecuperati (listaPost);
		}
    }
	
	
	
	
	

}