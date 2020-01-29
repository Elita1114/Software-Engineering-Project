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
    private TextField EmailText;
    
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
    	String user_name = removeSpaces(nameText.getText());
    	
		String I_D = removeNonNumbers(removeSpaces(IDText.getText()));    	
    	String passwd = removeSpaces(passText.getText());
    	String passValidate = removeSpaces(oassAgainText.getText());
    	String cardNumber = removeNonNumbers(removeSpaces(CardNumberText.getText()));
    	int pay_method = -1;
    	String phoneNumber = removeNonNumbers(removeSpaces(PhoneNumberText.getText()));
    	String email = removeSpaces(EmailText.getText());
    	int store = -1;
    	if (is_numeritic(storeselector.getText().substring(0,1))) {
    		store = Integer.parseInt(storeselector.getText().substring(0,1));
    	}
    	
    	if(PayPErOrderRadio.isSelected())
    		pay_method = PayingMethod.pay_per_order; 
    	if(MonthlySubRadio.isSelected())
    		pay_method = PayingMethod.monthly_subscription;
    	if(AnnualSubRadio.isSelected())
    		pay_method = PayingMethod.annual_subscription;
    	    	
    	try {
    		checkInput(passwd, passValidate, user_name, I_D, cardNumber, 
    	    		phoneNumber, email, pay_method, store);
    	} catch(IOException e) {
    		new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
    		return;
    	}
    	
    	// User(String username, String password, String id, String credit_card_number, int pay_method,String phone_number, String store)
    	User new_user =  new User(0, user_name, passwd, I_D, cardNumber, pay_method, phoneNumber, store,Status.values()[0],email); 
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
    	
		new Alert(Alert.AlertType.CONFIRMATION, "Sign up completed").showAndWait();
    	
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
    
    
    private void checkInput(String passwd, String passValidate, String user_name, String I_D, String cardNumber, 
    		String phoneNumber, String email, int pay_method, int store) throws IOException {
	    String error_message = "";
	    
	    if(pay_method == -1)
			error_message += "No pay method (subsciption)\n";
	    
	    if(store == -1)
			error_message += "No store\n";
	    
	    if(user_name == null)
				error_message += "No usename\n";
		else if(user_name.length() == 0)
			error_message += "No usename\n";
		
		if(I_D == null)
			error_message += "No ID\n";
		else if(I_D.length() == 0)
			error_message += "No ID\n";
		
		if(cardNumber == null)
			error_message += "No card number\n";
		else if(cardNumber.length() == 0)
			error_message += "No card number\n";
		
		boolean pass = true;
		boolean passVal = true;
		if(passwd == null)
		{
			error_message += "No Password\n";
			pass = false;
		}
		else if(passwd.length() == 0)
		{
			error_message += "No Password\n";
			pass = false;
		}
		else if(passValidate == null)
		{
			error_message += "No Password validation\n";
			passVal = false;
		}
		else if(passValidate.length() == 0)
		{
			error_message += "No Password validation\n";
			passVal = false;
		}
		if(!(passwd.length()>=6) && pass)
			error_message += "Your password is too short\n";
		else if(pass && passVal && !passwd.equals(passValidate))
			error_message += "Your enterd passwords are not equal\n";
	
		if(I_D.length()!=9)
			error_message += "Your ID isn't right\n";
		
		if(cardNumber.length()<11 || cardNumber.length()>19)
			error_message += "Your card number isn't right\n";
		
		if(phoneNumber == null)
			error_message += "No phone number\n";
		else if(phoneNumber.length() == 0)
			error_message += "No phone number\n";
		else if(phoneNumber.length()!=10 && phoneNumber.length()!=12) // Israeli
			error_message += "Your phone number isn't right\n";
				
		if(!validEmail(email))
			error_message += "Your email isn't right\n";
	    	
    	if(error_message.length() > 0)
    		throw(new IOException(error_message));
    }


    public static boolean is_numeritic(String str)
    {
		return (str.matches("[0-9]+"));
    }
    
    public static String removeSpaces(String str){
    	int i;
		for(i = str.length()-1; i >= 0 && str.charAt(i) == ' '; --i);
		return str.substring(0, i+1);
    }
    
    public static String removeNonNumbers(String str){
    	String temp = "";
		for(int i = 0; i < str.length(); ++i) 
			if(str.charAt(i) >= '0' && str.charAt(i) <= '9')
				temp += str.charAt(i);
		return temp;
    }
    
    public static boolean validEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
     }
    
    
}