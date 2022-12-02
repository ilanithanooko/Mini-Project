package client;

import common.Transaction;

public class ActionAnalyze {
	
	public static boolean actionAnalyzeClient(Transaction obj) {
		ClientController.setObj(obj);
		switch (obj.getResponse()) {
		case FOUND_SUBSCRIBERS: {
		  //insert relevant method from the clientQuaries on the future
		}
		case DIDNT_FOUND_SUBSCRIBERS:{
			//insert relevant method from the clientQuaries on the future
			}
		case UPDATE_SUBSCRIBERS_SUCCESS: {
			//insert relevant method from the clientQuaries on the future
				
			}
		case UPDATE_SUBSCRIBERS_FAILD: {
			//insert relevant method from the clientQuaries on the future
		}
		}
		return false;
	}
}
