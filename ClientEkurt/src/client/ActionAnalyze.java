package client;

import client_gui.LoginController;
import client_gui.LoginFXController;
import client_gui.PromoteOffersFXController;
import common.Transaction;

public class ActionAnalyze {

	public static boolean actionAnalyzeClient(Transaction msg) throws Exception {
		LoginController loginController = new LoginController();
		ClientController.setObj(msg);
		switch (msg.getResponse()) {
		case FOUND_SUBSCRIBERS: {
			
		}
		case DIDNT_FOUND_SUBSCRIBERS: {
			// insert relevant method from the clientQuaries on the future
		}
		case UPDATE_SUBSCRIBERS_SUCCESS: {
			// insert relevant method from the clientQuaries on the future

		}
		case UPDATE_SUBSCRIBERS_FAILD: {
			// insert relevant method from the clientQuaries on the future
		}
		case FAILED: {
			// insert relevant method from the clientQuaries on the future
		}
		case ALREADY_LOGGED_IN: {
			
			// insert relevant method from the clientQuaries on the future
			loginController.updateLoginStatusToAlreadyLoggedIn(msg);
			break;
		}
		case LOGGED_IN_SUCCESS: {
			loginController.updateLoginStatusToSucceed(msg);
			break;
			}
		case INCORRECT_VALUES: {
			// insert relevant method from the clientQuaries on the future
			loginController.updateLoginStatusToIncorrectVals(msg);
			break;
		}
		case FOUND_OFFERS:{
			//PromoteOffersFXController.setTableScreen(msg);
			break;
		}
		case FAILED_TO_GET_OFFERS:{
			break;
		}
		case FOUND_ORDERS:{
			break;
		}
		case FAILED_TO_GET_ORDERS:{
			break;
		}
	}
		return false;
	}
}
