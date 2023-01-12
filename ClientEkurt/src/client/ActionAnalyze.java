package client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import clientUtil.ClientUtils;
import client_gui.ChooseMachinePickupController;
import client_gui.ConnectToServerController;
import client_gui.CustomerCategoriesController;
import client_gui.FoodCategoryController;
import client_gui.OrderConfirmationPickUpController;
import client_gui.SubscriberCategoriesPageController;
import client_gui.PromoteOffersFXController;
import common.Transaction;
import enums.RoleEnum;
import javafx.application.Platform;
import javafx.stage.Stage;
import logic.*;

public class ActionAnalyze {

	public static boolean actionAnalyzeClient(Transaction msg) throws Exception {
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
			break;
		}
		case ALREADY_LOGGED_IN: {

			// insert relevant method from the clientQuaries on the future
			//loginController.updateLoginStatusToAlreadyLoggedIn(msg);
			break;
		}
		case LOGGED_IN_SUCCESS: {
			//loginController.updateLoginStatusToSucceed(msg);
			if(msg.getData() instanceof User) {
				ClientUtils.currUser = (User)msg.getData();
			}
			break;
		}
		case INCORRECT_VALUES: {
			// insert relevant method from the clientQuaries on the future
		//	loginController.updateLoginStatusToIncorrectVals(msg);
			break;
		}
		case FOUND_MACHINE_NAMES: {
			ChooseMachinePickupController.setMachineNames((List<String>)msg.getData());
			break;
		}
		case FOUND_PRODUCTS_FOR_DISPLAY: {
			if (msg.getData() instanceof ArrayList<?>) {
				ArrayList<ProductInGrid> list = ArrayList.class.cast(msg.getData());
				if (ClientUtils.localOrderInProcess != null) {
					ClientUtils.localOrderInProcess.getProductsForDisplay().addAll(list);
				} else if (ClientUtils.pickupOrderInProcess != null) {
					ClientUtils.pickupOrderInProcess.getProductsForDisplay().addAll(list);
				} else {
					ClientUtils.deliveryOrderInProcess.getProductsForDisplay().addAll(list);
				}
			}
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
		case FOUND_CUR_STOCK: {
			
			break;
		}
		case FOUND_PAYMENT_DETAILS: {
			
			break;
		}
		case ORDER_PLACED_SUCCESSFULLY: {
			if (ClientUtils.pickupOrderInProcess != null) {
				if (msg.getData() instanceof Integer) { // when pickup
					int pickupCode = (int) msg.getData();
					new OrderConfirmationPickUpController().setPickupCode(pickupCode);
				}
			} else if (ClientUtils.deliveryOrderInProcess != null) {
				if (msg.getData() instanceof LocalDate) {
					LocalDate estimatedDelivery = (LocalDate) msg.getData();
					ClientUtils.deliveryOrderInProcess.setEstimatedDelivery(estimatedDelivery);
				}
			}
			break;
		}
		case GET_CREDIT_CARD_BY_ID_SUCCESSFULLY: {
			break;
		}
		case GET_CREDIT_CARD_BY_ID_UNSUCCESSFULLY: {
			break;
		}
		case APPROVE_REGISTER_REQUEST_UNSUCCESSFULLY : {
			break;
		}
		case APPROVE_REGISTER_REQUEST_SUCCESSFULLY : {
			break;
		}
		case FOUND_SUB_ID_LIST: {
		//	ConnectToServerController.setSubIdList((List<String>)msg.getData());
		}
	}
		return false;
	}
}
