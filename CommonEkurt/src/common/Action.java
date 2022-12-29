package common;

public enum Action {
	GET_SUBSCRIBER("GET_SUBSCRIBER",0),
	UPDATE_SUBSCRIBER("UPDATE_SUBSCRIBER",1),
	LOGIN_USERNAME_PASSWORD("LOGIN_USERNAME_PASSWORD",2),
	LOGOUT_USER("LOGOUT_USER", 3),
	GET_OFFERS("GET_OFFERS",4),
	GET_ORDERS("GET_ORDERS",5);
	private Action (final String mission,final int serialNumber ) {
	}
}
