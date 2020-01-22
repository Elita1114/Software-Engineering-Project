package client.Controllers;


import java.util.ArrayList;

import common.Complaint;
import common.Status;
import common.User;
import common.UserRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ComplaintController {
	
	private MainController mainController;
    @FXML
    private TextField tvTitle;

    @FXML
    private TextArea tvDescription;

    @FXML
    private Button btnSend;
    
    public static boolean complaintFlag=false;

    public void injectMainController(MainController mainController_) {
		mainController = mainController_;
	}
    
    
    @FXML
    void sendPressed(ActionEvent event) {
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	if(mainController.getClient().client.loggedUser!=null) {
	    	    	String title=tvTitle.getText().toString();
	    	    	String description=tvDescription.getText().toString();
	    	    	Complaint complaint =  new Complaint(title, description, false, 0, mainController.getClient().client.loggedUser.user_id);
	    	    	ArrayList<Object> args =  new ArrayList<Object>();
	    	    	args.add(complaint);
	    	    	UserRequest user_request=new UserRequest("#addComplaint", args);
	    	    	complaintFlag=false;
	    	    	mainController.getClient().client.handleMessageFromClientUI(user_request);
	    	    	System.out.println("after send");
	    			while(!complaintFlag) {
	    				try {
	    					Thread.sleep(100);
	    				} catch (InterruptedException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				}
	    			}
	    			complaintFlag=false;
	    	    	Alert alert = new Alert(AlertType.INFORMATION);
	    	    	alert.setTitle("Success");
	    	    	alert.setHeaderText(null);
	    	    	alert.setContentText("Your complaint has been received.");
	    	    	alert.showAndWait();
	    	    }
    	    	else {
    	    		Alert alert = new Alert(AlertType.WARNING);
	    	    	alert.setTitle("Failed");
	    	    	alert.setHeaderText(null);
	    	    	alert.setContentText("You need to login in order to send a complaint.");
	    	    	alert.showAndWait();
    	    	}
    	    }
    	});

    }

}
