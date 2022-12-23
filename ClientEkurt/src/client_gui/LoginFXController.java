package client_gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.*;
import javafx.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import Utils.*;
import logic.*;
import common.Transaction;
import enums.RoleEnum;
import clientUtil.*;
/**
 * This class defines User Login screen
 */
public class LoginFXController extends Application  implements Initializable {
    @FXML
    private Button loginButton;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField userNameField;
    @FXML
    private Label loginErrorLabel = new Label();
    
    public static Stage primaryStage = new Stage();
    public static String currentUsername;
    //public static StringProperty loginStatus = new SimpleStringProperty();
    private generalMethods gm = new generalMethods();
    /**
     * Start screen and initialize the Stage and scene for the screen
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception
     */
    @SuppressWarnings("static-access")
	@Override
    public void start(Stage primaryStage) throws Exception {
    	this.primaryStage = primaryStage;
        gm.displayScreen(primaryStage, getClass(),"/client_fxml/LoginPage.fxml","Login");
    }

    /**
     * Connect to server once pressed on the Login button.
     * Prompt error text on screen if input not valid
     * @param event
     */
    @FXML
    void onButtonPressLogin(ActionEvent event) {
        String username = userNameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty()) {
            userNameField.setStyle(Constants.TEXT_FIELD_NOT_VALID_STYLE);
        } else userNameField.setStyle(Constants.TEXT_FIELD_VALID_STYLE);
        if (password.isEmpty()) {
            passwordField.setStyle(Constants.TEXT_FIELD_NOT_VALID_STYLE);
        } else passwordField.setStyle(Constants.TEXT_FIELD_VALID_STYLE);
        
        if (!username.isEmpty() && !password.isEmpty()) {
            currentUsername = username;
            LoginController.loginByUsernameAndPassword(username, password);

        }
//        if (loginErrorLabel.isVisible()) {
//        	loginErrorLabel.setVisible(false);
//        }
    }
    
    /**
     * Update login status of user, and show the relevant side navigation bar that corresponds
     * to the user's role
     * @param loginStatusStr Login status of the user
     * @param roleEnum The role of the user connected
     * @throws Exception 
     */
    void openDashboardByRole(String loginStatusStr, RoleEnum roleEnum) throws Exception {
    	//loginStatus.setValue(loginStatusStr);
        switch (roleEnum) {
	        case CEO:
	        	CeoDashboardController CEODashboard = new CeoDashboardController();
	            Platform.runLater(() -> {
						CEODashboard.start(primaryStage);
				});
	            break;
	        case CUSTOMER:
	        	CustomerDashboardController CustomerODashboard = new CustomerDashboardController();
	            Platform.runLater(() -> {
	            	CustomerODashboard.start(primaryStage);
				});
	            break;
	        case SUBSCRIBER:
	        	SubscriberDashboradController subDashboard = new SubscriberDashboradController();
	            Platform.runLater(() -> {
	            	subDashboard.start(primaryStage);
				});
	            break;	
	    }
    }
    
    void setLoginErrorLableToAlreadyLoggedIn(String loginStatusStr) throws Exception {
    	loginErrorLabel.setVisible(true);
    }
    	//loginStatus.setValue(loginStatusStr);    }

    /**
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */ 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	loginErrorLabel.setText("User already logged-in");
    	loginErrorLabel.setVisible(false);
    	
    	
    		
    	}
//        	loginStatus.addListener((observable, oldValue, newValue) -> {
//            loginErrorLabel.setText(newValue);
//            loginErrorLabel.setTextAlignment(TextAlignment.CENTER);
//            loginErrorLabel.setVisible(newValue.contains("incorrect") || newValue.contains("already logged-in"));
//        });  
}
    