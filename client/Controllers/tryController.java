package client.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class tryController {
    private MainController mainController;

    @FXML
    private Button btn;
    
    
    public void injectMainController(MainController mainController_) {
		mainController = mainController_;
	}
    @FXML
    void pres3(ActionEvent event) {
    	
    }

}
