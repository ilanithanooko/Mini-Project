package client_gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Utils.*;
import enums.RoleEnum;

/**
 * This class defines User Login screen
 */
public class LoginFXController extends Application {
	@FXML
	private Button loginButton;
	@FXML
	private TextField passwordField;
	@FXML
	private TextField userNameField;
	@FXML
	private Label loginErrorLabel = new Label("Start");

	public static Stage primaryStage = new Stage();
	public static String currentUsername;
	public static StringProperty loginStatus = new SimpleStringProperty("");

	/**
	 * Start screen and initialize the Stage and scene for the screen
	 * 
	 * @param primaryStage the primary stage for this application, onto which the
	 *                     application scene can be set. Applications may create
	 *                     other stages, if needed, but they will not be primary
	 *                     stages.
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/LoginPage.fxml", "Login Page");
	}

	/**
	 * Connect to server once pressed on the Login button. Prompt error text on
	 * screen if input not valid
	 * 
	 * @param event
	 */
	@FXML
	void onButtonPressLogin(ActionEvent event) {
		String username = userNameField.getText();
		String password = passwordField.getText();

		if (username.isEmpty()) {
			userNameField.setStyle(Constants.TEXT_FIELD_NOT_VALID_STYLE);
		} else
			userNameField.setStyle(Constants.TEXT_FIELD_VALID_STYLE);
		if (password.isEmpty()) {
			passwordField.setStyle(Constants.TEXT_FIELD_NOT_VALID_STYLE);
		} else
			passwordField.setStyle(Constants.TEXT_FIELD_VALID_STYLE);

		if (!username.isEmpty() && !password.isEmpty()) {
			currentUsername = username;
			LoginController.loginByUsernameAndPassword(username, password);
		}

	}

	/**
	 * Update login status of user, and show the relevant side navigation bar that
	 * corresponds to the user's role
	 * 
	 * @param loginStatusStr Login status of the user
	 * @param roleEnum       The role of the user connected
	 * @throws Exception
	 */

	// To be countinue..
	void openDashboardByRole(String loginStatusStr, RoleEnum roleEnum) throws Exception {
		loginStatus.set(loginStatusStr);
		switch (roleEnum) {
		case CEO:
			CeoDashboardController CEODashboard = new CeoDashboardController();
			Platform.runLater(() -> {
				CEODashboard.start(primaryStage);
			});
			break;
		case USER:
			UserDashboardController userDashboard = new UserDashboardController();
			Platform.runLater(() -> {
				userDashboard.start(primaryStage);
			});
			break;
		case REGION_MANAGER:
			CeoDashboardController regionManagerDashboard = new CeoDashboardController();
			Platform.runLater(() -> {
				regionManagerDashboard.start(primaryStage);
			});
			break;
		case SERVICE_WORKER:
			ServiceWorkerDashboardController ServiceWorkerDashboard = new ServiceWorkerDashboardController();
			Platform.runLater(() -> {
				ServiceWorkerDashboard.start(primaryStage);
			});
			break;
		default:
			break;
		}

	}

	void setLoginErrorLableToAlreadyLoggedIn(String loginStatusStr) throws Exception {
		loginStatus.set("User already logged-in");
		Platform.runLater(() -> {
			loginErrorLabel.textProperty().bind(loginStatus);

		});
	}
}