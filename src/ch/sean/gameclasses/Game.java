package ch.sean.gameclasses;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;



public class Game implements Runnable{
	
	private final int tileSize = 50;
	private final int FPS = 30;
	
	private GraphicsContext gc;
	
	private Player player;
	private MapTile[][] map,map1,map2,map3,map4,map5,map6,map7,map8,map9;
	private ArrayList<MapTile[][]> mapList;
	private int allNuggets;
	
	private boolean gameRun;
	private Thread gameLoop;
	
	
	//KONSTRUKTER FÜR DAS SPIEL
	public Game(Scene scene, GraphicsContext gc) {
		scene.setOnKeyPressed(e ->{
			keyHandler(e);
		});
		scene.setOnMouseClicked(e ->{
			checkAndSetSearchplace(e.getX(), e.getY());
		});
		mapList = new ArrayList<MapTile[][]>();
		
		this.gc = gc;
		this.gc.setFont(new Font("Arial",18));
		
		gameLoop = new Thread(this);
		createGame();
		gameRun = true;
		gameLoop.start();
		
	}
	
	//METHODE DIE ÜBERPRÜFT WELCHE FLÄCHE ANGEKLICKT WURDE
	//ES WERDEN DIE POSITIONEN AUF DER CANVAS UND IM ARRAY AUSGELESEN ZUM 
	//ERSTELLEN EINER NEUEN SUCHSTELLE
	private MapTile checkWhichTileIsPressed(double x, double y) {
		MapTile tempTile = null;
		for(int i=0;i<map.length;i++) {
			for(int j=0;j<map[i].length;j++) {
				if(map[i][j].getHitPlace().contains(x, y)) {
					 tempTile = map[i][j];
				}
			}
		}
		return tempTile;
	}
	
	
	//PROVISORISCH WENN EINE DB VORHANDEN IST MUSS ES ANDERS GEMACHT WERDEN,
	//DA DIE DATEN AUS DER DB GELESEN WERDEN
	private void createGame() {
		player = new Player(350, 320, 30, 40,false,gameLoop);
		boolean hasNugget=false;
		boolean isBigNugget = false;
		
		map = new MapTile[650/50][900/50];
		map1 = new MapTile[650/50][900/50];
		map2 = new MapTile[650/50][900/50];
		map3 = new MapTile[650/50][900/50];
		map4 = new MapTile[650/50][900/50];
		map5 = new MapTile[650/50][900/50];
		map6 = new MapTile[650/50][900/50];
		map7 = new MapTile[650/50][900/50];
		map8 = new MapTile[650/50][900/50];
		map9 = new MapTile[650/50][900/50];
		
		mapList.add(map1);
		mapList.add(map2);
		mapList.add(map3);
		mapList.add(map4);
		mapList.add(map5);
		mapList.add(map6);
		mapList.add(map7);
		mapList.add(map8);
		mapList.add(map9);
		
		int x=0;
		int y=0;

		for(int count=0;count<mapList.size();count++) {
			for(int i=0;i<mapList.get(count).length;i++) {
				for(int j=0;j<mapList.get(count)[i].length;j++) {
					int rnd = ThreadLocalRandom.current().nextInt(1, 22);
	
					if(!hasNugget) {
						int rndNugget = ThreadLocalRandom.current().nextInt(1, 235);
						if(rndNugget%3==0 && rndNugget%7==0 && rndNugget%12==0) {

							hasNugget=true;
							isBigNugget=true;
							allNuggets+=1;
						}
					}
					else {
						isBigNugget=false;
					}
					if(rnd%7==0) {
						mapList.get(count)[i][j] = new MapTile(x,y,tileSize,tileSize,true,new Image(getClass().getResourceAsStream("/images/baum.png")),i,j,false,isBigNugget);
					}
					else {
						mapList.get(count)[i][j] = new MapTile(x,y,tileSize,tileSize,true,new Image(getClass().getResourceAsStream("/images/boden.png")),i,j,true,isBigNugget);
					}
					x+=50;
				}
				x=0;
				y+=50;
			}
			hasNugget=false;
			x=0;
			y=0;
		}
		map = mapList.get(4);
		
	}
	
	//METHODE DIE ALLE ELEMENTE AUF DER CANVAS ZEICHNET
	private void draw() {
	
		for(int i=0;i<map.length;i++) {
			for(int j=0;j<map[i].length;j++) {
				gc.drawImage(map[i][j].getImg(), map[i][j].getxPos(), map[i][j].getyPos(),map[i][j].getxSize(),map[i][j].getySize());
			}
		}
		gc.setFill(Color.GOLD);
		gc.fillText("Goldnugget gefunden: "+player.getGoldNugget()+"/"+allNuggets, 350, 30);
		gc.setFill(Color.YELLOW);
		gc.fillText("Gold: "+player.getGold(), 20, 30);
		gc.setFill(Color.DARKBLUE);
		gc.fillText("Versuche: "+player.getVersuche(),750, 30);
		player.drawPlayer(gc);
	}
	
	
	//GAMELOOP 
	@Override
	public void run() {
		double interval = 1000000000/FPS;
		double delta =0;
		long lastTime = System.nanoTime();
		long currentTime;
			
		while(gameRun) {
				
			currentTime = System.nanoTime();
				
			delta +=(currentTime - lastTime) / interval;
			lastTime = currentTime;
				
			if(delta>=1) {
				checkPlayerPos();
				draw();
				delta--;	
			}
		}
	}
	
	//ÜBERPRÜFT DIE POSITION DES SPIELERS AUF DER MAP
	//UND JE NACH WERT DIE DIE setMap() METHODE LIEFERT
	//WIRD DER SPIELER VERSETZT ODER NICHT.
	private void checkPlayerPos() {
		
		if(player.getxPos()+player.getxSize()>900) {
			if(setMap('>')) {
				player.setxPos(20);
			}
			else {
				player.setxPos(900-player.getxSize());
			}
		}
		
		if(player.getyPos()+player.getySize()>650) {
			if(setMap('v')) {
				player.setyPos(0);
			}
			else {
				player.setyPos(650-player.getySize());
			}
			
		}
		if(player.getxPos()<0) {
			if(setMap('<')) {
				player.setxPos(850);
			}
			else {
				player.setxPos(0);
			}
		}
		
		if(player.getyPos()<0) {
			if(setMap('^')) {
				player.setyPos(580);
			}
			else {
				player.setyPos(0);
			}
		}
	}
	//METHODE ZUM SETZEN EINER NEUEN MAP WENN DER SPIELER ÜBER EINEN RAND LÄUFT
	//WENN ER DAS KONTINGGENT NOCH NICHT AUFGEBRAUCHT HAT AN MAPS
	//GIBT EINEN BOOLEAN ZURÜCK WENN DER SPIELER VERSETZT WERDEN KANN(true) ODER NICHT(false)

	private boolean setMap(char direction) {
		boolean setPlayer = true;
		if(direction == '>') {
			if(map.equals(mapList.get(2)) || map.equals(mapList.get(5)) || map.equals(mapList.get(8))) {
				setPlayer = false;
			}
			else {
				map = mapList.get(mapList.indexOf(map)+1);
			}
		}
		if(direction == '<') {
			if(map.equals(mapList.get(0)) || map.equals(mapList.get(3)) || map.equals(mapList.get(6))) {
				setPlayer = false;
			}
			else {
				map = mapList.get(mapList.indexOf(map)-1);
			}
		}
		if(direction == 'v') {
			if(map.equals(mapList.get(6)) || map.equals(mapList.get(7)) || map.equals(mapList.get(8))) {
				setPlayer = false;
			
			}
			else {
				map = mapList.get(mapList.indexOf(map)+3);
			}
		}
		if(direction == '^') {
			if(map.equals(mapList.get(0)) || map.equals(mapList.get(1)) || map.equals(mapList.get(2))) {
				setPlayer = false;
			}
			else {
				map = mapList.get((mapList.indexOf(map)-3));
			}
		}
		return setPlayer;
		
	}
	//ÜBERPRÜFT OB MAN ÜBER DIE FLÄCHE LAUFEN KANN ODER NICHT
	private boolean checkObstacle(char direction) {
		boolean possible = true;
		if(direction == '>') {
			for(int i=0;i<map.length;i++) {
				for(int j=0;j<map[i].length;j++) {
					if(!map[i][j].isPassable()) {
						if(map[i][j].getHitPlace().contains(player.getxPos()+player.getxSize()-10, 
								player.getyPos()+player.getySize()/2)) {
							
							possible=false;
							
						}
					}
				}
			}
		}
		
		if(direction=='<') {
			for(int i=0;i<map.length;i++) {
				for(int j=0;j<map[i].length;j++) {
					if(!map[i][j].isPassable()) {
						if(map[i][j].getHitPlace().contains(player.getxPos()+5, 
								player.getyPos()+player.getySize()/2)) {
							
							possible=false;
							
						}
					}
				}
			}
		}
		
		if(direction=='^') {
			for(int i=0;i<map.length;i++) {
				for(int j=0;j<map[i].length;j++) {
					if(!map[i][j].isPassable()) {
						if(map[i][j].getHitPlace().contains(player.getxPos(), 
								player.getyPos()+10)) {
							
							possible=false;
							
						}
					}
				}
			}
		}
		
		if(direction=='v') {
			for(int i=0;i<map.length;i++) {
				for(int j=0;j<map[i].length;j++) {
					if(!map[i][j].isPassable()) {
						if(map[i][j].getHitPlace().contains(player.getxPos(), 
								player.getyPos()+player.getySize())) {
							
							possible=false;
							
						}
					}
				}
			}
		}
		
		return possible;
	}
	
	private void keyHandler(KeyEvent key) {
		if(key.getCode() == KeyCode.RIGHT || key.getCode()==KeyCode.D) {
			if(checkObstacle('>')) {
				player.move('>');
			}
			
		}
		if(key.getCode() == KeyCode.LEFT || key.getCode()==KeyCode.A) {
			if(checkObstacle('<')) {
				player.move('<');
			}
		}
		if(key.getCode() == KeyCode.UP || key.getCode()==KeyCode.W) {
			if(checkObstacle('^')) {
				player.move('^');
			}
			
		}
		if(key.getCode() == KeyCode.DOWN || key.getCode()==KeyCode.S) {
			if(checkObstacle('v')) {
				player.move('v');
			}
		}
		
	}

	//ÜBERPRÜFT DIE ANGEKLICKE FLÄCHE WIE WEIT ENTFERNT SIE SICH VOM SPIELER BEFINDET
	//NICHT MEHR ALS 2 FLÄCHEN DARF SIE WEIT WEG SEIN
	private boolean checkDistance(double x, double y) {
		boolean possible = false;
		if(x<player.getxPos()) {
			if(player.getxPos()-100<x) {
				if(y<player.getyPos()) {
					if(player.getyPos()-100<y) {
						possible = true;
					}
				}
				else {
					if(player.getyPos()+150>y) {
						possible = true;
					}
				}
				
			}
			
		}
		else {
			if(player.getxPos()+150>x) {
				if(y<player.getyPos()) {
					if(player.getyPos()-100<y) {
						possible = true;
					}
				}
				else {
					if(player.getyPos()+150>y) {
						possible = true;
					}
				}
				
			}
		}
		
		return possible;
		
		
	}
	//WIRD VOM MOUSELISTENER AUFGERUFEN WENN EINE FLÄCHE ANGEGLICKT WIRD
	//PRÜFT ALLE VORGABEN BEVOR DER SPIELER DIE MINE SETZEN KANN
	private void checkAndSetSearchplace(double x, double y) {
		if(checkDistance(x, y)) {
			MapTile forCheckTile = checkWhichTileIsPressed(x, y);
			if(forCheckTile.isAbbaubar()) {
				player.setMine(forCheckTile,map);
			}
		}
	}
	
	public void setGameRun(boolean gameRun) {
		this.gameRun = gameRun;
		player.getSearchPlace().setStartOfSearchCount(600);
	}

}
