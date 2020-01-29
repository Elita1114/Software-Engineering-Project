package client.Controllers;


import java.io.IOException;
import java.util.ArrayList;

import common.CatalogItem;
import common.Item;
import common.User;
import common.UserRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class updateCatalog_ItemListViewCell extends ListCell<CatalogItem>{
    User user;
    UpdateCatalogController updateCatalog_controller;	
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
    private TextField typeField;
    

    @FXML
    private TextField saleField;

    
    @FXML
    private ImageView ivIm1;
	private FXMLLoader mLLoader;
    CatalogItem myItem;

    public updateCatalog_ItemListViewCell(UpdateCatalogController updateCatalog_controller_)
    {
    	updateCatalog_controller = updateCatalog_controller_;
    }
    
	
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
	  			MainController.getClient().client.flagServerAns=false;
	  			updateCatalog_controller.getMainController().getClient().flagCatalog = false;
  				ArrayList<Object> args =  new ArrayList<Object>();
  		    	args.add(updateCatalog_controller.getMainController().getClient().client.getLoggedUser());
  		    	UserRequest user_request = new UserRequest("#getCatalog 0",  args);
  		    	updateCatalog_controller.getMainController().getClient().client.handleMessageFromClientUI(user_request);
	  			while(!(updateCatalog_controller.getMainController()).getClient().flagCatalog) {
	  				try {
	  					Thread.sleep(100);
	  				} catch (InterruptedException e) {
	  					// TODO Auto-generated catch block
					e.printStackTrace();
	  				}
	  			}
	  			updateCatalog_controller.getMainController().setCatalog(updateCatalog_controller.getMainController().getClient().catalog.getList());
	  			updateCatalog_controller.getMainController().setUpdateCatalog(updateCatalog_controller.getMainController().getClient().catalog.getList());
	  			updateCatalog_controller.getMainController().getClient().flagCatalog=false;
	  		    new Alert(AlertType.INFORMATION, "item deleted :)").showAndWait();
	  		    
    	    }
    	});
    }
    	
	
	
	
	@Override
    protected void updateItem(CatalogItem item, boolean empty) {
		user= MainController.getClient().client.getLoggedUser();
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

            //filling field with data
            Title.setText(String.valueOf(item.getName()));
            Description.setText(String.valueOf(item.getDescription()));
            Color.setText(String.valueOf(item.getColor()));
            Price.setText(String.valueOf(item.getPrice()));
            IDlablel.setText(String.valueOf(item.getId()));
            saleField.setText(String.valueOf(item.getSale()));
            typeField.setText(String.valueOf(item.getType()));
            if(!item.getImagePath().contentEquals(""))
            	ivIm1.setImage(new Image(item.getImagePath()));
            Path.setText(String.valueOf(item.getImagePath()));

            setText(null);
            setGraphic(pane);
        }

    }
	
	
    @FXML
    void saveItem(ActionEvent event) {
    	//save the updated flower
        String titel= Title.getText(), description= Description.getText(), color=Color.getText(), path=Path.getText();
        float price=Float.valueOf(Price.getText()),sale=Float.valueOf(saleField.getText());
        int id= Integer.parseInt(IDlablel.getText()), type =Integer.parseInt(typeField.getText()),store = user.store;

    	ArrayList<Object> args =  new ArrayList<Object>();
    	
    	CatalogItem updatedItem = new CatalogItem(titel, description,color,price,id,path, type, store, sale);// 0 in id for now, database will give it id
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
	  			MainController.getClient().client.flagServerAns=false;
	  			updateCatalog_controller.getMainController().getClient().flagCatalog = false;
  				ArrayList<Object> args =  new ArrayList<Object>();
  		    	args.add(updateCatalog_controller.getMainController().getClient().client.getLoggedUser());
  		    	UserRequest user_request = new UserRequest("#getCatalog 0",  args);
  		    	updateCatalog_controller.getMainController().getClient().client.handleMessageFromClientUI(user_request);
	  			while(!(updateCatalog_controller.getMainController()).getClient().flagCatalog) {
	  				try {
	  					Thread.sleep(100);
	  				} catch (InterruptedException e) {
	  					// TODO Auto-generated catch block
					e.printStackTrace();
	  				}
	  			}
	  			updateCatalog_controller.getMainController().setCatalog(updateCatalog_controller.getMainController().getClient().catalog.getList());
	  			updateCatalog_controller.getMainController().setUpdateCatalog(updateCatalog_controller.getMainController().getClient().catalog.getList());
	  			updateCatalog_controller.getMainController().getClient().flagCatalog=false;
	  		    new Alert(AlertType.INFORMATION, "item updated :)").showAndWait();

    	    }
    	});
    }




	
}
