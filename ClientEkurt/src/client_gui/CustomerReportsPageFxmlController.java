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
import enums.RegionEnum;
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

public class CustomerReportsPageFxmlController implements Initializable {

	@FXML
	private ComboBox<Year> yearComboBox;
	@FXML
	private Button backBtn;
	@FXML
	private Label errorLabel;
	@FXML
	private Label lowLabel = new Label();
	@FXML
	private Label middleLabel = new Label();
	@FXML
	private Label highLabel = new Label();
	@FXML
	private Button showReportBtn;
	@FXML
	CategoryAxis monthsAxis = new CategoryAxis();
	@FXML
	NumberAxis numOfCustomerAxis = new NumberAxis();
	@FXML
	BarChart<String, Number> customerReportBarChart = new BarChart<>(monthsAxis, numOfCustomerAxis);
	@FXML
	List<XYChart.Series<String, Number>> seriesList = new ArrayList<>();

	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/CustomerReportPage.fxml",
				"Ekurt Customer Report Page");
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
		monthsAxis.setTickLabelsVisible(false);
		customerReportBarChart.setMinWidth(10);
		for (int year = currentYear.getValue(); year >= currentYear.getValue() - 7; year--) {
			yearComboBox.getItems().add(Year.of(year));
		}
		errorLabel.setVisible(false);
		lowLabel.setVisible(false);
		middleLabel.setVisible(false);
		highLabel.setVisible(false);
	}

	@SuppressWarnings("unchecked")
	public void viewCustomerReport(ActionEvent event) {
		seriesList.clear();
		errorLabel.setVisible(false);
		Transaction transaction = null;
		List<String> activityList = null;
		List<String> queryInputs = new ArrayList<>();
		String region = ClientUtils.currUser.getRegion().toString();
		queryInputs.add(region);
		queryInputs.add(yearComboBox.getValue().toString());
		String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		customerReportBarChart.getData().clear();
		if (yearComboBox.getValue() == null) {
			errorLabel.setVisible(true);
			errorLabel.setText("Inorder to view report\n you must choose a 'YEAR'!");
		} else {
			int lowActivityCounter = 0, middleActivityCounter = 0, highActivityCounter = 0;
			if (ClientUtils.currUser.getRegion() == RegionEnum.WORLDWIDE) {
				transaction = new Transaction(Action.GET_CUSTOMER_REPORT, null, yearComboBox.getValue().toString());
				ClientUI.chat.accept(transaction);
				transaction = ClientUI.chat.getObj();
				activityList = (List<String>) transaction.getData();
				if (activityList.isEmpty()) {
					errorLabel.setText("There is not available reports\n in the system");
					errorLabel.setVisible(true);
					return;
				}
			} else {
				transaction = new Transaction(Action.GET_CUSTOMER_REPORT_BY_REGION, null, queryInputs);
				ClientUI.chat.accept(transaction);
				transaction = ClientUI.chat.getObj();
				activityList = (List<String>) transaction.getData();
			}
			if (transaction.getResponse() == Response.GETCUSTOMERREPORT_SUCCESSFULLY
					|| transaction.getResponse() == Response.GETCUSTOMERREPORT_BY_REGION_SUCCESSFULLY) {
				lowLabel.setVisible(true);
				middleLabel.setVisible(true);
				highLabel.setVisible(true);
				for (int j = 1; j <= 12; j++) {
					for (int i = 0; i < activityList.size(); i = i + 2) {
						if (Integer.parseInt(activityList.get(i)) == j) {
							if (Integer.parseInt(activityList.get(i + 1)) < 3) {
								lowActivityCounter++;
							} else if (Integer.parseInt(activityList.get(i + 1)) < 4) {
								middleActivityCounter++;
							} else
								highActivityCounter++;
						}
					}
					seriesList.add(new XYChart.Series<>());
					seriesList.get(j - 1).setName(months[j - 1]);
					seriesList.get(j - 1).getData().add(new XYChart.Data<>("Low Activity", lowActivityCounter));
					seriesList.get(j - 1).getData().add(new XYChart.Data<>("Middle Activity", middleActivityCounter));
					seriesList.get(j - 1).getData().add(new XYChart.Data<>("High Activity", highActivityCounter));
					customerReportBarChart.getData().add(seriesList.get(j - 1));
					lowActivityCounter = 0;
					middleActivityCounter = 0;
					highActivityCounter = 0;
				}
			} else {
				errorLabel.setText("There is not available reports\n in the system");
				errorLabel.setVisible(true);
			}
		}
	}
}