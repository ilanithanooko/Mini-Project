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
	
	public static Connection conn;
	
	public static Connection getConn() {
		return conn;
	}
	 @SuppressWarnings("unchecked")
	  public static void parsingToData(Transaction obj) {
		 ActionAnalyze.actionAnalyzeServer(obj, conn);
		  	
	  }
	 public static boolean connectToDB(List<String> data){
		  try 
			{
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	        System.out.println("Driver definition succeed");
	    } catch (Exception ex) {
	    	/* handle the error*/
	    	 System.out.println("Driver definition failed");
	    	 }
	    
	    try 
	    {
	    	
	    	conn = DriverManager.getConnection(data.get(0),data.get(1),data.get(2));
	 		data.clear();

	        System.out.println("SQL connection succeed");
	        
	 	} catch (SQLException ex) 
	 	    {/* handle any errors*/
	 		data.clear();
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	        return false;
	        }
	    return true;
		}
	
}
