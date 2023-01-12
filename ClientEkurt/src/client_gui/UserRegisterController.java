package client_gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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

public class UserRegisterController implements Initializable {

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
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/UserRegisterPage.fxml",
				"Ekurt Register User");
	}

	@FXML
	void Back(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding window
		Stage primaryStage = new Stage();
		UserDashboardController page = new UserDashboardController();
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
	}

	@FXML
	void registerUserToCustomer(ActionEvent event) throws Exception {

		List<String> inputQuery = new ArrayList<>();
		String date = expMonthTxt.getText() + "/" + expYearTxt.getText();
		inputQuery.addAll(Arrays.asList(String.valueOf(ClientUtils.currUser.getId()), creditCardTxt.getText(),
				cvvTxt.getText(), date));
		Transaction t = new Transaction(Action.REGISTER_USER_TO_CUSTOMER, null, inputQuery);
		ClientUI.chat.accept(t);
		t = ClientUI.chat.getObj();
		if (t.getResponse() == Response.REGISTER_USER_TO_CUSTOMER_UNSUCCESSFULLY) {
			errorLabel.setVisible(true);
			errorLabel.setTextFill(Color.RED);
			errorLabel.setText("Register user to customer is faild.");
		} else {
			errorLabel.setVisible(true);
			errorLabel.setTextFill(Color.GREEN);
			errorLabel.setText("The request was sent.\nThe region manager need to\naprrove your request.");
			((Node) event.getSource()).getScene().getWindow().hide();
			RequestConfirmPageController page = new RequestConfirmPageController();
			Stage primaryStage = new Stage();
			page.start(primaryStage);
		}
	}
}
