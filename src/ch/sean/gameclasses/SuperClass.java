package ch.sean.gameclasses;

import javafx.scene.shape.Rectangle;

public abstract class SuperClass{
	
	private int xPos;
	private int yPos;
	private int xSize;
	private int ySize;
	private boolean abbaubar;
	//WIRD BENÖTIGT UM HERAUSZUFINDEN WELCHE MAPTILE ANGEKLICK WURDE
	//ODER OB MAN DARÜBER LAUFEN KANN
	private Rectangle hitPlace;
	
	public SuperClass(int xPos, int yPos, int xSize, int ySize, boolean abbaubar) {
		
		this.xPos = xPos;
		this.yPos = yPos;
		this.xSize = xSize;
		this.ySize = ySize;
		this.abbaubar = abbaubar;
		this.hitPlace = new Rectangle(xPos, yPos, xSize, ySize);
		
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getxSize() {
		return xSize;
	}

	public int getySize() {
		return ySize;
	}
	
	public Rectangle getHitPlace() {
		return this.hitPlace;
	}

	public boolean isAbbaubar() {
		return abbaubar;
	}

	public void setAbbaubar(boolean abbaubar) {
		this.abbaubar = abbaubar;
	}

}
