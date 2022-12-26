package client;

import client_gui.LoginController;
import client_gui.LoginFXController;
import common.Transaction;

public class ActionAnalyze {

	/**
	 * Analyzes a transaction and performs the appropriate action.
	 *
	 * @param msg The transaction to be analyzed.
	 * @return A boolean indicating whether the action was successful.
	 * @throws Exception If an error occurs while analyzing the transaction.
	 */
	public static boolean actionAnalyzeClient(Transaction msg) throws Exception {
	    LoginController loginController = new LoginController();
	    ClientController.setObj(msg);
	    switch (msg.getResponse()) {
	        case FOUND_SUBSCRIBERS: {
	            // TODO: Add relevant action for found subscribers
	            break;
	        }
	        case DIDNT_FOUND_SUBSCRIBERS: {
	            // TODO: Add relevant action for not found subscribers
	            break;
	        }
	        case UPDATE_SUBSCRIBERS_SUCCESS: {
	            // TODO: Add relevant action for successful subscriber update
	            break;
	        }
	        case UPDATE_SUBSCRIBERS_FAILD: {
	            // TODO: Add relevant action for failed subscriber update
	            break;
	        }
	        case FAILED: {
	            // TODO: Add relevant action for failed transaction
	            break;
	        }
	        case ALREADY_LOGGED_IN: {
	            loginController.updateLoginStatusToAlreadyLoggedIn(msg);
	            break;
	        }
	        case LOGGED_IN_SUCCESS: {
	            loginController.updateLoginStatusToSucceed(msg);
	            break;
	        }
	        case INCORRECT_VALUES: {
	            loginController.updateLoginStatusToIncorrectVals(msg);
	            break;
	        }
	    }
	    return false;
	}
}
