package common;

public enum Action {
	GET_SUBSCRIBER("GET_SUBSCRIBER",0),
	UPDATE_SUBSCRIBER("UPDATE_SUBSCRIBER",1),
	LOGIN_USERNAME_PASSWORD("LOGIN_USERNAME_PASSWORD",2),
	LOGOUT_USER("LOGOUT_USER", 3),
	GET_MACHINES_LIST("GET_MACHINES_LIST",4);
	private Action (final String mission,final int serialNumber ) {
	}
}
