package client_gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import client.ClientUI;
import common.Action;
import common.Response;
import common.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Utils.generalMethods;
import javafx.scene.control.Label;

public class UpdateSubscriberController {

	@FXML
	private Button updateBtn;
	@FXML
	private TextField CreditTxt;
	@FXML
	private TextField subNumTxt;
	@FXML
	private TextField subIdTxt = new TextField();
	@FXML
	private Label statusLbl;

	public void start(Stage primaryStage, String id) throws Exception {
		subIdTxt.setPromptText(id);
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/UpdateSubscriber.fxml",
				"Ekrut Update Subscriber");

	}

	@FXML
	void UpdateSubscribers(ActionEvent event) throws Exception {
		List<String> subscribers = new ArrayList<>();
		subscribers.addAll(Arrays.asList(subNumTxt.getText(), CreditTxt.getText(), subIdTxt.getText()));
		Transaction t = new Transaction(Action.UPDATE_SUBSCRIBER, null, subscribers);
		ClientUI.chat.accept(t);
		t = ClientUI.chat.getObj();
		if (t.getResponse() == Response.UPDATE_SUBSCRIBERS_FAILD) {
			statusLbl.setTextFill(Color.RED);
			statusLbl.setText("Edit Failed");
		} else {
			statusLbl.setTextFill(Color.GREEN);
			((Node) event.getSource()).getScene().getWindow().hide();

			Stage primaryStage = new Stage();
			GetSubscriberController getPage = new GetSubscriberController();
			getPage.start(primaryStage);
		}

	}
}
