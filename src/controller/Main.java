package controller;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class Main extends Application {
	private static ArrayList<OnChangeScreen> listeners = new ArrayList<>();
	private static Stage stage;
	private static Scene mainScene;
	private static Scene inserirScene;
	private static Scene buscaNome;
	private static Scene buscaNiver;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		primaryStage.setTitle("Crud");
		
		Parent fxmlMain = FXMLLoader.load(getClass().getResource("../view/main_screen.fxml"));	
		mainScene = new Scene(fxmlMain, 640, 400);
		
		Parent fxmlDetails = FXMLLoader.load(getClass().getResource("../view/details_screen.fxml"));
		inserirScene = new Scene(fxmlDetails, 640, 400);
		
		Parent fxmlNome = FXMLLoader.load(getClass().getResource("../view/buscaNome_screen.fxml"));
		buscaNome = new Scene(fxmlNome, 600, 400);
		
		Parent fxmlNiver = FXMLLoader.load(getClass().getResource("../view/buscaNiver_screen.fxml"));
		buscaNiver = new Scene(fxmlNiver, 600, 400);
		
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}
	public static void changeScreen(String tela, Object userData){
		switch(tela) {
			case "main":
				stage.setScene(mainScene);
				notifyAllListeners("main", userData);
				break;
			case "inserir":
				stage.setScene(inserirScene);
				notifyAllListeners("details", userData);
				break;
			case "buscaNome":
				stage.setScene(buscaNome);
				notifyAllListeners("buscaNome", userData);
				break;
			case "buscaNiver":
				stage.setScene(buscaNiver);
				notifyAllListeners("buscaNiver", userData);
		}
	}
	public static void changeScreen(String tela) {
		changeScreen(tela, null);
	}
	public static interface OnChangeScreen{
		void onScreenChanged(String newScreen, Object userData);
	}
	public static void addOnChangeScreenListener(OnChangeScreen newListener) {
		listeners.add(newListener);
	}
	private static void notifyAllListeners(String newScreen, Object userData) {
		for(OnChangeScreen l:listeners) {
			l.onScreenChanged(newScreen, userData);
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
