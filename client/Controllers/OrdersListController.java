package client.Controllers;


import java.util.ArrayList;

import common.Complaint;
import common.MonthlyReport;
import common.Order;
import common.UserRequest;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class OrdersListController {

	private MainController mainController;
    @FXML private ListView<Order> lvReports;
    static public ObservableList<Order> itemObservableList;

    public OrdersListController() {
    	itemObservableList = FXCollections.observableArrayList();
    	// complaintBarChart.setVisible(false);
    }
    
    public void injectMainController(MainController mainController_) {
		mainController = mainController_;
	}
    
    static public void setOrder(ArrayList<Order> itemList_) {
    	itemObservableList.clear();
    	if(!itemList_.isEmpty()) {
    		for (Order item : itemList_) { 		      
	    		itemObservableList.add(item);
    		}
    	}
    }
    
    public void updateOrder() {
    	lvReports.setItems(itemObservableList);
    	lvReports.setCellFactory(itemListView  -> new OrderListViewCell(this));
    }
    @FXML
    public void initialize() {
    	Platform.runLater(() -> {
    		lvReports.setItems(itemObservableList);
    		lvReports.setCellFactory(itemListView  -> new OrderListViewCell(this));

        });	
    }
    
    public void fetchReports() { 
    	System.out.println("fetching reports");
		ArrayList<Object> args =  new ArrayList<Object>();
		args.add(mainController.getClient().client.getLoggedUser());
    	UserRequest user_request = new UserRequest("#getreports",  args);
    	
		Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	mainController.getClient().client.flagServerAns=false;
    	    	mainController.getClient().client.handleMessageFromClientUI(user_request);
    			while(!mainController.getClient().client.flagServerAns) {
    				try {
    					Thread.sleep(100);
    					System.out.println("waiitng for server");
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				};
    			}
    			System.out.println("got Reports");
    			System.out.println("reports are "+ mainController.getClient().monthly_reports.getItemList());
    			setOrder(mainController.getClient().orders.getItemList());
    			updateOrder();
    			System.out.println("finished displaying reports");
    			System.out.println(itemObservableList);
    			mainController.getClient().client.flagServerAns = false;
    	    }
    	});
    }

}
