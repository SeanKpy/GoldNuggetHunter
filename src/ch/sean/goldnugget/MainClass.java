package ch.sean.goldnugget;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainClass extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		new GameWindow(stage);
		
	}

}
