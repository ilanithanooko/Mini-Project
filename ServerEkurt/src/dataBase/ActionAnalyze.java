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
			ServerQuaries.GetOrderFromDB(obj, con);
			break;
		}
		case UPDATE_SUBSCRIBER:{
			ServerQuaries.UpdateSubscriberOnDB(obj, con);
			break;
			}
		}
		
	}
}
