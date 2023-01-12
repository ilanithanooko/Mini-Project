package client_gui;

import java.io.IOException;
import Utils.generalMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import clientUtil.ClientUtils;

public class StorageWorkerDashboardController {

	@FXML
	private Button UpdateStockBtn;
	@FXML
	private Button LogoutBtn;
	//private generalMethods gm = new generalMethods();

	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/StorageWorkerDashboard.fxml", "Storage worker window");
	}

	@FXML
	void UpdateStock(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		UpdateStockFXController getPage = new UpdateStockFXController();
		getPage.start(primaryStage);
		}
	
	@FXML
	void logout(ActionEvent event) throws Exception {
		LoginController.logout(ClientUtils.currUser.getUsername());
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		LoginFXController getPage = new LoginFXController();
		getPage.start(primaryStage);
	}
	
}