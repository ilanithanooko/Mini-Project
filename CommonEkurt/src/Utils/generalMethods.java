package Utils;

import java.util.List;

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
	  /**
     * Get the first element form the provided list
     * @param list
     * @return First element of the list, if not exist return null
     */
    public static <T> T getFirstElementFromList(List<T> list) {
        if (list == null || list.isEmpty())
            return null;
        else return list.get(0);
    }
}
