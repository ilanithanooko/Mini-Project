package client_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import client.ClientUI;
import common.Mission;
import common.Response;
import common.TransmissionPack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.Subscriber;

public class GetSubscriberController implements Initializable {
	
	@FXML
	private Button backBtn;
	@FXML
	private Button getSubscribersBtn;
	@FXML
	private Label statusLbl;
	@FXML
	private TableView<Subscriber> table;
	@FXML
	private TableColumn<Subscriber, String> subNumColTbl;
	@FXML
	private TableColumn<Subscriber, String> subIdColTbl;
	@FXML
	private TableColumn<Subscriber, String> subNameColTbl;
	@FXML
	private TableColumn<Subscriber, String> subLastNameColTbl;
	@FXML
	private TableColumn<Subscriber, String> subPhoneColTbl;
	@FXML
	private TableColumn<Subscriber, String> subEmailColTbl;
	@FXML
	private TableColumn<Subscriber, String> subCreditColTbl;

	ObservableList<Subscriber> listView = FXCollections.observableArrayList();
	String[] list;

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/client_gui/GetSubscriber.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Ekurt Subscriber");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		subNumColTbl.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("subscriberNumber"));
		subIdColTbl.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("subscriberID"));
		subNameColTbl.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("subscriberName"));
		subLastNameColTbl.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("subscriberLastName"));
		subPhoneColTbl.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("subscriberPhone"));
		subEmailColTbl.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("subscriberEmail"));
		subCreditColTbl.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("subscriberCreditCard"));
	}

	@FXML
	void Back(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding window
		Stage primaryStage = new Stage();
		MenuPageController menuPage = new MenuPageController();
		menuPage.start(primaryStage);
	}
	
	void Update(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding window
		Stage primaryStage = new Stage();
		UpdateSubscriberController updatePage = new UpdateSubscriberController();
		updatePage.start(primaryStage);
	}

	
	@FXML
	void GetSubscribers(ActionEvent event) {

		TransmissionPack tp = new TransmissionPack(Mission.GETORDERS, null, null);
		ClientUI.chat.accept(tp);
		tp = ClientUI.chat.getObj();

		if (tp.getResponse() == Response.FOUND_ORDERS) {
			listView.clear();
			List<String> temp = new ArrayList();
			temp = (List<String>) tp.getInformation();
			for (int i = 0; i < temp.size(); i++) {
				list = (temp.get(i).split("\\s+"));
				listView.add(
						new Subscriber(list[0], list[1], list[2], list[3], list[4], list[5], list[6]));
			}
			table.setItems(listView);
			statusLbl.setTextFill(Color.GREEN);
			statusLbl.setText("Upload Success");
		}

		else {
			statusLbl.setTextFill(Color.RED);
			statusLbl.setText("Upload Failed");
		}

	}
}
