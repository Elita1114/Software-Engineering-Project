package client.Controllers;


import java.io.IOException;
import java.util.ArrayList;

import common.CatalogItem;
import common.Item;
import common.UserRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class updateCatalog_ItemListViewCell extends ListCell<CatalogItem>{
    @FXML
    private Label tvColor;

    @FXML
    private TextField Description;

    @FXML
    private Button saveBtn;

    @FXML
    private TextField Price;

    @FXML
    private TextField Color;

    @FXML
    private TextField Title;

    @FXML
    private Button deleteBtn;

    @FXML
    private AnchorPane pane;

    @FXML
    private Label tvPrice;
    
    @FXML
    private TextField Path;
    
    @FXML
    private RadioButton AddCatalogRadio;
    

    @FXML
    private Label IDlablel;

    @FXML
    private ImageView ivIm1;
	private FXMLLoader mLLoader;
    CatalogItem myItem;

	
	
    @FXML
    void deleteItem(ActionEvent event) {
    	
    	ArrayList<Object> args =  new ArrayList<Object>();
    	args.add(myItem.getId());
    	UserRequest user_request = new UserRequest("#delCatalogItem",  args);
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	System.out.println("entered");
    	    	MainController.getClient().client.handleMessageFromClientUI(user_request);
    	    	System.out.println("finished_1");
    	    	MainController.getClient().client.flagServerAns=false;
    	    	System.out.println("set delete Catalog Item flag\n");
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
    protected void updateItem(CatalogItem item, boolean empty) {
        super.updateItem(item, empty);
        myItem= item;
        if(empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/client/fxml/UpdateCatalog_ListViewCell.fxml"));
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
            IDlablel.setText(String.valueOf(item.getId()));
            if(!item.getImagePath().contentEquals(""))
            	ivIm1.setImage(new Image(item.getImagePath()));
            Path.setText(String.valueOf(item.getImagePath()));

            setText(null);
            setGraphic(pane);
        }

    }
	
	
    @FXML
    void saveItem(ActionEvent event) {
        String titel= Title.getText(), description= Description.getText(), color=Color.getText(), path=Path.getText();
        float price=Float.valueOf(Price.getText());
        int id= Integer.parseInt(IDlablel.getText());

    	ArrayList<Object> args =  new ArrayList<Object>();
    	
    	CatalogItem updatedItem = new CatalogItem(titel,description, color,price,id,path);
    	args.add(updatedItem);
    	UserRequest user_request = new UserRequest("#UpdateItem",  args);
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	System.out.println("entered");
    	    	MainController.getClient().client.handleMessageFromClientUI(user_request);
    	    	System.out.println("finished_1");
    	    	MainController.getClient().client.flagServerAns=false;
    	    	System.out.println("set update flag\n");
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
