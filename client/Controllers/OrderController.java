package client.Controllers;
/**
 * Sample Skeleton for 'Order.fxml' Controller Class
 */

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;

public class OrderController {
	
    private MainController mainController;
    
    public void injectMainController(MainController mainController_) {
		mainController = mainController_;
	}
    
    private BooleanProperty wantShipping = new SimpleBooleanProperty(false);

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

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
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	recieverText.visibleProperty().bind(wantShipping);  
        addressText.visibleProperty().bind(wantShipping);  
        phonenumberText.visibleProperty().bind(wantShipping);  
    }
    
    @FXML
    void shippingBttnSelect(ActionEvent event) {
    	wantShipping.set(!wantShipping.get());
    }
    
    @FXML
    void orderbttnPressed(ActionEvent event) {
    	String letter = letterText.getText();
    	LocalDate date_pick = dateSelect.getValue();
    	Date date = Date.from(date_pick.atStartOfDay(ZoneId.systemDefault()).toInstant());
    	System.out.println("the selected date is: " + date);
    	String address="", recieverName="", phoneNumber="";
    	if(shippingButton.isSelected())
    	{
    		address = addressText.getText();
    		recieverName = recieverText.getText();
    		phoneNumber = phonenumberText.getText();
    	}
    }
}

