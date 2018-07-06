package org.author.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Card {

	static Card[] all = new Card[1000];

	private boolean basic = false;

	private int id, cost;
	private String name, text;
	private boolean listable = true;
	private String art;
	protected int targetType = 0; // 0 = on_unit, 1 = on_ground, 2 = on_ally, 3
									// = on_enemy
	private int costRed, costBlue, costGreen, costBlack;
	static final int RANK_COMMON = 0, RANK_UNCOMMON = 1, RANK_MYTHIC = 2, RANK_REGAL = 3, RANK_COMMANDER = 4;
	private CardSet set = CardSet.SET_CLASSIC;

	public Card(int id, String name, int cost, String art) {
		this(id, name, cost, art, CardSet.SET_CLASSIC);
		this.setCost(cost);
	}

	public Card(int id, String name, int cost, String art, CardSet set) {
		this.setId(id);
		this.setName(name);
		this.setCost(cost);
		this.setArt(art);
		this.setSet(set);
		if (set.getVersion() <= Util.version) {
			all[id] = this;
		}
		this.setCost(cost);
	}

	public int getDeckLimit() {
		if (this instanceof UnitTemplate) {
			int rank = ((UnitTemplate) this).getRank();
			return (rank == RANK_MYTHIC ? 2 : rank == RANK_REGAL ? 1 : Util.CARD_TYPE_LIMIT);
		}
		return Util.SPELL_TYPE_LIMIT;
	}
	
	public Card setCosts(int r, int g, int b, int n){
		this.costRed = r;
		this.costGreen = g;
		this.costBlue = b;
		this.costBlack = n;
		return this;
	}

	public static Card getUsableCard(int r1) {
		ArrayList<Card> usable = new ArrayList<Card>();
		for (int i = 0; i < Card.all.length; ++i) {
			Card c = Card.all[i];
			if (c != null) {
				if (c.listable) {
					if (c.getUnit() != null) {
						if (c.getUnit().getRank() == UnitTemplate.RANK_COMMANDER) {
						} else {
							usable.add(c);
						}
					} else {
						usable.add(c);
					}
				}
			}
		}
		return usable.get(Util.rand.nextInt(usable.size()));
	}

	public UnitTemplate getUnit() {
		return (this instanceof UnitTemplate ? ((UnitTemplate) this) : null);
	}

	public Card setText(String t) {
		this.text = t;
		return this;
	}

	public static final Card filler = new Card(1, "Fizzled Magic", 0, "arcanerune.png").setText("Something has gone wrong.").setListable(false);
	// public static final Card weakness = new Card(42, "Curse of Weakness", 2,
	// Bank.weakness).setText("Reduces units ATTACK to 1 on a tile.");
	// public static final Card obliterate = new Card(43, "Obliterate Entity",
	// 6, Bank.obliterate).setText("Destroys any units on a tile.");
	// public static final Card upheaval = new Card(44, "Upheaval", 4,
	// Bank.upheaval).setText("Deal 1 damage in a 3x3 radius.");
	public static final Card storm = new Card(100, "Roaring Thunder", 5, "storm.png")
			.setText("Deals 3 damage in a 3x3 radius.");
	// public static final Card soothe = new Card(46, "Immense Focus", 2,
	// "soothe).setText("Draw 2 cards.");
	public static final Card imbue = new Card(101, "Imbue", 2, "imbue.png").setText("Give all your units +1 Energy.").setCosts(0, 2, 0, 0);
	// public static final Card arcanerune = new Card(48, "Arcane Rune:
	// Freedom", 2, "rune).setText("Removes effects from any units on a tile.");
	public static final Card frost = new Card(102, "Veil of Frost", 2, "freeze.png")
			.setText("Chills ALL units until your next turn.").setCosts(0, 0, 2, 0);
	public static final Card charge = new Card(103, "Charge", 1, "charge.png")
			.setText("Gives all your units +2 speed for 1 turn.");
	public static final Card coldsnap = new Card(104, "Cold Snap", 2, "arcanerot.png")
			.setText("Chills units in a 3x3 radius. Deals 2 damage if chilled already.");
	public static final Card healthpotion = new Card(105, "Potion of Healing", 2, "redpotion.png")
			.setText("Restore 3 health to a unit.").setListable(false);
	public static final Card energypotion = new Card(106, "Potion of Energy", 2, "yellowpotion.png")
			.setText("Restore 2 energy to a unit.").setListable(false);
	public static final Card weapon = new Card(107, "Discarded Weapon", 2, "weapon.png")
			.setText("Give a unit +1 attack.").setListable(false);
	public static final Card recharge = new Card(109, "Re-Charge", 2, "yellowpotion.png")
			.setText("Grant a unit 3 energy.");
	public static final Card blessingPower = new Card(110, "Fraijuct", 4, "blessing1.png")
			.setText("Grant a unit +5/+5.").setCosts(0, 0, 4, 0);
	public static final Card blessingJustice = new Card(112, "Jadibrad", 0, "blessing.png")
			.setText("Gain 2 " + Util.RESOURCE_NAME + ".");
	public static final Card hornEkeziel = new Card(113, "Horn of Ekeziel", 3, "horn.png")
			.setText("Grant units in a 3x3 radius [Heal].").setCosts(0, 1, 1, 0);
	public static final Card ravenseye = new Card(115, "Raven's Eye", 0, "ravenplague.png")
			.setText("Briefly view 3 random cards from your opponent's hand.");
	public static final Card empathy = new Card(117, "Yerulen", 5, "blessing0.png")
			.setText("Afflict a unit, causing them to take 2 damage whenever a unit dies. Draw a card.");
	public static final Card valor = new Card(118, "Word of Valor", 3, "discilla.png")
			.setText("Whenever a unit is healed, the target gains +1 ATTACK.").setListable(false);
	public static final Card tinker = new Card(119, "Tinker", 2, "stread.png")
			.setText("Add a random mechanical unit card to your hand.").setListable(false);
	public static final Card firejet = new Card(123, "Jet Blast", 3, "flamejet.png")
			.setText("Deal 2 damage to a unit. If it survives, add this card to your hand.");
	public static final Card apocalypse = new Card(124, "Apocalypse", 3, "deflect.png", CardSet.SET_WAR)
			.setText("Deal 4 damage to all units on the board.");
	public static final Card haste = new Card(125, "Ancestral Haste", 2, "haste.png")
			.setText("Increase a unit's speed by 3.");
	public static final Card obliterate = new Card(126, "Bloody Murder", 5, "rampage.png")
			.setText("Destroys a unit. Fails on commanders.");
	public static final Card rebirth = new Card(127, "Hour of Rebirth", 5, "renewal.png")
			.setText("Grants all units [Evole] and +2 Energy.");
	public static final Card sogarokswill = new Card(128, "Will of Sogarok", 7, "willsogarok.png")
			.setText("Grants all friendly units [Evole].");
	public static final Card siege = new Card(129, "Siege", 3, "siege.png", CardSet.SET_WAR)
			.setText("Deals 5 damage to ALL enemy walls and cannons.");
	public static final Card mapTreasure = new Card(130, "Treasure Map", 1, "treasuremap.png")
			.setText("Places 3 Treasure Chests randomly on the map.");
	public static final Card bomb = new Card(131, "Deploy Bomb", 2, "siege.png")
			.setText("Deploys a Bomb Bot on the selected tile.");
	public static final Card train = new Card(132, "Field Training", 2, "training.png")
			.setText("Gives a unit a random ability.");
	public static final Card revolution = new Card(133, "Revolution", 7, "revolution.png")
			.setText("Gives all units a random ability.");
	public static final Card train1 = new Card(134, "Mystic Vision", 3, "vision.png")
			.setText("Gives a unit 2 random abilities.").setCosts(0, 2, 0, 0);
	public static final Card curse = new Card(135, "Elypsian Curse", 4, "curse.png")
			.setText("Transform the target into a random undead. Fails on heroes.");
	public static final Card electrify = new Card(136, "Thundercall", 2, "lightning.png")
			.setText("Deal 3 damage to a unit and grant it 3 energy.");
	public static final Card divine = new Card(137, "Divine Surge", 3, "surge.png").setText("Heal a unit for 9.");
	public static final Card earth = new Card(138, "Gift of Earth", 2, "stoneheart.png")
			.setText("Heal a unit for 3. Any over-healing grants attack damage.");
	public static final Card flameburst = new Card(139, "Dragonfire", 3, "inferno.png")
			.setText("Deal 5 damage to a unit.").setCosts(1, 0, 0, 0);
	public static final Card voidshell = new Card(140, "Void Shell", 1, "voidshell.png")
			.setText("Give a unit +2 HP and draw a card.");
	public static final Card rageofkholos = new Card(141, "Rage of Kholos", 2, "kholosian.png")
			.setText("Deals 6 damage to a random unit.");
	public static final Card shatter = new Card(142, "Shatter", 3, "shatter.png")
			.setText("Destroy a frozen unit. Fails on heroes.");
	public static final Card exhaust = new Card(143, "Exhaust", 0, "exhaust.png").setText("Drain a unit's energy.");
	public static final Card wither = new Card(144, "Wither", 2, "wither.png")
			.setText("Reduce a unit's energy by 3. If it still has energy, deal 4 damage.");
	public static final Card voidcurse = new Card(145, "Curse of the Void", 3, "voidcurse.png")
			.setText("Transforms a unit into a random demon. Fails on heroes.");
	public static final Card nullpointer = new Card(146, "Null Pointer", 1, "null.png")
			.setText("Remove a unit's abilities. Fails on heroes.");
	public static final Card defile = new Card(147, "Judgement", 3, "defile.png")
			.setText("Remove a unit's abilities and deal 2 damage. Fails on heroes.");
	public static final Card stormblaze = new Card(148, "Stormblaze", 6, "knockout.png")
			.setText("Deal 5 damage to a unit. If it is a minion, immobilize it.");
	public static final Card bombingrun = new Card(149, "Bombing Run", 4, "bombingrun.png")
			.setText("Deal 3 damage to all enemies.");
	public static final Card volley = new Card(150, "Arrow Volley", 2, "volley.png")
			.setText("Deal 2 damage in a 3x3 radius.");
	public static final Card sacrifice = new Card(151, "Sacrifice", 2, "sacrifice.png")
			.setText("Destroy a demon and deal 1 damage to all enemies.");
	public static final Card upheaval = new Card(152, "Shatter Earth", 4, "upheaval.png")
			.setText("Deal 2 - 8 damage to a unit. If they die, also damage surround units.").setCosts(1, 1, 0, 0);
	public static final Card bluepotion = new Card(153, "Potion of Spirit", 2, "bluepotion.png").setText("Draw a card.")
			.setListable(false);
	public static final Card raid = new Card(154, "Soul Army", 10, "soularmy.png")
			.setText("Summon a random unit with cost equal to target's attack (max 7) for each of their HP (max 7).");
	public static final Card madness = new Card(155, "Curse of Madness", 5, "madness.png")
			.setText("Give all non-hero units [Confusion].");
	public static final Card crypt = new Card(156, "From the Crypts", 10, "undeadarmy.png")
			.setText("Summon 4 random UNDEAD units.");
	public static final Card assault = new Card(157, "Demonic Assault", 10, "demonarmy.png")
			.setText("Summon 3 random DEMON units.").setCosts(5, 5, 5, 0);
	public static final Card steal = new Card(158, "Dirty Tricks", 2, "steal.png")
			.setText("Copy a card from your opponent's hand.");
	public static final Card terrify = new Card(159, "Terrify", 6, "steal.png")
			.setText("Drains all energy from enemy units. Deals damage equal to energy drained. Cannot exceed (8).")
			.setListable(false);
	public static final Card stateBlizzard = new CardBoardstate(160, "Status: Ramsonian Blizzard", 3, "blizzard.png")
			.setText(
					"Applies the 'Ramsonian Blizzard' status to the board for 4 turns. During this time, all units are CHILLED for 1 turn at the end of each turn.");
	public static final Card stateHealingrain = new CardBoardstate(161, "Status: Healing Rain", 1, "healingrain.png")
			.setText(
					"Applies the 'Healing Rain' status to the board for 4 turns. During this time, all units are healed for 1 at the end of each turn.");
	public static final Card stateArcaneSurge = new CardBoardstate(162, "Status: Arcane Surge", 3, "arcanesurge.png")
			.setText(
					"Applies the 'Arcane Surge' status to the board for 4 turns. During this time, all units on the board gain +1/+1 at the end of each turn.");
	public static final Card stateSandstorm = new CardBoardstate(163, "Status: Sandstorm", 3, "sandstorm.png").setText(
			"Applies the 'Mind Storm' status to the board for 4 turns. During this time, all units will gain a random ability at the end of eacdh turn.");
	public static final Card stateHellfire = new CardBoardstate(164, "Status: Fires of Hell", 4, "hellfire.png")
			.setText(
					"Applies the 'Fires of Hell' status to the board for 4 turns. During this time, all units will take 1 damage at the end of each turn, and 3 damage when played.");
	public static final Card stateNormal = new CardBoardstate(165, "Status: Normal", 0, "quakewoods.png")
			.setText("Nothing special.").setListable(false);
	public static final Card stateMalestrom = new CardBoardstate(166, "Status: Crackling Typhoon", 2, "seastorm.png")
			.setText(
					"Applies the 'Crackling Typhoon' status to the board for 4 turns. Any units who move during this time will take 1 damage and gain 1 energy.");
	public static final Card stateSpirit = new CardBoardstate(167, "Status: Glowfly Storm", 3, "spiritstorm.png")
			.setText(
					"Applies the 'Glowfly Storm' status to the board for 4 turns. During this time, both players will draw an extra card at the end of each turn.");
	public static final Card stateKholosian = new CardBoardstate(168, "Status: Shadow of Kholos", 3, "astralstorm.png")
			.setText(
					"Applies the 'Shadow of Kholos' status to the board for 4 turns. Any non-undead and non-demon units played during this time will take 10 damage.");
	public static final Card stateRevolution = new CardBoardstate(169, "Status: Iron Revolution", 5,
			"ironrevolution.png").setText(
					"Applies the 'Iron Revolution' status to the board for 4 turns. At the end of each turn, a random Mechanical unit will be added to each player's hand, and all units will gain a random ability.");

	public static final Card eldritchParasite = new Card(175, "Eldritch Parasite", 0, "eldritchparasite.png",
			CardSet.SET_SEA).setText("Afflict a unit, when it dies, its owner gains 1 " + Util.RESOURCE_NAME + ".");
	public static final Card goldShard = new Card(176, "Sack of Riches", 0, "goldsack.png")
			.setText("Gives you one "+Util.RESOURCE_NAME+". At the end of your turn, discard this.").setListable(false);
	public static final Card greed = new Card(177, "Curse of Greed", 1, "greed.png")
			.setText("Draw 2 cards and deal 5 damage to your hero.");
	public static final Card cursedGold = new Card(178, "Cursed Riches", 0, "cursedgold.png", CardSet.SET_IVORGLEN)
			.setText("Take 5 damage and gain 1 " + Util.RESOURCE_NAME + ".");
	public static final Card pileWood = new Card(179, "Pile of Lumber", 2, "lumberpile.png").setText("Draw a card.")
			.setListable(false);
	public static final Card decompose = new Card(180, "Return to Earth", 2, "decompose.png")
			.setText("Destroy a unit and add a copy of it to its owner's hand.").setCosts(0, 2, 0, 0);
	public static final Card heartgem = new Card(181, "Queen's Pendant", 0, "heartgem.png")
			.setText("Give both heroes +12 Health.");
	public static final Card urns = new Card(182, "Ancient Urns", 0, "urns.png")
			.setText("Add a Red, Green and Blue Urn to your hand.");
	public static final Card urnRed = new Card(183, "Magical Red Urn", 1, "urnred.png")
			.setText("Draw a 2-cost card FROM YOUR DECK.").setListable(false);
	public static final Card urnGreen = new Card(184, "Magical Green Urn", 3, "urngreen.png")
			.setText("Draw a 4-cost card FROM YOUR DECK.").setListable(false);
	public static final Card urnBlue = new Card(185, "Magical Blue Urn", 5, "urnblue.png")
			.setText("Draw a 6-cost card FROM YOUR DECK.").setListable(false);
	public static final Card execute = new Card(186, "Field Execution", 3, "execute.png", CardSet.SET_WAR)
			.setText("Deal 3 damage to a unit. If its health is still greater than 4, destroy it.");
	public static final Card moltenhammer = new Card(187, "Disintegrate", 1, "moltenhammer.png")
			.setText("Destroy's a unit's EQUIPMENT.");
	public static final Card shadowblast = new Card(188, "Apocalytpic Blast", 4, "shadowblast.png",
			CardSet.SET_IVORGLEN).setText("Deal 6 damage to a unit. If it dies, ALL units take 2 damage.");
	public static final Card spirithounds = new Card(189, "Spirit Hounds", 3, "spirithounds.png")
			.setText("Add 2 Spirit Hounds to your hand.");
	public static final Card fleshoffire = new Card(190, "Flesh of Fire", 3, "fleshoffire.png").setText(
			"Afflict the target, causing them to take 2 damage at the end of each turn, and spread this to surrounding units.");
	public static final Card eldritchtome = new Card(191, "Eldritch Tome", 2, "eldritchtome.png", CardSet.SET_SEA)
			.setText("Destroy the target unit's 'Attack' ability. They then gain 5 random abilities.");
	public static final Card bloodsurge = new Card(192, "Valkyrie's Rage", 4, "break.png")
			.setText("Deal 2 damage to any units surrounding your units.");
	public static final Card leafarrow = new Card(193, "Thistleblade Volley", 2, "leafarrow.png")
			.setText("Deal 1 damage to each enemy unit. If one dies, add a copy of this card to your hand.");
	public static final Card voyage = new Card(194, "Rahkan Voyage", 2, "voyage.png", CardSet.SET_SEA)
			.setText("Each player shuffles a copy of every card in their deck into their deck.");
	public static final Card markets = new Card(195, "Blackrise Markets", 5, "markets.png", CardSet.SET_IVORGLEN)
			.setText(
					"Add a copy of every spell from your deck to your hand. Replace all spells in your deck with demons.");
	public static final Card bladequest = new Card(196, "Quest for the Runeblade", 1, "runebladequest.png")
			.setText("Shuffle a Frozen Runeblade into your deck.");
	public static final Card blade = new Card(197, "Frozen Runeblade", 3, "blade.png")
			.setText("Give a unit +7/+7 and add a 'Quest for the Runeblade' card to your hand.").setListable(false);
	public static final Card worldmelt = new Card(198, "Atrophic Dismay", 4, "worldmelt.png")
			.setText("Combine your deck with your opponents. Both players now use a copy of this deck.");
	public static final Card prism = new Card(199, "Leora's Blue Prism", 1, "prism.png")
			.setText("Shuffle a copy of every SPELL card in your deck to your deck.");
	public static final Card prism1 = new Card(500, "Leora's Red Prism", 1, "prism1.png")
			.setText("Shuffle a copy of every UNIT card in your deck to your deck.");
	public static final Card necrotome = new Card(501, "Necronomicon", 3, "deathtome.png", CardSet.SET_IVORGLEN)
			.setText("Replace your entire deck with random UNDEAD units. Set both commanders to 15 HP.");
	public static final Card deathlotus = new Card(502, "Death Lotus", 2, "deathlotus.png", CardSet.SET_IVORGLEN)
			.setText(
					"At the end of each turn shuffle a copy of this into your deck and deal 2 damage to your commander.")
			.setListable(false);
	public static final Card bogtrade = new Card(503, "Swamp King's Ferry", 1, "swamptrader.png")
			.setText("Replace all spells in your deck with Piles of Lumber.");
	public static final Card prepareWar = new Card(504, "Concussive Blow", 1, "prepWar.png", CardSet.SET_WAR)
			.setText("STUN a unit for 3 turns and draw a card.");
	public static final Card duplication = new Card(505, "Duplication", 2, "duplication.png")
			.setText("CONJURE(3) a SPELL that has been cast this game.");
	public static final Card bladecharm = new Card(506, "Blade Charm", 3, "bladecharm.png")
			.setText("Steal a unit's EQUIPMENT and add it to your hand.");
	public static final Card weaponspell = new Card(507, "Reforge", 1, "weaponspell.png")
			.setText("If the target unit has EQUIPMENT, add 3 DURABILITY to it.");
	public static final Card chaosNova = new Card(508, "Fel Madness", 5, "chaonova.png", CardSet.SET_IVORGLEN)
			.setText("Force a unit to attack itself and draw a card.");
	public static final Card corruptGround = new Card(509, "Nectrotic Ritual", 0, "corruptground.png")
			.setText("Destroy a friendly unit. CONJURE(4) a card with cost equal to its attack; Heal your commander for its health.");
	public static final Card voidErupt = new Card(510, "Void Eruption", 4, "decay.png")
			.setText("Deal 3 damage to all enemies on CORRUPTED tiles.");
	public static final Card torment = new Card(511, "Void Infusion", 3, "torment.png")
			.setText("Grant a STUNNED unit +5/+5.");
	public static final Card prayer = new Card(512, "Divine Prayer", 4, "prayer.png")
			.setText("Restore 3 health to your commander, draw a card, and shuffle 2 copies of this into your deck.");
	public static final Card netherlash = new Card(513, "Nether Lash", 1, "voidshard.png")
			.setText("Select a unit. If it's STUNNED, deal 5 damage to it; Otherwise, stun it for 2 turns.");
	public static final Card divineshock = new Card(514, "Seraphim Runes", 3, "corruptblast.png")
			.setText("Damage a unit until it has 1 HP. Increase the next instance of damage by the HP lost.");
	public static final Card enterVoid = new Card(515, "Void Dragon's Fire", 6, "enterthevoid.png",
			CardSet.SET_IVORGLEN).setText("Give a unit +2/+2 then deal 5 damage to all units surrounding it.");
	public static final Card sculpture = new Card(516, "Stone Sculpture", 3, "stoneimage.png")
			.setText("Add a copy of a unit to your hand.");
	public static final Card flood = new Card(517, "Flood", 3, "diver.png", CardSet.SET_SEA)
			.setText("Transform a 3x3 area into WATER.");
	public static final Card splash = new Card(518, "Splash", 1, "watermancer.png")
			.setText("Transform a tile into WATER and shuffle 2 copies of this into your deck.");
	public static final Card summonWaygate = new Card(519, "Summon Gateway", 3, "waygate.png")
			.setText("Summon an Arcane Gateway.");
	public static final Card destruction = new Card(520, "Oblivion", 7, "flametempest.png")
			.setText("Transform all tiles to DIRT and remove board status. Deal 5 damage to ALL units.");
	public static final Card darkvisions = new Card(521, "Dark Visions", 3, "mindslave.png")
			.setText("Destroy a unit's abilities and give it +2/+2 for each.");
	public static final Card brainwash = new Card(522, "Brainwash", 6, "mindwarp.png", CardSet.SET_IVORGLEN)
			.setText("The target unit switches sides and gains -4/-4.");
	public static final Card quickshot = new Card(523, "Barrage", 3, "quickshot.png")
			.setText("Deal 3 damage to all units on the target row.");
	public static final Card strangulate = new Card(524, "Storm Chains", 4, "strangulate.png", CardSet.SET_WAR)
			.setText("Set a unit's stats to 3/3/3 and clear its conditions/effects.").setCosts(0, 0, 3, 0);
	public static final Card song = new Card(525, "Song of Life", 6, "songoflife.png")
			.setText("For each damaged unit on board, add a 'Healing Song' card to your hand.");
	public static final Card healingsong = new Card(526, "Healing Song", 1, "songoflife.png")
			.setText("Restore 2 Health to a unit.").setListable(false);
	public static final Card kengmarkets = new Card(527, "Markets of Keng", 2, "marketsofkeng.png")
			.setText("CONJURE(3) a card with cost equal to your next turn's " + Util.RESOURCE_NAME + ".")
			.setListable(true);
	public static final Card demonicritual = new Card(528, "Demonic Ritual", 1, "bloodsurge.png", CardSet.SET_IVORGLEN)
			.setText("CONJURE(5) a card containing 'demon'.").setListable(true);
	public static final Card northwind = new Card(529, "Northwind", 3, "northwind.png")
			.setText(
					"Deal 3 damage to a unit. If it's in the top half of the board, deal another 2 damage and CHILL it.")
			.setListable(true).setCosts(0, 0, 2, 0);
	public static final Card icicle = new Card(530, "Icicle Flurry", 1, "icicle.png")
			.setText("CHILL a unit and then force it to deal 1 damage to all CHILLED units.").setListable(true).setCosts(0, 0, 2, 0);
	public static final Card blacktome = new Card(531, "Infinite Wisdom", 5, "arcanetome.png").setText("Gain 2 "
			+ Util.RESOURCE_NAME + " and CONJURE(6) a card with cost equal to your max " + Util.RESOURCE_NAME + ".")
			.setListable(true);
	public static final Card greenrelic = new Card(532, "Relic of Shan'Zin", 6, "shatteredrelic.png")
			.setText("Double a unit's stats.").setListable(true);
	public static final Card greenpotion = new Card(533, "Ichorflask", 3, "deathpotion.png", CardSet.SET_IVORGLEN)
			.setText("Deal damage to a unit equal to how many units died this turn.").setListable(true);
	public static final Card engine = new Card(534, "Energy Bomb", 6, "mechpod.png")
			.setText("Grant all friendly MECHANICAL units +3 Energy and deal their combined ENERGY to the enemy base.")
			.setListable(true);
	public static final Card roar = new Card(535, "Feral Rampage", 4, "savagery.png")
			.setText("Deal damage equal to the combined attack of your BEASTS to a non-Commander unit.")
			.setListable(true);
	public static final Card holybind = new Card(536, "Holy Shackles", 3, "cleric.png", CardSet.SET_SEA)
			.setText(
					"Deal damage to a unit equal to its SPEED. If it is DEMON, ELDRITCH or UNDEAD, take control of it.")
			.setListable(true);
	public static final Card steamstrike = new Card(537, "Steam Strike", 4, "steamstrike.png")
			.setText(
					"Deal 3 damage to a unit. If you have a MECHANICAL unit, also deal 4 damage left and right of the target.")
			.setListable(true);
	public static final Card spiritbomb = new Card(538, "War of Spirits", 7, "spiritbattle.png", CardSet.SET_IVORGLEN)
			.setText("Deal 100 damage to each non-Commander non-Base enemy. Set your " + Util.RESOURCE_NAME + " to 1.")
			.setListable(true);
	public static final Card mindsurge = new Card(539, "Mindsurge", 6, "arcanetome.png")
			.setText("Deal 10 damage to a unit. Grant its abilities to all other units.").setListable(true);
	public static final Card finalstand = new Card(540, "Final Stand", 3, "finalstand.png")
			.setText("Restore 5 health to a unit and force the nearest enemy to PROECT it.").setListable(true);
	public static final Card svanjarships = new Card(541, "Svanjan Fleet", 4, "svanjarships.png", CardSet.SET_SEA)
			.setText("Give all your units +1/+2 and [Amphibious].").setListable(false);
	public static final Card legend = new Card(543, "Legend of Azahish", 4, "bannerbearer.png")
			.setText("Give a unit +6/+6. It then loses 1/1 each turn until it has lost the buff.").setListable(true);
	public static final Card deflect = new Card(544, "Slaying Strike", 2, "deflection.png")
			.setText(
					"Give a friendly unit [Deadly], force the nearest enemy with > 4 attack to attack it, then remove the ability.")
			.setListable(true);
	public static final Card shout = new Card(545, "Booming Shout", 2, "shout.png", CardSet.SET_WAR)
			.setText("Give units in a 5x5 radius +1/+1, or +2/+2 if damaged.").setListable(true).setCosts(2, 0, 0, 0);
	public static final Card dragoncall = new Card(546, "Call of the Dragon", 3, "dragoncall.png")
			.setText(
					"Transform all GROUND tiles in a 3x3 radius into SCORCHED tiles. Units on these tiles will take 2 damage each turn.")
			.setListable(true);
	public static final Card fleshpile = new Card(547, "Pile of Flesh", 2, "fleshpile.png", CardSet.SET_IVORGLEN)
			.setText("Give an undead unit +1/+1.").setListable(true);
	public static final Card holdingtome = new Card(548, "Eternity Tome", 2, "holdingtome.png")
			.setText("Transform a unit into a 1/2 Book with [Learn].").setListable(true);
	public static final Card trap = new Card(549, "Hunting Trap", 2, "trap.png", CardSet.SET_JUNGLE)
			.setText("Change a unit's HEALTH to be equal to its SPEED.").setListable(true);
	public static final Card boarcharge = new Card(550, "Boar Charge", 3, "chargecaptain.png")
			.setText("Grant a unit [Charger], causing its ATTACK to equal its SPEED.").setListable(true);
	public static final Card useless = new Card(551, "Useless Artifact", 5, "urngreen.png")
			.setText("Does NOTHING! AT ALL! HAHAHA!.").setListable(false);
	public static final Card tracking = new Card(552, "Desert Tracking", 1, "tracking.png")
			.setText("Grant a unit +1/+1 and +2 SPEED.").setListable(true);
	public static final Card spelldecay = new Card(553, "Curse of Sands", 3, "sanddecay.png")
			.setText("Grant a unit [Shifting Sands], causing it to lose 2/2 whenever a spell is cast.")
			.setListable(true);
	public static final Card tombtoxin = new Card(554, "Tomb Toxin", 6, "tombpoison.png")
			.setText("Grant a unit [Deadly].").setListable(true);
	public static final Card filthwater = new Card(555, "Filthwater Vial", 5, "filthwater.png")
	.setText("Grant a unit +2/+2 and +5 Energy. CONJURE(3) a SPELL.").setListable(true);
	public static final Card blackblessing = new Card(556, "Occult Ritual", 4, "darkenchant.png").setText("Deal 4 damage to a unit, then grant it +3/+7.").setListable(true);
	public static final Card revenge = new Card(557, "Revenge", 3, "revenge.png").setText("Increase the next instance of damage by 1 for every 2 HP your commander is missing.").setListable(true);
	public static final Card orchorde = new Card(558, "Thundering Horde", 5, "boarcharge.png").setText("Increase the next instance of damage by 1 for each friendly unit.").setListable(true);
	public static final Card spellshield = new Card(559, "Guardian's Watch", 1, "spellreflect.png").setText("Reduce the next instance of damage by 10.").setListable(true);
	public static final Card markofice = new Card(560, "Mark of Ice", 2, "frostmark.png").setText("Give a unit +4 Attack this turn. If it's CHILLED, also deal 3 damage to it.").setListable(true);
	public static final Card spiritlink = new Card(561, "Force Bubble", 4, "bubble.png").setText("Reduce all damage dealt this game by 2.").setListable(true);
	public static final Card voidbreath = new Card(562, "Divine Champion", 7, "voidbreath.png").setText("Set the target's stats to 8/8 and clear its effects, then deal 5 damage to all other units.").setListable(true);
	public static final Card voidtome = new Card(563, "Nether Mysteries", 4, "voidtome.png").setText("Increase all damage dealt this game by 2.").setListable(true);
	public static final Card corruptground = new Card(564, "Cursed Earth", 3, "shadowblast.png").setText("CORRUPT a 3x3 area.").setListable(true);
	// public static final Card chaosNova = new Card(509, "Chaos Nova", 1,
	// "weaponspell.png").setText("If the target unit has EQUIPMENT, add 3
	// DURABILITY to it.");

	public static final Card equipmentHelmIron = new CardEquipment(900, "Iron Skullcap", 1, 2, "weaponHelmetIron.png")
			.addEffect(new Effect(EffectType.health, 3, 1))
			.setText("Equipped unit gains +3 HEALTH and listed abilities.");
	public static final Card equipmentAxeVoid = new CardEquipment(901, "Voidcleavers", 3, 4, "weaponAxeVoid.png")
			.addEffect(new Effect(EffectType.attack, 2, 1)).addAbility(CardAbility.blink)
			.setText("Equipped unit gains +2 ATTACK and listed abilities.");
	public static final Card equipmentScrollDemon = new CardEquipment(902, "Hellfire Scroll", 3, 3,
			"weaponScrollDemon.png").addAbility(CardAbility.abyssaltemple)
					.setText("Equipped unit counts as a demon and gains listed abilities.");
	public static final Card equipmentAmuletEldritch = new CardEquipment(903, "Amulet of Thurgyl", 2, 2,
			"weaponAmuletEldritch.png", CardSet.SET_IVORGLEN).addEffect(new Effect(EffectType.attack, -3, 1))
					.addAbility(CardAbility.eldritchSlam).addAbility(CardAbility.eldritchTorment)
					.setText("Equipped unit gains -3 ATTACK and listed abilities.");
	public static final Card equipmentAmuletDragon = new CardEquipment(904, "Dragonfire Amulet", 3, 2,
			"weaponAmuletDragon.png").addAbility(CardAbility.scorch).setText("Equipped unit gains listed abilities.");
	public static final Card equipmentAmuletFire = new CardEquipment(905, "Searing Locket", 5, 3,
			"weaponAmuletFire.png").addAbility(CardAbility.erupt).setText("Equipped unit gains listed abilities.");
	public static final Card equipmentGloveUndead = new CardEquipment(906, "Plague Clutch", 4, 2,
			"weaponGauntletUndead.png").addAbility(CardAbility.harvest)
					.setText("Equipped unit gains listed abilities.");
	public static final Card equipmentGloveUndead1 = new CardEquipment(907, "Necrogauntlet", 5, 1,
			"weaponGauntletElven.png").addAbility(CardAbility.cryptthief).addAbility(CardAbility.raise)
					.setText("Equipped unit gains listed abilities.");
	public static final Card equipmentHammerDwarf = new CardEquipment(908, "Svanjar Warhammer", 3, 3,
			"weaponHammerDwarf.png").addAbility(CardAbility.wintersembrace)
					.setText("Equipped unit gains listed abilities.");
	public static final Card equipmentBowRiver = new CardEquipment(909, "Riverbend Bow", 3, 2, "weaponBowRiver.png",
			CardSet.SET_JUNGLE).addEffect(new Effect(EffectType.speed, 3, 1)).addAbility(CardAbility.snipe)
					.addAbility(CardAbility.energetic).setText("Equipped unit gains +3 SPEED and listed abilities.");
	public static final Card equipmentBladeVenom = new CardEquipment(910, "Ghost Saber", 2, 3, "weaponBladeVenom.png",
			CardSet.SET_IVORGLEN).addEffect(new Effect(EffectType.speed, 3, 1)).addAbility(CardAbility.poison)
					.addAbility(CardAbility.energetic).setText("Equipped unit gains +3 SPEED and listed abilities.");
	public static final Card equipmentStaffLight = new CardEquipment(911, "Staff of Light", 2, 4,
			"weaponStaffPortals.png").addAbility(CardAbility.heal2)
					.setText("Equipped unit gains +3 SPEED and listed abilities.");
	public static final Card equipmentClock = new CardEquipment(912, "Clockwork Engine", 4, 2, "weaponRelicClock.png")
			.addAbility(CardAbility.tinker).setText("Equipped unit gains +3 SPEED and listed abilities.");
	public static final Card equipmentShieldElven = new CardEquipment(913, "Elven Aegis", 3, 3, "weaponShieldElven.png",
			CardSet.SET_JUNGLE).addAbility(CardAbility.disarm).addAbility(CardAbility.energetic)
					.addAbility(CardAbility.energize).setText("Equipped unit gains +4 HEALTH and listed abilities.");
	public static final Card equipmentShieldHoly = new CardEquipment(914, "Holy Defender", 3, 7, "weaponShieldHoly.png")
			.addEffect(new Effect(EffectType.health, 6, 1)).addAbility(CardAbility.protect)
			.addAbility(CardAbility.smite).setText("Equipped unit gains +6 HEALTH and listed abilities.");
	public static final Card equipmentShieldDemon = new CardEquipment(915, "Demonic Bulwark", 2, 4,
			"weaponShieldDemon.png").addEffect(new Effect(EffectType.health, 5, 1)).addAbility(CardAbility.protect)
					.addAbility(CardAbility.curse).setText("Equipped unit gains +5 HEALTH and listed abilities.");
	public static final Card equipmentGauntletIron = new CardEquipment(916, "Truesteel Gauntlet", 4, 3,
			"weaponGauntletIron.png").addEffect(new Effect(EffectType.health, 2, 1)).addAbility(CardAbility.protect)
					.addAbility(CardAbility.warding).setText("Equipped unit gains +2 HEALTH and listed abilities.");
	public static final Card equipmentHelmDemon = new CardEquipment(917, "Voidflame Helm", 4, 3,
			"weaponHelmetDemon.png").addAbility(CardAbility.energetic).addAbility(CardAbility.ascend)
					.setText("Equipped unit gains +2 HEALTH and listed abilities.");
	public static final Card equipmentRingElven = new CardEquipment(918, "Royal Elven Ring", 4, 2,
			"weaponRingElven.png", CardSet.SET_JUNGLE).addAbility(CardAbility.energetic).addAbility(CardAbility.plant)
					.addAbility(CardAbility.plantTree).addAbility(CardAbility.grove)
					.setText("Equipped unit gains listed abilities.");
	public static final Card equipmentAxeDwarf = new CardEquipment(919, "Dwarven Battleaxe", 3, 3, "weaponAxeDwarf.png")
			.addEffect(new Effect(EffectType.attack, 3, 1)).addEffect(new Effect(EffectType.health, 2, 1))
			.setText("Equipped unit gains +3/+2 and listed abilities.");
	public static final Card equipmentBladeElf = new CardEquipment(920, "Elven Longsword", 4, 1, "weaponBladeElf.png",
			CardSet.SET_JUNGLE).addEffect(new Effect(EffectType.health, -2, 1))
					.addEffect(new Effect(EffectType.attack, 5, 1)).addAbility(CardAbility.strike)
					.setText("Equipped unit gains +5/-2 and listed abilities.");
	public static final Card equipmentTeacherGauntlet = new CardEquipment(921, "Instructor's Glove", 1, 2,
			"weaponGauntletFire.png").addAbility(CardAbility.teach).setText("Equipped unit gains listed abilities.");
	public static final Card equipmentContract = new CardEquipment(922, "Mercenary Contract", 0, 1,
			"weaponContract.png").addAbility(CardAbility.mercenary).setText("Equipped unit gains listed abilities.");
	public static final Card equipmentEmperorBlades = new CardEquipment(923, "Imperial Scimitars", 4, 1,
			"weaponAxeEmperor.png").addEffect(new Effect(EffectType.speed, 2, 1)).addAbility(CardAbility.dragonfist)
					.addAbility(CardAbility.monkeyfist).setText("Equipped unit gains +2 SPEED and listed abilities.");
	public static final Card equipmentStaffDragon = new CardEquipment(924, "Dragon Scepter", 6, 4,
			"weaponStaffDragons.png").addEffect(new Effect(EffectType.health, 4, 1))
					.addEffect(new Effect(EffectType.attack, -4, 1)).addAbility(CardAbility.dragonwrath)
					.addAbility(CardAbility.erupt).addAbility(CardAbility.energetic)
					.setText("Equipped unit gains -4/+4 and listed abilities.");
	public static final Card equipmentBladeCommander = new CardEquipment(925, "Crusader's Sabre", 4, 3,
			"weaponBladeFire.png").addAbility(CardAbility.inspire).addAbility(CardAbility.behead)
					.addAbility(CardAbility.energetic).setText("Equipped unit gains listed abilities.");
	public static final Card equipmentDaggers = new CardEquipment(926, "Daggers of Kitan", 5, 3, "weaponDaggers.png",
			CardSet.SET_ANURIM).addEffect(new Effect(EffectType.health, -3, 1))
					.addEffect(new Effect(EffectType.attack, 3, 1)).addEffect(new Effect(EffectType.speed, 5, 1))
					.addAbility(CardAbility.bloodlust).addAbility(CardAbility.execute).addAbility(CardAbility.energetic)
					.setText("Equipped unit gains +3/-3, +5 SPEED, and listed abilities.");
	public static final Card equipmentStonemaul = new CardEquipment(927, "Maul of Elements", 5, 3,
			"weaponHammerElements.png").addEffect(new Effect(EffectType.health, 3, 1))
					.addEffect(new Effect(EffectType.attack, 3, 1)).addEffect(new Effect(EffectType.speed, -2, 1))
					.addAbility(CardAbility.fury).addAbility(CardAbility.erupt).addAbility(CardAbility.growing)
					.addAbility(CardAbility.eldritchSwirl).addAbility(CardAbility.soar)
					.setText("Equipped unit gains +3/+3, -2 SPEED, and listed abilities.");
	public static final Card equipmentSlayer = new CardEquipment(928, "Azashi Slayer", 5, 2, "deflection.png")
	.addAbility(CardAbility.assassination).setText("Equipped unit gains listed abilities.");
	public static final Card equipmentRunestaff = new CardEquipment(929, "Shurim's Runestaff", 3, 1, "shurimstaff.png")
	.addAbility(CardAbility.shurim).setText("Equipped unit gains listed abilities.").setListable(false);

	// public static final Card leafarrow = new Card(194, "Thistleblade Volley",
	// 2, "leafarrow.png").setText("Deal 1 damage to each enemy unit. If one
	// dies, add a copy of this card to your hand.");
	// public static final Card goldShard = new Card(177, "Gold Shard", 0,
	// "eldritchparasite.png").setText("Gives you one Mana this turn only.");

	public static final Card skrittScout = new UnitTemplate(20, "Kratt Scout", "kratScout.png", 1, RANK_COMMON, 2, 2, 4)
			.setFamily(Family.kratt).addAbility(CardAbility.energetic)
			.setShouts("Eee! Swarm! Swarm!", "Euch!", "YICK!");
	public static final Card skrittRogue = new UnitTemplate(21, "Kratt Rogue", "kratRogue.png", 3, RANK_COMMON, 4, 3, 4)
			.setFamily(Family.kratt).setShouts("The sun burns!", "You cannot stop the swarm!", "YICK!");
	public static final Card ebonSpirit = new UnitTemplate(23, "Ebon Spirit", "ebonSpirit.png", 2, RANK_COMMON, 2, 3, 6,
			CardSet.SET_IVORGLEN).setFamily(Family.undead).setParticleType("SKULL").setShouts("I wish only... To rest.",
					"Is it over?", "If I must.");
	public static final Card assassin = new UnitTemplate(24, "Lone Assassin", "assassin.png", 2, RANK_COMMON, 2, 3, 3)
			.setParticleType("IRON").addAbility(CardAbility.disarm)
			.setShouts("They'll never see me comin'.", "Gah, rookie mistake.", "Bleed!");
	public static final Card ogrewarlord = new UnitTemplate(25, "Morkhan Warbringer", "ogrewarlord.png", 5, RANK_COMMON,
			8, 3, 2).addAbility(CardAbility.protect);
	public static final Card junglegoblin = new UnitTemplate(26, "Poison Goblin", "junglegoblin.png", 1, RANK_COMMON, 1,
			1, 3).addAbility(CardAbility.assassination).setParticleType("BLOOM");
	public static final Card colossus = new UnitTemplate(27, "Skycore Colossus", "colossus.png", 7, RANK_COMMON, 6, 6,
			3).addAbility(CardAbility.upgrade1).addAbility(CardAbility.upgrade2).addAbility(CardAbility.upgrade3)
					.setFamily(Family.mech);
	public static final Card steamgolem = new UnitTemplate(28, "Steam Golem", "steamgolem.png", 3, RANK_COMMON, 4, 3, 3)
			.setFamily(Family.mech).addAbility(CardAbility.upgrade1);
	public static final Card voidcaster = new UnitTemplate(29, "Voidcaster", "voidbarrage.png", 6, RANK_COMMON, 4, 5, 3)
			.setParticleType("SWIRL").addAbility(CardAbility.shadowburn).setFamily(Family.undead);
	public static final Card witchhunter = new UnitTemplate(30, "Witch Hunter", "witchhunter.png", 4, RANK_COMMON, 2, 3,
			4).setParticleType("FIRE").addAbility(CardAbility.witchhunt).addAbility(CardAbility.crusade);
	public static final Card infernoguard = new UnitTemplate(31, "Volcanic Destroyer", "infernalstrike.png", 5,
			RANK_COMMON, 4, 7, 3).setParticleType("FIRE").addAbility(CardAbility.leap).addAbility(CardAbility.energetic)
					.setFamily(Family.demon);
	public static final Card drone = new UnitTemplate(32, "Underdrone", "drone.png", 3, RANK_COMMON, 4, 4, 3,
			CardSet.SET_IVORGLEN).setParticleType("SKULL").setFamily(Family.insectoid);
	public static final Card spiderling = new UnitTemplate(33, "Spiderling", "spiderling.png", 0, RANK_COMMON, 1, 1, 4)
			.setParticleType("SKULL").addAbility(CardAbility.minion).setFamily(Family.insectoid).setListable(false);
	public static final Card broodmother = new UnitTemplate(34, "Webmother", "broodmother.png", 4, RANK_COMMON, 5, 2, 1)
			.setParticleType("SKULL").addAbility(CardAbility.hatch).setFamily(Family.insectoid);
	public static final Card duskwalkerSeer = new UnitTemplate(35, "Duskwalker Seer", "duskwalkerseer.png", 4,
			RANK_COMMON, 6, 2, 3).setParticleType("BLOOM").addAbility(CardAbility.heal).addAbility(CardAbility.raise);
	public static final Card emperorTannis = new UnitTemplate(36, "Emperor Tannis", "dragonemperor.png", 9, RANK_REGAL,
			11, 4, 4).setParticleType("FIRE").addAbility(CardAbility.soar).addAbility(CardAbility.hovering)
					.addAbility(CardAbility.dragonwrath).addAbility(CardAbility.scorchground);
	public static final Card dragon = new UnitTemplate(37, "Violet Drake", "dragon.png", 8, RANK_MYTHIC, 7, 6, 5)
			.setParticleType("SWIRL").addAbility(CardAbility.soar).addAbility(CardAbility.scorch)
			.addAbility(CardAbility.hovering);
	public static final Card frostmage = new UnitTemplate(38, "Glacial Wizard", "frostmage.png", 3, RANK_COMMON, 4, 2,
			3).setParticleType("BUBBLE").addAbility(CardAbility.chill);
	public static final Card arcanist = new UnitTemplate(39, "Arcanist", "arcanist.png", 8, RANK_MYTHIC, 8, 5, 3)
			.setParticleType("SHATTER").addAbility(CardAbility.blink).addAbility(CardAbility.enchant);
	public static final Card venomslinger = new UnitTemplate(40, "Venomous Lurker", "venomslinger.png", 4, RANK_COMMON,
			2, 3, 3).addAbility(CardAbility.poison).setParticleType("BLOOM").setFamily(Family.insectoid);
	public static final Card wastewalker = new UnitTemplate(41, "Waste Walker", "wastewalker.png", 2, RANK_COMMON, 2, 2,
			2).setParticleType("SKULL").addAbility(CardAbility.minion).setFamily(Family.undead).setListable(false);
	public static final Card skeletonking = new UnitTemplate(42, "Underworld Baron", "skeletonking.png", 6, RANK_COMMON,
			5, 5, 2).setParticleType("SKULL").addAbility(CardAbility.summonWarrior).setFamily(Family.undead);
	public static final Card valkyrie = new UnitTemplate(43, "Blessed Valkyrie", "valkyrie.png", 7, RANK_COMMON, 7, 2,
			4).setParticleType("SKULL").addAbility(CardAbility.heal).addAbility(CardAbility.soar)
					.addAbility(CardAbility.hovering).addAbility(CardAbility.energize);
	public static final Card lifelessWarrior = new UnitTemplate(44, "Lifeless Guardian", "king.png", 3, RANK_COMMON, 4,
			3, 3).setParticleType("IRON").setFamily(Family.undead).addAbility(CardAbility.nearprotect)
					.setListable(false);
	public static final Card goblinShaman = new UnitTemplate(45, "Goblin Chief", "goblinshaman.png", 3, RANK_COMMON, 3,
			3, 3).setParticleType("BLOOM").addAbility(CardAbility.heal);
	public static final Card goblinRogue = new UnitTemplate(46, "Goblin Warrior", "goblin.png", 1, RANK_COMMON, 4,
			1, 4).addAbility(CardAbility.nearprotect).setParticleType("BLOOM").setCosts(0, 0, 0, 0);
	public static final Card tigerhound = new UnitTemplate(47, "Tigerhound", "tigerhound.png", 2, RANK_COMMON, 4, 1, 5)
			.setParticleType("BLOOM").addAbility(CardAbility.bloodlust).addAbility(CardAbility.energetic)
			.setFamily(Family.beast);
	public static final Card pirate = new UnitTemplate(48, "Pirate", "seadog.png", 4, RANK_COMMON, 5, 4, 4,
			CardSet.SET_SEA).addAbility(CardAbility.amphibious).setParticleType("IRON");
	public static final Card pirateCaptain = new UnitTemplate(49, "Pirate Captain", "pirate.png", 7, RANK_COMMON, 9, 5,
			3, CardSet.SET_SEA).addAbility(CardAbility.amphibious).setParticleType("IRON")
					.addAbility(CardAbility.cannon);
	public static final Card bloodfurySlayer = new UnitTemplate(50, "Rampaging Bloodfur", "rampager.png", 5,
			RANK_COMMON, 7, 3, 4, CardSet.SET_JUNGLE).setParticleType("BLOOM").addAbility(CardAbility.battlerage)
					.addAbility(CardAbility.bloodlust);
	public static final Card jungleKing = new UnitTemplate(51, "King of the Jungle", "jungleking.png", 10, RANK_REGAL,
			12, 9, 5, CardSet.SET_TRIBELANDS).setParticleType("BLOOM").addAbility(CardAbility.leap)
					.addAbility(CardAbility.heal).addAbility(CardAbility.energetic).setFamily(Family.beast)
					.setShouts("Power breeds pride. I am both.", "All things must end.&This is the natural course.",
							"Farewell.");
	public static final Card voidpanther = new UnitTemplate(52, "Void Panther", "voidpanther.png", 7, RANK_MYTHIC, 8, 5,
			3).setParticleType("SWIRL").addAbility(CardAbility.blink).addAbility(CardAbility.voidclaw)
					.setFamily(Family.beast);
	public static final Card executioner = new UnitTemplate(53, "Khovani Executioner", "khovaniexecutioner.png", 6,
			RANK_UNCOMMON, 6, 4, 3).setParticleType("SWIRL").addAbility(CardAbility.execute)
					.addAbility(CardAbility.behead);
	public static final Card khovani = new UnitTemplate(54, "Khovani Gladiator", "khovanigladiator.png", 1, RANK_COMMON,
			3, 3, 3).addAbility(CardAbility.shadows).setParticleType("SWIRL");
	public static final Card ascended = new UnitTemplate(55, "Demonic Ascendant", "ancientfirefist.png", 5, RANK_MYTHIC,
			4, 4, 4).setParticleType("FIRE").addAbility(CardAbility.scorch).addAbility(CardAbility.ascend)
					.setFamily(Family.demon);
	public static final Card headlessprince = new UnitTemplate(56, "Falkryn the Beheaded", "charredprince.png", 8,
			RANK_MYTHIC, 12, 3, 4, CardSet.SET_IVORGLEN).setParticleType("SWIRL").addAbility(CardAbility.execute)
					.addAbility(CardAbility.behead).setFamily(Family.undead).setListable(false);
	public static final Card tent = new StructureTemplate(57, "Campsite", "tent.png", 0, RANK_COMMON, 10, 0, 0)
			.setParticleType("SWIRL").addAbility(CardAbility.tentBuild).addAbility(CardAbility.buildTower).setListable(false);
	public static final Card outpost = new StructureTemplate(58, "Outpost", "outpost.png", 1, RANK_COMMON, 20, 0, 0)
			.setParticleType("SWIRL").addAbility(CardAbility.outpostBuild).addAbility(CardAbility.snipe).addAbility(CardAbility.buildWall).addAbility(CardAbility.buildTower)
			.setListable(false);
	public static final Card stronghold = new StructureTemplate(59, "Fortress", "stronghold.png", 1, RANK_COMMON, 30, 0,
			0).setParticleType("SWIRL").addAbility(CardAbility.energetic).addAbility(CardAbility.snipe).addAbility(CardAbility.heal1).addAbility(CardAbility.buildTower).addAbility(CardAbility.energize)
					.addAbility(CardAbility.buildWall).addAbility(CardAbility.buildCannon).addAbility(CardAbility.terraform).setListable(false);
	public static final Card zinsamurai = new UnitTemplate(60, "Zin Legion Monk", "samurai.png", 4, RANK_MYTHIC, 6, 2,
			4).setParticleType("BLOOM").addAbility(CardAbility.dragonfist).addAbility(CardAbility.tigerfist)
					.addAbility(CardAbility.monkeyfist)
					.setShouts("This world needs healing.", "How can this be?", "For Zin Legion!");
	public static final Card imp = new UnitTemplate(61, "Lesser Imp", "imp.png", 1, RANK_COMMON, 2, 2, 3)
			.setParticleType("FIRE").addAbility(CardAbility.sacrifice).setFamily(Family.demon);
	public static final Card cannon = new StructureTemplate(62, "Cannon", "cannon.png", 0, RANK_COMMON, 6, 0, 0)
			.setParticleType("SWIRL").addAbility(CardAbility.minion).addAbility(CardAbility.cannonball)
			.addAbility(CardAbility.cannonball1).setListable(false);
	public static final Card wall = new StructureTemplate(63, "Wall", "wall.png", 0, RANK_COMMON, 5, 0, 0)
			.setParticleType("SWIRL").addAbility(CardAbility.minion).addAbility(CardAbility.wall).setListable(false);
	public static final Card phoenixmage = new UnitTemplate(64, "Phoenix Mage", "phoenixmage.png", 3, RANK_UNCOMMON, 4,
			1, 3).setParticleType("FIRE").addAbility(CardAbility.scorchground).addAbility(CardAbility.scorch)
					.addAbility(CardAbility.phoenix);
	public static final Card phoenix = new UnitTemplate(65, "Imperial Phoenix", "phoenix.png", 4, RANK_MYTHIC, 8, 6, 5)
			.setParticleType("FIRE").addAbility(CardAbility.hovering).addAbility(CardAbility.soar)
			.addAbility(CardAbility.energetic).addAbility(CardAbility.scorch).setListable(false);
	public static final Card bombbot = new UnitTemplate(66, "Bomb Bot", "bombbot.png", 1, RANK_UNCOMMON, 3, 0, 3)
			.setFamily(Family.mech).setParticleType("FIRE").addAbility(CardAbility.litFuse).setFamily(Family.mech);
	public static final Card batterybot = new UnitTemplate(67, "Battery Bot", "batterybot.png", 2, RANK_UNCOMMON, 4, 0,
			3).setFamily(Family.mech).setParticleType("FIRE").addAbility(CardAbility.energize).setFamily(Family.mech);
	public static final Card artillerybot = new UnitTemplate(68, "Scrapcannon", "artillerybot.png", 5, RANK_UNCOMMON, 4,
			5, 3, CardSet.SET_WAR).setFamily(Family.mech).setParticleType("FIRE").addAbility(CardAbility.blast)
					.addAbility(CardAbility.scrap).addAbility(CardAbility.upgrade1).setFamily(Family.mech);
	public static final Card goliath = new UnitTemplate(69, "The Iron Giant", "goliath.png", 8, RANK_REGAL, 10, 9, 3)
			.setParticleType("IRON").addAbility(CardAbility.upgrade3).addAbility(CardAbility.stomp)
			.addAbility(CardAbility.combust).addAbility(CardAbility.energize).addAbility(CardAbility.energetic)
			.setFamily(Family.mech);
	public static final Card swine = new UnitTemplate(70, "Swine", "swine.png", 1, RANK_COMMON, 2, 0, 2)
			.setParticleType("IRON").addAbility(CardAbility.charger).setFamily(Family.beast);
	public static final Card hogrider = new UnitTemplate(71, "Hogrider", "hogrider.png", 3, RANK_UNCOMMON, 4, 0, 3)
			.setParticleType("IRON").addAbility(CardAbility.charger);
	public static final Card boarwrangler = new UnitTemplate(72, "Boar Wrangler", "boarwrangler.png", 5, RANK_MYTHIC, 6,
			3, 3).setParticleType("IRON").addAbility(CardAbility.mastersCall).setShouts("Swines! To my side!",
					"You've set them free!", "For the Orklad!");
	public static final Card mimic = new UnitTemplate(73, "Pixie-Troll Mimic", "orkladrunt.png", 4, RANK_MYTHIC, 3, 3,
			3, CardSet.SET_IVORGLEN).setParticleType("SWIRL").addAbility(CardAbility.mimic)
					.setShouts("I wanna be just like you!", "HEUGH! No!", "Size doesn't matter!");
	public static final Card spelleater = new UnitTemplate(74, "Arcane Devourer", "frostfire.png", 7, RANK_UNCOMMON, 9,
			5, 2).setParticleType("SWIRL").addAbility(CardAbility.soar).addAbility(CardAbility.hovering)
					.addAbility(CardAbility.spellsteal);
	public static final Card apprentice = new UnitTemplate(75, "Apprentice Mage", "apprentice.png", 2, RANK_UNCOMMON, 3,
			1, 3).setParticleType("SWIRL").addAbility(CardAbility.study).setShouts("I hope I'm not late.",
					"No risk assessment?", "I don't recall seeing&this in the lesson outline.");
	public static final Card magehunter = new UnitTemplate(76, "Rahkan Mage Hunter", "magehunter.png", 5, RANK_UNCOMMON,
			5, 4, 4).setParticleType("SWIRL").addAbility(CardAbility.spelllock).setShouts("Never trust the arcane.",
					"One day you'll see!", "Back down!");
	public static final Card polymage = new UnitTemplate(77, "Helmswood Arcanist", "soothe.png", 6, RANK_UNCOMMON, 6, 5,
			3).setParticleType("SWIRL").addAbility(CardAbility.mimic).addAbility(CardAbility.study);
	public static final Card prowler = new UnitTemplate(78, "Blackmoon Prowler", "highlandprowler.png", 5,
			RANK_UNCOMMON, 5, 6, 3).setParticleType("SWIRL").addAbility(CardAbility.energetic).setFamily(Family.beast);
	public static final Card yeti = new UnitTemplate(79, "Ramsonian Yeti", "yeti.png", 4, RANK_UNCOMMON, 5, 4, 3)
			.setParticleType("SHATTER").addAbility(CardAbility.chill).addAbility(CardAbility.wintersembrace);
	public static final Card icegoblin = new UnitTemplate(80, "Frost Goblin", "snowgoblin.png", 1, RANK_COMMON, 2, 2, 2)
			.setParticleType("SHATTER").addAbility(CardAbility.energetic).addAbility(CardAbility.wintersembrace);
	public static final Card devourer = new UnitTemplate(81, "The Nexus Devourer", "voideater.png", 7, RANK_REGAL, 7, 6,
			4).setParticleType("SWIRL").addAbility(CardAbility.mimic).addAbility(CardAbility.devour)
					.setFamily(Family.demon)
					.setShouts("Trifling worms!", "A mere setback...", "THE VOID CONSUMES ALL!");
	public static final Card essence = new UnitTemplate(82, "Essence Siphoner", "violetprince.png", 7, RANK_COMMON, 6,
			6, 3, CardSet.SET_WAR).setParticleType("SWIRL").addAbility(CardAbility.siphonessence)
					.addAbility(CardAbility.spiritnova);
	public static final Card phalanx = new UnitTemplate(83, "Phalanx", "phalanx.png", 2, RANK_COMMON, 1, 1, 2,
			CardSet.SET_WAR).setParticleType("SWIRL").addAbility(CardAbility.formation);
	public static final Card undertaker = new UnitTemplate(84, "Crypt Thief", "undertaker.png", 6, RANK_MYTHIC, 5, 4, 3)
			.setParticleType("SKULL").addAbility(CardAbility.cryptthief).setFamily(Family.undead)
			.setShouts("I am risen!", "I return... To the crypt...", "Feel the wrath of undeath!");
	public static final Card shapeshifter = new UnitTemplate(85, "Shapeshifter", "shapeshifter.png", 3, RANK_MYTHIC, 4,
			3, 3).setParticleType("SWIRL").addAbility(CardAbility.evolve).setShouts("This isn't even my final form!",
					"Couldn't even give me a chance?", "Away with you!");
	public static final Card cadrius = new UnitTemplate(87, "Cadrius Moore", "vinciron.png", 5, RANK_REGAL, 9, 5, 4,
			CardSet.SET_IVORGLEN).setParticleType("SKULL").addAbility(CardAbility.plague).setFamily(Family.undead);
	public static final Card wartusk = new UnitTemplate(88, "Wartusk", "warboar.png", 6, RANK_UNCOMMON, 8, 8, 5)
			.setParticleType("BLOOM").addAbility(CardAbility.charger).addAbility(CardAbility.mount)
			.addAbility(CardAbility.trample).setFamily(Family.beast);
	public static final Card nightmare = new UnitTemplate(89, "Nightmare", "nightmare.png", 5, RANK_MYTHIC, 6, 6, 4)
			.setParticleType("FIRE").addAbility(CardAbility.mount).addAbility(CardAbility.energetic)
			.addAbility(CardAbility.shadows).setFamily(Family.undead);
	public static final Card stallion = new UnitTemplate(90, "War Stallion", "warsteed.png", 3, RANK_COMMON, 3, 2, 5,
			CardSet.SET_WAR).setParticleType("IRON").addAbility(CardAbility.mount).setFamily(Family.beast);
	public static final Card mercenary = new UnitTemplate(91, "Rakhan Mercenary", "guardian.png", 2, RANK_COMMON, 3, 5,
			4).setParticleType("IRON").addAbility(CardAbility.mercenary);
	public static final Card gambler = new UnitTemplate(92, "Rakhan Gambler", "gambler.png", 3, RANK_UNCOMMON, 3, 3, 3)
			.setParticleType("SWIRL").addAbility(CardAbility.gamble);
	public static final Card volcanyr = new UnitTemplate(93, "Volcanaros", "volcanyr.png", 10, RANK_REGAL, 12, 12, 3)
			.setFamily(Family.elemental).setParticleType("FIRE").addAbility(CardAbility.erupt)
			.addAbility(CardAbility.scorchground)
			.setShouts("THE END DRAWS NEAR!", "THE FIRE BURNS ON!", "BUURRRRNN!!!");
	public static final Card drunk = new UnitTemplate(94, "Drunk Reveler", "drunk.png", 2, RANK_COMMON, 2, 2, 3)
			.setParticleType("BLOOM").addAbility(CardAbility.drink).addAbility(CardAbility.share)
			.setShouts("I've only had a couple... hic!", "That hurt.", "I got this one.").setListable(false);
	public static final Card tavern = new UnitTemplate(95, "Rakhan Tavern", "tavern.png", 2, RANK_UNCOMMON, 8, 0, 0)
			.setParticleType("SWIRL").addAbility(CardAbility.recruit);
	public static final Card stormrider = new UnitTemplate(96, "Storm Archer", "stormrider.png", 4, RANK_COMMON, 5, 2,
			3, CardSet.SET_WAR).setParticleType("IRON").addAbility(CardAbility.snipe).addAbility(CardAbility.hovering)
					.addAbility(CardAbility.soar);
	public static final Card wildmage = new UnitTemplate(97, "Wild Mage", "wildmage.png", 4, RANK_MYTHIC, 1, 1, 2)
			.setParticleType("SWIRL").addAbility(CardAbility.wildmagic).setShouts("You are not prepared!", null, null);
	public static final Card dragonhunter = new UnitTemplate(98, "Dragon Hunter", "dragonhunter.png", 4, RANK_MYTHIC, 4,
			4, 3, CardSet.SET_EXPEDITION).setParticleType("SWIRL").addAbility(CardAbility.slay)
					.setShouts("Show me the beast!", "This cannot be!", "Burn, fiend!");
	public static final Card disloyalpirate = new UnitTemplate(99, "Disloyal Buccaneer", "girlpirate.png", 4,
			RANK_COMMON, 6, 7, 4).setParticleType("IRON").addAbility(CardAbility.mercenary);
	public static final Card cupper = new UnitTemplate(200, "Ben From the Future", "cupper.png", 6, RANK_REGAL, 6, 8, 4)
			.setParticleType("IRON").addAbility(CardAbility.meditate).addAbility(CardAbility.spellsteal);
	public static final Card yorglankhar = new UnitTemplate(201, "Yorglankhar", "yorglankhar.png", 5, RANK_REGAL, 9, 6,
			4).setParticleType("FLASH").addAbility(CardAbility.guilt).addAbility(CardAbility.abolish)
					.addAbility(CardAbility.heal)
					.setShouts("The prohet returns!&Take arms, soldiers of the North!&Heed the call of the light!",
							"My burden is lifted.", "I will not fail my people again!");
	public static final Card necromancer = new UnitTemplate(202, "Deathlord Aba-Toth", "necromancer.png", 4, RANK_REGAL,
			1, 2, 2).setParticleType("VOID").addAbility(CardAbility.reanimate).addAbility(CardAbility.cryptthief)
					.addAbility(CardAbility.epidemic).setFamily(Family.undead)
					.setShouts("I am death itself!", "How can you kill that which has no life?", "Join me in undeath!");
	public static final Card spiritdancer = new UnitTemplate(203, "The Spirit-Dancer", "student.png", 5, RANK_REGAL, 7,
			3, 4).setParticleType("HEAL").addAbility(CardAbility.learn).addAbility(CardAbility.mindburst)
					.setShouts("Knowledge is power.", "I did not foresee such an ending.", "A simple maneuvre.");
	public static final Card engineer = new UnitTemplate(204, "Grand Engineer Stread", "stread.png", 8, RANK_REGAL, 9,
			2, 3).setParticleType("IRON").addAbility(CardAbility.tinker).setShouts("Hail! Stread is here.",
					"How droll.", "Buckle up!");
	public static final Card professor = new UnitTemplate(205, "Rapturon Professor", "professor.png", 4, RANK_COMMON, 5,
			3, 3).setParticleType("SWIRL").addAbility(CardAbility.curse).addAbility(CardAbility.teach);
	public static final Card trainer = new UnitTemplate(206, "Spaltyrian Champion", "champion.png", 7, RANK_UNCOMMON, 4,
			7, 3, CardSet.SET_WAR).setParticleType("HOLY").addAbility(CardAbility.strike);
	public static final Card commander = new UnitTemplate(207, "Confident Leader", "marshal.png", 6, RANK_COMMON, 7, 5,
			3).setParticleType("SWIRL").addAbility(CardAbility.inspire).addAbility(CardAbility.teach);
	public static final Card edgewalker = new UnitTemplate(208, "Edgewalker", "edgewalker.png", 2, RANK_MYTHIC, 3, 2, 3)
			.setParticleType("SWIRL").addAbility(CardAbility.liberate).addAbility(CardAbility.teach)
			.setShouts("I walk a fine line.", "It is... No matter.", "Face judgement!");
	public static final Card farmer = new UnitTemplate(209, "Eiraph Farmer", "farmer.png", 4, RANK_UNCOMMON, 4, 2, 3)
			.setParticleType("BLOOM").addAbility(CardAbility.plant);
	public static final Card pumpkin = new UnitTemplate(210, "Pumpkin", "pumpkin.png", 0, RANK_COMMON, 1, 0, 0)
			.setParticleType("HEAL").addAbility(CardAbility.growing).addAbility(CardAbility.feed)
			.addAbility(CardAbility.germinate).addAbility(CardAbility.minion).setListable(false);
	public static final Card bloodcap = new UnitTemplate(211, "Bloodcap Mushroom", "mushroom.png", 0, RANK_COMMON, 1, 0,
			0).setParticleType("BLOOD").addAbility(CardAbility.growing).addAbility(CardAbility.toxic)
					.addAbility(CardAbility.germinate).addAbility(CardAbility.minion).setListable(false);
	public static final Card firestalk = new UnitTemplate(212, "Firestalk", "firestalk.png", 0, RANK_COMMON, 1, 0, 0)
			.setParticleType("FIRE").addAbility(CardAbility.growing).addAbility(CardAbility.firepower)
			.addAbility(CardAbility.germinate).addAbility(CardAbility.minion).setListable(false);
	public static final Card marshal = new UnitTemplate(213, "Rallying Commander", "commander.png", 8, RANK_COMMON, 8,
			8, 4).setParticleType("IRON").addAbility(CardAbility.energetic).addAbility(CardAbility.teach);
	public static final Card banner = new UnitTemplate(214, "War Banner", "banner.png", 0, RANK_COMMON, 3, 0, 0)
			.setParticleType("FIRE").addAbility(CardAbility.inspire).addAbility(CardAbility.minion).setListable(false);
	public static final Card magerogue = new UnitTemplate(215, "Mage-Rogue Tutor", "magerogue.png", 6, RANK_MYTHIC, 5,
			5, 2).setParticleType("SWIRL").addAbility(CardAbility.spellsteal).addAbility(CardAbility.teach)
					.setShouts("My students are many.", "Impossible!", "Taste my blade!");
	public static final Card stonefist = new UnitTemplate(216, "Injured Stonefist", "hesacious.png", 3, RANK_COMMON, 1,
			6, 3).setParticleType("SLASH").addAbility(CardAbility.leap);
	public static final Card archer = new UnitTemplate(217, "Risen Marksman", "archer.png", 2, RANK_UNCOMMON, 2, 1, 2)
			.setParticleType("SLASH").addAbility(CardAbility.snipe).setFamily(Family.undead);
	public static final Card guardian = new UnitTemplate(218, "Pestilent Thrall", "servant.png", 1, RANK_COMMON, 2, 2,
			3).setParticleType("SKULL").addAbility(CardAbility.epidemic).setFamily(Family.undead);
	public static final Card bane = new UnitTemplate(219, "Bane of Fenra", "bane.png", 8, RANK_REGAL, 8, 8, 4,
			CardSet.SET_IVORGLEN).setParticleType("SKULL").addAbility(CardAbility.confusion).setFamily(Family.undead)
					.setShouts("Where...", "Lay me to rest.", "Who?");
	public static final Card conjurer = new UnitTemplate(220, "Pip-Elf Conjurer", "conjurer.png", 4, RANK_MYTHIC, 5, 2,
			3, CardSet.SET_IVORGLEN).setParticleType("BLOOM").addAbility(CardAbility.conjure).setShouts("Ooooot!",
					"Ree! Most unfortunate.", "Pip pip!");
	public static final Card templar = new UnitTemplate(221, "Righteous Templar", "templar.png", 9, RANK_MYTHIC, 8, 4,
			3).setParticleType("HOLY").addAbility(CardAbility.strike).addAbility(CardAbility.smite)
					.addAbility(CardAbility.heal2).addAbility(CardAbility.spelllock)
					.setShouts("The undead incursion must be stopped!", "Why have you forsaken me?",
							"Bow before me, insect!");
	public static final Card cardmaster = new UnitTemplate(222, "Dirty Cheat", "cheat.png", 6, RANK_COMMON, 5, 6, 3)
			.setParticleType("RUNE").addAbility(CardAbility.reshuffle);
	public static final Card sogarok = new UnitTemplate(223, "Fury of Sogarok", "sogarok.png", 8, RANK_MYTHIC, 8, 5, 2)
			.setFamily(Family.elemental).setParticleType("BLOOM").addAbility(CardAbility.fury)
			.setShouts("RRRAAAGGGEEE!", "NO! NO! NO!", "DIE! DIE!");
	public static final Card scientist = new UnitTemplate(224, "Kratt Scientist", "scientist.png", 5, RANK_MYTHIC, 3, 5,
			3, CardSet.SET_IVORGLEN).setFamily(Family.kratt).setParticleType("BLOOD").addAbility(CardAbility.harvest)
					.setShouts("SCIENCE! AHAHAHA!", "Yick! Not good.", "Reeee!");
	public static final Card darkweaver = new UnitTemplate(225, "Darkweaver", "darkweaver.png", 3, RANK_UNCOMMON, 3, 3,
			3, CardSet.SET_IVORGLEN).setParticleType("SKULL").addAbility(CardAbility.deathwish)
					.addAbility(CardAbility.teach).setCosts(3, 0, 2, 0);
	public static final Card abomination = new UnitTemplate(226, "Cursed One", "abomination.png", 4, RANK_COMMON, 6, 5,
			3).setParticleType("VOID").addAbility(CardAbility.shadows);
	public static final Card smoothtalker = new UnitTemplate(227, "Smooth Talker", "talker.png", 6, RANK_MYTHIC, 7, 3,
			3).setParticleType("HEAL").addAbility(CardAbility.persuade).setShouts("Fear not! I have arrived.",
					"Someone doesn't appreciate my genius.", "Avast!");
	public static final Card learner = new UnitTemplate(228, "Michael Cera", "cera.png", 3, RANK_UNCOMMON, 3, 2, 3)
			.setParticleType("MAGIC").addAbility(CardAbility.learn);
	public static final Card troy = new UnitTemplate(229, "Troy the Boy", "troy.png", 9, RANK_REGAL, 6, 9, 3)
			.setParticleType("EXPLOSION").addAbility(CardAbility.evermore).addAbility(CardAbility.meme)
			.addAbility(CardAbility.kill).setShouts("It's-A-Me! Troy!", "dang", "For the memes!");
	public static final Card chest = new UnitTemplate(230, "Treasure Chest", "chest.png", 0, RANK_COMMON, 6, 0, 0)
			.setParticleType("EXPLOSION").addAbility(CardAbility.lootfilled).addAbility(CardAbility.minion)
			.setListable(false);
	public static final Card shadowgate = new UnitTemplate(231, "Black Gate", "shadowgate.png", 5, RANK_MYTHIC, 3, 0, 0)
			.setParticleType("VOID").addAbility(CardAbility.shadowgate).addAbility(CardAbility.barrier);
	public static final Card wolfscout = new UnitTemplate(232, "Grinn Pup", "luposscout.png", 2, RANK_COMMON, 2, 2, 4,
			CardSet.SET_NIGHT).setParticleType("SLASH").addAbility(CardAbility.bite).setFamily(Family.werewolf);
	public static final Card wolfstalker = new UnitTemplate(233, "Grinn Stalker", "luposhunter.png", 4, RANK_MYTHIC, 4,
			4, 4, CardSet.SET_NIGHT).setParticleType("SLASH").addAbility(CardAbility.bite).addAbility(CardAbility.hunt)
					.setFamily(Family.werewolf);
	public static final Card wolfironfang = new UnitTemplate(234, "Grinn Ironfang", "luposdestroyer.png", 7,
			RANK_MYTHIC, 6, 5, 4, CardSet.SET_NIGHT).setParticleType("SLASH").addAbility(CardAbility.bite)
					.addAbility(CardAbility.howl).addAbility(CardAbility.legion).setFamily(Family.werewolf);
	public static final Card wolfhulk = new UnitTemplate(235, "Grinn Hulk", "luposgoliath.png", 5, RANK_UNCOMMON, 7, 2,
			3, CardSet.SET_NIGHT).setParticleType("SLASH").addAbility(CardAbility.feast).addAbility(CardAbility.pack)
					.setFamily(Family.werewolf);
	public static final Card leaper = new UnitTemplate(236, "The Night Leaper", "luposslayer.png", 8, RANK_REGAL, 8, 7,
			2, CardSet.SET_NIGHT).setParticleType("SLASH").addAbility(CardAbility.bite).addAbility(CardAbility.legion)
					.addAbility(CardAbility.lunge).setFamily(Family.werewolf);
	public static final Card maru = new UnitTemplate(237, "Khovani Demigod", "maru.png", 8, RANK_MYTHIC, 9, 5, 4)
			.setParticleType("VOID").addAbility(CardAbility.shadowburn).addAbility(CardAbility.oblivion);
	public static final Card barracksMilitia = new UnitTemplate(238, "Militia", "militia.png", 3, RANK_COMMON, 3, 2, 3)
			.setParticleType("IRON").setListable(false);
	public static final Card barracksKnight = new UnitTemplate(239, "Knight", "knight.png", 5, RANK_COMMON, 5, 4, 4)
			.setParticleType("IRON").setListable(false);
	public static final Card rlgybar = new UnitTemplate(240, "Rlg'Ybar", "rgaedus.png", 1, RANK_MYTHIC, 6, 6, 4,
			CardSet.SET_IVORGLEN).setParticleType("SWIRL").setFamily(Family.eldritch).setListable(false);
	public static final Card eldritchcultist = new UnitTemplate(241, "Eldritch Cultist", "eldritchcultist.png", 4,
			RANK_COMMON, 4, 3, 3, CardSet.SET_SEA).addAbility(CardAbility.eldritchPower).setParticleType("SWIRL");
	public static final Card eldritchspawn = new UnitTemplate(242, "Spawn of Thurgyl", "eldritchleech.png", 2,
			RANK_COMMON, 3, 2, 4, CardSet.SET_SEA).setParticleType("SWIRL").addAbility(CardAbility.amphibious)
					.setFamily(Family.eldritch);
	public static final Card eldritchstormspawn = new UnitTemplate(243, "Eldritch Surger", "eldritchcrackler.png", 5,
			RANK_MYTHIC, 0, 3, 4, CardSet.SET_IVORGLEN).addAbility(CardAbility.barrier).addAbility(CardAbility.aquatic)
					.addAbility(CardAbility.eldritchCrackle).setParticleType("SWIRL").setFamily(Family.eldritch);
	public static final Card eldritchvillager = new UnitTemplate(244, "Infested Noble", "eldritchvillager.png", 1,
			RANK_UNCOMMON, 2, 1, 3, CardSet.SET_SEA).addAbility(CardAbility.eldritchCorruption)
					.setParticleType("SWIRL");
	public static final Card eldritchsummoner = new UnitTemplate(245, "Eldritch Summoner", "eldritchsummoner.png", 4,
			RANK_UNCOMMON, 5, 2, 3, CardSet.SET_IVORGLEN).addAbility(CardAbility.eldritchRitual)
					.addAbility(CardAbility.eldritchSummon).setParticleType("SWIRL");
	public static final Card eldritchdevourer = new UnitTemplate(246, "Eldritch Terror", "eldritchterror.png", 8,
			RANK_MYTHIC, 5, 8, 0, CardSet.SET_SEA).addAbility(CardAbility.aquatic).addAbility(CardAbility.eldritchSwirl)
					.setParticleType("SWIRL").setFamily(Family.eldritch);
	public static final Card eldritchoracle = new UnitTemplate(247, "Oracle of Thurgyl", "eldritchoracle.png", 6,
			RANK_MYTHIC, 3, 5, 3, CardSet.SET_SEA).addAbility(CardAbility.amphibious).addAbility(CardAbility.teach)
					.addAbility(CardAbility.eldritchSlam).addAbility(CardAbility.eldritchHeal).setParticleType("SWIRL")
					.setFamily(Family.eldritch);
	public static final Card eldritchdestroyer = new UnitTemplate(248, "Eldritch Behemoth", "eldritchreaper.png", 6,
			RANK_MYTHIC, 5, 3, 3, CardSet.SET_SEA).addAbility(CardAbility.amphibious).addAbility(CardAbility.strike)
					.addAbility(CardAbility.eldritchLegion).addAbility(CardAbility.soar).setParticleType("SWIRL")
					.setFamily(Family.eldritch);
	public static final Card eldritchlegendary = new UnitTemplate(249, "Thurgyl the Ancient Sleeper", "greatone.png",
			10, RANK_REGAL, 8, 8, 2, CardSet.SET_SEA).addAbility(CardAbility.amphibious)
					.addAbility(CardAbility.eldritchLeg).setParticleType("SWIRL").setFamily(Family.eldritch);
	public static final Card kraken = new UnitTemplate(250, "Ancient Kraken", "giantkraken.png", 7, RANK_MYTHIC, 9, 4,
			3, CardSet.SET_IVORGLEN).setParticleType("SWIRL").addAbility(CardAbility.amphibious)
					.addAbility(CardAbility.eldritchEat).setFamily(Family.beast);
	public static final Card eldritchtormentor = new UnitTemplate(251, "Eldritch Darkwraith", "eldritchtormentor.png",
			5, RANK_COMMON, 3, 4, 3, CardSet.SET_SEA).addAbility(CardAbility.eldritchMorph).setParticleType("SWIRL")
					.setFamily(Family.eldritch);
	public static final Card eldritchqueen = new UnitTemplate(252, "Queen Ylg'Kebeth", "eldritchqueen.png", 2,
			RANK_REGAL, 5, 0, 1, CardSet.SET_SEA).addAbility(CardAbility.aquatic).addAbility(CardAbility.forbiddenMagic)
					.addAbility(CardAbility.eldritchHeal).setParticleType("SWIRL").setFamily(Family.eldritch);
	public static final Card goldMine = new StructureTemplate(253, "Gold Mine", "goldmine.png", 2, RANK_COMMON, 7, 1, 0)
			.addAbility(CardAbility.goldMine).addAbility(CardAbility.collapsing).setParticleType("SLASH")
			.setListable(false);
	public static final Card goldVein = new StructureTemplate(265, "Gold Vein", "goldore.png", 1, RANK_COMMON, 3, 0, 0)
			.addAbility(CardAbility.goldMine).addAbility(CardAbility.collapsing).setParticleType("SLASH")
			.setListable(false);
	public static final Card tree = new StructureTemplate(266, "Sturdy Oak", "oak.png", 1, RANK_COMMON, 4, 0, 0)
			.addAbility(CardAbility.tree).setParticleType("SLASH").setListable(false);
	public static final Card orchard = new StructureTemplate(267, "Helmswood Orchard", "orchard.png", 4, RANK_COMMON, 6,
			0, 0).addAbility(CardAbility.plantTree).setParticleType("SLASH").setListable(true);
	public static final Card lumbermill = new StructureTemplate(268, "Timbersaw Mill", "mill.png", 3, RANK_COMMON, 5, 0,
			0).addAbility(CardAbility.buzzsaw).setParticleType("SLASH").setListable(true);
	public static final Card trollbrothers = new UnitTemplate(269, "Krog and Mag", "trollbrothers.png", 5, RANK_REGAL,
			5, 5, 2).addAbility(CardAbility.broswap).setParticleType("SLASH").setListable(true);
	public static final Card burrower = new UnitTemplate(270, "Gorge Burrower", "burrower.png", 3, RANK_UNCOMMON, 1, 4,
			5, CardSet.SET_IVORGLEN).addAbility(CardAbility.burrow).setParticleType("SLASH").setListable(true);
	public static final Card darkent = new UnitTemplate(271, "Ivorglen Treant", "darktreant.png", 5, RANK_MYTHIC, 2, 1,
			2, CardSet.SET_IVORGLEN).setFamily(Family.elemental).addAbility(CardAbility.barrier_tree)
					.addAbility(CardAbility.plantTree).setParticleType("SLASH").setListable(true);
	public static final Card agleanoracle = new UnitTemplate(272, "Aglean Farseer", "oracle.png", 4, RANK_MYTHIC, 3, 3,
			3, CardSet.SET_IVORGLEN).addAbility(CardAbility.agleanVision).setParticleType("SLASH").setListable(true);
	public static final Card fungalwart = new UnitTemplate(273, "Fungal Wart", "fungalone.png", 3, RANK_MYTHIC, 3, 3, 3,
			CardSet.SET_IVORGLEN).addAbility(CardAbility.spore).setParticleType("SLASH").setListable(true);
	public static final Card crowtalon = new UnitTemplate(274, "Crowtalon", "herald.png", 5, RANK_REGAL, 5, 5, 0,
			CardSet.SET_IVORGLEN).addAbility(CardAbility.buzzsaw).setParticleType("SLASH").setListable(true);
	public static final Card wraith = new UnitTemplate(275, "The Wraith Knight", "wraith.png", 7, RANK_REGAL, 4, 5, 1,
			CardSet.SET_IVORGLEN).setFamily(Family.undead).addAbility(CardAbility.wraithstorm)
					.setParticleType("FELMAGIC").setShouts("FACE THE GRAVE WYRM'S FIRE!", "I HAVE RETURNED BEFORE!",
							"YOUR NAME IS WRITTEN BENEATH THE WORLD.")
					.setListable(true);
	public static final Card wretched = new UnitTemplate(276, "Underwood Wretched", "wretched.png", 2, RANK_COMMON, 4,
			4, 0, CardSet.SET_IVORGLEN).setFamily(Family.undead).setParticleType("SLASH").setListable(true);
	public static final Card batBaby = new UnitTemplate(277, "Underwood Batling", "babybat.png", 2, RANK_COMMON, 3, 2,
			4, CardSet.SET_IVORGLEN).setFamily(Family.beast).setParticleType("SLASH").addAbility(CardAbility.soar)
					.addAbility(CardAbility.hovering).setListable(true);
	public static final Card bearDruid = new UnitTemplate(278, "Fen Druid", "beardruid.png", 2, RANK_COMMON, 4, 4, 2,
			CardSet.SET_IVORGLEN).addAbility(CardAbility.guilt).setParticleType("BLOOM").setListable(true);
	public static final Card bloodprince = new UnitTemplate(279, "The Blood Prince", "bloodking.png", 8, RANK_REGAL, 10,
			4, 2, CardSet.SET_IVORGLEN).addAbility(CardAbility.bloodguard).setFamily(Family.undead)
					.setParticleType("SLASH").setListable(true);
	public static final Card bloodwitch = new UnitTemplate(280, "Troll Bloodwitch", "bloodwitch.png", 7, RANK_MYTHIC, 1,
			2, 2, CardSet.SET_IVORGLEN).addAbility(CardAbility.bloodpuppet).setParticleType("SLASH").setListable(true);
	public static final Card boulderback = new UnitTemplate(281, "Anurim Boulderback", "boulderback.png", 6,
			RANK_UNCOMMON, 8, 0, 2, CardSet.SET_IVORGLEN).setFamily(Family.beast).addAbility(CardAbility.raiseEarth)
					.addAbility(CardAbility.charger).setParticleType("SLASH").setListable(true);
	public static final Card butcher = new UnitTemplate(282, "Maggut the Butcher", "butcher.png", 6, RANK_REGAL, 7, 2,
			2, CardSet.SET_IVORGLEN).setFamily(Family.undead).addAbility(CardAbility.meatsaw)
					.addAbility(CardAbility.fleshrebirth).setParticleType("BLOOD").setListable(true);
	public static final Card kidnapper = new UnitTemplate(283, "Skeletal Kidnapper", "kidnapper.png", 4, RANK_MYTHIC, 3,
			4, 2, CardSet.SET_IVORGLEN).addAbility(CardAbility.kidnap).setParticleType("SLASH").setListable(true);
	public static final Card krattAlchemist = new UnitTemplate(284, "Kratt Alchemist", "kratAlchemist.png", 3,
			RANK_UNCOMMON, 3, 2, 2, CardSet.SET_IVORGLEN).setFamily(Family.kratt).addAbility(CardAbility.deathpotion)
					.addAbility(CardAbility.stinkbomb).addAbility(CardAbility.abolish).setParticleType("SLASH")
					.setListable(true);
	public static final Card krattMystic = new UnitTemplate(285, "Kratt Mystic", "kratMystic.png", 4, RANK_UNCOMMON, 3,
			4, 2).setFamily(Family.kratt).addAbility(CardAbility.krattRitual).addAbility(CardAbility.heal1)
					.setParticleType("SLASH").setListable(true);
	public static final Card krattInfector = new UnitTemplate(286, "Plaguerat Skerros", "kratInfector.png", 5,
			RANK_REGAL, 4, 4, 5).setFamily(Family.kratt).addAbility(CardAbility.infectious)
					.addAbility(CardAbility.swarmlord).setParticleType("BLOOM").setListable(true);
	public static final Card fenlurker = new UnitTemplate(287, "Ivorglen Lurker", "lurker.png", 4, RANK_UNCOMMON, 7, 1,
			2, CardSet.SET_IVORGLEN).addAbility(CardAbility.eldritchConjure).setFamily(Family.eldritch)
					.setParticleType("SLASH").setListable(true);
	public static final Card pendleton = new UnitTemplate(288, "Arthur-David Pendleton", "arthurdavid.png", 4,
			RANK_REGAL, 8, 3, 2, CardSet.SET_IVORGLEN).setParticleType("SLASH").setListable(true);
	public static final Card pendleton1 = new UnitTemplate(289, "The Fallen Coward", "runebone.png", 4, RANK_REGAL, 8,
			3, 2, CardSet.SET_IVORGLEN).setFamily(Family.undead).setParticleType("SLASH").setListable(false);
	public static final Card spirithound = new UnitTemplate(290, "Spirit Hound", "spirithoundtoken.png", 2, RANK_COMMON,
			2, 2, 3).setFamily(Family.elemental).addAbility(CardAbility.spirithound).setParticleType("SLASH").setListable(false);
	public static final Card worldbreaker = new UnitTemplate(291, "Soul Elemental", "stormbreaker.png", 4, RANK_COMMON,
			4, 3, 3).setFamily(Family.elemental).addAbility(CardAbility.stormsoul).setParticleType("SLASH")
					.setListable(true);
	public static final Card titanaros = new UnitTemplate(292, "Titanaros the Enormous", "titanaros.png", 8, RANK_REGAL,
			10, 6, 3, CardSet.SET_SEA).setFamily(Family.beast).addAbility(CardAbility.titanichunger)
					.addAbility(CardAbility.amphibious).setParticleType("POOL").setListable(true);
	public static final Card raptorVenom = new UnitTemplate(293, "Spitting Raptor", "venomraptor.png", 3, RANK_UNCOMMON,
			2, 3, 2, CardSet.SET_JUNGLE).addAbility(CardAbility.poisonspit).setFamily(Family.beast)
					.setParticleType("BOON").setListable(true);
	public static final Card raptorGreen = new UnitTemplate(294, "Verdant Raptor", "verdantraptor.png", 5, RANK_COMMON,
			5, 4, 4, CardSet.SET_JUNGLE).addAbility(CardAbility.regrowth).setFamily(Family.beast)
					.setParticleType("BOON").setListable(true);
	public static final Card raptorRunner = new UnitTemplate(295, "Swift Raptor", "raptorrunner.png", 2, RANK_COMMON, 2,
			3, 4, CardSet.SET_JUNGLE).setFamily(Family.beast).addAbility(CardAbility.sprint).setParticleType("BOON")
					.setListable(true);
	public static final Card gorillaCrusher = new UnitTemplate(296, "Primal Necksnapper", "wargorilla.png", 6,
			RANK_UNCOMMON, 5, 5, 2, CardSet.SET_JUNGLE).addAbility(CardAbility.necksnap).setFamily(Family.beast)
					.setParticleType("BOON").setListable(true);
	public static final Card gorillaShaman = new UnitTemplate(297, "Primal Shaman", "gorillashaman.png", 3,
			RANK_UNCOMMON, 3, 2, 4, CardSet.SET_JUNGLE).setFamily(Family.beast).addAbility(CardAbility.energyaoe)
					.setParticleType("BURST").setListable(true);
	public static final Card gorillaProtector = new UnitTemplate(298, "Primal Matriarch", "gorillavslions.png", 3,
			RANK_UNCOMMON, 2, 4, 4, CardSet.SET_JUNGLE).addAbility(CardAbility.protective).setFamily(Family.beast)
					.setParticleType("BOON").setListable(true);
	public static final Card gorillaLeaper = new UnitTemplate(299, "Canopy Leaper", "gorillahunt.png", 9, RANK_UNCOMMON,
			7, 9, 4, CardSet.SET_JUNGLE).addAbility(CardAbility.conjurebeast).setFamily(Family.beast)
					.setParticleType("BOON").setListable(true);
	public static final Card dryad = new UnitTemplate(300, "Friendly Dryad", "dryad.png", 4, RANK_UNCOMMON, 3, 4, 3,
			CardSet.SET_JUNGLE).addAbility(CardAbility.beastheal).setParticleType("BOON").setListable(true);
	public static final Card deckworm = new UnitTemplate(301, "Aglean Paperworm", "deckworm.png", 2, RANK_UNCOMMON, 3,
			3, 3, CardSet.SET_IVORGLEN).setFamily(Family.beast).addAbility(CardAbility.deckwormdeath)
					.addAbility(CardAbility.deckwormstay).addAbility(CardAbility.deckwormhold).setParticleType("BOON")
					.setListable(true);
	public static final Card vinciron = new UnitTemplate(302, "Vinciron the Black", "nightmarebat.png", 3,
			RANK_UNCOMMON, 2, 2, 4, CardSet.SET_IVORGLEN).setFamily(Family.beast).addAbility(CardAbility.soar)
					.addAbility(CardAbility.hovering).addAbility(CardAbility.sonicstrike).addAbility(CardAbility.vincdeath).setParticleType("BOON").setListable(true);
	public static final Card fenZombie = new UnitTemplate(303, "Fetid Bogfist", "swampzombie.png", 3, RANK_UNCOMMON, 2,
			2, 4, CardSet.SET_IVORGLEN).setFamily(Family.beast).addAbility(CardAbility.sludgify).setParticleType("BOON")
					.setListable(true);
	public static final Card defiler = new UnitTemplate(304, "Defiler", "defiler.png", 4, RANK_MYTHIC, 3, 5, 3,
			CardSet.SET_IVORGLEN).setFamily(Family.undead).addAbility(CardAbility.blacklotus).setParticleType("BOON")
					.setListable(true);
	public static final Card rootedOne = new UnitTemplate(305, "Rooted Bog-Ent", "rooted.png", 4, RANK_UNCOMMON, 7, 3,
			-9, CardSet.SET_IVORGLEN).setFamily(Family.elemental).addAbility(CardAbility.uproot).setParticleType("BOON")
					.setListable(true);
	public static final Card deadwoodTree = new UnitTemplate(306, "The Deadwood Tree", "deadwood.png", 5, RANK_REGAL, 4,
			4, 2, CardSet.SET_IVORGLEN).setFamily(Family.elemental).setFamily(Family.undead)
					.addAbility(CardAbility.deadroot).addAbility(CardAbility.deadwoodpulse).setParticleType("LEAVES")
					.setListable(true);
	public static final Card dwarfSmith = new UnitTemplate(307, "Dwarven Smith", "dwarvensmith.png", 5, RANK_COMMON, 4,
			4, 2).setFamily(Family.undead).addAbility(CardAbility.repair).setParticleType("BOON").setListable(true);
	public static final Card dwarfPriest = new UnitTemplate(308, "Dwarven Bishop", "dwarvenpriest.png", 5,
			RANK_UNCOMMON, 4, 4, 2).setFamily(Family.undead).addAbility(CardAbility.heal2).setParticleType("BOON")
					.setListable(true);
	public static final Card mrondir = new UnitTemplate(309, "Mrondir Stormbraid", "dwarvenking.png", 5, RANK_REGAL, 4,
			4, 2).setFamily(Family.undead).addAbility(CardAbility.forge).setParticleType("BOON").setListable(true);
	public static final Card darkshaman = new UnitTemplate(310, "Deadwood Shaman", "darkwolfshaman.png", 3, RANK_COMMON,
			3, 3, 2, CardSet.SET_IVORGLEN).setFamily(Family.undead).addAbility(CardAbility.corruptHealing)
					.setParticleType("BOON").setListable(true);
	public static final Card volcano = new StructureTemplate(311, "Mount Omaz", "mountcudi.png", 3, RANK_REGAL, 80, 0,
			0, CardSet.SET_TRIBELANDS).addAbility(CardAbility.volcano).setParticleType("BOON")
					.setListable(true);
	public static final Card merman = new UnitTemplate(312, "Merfolk Militia", "merman.png", 1, RANK_COMMON, 3, 3, 3)
			.addAbility(CardAbility.aquatic).addAbility(CardAbility.splash).setParticleType("BUBBLE").setListable(true);
	public static final Card mermandemon = new UnitTemplate(313, "Murk Templar", "mermandemon.png", 4, RANK_COMMON, 5,
			5, 3).addAbility(CardAbility.aquatic).addAbility(CardAbility.waverider).setParticleType("BUBBLE")
					.setListable(true);
	public static final Card mermanevil = new UnitTemplate(314, "Serpentguard", "mermanevil.png", 7, RANK_MYTHIC, 8, 6,
			4, CardSet.SET_SEA).addAbility(CardAbility.amphibious).addAbility(CardAbility.whirlpool)
					.addAbility(CardAbility.stormbolt).setParticleType("BUBBLE").setListable(true);
	public static final Card mermanking = new UnitTemplate(315, "Trident Bearer", "mermanking.png", 4, RANK_UNCOMMON, 6,
			4, 3).addAbility(CardAbility.aquatic).addAbility(CardAbility.wavecrash).addAbility(CardAbility.waverider)
					.setParticleType("BUBBLE").setListable(true);
	public static final Card mermaidsiren = new UnitTemplate(316, "Murk Siren", "siren.png", 3, RANK_UNCOMMON, 4, 3, 2)
			.addAbility(CardAbility.aquatic).addAbility(CardAbility.splash).addAbility(CardAbility.healingwaters)
			.setParticleType("BUBBLE").setListable(true);
	public static final Card aquafrog = new UnitTemplate(317, "Jumping Toad", "bluefrog.png", 2, RANK_UNCOMMON, 2, 3, 3)
			.addAbility(CardAbility.amphibious).addAbility(CardAbility.leap).setParticleType("BUBBLE")
			.setListable(true);
	public static final Card toadking = new UnitTemplate(318, "Toadiah", "toadking.png", 8, RANK_REGAL, 8, 8, 4)
			.addAbility(CardAbility.amphibious).addAbility(CardAbility.spore).addAbility(CardAbility.leap)
			.addAbility(CardAbility.stomp).setParticleType("BUBBLE").setListable(true);
	public static final Card tidelord = new UnitTemplate(319, "Tidelord", "tidelord.png", 5, RANK_MYTHIC, 1, 1, 3,
			CardSet.SET_SEA).setFamily(Family.elemental).addAbility(CardAbility.aquatic)
					.addAbility(CardAbility.barrier_tidelord).setParticleType("BUBBLE").setListable(true);
	public static final Card waterelemental = new UnitTemplate(320, "Water Elemental", "waterelemental.png", 4,
			RANK_COMMON, 5, 2, 4, CardSet.SET_SEA).setFamily(Family.elemental).addAbility(CardAbility.amphibious)
					.addAbility(CardAbility.splash).addAbility(CardAbility.aquastrike).setParticleType("BUBBLE")
					.setListable(true);
	public static final Card sandElemental = new UnitTemplate(321, "Sand Golem", "sandgolem.png", 4, RANK_COMMON, 5, 4,
			3, CardSet.SET_ANURIM).setFamily(Family.elemental).addAbility(CardAbility.aoeslow).setParticleType("EARTH")
					.setListable(true);
	public static final Card sandTitan = new UnitTemplate(322, "Desert Titan", "deserttitan.png", 7, RANK_MYTHIC, 11,
			11, 0, CardSet.SET_ANURIM).addAbility(CardAbility.spelldecay).setFamily(Family.elemental)
					.setParticleType("EARTH").setListable(true);
	public static final Card desertrhino = new UnitTemplate(323, "Dusthorn Rider", "desertrhino.png", 5, RANK_COMMON, 4,
			5, 3, CardSet.SET_ANURIM).addAbility(CardAbility.rampage).setParticleType("BUBBLE").setListable(true);
	public static final Card desertraptor = new UnitTemplate(324, "Canyon Stalker", "desertraptor.png", 5, RANK_COMMON,
			4, 5, 3, CardSet.SET_ANURIM).addAbility(CardAbility.conjurespell).setParticleType("FIRE").setListable(true);
	public static final Card desertpacker = new UnitTemplate(325, "Kengai Packbeast", "desertpacker.png", 5,
			RANK_COMMON, 4, 5, 3, CardSet.SET_ANURIM).addAbility(CardAbility.packbeast).setParticleType("BUBBLE")
					.setListable(true);
	public static final Card desertdino = new UnitTemplate(326, "Kengai Dinorider", "dinostrider.png", 5, RANK_COMMON,
			4, 5, 3, CardSet.SET_ANURIM).addAbility(CardAbility.earthshaker).setParticleType("BUBBLE")
					.setListable(true);
	public static final Card desertnomad = new UnitTemplate(327, "Kengai Nomad", "nomad.png", 5, RANK_COMMON, 4, 5, 3,
			CardSet.SET_ANURIM).addAbility(CardAbility.sandglaive).setParticleType("BUBBLE").setListable(true);
	public static final Card rakaj = new UnitTemplate(328, "Rakaj the Ascended", "rakaj.png", 5, RANK_REGAL, 10, 10, 5,
			CardSet.SET_ANURIM).setFamily(Family.elemental).addAbility(CardAbility.elementalexplosion).addAbility(CardAbility.hovering).setParticleType("fx8_lighteningBall")
					.setListable(false);
	public static final Card srahnisandmage = new UnitTemplate(329, "Sarani Sandmancer", "sandmancer.png", 5,
			RANK_REGAL, 4, 5, 3, CardSet.SET_ANURIM).addAbility(CardAbility.createdesert).setParticleType("BUBBLE")
					.setListable(true);
	public static final Card koashira = new UnitTemplate(330, "The Koa-Shira", "soulchamber.png", 5, RANK_REGAL, 3, 3,
			0, CardSet.SET_ANURIM).addAbility(CardAbility.koashira).addAbility(CardAbility.releaseSouls)
					.addAbility(CardAbility.createGolem).setParticleType("BUBBLE").setListable(true);
	public static final Card duskstriders = new UnitTemplate(331, "The Dunestriders", "duskstriders.png", 7, RANK_REGAL,
			5, 7, 5, CardSet.SET_ANURIM).addAbility(CardAbility.snipe).addAbility(CardAbility.energetic)
					.addAbility(CardAbility.leap).addAbility(CardAbility.execute).addAbility(CardAbility.strike)
					.setParticleType("SLASH").setListable(true);
	public static final Card croc = new UnitTemplate(332, "Deepmaw Croc", "croc.png", 6, RANK_MYTHIC, 4, 6, 5,
			CardSet.SET_SEA).setFamily(Family.beast).addAbility(CardAbility.amphibious).addAbility(CardAbility.deathjaw)
					.addAbility(CardAbility.evasive).setParticleType("SLASH").setListable(true);
	public static final Card waygate = new UnitTemplate(333, "Arcane Gateway", "waygate.png", 4, RANK_COMMON, 3, 0, 0)
			.addAbility(CardAbility.swap).addAbility(CardAbility.unstablegateway).setParticleType("SWIRL")
			.setListable(false);
	public static final Card pyromancer = new UnitTemplate(334, "Elementalist", "pyromancer.png", 3, RANK_MYTHIC, 3, 2,
			3, CardSet.SET_JUNGLE).addAbility(CardAbility.flames).setParticleType("FIRE")
					.setShouts("I bring the fire!", "I am... Extinguished...", "Burn, weakling!").setListable(true);
	public static final Card eel = new UnitTemplate(335, "Electric Eel", "electriceel.png", 5, RANK_UNCOMMON, 5, 7, 3)
			.setFamily(Family.beast).addAbility(CardAbility.aquatic).addAbility(CardAbility.stormbolt)
			.setParticleType("SHATTER").setListable(true);
	public static final Card fish = new UnitTemplate(336, "Barrcuda", "snapper.png", 2, RANK_COMMON, 3, 3, 4)
			.setFamily(Family.beast).addAbility(CardAbility.aquatic).addAbility(CardAbility.chomp)
			.setParticleType("FIRE").setListable(true);
	public static final Card hydra = new UnitTemplate(337, "Crackling Hydra", "hydra.png", 8, RANK_UNCOMMON, 7, 7, 4,
			CardSet.SET_SEA).setFamily(Family.beast).addAbility(CardAbility.amphibious)
					.addAbility(CardAbility.stormbreath).setParticleType("SHATTER").setListable(true);
	public static final Card stormelemental = new UnitTemplate(338, "Storm Revenant", "stormelemental.png", 3,
			RANK_UNCOMMON, 2, 2, 3).setFamily(Family.elemental).addAbility(CardAbility.crackle)
					.setParticleType("SHATTER").setListable(true);
	public static final Card forestkeeper = new UnitTemplate(339, "Forest Guardian", "keeper.png", 2, RANK_UNCOMMON, 3,
			1, 3, CardSet.SET_JUNGLE).setFamily(Family.elemental).addAbility(CardAbility.vengeance)
					.setParticleType("BLOOM").setListable(true);
	public static final Card brigand = new UnitTemplate(340, "Helmswood Brigand", "bandit.png", 4, RANK_UNCOMMON, 3, 3,
			3).addAbility(CardAbility.slam).setParticleType("SLASH").setListable(true);
	public static final Card warhound = new UnitTemplate(341, "Warhound", "warhound.png", 4, RANK_UNCOMMON, 5, 3, 4,
			CardSet.SET_EXPEDITION).addAbility(CardAbility.finalfrenzy).addAbility(CardAbility.chomp)
					.setParticleType("BLOOM").setListable(true);
	public static final Card tyranosaur = new UnitTemplate(342, "Jungle Tyrant", "tryanosaur.png", 7, RANK_MYTHIC, 8, 6,
			3, CardSet.SET_TRIBELANDS).addAbility(CardAbility.finalfrenzy).addAbility(CardAbility.devourprey)
					.setParticleType("BLOOM").setListable(true);
	public static final Card amberhorn = new UnitTemplate(344, "Amberhorn", "amberdeer.png", 7, RANK_MYTHIC, 7, 7, 3,
			CardSet.SET_JUNGLE).addAbility(CardAbility.swipe).setParticleType("BLOOM").setListable(true);
	public static final Card sirenegg = new UnitTemplate(345, "Merfolk Egg", "nagaegg.png", 2, RANK_UNCOMMON, 3, 0, 0,
			CardSet.SET_SEA).addAbility(CardAbility.aquatic).addAbility(CardAbility.hatchSiren)
					.setParticleType("SHATTER").setListable(true);
	public static final Card ogreThrower = new UnitTemplate(346, "Boulder Thrower", "boulderthrower.png", 2,
			RANK_UNCOMMON, 20, 0, 0).addAbility(CardAbility.bouldertoss).setParticleType("IRON").setListable(false);
	public static final Card ogreKing = new UnitTemplate(347, "King Mudlug", "ogreking.png", 2, RANK_COMMANDER, 40, 0,
			0).addAbility(CardAbility.battlerage).setParticleType("IRON").setListable(false);
	public static final Card shady = new UnitTemplate(348, "Kengai Darkdealer", "kengaiking.png", 4, RANK_MYTHIC, 3, 2,
			3, CardSet.SET_ANURIM).addAbility(CardAbility.shadydeal).setParticleType("IRON").setListable(true);
	public static final Card elite = new UnitTemplate(349, "Royal Elite", "kengainoble.png", 7, RANK_MYTHIC, 5, 6, 3,
			CardSet.SET_ANURIM).addAbility(CardAbility.shadydeal).setParticleType("IRON").setListable(true);
	public static final Card nagapriestess = new UnitTemplate(350, "Nagani Priestess", "nagaslayer.png", 7, RANK_MYTHIC,
			9, 9, 3, CardSet.SET_ANURIM).addAbility(CardAbility.naganigift).setParticleType("IRON").setListable(true);
	public static final Card kengassassin = new UnitTemplate(352, "Dune Hunter", "kengaiassassin.png", 4, RANK_MYTHIC,
			2, 3, 3, CardSet.SET_ANURIM).addAbility(CardAbility.kengpower).setParticleType("IRON").setListable(true);
	public static final Card cobra = new UnitTemplate(353, "Elder Cobra", "cobra.png", 4, RANK_MYTHIC, 2, 2, 2,
			CardSet.SET_EXPEDITION).addAbility(CardAbility.beastpoison).setParticleType("BURST").setListable(true);
	public static final Card swamptroll = new UnitTemplate(354, "Swamp Giant", "swamptroll.png", 7, RANK_MYTHIC, 6, 8,
			3).addAbility(CardAbility.slam).setParticleType("BURST").setFamily(Family.beast).setListable(true);
	public static final Card whitesnake = new UnitTemplate(355, "Ivory Constrictor", "whitesnake.png", 3, RANK_UNCOMMON,
			3, 2, 0, CardSet.SET_JUNGLE).addAbility(CardAbility.constrict).setParticleType("BURST")
					.setFamily(Family.beast).setListable(true);
	public static final Card camelthief = new UnitTemplate(356, "Camel Thief", "camelthief.png", 5, RANK_MYTHIC, 3, 2,
			3, CardSet.SET_EXPEDITION).addAbility(CardAbility.kidnapbeast).setParticleType("BURST")
					.setFamily(Family.beast).setListable(true);
	public static final Card druid = new UnitTemplate(357, "Forest Druid", "druid.png", 8, RANK_UNCOMMON, 5, 5, 3,
			CardSet.SET_JUNGLE).addAbility(CardAbility.nearheal).setParticleType("BURST").setFamily(Family.beast)
					.setListable(true);
	public static final Card earthelemental = new UnitTemplate(358, "Stone Golem", "earthelemental.png", 3,
			RANK_UNCOMMON, 5, 1, 2).addAbility(CardAbility.protect).addAbility(CardAbility.crystalize).setParticleType("BURST").setFamily(Family.elemental)
					.setListable(true);
	public static final Card elfdruid = new UnitTemplate(359, "Emerald Mystic", "elfdruid.png", 4, RANK_UNCOMMON, 3, 3,
			3, CardSet.SET_TRIBELANDS).addAbility(CardAbility.nearbuff).setParticleType("BURST").setListable(true);
	public static final Card firelord = new UnitTemplate(360, "Ember Enforcer", "firelord.png", 6, RANK_UNCOMMON, 7, 4,
			3).addAbility(CardAbility.fleshOfFire).setParticleType("FIRE").setFamily(Family.elemental)
					.setListable(true);
	public static final Card tortoise = new UnitTemplate(361, "Giant Tortoise", "giantturtle.png", 5, RANK_UNCOMMON, 6,
			4, 3).addAbility(CardAbility.nearprotect).addAbility(CardAbility.amphibious).setParticleType("BURST")
					.setFamily(Family.beast).setListable(true);
	public static final Card mammoth = new UnitTemplate(362, "Ramsonian Mammoth", "mammoth.png", 8, RANK_UNCOMMON, 13,
			8, 4).addAbility(CardAbility.rampage).addAbility(CardAbility.wintersembrace).addAbility(CardAbility.protect)
					.setParticleType("SHATTER").setFamily(Family.beast).setListable(true);
	public static final Card taurarcher = new UnitTemplate(363, "Cleft Tracker", "bullarcher.png", 4, RANK_MYTHIC, 3, 3,
			3).addAbility(CardAbility.eagleshot).setParticleType("IRON")
					.setShouts("Found, by the wind.", "The spirits claim me...", "Away!").setListable(true);
	public static final Card taurtank = new UnitTemplate(364, "Ironguard", "bullguard.png", 8, RANK_MYTHIC, 7, 7, 3)
			.addAbility(CardAbility.headbutt).addAbility(CardAbility.protect).setParticleType("IRON")
			.setShouts("Form ranks!", "The spirits claim me...", "Face your death!").setListable(true);
	public static final Card stormwizard = new UnitTemplate(365, "Tempest Wizard", "cracklingtempest.png", 5,
			RANK_MYTHIC, 3, 4, 2).addAbility(CardAbility.tempest).setParticleType("IRON")
					.setShouts("Form ranks!", "The spirits claim me...", "Face your death!").setListable(true);
	public static final Card ancient = new UnitTemplate(366, "Svanjar Ancient", "snowgiant.png", 5, RANK_UNCOMMON, 4, 5,
			3).addAbility(CardAbility.iceboulder).setParticleType("FREEZE").setFamily(Family.beast).setListable(true);
	public static final Card crusher = new UnitTemplate(368, "Morkhan Breaker", "ogrestomper.png", 4, RANK_UNCOMMON, 4,
			4, 3).addAbility(CardAbility.wallcrush).setParticleType("EARTH").setListable(true);
	public static final Card bloodpuppet = new UnitTemplate(369, "Blood Puppet", "bloodgolem.png", 4, RANK_COMMON, 5, 5,
			4, CardSet.SET_TRIBELANDS).setParticleType("BLOOD").setListable(false);
	public static final Card grizzly = new UnitTemplate(370, "Runed Grizzly", "runegrizzly.png", 3, RANK_COMMON, 4, 2,
			4).addAbility(CardAbility.runefur).setParticleType("RUNE").setFamily(Family.beast).setListable(true);
	public static final Card snoozer = new UnitTemplate(371, "The Slumberer", "rowy.png", 6, RANK_REGAL, 5, 5, 2)
			.addAbility(CardAbility.snoozing).setParticleType("SLEEP").setFamily(Family.beast).setListable(true);
	public static final Card sandgolem = new UnitTemplate(372, "Sandstone Vessel", "sandstonevessel.png", 1, RANK_COMMON,
			4, 0, 0).setParticleType("SLOW").setFamily(Family.elemental).setListable(true);
	public static final Card keeper = new UnitTemplate(373, "Keeper Taruum", "forestgod.png", 9, RANK_REGAL, 16, 1, 3)
			.addAbility(CardAbility.newworld).setParticleType("LEAVES").setFamily(Family.elemental).setListable(true);
	public static final Card watcher = new UnitTemplate(374, "Blind Watcher", "watcher.png", 4, RANK_MYTHIC, 3, 3, 2,
			CardSet.SET_EXPEDITION).addAbility(CardAbility.gaze).addAbility(CardAbility.hovering)
					.setParticleType("VOID").setShouts("Form ranks!", "The spirits claim me...", "Face your death!")
					.setListable(true);
	public static final Card watcherleg = new UnitTemplate(375, "Urch'Is the Beholder", "watcher1.png", 6, RANK_REGAL,
			6, 5, 3).addAbility(CardAbility.gaze).addAbility(CardAbility.gaze1).addAbility(CardAbility.soar)
					.addAbility(CardAbility.hovering).setParticleType("VOID")
					.setShouts("Form ranks!", "The spirits claim me...", "Face your death!").setListable(true);
	public static final Card babyshell = new UnitTemplate(376, "Shelly", "babyturtle.png", 1, RANK_REGAL, 1, 1, 4,
			CardSet.SET_SEA).addAbility(CardAbility.amphibious).addAbility(CardAbility.warding)
					.setParticleType("SHATTER").setFamily(Family.beast).setListable(true);
	public static final Card qCannoneer = new UnitTemplate(377, "Storian Cannoneer", "qcannon.png", 2, RANK_UNCOMMON, 2,
			2, 2).addAbility(CardAbility.blast).addAbility(CardAbility.reload).setParticleType("EXPLOSION")
					.setListable(true);
	public static final Card qSniper = new UnitTemplate(378, "Storian Sniper", "qsniper.png", 5, RANK_MYTHIC, 4, 3, 1)
			.addAbility(CardAbility.snipe1).setParticleType("BWNOVA").setListable(true);
	public static final Card mechknight = new UnitTemplate(379, "Clockwork Knight", "clockworkknight.png", 5,
			RANK_MYTHIC, 6, 4, 2).addAbility(CardAbility.nearprotect).addAbility(CardAbility.whirl1)
					.addAbility(CardAbility.reload).setParticleType("BWNOVA").setListable(true);
	public static final Card holdingtometoken = new UnitTemplate(380, "Book", "holdingtome.png", 1, RANK_MYTHIC, 2, 1,
			0).addAbility(CardAbility.learn).setParticleType("RUNE").setListable(true);
	public static final Card mechboar = new UnitTemplate(381, "Clockwork Boar", "mechboar.png", 4, RANK_MYTHIC, 5, 0, 3)
			.setFamily(Family.mech).addAbility(CardAbility.charger).addAbility(CardAbility.upgrade3)
			.setParticleType("SLASH").setListable(true);
	public static final Card snakeWarrior = new UnitTemplate(382, "Suremis Gladiator", "meduusawarrior.png", 6,
			RANK_MYTHIC, 7, 5, 3).addAbility(CardAbility.protect).addAbility(CardAbility.petrify)
					.setParticleType("SLASH").setListable(true);
	public static final Card timegod = new UnitTemplate(383, "Amaha'Rul", "timegod.png", 3, RANK_REGAL, 2, 4, 3)
			.addAbility(CardAbility.timegod).setParticleType("SLOW").setListable(true);
	public static final Card mummyemperor = new UnitTemplate(384, "Risen Pharoh", "mummyemporer.png", 6, RANK_MYTHIC, 4,
			6, 3).setFamily(Family.undead).addAbility(CardAbility.emperor).setParticleType("SLOW").setListable(true);
	public static final Card mummymage = new UnitTemplate(385, "Tombweaver", "mummysorcerer.png", 5, RANK_UNCOMMON, 3,
			4, 2).setFamily(Family.undead).addAbility(CardAbility.mummymagic).setParticleType("VOIDCAST")
					.setListable(true);
	public static final Card mummycommander = new UnitTemplate(386, "Tomb Warrior", "mummyknight.png", 3, RANK_COMMON,
			2, 2, 3).setFamily(Family.undead).addAbility(CardAbility.mummyspirit)
					.setParticleType("VOIDCAST").setListable(true);
	public static final Card zerdurim = new UnitTemplate(387, "Zera'Durim", "zerdurim.png", 7, RANK_REGAL, 5, 4, 3)
			.addAbility(CardAbility.zerdurim).addAbility(CardAbility.whispers).addAbility(CardAbility.yearpass).setParticleType("VOIDCAST").setListable(true);
	public static final Card timekeeper = new UnitTemplate(388, "Yarami Collector", "relickeeper.png", 6, RANK_MYTHIC,
			6, 6, 4).addAbility(CardAbility.relicsearch).setParticleType("SLOW").setListable(true);
	public static final Card root = new UnitTemplate(389, "Deadwood Root", "deadwood.png", 0, RANK_COMMON,
			4, 0, 0).setParticleType("VOIDCAST").setListable(false);
	public static final Card centaurChamp = new UnitTemplate(390, "Banner Bearer", "centaurchampion.png", 5, RANK_MYTHIC, 4, 3, 3).addAbility(CardAbility.banner).setParticleType("SLASH").setListable(true);
	public static final Card lizardleg1 = new UnitTemplate(391, "Slavemaster Erezam", "lizardcaptain.png", 7, RANK_REGAL, 6, 8, 3).addAbility(CardAbility.enslave).setParticleType("BURST").setListable(true);
	public static final Card kergis = new UnitTemplate(392, "Captain Kergis", "captainkergis.png", 7, RANK_REGAL, 6, 8, 3).setParticleType("WATERBIRD").addAbility(CardAbility.amphibious).addAbility(CardAbility.sealegs).setFamily(Family.eldritch).setListable(true);
	public static final Card centaurArcher = new UnitTemplate(393, "Valley Strider", "centaurarcher.png", 1, RANK_COMMON, 2, 1, 4).setParticleType("IRON").addAbility(CardAbility.plainshot).setListable(true);
	public static final Card lizardleg2 = new UnitTemplate(394, "Fencleaver Kromus", "lizardcleaver.png", 6, RANK_REGAL, 5, 5, 3).setParticleType("LEAVES").addAbility(CardAbility.amphibious).addAbility(CardAbility.swiping).addAbility(CardAbility.murkblade).setListable(true);
	public static final Card centaurRaider = new UnitTemplate(396, "Raelar Charger", "centaurraider.png", 4, RANK_COMMON, 3, 1, 4).setParticleType("LEAVES").addAbility(CardAbility.movepower).setListable(true);
	public static final Card khan2 = new UnitTemplate(397, "Khan Morghis", "centaurkhan2.png", 5, RANK_REGAL, 6, 3, 4).setParticleType("LEAVES").addAbility(CardAbility.hoofstorm).setListable(true);
	public static final Card centaurCharger = new UnitTemplate(398, "Eiraph Marauder", "centaurcharger.png", 5, RANK_UNCOMMON, 6, 3, 4).setParticleType("LEAVES").addAbility(CardAbility.hoofstorm).setListable(true);
	public static final Card jarong = new UnitTemplate(399, "Demigod Jarong", "jarong.png", 5, RANK_REGAL, 6, 3, 4, CardSet.SET_ANURIM).setParticleType("LEAVES").addAbility(CardAbility.scavenge).setListable(true);
	public static final Card kondar = new UnitTemplate(400, "Demigod Kondar", "kondar.png", 5, RANK_REGAL, 3, 4, 3, CardSet.SET_ANURIM).setParticleType("BLOOD").addAbility(CardAbility.soar).setListable(true);
	public static final Card jarongSoldier = new UnitTemplate(401, "Jarish Marauder", "jarongsoldier.png", 3, RANK_UNCOMMON, 3, 4, 3, CardSet.SET_ANURIM).setFamily(Family.beast).setParticleType("SLASH").addAbility(CardAbility.scavenge).setListable(true);
	public static final Card jarongArcher = new UnitTemplate(402, "Jarish Sharpeye", "jarongarcher.png", 5, RANK_UNCOMMON, 3, 4, 3, CardSet.SET_ANURIM).setFamily(Family.beast).setParticleType("IRON").addAbility(CardAbility.soar).setListable(true);
	public static final Card vampdemon = new UnitTemplate(403, "Dreadfang", "vampiredemon.png", 5, RANK_REGAL, 7, 3, 3).setParticleType("SLASH").addAbility(CardAbility.soar).addAbility(CardAbility.dreadharvest).addAbility(CardAbility.frenzy).setFamily(Family.demon).setListable(true);
	public static final Card evilspirit = new UnitTemplate(404, "Vengeful Echo", "transform.png", 4, RANK_MYTHIC, 1, 1, 3).setParticleType("SLASH").addAbility(CardAbility.growingpower).setFamily(Family.undead).setListable(true);
	public static final Card walkingdead = new UnitTemplate(405, "The Rotting Horde", "horde.png", 5, RANK_REGAL, 4, 2, 1).setParticleType("VOID").addAbility(CardAbility.winteriscoming).setFamily(Family.undead).setListable(true);
	public static final Card blackwalker = new UnitTemplate(406, "Black Walker", "horde.png", 0, RANK_REGAL, 3, 3, 2).setParticleType("SKULLGAS").addAbility(CardAbility.nightmarch).setFamily(Family.undead).setListable(false);
	public static final Card khan3 = new UnitTemplate(407, "Khan Jorum", "centaurkhan3.png", 2, RANK_REGAL, 4, 4, 4).setParticleType("SLASH").addAbility(CardAbility.longestride).addAbility(CardAbility.gladfury).setListable(true);
	public static final Card khan1 = new UnitTemplate(408, "Khan Shurim", "centaurarcanist.png", 5, RANK_REGAL, 4, 4, 4).setParticleType("RUNE").addAbility(CardAbility.runestaff).setListable(true);
	public static final Card titan = new UnitTemplate(409, "Earthen Titan", "foresttitan.png", 8, RANK_UNCOMMON, 8, 8, 3).setParticleType("EARTH").addAbility(CardAbility.nearprotect).addAbility(CardAbility.nearbuff).setListable(true);
	public static final Card elfbattlemage = new UnitTemplate(410, "Elven Blademage", "elfbattlemage.png", 8, RANK_UNCOMMON, 8, 8, 4).setParticleType("EARTH").addAbility(CardAbility.firebomb).setListable(true);
	public static final Card centaurlancer = new UnitTemplate(411, "Raelar Darklance", "centaurlancer.png", 6, RANK_UNCOMMON, 6, 6, 4).setParticleType("EARTH").addAbility(CardAbility.dreadharvest).setListable(true);
	public static final Card golemmage = new UnitTemplate(412, "Golem Wizard", "summoning.png", 10, RANK_UNCOMMON, 4, 4, 2).setParticleType("NOVA").addAbility(CardAbility.golemwizard).setListable(false);
	public static final Card flametongue = new UnitTemplate(413, "Salamancer", "salamander.png", 7, RANK_MYTHIC, 7, 7, 3).setParticleType("FIRE").addAbility(CardAbility.fireblade).addAbility(CardAbility.amphibious).setListable(true);
	public static final Card centaurdeadwood = new UnitTemplate(414, "Deadwood Charger", "centaurtwisted.png", 7, RANK_COMMON, 7, 8, 4).setParticleType("EARTH").addAbility(CardAbility.darkcharge).setListable(true);
	public static final Card spark = new UnitTemplate(415, "Wicked Spark", "deviantspark.png", 2, RANK_COMMON, 2, 3, 3).setParticleType("SWIRL").addAbility(CardAbility.spark).setFamily(Family.elemental).setListable(true);
	public static final Card sprite = new UnitTemplate(416, "Mushroom Sprite", "shroomsprite.png", 2, RANK_MYTHIC, 3, 2, 3).setParticleType("LEAVES").addAbility(CardAbility.shrinkage).setListable(true);
	public static final Card yakariWitch = new UnitTemplate(417, "Yakari Witch", "yakarimage.png", 7, RANK_MYTHIC, 7, 7, 3).setParticleType("BLOOD").addAbility(CardAbility.spark).setListable(true);
	public static final Card yakariWarrior = new UnitTemplate(418, "Yakari Warrior", "yakaribloodwarrior.png", 6, RANK_COMMON, 6, 6, 4).setParticleType("SLASH").addAbility(CardAbility.massochism).setListable(true);
	public static final Card yakariHawk = new UnitTemplate(419, "Hawktalon", "yakariArcher.png", 3, RANK_REGAL, 3, 3, 3).setParticleType("SWIRL").addAbility(CardAbility.hawktalons).addAbility(CardAbility.wardrums).setListable(true);
	public static final Card yakariArcher = new UnitTemplate(420, "Yakari Archer", "yakarihawk.png", 5, RANK_COMMON, 4, 4, 3).setParticleType("IRON").addAbility(CardAbility.snipe).addAbility(CardAbility.tribalarrows).setListable(true);
	public static final Card bloodmage = new UnitTemplate(421, "Blood Mage", "bloodmage.png", 2, RANK_UNCOMMON, 3, 3, 3).setParticleType("BLOOD").addAbility(CardAbility.spark).setListable(true);
	public static final Card rakajHuman = new UnitTemplate(422, "Wastewalker Rakaj", "rakajhuman.png", 6, RANK_REGAL, 4, 4, 3).setParticleType("fx10_blackExplosion").addAbility(CardAbility.rakaj).setListable(true);
	public static final Card arbiter = new UnitTemplate(423, "Divine Arbiter", "arbiter.png", 5, RANK_UNCOMMON, 5, 5, 3).setParticleType("SHINE").addAbility(CardAbility.cleanse).addAbility(CardAbility.heal).setListable(true);
	public static final Card magmagiant = new UnitTemplate(424, "Magma Giant", "magmagiant.png", 7, RANK_MYTHIC, 5, 5, 3).setParticleType("FIRE").addAbility(CardAbility.elementalcreate).addAbility(CardAbility.lavalash).setFamily(Family.elemental).setListable(true);
	public static final Card lavaspawn = new UnitTemplate(425, "Flamespawn", "magmagolem.png", 2, RANK_COMMON, 2, 2, 3).setParticleType("FIRE").addAbility(CardAbility.magmasurge).setFamily(Family.elemental).setListable(true);
	public static final Card bloodfire = new UnitTemplate(426, "Bloodfire Golem", "firegiant.png", 4, RANK_UNCOMMON, 3, 5, 3).setParticleType("FIRE").addAbility(CardAbility.bloodfire).setFamily(Family.elemental).setListable(true);
	public static final Card voidelemental = new UnitTemplate(427, "Runic Anomaly", "voidelemental.png", 6, RANK_MYTHIC, 5, 5, 2).setParticleType("SWIRL").addAbility(CardAbility.elementalpower).addAbility(CardAbility.shock).setFamily(Family.elemental).setListable(true);
	public static final Card cloudlord = new UnitTemplate(428, "Cloudlord Farah", "cloudlord.png", 5, RANK_REGAL, 4, 4, 3).setParticleType("SWIRL").addAbility(CardAbility.cloudlord).addAbility(CardAbility.energizeElemental).setFamily(Family.elemental).setListable(true);
	public static final Card entgod = new UnitTemplate(429, "Earthwatcher Yod", "entgod.png", 9, RANK_REGAL, 8, 6, 3).setParticleType("LEAVES").addAbility(CardAbility.armyofelements).addAbility(CardAbility.circle).setFamily(Family.elemental).setListable(true);
	public static final Card entelder = new UnitTemplate(430, "Forest Warden", "entelder.png", 3, RANK_MYTHIC, 3, 3, 2).setParticleType("LEAVES").addAbility(CardAbility.earthengrace).setFamily(Family.elemental).setListable(true);
	public static final Card sharam = new UnitTemplate(431, "Sharam", "moltenlion.png", 4, RANK_REGAL, 3, 3, 2).setParticleType("FIRE").addAbility(CardAbility.firebomb).addAbility(CardAbility.lavalash).addAbility(CardAbility.energetic).setFamily(Family.elemental).setListable(true);
	public static final Card krom = new UnitTemplate(432, "Graveguard Krom", "krom.png", 5, RANK_REGAL, 5, 5, 3).setParticleType("SKULLGAS").addAbility(CardAbility.protect).addAbility(CardAbility.shieldbash).setFamily(Family.undead).setListable(true);
	public static final Card yakarichief = new UnitTemplate(433, "Yakari Wanderer", "yakarichief.png", 2, RANK_COMMON, 2, 3, 3).setParticleType("SLASH").addAbility(CardAbility.nearstun).setListable(true);
	public static final Card stonebreaker = new UnitTemplate(434, "Stonebreaker", "breaker.png", 7, RANK_COMMON, 6, 5, 2).setParticleType("EARTH").addAbility(CardAbility.deathstun).addAbility(CardAbility.crystalize).setFamily(Family.elemental).setListable(true);
	public static final Card militia0 = new UnitTemplate(435, "Infantry", "militianorth.png", 1, RANK_COMMON, 2, 1, 3).setParticleType("SLASH").setListable(false);
	public static final Card militia1 = new UnitTemplate(436, "Orkling", "militiaork.png", 1, RANK_COMMON, 1, 2, 3).setParticleType("SLASH").setListable(false);
	public static final Card militia2 = new UnitTemplate(437, "Prototype", "militiasteam.png", 1, RANK_COMMON, 1, 1, 2).addAbility(CardAbility.upgrade1).addAbility(CardAbility.upgrade2).addAbility(CardAbility.upgrade3).setFamily(Family.mech).setParticleType("SLASH").setListable(false);
	public static final Card militia3 = new UnitTemplate(438, "Hellspawn", "militiademon.png", 1, RANK_COMMON, 1, 1, 4).setFamily(Family.demon).addAbility(CardAbility.corruptground).setParticleType("SLASH").setListable(false);
	public static final Card militia4 = new UnitTemplate(439, "Dust Roamer", "militiasand.png", 1, RANK_COMMON, 2, 1, 3).setFamily(Family.elemental).setParticleType("SLASH").setListable(false);
	public static final Card tower = new UnitTemplate(440, "War Tower", "tower.png", 3, RANK_COMMON, 7, 0, 0).addAbility(CardAbility.spawnWarrior).setParticleType("SLASH").setListable(false);

	// public static final Card crowtalon = new UnitTemplate(275, "Crowtalon",
	// "herald.png", 5, RANK_REGAL, 5, 5,
	// 0).addAbility(CardAbility.buzzsaw).setParticleType("SLASH").setListable(true);
	// public static final Card mercCamp = new UnitTemplate(254, "Bandit Camp",
	// "eldritchqueen.png", 2, RANK_REGAL, 6, 0,
	// 2).addAbility(CardAbility.forbiddenMagic).addAbility(CardAbility.eldritchHeal).setParticleType("SWIRL").setFamily(Family.eldritch);

	public static final Card farm = new StructureTemplate(254, "Farm", "farm.png", 2, RANK_UNCOMMON, 05, 0, 0)
			.setParticleType("SLASH").addAbility(CardAbility.minion).addAbility(CardAbility.farmFeed);
	public static final Card barracks = new StructureTemplate(255, "Barracks", "barracks.png", 3, RANK_UNCOMMON, 8, 0,
			0).setParticleType("SLASH").addAbility(CardAbility.minion).addAbility(CardAbility.recruitMilitia)
					.addAbility(CardAbility.recruitKnight);
	public static final Card krattHut = new StructureTemplate(256, "Kratt Hut", "kratthut.png", 2, RANK_UNCOMMON, 5, 0,
			0, CardSet.SET_JUNGLE).setParticleType("SLASH").addAbility(CardAbility.minion)
					.addAbility(CardAbility.krattHut);
	public static final Card graveyard = new StructureTemplate(257, "Graveyard", "crypt.png", 2, RANK_UNCOMMON, 7, 0, 0)
			.setParticleType("SLASH").addAbility(CardAbility.minion).addAbility(CardAbility.structurePassiveGraveyard);
	public static final Card cannonTower = new StructureTemplate(258, "Cannon Tower", "cannontower.png", 2,
			RANK_UNCOMMON, 6, 0, 0).setParticleType("SLASH").addAbility(CardAbility.minion)
					.addAbility(CardAbility.cannonShot).addAbility(CardAbility.upgradeCannon).setListable(false);
	public static final Card ragetusk = new StructureTemplate(259, "Omazian Village", "ragetusk.png", 4, RANK_MYTHIC,
			10, 1, 0).setParticleType("SLASH").addAbility(CardAbility.minion).addAbility(CardAbility.omazvillage);
	public static final Card abyssyal = new StructureTemplate(260, "Temple of the Abyss", "abyssaltemple.png", 3,
			RANK_MYTHIC, 10, 1, 0).setParticleType("SLASH").addAbility(CardAbility.minion)
					.addAbility(CardAbility.abyssaltemple);
	public static final Card chapel = new StructureTemplate(261, "Lyrish Chapel", "chapel.png", 2, RANK_UNCOMMON, 6, 0,
			0).setParticleType("SLASH").addAbility(CardAbility.minion).addAbility(CardAbility.chapel);
	public static final Card deepmurk = new StructureTemplate(262, "Deepmurk Temple", "deepmurk.png", 2, RANK_UNCOMMON,
			6, 0, 0, CardSet.SET_SEA).setParticleType("SLASH").addAbility(CardAbility.minion)
					.addAbility(CardAbility.deepmurk).addAbility(CardAbility.forbiddenMagic);
	public static final Card mountainkeep = new StructureTemplate(263, "Dwarven Smithy", "mountaincastle.png", 3,
			RANK_UNCOMMON, 6, 0, 0).setParticleType("SLASH").addAbility(CardAbility.minion)
					.addAbility(CardAbility.dwarvenSmithy);
	public static final Card groveshack = new StructureTemplate(264, "Enchanted Grove", "groveshack.png", 3,
			RANK_UNCOMMON, 6, 0, 0, CardSet.SET_JUNGLE).setParticleType("SLASH").addAbility(CardAbility.minion)
					.addAbility(CardAbility.grove);
	public static final Card murkwater = new StructureTemplate(395, "Grimwater Tavern", "grimwater.png", 3, RANK_UNCOMMON, 6, 0, 0, CardSet.SET_JUNGLE).setParticleType("SLASH").addAbility(CardAbility.minion).addAbility(CardAbility.grove);
	// public static final Card core = new UnitTemplate(238, "", "tent.png", 8,
	// RANK_MYTHIC, 9, 5,
	// 4).setParticleType("VOID").addAbility(CardAbility.shadowburn).addAbility(CardAbility.oblivion);

	public static final Card commYorg = new UnitTemplate(2, "Svanjan Priestess", "lyria.png", 3, RANK_COMMANDER, 20,
			1, 3).addAbility(CardAbility.humility).addAbility(CardAbility.jadibrad).addAbility(CardAbility.dawnlight)
					.setShouts("Svanjar belongs to us!", "The prophet lives yet...", "For The Northgarde!");
	public static final Card commHera = new UnitTemplate(3, "Dwarven Clanlord", "heragore.png", 3, RANK_COMMANDER, 22,
			2, 3).addAbility(CardAbility.humility).setParticleType("IRON").addAbility(CardAbility.cleave)
					.addAbility(CardAbility.bash)
					.setShouts("I won't go down without a fight.", "It can't be over...", "Death to ye!").setBasic(true);
	public static final Card commDiscilla = new UnitTemplate(4, "Ravenmage Discilla", "discilla.png", 3, RANK_COMMANDER,
			18, 1, 3).addAbility(CardAbility.humility).addAbility(CardAbility.shadowburn).addAbility(CardAbility.blink);
	public static final Card commOzaaroth = new UnitTemplate(5, "Khovani Dark Mage", "ozaaroth.png", 3,
			RANK_COMMANDER, 20, 1, 4).addAbility(CardAbility.humility).addAbility(CardAbility.infinityburst)
					.addAbility(CardAbility.voidgift).setShouts("Surrender, mortal!&The shadows speak your name.",
							"This is not the end.", "The Khovani will rise again!").setBasic(true);
	public static final Card commArachna = new UnitTemplate(6, "Spider Queen", "arachna.png", 3, RANK_COMMANDER, 22, 1,
			4).addAbility(CardAbility.humility).addAbility(CardAbility.web).addAbility(CardAbility.consume).setShouts(
					"From the depths, I have come!", "The Underqueen never truly dies.", "In the master's name!");
	public static final Card commShanzin = new UnitTemplate(7, "Dune Emperor", "shanzin.png", 3, RANK_COMMANDER, 22,
			1, 4).addAbility(CardAbility.humility).addAbility(CardAbility.dive).addAbility(CardAbility.meditate)
					.setShouts("I tire of this conflict.", "Ah... Peace at last.", "This is necessary.");
	public static final Card commBashar = new UnitTemplate(8, "Mountain Chief", "bashar.png", 3, RANK_COMMANDER, 20, 1, 3)
			.addAbility(CardAbility.humility).addAbility(CardAbility.wildAxe).addAbility(CardAbility.bloodCharge)
			.setShouts("I will be your end!", "This... Is not possible...", "GAH!").setBasic(true);
	public static final Card commQuanir = new UnitTemplate(9, "Tidelord Quanir", "quanir.png", 3, RANK_COMMANDER, 16, 2,
			5).addAbility(CardAbility.humility);
	public static final Card commRomar = new UnitTemplate(10, "Elder Romar", "romar.png", 3, RANK_COMMANDER, 24, 1, 5)
			.addAbility(CardAbility.humility);
	public static final Card commStread = new UnitTemplate(11, "", "stread.png", 3, RANK_COMMANDER, 26, 1,
			4).addAbility(CardAbility.humility);
	public static final Card commRangrim = new UnitTemplate(12, "Taurus Commander", "rangrim.png", 3, RANK_COMMANDER, 28,
			1, 3).addAbility(CardAbility.humility).addAbility(CardAbility.hoofsmash).addAbility(CardAbility.trample)
					.addAbility(CardAbility.warbanner).addAbility(CardAbility.fortify)
					.setShouts("Rangrim reporting for duty!", "Honour me with blood!", "For the guild!");
	public static final Card commChristy = new UnitTemplate(13, "Storian Engineer", "christy.png", 3, RANK_COMMANDER,
			22, 1, 4).addAbility(CardAbility.humility).addAbility(CardAbility.supercharge)
					.addAbility(CardAbility.overload).addAbility(CardAbility.salvage)
					.setShouts("The Sky City will not fall today.", "Perhaps I miscalculated...", "That'll fix you!");
	public static final Card commTikesh = new UnitTemplate(14, "Jaram of Keng", "jaram.png", 3, RANK_COMMANDER, 22,
			1, 4).addAbility(CardAbility.humility).addAbility(CardAbility.supercharge).addAbility(CardAbility.overload)
					.addAbility(CardAbility.salvage)
					.setShouts("hep.", "Perhaps I miscalculated...", "That'll fix you!").setBasic(true);
	public static final Card commSiren = new UnitTemplate(343, "Goddess Adrelia", "templesiren.png", 3, RANK_COMMANDER,
			25, 1, 4).addAbility(CardAbility.humility).addAbility(CardAbility.splash).addAbility(CardAbility.evasive)
					.addAbility(CardAbility.aquastrike).addAbility(CardAbility.amphibious).setShouts(
							"Where have they taken the Pearl!?", "To the Mystic Sea I return...", "Leave this place!");
	public static final Card commJaram = new UnitTemplate(351, "Kherem Witch", "kherem.png", 3, RANK_COMMANDER, 20, 0,
			3).addAbility(CardAbility.humility).addAbility(CardAbility.soulfeast).addAbility(CardAbility.souldrain)
					.addAbility(CardAbility.melt).addAbility(CardAbility.darkvision)
					.setShouts("Only death awaits you, mortal!", "A laughable setback.", "Enk'esh Avarim!");
	public static final Card commEnt = new UnitTemplate(367, "Keeper Taruum", "forestgod.png", 3, RANK_COMMANDER, 16, 1,
			3).addAbility(CardAbility.humility).addAbility(CardAbility.plantTree).addAbility(CardAbility.plant)
					.addAbility(CardAbility.grove).addAbility(CardAbility.heal1)
					.setShouts("I will protect the forest!", "Back... To rest.", "Has it... Come to this?");

	public static final double CRAFT_MULTI = 3;

	public Card setTargetType(int tt) {
		this.targetType = tt;
		return this;
	}

	/**
	 * 
	 * @param type
	 * @param x
	 * @param y
	 * @param guid
	 * @param o
	 *            (Giving a negative value will cause the play to cost no mana
	 *            client-side. You must also subtract 1 from the negative value
	 *            for this to work.)
	 * @return
	 */
	public boolean onPlayed(int type, int x, int y, int guid, int o, int roomID) {
		// 0 = SERVER, 1 = CLIENT
		int owner = o;
		if (owner < 0) {
			owner = (Math.abs(o) - 1);
		}
		Point point = new Point(x, y);
		UnitTemplate unit = null;
		if (this instanceof UnitTemplate)
			unit = (UnitTemplate) this;
		if (type == 1) {
			if (o >= 0 && o == Util.clientID){
				Util.gold -= getCost();
				Util.goldRed -= getRedCost();
				Util.goldGreen -= getGreenCost();
				Util.goldBlue -= getBlueCost();
			}
			if (owner != Util.clientID)
				Util.doPlayAnim(this);
		}
		if (unit != null) { // Unit card
			int paw = Grid.tileSize * 2;
			AnimatedParticle part = new AnimatedParticle(
					Util.boardOffsetX + Grid.tileSize * x + Grid.tileSize / 2 - paw / 2,
					Util.boardOffsetY + Grid.tileSize * y + Grid.tileSize / 2 - paw / 2, paw, 1200).addFrameSet("NOVA");
			Display.currentScreen.particles.add(part);
			Unit u = new Unit(unit, x, y, roomID);

			u.GUID = guid;
			u.ownerID = owner;
			if (type == 0) {
				Bank.server.getUnits(roomID).add(u);
			}
			if (type == 1) {
				Util.queue.add(u);
				Animation a = new Animation(Util.boardOffsetX + Grid.tileSize * x + Grid.tileSize / 2,
						Util.boardOffsetY + Grid.tileSize * y + Grid.tileSize / 2, 1200, Animation.TAG_SPEECH);
				a.setText(((UnitTemplate) this).getTxtPlay());
				Display.currentScreen.particles.add(a);
			}
			for (int i = 0; i < u.getAbilities().size(); ++i) {
				CardAbility ca = u.getAbilities().get(i);
				if(ca!=null)
					ca.onPlayed(u, roomID);
			}
			if(owner >= 0 && owner < 900){
				ArrayList<Unit> ulist = null;
				ulist = (Bank.isServer() ? Bank.server.getUnits(roomID) : Display.currentScreen.objects);
				for (int i = 0; i < ulist.size(); ++i){
					Unit other = ulist.get(i);
					if(other != null){
						if(other != u){
							for (int j = 0; j < u.getAbilities().size(); ++j) {
								CardAbility ca = u.getAbilities().get(j);
								if(ca!=null)
									ca.onOtherUnitPlayed(u, other, roomID);
							}
						}
					}
				}
			}
			if(Util.boardState!=null)
			Util.boardState.onUnitPlayed(u, roomID);
			if (!u.canUnitMoveOverTile(x, y)) {
				u.destroy();
			}
		} else { // Spell card
			Util.getCastSpells(roomID).add(this);
			Unit target = null;
			target = Display.currentScreen.getUnitOnTile(x, y, roomID);
			
			Unit commanderCaster = Display.currentScreen.getCommanderForPlayer(owner, roomID);

			if (this instanceof CardEquipment) {
				if (target != null) {
					((CardEquipment) this).equipUnit(target, true);
				} else {
					// Cannot play
				}
			} else {
				// Trigger oncast effects
				ArrayList<Unit> scunits = null;
				if (Bank.isServer())
					scunits = Bank.server.getUnits(roomID);
				else
					scunits = ((PanelBoard) Display.currentScreen).objects;
				for (int i = 0; i < scunits.size(); ++i) {
					if (scunits.get(i) != null) {
						Unit u = scunits.get(i);
						for (CardAbility c : u.getAbilities()) {
							c.onSpellCast(u, x, y, target, o, roomID);
						}
					}
				}
			}
			
			if(this == markofice){
				if(target!=null){
					target.addOrUpdateEffect(new Effect(EffectType.attack, 4, 1));
					if(target.getTotalEffectValue(EffectType.chill) > 0){
						target.hurt(3, commanderCaster);
					}
					if(!Bank.isServer()){
						Animation.createStaticAnimation(target, "fx1_blue_topEffect", 1, 500);
						Animation.createStaticAnimation(target, "FREEZE", 2, 400);
					}
				}
			}
			
			if(this == svanjarships){
					if (Bank.isServer()) {
						for (Unit t : Bank.server.getUnits(roomID)) {
							t.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
							t.addOrUpdateEffect(new Effect(EffectType.health, 2, 999));
							t.getAbilities().add(CardAbility.amphibious);
						}
					} else {
						for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
							Unit t = Display.currentScreen.objects.get(i);
							if (t != null) {
								t.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
								t.addOrUpdateEffect(new Effect(EffectType.health, 2, 999));
								t.getAbilities().add(CardAbility.amphibious);
								Animation.createStaticAnimation(t, "POOL", 2, 400);
							}
						}
					}
			}
			
			if(this == decompose){
				if(target != null){
					target.destroy();
					if(Bank.isServer()){
						GameServer room = Bank.server.getRoom(roomID);
						room.drawCard(room.clients.get(target.ownerID), target.getTemplate().getId(), 1);
					}else{
						Animation.createStaticAnimation(target, "LEAVES", 2, 500);
						Animation.createStaticAnimation(target, "BLOOM", 2, 700);
					}
				}
			}
			
			if(this == voidbreath){
				if(target != null){
					target.getEffects().clear();
					target.setAttack(8);
					target.setHealth(8);
					
					if (Bank.isServer()) {
						for (Unit t : Bank.server.getUnits(roomID)) {
							if(t!=target)
								t.hurt(5, target);
						}
					} else {
						for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
							Unit t = Display.currentScreen.objects.get(i);
							if (t != null) {
								if(t!=target)
									t.hurt(5, target);
								Animation.createStaticAnimation(t, "SHINE", 2, 400);
							}
						}
					}
				}
			}
			
			if(this == voidtome){
				PersistentValue.addUniquePersistentValue("GAME_DMGINCR", 2, roomID).setLifetime(1);
				Animation.createStaticAnimation(x, y, "fx10_blackExplosion", 3, 1000);
				Animation.createStaticAnimation(x, y, "VOID", 3, 800);
			}
			
			if(this == spiritlink){
				PersistentValue.addUniquePersistentValue("GAME_DMGINCR", -2, roomID).setLifetime(1);
				Animation.createStaticAnimation(x, y, "fx7_energyBall", 2, 700);
				Animation.createStaticAnimation(x, y, "HOLYCAST", 2, 850);
			}
			
			if(this == divineshock){
				if(target!=null){
					int dmg = target.getHealth() - 1;
					target.hurt(dmg, Display.currentScreen.getCommanderForPlayer(owner, roomID));
					PersistentValue.addPersistentValue("GAME_DMGINCR", dmg, roomID);
				}
			}
			
			if(this == prepareWar){
				target.addOrUpdateEffect(new Effect(EffectType.stunned, 1, 3));
				if(Bank.isServer()){
					GameServer room = Bank.server.getRoom(roomID);
					room.drawCard(room.clients.get(owner), -1, 1);
				}else{
					Animation.createStaticAnimation(target, "STONE", 2, 500);
				}
			}
			
			if(this == netherlash){
				if(target.isStunned()){
					target.hurt(5, Display.currentScreen.getCommanderForPlayer(owner, roomID));
				}else{
					target.addOrUpdateEffect(new Effect(EffectType.stunned, 1, 2));
				}
				if(Bank.isServer()){
				}else{
					Animation.createStaticAnimation(target, "VOID", 2, 500);
				}
			}
			
			if(this == blackblessing){
				target.hurt(4, commanderCaster);
				if(target.getHealth() > 0){
					target.addOrUpdateEffect(new Effect(EffectType.attack, 4, 999));
					target.addOrUpdateEffect(new Effect(EffectType.attack, 8, 999));
					if(!Bank.isServer())Animation.createStaticAnimation(target, "VOID", 2, 600);
				}
				if(!Bank.isServer())Animation.createStaticAnimation(target, "BLOOD", 3, 600);
			}
			
			if(this == spellshield){
				PersistentValue.addUniquePersistentValue("GAME_DMGINCR", -10, roomID);
				if(!Bank.isServer())Animation.createStaticAnimation(Display.currentScreen.mousePoint.x,  Display.currentScreen.mousePoint.y, "RUNE", 3, 800);
			}
			
			if(this == revenge){
				int dif = Display.currentScreen.getCommanderForPlayer(owner, roomID).getHealthMax() - Display.currentScreen.getCommanderForPlayer(owner, roomID).getHealth();
				PersistentValue.addUniquePersistentValue("GAME_DMGINCR", (dif / 2), roomID);
				if(!Bank.isServer()){
					Animation.createStaticAnimation(Display.currentScreen.mousePoint.x,  Display.currentScreen.mousePoint.y, "CHARGE", 2.25f, 600);
					Animation.createStaticAnimation(Display.currentScreen.mousePoint.x,  Display.currentScreen.mousePoint.y, "BLOOD", 3, 800);
					Animation.createStaticAnimation(Display.currentScreen.mousePoint.x,  Display.currentScreen.mousePoint.y, "SLASH", 3, 700);
				}
			}

			if (this == holdingtome) {
				if (target != null) {
					target.flagRemove = true;
					if (Bank.isServer()) {
						Bank.server.addUnit((UnitTemplate) holdingtometoken, target.posX, target.posY, target.ownerID, roomID);
					} else {
						Animation.createStaticAnimation(target, "RUNE", 2, 800);
					}
				}
			}
			
			if(this == spirithounds){
				if(Bank.isServer()){
					GameServer room = Bank.server.getRoom(roomID);
					room.drawCard(room.clients.get(owner), this.spirithound.getId(), 2);
				}
			}

			if (this == spelldecay) {
				if (target != null) {
					target.getAbilities().add(CardAbility.spelldecay);
					Animation.createStaticAnimation(target, "RUNE", 2, 900);
					Animation.createStaticAnimation(target, "SLOW", 2, 700);
				}
			}

			if (this == filthwater) {
				if (target != null) {
					target.addOrUpdateEffect(new Effect(EffectType.attack, 2, 999));
					target.addOrUpdateEffect(new Effect(EffectType.health, 2, 999));
					target.addEnergy1(5);

					if(Bank.isServer()){
						ArrayList<Card> avail = new ArrayList<Card>();
						for (Card c : Card.all) {
							if (c != null) {
								if (c.isSpell()) {
									avail.add(c);
								}
							}
						}
						ArrayList<Card> list = Util.createConjuringList(avail, 3);
						Bank.server.setPicks(list, roomID);
						Packet14ShowPicks pkt = new Packet14ShowPicks(owner, -1, -1, list);
						pkt.write(Bank.server.getRoom(roomID));
					}
				}
			}

			if (this == tombtoxin) {
				if (target != null) {
					target.getAbilities().add(CardAbility.assassination);
					Animation.createStaticAnimation(target, "BURST", 2, 900);
					Animation.createStaticAnimation(target, "LEAVES", 2, 700);
				}
			}

			if (this == holybind) {
				if (target != null) {
					boolean cond = (target.getFamily() == Family.eldritch || target.getFamily() == Family.undead
							|| target.getFamily() == Family.demon);
					target.hurt((int) target.getSpeed(), Display.currentScreen.getCommanderForPlayer(owner, roomID));
					if (cond) {
						target.ownerID = owner;
					}
					if (!Bank.isServer()) {
						Animation.createStaticAnimation(target, "HOLYCAST", 2, 750);
						if (cond) {
							Animation.createStaticAnimation(target, "FLASH", 2, 900);
						}
					}
				}
			}

			if (this == tracking) {
				if (target != null) {
					target.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
					target.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
					target.addOrUpdateEffect(new Effect(EffectType.speed, 2, 999));
					if (!Bank.isServer()) {
						Animation.createStaticAnimation(target, "CHARGE", 2, 500);
						Animation.createStaticAnimation(target, "SLOW", 2.75f, 700);
					}
				}
			}

			if (this == engine) {
				if (target != null) {
					Unit tar = Display.currentScreen.getBaseForPlayer((owner == 0 ? 1 : 0), roomID);

					ArrayList<Unit> targets = new ArrayList<Unit>();
					if (Bank.isServer())
						targets = Bank.server.getUnits(roomID);
					else
						targets = Display.currentScreen.objects;
					for (Unit u : targets) {
						if (u.getFamily() == Family.mech && u.ownerID == owner) {
							u.addEnergy1(3);
							if (!Bank.isServer()) {
								Util.drawParticleLine(u.getPoint(), new Point(u.getPoint().x, 0), 12, false, "BWNOVA");
								Util.drawParticleLine(u.getPoint(), new Point(u.getPoint().x, 0), 8, false,
										"EXPLOSION");
								Util.drawParticleLine(new Point(u.getPoint().x, 0), tar.getPoint(), 40, false, "FIRE",
										65, 1000 + Util.generateRange(200), false);
								Animation.createStaticAnimation(tar, "EXPLOSION", 3.5f, 1000);
							}
							tar.hurt(u.getEnergy(), u);
						}
					}
				}
			}

			if (this == fleshpile) {
				if (target != null) {
					if (target.getFamily() == Family.undead) {
						target.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
						target.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
						if (!Bank.isServer()) {
							Animation.createStaticAnimation(target, "SKULLGAS", 2, 750);
							Animation.createStaticAnimation(target, "BLOOD", 2, 850);
						}
					}
				}
			}

			if (this == finalstand) {
				if (target != null) {
					target.heal(5);
					ArrayList<Unit> targets = new ArrayList<Unit>();
					if (Bank.isServer())
						targets = Bank.server.getUnits(roomID);
					else
						targets = Display.currentScreen.objects;
					ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
					for (int i = 0; i < targets.size(); ++i) {
						Unit t = targets.get(i);
						if (t != null)
							if (t.getIsCommander() | !t.isPlayerUnit() || t.ownerID == owner)
								newtargets.remove(t);
					}
					Unit tar = Util.getNearestUnit(target, newtargets);
					target.Protect(tar, 999);

					if (!Bank.isServer()) {
						Util.drawParticleLine(Display.currentScreen.getMousePoint(), target.getPoint(), 10, false,
								"IRON");
						Animation.createStaticAnimation(target, "HOLYCAST", 1.25f, 750);
					}
				}
			}

			if (this == legend) {
				if (target != null) {
					for (int i = 1; i <= 6; ++i) {
						target.addEffect(new Effect(EffectType.attack, 1, i));
						target.addEffect(new Effect(EffectType.health, 1, i));
					}
					if (!Bank.isServer()) {
						Animation.createStaticAnimation(target, "RUNE", 2, 750);
						Animation.createStaticAnimation(target, "SLOW", 2, 900);
					}
				}
			}

			if (this == spiritbomb) {
				ArrayList<Unit> units = (Bank.isServer() ? Bank.server.getUnits(roomID) : Display.currentScreen.objects);
				for (Unit u : units) {
					if (!u.getIsCommander() && !u.isBase() && u.ownerID != owner) {
						u.hurt(100, Display.currentScreen.getCommanderForPlayer(owner, roomID));
						if (!Bank.isServer()) {
							Animation.createStaticAnimation(u, "MANABURN", 3.5f, 1200);
						}
					}
				}
				if (Bank.isServer()) {
					Bank.server.getClients(roomID).get(owner).baseGold = 1;
					Bank.server.getClients(roomID).get(owner).gold = 1;
				} else {
					if (Util.clientID == owner) {
						Util.gold = 1;
						Util.maxGold = 1;
					}
				}
			}

			if (this == trap) {
				if (target != null) {
					target.setHealth((int) target.getSpeed());
					target.setHealthMax(target.getHealth());
					if (!Bank.isServer()) {
						Animation.createStaticAnimation(target, "CHARGE", 2, 500);
						Animation.createStaticAnimation(target, "BLOOD", 2, 650);
					}
				}
			}

			if (this == boarcharge) {
				if (target != null) {
					target.getAbilities().add(CardAbility.charger);
					if (!Bank.isServer()) {
						Animation.createStaticAnimation(target, "CHARGE", 2, 750);
						Animation.createStaticAnimation(target, "LEAVES", 2, 500);
					}
				}
			}

			if (this == steamstrike) {
				ArrayList<Unit> units = (Bank.isServer() ? Bank.server.getUnits(roomID) : Display.currentScreen.objects);
				boolean cond = false;
				for (Unit u : units) {
					if (u.getFamily() == Family.mech && u.ownerID == owner) {
						cond = true;
						break;
					}
				}
				Unit u1 = Display.currentScreen.getUnitOnTile(x + 1, y, roomID);
				Unit u2 = Display.currentScreen.getUnitOnTile(x - 1, y, roomID);
				if (cond) {
					if (u1 != null)
						u1.hurt(4, Display.currentScreen.getCommanderForPlayer(owner, roomID));
					if (u2 != null)
						u2.hurt(4, Display.currentScreen.getCommanderForPlayer(owner, roomID));
				}
				target.hurt(3, Display.currentScreen.getCommanderForPlayer(owner, roomID));
				if (!Bank.isServer()) {
					Util.drawParticleLine(Display.currentScreen.getMousePoint(), target.getPoint(), 20, false, "IRON");
					Animation.createStaticAnimation(target, "INCINERATE", 2, 800);
					Animation.createStaticAnimation(target, "SLOW", 2, 500);
					if (cond) {
						if (u1 != null)
							Animation.createStaticAnimation(u1, "EXPLOSION", 2, 800);
						if (u2 != null)
							Animation.createStaticAnimation(u2, "EXPLOSION", 2, 800);
					}
				}
			}

			if (this == roar) {
				if (target != null) {
					int dmg = 0;
					ArrayList<Unit> units = (Bank.isServer() ? Bank.server.getUnits(roomID) : Display.currentScreen.objects);
					for (Unit u : units) {
						if (u.ownerID == owner && u.getFamily() == Family.beast) {
							dmg += u.getAttack();
							if (!Bank.isServer()) {
								Animation.createStaticAnimation(u, "CHARGE", 2, 750);
							}
						}
					}
					target.hurt(dmg, Display.currentScreen.getCommanderForPlayer(owner, roomID));
					if (!Bank.isServer())
						Animation.createStaticAnimation(target, "BLOOD", 3, 900);
				}
			}

			if (this == engine) {
				if (target != null) {
					ArrayList<Unit> units = (Bank.isServer() ? Bank.server.getUnits(roomID) : Display.currentScreen.objects);
					for (Unit u : units) {
						if (u.getFamily() == Family.mech) {
							u.addOrUpdateEffect(new Effect(EffectType.attack, 2, 2));
							u.addOrUpdateEffect(new Effect(EffectType.health, 2, 2));
							if (!Bank.isServer()) {
								Animation.createStaticAnimation(u, "SLOW", 2, 750);
							}
						}
					}
					if(Bank.isServer()){
						ArrayList<Card> avail = new ArrayList<Card>();
						for (Card c : Card.all) {
							if (c != null) {
								if (c.isUnit()) {
									if (c.getUnit().getFamily() == Family.mech)
										avail.add(c);
								}
							}
						}
						ArrayList<Card> list = Util.createConjuringList(avail, 3);
						Bank.server.setPicks(list, roomID);
						Packet14ShowPicks pkt = new Packet14ShowPicks(owner, -1, -1, list);
						pkt.write(Bank.server.getRoom(roomID));
					}
				}
			}

			if (this == blacktome) {
				if (Bank.isServer()) {
					PlayerMP user = Bank.server.getClients(roomID).get(owner);
					user.gold += 2;
					user.baseGold += 2;
					ArrayList<Card> avail = new ArrayList<Card>();
					for (Card c : Card.all) {
						if (c != null) {
							if (c.getCost() == Bank.server.getClients(roomID).get(owner).baseGold)
								avail.add(c);
						}
					}
					ArrayList<Card> list = Util.createConjuringList(avail, 5);
					Bank.server.setPicks(list, roomID);
					Packet14ShowPicks pkt = new Packet14ShowPicks(owner, -1, -1, list);
					pkt.write(Bank.server.getRoom(roomID));
				} else {
					if (Util.clientID == owner) {
						Util.gold += 2;
						Util.maxGold += 2;
					}
				}
			}

			if (this == chaosNova) {
				if (target != null) {
					target.attack(type, target);
					if (!Bank.isServer())
						Animation.createStaticAnimation(target, "FELMAGIC", 1.25f, 600);
					else
						Bank.server.drawCard(Bank.server.getClients(roomID).get(owner), -1, 1);
				}
			}

			if (this == icicle) {
				if (target != null) {
					target.addOrUpdateEffect(new Effect(EffectType.chill, 2, 2));
					ArrayList<Unit> units = (Bank.isServer() ? Bank.server.getUnits(roomID) : Display.currentScreen.objects);
					if (!Bank.isServer()) {
						Animation.createStaticAnimation(target, "FREEZE", 2, 800);
					}
					for (Unit u : units) {
						if (u.hasEffect(EffectType.chill)) {
							u.hurt(1, target);
							if (!Bank.isServer()) {
								Util.drawParticleLine(target.getPoint(), u.getPoint(), 7, false, "FREEZE");
							}
						}
					}
				}
			}

			if (this == northwind) {
				if (target != null) {
					boolean cond = (target.posY < Util.boardHeight / 2);
					target.hurt(3, Display.currentScreen.getCommanderForPlayer(owner, roomID));
					Animation.createStaticAnimation(target, "FREEZE", 2, 900);
					if (cond) {
						target.addOrUpdateEffect(new Effect(EffectType.chill, 1, 2));
						target.hurt(2, Display.currentScreen.getCommanderForPlayer(owner, roomID));
						Animation.createStaticAnimation(target, "MANABURN", 2, 700);
					}
				}
			}

			if (this == strangulate) {
				if (target != null) {
					target.setHealth(3);
					target.setAttack(3);
					target.setSpeed(3);
					target.getEffects().clear();
				}
			}

			if (this == greenpotion) {
				int dmg = PersistentValue.getPersistentValueByTag("TURN_DIED", roomID).nVal;
				if (target != null) {
					target.hurt(dmg, Display.currentScreen.getCommanderForPlayer(owner, roomID));
					if (!Bank.isServer())
						Animation.createStaticAnimation(target, "FELFIRE", 3, 850);
				}
			}

			if (this == greenrelic) {
				if (target != null) {
					target.addOrUpdateEffect(new Effect(EffectType.attack, target.getAttack(), 999));
					target.addOrUpdateEffect(new Effect(EffectType.health, target.getHealth(), 999));
					target.addOrUpdateEffect(new Effect(EffectType.speed, (int) target.getSpeed(), 999));
					if (!Bank.isServer()) {
						Animation.createStaticAnimation(target, "FELMAGIC", 1.75f, 850);
						Animation.createStaticAnimation(target, "BURST", 2, 700);
					}
				}
			}

			if (this == bloodsurge) {
				ArrayList<Unit> units = (Bank.isServer() ? Bank.server.getUnits(roomID) : Display.currentScreen.objects);
				for (Unit u : units) {
					if (u.ownerID == owner) {
						for (int i = 0; i < 3; ++i) {
							for (int j = 0; j < 3; ++j) {
								int x1 = u.posX - 1 + i, y1 = u.posY - 1 + j;
								Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
								if (t != null) {
									t.hurt(2, null);
									if (!Bank.isServer()) {
										Util.drawParticleLine(u.getPoint(), t.getPoint(), 15, false, "FIRE");
										Animation.createStaticAnimation(t, "INCINERATE", 2, 900);
									}
								}
							}
						}
					}
				}
			}

			if (this == kengmarkets) {
				if (Bank.isServer()) {
					int num = Bank.server.getClients(roomID).get(owner).baseGold + 1;
					if (num > 10)
						num = 10;
					ArrayList<Card> avail = new ArrayList<Card>();
					for (Card c : Card.all) {
						if (c != null) {
							if (c.getCost() == num)
								avail.add(c);
						}
					}
					ArrayList<Card> list = new ArrayList<Card>();
					for (int i = 0; i < 3; ++i) {
						Card candidate = avail.get(Util.rand.nextInt(avail.size()));
						if (!list.contains(candidate)) {
							list.add(candidate);
							avail.remove(candidate);
						}
					}
					Bank.server.setPicks(list, roomID);
					Packet14ShowPicks pkt = new Packet14ShowPicks(owner, -1, -1, list);
					pkt.write(Bank.server.getRoom(roomID));
				}
			}

			if (this == duplication) {
				if (Bank.isServer()) {
					ArrayList<Card> avail = (ArrayList<Card>) Bank.server.getRoom(roomID).castSpells.clone();
					ArrayList<Card> list = Util.createConjuringList(avail, 3);
					Bank.server.setPicks(list, roomID);
					Packet14ShowPicks pkt = new Packet14ShowPicks(owner, -1, -1, list);
					pkt.write(Bank.server.getRoom(roomID));
				}
			}

			if (this == demonicritual) {
				if (Bank.isServer()) {
					String keyword = "demon";
					ArrayList<Card> avail = new ArrayList<Card>();
					for (Card c : Card.all) {
						if (c != null) {
							if (Util.findMatch(c, keyword) > 0)
								avail.add(c);
						}
					}
					ArrayList<Card> list = new ArrayList<Card>();
					for (int i = 0; i < 5; ++i) {
						Card candidate = avail.get(Util.rand.nextInt(avail.size()));
						if (!list.contains(candidate)) {
							list.add(candidate);
							avail.remove(candidate);
						}
					}
					Bank.server.setPicks(list, roomID);
					Packet14ShowPicks pkt = new Packet14ShowPicks(owner, -1, -1, list);
					pkt.write(Bank.server.getRoom(roomID));
				}
			}

			if (this == weaponspell) {
				if (target != null) {
					if (!Bank.isServer())
						target.setEquipmentDurability((byte) (target.getEquipmentDurability() + 2));
					Animation.createStaticAnimation(target, "BWNOVA", 0.8f, 1000);
				}
			}
			if (this == sculpture) {
				if (target != null) {
					if (Bank.isServer()) {
						Bank.server.drawCard(Bank.server.getClients(roomID).get(owner), target.getTemplate().getId(), 1);
					} else {
						Animation.createStaticAnimation(target, "MAGIC", 2, 900);
						Animation.createStaticAnimation(target, "EARTH", 2, 800);
					}
				}
			}
			if (this == prayer) {
				if (Bank.isServer()) {
					Unit hero = Bank.server.getCommander(Bank.server.getClients(roomID).get(owner));
					hero.heal(3);
					Bank.server.drawCard(Bank.server.getClients(roomID).get(owner), -1, 1);
					Bank.server.getClients(roomID).get(owner).deck.add(new CardShell(Card.prayer));
					Bank.server.getClients(roomID).get(owner).deck.add(new CardShell(Card.prayer));
				} else {
					Unit hero = Display.currentScreen.getCommanderForPlayer(owner, roomID);
					hero.heal(3);
					Animation.createStaticAnimation(hero, "FLASH", 2, 900);
					Animation.createStaticAnimation(hero, "SHINE", 2, 500);
				}
			}
			if (this == destruction) {
				Util.boardState = (CardBoardstate) Card.stateNormal;
				ArrayList<Unit> units = null;
				if (Bank.isServer())
					units = Bank.server.getUnits(roomID);
				else
					units = ((PanelBoard) Display.currentScreen).objects;
				for (int i = 0; i < units.size(); ++i) {
					if (units.get(i) != null) {
						Unit u = units.get(i);
						u.hurt(4, u);
						if (!Bank.isServer()) {
							Animation.createStaticAnimation(u, "EXPLOSION", 2.5f, 800);
						}
					}
				}
				// Animation.createStaticAnimation(Display.currentScreen.mousePoint.x,
				// Display.currentScreen.mousePoint.y, "EXPLOSION", 6, 800);
				byte[][] grid = null;
				if (Bank.isServer())
					grid = Bank.server.getGrid(roomID).getCore();
				else
					grid = ((PanelBoard) Display.currentScreen).getGrid().getCore();
				for (int i = 0; i < grid[0].length; ++i) {
					for (int j = 0; j < grid[1].length; ++j) {
						grid[i][j] = (byte) GridBlock.ground.getID();
						if (!Bank.isServer())
							Util.drawParticleLine(Display.currentScreen.mousePoint,
									new Point(Util.boardOffsetX + Grid.tileSize * i + Grid.tileSize / 2,
											Util.boardOffsetY + Grid.tileSize * j + Grid.tileSize / 2),
									14, false, "FIRE");
					}
				}
			}
			if (this == brainwash) {
				if (target != null) {
					if (target.ownerID == 0)
						++target.ownerID;
					else
						--target.ownerID;
					target.addOrUpdateEffect(new Effect(EffectType.attack, -4, 999));
					target.addOrUpdateEffect(new Effect(EffectType.health, -4, 999));
					if (target.getHealth() <= 0) {
						target.hurt(1, target);
					}
				}
			}
			if (this == quickshot) {
				for (int i = 0; i < Util.boardWidth; ++i) {
					Animation anim = new Animation(Util.boardOffsetX + Grid.tileSize * i,
							Util.boardOffsetY + Grid.tileSize * y, 500 + 100 * i, Animation.TAG_VOLLEY);
					Display.currentScreen.particles.add(anim);
					Unit u = Display.currentScreen.getUnitOnTile(i, y, roomID);
					if (u != null) {
						u.hurt(3, Display.currentScreen.getCommanderForPlayer(owner, roomID));
					}
				}
			}
			if (this == darkvisions) {
				if (target != null) {
					if (!target.getIsCommander()) {
						int abilitycount = target.getAbilities().size() - 2;
						target.getAbilities().clear();
						target.addOrUpdateEffect(new Effect(EffectType.attack, abilitycount * 2, 999));
						target.addOrUpdateEffect(new Effect(EffectType.health, abilitycount * 2, 999));
						Animation.createStaticAnimation(target, "VOIDCAST", 2, 900);
						Animation.createStaticAnimation(target, "THUNDER", 2, 500);
						target.getAbilities().add(CardAbility.attack);
						target.getAbilities().add(CardAbility.move);
					}
				}
			}
			if (this == song) {
				ArrayList<Unit> units = null;
				if (Bank.isServer())
					units = Bank.server.getUnits(roomID);
				else
					units = ((PanelBoard) Display.currentScreen).objects;
				for (Unit u : units) {
					if (u.getHealth() < u.getHealthMax()) {
						if (Bank.isServer()) {
							Bank.server.drawCard(Bank.server.getClients(roomID).get(o), healingsong.getId(), 1);
						} else {
							Util.drawParticleLine(u.getPoint(), Display.currentScreen.getMousePoint(), 14, false,
									"BLOOM");
						}
					}
				}
			}
			if (this == healingsong) {
				target.heal(2);
				Animation.createStaticAnimation(target, "HEAL", 2, 600);
			}
			if (this == splash) {
				if (Bank.isServer()) {
					Bank.server.getGrid(roomID).setTileID(x, y, GridBlock.water.getID());
					Bank.server.getClients(roomID).get(owner).deck.add(new CardShell(Card.splash));
					Bank.server.getClients(roomID).get(owner).deck.add(new CardShell(Card.splash));
				} else {
					((PanelBoard) Display.currentScreen).getGrid().setTileID(x, y, GridBlock.water.getID());
					Animation.createStaticAnimation(Display.currentScreen.mousePoint.x,
							Display.currentScreen.mousePoint.y, "POOL", 1.5f, 900);
				}
			}
			if (this == flood) {
				for (int i = 0; i < 3; ++i) {
					for (int j = 0; j < 3; ++j) {
						int x1 = x - 1 + i, y1 = y - 1 + j;
						if (Bank.isServer()) {
							Bank.server.getGrid(roomID).setTileID(x1, y1, GridBlock.water.getID());
						} else {
							((PanelBoard) Display.currentScreen).getGrid().setTileID(x1, y1, GridBlock.water.getID());
							Animation.createStaticAnimation(Display.currentScreen.mousePoint.x,
									Display.currentScreen.mousePoint.y, "POOL", 4, 1200);
						}
					}
				}
			}
			if (this == corruptground) {
				for (int i = 0; i < 3; ++i) {
					for (int j = 0; j < 3; ++j) {
						int x1 = x - 1 + i, y1 = y - 1 + j;
						if (Bank.isServer()) {
							Bank.server.getGrid(roomID).setTileID(x1, y1, GridBlock.voidtile.getID());
						} else {
							((PanelBoard) Display.currentScreen).getGrid().setTileID(x1, y1, GridBlock.voidtile.getID());
							Animation.createStaticAnimation(Display.currentScreen.mousePoint.x,
									Display.currentScreen.mousePoint.y, "VOID", 4, 1200);
						}
					}
				}
			}
			if (this == dragoncall) {
				for (int i = 0; i < 3; ++i) {
					for (int j = 0; j < 3; ++j) {
						int x1 = x - 1 + i, y1 = y - 1 + j;
						if (Bank.isServer()) {
							if (Bank.server.getGrid(roomID).getTileID(x1, y1) == GridBlock.ground.getID())
								Bank.server.getGrid(roomID).setTileID(x1, y1, GridBlock.scorched.getID());
						} else {
							if (((PanelBoard) Display.currentScreen).getGrid().getTileID(x1, y1) == GridBlock.ground
									.getID()) {
								((PanelBoard) Display.currentScreen).getGrid().setTileID(x1, y1,
										GridBlock.scorched.getID());
								Animation.createStaticAnimation(Grid.tileSize * x1 + Util.boardOffsetX,
										Grid.tileSize * y1 + Util.boardOffsetY, "INCINERATE", 2, 800);
							}
						}
					}
				}
			}
			if (this == ravenseye) {
				if (Bank.isServer()) {
					int enemy = (owner == 0 ? 1 : 0);
					String comm = "";
					for (int i = 0; i < 3; ++i) {
						CardShell cs = Bank.server.getClients(roomID).get(enemy).hand
								.get(Util.rand.nextInt(Bank.server.getClients(roomID).get(enemy).hand.size()));
						if(cs==null)cs = new CardShell(Card.filler);
						comm += (cs.getCard().getId() + ",");
					}
					this.sendComm(comm, owner, guid, x, y, roomID);
				}
				if (Bank.isClient()) {
					Animation.createStaticAnimation(Display.currentScreen.mousePoint.x,
							Display.currentScreen.mousePoint.y, "VOIDCAST", 3, 1000);
				}
			}
			if (this == fleshoffire) {
				if (target != null) {
					target.getAbilities().add(CardAbility.fleshOfFire);
					if (Bank.isClient()) {
						Animation.createStaticAnimation(target, "EXPLOSION", 2, 700);
					}
				}
			}
			if (this == raid && Bank.isServer()) {
				if (target != null) {
					int count = target.getHealth();
					int atk = target.getAttack();
					if (atk > 7)
						atk = 6;
					if (count > 7)
						count = 7;
					for (int j = 0; j < count; ++j) {
						ArrayList<Card> avail = new ArrayList<Card>();
						for (int i = 0; i < Card.all.length; ++i) {
							if (Card.all[i] != null) {
								if (Card.all[i].getUnit() != null) {
									if (Card.all[i].getCost() == atk)
										avail.add(Card.all[i]);
								}
							}
						}
						if (avail.size() > 0) {
							UnitTemplate tid = (UnitTemplate) avail.get(Util.rand.nextInt(avail.size()));
							Bank.server.addUnit(tid, Util.rand.nextInt(Util.boardWidth),
									Util.rand.nextInt(Util.boardHeight), owner, roomID);
						}
					}
				}
			}
			if (this == execute) {
				if (target != null) {
					target.hurt(3, target);
					if (Bank.isClient()) {
						Animation.createStaticAnimation(target, "SLASH", 2, 700);
					}
					if (target.getHealth() > 4) {
						target.hurt(target.getHealth(), target);
						if (Bank.isClient()) {
							Animation.createStaticAnimation(target, "EARTH", 2, 900);
						}
					}
				}

			}
			if (this == voyage) {
				if (Bank.isServer()) {
					for (PlayerMP pl : Bank.server.getClients(roomID)) {
						ArrayList<CardShell> dupe = (ArrayList<CardShell>) pl.deck.clone();
						for (CardShell cs : dupe) {
							pl.deck.add(cs);
						}
					}
				} else {
					Animation.createStaticAnimation(Util.boardOffsetX + Grid.tileSize * (Util.boardWidth + 2),
							Util.boardOffsetY + Grid.tileSize * (Util.boardHeight + 1), "WATERCOLUMN", 10, 3000);
				}
			}
			if (this == prism) {
				if (Bank.isServer()) {
					PlayerMP pl = Bank.server.getClients(roomID).get(owner);
					ArrayList<CardShell> dupe = (ArrayList<CardShell>) pl.deck.clone();
					for (CardShell cs : dupe) {
						if (cs.getCard().isSpell()) {
							pl.deck.add(cs);
						}
					}
				} else {
					Animation.createStaticAnimation(Util.boardOffsetX + Grid.tileSize * (Util.boardWidth + 2),
							Util.boardOffsetY + Grid.tileSize * (Util.boardHeight + 1), "MAGIC", 10, 3000);
				}
			}
			if (this == heartgem) {
				if (Bank.isServer()) {
					for (PlayerMP pl : Bank.server.getClients(roomID)) {
						Bank.server.getCommander(pl).addOrUpdateEffect(new Effect(EffectType.health, 12, 999));
					}
				} else {
					PanelBoard panel = (PanelBoard) Display.currentScreen;
					for (Unit u : panel.objects) {
						if (u.getIsCommander()) {
							u.addOrUpdateEffect(new Effect(EffectType.health, 12, 999));
							Animation.createStaticAnimation(u, "SHIELD", 1, 700);
							Animation.createStaticAnimation(u, "SHINE", 2, 900);
						}
					}
				}
			}
			if (this == prism1) {
				if (Bank.isServer()) {
					PlayerMP pl = Bank.server.getClients(roomID).get(owner);
					ArrayList<CardShell> dupe = (ArrayList<CardShell>) pl.deck.clone();
					for (CardShell cs : dupe) {
						if (cs.getCard().isUnit()) {
							pl.deck.add(cs);
						}

					}
				} else {
					Animation.createStaticAnimation(Util.boardOffsetX + Grid.tileSize * (Util.boardWidth + 2),
							Util.boardOffsetY + Grid.tileSize * (Util.boardHeight + 1), "RUNE", 10, 3000);
				}
			}
			if (this == bogtrade) {
				if (Bank.isServer()) {
					PlayerMP pl = Bank.server.getClients(roomID).get(owner);
					ArrayList<CardShell> dupe = (ArrayList<CardShell>) pl.deck.clone();
					for (CardShell cs : pl.deck) {
						if (cs.getCard().isSpell()) {
							dupe.remove(cs);
							dupe.add(new CardShell(Card.pileWood));
						}
					}
					pl.deck = dupe;
				} else {
					Animation.createStaticAnimation(Util.boardOffsetX + Grid.tileSize * (Util.boardWidth + 2),
							Util.boardOffsetY + Grid.tileSize * (Util.boardHeight + 1), "LEAVES", 10, 2000);
					Animation.createStaticAnimation(Util.boardOffsetX + Grid.tileSize * (Util.boardWidth + 2),
							Util.boardOffsetY + Grid.tileSize * (Util.boardHeight + 1), "SLASH", 8, 1000);
				}
			}
			if (this == bladequest) {
				if (Bank.isServer()) {
					PlayerMP pl = Bank.server.getClients(roomID).get(owner);
					pl.deck.add(new CardShell(Card.blade));
				} else {
					Animation.createStaticAnimation(Display.currentScreen.mousePoint.x,
							Display.currentScreen.mousePoint.y, "FREEZE", 8, 2500);
					Animation.createStaticAnimation(Util.boardOffsetX + Grid.tileSize * (Util.boardWidth + 2),
							Util.boardOffsetY + Grid.tileSize * (Util.boardHeight + 1), "SLASH", 8, 1000);
				}
			}
			if (this == deathlotus) {
				if (Bank.isServer()) {
				} else {
					Animation.createStaticAnimation(Display.currentScreen.getMousePoint().x,
							Display.currentScreen.getMousePoint().y, "BLUELIGHT", 4, 1250);
				}
			}
			if (this == markets) {
				if (Bank.isServer()) {
					PlayerMP pl = Bank.server.getClients(roomID).get(owner);
					ArrayList<CardShell> dupe = (ArrayList<CardShell>) pl.deck.clone();
					ArrayList<Card> avail = new ArrayList<Card>();
					for (Card c : Card.all) {
						if (c instanceof UnitTemplate) {
							if (((UnitTemplate) c).getFamily() == Family.demon) {
								avail.add(c);
							}
						}
					}
					for (CardShell cs : pl.deck) {
						if (cs.getCard().isSpell()) {
							Bank.server.drawCard(pl, cs.getCard().id, 1);
							dupe.remove(cs);
							dupe.add(new CardShell(avail.get(Util.rand.nextInt(avail.size()))));
						}
					}
					pl.deck = dupe;
				} else {
					Animation.createStaticAnimation(Util.boardOffsetX + Grid.tileSize * (Util.boardWidth + 2),
							Util.boardOffsetY + Grid.tileSize * (Util.boardHeight + 1), "VOID", 10, 3000);
				}
			}
			if (this == necrotome) {
				if (Bank.isServer()) {
					PlayerMP pl = Bank.server.getClients(roomID).get(owner);
					ArrayList<CardShell> dupe = (ArrayList<CardShell>) pl.deck.clone();
					ArrayList<Card> avail = new ArrayList<Card>();
					for (Card c : Card.all) {
						if (c instanceof UnitTemplate) {
							if (((UnitTemplate) c).getFamily() == Family.undead) {
								avail.add(c);
							}
						}
					}
					int size = pl.deck.size();
					pl.deck.clear();
					for (int i = 0; i < size; ++i) {
						pl.deck.add(new CardShell(avail.get(Util.rand.nextInt(avail.size()))));
					}
					for (Unit u : (Bank.isServer() ? Bank.server.getUnits(roomID) : Display.currentScreen.objects)) {
						if (u.getIsCommander()) {
							u.setHealth(15);
							u.setHealthMax(15);
							if (!Bank.isServer())
								Animation.createStaticAnimation(u, "SKULLGAS", 2, 750);
						}
					}
				} else {
					Animation.createStaticAnimation(Util.boardOffsetX + Grid.tileSize * (Util.boardWidth + 2),
							Util.boardOffsetY + Grid.tileSize * (Util.boardHeight + 1), "SKULLGAS", 10, 3000);
				}
			}
			if (this == worldmelt) {
				if (Bank.isServer()) {
					ArrayList<CardShell> combined = new ArrayList<CardShell>();
					for (PlayerMP pl : Bank.server.getClients(roomID)) {
						for (CardShell cs : pl.deck) {
							combined.add(cs);
						}
					}
					for (PlayerMP pl : Bank.server.getClients(roomID)) {
						pl.deck = (ArrayList<CardShell>) combined.clone();
					}
				} else {
					Animation.createStaticAnimation(Display.currentScreen.mousePoint.x,
							Display.currentScreen.mousePoint.y, "EXPLOSION", 20, 4000);
					Animation.createStaticAnimation(Util.boardOffsetX + Grid.tileSize * (Util.boardWidth + 2),
							Util.boardOffsetY + Grid.tileSize * (Util.boardHeight + 1), "VOIDCAST", 10, 3000);
				}
			}
			if (this == greed) {
				if (Bank.isServer()) {
					Bank.server.drawCard(Bank.server.getClients(roomID).get(owner), -1, 1);
					Bank.server.drawCard(Bank.server.getClients(roomID).get(owner), -1, 1);
					Unit hero = Bank.server.getCommander(Bank.server.getClients(roomID).get(owner));
					hero.hurt(5, hero);
				} else {
					Unit hero = ((PanelBoard) Display.currentScreen).getCommanderForPlayer(owner, roomID);
					hero.hurt(5, hero);
				}
			}
			if (this == pileWood) {
				if (Bank.isServer()) {
					Bank.server.drawCard(Bank.server.getClients(roomID).get(owner), -1, 1);
				} else {
					Animation.createStaticAnimation(Display.currentScreen.getMousePoint().x,
							Display.currentScreen.getMousePoint().y, "LEAVES", 3, 1200);
					Animation.createStaticAnimation(Display.currentScreen.getMousePoint().x,
							Display.currentScreen.getMousePoint().y, "SLASH", 3, 1200);
				}
			}
			if (this == cursedGold) {
				if (Bank.isServer()) {
					PlayerMP user = Bank.server.getClients(roomID).get(owner);
					user.baseGold++;
					Unit hero = Bank.server.getCommander(user);
					hero.hurt(5, hero);
				} else {
					if (Util.clientID == owner) {
						Unit hero = ((PanelBoard) Display.currentScreen).getCommanderForPlayer(owner, roomID);
						hero.hurt(5, hero);
						Util.gold++;
						Util.maxGold++;
					}
				}
			}
			if (this == goldShard) {
				if (Bank.isServer()) {
					PlayerMP user = Bank.server.getClients(roomID).get(owner);
					user.gold++;
				} else {
					if (Util.clientID == owner) {
						Util.gold++;
					}
					Animation.createStaticAnimation(Display.currentScreen.getMousePoint().x,
							Display.currentScreen.getMousePoint().y, "FLASH", 3, 1200);
					for (int i = 0; i < Util.gold; ++i) {
						Animation.createStaticAnimation(Util.boardOffsetX + i * 26 + 5,
								Util.boardOffsetY + Util.boardHeight * Grid.tileSize + 75, "BLUELIGHT", 1.75f,
								500 + 200 * i);
					}
				}
			}
			if (this == blade) {
				if (target != null) {
					if (Bank.isServer()) {
						Bank.server.drawCard(Bank.server.getClients(roomID).get(owner), bladequest.getId(), 1);
					}
					target.addOrUpdateEffect(new Effect(EffectType.attack, 7, 999));
					target.addOrUpdateEffect(new Effect(EffectType.health, 7, 999));
					Animation.createStaticAnimation(target, "FREEZE", 2, 600);
					Animation.createStaticAnimation(target, "SLASH", 2, 1000);
				}
			}
			if (this == assault && Bank.isServer()) {
				for (int j = 0; j < 3; ++j) {
					ArrayList<Card> avail = new ArrayList<Card>();
					for (int i = 0; i < Card.all.length; ++i) {
						if (Card.all[i] != null) {
							if (Card.all[i].getUnit() != null) {
								if (Card.all[i].getUnit().getFamily() == Family.demon)
									avail.add(Card.all[i]);
							}
						}
					}
					UnitTemplate tid = (UnitTemplate) avail.get(Util.rand.nextInt(avail.size()));
					Bank.server.addUnit(tid, Util.rand.nextInt(Util.boardWidth), Util.rand.nextInt(Util.boardHeight),
							owner, roomID);
				}
			}
			if (this == eldritchParasite) {
				target.getAbilities().add(CardAbility.eldritchParasite);
				if (!Bank.isServer()) {
					Animation.createStaticAnimation(target, "WATERBIRD", 2, 1000);
					Animation.createStaticAnimation(target, "FELMAGIC", 2, 800);
				}
			}
			if (this == crypt && Bank.isServer()) {
				for (int j = 0; j < 4; ++j) {
					ArrayList<Card> avail = new ArrayList<Card>();
					for (int i = 0; i < Card.all.length; ++i) {
						if (Card.all[i] != null) {
							if (Card.all[i].getUnit() != null) {
								if (Card.all[i].getUnit().getFamily() == Family.undead)
									avail.add(Card.all[i]);
							}
						}
					}
					UnitTemplate tid = (UnitTemplate) avail.get(Util.rand.nextInt(avail.size()));
					Bank.server.addUnit(tid, Util.rand.nextInt(Util.boardWidth), Util.rand.nextInt(Util.boardHeight),
							owner, roomID);
				}
			}
			if (this == steal && Bank.isServer()) {
				int opponent = (owner == 0 ? 1 : 0);
				PlayerMP pl = Bank.server.getClients(roomID).get(opponent);
				if (pl.hand.size() > 0) {
					int r = Util.rand.nextInt(pl.hand.size());
					Bank.server.drawCard(Bank.server.getClients(roomID).get(owner), pl.hand.get(r).getCard().getId(), 1);
				}
			}
			if (this == stateHellfire || this == stateHealingrain || this == stateArcaneSurge || this == stateNormal
					|| this == stateBlizzard || this == stateSandstorm || this == this.stateRevolution
					|| this == stateSpirit || this == this.stateMalestrom || this == stateKholosian) {
				Util.boardState = (CardBoardstate) this;
				Util.boardStateTimer = 4;
			}
			if (this == madness) {
				if (Bank.server != null) {
					for (Unit u : Bank.server.getUnits(roomID)) {
						if (!u.getIsCommander())
							u.getAbilities().add(CardAbility.confusion);
					}
				}
				if (Bank.isClient()) {
					for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
						Unit u = Display.currentScreen.objects.get(i);
						if (u != null) {
							if (!u.getIsCommander())
								u.getAbilities().add(CardAbility.confusion);
							Animation.createStaticAnimation(u, "VOIDCAST", 2, 900);
						}
					}
				}
			}
			if (this == stormblaze) {
				if (target != null) {
					target.hurt(5, Display.currentScreen.getCommanderForPlayer(owner, roomID));
					Animation.createStaticAnimation(target, "INCINERATE", 2, 900);
					Animation.createStaticAnimation(target, "THUNDER", 2, 900);
					if (!target.isRemoving() && !target.getIsCommander()) {
						target.getAbilities().remove(CardAbility.move);
						Animation.createStaticAnimation(target, "MANABURN", 2, 900);
					}
				}
			}
			if (this == earth) {
				if (target != null) {
					int atk = target.getHealth() + 3 - target.getHealthMax();
					target.heal(3);
					if (atk > 0)
						target.addOrUpdateEffect(new Effect(EffectType.attack, atk, 999));
					Animation.createStaticAnimation(target, "BOON", 2, 700);
					Animation.createStaticAnimation(target, "HEAL", 3, 900);
				}
			}
			if (this == divine) {
				if (target != null) {
					target.heal(9);
					Animation.createStaticAnimation(target, "HOLY", 2, 500);
					Animation.createStaticAnimation(target, "SHINE", 2, 1200);
				}
			}
			if (this == voidshell) {
				if (target != null) {
					target.addOrUpdateEffect(new Effect(EffectType.health, 2, 999));
					if (Bank.isServer()) {
						Bank.server.drawCard(Bank.server.getClients(roomID).get(owner), -1, 1);
					}
					Animation.createStaticAnimation(target, "VOID", 2, 900);
					Animation.createStaticAnimation(target, "SHIELD", 2, 600);
				}
			}
			if (this == shatter) {
				if (target != null) {
					if (target.hasEffect(EffectType.chill) & !target.getIsCommander()) {
						target.hurt(target.getHealth() * 3, target);
						Animation.createStaticAnimation(target, "SHATTER", 3, 600);
						target.flagRemove = true;
					}
				}
			}
			if (this == nullpointer) {
				if (target != null) {
					if (!target.getIsCommander()) {
						target.getAbilities().clear();
						Animation.createStaticAnimation(target, "MANABURN", 2, 900);
						Animation.createStaticAnimation(target, "THUNDER", 2, 500);
						target.getAbilities().add(CardAbility.attack);
						target.getAbilities().add(CardAbility.move);
					}
				}
			}
			if (this == defile) {
				if (target != null) {
					if (!target.getIsCommander()) {
						target.getAbilities().clear();
						Animation.createStaticAnimation(target, "BLUELIGHT", 2, 1200);
						Animation.createStaticAnimation(target, "FLASH", 2, 600);
						target.hurt(2, target);
						target.getAbilities().add(CardAbility.attack);
						target.getAbilities().add(CardAbility.move);
					}
				}
			}
			if (this == upheaval && Bank.isServer()) {
				if (target != null) {
					int dmg = 2 + Util.rand.nextInt(7);
					target.hurt(dmg, target);
					if (target.getHealth() <= 0) {
						for (int i = 0; i < 3; ++i) {
							for (int j = 0; j < 3; ++j) {
								int x1 = x - 1 + i, y1 = y - 1 + j;
								Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
								if (t != null) {
									t.hurt(dmg, null);
								}
							}
						}
					}
					this.sendComm(target.GUID + "," + dmg, owner, guid, 0, 0, roomID);
				}
			}
			if (this == volley) {
				for (int i = 0; i < 3; ++i) {
					for (int j = 0; j < 3; ++j) {
						int x1 = x - 1 + i, y1 = y - 1 + j;
						Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
						if (t != null) {
							t.hurt(2, null);
							Animation anim = new Animation(Util.boardOffsetX + Grid.tileSize * t.posX,
									Util.boardOffsetY + Grid.tileSize * t.posY, 800 + Util.rand.nextInt(1201),
									Animation.TAG_VOLLEY);
							Display.currentScreen.particles.add(anim);
							Animation.createStaticAnimation(t, "SLASH", 2, 500);
						}
					}
				}
			}
			if (this == sacrifice) {
				if (target != null) {
					if (target.getFamily() == Family.demon) {
						target.hurt(target.getHealth(), target);
						if (Bank.isServer()) {
							for (Unit t : Bank.server.getUnits(roomID)) {
								if (t.ownerID != owner) {
									t.hurt(1, target);
									Animation.createStaticAnimation(t, "INCINERATE", 1.25f, 600);
									Animation.createStaticAnimation(t, "BLOOD", 2, 400);
								}
							}
						} else {
							for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
								Unit t = Display.currentScreen.objects.get(i);
								if (t != null) {
									if (t.ownerID != owner) {
										t.hurt(1, target);
										Animation.createStaticAnimation(t, "INCINERATE", 1.25f, 600);
										Animation.createStaticAnimation(t, "BLOOD", 2, 400);
									}
								}
							}
						}
					}
				}
			}
			if (this == bombingrun) {
				if (Bank.isServer()) {
					for (Unit t : Bank.server.getUnits(roomID)) {
						if (t.ownerID != owner) {
							t.hurt(3, target);
							Animation.createStaticAnimation(t, "EXPLOSION", 2, 400);
						}
					}
				} else {
					for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
						Unit t = Display.currentScreen.objects.get(i);
						if (t != null) {
							if (t.ownerID != owner) {
								t.hurt(3, target);
								Animation.createStaticAnimation(t, "EXPLOSION", 2, 400);
							}
						}
					}
				}
			}
			if (this == voidcurse && Bank.isServer()) {
				if (target != null) {
					if (!target.getIsCommander()) {
						ArrayList<Card> avail = new ArrayList<Card>();
						for (int i = 0; i < Card.all.length; ++i) {
							if (Card.all[i] != null) {
								if (Card.all[i].getUnit() != null) {
									if (Card.all[i].getUnit().getFamily() == Family.demon)
										avail.add(Card.all[i]);
								}
							}
						}
						UnitTemplate tid = (UnitTemplate) avail.get(Util.rand.nextInt(avail.size()));
						target.setTemplate(tid);
						target.resummon();
						this.sendComm(target.GUID + "," + tid.getId(), owner, guid, 0, 0, roomID);
					}
				}
			}
			if (this == curse && Bank.isServer()) {
				if (target != null) {
					if (!target.getIsCommander()) {
						ArrayList<Card> avail = new ArrayList<Card>();
						for (int i = 0; i < Card.all.length; ++i) {
							if (Card.all[i] != null) {
								if (Card.all[i].getUnit() != null) {
									if (Card.all[i].getUnit().getFamily() == Family.undead)
										avail.add(Card.all[i]);
								}
							}
						}
						UnitTemplate tid = (UnitTemplate) avail.get(Util.rand.nextInt(avail.size()));
						target.setTemplate(tid);
						target.resummon();
						this.sendComm(target.GUID + "," + tid.getId(), owner, guid, 0, 0, roomID);
					}
				}
			}
			if (this == exhaust) {
				if (target != null) {
					target.setEnergy1(0);
					Animation.createStaticAnimation(target, "MANABURN", 2, 900);
				}
			}
			if (this == wither) {
				if (target != null) {
					target.setEnergy1(target.getEnergy() - 3);
					Animation.createStaticAnimation(target, "MANABURN", 2, 900);
					if (target.getEnergy() >= 1) {
						Animation.createStaticAnimation(target, "VOID", 2, 500);
						target.hurt(4, target);
					}
				}
			}
			if (this == electrify) {
				if (target != null) {
					target.setEnergy1(target.getEnergy() + 3);
					Animation.createStaticAnimation(target, "CHARGE", 2, 800);
					Animation.createStaticAnimation(target, "THUNDER", 2, 1200);
					target.hurt(3, target);
				}
			}
			if (this == firejet) {
				if (target != null) {
					target.hurt(2, target);
					Animation.createStaticAnimation(target, "INCINERATE", 1.5f, 800);
					if (target != null) {
						if (target.getHealth() >= 0) {
							if (Bank.isServer()) {
								Bank.server.drawCard(Bank.server.getClients(roomID).get(owner), firejet.getId(), 1);
							}
						}
					}
				}
			}
			if (this == rageofkholos && Bank.isServer()) {
				if (Bank.server.getUnits(roomID).size() > 0) {
					int r = Util.rand.nextInt(Bank.server.getUnits(roomID).size());
					this.sendComm(Bank.server.getUnits(roomID).get(r).GUID + "", owner, guid, x, y, roomID);
				}
			}
			if (this == eldritchtome) {
				if (target != null) {
					ArrayList<CardAbility> newabs = (ArrayList<CardAbility>) target.getAbilities().clone();
					for (CardAbility a : target.getAbilities()) {
						if (a == CardAbility.attack)
							newabs.remove(a);
					}
					target.setAbilities(newabs);
					for (int i = 0; i < 5; ++i) {
						ArrayList<CardAbility> abs = new ArrayList<CardAbility>();
						for (CardAbility ca : CardAbility.all) {
							if (ca != null)
								abs.add(ca);
						}
						CardAbility ab = abs.get(Util.rand.nextInt(abs.size()));
						this.sendComm(target.GUID + "," + ab.getId(), owner, guid, x, y, roomID);
					}
					if (!Bank.isServer()) {
						Animation.createStaticAnimation(target, "VOID", 2, 800);
						Animation.createStaticAnimation(target, "WATERBIRD", 2, 1000);
					}
				}
			}
			if (this == train && Bank.isServer()) {
				if (target != null) {
					ArrayList<CardAbility> abs = new ArrayList<CardAbility>();
					for (CardAbility ca : CardAbility.all) {
						if (ca != null)
							abs.add(ca);
					}
					CardAbility ab = abs.get(Util.rand.nextInt(abs.size()));
					this.sendComm(target.GUID + "," + ab.getId(), owner, guid, x, y, roomID);
				}
			}
			if (this == train1 && Bank.isServer()) {
				if (target != null) {
					ArrayList<CardAbility> abs = new ArrayList<CardAbility>();
					for (CardAbility ca : CardAbility.all) {
						if (ca != null)
							abs.add(ca);
					}
					CardAbility ab = abs.get(Util.rand.nextInt(abs.size()));
					CardAbility ab1 = abs.get(Util.rand.nextInt(abs.size()));
					this.sendComm(target.GUID + "," + ab.getId() + "," + ab1.getId(), owner, guid, x, y, roomID);
				}
			}
			if (this == revolution && Bank.isServer()) {
				for (Unit u : Bank.server.getUnits(roomID)) {
					ArrayList<CardAbility> abs = new ArrayList<CardAbility>();
					for (CardAbility ca : CardAbility.all) {
						if (ca != null)
							abs.add(ca);
					}
					CardAbility ab = abs.get(Util.rand.nextInt(abs.size()));
					this.sendComm(u.GUID + "," + ab.getId(), owner, guid, x, y, roomID);
				}
			}
			if (this == mapTreasure) {
				for (int i = 0; i < 3; ++i) {
					if (Bank.isServer()) {
						Unit u = Bank.server.addUnit((UnitTemplate) UnitTemplate.chest,
								Util.rand.nextInt(Util.boardWidth), Util.rand.nextInt(Util.boardHeight), owner, roomID);
					}
				}
			}
			if (this == bomb) {
				if (Bank.isServer()) {
					Unit u = Bank.server.addUnit((UnitTemplate) UnitTemplate.bombbot, x, y, owner, roomID);
				}
			}
			if (this == summonWaygate) {
				if (Bank.isServer()) {
					Unit u = Bank.server.addUnit((UnitTemplate) UnitTemplate.waygate, x, y, owner, roomID);
				}
			}
			/*
			 * if(this == summon){ if(Bank.isServer()){ Card c = Card.filler;
			 * ArrayList<Card> avail = new ArrayList<Card>(); for(Card card :
			 * all){ if(card!=null){ if(card.getCost() == 2)avail.add(card); } }
			 * c = avail.get(Util.rand.nextInt(avail.size()));
			 * Bank.server.drawCard(Bank.server.getClients(roomID).get(owner), c.getId(),
			 * 1); }else{ int cw = Properties.width/6; AnimatedParticle part =
			 * new AnimatedParticle(Display.currentScreen.getMousePoint().x - cw
			 * / 2, Display.currentScreen.getMousePoint().y - cw / 2 - cw / 10,
			 * cw, 800).addFrameSet("NOVA");
			 * Display.currentScreen.particles.add(part); } } if(this ==
			 * summon1){ if(Bank.isServer()){ Card c = Card.filler;
			 * ArrayList<Card> avail = new ArrayList<Card>(); for(Card card :
			 * all){ if(card!=null){ if(card.getCost() == 3)avail.add(card); } }
			 * c = avail.get(Util.rand.nextInt(avail.size()));
			 * Bank.server.drawCard(Bank.server.getClients(roomID).get(owner), c.getId(),
			 * 1); }else{ int cw = Properties.width/6; AnimatedParticle part =
			 * new AnimatedParticle(Display.currentScreen.getMousePoint().x - cw
			 * / 2, Display.currentScreen.getMousePoint().y - cw / 2 - cw / 10,
			 * cw, 800).addFrameSet("NOVA");
			 * Display.currentScreen.particles.add(part); } } if(this ==
			 * summon2){ if(Bank.isServer()){ Card c = Card.filler;
			 * ArrayList<Card> avail = new ArrayList<Card>(); for(Card card :
			 * all){ if(card!=null){ if(card.getCost() == 5)avail.add(card); } }
			 * c = avail.get(Util.rand.nextInt(avail.size()));
			 * Bank.server.drawCard(Bank.server.getClients(roomID).get(owner), c.getId(),
			 * 1); }else{ int cw = Properties.width/6; AnimatedParticle part =
			 * new AnimatedParticle(Display.currentScreen.getMousePoint().x - cw
			 * / 2, Display.currentScreen.getMousePoint().y - cw / 2, cw,
			 * 800).addFrameSet("NOVA");
			 * Display.currentScreen.particles.add(part); } }
			 */
			if (this == flameburst) {
				if (target != null) {
					target.hurt(5, target);
					Animation.createStaticAnimation(target, "EXPLOSION", 2, 900);
					Animation.createStaticAnimation(target, "INCINERATE", 2, 1600);
				}
			}
			if (this == apocalypse) {
				if (Bank.isServer()) {
					for (Unit t : Bank.server.getUnits(roomID)) {
						t.hurt(4, target);
						Animation.createStaticAnimation(t, "EXPLOSION", 2, 400);
					}
				} else {
					for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
						Unit t = Display.currentScreen.objects.get(i);
						if (t != null) {
							t.hurt(4, target);
							Animation.createStaticAnimation(t, "EXPLOSION", 2, 400);
						}
					}
				}
			}
			if (this == siege) {
				if (Bank.isServer()) {
					for (Unit t : Bank.server.getUnits(roomID)) {
						if (t.ownerID != owner && t.getTemplate() == Card.wall || t.getTemplate() == Card.cannon) {
							t.hurt(5, target);
						}
					}
				} else {
					for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
						Unit t = Display.currentScreen.objects.get(i);
						if (t != null) {
							if (t.ownerID != owner && t.getTemplate() == Card.wall || t.getTemplate() == Card.cannon) {
								t.hurt(5, target);
								Animation.createStaticAnimation(t, "EXPLOSION", 2, 800);
								Animation anim = new Animation(Grid.tileSize * t.posX + Util.boardOffsetX,
										Grid.tileSize * t.posY + Util.boardOffsetY, 2000, Animation.TAG_CANNONBALL);
								Display.currentScreen.particles.add(anim);
							}
						}
					}
				}
			}
			if (this == storm) {
				for (int i = 0; i < 3; ++i) {
					for (int j = 0; j < 3; ++j) {
						int x1 = x - 1 + i, y1 = y - 1 + j;
						Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
						if (t != null) {
							t.hurt(3, null);
							Animation.createStaticAnimation(t, "THUNDER", 2, 900);
						}
					}
				}
			}
			if (this == shout) {
				for (int i = 0; i < 5; ++i) {
					for (int j = 0; j < 5; ++j) {
						int x1 = x - 2 + i, y1 = y - 2 + j;
						Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
						if (t != null) {
							if (!Bank.isServer())
								Animation.createStaticAnimation(t, "CHARGE", 2, 900);
							if (t.getHealth() < t.getHealthMax()) {
								t.addOrUpdateEffect(new Effect(EffectType.attack, 2, 999));
								t.addOrUpdateEffect(new Effect(EffectType.health, 2, 999));
								if (!Bank.isServer())
									Animation.createStaticAnimation(t, "BLOOD", 3, 500);
							} else {
								t.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
								t.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
							}
						}
					}
				}
			}
			if (this == enterVoid) {
				target.addOrUpdateEffect(new Effect(EffectType.attack, 2, 999));
				target.addOrUpdateEffect(new Effect(EffectType.health, 2, 999));
				Animation.createStaticAnimation(target, "VOIDCAST", 2.75f, 1000);
				for (int i = 0; i < 3; ++i) {
					for (int j = 0; j < 3; ++j) {
						int x1 = x - 1 + i, y1 = y - 1 + j;
						Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
						if (t != null && t != target) {
							t.hurt(5, null);
							Animation.createStaticAnimation(t, "VOID", 1.8f, 800);
						}
					}
				}
			}
			if (this == obliterate) {
				Unit u = Display.currentScreen.getUnitOnTile(x, y, roomID);
				if (u != null) {
					if (!u.getIsCommander()) {
						u.hurt(u.getHealth(), u);
						Animation.createStaticAnimation(u, "FLASH", 3, 800);
					}
				}
			}
			if (this == blessingPower) {
				Unit u = Display.currentScreen.getUnitOnTile(x, y, roomID);
				if (u != null) {
					u.addEffect(new Effect(EffectType.attack, 5, 999));
					u.addEffect(new Effect(EffectType.health, 5, 999));
					Animation.createStaticAnimation(u, "HOLY", 2, 800);
				}
			}
			if (this == haste) {
				Unit u = Display.currentScreen.getUnitOnTile(x, y, roomID);
				if (u != null) {
					u.addEffect(new Effect(EffectType.speed, 3, 999));
					Animation.createStaticAnimation(u, "CHARGE", 2, 800);
				}
			}
			if (this == blessingJustice) {
				if (Bank.isServer()) {
					PlayerMP user = Bank.server.getClients(roomID).get(owner);
					user.baseGold+=2;
				} else {
					if (Util.clientID == owner) {
						Util.gold+=2;
						Util.maxGold+=2;
					}
				}
			}
			if (this == empathy) {
				Unit u = Display.currentScreen.getUnitOnTile(x, y, roomID);
				if (u != null) {
					u.getAbilities().add(CardAbility.guilt);
					Animation.createStaticAnimation(u, "HOLY", 2, 1000);
					Animation.createStaticAnimation(u, "FLASH", 2, 800);
				}
			}
			if (this == recharge) {
				Unit u = Display.currentScreen.getUnitOnTile(x, y, roomID);
				if (u != null) {
					u.setEnergy1(u.getEnergy() + 3);
					Animation.createStaticAnimation(u, "CHARGE", 2, 800);
				}
			}
			if (this == energypotion) {
				Unit u = Display.currentScreen.getUnitOnTile(x, y, roomID);
				if (u != null) {
					u.setEnergy1(u.getEnergy() + 2);
					Animation.createStaticAnimation(u, "CHARGE", 2, 800);
				}
			}
			if (this == healthpotion) {
				Unit u = Display.currentScreen.getUnitOnTile(x, y, roomID);
				if (u != null) {
					u.heal(4);
					Animation.createStaticAnimation(u, "HEAL", 2, 800);
				}
			}
			if (this == bluepotion) {
				if (Bank.isServer()) {
					Bank.server.drawCard(Bank.server.getClients(roomID).get(owner), -1, 1);
				}
				Animation.createStaticAnimation(Display.currentScreen.mousePoint.x, Display.currentScreen.mousePoint.y,
						"BLUELIGHT", 2, 800);
			}
			if (this == rebirth) {
				if (Bank.isServer()) {
					for (Unit u : Bank.server.getUnits(roomID)) {
						if (!u.getIsCommander())
							u.getAbilities().add(CardAbility.evolve);
						u.addEnergy1(2);
					}
				}
				if (Bank.isClient()) {
					for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
						Unit u = Display.currentScreen.objects.get(i);
						if (u != null) {
							if (!u.getIsCommander())
								u.getAbilities().add(CardAbility.evolve);
							Animation.createStaticAnimation(u, "LEAVES", 2, 700);
							u.addEnergy1(2);
						}
					}
				}
			}
			if (this == sogarokswill) {
				if (Bank.isServer()) {
					for (Unit u : Bank.server.getUnits(roomID)) {
						if (!u.getIsCommander() && u.ownerID == owner)
							u.getAbilities().add(CardAbility.evolve);
					}
				}
				if (Bank.isClient()) {
					for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
						Unit u = Display.currentScreen.objects.get(i);
						if (u != null) {
							if (!u.getIsCommander() && u.ownerID == owner) {
								u.getAbilities().add(CardAbility.evolve);
								Animation.createStaticAnimation(u, "EARTH", 2, 850);
								Animation.createStaticAnimation(u, "GALE", 1, 600);
							}
						}
					}
				}
			}
			if (this == imbue) {
				if (Bank.isServer()) {
					for (Unit u : Bank.server.getUnits(roomID)) {
						if (u.ownerID == owner) {
							u.addEnergy1(1);
						}
					}
				}
				if (Bank.isClient()) {
					for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
						Unit u = Display.currentScreen.objects.get(i);
						if (u != null) {
							if (u.ownerID == owner) {
								u.addEnergy1(1);
								Util.drawParticleLine(Display.currentScreen.mousePoint, u.getPoint(), 16, true,
										"BLOOM");
								Animation.createStaticAnimation(u, "GALE", 2, 600);
							}
						}
					}
				}
			}
			/*
			 * if(this == lunarblessing){ if(Bank.isServer()){ for(Unit u :
			 * Bank.server.getUnits(roomID)){ if(u.ownerID == owner){ u.addEffect(new
			 * Effect(EffectType.attack, 1, 1)); u.heal(2); } } }
			 * if(Bank.isClient()){ for(int i = 0; i <
			 * Display.currentScreen.objects.size(); ++i){ Unit u =
			 * Display.currentScreen.objects.get(i); if(u!=null){ if(u.ownerID
			 * == owner){ u.addEffect(new Effect(EffectType.attack, 1, 1));
			 * u.heal(2); int s = Grid.tileSize*2;
			 * Animation.createStaticAnimation(u, "GALE", 2, 300);
			 * Animation.createStaticAnimation(u, "MOON", 3, 1500); } } } } }
			 */
			if (this == charge) {
				if (Bank.server != null) {
					for (Unit u : Bank.server.getUnits(roomID)) {
						if (u.ownerID == owner) {
							u.addEffect(new Effect(EffectType.speed, 2, 1));
						}
					}
				}
				if (Bank.isClient()) {
					for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
						Unit u = Display.currentScreen.objects.get(i);
						if (u != null) {
							if (u.ownerID == owner) {
								u.addEffect(new Effect(EffectType.speed, 2, 1));
								Util.drawParticleLine(Display.currentScreen.mousePoint, u.getPoint(), 16, true, "FIRE");
								Animation.createStaticAnimation(u, "CHARGE", 2, 800);
							}
						}
					}
				}
			}
			if (this == frost) {
				if (Bank.server != null) {
					for (Unit u : Bank.server.getUnits(roomID)) {
						u.addEffect(new Effect(EffectType.chill, 1, 2));
					}
				}
				if (Bank.isClient()) {
					for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
						Unit u = Display.currentScreen.objects.get(i);
						if (u != null) {
							u.addEffect(new Effect(EffectType.chill, 1, 2));
							Util.drawParticleLine(Display.currentScreen.mousePoint, u.getPoint(), 8, true, "SHATTER");
							Util.drawParticleLine(Display.currentScreen.mousePoint, u.getPoint(), 8, true, "BUBBLE");
							Animation.createStaticAnimation(u, "FREEZE", 2, 1600);
						}
					}
				}
			}
			if (this == coldsnap) {
				for (int i = 0; i < 3; ++i) {
					for (int j = 0; j < 3; ++j) {
						int x1 = x - 1 + i, y1 = y - 1 + j;
						Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
						if (t != null) {
							if (t.hasEffect(EffectType.chill))
								t.hurt(2, null);
							else
								t.addEffect(new Effect(EffectType.chill, 1, 2));
							Animation.createStaticAnimation(t, "FREEZE", 2, 800);
						}
					}
				}
			}

			if (this == hornEkeziel) {
				for (int i = 0; i < 3; ++i) {
					for (int j = 0; j < 3; ++j) {
						int x1 = x - 1 + i, y1 = y - 1 + j;
						Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
						if (t != null) {
							t.getAbilities().add(CardAbility.heal1);
							Animation.createStaticAnimation(t, "BOON", 2, 900);
							Animation.createStaticAnimation(t, "LEAVES", 2, 1300);
						}
					}
				}
			}
		}
		Util.resolveUnits(roomID);
		return false;
	}

	public boolean isSpell() {
		return this instanceof Card && (this instanceof UnitTemplate == false)
				&& (this instanceof CardBoardstate == false) && (this instanceof CardEquipment == false);
	}

	public boolean isUnit() {
		return this instanceof UnitTemplate;
	}

	public boolean isBoardState() {
		return this instanceof CardBoardstate;
	}

	public boolean isEquipment() {
		return this instanceof CardEquipment;
	}

	public void parseComm(String comm, int owner, int guid, int x, int y, int roomID) {
		if (this == ravenseye) {
			String[] dats = comm.split(",");
			int spacing = 32, dur = 4000;
			for (int i = 0; i < 3; ++i) {
				int cw = Properties.width / 4;
				int ch = (int) (cw * 1.5f);
				Animation anim = new Animation(
						Properties.width / 2 + cw / 2 - ((cw + spacing) * 3) / 2 + (cw + spacing) * i,
						Properties.height / 2, dur, Animation.TAG_CARD);
				anim.setData(Integer.parseInt(dats[i]));
				anim.width = cw;
				anim.height = ch;
				Display.currentScreen.particles.add(anim);
			}
		}
		if (this == deathlotus) {
			Unit target = Display.currentScreen.getUnitFromGUID(Integer.parseInt(comm.split(",")[0]), roomID);
			int dmg = Integer.parseInt(comm.split(",")[1]);
			target.hurt(dmg, target);
			Animation.createStaticAnimation(target, "SKULLGAS", 2, 1000);
			Animation.createStaticAnimation(target, "GALE", 1.75f, 750);
		}
		if (this == upheaval) {
			Unit target = Display.currentScreen.getUnitFromGUID(Integer.parseInt(comm.split(",")[0]), roomID);
			int dmg = Integer.parseInt(comm.split(",")[1]);
			Animation.createStaticAnimation(target, "EARTH", 3, 1000);
			target.hurt(dmg, target);
			if (target.getHealth() <= 0) {
				Animation.createStaticAnimation(target, "EXPLOSION", 3, 400);
				for (int i = 0; i < 3; ++i) {
					for (int j = 0; j < 3; ++j) {
						int x1 = x - 1 + i, y1 = y - 1 + j;
						Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
						if (t != null) {
							t.hurt(dmg, null);
							Animation.createStaticAnimation(t, "EARTH", 2, 500);
						}
					}
				}
			}
		}
		if (this == voidcurse) {
			Unit target = Display.currentScreen.getUnitFromGUID(Integer.parseInt(comm.split(",")[0]), roomID);
			if (target != null) {
				Animation.createStaticAnimation(target, "VOIDCAST", 3, 1000);
				Animation.createStaticAnimation(target, "VOID", 2, 750);
				UnitTemplate tid = (UnitTemplate) Card.all[(Integer.parseInt(comm.split(",")[1]))];
				target.setTemplate(tid);
				target.resummon();
			}
		}
		if (this == curse) {
			Unit target = Display.currentScreen.getUnitFromGUID(Integer.parseInt(comm.split(",")[0]), roomID);
			if (target != null) {
				Animation.createStaticAnimation(target, "SKULLGAS", 2, 1000);
				UnitTemplate tid = (UnitTemplate) Card.all[(Integer.parseInt(comm.split(",")[1]))];
				target.setTemplate(tid);
				target.resummon();
			}
		}
		if (this == rageofkholos) {
			Unit u = Display.currentScreen.getUnitFromGUID(Integer.parseInt(comm), roomID);
			if (u != null) {
				u.hurt(6, null);
				Animation anim = new Animation(Util.boardOffsetX + Grid.tileSize * u.posX,
						Util.boardOffsetY + Grid.tileSize * u.posY, 1200, Animation.TAG_KHOLOS);
				Display.currentScreen.particles.add(anim);
				Animation.createStaticAnimation(u, "VOID", 2.5f, 1350);
			}
		}
		if (this == train || this == train1 || this == revolution) {
			Unit tar = Display.currentScreen.getUnitFromGUID(Integer.parseInt(comm.split(",")[0]), roomID);
			if (tar != null) {
				CardAbility ab = CardAbility.all[Integer.parseInt(comm.split(",")[1])];
				CardAbility ab1 = null;
				if (comm.split(",").length == 3) {
					ab1 = CardAbility.all[Integer.parseInt(comm.split(",")[2])];
				}
				if (ab != null)
					tar.getAbilities().add(ab);
				if (ab1 != null)
					tar.getAbilities().add(ab1);
				Animation.createStaticAnimation(tar, "RUNE", 2, 900);
			}
		}
	}

	public void sendComm(String dat, int owner, int guid, int i, int j, int roomID) {
		Packet10CardComm pkt = new Packet10CardComm(
				"2" + "%" + this.getId() + "%" + owner + "%" + guid + "%" + i + "%" + j + "%" + dat);
		pkt.write(Bank.server.getRoom(roomID));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public void draw(Image art, Graphics g, int bx, int by, int w, int h, boolean b) {
		int x = bx, y = by;
		boolean deckbuild = false;
		if(Display.currentScreen instanceof PanelDeckbuilder){
			deckbuild = true;
		}
		int borderCount = 5;
		int borderCountH = (int) (5 * 1.5);
		int borderW = w / borderCount, borderH = borderW / 3 * 2;
		int borderWY = h / borderCountH, borderHY = borderWY / 3 * 2;
		UnitTemplate unit = null;
		if (this instanceof UnitTemplate)
			unit = (UnitTemplate) this;
		g.setFont(Util.cooldownFont);
		g.setColor(Color.WHITE);
		int ts = w / 10;
		Font f = new Font(Font.MONOSPACED, Font.BOLD, ts);
		
		//g.drawImage(art, x, y + h / 5 + borderH / 2, w, w, null);
		int artEndY = y + h / 3 * 2;
		int artStartY = y + h / 5 + borderH / 3 * 2;
		g.setColor(Color.WHITE);
		g.fillRect(x, artStartY, w, artEndY-artStartY);
		g.drawImage(art, x, artStartY, w, artEndY-artStartY, null);
		g.setColor(Color.GRAY);
		g.fillRect(x, y, w, h / 5);
		// g.setColor(Util.SEMIDARK_GRAY);
		g.fillRect(x, y + h / 3 * 2, w, h / 3);
		// g.drawImage(Bank.setbg, x, y+h/3*2, w, h/3, null);
		int sih = h / 4;
		int siw = sih / 3 * 4;
		g.drawImage(set.getIcon(), x + w / 2 - siw / 2, y + h / 3 * 2 + h / 6 - sih / 2, siw, sih, null);
		if(this.isCraftable()){
			for (int i = 0; i < borderCount; ++i) {
				g.drawImage(Bank.cardBorderTop, x + i * borderW, y, borderW, borderH, null);
				g.drawImage(Bank.cardBorderBottom, x + i * borderW, y + h - borderH, borderW, borderH, null);

				g.drawImage(Bank.cardBorderTop, x + i * borderW, y + h / 3 * 2, borderW, borderH / 3 * 2, null);
				g.drawImage(Bank.cardBorderBottom, x + i * borderW, y + h / 5, borderW, borderH / 3 * 2, null);
			}
			for (int i = 0; i < borderCountH; ++i) {
				g.drawImage(Bank.cardBorderLeft, x, y + i * borderWY, borderHY / 3 * 2, borderWY, null);
				g.drawImage(Bank.cardBorderRight, x + w - (borderHY / 3 * 2), y + i * borderWY, borderHY / 3 * 2, borderWY,
						null);
			}
			g.drawImage(Bank.cornerTopleft, x, y, borderW, borderW, null);
			g.drawImage(Bank.cornerTopright, x + w - borderW, y, borderW, borderW, null);
			g.drawImage(Bank.cornerBottomleft, x, y + h - borderW, borderW, borderW, null);
			g.drawImage(Bank.cornerBottomright, x + w - borderW, y + h - borderW, borderW, borderW, null);
		}else{
			if(this.isBasic()){
				for (int i = 0; i < borderCount; ++i) {
					g.drawImage(Bank.cardBorderTop1, x + i * borderW, y, borderW, borderH, null);
					g.drawImage(Bank.cardBorderBottom1, x + i * borderW, y + h - borderH, borderW, borderH, null);
	
					g.drawImage(Bank.cardBorderTop1, x + i * borderW, y + h / 3 * 2, borderW, borderH / 3 * 2, null);
					g.drawImage(Bank.cardBorderBottom1, x + i * borderW, y + h / 5, borderW, borderH / 3 * 2, null);
				}
				for (int i = 0; i < borderCountH; ++i) {
					g.drawImage(Bank.cardBorderLeft1, x, y + i * borderWY, borderHY / 3 * 2, borderWY, null);
					g.drawImage(Bank.cardBorderRight1, x + w - (borderHY / 3 * 2), y + i * borderWY, borderHY / 3 * 2, borderWY,
							null);
				}
				g.drawImage(Bank.cornerTopleft1, x, y, borderW, borderW, null);
				g.drawImage(Bank.cornerTopright1, x + w - borderW, y, borderW, borderW, null);
				g.drawImage(Bank.cornerBottomleft1, x, y + h - borderW, borderW, borderW, null);
				g.drawImage(Bank.cornerBottomright1, x + w - borderW, y + h - borderW, borderW, borderW, null);
			}else{
				for (int i = 0; i < borderCount; ++i) {
					g.drawImage(Bank.cardBorderTop2, x + i * borderW, y, borderW, borderH, null);
					g.drawImage(Bank.cardBorderBottom2, x + i * borderW, y + h - borderH, borderW, borderH, null);
	
					g.drawImage(Bank.cardBorderTop2, x + i * borderW, y + h / 3 * 2, borderW, borderH / 3 * 2, null);
					g.drawImage(Bank.cardBorderBottom2, x + i * borderW, y + h / 5, borderW, borderH / 3 * 2, null);
				}
				for (int i = 0; i < borderCountH; ++i) {
					g.drawImage(Bank.cardBorderLeft2, x, y + i * borderWY, borderHY / 3 * 2, borderWY, null);
					g.drawImage(Bank.cardBorderRight2, x + w - (borderHY / 3 * 2), y + i * borderWY, borderHY / 3 * 2, borderWY,
							null);
				}
				g.drawImage(Bank.cornerTopleft2, x, y, borderW, borderW, null);
				g.drawImage(Bank.cornerTopright2, x + w - borderW, y, borderW, borderW, null);
				g.drawImage(Bank.cornerBottomleft2, x, y + h - borderW, borderW, borderW, null);
				g.drawImage(Bank.cornerBottomright2, x + w - borderW, y + h - borderW, borderW, borderW, null);
			}
		}
		
		if(deckbuild &! b && this.isCraftable()){
			if(Util.collection[id] <= 0){
				g.setColor(Util.transparent_dark);
				g.fillRect(x, y, w, h);
				int lockWidth = w / 3 * 2;
				int lockHeight = lockWidth / 5 * 4;
				g.drawImage(Bank.iconLock, x + w / 2 - lockWidth / 2, y + h / 2 - lockHeight / 2, lockWidth, lockHeight, null);
			}
		}
		if (this instanceof UnitTemplate && b) {
			int energyW = w / 10;
			if (unit.getAbilities().size() > 0) {
				int ttindex = 0;
				for (int i = 0; i < unit.getAbilities().size(); ++i) {
					CardAbility ab = unit.getAbilities().get(i);
					if (ab != CardAbility.move && ab != CardAbility.attack)
						++ttindex;
					Rectangle button = new Rectangle(x + borderWY, y + h / 3 * 2 + i * 30 + 3 + borderH,
							w - (borderWY * 2), 30);
					g.drawImage(Bank.button, button.x, button.y, button.width, button.height, null);
					g.drawImage(ab.getImg(), button.x, button.y, button.height, button.height, null);
					g.setFont(Util.spellDesc);
					g.setColor(Color.WHITE);
					int sw = g.getFontMetrics().stringWidth(ab.getName());
					for (int j = 0; j < ab.getCost(); ++j) {
						Image costImg = Bank.energyFull;
						if (ab.getCostType() == CardAbility.COST_TYPE_MANA) {
							costImg = Bank.starFull;
						}
						if (ab.getCostType() == CardAbility.COST_TYPE_RESOURCE) {
							costImg = Bank.ball;
						}
						if (ab.getCostType() == CardAbility.COST_TYPE_SPECIAL) {
							costImg = Bank.holynova;
						}
						g.drawImage(costImg, button.height + button.x + sw + (j) * energyW / 2 + 20,
								button.y + button.height / 2 - energyW / 2, energyW, energyW, null);
					}
					g.drawString(ab.getName(), button.x + button.height, button.y + button.height / 3 * 2);
					if (ab.getTargetType() == CardAbility.TARGET_PASSIVE) {
						g.setColor(Util.transparent);
						g.fillRect(button.x, button.y, button.width, button.height);
					}
					Bank.drawOutlineOut(g, button.x, button.y, button.width, button.height,
							(ab.getTargetType() == CardAbility.TARGET_PASSIVE ? Color.YELLOW : (Color.BLACK)), 3);
					if (ab != CardAbility.move && ab != CardAbility.attack)
						ab.drawTooltip(g, w, x, y + 100 * (ttindex - 1));
				}
			}
		}
		if (this instanceof CardEquipment && b) {
			CardEquipment equip = (CardEquipment) this;
			int energyW = w / 10;
			if (equip.getAbilities().size() > 0) {
				int ttindex = 1;
				for (int i = 0; i < equip.getAbilities().size(); ++i) {
					CardAbility ab = equip.getAbilities().get(i);
					if (ab != CardAbility.move && ab != CardAbility.attack)
						++ttindex;
					Rectangle button = new Rectangle(x + borderWY, y + h / 3 * 2 + i * 30 + 3 + borderH,
							w - (borderWY * 2), 30);
					g.drawImage(Bank.button, button.x, button.y, button.width, button.height, null);
					g.drawImage(ab.getImg(), button.x, button.y, button.height, button.height, null);
					g.setFont(Util.spellDesc);
					g.setColor(Color.WHITE);
					int sw = g.getFontMetrics().stringWidth(ab.getName());
					for (int j = 0; j < ab.getCost(); ++j) {
						Image costImg = Bank.energyFull;
						if (ab.getCostType() == CardAbility.COST_TYPE_MANA) {
							costImg = Bank.starFull;
						}
						if (ab.getCostType() == CardAbility.COST_TYPE_RESOURCE) {
							costImg = Bank.ball;
						}
						if (ab.getCostType() == CardAbility.COST_TYPE_SPECIAL) {
							costImg = Bank.holynova;
						}
						g.drawImage(costImg, button.height + button.x + sw + (j) * energyW / 2 + 20,
								button.y + button.height / 2 - energyW / 2, energyW, energyW, null);
					}
					g.drawString(ab.getName(), button.x + button.height, button.y + button.height / 3 * 2);
					if (ab.getTargetType() == CardAbility.TARGET_PASSIVE) {
						g.setColor(Util.transparent);
						g.fillRect(button.x, button.y, button.width, button.height);
					}
					Bank.drawOutlineOut(g, button.x, button.y, button.width, button.height,
							(ab.getTargetType() == CardAbility.TARGET_PASSIVE ? Color.YELLOW : (Color.BLACK)), 3);
					if (ab != CardAbility.move && ab != CardAbility.attack)
						ab.drawTooltip(g, w, x, y + 100 * (ttindex - 1));
				}
			}
		}
		g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, w / 10));
		int cw = w / 3;
		g.drawImage(Bank.starFull, x + w / 2 - cw / 2, y - cw / 4 * 3, cw, cw, null);

		g.setColor(Color.BLACK);
		g.drawString(this.getName(), x + w / 2 - g.getFontMetrics().stringWidth(this.getName()) / 2 - 1,
				y + h / 7 + 4 - 1);
		g.setColor(Color.WHITE);
		g.drawString(this.getName(), x + w / 2 - g.getFontMetrics().stringWidth(this.getName()) / 2, y + h / 7 + 4);

		g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, w / 5));
		g.setColor(Color.BLACK);
		g.drawString(getCost() + "", x + w / 2 - g.getFontMetrics().stringWidth(getCost() + "") / 2 - 2, y - 2);
		g.setColor(Color.WHITE);
		g.drawString(getCost() + "", x + w / 2 - g.getFontMetrics().stringWidth(getCost() + "") / 2, y);
		
		int runeSize = h / 6;
		if(this.costRed > 0){
			g.drawImage(Bank.starRed, x + w - runeSize / 3, y + runeSize * 0 + runeSize / 2, runeSize, runeSize, null);
			g.setColor(Color.BLACK);
			g.drawString(costRed+"", x + w + runeSize / 5 - g.getFontMetrics().stringWidth(costRed+"")/2 - 2, y + runeSize * 0 + runeSize / 4 * 3 - 2 + runeSize / 2);
			g.setColor(Color.WHITE);
			g.drawString(costRed+"", x + w + runeSize / 5 - g.getFontMetrics().stringWidth(costRed+"")/2, y + runeSize * 0 + runeSize / 4 * 3 + runeSize / 2);
		}
		if(this.costGreen > 0){
			g.drawImage(Bank.starGreen, x + w - runeSize / 3, y + runeSize * 1 + runeSize / 2, runeSize, runeSize, null);
			g.setColor(Color.BLACK);
			g.drawString(costGreen+"", x + w + runeSize / 5 - g.getFontMetrics().stringWidth(costGreen+"")/2 - 2, y + runeSize * 1 + runeSize / 4 * 3 - 2 + runeSize / 2);
			g.setColor(Color.WHITE);
			g.drawString(costGreen+"", x + w + runeSize / 5 - g.getFontMetrics().stringWidth(costGreen+"")/2, y + runeSize * 1 + runeSize / 4 * 3 + runeSize / 2);
		}
		if(this.costBlue > 0){
			g.drawImage(Bank.starBlue, x + w - runeSize / 3, y + runeSize * 2 + runeSize / 2, runeSize, runeSize, null);
			g.setColor(Color.BLACK);
			g.drawString(costBlue+"", x + w + runeSize / 5 - g.getFontMetrics().stringWidth(costBlue+"")/2 - 2, y + runeSize * 2 + runeSize / 4 * 3 - 2 + runeSize / 2);
			g.setColor(Color.WHITE);
			g.drawString(costBlue+"", x + w + runeSize / 5 - g.getFontMetrics().stringWidth(costBlue+"")/2, y + runeSize * 2 + runeSize / 4 * 3 + runeSize / 2);
		}
		
		if (this instanceof UnitTemplate) {
			int iconW = w / 4;
			g.drawImage(Bank.iconHealth, x - iconW / 3, y + h - iconW + iconW / 4, iconW, iconW, null);
			g.drawImage(Bank.iconSpeed, x - iconW / 3, y + h - iconW + iconW / 4 - iconW, iconW, iconW, null);
			g.drawImage(Bank.iconAttack, x + w - iconW / 3 * 2, y + h - iconW + iconW / 4, iconW, iconW, null);
			g.drawImage(Bank.slot_head, x - iconW / 3, y + h - iconW + iconW / 4, iconW, iconW, null);
			g.drawImage(Bank.slot_foot, x - iconW / 3, y + h - iconW + iconW / 4 - iconW, iconW, iconW, null);
			g.drawImage(Bank.slot_weapon, x + w - iconW / 3 * 2, y + h - iconW + iconW / 4, iconW, iconW, null);
			Bank.drawOvalOutlineOut(g, x - iconW / 3, y + h - iconW + iconW / 4, iconW, iconW, Color.BLACK, 2);
			Bank.drawOvalOutlineOut(g, x + w - iconW / 3 * 2, y + h - iconW + iconW / 4, iconW, iconW, Color.BLACK, 2);
			int health = unit.getHealth();
			int attack = unit.getAttack();
			int speed = unit.getSpeed();
			g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, w / 4));
			g.setColor(Color.BLACK);
			g.drawString(health + "", x - iconW / 3 + iconW / 2 - g.getFontMetrics().stringWidth(health + "") / 2 - 3,
					y + h - 2);
			g.setColor(Color.WHITE);
			g.drawString(health + "", x - iconW / 3 + iconW / 2 - g.getFontMetrics().stringWidth(health + "") / 2,
					y + h);

			g.setColor(Color.BLACK);
			g.drawString(attack + "",
					x + w - iconW / 3 * 2 + iconW / 2 - g.getFontMetrics().stringWidth(attack + "") / 2 - 3, y + h - 2);
			g.setColor(Color.WHITE);
			g.drawString(attack + "",
					x + w - iconW / 3 * 2 + iconW / 2 - g.getFontMetrics().stringWidth(attack + "") / 2, y + h);

			g.setColor(Color.BLACK);
			g.drawString((int) speed + "",
					x - iconW / 3 + iconW / 2 - g.getFontMetrics().stringWidth((int) speed + "") / 2 - 3,
					y + h - 2 - iconW);
			g.setColor(Color.WHITE);
			g.drawString((int) speed + "",
					x - iconW / 3 + iconW / 2 - g.getFontMetrics().stringWidth((int) speed + "") / 2, y + h - iconW);
			int rank = ((UnitTemplate) this).getRank();
			if (rank != UnitTemplate.RANK_COMMANDER) {
				int bannerW = w - w / 4;
				int bannerH = (int) (bannerW * 0.25);
				g.drawImage(
						this instanceof StructureTemplate ? Bank.bannerStructure
								: (rank == UnitTemplate.RANK_COMMON ? Bank.bannerCommon
										: rank == UnitTemplate.RANK_UNCOMMON ? Bank.bannerUncommon
												: rank == UnitTemplate.RANK_MYTHIC ? Bank.bannerMythic
														: Bank.bannerRegal),
						x + w / 2 - bannerW / 2, y + (h / 3 * 2) - bannerH / 4, bannerW, bannerH, null);
			}
			if (((UnitTemplate) this).getFamily() != Family.none) {
				g.setFont(Util.descFont);
				String fam = ((UnitTemplate) this).getFamily().getName();
				int famW = g.getFontMetrics().stringWidth(fam) + 40;
				g.drawImage(Bank.button, x + w / 2 - famW / 2, y + h - (famW / 4 / 2), famW, famW / 4, null);
				g.setColor(Color.BLACK);
				g.drawString(fam, x + w / 2 - g.getFontMetrics().stringWidth(fam) / 2, y + h + famW / 20);
			}
			g.setFont(Util.descTitleFont);
			g.setColor(Color.WHITE);
			int ind = 0;
			if (!b) {
				for (CardAbility ca : unit.getAbilities()) {
					String abname = ca.getName();
					int sw = g.getFontMetrics().stringWidth(abname);
					if (ca != CardAbility.move && ca != CardAbility.attack) {
						g.drawString(abname, x + w / 2 - sw / 2, y + h / 4 * 3 + 14 * ind + 10);
						++ind;
					}
				}
			}
		} else {
			if (this.isSpell()) {
				if (b)
					this.drawTooltip(g, w, bx, by);
				int bannerW = w - w / 4;
				int bannerH = (int) (bannerW * 0.25);
				g.drawImage(Bank.bannerSpell, x + w / 2 - bannerW / 2, y + (h / 3 * 2) - bannerH / 4, bannerW, bannerH,
						null);

				ts = w / 18;
				f = new Font(Font.MONOSPACED, Font.PLAIN, ts);
				g.setFont(f);
				g.setColor(Color.WHITE);
				if (getText() != null & !b) {
					int stdmax = (w / 4 * 3) / g.getFontMetrics().stringWidth(" ");
					int linemax = stdmax;
					ArrayList<String> nameparts = new ArrayList<String>();
					for (int i = 0; i < getText().length(); i += linemax) {
						nameparts.add(getText().substring(i, Math.min(i + linemax, getText().length())));
					}
					for (int i = 0; i < nameparts.size(); ++i) {
						g.drawString(nameparts.get(i),
								x + w / 2 - (g.getFontMetrics().stringWidth(nameparts.get(i))) / 2,
								y + h / 4 * 3 + ts * i + ts / 5 * 4);
					}
				}
			}
			if (this.isBoardState()) {
				if (b)
					this.drawTooltip(g, w, bx, by);
				int bannerW = w - w / 4;
				int bannerH = (int) (bannerW * 0.25);
				g.drawImage(Bank.bannerBoardstate, x + w / 2 - bannerW / 2, y + (h / 3 * 2) - bannerH / 4, bannerW,
						bannerH, null);

				g.setFont(Util.smallDescFont);
				g.setColor(Color.WHITE);
				if (getText() != null & !b) {
					int stdmax = (w / 4 * 3) / g.getFontMetrics().stringWidth(" ");
					int linemax = stdmax;
					ArrayList<String> nameparts = new ArrayList<String>();
					for (int i = 0; i < getText().length(); i += linemax) {
						nameparts.add(getText().substring(i, Math.min(i + linemax, getText().length())));
					}
					for (int i = 0; i < nameparts.size(); ++i) {
						g.drawString(nameparts.get(i),
								x + w / 2 - (g.getFontMetrics().stringWidth(nameparts.get(i))) / 2,
								y + h / 4 * 3 + 12 * i + 10);
					}
				}
			}
			if (this.isEquipment()) {
				CardEquipment equip = (CardEquipment) this;
				int iconW = w / 4;
				g.drawImage(Bank.iconDurability, x - iconW / 3, y + h - iconW + iconW / 4, iconW, iconW, null);
				g.drawImage(Bank.slot_head, x - iconW / 3, y + h - iconW + iconW / 4, iconW, iconW, null);
				Bank.drawOvalOutlineOut(g, x - iconW / 3, y + h - iconW + iconW / 4, iconW, iconW, Color.BLACK, 2);
				int health = equip.getDurability();
				g.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, w / 4));
				g.setColor(Color.BLACK);
				g.drawString(health + "",
						x - iconW / 3 + iconW / 2 - g.getFontMetrics().stringWidth(health + "") / 2 - 3, y + h - 2);
				g.setColor(Color.WHITE);
				g.drawString(health + "", x - iconW / 3 + iconW / 2 - g.getFontMetrics().stringWidth(health + "") / 2,
						y + h);
				if (b)
					this.drawTooltip(g, w, bx, by);
				int bannerW = w - w / 4;
				int bannerH = (int) (bannerW * 0.25);
				g.drawImage(Bank.bannerEquipment, x + w / 2 - bannerW / 2, y + (h / 3 * 2) - bannerH / 4, bannerW,
						bannerH, null);

				g.setFont(Util.descTitleFont);
				g.setColor(Color.WHITE);
				int ind = 0;
				if (!b) {
					for (CardAbility ca : equip.getAbilities()) {
						String abname = ca.getName();
						int sw = g.getFontMetrics().stringWidth(abname);
						if (ca != CardAbility.move && ca != CardAbility.attack && ca != CardAbility.unequip) {
							g.drawString(abname, x + w / 2 - sw / 2, y + h / 4 * 3 + 14 * ind + 10);
							++ind;
						}
					}

					g.setFont(Util.smallDescFont);
					g.setColor(Color.WHITE);
					if (getText() != null) {
						int stdmax = (w / 4 * 3) / g.getFontMetrics().stringWidth(" ");
						int linemax = stdmax;
						ArrayList<String> nameparts = new ArrayList<String>();
						for (int i = 0; i < getText().length(); i += linemax) {
							nameparts.add(getText().substring(i, Math.min(i + linemax, getText().length())));
						}
						for (int i = 0; i < nameparts.size(); ++i) {
							g.drawString(nameparts.get(i),
									x + w / 2 - (g.getFontMetrics().stringWidth(nameparts.get(i))) / 2,
									y + h / 4 * 3 + 12 * i + (14 * ind + 10));
						}
					}
				}
			}
		}
		if (CardList.getListForCard(this) != CardList.common) {
			int iconSize = w / 4;
			g.drawImage(CardList.getListForCard(this).getIcon(), x + w - iconSize / 2 - iconSize / 8, y - iconSize / 2,
					iconSize, iconSize, null);
		}
	}

	public void drawTooltip(Graphics g, int cw, int bx, int y) {
		if (getText() != null) {
			g.setFont(Util.descFont);
			int w = g.getFontMetrics().stringWidth(getText()) + 30, h = 100;
			if (g.getFontMetrics(Util.cooldownBold).stringWidth("Effect:") > w)
				w = g.getFontMetrics(Util.cooldownBold).stringWidth("Effect:");
			int x = ((bx - w < Properties.width / 10 && cw > 0) ? (bx + cw) : bx - w);
			g.drawImage(Bank.paper, x, y, w, h, null);
			g.setFont(Util.cooldownBold);
			g.setColor(Color.BLACK);
			g.drawString("Effect:", x + 10 - 2, y + 30 - 1);
			g.setColor(Color.WHITE);
			g.drawString("Effect:", x + 10, y + 30);
			g.setColor(Color.BLACK);
			g.setFont(Util.descFont);
			g.drawString(getText(), x + 10, y + 50);
			g.setFont(new Font(Font.SERIF, Font.ITALIC, 14));
			Bank.drawOutlineOut(g, x, y, w, h, Color.BLACK, 3);
		}
	}

	public int getCost() {
		return cost;
	}
	
	public int getRedCost() {
		return costRed;
	}
	
	public int getBlueCost() {
		return costBlue;
	}
	
	public int getGreenCost() {
		return costGreen;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getArt() {
		return (art == null ? "ball.png" : art);
	}

	public boolean isListable() {
		return listable;
	}

	public Card setListable(boolean listable) {
		this.listable = listable;
		return this;
	}

	public boolean availableFor(Card hero) {
		CardList list = CardList.getListForCard(this);
		if (list == CardList.getListForCard(hero) || list == CardList.common)
			return true;
		return false;
	}

	public void setArt(String art) {
		this.art = art;
	}

	public String getText() {
		return text;
	}

	public boolean isStructure() {
		return this instanceof StructureTemplate;
	}

	public CardSet getSet() {
		return set;
	}

	public void setSet(CardSet set) {
		this.set = set;
	}

	public boolean isBasic() {
		return basic;
	}

	public Card setBasic(boolean basic) {
		this.basic = basic;
		return this;
	}

	public boolean isCraftable() {
		if(this.isBasic())return false;
		return isListable();
	}
}
