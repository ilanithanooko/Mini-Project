package client;
import client_gui.ConnectToServerController;
import client_gui.GetSubscriberController;
import client_gui.CeoDashboardController;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ClientUI extends Application {
	public static ClientController chat; //only one instance

	public static void main( String args[] ) throws Exception
	   { 
		    launch(args);  
	   } // end main
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// TODO Auto-generated method stub
		ConnectToServerController ConnectToServer=new ConnectToServerController();
		ConnectToServer.start(primaryStage);
		 
		
	}
	
	
}
