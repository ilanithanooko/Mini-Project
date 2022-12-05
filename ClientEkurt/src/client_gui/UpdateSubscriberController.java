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

public class UpdateSubscriberController implements Initializable{

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
	private String chosenIdToUpdate;
	
	
	public void start(Stage primaryStage) throws Exception {
		Pane root = FXMLLoader.load(getClass().getResource("/client_gui/UpdateSubscriber.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Ekurt Subscriber");
		primaryStage.setScene(scene);
		subIdTxt.setText(chosenIdToUpdate);
		System.out.println(chosenIdToUpdate);
		primaryStage.show();
		primaryStage.setResizable(false);
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		subIdTxt.setText(chosenIdToUpdate);
	}

	public String getChosenIdToUpdate() {
		return chosenIdToUpdate;
	}

	public void setChosenIdToUpdate(String chosenIdToUpdate) {
		this.chosenIdToUpdate = chosenIdToUpdate;
	}

}
