package client_gui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import Utils.Constants;
import Utils.generalMethods;
import clientUtil.ClientUtils;
import client.ClientUI;
import common.Action;
import common.Response;
import common.Transaction;
import enums.OrderStatusEnum;
import enums.RoleEnum;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.LocalOrder;
import logic.PickUpOrder;

public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private Label loginErrorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userNameField;
    
    private boolean labelFlag = false;
    
    @FXML
	public void start(Stage primaryStage) {
        generalMethods.displayScreen(primaryStage, getClass(),"/client_fxml/LoginPage.fxml","Login");
	}

    @FXML
    void onButtonPressLogin(ActionEvent event) {
    	if(userNameField.getText().isEmpty() || passwordField.getText().isEmpty() ) {
    		setLabelStyleForIncorrectValues("Please supply both username and password");
    		}else {
            HashMap<String, String> args = new HashMap<>();
            args.put("username", userNameField.getText());
            args.put("password", passwordField.getText());
            Transaction msg = new Transaction(Action.LOGIN_USERNAME_PASSWORD, args);
            ClientUI.chat.accept(msg);
            msg = (Transaction)ClientUI.chat.getObj();
            if(msg.getResponse().equals(Response.ALREADY_LOGGED_IN)) {
        		setLabelStyleForIncorrectValues("User is already logged in");
            }else if (msg.getResponse().equals(Response.INCORRECT_VALUES)) {
        		setLabelStyleForIncorrectValues("Wrong username or password - Please try again");
            }else if(msg.getResponse().equals(Response.LOGGED_IN_SUCCESS)) {
            	try {
            		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
					openDashboardByRole(ClientUtils.currUser.getRole());
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
    	}
    }
    
	public static void logout(String username) {
        ClientUtils.currUser = null;
        HashMap<String, String> args = new HashMap<>();
        args.put("username", username);
        Transaction msg = new Transaction(Action.LOGOUT_USER, args);
		ClientUI.chat.accept(msg);
	}
    
    void setLabelStyleForIncorrectValues(String string) {
		loginErrorLabel.setText(string);
		loginErrorLabel.setVisible(true);
		labelFlag = true;
		userNameField.setStyle(Constants.TEXT_FIELD_NOT_VALID_STYLE);
		passwordField.setStyle(Constants.TEXT_FIELD_NOT_VALID_STYLE);
		userNameField.clear();
		passwordField.clear();
    }
    
    @FXML
    void clickOnPasswordField(MouseEvent event) {
    	if(labelFlag) {
    		loginErrorLabel.setVisible(false);
    		userNameField.setStyle("");
    		passwordField.setStyle("");
    		labelFlag = false;
    	}
    }

    @FXML
    void clickOnUsernameField(MouseEvent event) {
    	if(labelFlag) {
    		loginErrorLabel.setVisible(false);
    		userNameField.setStyle("");
    		passwordField.setStyle("");
    		labelFlag = false;
    	}
    }
    void openDashboardByRole(RoleEnum roleEnum) throws Exception {
        switch (roleEnum) {
	        case CEO:
	        	CeoDashboardController CEODashboard = new CeoDashboardController();
	            Platform.runLater(() -> {
						CEODashboard.start(new Stage());
				});
	            break;
	        case CUSTOMER:
	        	if(ClientUtils.configuration.equals("ek")) {
        			ekConfiguration();
        			new CustomerCategoriesController().start(new Stage());
        	}else if (ClientUtils.configuration.equals("ol")) {
	        	MainDashboradController CustomerODashboard = new MainDashboradController();
	            Platform.runLater(() -> {
	            	CustomerODashboard.start(new Stage());
				});
        	}
	            break;
	        case SUBSCRIBER:
	        	if(ClientUtils.configuration.equals("ek")) {
	        			ekConfiguration();
	        			new SubscriberCategoriesPageController().start(new Stage());
	        	}else if (ClientUtils.configuration.equals("ol")) {
		        	MainDashboradController subDashboard = new MainDashboradController();
		            Platform.runLater(() -> {
		            	subDashboard.start(new Stage());
					});
	        	}
	            break;
	        case MARKETING_WORKER:
	        	MarketingWorkerDashboardFXController MWDashboard = new MarketingWorkerDashboardFXController();
	            Platform.runLater(() -> {
						MWDashboard.start(new Stage());
				});
	            break;  
	        case DELIVERY_OPERATOR:
	        	DeliveryOperatorDashboardController DODashboard = new DeliveryOperatorDashboardController();
	            Platform.runLater(() -> {
						DODashboard.start(new Stage());
				});
	            break;
        }
    }
    
    void ekConfiguration() {
		ClientUtils.localOrderInProcess = new LocalOrder(ClientUtils.currUser, ClientUtils.machine,
				LocalDateTime.now(), new ArrayList<>(), OrderStatusEnum.IN_PROCESS);
		Transaction msg1 = new Transaction(Action.GET_AVAILABLE_PRODUCTS_IN_MACHINE,
				ClientUtils.localOrderInProcess.getMachineName());
		ClientUI.chat.accept(msg1);
		
		Transaction msg2 = new Transaction(Action.GET_NOT_AVAILABLE_PRODUCTS_IN_MACHINE,
				ClientUtils.localOrderInProcess.getMachineName());
		ClientUI.chat.accept(msg2);

		ClientUtils.cartDisplayFlag = true;
    }
}
