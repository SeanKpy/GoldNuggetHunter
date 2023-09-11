package ch.sean.goldnugget;

import ch.sean.gameclasses.Game;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameWindow {
	public static final int HIGHT = 650;
	public static final int WIDHT = 900;
	
	private Stage stage;
	private Scene scene; 
	private BorderPane root;
	private GraphicsContext gc;
	private MenuBar menuBar;
	private Game game;
	
	class WindowListener implements EventHandler<WindowEvent>{

		@Override
		public void handle(WindowEvent e) {
			if(e.getEventType()== WindowEvent.WINDOW_CLOSE_REQUEST) {
				game.setGameRun(false);
			}
		}
	}
	
	public GameWindow(Stage stage)  {
		this.stage = stage;
		stage.setOnCloseRequest(new WindowListener());
		stage.setResizable(true);
		initWindow();
		//initMenuBar();
		game = new Game(scene, gc);
	}
	
	//FÜGT EINE MENUBAR HINZU ZUM SPEICHERN UND SCHLIESSEN DES SPIELS
	private void initMenuBar() {
		menuBar = new MenuBar();
		Menu menuGame = new Menu("Spielmenu");
		MenuItem itemClose = new MenuItem("Schliessen");
		MenuItem itemSaveGame = new MenuItem("Speichern");
		itemSaveGame.setOnAction(e -> saveGame());
		itemClose.setOnAction(e -> closeGame());
		menuGame.getItems().add(itemSaveGame);
		menuGame.getItems().add(itemClose);
		menuBar.getMenus().add(menuGame);
		
		root.setTop(menuBar);
	}
	//DAS FENSTER, DIE CANVAS UND DER GRAPHICSCONTEXT WIRD ERSTELLT UND IN DER MITTE PLATZIERT
	private void initWindow(){
		root = new BorderPane();
		scene = new Scene(root);
		Canvas canvas = new Canvas(WIDHT,HIGHT);
		gc = canvas.getGraphicsContext2D();
		root.setCenter(canvas);
		stage.setScene(scene);
		stage.show();
	}
	private void saveGame() {
		
	}
	private void closeGame() {
		game.setGameRun(false);
		Platform.exit();
	}

}
