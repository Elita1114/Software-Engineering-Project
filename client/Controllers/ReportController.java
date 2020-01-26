package client.Controllers;


import java.util.ArrayList;

import common.Complaint;
import common.MonthlyReport;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReportController {

	private MainController mainController;
    @FXML
    private ListView<MonthlyReport> lvReports;
    static public ObservableList<MonthlyReport> itemObservableList;
    
    @FXML
    private TextField tvDescription;

    @FXML
    private TextArea tvContent;
    
    public ReportController() {
    	itemObservableList = FXCollections.observableArrayList();
    }
    
    public void injectMainController(MainController mainController_) {
		mainController = mainController_;
	}
    
    static public void setComplaint(ArrayList<MonthlyReport> itemList_) {
    	itemObservableList.clear();
    	if(!itemList_.isEmpty()) {
    		for (MonthlyReport item : itemList_) { 		      
	    		itemObservableList.add(item);
    		}
    	}
    }
    
    public void initialize() {
    	Platform.runLater(() -> {
    		lvReports.setItems(itemObservableList);
    		// lvReports.setCellFactory(itemListView  -> new ComplaintListViewCell(true));

        });
    	
    }
}
