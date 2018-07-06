package org.author.game;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class GridBlock {
	
	private byte id = 0, shape = 0;
	private boolean psolid = false, osolid, canBreak = false;
	static GridBlock[] all = new GridBlock[200];
	private Image image;
	private String name;
	
	public static final GridBlock air = new GridBlock("Air", 0, null).setSolid(false);
	public static final GridBlock ground = new GridBlock("Dirt", 1, Bank.dirt).setSolid(true);
	public static final GridBlock water = new GridBlock("Water", 2, Bank.water).setSolid(true);
	public static final GridBlock grass = new GridBlock("Grass", 3, Bank.grass).setSolid(true);
	public static final GridBlock dirtTopLeft = new GridBlock("Grass", 4, Bank.dirtTopLeft).setSolid(true);
	public static final GridBlock dirtTop = new GridBlock("Grass", 5, Bank.dirtTop).setSolid(true);
	public static final GridBlock dirtTopRight = new GridBlock("Grass", 6, Bank.dirtTopRight).setSolid(true);
	public static final GridBlock dirtLeft = new GridBlock("Grass", 7, Bank.dirtLeft).setSolid(true);
	public static final GridBlock dirtRight = new GridBlock("Grass", 8, Bank.dirtRight).setSolid(true);
	public static final GridBlock dirtBotLeft = new GridBlock("Grass", 9, Bank.dirtBotLeft).setSolid(true);
	public static final GridBlock dirtBot = new GridBlock("Grass", 10, Bank.dirtBot).setSolid(true);
	public static final GridBlock dirtBotRight = new GridBlock("Grass", 11, Bank.dirtBotRight).setSolid(true);
	public static final GridBlock corrupted = new GridBlock("Void", 12, Bank.voidtile).setSolid(true);
	public static final GridBlock boulder = new GridBlock("Boulder", 13, Bank.bouldertile).setSolid(true);
	public static final GridBlock sand = new GridBlock("Sand", 14, Bank.sand).setSolid(true);
	public static final GridBlock scorched = new GridBlock("Scorched", 15, Bank.scorched).setSolid(true);
	public static final GridBlock voidtile = new GridBlock("Corruption", 16, Bank.voidtile).setSolid(true);
	
	
	public GridBlock(String name, int id, Image img){
		this.id = (byte) id;
		this.setName(name);
		this.image = img;
		Grid.imgs[id] = img;
		all[id] = this;
	}
	
	public Image getImage(){
		return image;
	}
	
	public GridBlock setImage(BufferedImage img){
		this.image = img;
		return this;
	}

	private GridBlock setPlayerSolid(boolean f){
		psolid = f;
		return this;
	}
	
	private GridBlock setObjSolid(boolean f){
		osolid = f;
		return this;
	}
	
	private GridBlock setSolid(boolean f){
		psolid = f;
		osolid = f;
		return this;
	}
	
	public boolean playerSolid(){
		return psolid;
	}
	
	public boolean objSolid(){
		return osolid;
	}
	
	public int getID(){
		return this.id;
	}

	public void onUpdate(Grid grid, int x, int i) {
	}

	public boolean getBreakable() {
		return canBreak;
	}

	public void onBreak(Unit p, Grid grid, int x, int y) {
	}

	public void onTurnChange(int x, int y, int roomID){
		Unit tar = Display.currentScreen.getUnitOnTile(x, y, roomID);
		if(this == scorched){
			if(tar!=null){
				tar.hurt(2, tar);
				if(!Bank.isServer())
					Animation.createStaticAnimation(tar, "FIRE", 2, 500);
			}
		}
		if(this == voidtile){
			if(tar!=null){
				if(tar.getAbilities().contains(CardAbility.voidimmune)){
					tar.heal(3);
				}else{
					tar.hurt(3, tar);
				}
				if (Bank.isServer()) {
					Bank.server.getGrid(roomID).setTileID(x, y, GridBlock.ground.getID());
				} else {
					Animation.createStaticAnimation(tar, "VOIDFLAME", 2, 500);
					((PanelBoard) Display.currentScreen).getGrid().setTileID(x, y, GridBlock.ground.getID());
				}
			}
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getShape() {
		return shape;
	}

	public GridBlock setShape(byte i) {
		this.shape = i;
		return this;
	}
}
