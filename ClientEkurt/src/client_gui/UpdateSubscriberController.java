package client_gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import client.ClientUI;
import common.Mission;
import common.Response;
import common.TransmissionPack;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UpdateSubscriberController {
	
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/client_gui/UpdateSubscriber.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Ekurt Subscriber");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
	}
	
	@FXML
	void UpdateSubscribers(ActionEvent event) {
		List<String> order = new ArrayList<>();
		order.addAll(Arrays.asList(lblEditColor.getText(), lblEditDate.getText(), lblEditOrderNumber.getText()));
		TransmissionPack tp = new TransmissionPack(Mission.EDITORDER, null, order);
		ClientUI.chat.accept(tp);
		tp = ClientUI.chat.getObj();
		if (tp.getResponse() == Response.EDIT_ORDER_FAILD) {
			statusLbl.setTextFill(Color.RED);
			statusLbl.setText("Edit Failed");
		} else {
			statusLbl.setTextFill(Color.GREEN);
			statusLbl.setText("Edit Success");
		}

	}

}
