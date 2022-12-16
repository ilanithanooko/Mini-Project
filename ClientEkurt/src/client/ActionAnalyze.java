package client;

import client_gui.LoginController;
import common.Transaction;

public class ActionAnalyze {

	public static boolean actionAnalyzeClient(Transaction obj) {
		ClientController.setObj(obj);
		switch (obj.getResponse()) {
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
		}
		case SUCCEED: {
			try {
				LoginController.updateLoginStatus(obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			}
		case INCORRECT_VALUES: {
			// insert relevant method from the clientQuaries on the future
		}
		}
		return false;
	}
}
