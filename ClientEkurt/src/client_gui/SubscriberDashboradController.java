package client_gui;

import java.io.IOException;

import Utils.generalMethods;
import client.ClientUI;
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
import common.Action;
import common.Transaction;

public class SubscriberDashboradController {
	@FXML
	private Button pickUpBtn;
	@FXML
	private Button deliveryBtn;
	@FXML
	private Button manageDeliveryBtn;
	@FXML
	private Button LogoutBtn;
	
	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/SubscriberDashboard.fxml", "Main menu");
	}
	
	@FXML
	void logout(ActionEvent event) throws Exception {
		LoginController.logout(ClientUtils.currUser.getUsername());
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Stage primaryStage = new Stage();
		LoginFXController getPage = new LoginFXController();
		getPage.start(primaryStage);
	}
	
	@FXML
	void pickUpButton(ActionEvent event) throws Exception {
		Stage primaryStage = new Stage();
		ChooseMachinePickupController chooseMachine = new ChooseMachinePickupController();
		Transaction machineGetter = new Transaction(Action.GET_MACHINES_LIST, null);
        ClientUI.chat.accept(machineGetter); 
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		//System.out.println(ChooseMachinePickupController.getMachineList());
		chooseMachine.start(primaryStage);
	}
	
}