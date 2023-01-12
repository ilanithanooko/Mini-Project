package client_gui;

import Utils.generalMethods;
import clientUtil.ClientUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RequestConfirmPageController {

	@FXML
	private Button okBtn;

	@FXML
	private Label orderCode = new Label();

	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/RequestConfirmaionPage.fxml",
				"Request Confirmation");
	}

	@FXML
	void clickOnOkBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		switch (ClientUtils.currUser.getRole()) {
		case USER: {
			new UserDashboardController().start(new Stage());
			break;
		}
		/*
		 * case CUSTOMER: { new MainDashboradController().start(new Stage()); break; }
		 * 
		 * case SUBSCRIBER: { new MainDashboradController().start(new Stage()); break; }
		 */
		default:
			break;
		}
	}
}
