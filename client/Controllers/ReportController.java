package client.Controllers;


import java.util.ArrayList;

import common.Complaint;
import common.MonthlyReport;
import common.UserRequest;
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
    
    static public void setReport(ArrayList<MonthlyReport> itemList_) {
    	itemObservableList.clear();
    	if(!itemList_.isEmpty()) {
    		for (MonthlyReport item : itemList_) { 		      
	    		itemObservableList.add(item);
    		}
    	}
    	System.out.println("\n\n\ncheck whats inside!!"+itemObservableList.get(0).get_order_content()+"\n\n\n\\n");
    }
    
    public void updateReport() {
    	lvReports.setItems(itemObservableList);
    	lvReports.setCellFactory(itemListView  -> new ReportListViewCell());
    	System.out.println("lvReports: " + lvReports);
    }
    @FXML
    public void initialize() {
    	Platform.runLater(() -> {
    		lvReports.setItems(itemObservableList);
    		lvReports.setCellFactory(itemListView  -> new ReportListViewCell());

        });	
    }
    
    public void fetchReports() { 
    	System.out.println("fetching reports");
		ArrayList<Object> args =  new ArrayList<Object>();
		args.add(mainController.getClient().client.getLoggedUser());
    	UserRequest user_request = new UserRequest("#getreports",  args);
    	
		Platform.runLater(new Runnable() {
    	    @Override
    	    public void run() {
    	    	mainController.getClient().client.flagServerAns=false;
    	    	mainController.getClient().client.handleMessageFromClientUI(user_request);
    			while(!mainController.getClient().client.flagServerAns) {
    				try {
    					Thread.sleep(100);
    					System.out.println("waiitng for server");
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				};
    			}
    			System.out.println("got Reports");
    			System.out.println("reports are "+ mainController.getClient().monthly_reports.getItemList());
    			setReport(mainController.getClient().monthly_reports.getItemList());
    			updateReport();
    			System.out.println("finished displaying reports");
    			System.out.println(itemObservableList);
    			mainController.getClient().client.flagServerAns = false;
    	    }
    	});
    	
    }
}
