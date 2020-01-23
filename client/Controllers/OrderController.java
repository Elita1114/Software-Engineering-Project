package client.Controllers;
/**
 * Sample Skeleton for 'Order.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;
import java.util.ResourceBundle;

import common.Catalog;
import common.CatalogItem;
import common.Complaint;
import common.ComplaintsList;
import common.Item;
import common.Order;
import common.UserRequest;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class OrderController {
	private ArrayList<Item> order_items;
	private MainController mainController;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    private BooleanProperty wantShipping =  new SimpleBooleanProperty(false);
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private TextField phonenumberText;

    @FXML
    private TextField timeText;

    @FXML
    private RadioButton shippingButton;

    @FXML
    private TextField recieverText;

    @FXML
    private TextField addressText;

    @FXML
    private DatePicker dateSelect;

    @FXML
    private TextArea letterText;
    
    @FXML
    private Label addressLabel;
    
    @FXML
    private Label phoneLabel;
    
    @FXML
    private Label recieverLabel;
    

    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	recieverText.visibleProperty().bind(wantShipping);  
        addressText.visibleProperty().bind(wantShipping);  
        phonenumberText.visibleProperty().bind(wantShipping);  
        addressLabel.visibleProperty().bind(wantShipping);  
        phoneLabel.visibleProperty().bind(wantShipping);
        recieverLabel.visibleProperty().bind(wantShipping);  
    }
    
    @FXML
    void shippingBttnSelect(ActionEvent event) {
    	wantShipping.set(!wantShipping.get());
    }
    
    @FXML
    void orderbttnPressed(ActionEvent event) {
    	String letter = letterText.getText();
    	LocalDate date_pick = dateSelect.getValue();
    	//Date date = Date.from(date_pick.atStartOfDay(ZoneId.systemDefault()).toInstant());
    	//Date date = 
    	Date date = new Date(System.currentTimeMillis());
    	System.out.println("the selected date is: " + date);
    	String address="", recieverName="", phoneNumber="";
    	boolean want_shipping = shippingButton.isSelected();
    	if(want_shipping)
    	{
    		address = addressText.getText();
    		recieverName = recieverText.getText();
    		phoneNumber = phonenumberText.getText();
    	}
    	Order my_order = new Order(order_items, date, letter, want_shipping, address, recieverName, phoneNumber);
    	ArrayList<Object> args =  new ArrayList<Object>();
    	args.add(mainController.getClient().client.getLoggedUser());
    	args.add(my_order);
    	System.out.println(my_order.getDetails());
    	UserRequest user_request = new UserRequest("#order",  args);
    	System.out.println(user_request);
    	System.out.println("sending request to server blah blah");
    	System.out.println(mainController);
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	System.out.println(mainController);
    	    	System.out.println("entered order");
    	    	mainController.getClient().client.handleMessageFromClientUI(user_request);
    	    	System.out.println("finished_1");
    	    }
    	});
    	System.out.println("finished");
 
    }

	public void injectMainController(MainController mainController_) {
		mainController = mainController_;
		
	}
}