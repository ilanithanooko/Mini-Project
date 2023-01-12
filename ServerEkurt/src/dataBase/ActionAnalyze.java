package dataBase;

import common.Transaction;
import java.sql.Connection;
import java.util.ArrayList;

public class ActionAnalyze {
	public static ArrayList<String> list = new ArrayList<String>();

	public static void actionAnalyzeServer(Transaction obj, Connection con) {

		switch (obj.getAction()) {
		case GET_SUBSCRIBER: {
			CEOQuaries.GetOrderFromDB(obj, con);
			break;
		}
		case UPDATE_SUBSCRIBER: {
			CEOQuaries.UpdateSubscriberOnDB(obj, con);
			break;
		}
		case LOGIN_USERNAME_PASSWORD: {
			LoginQuaries.loginByUsernameAndPassword(obj);
			break;
		}
		case LOGOUT_USER: {
			LoginQuaries.logoutUsername(obj);
			break;
		}
		case GET_MACHINE: {
			CEOQuaries.getMachine(obj, con);
			break;
		}
		case GET_ORDERS_REPORT: {
			CEOQuaries.getOrderReport(obj, con);
			break;
		}
		case GET_INVENTORY_REPORT: {
			CEOQuaries.getInventoryReport(obj, con);
			break;
		}
		case GET_CUSTOMER_REPORT: {
			CEOQuaries.getCustomerActivityReport(obj, con);
			break;
		}
		case GET_MACHINE_BY_REGION: {
			CEOQuaries.getMachineByRegion(obj, con);
			break;
		}
		case GET_CUSTOMER_REPORT_BY_REGION: {
			CEOQuaries.getCustomerActivityReportByRegion(obj, con);
			break;
		}
		case UPDATE_LIMIT_QUANTITY_IN_MACHINE: {
			CEOQuaries.setLimitQuantityInMachine(obj, con);
			break;
		}
		case UPDATE_QUANTITY_STATUS: {
			CEOQuaries.setStatusStockByQuantity(obj, con);
			break;
		}
		case GET_USER_REGISTER_REQUEST: {
			CEOQuaries.getUserRegisterRequest(obj, con);
			break;
		}
		case APPROVE_REGISTER_REQUEST: {
			CEOQuaries.approveRegisterRequest(obj, con);
			break;
		}
		case REGISTER_USER_TO_CUSTOMER: {
			UserQuaries.registerUserToCustomerWithCreditCard(obj, con);
			break;
		}
		case GET_CREDIT_CARD_BY_ID: {
			UserQuaries.getCreditCardById(obj, con);
			break;
		}
		case REGISTER_CUSTOMER_TO_SUBSCRIBER: {
			UserQuaries.subscriberRequest(obj, con);
			break;
		}
		case GET_SUBSCRIBER_REQUESTS_TO_SERVICE_WORKER: {
			ServiceWorkerQuaries.getSubscriberRequestsToServiceWorker(obj, con);
			break;
		}
		case APPROVE_SUBSCRIBER_REQUEST: {
			ServiceWorkerQuaries.approveSubscriberRequest(obj, con);
			break;
		}
		case GET_LIMIT_BY_MACHINE: {
			CEOQuaries.getLimitByMachine(obj, con);
			break;
		}
		}

	}
}
