package client.Controllers;

import java.io.IOException;

import common.Complaint;
import common.Item;
import common.MonthlyReport;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class ReportListViewCell extends ListCell<MonthlyReport>{
	  	@FXML private Button bttnIncome;
	    @FXML private Button bttnComplaints;
	    @FXML private Button bttnOrders;
	    @FXML private Label tvTitle;
	    @FXML private Label tvStore;
	    @FXML private AnchorPane pane;
	    public MonthlyReport monthly_report;
		private FXMLLoader mLLoader;

	    
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
	            tvTitle.setText("reported for: " + item.getDate());
	            tvTitle.setText("Store " + String.valueOf(item.getStore()));
	            setText(null);
	            setGraphic(pane);
	        }

	    }
}
