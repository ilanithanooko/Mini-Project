package client_gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import client.ClientUI;
import clientUtil.ClientUtils;
import common.Action;
import common.Transaction;
import enums.RoleEnum;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import logic.Product;
import logic.ProductInGrid;

public class ProductInGridController implements Initializable {

    @FXML
    private Button addToCart;

    @FXML
    private Button decreaseAmount = new Button();

    @FXML
    private Button increaseAmount = new Button();

    @FXML
    private TextField productAmount = new TextField();

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;
    
    @FXML
    private Label saleLabel = new Label();

	@FXML
    private Label productPrice;
    private int currentStock;
    private float price;
    
   private int desiredAmount;

    public int getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(int stock) {
		this.currentStock = stock;
	}

	@FXML
    void addProductToCart(ActionEvent event) {
		if(ClientUtils.localOrderInProcess != null) {
			ClientUtils.localOrderInProcess.addProduct(new Product(productName.getText(),
					price, desiredAmount));
			currentStock -= desiredAmount;
			productAmount.setText("0");
			if(productAmount.getText().equals("0"))
				decreaseAmount.setDisable(true);
		}else if(ClientUtils.pickupOrderInProcess != null) {
			ClientUtils.pickupOrderInProcess.addProduct(new Product(productName.getText(),
					price, desiredAmount));
			currentStock -= desiredAmount;
			productAmount.setText("0");
			if(productAmount.getText().equals("0"))
				decreaseAmount.setDisable(true);
		}else {
			ClientUtils.deliveryOrderInProcess.addProduct(new Product(productName.getText(),
					price, desiredAmount));
			currentStock -= desiredAmount;
			productAmount.setText("0");
			if(productAmount.getText().equals("0"))
				decreaseAmount.setDisable(true);
		}
    }

    @FXML
    void decreaseAmountByOne(ActionEvent event) {
    	desiredAmount = Integer.parseInt(productAmount.getText())- 1;
    	if(desiredAmount == 0) {
    		decreaseAmount.setDisable(true);
    		productAmount.setText(String.valueOf(desiredAmount));
    	}else {
    		if(Integer.parseInt(productAmount.getText()) == currentStock) {
    			increaseAmount.setDisable(false);
    		}
    		productAmount.setText(String.valueOf(desiredAmount));
    	}
		if(productAmount.getText().equals("0")) {
			decreaseAmount.setDisable(true);
		}
    }

    @FXML
    void increaseAmountByOne(ActionEvent event) {
    	desiredAmount = Integer.parseInt(productAmount.getText())+1;
    	if(desiredAmount > currentStock) {
    		increaseAmount.setDisable(true);
    	}else {
    		productAmount.setText(String.valueOf(desiredAmount));
    		decreaseAmount.setDisable(false);
    		if(desiredAmount == currentStock)
        		increaseAmount.setDisable(true);
    	}
		if(productAmount.getText().equals("0")) {
			decreaseAmount.setDisable(true);
		}
    	//check 
    }
 
	public void setData(ProductInGrid product) {
		Image image = new Image(getClass().getResourceAsStream(product.getImage()));
		productImage.setImage(image);
		productName.setText(product.getPro_name());
		productAmount.setText("0");
		price = product.getPrice();
		productPrice.setText(String.valueOf(product.getPrice()) + " ₪");
		decreaseAmount.setDisable(true);
		setCurrentStock(product.getStockFromDb());
		if (ClientUtils.currUser.getRole().equals(RoleEnum.SUBSCRIBER)) {
			if (product.isIs_in_sale()) {
				saleLabel.setVisible(true);
				saleLabel.setText(product.getOfferName());
				Text originalPriceText = new Text(String.valueOf(product.getPrice()));
				originalPriceText.setStrikethrough(true);
				price = product.getPrice_after_discount();
				productPrice.setText(originalPriceText.getText() + " ₪ "
						+ String.format("%.2f", product.getPrice_after_discount()) + " ₪");
			} else {
				saleLabel.setVisible(false);
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	saleLabel.setVisible(false);
	}

}
