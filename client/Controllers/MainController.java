package client.Controllers;

import java.util.ArrayList;

import client.ClientConsole;
import common.CatalogItem;
import common.User;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

public class MainController {
	
	
	@FXML private CatalogController catalogController;
	@FXML private SignUpController signUpController;
	@FXML private LoginController loginController;
	@FXML private ComplaintController complaintController;
	@FXML private UpdateCatalogController updatecatalogController;//update catalog controller
	@FXML public TabPane tabPane;

	static public ClientConsole client;
	



	
	@FXML
	private void initialize() {
		
		catalogController.injectMainController(this);
		signUpController.injectMainController(this);
		loginController.injectMainController(this);
		complaintController.injectMainController(this);
		updatecatalogController.injectMainController(this);
	}
	public static ClientConsole getClient() {
		return client;
	}
	public void setFlag(boolean flag_) {
		client.flagCatalog=flag_;
	}
	public void setCatalog(ArrayList<CatalogItem> list) {
		catalogController.setCatalog(list);
	}
	public void setUpdateCatalog(ArrayList<CatalogItem> list) {
		updatecatalogController.setCatalog(list);
	}
	public void setClient(ClientConsole client_) {
		client=client_;
	}
	
/*
	
	@FXML private ConsoleTabController consoleTabController;
	
	@FXML private LoggerTabController loggerTabController;

	public TextArea getVisualLog() {
		return loggerTabController.getLoggerTxtArea();
	}

	@FXML
	private void initialize() {
		consoleTabController.injectMainController(this);
	}
	*/
}