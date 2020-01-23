/**
 * Sample Skeleton for 'LoginScene.fxml' Controller Class
 */

package client.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import common.PayingMethod;
import common.Status;
import common.User;
import common.UserRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType; 

public class LoginController {

	private MainController mainController;
	
    @FXML // fx:id="idSplit"
    private SplitMenuButton idSplit; // Value injected by FXMLLoader

    @FXML // fx:id="idBtn"
    private MenuButton idBtn; // Value injected by FXMLLoader

    @FXML // fx:id="idBar"
    private ContextMenu idBar; // Value injected by FXMLLoader
    
    @FXML
    private TextField nameText;

    @FXML
    private TextField passText;

    public void injectMainController(MainController mainController_) {
		mainController = mainController_;
	}
    
    @FXML
    void login(ActionEvent event) {
    	String user_name = nameText.getText();
    	String passwd = passText.getText();
    	System.out.println("login");
    	User new_user =  new User(user_name, passwd);
    	ArrayList<Object> args =  new ArrayList<Object>();
    	args.add(new_user);
    	UserRequest user_request = new UserRequest("#login",  args);

    	System.out.println("sending request to server");
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	System.out.println(mainController);
    	    	System.out.println("entered");
    	    	mainController.getClient().client.handleMessageFromClientUI(user_request);
    	    	System.out.println("finished_1");
    	    }
    	});
    	System.out.println("finished");
		Platform.runLater(new Runnable() {
	  	    @Override
	  	    public void run() {
	  			while(!mainController.getClient().client.getlogged() && !mainController.getClient().client.isWrongdetails()) {
	  				try {
	  					Thread.sleep(100);
	  				} catch (InterruptedException e) {
	  					// TODO Auto-generated catch block
					e.printStackTrace();
	  				}
	  			}
	  			if(mainController.getClient().client.getlogged())
	  			{
		            if(mainController.getClient().client.getLoggedUser().status==Status.customService)
		            {
		            	mainController.getTabPane().getTabs().add(mainController.tabHandleComplaint);
		            }
	  				Alert alert = new Alert(AlertType.INFORMATION, "Login successful!");
	  				alert.show();
					mainController.getTabPane().getTabs().remove(1);
					mainController.getTabPane().getTabs().remove(1);
					mainController.getTabPane().getSelectionModel().select(0);
	  			}else if(mainController.getClient().client.isWrongdetails())
	  			{
	  				Alert alert = new Alert(AlertType.ERROR, "Username or password incorrect!");
	  				alert.show();
	  				mainController.getClient().client.setWrongdetails(false);
	  			}
		    }
		});
    	
    }
}
