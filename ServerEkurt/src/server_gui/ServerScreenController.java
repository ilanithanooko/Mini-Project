package server_gui;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import server.EchoServer;
import server.ServerUI;

public class ServerScreenController implements Initializable {

	@FXML
	private Button BTNConnect;

	@FXML
	private Button BTNDisconnect;

	@FXML
	private Button BTNImport;

	@FXML
	private Pane ServerPane;

	@FXML
	private TextField TxtDataBase;

	@FXML
	private TextField TxtIp;

	@FXML
	private TextField TxtPassword;

	@FXML
	private TextField TxtPort;

	@FXML
	private TextField TxtUserName;

	@FXML
	private TextField portxt;

	@FXML
	private GridPane ConnectedUsers;

	private List<String> data = new ArrayList<String>(); // Array to pass information
	private String hostName;
	private Label ServerManagerIp, ServerManagerStatus, ServerManagerHost;

	private String getport() {
		return TxtPort.getText();
	}

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/server_gui/ServerFXML.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Ekurt Server");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
	}

	@FXML
	void Connect(ActionEvent event) {
		String p;
		data.add(TxtDataBase.getText());
		data.add(TxtUserName.getText());
		data.add(TxtPassword.getText());
		System.out.println(data.toString());
		p = getport();
		if (p.trim().isEmpty()) {
			System.out.println("Enter a port number please.");

		} else {
		
			if (ServerUI.runServer(p, data)) {
				//IP
				ServerManagerIp = new Label();
				ServerManagerIp.setText(TxtIp.getText());
				ConnectedUsers.add(ServerManagerIp, 0, 0);
				//HOST
				ServerManagerHost = new Label();
				ServerManagerHost.setText(hostName);
				ConnectedUsers.add(ServerManagerHost, 1, 0);
				//STATUS
				ServerManagerStatus = new Label();
				ServerManagerStatus.setText("Connected-server");
				ConnectedUsers.add(ServerManagerStatus, 2, 0);
			}

		}

	}

	@FXML
	void Disconnect(ActionEvent event) {
		ConnectedUsers.getChildren().clear();// Clear gridpane
		ServerUI.stopServer();

	}
	
	// To be continued - in the next phase
	@FXML
	void Import(ActionEvent event) {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadData();
	}

	private void loadData() {
		this.TxtPort.setText(String.valueOf(5555));
		try {
			this.TxtIp.setText(InetAddress.getLocalHost().getHostAddress());
			this.TxtIp.setDisable(true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.TxtDataBase.setText("jdbc:mysql://localhost/ekurt?serverTimezone=IST");
		this.TxtUserName.setText("root");
		//this.TxtPassword.setText("************");
		this.TxtPassword.setText("aA123456");
		this.BTNImport.setDisable(true);
		try {
			hostName = InetAddress.getLocalHost().getHostName();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
