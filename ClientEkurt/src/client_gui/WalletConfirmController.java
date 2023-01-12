package client_gui;

import java.net.URL;
import java.util.ResourceBundle;

import Utils.generalMethods;
import client.ClientUI;
import clientUtil.ClientUtils;
import common.Action;
import common.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WalletConfirmController implements Initializable{

    @FXML
    private Button backBtn;

    @FXML
    private Button placeOrderBtn;

    @FXML
    private Label totalPrice = new Label();
    
	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/WalletConfirmPage.fxml",
				"Add to wallet");
	}
    
    @FXML
    void clickOnBack(ActionEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

	@FXML
	void clickOnPlaceOrder(ActionEvent event) {
		if (ClientUtils.localOrderInProcess != null) { // if local
			Transaction msg = new Transaction(Action.PLACE_LOCAL_ORDER, ClientUtils.localOrderInProcess);
			ClientUI.chat.accept(msg);
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage.getWindows().get(0).hide();
			new OrderConfirmationLocalController().start(new Stage());
		}
		else if (ClientUtils.pickupOrderInProcess != null) {
			Transaction msg = new Transaction(Action.PLACE_PICKUP_ORDER, ClientUtils.pickupOrderInProcess);
			ClientUI.chat.accept(msg);
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage.getWindows().get(0).hide();
			new OrderConfirmationPickUpController().start(new Stage());
		} else {
			Transaction msg = new Transaction(Action.PLACE_DELIVERY_ORDER, ClientUtils.deliveryOrderInProcess);
			ClientUI.chat.accept(msg);
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage.getWindows().get(0).hide();
			new OrderConfirmationDeliveryController().start(new Stage());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(ClientUtils.localOrderInProcess != null) {
			totalPrice.setText(String.valueOf(ClientUtils.localOrderInProcess.getTotalToPay())+" ₪");
		}else if(ClientUtils.pickupOrderInProcess != null) {
			totalPrice.setText(String.valueOf(ClientUtils.pickupOrderInProcess.getTotalToPay())+" ₪");
		}else {
			totalPrice.setText(String.valueOf(ClientUtils.deliveryOrderInProcess.getTotalToPay())+" ₪");

		}
	}

}
