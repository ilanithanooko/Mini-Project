package client_gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import Utils.Constants;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.*;

public class SubscriberApprovingController implements Initializable {

	@FXML
	private Button backBtn;
	@FXML
	private Button approveRequestBtn;
	@FXML
	private TableView<User> requestSubscriberToRegisterTbl = new TableView<User>();
	@FXML
	private TableColumn<User, String> idCol;
	@FXML
	private TableColumn<User, String> nameCol;
	@FXML
	private TableColumn<User, String> lastNameCol;
	@FXML
	private TableColumn<User, String> phoneCol;
	@FXML
	private TableColumn<User, String> emailCol;
	@FXML
	private TableColumn<User, String> regionCol;
	@FXML
	private Label errorLabel;
	@FXML
	private ObservableList<User> listView = FXCollections.observableArrayList();

	public void start(Stage primaryStage) throws Exception {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/SubscriberApprovingPage.fxml",
				"Ekrut Subscriber Approving Page");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		idCol.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
		nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
		phoneCol.setCellValueFactory(new PropertyValueFactory<User, String>("telephone"));
		emailCol.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		regionCol.setCellValueFactory(new PropertyValueFactory<User, String>("region"));
		errorLabel.setVisible(false);
		displayTable();
	}

	@FXML
	void Back(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding window
		Stage primaryStage = new Stage();
		ServiceWorkerDashboardController menuPage = new ServiceWorkerDashboardController();
		menuPage.start(primaryStage);
	}

	@FXML
	void approveRequest(ActionEvent event) throws Exception {
		int userId = requestSubscriberToRegisterTbl.getSelectionModel().getSelectedItem().getId();
		Transaction transaction = new Transaction(Action.APPROVE_SUBSCRIBER_REQUEST, null, userId);
		ClientUI.chat.accept(transaction);
		transaction = ClientUI.chat.getObj();
		if (transaction.getResponse() == Response.APPROVE_SUBSCRIBER_REQUEST_SUCCESSFULLY) {
			listView.clear();
			errorLabel.setStyle(Constants.TEXT_FIELD_VALID_STYLE);
			errorLabel.setTextFill(Color.GREEN);
			errorLabel.setText("The User Approved.");
			errorLabel.setVisible(true);
			displayTable();
		}
	}

	void displayTable() {
		Transaction transaction = new Transaction(Action.GET_SUBSCRIBER_REQUESTS_TO_SERVICE_WORKER, null, null);
		ClientUI.chat.accept(transaction);
		transaction = ClientUI.chat.getObj();
		requestSubscriberToRegisterTbl.setEditable(true);
		@SuppressWarnings("unchecked")
		List<User> temp = (List<User>) transaction.getData();
		if (transaction.getResponse() == Response.GET_SUBSCRIBER_REQUESTS_TO_SERVICE_WORKER_SUCCESSFULLY) {
			for (int i = 0; i < temp.size(); i++) {
				listView.add(temp.get(i));
			}
			requestSubscriberToRegisterTbl.setItems(listView);
		}
	}
}
