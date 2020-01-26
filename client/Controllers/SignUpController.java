package client.Controllers;


import java.io.IOException;
import java.util.*;
import client.ClientConsole;
import common.Complaint;
import common.PayingMethod;
import common.Status;
import common.Store;
import common.User;
import common.UserRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class SignUpController {

    private MainController mainController;
    
    @FXML
    private RadioButton MonthlySubRadio;

    @FXML
    private TextField CardNumberText;

    @FXML
    private Button signUpbtn;

    @FXML
    private TextField PhoneNumberText;
    
    @FXML
    private TextField passText;

    @FXML
    private ToggleGroup tgPayMethod;

    @FXML
    private TextField oassAgainText;

    @FXML
    private RadioButton AnnualSubRadio;

    @FXML
    private TextField nameText;

    @FXML
    private RadioButton PayPErOrderRadio;

    @FXML
    private TextField IDText;
    
    @FXML
    private MenuButton storeselector;
    
    public void injectMainController(MainController mainController_) {
		mainController = mainController_;
	}
    @FXML
	private void initialize() {

    }
   
    
    @FXML
    void signUp(ActionEvent event) {
    	String user_name = nameText.getText();
    	String I_D = IDText.getText();
    	String passwd = passText.getText();
    	String passValidate = oassAgainText.getText();
    	String cardNumber = CardNumberText.getText();
    	int pay_method = PayingMethod.pay_per_order;
    	String phoneNumber = PhoneNumberText.getText();
    	System.out.println(storeselector.getText().substring(0,1));
    	int store = Integer.parseInt(storeselector.getText().substring(0,1));
    	
    	if(PayPErOrderRadio.isSelected())
    		pay_method = PayingMethod.pay_per_order; 
    	if(MonthlySubRadio.isSelected())
    		pay_method = PayingMethod.monthly_subscription;
    	if(AnnualSubRadio.isSelected())
    		pay_method = PayingMethod.annual_subscription;
    	
    	try {
    		checkInput(passwd, passValidate);
    	} catch(IOException e) {
    		new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
    		return;
    	}
    	
    	// User(String username, String password, String id, String credit_card_number, int pay_method,String phone_number, String store)
    	User new_user =  new User(0, user_name, passwd, I_D, cardNumber, pay_method, phoneNumber, store,Status.values()[0]); 
    	ArrayList<Object> args =  new ArrayList<Object>();
    	args.add(new_user);
    	UserRequest user_request = new UserRequest("#signup",  args);

    	System.out.println("sending request to server");
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	System.out.println(mainController);
    	    	System.out.println("entered");
    	    	mainController.getClient().client.handleMessageFromClientUI(user_request);
    	    	System.out.println("finished_1");
    	    }
    	});
    	System.out.println("finished");
    	
    	
    	/*
    	try{
    		openCatalogscene(event);
  		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		
    
    }

    void updateStoresList() {
    	EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	storeselector.setText(((MenuItem)e.getSource()).getText() + ""); 
            } 
        }; 
  
        // add action events to the menuitems 
        
    	for (Store store : mainController.getClient().client.getStoreslist().stores) { 
    		MenuItem m = new MenuItem(store.getId() + ". " + store.getName());
    		m.setOnAction(event1); 
    		storeselector.getItems().add(m);
		}
    }
    
    void openCatalogscene(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/fxml/CatalogScene.fxml"));     
		Parent root = (Parent)fxmlLoader.load();
		Scene scene = new Scene(root); 
		Stage appstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appstage.setScene(scene);    	
		appstage.show(); 
    }
    
    
    private void checkInput(String pass,  String secondPass) throws IOException {
    	String error_message = "";
    	if(!(pass.length()>1))
    		error_message += "Your password is short\n";
    	if(!pass.equals(secondPass))
    		error_message += "Your two enterd passwords are not equal\n";
  
    	if(error_message.length() > 0)
    		throw(new IOException(error_message));
    		
    }

    
    
    
}