package client.Controllers;


import java.io.IOException;

import common.CatalogItem;
import common.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class updateCatalog_ItemListViewCell extends ListCell<CatalogItem>{
	@FXML private Label Title;
	@FXML private Label Description;
	@FXML private Label Color;
	@FXML private Label Price;
	@FXML private ImageView ivIm1;
	
	@FXML private AnchorPane pane;
	
	private FXMLLoader mLLoader;
	
	
	@Override
    protected void updateItem(CatalogItem item, boolean empty) {
        super.updateItem(item, empty);

        if(empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/client/fxml/UpdateCatalog_ListViewCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            Title.setText(String.valueOf(item.getName()));
            Description.setText(String.valueOf(item.getDescription()));
            Color.setText(String.valueOf(item.getColor()));
            Price.setText(String.valueOf(item.getPrice()));
            ivIm1.setImage(new Image(item.getImagePath()));

            setText(null);
            setGraphic(pane);
        }

    }
	
}
