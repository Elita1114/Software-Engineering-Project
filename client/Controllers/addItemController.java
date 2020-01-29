package client.Controllers;


import java.io.IOException;
import java.util.ArrayList;

import common.CatalogItem;
import common.User;
import common.UserRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

public class addItemController {
    User user;

    @FXML
    private Button addItem;

    @FXML
    private TextField pathText;

    @FXML
    private TextField saleText;

    @FXML
    private TextField colorText;

    @FXML
    private TextField typeText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField priceText;

    @FXML
    void add(ActionEvent event) {
    	try {
    		checkInput(nameText.getText(), descriptionText.getText(),colorText.getText(),pathText.getText(),priceText.getText(),saleText.getText(),typeText.getText());
    	} catch(IOException e) {
    		new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
    		return;
    	} 	
    	user= MainController.getClient().client.getLoggedUser();
        String titel= nameText.getText(), description= descriptionText.getText(), color=colorText.getText(), path=pathText.getText();
        float price=Float.valueOf(priceText.getText()),sale=Float.valueOf(saleText.getText());
        int type =Integer.parseInt(typeText.getText()),store = user.store;
    	ArrayList<Object> args =  new ArrayList<Object>();
    	CatalogItem updatedItem = new CatalogItem(titel, description,color,price,0,path, type, store, sale);// 0 in id for now, database will give it id
    	args.add(updatedItem);
    	UserRequest user_request = new UserRequest("#addCatalogItem",  args);
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	System.out.println("entered");
    	    	MainController.getClient().client.handleMessageFromClientUI(user_request);
    	    	System.out.println("finished_1");
    	    	MainController.getClient().client.flagServerAns=false;
    	    	System.out.println("set add catalog Item flag\n");
	  			while(!MainController.getClient().client.flagServerAns) {
	  				try {
	  					Thread.sleep(100);
	  				} catch (InterruptedException e) {
	  					// TODO Auto-generated catch block
					e.printStackTrace();
	  				}
	  				
	  			}
	  			success();
    	    }
    	});
    }

    private void success() {
    	new Alert(AlertType.INFORMATION, "item added :)").showAndWait();
    }
    
    private void checkInput(String nameText1,String descriptionText1,String colorText1,String pathText1,String priceText1,String saleText1,String typeText1) throws IOException {
    	String error_message = "";
    	if(!(nameText1.length()>0))
    		error_message += "fill user name\n";
    	if(!(descriptionText1.length()>0))
    		error_message += "fill description\n";
    	if(!(colorText1.length()>0))
    		error_message += "fill color\n";
    	if(!(pathText1.length()>0))
    		error_message += "fill path\n";
    	if(!(priceText1.length()>0))
    		error_message += "fill price\n";
    	else {
            try{
                Float.parseFloat(priceText1);
            }catch(NumberFormatException e){
            	error_message += "price must be number\n";
            }
    	}
    	if(!(saleText1.length()>0))
    		error_message += "fill sale\n";
    	else {
            try{
                Float.parseFloat(saleText1);
                if(Float.valueOf(saleText1) <0 ||Float.valueOf(saleText1) >1)
                	error_message += "sale must be number between 1 and 0\n";
            }catch(NumberFormatException e){
            	error_message += "sale must be number\n";
            }
    	}
        if(!(typeText1.length()>0))
        	error_message += "fill type\n";
        else {
            try{
                Integer.parseInt(typeText1);
            }catch(NumberFormatException e){
            	error_message += "type must be integer\n";
            }
        }



        
        
    	if(error_message.length() > 0)
    		throw(new IOException(error_message));
    		
    }
}
