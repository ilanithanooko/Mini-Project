package client_gui;

import java.net.URL;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Utils.generalMethods;
import client.ClientUI;
import clientUtil.ClientUtils;
import common.Action;
import common.Response;
import common.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class OrderReportsPageFxmlController implements Initializable {

	@FXML
	private ComboBox<String> monthComboBox;
	@FXML
	private ComboBox<Year> yearComboBox;
	@FXML
	private ComboBox<String> regionComboBox;
	@FXML
	private Button backBtn;
	@FXML
	private Label errorLabel;
	@FXML
	private Button showReportBtn;
	@FXML
	CategoryAxis machinesAxis = new CategoryAxis();
	@FXML
	NumberAxis numOfOrdersAxis = new NumberAxis();
	@FXML
	BarChart<String, Number> ordersBarChart = new BarChart<>(machinesAxis, numOfOrdersAxis);
	@FXML
	List<XYChart.Series<String, Number>> seriesList = new ArrayList<>();

	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/OrdersReportPage.fxml",
				"Ekurt Order Report Page");
	}

	@FXML
	void Back(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding window
		Stage primaryStage = new Stage();
		ManagerReportsFxmlController menuPage = new ManagerReportsFxmlController();
		menuPage.start(primaryStage);
	}

	public void initialize(URL location, ResourceBundle resources) {
		Year currentYear = Year.now();
		machinesAxis.setTickLabelsVisible(false);
		ordersBarChart.setMinWidth(10);
		ObservableList<String> region = null;
		for (int year = currentYear.getValue(); year >= currentYear.getValue() - 7; year--) {
			yearComboBox.getItems().add(Year.of(year));
		}
		switch (ClientUtils.currUser.getRegion()) {
		case WORLDWIDE:
			region = FXCollections.observableArrayList("North", "South", "UAE");
			break;
		case NORTH:
			region = FXCollections.observableArrayList("North");
			break;
		case SOUTH:
			region = FXCollections.observableArrayList("South");
			break;
		case UAE:
			region = FXCollections.observableArrayList("UAE");
			break;
		}
		ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December");
		this.monthComboBox.setItems(months);
		this.regionComboBox.setItems(region);
		errorLabel.setVisible(false);
	}

	public void viewOrderReport(ActionEvent event) {
		seriesList.clear();
		errorLabel.setVisible(false);
		ordersBarChart.getData().clear();
		if (monthComboBox.getValue() == null || yearComboBox.getValue() == null || regionComboBox.getValue() == null) {
			errorLabel.setVisible(true);
			errorLabel.setText("Inorder to view report\n you must choose\n'YEAR', 'MONTH' and 'REGION'!");
		} else {
			List<String> list = new ArrayList<String>();
			int j = 0;
			list.add(regionComboBox.getValue());
			list.add(yearComboBox.getValue().toString());
			list.add(monthComboBox.getValue());
			Transaction transaction = new Transaction(Action.GET_ORDERS_REPORT, null, list);
			ClientUI.chat.accept(transaction);
			transaction = ClientUI.chat.getObj();
			@SuppressWarnings("unchecked")
			List<String> orderList = (List<String>) transaction.getData();
			if (orderList.isEmpty()) {
				errorLabel.setText("There is not available reports\n in the system");
				errorLabel.setVisible(true);
			}
			if (transaction.getResponse() == Response.GETORDERREPORT_SUCCESSFULLY) {
				for (int i = 0; i < orderList.size(); i = i + 2) {
					seriesList.add(new XYChart.Series<>());
					seriesList.get(j).setName(orderList.get(i));
					j++;
				}
				j = 1;
				for (int i = 0; i < orderList.size() / 2; i++) {
					int num = Integer.parseInt(orderList.get(j));
					seriesList.get(i).getData().add(new XYChart.Data<>(seriesList.get(i).getName(), num));
					ordersBarChart.getData().add(seriesList.get(i));
					j = j + 2;
				}
			} else {
				errorLabel.setText("There is not available reports\n in the system");
				errorLabel.setVisible(true);
			}
		}

	}
}
