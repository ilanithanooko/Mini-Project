package client_gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.ClientUI;
import clientUtil.ClientUtils;
import common.Action;
import common.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logic.Product;
import logic.ProductInCart;
import logic.ProductInGrid;

public class ProductInCartController implements Initializable{

    @FXML
    private Label finalPrice;

    @FXML
    private Label productName;

    @FXML
    private Label productPrice;

    @FXML
    private Label productsQuantity;
    
    @FXML
    private ImageView removeProductBtn;

    @FXML
    void clickOnTrashbin(MouseEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
    public void setData(Product product) {
    	productName.setText(product.getName());
    	productsQuantity.setText("x" + String.valueOf(product.getAmount()));
    	productPrice.setText(String.valueOf(product.getPrice()) + " ₪");
    	finalPrice.setText(String.format("%.2f", product.getFinalPrice()) + " ₪");
    	
    	
    }

}
