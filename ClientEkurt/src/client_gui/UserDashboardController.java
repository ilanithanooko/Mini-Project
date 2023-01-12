package client_gui;

import Utils.generalMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import clientUtil.*;

public class UserDashboardController {

	@FXML
	private Button registerBtn;
	@FXML
	private Button LogoutBtn;
	@FXML
	private Button registerSubscriberBtn;

	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/UserDashboard.fxml", "Ekurt User's Menu");
	}

	@FXML
	void goToRegister(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		UserRegisterController Page = new UserRegisterController();
		Page.start(primaryStage);
	}

	@FXML
	void registerSubscriber(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		SubscriberRegisterController Page = new SubscriberRegisterController();
		Page.start(primaryStage);
	}

	@FXML
	void logout(ActionEvent event) throws Exception {
		LoginController.logout(ClientUtils.currUser.getUsername());
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		new LoginController().start(new Stage());

	}

}