package client.Controllers;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientConsole;
import common.CatalogItem;
import common.ChainManager;
import common.Customer;
import common.Employee;
import common.PayingMethod;
import common.Status;
import common.StoreManager;
import common.SystemAdministrator;
import common.User;
import common.UserRequest;
import common.customerService;
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
import javafx.scene.control.SingleSelectionModel;

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
	@FXML private UpdateUserController updateUserController;

	
	static public ClientConsole client;
	private boolean flagCheck=false;
	@FXML private TabPane tabPane;

	@FXML public Tab catalogTab;
	@FXML public Tab tabHandleComplaint;
	@FXML public Tab tabComplaint;
	@FXML public Tab tabOrder;
	@FXML public Tab tabReports;
	@FXML public Tab tabSignUp;
	@FXML public Tab tabUpdateCatalog;
	@FXML public Tab tabAddCatalogItem;
	@FXML public Tab tabSignIn;
	@FXML public Tab tabupdateUser;


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
		updateUserController.injectMainController(this);

		permissions();
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
	public final EventHandler<Event> signupClick(){
		if(tabSignUp.isSelected())
			signUpController.updateStoresList();
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
	
	
	//premision tabs
	public void permissions()
	{
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		selectionModel.select(catalogTab);
		//tabPane.getTabs().remove(catalogTab);
		tabPane.getTabs().remove(tabHandleComplaint);
		tabPane.getTabs().remove(tabComplaint);
		tabPane.getTabs().remove(tabOrder);
		tabPane.getTabs().remove(tabReports);
		tabPane.getTabs().remove(tabSignUp);
		tabPane.getTabs().remove(tabUpdateCatalog);
		tabPane.getTabs().remove(tabAddCatalogItem);
		tabPane.getTabs().remove(tabSignIn);
		tabPane.getTabs().remove(tabupdateUser);
		
		
		if(client==null || client.client==null ||client.client==null || client.client.getLoggedUser()==null) {
	//		tabPane.getTabs().add(catalogTab);
			tabPane.getTabs().add(tabSignIn);
			tabPane.getTabs().add(tabSignUp);
			tabPane.getTabs().add(tabComplaint);
		}
		else if(client.client.getLoggedUser() instanceof SystemAdministrator) {
	//		tabPane.getTabs().add(catalogTab);
			tabPane.getTabs().add(tabupdateUser);

		}
		else if(client.client.getLoggedUser() instanceof StoreManager) {
		//	tabPane.getTabs().add(catalogTab);
			tabPane.getTabs().add(tabReports);
			tabPane.getTabs().add(tabUpdateCatalog);
			tabPane.getTabs().add(tabAddCatalogItem);
		}
		else if(client.client.getLoggedUser() instanceof Customer) {
	//		tabPane.getTabs().add(catalogTab);
			tabPane.getTabs().add(tabOrder);
			tabPane.getTabs().add(tabComplaint);

		}
		else if(client.client.getLoggedUser() instanceof Employee) {
	//		tabPane.getTabs().add(catalogTab);
			tabPane.getTabs().add(tabUpdateCatalog);
			tabPane.getTabs().add(tabAddCatalogItem);

		}
		else if(client.client.getLoggedUser() instanceof customerService) {
	//		tabPane.getTabs().add(catalogTab);
			tabPane.getTabs().add(tabHandleComplaint);

		}

		else if(client.client.getLoggedUser() instanceof ChainManager) {
	//		tabPane.getTabs().add(catalogTab);
			tabPane.getTabs().add(tabReports);
			tabPane.getTabs().add(tabUpdateCatalog);
			tabPane.getTabs().add(tabAddCatalogItem);

		}
	}
	
	
}