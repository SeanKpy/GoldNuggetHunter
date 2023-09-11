package ch.sean.gameclasses;


import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.image.Image;

public class SearchPlace extends SuperClass implements Runnable{
	
	private Image img;
	private Player player;
	private Thread searchThread;
	private Thread gameLoop;
	private int startOfSearchCount;
	
	private MapTile mapTile;//Wird dann ersetzt mit der Suchmaschine für den Such Thread
	
	public SearchPlace(int xPos, int yPos, int xSize, int ySize, Player player, Thread gameLoop) {
		super(xPos, yPos, xSize, ySize, false);
		this.player = player;
		this.gameLoop = gameLoop;
		this.img = new Image(getClass().getResourceAsStream("/images/suchstelle.png"));
		this.startOfSearchCount = 0;
	}
	
	//MEHTODE UM DIE SUCHMASCHINE IN DIE MAP ZUSETZEN
	//DAZU WIRD DAS BILD AN DER ENTSPRECHENTER STELLE GEÄNDERT IN DIE SUCHMASCHINE
	//UND MAN KANN DIE STELLE NICHT MEHR PASSIEREN
	public void setTheSearchPlace(MapTile[][] map, MapTile mapTile) {
		this.mapTile = mapTile;
		this.mapTile.setPassable(false);
		this.mapTile.setImg(this.img);
	}
	
	
	public Thread getSearchThread() {
		return this.searchThread;
	}
	
	public void createSearchThread() {
		this.searchThread = new Thread(this);

	}
	public void destroySearchThread() {
		this.searchThread = null;
	}
	
	public void searchGold() {
		this.searchThread.start();
		
	}
	
	public void setStartOfSearchCount(int end) {
		this.startOfSearchCount = end;
	}
	
	//THREAD ZUM DAS GOLD SUCHEN
	//AM ENDE DES THREADS WIRD DAS BILD IN DEM MAPARRAY WIEDER ZURÜCKGESETZT ZU EINEM BODEN UND PASSIERBAR GEMACHT
	@Override
	public void run() {
		int valueOfGold = ThreadLocalRandom.current().nextInt(10, 101);
		int valueOfGoldFind = ThreadLocalRandom.current().nextInt(2, 21);
		
		synchronized (gameLoop) {
			
			while(startOfSearchCount<=valueOfGold) {
				
				try {
					this.player.setGold(valueOfGoldFind);
					startOfSearchCount+=valueOfGoldFind;
					
					Thread.sleep(200);
					
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		endSearch();
	}
	
	private void endSearch() {
		this.player.setSearch(false);
		this.setStartOfSearchCount(0);
		destroySearchThread();
		
		//SETZT WIEDER EINE MAPTILE ALS BODEN ODER DAS GROSSE NUGGEt
		if(mapTile.isBigNugget()) {
			mapTile.setImg(new Image(getClass().getResourceAsStream("/images/goldNugget.png")));
			player.setGoldNugget(player.getGoldNugget()+1) ;
		}
		else {
			mapTile.setImg(new Image(getClass().getResourceAsStream("/images/fertigLoch.png")));
			
		}
		mapTile.setPassable(true);
		mapTile.setAbbaubar(false);
	}

}
