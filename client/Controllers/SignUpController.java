package client.Controllers;


import java.io.IOException;

import client.ClientConsole;
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

    @FXML
    void signUp(ActionEvent event) {
    	String pass=passText.getText(),passAgain=oassAgainText.getText();
    	String flag1=checkPassLen(pass);
    	String flag2=checkPassAgain(pass,passAgain);
    	String errors= flag1+flag2;
    	if(!(errors.length()==0))
    		new Alert(Alert.AlertType.ERROR, errors).showAndWait();
    	else {
	     /* sign up
	    	*/	
	    	try {
	    		openCatalogscene(event);
	  		} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
    
    private String checkPassLen(String pass) {
    	if(!(pass.length()>4))
    		return "Your password is short\n";
    	return "";
    }
    private String checkPassAgain(String pass, String passAgain) {
    	if(!pass.equals(passAgain))
    		return "Your two enterd passwords are not equal\n";
    	return "";
    }
    
    
    
}
