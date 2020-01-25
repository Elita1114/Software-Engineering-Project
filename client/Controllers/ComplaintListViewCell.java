package client.Controllers;


import java.io.IOException;
import java.util.ArrayList;

import common.CatalogItem;
import common.Complaint;
import common.Item;
import common.Status;
import common.User;
import common.UserRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ComplaintListViewCell extends ListCell<Complaint>{
	@FXML private Label tvTitle;
	@FXML private Label tvUserID;
	@FXML private Text tvHandled;
	@FXML private Text tvUrgent;
	@FXML private Label tvUserIDtitle;
	@FXML private AnchorPane pane;
	
	private boolean flag;
	public Complaint complaint;
	private FXMLLoader mLLoader;
	
	public ComplaintListViewCell(boolean flag_) {
		flag=flag_;
	}
	@Override
    protected void updateItem(Complaint item, boolean empty) {
        super.updateItem(item, empty);
        complaint=item;
        if(empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/client/fxml/ComplaintListViewCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            tvTitle.setText(String.valueOf(item.title));
            tvUserID.setText(String.valueOf(item.userID));
            tvUserIDtitle.setText("User ID: " );
            if(flag) {
            	int hours = item.timer/60;
            	int minutes=item.timer%60;
            	tvUserIDtitle.setText("Time Left: " );
            	tvUserID.setText(hours+" hours and "+minutes+" minutes.");
            }
            
            tvUrgent.setVisible(false);
            if(item.status)
            	tvHandled.setVisible(true);
            else {
            	tvHandled.setVisible(false);
            	if(item.timer<=60)
            		tvUrgent.setVisible(true);
            }

            setText(null);
            setGraphic(pane);
        }

    }
	
}
