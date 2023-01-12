package client_gui;

import Utils.generalMethods;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import client.ClientController;
import client.ClientUI;
import common.Action;
import common.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
		new LoginController().start(new Stage());

	}

	public void start(Stage primaryStage) throws Exception {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/ConnectToServer.fxml",
				"Ekrut Connect To Server");
	}
}
