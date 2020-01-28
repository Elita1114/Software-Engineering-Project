package client.Controllers;

import java.io.IOException;

import common.ChainManager;
import common.Complaint;
import common.Customer;
import common.Employee;
import common.Item;
import common.MonthlyReport;
import common.Order;
import common.customerService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class OrderListViewCell extends ListCell<Order>{
    @FXML
    private Button bttnCancel;

    @FXML
    private TextArea taDescription;

    @FXML
    private Button bttnOrder;

    @FXML
    private Label tvTitle;

    @FXML
    private AnchorPane pane;

    @FXML
    void cancelBttnPressed(ActionEvent event) {
    	orderslist_controller.delete_order(this.order);
    }

    @FXML
    void orderDeliveredBttnPressed(ActionEvent event) {
    	orderslist_controller.set_delivered(this.order);
    }
    
    public Order order;
	private FXMLLoader mLLoader;
	OrderListController orderslist_controller;

	    
	public OrderListViewCell(OrderListController orderslist_controller_)
	{
	 	orderslist_controller = orderslist_controller_;
	}
    @FXML
    public void initialize() {
    	if(!(MainController.getLoggedUser() instanceof Customer))
    		bttnCancel.setVisible(false);
    	if(!(MainController.getLoggedUser() instanceof Employee))
    		bttnOrder.setVisible(false);
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
            	System.out.println("Trying to load fxml");
                mLLoader = new FXMLLoader(getClass().getResource("/client/fxml/OrderListViewCell.fxml"));
                System.out.println("error loading fxml");
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } 
				catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("reached here");
            System.out.println(tvTitle);
            System.out.println(taDescription);
            tvTitle.setText("Order ID  " + item.getId());
            taDescription.setText(item.print_order());
            System.out.println("updating item ");

            setText(null);
            setGraphic(pane);
        }
    }
}
