package client_gui;

import Utils.generalMethods;
import clientUtil.ClientUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OrderConfirmationDeliveryController {

    @FXML
    private Button okBtn;
    
	public void start (Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/OrderConfirmationDelivery.fxml", "Order Confirmation");
	}
    
    @FXML
    void clickOnOk(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		switch (ClientUtils.currUser.getRole()) {
		case CUSTOMER: {
			new MainDashboradController().start(new Stage());
			break;
		}

		case SUBSCRIBER: {
			new MainDashboradController().start(new Stage());
			break;
		}
		case CEO: {
			new CeoDashboardController().start(new Stage());
			break;
		}
		}
    }

}
