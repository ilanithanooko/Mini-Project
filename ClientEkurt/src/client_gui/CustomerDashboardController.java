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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import clientUtil.ClientUtils;

public class CustomerDashboardController {
	@FXML
	private Label welcomeLabel = new Label("");
	@FXML
	private Button pickUpBtn;
	@FXML
	private Button deliveryBtn;
	@FXML
	private Button manageDeliveryBtn;
	@FXML
	private Button LogoutBtn;
	
	
	
	public void start(Stage primaryStage) {
		welcomeLabel.setVisible(true);
		welcomeLabel.setText("Welcome Back " + ClientUtils.currUser.getFirstName()+ " " + ClientUtils.currUser.getLastName());
		welcomeLabel.setVisible(true);
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/customerDashboard.fxml", "Main menu");
	}
	
	@FXML
	void logout(ActionEvent event) throws Exception {
		LoginController.logout(ClientUtils.currUser.getUsername());
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		LoginFXController getPage = new LoginFXController();
		getPage.start(primaryStage);
	}
	
	public Label getWelcomeLabel() {
		return welcomeLabel;
	}

	public void setWelcomeLabel(Label welcomeLabel) {
		this.welcomeLabel = welcomeLabel;
	}
}