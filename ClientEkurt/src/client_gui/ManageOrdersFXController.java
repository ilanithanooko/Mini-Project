package client_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.DeliveryOrder;
import logic.Offer;
import logic.Subscriber;

public class ManageOrdersFXController implements Initializable {
	
	@FXML
	private Button backBtn;
	@FXML
	private Button ShowOrdersBtn;
	@FXML
	private Button ApproveOrderBtn;
	@FXML
	private Button CompleteOrderBtn;
	@FXML
	private TableView<DeliveryOrder> table = new TableView<DeliveryOrder>(); //changed to static
	@FXML
	private TableColumn<DeliveryOrder, String> OrderIdColTbl;
	@FXML
	private TableColumn<DeliveryOrder, String> ClientIDColTbl;
	@FXML
	private TableColumn<DeliveryOrder, Date> OrderDateColTbl;
	@FXML
	private TableColumn<DeliveryOrder, Date> SupplyDateColTbl;
	@FXML
	private TableColumn<DeliveryOrder, Float> StatusColTbl;
	@FXML
	private Label errorLabel;
	private ObservableList<DeliveryOrder> listView = FXCollections.observableArrayList();
	private generalMethods gm = new generalMethods();
	private DeliveryOrder selectedOrder;
	
	@SuppressWarnings("static-access")
	public void start(Stage primaryStage) throws Exception {
		gm.displayScreen(primaryStage, getClass(), "/client_fxml/ManageOrdersPage.fxml", "Promote Offers Page");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		OrderIdColTbl.setCellValueFactory(new PropertyValueFactory<DeliveryOrder, String>("OrderId"));
		ClientIDColTbl.setCellValueFactory(new PropertyValueFactory<DeliveryOrder, String>("ClientId"));
		OrderDateColTbl.setCellValueFactory(new PropertyValueFactory<DeliveryOrder, Date>("OrderDate"));
		SupplyDateColTbl.setCellValueFactory(new PropertyValueFactory<DeliveryOrder, Date>("SuppDate"));
		StatusColTbl.setCellValueFactory(new PropertyValueFactory<DeliveryOrder, Float>("Price"));
		errorLabel.setVisible(false);
		table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	    // Update the selectedOffer variable with the new selected Offer object
	    selectedOrder = newValue;
		});
	}

	@FXML
	void Back(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding window
		Stage primaryStage = new Stage();
		DeliveryOperatorDashboardController menuPage = new DeliveryOperatorDashboardController();
		menuPage.start(primaryStage);
	}
	@FXML
	void GetOrders(ActionEvent event) throws Exception {
		Transaction msg;
		msg = new Transaction(Action.GET_ORDERS,ClientUtils.currUser.getRegion());
	    ClientUI.chat.accept(msg);
		msg = ClientUI.chat.getObj();
		if(msg.getResponse()==Response.FAILED_TO_GET_ORDERS) {
			listView.clear();
			errorLabel.setVisible(true);
		}else {
		listView.clear();
		errorLabel.setVisible(false);
		@SuppressWarnings("unchecked")
		List<DeliveryOrder> Alist = (List<DeliveryOrder>) msg.getData();
		for (DeliveryOrder order : Alist) {
			  listView.add(order);
			}
			listView.forEach(Offer -> System.out.println(Offer));
			table.setEditable(true);
			table.setItems(listView);
		}
	}
	@FXML
	void PromoteOffers(ActionEvent event) throws Exception {
		System.out.println("hi");
		System.out.println(selectedOrder);
	}
}
