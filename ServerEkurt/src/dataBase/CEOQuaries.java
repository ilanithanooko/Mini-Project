package dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import common.Response;
import common.Transaction;

public class CEOQuaries {

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
	
	public static void getMachine(Transaction obj, Connection con) {
		if (obj instanceof Transaction) {
			List<String> list = new ArrayList<>();
			Statement stmt;
			try {
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT machine_name FROM machine;");
				while (rs.next()) {
					StringBuilder tmpOrder = new StringBuilder();
							tmpOrder.append(rs.getString(1));
					list.add(tmpOrder.toString());
				}

				obj.setData(list);
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				obj.setResponse(Response.GETMACHINE_UNSUCCESSFULLY);
				return;
			}
			obj.setResponse(Response.GETMACHINE_SUCCESSFULLY);
		} else
			obj.setResponse(Response.GETMACHINE_UNSUCCESSFULLY);
	}
	
	public static void getOrderReport(Transaction obj, Connection con) {
		if (obj instanceof Transaction) {
			ArrayList<String> list = (ArrayList<String>) obj.getData();
			ArrayList<String> list2 = new ArrayList<String>();
			String matchingDate = list.get(1);
			matchingDate = matchingDate.substring(0,8);
			matchingDate = matchingDate+"30";
			PreparedStatement pstmt;
			Statement stmt;
			System.out.println(list.get(1));
			System.out.println(matchingDate);
			try {
				for(String a:list) {
					if(a.equals("")) {
						obj.setResponse(Response.GETORDERREPORT_UNSUCCESSFULLY);
					return;}
				}
				pstmt = con.prepareStatement("SELECT * FROM orderreport WHERE machine_name=? AND issue_date BETWEEN ? AND ?");
				pstmt.setString(1, list.get(0));
				pstmt.setString(2, list.get(1));
				pstmt.setString(3, matchingDate);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					StringBuilder tmpOrder = new StringBuilder();
					for (int i = 1; i <= 6; i++) {
						if (i == 6) {
							tmpOrder.append(rs.getString(i));
						} else {

							tmpOrder.append(rs.getString(i));
							tmpOrder.append(" ");
						}
					}
					list2.add(tmpOrder.toString());
				}
				System.out.println(list2);
				obj.setData(list2);
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				obj.setResponse(Response.GETORDERREPORT_UNSUCCESSFULLY);
				return;
			}
			obj.setResponse(Response.GETORDERREPORT_SUCCESSFULLY);
		} else
			obj.setResponse(Response.GETORDERREPORT_UNSUCCESSFULLY);
	}
	
	// to be continue...
	public static void getInventoryReport(Transaction obj, Connection con) {
		if (obj instanceof Transaction) {
			List<String> list = new ArrayList<>();
			Statement stmt;
			try {
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM inventoryreport;");
				while (rs.next()) {
					StringBuilder tmpOrder = new StringBuilder();
					for (int i = 1; i <= 5; i++) {
						if (i == 5) {
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
				obj.setResponse(Response.GETINVENTORYREPORT_UNSUCCESSFULLY);
				return;
			}
			obj.setResponse(Response.GETINVENTORYREPORT_SUCCESSFULLY);
		} else
			obj.setResponse(Response.GETINVENTORYREPORT_UNSUCCESSFULLY);
	}
	
}
