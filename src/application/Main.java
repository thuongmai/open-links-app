package application;

import java.io.IOException;

import application.view.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	private FXMLLoader loader;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Open Links App");

		showMainView();
	}

	/*
	 * Initialize main view and set scene to primaryStage
	 */
	private void showMainView() {
		try {
			// Load layout from fxml file
			loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/MainView.fxml"));
			AnchorPane mainView = (AnchorPane) loader.load();

			// Show the scene containing main view layout
			Scene scene = new Scene(mainView);
			primaryStage.setScene(scene);
			primaryStage.show();

			// Give the controller access to the main view
			MainViewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			System.out.println("file: " + loader.getLocation());
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
