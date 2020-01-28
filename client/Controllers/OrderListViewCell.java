package client.Controllers;

import java.io.IOException;

import common.Complaint;
import common.Item;
import common.MonthlyReport;
import common.Order;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class OrderListViewCell extends ListCell<Order>{
	  	@FXML private Button bttnIncome;
	    @FXML private Button bttnComplaints;
	    @FXML private Button bttnOrders;
	    @FXML private Button bttnCompare;
	    @FXML private Label tvTitle;
	    @FXML private Label tvStore;
	    @FXML private AnchorPane pane;
	    public Order order;
		private FXMLLoader mLLoader;
		OrdersListController orderslist_controller;
	    
	    public OrderListViewCell(OrdersListController orderslist_controller_)
	    {
	    	orderslist_controller = orderslist_controller_;
	    }
	    
	    @Override
	    protected void updateItem(Order item, boolean empty) {
	        super.updateItem(item, empty);
	        order = item;
	        if(empty || item == null) {
	            setText(null);
	            setGraphic(null);
	        } 
	        else {
	            if (mLLoader == null) {
	                mLLoader = new FXMLLoader(getClass().getResource("/client/fxml/OrderListViewCell.fxml"));
	                mLLoader.setController(this);
	                try {
	                    mLLoader.load();
	                } 
					catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            System.out.println("updating item ");
	            tvTitle.setText("reported for: " + item.date);
	            tvStore.setText("Store " + String.valueOf(item.getStore()));
	            System.out.println(tvTitle.getText());
	            System.out.println(tvStore.getText());
	            setText(null);
	            setGraphic(pane);
	        }

	    }
}
