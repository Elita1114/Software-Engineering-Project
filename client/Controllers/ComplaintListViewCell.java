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
	
	@FXML private AnchorPane pane;
	
	public Complaint complaint;
	private FXMLLoader mLLoader;
	
	
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
            if(item.status)
            	tvHandled.setVisible(true);
            else
            	tvHandled.setVisible(false);

            setText(null);
            setGraphic(pane);
        }

    }
	
}
