package org.author.game;

import java.awt.Graphics;

public class Effect {

	private EffectType effectType;
	private int val, duration, durationMax, x, y;
	private boolean infinite = false;
	
	public Effect(EffectType e, int val, int duration){
		this.setEffectType(e);
		this.setVal(val);
		if(duration==-1){
			this.setDuration(e.getDuration());
			this.durationMax = e.getDuration();
		}else{
			this.setDuration(duration);
			this.durationMax = duration;
		}
		if(this.getDuration() == 999){
			setInfinite(true);
		}
	}
	
	public void draw(Graphics g){
		if(effectType == EffectType.atrophy){
		}
	}
	
	public void onTurnChange(Unit u, int turn){
		if(!infinite){
			setDuration(getDuration() - 1);
			if(getDuration() <= 0)remove(u);
		}
	}	
	
	public String getDurationText(){
		return (isInfinite()? "Infinite" : (getDuration())+"");
	}
	
	public void remove(Unit u){
		u.removeEffect(this);
	}

	public EffectType getEffectType() {
		return effectType;
	}

	public void setEffectType(EffectType effectType) {
		this.effectType = effectType;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}

	public boolean isInfinite() {
		return infinite;
	}

	public void setInfinite(boolean infinite) {
		this.infinite = infinite;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
