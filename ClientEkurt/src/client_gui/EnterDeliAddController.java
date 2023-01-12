package client_gui;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Utils.generalMethods;
import client.ClientUI;
import clientUtil.ClientUtils;
import common.Action;
import common.Transaction;
import enums.OrderStatusEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.DeliveryOrder;
import logic.PickUpOrder;

public class EnterDeliAddController implements Initializable {

    @FXML
    private TextField city=new TextField();

    @FXML
    private Button continueBtn;

    @FXML
    private TextField houseNum = new TextField();

    @FXML
    private TextField streetName= new TextField();
    
    @FXML
    private ImageView backBtn;
    
    @FXML
    private Label addressLabel = new Label();
    
	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/EnterDeliveryAddress.fxml", "Enter Address");
	}

    @FXML
    void clickOnContinue(ActionEvent event) {
    	ClientUtils.deliveryOrderInProcess = new DeliveryOrder(ClientUtils.currUser, LocalDate.now(), new ArrayList<>(), OrderStatusEnum.IN_PROCESS);
		Transaction msg = new Transaction(Action.GET_PRODUCTS_FOR_DELIVERY,
				ClientUtils.deliveryOrderInProcess.getClient().getRegion());
		ClientUI.chat.accept(msg);
		if(city.getText().equals("") || streetName.getText().equals("") || houseNum.getText().equals("")) {
			addressLabel.setText("Incorrect details - Please try again");
			addressLabel.setVisible(true);
    		city.setStyle("-fx-border-color: #EB5234; -fx-border-width: 1px 1px 1px 1px;");
    		streetName.setStyle("-fx-border-color: #EB5234; -fx-border-width: 1px 1px 1px 1px;");
    		houseNum.setStyle("-fx-border-color: #EB5234; -fx-border-width: 1px 1px 1px 1px;");
		}else {
    	ClientUtils.deliveryOrderInProcess.setCity(city.getText());
    	ClientUtils.deliveryOrderInProcess.setStreetName(streetName.getText());
    	ClientUtils.deliveryOrderInProcess.setHouseNum(Integer.valueOf(houseNum.getText()));
		
		((Node) event.getSource()).getScene().getWindow().hide();
		ClientUtils.cartDisplayFlag = true;
		switch (ClientUtils.currUser.getRole()) {
		case SUBSCRIBER: {
			new SubscriberCategoriesPageController().start(new Stage());
			break;
		}
		case CUSTOMER: {
			new CustomerCategoriesController().start(new Stage());
			break;
		}
		}
		}
    }
    @FXML
    void clickOnCity(MouseEvent event) {
    	clearTextField();
    }

    @FXML
    void clickOnHouseNum(MouseEvent event) {
    	clearTextField();
    }

    @FXML
    void clickOnStreetName(MouseEvent event) {
    	clearTextField();
    }
    
    

	@FXML
	void clickOnBackButton(MouseEvent event) {
		ClientUtils.deliveryOrderInProcess = null;
		((Node) event.getSource()).getScene().getWindow().hide();
		switch (ClientUtils.currUser.getRole()) {
		case SUBSCRIBER: {
			new MainDashboradController().start(new Stage());
			break;
		}
		case CUSTOMER: {
			new MainDashboradController().start(new Stage());
			break;
		}
		}
	}
	
	public void clearTextField () {
		addressLabel.setVisible(false);
		city.setStyle("-fx-border-color: transparent; -fx-border-width: 0px 0px 0px 0px;");
		streetName.setStyle("-fx-border-color: transparent; -fx-border-width: 0px 0px 0px 0px;");
		houseNum.setStyle("-fx-border-color: transparent; -fx-border-width: 0px 0px 0px 0px;");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addressLabel.setVisible(false);
		city.setStyle("-fx-border-color: transparent; -fx-border-width: 0px 0px 0px 0px;");
		streetName.setStyle("-fx-border-color: transparent; -fx-border-width: 0px 0px 0px 0px;");
		houseNum.setStyle("-fx-border-color: transparent; -fx-border-width: 0px 0px 0px 0px;");
	}

}