package client.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.MyThread;
import common.Catalog;
import common.CatalogItem;
import common.Item;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

/**
 * Sample Skeleton for 'CatalogScene.fxml' Controller Class
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UpdateCatalogController implements Initializable {

    @FXML // fx:id="lvItems"
    private ListView<CatalogItem> lvItems; // Value injected by FXMLLoader
    private ObservableList<CatalogItem> itemObservableList;
    private int x;
    private MainController mainController;
    
    
    public UpdateCatalogController()  {
    	
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
    
    public void injectMainController(MainController mainController_) {
		mainController = mainController_;
	}
    
    public void setX(int x_) {
    	x=x_;
    	System.out.println(Integer.toString(x));
    }
    
    public void setCatalog(ArrayList<CatalogItem> itemList_) {
    	itemObservableList.clear();
    	if(!itemList_.isEmpty()) {
    		for (CatalogItem item : itemList_) { 		      
	    		itemObservableList.add(item);
    		}
    	}
    }
    

    @FXML
    void openloginscene(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/fxml/Login.fxml"));     
		Parent root = (Parent)fxmlLoader.load();
		Scene scene = new Scene(root); 
		Stage appstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appstage.setScene(scene);    	
		appstage.show(); 
    }

    @FXML
    void opensignupscene(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/fxml/SignUp.fxml"));     
		Parent root = (Parent)fxmlLoader.load();
		Scene scene = new Scene(root); 
		Stage appstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appstage.setScene(scene);    	
		appstage.show(); 
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	
    	Platform.runLater(() -> {

    		lvItems.setItems(itemObservableList);
        	lvItems.setCellFactory(itemListView  -> new updateCatalog_ItemListViewCell());

        });
    	
    }
    
    public void updateCatalog() {
    	//lvItems.getItems().clear();
    	lvItems.setItems(itemObservableList);
    	lvItems.setCellFactory(itemListView  -> new updateCatalog_ItemListViewCell());
    }
    
    
    

    
}
