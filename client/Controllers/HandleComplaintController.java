package client.Controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import common.CatalogItem;
import common.Complaint;
import common.ComplaintsList;
import common.UserRequest;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class HandleComplaintController {

    @FXML
    private TextArea tvDescription;

    @FXML
    private TextField tvTitle;

    @FXML
    private TextArea tvReply;

    @FXML
    private ListView<Complaint> lvComplaints;

    @FXML
    private TextField tvRefund;

    @FXML
    private Button btnSend;

    private MainController mainController;
    static public ComplaintsList complaintsList;
    static public ObservableList<Complaint> itemObservableList;
    static public Complaint current=null;
    static public boolean flag=false;
    static public boolean updateComplaint=false;
    
    public HandleComplaintController()  { 
        itemObservableList = FXCollections.observableArrayList();
    }
    
    public void injectMainController(MainController mainController_) {
		mainController = mainController_;
	}
     
    static public void setComplaint(ArrayList<Complaint> itemList_) {
    	itemObservableList.clear();
    	if(!itemList_.isEmpty()) {
    		for (Complaint item : itemList_) { 		      
	    		itemObservableList.add(item);
    		}
    	}
    }
    
    public void complaintsOpened() {
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	flag=false;
    	    	mainController.getClient().client.handleMessageFromClientUI("#getComplaints");
    			while(!flag) {
    				try {
    					Thread.sleep(100);
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    			setComplaint(complaintsList.itemList);
    			updateComplaint();
    			flag=false;
    	    }
    	});
    }
    
    public void initialize() {
    	Platform.runLater(() -> {
    		lvComplaints.setItems(itemObservableList);
    		lvComplaints.setCellFactory(itemListView  -> new ComplaintListViewCell(false)); 
    		});
    	
    }
    
    public void updateComplaint() {
    	// lvItems.getItems().clear();
    	lvComplaints.setItems(itemObservableList);
    	lvComplaints.setCellFactory(itemListView  -> new ComplaintListViewCell(false));
    }
    

    @FXML
    void mouseClicked(MouseEvent event) {

    	lvComplaints.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				
				int x=lvComplaints.getSelectionModel().getSelectedIndex();
				if(x<itemObservableList.size() && x!=-1) {
					current=itemObservableList.get(x);
					tvTitle.setText(current.title);
					tvDescription.setText(current.description);
					tvReply.setText(current.reply);
					if(current.status) {
						btnSend.setDisable(true);
						tvRefund.setEditable(false);
						tvReply.setEditable(false);
					}
					else
					{
						btnSend.setDisable(false);
						tvRefund.setEditable(true);
						tvReply.setEditable(true);
					}
					tvRefund.setText(String.valueOf(current.refund));
					System.out.println("click "+String.valueOf(x));
				}
			}
		});
    	
    }
    
    @FXML
    void sendPressed(ActionEvent event) {
    	if(tvReply.getText().isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
	    	alert.setTitle("Failed");
	    	alert.setHeaderText(null);
	    	alert.setContentText("Please fill in a reply.");
	    	alert.showAndWait();
    	}
    	else {
    		
    		
    		Platform.runLater(new Runnable() {
        	    @Override
        	    public void run() {
        	    	try {
    	    	    	String reply=tvReply.getText().toString();
    	    	    	Double refund=Double.parseDouble(tvRefund.getText().toString());
    	    	    	ArrayList<Object> args =  new ArrayList<Object>();
    	    	    	
    	    	    	args.add(current.complaintID);
    	    	    	args.add(reply);
    	    	    	args.add(refund);
    	    	    	
    	    	    	UserRequest user_request=new UserRequest("#updateComplaint", args);
    	    	    	updateComplaint=false;
    	    	    	mainController.getClient().client.handleMessageFromClientUI(user_request);
    	    	    	System.out.println("after send");
    	    			while(!updateComplaint) {
    	    				try {
    	    					Thread.sleep(100);
    	    				} catch (InterruptedException e) {
    	    					// TODO Auto-generated catch block
    	    					e.printStackTrace();
    	    				}
    	    			}
    	    			updateComplaint=false;
    	    	    	Alert alert = new Alert(AlertType.INFORMATION);
    	    	    	alert.setTitle("Success");
    	    	    	alert.setHeaderText(null);
    	    	    	alert.setContentText("Your reply has been saved.");
    	    	    	alert.showAndWait();
    	    	    	
    	    	    	complaintsOpened();
        	    	}catch(Exception e) {
        	    		Alert alert = new Alert(AlertType.WARNING);
        		    	alert.setTitle("Fail");
        		    	alert.setHeaderText(null);
        		    	alert.setContentText("Please make sure refund is a valid number.");
        		    	alert.showAndWait();
        				System.out.println(e);
        			  }
        	    }
    		});
    		
    	
    	}
    	
    }
    

}
