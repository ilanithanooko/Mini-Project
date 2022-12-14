package logic;


import java.util.Date;

import enums.OrderStatus;
import enums.RegionEnum;
import enums.RoleEnum;
import enums.StatusEnum;

public class DeliveryOrder {
	private String OrderId;
    private String ClientId;
    private Date OrderDate;
    private Date SuppDate;
    private float Price;
    private OrderStatus status;
    private RegionEnum region;
    
    public DeliveryOrder(String orderId2, String clientId2, Date orderDate2, Date supplyDate, float price2, OrderStatus suppMethod) {
		this.OrderId=orderId2;
		this.ClientId=clientId2;
		this.OrderDate=orderDate2;
		this.SuppDate=supplyDate;
		this.Price=price2;
		this.status = suppMethod;
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
	public Date getOrderDate() {
		return OrderDate;
	}
	public void setOrderDate(Date orderDate) {
		OrderDate = orderDate;
	}
	public Date getSuppDate() {
		return SuppDate;
	}
	public void setSuppDate(Date suppDate) {
		SuppDate = suppDate;
	}
	public float getPrice() {
		return Price;
	}
	public void setPrice(float price) {
		Price = price;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public RegionEnum getRegion() {
		return region;
	}
	public void setRegion(RegionEnum region) {
		this.region = region;
	}
}
