package client_gui;

import Utils.generalMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import clientUtil.*;

public class ServiceWorkerDashboardController {

	@FXML
	private Button approveSubscribersBtn;
	@FXML
	private Button LogoutBtn;

	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/ServiceWorkerDashboard.fxml",
				"Ekurt Manager's Menu");
	}

	@FXML
	void approveSubscribers(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		SubscriberApprovingController getPage = new SubscriberApprovingController();
		getPage.start(primaryStage);
	}

	@FXML
	void logout(ActionEvent event) throws Exception {
		LoginController.logout(ClientUtils.currUser.getUsername());
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		new LoginController().start(new Stage());
	}

}