package client_gui;

import java.net.URL;
import java.util.ResourceBundle;
import Utils.generalMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import clientUtil.*;
import enums.*;

public class CeoDashboardController implements Initializable {

	@FXML
	private Button getSubscribersBtn;
	@FXML
	private Button LogoutBtn;
	@FXML
	private Button viewReportsBtn;
	@FXML
	private Button showReportBtn;
	@FXML
	private Button aprroveCustomersBtn;

	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/CeoDashboard.fxml",
				"Ekurt Manager's Menu");
	}

	@FXML
	void viewReports(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		ManagerReportsFxmlController getPage = new ManagerReportsFxmlController();
		getPage.start(primaryStage);
	}

	@FXML
	void Get(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		GetSubscriberController getPage = new GetSubscriberController();
		getPage.start(primaryStage);
	}

	@FXML
	void aprroveCustomers(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		CustomerApprovingController page = new CustomerApprovingController();
		page.start(primaryStage);
	}

	@FXML
	void logout(ActionEvent event) throws Exception {
		LoginController.logout(ClientUtils.currUser.getUsername());
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		LoginFXController getPage = new LoginFXController();
		getPage.start(primaryStage);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (ClientUtils.currUser.getRegion() != RegionEnum.WORLDWIDE) {
			getSubscribersBtn.setVisible(false);
		}
	}

}