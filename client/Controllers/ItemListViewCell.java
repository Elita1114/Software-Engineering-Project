package client.Controllers;


import java.io.IOException;

import common.CatalogItem;
import common.Item;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

public class ItemListViewCell extends ListCell<CatalogItem>{
	@FXML private Label Title;
	@FXML private Label Description;
	@FXML private Label Color;
	
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
                mLLoader = new FXMLLoader(getClass().getResource("/client/fxml/ItemListViewCell.fxml"));
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
            

            setText(null);
            setGraphic(pane);
        }

    }
	
}
