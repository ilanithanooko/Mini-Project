package Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class generalMethods {
	
	public static void displayScreen(Stage primaryStage,Class<?> currClass, String locationUrl, String title) throws Exception {
		Parent root = FXMLLoader.load(currClass.getResource(locationUrl));
		Scene scene = new Scene(root);
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
