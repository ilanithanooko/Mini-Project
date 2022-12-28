package dataBase;
import common.Transaction;
import java.sql.Connection;
import java.util.ArrayList;
import common.Response;

public class ActionAnalyze {
	public static ArrayList<String> list = new ArrayList<String>();

	public static void actionAnalyzeServer(Transaction obj, Connection con) {
		
		switch (obj.getAction()) {
		case GET_SUBSCRIBER: {
			CEOQuaries.GetOrderFromDB(obj, con);
			break;
		}
		case UPDATE_SUBSCRIBER:{
			CEOQuaries.UpdateSubscriberOnDB(obj, con);
			break;
			}
	    case LOGIN_USERNAME_PASSWORD:{
	    	LoginQuaries.loginByUsernameAndPassword(obj);
	    	break;
		}
	    case LOGOUT_USER: {
	    	LoginQuaries.logoutUsername(obj);
	    	break;
	    }
	    case GET_MACHINES_LIST: {
	    	OrderQueries.getMachineList(obj);
	    	break;
	    }
	    case GET_AVAILABLE_PRODUCTS_IN_MACHINE: {
	    		OrderQueries.getProductCodesInMachine(obj);
	    	break;
	    }
	    case GET_NOT_AVAILABLE_PRODUCTS_IN_MACHINE: {
	    	OrderQueries.getProductCodesInMachineNotInStock(obj);
	    }
	}
	}
}
