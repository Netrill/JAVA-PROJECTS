package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.DatabaseAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HomeController implements Initializable{
	
	private DatabaseAccess db;
	
    @FXML
    private TextField t_login1;

    @FXML
    private PasswordField t_login2;

    @FXML
    private Button b_login;

    @FXML
    void login(ActionEvent event) throws IOException, SQLException {
    	cambioScena ( event);/*
    	String utente =t_login1.getText();
    	String password=t_login2.getText();
    	if (utente.length()>0 && password.length()>0) {
    		t_login1.setText("");
    		t_login2.setText("");
    		if (db.recuperaUtenza(utente,password)>=0)
   		    	cambioScena ( event);
	
    	}*/
    	 
    }
    void cambioScena (ActionEvent event) throws IOException, SQLException {
    	
    	Parent main = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene applicazionePrincipale = new Scene(main);
        //This line gets the Stage information
        Node node = ((Node)event.getSource());
        Stage window =(Stage) node.getScene().getWindow();
        window.setScene(applicazionePrincipale);
        
        window.show();
        
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			
			db = new DatabaseAccess();
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	public void finalize () throws SQLException {
		db.close();
	}

}
