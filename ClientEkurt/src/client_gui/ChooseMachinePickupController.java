package client_gui;

import Utils.generalMethods;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.*;

public class ChooseMachinePickupController {
	
	@FXML
	private ComboBox chooseMachine; 
	
	@FXML
	private Button continueBtn;
	
	void start (Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/ChooseMachinePickup.fxml", "choose machine");
	}
	
	//dont forget to add a "back" button!
	
}
