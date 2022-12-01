package dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import common.Response;
import common.Transaction;

public class ServerQuaries {

	public static void UpdateSubscriberOnDB(Transaction obj, Connection con) {
		
			if (obj instanceof Transaction) {
				ArrayList<String> list = (ArrayList<String>) obj.getData();
				
				try {
					for(String a:list) {
						if(a.equals("")) {
							obj.setResponse(Response.UPDATE_SUBSCRIBERS_FAILD);
						return;}
					}
					PreparedStatement pstmt = con.prepareStatement("UPDATE subscriber SET subNumber=? , subCreditcard=? WHERE subId=?;");
					pstmt.setString(1, list.get(0));
					pstmt.setString(2, list.get(1));
					pstmt.setString(3, list.get(2));
					if(pstmt.executeUpdate() ==0 ) {
						obj.setResponse(Response.UPDATE_SUBSCRIBERS_FAILD);
						return;
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					obj.setResponse(Response.UPDATE_SUBSCRIBERS_FAILD);
					return;
				} 
				obj.setResponse(Response.UPDATE_SUBSCRIBERS_SUCCESS);		
			}
		
	}

	public static void GetOrderFromDB(Transaction obj, Connection con) {
		if (obj instanceof Transaction) {
			List<String> list = new ArrayList<>();
			Statement stmt;
			try {
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM subscriber;");
				while (rs.next()) {
					StringBuilder tmpOrder = new StringBuilder();
					for (int i = 1; i <= 7; i++) {
						if (i == 7) {
							tmpOrder.append(rs.getString(i));
						} else {

							tmpOrder.append(rs.getString(i));
							tmpOrder.append(" ");
						}
					}
					list.add(tmpOrder.toString());

				}

				obj.setData(list);
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				obj.setResponse(Response.DIDNT_FOUND_SUBSCRIBERS);
				return;
			}
			obj.setResponse(Response.FOUND_SUBSCRIBERS);
		} else
			obj.setResponse(Response.DIDNT_FOUND_SUBSCRIBERS);
	}
}
