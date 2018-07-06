package org.author.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class GridPanel extends PanelBase{
	public Grid grid, scene, fore;
	public String name = "World";
	boolean renderScene = true, renderGrid = true, renderForeground = false;
	int tileSize = 0;
	
	public GridPanel(Grid g){
		this.tileSize = g.getTileSize();
		this.grid = new Grid(g.getCore()[0].length, g.getCore()[1].length, this);
	}
	public abstract void onUpdate();
	public final void drawScreen(Graphics g){
	}
	public abstract void renderScene(Graphics g);
	public void renderForeground(Graphics g){}
	
	public Grid getGrid(){
		return this.grid;
	}
	public Grid getScene(){
		return this.scene;
	}
	public Grid getForeground(){
		return this.fore;
	}
	public void save(File f){
		String save = "0%";
		File file = f;
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		save+=grid.save(this, "")+"%";
		save+=scene.save(this, "")+"%";
		save+=fore.save(this, "")+"%";
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.print(save);
			writer.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	public void load(String name, boolean str){
		grid.load(name, 1, str);
		scene.load(name, 2, str);
		fore.load(name, 3, str);
		loadDat(name, str);
		this.name = name;
	}
	
	public void load(String name){
		grid.load(name, 1);
		scene.load(name, 2);
		fore.load(name, 3);
		loadDat(name, false);
		this.name = name;
	}
	
	public void loadRaw(File f){
		grid.loadRaw(f, this, 1);
		scene.loadRaw(f, this, 2);
		fore.loadRaw(f, this, 3);
	}
	
	public void loadDat(String n, boolean str){
		ArrayList<Point> ret = new ArrayList<Point>();
		String s = (str?n:(Bank.path+"maps/"+n+".HB"));
		String spl = s.split("%")[0];
		if(spl!=null){
			String[] dats = spl.split(";");
			if(dats!=null){
				for(int i = 0; i < dats.length; ++i){
					if(dats[i]!=null){
						if(dats[i].contains("=")){
							String cmd = dats[i].split("=")[0];
							String[] args = dats[i].split("=")[1].split(",");
							if(cmd.equalsIgnoreCase("addspawn")){
								ret.add(new Point(Integer.parseInt(args[0]), Integer.parseInt(args[1])));
							}
						}
					}
				}
			}
		}
	}
}
