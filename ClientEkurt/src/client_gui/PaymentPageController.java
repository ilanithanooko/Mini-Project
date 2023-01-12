package client_gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Utils.Constants;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import logic.ProductInGrid;

public class PaymentPageController implements Initializable{


    @FXML
    private Button backBtn;

    @FXML
    private Button placeOrderBtn;
    
    @FXML
    private TextField cvv;

    @FXML
    private TextField month;

    @FXML
    private TextField year;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private TextField creditCardText = new TextField();

    @FXML
    private TextField emailText = new TextField();

    @FXML
    private TextField idNumText = new TextField();
    
    private ArrayList<String> paymentDetails = new ArrayList<>();
    private String monthValue;
    private String yearValue;
    private String cvvValue;

    
	public void start (Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/PaymentPage.fxml", "Payment");
	}

    @FXML
    void clickOnBackBtn(ActionEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void clickOnPlaceOrder(ActionEvent event) {
    	if(cvv.getText().equals(cvvValue) && month.getText().equals(monthValue) && year.getText().equals(yearValue)) {
    		if(ClientUtils.localOrderInProcess != null) { //if local
    		Transaction msg = new Transaction(Action.PLACE_LOCAL_ORDER, ClientUtils.localOrderInProcess);
        	ClientUI.chat.accept(msg);
        	((Node) event.getSource()).getScene().getWindow().hide();
        	Stage.getWindows().get(0).hide();
        	new OrderConfirmationLocalController().start(new Stage());
    		}
    		else if(ClientUtils.pickupOrderInProcess != null) { //if pickup
    		Transaction msg = new Transaction(Action.PLACE_PICKUP_ORDER, ClientUtils.pickupOrderInProcess);
        	ClientUI.chat.accept(msg);
        	((Node) event.getSource()).getScene().getWindow().hide();
        	Stage.getWindows().get(0).hide();
        	new OrderConfirmationPickUpController().start(new Stage());
    		}
    		else { //if delivery
        		Transaction msg = new Transaction(Action.PLACE_DELIVERY_ORDER, ClientUtils.deliveryOrderInProcess);
            	ClientUI.chat.accept(msg);
            	((Node) event.getSource()).getScene().getWindow().hide();
            	Stage.getWindows().get(0).hide();
            	new OrderConfirmationDeliveryController().start(new Stage());
    		}
    	}else {
    		errorLabel.setVisible(true);
    		cvv.setStyle("-fx-border-color: #EB5234; -fx-border-width: 1px 1px 1px 1px;");
    		year.setStyle("-fx-border-color: #EB5234; -fx-border-width: 1px 1px 1px 1px;");
    		month.setStyle("-fx-border-color: #EB5234; -fx-border-width: 1px 1px 1px 1px;");
    	}
    }
    
    @FXML
    void clickOnCvv(MouseEvent event) {
    	clearTextField();
    }

    @FXML
    void clickOnMonth(MouseEvent event) {
    	clearTextField();
    }
    

    @FXML
    void clickOnyear(MouseEvent event) {
    	clearTextField();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		errorLabel.setVisible(false);
		Transaction msg = new Transaction(Action.GET_PAYMENT_DETAILS, ClientUtils.currUser.getId());
    	ClientUI.chat.accept(msg);
		msg = ClientUI.chat.getObj();
		if(msg.getData() instanceof ArrayList<?>) {
			paymentDetails = ArrayList.class.cast(msg.getData());
			emailText.setText(paymentDetails.get(0));
			creditCardText.setText(paymentDetails.get(1));
			idNumText.setText(String.valueOf(ClientUtils.currUser.getId()));
			monthValue = paymentDetails.get(3).substring(0, 2);
			yearValue = paymentDetails.get(3).substring(3, 5);
			cvvValue = paymentDetails.get(2);
		}else {
			
		}
	}
	
	public void clearTextField () {
		errorLabel.setVisible(false);
		cvv.setStyle("-fx-border-color: transparent; -fx-border-width: 0px 0px 0px 0px;");
		year.setStyle("-fx-border-color: transparent; -fx-border-width: 0px 0px 0px 0px;");
		month.setStyle("-fx-border-color: transparent; -fx-border-width: 0px 0px 0px 0px;");
	}

}
