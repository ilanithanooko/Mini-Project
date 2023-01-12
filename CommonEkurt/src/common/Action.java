package common;

public enum Action {
	GET_SUBSCRIBER("GET_SUBSCRIBER", 0),
	UPDATE_SUBSCRIBER("UPDATE_SUBSCRIBER", 1),
	LOGIN_USERNAME_PASSWORD("LOGIN_USERNAME_PASSWORD", 2),
	LOGOUT_USER("LOGOUT_USER", 3),
	GET_MACHINE("GET_MACHINE", 4),
	GET_ORDERS_REPORT("GET_ORDERS_REPORT", 5),
	GET_INVENTORY_REPORT("GET_INVENTORY_REPORT", 6),
	GET_CUSTOMER_REPORT("GET_CUSTOMER_REPORT", 7),
	GET_MACHINE_BY_REGION("GET_MACHINE_BY_REGION", 8),
	GET_CUSTOMER_REPORT_BY_REGION("GET_CUSTOMER_REPORT_BY_REGION", 9),
	UPDATE_LIMIT_QUANTITY_IN_MACHINE("UPDATE_LIMIT_QUANTITY_IN_MACHINE", 10),
	UPDATE_QUANTITY_STATUS("UPDATE_QUANTITY_STATUS", 11),
	GET_USER_REGISTER_REQUEST("GET_USER_REGISTER_REQUEST", 12),
	APPROVE_REGISTER_REQUEST("APPROVE_REGISTER_REQUEST", 13),
	REGISTER_USER_TO_CUSTOMER("REGISTER_USER_TO_CUSTOMER", 14),
	GET_CREDIT_CARD_BY_ID("GET_CREDIT_CARD_BY_ID", 15),
	REGISTER_CUSTOMER_TO_SUBSCRIBER("REGISTER_CUSTOMER_TO_SUBSCRIBER", 15),
	GET_SUBSCRIBER_REQUESTS_TO_SERVICE_WORKER("GET_SUBSCRIBER_REQUESTS_TO_SERVICE_WORKER", 16),
	APPROVE_SUBSCRIBER_REQUEST("APPROVE_SUBSCRIBER_REQUEST", 17),
	GET_LIMIT_BY_MACHINE("GET_LIMIT_BY_MACHINE", 18);

	private Action(final String mission, final int serialNumber) {
	}
}
