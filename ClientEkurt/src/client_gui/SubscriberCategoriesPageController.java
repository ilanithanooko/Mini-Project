package client_gui;

import java.util.ArrayList;
import java.util.List;

import Utils.generalMethods;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.ProductInGrid;

public class SubscriberCategoriesPageController {
	public static List<ProductInGrid> foodCategoryProducts = new ArrayList<>();
	public static List<ProductInGrid> snackCategoryProducts = new ArrayList<>();
	public static List<ProductInGrid> sweetsCategoryProducts = new ArrayList<>();
	public static List<ProductInGrid> drinksCategoryProducts = new ArrayList<>();


    @FXML
    private ImageView backBtn;
    @FXML
    private ImageView cartBtn;
    @FXML
    private ImageView drinksCategory;
    @FXML
    private ImageView foodCategory;

    @FXML
    private ImageView salesCategory;

    @FXML
    private ImageView snackCategory;

    @FXML
    private ImageView sweetsCategory;

	void start (Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/SubscriberCategoriesPage.fxml", "Categories");
	}
    
    @FXML
    void clickOnBackButton(MouseEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	new ChooseMachinePickupController().start(new Stage());
    	
    	//implement removing products from ALL categories to avoid dupliacation
//    	DrinksCategoryController.gridPane.getChildren().clear();
//    	SweetsCategoryController.gridPane.getChildren().clear();
//    	SnackCategoryController.gridPane.getChildren().clear();
//    	FoodCategoryController.gridPane.getChildren().clear();


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
    void clickOnSalesCategory(MouseEvent event) {

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

    
	public static void sortProductsByCategory(List<ProductInGrid> products) {
			for (int i = 0; i < products.size(); i++) {
				switch (products.get(i).getCategory()) {
				case FOOD: {
					foodCategoryProducts.add(products.get(i));
					break;
				}
				case SNACKS: {
					snackCategoryProducts.add(products.get(i));
					break;
				}
				case SWEETS: {
					sweetsCategoryProducts.add(products.get(i));
					break;
				}
				case DRINKS: {
					drinksCategoryProducts.add(products.get(i));
					break;
				}
				}
			}
	}
	
	public static List<ProductInGrid> getFoodCategoryProducts() {
		return foodCategoryProducts;
	}

	public static void setFoodCategoryProducts(List<ProductInGrid> foodCategoryProducts) {
		SubscriberCategoriesPageController.foodCategoryProducts = foodCategoryProducts;
	}

	public static List<ProductInGrid> getSnackCategoryProducts() {
		return snackCategoryProducts;
	}

	public static void setSnackCategoryProducts(List<ProductInGrid> snackCategoryProducts) {
		SubscriberCategoriesPageController.snackCategoryProducts = snackCategoryProducts;
	}

	public static List<ProductInGrid> getSweetsCategoryProducts() {
		return sweetsCategoryProducts;
	}

	public static void setSweetsCategoryProducts(List<ProductInGrid> sweetsCategoryProducts) {
		SubscriberCategoriesPageController.sweetsCategoryProducts = sweetsCategoryProducts;
	}

	public static List<ProductInGrid> getDrinksCategoryProducts() {
		return drinksCategoryProducts;
	}

	public static void setDrinksCategoryProducts(List<ProductInGrid> drinksCategoryProducts) {
		SubscriberCategoriesPageController.drinksCategoryProducts = drinksCategoryProducts;
	}
		
    @FXML
    void clickOnViewCart(MouseEvent event) {
    	new CartPageController().start(new Stage());
    }

}
