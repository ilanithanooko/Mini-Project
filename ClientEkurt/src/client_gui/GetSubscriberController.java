package client_gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import Utils.generalMethods;
import client.ClientUI;
import common.Action;
import common.Response;
import common.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private TableView<Subscriber> table = new TableView<Subscriber>();
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
	private Label warningLbl;
	@FXML
	private ObservableList<Subscriber> listView = FXCollections.observableArrayList();
	private String[] list;
	private int getClicked = 0;

	public void start(Stage primaryStage) throws Exception {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/GetSubscriber.fxml",
				"Ekrut Get Subscriber");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		subNumColTbl.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("subNumber"));
		subIdColTbl.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("subId"));
		subNameColTbl.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("subFirstname"));
		subLastNameColTbl.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("subLastname"));
		subPhoneColTbl.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("subPhone"));
		subEmailColTbl.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("subEmail"));
		subCreditColTbl.setCellValueFactory(new PropertyValueFactory<Subscriber, String>("subCreditcard"));

	}

	@FXML
	void Back(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding window
		Stage primaryStage = new Stage();
		CeoDashboardController menuPage = new CeoDashboardController();
		menuPage.start(primaryStage);
	}

	@FXML
	void UpdateSub(ActionEvent event) throws Exception {
		// ((Node)event.getSource()).getScene().getWindow().hide(); //hiding window
		if (getClicked == 1) {
			Stage Stage = new Stage();
			String userId = table.getSelectionModel().getSelectedItem().getSubId();
			// Subscriber sub = table.getSelectionModel().getSelectedItem();
			UpdateSubscriberController updatePage = new UpdateSubscriberController();
			updatePage.start(Stage, userId);
		} else {
			warningLbl.setText("Click On Get Please");
		}
	}

	@SuppressWarnings("unchecked")
	@FXML
	void GetSubscribers(ActionEvent event) {
		getClicked = 1;
		Transaction t = new Transaction(Action.GET_SUBSCRIBER, null, null);
		ClientUI.chat.accept(t);
		t = ClientUI.chat.getObj();
		table.setEditable(true);
		List<String> temp = (List<String>) t.getData();
		if (t.getResponse() == Response.FOUND_SUBSCRIBERS) {
			listView.clear();
			for (int i = 0; i < temp.size(); i++) {
				list = (temp.get(i).split("\\s+"));
				listView.add(new Subscriber(list[0], list[1], list[2], list[3], list[4], list[5], list[6]));
			}
			table.setItems(listView);
		}
	}
}
