package client_gui;

import java.net.URL;
import java.util.ResourceBundle;

import Utils.generalMethods;
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
    private Button cancelBtn;

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
			for(int i=0 ; i<SubscriberCategoriesPageController.snackCategoryProducts.size();i++) {
				VBox box;
				if(SubscriberCategoriesPageController.snackCategoryProducts.get(i).isAvailable()) {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/client_fxml/ProductInGrid.fxml"));
					box = fxmlLoader.load();
					ProductInGridController productInGridController = fxmlLoader.getController();
					productInGridController.setData(SubscriberCategoriesPageController.snackCategoryProducts.get(i));
				}
				else {
					FXMLLoader fxmlLoader = new FXMLLoader();
					fxmlLoader.setLocation(getClass().getResource("/client_fxml/ProductNotInStock.fxml"));
					box = fxmlLoader.load();
					ProductInGridController productInGridController = fxmlLoader.getController();
					productInGridController.setData(SubscriberCategoriesPageController.snackCategoryProducts.get(i));
				}
				if(columns == 4) {
					columns = 0;
					++row;
				}
				gridPane.add(box, columns++, row);
				gridPane.setMargin(box, new Insets(8));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

    @FXML
    void clickOnBackButton(MouseEvent event) {
    	//don't forget to add case for customer
    	gridPane.getChildren().clear();
    	((Node) event.getSource()).getScene().getWindow().hide();
    	new SubscriberCategoriesPageController().start(new Stage());
    }

    @FXML
    void clickOnCancel(ActionEvent event) {

    }

    @FXML
    void clickOnCheckout(ActionEvent event) {

    }

    @FXML
    void clickOnViewCart(MouseEvent event) {
    	new CartPageController().start(new Stage());
    }
}
