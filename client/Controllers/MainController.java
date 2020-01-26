package client.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientConsole;
import common.CatalogItem;
import common.PayingMethod;
import common.Status;
import common.User;
import common.UserRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;

public class MainController {
	
	@FXML private CatalogController catalogController;
	@FXML private SignUpController signUpController;
	@FXML private LoginController loginController;
	@FXML private ComplaintController complaintController;
	@FXML private UpdateCatalogController updatecatalogController;//update catalog controller
	@FXML private OrderController orderController;//update order controller
	@FXML private HandleComplaintController handleComplaintController;//update order controller
	@FXML private addItemController addCatalogItemController;
	@FXML private ReportController reportsController;

	static public ClientConsole client;
	private boolean flagCheck=false;
	@FXML private TabPane tabPane;

	@FXML public Tab tabHandleComplaint;
	@FXML public Tab tabComplaint;
	@FXML public Tab tabOrder;
	@FXML public Tab tabReports;

	@FXML
	private void initialize() {
		catalogController.injectMainController(this);
		signUpController.injectMainController(this);
		loginController.injectMainController(this);
		complaintController.injectMainController(this);
		updatecatalogController.injectMainController(this);
		orderController.injectMainController(this);
		handleComplaintController.injectMainController(this);
		reportsController.injectMainController(this);
		tabPane.getTabs().remove(tabHandleComplaint);
		
	}
	public static ClientConsole getClient() {
		return client;
	}
	public void setFlag(boolean flag_) {
		client.flagCatalog=flag_;
	}
	public void setCatalog(ArrayList<CatalogItem> list) {
		catalogController.setCatalog(list);
	}
	public void setUpdateCatalog(ArrayList<CatalogItem> list) {
		updatecatalogController.setCatalog(list);
	}
	public void setClient(ClientConsole client_) {
		client=client_;
	}
	public TabPane getTabPane() {
		return tabPane;
	}
	
	
	static public void notifyCustomerService() {
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Alert alert = new Alert(AlertType.WARNING);
    	    	alert.setTitle("Warning");
    	    	alert.setHeaderText(null);
    	    	alert.setContentText("There are urgent unhandled complaints.");
    	    	alert.showAndWait();
			}
		});
	}
	
	@FXML
	public final EventHandler<Event> handleClick(){
		if(tabHandleComplaint.isSelected()) {
			
			handleComplaintController.complaintsOpened();
			
			System.out.println("handle was clicked");
			
			
		}
		else if(tabOrder.isSelected()) {
			System.out.println("Getting cart for user");
			orderController.fetchOrder();
			
		}
		else if(tabReports.isSelected()) {
			System.out.println("Getting reports for user");
			reportsController.fetchReports();
			
		}
		return null;
		
	}
	
	
	@FXML
	public final EventHandler<Event> complaintClick(){
		if(tabComplaint.isSelected()) {
			if(client.client.getlogged())
				complaintController.complaintsOpened();
			
			System.out.println("handle was clicked");
			
			
		}
		return null;
		
	}
}