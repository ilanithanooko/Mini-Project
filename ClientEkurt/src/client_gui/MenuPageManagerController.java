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

public class MenuPageManagerController {

	@FXML
	private Button getSubscribersBtn;
	@FXML
	private Button exitBtn;
	private generalMethods gm = new generalMethods();

	public void start(Stage primaryStage) throws Exception {
		gm.displayScreen(primaryStage, getClass(), "/client_fxml/CeoDashboard.fxml", "Ekurt Manager's Menu");
	}

	@FXML
	void Get(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		GetSubscriberController getPage = new GetSubscriberController();
		getPage.start(primaryStage);
	}
	
	@FXML
	void Exit(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		System.exit(0);
	}
	
}