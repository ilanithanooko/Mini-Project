package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import common.Transaction;

public class dbController {
	private static dbController databaseController;
	public static Connection conn;

	public static Connection getConn() {
		return conn;
	}

	public static dbController getInstance() {
		if (databaseController == null) {
			databaseController = new dbController();
		}
		return databaseController;
	}

	/**
	 * @param query SELECT query
	 * @return ResultSet
	 */
	public ResultSet executeQuery(String query) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param query UPDATE/INSERT/DELETE queries
	 * @return Number of values that has been changed
	 */
	public int executeUpdate(String query) {
		PreparedStatement prepareStatement = null;

		try {
			prepareStatement = conn.prepareStatement(query);
			return prepareStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static void parsingToData(Transaction obj) {
		ActionAnalyze.actionAnalyzeServer(obj, conn);

	}

	public static boolean connectToDB(List<String> data) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {

			conn = DriverManager.getConnection(data.get(0), data.get(1), data.get(2));
			data.clear();

			System.out.println("SQL connection succeed");

		} catch (SQLException ex) {/* handle any errors */
			data.clear();
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}
		return true;
	}

}
