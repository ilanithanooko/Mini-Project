package client_gui;

import Utils.generalMethods;

import client.ClientController;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnectToServerController {
	private String ip, port;
	@FXML
	private Label ConnectStatusLabel;

	@FXML
	private TextField IpTxt;
	@FXML
	private Button ConfirmDetails;

	@FXML
	private TextField PortTxt;

	@FXML
	void ConfirmClick(ActionEvent event) throws Exception {
		ip = IpTxt.getText();
		port = PortTxt.getText();
		ClientUI.chat = new ClientController(ip, Integer.parseInt(port));
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding window
		Stage primaryStage = new Stage();
		LoginFXController loginPage = new LoginFXController();
		loginPage.start(primaryStage);

	}

	public void start(Stage primaryStage) throws Exception {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/ConnectToServer.fxml",
				"Ekrut Connect To Server");
	}
}
