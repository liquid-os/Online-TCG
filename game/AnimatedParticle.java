package org.author.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

public class AnimatedParticle extends Animation{
	

	PhysicsPlug phys = new PhysicsPlug(this);
	private ArrayList<Image> frames = new ArrayList<Image>();
	private boolean shrinks = false;

	public AnimatedParticle(int x, int y, int startSize, int maxTime) {
		super(x, y, maxTime, 0);
		width = startSize;
		height = startSize;
	}
	
	public void fillFromSheet(BufferedImage[] sheet){
		for(int i = 0; i < sheet.length; ++i){
			if(sheet[i]!=null){
				getFrames().add(sheet[i]);
			}
		}
	}
	
	public void fillFromSheet(String sheet){
		if(!Bank.isServer()){
			BufferedImage bi = (BufferedImage) Filestream.getImageFromPath(sheet);
			int x = 5, y = bi.getHeight() / 192;
			BufferedImage[] imgs = new BufferedImage[x * y];
			for (int i = 0; i < y; i++){
			    for (int j = 0; j < x; j++){
			    	imgs[(i * x) + j] = (bi).getSubimage(j*192,i*192,192,192);
			    }
			}
			this.fillFromSheet(imgs);
		}
	}
	
	public void fillFromFolder(String subfolder){
		if(!Bank.isServer()){
			try {
				System.out.println("res/"+subfolder);
				URI uri = Filestream.class.getResource("res/"+subfolder).toURI();
				File folder = new File(uri);
				File[] files = folder.listFiles();
				for(File f : files){
					BufferedImage bi = (BufferedImage) Filestream.getImageFromPath(subfolder+"/"+f.getName());
					this.frames.add(bi);
				}
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static BufferedImage[] getSheet(String sheet){
				BufferedImage bi = (BufferedImage) Filestream.getImageFromPath(sheet);
				int x = 5, y = bi.getHeight() / 192;
				BufferedImage[] imgs = new BufferedImage[x * y];
				for (int i = 0; i < y; i++){
				    for (int j = 0; j < x; j++){
				    	imgs[(i * x) + j] = (bi).getSubimage(j*192,i*192,192,192);
				    }
				}
				return imgs;
	}
	
	public AnimatedParticle addFrameSet(String name){
		if(name.contains("fx")){
			fillFromFolder(name);
		}
		if(name.equalsIgnoreCase("blood")){
			fillFromSheet("blood.png");
		}
		if(name.equalsIgnoreCase("earth")){
			fillFromSheet("earth.png");
		}
		if(name.equalsIgnoreCase("voidflame")){
			fillFromSheet("voidflame.png");
		}
		if(name.equalsIgnoreCase("flash")){
			fillFromSheet("flash.png");
		}
		if(name.equalsIgnoreCase("healcolumn")){
			fillFromSheet("healcolumn.png");
		}
		if(name.equalsIgnoreCase("shield")){
			fillFromSheet("shield.png");
		}
		if(name.equalsIgnoreCase("shine")){
			fillFromSheet("shine.png");
		}
		if(name.equalsIgnoreCase("stone")){
			fillFromSheet("stoneparticle.png");
		}
		if(name.equalsIgnoreCase("greenstone")){
			fillFromSheet("greenstone.png");
		}
		if(name.equalsIgnoreCase("skullgas")){
			fillFromSheet("skullgas.png");
		}
		if(name.equalsIgnoreCase("watercolumn")){
			fillFromSheet("watercolumn.png");
		}
		if(name.equalsIgnoreCase("bluelight")){
			fillFromSheet("bluelight.png");
		}
		if(name.equalsIgnoreCase("explosion")){
			fillFromSheet("explosion.png");
		}
		if(name.equalsIgnoreCase("boon")){
			fillFromSheet("boon.png");
		}
		if(name.equalsIgnoreCase("leaves")){
			fillFromSheet("leaves.png");
		}
		if(name.equalsIgnoreCase("magic")){
			fillFromSheet("magic.png");
		}
		if(name.equalsIgnoreCase("pool")){
			fillFromSheet("pool.png");
		}
		if(name.equalsIgnoreCase("manaburn")){
			fillFromSheet("manaburn.png");
		}
		if(name.equalsIgnoreCase("slash")){
			fillFromSheet("slash.png");
		}
		if(name.equalsIgnoreCase("splash")){
			fillFromSheet("splash.png");
		}
		if(name.equalsIgnoreCase("burst")){
			fillFromSheet("burst.png");
		}
		if(name.equalsIgnoreCase("thunder")){
			fillFromSheet("thunder.png");
		}
		if(name.equalsIgnoreCase("nova")){
			fillFromSheet("nova.png");
		}
		if(name.equalsIgnoreCase("moonflare") || name.equalsIgnoreCase("moon")){
			fillFromSheet("moonflare.png");
		}
		if(name.equalsIgnoreCase("heal")){
			fillFromSheet("heal.png");
		}
		if(name.equalsIgnoreCase("incinerate")){
			fillFromSheet("incinerate.png");
		}
		if(name.equalsIgnoreCase("rune")){
			fillFromSheet("rune.png");
		}
		if(name.equalsIgnoreCase("void")){
			fillFromSheet("void.png");
		}
		if(name.equalsIgnoreCase("holy")){
			fillFromSheet("holycast.png");
		}
		if(name.equalsIgnoreCase("felfire")){
			fillFromSheet("felfire.png");
		}
		if(name.equalsIgnoreCase("felmagic")){
			fillFromSheet("felmagic.png");
		}
		if(name.equalsIgnoreCase("bwnova")){
			fillFromSheet("bwnova.png");
		}
		if(name.equalsIgnoreCase("freeze")){
			fillFromSheet("freezeanim.png");
		}
		if(name.equalsIgnoreCase("gale")){
			fillFromSheet("gale.png");
		}
		if(name.equalsIgnoreCase("charge")){
			fillFromSheet("chargeanim.png");
		}
		if(name.equalsIgnoreCase("voidcast")){
			fillFromSheet("voidcast.png");
		}
		if(name.equalsIgnoreCase("fire")){
			getFrames().add(Bank.particleFire_0);
			getFrames().add(Bank.particleFire_1);
			getFrames().add(Bank.particleFire_2);
			getFrames().add(Bank.particleFire_3);
			getFrames().add(Bank.particleFire_4);
			getFrames().add(Bank.particleFire_5);
			getFrames().add(Bank.particleFire_6);
			getFrames().add(Bank.particleFire_7);
		}
		if(name.equalsIgnoreCase("energy")){
			getFrames().add(Bank.energyEmpty);
			getFrames().add(Bank.energyFull);
			getFrames().add(Bank.energyEmpty);
			getFrames().add(Bank.energyFull);
			getFrames().add(Bank.energyEmpty);
			getFrames().add(Bank.energyFull);
			getFrames().add(Bank.energyEmpty);
			getFrames().add(Bank.energyFull);
			getFrames().add(Bank.energyEmpty);
			getFrames().add(Bank.energyFull);
			getFrames().add(Bank.energyEmpty);
			getFrames().add(Bank.energyFull);
		}
		if(name.equalsIgnoreCase("sleep")){
			getFrames().add(Bank.z);
			getFrames().add(Bank.z);
			getFrames().add(Bank.z);
			getFrames().add(Bank.z);
			getFrames().add(Bank.z);
			getFrames().add(Bank.z);
			getFrames().add(Bank.z);
			getFrames().add(Bank.z);
			getFrames().add(Bank.z);
			this.shrinks = true;
		}
		if(name.equalsIgnoreCase("skull")){
			setShrinks(true);
			getFrames().add(Bank.particleSkull_0);
			getFrames().add(Bank.particleSkull_1);
			getFrames().add(Bank.particleSkull_2);
			getFrames().add(Bank.particleSkull_3);
			getFrames().add(Bank.particleSkull_4);
			getFrames().add(Bank.particleSkull_5);
			getFrames().add(Bank.particleSkull_6);
			getFrames().add(Bank.particleSkull_0);
			getFrames().add(Bank.particleSkull_1);
			getFrames().add(Bank.particleSkull_2);
			getFrames().add(Bank.particleSkull_3);
			getFrames().add(Bank.particleSkull_4);
			getFrames().add(Bank.particleSkull_5);
			getFrames().add(Bank.particleSkull_6);
			getFrames().add(Bank.particleSkull_0);
			getFrames().add(Bank.particleSkull_1);
			getFrames().add(Bank.particleSkull_2);
			getFrames().add(Bank.particleSkull_3);
			getFrames().add(Bank.particleSkull_4);
			getFrames().add(Bank.particleSkull_5);
			getFrames().add(Bank.particleSkull_6);
		}
		if(name.equalsIgnoreCase("iron")){
			getFrames().add(Bank.particleIron_0);
			getFrames().add(Bank.particleIron_1);
			getFrames().add(Bank.particleIron_2);
			getFrames().add(Bank.particleIron_3);
			getFrames().add(Bank.particleIron_4);
			getFrames().add(Bank.particleIron_5);
			getFrames().add(Bank.particleIron_6);
			getFrames().add(Bank.particleIron_7);
			getFrames().add(Bank.particleIron_8);
			getFrames().add(Bank.particleIron_9);
			getFrames().add(Bank.particleIron_10);
			getFrames().add(Bank.particleIron_11);
			getFrames().add(Bank.particleIron_12);
			getFrames().add(Bank.particleIron_13);
			getFrames().add(Bank.particleIron_14);
		}
		if(name.equalsIgnoreCase("bloom")){
			getFrames().add(Bank.particleBloom_0);
			getFrames().add(Bank.particleBloom_1);
			getFrames().add(Bank.particleBloom_2);
			getFrames().add(Bank.particleBloom_3);
			getFrames().add(Bank.particleBloom_4);
			getFrames().add(Bank.particleBloom_5);
			getFrames().add(Bank.particleBloom_6);
			getFrames().add(Bank.particleBloom_7);
		}
		if(name.equalsIgnoreCase("shatter")){
			getFrames().add(Bank.particleShatter_0);
			getFrames().add(Bank.particleShatter_1);
			getFrames().add(Bank.particleShatter_2);
			getFrames().add(Bank.particleShatter_3);
			getFrames().add(Bank.particleShatter_4);
			getFrames().add(Bank.particleShatter_5);
			getFrames().add(Bank.particleShatter_6);
			getFrames().add(Bank.particleShatter_7);
		}
		if(name.equalsIgnoreCase("swirl")){
			getFrames().add(Bank.particleSwirl_0);
			getFrames().add(Bank.particleSwirl_1);
			getFrames().add(Bank.particleSwirl_2);
			getFrames().add(Bank.particleSwirl_3);
			getFrames().add(Bank.particleSwirl_4);
			getFrames().add(Bank.particleSwirl_5);
		}
		if(name.equalsIgnoreCase("bubble")){
			getFrames().add(Bank.particleBubble_0);
			getFrames().add(Bank.particleBubble_1);
			getFrames().add(Bank.particleBubble_2);
			getFrames().add(Bank.particleBubble_3);
		}
		return this;
	}

	public void resetY(){
		this.phys.motionY = (phys.motionY > 0 ? 20 : -20)+(getRand().nextInt(8)-getRand().nextInt(8));
	}
	
	public AnimatedParticle setMotions(int x, int y){
		this.phys.motionX = x;
		this.phys.motionY = y;
		return this;
	}
	
	
	public void updateBase(){
		int index = (int) ((System.currentTimeMillis()-this.getStart()) * getFrames().size() / getMaxTime());
		phys.execute(0);
		if(System.currentTimeMillis()-this.getStart() > getMaxTime() || Bank.isServer())this.kill();
	}
	
	public void draw(Graphics g, int x, int y) {
		int index = (int) ((System.currentTimeMillis()-this.getStart()) * getFrames().size() / getMaxTime());
		int aw = width, ah = height;
		if(isShrinks()){
			aw = width-(int) ((System.currentTimeMillis()-this.getStart()) * width / getMaxTime());
			ah = height-(int) ((System.currentTimeMillis()-this.getStart()) * width / getMaxTime());
		}
		if(index >= getFrames().size())index=getFrames().size()-1;
		if(index >= 0)
		g.drawImage((getFrames().get(index) == null ? getFrames().get(0) : getFrames().get(index)), posX+width/2-aw/2, posY+height/2-ah/2, aw, ah, null);
	}

	public boolean isShrinks() {
		return shrinks;
	}

	public AnimatedParticle setShrinks(boolean shrinks) {
		this.shrinks = shrinks;
		return this;
	}

	public ArrayList<Image> getFrames() {
		return frames;
	}

	public void setFrames(ArrayList<Image> frames) {
		this.frames = frames;
	}
}
