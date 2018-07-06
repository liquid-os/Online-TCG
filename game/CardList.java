package org.author.game;

import java.awt.Image;
import java.util.ArrayList;

public class CardList {
	
	private ArrayList<Card> list = new ArrayList<Card>();
	private String name = null;
	private Image icon = null;
	
	public CardList(String name, Image icon){
		this.setName(name);
		this.setIcon(icon);
	}

	public static final CardList common = new CardList("Basic", Bank.ball);
	public static final CardList northgarde = new CardList("The Northgarde", Bank.logoNorthgarde);
	public static final CardList eboncreed = new CardList("The Ebon Creed", Bank.logoEboncreed);
	public static final CardList steamguild = new CardList("The Steam Guild", Bank.logoSteamguild);
	public static final CardList orklad = new CardList("The Orklad", Bank.logoOrklad);
	public static final CardList legion = new CardList("The Azashi Legion", Bank.logoLegion);
	public static final CardList basic = new CardList("Basic Set", Bank.ball);
	
	public static void initLists(){
		eboncreed.add(Card.commArachna);
		eboncreed.add(Card.commOzaaroth);
		eboncreed.add(Card.commDiscilla);
		eboncreed.add(Card.imp);
		eboncreed.add(Card.khovani);
		eboncreed.add(Card.executioner);
		eboncreed.add(Card.infernoguard);
		eboncreed.add(Card.skeletonking);
		//eboncreed.add(Card.atrophy);
		eboncreed.add(Card.drone);
		eboncreed.add(Card.ravenseye);
		eboncreed.add(Card.ascended);
		eboncreed.add(Card.headlessprince);
		eboncreed.add(Card.devourer);
		eboncreed.add(Card.nightmare);
		eboncreed.add(Card.necromancer);
		eboncreed.add(Card.rageofkholos);
		eboncreed.add(Card.wither);
		eboncreed.add(Card.voidshell);
		eboncreed.add(Card.curse);
		eboncreed.add(Card.voidcurse);
		eboncreed.add(Card.apocalypse);
		eboncreed.add(Card.upheaval);
		eboncreed.add(Card.sacrifice);
		eboncreed.add(Card.assault);
		eboncreed.add(Card.madness);
		eboncreed.add(Card.shadowgate);
		eboncreed.add(Card.maru);
		eboncreed.add(Card.stateKholosian);
		eboncreed.add(Card.equipmentAxeVoid);
		eboncreed.add(Card.equipmentScrollDemon);
		eboncreed.add(Card.darkvisions);
		eboncreed.add(Card.chaosNova);
		eboncreed.add(Card.enterVoid);
		eboncreed.add(Card.mindsurge);
		eboncreed.add(Card.fleshoffire);
		eboncreed.add(Card.demonicritual);
		eboncreed.add(Card.markets);
		eboncreed.add(Card.holdingtome);
		eboncreed.add(Card.defile);
		eboncreed.add(Card.defiler);
		eboncreed.add(Card.blackblessing);
		eboncreed.add(Card.voidtome);
		
		northgarde.add(Card.commHera);
		northgarde.add(Card.commYorg);
		northgarde.add(Card.commRomar);
		northgarde.add(Card.witchhunter);
		northgarde.add(Card.blessingJustice);
		northgarde.add(Card.blessingPower);
		northgarde.add(Card.icegoblin);
		northgarde.add(Card.polymage);
		northgarde.add(Card.yeti);
		northgarde.add(Card.hornEkeziel);
		northgarde.add(Card.emperorTannis);
		northgarde.add(Card.yorglankhar);
		northgarde.add(Card.rebirth);
		northgarde.add(Card.empathy);
		northgarde.add(Card.coldsnap);
		northgarde.add(Card.divine);
		northgarde.add(Card.shatter);
		northgarde.add(Card.flameburst);
		northgarde.add(Card.crypt);
		northgarde.add(Card.stateBlizzard);
		northgarde.add(Card.equipmentHammerDwarf);
		northgarde.add(Card.equipmentStaffLight);
		northgarde.add(Card.prayer);
		northgarde.add(Card.grizzly);
		northgarde.add(Card.northwind);
		northgarde.add(Card.icicle);
		northgarde.add(Card.spiritbomb);
		northgarde.add(Card.finalstand);
		northgarde.add(Card.holybind);
		northgarde.add(Card.coldsnap);
		northgarde.add(Card.frost);
		northgarde.add(Card.bladequest);
		northgarde.add(Card.blade);
		northgarde.add(Card.bloodsurge);
		northgarde.add(Card.necrotome);
		northgarde.add(Card.dragoncall);
		northgarde.add(Card.shout);
		northgarde.add(Card.trap);
		northgarde.add(Card.mammoth);
		northgarde.add(Card.ancient);
		northgarde.add(Card.volley);
		northgarde.add(Card.spellshield);
		northgarde.add(Card.arbiter);
		northgarde.add(Card.voidbreath);
		northgarde.add(Card.markofice);
		northgarde.add(Card.spiritlink);
		
		steamguild.add(Card.commChristy);
		steamguild.add(Card.commRangrim);
		steamguild.add(Card.commStread);
		steamguild.add(Card.colossus);
		steamguild.add(Card.batterybot);
		steamguild.add(Card.bombbot);
		steamguild.add(Card.goliath);
		steamguild.add(Card.artillerybot);
		//steamguild.add(Card.summon);
		steamguild.add(Card.engineer);
		steamguild.add(Card.bomb);
		steamguild.add(Card.train);
		steamguild.add(Card.revolution);
		steamguild.add(Card.exhaust);
		steamguild.add(Card.firejet);
		steamguild.add(Card.nullpointer);
		steamguild.add(Card.bombingrun);
		steamguild.add(Card.stateRevolution);
		steamguild.add(Card.equipmentClock);
		steamguild.add(Card.equipmentTeacherGauntlet);
		steamguild.add(Card.qCannoneer);
		steamguild.add(Card.qSniper);
		steamguild.add(Card.brainwash);
		steamguild.add(Card.strangulate);
		steamguild.add(Card.destruction);
		steamguild.add(Card.weaponspell);
		steamguild.add(Card.darkvisions);
		steamguild.add(Card.quickshot);
		steamguild.add(Card.engine);
		steamguild.add(Card.mechknight);
		steamguild.add(Card.steamstrike);
		steamguild.add(Card.engine);
		steamguild.add(Card.execute);
		steamguild.add(Card.moltenhammer);
		steamguild.add(Card.mechknight);
		steamguild.add(Card.mechboar);
		steamguild.add(Card.taurtank);
		steamguild.add(Card.pyromancer);
		steamguild.add(Card.eel);
		steamguild.add(Card.agleanoracle);
		steamguild.add(Card.scientist);
		steamguild.add(Card.revenge);
		steamguild.add(Card.steamgolem);

		orklad.add(Card.commBashar);
		orklad.add(Card.commShanzin);
		orklad.add(Card.commQuanir);
		orklad.add(Card.zinsamurai);
		orklad.add(Card.duskwalkerSeer);
		orklad.add(Card.charge);
		orklad.add(Card.swine);
		orklad.add(Card.boarwrangler);
		orklad.add(Card.hogrider);
		orklad.add(Card.mimic);
		orklad.add(Card.jungleKing);
		orklad.add(Card.wartusk);
		orklad.add(Card.spiritdancer);
		orklad.add(Card.sogarokswill);
		orklad.add(Card.sogarok);
		orklad.add(Card.train1);
		orklad.add(Card.earth);
		orklad.add(Card.electrify);
		orklad.add(Card.storm);
		orklad.add(Card.obliterate);
		orklad.add(Card.raid);
		orklad.add(Card.stormblaze);
		orklad.add(Card.stateMalestrom);
		orklad.add(Card.equipmentStonemaul);
		orklad.add(Card.equipmentBladeVenom);
		orklad.add(Card.bloodwitch);
		orklad.add(Card.swamptroll);
		orklad.add(Card.greenpotion);
		orklad.add(Card.song);
		orklad.add(Card.greenrelic);
		orklad.add(Card.sculpture);
		orklad.add(Card.roar);
		orklad.add(Card.tigerhound);
		orklad.add(Card.bladecharm);
		orklad.add(Card.boarcharge);
		orklad.add(Card.tyranosaur);
		orklad.add(Card.volcano);
		orklad.add(Card.gorillaProtector);
		orklad.add(Card.raptorGreen);
		orklad.add(Card.croc);
		orklad.add(Card.tortoise);
		orklad.add(Card.ragetusk);
		orklad.add(Card.orchorde);
		
		legion.add(Card.commTikesh);
		legion.add(Card.commJaram);
		legion.add(Card.koashira);
		legion.add(Card.sandgolem);
		legion.add(Card.sandElemental);
		legion.add(Card.timegod);
		legion.add(Card.snakeWarrior);
		legion.add(Card.deflect);
		legion.add(Card.legend);
		legion.add(Card.elite);
		legion.add(Card.equipmentSlayer);
		legion.add(Card.mummycommander);
		legion.add(Card.mummymage);
		legion.add(Card.mummyemperor);
		legion.add(Card.nagapriestess);
		legion.add(Card.shady);
		legion.add(Card.duskstriders);
		legion.add(Card.desertdino);
		legion.add(Card.useless);
		legion.add(Card.tracking);
		legion.add(Card.spelldecay);
		legion.add(Card.tombtoxin);
		legion.add(Card.zerdurim);
		legion.add(Card.timekeeper);
		legion.add(Card.filthwater);
		legion.add(Card.equipmentDaggers);
		
		for(int i = 0; i < Card.all.length; ++i){
			Card c = Card.all[i];
			if(c!=null){
				if(c.isListable()){
					boolean inOtherList = false;
					if(northgarde.list.contains(c)){inOtherList = true;}
					if(eboncreed.list.contains(c)){inOtherList = true;}
					if(steamguild.list.contains(c)){inOtherList = true;}
					if(orklad.list.contains(c)){inOtherList = true;}
					if(legion.list.contains(c)){inOtherList = true;}
					if(!inOtherList)common.add(c);
				}
			}
		}
		for(Card c : orklad.list){
			if(c.getSet() == CardSet.SET_CLASSIC){
				System.out.println(c.getName()+" moved to set "+CardSet.SET_TRIBELANDS.getName());
				c.setSet(CardSet.SET_TRIBELANDS);
			}
		}
		for(Card c : legion.list){
			if(c.getSet() == CardSet.SET_CLASSIC){
				System.out.println(c.getName()+" moved to set "+CardSet.SET_ANURIM.getName());
				c.setSet(CardSet.SET_ANURIM);
			}
		}
	}
	
	public static void flushCards(int version){
		for(Card c : Card.all){
			if(c!=null){
				if(c.getSet() != null){
					if(c.getSet().getVersion() > Util.version){
						Card.all[c.getId()] = null;
					}
				}
			}
		}
	}
	
	public static CardList getListForCard(Card c){
		if(northgarde.list.contains(c))return northgarde;
		if(eboncreed.list.contains(c))return eboncreed;
		if(steamguild.list.contains(c))return steamguild;
		if(orklad.list.contains(c))return orklad;
		if(legion.list.contains(c))return legion;
		if(basic.list.contains(c))return basic;
		return common;
	}
	
	public CardList add(Card c){
		list.add(c);
		if(this == orklad && c.getSet().getVersion() < CardSet.SET_TRIBELANDS.getVersion())
			c.setSet(CardSet.SET_TRIBELANDS);
		if(this == legion && c.getSet().getVersion() < CardSet.SET_ANURIM.getVersion())
			c.setSet(CardSet.SET_ANURIM);
		return this;
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
