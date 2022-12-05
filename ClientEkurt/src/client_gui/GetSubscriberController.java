package client_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import client.ClientUI;
import common.Action;
import common.Response;
import common.Transaction;
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
	private Button updateSubscribersBtn;
	@FXML
	private final TableView<Subscriber> table = new TableView<Subscriber>();
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
	@FXML
	private final ObservableList<Subscriber> listView = FXCollections.observableArrayList();
	private String[] list;

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/client_gui/GetSubscriber.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Ekurt Subscriber");
		primaryStage.setScene(scene);
		//ActionEvent event = new ActionEvent();
		//this.GetSubscribers(event);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		subNumColTbl.setCellValueFactory(new PropertyValueFactory<>("Subscriber Number"));
		subIdColTbl.setCellValueFactory(new PropertyValueFactory<>("ID"));
		subNameColTbl.setCellValueFactory(new PropertyValueFactory<>("First Name"));
		subLastNameColTbl.setCellValueFactory(new PropertyValueFactory<>("Last Name"));
		subPhoneColTbl.setCellValueFactory(new PropertyValueFactory<>("Phone"));
		subEmailColTbl.setCellValueFactory(new PropertyValueFactory<>("Email"));
		subCreditColTbl.setCellValueFactory(new PropertyValueFactory<>("Creditcard"));
		//table.setItems(listView);
		
	}

	@FXML
	void Back(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding window
		Stage primaryStage = new Stage();
		MenuPageController menuPage = new MenuPageController();
		menuPage.start(primaryStage);
	}
	@FXML
	void UpdateSub(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding window
		Stage Stage = new Stage();
		UpdateSubscriberController updatePage = new UpdateSubscriberController();
		updatePage.start(Stage);
	}

	@SuppressWarnings("unchecked")
	@FXML
	void GetSubscribers(ActionEvent event) {

		Transaction t = new Transaction(Action.GET_SUBSCRIBER, null, null);
		ClientUI.chat.accept(t);
		t = ClientUI.chat.getObj();
		table.setEditable(true);
		List<String> temp = new ArrayList();
		if (t.getResponse() == Response.FOUND_SUBSCRIBERS) {
			listView.clear();
			temp = (List<String>) t.getData();
			System.out.println(temp);
		for (int i = 0; i <temp.size(); i++) {
			list = (temp.get(i).split("\\s+"));
				listView.add(new Subscriber (list[0],list[1], list[2],list[3],list[4],list[5],list[6]));
			}
			table.setItems(listView);
		}
	}
}
