package ch.sean.gameclasses;

import javafx.scene.image.Image;



public class MapTile extends SuperClass{
	
	private Image img;
	private int row;
	private int column;
	private boolean passable;
	private boolean isBigNugget;


	public MapTile(int xPos, int yPos,int xSize, int ySize, boolean abbaubar,
			Image img, int row, int column, boolean passable, boolean isBigNugget){
		
		super(xPos, yPos, xSize, ySize,abbaubar);
		this.img = img;
		this.row = row;
		this.column = column;
		this.passable = passable;
		this.isBigNugget = isBigNugget;
	}
	
	public void setImg(Image img) {
		this.img = img;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public Image getImg() {
		return this.img;
	}

	public boolean isPassable() {
		return passable;
	}

	public void setPassable(boolean passable) {
		this.passable = passable;
	}

	public boolean isBigNugget() {
		return isBigNugget;
	}

	public void setBigNugget(boolean isBigNugget) {
		this.isBigNugget = isBigNugget;
	}
	
	
}
