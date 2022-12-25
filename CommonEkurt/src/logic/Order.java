package logic;

import java.sql.Date;
import java.util.ArrayList;

import enums.OrderStatusEnum;
import enums.SupplyMethodEnum;

public class Order {
	private User client;
	private SupplyMethodEnum supplyMethod;
	private Date date;
	private ArrayList<Product> products;
	private OrderStatusEnum status;
}
