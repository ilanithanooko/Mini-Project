package logic;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import enums.OrderStatusEnum;
import enums.SupplyMethodEnum;

public class PickUpOrder {
	
	@Override
	public String toString() {
		return "PickUpOrder [client=" + client + ", machineName=" + machineName + ", date=" + date + ", products="
				+ products + ", status=" + status + "]";
	}

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
	private float totalToPay = 0;
	
	public float getTotalToPay() {
		return totalToPay;
	}

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
	
	public void addProduct(Product product) {
		int flag = 0;
		for (Product index : products) {
			if (index.getName().equals(product.getName())) {
				index.setAmount(index.getAmount() + product.getAmount());
				totalToPay += (product.getPrice() * product.getAmount());
				flag = 1;
			}
		}
		if (flag == 0) {
			products.add(product);
			totalToPay += (product.getPrice() * product.getAmount());
		}
	}
	
	
}
