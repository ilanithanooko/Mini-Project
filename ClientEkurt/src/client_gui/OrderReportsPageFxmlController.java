package client_gui;

import java.net.URL;
import java.time.Year;
import java.util.List;
import java.util.ResourceBundle;

import Utils.generalMethods;
import client.ClientUI;
import common.Action;
import common.Response;
import common.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.*;

public class OrderReportsPageFxmlController implements Initializable {
	
	@FXML
	private TableView<OrderReport> table = new TableView<OrderReport>();
	@FXML
	private TableColumn<OrderReport, String> machineIdCol;
	@FXML
	private TableColumn<OrderReport, String> machineNameCol;
	@FXML
	private TableColumn<OrderReport, String> IssueDateCol;
	@FXML
	private TableColumn<OrderReport, String> orderIdCol;
	@FXML
	private TableColumn<OrderReport, String> deliveryMethodCol;
	@FXML
	private TableColumn<OrderReport, String> orderPriceCol;
	@FXML
	private Button backBtn;
	@FXML
	private Label warningLbl;


	public void start(Stage primaryStage , ObservableList<OrderReport> listView) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/OrdersReportPage.fxml",
				"Ekurt Report Page");
		table.setItems(listView);
	}
	
	@FXML
	void Back(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding window
		Stage primaryStage = new Stage();
		ManagerReportsFxmlController menuPage = new ManagerReportsFxmlController();
		menuPage.start(primaryStage);
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		machineIdCol.setCellValueFactory(new PropertyValueFactory<OrderReport, String>("machineID"));
		machineNameCol.setCellValueFactory(new PropertyValueFactory<OrderReport, String>("machineName"));
		IssueDateCol.setCellValueFactory(new PropertyValueFactory<OrderReport, String>("orderDate"));
		orderIdCol.setCellValueFactory(new PropertyValueFactory<OrderReport, String>("orderID"));
		deliveryMethodCol.setCellValueFactory(new PropertyValueFactory<OrderReport, String>("deliveryMethod"));
		orderPriceCol.setCellValueFactory(new PropertyValueFactory<OrderReport, String>("orderPrice"));
		
		
	}
}
