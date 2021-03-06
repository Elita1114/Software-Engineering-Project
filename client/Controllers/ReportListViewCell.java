package client.Controllers;

import java.io.IOException;

import common.ChainManager;
import common.Complaint;
import common.Item;
import common.MonthlyReport;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class ReportListViewCell extends ListCell<MonthlyReport>{
	  	@FXML private Button bttnIncome;
	    @FXML private Button bttnComplaints;
	    @FXML private Button bttnOrders;
	    @FXML private Button bttnCompare;
	    @FXML private Label tvTitle;
	    @FXML private Label tvStore;
	    @FXML private AnchorPane pane;
	    public MonthlyReport monthly_report;
		private FXMLLoader mLLoader;
		ReportController report_controller;

	    @FXML
	    void incomeBttnPressed(ActionEvent event) {
	    	report_controller.curr_display = 1;
	    	report_controller.complaintBarChart.setVisible(false);
	    	report_controller.tvContent.setVisible(true);
	    	report_controller.tvDescription.setText("Generated report for store " + monthly_report.getStore() + " on " + monthly_report.getDate());
	    	report_controller.tvContent.setText("Total income: " + monthly_report.getIncome_content());
	    }

	    @FXML
	    void complaintsBttnPressed(ActionEvent event) {
	    	report_controller.curr_display = 2;
	     	report_controller.complaintBarChart.setVisible(true);
	    	report_controller.tvContent.setVisible(false);
	    	report_controller.tvDescription.setText("Generated report for store " + monthly_report.getStore() + " on " + monthly_report.getDate());
	    	XYChart.Series<String, Number>  set = new XYChart.Series<>();
	    	set.getData().add(new XYChart.Data<>("Handled Complaints", monthly_report.getHandledcomplaint()));
	    	set.getData().add(new XYChart.Data<>("Unhandled Complaints", monthly_report.getUnhandledcomplaint()));
	    	report_controller.xAxis.setCategories(FXCollections.observableArrayList("Handled Complaints", "Unhandled Complaints"));
	    	report_controller.complaintBarChart.getData().setAll(set);
	    }

	    @FXML
	    void ordersBttnPressed(ActionEvent event) {
	    	report_controller.curr_display = 3;
	    	report_controller.complaintBarChart.setVisible(false);
	    	report_controller.tvContent.setVisible(true);
	    	report_controller.tvDescription.setText("Generated report for store " + monthly_report.getStore() + " on " + monthly_report.getDate());
	    	report_controller.tvContent.setText(monthly_report.get_order_content());
	    }
	    
	    @FXML
	    void compareBttnPressed(ActionEvent event) {
	    	if (report_controller.curr_display == -1) 
	    		return;
	    	report_controller.bttnExitCompare.setVisible(true);;
	    	report_controller.tlDescriptCompare.setVisible(true);
	    	report_controller.tlContentCompare.setVisible(true);
	    	report_controller.tvDescriptionCompare.setVisible(true);
	    	report_controller.tvDescriptionCompare.setText("Generated report for store " + monthly_report.getStore() + " on " + monthly_report.getDate());
	    	if (report_controller.curr_display == 1) // income 
	    	{
	    		report_controller.complaintBarChartCompare.setVisible(false);
		    	report_controller.tvContentCompare.setVisible(true);
		    	report_controller.tvContentCompare.setText(monthly_report.get_order_content());

	    	}
	    	else if(report_controller.curr_display == 2) // complaint
	    	{
	    		report_controller.complaintBarChartCompare.setVisible(true);
		    	report_controller.tvContentCompare.setVisible(false);
		    	XYChart.Series<String, Number>  set = new XYChart.Series<>();
		    	set.getData().add(new XYChart.Data<>("Handled Complaints", monthly_report.getHandledcomplaint()));
		    	set.getData().add(new XYChart.Data<>("Unhandled Complaints", monthly_report.getUnhandledcomplaint()));
		    	report_controller.xAxis.setCategories(FXCollections.observableArrayList("Handled Complaints", "Unhandled Complaints"));
		    	report_controller.complaintBarChartCompare.getData().setAll(set);
	    	}
	    	else if(report_controller.curr_display == 3) // orders
	    	{
	    		report_controller.complaintBarChartCompare.setVisible(false);
		    	report_controller.tvContentCompare.setVisible(true);
		    	report_controller.tvContentCompare.setText(monthly_report.get_order_content());
	    	}
	    }
	    
	    public ReportListViewCell(ReportController report_controller_)
	    {
	    	report_controller = report_controller_;
	    }
	    
	    @FXML
	    public void initialize() {
	    	if(!(MainController.getLoggedUser() instanceof ChainManager))
	    		bttnCompare.setDisable(true);
	    }
	    
	    @Override
	    protected void updateItem(MonthlyReport item, boolean empty) {
	        super.updateItem(item, empty);
	        monthly_report = item;
	        if(empty || item == null) {
	            setText(null);
	            setGraphic(null);
	        } 
	        else {
	            if (mLLoader == null) {
	                mLLoader = new FXMLLoader(getClass().getResource("/client/fxml/ReportListViewCell.fxml"));
	                mLLoader.setController(this);
	                try {
	                    mLLoader.load();
	                } 
					catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            System.out.println("updating item ");
	            tvTitle.setText("reported for: " + item.getDate());
	            tvStore.setText("Store " + String.valueOf(item.getStore()));
	            System.out.println(tvTitle.getText());
	            System.out.println(tvStore.getText());
	            setText(null);
	            setGraphic(pane);
	        }

	    }
}
