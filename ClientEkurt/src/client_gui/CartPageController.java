package client_gui;

import java.net.URL;
import java.util.ResourceBundle;

import Utils.generalMethods;
import clientUtil.ClientUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CartPageController implements Initializable{
	
	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/CartPage.fxml",
				"Shopping Cart");
	}

    @FXML
    private Button clearBtn;

    @FXML
    private GridPane cartGridPane;

    public GridPane getCartGridPane() {
		return cartGridPane;
	}

	public void setCartGridPane(GridPane cartGridPane) {
		this.cartGridPane = cartGridPane;
	}

	@FXML
    private Button confirmBtn;

    @FXML
    private Label pageTitle;
    
    @FXML
    private Label totalToPayLable = new Label();

    @FXML
    void clickOnClearBtn(ActionEvent event) {
    	if(ClientUtils.localOrderInProcess != null) {
        	for(int i=ClientUtils.localOrderInProcess.getProducts().size()-1; i>= 0  ; i--) {
        		ClientUtils.localOrderInProcess.getProducts().remove(i);
        	}
        	ClientUtils.localOrderInProcess.setTotalToPay(0);
        	((Node) event.getSource()).getScene().getWindow().hide();
        	new CartPageController().start(new Stage());
    	}else if(ClientUtils.pickupOrderInProcess != null) {
        	for(int i=ClientUtils.pickupOrderInProcess.getProducts().size()-1; i>= 0  ; i--) {
        		ClientUtils.pickupOrderInProcess.getProducts().remove(i);
        	}
        	ClientUtils.pickupOrderInProcess.setTotalToPay(0);
        	((Node) event.getSource()).getScene().getWindow().hide();
        	new CartPageController().start(new Stage());
    	}else {
        	for(int i=ClientUtils.deliveryOrderInProcess.getProducts().size()-1; i>= 0  ; i--) {
        		ClientUtils.deliveryOrderInProcess.getProducts().remove(i);
        	}
        	ClientUtils.deliveryOrderInProcess.setTotalToPay(0);
        	((Node) event.getSource()).getScene().getWindow().hide();
        	new CartPageController().start(new Stage());
    	}

    }
    
    @FXML
    void clickOnConfirm(ActionEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	switch (ClientUtils.currUser.getRole()) {
		case CUSTOMER: {
	    	new PaymentPageController().start(new Stage());
			break;
		}
		case SUBSCRIBER: {
			new WalletConfirmController().start(new Stage());
			break;
		}
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(ClientUtils.cartFlag)
			pageTitle.setText("Shopping Cart");
		else {
			pageTitle.setText("Order Summary");
		}
		int row = 1;
		if(ClientUtils.localOrderInProcess != null) {
		if(ClientUtils.localOrderInProcess.getProducts().size() == 0) {
			clearBtn.setVisible(false);
			confirmBtn.setVisible(false);
		}
		try {
			for (int i = 0; i < ClientUtils.localOrderInProcess.getProducts().size(); i++) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/client_fxml/productInCart.fxml"));
				HBox box = fxmlLoader.load();
				ProductInCartController productInCartController = fxmlLoader.getController();
				productInCartController.setData(ClientUtils.localOrderInProcess.getProducts().get(i));
				cartGridPane.add(box, 0, row++);
				cartGridPane.setMargin(box, new Insets(1));
				ClientUtils.cartProducts.add(box);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ProductInCartController.cartGridPane = cartGridPane;
		totalToPayLable.setText(String.format("%.2f", ClientUtils.localOrderInProcess.getTotalToPay()) + " ₪");	
		
		}else if(ClientUtils.pickupOrderInProcess != null) {
		if(ClientUtils.pickupOrderInProcess.getProducts().size() == 0) {
			clearBtn.setVisible(false);
			confirmBtn.setVisible(false);
		}
		try {
			for (int i = 0; i < ClientUtils.pickupOrderInProcess.getProducts().size(); i++) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/client_fxml/productInCart.fxml"));
				HBox box = fxmlLoader.load();
				ProductInCartController productInCartController = fxmlLoader.getController();
				productInCartController.setData(ClientUtils.pickupOrderInProcess.getProducts().get(i));
				cartGridPane.add(box, 0, row++);
				cartGridPane.setMargin(box, new Insets(1));
				ClientUtils.cartProducts.add(box);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ProductInCartController.cartGridPane = cartGridPane;
		totalToPayLable.setText(String.format("%.2f", ClientUtils.pickupOrderInProcess.getTotalToPay()) + " ₪");	
		
		}else {
			if(ClientUtils.deliveryOrderInProcess.getProducts().size() == 0) {
				clearBtn.setVisible(false);
				confirmBtn.setVisible(false);
			}
			try {
				for (int i = 0; i < ClientUtils.deliveryOrderInProcess.getProducts().size(); i++) {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/client_fxml/productInCart.fxml"));
					HBox box = fxmlLoader.load();
					ProductInCartController productInCartController = fxmlLoader.getController();
					productInCartController.setData(ClientUtils.deliveryOrderInProcess.getProducts().get(i));
					cartGridPane.add(box, 0, row++);
					cartGridPane.setMargin(box, new Insets(1));
					ClientUtils.cartProducts.add(box);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ProductInCartController.cartGridPane = cartGridPane;
			totalToPayLable.setText(String.format("%.2f", ClientUtils.deliveryOrderInProcess.getTotalToPay()) + " ₪");	
		}
		
	}

}
