package client_gui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Utils.generalMethods;
import client.ClientUI;
import clientUtil.ClientUtils;
import common.Action;
import common.Transaction;
import enums.OrderStatusEnum;
import enums.RoleEnum;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.Machine;
import logic.PickUpOrder;
import logic.Product;
import logic.User;

public class ChooseMachinePickupController {
	
	public static int flag = 0;
	@FXML
	private ImageView backBtn;

	@FXML
	private ComboBox<String> chooseMachine;

	@FXML
	private Button continueBtn;

	private static List<String> machineNames;
	private static List<Machine> machineList;


	public static List<Machine> getMachineList() {
		return machineList;
	}

	public static List<String> getMachineNames() {
		return machineNames;
	}

	public static void setMachineNames(List<String> list) {
			machineNames = list;
	}

	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/ChooseMachinePickup.fxml",
				"choose machine");
	}

	@FXML
	void clickOnBackButton(MouseEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		switch (ClientUtils.currUser.getRole()) {
		case SUBSCRIBER: {
			new SubscriberDashboradController().start(new Stage());
			break;
		}
		}
	}

	@FXML
	void clickOnContinue(ActionEvent event) {
        ClientUtils.pickupOrderInProcess = new PickUpOrder(ClientUtils.currUser, chooseMachine.getValue(),
				LocalDate.now(), new ArrayList<>(), OrderStatusEnum.IN_PROCESS);
		Transaction msg1 = new Transaction(Action.GET_AVAILABLE_PRODUCTS_IN_MACHINE, ClientUtils.pickupOrderInProcess.getMachineName());
        ClientUI.chat.accept(msg1);
		Transaction msg2 = new Transaction(Action.GET_NOT_AVAILABLE_PRODUCTS_IN_MACHINE, ClientUtils.pickupOrderInProcess.getMachineName());
        ClientUI.chat.accept(msg2);
        
		((Node) event.getSource()).getScene().getWindow().hide();
		
		switch (ClientUtils.currUser.getRole()) {
		case SUBSCRIBER: {
			new SubscriberCategoriesPageController().start(new Stage());
			break;
		}
		}
	}

	public void initialize() {
		chooseMachine.getItems().addAll(machineNames);
	}

}
