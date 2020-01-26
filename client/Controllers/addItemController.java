package client.Controllers;


import java.util.ArrayList;

import common.CatalogItem;
import common.User;
import common.UserRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    	    }
    	});
    }

}
