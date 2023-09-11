package ch.sean.gameclasses;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player extends SuperClass{
	
	private int speed = 10;
	private boolean isSearch=false;
	private int gold = 0;
	private int goldNugget = 0;
	private int versuche = 25;
	private Image img;
	private SearchPlace searchPlace;
	private Image[] arrayImg;


	public Player(int xPos, int yPos, int xSize, int ySize, boolean abbaubar,Thread gameLoop) {
		super(xPos, yPos, xSize, ySize, abbaubar);
		this.arrayImg = new Image[] {new Image(getClass().getResourceAsStream("/images/menFront1.png")),
				new Image(getClass().getResourceAsStream("/images/menFront2.png")),
				new Image(getClass().getResourceAsStream("/images/menBack1.png")),
				new Image(getClass().getResourceAsStream("/images/menBack2.png")),
				new Image(getClass().getResourceAsStream("/images/menLinks.png")),
				new Image(getClass().getResourceAsStream("/images/menLinksLaufen.png")),
				new Image(getClass().getResourceAsStream("/images/menRechts.png")),
				new Image(getClass().getResourceAsStream("/images/menRechtsLaufen.png"))};
		this.searchPlace = new SearchPlace(xPos, yPos, 50, 50, this,gameLoop);
		this.img = this.arrayImg[0];
		
	}
	
	//ZEICHNET DEN SPIELER AUF DIE MAP
	public void drawPlayer(GraphicsContext gc) {
		gc.drawImage(this.getImg(), this.getxPos(), this.getyPos(),this.getxSize(),this.getySize());
	}
	
	//MEHTODE FÜR DAS LAUFEN DES SPIELERS
	//DIESE METHODE ERWARTET EIN CHAR ZUM IN DIE RICHTIGE RICHTUNG
	//ZU LAUFEN
	public void move(char direction) {
		if(direction == '>') {
			if(getImageFromArray(6).equals(getImg())) {
				setImg(getImageFromArray(7));
			}
			else {
				setImg(getImageFromArray(6));
			}
			setxPos(getxPos()+speed);
		}
		if(direction == '<') {
			if(getImageFromArray(4).equals(getImg())) {
				setImg(getImageFromArray(5));
			}
			else {
				setImg(getImageFromArray(4));
			}
			setxPos(getxPos()-speed);
		}
		if(direction == '^') {
			if(getImageFromArray(2).equals(getImg())) {
				setImg(getImageFromArray(3));
			}
			else {
				setImg(getImageFromArray(2));
			}
			setyPos(getyPos()-speed);
		}
		if(direction == 'v') {
			if(getImageFromArray(0).equals(getImg())) {
				setImg(getImageFromArray(1));
			}
			else {
				setImg(getImageFromArray(0));
			}
			setyPos(getyPos()+speed);
		}
		
	}
	//METHODE FÜR DAS SETZEN DES SUCHPLATZES
	//UND FÜR DAS ERSTELLEN UND STARTEN DES SUCH THREADS
	public void setMine(MapTile mapTile, MapTile[][] map) {
		if(this.getSearchPlace().getSearchThread() == null) {
			if(this.versuche > 0) {
				this.setSearch(true);
				this.getSearchPlace().setTheSearchPlace( map, mapTile);
				this.getSearchPlace().createSearchThread(); 
				this.getSearchPlace().searchGold();
				this.versuche--;
			}
			
		}
		
	}
	
	
	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	
	private Image getImageFromArray(int imgPos) {
		return this.arrayImg[imgPos];
	}
	public boolean isSearch() {
		return isSearch;
	}
	public void setSearch(boolean isSearch) {
		this.isSearch = isSearch;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold += gold;
	}

	public SearchPlace getSearchPlace() {
		return searchPlace;
	}

	public int getVersuche() {
		return versuche;
	}

	public void setVersuche(int versuche) {
		this.versuche = versuche;
	}

	public int getGoldNugget() {
		return goldNugget;
	}

	public void setGoldNugget(int goldNugget) {
		this.goldNugget = goldNugget;
	}
	
	
	
}
