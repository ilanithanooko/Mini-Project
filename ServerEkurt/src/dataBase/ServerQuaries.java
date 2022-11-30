package dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import common.Response;
import common.TransmissionPack;

public class ServerQuaries {

	public static void InsertOrderToDB(TransmissionPack obj, Connection con) {
		if (obj.getInformation() != null) {
			if (obj instanceof TransmissionPack) {
				ArrayList<String> list = (ArrayList<String>) obj.getInformation();
				Statement stmt,stmt2;
								
				try {
					stmt2 =con.createStatement();
					ResultSet rs = stmt2.executeQuery("SELECT orderNumber FROM orders;");
					while (rs.next()) {
						if(list.get(0).equals(rs.getString(1)) || list.get(0).equals("")) {
							obj.setResponse(Response.INSERT_ORDER_FAILD);
							return;
						}
					}
					stmt = con.createStatement();
					stmt.executeUpdate(String.format(
							"INSERT INTO zerli.orders(orderNumber, price, greetingCard, color, dOrder, shop, date, orderDate) VALUES ('%s', '%s', '%s', '%s','%s', '%s', '%s', '%s');",
							list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5), list.get(6),
							list.get(7)));
						
					

				} catch (SQLException e) {
					e.printStackTrace();
					obj.setResponse(Response.INSERT_ORDER_FAILD);
					return;
				}
				obj.setResponse(Response.INSERT_ORDER_SUCCESS);
			}
		} else
			obj.setResponse(Response.INSERT_ORDER_FAILD);
	}

	public static void EditOrderOnDB(TransmissionPack obj, Connection con) {
		
			if (obj instanceof TransmissionPack) {
				ArrayList<String> list = (ArrayList<String>) obj.getInformation();
				
				try {
					for(String a:list) {
						if(a.equals("")) {
							obj.setResponse(Response.EDIT_ORDER_FAILD);
						return;}
					}
					PreparedStatement pstmt = con.prepareStatement("UPDATE orders SET Color=? , Date=? WHERE OrderNumber=?;");
					pstmt.setString(1, list.get(0));
					pstmt.setString(2, list.get(1));
					pstmt.setString(3, list.get(2));
					if(pstmt.executeUpdate() ==0 ) {
						obj.setResponse(Response.EDIT_ORDER_FAILD);
						return;
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					obj.setResponse(Response.EDIT_ORDER_FAILD);
					return;
				} 
				obj.setResponse(Response.EDIT_ORDER_SUCCESS);

				
			}
		
	}

	public static void GetOrderFromDB(TransmissionPack obj, Connection con) {
		if (obj instanceof TransmissionPack) {
			List<String> list = new ArrayList<>();
			Statement stmt;
			try {
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM orders;");
				while (rs.next()) {
					StringBuilder tmpOrder = new StringBuilder();
					for (int i = 1; i <= 8; i++) {
						if (i == 8) {
							tmpOrder.append(rs.getString(i));
						} else {

							tmpOrder.append(rs.getString(i));
							tmpOrder.append(" ");
						}
					}
					list.add(tmpOrder.toString());

				}

				obj.setInformation(list);
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				obj.setResponse(Response.DIDNT_FOUND_ORDERS);
				return;
			}
			obj.setResponse(Response.FOUND_ORDERS);
		} else
			obj.setResponse(Response.DIDNT_FOUND_ORDERS);
	}
}
