package client.Controllers;


import java.io.IOException;
import java.util.ArrayList;

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
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class UpdateUserController {
	User userChange;
	private MainController mainController;

    public void injectMainController(MainController mainController_) {
		mainController = mainController_;
	}
    
    @FXML
    private ToggleGroup tgPayMethod;
    @FXML
    private TextField userNameField;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Button signUpbtn;

    @FXML
    private Button findBtn;



    @FXML
    private TextField passText;

    @FXML
    private TextField statusText;

    @FXML
    private TextField storeText;

    @FXML
    private TextField ID;

    @FXML
    private TextField nameText;

    @FXML
    private TextField paymentText;

    @FXML
    private TextField emailField;
    
    @FXML
    private RadioButton PayPErOrderRadio;
    @FXML
    private RadioButton AnnualSubRadio;
    @FXML
    private RadioButton MonthlySubRadio;

    @FXML
    void update(ActionEvent event) {
    	try {
    		checkInput(nameText.getText(), passText.getText(),paymentText.getText(),statusText.getText(),storeText.getText(),phoneNumber.getText(),ID.getText(),emailField.getText());
    	} catch(IOException e) {
    		new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
    		return;
    	} 	
    	if((userChange=mainController.getClient().foundUser)!=null) {
	    	User UpdatedUser;
	    	int pay_method = PayingMethod.pay_per_order;
	        String name= nameText.getText(), pass= passText.getText(), payment=paymentText.getText(), phoneNumber1=phoneNumber.getText(), ID1 =ID.getText(), email=emailField.getText();
	        int store= Integer.parseInt(storeText.getText()), user_id = userChange.user_id,status=Integer.parseInt(statusText.getText());
	    	if(PayPErOrderRadio.isSelected())
	    		pay_method = PayingMethod.pay_per_order; 
	    	if(MonthlySubRadio.isSelected())
	    		pay_method = PayingMethod.monthly_subscription;
	    	if(AnnualSubRadio.isSelected())
	    		pay_method = PayingMethod.annual_subscription;
	  	  switch(Integer.parseInt(statusText.getText())) {
	  	  case 1:
	  		UpdatedUser = new StoreManager(user_id, name,pass, ID1, payment,pay_method, phoneNumber1, store,Status.values()[status],email);
	  		  break;
	  	  case 2:
	  		UpdatedUser = ChainManager.getInstance(user_id, name,pass, ID1, payment,pay_method, phoneNumber1, store,Status.values()[status],email);
	  		  break;
	  	  case 3:
	  		UpdatedUser = new Employee(user_id, name,pass, ID1, payment,pay_method, phoneNumber1, store,Status.values()[status],email);
	  		  break;
	  	  case 4:
	  		UpdatedUser =new customerService(user_id, name,pass, ID1, payment,pay_method, phoneNumber1, store,Status.values()[status],email);
	  		  break;
	  	  case 5:
	  		UpdatedUser =new SystemAdministrator(user_id, name,pass, ID1, payment,pay_method, phoneNumber1, store,Status.values()[status],email);
	  		  break;
	  	  default:				    	
	  		UpdatedUser = new Customer(user_id, name,pass, ID1, payment,pay_method, phoneNumber1, store,Status.values()[status],email);
	  		  break;
	  	  }
	    	ArrayList<Object> args =  new ArrayList<Object>();
	    	
	    	args.add(UpdatedUser);
	    	if(userChange.password.equals(pass)) {
	    		args.add(false);
	    	}
	    	else {
	    		args.add(true);
	    	}
	    	UserRequest user_request = new UserRequest("#UpdateUser",  args);
	    	Platform.runLater(new Runnable() {
	    	    @Override
	    	    public void run() {
	    	    	MainController.getClient().client.useralreadyExist= false;
	    	    	System.out.println("entered");
	    	    	MainController.getClient().client.handleMessageFromClientUI(user_request);
	    	    	System.out.println("finished_1");
	    	    	MainController.getClient().client.flagServerAns=false;
	    	    	System.out.println("set update user flag\n");
		  			while(!MainController.getClient().client.flagServerAns) {
		  				try {
		  					Thread.sleep(100);
		  				} catch (InterruptedException e) {
		  					// TODO Auto-generated catch block
						e.printStackTrace();
		  				}	  				
		  			}
	    	    	MainController.getClient().client.flagServerAns=false;
	  				if(MainController.getClient().client.useralreadyExist) {
	  	    	    	MainController.getClient().client.useralreadyExist= false;
		  	  			Alert alert = new Alert(AlertType.ERROR, "Username already exist with this username");
		  				alert.show();
	  				}
	  				else {
	  			    	new Alert(AlertType.INFORMATION, "user updated :)").showAndWait();
	  				}
	
	    	    }
	    	});
	    }else {
	    	new Alert(AlertType.ERROR, "you need to find user first").showAndWait();
	    }
    }

    @FXML
    void find(ActionEvent event) {
        String username= userNameField.getText();
    	ArrayList<Object> args =  new ArrayList<Object>();
    	args.add(username);
    	UserRequest user_request = new UserRequest("#findUser",  args);
    	

    	
    	System.out.println("entered");
    	MainController.getClient().client.handleMessageFromClientUI(user_request);
    	System.out.println("finished_1");
    	MainController.getClient().client.flagServerAns=false;
    	System.out.println("set update flag\n");
    	mainController.getClient().foundUser=null;
	  	while(!MainController.getClient().client.flagServerAns) {
	  		try {
	  			Thread.sleep(100);
	  		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}				
	  	}
    	MainController.getClient().client.flagServerAns=false;
	  	if((userChange=mainController.getClient().foundUser)!=null) {
		  	nameText.setText(String.valueOf(userChange.username));
		  	passText.setText(String.valueOf(userChange.password));
		  	paymentText.setText(String.valueOf(userChange.credit_card_number));
		  	
		   if(userChange instanceof StoreManager) {
			   statusText.setText("1");
			}
			else if(userChange instanceof Customer) {
				   statusText.setText("0");
			}
			else if(userChange instanceof Employee) {
				statusText.setText("3");
			}
			else if(userChange instanceof customerService) {
				statusText.setText("4");
			}
			else if(userChange instanceof SystemAdministrator) {
				statusText.setText("5");
			}
			else if(userChange instanceof ChainManager) {
				statusText.setText("2");
			}
		  	storeText.setText(String.valueOf(userChange.store));
		  	phoneNumber.setText(String.valueOf(userChange.phone_number));
	    	if( PayingMethod.pay_per_order ==userChange.pay_method)
	    		PayPErOrderRadio.setSelected(true);
	    	if(PayingMethod.monthly_subscription ==userChange.pay_method)
	    		MonthlySubRadio.setSelected(true);
	    	if(PayingMethod.annual_subscription ==userChange.pay_method )
	    		AnnualSubRadio.setSelected(true);
		  	ID.setText(String.valueOf(userChange.id));
		  	emailField.setText(String.valueOf(userChange.email));
	  	}
	  	else {
				Alert alert = new Alert(AlertType.ERROR, "Username or store incorrect!");
				alert.show();
			  	nameText.setText("");
			  	passText.setText("");
			  	paymentText.setText("");
				statusText.setText("");
			  	storeText.setText("");
			  	phoneNumber.setText("");
			  	ID.setText("");
			  	emailField.setText("");
	  	}
	  }
    
    private void checkInput(String nameText1,String passText1,String paymentText1,String statusText1,String store1,String phoneNumber1,String ID1,String emailField1) throws IOException {
    	String error_message = "";
    	if(!(nameText1.length()>0))
    		error_message += "fill user name\n";
    	if(!(passText1.length()>0))
    		error_message += "fill password\n";
    	if(!(paymentText1.length()>0))
    		error_message += "fill payment details\n";
    	if(!(statusText1.length()>0))
    		error_message += "fill status\n";
        else {
            try{
                Integer.parseInt(statusText1);
                if(!(Integer.valueOf(statusText1)==0 || Integer.valueOf(statusText1)==1 || Integer.valueOf(statusText1)==2 || Integer.valueOf(statusText1)==3 || Integer.valueOf(statusText1)==4 || Integer.valueOf(statusText1)==5))
                	error_message += "status must be 0-5\n";
            }catch(NumberFormatException e){
            	error_message += "type must be integer\n";
            }
        }
    	if(!(store1.length()>0))
    		error_message += "fill store\n";
    	else {
            try{
            	Integer.parseInt(store1);
            }catch(NumberFormatException e){
            	error_message += "store must be integer\n";
            }
    	}
    	if(!(phoneNumber1.length()>0))
    		error_message += "fill phoneNumber1\n";
        if(!(ID1.length()>0))
        	error_message += "fill type\n";
        else {
            try{
                Integer.parseInt(ID1);
            }catch(NumberFormatException e){
            	error_message += "ID must be integer\n";
            }
        }
    	if(!(emailField1.length()>0))
    		error_message += "fill email\n";        
    	if(error_message.length() > 0)
    		throw(new IOException(error_message));
    		
    }
    
    
}



