package client_gui;

import Utils.generalMethods;
import clientUtil.ClientUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class OrderConfirmationLocalController {

    @FXML
    private Button okBtn;
    
	public void start (Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/OrderConfirmationLocal.fxml", "Order Confirmation");
	}

    @FXML
    void clickOnOkBtn(ActionEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    		LoginController.logout(ClientUtils.currUser.getUsername());
    		System.exit(1);
    }

}
