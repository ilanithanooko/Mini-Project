package logic;

public class OrderReport {
	private String deliveryMethod, orderDate, machineName, machineID, orderID, orderPrice;

	public OrderReport(String deliveryMethod, String orderDate, String machineName, String machineID, String orderID,
			String orderPrice) {
		super();
		this.deliveryMethod = deliveryMethod;
		this.orderDate = orderDate;
		this.machineName = machineName;
		this.machineID = machineID;
		this.orderID = orderID;
		this.orderPrice = orderPrice;
	}
	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getMachineID() {
		return machineID;
	}

	public void setMachineID(String machineID) {
		this.machineID = machineID;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	@Override
	public String toString() {
		return "OrderReport [deliveryMethod=" + deliveryMethod + ", orderDate=" + orderDate + ", machineName="
				+ machineName + ", machineID=" + machineID + ", orderID=" + orderID + ", orderPrice=" + orderPrice
				+ "]";
	}


}
