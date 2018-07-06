package org.author.game;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PluginEvent {
	
	static byte 
		EVENT_SPAWN_UNIT = 0, //templateID, ownerID, x, y, chance
		EVENT_DRAW_CARD = 1, //cardID, playerID, chance
		EVENT_CHANGE_BLOCK = 2, //tileID, x, y, chance
		EVENT_PLAY_CARD = 3; //cardID, ownerID, x, y, chance
	
	static byte TRIGGER_TURNSTART = 0, TRIGGER_EVERYTURNSTART = 2;
	
	private ArrayList<String> args = new ArrayList<String>();
	private String triggerArg = "";
	private byte trigger = 0, event = 0;
	
	public  boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");
	}
	
	public void execute(GameServer s){
		int roomID = s.getId();
		boolean allowed = false;
		if(trigger == TRIGGER_TURNSTART){
			if(s.turn == Integer.parseInt(triggerArg) && s.playerTurn == 0){
				allowed = true;
			}
		}
		if(trigger == TRIGGER_EVERYTURNSTART)allowed = true;
		if(allowed){
			if(event == EVENT_CHANGE_BLOCK){
				GridBlock tile = null;
				if(this.isNumeric(processArgument(args.get(0))))
					tile = GridBlock.all[Integer.parseInt(processArgument(args.get(0)))];
				else{
					for(int i = 0; i < GridBlock.all.length; ++i){
						if(GridBlock.all[i]!=null){
							if(GridBlock.all[i].getName().replace(" ", "").equalsIgnoreCase(processArgument(args.get(0))))
								tile = GridBlock.all[i];
						}
					}
				}
				int x = Integer.parseInt(processArgument(args.get(1)));
				int y = Integer.parseInt(processArgument(args.get(2)));
				int chance = Integer.parseInt(processArgument(args.get(3)));
				boolean spawn = false;
				if(chance == 100)spawn = true;
				else{
					int r = Util.rand.nextInt(101);
					if(r - chance <= 0)spawn = true;
				}
				if(spawn){
					Packet13ChangeTile packet = new Packet13ChangeTile(tile.getID(), x, y);
					packet.write(s);
				}
			}
			if(event == EVENT_SPAWN_UNIT){
				UnitTemplate template = null;
				if(this.isNumeric(processArgument(args.get(0))))
					template = (UnitTemplate)Card.all[Integer.parseInt(processArgument(args.get(0)))];
				else{
					for(int i = 0; i < Card.all.length; ++i){
						if(Card.all[i]!=null){
							if(Card.all[i].getUnit() != null && Card.all[i].getName().replace(" ", "").equalsIgnoreCase(processArgument(args.get(0))))
								template = (UnitTemplate)Card.all[i];
						}
					}
				}
				int owner = Integer.parseInt(processArgument(args.get(1)));
				int x = Integer.parseInt(processArgument(args.get(2)));
				int y = Integer.parseInt(processArgument(args.get(3)));
				int chance = Integer.parseInt(processArgument(args.get(4)));
				boolean spawn = false;
				if(chance == 100)spawn = true;
				else{
					int r = Util.rand.nextInt(101);
					if(r - chance <= 0)spawn = true;
				}
				if(spawn){
					s.addUnit(template, x, y, owner);
				}
			}
			if(event == EVENT_PLAY_CARD){
				Card card = null;
				if(this.isNumeric(processArgument(args.get(0))))
					if(Card.all[Integer.parseInt(processArgument(args.get(0)))].getUnit() == null){
						card = Card.all[Integer.parseInt(processArgument(args.get(0)))];
					}else{
						Bank.server.getLog().add("> ERROR - Unit card attempting to be played via 'playCard' command. Use 'spawn' instead.");
					}
				else{
					for(int i = 0; i < Card.all.length; ++i){
						if(Card.all[i]!=null){
							if(Card.all[i].getName().replace(" ", "").equalsIgnoreCase(processArgument(args.get(0)))){
								if(Card.all[i].getUnit() == null){
									card = Card.all[i];
								}else{
									Bank.server.getLog().add("> ERROR - Unit card attempting to be played via 'playCard' command. Use 'spawn' instead.");
								}
							}
						}
					}
				}
				int owner = Integer.parseInt(processArgument(args.get(1)));
				int x = Integer.parseInt(processArgument(args.get(2)));
				int y = Integer.parseInt(processArgument(args.get(3)));
				if(x == -1 && y == -1){
					ArrayList<Unit> avail = new ArrayList<Unit>();
					for(int i = 0; i < s.units.size(); ++i){
						Unit u = s.units.get(i);
						if(u!=null){
							if(!u.isRemoving()){
								avail.add(u);
							}
						}
					}
					if(avail.size() > 0){
						int r = Util.rand.nextInt(avail.size());
						Unit sel = avail.get(r);
						x = sel.posX;
						y = sel.posY;
					}
				}
				int chance = Integer.parseInt(processArgument(args.get(4)));
				boolean spawn = false;
				if(chance == 100)spawn = true;
				else{
					int r = Util.rand.nextInt(101);
					if(r - chance <= 0)spawn = true;
				}
				if(spawn){
					card.onPlayed(0, x, y, -1, owner, s.roomID);
					Packet02PlayCard packet = new Packet02PlayCard(owner, card.getId(), x, y, -1);
					packet.write(s);
				}
			}
			if(event == EVENT_DRAW_CARD){
				Card card = null;
				if(this.isNumeric(processArgument(args.get(0))))
					card = Card.all[Integer.parseInt(processArgument(args.get(0)))];
				else{
					for(int i = 0; i < Card.all.length; ++i){
						if(Card.all[i]!=null){
							if(Card.all[i].getName().equalsIgnoreCase(processArgument(args.get(0))))
								card = Card.all[i];
						}
					}
				}				
				int owner = Integer.parseInt(processArgument(args.get(1)));
				int chance = Integer.parseInt(processArgument(args.get(2)));
				boolean draw = false;
				if(chance == 100)draw = true;
				else{
					int r = Util.rand.nextInt(101);
					if(r - chance <= 0)draw = true;
				}
				if(draw){
					s.drawCard(s.clients.get(owner), card.getId(), 1);
				}
			}
		}
	}
	
	public static PluginEvent read(String d){
		PluginEvent pe = new PluginEvent();
		String trigger = d.split("=")[0];
		if(trigger.contains("onTurn")){
			pe.trigger = TRIGGER_TURNSTART;
			pe.triggerArg = trigger.replace("onTurn", "");
		}
		if(trigger.contains("onTurnEnd")){
		//	pe.trigger = TRIGGER_TURNEND;
			pe.triggerArg = trigger.replace("onTurnEnd", "");
		}
		if(trigger.contains("onEveryTurn")){
			pe.trigger = TRIGGER_EVERYTURNSTART;
			pe.triggerArg = trigger.replace("onEveryTurn", "");
		}
		if(trigger.contains("everyTurnEnd")){
		//	pe.trigger = TRIGGER_EVERYTURNEND;
			pe.triggerArg = trigger.replace("everyTurnEnd", "");
		}
		String ev = d.split("=")[1];
		String eventType = ev.split(":")[0];
		if(eventType.equals("spawn")){
			pe.event = EVENT_SPAWN_UNIT;
			String[] localargs = ev.split(":")[1].split(",");
			for(int i = 0; i < localargs.length; ++i){
				pe.args.add(localargs[i]);
			}
		}
		if(eventType.equals("drawCard")){
			pe.event = EVENT_DRAW_CARD;
			String[] localargs = ev.split(":")[1].split(",");
			for(int i = 0; i < localargs.length; ++i){
				pe.args.add(localargs[i]);
			}
		}
		if(eventType.equals("changeTile")){
			pe.event = EVENT_CHANGE_BLOCK;
			String[] localargs = ev.split(":")[1].split(",");
			for(int i = 0; i < localargs.length; ++i){
				pe.args.add(localargs[i]);
			}
		}
		if(eventType.equals("playCard")){
			pe.event = EVENT_PLAY_CARD;
			String[] localargs = ev.split(":")[1].split(",");
			for(int i = 0; i < localargs.length; ++i){
				pe.args.add(localargs[i]);
			}
		}
		Bank.server.getLog().add("> Event: "+eventType+" added @ turn "+(pe.trigger == TRIGGER_EVERYTURNSTART ? "all" : pe.triggerArg)+" ["+ev.split(":")[1]+"]");
		return pe;
	}
	
	public static String processArgument(String a){
		String s = a;
		if(a.contains("rand")){
			int e1 = Integer.parseInt(a.replace("rand", "").split("-")[0]);
			int e2 = Integer.parseInt(a.replace("rand", "").split("-")[1]);
			int random = Util.rand.nextInt(e2 - e1) + e1;
			s = random+"";
		}
		return s;
	}
}
