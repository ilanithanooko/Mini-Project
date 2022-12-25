package client_gui;

import Utils.generalMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SnackCategoryController {
    @FXML
    private ComboBox<?> SortByComboBox;
    @FXML
    private Button addApropo;
    @FXML
    private Button addBamba;
    @FXML
    private Button addBambaMix;
    @FXML
    private Button addBambaNougat;
    @FXML
    private Button addBisli;
    @FXML
    private Button addCheetos;
    @FXML
    private Button addDoritos;
    @FXML
    private Button addFitness;
    @FXML
    private TextField apropoAmount;
    @FXML
    private ImageView backBtn;
    @FXML
    private TextField bambaAmount;
    @FXML
    private TextField bambaMixAmount;
    @FXML
    private TextField bambaNougatAmount;
    @FXML
    private TextField bisliAmount;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button checkoutBtn;
    @FXML
    private TextField cheetosAmount;
    @FXML
    private TextField doritosAmount;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private TextField fitnessAmount;

    @FXML
    private Button minusApropo;

    @FXML
    private Button minusBamba;

    @FXML
    private Button minusBambaMix;

    @FXML
    private Button minusBambaNougat;

    @FXML
    private Button minusBisli;

    @FXML
    private Button minusCheetos;

    @FXML
    private Button minusDoritos;

    @FXML
    private Button minusFitness;

    @FXML
    private Label nameApropo;

    @FXML
    private Label nameBamba;

    @FXML
    private Label nameBambaMix;

    @FXML
    private Label nameBambaNougat;

    @FXML
    private Label nameBisli;

    @FXML
    private Label nameCheetos;

    @FXML
    private Label nameDoritos;

    @FXML
    private Label nameFitness;

    @FXML
    private Button plusApropo;

    @FXML
    private Button plusBamba;

    @FXML
    private Button plusBambaMix;

    @FXML
    private Button plusBambaNougat;

    @FXML
    private Button plusBisli;

    @FXML
    private Button plusCheetos;

    @FXML
    private Button plusDoritos;

    @FXML
    private Button plusFitness;

    @FXML
    private Label priceApropo;

    @FXML
    private Label priceBamba;

    @FXML
    private Label priceBambaMix;

    @FXML
    private Label priceBambaNougat;

    @FXML
    private Label priceBisli;

    @FXML
    private Label priceCheetos;

    @FXML
    private Label priceDoritos;

    @FXML
    private Label priceFitness;
    
	public void start (Stage primaryStage) {
		generalMethods.displayScreen(primaryStage, getClass(), "/client_fxml/SnackCategorySubscribers.fxml", "Snacks");
	}
    @FXML
    void addProductToCart(ActionEvent event) {

    }

    @FXML
    void clickOnBackButton(MouseEvent event) {
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
    void decreaseAmountByOne(ActionEvent event) {

    }

    @FXML
    void increaseAmountByOne(ActionEvent event) {

    }

}
