package logic;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import enums.OrderStatusEnum;
import enums.SupplyMethodEnum;

public class PickUpOrder {
	
	public PickUpOrder(User client, String machineName, LocalDate date,
						ArrayList<Product> products, OrderStatusEnum status) {
		this.client = client;
		this.machineName = machineName;
		this.date = date;
		this.products = products;
		this.status = status;
	}
	
	private User client;
	//private SupplyMethodEnum supplyMethod;
	//private Machine machine;
	private String machineName;
	private LocalDate date;
	private ArrayList<Product> products;
	private OrderStatusEnum status;
	
	public User getClient() {
		return client;
	}
	public void setClient(User client) {
		this.client = client;
	}
	public String getMachineName() {
		return machineName;
	}
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public ArrayList<Product> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	public OrderStatusEnum getStatus() {
		return status;
	}
	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}

}
