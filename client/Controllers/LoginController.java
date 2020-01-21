/**
 * Sample Skeleton for 'LoginScene.fxml' Controller Class
 */

package client.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SplitMenuButton;


public class LoginController {
    private MainController mainController;

    public void injectMainController(MainController mainController_) {
		mainController = mainController_;
	}
}
