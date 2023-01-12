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
	    case GET_OFFERS:{
	    	OffersQuaries.getOffers(obj);
	    break;
	    }
	    case GET_ORDERS:{
	    	DeliveryOperatorQuaries.getOrders(obj);
	    break;
	    }
	    case APPROVE_ORDER:{
	    	DeliveryOperatorQuaries.ApproveOrder(obj);
	    	 break;
	    }
	    case COMPLETE_ORDER:{
	    	DeliveryOperatorQuaries.CompleteOrder(obj);
	    	 break;
	    }
	    case GET_MACHINE:{
	    	CEOQuaries.getMachine(obj,con);
	    	break;
	    }
	    case GET_MACHINE_BY_REGION:{
	    	CEOQuaries.getMachineByRegion(obj,con);
	    	break;
	    }
	    case GET_INVENTORY_REPORT:{
	    	CEOQuaries.getInventoryReport(obj,con);
	    	break;
	    }
	    case GET_INVENTORY_STOCK:{
	    	StorageWorkerQuaries.getRefillStock(obj, con);
	    	break;
	    }
	    case UPDATE_QUANTITY_IN_MACHINE:{
	    	StorageWorkerQuaries.UpdateQuantityInMachine(obj, con);
	    	break;
	    }
	    case PROMOTE_OFFER:{
	    	OffersQuaries.PromoteOffer(obj);
	    	break;
	    }
	    case STOP_OFFER:{
	    	OffersQuaries.StopOffer(obj);
	    	break;
	    }
	}
	}
}
