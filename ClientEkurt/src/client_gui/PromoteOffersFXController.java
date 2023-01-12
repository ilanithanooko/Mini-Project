package client_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import Utils.generalMethods;
import client.ClientUI;
import clientUtil.ClientUtils;
import common.Action;
import common.Response;
import common.Transaction;
import enums.OrderStatusEnum;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.Offer;
import logic.ProductsInMachine;
import logic.Subscriber;

public class PromoteOffersFXController implements Initializable {
	
	//@FXML
	//private ComboBox<String> RegionComboBox;
	@FXML
	private Button backBtn;
	@FXML
	private Button getOffersBtn;
	@FXML
	private Button PromoteOfferBtn;
	@FXML
	private Button StopOffersBtn;
	@FXML
	private TableView<Offer> table = new TableView<Offer>(); //changed to static
	@FXML
	private TableColumn<Offer, String> IdColTbl;
	@FXML
	private TableColumn<Offer, String> NameColTbl;
	@FXML
	private TableColumn<Offer, String> PriceColTbl;
	@FXML
	private TableColumn<Offer, String> DiscountColTbl;
	@FXML
	private TableColumn<Offer, String> StatusColTbl;
	@FXML
	private Label errorLabel;
	private ObservableList<Offer> listView = FXCollections.observableArrayList();
	private generalMethods gm = new generalMethods();
	private Offer selectedOffer;
	
	@SuppressWarnings("static-access")
	public void start(Stage primaryStage) throws Exception {
		gm.displayScreen(primaryStage, getClass(), "/client_fxml/PromoteOffersPage.fxml", "Promote Offers Page");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		IdColTbl.setCellValueFactory(new PropertyValueFactory<Offer, String>("productID"));
		NameColTbl.setCellValueFactory(new PropertyValueFactory<Offer, String>("productName"));
		PriceColTbl.setCellValueFactory(new PropertyValueFactory<Offer, String>("productPrice"));
		DiscountColTbl.setCellValueFactory(new PropertyValueFactory<Offer, String>("productDiscount"));
		StatusColTbl.setCellValueFactory(new PropertyValueFactory<Offer, String>("IsActive"));
		//ObservableList<String> regions = FXCollections.observableArrayList("South","North","UAE");
		//RegionComboBox.setItems(regions);
		errorLabel.setVisible(false);
		table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	    // Update the selectedOffer variable with the new selected Offer object
	    selectedOffer = newValue;
		});
		setColors();
	}

	@FXML
	void Back(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding window
		Stage primaryStage = new Stage();
		MarketingWorkerDashboardFXController menuPage = new MarketingWorkerDashboardFXController();
		menuPage.start(primaryStage);
	}
	@FXML
	void GetOffers(ActionEvent event) throws Exception {
		errorLabel.setVisible(false);
		Transaction msg;
		//if(RegionComboBox.getValue() == null)
			//errorLabel.setVisible(true);
		msg = new Transaction(Action.GET_OFFERS,ClientUtils.currUser.getRegion());
	    ClientUI.chat.accept(msg);
		msg = ClientUI.chat.getObj(); 
		if(msg.getResponse()==Response.FAILED_TO_GET_OFFERS) {
			listView.clear();
			errorLabel.setVisible(true);
		}else {
		listView.clear();
		errorLabel.setVisible(false);
		@SuppressWarnings("unchecked")
		List<Offer> Alist = (List<Offer>) msg.getData();
		for (Offer offer : Alist) {
			  listView.add(offer);
			}
			listView.forEach(Offer -> System.out.println(Offer));
			table.setEditable(true);
			table.setItems(listView);
			StopOffersBtn.setDisable(false);
			PromoteOfferBtn.setDisable(false);

		}
	}
	@FXML
	void PromoteOffers(ActionEvent event) throws Exception {
		errorLabel.setVisible(false);
		Transaction msg;
		selectedOffer.setRegion(ClientUtils.currUser.getRegion());		
		if(selectedOffer==null) {
			errorLabel.setText("Please choose an offer");
			errorLabel.setVisible(true);
			return;
		}
		if(selectedOffer.getIsActive().equalsIgnoreCase("ON")) {
			errorLabel.setText("The offer is active already!");
			errorLabel.setVisible(true);
			return;
		}
		msg = new Transaction(Action.PROMOTE_OFFER,selectedOffer);
	    ClientUI.chat.accept(msg);
		msg = ClientUI.chat.getObj(); 
		if(msg.getResponse()==Response.OFFER_PROMOTED_UNSUCCESSFULLY) {
			errorLabel.setText("Failed to promote offer pleast try again");
			errorLabel.setVisible(true);
		}
		else {
		listView.get(listView.indexOf(selectedOffer)).setIsActive("ON");
		setColors();
		table.refresh();
		}
	}
	@FXML
	void stopOffer(ActionEvent event) throws Exception {
		errorLabel.setVisible(false);
		Transaction msg;
		selectedOffer.setRegion(ClientUtils.currUser.getRegion());
		
		if(selectedOffer==null) {
			errorLabel.setText("Please choose an offer");
			errorLabel.setVisible(true);
			return;
		}
		if(selectedOffer.getIsActive().equalsIgnoreCase("OFF")) {
			errorLabel.setText("The offer is not active already!");
			errorLabel.setVisible(true);
			return;
		}
		msg = new Transaction(Action.STOP_OFFER,selectedOffer);
	    ClientUI.chat.accept(msg);
		msg = ClientUI.chat.getObj(); 
		if(msg.getResponse()==Response.OFFER_PROMOTED_UNSUCCESSFULLY) {
			errorLabel.setText("Failed to stop offer pleast try again");
			errorLabel.setVisible(true);
		}
		else {
		listView.get(listView.indexOf(selectedOffer)).setIsActive("OFF");
		setColors();
		table.refresh();
		}
	}
	void setColors() {
		// Get the columns you want to compare
		TableColumn<Offer, String> statusColumn = StatusColTbl;
		// Create a cell factory that sets the text color to green if the value in the status column is "ON", and red otherwise
		statusColumn.setCellFactory(tc -> {
		    TableCell<Offer, String> cell = new TableCell<>();
		    cell.textProperty().bind(cell.itemProperty());
		    cell.itemProperty().addListener((obs, oldText, newText) -> {
		        if (newText != null) {
		            if (newText.equals("ON")) {
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
