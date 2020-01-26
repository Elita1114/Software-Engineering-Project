package client.Controllers;


import java.util.ArrayList;

import common.Complaint;
import common.ComplaintsList;
import common.Status;
import common.User;
import common.UserRequest;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
    
    
    
    
    
    
    
    @FXML
    private TextArea tvDescription1;

    @FXML
    private TextField tvTitle1;

    @FXML
    private TextArea tvReply;

    @FXML
    private ListView<Complaint> lvComplaints;

    @FXML
    private TextField tvRefund;
    
    static public ComplaintsList complaintsList;
    static public ObservableList<Complaint> itemObservableList;

    static public Complaint current=null;
    static public boolean flag=false;
    static public boolean updateComplaint=false;
    
    
    
    
    
    
    public static boolean complaintFlag=false;

    public ComplaintController() {
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
    	    	int id=MainController.client.client.getLoggedUser().user_id;
    	    	mainController.getClient().client.handleMessageFromClientUI("#getComplaints "+id);
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
    		lvComplaints.setCellFactory(itemListView  -> new ComplaintListViewCell(true));

        });
    	
    }
    
    public void updateComplaint() {
    	//lvItems.getItems().clear();
    	lvComplaints.setItems(itemObservableList);
    	lvComplaints.setCellFactory(itemListView  -> new ComplaintListViewCell(true));
    	
    	
    }
    

    @FXML
    void mouseClicked(MouseEvent event) {

    	
    	lvComplaints.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				int x=lvComplaints.getSelectionModel().getSelectedIndex();
				if(x<itemObservableList.size() && x!=-1) {
				current=itemObservableList.get(x);
				tvTitle1.setText(current.title);
				tvDescription1.setText(current.description);
				tvReply.setText(current.reply);
				tvRefund.setText(String.valueOf(current.refund));
				System.out.println("click "+String.valueOf(x));
				}
				
			}
		});
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    void sendPressed(ActionEvent event) {
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	if(mainController.getClient().client.getlogged()) {
	    	    	String title=tvTitle.getText().toString();
	    	    	String description=tvDescription.getText().toString();
	    	    	Complaint complaint =  new Complaint(title, description, false, 0, mainController.getClient().client.getLoggedUser().user_id,mainController.getClient().client.getLoggedUser().store);
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
	    	    	complaintsOpened();
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
