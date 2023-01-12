package server;

import java.io.IOException;
import java.util.List;
import dataBase.dbController;
import javafx.application.Application;
import javafx.stage.Stage;
import server_gui.ServerScreenController;

public class ServerUI extends Application {

	final public static int DEFAULT_PORT = 5555;
	static EchoServer sv;
	static dbController db;

	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		ServerScreenController server = new ServerScreenController(); // create frame
		server.start(primaryStage);
	}

	public static boolean runServer(String p, List<String> data) {
		int port = 0; // Port:
		if (dbController.connectToDB(data)) {
			try {
				port = Integer.parseInt(p); // Set port 5555

			} catch (Throwable t) {
				System.out.println("ERROR - Could not connect!");
				return false;
			}

			sv = new EchoServer(port);

			try {
				sv.listen(); // Listening for connections
			} catch (Exception ex) {
				System.out.println("ERROR - Could not listen for clients!");
				return false;
			}
			return true;
		}
		return false;
	}

	public static void stopServer() {
		sv.stopListening(); // stopping listening to the port
		try {
			sv.close(); // closing the port.
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
