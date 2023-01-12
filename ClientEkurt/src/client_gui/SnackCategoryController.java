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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SnackCategoryController implements Initializable {

    @FXML
    private ImageView backBtn;

    @FXML
    private Button checkoutBtn;

    @FXML
    private GridPane gridPane;
    @FXML
    private ImageView viewCartBtn;
    
	public void start (Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/SnackCategory.fxml", "Snacks");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int columns = 0;
		int row = 1;
		
		try {
			if(ClientUtils.localOrderInProcess != null) {
			for(int i=0 ; i<ClientUtils.localOrderInProcess.getSnackCategoryProducts().size();i++) {
				VBox box;
				if(ClientUtils.localOrderInProcess.getSnackCategoryProducts().get(i).getStockFromDb() > 0) {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/client_fxml/ProductInGrid.fxml"));
					box = fxmlLoader.load();
					ProductInGridController productInGridController = fxmlLoader.getController();
					productInGridController.setData(ClientUtils.localOrderInProcess.getSnackCategoryProducts().get(i));
				}
				else {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/client_fxml/ProductNotInStock.fxml"));
					box = fxmlLoader.load();
					ProductInGridController productInGridController = fxmlLoader.getController();
					productInGridController.setData(ClientUtils.localOrderInProcess.getSnackCategoryProducts().get(i));
				}
				if(columns == 4) {
					columns = 0;
					++row;
				}
				gridPane.add(box, columns++, row);
				gridPane.setMargin(box, new Insets(8));
				ClientUtils.categoryProducts.add(box);
			}
			}else if(ClientUtils.pickupOrderInProcess != null) {
			for(int i=0 ; i<ClientUtils.pickupOrderInProcess.getSnackCategoryProducts().size();i++) {
				VBox box;
				if(ClientUtils.pickupOrderInProcess.getSnackCategoryProducts().get(i).getStockFromDb() > 0) {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/client_fxml/ProductInGrid.fxml"));
					box = fxmlLoader.load();
					ProductInGridController productInGridController = fxmlLoader.getController();
					productInGridController.setData(ClientUtils.pickupOrderInProcess.getSnackCategoryProducts().get(i));
				}
				else {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/client_fxml/ProductNotInStock.fxml"));
					box = fxmlLoader.load();
					ProductInGridController productInGridController = fxmlLoader.getController();
					productInGridController.setData(ClientUtils.pickupOrderInProcess.getSnackCategoryProducts().get(i));
				}
				if(columns == 4) {
					columns = 0;
					++row;
				}
				gridPane.add(box, columns++, row);
				gridPane.setMargin(box, new Insets(8));
				ClientUtils.categoryProducts.add(box);
			}
			}else {
				for(int i=0 ; i<ClientUtils.deliveryOrderInProcess.getSnackCategoryProducts().size();i++) {
					VBox box;
					if(ClientUtils.deliveryOrderInProcess.getSnackCategoryProducts().get(i).getStockFromDb() > 0) {
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(getClass().getResource("/client_fxml/ProductInGrid.fxml"));
						box = fxmlLoader.load();
						ProductInGridController productInGridController = fxmlLoader.getController();
						productInGridController.setData(ClientUtils.deliveryOrderInProcess.getSnackCategoryProducts().get(i));
					}
					else {
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(getClass().getResource("/client_fxml/ProductNotInStock.fxml"));
						box = fxmlLoader.load();
						ProductInGridController productInGridController = fxmlLoader.getController();
						productInGridController.setData(ClientUtils.deliveryOrderInProcess.getSnackCategoryProducts().get(i));
					}
					if(columns == 4) {
						columns = 0;
						++row;
					}
					gridPane.add(box, columns++, row);
					gridPane.setMargin(box, new Insets(8));
					ClientUtils.categoryProducts.add(box);
			}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

    @FXML
    void clickOnBackButton(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	switch (ClientUtils.currUser.getRole()) {
		case CUSTOMER: {
	    	new CustomerCategoriesController().start(new Stage());
			break;
		}
		case SUBSCRIBER: {
			new SubscriberCategoriesPageController().start(new Stage());
			break;
		}
    	}	
    }

    @FXML
    void clickOnCheckout(ActionEvent event) {
    	ClientUtils.cartFlag = false;
    	new CartPageController().start(new Stage());
    }

    @FXML
    void clickOnViewCart(MouseEvent event) {
    	ClientUtils.cartFlag = true;
    	new CartPageController().start(new Stage());
    }
}
