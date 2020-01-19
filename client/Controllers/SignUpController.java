package client.Controllers;


import java.io.IOException;

import client.ClientConsole;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    private MainController mainController;

    @FXML
    private Button signUpbtn;

    @FXML
    private TextField passText;

    @FXML
    private TextField oassAgainText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField payDetailsText;

    public void injectMainController(MainController mainController_) {
		mainController = mainController_;
	}
    
    @FXML
    void signUp(ActionEvent event) {
    	
    	String pass=passText.getText(),passValidate=oassAgainText.getText();
    	
    	// validate sign-up input 
    	try {
    		checkInput(pass, passValidate);
    	} catch(IOException e) {
    		new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
    		return;
    	}
    	/*
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	mainController.getClient().client.handleMessageFromClientUI("#signup "+);
    	    }
    	});
    	*/
    	
    	
    	
    	try{
    		openCatalogscene(event);
  		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
    
    }

        
    void openCatalogscene(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/fxml/CatalogScene.fxml"));     
		Parent root = (Parent)fxmlLoader.load();
		Scene scene = new Scene(root); 
		Stage appstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appstage.setScene(scene);    	
		appstage.show(); 
    }
    
    
    private void checkInput(String pass,  String secondPass) throws IOException {
    	String error_message = "";
    	if(!(pass.length()>4))
    		error_message += "Your password is short\n";
    	if(!pass.equals(secondPass))
    		error_message += "Your two enterd passwords are not equal\n";
  
    	if(error_message.length() > 0)
    		throw(new IOException(error_message));
    		
    }

    
    
    
}
