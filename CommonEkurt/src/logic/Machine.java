package logic;

public class Machine {
	
	private String machine_code, machine_name, street,city;
	private int street_num;
	
	public Machine(String machine_code, String machine_name, String street, String city, int street_num) {
		super();
		this.machine_code = machine_code;
		this.machine_name = machine_name;
		this.street = street;
		this.city = city;
		this.street_num = street_num;
	}
	
	@Override
	public String toString() {
		return "Machine [machine_code=" + machine_code + ", machine_name=" + machine_name + ", street=" + street
				+ ", city=" + city + ", street_num=" + street_num + "]";
	}
	public String getMachine_code() {
		return machine_code;
	}
	public void setMachine_code(String machine_code) {
		this.machine_code = machine_code;
	}
	public String getMachine_name() {
		return machine_name;
	}
	public void setMachine_name(String machine_name) {
		this.machine_name = machine_name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getStreet_num() {
		return street_num;
	}
	public void setStreet_num(int street_num) {
		this.street_num = street_num;
	}

}
