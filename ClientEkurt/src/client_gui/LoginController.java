package client_gui;

import java.util.HashMap;
import client.ClientUI;
import common.Action;
import common.Transaction;
import enums.RoleEnum;
import logic.User;
import clientUtil.ClientUtils;

/**
 * This class handles the Item methods to be sent and received from the server
 * for the relevant controllers
 */
public class LoginController {
	private LoginFXController loginFXController = new LoginFXController();

	/**
	 * This method creates a new message for logging in a user with it username and
	 * password provided
	 * 
	 * @param username The username of the client
	 * @param password The password of the client
	 */
	public static void loginByUsernameAndPassword(String username, String password) {
		HashMap<String, String> args = new HashMap<>();
		args.put("username", username);
		args.put("password", password);
		Transaction msg = new Transaction(Action.LOGIN_USERNAME_PASSWORD, args);
		ClientUI.chat.accept(msg);

	}

	/**
	 * This method creates a new message used to update the login status of an
	 * account in the GUI and the Current User
	 * 
	 * @param msg The message containing the answer regarding the log in status of
	 *            the account from the server
	 * @throws Exception
	 */

	public void updateLoginStatusToSucceed(Transaction msg) throws Exception {
		String loginStatus = "Login successfully";
		RoleEnum currUserRole = null;
		if (msg.getData() instanceof User) {
			ClientUtils.currUser = (User) msg.getData();
			currUserRole = ClientUtils.currUser.getRole();
		}
		loginFXController.openDashboardByRole(loginStatus, currUserRole);

	}

	public void updateLoginStatusToAlreadyLoggedIn(Transaction msg) throws Exception {
		String loginStatus = "User already logged-in";
		loginFXController.setLoginErrorLableToAlreadyLoggedIn(loginStatus);

	}

	public static void updateLoginStatusToIncorrectVals(Transaction msg) throws Exception {
		@SuppressWarnings("unused")
		String loginStatus = "Username or password is incorrect";

	}

	/**
	 * Set 'is_logged_in' column in DB to 0
	 * 
	 * @param username to logout
	 */
	public static void logout(String username) {
		ClientUtils.currUser = null;
		HashMap<String, String> args = new HashMap<>();
		args.put("username", username);
		Transaction msg = new Transaction(Action.LOGOUT_USER, args);
		ClientUI.chat.accept(msg);
	}

}