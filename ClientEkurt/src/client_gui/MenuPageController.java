package client_gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuPageController {

	@FXML
	private Button getSubscribersBtn;
	@FXML
	private Button updateSubscribersBtn;

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/client_gui/MenuPage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Ekurt Menu - Prototype");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
	}

	@FXML
	void Get(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		Pane root = loader.load(getClass().getResource("/client_gui/GetSubscriber.fxml").openStream());
		Scene scene = new Scene(root); // create a scene
		Stage primaryStage = new Stage();
		primaryStage.setTitle("Subscribers View Page");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
	}
	
}