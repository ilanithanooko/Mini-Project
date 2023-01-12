package client_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Utils.generalMethods;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import clientUtil.ClientUtils;
import common.Action;
import common.Transaction;
import enums.RoleEnum;

public class MainDashboradController implements Initializable{

    @FXML
    private Button LogoutBtn;

    @FXML
    private Button deliveryBtn;

    @FXML
    private Button manageDeliveryBtn;

    @FXML
    private Button pickUpBtn;

    @FXML
    private Button regAsSubBtn;

    @FXML
    private Label usernameLabel;
	
	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/MainDashboard.fxml", "Main menu");
	}
	
	@FXML
    void clickOnlogout(ActionEvent event) throws Exception {
		LoginController.logout(ClientUtils.currUser.getUsername());
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		new LoginController().start(new Stage());
	}
	
	@FXML
    void clickOnPickup(ActionEvent event) {
		Transaction machineGetter = new Transaction(Action.GET_MACHINES_LIST, null);
        ClientUI.chat.accept(machineGetter); 
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		new ChooseMachinePickupController().start(new Stage());
	}
	

    @FXML
    void clickOnBecomeASubscriber(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		new SubscriberRegisterController().start(new Stage());
    }

    @FXML
    void clickOnDelivery(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		new EnterDeliAddController().start(new Stage());
    }

    @FXML
    void clickOnManageDeliveries(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		usernameLabel.setText(ClientUtils.currUser.getFirstName()+ " " + ClientUtils.currUser.getLastName() + "☺");
		if(ClientUtils.currUser.getRole().equals(RoleEnum.SUBSCRIBER)) {
			regAsSubBtn.setVisible(false);
		}
	}
	
}