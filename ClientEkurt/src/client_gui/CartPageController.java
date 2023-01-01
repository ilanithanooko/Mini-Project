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
    private Button cancelBtn;

    @FXML
    private GridPane cartGridPane;

    @FXML
    private Button confirmBtn;

    @FXML
    private Label pageTitle;
    
    @FXML
    private Label totalToPayLable = new Label();

    @FXML
    void clickOnCancel(ActionEvent event) {
    	
    }
    
    @FXML
    void clickOnConfirm(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pageTitle.setText("Shopping Cart");
		int row = 1;
		try {
			for (int i = 0; i < ClientUtils.pickupOrderInProcess.getProducts().size(); i++) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/client_fxml/productInCart.fxml"));
				HBox box = fxmlLoader.load();
				ProductInCartController productInCartController = fxmlLoader.getController();
				productInCartController.setData(ClientUtils.pickupOrderInProcess.getProducts().get(i));

				cartGridPane.add(box, 0, row++);
				cartGridPane.setMargin(box, new Insets(1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		totalToPayLable.setText(String.format("%.2f", ClientUtils.pickupOrderInProcess.getTotalToPay()) + " ₪");
		
		
	}

}
