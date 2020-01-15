package client.Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    /*	else
     * sign up
    	*/	
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
