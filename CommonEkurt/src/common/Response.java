package common;

public enum Response {
	FOUND_SUBSCRIBERS("FOUND_SUBSCRIBERS",0),
	DIDNT_FOUND_SUBSCRIBERS("DIDNT_FOUND_SUBSCRIBERS",1),
	UPDATE_SUBSCRIBERS_SUCCESS("UPDATE_SUBSCRIBERS_SUCCESS",2),
	UPDATE_SUBSCRIBERS_FAILD("UPDATE_SUBSCRIBERS_FAILD",3);
	private Response(final String response,final int serialNumber) {
	}
}