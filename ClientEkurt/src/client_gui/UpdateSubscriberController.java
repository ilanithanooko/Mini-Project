package client_gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import client.ClientUI;
import common.Action;
import common.Response;
import common.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;

import logic.Subscriber;

public class UpdateSubscriberController {
	
		@FXML
		private Button updateBtn;
		@FXML
	   private TextField CreditTxt;
	   @FXML
	   private TextField subNumTxt;
	  @FXML
	  private Label subIdLbl;
	  @FXML
		private Label statusLbl;
	
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
		List<String> subscribers = new ArrayList<>();
		subscribers.addAll(Arrays.asList(subNumTxt.getText(), CreditTxt.getText(),  subIdLbl.getText()));
		Transaction tp = new Transaction(Action.UPDATE_SUBSCRIBER, null, subscribers);
		ClientUI.chat.accept(tp);
		tp = ClientUI.chat.getObj();
		if (tp.getResponse() == Response.UPDATE_SUBSCRIBERS_FAILD) {
			statusLbl.setTextFill(Color.RED);
			statusLbl.setText("Edit Failed");
		} else {
			statusLbl.setTextFill(Color.GREEN);
			statusLbl.setText("Edit Success");
		}

	}

}
