package client.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import common.Catalog;
import common.CatalogItem;
import common.Item;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Sample Skeleton for 'CatalogScene.fxml' Controller Class
 */

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class CatalogController implements Initializable {

    @FXML // fx:id="lvItems"
    private ListView<CatalogItem> lvItems; // Value injected by FXMLLoader
    private ObservableList<CatalogItem> itemObservableList;
    private int x;
    
    public CatalogController()  {
    	
        itemObservableList = FXCollections.observableArrayList();

        //add some Students
        /*itemObservableList.addAll(
                new Item("John Doe1", "trhrg","blue"),
                new Item("John Doe2", "abcd","red"),
                new Item("John Doe3", "efgh","orange"));
        */
        if(!itemObservableList.isEmpty()) {
        	System.out.println("not empty\n");
        }
        else
        	System.out.println("empty\n");
        
        
        
    }
    
    public void setX(int x_) {
    	x=x_;
    	System.out.println(Integer.toString(x));
    }
    
    public void setCatalog(ArrayList<CatalogItem> itemList_) {
    	for (CatalogItem item : itemList_) { 		      
    		itemObservableList.add(item);
       }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	
    	Platform.runLater(() -> {

    		lvItems.setItems(itemObservableList);
        	lvItems.setCellFactory(itemListView  -> new ItemListViewCell());

        });
    	
    }
}
