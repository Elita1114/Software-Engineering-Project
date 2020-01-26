package client.Controllers;


import java.io.IOException;
import java.util.ArrayList;

import common.CartItem;
import common.CatalogItem;
import common.Item;
import common.Status;
import common.User;
import common.UserRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ItemListViewCell extends ListCell<Item>{
	@FXML private Label Title;
	@FXML private Label Description;
	@FXML private Label Color;
	@FXML private Label Price;
	@FXML private ImageView ivIm1;
	@FXML private AnchorPane pane;
	
	private FXMLLoader mLLoader;

    User user= MainController.getClient().client.getLoggedUser();
    Item myItem;
	
	
	


    @FXML
    void clickadd(ActionEvent event) {
    	System.out.println("send add to cart");
    	ArrayList<Object> args =  new ArrayList<Object>();
    	args.add(MainController.getClient().client.getLoggedUser());
    	args.add(new CartItem(myItem,1));
    	UserRequest user_request = new UserRequest("#addtocart",  args);
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	MainController.getClient().client.flagServerAns = false;
    	    	MainController.getClient().client.handleMessageFromClientUI(user_request);
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
    
	@Override
    protected void updateItem(Item item, boolean empty) {
        super.updateItem(item, empty);
        myItem=item;
        if(empty || item == null) {
            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/client/fxml/ItemListViewCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            Title.setText(String.valueOf(item.getName()));
            Description.setText(String.valueOf(item.getDescription()));
            Color.setText(String.valueOf(item.getColor()));
            if (item instanceof CatalogItem)
            {
            	CatalogItem c_item = (CatalogItem) item;
            	Price.setText(String.valueOf(c_item.getPrice()));
            	ivIm1.setImage(new Image(c_item.getImagePath()));
            }

            
            

            setText(null);
            setGraphic(pane);
        }

    }
	
}
