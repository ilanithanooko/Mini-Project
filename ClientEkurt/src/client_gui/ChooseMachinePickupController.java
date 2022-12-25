package client_gui;

import java.util.List;

import Utils.generalMethods;
import clientUtil.ClientUtils;
import common.Transaction;
import enums.RoleEnum;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChooseMachinePickupController {
    @FXML
    private ImageView backBtn;

    @FXML
    private ComboBox<String> chooseMachine;
	
	@FXML
	private Button continueBtn;
	
	private static List<String> machineNames;
	
	
	public static List<String> getMachineNames() {
		return machineNames;
	}

	public static void setMachineNames(List<String> machineNames) {
		ChooseMachinePickupController.machineNames = machineNames;
	}

	void start (Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/ChooseMachinePickup.fxml", "choose machine");
	}
	
    @FXML
    void clickOnBackButton(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
		switch(ClientUtils.currUser.getRole()) {
		case SUBSCRIBER: {
			new SubscriberDashboradController().start(new Stage());
		break;
		}
	}
    }
    
    //don't forget to implement "continue" button
    
	public void initialize() {
		chooseMachine.getItems().addAll(machineNames);
	}
	
}
