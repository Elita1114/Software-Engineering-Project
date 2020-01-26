package client.Controllers;


import java.util.ArrayList;

import common.Complaint;
import common.MonthlyReport;
import common.UserRequest;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ReportController {

	private MainController mainController;
    @FXML private ListView<MonthlyReport> lvReports;
    static public ObservableList<MonthlyReport> itemObservableList;
    @FXML
    public TextField tvDescription;

    @FXML
    public TextArea tvContent;
    
    public NumberAxis yAxis;
    public CategoryAxis xAxis;
    
    @FXML
    public BarChart<String, Number> complaintBarChart;
    
    public ReportController() {
    	itemObservableList = FXCollections.observableArrayList();
    	if(complaintBarChart == null)
    	{
    			xAxis = new CategoryAxis();
    			yAxis = new NumberAxis();
    			xAxis.setCategories(FXCollections.observableArrayList("Handled Complaints", "Unhandled Complaints"));
    	      //Creating the Bar chart
    			BarChart<String, Number> complaintBarChart = new BarChart<>(xAxis, yAxis);
    	
    	}
    	// complaintBarChart.setVisible(false);
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
    	System.out.println("bla bla bla " + itemObservableList);
    	lvReports.setItems(itemObservableList);
    	lvReports.setCellFactory(itemListView  -> new ReportListViewCell(this));
    	System.out.println("lvReports: " + lvReports);
    }
    @FXML
    public void initialize() {
    	complaintBarChart.setVisible(false);
    	Platform.runLater(() -> {
    		lvReports.setItems(itemObservableList);
    		lvReports.setCellFactory(itemListView  -> new ReportListViewCell(this));

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
