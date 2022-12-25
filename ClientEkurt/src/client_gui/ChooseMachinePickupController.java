package client_gui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Utils.generalMethods;
import clientUtil.ClientUtils;
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
