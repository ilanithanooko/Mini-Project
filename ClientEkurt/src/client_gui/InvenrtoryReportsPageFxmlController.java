package client_gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import Utils.Constants;
import Utils.generalMethods;
import client.ClientUI;
import clientUtil.ClientUtils;
import common.Action;
import common.Response;
import common.Transaction;
import enums.RegionEnum;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.*;

public class InvenrtoryReportsPageFxmlController implements Initializable {

	@FXML
	private TableView<ProductsInMachine> productsTable = new TableView<ProductsInMachine>();
	@FXML
	private TableColumn<ProductsInMachine, String> productIdCol;
	@FXML
	private TableColumn<ProductsInMachine, String> productNameCol;
	@FXML
	private TableColumn<ProductsInMachine, String> quantityCol;
	@FXML
	private TableColumn<ProductsInMachine, String> priceCol;
	@FXML
	private TableColumn<ProductsInMachine, String> quantityStatusCol;
	@FXML
	private TableCell<ProductsInMachine, String> quantityCell = new TableCell<>();
	@FXML
	private ComboBox<String> machineComboBox;
	@FXML
	private Button backBtn;
	@FXML
	private Label errorLabel;
	@FXML
	private Button showReportBtn;
	@FXML
	private Button UpdateProductLimitBtn;
	@FXML
	private TextField limitTxt;

	private int limit;
	private ObservableList<ProductsInMachine> listView = FXCollections.observableArrayList();

	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/InventoryReportPage.fxml",
				"Ekurt Report Page");
	}

	@FXML
	void Back(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding window
		Stage primaryStage = new Stage();
		ManagerReportsFxmlController menuPage = new ManagerReportsFxmlController();
		menuPage.start(primaryStage);
	}

	@SuppressWarnings("unchecked")
	public void initialize(URL location, ResourceBundle resources) {
		productIdCol.setCellValueFactory(new PropertyValueFactory<ProductsInMachine, String>("productId"));
		productNameCol.setCellValueFactory(new PropertyValueFactory<ProductsInMachine, String>("productName"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<ProductsInMachine, String>("quantity"));
		priceCol.setCellValueFactory(new PropertyValueFactory<ProductsInMachine, String>("price"));
		quantityStatusCol.setCellValueFactory(new PropertyValueFactory<ProductsInMachine, String>("quantityStatus"));
		errorLabel.setVisible(false);
		String region = ClientUtils.currUser.getRegion().toString();
		List<String> machines;
		Transaction transaction;
		if (ClientUtils.currUser.getRegion() == RegionEnum.WORLDWIDE) {
			transaction = new Transaction(Action.GET_MACHINE, null, null);
			ClientUI.chat.accept(transaction);
			transaction = ClientUI.chat.getObj();
			machines = (List<String>) transaction.getData();
		} else {
			transaction = new Transaction(Action.GET_MACHINE_BY_REGION, null, region);
			ClientUI.chat.accept(transaction);
			transaction = ClientUI.chat.getObj();
			machines = (List<String>) transaction.getData();
		}
		for (int i = 0; i < machines.size(); i++) {
			machines.get(i).split("\\s+");
		}
		if (transaction.getResponse() == Response.GETMACHINE_SUCCESSFULLY
				|| transaction.getResponse() == Response.GET_MACHINE_BY_REGION_SUCCESSFULLY) {
			machineComboBox.setItems(FXCollections.observableList(machines));
		}
	}

	@FXML
	void viewInventoryReport(ActionEvent event) {
		listView.clear();
		// limitTxt.setText(null);
		String machine = machineComboBox.getValue();
		if (machine.equals("")) {
			errorLabel.setText("Please choose machine.");
			errorLabel.setVisible(true);
		}
		Transaction transaction = new Transaction(Action.GET_INVENTORY_REPORT, null, machine);
		ClientUI.chat.accept(transaction);
		transaction = ClientUI.chat.getObj();
		productsTable.setEditable(true);
		@SuppressWarnings("unchecked")
		List<ProductsInMachine> temp = (List<ProductsInMachine>) transaction.getData();
		if (transaction.getResponse() == Response.GETINVENTORYREPORT_SUCCESSFULLY) {
			for (int i = 0; i < temp.size(); i++) {
				listView.add(temp.get(i));
			}
			productsTable.setItems(listView);
		} else {
			errorLabel.setText("There is not available reports in the system");
			errorLabel.setVisible(true);
		}
		transaction = new Transaction(Action.GET_LIMIT_BY_MACHINE, null, machineComboBox.getValue());
		ClientUI.chat.accept(transaction);
		transaction = ClientUI.chat.getObj();
		String limit = (String) transaction.getData();
		limitTxt.setText(limit);
		// Create a TableColumn and set the cellFactory
		quantityCol.setCellFactory(tc -> {
			// Create a new TableCell and bind the textFill property to a value in the data
			// model
			TableCell<ProductsInMachine, String> cell = new TableCell<>();
			cell.textProperty().bind(cell.itemProperty());
			cell.textFillProperty().bind(Bindings.when(Bindings.createBooleanBinding(() -> {
				// Parse the cell's value as an integer and return true if it's less than 3
				int quantity = Integer.parseInt(cell.getItem());
				return quantity < Integer.parseInt(limit);
			}, cell.itemProperty())).then(Color.RED).otherwise(Color.GREEN));
			return cell;
		});
	}

	@FXML
	void UpdateProductLimit(ActionEvent event) {
		List<String> queryInputs = new ArrayList<>();
		queryInputs.add(limitTxt.getText());
		limit = Integer.parseInt(limitTxt.getText());
		queryInputs.add(machineComboBox.getValue());
		Transaction transaction = new Transaction(Action.UPDATE_LIMIT_QUANTITY_IN_MACHINE, null, queryInputs);
		ClientUI.chat.accept(transaction);
		transaction = ClientUI.chat.getObj();
		if (limitTxt.getText().isEmpty()) {
			limitTxt.setStyle(Constants.TEXT_FIELD_NOT_VALID_STYLE);
			errorLabel.setStyle(Constants.TEXT_FIELD_NOT_VALID_STYLE);
			errorLabel.setText("Please enter Limit.");
			errorLabel.setVisible(true);
		} else {
			// Create a TableColumn and set the cellFactory
			quantityCol.setCellFactory(tc -> {
				// Create a new TableCell and bind the textFill property to a value in the data
				// model
				TableCell<ProductsInMachine, String> cell = new TableCell<>();
				cell.textProperty().bind(cell.itemProperty());
				cell.textFillProperty().bind(Bindings.when(Bindings.createBooleanBinding(() -> {
					// Parse the cell's value as an integer and return true if it's less than 3
					int quantity = Integer.parseInt(cell.getItem());
					return quantity < limit;
				}, cell.itemProperty())).then(Color.RED).otherwise(Color.GREEN));
				return cell;
			});

			// Add the column to the TableView
			productsTable.getColumns().remove(quantityCol);
			productsTable.getColumns().add(quantityCol);
		}
	}

	@FXML
	void updateQuantityStatus(ActionEvent event) {
		String machine = machineComboBox.getValue();
		Transaction transaction = new Transaction(Action.UPDATE_QUANTITY_STATUS, null, machine);
		ClientUI.chat.accept(transaction);
		transaction = ClientUI.chat.getObj();
		listView.clear();
		if (machine.equals("")) {
			errorLabel.setText("Please choose machine.");
			errorLabel.setVisible(true);
		}
		productsTable.setEditable(true);
		if (transaction.getResponse() == Response.UPDATE_QUANTITY_STATUS_SUCCESSFULLY) {
			viewInventoryReport(event);
		} else {
			errorLabel.setText("There is not available reports in the system");
			errorLabel.setVisible(true);
		}
	}
}
