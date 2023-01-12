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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SalesCategoryController implements Initializable {

	@FXML
	private ImageView backBtn;

	@FXML
	private Button checkoutBtn;

	@FXML
	private GridPane gridPane;

	@FXML
	private Label numOfProductsInCartLabel;

	@FXML
	private ImageView viewCartBtn;

	public void start(Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/SaleCategory.fxml", "Sale");
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int columns = 0;
		int row = 1;

		try {
			if (ClientUtils.localOrderInProcess != null) {
				for (int i = 0; i < ClientUtils.localOrderInProcess.getProductsForDisplay().size(); i++) {
					VBox box;
					if (ClientUtils.localOrderInProcess.getProductsForDisplay().get(i).getStockFromDb() > 0
							&& ClientUtils.localOrderInProcess.getProductsForDisplay().get(i).isIs_in_sale()) {
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(getClass().getResource("/client_fxml/ProductInGrid.fxml"));
						box = fxmlLoader.load();
						ProductInGridController productInGridController = fxmlLoader.getController();
						productInGridController
								.setData(ClientUtils.localOrderInProcess.getProductsForDisplay().get(i));

						if (columns == 4) {
							columns = 0;
							++row;
						}
						gridPane.add(box, columns++, row);
						gridPane.setMargin(box, new Insets(8));
						ClientUtils.categoryProducts.add(box);
					}
				}
			}else if (ClientUtils.pickupOrderInProcess != null) {
				for (int i = 0; i < ClientUtils.pickupOrderInProcess.getProductsForDisplay().size(); i++) {
					VBox box;
					if (ClientUtils.pickupOrderInProcess.getProductsForDisplay().get(i).getStockFromDb() > 0
							&& ClientUtils.pickupOrderInProcess.getProductsForDisplay().get(i).isIs_in_sale()) {
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(getClass().getResource("/client_fxml/ProductInGrid.fxml"));
						box = fxmlLoader.load();
						ProductInGridController productInGridController = fxmlLoader.getController();
						productInGridController
								.setData(ClientUtils.pickupOrderInProcess.getProductsForDisplay().get(i));

						if (columns == 4) {
							columns = 0;
							++row;
						}
						gridPane.add(box, columns++, row);
						gridPane.setMargin(box, new Insets(8));
						ClientUtils.categoryProducts.add(box);
					}
				}
			} else {
				for (int i = 0; i < ClientUtils.deliveryOrderInProcess.getProductsForDisplay().size(); i++) {
					VBox box;
					if (ClientUtils.deliveryOrderInProcess.getProductsForDisplay().get(i).getStockFromDb() > 0
							&& ClientUtils.deliveryOrderInProcess.getProductsForDisplay().get(i).isIs_in_sale()) {
						FXMLLoader fxmlLoader = new FXMLLoader();
						fxmlLoader.setLocation(getClass().getResource("/client_fxml/ProductInGrid.fxml"));
						box = fxmlLoader.load();
						ProductInGridController productInGridController = fxmlLoader.getController();
						productInGridController
								.setData(ClientUtils.deliveryOrderInProcess.getProductsForDisplay().get(i));

						if (columns == 4) {
							columns = 0;
							++row;
						}
						gridPane.add(box, columns++, row);
						gridPane.setMargin(box, new Insets(8));
						ClientUtils.categoryProducts.add(box);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
