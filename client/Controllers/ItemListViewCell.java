package client.Controllers;


import java.io.IOException;
import java.util.ArrayList;

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

public class ItemListViewCell extends ListCell<CatalogItem>{
	@FXML private Label Title;
	@FXML private Label Description;
	@FXML private Label Color;
	@FXML private Label Price;
	@FXML private ImageView ivIm1;
	
	@FXML private AnchorPane pane;
	
	private FXMLLoader mLLoader;
    @FXML
    private Button dropBtn;
    User user= MainController.getClient().client.getLoggedUser();
    CatalogItem myItem;
	
	
	
    @FXML
    void drop(ActionEvent event) {
    	
    	ArrayList<Object> args =  new ArrayList<Object>();
    	args.add(myItem.getId());
    	UserRequest user_request = new UserRequest("#dropCatalog",  args);
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	System.out.println("entered");
    	    	MainController.getClient().client.handleMessageFromClientUI(user_request);
    	    	System.out.println("finished_1");
    	    	MainController.getClient().client.flagServerAns=false;
    	    	System.out.println("set drop flag\n");
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
    	

    @FXML
    void clickadd(ActionEvent event) {

    }
    
    
	@Override
    protected void updateItem(CatalogItem item, boolean empty) {
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
            Price.setText(String.valueOf(item.getPrice()));
            ivIm1.setImage(new Image(item.getImagePath()));

            
            
            dropBtn.setVisible(false);
            if(user!=null && user.status!=Status.client )
                dropBtn.setVisible(true);
            
            setText(null);
            setGraphic(pane);
        }

    }
	
}
