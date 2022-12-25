package logic;

public class Product {
	private String name;
	private int price;
	private int product_code;
	private int amount;

	public Product(String name, int price, int product_code, int amount) {
		super();
		this.name = name;
		this.price = price;
		this.product_code = product_code;
		this.amount = amount;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getProduct_code() {
		return product_code;
	}

	public void setProduct_code(int product_code) {
		this.product_code = product_code;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
