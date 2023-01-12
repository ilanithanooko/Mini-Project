package logic;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import enums.CategoriesEnum;
import enums.RegionEnum;

public class ProductInGrid implements Serializable {
	
	@Override
	public String toString() {
		return "ProductInGrid [pro_code=" + pro_code + ", pro_name=" + pro_name + ", price=" + price + ", category="
				+ category + ", image=" + image + "]";
	}
	public ProductInGrid(String pro_code, String pro_name, float price, CategoriesEnum category, String image) {
		this.pro_code = pro_code;
		this.pro_name = pro_name;
		this.price = price;
		this.category = category;
		this.image = image;
	}

	private String pro_code;
	private String pro_name;
	private float price;
	private CategoriesEnum category;
	private String image;
	boolean available;
	
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public String getPro_code() {
		return pro_code;
	}
	public void setPro_code(String pro_code) {
		this.pro_code = pro_code;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public CategoriesEnum getCategory() {
		return category;
	}
	public void setCategory(CategoriesEnum category) {
		this.category = category;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
    public static ProductInGrid getProductFromResultSet(ResultSet rs){
        //ProductInGrid> products = new ArrayList<>();
        try{
            while(rs.next()){
            	ProductInGrid product = new ProductInGrid(rs.getString("pro_code"),
                						rs.getString("pro_name"),
                						rs.getFloat("price"),
                						CategoriesEnum.valueOf(rs.getString("category")),
                						rs.getString("image"));
                return product;

            	}
            } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
