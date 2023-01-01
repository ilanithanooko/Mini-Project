package logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import enums.RegionEnum;

public class Product{
	@Override
	public String toString() {
		return "Product [name=" + name + ", price=" + price + ", amount=" + amount + "]";
	}

	private String name;
	private float price;
	//private int product_code;
	private int amount;
	private float finalPrice;

	public float getFinalPrice() {
		return finalPrice;
	}

	public Product(String name, float price, int amount) {
		super();
		this.name = name;
		this.price = price;
		//this.product_code = product_code;
		this.amount = amount;
		finalPrice = this.price * this.amount;
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

//	public int getProduct_code() {
//		return product_code;
//	}
//
//	public void setProduct_code(int product_code) {
//		this.product_code = product_code;
//	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
