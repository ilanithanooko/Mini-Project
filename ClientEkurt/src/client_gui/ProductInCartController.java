package client_gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.ClientUI;
import clientUtil.ClientUtils;
import common.Action;
import common.Transaction;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logic.Product;
import logic.ProductInCart;
import logic.ProductInGrid;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
    
    public static GridPane cartGridPane;
    

	@FXML
	void clickOnTrashbin(MouseEvent event) {
		int i = 0;
		if(ClientUtils.localOrderInProcess != null) {
		for (i = 0; i < ClientUtils.localOrderInProcess.getProducts().size(); i++) {
			if (ClientUtils.localOrderInProcess.getProducts().get(i).getName().equals(productName.getText())) {
				break;
			}
		}
		ClientUtils.localOrderInProcess.setTotalToPay(ClientUtils.localOrderInProcess.getTotalToPay()
				- ClientUtils.localOrderInProcess.getProducts().get(i).getFinalPrice());
		new ProductInGridController().setCurrentStock(new ProductInGridController().getCurrentStock()
				+ ClientUtils.localOrderInProcess.getProducts().get(i).getAmount());
		ClientUtils.localOrderInProcess.getProducts().remove(i);
		((Node) event.getSource()).getScene().getWindow().hide();
		new CartPageController().start(new Stage());
		
		}else if(ClientUtils.pickupOrderInProcess != null) {
		for (i = 0; i < ClientUtils.pickupOrderInProcess.getProducts().size(); i++) {
			if (ClientUtils.pickupOrderInProcess.getProducts().get(i).getName().equals(productName.getText())) {
				break;
			}
		}
		ClientUtils.pickupOrderInProcess.setTotalToPay(ClientUtils.pickupOrderInProcess.getTotalToPay()
				- ClientUtils.pickupOrderInProcess.getProducts().get(i).getFinalPrice());
		new ProductInGridController().setCurrentStock(new ProductInGridController().getCurrentStock()
				+ ClientUtils.pickupOrderInProcess.getProducts().get(i).getAmount());
		ClientUtils.pickupOrderInProcess.getProducts().remove(i);
		((Node) event.getSource()).getScene().getWindow().hide();
		new CartPageController().start(new Stage());
		}else {
			for (i = 0; i < ClientUtils.deliveryOrderInProcess.getProducts().size(); i++) {
				if (ClientUtils.deliveryOrderInProcess.getProducts().get(i).getName().equals(productName.getText())) {
					break;
				}
			}
			ClientUtils.deliveryOrderInProcess.setTotalToPay(ClientUtils.deliveryOrderInProcess.getTotalToPay()
					- ClientUtils.deliveryOrderInProcess.getProducts().get(i).getFinalPrice());
			new ProductInGridController().setCurrentStock(new ProductInGridController().getCurrentStock()
					+ ClientUtils.deliveryOrderInProcess.getProducts().get(i).getAmount());
			ClientUtils.deliveryOrderInProcess.getProducts().remove(i);
			((Node) event.getSource()).getScene().getWindow().hide();
			new CartPageController().start(new Stage());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//cartGridPane = new CartPageController().getCartGridPane();
	}
	
    public void setData(Product product) {
    	productName.setText(product.getName());
    	productsQuantity.setText("x" + String.valueOf(product.getAmount()));
    	productPrice.setText(String.valueOf(product.getPrice()) + " ₪");
    	finalPrice.setText(String.format("%.2f", product.getPrice()*product.getAmount()) + " ₪");
    	
    	
    }

}
