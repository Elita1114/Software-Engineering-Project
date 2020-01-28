package client.Controllers;


import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

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

public class OrderListController {

	private MainController mainController;
    @FXML private ListView<Order> lvOrders;
    static public ObservableList<Order> itemObservableList;

    public OrderListController() {
    	itemObservableList = FXCollections.observableArrayList();
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
    	lvOrders.setItems(itemObservableList);
    	lvOrders.setCellFactory(itemListView  -> new OrderListViewCell(this));
    }
    @FXML
    public void initialize() {
    	Platform.runLater(() -> {
    		lvOrders.setItems(itemObservableList);
    		lvOrders.setCellFactory(itemListView  -> new OrderListViewCell(this));

        });	
    }
    
    public void delete_order(Order order)
    {
    	ArrayList<Object> args =  new ArrayList<Object>();
		args.add(order);
    	UserRequest user_request = new UserRequest("#deleteOrder",  args);
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
    			mainController.getClient().client.flagServerAns = false;
    			fetchOrders();
    	    }
    	    
    	});
    }
    
    public void set_delivered(Order order)
    {
    	ArrayList<Object> args =  new ArrayList<Object>();
		args.add(order);
    	UserRequest user_request = new UserRequest("#setdelivered",  args);
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
    			mainController.getClient().client.flagServerAns = false;
    			fetchOrders();
    			/*
    			if(order.want_shipping() && !order.get_shipping_reciever().equals(mainController.getLoggedUser().username)) // send email
    			{
    				String client_email = mainController.getLoggedUser().email;
    				String subject = "Order Delivery";
    				Date delivery_date = new Date(); 
    				String content = "Order number " + order.getId() + " was delievered to " + order.get_shipping_reciever() + " on " + delivery_date.toString();
    				mainController.getClient().send_mail(client_email, subject, content);
    			}
    			*/
    			
    			String client_email = mainController.getLoggedUser().email;
    			System.out.println(client_email);
				String subject = "Order Delivery";
				Date delivery_date = new Date(); 
				String content = "Order number " + order.getId() + " was delievered to " + order.get_shipping_reciever() + " on " + delivery_date.toString();
				mainController.getClient().send_mail(client_email, subject, content);
    	    }
    	    
    	});
    }
    
    public void fetchOrders() { 
    	System.out.println("fetching orders");
		ArrayList<Object> args =  new ArrayList<Object>();
		args.add(mainController.getLoggedUser());
    	UserRequest user_request = new UserRequest("#getOrders",  args);
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
    			System.out.println("got Orders");
    			System.out.println("orders are "+ mainController.getClient().orders.getItemList());
    			setOrder(mainController.getClient().orders.getItemList());
    			updateOrder();
    			System.out.println("finished displaying reports");
    			System.out.println(itemObservableList);
    			mainController.getClient().client.flagServerAns = false;
    	    }
    	});
    }

}
