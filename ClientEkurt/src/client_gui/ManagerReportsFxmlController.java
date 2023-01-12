package client_gui;

import javafx.scene.control.ComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import Utils.generalMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ManagerReportsFxmlController implements Initializable {

	@FXML
	private ComboBox<String> reportTypeComboBox;
	@FXML
	private Button showReportBtn;
	@FXML
	private Button backBtn;
	@FXML
	private Label errorLabel;

	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/ManagerReportsPage.fxml",
				"Ekurt Report's Menu");
	}

	@FXML
	void Back(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding window
		Stage primaryStage = new Stage();
		CeoDashboardController menuPage = new CeoDashboardController();
		menuPage.start(primaryStage);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> types = FXCollections.observableArrayList("Orders Report", "Inventory report",
				"Customer report");
		this.reportTypeComboBox.setItems(types);
		errorLabel.setVisible(false);
	}

	@FXML
	void viewReport(ActionEvent event) {
		Stage primaryStage = new Stage();
		if (reportTypeComboBox.getValue() == null) {
			errorLabel.setVisible(true);
			errorLabel.setText("Inorder to view report you must choose\nReport Type!");
		} else {
			switch (reportTypeComboBox.getValue()) {
			case "Orders Report":
				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
				OrderReportsPageFxmlController orderReportPage = new OrderReportsPageFxmlController();
				orderReportPage.start(primaryStage);
				break;
			case "Inventory report":
				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
				InvenrtoryReportsPageFxmlController inventoryReportPage = new InvenrtoryReportsPageFxmlController();
				inventoryReportPage.start(primaryStage);
				break;
			case "Customer report":
				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
				CustomerReportsPageFxmlController CustomerReportPage = new CustomerReportsPageFxmlController();
				CustomerReportPage.start(primaryStage);
				break;
			}
		}
	}
}