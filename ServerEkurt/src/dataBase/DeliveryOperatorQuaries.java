package dataBase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.Response;
import common.Transaction;
import enums.OrderStatus;
import enums.RegionEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.DeliveryOrder;
import logic.Offer;
import logic.Subscriber;

public class DeliveryOperatorQuaries {

	public static void getOrders(Transaction msg) {
		List<String> Alist = new ArrayList<>();
		if (msg instanceof Transaction) {
			Object obj = msg.getData();
			RegionEnum region = RegionEnum.valueOf(obj.toString());
			OrderStatus supplyMethod = OrderStatus.valueOf(obj.toString());
			try {
				ResultSet rs = dbController.getInstance().executeQuery(
						"SELECT order_code, client_id, order_date, supply_date, total_price,order_status FROM ekurt.orders WHERE region='"
								+ region.toString() + "' AND supply_method='" + supplyMethod.toString() + "'");
				System.out.println("Number of rows: " + rs.getRow());
				if (!(rs.next())) {
					msg.setResponse(Response.FAILED_TO_GET_ORDERS);
					return;
				}
				rs.previous();
				List<DeliveryOrder> orders = new ArrayList<>();
				while (rs.next()) {
					String orderId = rs.getString("order_code");
					String clientId = rs.getString("client_id");
					Date OrderDate = rs.getTimestamp("order_date");
					Date supplyDate = rs.getTimestamp("supply_date");
					float price = rs.getFloat("total_price");
					String enumValue = rs.getString("total_price");
					OrderStatus suppMethod = OrderStatus.valueOf(enumValue);
					DeliveryOrder order = new DeliveryOrder(orderId, clientId, OrderDate, supplyDate, price,suppMethod);
					orders.add(order);
				}
				msg.setResponse(Response.FOUND_ORDERS);
				msg.setData(orders);
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				msg.setResponse(Response.FAILED_TO_GET_ORDERS);
				return;
			}
		} else
			msg.setResponse(Response.FAILED_TO_GET_ORDERS);
	}
}
