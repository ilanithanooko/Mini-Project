package client_gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import Utils.generalMethods;
import client.ClientUI;
import clientUtil.ClientUtils;
import common.Action;
import common.Response;
import common.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SubscriberRegisterController implements Initializable {

	@FXML
	private Button registerBtn;
	@FXML
	private Button backBtn;
	@FXML
	private TextField firstNameTxt;
	@FXML
	private TextField lastNameTxt;
	@FXML
	private TextField idNumberTxt;
	@FXML
	private TextField emailTxt;
	@FXML
	private TextField phoneTxt;
	@FXML
	private TextField creditCardTxt;
	@FXML
	private TextField expMonthTxt;
	@FXML
	private TextField expYearTxt;
	@FXML
	private TextField cvvTxt;
	@FXML
	private Label errorLabel;

	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/SubscriberRegisterPage.fxml",
				"Ekurt To Be Subscriber");
	}

	@FXML
	void Back(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding window
		Stage primaryStage = new Stage();
		UserDashboardController page = new UserDashboardController(); // customerDashboardcontroller
		page.start(primaryStage);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		firstNameTxt.setPromptText(ClientUtils.currUser.getFirstName());
		lastNameTxt.setPromptText(ClientUtils.currUser.getLastName());
		idNumberTxt.setPromptText(String.valueOf(ClientUtils.currUser.getId()));
		emailTxt.setPromptText(ClientUtils.currUser.getEmail());
		phoneTxt.setPromptText(ClientUtils.currUser.getTelephone());
		errorLabel.setVisible(false);
		Transaction transaction = new Transaction(Action.GET_CREDIT_CARD_BY_ID, null,
				String.valueOf(ClientUtils.currUser.getId()));
		ClientUI.chat.accept(transaction);
		transaction = ClientUI.chat.getObj();
		@SuppressWarnings("unchecked")
		List<String> paymentDetails = (List<String>) transaction.getData();
		creditCardTxt.setPromptText(paymentDetails.get(0));
		cvvTxt.setPromptText(paymentDetails.get(1));
		String date = paymentDetails.get(2);
		String[] splitDate = date.split("/");
		String month = splitDate[0];
		String year = splitDate[1];
		expMonthTxt.setPromptText(month);
		expYearTxt.setPromptText(year);

	}

	@FXML
	void requestToSubscriber(ActionEvent event) throws Exception {
		Transaction transaction = new Transaction(Action.REGISTER_CUSTOMER_TO_SUBSCRIBER, null,
				String.valueOf(ClientUtils.currUser.getId()));
		ClientUI.chat.accept(transaction);
		transaction = ClientUI.chat.getObj();
		if (transaction.getResponse() == Response.REGISTER_CUSTOMER_TO_SUBSCRIBER_UNSUCCESSFULLY) {
			errorLabel.setVisible(true);
			errorLabel.setTextFill(Color.RED);
			errorLabel.setText("Request is faild.");
		} else {
			errorLabel.setVisible(true);
			errorLabel.setTextFill(Color.GREEN);
			errorLabel.setText("The request was sent.\nThe service worker need \nto aprrove your request.");
			((Node) event.getSource()).getScene().getWindow().hide();
			RequestConfirmPageController page = new RequestConfirmPageController();
			Stage primaryStage = new Stage();
			page.start(primaryStage);
		}
	}
}
