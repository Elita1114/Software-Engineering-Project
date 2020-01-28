package client.Controllers;

import java.awt.TextArea;
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
    @FXML private Button bttnCancel;
    @FXML private TextArea taDescription;
    @FXML private Button bttnOrder;
    @FXML private Label tvTitle;
    @FXML private AnchorPane pane;
    public Order order;
	private FXMLLoader mLLoader;
	OrdersListController orderslist_controller;
	
    @FXML
    void cancelBttnPressed(ActionEvent event) {

    }

    @FXML
    void orderBttnPressed(ActionEvent event) {

    }
	    
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
            tvTitle.setText("Order ID  " + item.getId());
            taDescription.setText(item.toString());
            setText(null);
            setGraphic(pane);
        }
    }
}
