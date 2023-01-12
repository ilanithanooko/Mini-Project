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
	private int stockFromDb;
	private float price_after_discount;
	private boolean is_in_sale;
	private String offerName;
	
	public int getStockFromDb() {
		return stockFromDb;
	}
	public void setStockFromDb(int stockFromDb) {
		this.stockFromDb = stockFromDb;
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
	public void setPrice_after_discount(String name) {
		float num=0;
		float present=0;
		if(name.contains("%")) {
			num=Float.valueOf(offerName.replace("%",""));
			present=(1-(num/100));
			price_after_discount=price*present;
		}
	}
	public float getPrice_after_discount() {
        return price_after_discount;
	}

	public float getPrice() {
		return price;
	}
	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
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
	
    public boolean isIs_in_sale() {
		return is_in_sale;
	}
	public void setIs_in_sale(boolean is_in_sale) {
		this.is_in_sale = is_in_sale;
	}

	
	public static ProductInGrid getProductFromResultSet(ResultSet rs, int stock, boolean is_in_sale, String offerName){
        //ProductInGrid> products = new ArrayList<>();
	
        try{
            while(rs.next()){
            	ProductInGrid product = new ProductInGrid(rs.getString("pro_code"),
                						rs.getString("pro_name"),
                						rs.getFloat("price"),
                						CategoriesEnum.valueOf(rs.getString("category")),
                						rs.getString("image"));
            	product.setStockFromDb(stock);
            	product.setIs_in_sale(is_in_sale);
            	if(offerName != null) {
                	product.setOfferName(offerName);
                	product.setPrice_after_discount(offerName);
            	}
            	return product;

            	}
            } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
