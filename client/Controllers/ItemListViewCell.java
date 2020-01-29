package client.Controllers;


import java.io.IOException;
import java.util.ArrayList;

import common.CartItem;
import common.CatalogItem;
import common.ChainManager;
import common.Item;
import common.Status;
import common.User;
import common.UserRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Alert.AlertType;
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
	@FXML public Button btnAdd;
    @FXML private Label saleLabel;
    @FXML private Label afterSaleLabel;
    
	OrderController order_controller;
	private FXMLLoader mLLoader;

    User user = MainController.getClient().client.getLoggedUser();
    Item myItem;
	int order = 0;

    @FXML
    void clickadd(ActionEvent event) {
       	if(MainController.getLoggedUser() == null)
       	{
       		Alert alert = new Alert(AlertType.INFORMATION, "You have to register first");
			alert.show();
			return;
       	}
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
        	    new Alert(AlertType.INFORMATION, "Successfully added item to Catalog").show();
    	    }
			
    	    
    	});
    }
    
    public ItemListViewCell(OrderController order_controller_)
    {
    	order_controller = order_controller_;
    }
    
    @FXML
    public void initialize() {
    }
    
    public ItemListViewCell()
    {
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
            System.out.println("updating item " + String.valueOf(item.getName()));
            Title.setText(String.valueOf(item.getName()));
            Description.setText(String.valueOf(item.getDescription()));
            System.out.println("color: "+ item.getColor());
            Color.setText(String.valueOf(item.getColor()));
            if (item instanceof CatalogItem && ((CatalogItem) item).getImagePath() != null)
            {
            	System.out.println("is a catalog item ");
            	CatalogItem c_item = (CatalogItem) item;
            	Price.setText(String.valueOf(c_item.getPrice()));
            	ivIm1.setImage(new Image(c_item.getImagePath()));
            	saleLabel.setText(String.valueOf(c_item.getSale()));
            	afterSaleLabel.setText(String.valueOf((1-c_item.getSale())*c_item.getPrice()));
            }
            else {
            	if (item instanceof CatalogItem)
            	{
                	CatalogItem c_item = (CatalogItem) item;
                	Price.setText(String.valueOf(c_item.getPrice()));  
                	saleLabel.setText(String.valueOf(c_item.getSale()));
                	afterSaleLabel.setText(String.valueOf((1-c_item.getSale())*c_item.getPrice()));
            	}
            	order=1;
            	btnAdd.setText("Remove");
            	btnAdd.setOnAction(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent e) {
            		ArrayList<Object> args =  new ArrayList<Object>();
                	args.add(MainController.getClient().client.getLoggedUser());
                	args.add(new CartItem(myItem,1));
                	UserRequest user_request = new UserRequest("#removefromcart",  args);
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
                	    	order_controller.fetchOrder();
                	    }
                	});
                } });
            }

            
            

            setText(null);
            setGraphic(pane);
        }

    }
	
}
