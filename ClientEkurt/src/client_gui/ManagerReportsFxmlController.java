package client_gui;

import javafx.scene.control.ComboBox;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.Machine;
import logic.OrderReport;

public class ManagerReportsFxmlController implements Initializable{

	@FXML
	private ComboBox<String> machineComboBox;
	@FXML
	private ComboBox<String> reportTypeComboBox;
	@FXML
	private ComboBox<String> monthComboBox;
	@FXML
	private ComboBox<Year> yearComboBox;
	@FXML
	private Button showReportBtn;
	@FXML
	private Button backBtn;
	@FXML
	private Label errorLabel;
	public static ObservableList<OrderReport> listView = FXCollections.observableArrayList();
	private String[] list;

	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/ManagerReportsPage.fxml",
				"Ekurt Report's Menu");
	}
	
	@FXML
	void Back(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding window
		Stage primaryStage = new Stage();
		CeoDashboardController menuPage = new CeoDashboardController();
		menuPage.start(primaryStage);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Year currentYear = Year.now();
		for (int year = currentYear.getValue(); year >= currentYear.getValue() - 7; year--) {
		    yearComboBox.getItems().add(Year.of(year));
		}
		ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October", "November", "December");
		ObservableList<String> types = FXCollections.observableArrayList("Orders Report", "Inventory report",
				"Customer report");
		this.monthComboBox.setItems(months);
		this.reportTypeComboBox.setItems(types);
		Transaction transaction = new Transaction(Action.GET_MACHINE, null, null);
		ClientUI.chat.accept(transaction);
		transaction = ClientUI.chat.getObj();
		List<String> machines = (List<String>) transaction.getData();
		for (int i = 0; i <machines.size(); i++) {
		machines.get(i).split("\\s+");
		}
		if (transaction.getResponse() == Response.GETMACHINE_SUCCESSFULLY) {
			machineComboBox.setItems(FXCollections.observableList(machines));
		}
	}
	
	// to be continue...
    @FXML
    void viewReport(ActionEvent event) {
    	errorLabel.setVisible(false);
		Stage primaryStage = new Stage();
		Transaction transaction;
		String dateString;
    	if(monthComboBox.getValue() == null || yearComboBox.getValue() == null || reportTypeComboBox.getValue() == null|| 
    			machineComboBox.getValue() == null) {
    		errorLabel.setVisible(true);
			errorLabel.setText("Inorder to view report you must choose\n'YEAR', 'MONTH'/'QUARTER' and Store!");
    	} else {
    		String yearString = yearComboBox.getValue().toString();
    	    // Parse the month name
    		String monthName = monthComboBox.getValue();
    	    Month month = Month.valueOf(monthName.toUpperCase());
    	    // Get the month number
    	    int monthNumber = month.getValue();
    	    if (monthNumber < 9) {
    	    	dateString = yearString +"-0" +monthNumber+ "-01";
    	    }
    	    else {dateString = yearString +"-" +monthNumber+ "-01";}
    		ArrayList<String> reportDetails = new ArrayList<String>(Arrays.asList(machineComboBox.getValue(),dateString));
    		/*ArrayList<String> reportDetails = new ArrayList<String>(Arrays.asList(machineComboBox.getValue(),monthComboBox.getValue() ,
    				yearComboBox.getValue().toString()));*/
    		switch(reportTypeComboBox.getValue()) 
    		{	
    		  case "Orders Report":
    			  transaction = new Transaction(Action.GET_ORDERS_REPORT, reportDetails);
    			  ClientUI.chat.accept(transaction);
    			  if (transaction.getResponse() == Response.GETORDERREPORT_UNSUCCESSFULLY) {
    				  errorLabel.setVisible(true);
    			  	  errorLabel.setText("No such report");
    			  }
    			  else {
    				  listView.clear();
    				  transaction = ClientUI.chat.getObj();
    					List<String> orderReportList = (List<String>) transaction.getData();
	    				for (int i = 0; i <orderReportList.size(); i++) {
	    					list = (orderReportList.get(i).split("\\s+"));
	    						listView.add(new OrderReport (list[0],list[1], list[2],list[3],list[4],list[5]));
	        					System.out.println(orderReportList);
	    					}
	    			  ((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	    				OrderReportsPageFxmlController page = new OrderReportsPageFxmlController();
	    				page.start(primaryStage, listView);
	    				
    			  }
    		    break;
    			// to be continue...
    		  case "Inventory report":
    			  transaction = new Transaction(Action.GET_INVENTORY_REPORT, reportDetails);
    			  ClientUI.chat.accept(transaction);
    			  if (transaction.getResponse() == Response.GETINVENTORYREPORT_UNSUCCESSFULLY) {
    				  errorLabel.setVisible(true);
    				  errorLabel.setText("No such report");
    			  }
    			  else {
	    			  ((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	    			  InvenrtoryReportsPageFxmlController page = new InvenrtoryReportsPageFxmlController();
	    				page.start(primaryStage);
    			  }
    		    break;
    		  case "Customer report":
    		  {/*
    			  transaction = new Transaction(Action.GET_CUSTOMER_REPORT, reportDetails);
    			  ClientUI.chat.accept(transaction);
    			  if (transaction.getResponse() == Response.GETGET_CUSTOMERREPORT_UNSUCCESSFULLY) {
    				  errorLabel.setVisible(true);
    				  errorLabel.setText("No such report");
    			  }
    			  else {
	    			  ((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	    			  InvenrtoryReportsPageFxmlController page = new InvenrtoryReportsPageFxmlController();
	    				page.start(primaryStage);
    			  }*/
    		  } break;
    		}

    	}
    	
    }
}