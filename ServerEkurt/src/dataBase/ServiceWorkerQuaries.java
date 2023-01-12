package dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import common.Response;
import common.Transaction;
import enums.RegionEnum;
import logic.User;

public class ServiceWorkerQuaries {

	public static void getSubscriberRequestsToServiceWorker(Transaction obj, Connection con) {
		if (obj instanceof Transaction) {
			Statement stmt;
			ArrayList<User> list = new ArrayList<User>();
			try {
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT id, firstName, lastName, telephone, email, region "
						+ "FROM users " + "WHERE users.role = 'CUSTOMER' AND status='REQ_TO_SUB'");
				while (rs.next()) {
					list.add(new User(rs.getInt("id"), rs.getString("firstName"), rs.getString("lastName"),
							rs.getString("telephone"), rs.getString("email"),
							RegionEnum.valueOf(rs.getString("region"))));

				}
				obj.setData(list);
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				obj.setResponse(Response.GET_SUBSCRIBER_REQUESTS_TO_SERVICE_WORKER_UNSUCCESSFULLY);
				return;
			}
			obj.setResponse(Response.GET_SUBSCRIBER_REQUESTS_TO_SERVICE_WORKER_SUCCESSFULLY);
		} else
			obj.setResponse(Response.GET_SUBSCRIBER_REQUESTS_TO_SERVICE_WORKER_UNSUCCESSFULLY);
	}

	public static void approveSubscriberRequest(Transaction obj, Connection con) {
		if (obj instanceof Transaction) {
			int userId = (int) obj.getData();
			PreparedStatement pstmt;
			try {
				pstmt = con.prepareStatement(
						"UPDATE users SET role='SUBSCRIBER', status= 'SUBSCRIBER_APPROVED' WHERE id=?;");
				pstmt.setInt(1, userId);
				if (pstmt.executeUpdate() == 0) {
					obj.setResponse(Response.APPROVE_SUBSCRIBER_REQUEST_UNSUCCESSFULLY);
					return;
				}

			} catch (SQLException e) {
				e.printStackTrace();
				obj.setResponse(Response.APPROVE_SUBSCRIBER_REQUEST_UNSUCCESSFULLY);
				return;
			}
			obj.setResponse(Response.APPROVE_SUBSCRIBER_REQUEST_SUCCESSFULLY);
		}

	}
}
