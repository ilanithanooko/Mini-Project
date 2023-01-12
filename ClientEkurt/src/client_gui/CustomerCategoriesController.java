package client_gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Utils.generalMethods;
import clientUtil.ClientUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.ProductInGrid;

public class CustomerCategoriesController implements Initializable {
    @FXML
    private ImageView backBtn;
    @FXML
    private ImageView cartBtn;
    @FXML
    private ImageView drinksCategory;
    @FXML
    private ImageView foodCategory;
    @FXML
    private ImageView snackCategory;
    @FXML
    private ImageView sweetsCategory;
    @FXML
    private Label machineName = new Label();

	public void start (Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/CustomerCategoriesPage.fxml", "Categories");
	}
    
    @FXML
    void clickOnBackButton(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	if(ClientUtils.localOrderInProcess != null) {
    		LoginController.logout(ClientUtils.currUser.getUsername());
    		System.exit(1);
    	}
		if(ClientUtils.pickupOrderInProcess != null) {
			new ChooseMachinePickupController().start(new Stage());
		}else {
			new EnterDeliAddController().start(new Stage());
		}
    }
    @FXML
    void clickOnViewCart(MouseEvent event) {
    	ClientUtils.cartFlag = true;
    	new CartPageController().start(new Stage());
    }

    @FXML
    void clickOnDrinksCategory(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	new DrinksCategoryController().start(new Stage());
    }

    @FXML
    void clickOnFoodCategory(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	new FoodCategoryController().start(new Stage()); 
    }

    @FXML
    void clickOnSnackCategory(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	new SnackCategoryController().start(new Stage()); 
    }

    @FXML
    void clickOnSweetsCategory(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	new SweetsCategoryController().start(new Stage());
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// add if to pickUp order only
		if (ClientUtils.localOrderInProcess != null) {
			machineName.setText(ClientUtils.localOrderInProcess.getMachineName());
			if (ClientUtils.cartDisplayFlag) {
				for (int i = 0; i < ClientUtils.localOrderInProcess.getProductsForDisplay().size(); i++) {
					switch (ClientUtils.localOrderInProcess.getProductsForDisplay().get(i).getCategory()) {
					case FOOD: {
						ClientUtils.localOrderInProcess.getFoodCategoryProducts()
								.add(ClientUtils.localOrderInProcess.getProductsForDisplay().get(i));
						break;
					}
					case SNACKS: {
						ClientUtils.localOrderInProcess.getSnackCategoryProducts()
								.add(ClientUtils.localOrderInProcess.getProductsForDisplay().get(i));
						break;
					}
					case SWEETS: {
						ClientUtils.localOrderInProcess.getSweetsCategoryProducts()
								.add(ClientUtils.localOrderInProcess.getProductsForDisplay().get(i));
						break;
					}
					case DRINKS: {
						ClientUtils.localOrderInProcess.getDrinksCategoryProducts()
								.add(ClientUtils.localOrderInProcess.getProductsForDisplay().get(i));
						break;
					}
					}
				}
				ClientUtils.cartDisplayFlag = false;
			}
		}else if (ClientUtils.pickupOrderInProcess != null) {
			machineName.setText(ClientUtils.pickupOrderInProcess.getMachineName());
			if (ClientUtils.cartDisplayFlag) {
				for (int i = 0; i < ClientUtils.pickupOrderInProcess.getProductsForDisplay().size(); i++) {
					switch (ClientUtils.pickupOrderInProcess.getProductsForDisplay().get(i).getCategory()) {
					case FOOD: {
						ClientUtils.pickupOrderInProcess.getFoodCategoryProducts()
								.add(ClientUtils.pickupOrderInProcess.getProductsForDisplay().get(i));
						break;
					}
					case SNACKS: {
						ClientUtils.pickupOrderInProcess.getSnackCategoryProducts()
								.add(ClientUtils.pickupOrderInProcess.getProductsForDisplay().get(i));
						break;
					}
					case SWEETS: {
						ClientUtils.pickupOrderInProcess.getSweetsCategoryProducts()
								.add(ClientUtils.pickupOrderInProcess.getProductsForDisplay().get(i));
						break;
					}
					case DRINKS: {
						ClientUtils.pickupOrderInProcess.getDrinksCategoryProducts()
								.add(ClientUtils.pickupOrderInProcess.getProductsForDisplay().get(i));
						break;
					}
					}
				}
				ClientUtils.cartDisplayFlag = false;
			}
		} else {
			machineName.setText(String.valueOf(ClientUtils.deliveryOrderInProcess.getClient().getRegion()));
			if (ClientUtils.cartDisplayFlag) {
				for (int i = 0; i < ClientUtils.deliveryOrderInProcess.getProductsForDisplay().size(); i++) {
					switch (ClientUtils.deliveryOrderInProcess.getProductsForDisplay().get(i).getCategory()) {
					case FOOD: {
						ClientUtils.deliveryOrderInProcess.getFoodCategoryProducts()
								.add(ClientUtils.deliveryOrderInProcess.getProductsForDisplay().get(i));
						break;
					}
					case SNACKS: {
						ClientUtils.deliveryOrderInProcess.getSnackCategoryProducts()
								.add(ClientUtils.deliveryOrderInProcess.getProductsForDisplay().get(i));
						break;
					}
					case SWEETS: {
						ClientUtils.deliveryOrderInProcess.getSweetsCategoryProducts()
								.add(ClientUtils.deliveryOrderInProcess.getProductsForDisplay().get(i));
						break;
					}
					case DRINKS: {
						ClientUtils.deliveryOrderInProcess.getDrinksCategoryProducts()
								.add(ClientUtils.deliveryOrderInProcess.getProductsForDisplay().get(i));
						break;
					}
					}
				}
			}
			ClientUtils.cartDisplayFlag = false;
		}

	}

}
