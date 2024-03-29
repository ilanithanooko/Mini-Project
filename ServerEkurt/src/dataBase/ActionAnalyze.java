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
	    break;
	    }
	    case GET_OFFERS:{
	    	OffersQuaries.getOffers(obj);
	    break;
	    }
	    case GET_ORDERS:{
	    //	DeliveryOperatorQuaries.getOrders(obj);
	    break;
	    }
	    case GET_CUR_STOCK: {
	    	OrderQueries.getProductInMachineStock(obj);
	    break;
	    }
	    case GET_PAYMENT_DETAILS: {
	    	OrderQueries.getPaymentDetails(obj);
	    break;
	    }
	    case PLACE_PICKUP_ORDER: {
	    	OrderQueries.placePickupOrder(obj);
	    break;
	    }
	    case PLACE_DELIVERY_ORDER: {
	    	OrderQueries.placeDeliveryOrder(obj);
	    break;
	    }
	    case PLACE_LOCAL_ORDER: {
	    	OrderQueries.placeLocalOrder(obj);
	    	break;
	    }
	    case GET_PRODUCTS_FOR_DELIVERY: {
	    	OrderQueries.getProductsFromWarehous(obj);
	    break;
	    }
	    case GET_CREDIT_CARD_BY_ID : {
	    	UserQuaries.getCreditCardById(obj, con);
	    }
	    case GET_ID_LIST: {
	    	UserQuaries.getSubscribersId(obj);
	    }
	}
	}
}
