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
import logic.*;
/**
 * This class defines the methods that send query's to the DB and receive the data back from the DB
 * and return it to the relevant controllers and classes that need to use that information
 */
public class OrderQueries {
	 //from ceoQueries
	public static void getMachineList(Transaction obj) {
		ResultSet rs = dbController.getInstance().executeQuery("SELECT * FROM ekurt.machines");
				if (rs == null)
					obj.setResponse(Response.FAILED);
				else {
					List<Machine> machines = new ArrayList<>(Machine.createMachineListFromResultSet(rs));
					List<String> machineNames = new ArrayList<>();
					for(int i=0; i<machines.size(); i++) {
						machineNames.add(machines.get(i).getMachine_name());
					}
					obj.setData(machineNames);
					obj.setResponse(Response.FOUND_MACHINE_NAMES);
				}	
	}
	
	public static void getProductCodesInMachine(Transaction obj) {
		String chosenMachine = (String)obj.getData();
		ResultSet rs = dbController.getInstance().executeQuery("SELECT pro_code FROM ekurt.productinmachine where machine_code = (SELECT machine_code FROM ekurt.machines where machine_name='" + chosenMachine + "') AND (stock > 0);");
		if (rs == null)
			obj.setResponse(Response.FAILED);
		else {
			List<ProductInGrid> products = new ArrayList<>();
			products = getProduct(rs);
			for(int i=0; i<products.size(); i++) {
				products.get(i).setAvailable(true);
			}
			obj.setData(products);
			obj.setResponse(Response.FOUND_PRODUCTS_FOR_DISPLAY);
		}
	}
	
	public static void getProductCodesInMachineNotInStock(Transaction obj) {
		String chosenMachine = (String)obj.getData();
		ResultSet rs = dbController.getInstance().executeQuery("SELECT pro_code FROM ekurt.productinmachine where machine_code = (SELECT machine_code FROM ekurt.machines where machine_name='" + chosenMachine + "') AND (stock = 0);");
		if (rs == null)
			obj.setResponse(Response.FAILED);
		else {
			List<ProductInGrid> products = new ArrayList<>();
			products = getProduct(rs);
			for(int i=0; i<products.size(); i++) {
				products.get(i).setAvailable(false);
			}
			obj.setData(products);
			obj.setResponse(Response.FOUND_PRODUCTS_FOR_DISPLAY);
		}
	}
	
	public static List<ProductInGrid> getProduct(ResultSet productCodes) {
		List<ProductInGrid> products = new ArrayList<>();
		String pro_code;
		try {
			while(productCodes.next()) {
				pro_code = productCodes.getString("pro_code");
				ResultSet rs = dbController.getInstance().executeQuery("SELECT * FROM ekurt.products where pro_code = '"+ pro_code + "';");
				products.add(ProductInGrid.getProductFromResultSet(rs));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
		
		
	}
}
