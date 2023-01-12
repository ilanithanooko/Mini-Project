package dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import common.Response;
import common.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.ProductsInMachine;

public class CEOQuaries {

	public static void UpdateSubscriberOnDB(Transaction obj, Connection con) {

		if (obj instanceof Transaction) {
			ArrayList<String> list = (ArrayList<String>) obj.getData();

			try {
				for (String a : list) {
					if (a.equals("")) {
						obj.setResponse(Response.UPDATE_SUBSCRIBERS_FAILD);
						return;
					}
				}
				PreparedStatement pstmt = con
						.prepareStatement("UPDATE subscriber SET subNumber=? , subCreditcard=? WHERE subId=?;");
				pstmt.setString(1, list.get(0));
				pstmt.setString(2, list.get(1));
				pstmt.setString(3, list.get(2));
				if (pstmt.executeUpdate() == 0) {
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
				ResultSet rs = stmt.executeQuery("SELECT machine_name FROM machines;");
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

	public static void getMachineByRegion(Transaction obj, Connection con) {
		if (obj instanceof Transaction) {
			String region = (String) obj.getData();
			List<String> list = new ArrayList<>();
			PreparedStatement pstmt;
			try {
				pstmt = con.prepareStatement("SELECT machine_name FROM machines WHERE region=?;");
				pstmt.setString(1, region);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					list.add(rs.getString("machine_name"));
				}
				System.out.println(list.toString());
				obj.setData(list);
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				obj.setResponse(Response.GET_MACHINE_BY_REGION_UNSUCCESSFULLY);
				return;
			}
			obj.setResponse(Response.GET_MACHINE_BY_REGION_SUCCESSFULLY);
		} else
			obj.setResponse(Response.GET_MACHINE_BY_REGION_UNSUCCESSFULLY);
	}

	public static void getOrderReport(Transaction obj, Connection con) {
		if (obj instanceof Transaction) {
			PreparedStatement pstmt;
			List<String> list = (List<String>) obj.getData();
			List<String> list2 = new ArrayList<>();
			String yearString = list.get(1);
			String monthName = list.get(2);
			String dateString;
			Month month = Month.valueOf(monthName.toUpperCase());
			int monthNumber = month.getValue();
			if (monthNumber < 9) {
				dateString = yearString + "-0" + monthNumber + "-01";
			} else {
				dateString = yearString + "-" + monthNumber + "-01";
			}
			String matchingDate = dateString;
			matchingDate = matchingDate.substring(0, 8);
			matchingDate = matchingDate + "30";
			try {
				pstmt = con.prepareStatement("SELECT t1.machine_name, count(t2.order_code) AS numOfOrders "
						+ "FROM machines t1 " + "INNER JOIN machine_orders t2 ON t1.machine_code = t2.machine_code "
						+ "INNER JOIN orders t3 ON t3.order_code=t2.order_code "
						+ "WHERE t1.region=? AND t3.order_date BETWEEN ? AND ? " + "GROUP BY t1.machine_code");
				pstmt.setString(1, list.get(0));
				pstmt.setString(2, dateString);
				pstmt.setString(3, matchingDate);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					list2.add(rs.getString("machine_name"));
					list2.add(rs.getString("numOfOrders"));
				}
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

	public static void getInventoryReport(Transaction obj, Connection con) {
		if (obj instanceof Transaction) {
			String machine = (String) obj.getData();
			ArrayList<ProductsInMachine> list = new ArrayList<ProductsInMachine>();
			PreparedStatement pstmt;
			try {
				if (machine.equals("")) {
					obj.setResponse(Response.GETINVENTORYREPORT_UNSUCCESSFULLY);
					return;
				}
				pstmt = con.prepareStatement("SELECT t2.pro_code, t3.pro_name, t2.stock,  t1.price, t2.status_stock"
						+ " FROM machines t1" + " JOIN productinmachine t2 ON t1.machine_code = t2.machine_code"
						+ " JOIN products t3 ON t2.pro_code = t3.pro_code" + " WHERE t1.machine_name = ?");
				pstmt.setString(1, machine);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					list.add(new ProductsInMachine(rs.getString("pro_code"), rs.getString("pro_name"),
							rs.getString("stock"), rs.getString("price"), rs.getString("status_stock")));
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

	public static void getCustomerActivityReport(Transaction obj, Connection con) {
		if (obj instanceof Transaction) {
			PreparedStatement pstmt;
			List<String> list = new ArrayList<>();
			String yearString = (String) obj.getData();
			String startString = yearString + "-01" + "-01";
			String endString = yearString + "-12" + "-30";
			try {
				pstmt = con.prepareStatement("SELECT COUNT(*) as num_orders, EXTRACT(MONTH FROM order_date) as month "
						+ "FROM orders " + "WHERE order_date BETWEEN ? AND ? " + "GROUP BY client_id, month");
				pstmt.setString(1, startString);
				pstmt.setString(2, endString);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					list.add(rs.getString("month"));
					list.add(rs.getString("num_orders"));
				}
				obj.setData(list);
				System.out.println(list.toString());
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				obj.setResponse(Response.GETCUSTOMERREPORT_UNSUCCESSFULLY);
				return;
			}
			obj.setResponse(Response.GETCUSTOMERREPORT_SUCCESSFULLY);
		} else
			obj.setResponse(Response.GETCUSTOMERREPORT_UNSUCCESSFULLY);
	}

	public static void getCustomerActivityReportByRegion(Transaction obj, Connection con) {
		if (obj instanceof Transaction) {
			PreparedStatement pstmt;
			List<String> list = new ArrayList<>();
			List<String> queryInputs = new ArrayList<>();
			queryInputs = (List<String>) obj.getData();
			String region = queryInputs.get(0);
			String yearString = queryInputs.get(1);
			String startString = yearString + "-01" + "-01";
			String endString = yearString + "-12" + "-30";
			try {
				pstmt = con.prepareStatement("SELECT COUNT(*) as num_orders, EXTRACT(MONTH FROM order_date) as month "
						+ "FROM orders " + "JOIN users ON orders.client_id = users.id "
						+ "WHERE order_date BETWEEN ? AND ? AND users.region = ?"
						+ "GROUP BY orders.client_id, month");
				pstmt.setString(1, startString);
				pstmt.setString(2, endString);
				pstmt.setString(3, region);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					list.add(rs.getString("month"));
					list.add(rs.getString("num_orders"));
				}
				obj.setData(list);
				System.out.println(list.toString());
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				obj.setResponse(Response.GETCUSTOMERREPORT_BY_REGION_UNSUCCESSFULLY);
				return;
			}
			obj.setResponse(Response.GETCUSTOMERREPORT_BY_REGION_SUCCESSFULLY);
		} else
			obj.setResponse(Response.GETCUSTOMERREPORT_BY_REGION_UNSUCCESSFULLY);
	}
	
	public static void setLimitQuantityInMachine(Transaction obj, Connection con) {
		if (obj instanceof Transaction) {
			List<String> queryInputs = new ArrayList<>();
			queryInputs = (List<String>) obj.getData();
			String quantityLimit = (String) queryInputs.get(0);
			String machine = (String) queryInputs.get(1);
			PreparedStatement pstmt;
			try {
				for (String a : queryInputs) {
					if (a.equals("")) {
						obj.setResponse(Response.UPDATE_LIMIT_QUANTITY_IN_MACHINE_UNSUCCESSFULLY);
						return;
					}
					}
				pstmt = con
						.prepareStatement("UPDATE machines SET quantity_limit=? WHERE machine_name=?;");
				pstmt.setString(1, quantityLimit);
				pstmt.setString(2, machine);
				if (pstmt.executeUpdate() == 0) {
					obj.setResponse(Response.UPDATE_LIMIT_QUANTITY_IN_MACHINE_UNSUCCESSFULLY);
					return;
				}

			} catch (SQLException e) {
				e.printStackTrace();
				obj.setResponse(Response.UPDATE_LIMIT_QUANTITY_IN_MACHINE_UNSUCCESSFULLY);
				return;
			}
			obj.setResponse(Response.UPDATE_LIMIT_QUANTITY_IN_MACHINE_SUCCESSFULLY);
		}
	}
	
	public static void setStatusStockByQuantity(Transaction obj, Connection con) {
		if (obj instanceof Transaction) {
			List<String> queryInputs = new ArrayList<>();
			queryInputs = (List<String>) obj.getData();
			String quantityLimit = (String) queryInputs.get(0);
			String machine = (String) queryInputs.get(1);
			PreparedStatement pstmt;
			try {
				for (String a : queryInputs) {
					if (a.equals("")) {
						obj.setResponse(Response.UPDATE_STATUS_STOCK_BY_QUANTITY_UNSUCCESSFULLY);
						return;
					}
					}
				pstmt = con
						.prepareStatement("UPDATE productinmachine p "
								+ "SET p.status_stock = 'REFILL_REQUEST' "
								+ "WHERE EXISTS ("
								+ "SELECT 1 FROM machines m "
								+ "WHERE m.machine_name = ? AND m.machine_code = p.machine_code AND m.quantity_limit > p.stock)");
				pstmt.setString(1, machine);
				if (pstmt.executeUpdate() == 0) {
					obj.setResponse(Response.UPDATE_STATUS_STOCK_BY_QUANTITY_UNSUCCESSFULLY);
					return;
				}

			} catch (SQLException e) {
				e.printStackTrace();
				obj.setResponse(Response.UPDATE_STATUS_STOCK_BY_QUANTITY_UNSUCCESSFULLY);
				return;
			}
			obj.setResponse(Response.UPDATE_STATUS_STOCK_BY_QUANTITY_SUCCESSFULLY);
		}
	}
}
