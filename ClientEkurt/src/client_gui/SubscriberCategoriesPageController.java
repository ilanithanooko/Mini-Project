package client_gui;

import Utils.generalMethods;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SubscriberCategoriesPageController {

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
    }

    @FXML
    void clickOnDrinksCategory(MouseEvent event) {

    }

    @FXML
    void clickOnFoodCategory(MouseEvent event) {

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

    }

    @FXML
    void clickOnViewCart(MouseEvent event) {

    }

}
