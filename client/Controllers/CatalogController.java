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

public class CatalogController implements Initializable {

    @FXML // fx:id="lvItems"
    private ListView<CatalogItem> lvItems; // Value injected by FXMLLoader
    private ObservableList<CatalogItem> itemObservableList;
    private int x;
    private MainController mainController;
    
    
    
    @FXML
    private Button btnAll; //0
    @FXML
    private Button btnDaisy; //1
    @FXML
    private Button btnArrang; //2
    @FXML
    private Button btnIris; //3
    @FXML
    private Button btnLily; //4
    @FXML
    private Button btnMix; //5
    @FXML
    private Button btnOrchid; //6
    @FXML
    private Button btnRose; //7
    @FXML
    private Button btnWedding; //8
    @FXML
    private Button btnBonsai; //9
    @FXML
    private Button btnOffice; //10
    @FXML
    private Button btnGreen; //11
    @FXML
    private Button btnCacti; //12

    

    

    

    

    
    public CatalogController()  {
    	
        itemObservableList = FXCollections.observableArrayList();

        //add some Students
        /*
        		itemObservableList.addAll(
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
        	lvItems.setCellFactory(itemListView  -> new ItemListViewCell());

        });
    	
    }
    
    public void updateCatalog() {
    	//lvItems.getItems().clear();
    	lvItems.setItems(itemObservableList);
    	lvItems.setCellFactory(itemListView  -> new ItemListViewCell());
    }
    
    
    
    private void updateButtonPressed(String msg) {
    	Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	mainController.getClient().flagCatalog=false;
    	    	mainController.getClient().client.handleMessageFromClientUI(msg);
    			while(!mainController.getClient().flagCatalog) {
    				try {
    					Thread.sleep(100);
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    			setCatalog(mainController.getClient().catalog.getList());
    			updateCatalog();
    			mainController.getClient().flagCatalog=false;
    	    }
    	});
		
    }
    
    @FXML
    void daisyPressed(ActionEvent event) {
    	updateButtonPressed("#getCatalog 1");
    	
    }

    @FXML
    void arrangPressed(ActionEvent event) {
    	updateButtonPressed("#getCatalog 2");
    	
    }

    @FXML
    void irisPressed(ActionEvent event) {
    	updateButtonPressed("#getCatalog 3");
    	
    }

    @FXML
    void lilyPressed(ActionEvent event) {
    	updateButtonPressed("#getCatalog 4");
    }

    @FXML
    void mixPressed(ActionEvent event) {
    	updateButtonPressed("#getCatalog 5");
    }

    @FXML
    void orchidPressed(ActionEvent event) {
    	updateButtonPressed("#getCatalog 6");
    }
    
    @FXML
    void rosePressed(ActionEvent event) {
    	updateButtonPressed("#getCatalog 7");
    }

    @FXML
    void weddingPressed(ActionEvent event) {
    	updateButtonPressed("#getCatalog 8");
    }

    @FXML
    void bonsaiPressed(ActionEvent event) {
    	updateButtonPressed("#getCatalog 9");
    }

    @FXML
    void officePressed(ActionEvent event) {
    	updateButtonPressed("#getCatalog 10");
    }

    @FXML
    void greenPressed(ActionEvent event) {
    	updateButtonPressed("#getCatalog 11");
    }

    @FXML
    void cactiPressed(ActionEvent event) {
    	updateButtonPressed("#getCatalog 12");
    }
    
    @FXML
    void allPressed(ActionEvent event) {
    	updateButtonPressed("#getCatalog 0");
    }
    
    
    
}
