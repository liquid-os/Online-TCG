package org.author.game;

import java.awt.Point;
import java.util.Random;


public class PhysicsPlug {
	
	//Object
	AnimatedParticle obj;
	Camera cam;
	Random rand = new Random();
	boolean fixate = false, execute = true;
	int scrollBorderY = 100, scrollBorderYTop = Properties.height/2;
	boolean resist = true;
	
	public PhysicsPlug(AnimatedParticle obj){
		this.obj = obj;
	}
	
	public PhysicsPlug(Camera c){
		this.cam = c;
	}
	
	public PhysicsPlug(AnimatedParticle obj, boolean fixCam){
		this.obj = obj;
		this.fixate = fixCam;
	}
	
	public PhysicsPlug setMotionCap(int cap){
		this.motionCap = cap;
		return this;
	}
	
	public int getMotionCap(){
		int mc = motionCap;
		return 100000;
	}
	
	//Mass in KG
	float mass = 25;
	//Velocity in m/s
	int velocity = 3;
	//Acceleration in m/s/s
	int acceleration = 4;
	//Gravity is g's
	float gravity = Properties.gravity;
	//Y motion
	int motionY = 0;
	//X motion
	int motionX = 0;
	public int div = 10;
	//Maximum motion either end of spectrum
	int motionCap = 100;
	
	public int getScrollBorderY(boolean bottom){
		if(!bottom){
			return 240;
		}
		return scrollBorderY;
	}
	
	public void execute(double delta){
		if(execute){
			if(motionX > 0){
				if(motionX > getMotionCap() && getMotionCap() != -1)motionX = getMotionCap();
				--motionX;
				obj.posX += (motionX/div>1?motionX/div:1);
				if(resist)--motionX;
			}
			if(motionX < 0){
				if(motionX < -getMotionCap() && getMotionCap() != -1)motionX = -getMotionCap();
				++motionX;
				obj.posX -= (Math.abs(motionX)/div>1?Math.abs(motionX)/div:1);
				if(resist)++motionX;
			}
			if(motionY > 0){
				if(resist)--motionY;
				obj.posY -= (motionY/div>1?motionY/div:1);
			}
			if(motionY < 0){
				if(resist)++motionY;
				obj.posY += (Math.abs(motionY)/div>1?Math.abs(motionY)/div:1);
			}
		}
	}

	private void scrollLeft(int i) {
		Display.cam.x-=i;
	}
	
	private void scrollUp(int i) {
		Display.cam.y-=i;
	}
	
	private void scrollRight(int i) {
		Display.cam.x+=i;
	}
	
	private void scrollDown(int i) {
		Display.cam.y+=i;
	}
}
