package application;
	
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("home.fxml"));
			Scene scene = new Scene(root,800,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	 
	
	
	
	public static void main(String[] args) {
		launch(args);
		eliminaFileTemporaneo ();
	}


	private static void eliminaFileTemporaneo() {
 
		Path currentRelativePath = Paths.get("");
		String pathString = currentRelativePath.toAbsolutePath().toString()+"\\temporanei\\paginahtml.html";
		pathString=pathString.replace("\\","//");
	 
		 
		currentRelativePath = Paths.get(pathString);
		try {
			 
		    Files.delete(currentRelativePath);
		} catch (Exception x) {
		    System.err.format("%s: no such" + " file or directory%n", currentRelativePath);
		 
		}
	}
}
