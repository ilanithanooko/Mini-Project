package client_gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.ProductInGrid;

public class ProductInGridController {

    @FXML
    private Button addToCart;

    @FXML
    private Button decreaseAmount;

    @FXML
    private Button increaseAmount;

    @FXML
    private TextField productAmount;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;

    @FXML
    private Label productPrice;

    @FXML
    void addProductToCart(ActionEvent event) {

    }

    @FXML
    void decreaseAmountByOne(ActionEvent event) {

    }

    @FXML
    void increaseAmountByOne(ActionEvent event) {

    }
 
    public void setData(ProductInGrid product) {
    	Image image = new Image(getClass().getResourceAsStream(product.getImage()));
    	productImage.setImage(image);
    	productName.setText(product.getPro_name());
    	productPrice.setText(String.valueOf(product.getPrice()) + " ₪");
    	//saleLable.setVisible(false);
    }

}
