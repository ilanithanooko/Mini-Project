package client_gui;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
public class LoginFXController extends Application implements Initializable {
    @FXML
    private Button loginButton;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField userNameField;
    @FXML
    private Label loginErrorLabel;
    
    public static Stage primaryStage = new Stage();
    public static String currentUsername;
    public static StringProperty loginStatus = new SimpleStringProperty("");
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
    	loginErrorLabel = new Label();
        gm.displayScreen(primaryStage, getClass(),"/client_fxml/LoginPage.fxml","Login Page");
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
        //loginErrorLabel.setVisible(username.isEmpty());
        //loginErrorLabel.setVisible(password.isEmpty());

        if (username.isEmpty()) {
            userNameField.setStyle(Constants.TEXT_FIELD_NOT_VALID_STYLE);
        } else userNameField.setStyle(Constants.TEXT_FIELD_VALID_STYLE);
        if (password.isEmpty()) {
            passwordField.setStyle(Constants.TEXT_FIELD_NOT_VALID_STYLE);
        } else passwordField.setStyle(Constants.TEXT_FIELD_VALID_STYLE);

        if (!username.isEmpty() && !password.isEmpty()) {
            currentUsername = username;
           // primaryStage = ((Stage) ((Button) event.getSource()).getScene().getWindow());
            LoginController.loginByUsernameAndPassword(username, password);
        }
       // if (loginErrorLabel.isVisible()) {
      //      loginErrorLabel.setVisible(false);
      //  }
    }
    
    /**
     * Update login status of user, and show the relevant side navigation bar that corresponds
     * to the user's role
     * @param loginStatusStr Login status of the user
     * @param roleEnum The role of the user connected
     * @throws Exception 
     */
    
    
    void openDashboardByRole(String loginStatusStr, RoleEnum roleEnum) throws Exception {
       // loginStatus.set(loginStatusStr);
        switch (roleEnum) {
	        case CEO:
	        	CeoDashboardController CEODashboard = new CeoDashboardController();
	            Platform.runLater(() -> {
						CEODashboard.start(primaryStage);
				});
	            break;
	        case MARKETING_WORKER:
	        	MarketingWorkerDashboardFXController MWDashboard = new MarketingWorkerDashboardFXController();
	            Platform.runLater(() -> {
						MWDashboard.start(primaryStage);
				});
	            break;  
	        case DELIVERY_OPERATOR:
	        	DeliveryOperatorDashboardController DODashboard = new DeliveryOperatorDashboardController();
	            Platform.runLater(() -> {
						DODashboard.start(primaryStage);
				});
	            break;
        }
    }
    
    void setLoginErrorLabelToAlreadyLoggedIn(String loginStatusStr) {
        loginStatus.set(loginStatusStr);
        Platform.runLater(() -> {
            loginErrorLabel.setText(loginStatusStr);
            loginErrorLabel.setVisible(true);
        });
    }

   // @Override
   public void initialize(URL location, ResourceBundle resources) {}
   //     loginStatus.addListener((observable, oldValue, newValue) -> {
   //         if (newValue.equals("Already logged in")) {
   //             loginErrorLabel.setText("Already logged in");
   //             loginErrorLabel.setVisible(true);
   //         }
   //    });
    }


    /**
     * Show the relevant side navigation for each role
     * @param roleEnum The user's role
     */
    
   // public static void openSideNavigationByRole(RoleEnum roleEnum) throws Exception {
       
            
  //      }
   // }
                /*
            case CLIENT:
                SidenavigationClientFXController sidenavigationClientFXController = new SidenavigationClientFXController();
                Platform.runLater(() -> sidenavigationClientFXController.start(primaryStage));
                break;
            case COMPANY_MARKETING_WORKER:
                SidenavigationCompanyMarketingWorkerFXController sidenavigationCompanyMarketingWorkerFXController = new SidenavigationCompanyMarketingWorkerFXController();
                Platform.runLater(() -> sidenavigationCompanyMarketingWorkerFXController.start(primaryStage));
                break;
            case CUSTOMER_SERVICE_WORKER:
                SidenavigationCustomerServiceWorkerFXController sidenavigationCustomerServiceWorkerFXController = new SidenavigationCustomerServiceWorkerFXController();
                Platform.runLater(() -> sidenavigationCustomerServiceWorkerFXController.start(primaryStage));
                break;
            case STORE_MANAGER:
                SidenavigationStoreManagerFXController sidenavigationStoreManagerFXController = new SidenavigationStoreManagerFXController();
                Platform.runLater(() -> sidenavigationStoreManagerFXController.start(primaryStage));
                break;
            case STORE_WORKER:
                SidenavigationStoreWorkerFXController sidenavigationStoreWorkerFXController = new SidenavigationStoreWorkerFXController();
                Platform.runLater(() -> sidenavigationStoreWorkerFXController.start(primaryStage));
                break;
            case SERVICE_EXPERT:
                SidenavigationServiceExpertFXController sidenavigationServiceExpertFXController = new SidenavigationServiceExpertFXController();
                Platform.runLater(() -> sidenavigationServiceExpertFXController.start(primaryStage));
                break;
            case DELIVERY_MAN:
                SidenavigationDeliverymanFXController sidenavigationDeliveryManFXController = new SidenavigationDeliverymanFXController();
                Platform.runLater(() -> sidenavigationDeliveryManFXController.start(primaryStage));
                break;
        }
    } */

    

    