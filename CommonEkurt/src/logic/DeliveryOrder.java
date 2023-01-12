package logic;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enums.OrderStatusEnum;
import enums.RegionEnum;
import enums.RoleEnum;
import enums.StatusEnum;

public class DeliveryOrder implements Serializable {
	private String OrderId;
    private String ClientId; //need to be user
    private User client;
	private LocalDate OrderDate;
    private LocalDate SuppDate;
	private ArrayList<Product> products;
    private float Price;
    private OrderStatusEnum status;
    private RegionEnum region;
    private String streetName;
    private String city;
    private int houseNum;
	private List<ProductInGrid> ProductsForDisplay;
	private List<ProductInGrid> foodCategoryProducts;
	private List<ProductInGrid> snackCategoryProducts;
	private List<ProductInGrid> sweetsCategoryProducts;
	private List<ProductInGrid> drinksCategoryProducts;
	private float totalToPay = 0;
	private LocalDate estimatedDelivery;
	
	
	
	public float getTotalToPay() {
		return totalToPay;
	}
	public void setTotalToPay(float totalToPay) {
		this.totalToPay = totalToPay;
	}
	private int productquantity = 0;

    
	public DeliveryOrder(String orderId2, String clientId2, LocalDate orderDate2, LocalDate supplyDate, float price2, OrderStatusEnum suppMethod) {
		this.OrderId=orderId2;
		this.ClientId=clientId2;
		this.OrderDate=orderDate2;
		this.SuppDate=supplyDate;
		this.Price=price2;
		this.status = suppMethod;
	}
	public DeliveryOrder(User client, LocalDate OrderDate, ArrayList<Product> products, OrderStatusEnum status) {
		this.client = client;
		this.OrderDate = OrderDate;
		this.products = products;
		this.status = status;
		ProductsForDisplay = new ArrayList<>();
		foodCategoryProducts = new ArrayList<>();
		snackCategoryProducts = new ArrayList<>();
		sweetsCategoryProducts = new ArrayList<>();
		drinksCategoryProducts = new ArrayList<>();
	}
	
    public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getClientId() {
		return ClientId;
	}
	public void setClientId(String clientId) {
		ClientId = clientId;
	}
	public LocalDate getOrderDate() {
		return OrderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		OrderDate = orderDate;
	}
	public LocalDate getSuppDate() {
		return SuppDate;
	}
	public void setSuppDate(LocalDate suppDate) {
		SuppDate = suppDate;
	}
	public float getPrice() {
		return Price;
	}
	public void setPrice(float price) {
		Price = price;
	}
	public OrderStatusEnum getStatus() {
		return status;
	}
	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}
	public RegionEnum getRegion() {
		return region;
	}
	public void setRegion(RegionEnum region) {
		this.region = region;
	}
    public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getHouseNum() {
		return houseNum;
	}
	public void setHouseNum(int houseNum) {
		this.houseNum = houseNum;
	}
	public ArrayList<Product> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	public List<ProductInGrid> getProductsForDisplay() {
		return ProductsForDisplay;
	}
	public void setProductsForDisplay(List<ProductInGrid> productsForDisplay) {
		ProductsForDisplay = productsForDisplay;
	}
	public List<ProductInGrid> getFoodCategoryProducts() {
		return foodCategoryProducts;
	}
	public void setFoodCategoryProducts(List<ProductInGrid> foodCategoryProducts) {
		this.foodCategoryProducts = foodCategoryProducts;
	}
	public List<ProductInGrid> getSnackCategoryProducts() {
		return snackCategoryProducts;
	}
	public void setSnackCategoryProducts(List<ProductInGrid> snackCategoryProducts) {
		this.snackCategoryProducts = snackCategoryProducts;
	}
	public List<ProductInGrid> getSweetsCategoryProducts() {
		return sweetsCategoryProducts;
	}
	public void setSweetsCategoryProducts(List<ProductInGrid> sweetsCategoryProducts) {
		this.sweetsCategoryProducts = sweetsCategoryProducts;
	}
	public List<ProductInGrid> getDrinksCategoryProducts() {
		return drinksCategoryProducts;
	}
	public void setDrinksCategoryProducts(List<ProductInGrid> drinksCategoryProducts) {
		this.drinksCategoryProducts = drinksCategoryProducts;
	}
	public LocalDate getEstimatedDelivery() {
		return estimatedDelivery;
	}
	public void setEstimatedDelivery(LocalDate estimatedDelivery) {
		this.estimatedDelivery = estimatedDelivery;
	}
	public void addProduct(Product product) {
		int flag = 0;
		for (Product index : products) {
			if (index.getName().equals(product.getName())) {
				index.setAmount(index.getAmount() + product.getAmount());
				totalToPay  += (product.getPrice() * product.getAmount());
				flag = 1;
			}
		}
		if (flag == 0) {
			products.add(product);
			totalToPay += (product.getPrice() * product.getAmount());
		}
		
		productquantity += product.getAmount();

	}
}
