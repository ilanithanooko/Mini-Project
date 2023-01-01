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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Label productPrice;
    
    private int stock;
    private float price;
    
   private int desiredAmount;

    public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@FXML
    void addProductToCart(ActionEvent event) {
		ClientUtils.pickupOrderInProcess.addProduct(new Product(productName.getText(),
				price, desiredAmount));
		stock -= desiredAmount;
		
		
		
    }

    @FXML
    void decreaseAmountByOne(ActionEvent event) {
    	desiredAmount = Integer.parseInt(productAmount.getText())- 1;
    	if(desiredAmount == 0) {
    		decreaseAmount.setDisable(true);
    		productAmount.setText(String.valueOf(desiredAmount));
    	}else {
    		if(Integer.parseInt(productAmount.getText()) == stock) {
    			increaseAmount.setDisable(false);
    		}
    		productAmount.setText(String.valueOf(desiredAmount));
    	}
    }

    @FXML
    void increaseAmountByOne(ActionEvent event) {
    	desiredAmount = Integer.parseInt(productAmount.getText())+1;
    	if(desiredAmount > stock) {
    		increaseAmount.setDisable(true);
    	}else {
    		productAmount.setText(String.valueOf(desiredAmount));
    		decreaseAmount.setDisable(false);
    		if(desiredAmount == stock)
        		increaseAmount.setDisable(true);
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
    	List<String> list = new ArrayList<>();
    	list.add(ClientUtils.pickupOrderInProcess.getMachineName());
    	list.add(productName.getText());
    	Transaction msg = new Transaction(Action.GET_CUR_STOCK, list);
    	ClientUI.chat.accept(msg);
		msg = ClientUI.chat.getObj();
    	int x = 0;
    	if (msg.getData() instanceof Integer) {
    	  x = (int) msg.getData();
    	} else {
    	System.out.println("Not Integer");	
    	}
    	setStock(x);
    	//saleLable.setVisible(false);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	
	}

}
