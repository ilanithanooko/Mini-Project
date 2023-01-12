package dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.print.DocFlavor.STRING;

import common.Response;
import common.Transaction;
import enums.OrderStatusEnum;
import enums.RegionEnum;
import enums.RoleEnum;
import enums.StatusEnum;
import enums.SupplyMethodEnum;
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
		ResultSet rs = dbController.getInstance().executeQuery("SELECT pro_code, stock, is_in_sale FROM ekurt.productinmachine where machine_code = (SELECT machine_code FROM ekurt.machines where machine_name='" + chosenMachine + "') AND (stock > 0);");
		if (rs == null)
			obj.setResponse(Response.FAILED);
		else {
			List<ProductInGrid> products = new ArrayList<>();
			products = getProduct(rs);
			obj.setData(products);
			obj.setResponse(Response.FOUND_PRODUCTS_FOR_DISPLAY);
		}
	}
	
	public static void getProductCodesInMachineNotInStock(Transaction obj) {
		String chosenMachine = (String)obj.getData();
		ResultSet rs = dbController.getInstance().executeQuery("SELECT pro_code, stock, is_in_sale FROM ekurt.productinmachine where machine_code = (SELECT machine_code FROM ekurt.machines where machine_name='" + chosenMachine + "') AND (stock = 0);");
		if (rs == null)
			obj.setResponse(Response.FAILED);
		else {
			List<ProductInGrid> products = new ArrayList<>();
			products = getProduct(rs);
			obj.setData(products);
			obj.setResponse(Response.FOUND_PRODUCTS_FOR_DISPLAY);
		}
	}
	
	public static void getProductsFromWarehous (Transaction obj) {
		RegionEnum region = (RegionEnum)obj.getData();
		ResultSet rs = dbController.getInstance().executeQuery("SELECT pro_code, stock, is_in_sale from ekurt.productinwarehouse where region = '" + region + "';");
		if (rs == null)
			obj.setResponse(Response.FAILED);
		else {
			List<ProductInGrid> products = new ArrayList<>();
			products = getProduct(rs);
			obj.setData(products);
			obj.setResponse(Response.FOUND_PRODUCTS_FOR_DISPLAY);
		}
	}
	
	public static List<ProductInGrid> getProduct(ResultSet productCodesAndStock) {
		List<ProductInGrid> products = new ArrayList<>();
		String pro_code;
		int stock;
		boolean is_in_sale;
		String offerName = null;
		try {
			while(productCodesAndStock.next()) {
				pro_code = productCodesAndStock.getString("pro_code");
				stock = productCodesAndStock.getInt("stock");
				is_in_sale = productCodesAndStock.getBoolean("is_in_sale");
				ResultSet rs = dbController.getInstance().executeQuery("SELECT * FROM ekurt.products where pro_code = '"+ pro_code + "';");
				
				if(is_in_sale)
				{
					offerName=getOfferName(pro_code);
				}
				products.add(ProductInGrid.getProductFromResultSet(rs, stock, is_in_sale, offerName));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
		
		
	}
	public static String getOfferName(String pro_code) {
		String offerName = null;
		ResultSet rs = dbController.getInstance().executeQuery("SELECT discount FROM  ekurt.offers WHERE pro_code='"+pro_code+"';");
		try {
			if(rs.next()) {
				offerName=rs.getString("discount");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return offerName;
		
	}
	//not in use anymore! - delete when sure.
	public static void getProductInMachineStock(Transaction obj) {
		if (obj.getData() instanceof ArrayList<?>) {
			ArrayList<String> list = ArrayList.class.cast(obj.getData());

			int stock = 0;
			ResultSet rs = dbController.getInstance().executeQuery("SELECT stock FROM ekurt.productinmachine WHERE pro_code = (SELECT pro_code FROM ekurt.products WHERE pro_name = '" + list.get(1) + "') AND (machine_code = (SELECT machine_code FROM ekurt.machines where machine_name='" + list.get(0) + "') );");
			if (rs == null)
				obj.setResponse(Response.FAILED);
			else {
				try {
					if (rs.next()) {
					    stock = rs.getInt("stock");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				obj.setData(stock);
				obj.setResponse(Response.FOUND_CUR_STOCK);
			}
		}
	}
	
	public static void getPaymentDetails(Transaction obj) {
		if (obj.getData() instanceof Integer) {
			int userId = (int) obj.getData();
			ResultSet rs = dbController.getInstance().executeQuery("SELECT email, CreditCardNum, CVV, ExpDate FROM ekurt.users , ekurt.paymentdetails WHERE users.id ='" + userId + "' AND paymentdetails.Id = '" + userId + "';");
			if (rs == null)
				obj.setResponse(Response.FAILED);
			else {
				
				List<String> paymentDetails = new ArrayList<>();
				try {
					if (rs.next()) {
						paymentDetails.add(rs.getString("email"));
						paymentDetails.add(rs.getString("CreditCardNum"));
						paymentDetails.add(rs.getString("CVV"));
						paymentDetails.add(rs.getString("ExpDate"));
					  
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				obj.setData(paymentDetails);
				obj.setResponse(Response.FOUND_PAYMENT_DETAILS);
			}
			
			
			
		} else {
			System.out.println("Not Integer");
		}
	}
	
	public static void placeLocalOrder(Transaction obj) {
		LocalOrder order = (LocalOrder) obj.getData();
		int affectedRows;
		HashMap<Integer, Product> productAndQuantity = new HashMap<>();
		String machine_code = null;
		int order_code = 0;
		affectedRows = dbController.getInstance().executeUpdate(
				"insert into ekurt.orders (client_id, Order_date, supply_date, supply_method, total_price, status) values('"
						+ order.getClient().getId() + "', '" + order.getDate() + "', '" + LocalDateTime.now() + "', '" + SupplyMethodEnum.LOCAL
						+ "'," + order.getTotalToPay() + ",'" + OrderStatusEnum.COMPLETE + "');");
		if (affectedRows == 0)
			obj.setResponse(Response.FAILED);
		ResultSet rs = dbController.getInstance().executeQuery(
				"SELECT MAX(ekurt.orders.order_code), machine_code FROM ekurt.orders,ekurt.machines WHERE  machine_name='"
						+ order.getMachineName() + "'; ");
		try {
			if (rs.next()) {
				machine_code = rs.getString("machine_code");
				order_code = rs.getInt("MAX(ekurt.orders.order_code)");
				affectedRows = insertIntoMachineOrder(order_code, machine_code);
				if (affectedRows != 1)
					obj.setResponse(Response.FAILED);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < order.getProducts().size(); i++) {
			String proName = order.getProducts().get(i).getName();

			rs = dbController.getInstance()
					.executeQuery("SELECT pro_code FROM ekurt.products WHERE pro_name='" + proName + "';");
			try {
				if (rs.next())
					order.getProducts().get(i).setProduct_code(rs.getString("pro_code"));
				productAndQuantity.put(order_code, order.getProducts().get(i));

				affectedRows = updateProStock(productAndQuantity, machine_code);
				if (affectedRows != 1)
					obj.setResponse(Response.FAILED);

				affectedRows = insertIntoProductsInOrder(productAndQuantity);
				if (affectedRows != 1)
					obj.setResponse(Response.FAILED);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (order.getClient().getStatus().equals(StatusEnum.FIRST_ORDER)) {
			affectedRows = upadteUserStatus(order.getClient().getId());
			if (affectedRows != 1)
				obj.setResponse(Response.FAILED);
		}
		if (order.getClient().getRole().equals(RoleEnum.SUBSCRIBER)) {
			affectedRows = dbController.getInstance()
					.executeUpdate("UPDATE ekurt.subscriberwallet SET balance = balance+" + order.getTotalToPay()
							+ "WHERE id='" + order.getClient().getId() + "';");
			if (affectedRows != 1)
				obj.setResponse(Response.FAILED);
		}
		obj.setResponse(Response.ORDER_PLACED_SUCCESSFULLY);

	}
 
	
	public static void placePickupOrder(Transaction obj) {
		PickUpOrder order = (PickUpOrder) obj.getData();
		int affectedRows;
		HashMap<Integer, Product> productAndQuantity = new HashMap<>();
		String machine_code = null;
		int order_code = 0;
		affectedRows = dbController.getInstance().executeUpdate(
				"insert into ekurt.orders (client_id, Order_date, supply_date, supply_method, total_price, status) values('"
						+ order.getClient().getId() + "', '" + order.getDate() + "', null, '" + SupplyMethodEnum.PICKUP
						+ "'," + order.getTotalToPay() + ",'" + OrderStatusEnum.WAIT_PICKUP + "');");
		if (affectedRows != 1)
			obj.setResponse(Response.FAILED);
		ResultSet rs = dbController.getInstance().executeQuery(
				"SELECT MAX(ekurt.orders.order_code), machine_code FROM ekurt.orders,ekurt.machines WHERE  machine_name='"
						+ order.getMachineName() + "'; ");
		try {
			if (rs.next()) {
				machine_code = rs.getString("machine_code");
				order_code = rs.getInt("MAX(ekurt.orders.order_code)");
				System.out.println(machine_code + " and " + order_code);
				affectedRows = insertIntoMachineOrder(order_code, machine_code);
				if (affectedRows != 1)
					obj.setResponse(Response.FAILED);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < order.getProducts().size(); i++) {
			String proName = order.getProducts().get(i).getName();

			rs = dbController.getInstance()
					.executeQuery("SELECT pro_code FROM ekurt.products WHERE pro_name='" + proName + "';");
			try {
				if (rs.next())
					order.getProducts().get(i).setProduct_code(rs.getString("pro_code"));
				productAndQuantity.put(order_code, order.getProducts().get(i));

				affectedRows = updateProStock(productAndQuantity, machine_code);
				if (affectedRows != 1)
					obj.setResponse(Response.FAILED);

				affectedRows = insertIntoProductsInOrder(productAndQuantity);
				if (affectedRows != 1)
					obj.setResponse(Response.FAILED);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (order.getClient().getStatus().equals(StatusEnum.FIRST_ORDER)) {
			affectedRows = upadteUserStatus(order.getClient().getId());
			if (affectedRows != 1)
				obj.setResponse(Response.FAILED);
		}
		if (order.getClient().getRole().equals(RoleEnum.SUBSCRIBER)) {
			affectedRows = dbController.getInstance()
					.executeUpdate("UPDATE ekurt.subscriberwallet SET balance = balance+" + order.getTotalToPay()
							+ "WHERE id='" + order.getClient().getId() + "';");
			if (affectedRows != 1)
				obj.setResponse(Response.FAILED);
		}

		Random rand = new Random();
		int pickupCode = 100000 + rand.nextInt(900000);

		affectedRows = insertIntoPickupCodes(pickupCode, order_code);
		if (affectedRows != 1)
			obj.setResponse(Response.FAILED);
		obj.setData(pickupCode);
		obj.setResponse(Response.ORDER_PLACED_SUCCESSFULLY);

	}
	
	public static void placeDeliveryOrder(Transaction obj) {
		DeliveryOrder order = (DeliveryOrder) obj.getData();
		int affectedRows;
		HashMap<Integer, Product> productAndQuantity = new HashMap<>();
		int order_code = 0;
		affectedRows = dbController.getInstance().executeUpdate(
				"insert into ekurt.orders (client_id, Order_date, supply_date, supply_method, total_price, status) values('"
						+ order.getClient().getId() + "', '" + order.getOrderDate() + "', null, '" + SupplyMethodEnum.DELIVERY
						+ "'," + order.getTotalToPay() + ",'" + OrderStatusEnum.WAIT_APPROVAL + "');");
		if (affectedRows != 1)
			obj.setResponse(Response.FAILED);
		ResultSet rs = dbController.getInstance().executeQuery(
				"SELECT MAX(ekurt.orders.order_code) FROM ekurt.orders;");
		try {
			if (rs.next()) {
				order_code = rs.getInt("MAX(ekurt.orders.order_code)");
				affectedRows = insertIntoDeliveryOrders(order_code, order.getClient().getRegion());
				if (affectedRows != 1)
					obj.setResponse(Response.FAILED);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < order.getProducts().size(); i++) {
			String proName = order.getProducts().get(i).getName();
			rs = dbController.getInstance()
					.executeQuery("SELECT pro_code FROM ekurt.products WHERE pro_name='" + proName + "';");
			try {
				if (rs.next())
					order.getProducts().get(i).setProduct_code(rs.getString("pro_code"));
				productAndQuantity.put(order_code, order.getProducts().get(i));

				affectedRows = updateProStockDelivery(productAndQuantity, order.getClient().getRegion());
				if (affectedRows != 1)
					obj.setResponse(Response.FAILED);

				affectedRows = insertIntoProductsInOrder(productAndQuantity);
				if (affectedRows != 1)
					obj.setResponse(Response.FAILED);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (order.getClient().getStatus().equals(StatusEnum.FIRST_ORDER)) {
			affectedRows = upadteUserStatus(order.getClient().getId());
			if (affectedRows != 1)
				obj.setResponse(Response.FAILED);
		}
		if (order.getClient().getRole().equals(RoleEnum.SUBSCRIBER)) {
			affectedRows = dbController.getInstance()
					.executeUpdate("UPDATE ekurt.subscriberwallet SET balance = balance+" + order.getTotalToPay()
							+ "WHERE id='" + order.getClient().getId() + "';");
			if (affectedRows != 1)
				obj.setResponse(Response.FAILED);
		}
		
		affectedRows = dbController.getInstance()
				.executeUpdate("INSERT INTO ekurt.addresses VALUES('" + order.getClient().getId() + "','"
						+ order.getCity() + "','" + order.getStreetName() + "'," + order.getHouseNum() + ");");		
		if (affectedRows != 1)
			obj.setResponse(Response.FAILED);
		
		//for simulating estimated delivery time
		int load=1;
		int distance = 1;
		int droneAvailability = 1;
		switch (order.getClient().getRegion()) {
		case NORTH:
			obj.setData(order.getOrderDate().plusDays(load+distance+droneAvailability));
			obj.setResponse(Response.ORDER_PLACED_SUCCESSFULLY);
			break;
		case SOUTH:
			obj.setData(order.getOrderDate().plusDays(load+distance+droneAvailability));
			obj.setResponse(Response.ORDER_PLACED_SUCCESSFULLY);
			break;
			
		case UAE:
			obj.setData(order.getOrderDate().plusDays(load+distance+droneAvailability));
			obj.setResponse(Response.ORDER_PLACED_SUCCESSFULLY);
			break;
		}
	}
	
	 public static int insertIntoPickupCodes(int pickupCode, int order_code) {
		 return dbController.getInstance().executeUpdate("INSERT INTO ekurt.pickupcodes VALUES("+ pickupCode +"," + order_code +")");
	}

	public static int upadteUserStatus(int id) {

		 return dbController.getInstance().executeUpdate("UPDATE ekurt.users SET `status` = 'DISCOUNT_APPLIED' WHERE(id = '" + id +"');");
	}

	public static int insertIntoProductsInOrder(HashMap<Integer,Product> productAndQuantity) {
		 int row=0;
		 for(Map.Entry<Integer,Product> current : productAndQuantity.entrySet()) {
		      return dbController.getInstance().executeUpdate("insert into ekurt.productsinorder values("+ current.getKey() +", '" + current.getValue().getProduct_code() +"',"+ current.getValue().getAmount()+","+ current.getValue().getFinalPrice() +");");
		 }
		 return row;
	}

	public static int insertIntoMachineOrder(int order_code,String machine_code) {
		 return dbController.getInstance().executeUpdate("INSERT INTO ekurt.machine_orders VALUES("+order_code+",'"+machine_code+"');");
	 }
	 
	 public static int updateProStock(HashMap<Integer,Product> map,String machine_code) {
		 int row=0;
		 for(Map.Entry<Integer,Product> current : map.entrySet()) {
		      return dbController.getInstance().executeUpdate("UPDATE ekurt.productinmachine SET stock=stock-"+current.getValue().getAmount()+" WHERE pro_code='"+current.getValue().getProduct_code()+"' AND machine_code='"+machine_code+"';");
		 }
		 return 0;
	 }
	 
		public static int insertIntoDeliveryOrders(int order_code, RegionEnum region) {
			 return dbController.getInstance().executeUpdate("INSERT INTO ekurt.delivery_orders VALUES("+order_code+",'"+region+"');");
		 }
		
		 public static int updateProStockDelivery(HashMap<Integer,Product> map, RegionEnum region) {
			 int row=0;
			 for(Map.Entry<Integer,Product> current : map.entrySet()) {
			      return dbController.getInstance().executeUpdate("UPDATE ekurt.productinwarehouse SET stock=stock-"+current.getValue().getAmount()+" WHERE pro_code='"+current.getValue().getProduct_code()+"' AND region='"+region+"';");
			 }
			 return 0;
		 }


}
