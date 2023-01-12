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
import enums.OrderStatusEnum;
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

public class UpdateStockFXController implements Initializable {

	@FXML
	private TableView<ProductsInMachine> productsTable = new TableView<ProductsInMachine>();
	@FXML
	private TableColumn<ProductsInMachine, String> productIdCol;
	@FXML
	private TableColumn<ProductsInMachine, String> productNameCol;
	@FXML
	private TableColumn<ProductsInMachine, String> quantityCol;
	@FXML
	private TableColumn<ProductsInMachine, Integer> LimitCol;
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
	private Button showRequestsBtn;
	@FXML
	private Button UpdateProductQuantityBtn;
	@FXML
	private TextField QuantityTxt;
	
	private ObservableList<ProductsInMachine> listView = FXCollections.observableArrayList();
	private static ProductsInMachine selectedProduct;
	
	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/UpdateStockPage.fxml",
				"Update stock Page");
	}

	@FXML
	void Back(ActionEvent event) throws Exception {
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding window
		Stage primaryStage = new Stage();
		StorageWorkerDashboardController menuPage = new StorageWorkerDashboardController();
		menuPage.start(primaryStage);
	}

	public void initialize(URL location, ResourceBundle resources) {
		productIdCol.setCellValueFactory(new PropertyValueFactory<ProductsInMachine, String>("productId"));
		productNameCol.setCellValueFactory(new PropertyValueFactory<ProductsInMachine, String>("productName"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<ProductsInMachine, String>("quantity"));
		LimitCol.setCellValueFactory(new PropertyValueFactory<ProductsInMachine, Integer>("productLimit"));
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
		productsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    // Update the selectedOffer variable with the new selected Offer object
			selectedProduct = newValue;
			});
	}

	@FXML
	void ShowRequests(ActionEvent event) {
		listView.clear();
		QuantityTxt.setText(null);
		String machine = machineComboBox.getValue();
		if (machine.equals("")) {
			errorLabel.setText("Please choose a machine.");
			errorLabel.setVisible(true);
		}
		Transaction transaction = new Transaction(Action.GET_INVENTORY_STOCK, null, machine);
		ClientUI.chat.accept(transaction);
		transaction = ClientUI.chat.getObj();
		productsTable.setEditable(true);
		List<ProductsInMachine> temp = (List<ProductsInMachine>) transaction.getData();
		if (transaction.getResponse() == Response.GETSTOCK_SUCCESSFULLY) {
			for (int i = 0; i < temp.size(); i++) {
				listView.add(temp.get(i));
			}
			System.out.println(listView.toString());
			productsTable.setItems(listView);
			//setColors();
		} else {
			errorLabel.setText("There is not available reports in the system");
			errorLabel.setVisible(true);
		}
		TableColumn<ProductsInMachine, String> column = quantityCol;

		// Create a cell factory that sets the text color to red for cells in the specified column
		column.setCellFactory(tc -> {
		    TableCell<ProductsInMachine, String> cell = new TableCell<>();
		    cell.textProperty().bind(cell.itemProperty());
		    cell.itemProperty().addListener((obs, oldText, newText) -> {
		        if (newText != null) {
		            cell.setTextFill(Color.RED);
		        }
		    });
		    return cell;
		});
	}

	@FXML
	void UpdateQuantity(ActionEvent event) {
		if(selectedProduct==null) {
			errorLabel.setText("Please Choose a product");
			errorLabel.setVisible(true);
			return;
		}
		if (QuantityTxt.getText()==null) {
			QuantityTxt.setStyle(Constants.TEXT_FIELD_NOT_VALID_STYLE);
			errorLabel.setText("Please enter Quantity.");
			errorLabel.setVisible(true);
			return;
		}
		else {
			List<String> queryInputs = new ArrayList<>();
			queryInputs.add(QuantityTxt.getText());
			queryInputs.add(machineComboBox.getValue());
			queryInputs.add(selectedProduct.getProductId());
			Transaction transaction = new Transaction(Action.UPDATE_QUANTITY_IN_MACHINE, null, queryInputs);
			ClientUI.chat.accept(transaction);
			transaction = ClientUI.chat.getObj();
			listView.get(listView.indexOf(selectedProduct)).setQuantity(QuantityTxt.getText());
			listView.get(listView.indexOf(selectedProduct)).setQuantityStatus("IN_STOCK");
			setColors();
		}
	}
	void setColors() {
		// Get the columns you want to compare
		TableColumn<ProductsInMachine, String> quantityColumn = quantityCol;
		TableColumn<ProductsInMachine, Integer> limitColumn = LimitCol;

		// Create a cell factory that sets the text color to green if the value in the quantity column is greater than the value in the limit column, and red otherwise
		quantityColumn.setCellFactory(tc -> {
		    TableCell<ProductsInMachine, String> cell = new TableCell<>();
		    cell.textProperty().bind(cell.itemProperty());
		    cell.itemProperty().addListener((obs, oldText, newText) -> {
		        if (newText != null) {
		            // Get the row index for the current cell
		            int rowIndex = cell.getIndex();

		            // Get the value in the limit column for the current row
		            Integer limitValue = limitColumn.getCellData(rowIndex);

		            // Compare the values and set the text color accordingly
		            if (Integer.parseInt(newText) > limitValue) {
		                cell.setTextFill(Color.GREEN);
		            } else {
		                cell.setTextFill(Color.RED);
		            }
		        }
		    });
		    return cell;
		});
	}
	

	
}
