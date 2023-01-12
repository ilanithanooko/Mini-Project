package Utils;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class generalMethods {

	public static void displayScreen(Stage primaryStage, Class<?> currClass, String locationUrl, String title) {
		try {
			// Set the icon for the stage (taskbar and window)
			primaryStage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("client_images/EKURT.png")));
			Parent root = FXMLLoader.load(currClass.getResource(locationUrl));
			Scene scene = new Scene(root);
			primaryStage.setTitle(title);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get the first element form the provided list
	 * 
	 * @param list
	 * @return First element of the list, if not exist return null
	 */
	public static <T> T getFirstElementFromList(List<T> list) {
		if (list == null || list.isEmpty())
			return null;
		else
			return list.get(0);
	}
}
