package org.author.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

public class CardAbility {

	static int TARGET_UNIT = 1, TARGET_AREA = 2, TARGET_PASSIVE = 3, TARGET_SELF = 4;
	private int id, cost, targetType, range;
	static byte COST_TYPE_ENERGY = 1, COST_TYPE_MANA = 2, COST_TYPE_RESOURCE = 3, COST_TYPE_SPECIAL = 4;
	private byte costType = COST_TYPE_ENERGY;
	private String name, desc = "No description.";
	private Image img;
	private boolean unbound = false;
	private ArrayList<Point> coordinates = new ArrayList<Point>();
	private Card linked = null;

	static CardAbility[] all = new CardAbility[500];

	public CardAbility(int id, Image img, String name, int cost, int targetType, int range) {
		this.setId(id);
		this.setImg(img);
		this.setName(name);
		this.setCost(cost);
		this.setTargetType(targetType);
		this.setRange(range);
		all[id] = this;
	}

	public static final CardAbility upgrade1 = new CardAbility(0, Bank.slot_weapon, "Upgrade Blades", 1, TARGET_SELF, 1)
			.setDesc("Gain +1 Attack.").setUnbound(true);
	public static final CardAbility upgrade2 = new CardAbility(1, Bank.slot_head, "Upgrade Armour", 1, TARGET_SELF, 1)
			.setDesc("Gain +1 Health.").setUnbound(true);
	public static final CardAbility upgrade3 = new CardAbility(2, Bank.slot_foot, "Upgrade Jets", 1, TARGET_SELF, 1)
			.setDesc("Gain +1 Speed.").setUnbound(true);
	public static final CardAbility scurry = new CardAbility(3, Bank.flamehelm, "Scurry", 1, TARGET_SELF, 1)
			.setUnbound(false);
	public static final CardAbility move = new CardAbility(4, Bank.slot_foot, "Walk", 1, TARGET_AREA, 1)
			.setUnbound(false);
	public static final CardAbility attack = new CardAbility(5, Bank.slot_weapon, "Attack", 1, TARGET_UNIT, 1)
			.setUnbound(false);
	public static final CardAbility shadowburn = new CardAbility(6, Bank.particleSkull_0, "Shadowburn", 2, TARGET_UNIT,
			4).setDesc("Deals 3 damage to the target.").setUnbound(true);
	public static final CardAbility leap = new CardAbility(7, Bank.particleSkull_0, "Leap", 2, TARGET_AREA, 7)
			.setDesc("Leaps to the selected tile.").setUnbound(false);
	public static final CardAbility energetic = new CardAbility(8, Bank.iconSpeed, "Energetic", 0, TARGET_PASSIVE, 0)
			.setDesc("Grants 1 energy at the end of every turn.").setUnbound(false);
	public static final CardAbility hatch = new CardAbility(9, Bank.flamering, "Hatch", 0, TARGET_PASSIVE, 0)
			.setDesc("Adds a Spiderling to your hand at the end of every turn.").setUnbound(false)
			.linkCard(Card.spiderling);
	public static final CardAbility heal = new CardAbility(10, Bank.grassyrock, "Mend", 1, TARGET_UNIT, 4)
			.setDesc("Heals a unit for 1 Health.").setUnbound(false);
	public static final CardAbility raise = new CardAbility(11, Bank.flamering, "Raise Dead", 0, TARGET_PASSIVE, 0)
			.setDesc("Add a Waste Walker to your hand when a unit dies.").setUnbound(false).linkCard(Card.wastewalker);
	public static final CardAbility soar = new CardAbility(12, Bank.flamering, "Soar", 0, TARGET_AREA, 10)
			.setDesc("Flies to the selected tile.").setUnbound(false);
	public static final CardAbility scorch = new CardAbility(13, Bank.particleFire_0, "Scorch", 2, TARGET_UNIT, 8)
			.setDesc("Deals 2 damage to a unit.").setUnbound(false);
	public static final CardAbility dragonwrath = new CardAbility(14, Bank.flamering, "Dragon's Wrath", 1, TARGET_UNIT,
			8).setDesc("Deals 2 damage to a unit.").setUnbound(true);
	public static final CardAbility chill = new CardAbility(15, Bank.freezespell, "Chill", 1, TARGET_UNIT, 5)
			.setDesc("Reduces a unit's speed to 1 until your next turn.").setUnbound(true);
	public static final CardAbility energize = new CardAbility(16, Bank.iconSpeed, "Energize", 1, TARGET_UNIT, 4)
			.setDesc("Grants a unit 1 energy.").setUnbound(true);
	public static final CardAbility enchant = new CardAbility(17, Bank.flamering, "Enchant Weapon", 2, TARGET_UNIT, 5)
			.setDesc("Grants a unit +1 Attack.").setUnbound(true);
	public static final CardAbility blink = new CardAbility(18, Bank.flamering, "Blink", 0, TARGET_AREA, 4)
			.setDesc("Teleport to the target location.").setUnbound(false);
	public static final CardAbility summonWarrior = new CardAbility(19, Bank.flamering, "Summon Guardian", 1,
			TARGET_SELF, 0).setDesc("Add a Lifeless Guardian to your hand.").setUnbound(false)
					.linkCard(Card.lifelessWarrior);
	public static final CardAbility bloodlust = new CardAbility(20, Bank.flamering, "Bloodlust", 1, TARGET_SELF, 0)
			.setDesc("Allows the unit to attack again this turn.").setUnbound(true);
	public static final CardAbility battlerage = new CardAbility(21, Bank.flamering, "Battle Rage", 1, TARGET_PASSIVE,
			0).setDesc("Gain +1 Attack each time this unit attacks.").setUnbound(true);
	public static final CardAbility cannon = new CardAbility(22, Bank.ball, "Cannon Blast", 2, TARGET_AREA, 5)
			.setDesc("Deals 1 damage to units in a 3x3 radius.").setUnbound(false);
	public static final CardAbility voidclaw = new CardAbility(23, Bank.ball, "Spectral Swipe", 1, TARGET_UNIT, 1)
			.setDesc("Deals 2 damage to a unit.").setUnbound(true);
	public static final CardAbility ascend = new CardAbility(24, Bank.flamering, "Fiery Ascension", 1, TARGET_SELF, 1)
			.setDesc("Gain (+1/+1).").setUnbound(true);
	public static final CardAbility execute = new CardAbility(25, Bank.ball, "Execute", 1, TARGET_UNIT, 1)
			.setDesc("Deals 2 damage to a damaged unit.");
	public static final CardAbility behead = new CardAbility(26, Bank.ball, "Behead", 0, TARGET_UNIT, 1)
			.setDesc("Destroys a unit on 1 hp.").setUnbound(true);
	public static final CardAbility heal1 = new CardAbility(27, Bank.grassyrock, "Heal", 1, TARGET_UNIT, 4)
			.setDesc("Heals a unit for 2 Health.").setUnbound(false);
	public static final CardAbility heal2 = new CardAbility(28, Bank.grassyrock, "Touch of Life", 1, TARGET_UNIT, 4)
			.setDesc("Heals a unit for 3 Health.").setUnbound(false);
	public static final CardAbility dragonfist = new CardAbility(29, Bank.fist, "Dragon Fist", 1, TARGET_UNIT, 2)
			.setDesc("Deals 3 damage then heals target for 4.").setUnbound(false);
	public static final CardAbility monkeyfist = new CardAbility(30, Bank.fist, "Monkey Fist", 1, TARGET_UNIT, 2)
			.setDesc("Deals 1 damage and heals self for 1.").setUnbound(true);
	public static final CardAbility tigerfist = new CardAbility(31, Bank.fist, "Tiger Fist", 1, TARGET_UNIT, 2)
			.setDesc("Deals 1 damage and reduces target's attack by 1.").setUnbound(false);
	public static final CardAbility cauterize = new CardAbility(32, Bank.grassyrock, "Cauterize", 1, TARGET_UNIT, 4)
			.setDesc("Heals a unit for 3 Health.").setUnbound(false);
	public static final CardAbility abolish = new CardAbility(33, Bank.grassyrock, "Abolish", 1, TARGET_UNIT, 4)
			.setDesc("Removes all effects from a unit.").setUnbound(false);
	public static final CardAbility cannonball = new CardAbility(34, Bank.ball, "Cannon Shot", 2, TARGET_UNIT, 8)
			.setDesc("Deals 1 damage on a tile.").setUnbound(false);
	public static final CardAbility cannonball1 = new CardAbility(35, Bank.ball, "Bombard", 5, TARGET_AREA, 8)
			.setDesc("Deals 2 damage in a 3x3 radius.").setUnbound(true);
	public static final CardAbility phoenix = new CardAbility(36, Bank.flamering, "Phoenix Form", 6, TARGET_SELF, 0)
			.setDesc("Transforms this unit into a phoenix.").setUnbound(false).linkCard(Card.phoenix);
	public static final CardAbility jadibrad = new CardAbility(37, Bank.holystrike, "Holy Strike", 1, TARGET_UNIT, 2)
			.setCostType(COST_TYPE_MANA).setDesc("Heal yourself for 2 and deal 1 damage to an enemy.")
			.setUnbound(false);
	public static final CardAbility buildWall = new CardAbility(38, Bank.brick, "Build: Wall", 1, TARGET_AREA, 6)
			.setCostType(COST_TYPE_MANA).setDesc("Construct a WALL on the selected tile.").setUnbound(true);
	public static final CardAbility buildCannon = new CardAbility(39, Bank.ball, "Build: Cannon Tower", 3, TARGET_AREA,
			6).setCostType(COST_TYPE_MANA).setDesc("Construct a CANNON on the selected tile.").setUnbound(false);
	public static final CardAbility upgradeToOutpost = new CardAbility(40, Bank.holystrike, "Upgrade to: Outpost", 8,
			TARGET_SELF, 0).setDesc("Upgrade the tent to an OUTPOST.").setUnbound(false);
	public static final CardAbility upgradeToStronghold = new CardAbility(41, Bank.holystrike, "Upgrade to: Stronghold",
			8, TARGET_SELF, 0).setDesc("Upgrade the outpost to a STRONGHOLD.").setUnbound(false);
	public static final CardAbility minion = new CardAbility(42, Bank.holystrike, "Minion", 0, TARGET_PASSIVE, 0)
			.setDesc("This unit does not contribute to your max unit count.").setUnbound(false);
	public static final CardAbility sacrifice = new CardAbility(43, Bank.bloodbeam, "Blood Ritual", 0, TARGET_UNIT, 5)
			.setDesc("Destroys this unit and heals the target for its ATTACK.");
	public static final CardAbility infinityburst = new CardAbility(44, Bank.infinityburst, "Infinity Burst", 2,
			TARGET_UNIT, 6).setCostType(COST_TYPE_MANA).setDesc("Deals 1 damage to a unit.").setUnbound(true);
	public static final CardAbility siphon = new CardAbility(45, Bank.siphon, "Siphon", 2, TARGET_UNIT, 6)
			.setCostType(COST_TYPE_MANA).setDesc("Siphons a unit's energy.");
	public static final CardAbility voidgift = new CardAbility(46, Bank.voidgift, "Gift of the Void", 2, TARGET_UNIT, 3)
			.setCostType(COST_TYPE_MANA).setDesc("Grant the target [BLINK].").setUnbound(true);
	public static final CardAbility cleave = new CardAbility(47, Bank.cleave, "Cleave", 2, TARGET_UNIT, 2)
			.setCostType(COST_TYPE_MANA).setDesc("Deals 2 damage to a unit, and 1 to those above and below it.")
			.setUnbound(false);
	public static final CardAbility web = new CardAbility(48, Bank.web, "Web", 2, TARGET_UNIT, 3)
			.setCostType(COST_TYPE_MANA)
			.setDesc("Reduce a unit's speed by 3 until your next turn. If it is an insect, give it (+1/+1).")
			.setUnbound(true);
	public static final CardAbility bash = new CardAbility(49, Bank.bash, "Bash", 2, TARGET_UNIT, 2)
			.setCostType(COST_TYPE_MANA).setDesc("Deals 1 damage to a unit and reduces its energy by 2.")
			.setUnbound(true);
	public static final CardAbility consume = new CardAbility(50, Bank.consume, "Consume", 0, TARGET_UNIT, 2)
			.setCostType(COST_TYPE_MANA)
			.setDesc("Consume a friendly unit with 1+ ENERGY. You gain their ATTACK this turn.").setUnbound(true);
	public static final CardAbility meditate = new CardAbility(51, Bank.consume, "Meditate", 3, TARGET_SELF, 0)
			.setCostType(COST_TYPE_MANA).setDesc("Draw a card.");
	public static final CardAbility dive = new CardAbility(52, Bank.dive, "Diving Strike", 2, TARGET_AREA, 5)
			.setCostType(COST_TYPE_MANA)
			.setDesc("Travel at a right angle, dealing 1 damage to every unit you pass through.");
	public static final CardAbility poison = new CardAbility(53, Bank.minion, "Envenom", 2, TARGET_UNIT, 4)
			.setDesc("Grants a unit +2 attack this turn.");
	public static final CardAbility charger = new CardAbility(54, Bank.arrowRight, "Charger", 0, TARGET_PASSIVE, 0)
			.setDesc("This unit has ATTACK equal to its SPEED.");
	public static final CardAbility stomp = new CardAbility(55, Bank.consume, "C.U.R.B Stomp", 2, TARGET_SELF, 0)
			.setDesc("Deals 2 damage to units around this unit.");
	public static final CardAbility powershield = new CardAbility(56, Bank.defend, "Power Shield XL-30", 2, TARGET_UNIT,
			5).setDesc("Protects a unit, causing all damage they take to be reduced to 1 until the end of the turn.");
	public static final CardAbility combust = new CardAbility(58, Bank.flamering, "Combustion", 1, TARGET_SELF, 0)
			.setDesc("Deals 1 damage to this unit and units around it.");
	public static final CardAbility litFuse = new CardAbility(59, Bank.flamering, "Lit Fuse", 0, TARGET_PASSIVE, 0)
			.setDesc("Take 1 damage at the end of each turn. If this unit dies, deal 3 damage in a 3x3 radius.");
	public static final CardAbility bloodCharge = new CardAbility(60, Bank.flamering, "Blood Charge", 2, TARGET_UNIT, 4)
			.setCostType(COST_TYPE_MANA).setDesc("Grants a unit +1 SPEED.").setUnbound(true);
	public static final CardAbility limbripper = new CardAbility(61, Bank.holystrike, "Limbripper", 1, TARGET_SELF, 2)
			.setCostType(COST_TYPE_MANA).setDesc("Deals 1 damage around caster. Gain +1 speed this turn for each hit.");
	public static final CardAbility wildAxe = new CardAbility(62, Bank.loading, "Wild Axe", 2, TARGET_UNIT, 2)
			.setCostType(COST_TYPE_MANA).setDesc("Deal 3 damage to a unit and yourself.");
	public static final CardAbility mastersCall = new CardAbility(63, Bank.loading, "Master's Call", 1, TARGET_SELF, 2)
			.setDesc("Adds a Swine to your hand.").setUnbound(true);
	public static final CardAbility supercharge = new CardAbility(64, Bank.loading, "Supercharge", 2, TARGET_UNIT, 4)
			.setCostType(COST_TYPE_MANA).setDesc("Give a unit 2 energy.").setUnbound(true);
	public static final CardAbility overload = new CardAbility(65, Bank.loading, "Overload", 1, TARGET_UNIT, 4)
			.setCostType(COST_TYPE_MANA).setDesc("Deal damage to a unit equal to its energy.").setUnbound(false);
	public static final CardAbility salvage = new CardAbility(66, Bank.loading, "Salvage", 1, TARGET_UNIT, 3)
			.setCostType(COST_TYPE_MANA).setDesc("Deal 1 damage to a mech and draw a card. If it dies, draw another.")
			.setUnbound(false);
	public static final CardAbility mimic = new CardAbility(67, Bank.loading, "Mimic", 4, TARGET_UNIT, 3)
			.setDesc("Become a copy of the target. Fails on heroes.").setUnbound(false);
	public static final CardAbility spelllock = new CardAbility(68, Bank.loading, "Spell Lock", 4, TARGET_UNIT, 3)
			.setDesc("Destroy a unit's abilities.").setUnbound(false);
	public static final CardAbility spellsteal = new CardAbility(69, Bank.loading, "Spellsteal", 3, TARGET_UNIT, 3)
			.setDesc("Steal a unit's abilities.").setUnbound(false);
	public static final CardAbility devour = new CardAbility(70, Bank.loading, "Devour Magic", 3, TARGET_UNIT, 3)
			.setDesc("Steal a unit's abilities and deal 1 damage for each stolen.").setUnbound(false);
	public static final CardAbility study = new CardAbility(71, Bank.loading, "Study Magic", 2, TARGET_UNIT, 3)
			.setDesc("Gain the abilities of the target unit.").setUnbound(false);
	public static final CardAbility wintersembrace = new CardAbility(72, Bank.particleBubble_0, "Winter's Embrace", 0,
			TARGET_PASSIVE, 0).setDesc("Gain (+2/+2/+2) when chilled.").setUnbound(false);
	public static final CardAbility siphonessence = new CardAbility(73, Bank.holynova, "Feed", 0, TARGET_PASSIVE, 0)
			.setDesc("When played, gain +1 energy for all enemy units.").setUnbound(false);
	public static final CardAbility spiritnova = new CardAbility(74, Bank.flamering, "Spirit Nova", 2, TARGET_AREA, 5)
			.setDesc("Deal 1 damage in a 3x3 radius.").setUnbound(true);
	public static final CardAbility formation = new CardAbility(75, Bank.flamering, "Formation", 0, TARGET_PASSIVE, 0)
			.setDesc("Destroys allies around this unit and gains their stats.").setUnbound(false);
	public static final CardAbility cryptthief = new CardAbility(76, Bank.flamering, "Grave Raid", 0, TARGET_PASSIVE, 0)
			.setDesc("When a unit dies, gain (+1/+1).").setUnbound(false);
	public static final CardAbility evolve = new CardAbility(77, Bank.flamering, "Evolve", 1, TARGET_SELF, 0)
			.setDesc("Transform into a unit with cost equal to  your energy.").setUnbound(false);
	public static final CardAbility plague = new CardAbility(78, Bank.flamering, "Kholosian Plague", 1, TARGET_PASSIVE,
			0).setDesc("Take 5 damage at the end of each turn. Enemies hit are infected.").setUnbound(false);
	public static final CardAbility snipe = new CardAbility(79, Bank.flamering, "Snipe", 1, TARGET_UNIT, 6)
			.setDesc("Deals 1 damage to a unit.").setUnbound(true);
	public static final CardAbility mount = new CardAbility(80, Bank.flamering, "Mount", 0, TARGET_UNIT, 3)
			.setDesc("Destroy this unit and grant the target 4 speed.").setUnbound(false);
	public static final CardAbility mercenary = new CardAbility(81, Bank.flamering, "Mercenary", 0, TARGET_PASSIVE, 0)
			.setDesc("Change owner at the end of each turn.").setUnbound(false);
	public static final CardAbility gamble = new CardAbility(82, Bank.flamering, "Gamble", 1, TARGET_SELF, 1)
			.setDesc("50% chance to either gain (+2/+2) or die.").setUnbound(true);
	public static final CardAbility erupt = new CardAbility(83, Bank.flamering, "Erupt", 0, TARGET_PASSIVE, 0)
			.setDesc("At the end of each turn, deal 6 damage to a random unit.").setUnbound(false);
	public static final CardAbility wildmagic = new CardAbility(84, Bank.flamering, "Wild Magic", 0, TARGET_PASSIVE, 0)
			.setDesc("At the end of each turn, transform into a random unit.").setUnbound(false);
	public static final CardAbility slay = new CardAbility(85, Bank.flamering, "Bold Strike", 1, TARGET_UNIT, 4)
			.setDesc("Destroy a unit with 5 or more ATTACK.").setUnbound(false);
	public static final CardAbility fireblade = new CardAbility(86, Bank.flamering, "Searing Blade", 1, TARGET_UNIT, 3)
			.setDesc("Grant a unit +3 Attack this turn.").setUnbound(false);
	public static final CardAbility recruit = new CardAbility(87, Bank.flamehelm, "Enlist Patron", 0, TARGET_PASSIVE, 0)
			.setDesc("50% chance to draw a Drunk Reveler each turn.").setUnbound(false);
	public static final CardAbility drink = new CardAbility(88, Bank.flamehelm, "Drink Ale", 1, TARGET_SELF, 0)
			.setDesc("Gain 1 attack and lose 1 speed.").setUnbound(false);
	public static final CardAbility share = new CardAbility(89, Bank.flamehelm, "Share a Drink", 1, TARGET_UNIT, 3)
			.setDesc("Grant the target [Drink Ale].").setUnbound(false);
	public static final CardAbility guilt = new CardAbility(90, Bank.holynova, "Burdening Guilt", 4, TARGET_SELF, 0)
			.setDesc("When a unit dies, take 2 damage. Cast this spell to remove it.").setUnbound(false);
	// public static final CardAbility blindshot = new CardAbility(91,
	// Bank.holynova, "Blind Strike", 2, TARGET_SELF, 2).setDesc("Deal 2 damage
	// to a random enemy.").setUnbound(false);
	// public static final CardAbility juggle = new CardAbility(92,
	// Bank.holynova, "Juggle Bombs", 0, TARGET_PASSIVE, 0).setDesc("When
	// played, deal 1 damage randomly, 4 times.").setUnbound(false);
	// public static final CardAbility moonbeam = new CardAbility(93,
	// Bank.holynova, "Moonbeam", 1, TARGET_SELF, 0).setDesc("Deal 3 damage to a
	// random unit.").setUnbound(true);
	public static final CardAbility conjure = new CardAbility(94, Bank.holynova, "Conjure", 4, TARGET_SELF, 0)
			.setDesc("Add a random card to your hand.").setUnbound(false);
	// public static final CardAbility siege = new CardAbility(95,
	// Bank.holynova, "Siege", 4, TARGET_SELF, 0).setDesc("Conjure a random
	// card.").setUnbound(false);
	public static final CardAbility learn = new CardAbility(96, Bank.holynova, "Learn", 2, TARGET_SELF, 0)
			.setDesc("Gain a random ability.").setUnbound(false);
	public static final CardAbility epidemic = new CardAbility(97, Bank.holynova, "Epidemic", 0, TARGET_PASSIVE, 0)
			.setDesc("When this unit dies, deal 2 damage to all other units.").setUnbound(false);
	public static final CardAbility reanimate = new CardAbility(98, Bank.holynova, "Reanimate", 0, TARGET_PASSIVE, 0)
			.setDesc("When a unit dies, add it to your hand. Fails on 0-cost units.").setUnbound(false);
	public static final CardAbility mindburst = new CardAbility(99, Bank.holynova, "Mind Burst", 0, TARGET_UNIT, 6)
			.setDesc("Deal 1 damage to a unit for each ability you have.").setUnbound(false);
	public static final CardAbility tinker = new CardAbility(100, Bank.holynova, "Tinker", 2, TARGET_SELF, 6)
			.setDesc("Add a mech to your hand.").setUnbound(true);
	public static final CardAbility teach = new CardAbility(101, Bank.holynova, "Teach", 2, TARGET_UNIT, 6)
			.setDesc("The target gains your abilities.").setUnbound(true);
	public static final CardAbility curse = new CardAbility(102, Bank.holynova, "Curse", 2, TARGET_UNIT, 6)
			.setDesc("Reduces a unit's attack to 1 until your next turn.").setUnbound(true);
	public static final CardAbility strike = new CardAbility(103, Bank.holynova, "Defensive Strike", 2, TARGET_UNIT, 1)
			.setDesc("Deals your attack damage to a unit.").setUnbound(false);
	public static final CardAbility liberate = new CardAbility(104, Bank.holynova, "Liberate Soul", 1, TARGET_UNIT, 4)
			.setDesc("Removes effects/conditions from a unit.").setUnbound(true);
	public static final CardAbility inspire = new CardAbility(105, Bank.holynova, "Inspire Troops", 2, TARGET_SELF, 6)
			.setDesc("Units around this unit gain +1 attack for 1 turn.").setUnbound(true);
	public static final CardAbility wall = new CardAbility(106, Bank.flamering, "Solid Wall", 0, TARGET_PASSIVE, 6)
			.setDesc("Enemy units cannot move, attack or cast through this unit.").setUnbound(true);
	public static final CardAbility growing = new CardAbility(107, Bank.flamering, "Growing", 0, TARGET_PASSIVE, 6)
			.setDesc("This unit is growing. It will gain 1 health per turn.");
	public static final CardAbility feed = new CardAbility(108, Bank.flamering, "Force Feed", 2, TARGET_UNIT, 3)
			.setDesc("Destroys this unit and heals the target for this unit's health.");
	public static final CardAbility toxic = new CardAbility(109, Bank.flamering, "Toxic Burst", 2, TARGET_UNIT, 3)
			.setDesc("Destroys this unit deals its health as damage.");
	public static final CardAbility firepower = new CardAbility(110, Bank.flamering, "Blaze", 2, TARGET_UNIT, 3)
			.setDesc("Destroy this unit and grant energy equal to its health.");
	public static final CardAbility plant = new CardAbility(111, Bank.flamering, "Plant", 2, TARGET_AREA, 5)
			.setDesc("Grows a random plant on a tile.");
	public static final CardAbility germinate = new CardAbility(112, Bank.flamering, "Germinate", 2, TARGET_SELF, 6)
			.setDesc("Adds a copy of this card to your hand.");
	public static final CardAbility censure = new CardAbility(113, Bank.flamering, "Censure", 2, TARGET_SELF, 6)
			.setDesc("Adds a copy of this card to your hand.");
	public static final CardAbility hoofsmash = new CardAbility(114, Bank.bash, "Hoof Smash", 1, TARGET_UNIT, 3)
			.setCostType(COST_TYPE_MANA).setDesc("Weakens and slows a unit until your next turn. Destroys walls.");
	public static final CardAbility fortify = new CardAbility(115, Bank.flamering, "Bolster", 2, TARGET_UNIT, 2)
			.setCostType(COST_TYPE_MANA).setDesc("Gives a unit +1 Health.");
	public static final CardAbility warbanner = new CardAbility(116, Bank.flamering, "War Banner", 2, TARGET_AREA, 4)
			.setCostType(COST_TYPE_MANA).setDesc("Places a war banner.");
	public static final CardAbility trample = new CardAbility(117, Bank.flamering, "Trample", 2, TARGET_UNIT, 3)
			.setCostType(COST_TYPE_MANA)
			.setDesc("Deals damage equal to your speed, and reduces your speed by 2 until your next turn.");
	public static final CardAbility confusion = new CardAbility(118, Bank.loading, "Confusion", 0, TARGET_PASSIVE, 3)
			.setDesc("Randomizes your stats at the end of every turn.");
	public static final CardAbility disarm = new CardAbility(119, Bank.loading, "Disarm", 1, TARGET_UNIT, 3)
			.setDesc("Reduces a unit's attack by 1 this turn.");
	public static final CardAbility smite = new CardAbility(120, Bank.flamering, "Smite", 1, TARGET_UNIT, 4)
			.setDesc("Deals 2 damage to a unit. Damage doubled on undead.");
	public static final CardAbility fury = new CardAbility(121, Bank.flamering, "Unbridled Fury", 1, TARGET_PASSIVE, 4)
			.setDesc("Surrounding units take your attack damage at the start of your turn.");
	public static final CardAbility bodyslam = new CardAbility(122, Bank.flamering, "Body Slam", 2, TARGET_UNIT, 3)
			.setDesc("Deals your current health, as damage, to the target.");
	public static final CardAbility deathwish = new CardAbility(123, Bank.flamering, "Death Wish", 1, TARGET_SELF, 3)
			.setDesc("Destroy this unit and draw a card.");
	public static final CardAbility persuade = new CardAbility(124, Bank.flamering, "Rough Persuasion", 3, TARGET_UNIT,
			3).setDesc("The target unit switches sides, and this unit suffers its attack damage.");
	public static final CardAbility reshuffle = new CardAbility(125, Bank.flamering, "Shuffle", 2, TARGET_UNIT, 3)
			.setDesc("Re-shuffle a unit's stats.");
	public static final CardAbility shadows = new CardAbility(126, Bank.flamering, "Shadow Debt", 0, TARGET_PASSIVE, 0)
			.setDesc("Deal 1 damage to this unit's owner at the end of each turn.");
	public static final CardAbility harvest = new CardAbility(127, Bank.flamering, "Corpse Harvest", 0, TARGET_PASSIVE,
			0).setDesc("When a unit dies, heal for 1 and draw a card.");
	public static final CardAbility evermore = new CardAbility(128, Bank.flamering, "Listen to Evermore", 2,
			TARGET_SELF, 0).setDesc("Heal yourself for 30.");
	public static final CardAbility meme = new CardAbility(129, Bank.flamering, "Meme! Haha!", 1, TARGET_UNIT, 4)
			.setDesc("Deals 3 damage to a unit and give it [Confusion].");
	public static final CardAbility kill = new CardAbility(130, Bank.flamering, "Murder the Baby", 3, TARGET_SELF, 4)
			.setDesc("Any units with 3 or less attack take 4 damage.");
	public static final CardAbility lootfilled = new CardAbility(131, Bank.flamering, "Loot Filled", 0, TARGET_PASSIVE,
			0).setDesc("When destroyed by a minion, that minion's owner will recieve a loot card.");
	public static final CardAbility shadowgate = new CardAbility(132, Bank.flamering, "Call of Jados", 0,
			TARGET_PASSIVE, 0)
					.setDesc("Spawn a random demon on a random tile and take 1 damage at the start of your turn.");
	public static final CardAbility barrier = new CardAbility(133, Bank.flamering, "Dark Barrier", 0, TARGET_PASSIVE, 0)
			.setDesc("Gain 1 HP for all enemy units when played.");
	public static final CardAbility bite = new CardAbility(134, Bank.flamering, "Cursed Bite", 2, TARGET_UNIT, 2)
			.setDesc("Deal 2 damage. If the target survives, transform them into a " + Family.werewolf.getName()
					+ ". Grant a random ability if already " + Family.werewolf.getName() + ".");
	public static final CardAbility howl = new CardAbility(135, Bank.flamering, "Nighthunter's Howl", 2, TARGET_SELF, 0)
			.setDesc("Grant all " + Family.werewolf.getName() + " +1 ATTACK this turn.");
	public static final CardAbility feast = new CardAbility(136, Bank.flamering, "Feast", 2, TARGET_UNIT, 2)
			.setDesc("Deal 1 damage. If the target dies, fully heal.");
	public static final CardAbility hunt = new CardAbility(137, Bank.flamering, "Hunt", 1, TARGET_SELF, 0)
			.setDesc("Take 1 damage and gain 1 ATTACK.").setUnbound(true);
	public static final CardAbility pack = new CardAbility(138, Bank.flamering, "Fury of the Pack", 2, TARGET_SELF, 0)
			.setDesc("Restore 2 HP to all " + Family.werewolf.getName() + ".");
	public static final CardAbility lunge = new CardAbility(139, Bank.flamering, "Claw Lunge", 2, TARGET_AREA, 8)
			.setDesc("Leap to a tile. Deal 1 damage to surrounding units.").setUnbound(true);
	public static final CardAbility legion = new CardAbility(140, Bank.flamering, "Broken Legion", 2, TARGET_SELF, 0)
			.setDesc("Gain +1 SPEED and 1 ENERGY for every " + Family.werewolf.getName() + " on the board.");
	public static final CardAbility oblivion = new CardAbility(141, Bank.flamering, "Oblivion!", 1, TARGET_SELF, 0)
			.setDesc("Add a [Rage of Kholos] card to your hand.").setUnbound(true);
	public static final CardAbility protect = new CardAbility(142, Bank.flamering, "Protect", 0, TARGET_UNIT, 4)
			.setDesc("PROTECT a unit until your next turn, redirecting damage from them to you.").setUnbound(true);
	public static final CardAbility auraGuardian = new CardAbility(143, Bank.flamering, "Guardian Aura", 0,
			TARGET_PASSIVE, 0).setDesc("Surrounding units are PROTECTED by this one.");
	public static final CardAbility auraShadow = new CardAbility(144, Bank.flamering, "Shadow Aura", 0, TARGET_PASSIVE,
			0).setDesc("Deals 1 damage to surrounding units at the end of each turn.");
	public static final CardAbility farmFeed = new CardAbility(145, Bank.defend, "Food Supply", 2, TARGET_UNIT, 3)
			.setDesc("Restores 2 health to a unit.");
	public static final CardAbility recruitMilitia = new CardAbility(146, Bank.defend, "Train: Militia", 2, TARGET_SELF,
			0).setDesc("Adds a 2/3/3 Militia to your hands that costs (3).");
	public static final CardAbility recruitKnight = new CardAbility(147, Bank.defend, "Train: Knight", 2, TARGET_SELF,
			0).setDesc("Adds a 4/5/4 Knight to your hand that costs (5).");
	public static final CardAbility cannonShot = new CardAbility(148, Bank.ball, "Cannon Shot", 2, TARGET_UNIT, 10)
			.setDesc("Deals damage to a unit equal to the caster's attack.");
	public static final CardAbility structurePassiveGraveyard = new CardAbility(149, Bank.defend, "Animate Dead", 2,
			TARGET_PASSIVE, 3).setDesc("Units that die within 3 tiles will be returned to their owners' hand.");
	public static final CardAbility tentBuild = new CardAbility(150, Bank.defend, "Fortify Campsite", 1, TARGET_SELF, 0)
			.setCostType(COST_TYPE_MANA).setUnbound(true)
			.setDesc("Fortify your camp site, giving it +5 HP. When it reaches 30HP it will upgrade into an Outpost.");
	public static final CardAbility outpostBuild = new CardAbility(151, Bank.defend, "Fortify Outpost", 1, TARGET_SELF,
			0).setCostType(COST_TYPE_MANA).setUnbound(true).setDesc(
					"Fortify your outpost, giving it +3 HP. When it reaches 50HP it will upgrade into a Fortress.");
	public static final CardAbility upgradeCannon = new CardAbility(152, Bank.slot_weapon, "Greater Cannonballs", 2,
			TARGET_SELF, 0).setCostType(COST_TYPE_MANA).setUnbound(true).setDesc("Grant this unit +1 Attack. (Max 5)");
	public static final CardAbility tier2Structure = new CardAbility(153, Bank.slot_weapon, "Greater Cannonballs", 2,
			TARGET_SELF, 0).setCostType(COST_TYPE_MANA).setUnbound(true).setDesc("Grant this unit +1 Attack. (Max 5)");
	public static final CardAbility krattHut = new CardAbility(154, Bank.slot_weapon, "The Swarm", 2, TARGET_SELF, 0)
			.setUnbound(false)
			.setDesc("Add a random Kratt unit to your hand and give all Kratt units on the board +1 HP.");
	public static final CardAbility abyssaltemple = new CardAbility(155, Bank.slot_weapon, "Hell's Forge", 2,
			TARGET_SELF, 0).setUnbound(false).setDesc("Give all demons on the board +1/+1.");
	public static final CardAbility omazvillage = new CardAbility(156, Bank.slot_weapon, "Drums of War", 2, TARGET_SELF,
			0).setUnbound(false).setDesc("Give all friendly units +1 Speed.");
	public static final CardAbility dwarvenSmithy = new CardAbility(157, Bank.slot_weapon, "Fortifications", 2,
			TARGET_SELF, 0).setUnbound(false).setDesc("Give all friendly structures +1/+2.");
	public static final CardAbility deepmurk = new CardAbility(158, Bank.slot_weapon, "Call of Deepmurk", 7,
			TARGET_SELF, 0).setUnbound(false).setDesc("Add an 'Rlg'Ybar' to your hand.");
	public static final CardAbility chapel = new CardAbility(159, Bank.slot_weapon, "Might of the Gods", 2, TARGET_SELF,
			0).setUnbound(false).setDesc("Deal 3 damage to all demon and undead units.");
	public static final CardAbility grove = new CardAbility(160, Bank.slot_weapon, "Enchanted Healing", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("All healing effects are doubled.");
	public static final CardAbility forbiddenMagic = new CardAbility(161, Bank.particleSkull_5, "Forbidden Magic", 2,
			TARGET_UNIT, 4).setUnbound(false)
					.setDesc("Give another ELDRITCH unit (+3/+3/+1), or deal 3 damage to a non-Eldritch unit.");
	public static final CardAbility eldritchRitual = new CardAbility(162, Bank.particleSkull_5, "Eldritch Ritual", 3,
			TARGET_SELF, 0).setUnbound(false).setDesc("Add a random ELDRITCH unit to your hand.");
	public static final CardAbility eldritchCorruption = new CardAbility(163, Bank.particleSkull_5, "Fatal Corruption",
			4, TARGET_SELF, 0).setUnbound(false).setDesc("Transform this unit into a random Eldritch unit.");
	public static final CardAbility eldritchSummon = new CardAbility(164, Bank.particleSkull_5, "Awakening", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, a random Eldritch unit from your hand will also be summoned.");
	public static final CardAbility eldritchPower = new CardAbility(165, Bank.particleSkull_5, "From the Depths", 0,
			TARGET_SELF, 0).setUnbound(false).setDesc(
					"Gain the abilities of all ELDRITCH units in your hand. 'On play' effects are triggered. This ability is destroyed at the end of turn.");
	public static final CardAbility eldritchSlam = new CardAbility(166, Bank.particleSkull_5, "Tentacle Slam", 2,
			TARGET_UNIT, 3).setUnbound(false)
					.setDesc("Deal damage to a unit equal to the amount of Eldritch units in your hand.");
	public static final CardAbility eldritchHeal = new CardAbility(167, Bank.particleSkull_5, "Eldritch Mending", 2,
			TARGET_UNIT, 4).setUnbound(false).setDesc("Deal 3 damage to a unit and then heal it for 7.");
	public static final CardAbility eldritchSwirl = new CardAbility(168, Bank.particleSkull_5, "Churning Seas", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc(
					"At the end of each turn, deal 2 damage to all units. For each death, give all ELDRITCH units (+1/+2).");
	public static final CardAbility eldritchTorment = new CardAbility(169, Bank.particleSkull_5, "Spreading Corruption",
			2, TARGET_SELF, 0).setUnbound(true).setDesc("Add an 'Infested Noble' to your hand.");
	public static final CardAbility eldritchMorph = new CardAbility(170, Bank.particleSkull_5,
			"Eldritch Transformation", 2, TARGET_UNIT, 5).setUnbound(false)
					.setDesc("Transform a unit into a random ELDRITCH unit.");
	public static final CardAbility eldritchCrackle = new CardAbility(171, Bank.particleSkull_5, "Dark Surge", 0,
			TARGET_UNIT, 5).setUnbound(true).setDesc("Deal 1 damage to a target unit and this unit.");
	public static final CardAbility eldritchEat = new CardAbility(172, Bank.particleSkull_5, "Deep Sea Hunter", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("Deals 4 damage to all ELDRITCH units when played.");
	public static final CardAbility eldritchLeg = new CardAbility(173, Bank.particleSkull_5, "God of the Depths", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, this unit will gain (+3/+3) for each ELDRITCH unit on the board.");
	public static final CardAbility eldritchLegion = new CardAbility(174, Bank.particleSkull_5, "Deepmurk Legion", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When an ELDRITCH unit dies, gain (+2/+2).");
	public static final CardAbility eldritchParasite = new CardAbility(175, Bank.particleSkull_5, "Eldritch Parasite",
			0, TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("Whoever kills this unit will gain a random Eldritch unit.");
	public static final CardAbility goldMine = new CardAbility(176, Bank.coin, "Gold Mine", 0, TARGET_PASSIVE, 0)
			.setUnbound(false)
			.setDesc("Upon taking damage from a surrounding unit, the attacker will gain a [Sack of Riches].");
	public static final CardAbility banditCamp = new CardAbility(177, Bank.particleSkull_5, "Bandit Camp", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When struck by a hero, the attacker will draw a card.");
	public static final CardAbility magicWell = new CardAbility(178, Bank.particleSkull_5, "Magic Spring", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("At the end of each turn, surrounding units will gain +1/+1.");
	public static final CardAbility tree = new CardAbility(179, Bank.coin, "Tree", 0, TARGET_PASSIVE, 0)
			.setUnbound(false)
			.setDesc("Upon taking damage from a surrounding unit, the attacker will gain a [Pile of Lumber].");
	public static final CardAbility plantTree = new CardAbility(180, Bank.coin, "Plant Tree", 2, TARGET_AREA, 4)
			.setUnbound(false).setDesc("Places a Study Oak on the selected tile.");
	public static final CardAbility woodchipper = new CardAbility(181, Bank.coin, "Wood Chipper", 1, TARGET_SELF, 0)
			.setUnbound(true).setDesc("If you are holding a [Pile of Lumber], discard it and draw a card.");
	public static final CardAbility buzzsaw = new CardAbility(182, Bank.coin, "Buzz Saw", 2, TARGET_SELF, 0)
			.setUnbound(true).setDesc("Deals 1 damage to all surrounding units.");
	public static final CardAbility trollbros = new CardAbility(183, Bank.coin, "Bro-Slam", 2, TARGET_UNIT, 2)
			.setUnbound(true).setDesc("Deals this unit's attack damage to the target, twice.");
	public static final CardAbility agleanVision = new CardAbility(184, Bank.coin, "Doomsday Vision", 2, TARGET_SELF, 0)
			.setUnbound(false).setDesc("Draw a card.");
	public static final CardAbility burrow = new CardAbility(185, Bank.coin, "Burrow", 0, TARGET_SELF, 0)
			.setUnbound(false).setDesc("Burrow beneath the earth, becoming untargetable until your next turn.");
	public static final CardAbility fungus = new CardAbility(186, Bank.coin, "Fungal Spores", 0, TARGET_PASSIVE, 0)
			.setUnbound(false).setDesc("When this unit dies, a Fungal Wart will spawn for the enemy.");
	public static final CardAbility spore = new CardAbility(187, Bank.coin, "Spore Burst", 1, TARGET_UNIT, 3)
			.setUnbound(false)
			.setDesc("Infects the target, causing them to spawn a Fungal Goblin for the enemy when they die.");
	public static final CardAbility rake = new CardAbility(188, Bank.coin, "Fen Rake", 2, TARGET_UNIT, 2)
			.setUnbound(false).setDesc("Deals 4 damage to a unit, and 2 damage to units above and below it.");
	public static final CardAbility whispers = new CardAbility(189, Bank.coin, "Deathwhisper", 0, TARGET_PASSIVE, 0)
			.setUnbound(false).setDesc("When played, shuffle a Death Lotus into each player's deck and draw a card.");
	public static final CardAbility sonicStrike = new CardAbility(190, Bank.coin, "Sonic Strike", 0, TARGET_SELF, 1)
			.setUnbound(false).setDesc("Deal this unit's attack damage to all other units on this row.");
	public static final CardAbility deckwormdeath = new CardAbility(191, Bank.coin, "Breeding Ground", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc(
					"When this unit dies, shuffle a copy of it into your deck and deal 2 damage to your hero.");
	public static final CardAbility deckwormstay = new CardAbility(192, Bank.coin, "Parasitic", 0, TARGET_PASSIVE, 0)
			.setUnbound(false).setDesc("Shuffle a copy of this into your opponent's deck at the end of each turn.");
	public static final CardAbility blacklotus = new CardAbility(193, Bank.coin, "Defiler's Wake", 2, TARGET_SELF, 0)
			.setUnbound(false).setDesc("Add a Death Lotus to your opponent's hand.");
	public static final CardAbility barrier_tree = new CardAbility(194, Bank.flamering, "Dark Roots", 0, TARGET_PASSIVE,
			0).setDesc("When played, destroy all Study Oaks and gain +1/+1 for each.");
	public static final CardAbility despair = new CardAbility(195, Bank.flamering, "Despair", 0, TARGET_PASSIVE, 0)
			.setDesc("At the end of each turn, lose 1/1 for each card in your hand.");
	public static final CardAbility deckwormhold = new CardAbility(196, Bank.flamering, "Seething Corruption", 0,
			TARGET_PASSIVE, 0).setDesc("Each turn this unit is in your hand, your commander will take 2 damage.");
	public static final CardAbility unequip = new CardAbility(197, Bank.flamering, "Remove Equipment", 0, TARGET_SELF,
			0).setDesc("Removes the current EQUIPMENT from this unit, returning it to unit's owner's hand.");
	public static final CardAbility warding = new CardAbility(198, Bank.flamering, "Truesteel Ward", 0, TARGET_PASSIVE,
			0).setDesc("All damage taken is halved.");
	public static final CardAbility uproot = new CardAbility(199, Bank.flamering, "Uproot", 6, TARGET_SELF, 0)
			.setDesc("Set your speed to 4 and gain +4/+3.");
	public static final CardAbility fleshOfFire = new CardAbility(200, Bank.flamering, "Flesh of Fire", 2, TARGET_SELF,
			0).setDesc("Dealing 2 damage in a 3x3 area at the end of each turn. Cast to remove. Spreads.");
	public static final CardAbility corruptHealing = new CardAbility(201, Bank.flamering, "Aglean Plague", 0,
			TARGET_PASSIVE, 0).setDesc("All healing effects will instead deal damage.");
	public static final CardAbility deadroot = new CardAbility(202, Bank.flamering, "Deadwood Root", 1, TARGET_AREA, 10)
			.setUnbound(true).setDesc("Summons a Deadwood Root on the selected tile. Roots cannot be summoned within 3 tiles of another root.");
	public static final CardAbility repair = new CardAbility(203, Bank.flamering, "Repair", 2, TARGET_UNIT, 3)
			.setUnbound(true).setDesc("Add 1 Durability to the target unit's EQUIPMENT.");
	public static final CardAbility forge = new CardAbility(204, Bank.flamering, "Weapon Mastery", 1, TARGET_SELF, 0)
			.setUnbound(true).setDesc("CONJURE(3) an EQUIPMENT card.");
	public static final CardAbility volcano = new CardAbility(205, Bank.flamering, "Volcanic Sacrifice", 0,
			TARGET_PASSIVE, 0).setUnbound(true).setDesc(
					"When a unit attacks this, destroy it. Then, draw a card and deal 5 damage to a random enemy.");
	public static final CardAbility sprout = new CardAbility(206, Bank.flamering, "Rotspawn", 2, TARGET_AREA, 4)
			.setUnbound(true).setDesc("Spawn a X/X Sapling where X is this unit's HP.");
	public static final CardAbility aquatic = new CardAbility(207, Bank.particleBubble_2, "Aquatic", 0, TARGET_PASSIVE,
			0).setUnbound(false).setDesc("This unit may ONLY travel over WATER tiles.");
	public static final CardAbility amphibious = new CardAbility(208, Bank.particleBubble_0, "Amphibious", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("This unit may travel over WATER tiles.");
	public static final CardAbility hovering = new CardAbility(209, Bank.particleBubble_2, "Hovering", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("This unit may travel over AIR and WATER tiles.");
	public static final CardAbility burrowing = new CardAbility(210, Bank.particleBubble_2, "Burrower", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("This unit may travel over BOULDER & MOUNTAIN tiles.");
	public static final CardAbility splash = new CardAbility(211, Bank.particleBubble_2, "Splash", 2, TARGET_AREA, 2)
			.setUnbound(true).setDesc("Transform a tile into WATER.");
	public static final CardAbility wavecrash = new CardAbility(212, Bank.particleBubble_2, "Ocean's Call", 1,
			TARGET_UNIT, 3).setUnbound(true)
					.setDesc("Each AQUATIC or AMPHIBIOUS unit on board deals 1 damage to the target.");
	public static final CardAbility waverider = new CardAbility(213, Bank.particleBubble_2, "Waverider", 2, TARGET_AREA,
			6).setUnbound(false).setDesc("Jump to a WATER tile.");
	public static final CardAbility whirlpool = new CardAbility(214, Bank.particleBubble_2, "Whirlpool", 2, TARGET_SELF,
			0).setUnbound(false).setDesc("Deal 3 damage to all enemy units in WATER.");
	public static final CardAbility healingwaters = new CardAbility(215, Bank.particleBubble_2, "Healing Waters", 2,
			TARGET_SELF, 0).setUnbound(false).setDesc("Heal all units in WATER for 4.");
	public static final CardAbility stormbolt = new CardAbility(216, Bank.particleBubble_2, "Crackling Bolt", 2,
			TARGET_UNIT, 5).setUnbound(false)
					.setDesc("Deal 2 damage to a unit. If at least 6 tiles are WATER, deal 4 instead.");
	public static final CardAbility aquastrike = new CardAbility(217, Bank.particleBubble_2, "Aquapower", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("If you attack a unit while in WATER, deal double damage.");
	public static final CardAbility barrier_tidelord = new CardAbility(218, Bank.particleBubble_2, "Ocean's Embrace", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, gain +1/+1 for each WATER tile.");
	public static final CardAbility bloodpuppetry = new CardAbility(219, Bank.particleBubble_2, "Blood Puppetry", 2,
			TARGET_UNIT, 3).setUnbound(false).setDesc("Force a unit to attack itself.");
	public static final CardAbility darkthoughts = new CardAbility(220, Bank.particleBubble_2, "Dark Thoughts", 2,
			TARGET_UNIT, 3).setUnbound(false).setDesc("At the end of each turn, a random unit will kill itself.");
	public static final CardAbility krattRitual = new CardAbility(221, Bank.particleBubble_2, "Mystic Eye", 2,
			TARGET_UNIT, 3).setUnbound(false)
					.setDesc("Destroy a Kratt unit, add a copy of it to your hand, and gain its abilities");
	public static final CardAbility stinkbomb = new CardAbility(222, Bank.particleBubble_2, "Stink Bomb", 2,
			TARGET_UNIT, 6).setUnbound(false)
					.setDesc("Deals 6 damage to a unit, minus 1 for every tile between you and the unit.");
	public static final CardAbility deathpotion = new CardAbility(223, Bank.particleBubble_2, "Vile Elixir", 1,
			TARGET_UNIT, 2).setUnbound(false).setDesc("Give a unit +1 Attack, or a Kratt unit +1/+1.");
	public static final CardAbility swarmlord = new CardAbility(224, Bank.particleBubble_2, "Vengeance", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("Whenever you attack, or a Kratt unit dies, deal 2 damage to all enemies.");
	public static final CardAbility infectious = new CardAbility(225, Bank.particleBubble_2, "Infectious", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("Take 1 damage after each turn. Units you attack gain your abilities and effects.");
	public static final CardAbility raiseEarth = new CardAbility(226, Bank.particleBloom_3, "Raise Earth", 1,
			TARGET_AREA, 3).setUnbound(false).setDesc("Transform a tile into BOULDER.");
	public static final CardAbility rampage = new CardAbility(227, Bank.particleBloom_3, "Rampage", 0, TARGET_PASSIVE,
			0).setUnbound(false).setDesc("When played, deal 5 damage to surrounding structures.");
	public static final CardAbility earthshaker = new CardAbility(228, Bank.particleBloom_3, "Earthshaker", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("Deals 2 damage to units adjacent or on tiles this unit moves over.");
	public static final CardAbility packbeast = new CardAbility(229, Bank.particleBloom_3, "Lost Package", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("Upon death, this unit's owner will gain a random spell.");
	public static final CardAbility sandglaive = new CardAbility(230, Bank.particleBloom_3, "Sand Glaive", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("Whenever you cast a spell, deal 2 damage to the enemy commander.");
	public static final CardAbility sandmagic = new CardAbility(231, Bank.particleBloom_3, "Sandstorm Magic", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("Whenever you cast a spell, transform adjacent tiles to SAND.");
	public static final CardAbility createdesert = new CardAbility(232, Bank.particleBloom_3, "Sandburst", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, transform the surrounding 3x3 area into SAND.");
	public static final CardAbility serpent = new CardAbility(233, Bank.particleBloom_3, "Serpent's Call", 1,
			TARGET_AREA, 3).setUnbound(false).setDesc("Summon a X/X Snake where X is the amount of beasts on board.");
	public static final CardAbility bouldertoss = new CardAbility(234, Bank.particleBloom_3, "Boulder Toss", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("At the end of each turn, deal your attack damage to a random enemy.");
	public static final CardAbility releaseSouls = new CardAbility(235, Bank.particleBloom_3, "Imbue Soul", 1,
			TARGET_AREA, 4).setUnbound(false)
					.setDesc("Give a Sandstone Vessel +3 speed and stats equal to yours. Gain -2/-2.");
	public static final CardAbility koashira = new CardAbility(236, Bank.particleBloom_3, "Insatiable Hunger", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc(
					"At the end of each turn lose 2 HP, destroy the nearest non-Commander non-Elemental and gain its stats.");
	public static final CardAbility createGolem = new CardAbility(237, Bank.particleBloom_3, "Create Vessel", 1,
			TARGET_AREA, 3).setUnbound(false).setDesc("Summon a 0/3/0 Sandstone Vessel.");
	public static final CardAbility assassination = new CardAbility(238, Bank.particleBloom_3, "Deadly", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("Whenever you deal damage to a non-Commander, non-Structure unit, destroy it.");
	public static final CardAbility evasive = new CardAbility(239, Bank.particleBloom_3, "Evasive", 0, TARGET_PASSIVE,
			0).setUnbound(false).setDesc("Halves all damage taken while in WATER.");
	public static final CardAbility deathjaw = new CardAbility(240, Bank.particleBloom_3, "Jaws of Death", 0,
			TARGET_UNIT, 2).setUnbound(false).setDesc("Reduce a unit's SPEED by 4 until your next turn.");
	public static final CardAbility swap = new CardAbility(241, Bank.particleSwirl_5, "Particle Swap", 0, TARGET_UNIT,
			8).setUnbound(false).setDesc("Swap positions with a unit.");
	public static final CardAbility unstablegateway = new CardAbility(242, Bank.particleSwirl_3, "Unstable Gateway", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("Upon death, deal 4 damage to your commander.");
	public static final CardAbility flames = new CardAbility(243, Bank.particleFire_3, "Flame Channel", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("All damage sourcing from your units is increased by 1.");
	public static final CardAbility stormbreath = new CardAbility(244, Bank.particleFire_3, "Crackling Breath", 1,
			TARGET_AREA, 4).setUnbound(false)
					.setDesc("Deals 1 damage in a 3x3 radius. If you are in WATER, deal 2 instead.");
	public static final CardAbility crackle = new CardAbility(245, Bank.particleFire_3, "Surging Power", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When this unit attacks, it also deals 1 damage to ALL other enemies.");
	public static final CardAbility chomp = new CardAbility(254, Bank.particleFire_3, "Vicious Bite", 1, TARGET_UNIT, 2)
			.setUnbound(false).setDesc("Deal 1 damage to a unit. If  you are damaged, deal 2 instead.");
	public static final CardAbility vengeance = new CardAbility(246, Bank.particleFire_3, "Vengeance", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("Gain +2 Attack whenever a BEAST dies.");
	public static final CardAbility finalfrenzy = new CardAbility(247, Bank.particleFire_3, "Final Frenzy", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("Upon death, attack all surrounding units.");
	public static final CardAbility devourprey = new CardAbility(248, Bank.particleFire_3, "Devour", 0, TARGET_UNIT, 1)
			.setUnbound(false).setDesc("DESTROY a unit with 3 or less attack.");
	public static final CardAbility hatchSiren = new CardAbility(249, Bank.particleBubble_3, "Divine Torrent", 10,
			TARGET_SELF, 0).setUnbound(false)
					.setDesc("Destroy this unit and transform your commander into Goddess Adrelia.");
	public static final CardAbility shadydeal = new CardAbility(250, Bank.particleBubble_3, "Shady Deal", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, shuffle 2 Useless Artifacts into your opponent's deck.");
	public static final CardAbility kengheal = new CardAbility(251, Bank.particleBubble_3, "Sunstone Heal", 1,
			TARGET_UNIT, 4).setUnbound(false).setDesc("Restore 4 health to a unit, and 1 health to all enemies.");
	public static final CardAbility sunstrike = new CardAbility(252, Bank.particleBubble_3, "Searing Sun", 1,
			TARGET_UNIT, 4).setUnbound(false).setDesc("Deal X damage to a unit where X is your missing health.");
	public static final CardAbility naganigift = new CardAbility(253, Bank.particleBubble_3, "Nagani Curse", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, shuffle 2 Useless Artifacts into your deck.");
	public static final CardAbility kengpower = new CardAbility(255, Bank.particleBubble_3, "Kengai Power", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("Whenever a spell is cast, gain +1/+1.");
	public static final CardAbility melt = new CardAbility(256, Bank.particleBubble_3, "Withering Touch", 1,
			TARGET_AREA, 3).setCostType(COST_TYPE_MANA).setUnbound(true)
					.setDesc("Destroy a tile, replacing it with AIR.");
	public static final CardAbility souldrain = new CardAbility(257, Bank.particleBubble_3, "Void Ritual", 1,
			TARGET_SELF, 3).setUnbound(true).setDesc("Take 4 damage and gain 1 MANA this turn only.");
	public static final CardAbility darkvision = new CardAbility(258, Bank.particleBubble_3, "Dark Rebith", 3,
			TARGET_UNIT, 3).setCostType(COST_TYPE_MANA).setUnbound(false)
					.setDesc("Trigger a unit's ON PLAY and ON DEATH effects.");
	public static final CardAbility soulfeast = new CardAbility(259, Bank.particleBubble_3, "Feast of Souls", 1,
			TARGET_UNIT, 3).setCostType(COST_TYPE_MANA).setUnbound(true)
					.setDesc("Deal 1 damage to a unit. If it dies, gain +1 ATTACK.");
	public static final CardAbility stormsoul = new CardAbility(260, Bank.particleSwirl_3, "Spark of Oblivion", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When this unit dies, remove the current Board Status and heal all units for 2.");
	public static final CardAbility energyaoe = new CardAbility(261, Bank.particleSwirl_3, "Jungle Juice", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, grant surrounding units +1 Energy.");
	public static final CardAbility sludgify = new CardAbility(262, Bank.particleSwirl_3, "Lingering Stench", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, enemy units lose 2 Attack until your next turn.");
	public static final CardAbility carnage = new CardAbility(263, Bank.particleSwirl_3, "Mystical Carnage", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("Whenever you cast a spell, deal 2 damage to a random enemy.");
	public static final CardAbility beastheal = new CardAbility(264, Bank.particleSwirl_3, "Nature's Touch", 1,
			TARGET_UNIT, 4).setUnbound(false).setDesc("Restore 2 health to a BEAST.");
	public static final CardAbility cycle = new CardAbility(265, Bank.particleSwirl_3, "Circle of Life", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("Whenever a spell is cast, draw a card.");
	public static final CardAbility surprise = new CardAbility(266, Bank.particleSwirl_3, "Surprise Attack", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("If this unit dies while attacking from FULL HEALTH, return it to your hand.");
	public static final CardAbility ooze = new CardAbility(267, Bank.particleSwirl_3, "Duplication", 0, TARGET_PASSIVE,
			0).setUnbound(false).setDesc("When this unit attacks, halve its stats then spawn a copy of it randomly.");
	public static final CardAbility energytotem = new CardAbility(268, Bank.particleSwirl_3, "Surge", 0, TARGET_PASSIVE,
			0).setUnbound(false).setDesc("When a friendly unit is played, suffer 1 damage and grant it 1 Energy.");
	public static final CardAbility beastpoison = new CardAbility(269, Bank.particleSwirl_3, "Gift of Venom", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, grant the nearest friendly BEAST [Deadly].");
	public static final CardAbility nearbuff = new CardAbility(270, Bank.particleSwirl_3, "Riverwood Chant", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, grant the nearest creature +1/+1.");
	public static final CardAbility necksnap = new CardAbility(271, Bank.particleSwirl_3, "Neck Snapper", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, destroy the nearest creature with 3 or less attack.");
	public static final CardAbility constrict = new CardAbility(272, Bank.particleSwirl_3, "Constrict", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc(
					"When played, reduce the nearest enemy's speed to 0. When this unit dies, set the nearest enemy's speed to 4.");
	public static final CardAbility slam = new CardAbility(273, Bank.particleSwirl_3, "Head Crack", 0, TARGET_PASSIVE,
			0).setUnbound(false).setDesc("When played, deal 3 damage to the nearest non-commander unit.");
	public static final CardAbility spike = new CardAbility(274, Bank.particleSwirl_3, "Lasher", 0, TARGET_PASSIVE, 0)
			.setUnbound(false).setDesc("At the end of each turn, deal 1 damage to the nearest unit.");
	public static final CardAbility blast = new CardAbility(275, Bank.particleSwirl_3, "Blast", 0, TARGET_PASSIVE, 0)
			.setUnbound(false).setDesc("When played, deal 5 damage to the enemy base.");
	public static final CardAbility doomclaws = new CardAbility(276, Bank.particleSwirl_3, "Doom Claws", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, increase the nearest unit's attack by 3 until your next turn.");
	public static final CardAbility voidshards = new CardAbility(277, Bank.particleSwirl_3, "Blood Shards", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("Whenever this unit takes damage, deal 2 damage to a random enemy.");
	public static final CardAbility blessings = new CardAbility(278, Bank.particleSwirl_3, "Wild Blessings", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("Whenever you play a unit, grant it +1/+1.");
	public static final CardAbility sprint = new CardAbility(279, Bank.particleSwirl_3, "Sprint", 1, TARGET_SELF, 0)
			.setUnbound(true).setDesc("Increase your speed by 4 for 1 turn.");
	public static final CardAbility protective = new CardAbility(280, Bank.particleSwirl_3, "Protective Instinct", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, PROTECT the nearest unit until your next turn.");
	public static final CardAbility protect1 = new CardAbility(281, Bank.particleSwirl_3, "Greater Protection", 2,
			TARGET_UNIT, 3).setUnbound(false).setDesc("PROTECT a unit until you die.");
	public static final CardAbility meatsaw = new CardAbility(282, Bank.particleSwirl_3, "Meatsaw", 0, TARGET_PASSIVE,
			0).setUnbound(false).setDesc("Whenever this unit deals damage add a [Pile of Flesh] card to your hand.");
	public static final CardAbility fleshrebirth = new CardAbility(283, Bank.particleSwirl_3, "Abomination", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc(
					"When this unit dies, resummon it with +1/+1 for each [Flesh Pile] you hold, and discard them.");
	public static final CardAbility poisonspit = new CardAbility(284, Bank.particleSwirl_3, "Venom Spit", 0,
			TARGET_UNIT, 4).setUnbound(false).setDesc("Deal 1 damage to a unit.");
	public static final CardAbility regrowth = new CardAbility(285, Bank.particleSwirl_3, "Regrowth", 0, TARGET_PASSIVE,
			0).setUnbound(false).setDesc("At the end of each turn, restore 1 HP to this unit.");
	public static final CardAbility kidnapbeast = new CardAbility(286, Bank.particleSwirl_3, "Kidnap", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, take control of the nearest enemy BEAST.");
	public static final CardAbility nearprotect = new CardAbility(287, Bank.particleSwirl_3, "Protector's Vigil", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, PROTECT the nearest friendly unit until you die.");
	public static final CardAbility swipe = new CardAbility(288, Bank.particleSwirl_3, "Arcane Frenzy", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When you cast a spell, deal 2 damage to surrounding units.");
	public static final CardAbility wraithstorm = new CardAbility(289, Bank.particleSwirl_3, "Wraithpocalypse", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc(
					"When played, deal 3 damage to the nearest non-Commander unit, 4 times + 1 each time a unit dies (max 10).");
	public static final CardAbility bloodpower = new CardAbility(290, Bank.particleSwirl_3, "Crimson Ascension", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, gain +1/+1 for every HP missing from friendly non-commander units.");
	public static final CardAbility bloodfrenzy = new CardAbility(291, Bank.particleSwirl_3, "Bloodfrenzy", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, gain +1/+1 for every HP missing from friendly non-commander units.");
	public static final CardAbility bloodguard = new CardAbility(292, Bank.particleSwirl_3, "Blood Guardian", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, PROTECT all damaged friendly units.");
	public static final CardAbility eagleshot = new CardAbility(293, Bank.particleSwirl_3, "Eagle-Eye Shot", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, deal 4 damage to the opponent's furthest non-structure unit.");
	public static final CardAbility headbutt = new CardAbility(294, Bank.particleSwirl_3, "Horn Charge", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, deal X damage to the nearest enemy where X is (4 - distance).");
	public static final CardAbility tempest = new CardAbility(295, Bank.particleSwirl_3, "Forked Lightning", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, deal 1, 2 and 3 damage to the nearest 3 non-Commander units respectively.");
	public static final CardAbility nearheal = new CardAbility(296, Bank.particleSwirl_3, "Lifegiver", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, heal the nearest unit for 5 HP.");
	public static final CardAbility gaze = new CardAbility(297, Bank.particleSwirl_3, "Searing Gaze", 0, TARGET_PASSIVE,
			0).setUnbound(false).setDesc("Whenever a unit moves, deal 3 damage to it.");
	public static final CardAbility collapsing = new CardAbility(298, Bank.particleSwirl_3, "Collapsing", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("At the end of each turn, this unit takes 1 damage.");
	public static final CardAbility shiv = new CardAbility(299, Bank.particleSwirl_3, "Shiv", 0, TARGET_PASSIVE, 0)
			.setUnbound(false).setDesc("When played, deal 1 damage to the nearest non-commander unit.");
	public static final CardAbility iceboulder = new CardAbility(300, Bank.particleSwirl_3, "Ice Boulder", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc(
					"When played, deal 5 damage to the nearest non-commander unit that is more than 2 tiles away.");
	public static final CardAbility wallcrush = new CardAbility(301, Bank.particleSwirl_3, "Crushing Blow", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, deal 6 damage to the nearest STRUCTURE.");
	public static final CardAbility fanofknives = new CardAbility(302, Bank.particleSwirl_3, "Fan of Blades", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, deal 1 damage to the 3 nearest units.");
	public static final CardAbility nearenergize = new CardAbility(303, Bank.particleSwirl_3, "Surging Presence", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, give 2 energy to the nearest unit.");
	public static final CardAbility nearpummel = new CardAbility(304, Bank.particleSwirl_3, "Pummeling Blow", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, set the nearest unit's energy to (-1).");
	public static final CardAbility newworld = new CardAbility(305, Bank.particleSwirl_3, "Overgrowth", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc(
					"When played, change the map to Taruum's Grove. Replace your commander with this unit. Fully heal both commanders. Draw 2 cards.");
	public static final CardAbility bloodpuppet = new CardAbility(306, Bank.particleSwirl_3, "Blood Puppetry", 1,
			TARGET_AREA, 5).setUnbound(true).setDesc(
					"Summon a 5/5 Blood Puppet on the target tile. If this unit dies, all Blood Puppets are removed from play.");
	public static final CardAbility runefur = new CardAbility(307, Bank.particleSwirl_3, "Runic Roar", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, CONJURE(4) a SPELL card and cast it on this unit.");
	public static final CardAbility snoozing = new CardAbility(308, Bank.particleSwirl_3, "Slumber", 0, TARGET_PASSIVE,
			0).setUnbound(false).setDesc(
					"At the end of each turn, gain +X/+X where X is your half energy IF you have not moved or attacked this turn.");
	public static final CardAbility conjurebeast = new CardAbility(309, Bank.particleSwirl_3, "Kindred Bond", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc(
					"At the end of each turn, gain +X/+X where X is your half energy IF you have not moved or attacked this turn.");
	public static final CardAbility whirl = new CardAbility(310, Bank.particleSwirl_3, "Reckless Stomp", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, deal 2 damage to surrounding units.");
	public static final CardAbility whirl1 = new CardAbility(311, Bank.particleSwirl_3, "Whirl", 0, TARGET_PASSIVE, 0)
			.setUnbound(false).setDesc("When played, deal 1 damage to surrounding enemies.");
	public static final CardAbility conjurespell = new CardAbility(312, Bank.particleSwirl_3, "Stalker's Senses", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("Whenever played, CONJURE(5) a card with cost below 3.");
	public static final CardAbility spelldecay = new CardAbility(313, Bank.particleSwirl_3, "Shifting Sands", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc("Whenever a spell is cast, lose -2/-2.");
	public static final CardAbility aoeslow = new CardAbility(314, Bank.particleSwirl_3, "Sandstorm", 0, TARGET_PASSIVE,
			0).setUnbound(false).setDesc("When played, reduce the speed of all enemy units by 2 until your next turn.");
	public static final CardAbility eldritchConjure = new CardAbility(315, Bank.particleSwirl_3, "Stuck In The Mud", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, CONJURE(3) a card containing the word 'eldritch'.");
	public static final CardAbility kidnap = new CardAbility(316, Bank.particleSwirl_3, "Kidnap", 0, TARGET_PASSIVE, 0)
			.setUnbound(false).setDesc("When played, CONJURE(3) a unit from your deck, and remove it from your deck.");
	public static final CardAbility broswap = new CardAbility(317, Bank.particleSwirl_3, "Tag Team", 0, TARGET_PASSIVE,
			0).setUnbound(false).setDesc(
					"When this unit attacks, swap its attack and health then CONJURE(3) a card with cost equal to its health.");
	public static final CardAbility crusade = new CardAbility(318, Bank.particleSwirl_3, "Burn!", 2, TARGET_UNIT, 3)
			.setUnbound(false)
			.setDesc("Deal 3 damage to an UNDEAD, DEMON, or ELDRITCH unit. CONJURE(3) a card from that family.");
	public static final CardAbility witchhunt = new CardAbility(319, Bank.particleSwirl_3, "The Hunt", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, gain +1/+1 for each UNDEAD, DEMON or ELDRITCH unit on board.");
	public static final CardAbility humility = new CardAbility(320, Bank.particleSwirl_3, "Humility", 0, TARGET_PASSIVE,
			0).setUnbound(false).setDesc("Effects/Conditions on this unit cannot last more than 2 turns.");
	public static final CardAbility dawnlight = new CardAbility(321, Bank.particleSwirl_3, "Light of Dawn", 1,
			TARGET_UNIT, 2).setUnbound(false).setCostType(COST_TYPE_MANA).setDesc("Restore 3 health to a unit.");
	public static final CardAbility gaze1 = new CardAbility(322, Bank.particleSwirl_3, "Watchful Gaze", 0,
			TARGET_PASSIVE, 0).setUnbound(false).setDesc(
					"Whenever a card is CONJURED, gain a copy of it and deal 4 damage to the enemy commander.");
	public static final CardAbility titanichunger = new CardAbility(323, Bank.particleSwirl_3, "Titanic Feast", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When the game starts, remove up to 10 4-cost cards from your deck.");
	public static final CardAbility snipe1 = new CardAbility(324, Bank.particleSwirl_3, "Pulse Rifle", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("Whenever this unit moves, deal 2 damage to the farthest non-Structure non-Commander.");
	public static final CardAbility reload = new CardAbility(325, Bank.ball, "Reload", 3, TARGET_SELF, 0)
			.setUnbound(false).setDesc("Trigger this unit's ON PLAY effects again.");
	public static final CardAbility scrap = new CardAbility(326, Bank.ball, "Scrap Pile", 0, TARGET_PASSIVE, 0)
			.setUnbound(false)
			.setDesc("When this unit dies, grant the nearest MECHANICAL +X/+X where X is this unit's energy.");
	public static final CardAbility scorchground = new CardAbility(327, Bank.particleFire_3, "Magma Spew", 2,
			TARGET_AREA, 3).setUnbound(false)
					.setDesc("Transform a tile into SCORCHED. Units on SCORCHED tiles take 2 damage every turn.");
	public static final CardAbility vincdeath = new CardAbility(328, Bank.particleFire_3, "Recurring Nightmare", 2,
			TARGET_SELF, 0).setUnbound(false)
					.setDesc("Destroy this unit and add a copy of it to your hand. Draw a card.");
	public static final CardAbility sonicstrike = new CardAbility(329, Bank.particleFire_3, "Sonic Strike", 2,
			TARGET_SELF, 0).setUnbound(false)
					.setDesc("When played, deal 2 damage to the furthest non-Structure enemy.");
	public static final CardAbility petrify = new CardAbility(330, Bank.particleFire_3, "Petrify", 0, TARGET_PASSIVE, 0)
			.setUnbound(false).setDesc("When played, give the nearest unit +3 Health and reduce their speed to 0.");
	public static final CardAbility timegod = new CardAbility(331, Bank.particleFire_3, "Sands of Time", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("At the start of your turn, grant both players 1 " + Util.RESOURCE_NAME + ".");
	public static final CardAbility cower = new CardAbility(332, Bank.particleFire_3, "Cowardice", 1, TARGET_UNIT, 3)
			.setUnbound(false).setDesc("Force a friendly unit to PROTECT this unit.");
	public static final CardAbility legacy = new CardAbility(333, Bank.particleFire_3, "Infamous Legacy", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("If 6 or more units die PROTECTING this one, transform into The Fallen Coward.");
	public static final CardAbility emperor = new CardAbility(334, Bank.particleFire_3, "Imperial Tribute", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When this unit dies ON YOUR TURN, CONJURE(3) a REGAL unit card.");
	public static final CardAbility mummymagic = new CardAbility(335, Bank.particleFire_3, "Dark Magic", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("Whenever a friendy unit dies, deal 2 damage to the nearest enemy.");
	public static final CardAbility mummyspirit = new CardAbility(336, Bank.particleFire_3, "Eternal Service", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When this unit dies, give the nearest friendly unit +2/+2 and [Eternal Service].");
	public static final CardAbility zerdurim = new CardAbility(337, Bank.particleFire_3, "Awaken The Dead", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, re-Summon up to 4 random units that have died this game on adjacent tiles.");
	public static final CardAbility relicsearch = new CardAbility(338, Bank.particleFire_3, "Perusal", 0,
			TARGET_PASSIVE, 0).setUnbound(false)
					.setDesc("When played, the last (up to) 2 spells played will be cast on this unit.");
	public static final CardAbility deadwoodpulse = new CardAbility(339, Bank.particleFire_3, "Woodrot", 2,
			TARGET_SELF, 0).setUnbound(false)
					.setDesc("Destroy all your Deadwood Roots and deal 4 damage to units surround them. Gain +2/+2 for each root.");
	public static final CardAbility banner = new CardAbility(340, Bank.particleFire_3, "Cruel Command", 0, TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, the instance of damage is increased by 3.");
	public static final CardAbility movepower = new CardAbility(341, Bank.particleFire_3, "Thundering Hooves", 0, TARGET_PASSIVE, 0).setUnbound(false).setDesc("Whenever a friendly unit moves, gain ATTACK equal to their SPEED until your next turn.");
	public static final CardAbility spirithound = new CardAbility(342, Bank.particleSwirl_3, "Pack Hunting", 0, TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, CONJURE(3) a card with cost equal to how many ELEMENTALS are in play.");
	public static final CardAbility plainshot = new CardAbility(343, Bank.particleBloom_6, "Quickshot", 0, TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, deal 1 damage to the nearest enemy.");
	public static final CardAbility sealegs = new CardAbility(344, Bank.particleBloom_6, "Sea Legs", 1, TARGET_UNIT, 0).setUnbound(false).setDesc("Grant a unit [Amphibious].");
	public static final CardAbility hoofstorm = new CardAbility(345, Bank.particleBloom_6, "Hoofstorm", 2, TARGET_SELF, 0).setUnbound(true).setDesc("All units may move again.");
	public static final CardAbility dreadharvest = new CardAbility(346, Bank.particleBloom_6, "Doomrend", 0, TARGET_PASSIVE, 0).setUnbound(true).setDesc("When this unit attacks, heal the nearest friendly unit for the damage dealt.");
	public static final CardAbility frenzy = new CardAbility(347, Bank.particleBloom_6, "Blood Frenzy", 0, TARGET_PASSIVE, 0).setUnbound(true).setDesc("When this unit attacks, it continues attacking until the target dies.");
	public static final CardAbility swiping = new CardAbility(348, Bank.particleBloom_6, "Swiping Charge", 0, TARGET_PASSIVE, 0).setUnbound(true).setDesc("When this unit moves, it deals 3 damage to the nearest unit.");
	public static final CardAbility murkblade = new CardAbility(349, Bank.particleBloom_6, "The Fenblade", 0, TARGET_PASSIVE, 0).setUnbound(true).setDesc("When this unit kills a unit, spawn an Oak Tree where the unit died.");
	public static final CardAbility scavenge = new CardAbility(350, Bank.particleBloom_6, "Territorial", 0, TARGET_PASSIVE, 0).setUnbound(true).setDesc("Whenever an enemy unit moves within 2 tiles of this unit, they will suffer 3 damage.");
	public static final CardAbility enslave = new CardAbility(351, Bank.particleBloom_6, "Iron Chains", 0, TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, gain a copy of the last unit that was played.");
	public static final CardAbility terraform = new CardAbility(352, Bank.particleBloom_6, "Terraform", 1, TARGET_AREA, 20).setCostType(COST_TYPE_MANA).setUnbound(true).setDesc("Transform a tile into GROUND.");
	public static final CardAbility winteriscoming = new CardAbility(353, Bank.particleBloom_6, "Winter Is Coming", 0, TARGET_PASSIVE, 0).setUnbound(false).setDesc("Gain +1 HP at the end of each turn. If this unit reaches 15 HP, destroy it and add 10 [Black Walker] to your hand.");
	public static final CardAbility growingpower = new CardAbility(354, Bank.particleBloom_6, "Looming Terror", 0, TARGET_PASSIVE, 0).setUnbound(false).setDesc("At the end of your turn, double this unit's stats.");
	public static final CardAbility longestride = new CardAbility(355, Bank.particleBloom_6, "The Longest Ride", 0, TARGET_PASSIVE, 0).setUnbound(false).setDesc("When this unit dies, add it back to its owners hand.");
	public static final CardAbility gladfury = new CardAbility(356, Bank.particleBloom_6, "Gladiator's Fury", 0, TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, deal 4 damage to your commander.");
	public static final CardAbility nightmarch = new CardAbility(357, Bank.particleBloom_6, "Night March", 0, TARGET_PASSIVE, 0).setUnbound(false).setDesc("When played, deal 2 damage to the nearest enemy.");
	public static final CardAbility runestaff = new CardAbility(358, Bank.particleBloom_6, "Runic Legacy", 0, TARGET_PASSIVE, 0).setUnbound(false).setDesc("When this unit dies, add [Shurim's Runestaff] to its owners hand.");
	public static final CardAbility shurim = new CardAbility(359, Bank.particleBloom_6, "Runeweaving", 0, TARGET_PASSIVE, 0).setUnbound(false).setDesc("All spell cards trigger twice.");
	public static final CardAbility yearpass = new CardAbility(360, Bank.particleBloom_6, "Eternity", 2, TARGET_SELF, 0).setUnbound(true).setDesc("Trigger all units' ON PLAY and ON DEATH effects.");
	public static final CardAbility peace = new CardAbility(361, Bank.particleBloom_6, "Suffering's End", 0, TARGET_PASSIVE, 0).setUnbound(true).setDesc("When played, all damage taken is reduced by 1 for the rest of the game.");
	public static final CardAbility darkcharge = new CardAbility(362, Bank.dive, "Black Charge", 2, TARGET_AREA, 5).setDesc("Travel at a right angle, dealing 2 damage to every unit you pass through.");
	public static final CardAbility spark = new CardAbility(363, Bank.dive, "Spark", 0, TARGET_PASSIVE, 0).setDesc("When this unit dies, give the nearest friendly unit +3 Energy.");
	public static final CardAbility firebomb = new CardAbility(364, Bank.dive, "Flame Blast", 3, TARGET_UNIT, 4).setDesc("Deal 4 damage to a unit.");
	public static final CardAbility magmasurge = new CardAbility(365, Bank.dive, "Magma Surge", 1, TARGET_SELF, 0).setDesc("Increase the next instance of damage by 1.");
	public static final CardAbility cloudlord = new CardAbility(366, Bank.dive, "Cloudfall", 2, TARGET_SELF, 0).setDesc("When played or cast, this unit deals 3 damage to all non-ELEMENTALS.");
	public static final CardAbility bloodfire = new CardAbility(367, Bank.dive, "Bloodfire", 0, TARGET_PASSIVE, 0).setDesc("When this unit dies, increase the next instance of damage by 8.");
	public static final CardAbility volcanic = new CardAbility(368, Bank.dive, "Volcanic Blast", 2, TARGET_UNIT, 3).setDesc("Deal 1 damage to a unit. Damage doubled for each other friendly ELEMENTAL.");
	public static final CardAbility tribute = new CardAbility(369, Bank.dive, "Peaceful Tribute", 0, TARGET_PASSIVE, 0).setDesc("When this unit dies, reduce the all damage dealt this turn by 3.");
	public static final CardAbility wardrums = new CardAbility(370, Bank.dive, "Drums of War", 0, TARGET_PASSIVE, 0).setDesc("Whenever another unit is summoned, grant it 2 energy.");
	public static final CardAbility earthengrace = new CardAbility(371, Bank.dive, "Earthen Grace", 0, TARGET_PASSIVE, 0).setDesc("Whenever another unit is summoned, give it +1/+1.");
	public static final CardAbility totemtribute = new CardAbility(372, Bank.dive, "Totemic Tribute", 0, TARGET_PASSIVE, 0).setDesc("Whenever another unit is summoned, gain +1/+1.");
	public static final CardAbility hawktalons = new CardAbility(373, Bank.dive, "Blood Arrows", 0, TARGET_PASSIVE, 0).setDesc("At the start of your turn, increase the next instance of damage by this unit's attack.");
	public static final CardAbility duplication = new CardAbility(374, Bank.dive, "Duplication", 0, TARGET_PASSIVE, 0).setDesc("At the end of each turn, destroy this unit and add 2 copies of it to your hand.");
	public static final CardAbility shrinkage = new CardAbility(375, Bank.dive, "Fairy Odds", 0, TARGET_PASSIVE, 0).setDesc("When played, the next unit summoned will be set to (2/2/2).");
	public static final CardAbility intimidation = new CardAbility(376, Bank.dive, "Intimidation", 0, TARGET_PASSIVE, 0).setDesc("When played, the next unit summoned will take 5 damage.");
	public static final CardAbility elementalpower = new CardAbility(377, Bank.dive, "Primordial Runes", 0, TARGET_PASSIVE, 0).setDesc("Whenever another ELEMENTAL is summoned, increase the next instance of damage by 2.");
	public static final CardAbility circle = new CardAbility(378, Bank.dive, "Cycle of Rebirth", 0, TARGET_PASSIVE, 0).setDesc("Whenever a friendly unit dies, add a random ELEMENTAL to your hand.");
	public static final CardAbility massochism = new CardAbility(379, Bank.dive, "Blood Frenzy", 0, TARGET_PASSIVE, 0).setDesc("Whenever your commander takes damage, gain +3 Attack.");
	public static final CardAbility rakaj = new CardAbility(380, Bank.dive, "Elemental Ascension", 0, TARGET_PASSIVE, 0).setDesc("Whenever an ELEMENTAL is summoned, gain +2 HP. If you reach 14 HP, transform into Rakaj the Ascended.");
	public static final CardAbility elementalexplosion = new CardAbility(381, Bank.dive, "Wild Surge", 0, TARGET_PASSIVE, 0).setDesc("Whenever a unit is summoned, deal 4 damage to a random enemy.");
	public static final CardAbility cleanse = new CardAbility(382, Bank.dive, "Cleanse", 0, TARGET_PASSIVE, 0).setDesc("When played, deal 10 damage to the nearest UNDEAD unit.");
	public static final CardAbility energizeElemental = new CardAbility(383, Bank.dive, "Primordial Surge", 1, TARGET_UNIT, 3).setDesc("Grant an ELEMENTAL +2 Energy and [Primordial Surge].");
	public static final CardAbility shock = new CardAbility(384, Bank.particleSwirl_2, "Shock", 2, TARGET_UNIT, 6).setDesc("STUN a unit for 2 turns.");
	public static final CardAbility elementalscience = new CardAbility(385, Bank.particleSwirl_2, "Prototype", 2, TARGET_UNIT, 4).setDesc("Halve an ELEMENTAL's stats and add a copy of it to your hand.");
	public static final CardAbility crystalize = new CardAbility(386, Bank.particleSwirl_2, "Crystalize", 2, TARGET_SELF, 0).setDesc("Gain +2/+2 and lose 1 SPEED.");
	public static final CardAbility armyofelements = new CardAbility(387, Bank.particleSwirl_2, "Earthen Legion", 3, TARGET_SELF, 0).setDesc("Grant all ELEMENTALS the [Crystalize] ability.");
	public static final CardAbility lavalash = new CardAbility(388, Bank.particleSwirl_2, "Searing Lash", 0, TARGET_PASSIVE, 0).setDesc("When this unit dies, deal 3 damage to the nearest enemy.");
	public static final CardAbility elementalcreate = new CardAbility(389, Bank.particleSwirl_2, "Primordial Ritual", 2, TARGET_SELF, 0).setDesc("CONJURE(3) a card containing 'Elemental'.");
	public static final CardAbility shieldbash = new CardAbility(390, Bank.particleSwirl_2, "Shield Bash", 2, TARGET_UNIT, 2).setDesc("Deal damage to a unit equal to this unit's HP.");
	public static final CardAbility golemwizard = new CardAbility(391, Bank.particleSwirl_2, "Earthen Armour", 2, TARGET_UNIT, 3).setDesc("Set a non-Commander's stats to 10/10.");
	public static final CardAbility stun = new CardAbility(392, Bank.bash, "Bash", 1, TARGET_UNIT, 2).setDesc("STUN a unit for 2 turns.");
	public static final CardAbility stun1 = new CardAbility(393, Bank.bash, "Greater Bash", 1, TARGET_UNIT, 2).setDesc("STUN a unit for 4 turns.");
	public static final CardAbility nearstun = new CardAbility(394, Bank.bash, "Savage Hatchet", 0, TARGET_PASSIVE, 0).setDesc("When played, STUN the nearest non-Commander unit for 2 turns.");
	public static final CardAbility tribalarrows = new CardAbility(395, Bank.bash, "Totemic Horde", 0, TARGET_PASSIVE, 0).setDesc("Whenever a unit is played, increase the next instance of damage by 1.");
	public static final CardAbility deathstun = new CardAbility(396, Bank.bash, "Shattered Stone", 0, TARGET_PASSIVE, 0).setDesc("When this unit dies, stun ALL enemies for 2 turns.");
	public static final CardAbility corruptground = new CardAbility(397, Bank.particleFire_3, "Corruption", 1, TARGET_AREA, 3).setUnbound(false).setDesc("CORRUPT a tile.");
	public static final CardAbility voidwake = new CardAbility(398, Bank.particleFire_3, "Voidwake", 0, TARGET_PASSIVE, 0).setUnbound(false).setDesc("CORRUPT all tiles this unit moves over.");
	public static final CardAbility voidimmune = new CardAbility(399, Bank.particleFire_3, "Witherwalk", 0, TARGET_PASSIVE, 0).setUnbound(false).setDesc("This unit will be healed instead of damaged by CORRUPT tiles.");
	public static final CardAbility buildTower = new CardAbility(400, Bank.particleBloom_3, "Construct War Tower", 2, TARGET_AREA, 4).setCostType(COST_TYPE_MANA).setUnbound(false).setDesc("Summon a 0/8/0 War Tower which can train expendable units.");
	public static final CardAbility spawnWarrior = new CardAbility(401, Bank.particleBloom_3, "Train Warrior", 1, TARGET_AREA, 3).setCostType(COST_TYPE_MANA).setUnbound(false).setDesc("Summon a 1/2/3 warrior.");
	
	public CardAbility setDesc(String d) {
		this.desc = d;
		return this;
	}

	public CardAbility addCoordinate(int x, int y) {
		coordinates.add(new Point(x, y));
		return this;
	}

	public CardAbility setUnbound(boolean b) {
		this.unbound = b;
		return this;
	}

	public CardAbility setCostType(byte ct) {
		this.costType = ct;
		return this;
	}

	public void onPlayed(Unit u, int roomID) {
		boolean success = false;
		if(!u.isStunned()){
		if (this == formation) {
			Animation.createStaticAnimation(u, "CHARGE", 2, 1000);
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					int x1 = u.posX - 1 + i, y1 = u.posY - 1 + j;
					Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);	
					if (t != null) {
						if (t.ownerID == u.ownerID) {
							u.addOrUpdateEffect(new Effect(EffectType.attack, t.getAttack(), 999));
							u.addOrUpdateEffect(new Effect(EffectType.health, t.getHealth(), 999));
							t.getEffects().clear();
							t.hurt(t.getHealth() * 10, u);
						}
					}
				}
			}
			success = true;
		}
		if(this == gladfury){
			if (Bank.isServer()) {
				PlayerMP user = Bank.server.getClients(roomID).get(u.ownerID);
				Unit hero = Bank.server.getCommander(user);
				hero.hurt(4, u);
			} else {
				if (Util.clientID == u.ownerID) {
					Unit hero = ((PanelBoard) Display.currentScreen).getCommanderForPlayer(u.ownerID, roomID);
					hero.hurt(4, u);
					Util.drawParticleLine(u.getPoint(), hero.getPoint(), 9, true, "BLOOD");
				}
			}
		}
		if(this == peace){
			PersistentValue.addUniquePersistentValue("GAME_DMGINCR", -1, roomID).setLifetime(1);
			if(!Bank.isServer())Animation.createStaticAnimation(u, "BOON", 2, 500);
		}
		if(this == relicsearch){
			if(Bank.isServer()){
				GameServer room = Bank.getServer().getRoom(roomID);
				int size = room.castSpells.size();
				if(size >= 1){
					int start = size - 2;
					if(start < 0)start = 0;
					for(int i = start; i < size; ++ i){
						room.playCard(u.ownerID, room.castSpells.get(i), u.posX, u.posY);
					}
				}
			}else{
				Animation.createStaticAnimation(u, "fx4_sign_of_fire", 3, 800);
			}
		}
		if (this == shadydeal) {
			int tarID = (u.ownerID == 0 ? 1 : 0);
			if (Bank.isServer()) {
				Bank.server.getClients(roomID).get(tarID).hand.add(new CardShell(Card.useless));
				Bank.server.getClients(roomID).get(tarID).hand.add(new CardShell(Card.useless));
			} else {
				Animation.createStaticAnimation(u, "fx10_blackExplosion", 2, 750);
			}
		}
		if (this == banner){
			if(!Bank.isServer())Animation.createStaticAnimation(u, "CHARGE", 2, 800);
			PersistentValue.addPersistentValue("GAME_DMGINCR", 3, roomID);
		}
		if(this == plainshot){
				ArrayList<Unit> targets = new ArrayList<Unit>();
				if (Bank.isServer())
					targets = Bank.server.getUnits(roomID);
				else
					targets = Display.currentScreen.objects;
				ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
				for (int i = 0; i < targets.size(); ++i) {
					Unit t = targets.get(i);
					if (t != null)
						if (!t.isPlayerUnit() || t.ownerID == u.ownerID)
							newtargets.remove(t);
				}
				Unit tar = Util.getNearestUnit(u, newtargets);
				if (tar != null) {
					Util.drawParticleLine(u.getPoint(), tar.getPoint(), 8, true, "IRON");
					tar.hurt(1, u);
				}
		}
		if(this == spirithound){
			int cs = 0;
			ArrayList<Unit> uns = (ArrayList<Unit>) (Bank.isServer() ? Bank.server.getRoom(roomID).units.clone() : Display.currentScreen.objects.clone());
			for(Unit unit : uns){
				if(unit.getFamily() == Family.elemental)++cs;
			}
			if (Bank.isServer()) {
				ArrayList<Card> avail = new ArrayList<Card>();
				for (Card c : Card.all) {
					if (c != null) {
						if (c.getCost() == cs)
							avail.add(c);
					}
				}
				ArrayList<Card> list = Util.createConjuringList(avail, 5);
				Bank.server.setPicks(list, roomID);
				Packet14ShowPicks pkt = new Packet14ShowPicks(u.ownerID, u.GUID, 0, list);
				pkt.write(Bank.server.getRoom(roomID));
			} else {
				Animation.createStaticAnimation(u, "fx1_blue_topEffect", 2, 800);
			}
		}
		if (this == naganigift) {
			int tarID = u.ownerID;
			if (Bank.isServer()) {
				Bank.server.getClients(roomID).get(tarID).hand.add(new CardShell(Card.useless));
				Bank.server.getClients(roomID).get(tarID).hand.add(new CardShell(Card.useless));
			} else {
				Animation.createStaticAnimation(u, "FELMAGIC", 2, 950);
			}
		}
		if (this == rampage) {
			Animation.createStaticAnimation(u, "EARTH", 2, 1000);
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					int x1 = u.posX - 1 + i, y1 = u.posY - 1 + j;
					Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
					if (t != null) {
						if (t.isStructure()) {
							t.hurt(5, u);
							if (!Bank.isServer()) {
								Animation.createStaticAnimation(t, "EARTH", 1.75f, 850);
								Animation.createStaticAnimation(t, "SLASH", 2, 700);
							}
						}
					}
				}
			}
		}
		if (this == blast) {
			Unit tar = Display.currentScreen.getBaseForPlayer((u.ownerID == 0 ? 1 : 0), roomID);
			if (!Bank.isServer()) {
				Util.drawParticleLine(u.getPoint(), new Point(u.getPoint().x, 0), 12, false, "BWNOVA");
				Util.drawParticleLine(u.getPoint(), new Point(u.getPoint().x, 0), 8, false, "EXPLOSION");
				Util.drawParticleLine(new Point(u.getPoint().x, 0), tar.getPoint(), 40, false, "FIRE", 65,
						1000 + Util.generateRange(200), false);
				Animation.createStaticAnimation(tar, "EXPLOSION", 3.5f, 1000);
			}
			tar.hurt(5, u);
		}
		if (this == conjurespell) {
			if (Bank.isServer()) {
				ArrayList<Card> avail = new ArrayList<Card>();
				for (Card c : Card.all) {
					if (c != null) {
						if (c.getCost() < 3)
							avail.add(c);
					}
				}
				ArrayList<Card> list = Util.createConjuringList(avail, 5);
				Bank.server.setPicks(list, roomID);
				Packet14ShowPicks pkt = new Packet14ShowPicks(u.ownerID, u.GUID, 0, list);
				pkt.write(Bank.server.getRoom(roomID));
			} else {
				Animation.createStaticAnimation(u, "CHARGE", 2, 800);
			}
		}

		if (this == kidnap) {
			if (Bank.isServer()) {
				ArrayList<CardShell> deck = (ArrayList<CardShell>) Bank.server.getClients(roomID).get(u.ownerID).deck.clone();
				ArrayList<Card> avail = new ArrayList<Card>();
				for (CardShell cs : deck) {
					Card card = cs.getCard();
					if (card.isUnit())
						avail.add(card);
				}
				ArrayList<Card> list = Util.createConjuringList(avail, 3);
				Bank.server.setPicks(list, roomID);
				Packet14ShowPicks pkt = new Packet14ShowPicks(u.ownerID, u.GUID, 0, list);
				pkt.write(Bank.server.getRoom(roomID));
			} else {
				Animation.createStaticAnimation(u, "SKULLGAS", 2, 800);
			}
		}

		if (this == eldritchConjure) {
			if (Bank.isServer()) {
				ArrayList<Card> avail = new ArrayList<Card>();
				for (Card c : Card.all) {
					if (c != null) {
						if (Util.findMatch(c, "eldritch") > 0)
							avail.add(c);
					}
				}
				ArrayList<Card> list = Util.createConjuringList(avail, 3);
				Bank.server.setPicks(list, roomID);
				Packet14ShowPicks pkt = new Packet14ShowPicks(u.ownerID, u.GUID, 0, list);
				pkt.write(Bank.server.getRoom(roomID));
			} else {
				Animation.createStaticAnimation(u, "VOID", 2, 800);
			}
		}

		if (this == aoeslow) {
			if (!Bank.isServer())
				Animation.createStaticAnimation(u, "CHARGE", 2.4f, 1200);
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t.ownerID != u.ownerID) {
					t.addOrUpdateEffect(new Effect(EffectType.speed, -2, 2));
					if (!Bank.isServer())
						Animation.createStaticAnimation(t, "SLOW", 2, 750);
				}
			}
		}

		if (this == runefur) {
			if (Bank.isServer()) {
				ArrayList<Card> avail = new ArrayList<Card>();
				for (Card c : Card.all) {
					if (c != null) {
						if (c.isSpell())
							avail.add(c);
					}
				}
				ArrayList<Card> list = Util.createConjuringList(avail, 4);
				Bank.server.setPicks(list, roomID);
				Packet14ShowPicks pkt = new Packet14ShowPicks(u.ownerID, u.GUID, 0, list);
				pkt.write(Bank.server.getRoom(roomID));
			} else {
				Animation.createStaticAnimation(u, "RUNE", 2.4f, 1200);
			}
		}

		if (this == wraithstorm) {
			// Display.currentScreen.shake(2000, 12);
			if (!Bank.isServer())
				Animation.createStaticAnimation(u, "FELMAGIC", 2.4f, 1200);
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.getIsCommander() | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			int max = 4;
			int count = 0;
			while (count < max && count < 10) {
				Unit tar = Util.getNearestUnit(u, newtargets);
				if (tar != null) {
					if (!Bank.isServer())
						Util.drawParticleLine(u.getPoint(), tar.getPoint(), 12, false, "FELFIRE", 45, 650, false);
					tar.hurt(3, u);
					if (tar.getHealth() <= 0) {
						max++;
						newtargets.remove(tar);
					}
					++count;
				}
			}
		}
		if (this == bloodguard) {
			if (!Bank.isServer())
				Animation.createStaticAnimation(u, "SHIELD", 2, 1200);
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (Unit t : newtargets) {
				if (t.ownerID == u.ownerID && t.getHealth() < t.getHealthMax()) {
					t.Protect(u, 999);
					if (!Bank.isServer())
						Animation.createStaticAnimation(t, "BLOOD", 2, 850);
				}
			}
		}
		if (this == witchhunt) {
			if (!Bank.isServer())
				Animation.createStaticAnimation(u, "HOLYCAST", 2, 1200);
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (Unit t : newtargets) {
				if (t.getFamily() == Family.eldritch || t.getFamily() == Family.demon
						|| t.getFamily() == Family.undead) {
					u.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
					u.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
					if (!Bank.isServer()) {
						Animation.createStaticAnimation(t, "HOLYCAST", 1.25f, 850);
					}
				}
			}
		}
		if (this == slam) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.getIsCommander() | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 8, false, "BWNOVA");
				tar.hurt(3, u);
			}
		}
		if (this == nearstun) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.getIsCommander() | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 8, false, "STONE");
				tar.addOrUpdateEffect(new Effect(EffectType.stunned, 1, 2));
			}
		}
		if (this == cleanse) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.getIsCommander() | !t.isPlayerUnit() |! (t.getFamily() == Family.undead))
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 8, false, "HOLYCAST");
				tar.hurt(10, u);
			}
		}
		if (this == nightmarch) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (!t.isPlayerUnit() || t.ownerID == u.ownerID)
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 8, false, "SKULLGAS");
				tar.hurt(2, u);
			}
		}
		if (this == petrify) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.getIsCommander() | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 8, false, "SLOW");
				tar.addOrUpdateEffect(new Effect(EffectType.health, 3, 999));
				tar.setSpeed(0f);
				Animation.createStaticAnimation(tar, "SLOW", 2.5f, 800);
			}
		}
		if (this == doomclaws) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.getIsCommander() | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 8, false, "VOID");
				tar.addOrUpdateEffect(new Effect(EffectType.attack, 3, 2));
				Animation.createStaticAnimation(tar, "THUNDER", 1.5f, 750);
			}
		}
		if (this == wallcrush) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (!t.isStructure())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 8, false, "BWNOVA");
				Animation.createStaticAnimation(tar, "EARTH", 2, 800);
				tar.hurt(6, u);
			}
		}
		if (this == shiv) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.getIsCommander() | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 8, false, "SLASH");
				tar.hurt(1, u);
			}
		}
		if (this == newworld) {
			Grid grid = null;
			ArrayList<Unit> units = null;
			if (Bank.isServer()) {
				grid = Bank.server.getGrid(roomID);
				units = Bank.server.getUnits(roomID);
			} else {
				grid = Display.currentScreen.grid;
				units = Display.currentScreen.objects;
			}
			for (int i = 0; i < Util.boardWidth; ++i) {
				for (int j = 0; j < Util.boardHeight; ++j) {
					if (grid.getTileID(i, j) == GridBlock.boulder.getID()) {
						grid.setTileID(i, j, (byte) GridBlock.ground.getID());
					}
				}
			}
			for (int z = 0; z < 1; ++z)
				for (int i = 0; i < Util.boardWidth; ++i) {
					for (int j = 0; j < 3; ++j) {
						grid.setTileID(i, j + (z * (Util.boardHeight - 3)), (byte) GridBlock.water.getID());
					}
				}
			for (int i = 0; i < Util.boardWidth; ++i) {
				grid.setTileID(i, 3, (byte) GridBlock.dirtTop.getID());
				grid.setTileID(i, Util.boardHeight - 4, (byte) GridBlock.dirtTop.getID());
			}
			for (int i = 0; i < 8; ++i) {
				int x = Util.rand.nextInt(Util.boardWidth);
				int y = 3 + Util.rand.nextInt(Util.boardHeight - 6);
				if (Bank.isServer())
					Bank.server.addUnit(Card.tree.getUnit(), x, y, 999, roomID);
			}
			u.flagRemove = true;
			for (Unit unit : units) {
				if (!unit.canUnitMoveOverTile(unit.posX, unit.posY))
					unit.destroy();
				if (unit.getIsCommander()) {
					unit.heal(100);
					if (unit.ownerID == u.ownerID) {
						unit.setTemplate(Card.commEnt.getUnit());
						unit.resummon();
						if (!Bank.isServer()) {
							Animation.createStaticAnimation(unit, "LEAVES", 6, 1400);
						}
					}
				}

			}
			if (Bank.isServer())
				Bank.server.drawCard(Bank.server.getClients(roomID).get(u.ownerID), -1, 2);
		}
		if (this == iceboulder) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.getIsCommander() | !t.isPlayerUnit()
							|| Util.distance(t.getTilePosition(), u.getTilePosition()) < 3)
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 8, false, "FREEZE", 64, 500, false);
				tar.hurt(5, u);
			}
		}
		if (this == tempest) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.getIsCommander() | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			int dmg = 1;
			for (int i = 0; i < 3; ++i) {
				Unit tar = Util.getNearestUnit(u, newtargets);
				if (tar != null) {
					Util.drawParticleLine(u.getPoint(), tar.getPoint(), 8 + dmg * 3, false, "MANABURN");
					Animation.createStaticAnimation(tar, "THUNDER", 2 + i * 0.5f, 750 + i * 200);
					tar.hurt(dmg, u);
					++dmg;
					newtargets.remove(tar);
				}
			}
		}
		if (this == fanofknives) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (!t.isPlayerUnit() || t.ownerID == u.ownerID)
						newtargets.remove(t);
			}
			int dmg = 1;
			for (int i = 0; i < 3; ++i) {
				Unit tar = Util.getNearestUnit(u, newtargets);
				if (tar != null) {
					Util.drawParticleLine(u.getPoint(), tar.getPoint(), 7, false, "SLASH");
					Animation.createStaticAnimation(tar, "SLASH", 2.5f, 1200);
					tar.hurt(dmg, u);
					newtargets.remove(tar);
				}
			}
		}
		if (this == headbutt) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.ownerID == u.ownerID | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 8, false, "BWNOVA");
				Animation.createStaticAnimation(tar, "EARTH", 2, 700);
				int dmg = 5 - Util.distance(u.getTilePosition(), tar.getTilePosition());
				if (dmg < 0)
					dmg = 0;
				tar.hurt(dmg, u);
			}
		}
		if (this == eagleshot) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			int enemy = (u.ownerID == 0 ? 1 : 0);
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.ownerID != enemy | !t.isPlayerUnit() || t.isStructure())
						newtargets.remove(t);
			}
			Unit tar = Util.getFurthestUnit(u, newtargets);
			if (tar != null) {
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 10, true, "BWNOVA");
				tar.hurt(4, u);
			}
		}
		if (this == sonicstrike) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			int enemy = (u.ownerID == 0 ? 1 : 0);
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.ownerID != enemy | !t.isPlayerUnit() || t.isStructure())
						newtargets.remove(t);
			}
			Unit tar = Util.getFurthestUnit(u, newtargets);
			if (tar != null) {
				if (!Bank.isServer()) {
					Util.drawParticleLine(u.getPoint(), tar.getPoint(), 25, true, "SWIRL");
					Animation.createStaticAnimation(tar, "BLUELIGHT", 1, 500);
				}
				tar.hurt(2, u);
			}
		}
		if (this == kidnapbeast) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.getFamily() != Family.beast || t.ownerID == u.ownerID | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				tar.ownerID = u.ownerID;
				if (!Bank.isServer())
					Animation.createStaticAnimation(tar, "MAGIC", 2, 600);
			}
		}
		if (this == protective) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.ownerID != u.ownerID | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				tar.Protect(u, 2);
				Animation.createStaticAnimation(u, "CHARGE", 1.25f, 700);
				Animation.createStaticAnimation(tar, "LEAVES", 2, 900);
			}
		}
		if (this == nearprotect) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.ownerID != u.ownerID | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				tar.Protect(u, 999);
				Animation.createStaticAnimation(tar, "SHIELD", 1.25f, 700);
			}
		}
		if (this == beastpoison) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.getFamily() != Family.beast || t.ownerID != u.ownerID | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				tar.getAbilities().add(CardAbility.assassination);
				Animation.createStaticAnimation(tar, "BURST", 2, 900);
			}
		}
		if (this == nearbuff) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.ownerID != u.ownerID | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				tar.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
				tar.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
				Animation.createStaticAnimation(tar, "GALE", 2, 900);
			}
		}
		if (this == nearheal) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (!t.isPlayerUnit())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				tar.heal(5);
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 8, false, "LEAVES");
				Animation.createStaticAnimation(tar, "LEAVES", 2, 900);
			}
		}
		if (this == barrier_tree) {
			if (Bank.isServer()) {
				ArrayList<Unit> targets = new ArrayList<Unit>();
				for (int i = 0; i < Bank.server.getUnits(roomID).size(); ++i) {
					Unit t = Bank.server.getUnits(roomID).get(i);
					if (t != null) {
						if (t.getTemplate() == Card.tree) {
							targets.add(t);
						}
					}
				}
				for (Unit t : targets) {
					if (t != null) {
						t.hurt(t.getHealth(), u);
						u.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
						u.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
					}
				}
			} else {
				ArrayList<Unit> targets = new ArrayList<Unit>();
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit t = Display.currentScreen.objects.get(i);
					if (t != null) {
						if (t.getTemplate() == Card.tree) {
							targets.add(t);
						}
					}
				}
				for (Unit t : targets) {
					if (t != null) {
						t.hurt(t.getHealth(), u);
						u.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
						u.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
						Util.drawParticleLine(t.getPoint(), u.getPoint(), 10, false, "BLOOM");
						Animation.createStaticAnimation(t, "LEAVES", 2, 600);
					}
				}
			}
			success = true;
		}
		if (this == barrier_tidelord) {
			if (Bank.isServer()) {
				int count = 0;
				byte[][] grid = Bank.server.getGrid(roomID).getCore();
				for (int i = 0; i < grid[0].length; ++i) {
					for (int j = 0; j < grid[1].length; ++j) {
						if (grid[i][j] == GridBlock.water.getID()) {
							++count;
						}
					}
				}
				u.addOrUpdateEffect(new Effect(EffectType.attack, count, 999));
				u.addOrUpdateEffect(new Effect(EffectType.health, count, 999));
			} else {
				int count = 0;
				byte[][] grid = ((PanelBoard) Display.currentScreen).getGrid().getCore();
				for (int i = 0; i < grid[0].length; ++i) {
					for (int j = 0; j < grid[1].length; ++j) {
						if (grid[i][j] == GridBlock.water.getID()) {
							++count;
							Util.drawParticleLine(new Point(Util.boardOffsetX + Grid.tileSize * i,
									Util.boardOffsetY + Grid.tileSize * j), u.getPoint(), 10, true, "BUBBLE");
						}
					}
				}
				u.addOrUpdateEffect(new Effect(EffectType.attack, count, 999));
				u.addOrUpdateEffect(new Effect(EffectType.health, count, 999));
				Animation.createStaticAnimation(u, "WATERBIRD", 2f, 900);
			}
			success = true;
		}
		if (this == whispers) {
			if (Bank.isServer()) {
				for (PlayerMP pl : Bank.server.getClients(roomID)) {
					pl.deck.add(new CardShell(Card.deathlotus));
				}
				Bank.server.drawCard(Bank.server.getClients(roomID).get(u.ownerID), -1, 1);
			} else {
				Animation.createStaticAnimation(u, "MAGIC", 2, 1000);
				Animation.createStaticAnimation(Util.boardOffsetX + Grid.tileSize * (Util.boardWidth + 1),
						Util.boardOffsetY + Grid.tileSize * (Util.boardHeight - 1), "MAGIC", 10, 3000);
				Animation.createStaticAnimation(Util.boardOffsetX + Grid.tileSize * (Util.boardWidth + 1),
						Util.boardOffsetY + Grid.tileSize * (1), "MAGIC", 10, 3000);
			}
			success = true;
		}
		if (this == eldritchEat) {
			if (Bank.isServer()) {
				for (int i = 0; i < Bank.server.getUnits(roomID).size(); ++i) {
					Unit t = Bank.server.getUnits(roomID).get(i);
					if (t != null) {
						if (t.getFamily() == Family.eldritch) {
							t.hurt(4, u);
						}
					}
				}
			} else {
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit t = Display.currentScreen.objects.get(i);
					if (t != null) {
						if (t.getFamily() == Family.eldritch) {
							t.hurt(4, u);
							Util.drawParticleLine(t.getPoint(), u.getPoint(), 10, false, "POOL");
						}
					}
				}
			}
			success = true;
		}
		if (this == eldritchLeg) {
			if (Bank.isServer()) {
				for (int i = 0; i < Bank.server.getUnits(roomID).size(); ++i) {
					Unit t = Bank.server.getUnits(roomID).get(i);
					if (t != null) {
						if (t.getFamily() == Family.eldritch) {
							u.addOrUpdateEffect(new Effect(EffectType.attack, 3, 999));
							u.addOrUpdateEffect(new Effect(EffectType.health, 3, 999));
						}
					}
				}
			} else {
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit t = Display.currentScreen.objects.get(i);
					if (t != null) {
						if (t.getFamily() == Family.eldritch) {
							u.addOrUpdateEffect(new Effect(EffectType.attack, 3, 999));
							u.addOrUpdateEffect(new Effect(EffectType.health, 3, 999));
							Util.drawParticleLine(t.getPoint(), u.getPoint(), 8, false, "VOID");
						}
					}
				}
				Animation.createStaticAnimation(u, "VOIDCAST", 5, 1500);
				Animation.createStaticAnimation(u, "POOL", 5, 2000);
			}
			success = true;
		}
		if (this == siphonessence) {
			if (Bank.isServer()) {
				for (int i = 0; i < Bank.server.getUnits(roomID).size(); ++i) {
					Unit t = Bank.server.getUnits(roomID).get(i);
					if (t != null) {
						if (t.ownerID != u.ownerID) {
							u.addEnergy1(1);
						}
					}
				}
			} else {
				Animation.createStaticAnimation(u, "BLUELIGHT", 2, 1200);
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit t = Display.currentScreen.objects.get(i);
					if (t != null) {
						if (t.ownerID != u.ownerID) {
							u.addEnergy1(1);
							Util.drawParticleLine(u.getPoint(), t.getPoint(), 30, false, "SWIRL");
						}
					}
				}
			}
			success = true;
		}
		if (this == barrier) {
			if (Bank.isServer()) {
				for (int i = 0; i < Bank.server.getUnits(roomID).size(); ++i) {
					Unit t = Bank.server.getUnits(roomID).get(i);
					if (t != null) {
						if (t.ownerID != u.ownerID && t.ownerID < 100 && t.ownerID >= 0) {
							u.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
						}
					}
				}
			} else {
				Animation.createStaticAnimation(u, "VOID", 2, 1000);
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit t = Display.currentScreen.objects.get(i);
					if (t != null) {
						if (t.ownerID != u.ownerID && t.ownerID < 100 && t.ownerID >= 0) {
							u.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
							Util.drawParticleLine(t.getPoint(), u.getPoint(), 30, false, "VOIDCAST");
						}
					}
				}
			}
			success = true;
		}
		if (Bank.isClient() && success) {
			int w = Properties.width / 5;
			Animation anim = new Animation(Util.boardOffsetX + Grid.tileSize * u.posX + Grid.tileSize / 2,
					Util.boardOffsetY + Grid.tileSize * (u.posY), 4000, Animation.TAG_ABILITY);
			anim.width = w / 2;
			anim.setData(this.getId());
			Display.currentScreen.particles.add(anim);
		}
		}
		Util.resolveUnits(roomID);
	}

	public void onTurnChange(int turn, Unit u, int roomID) {
		boolean success = false;
		if(!u.isStunned()){
		int deaths = 0;
		if (this == eldritchSwirl) {
			if (Bank.isServer()) {
				for (Unit t : Bank.server.getUnits(roomID)) {
					if (t != null && t != u) {
						t.hurt(2, u);
						if (t.getHealth() <= 0) {
							deaths++;
						}
						success = true;
					}
				}
			} else {
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit t = Display.currentScreen.objects.get(i);
					if (t != null && t != u) {
						t.hurt(2, u);
						if (t.getHealth() <= 0) {
							deaths++;
						}
						Animation.createStaticAnimation(t, "POOL", 1.5f, 700);
						success = true;
					}
				}
			}
			if (Bank.isServer()) {
				for (Unit t : Bank.server.getUnits(roomID)) {
					if (t != null) {
						if (t.getFamily() == Family.eldritch) {
							t.addOrUpdateEffect(new Effect(EffectType.attack, deaths, 999));
							t.addOrUpdateEffect(new Effect(EffectType.health, deaths * 2, 999));
						}
					}
				}
			} else {
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit t = Display.currentScreen.objects.get(i);
					if (t != null) {
						if (t.getFamily() == Family.eldritch) {
							t.addOrUpdateEffect(new Effect(EffectType.attack, deaths, 999));
							t.addOrUpdateEffect(new Effect(EffectType.health, deaths * 2, 999));
							Animation.createStaticAnimation(t, "VOIDCAST", 2f, 1000);
						}
					}
				}
			}
		}
		if(this == hawktalons && turn == u.ownerID){
			if(!Bank.isServer()){
				Animation.createStaticAnimation(u, "LEAVES", 2, 500);
				Animation.createStaticAnimation(u, "SLASH", 2, 700);
			}
			PersistentValue.addPersistentValue("GAME_DMGINCR", u.getAttack(), roomID);
		}
		if (this == timegod) {
			if (turn == u.ownerID) {
				int enemy = (u.ownerID == 0 ? 1 : 0);
				if (Bank.isServer()) {
					Bank.server.getClients(roomID).get(u.ownerID).baseGold++;
					Bank.server.getClients(roomID).get(u.ownerID).gold++;

					Bank.server.getClients(roomID).get(enemy).baseGold++;
					Bank.server.getClients(roomID).get(enemy).gold++;
				} else {
					Util.gold++;
					Util.maxGold++;

					Animation.createStaticAnimation(u, "fx5_fire_scissors", 2, 1600);
					u.say("I will show you the future.");
				}
			}
		}
		if(this == winteriscoming){
			u.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
			if(u.getHealth() >= 15){
				if(Bank.isServer()){
					for(int i = 0; i < 10; ++i)
					Bank.getServer().getRoom(roomID).clients.get(u.ownerID).hand.add(new CardShell(Card.blackwalker));
				}else{
					
					Animation.createStaticAnimation(u, "SKULLGAS", 4, 1200);
					if(Util.clientID == u.ownerID){
					for(int i = 0; i < 10; ++i)
						((PanelBoard)Display.currentScreen).hand.add(new CardShell(Card.blackwalker));
					}
				}
				u.destroy();
			}
		}
		if(this == growingpower && u.ownerID != turn){
			u.addOrUpdateEffect(new Effect(EffectType.health, u.getHealth(), 999));
			u.addOrUpdateEffect(new Effect(EffectType.attack, u.getAttack(), 999));
			if(!Bank.isServer())Animation.createStaticAnimation(u, "fx1_blue_topEffect", 1.5f, 650);
		}
		if (this == spike) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.getIsCommander() | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 8, false, "SLASH");
				tar.hurt(1, u);
			}
		}
		if (this == humility) {
			for (Effect e : u.getEffects()) {
				if (e.getDuration() > 2 || e.getDuration() < 0)
					e.setDuration(1);
			}
		}
		if (this == koashira) {
			u.hurt(2, u);
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.getIsCommander() | !t.isPlayerUnit() || t.getFamily() == Family.elemental)
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				u.addOrUpdateEffect(new Effect(EffectType.attack, tar.getAttack(), 999));
				u.addOrUpdateEffect(new Effect(EffectType.health, tar.getHealth(), 999));
				Util.drawParticleLine(tar.getPoint(), u.getPoint(), 8, false, "SKULLGAS");
				tar.hurt(tar.getHealth() * 10, u);
				if (tar.getHealth() > 0)
					tar.setHealth(0);
			}
		}
		if (this == snoozing) {
			int energy = u.getEnergy() / 2;
			if (u.hasAttacked == false && u.hasMoved == false) {
				u.addOrUpdateEffect(new Effect(EffectType.attack, energy, 999));
				u.addOrUpdateEffect(new Effect(EffectType.health, energy, 999));
				if (!Bank.isServer()) {
					for (int i = 0; i < 20; ++i) {
						AnimatedParticle p = new AnimatedParticle(
								Util.boardOffsetX + u.posX * Grid.tileSize + Grid.tileSize / 2,
								Util.boardOffsetY + u.posY * Grid.tileSize + Grid.tileSize / 2,
								64 + Util.rand.nextInt(16), 900 + Util.rand.nextInt(301)).addFrameSet("SLEEP")
										.setMotions(Util.rand.nextInt(30) - Util.rand.nextInt(30),
												Util.rand.nextInt(30) - Util.rand.nextInt(30));
						Display.currentScreen.particles.add(p);
					}
				}
			}
		}
		if (this == collapsing) {
			int ciel = 5;
			if (Bank.isServer()) {
				if (Bank.server.getRoom(roomID).baseGold >= ciel) {
					u.flagRemove = true;
				}
			} else {
				if (Util.maxGold >= ciel) {
					u.flagRemove = true;
					Animation.createStaticAnimation(u, "EARTH", 1.25f, 500);
				}
			}
		}
		if (this == regrowth) {
			u.heal(1);
			Animation.createStaticAnimation(u, "HEAL", 2, 500);
		}
		if (this == deckwormstay) {
			if (u.ownerID == turn) {
				if (Bank.isServer()) {
					for (PlayerMP pl : Bank.server.getClients(roomID)) {
						if (Bank.server.getClients(roomID).indexOf(pl) != u.ownerID) {
							pl.deck.add(new CardShell(Card.deckworm));
						}
					}
				} else {
					Animation.createStaticAnimation(u, "VOIDCAST", 2, 1000);
				}
			}
		}
		if (this == infectious) {
			u.hurt(1, u);
			Animation.createStaticAnimation(u, "GALE", 2, 750);
			Animation.createStaticAnimation(u, "BURST", 2.75f, 950);
		}
		if (this == shadowgate) {
			if (Bank.isServer()) {
				if (Bank.server.getRoom(roomID).playerTurn == u.ownerID) {
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
							u.ownerID, roomID);
				}
			}
			success = true;
			u.hurt(1, u);
		}
		if (this == fury) {
			if (u.ownerID == turn) {
				for (int i = 0; i < 3; ++i) {
					for (int j = 0; j < 3; ++j) {
						int x1 = u.posX - 1 + i, y1 = u.posY - 1 + j;
						AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + x1 * Grid.tileSize,
								Util.boardOffsetY + y1 * Grid.tileSize, Grid.tileSize, 300).addFrameSet("SLASH");
						Display.currentScreen.particles.add(p);
						Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
						if (t != null && t != u) {
							t.hurt(u.getAttack(), u);
							success = true;
						}
					}
				}
			}
		}
		if (this == fleshOfFire) {
			if (u.ownerID == turn) {
				for (int i = 0; i < 3; ++i) {
					for (int j = 0; j < 3; ++j) {
						int x1 = u.posX - 1 + i, y1 = u.posY - 1 + j;
						AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + x1 * Grid.tileSize,
								Util.boardOffsetY + y1 * Grid.tileSize, Grid.tileSize, 300).addFrameSet("INCINERATE");
						if (!Bank.isServer())
							Display.currentScreen.particles.add(p);
						Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
						if (t != null && t != u) {
							t.hurt(2, u);
							success = true;
							if (!t.getAbilities().contains(this))
								t.getAbilities().add(this);
						}
					}
				}
			}
		}
		if (this == shadows) {
			if (Bank.isServer()) {
				Unit comm = Bank.server.getCommander(Bank.server.getClients(roomID).get(u.ownerID));
				comm.hurt(1, u);
			}
			if (Bank.isClient()) {
				Unit comm = Display.currentScreen.getCommanderForPlayer(u.ownerID, roomID);
				comm.hurt(1, u);
				Util.drawParticleLine(u.getPoint(), comm.getPoint(), 30, true, "VOID", 32, 500, true);
				Animation.createStaticAnimation(comm, "SKULLGAS", 2, 1000);
				Animation.createStaticAnimation(u, "voidcast", 2, 700);
			}
			success = true;
		}
		if (this == confusion) {
			if (Bank.isServer()) {
				int total = (int) (u.getAttack() + u.getHealth() + u.getSpeed());
				int atk = 0;
				int hp = 0;
				int spd = 0;
				for (int i = 0; i < total; ++i) {
					int r = Util.rand.nextInt(3);
					if (r == 0)
						atk++;
					if (r == 1)
						hp++;
					if (r == 2)
						spd++;
				}
				this.sendComm(atk + "," + hp + "," + spd, u, null, 0, 0, roomID);
				u.getEffects().clear();
				u.setHealth(hp);
				u.setAttack(atk);
				u.setSpeed(spd);
			}
			if (Bank.isClient()) {
				Animation.createStaticAnimation(u, "MAGIC", 1.5f, 800);
			}
			success = true;
		}
		if (this == growing) {
			u.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
			Animation.createStaticAnimation(u, "HEAL", 2, 750);
			success = true;
		}
		if (this == energetic) {
			u.setEnergy1(u.getEnergy() + 1);
			for (int i = 0; i < 20; ++i) {
				AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + u.posX * Grid.tileSize,
						Util.boardOffsetY + u.posY * Grid.tileSize, 16 + Util.rand.nextInt(32),
						300 + Util.rand.nextInt(201)).addFrameSet("ENERGY").setMotions(
								Util.rand.nextInt(30) - Util.rand.nextInt(30),
								Util.rand.nextInt(30) - Util.rand.nextInt(30));
				Display.currentScreen.particles.add(p);
			}
			success = true;
			Animation.createStaticAnimation(u, "fx8_lighteningBall", 1.5f, 800);
		}
		if (this == hatch) {
			if (Bank.server != null) {
				Bank.server.drawCard(Bank.server.getClients(roomID).get(u.ownerID), Card.spiderling.getId(), 1);
			}
			for (int i = 0; i < 20; ++i) {
				AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + u.posX * Grid.tileSize,
						Util.boardOffsetY + u.posY * Grid.tileSize, 16 + Util.rand.nextInt(32),
						300 + Util.rand.nextInt(201)).addFrameSet("SKULL").setMotions(
								Util.rand.nextInt(30) - Util.rand.nextInt(30),
								Util.rand.nextInt(30) - Util.rand.nextInt(30));
				Display.currentScreen.particles.add(p);
			}
			success = true;
			Animation.createStaticAnimation(u, "MAGIC", 2, 800);
		}
		if (this == erupt) {
			if (Bank.isServer()) {
				int rand = Util.rand.nextInt(Bank.server.getUnits(roomID).size());
				Unit unit = Bank.server.getUnits(roomID).get(rand);
				if (unit != null) {
					unit.hurt(6, u);
					this.sendComm(unit.GUID + "", u, null, 0, 0, roomID);
					success = true;
				}
			}
		}
		if (this == bouldertoss) {
			if (Bank.isServer()) {
				ArrayList<Unit> avail = new ArrayList<Unit>();
				for (Unit t : Bank.server.getUnits(roomID)) {
					if (t.ownerID != u.ownerID) {
						avail.add(t);
					}
				}
				int rand = Util.rand.nextInt(avail.size());
				Unit unit = avail.get(rand);
				if (unit != null) {
					unit.hurt(u.getAttack(), u);
					this.sendComm(unit.GUID + "", u, null, 0, 0, roomID);
					success = true;
				}
			}
		}
		if (this == litFuse) {
			u.hurt(1, u);
		}
		if (this == wildmagic) {
			if (Bank.isServer()) {
				UnitTemplate t = null;
				int e = u.getEnergy();
				ArrayList<UnitTemplate> avail = new ArrayList<UnitTemplate>();
				for (int i = 0; i < Card.all.length; ++i) {
					Card card = Card.all[i];
					if (card != null) {
						if (card.getUnit() != null) {
							avail.add((UnitTemplate) card);
						}
					}
				}
				t = avail.get(Util.rand.nextInt(avail.size()));
				u.setTemplate(t);
				u.resummon();
				u.getAbilities().add(CardAbility.wildmagic);
				u.setEnergy1(e);
				sendComm(t.getId() + "", u, null, 0, 0, roomID);
			}
			success = true;
			Animation.createStaticAnimation(u, "BLUELIGHT", 2, 1000);
		}
		if (this == mercenary) {
			Animation.createStaticAnimation(u, "FLASH", 2, 800);
			u.setOwnerID((u.ownerID == 0 ? 1 : 0));
			success = true;
		}
		if (this == plague) {
			Animation.createStaticAnimation(u, "skullgas", 2, 1400);
			u.hurt(5, u);
			success = true;
		}
		if (this == recruit) {
			if (Bank.isServer()) {
				int rand = Util.rand.nextInt(2);
				if (rand == 0) {
				} else {
					Bank.server.drawCard(Bank.server.getClients(roomID).get(u.ownerID), Card.drunk.getId(), 1);
				}
			} else {
				Animation.createStaticAnimation(u, "WATERCOLUMN", 2, 1200);
			}
			success = true;
		}
		if (Bank.isClient() && success) {
			int w = Properties.width / 5;
			Animation anim = new Animation(Util.boardOffsetX + Grid.tileSize * u.posX + Grid.tileSize / 2,
					Util.boardOffsetY + Grid.tileSize * (u.posY), 3000, Animation.TAG_ABILITY);
			anim.width = w / 2;
			anim.setData(this.getId());
			Display.currentScreen.particles.add(anim);
			success = true;
		}
		}
		Util.resolveUnits(roomID);
	}

	public void sendComm(String dat, Unit u, Unit t, int i, int j, int roomID) {
		Packet10CardComm pkt = new Packet10CardComm("1" + "%" + this.getId() + "%" + u.GUID + "%"
				+ (t != null ? t.GUID : -1) + "%" + i + "%" + j + "%" + dat);
		pkt.write(Bank.server.getRoom(roomID));
	}

	public void onAttack(Unit u, Unit t, int roomID) {
		boolean success = false;
		if(!u.isStunned()){
		if (this == crackle) {
			ArrayList<Unit> units = null;
			if (Bank.isServer())
				units = Bank.server.getUnits(roomID);
			else
				units = ((PanelBoard) Display.currentScreen).objects;
			for (int i = 0; i < units.size(); ++i) {
				if (units.get(i) != null) {
					Unit unit = units.get(i);
					if (unit.ownerID != u.ownerID && u.isPlayerUnit()) {
						unit.hurt(1, u);
						if (!Bank.isServer()) {
							Util.drawParticleLine(u.getPoint(), unit.getPoint(), 12, false, "SWIRL");
							Util.drawParticleLine(u.getPoint(), unit.getPoint(), 12, false, "SHATTER");
						}
					}
				}
			}
		}
		if(this == dreadharvest){
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit tar = targets.get(i);
				if (tar != null)
					if (!tar.isPlayerUnit() || tar.ownerID != u.ownerID)
						newtargets.remove(tar);
			}
			Unit tar = Util.getNearestUnit(u, newtargets);
			if (tar != null) {
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 8, true, "BLOOD");
				tar.heal(u.getAttack());
			}
		}
		if(this == frenzy){
			if(u.getHealth() > 0 && t.getHealth() > 0){
				u.attack(0, t);
				if(!Bank.isServer()){
					Util.drawParticleLine(u.getPoint(), t.getPoint(), 12, true, "BLOOD");
				}
			}
		}
		if (this == broswap) {
			int temp = u.getAttack();
			u.setAttack(u.getHealth());
			u.setHealthMax(temp);
			u.setHealth(temp);
			if (Bank.isServer()) {
				ArrayList<Card> avail = new ArrayList<Card>();
				for (Card c : Card.all) {
					if (c != null) {
						if (c.getCost() == u.getHealth())
							avail.add(c);
					}
				}
				ArrayList<Card> list = Util.createConjuringList(avail, 3);
				Bank.server.setPicks(list, roomID);
				Packet14ShowPicks pkt = new Packet14ShowPicks(u.ownerID, u.GUID, 0, list);
				pkt.write(Bank.server.getRoom(roomID));
			} else {
				Animation.createStaticAnimation(u, "EARTH", 2, 900);
			}
		}
		if (this == battlerage) {
			u.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
			success = true;
		}
		if (this == plague) {
			if (!t.getIsCommander()) {
				t.getAbilities().add(CardAbility.plague);
				success = true;
			}
		}
		if (Bank.isClient() && success) {
			int w = Properties.width / 5;
			Animation anim = new Animation(Util.boardOffsetX + Grid.tileSize * u.posX + Grid.tileSize / 2,
					Util.boardOffsetY + Grid.tileSize * (u.posY), 4000, Animation.TAG_ABILITY);
			anim.width = w / 2;
			anim.setData(this.getId());
			Display.currentScreen.particles.add(anim);
		}
		if (this == infectious) {
			for (CardAbility c : u.getAbilities()) {
				if (!t.getAbilities().contains(c))
					t.getAbilities().add(c);
			}
			for (Effect e : u.getEffects()) {
				t.addOrUpdateEffect(e);
			}
		}
		if (this == swarmlord) {
			ArrayList<Unit> units = null;
			if (Bank.isServer()) {
				units = Bank.server.getUnits(roomID);
			} else {
				units = ((PanelBoard) Display.currentScreen).objects;
			}
			for (Unit obj : units) {
				if (obj.ownerID != u.ownerID) {
					obj.hurt(2, u);
					if (!Bank.isServer()) {
						Util.drawParticleLine(u.getPoint(), obj.getPoint(), 15, true, "BLOOM");
						Animation.createStaticAnimation(obj, "BURST", 2, 600);
					}
				}
			}
		}
		}
		Util.resolveUnits(roomID);
	}

	public void onUnitDied(Unit caster, Unit died, int roomID) {
		boolean success = false;
		if(!caster.isStunned()){
		if (this == structurePassiveGraveyard) {
			if (Util.distance(caster.getTilePosition(), died.getTilePosition()) <= 3) {
				if (Bank.isServer())
					Bank.server.drawCard(Bank.server.getClients(roomID).get(died.ownerID), died.getTemplate().getId(), 1);
			}
		}
		if(this == runestaff && caster == died){
			if(Bank.isServer())
				Bank.server.drawCard(Bank.getServer().getRoom(roomID).clients.get(caster.ownerID), Card.equipmentRunestaff.getId(), 1);
			else{
				caster.say("Protect the staff!");
				Animation.createStaticAnimation(caster, "RUNE", 3, 500);
			}
		}
		if(this == longestride && caster == died){
			if(Bank.isServer())
				Bank.server.drawCard(Bank.getServer().getRoom(roomID).clients.get(caster.ownerID), Card.khan3.getId(), 1);
			else{
				caster.say("I will ride again!");
				Animation.createStaticAnimation(caster, "fx6_eaterFire", 2, 500);
			}
		}
		if(this == deathstun && caster == died){
			if (Bank.isServer()) {
				for (Unit u : Bank.server.getUnits(roomID)) {
					if (u != null && u != caster) {
						u.addEffect(new Effect(EffectType.stunned, 2, 2));
						success = true;
					}
				}
			} else {
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit u = Display.currentScreen.objects.get(i);
					if (u != null && u != caster) {
						u.addEffect(new Effect(EffectType.stunned, 2, 2));
						Animation.createStaticAnimation(u, "STONE", 1.5f, 700);
						success = true;
					}
				}
			}
		}
		if (this == lavalash && caster == died) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.ownerID == caster.ownerID | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(caster, newtargets);
			if (tar != null) {
				if(!Bank.isServer())
					Util.drawParticleLine(caster.getPoint(), tar.getPoint(), 8, false, "EXPLOSION");
				tar.hurt(3, caster);
			}
		}
		if (this == emperor && caster == died) {
			int turn = (Bank.isServer() ? Bank.server.getRoom(roomID).playerTurn : Util.turn % 2);
			if (caster.ownerID == turn) {
				if (Bank.isServer()) {
					ArrayList<Card> avail = new ArrayList<Card>();
					for (Card c : Card.all) {
						if (c != null) {
							if (c.isUnit()) {
								if (c.getUnit().getRank() == Card.RANK_REGAL)
									avail.add(c);
							}
						}
					}
					ArrayList<Card> list = Util.createConjuringList(avail, 3);
					Bank.server.setPicks(list, roomID);
					Packet14ShowPicks pkt = new Packet14ShowPicks(caster.ownerID, caster.GUID, 0, list);
					pkt.write(Bank.server.getRoom(roomID));
				} else {
					Animation.createStaticAnimation(caster, "SKULLGAS", 2, 800);
				}
			}
		}
		if (this == mummyspirit && caster == died) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.getIsCommander() | !t.isPlayerUnit() || t.ownerID != caster.ownerID)
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(caster, newtargets);
			if (tar != null) {
				Util.drawParticleLine(caster.getPoint(), tar.getPoint(), 8, false, "MAGIC");
				tar.addOrUpdateEffect(new Effect(EffectType.attack, 2, 999));
				tar.addOrUpdateEffect(new Effect(EffectType.health, 2, 999));
				tar.getAbilities().add(this);
			}
		}
		if(this == circle){
			if(died.ownerID == caster.ownerID){
				if (Bank.isServer()) {
					Card c = Card.spark;
					ArrayList<Card> avail = new ArrayList<Card>();
					for (Card card : Card.all) {
						if (card != null) {
							if (card instanceof UnitTemplate) {
								if (card.getUnit().getFamily() == Family.elemental)
									avail.add(card);
							}
						}
					}
					c = avail.get(Util.rand.nextInt(avail.size()));
					Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), c.getId(), 1);
				} else {
					Animation.createStaticAnimation(caster, "LEAVES", 2, 800);
					caster.say("The cycle continues...");
				}
			}
		}
		if (this == mummymagic && caster != died) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (!t.isPlayerUnit() || t.ownerID == caster.ownerID || t.getHealth() <= 0)
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(caster, newtargets);
			if (tar != null) {
				Util.drawParticleLine(caster.getPoint(), tar.getPoint(), 8, false, "SKULLGAS");
				tar.hurt(2, caster);
			}
		}
		if (this == fleshrebirth && caster == died) {
			int piles = 0;
			if (Bank.isServer()) {
				ArrayList<CardShell> newhand = (ArrayList<CardShell>) Bank.server.getClients(roomID).get(caster.ownerID).hand
						.clone();
				for (CardShell c : Bank.server.getClients(roomID).get(caster.ownerID).hand) {
					if (c.getCard() == Card.fleshpile) {
						++piles;
						newhand.remove(c);
					}
				}
				Bank.server.getClients(roomID).get(caster.ownerID).hand = newhand;
				Bank.server.addUnit((UnitTemplate) Card.butcher, caster.posX, caster.posY, caster.ownerID, roomID);
			} else {
				ArrayList<CardShell> newhand = (ArrayList<CardShell>) ((PanelBoard) Display.currentScreen).hand.clone();
				for (CardShell c : ((PanelBoard) Display.currentScreen).hand) {
					if (c.getCard() == Card.fleshpile) {
						++piles;
						newhand.remove(c);
					}
				}
				((PanelBoard) Display.currentScreen).hand = newhand;
				Animation.createStaticAnimation(caster, "SKULLGAS", 2, 800);
				if (piles > 0)
					caster.say("MAGGUT BACK!");
			}
			for (Unit u : (Bank.isServer() ? Bank.server.getUnits(roomID) : Display.currentScreen.objects)) {
				if (u.getTemplate() == Card.butcher) {
					u.addOrUpdateEffect(new Effect(EffectType.attack, piles, 999));
					u.addOrUpdateEffect(new Effect(EffectType.attack, piles, 999));
					break;
				}
			}
		}
		if (this == eldritchParasite) {
			if (caster == died && caster.isPlayerUnit()) {
				if (Bank.isServer()) {
					Bank.server.getClients(roomID).get(caster.ownerID).baseGold++;
					Bank.server.getClients(roomID).get(caster.ownerID).gold++;
				} else {
					Animation.createStaticAnimation(caster, "POOL", 2, 500);
					if (Util.clientID == caster.ownerID) {
						Util.maxGold++;
						Util.gold++;
					}
				}
			}
		}
		if (this == vengeance) {
			if (died.getFamily() == Family.beast) {
				if (!Bank.isServer())
					Animation.createStaticAnimation(caster, "LEAVES", 2, 700);
				caster.addOrUpdateEffect(new Effect(EffectType.attack, 2, 999));
			}
		}
		if (caster == died && this == litFuse) {
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					int x1 = caster.posX - 1 + i, y1 = caster.posY - 1 + j;
					for (int k = 0; k < 5; ++k) {
						AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + x1 * Grid.tileSize,
								Util.boardOffsetY + y1 * Grid.tileSize, Grid.tileSize, 600).addFrameSet("EXPLOSION");
						Display.currentScreen.particles.add(p);
					}
					Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
					if (t != null && t != caster) {
						t.hurt(3, caster);
						success = true;
					}
				}
			}
		}
		if (caster == died && this == scrap) {
			int x = caster.getEnergy();
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.getFamily() != Family.mech | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(caster, newtargets);
			if (tar != null) {
				tar.addOrUpdateEffect(new Effect(EffectType.attack, x, 999));
				tar.addOrUpdateEffect(new Effect(EffectType.health, x, 999));
				if (!Bank.isServer()) {
					Util.drawParticleLine(caster.getPoint(), tar.getPoint(), 20, false, "IRON");
					Animation.createStaticAnimation(tar, "IRON", 2, 900);
				}
			}
		}
		if (caster == died && this == spark) {
			ArrayList<Unit> targets = new ArrayList<Unit>();
			if (Bank.isServer())
				targets = Bank.server.getUnits(roomID);
			else
				targets = Display.currentScreen.objects;
			ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
			for (int i = 0; i < targets.size(); ++i) {
				Unit t = targets.get(i);
				if (t != null)
					if (t.ownerID != caster.ownerID | !t.isPlayerUnit())
						newtargets.remove(t);
			}
			Unit tar = Util.getNearestUnit(caster, newtargets);
			if (tar != null) {
				tar.addEnergy1(3);
				if (!Bank.isServer()) {
					Util.drawParticleLine(caster.getPoint(), tar.getPoint(), 20, false, "SWIRL");
					Animation.createStaticAnimation(tar, "fx7_energyBall", 2, 900);
				}
			}
		}
		if (this == harvest) {
			caster.heal(1);
			if (Bank.isServer())
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), -1, 1);
			Animation.createStaticAnimation(caster, "VOIDCAST", 2, 800);
			success = true;
		}
		if (this == packbeast && caster == died) {
			if (Bank.isServer()) {
				ArrayList<Card> avail = new ArrayList<Card>();
				for (Card c : Card.all) {
					if (c != null) {
						if (c.isSpell())
							avail.add(c);
					}
				}
				Card c = avail.get(Util.rand.nextInt(avail.size()));
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), c.getId(), 1);
			} else {
				Animation.createStaticAnimation(caster, "RUNE", 1.25f, 500);
			}
		}
		if (this == bloodpuppet) {
			if (caster == died) {
				ArrayList<Unit> units = null;
				if (Bank.isServer()) {
					units = Bank.server.getUnits(roomID);
				} else {
					units = ((PanelBoard) Display.currentScreen).objects;
				}
				for (Unit obj : units) {
					if (obj.getTemplate().equals(Card.bloodpuppet)) {
						obj.flagRemove = true;
						if (!Bank.isServer()) {
							Util.drawParticleLine(obj.getPoint(), caster.getPoint(), 8, false, "BLOOD");
						}
					}
				}
			}
		}
		if (this == stormsoul) {
			if (caster == died) {
				Util.boardState = (CardBoardstate) Card.stateNormal;
				if (!Bank.isServer())
					Animation.createStaticAnimation(caster, "NOVA", 2, 700);
				ArrayList<Unit> units = null;
				if (Bank.isServer()) {
					units = Bank.server.getUnits(roomID);
				} else {
					units = ((PanelBoard) Display.currentScreen).objects;
				}
				for (Unit obj : units) {
					obj.heal(2);
					if (!Bank.isServer()) {
						Util.drawParticleLine(caster.getPoint(), obj.getPoint(), 15, true, "SWIRL");
					}
				}
			}
		}
		if (this == swarmlord) {
			if (died.getFamily() == Family.kratt) {
				Util.drawParticleLine(died.getPoint(), caster.getPoint(), 10, false, "BOON");
				ArrayList<Unit> units = null;
				if (Bank.isServer()) {
					units = Bank.server.getUnits(roomID);
				} else {
					units = ((PanelBoard) Display.currentScreen).objects;
				}
				for (Unit obj : units) {
					if (obj.ownerID != caster.ownerID) {
						obj.hurt(2, caster);
						if (!Bank.isServer()) {
							Util.drawParticleLine(caster.getPoint(), obj.getPoint(), 15, true, "BLOOM");
							Animation.createStaticAnimation(obj, "BURST", 2, 600);
						}
					}
				}
			}
		}
		if (this == unstablegateway && died == caster) {
			Unit comm = null;
			if (Bank.isServer()) {
				comm = Bank.server.getCommander(Bank.server.getClients(roomID).get(caster.ownerID));
			} else {
				comm = ((PanelBoard) Display.currentScreen).getCommanderForPlayer(caster.ownerID, roomID);
				Util.drawParticleLine(caster.getPoint(), comm.getPoint(), 15, true, "SWIRL");
				Animation.createStaticAnimation(caster, "NOVA", 1.75f, 800);
				Animation.createStaticAnimation(comm, "MANABURN", 1.25f, 1000);
			}
			comm.hurt(4, caster);
		}
		if (this == eldritchLegion && died.getFamily() == Family.eldritch) {
			caster.addOrUpdateEffect(new Effect(EffectType.attack, 2, 999));
			caster.addOrUpdateEffect(new Effect(EffectType.health, 2, 999));
			Animation.createStaticAnimation(caster, "VOID", 2, 1200);
			Animation.createStaticAnimation(died, "SKULLGAS", 2, 900);
			success = true;
		}
		if (this == raise) {
			if (Bank.server != null)
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), Card.wastewalker.getId(), 1);
			Animation.createStaticAnimation(caster, "LEAVES", 2, 1000);
			success = true;
		}
		if (this == cryptthief) {
			Animation.createStaticAnimation(caster, "SKULLGAS", 2, 1200);
			caster.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
			caster.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
			success = true;
		}
		if (this == guilt) {
			if (died != caster && caster != null) {
				Animation.createStaticAnimation(caster, "SKULLGAS", 2, 1200);
				Animation.createStaticAnimation(caster, "FLASH", 2, 900);
				caster.hurt(2, null);
				success = true;
			}
		}
		if (this == reanimate) {
			if (died.getTemplate().getCost() > 0 && died.getTemplate() != caster.getTemplate()) {
				if (Bank.isServer()) {
					Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), died.getTemplate().getId(), 1);
				}
				Animation.createStaticAnimation(caster, "MAGIC", 2, 800);
				success = true;
			}
		}
		if (this == deckwormdeath) {
			if (caster == died) {
				if (Bank.isServer()) {
					PlayerMP pl = Bank.server.getClients(roomID).get(caster.ownerID);
					pl.deck.add(new CardShell(Card.deckworm));
					Unit comm = Bank.server.getCommander(pl);
					comm.hurt(2, comm);
				} else {
					Unit comm = ((PanelBoard) Display.currentScreen).getCommanderForPlayer(caster.ownerID, roomID);
					comm.hurt(2, comm);
				}
			}
		}
		if (this == epidemic) {
			if (caster == died) {
				if (Bank.isServer()) {
					for (Unit u : Bank.server.getUnits(roomID)) {
						if (u != null && u != caster) {
							u.hurt(2, caster);
							success = true;
						}
					}
				} else {
					for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
						Unit u = Display.currentScreen.objects.get(i);
						if (u != null && u != caster) {
							u.hurt(2, caster);
							Animation.createStaticAnimation(u, "SKULLGAS", 1.5f, 700);
							success = true;
						}
					}
				}
			}
		}
		if (this == fungus) {
			if (caster == died) {
				if (Bank.isServer())
					Bank.server.addUnit((UnitTemplate) Card.fungalwart, caster.posX, caster.posY,
							(caster.ownerID == 0 ? 1 : 0), roomID);
				else
					Animation.createStaticAnimation(caster, "BLOOM", 3, 800);
			}
		}
		if (Bank.isClient() && success) {
			int w = Properties.width / 5;
			Animation anim = new Animation(Util.boardOffsetX + Grid.tileSize * caster.posX + Grid.tileSize / 2,
					Util.boardOffsetY + Grid.tileSize * (caster.posY), 4000, Animation.TAG_ABILITY);
			anim.width = w / 2;
			anim.setData(this.getId());
			Display.currentScreen.particles.add(anim);
		}
		}
		Util.resolveUnits(roomID);
	}

	public void onCast(Unit caster, Unit target, int x, int y, int roomID) {
		if(!caster.isStunned()){
		if (Bank.isClient()) {
			int w = Properties.width / 5;
			Animation anim = new Animation(Util.boardOffsetX + Grid.tileSize * caster.posX + Grid.tileSize / 2,
					Util.boardOffsetY + Grid.tileSize * (caster.posY), 4000, Animation.TAG_ABILITY);
			anim.width = w / 2;
			anim.setData(this.getId());
			anim.setCs(new CardShell(caster.getTemplate()));
			Display.currentScreen.particles.add(anim);
			anim.setOrigin(Util.boardOffsetX + Grid.tileSize * x, Util.boardOffsetY + Grid.tileSize * y);
			if (target != null) {
				anim.setOrigin(Util.boardOffsetX + Grid.tileSize * target.posX,
						Util.boardOffsetY + Grid.tileSize * (target.posY));
				anim.setC((target.ownerID == caster.ownerID ? Color.GREEN : Color.RED));
			}
		}
		if (this == vincdeath) {
			if (Bank.isServer()) {
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), caster.getTemplate().getId(), 1);
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), -1, 1);
				caster.kill();
			} else {
				Animation.createStaticAnimation(caster, "VOID", 2, 800);
			}
		}
		if(this == shieldbash){
			target.hurt(caster.getHealth(), caster);
			if(!Bank.isServer()){
				Animation.createStaticAnimation(target, "fx10_blackExplosion", 2, 600);
				Animation.createStaticAnimation(target, "EARTH", 3, 400);
			}
		}
		if(this == shock){
			target.addOrUpdateEffect(new Effect(EffectType.stunned, 1, 2));
			if(!Bank.isServer()){
				Util.drawParticleLine(caster.getPoint(), target.getPoint(), 15, false, "SWIRL");
				Animation.createStaticAnimation(target, "fx8_lighteningBall", 3, 750);
			}
		}
		if(this == stun){
			target.addOrUpdateEffect(new Effect(EffectType.stunned, 1, 2));
			if(!Bank.isServer()){
				Util.drawParticleLine(caster.getPoint(), target.getPoint(), 8, false, "BWNOVA");
				Animation.createStaticAnimation(target, "STONE", 2, 750);
			}
		}
		if(this == stun1){
			target.addOrUpdateEffect(new Effect(EffectType.stunned, 1, 4));
			if(!Bank.isServer()){
				Util.drawParticleLine(caster.getPoint(), target.getPoint(), 8, false, "BWNOVA");
				Animation.createStaticAnimation(target, "STONE", 2, 750);
			}
		}
		if (this == elementalcreate) {
			if (Bank.isServer()) {
				ArrayList<Card> avail = new ArrayList<Card>();
				for (Card c : Card.all) {
					if (c != null) {
						if (Util.findMatch(c, "elemental") > 0)
							avail.add(c);
					}
				}
				ArrayList<Card> list = Util.createConjuringList(avail, 3);
				Bank.server.setPicks(list, roomID);
				Packet14ShowPicks pkt = new Packet14ShowPicks(caster.ownerID, caster.GUID, 0, list);
				pkt.write(Bank.server.getRoom(roomID));
			} else {
				Animation.createStaticAnimation(caster, "fx7_energyBall", 2, 800);
			}
		}
		if(this == crystalize){
			caster.addOrUpdateEffect(new Effect(EffectType.attack, 2, 999));
			caster.addOrUpdateEffect(new Effect(EffectType.health, 2, 999));
			caster.addOrUpdateEffect(new Effect(EffectType.speed, -1, 999));
			if(!Bank.isServer()){
				Animation.createStaticAnimation(caster, "STONE", 2, 800);
			}
		}
		if(this == firebomb){
			if(!Bank.isServer()){
				Util.drawParticleLine(caster.getPoint(), target.getPoint(), 12, false, "EXPLOSION");
				Animation.createStaticAnimation(target, "fx3_fireBall", 3, 400);
			}
			target.hurt(4, caster);
		}
		if(this == yearpass){
			if(!Bank.isServer()){
				caster.say("The end of all things draws ever closer!");
				Animation.createStaticAnimation(caster, "fx3_fireBall", 2, 500);
			}
			ArrayList<Unit> list = null;
			if(Bank.isServer())list = Bank.server.getUnits(roomID);
			else list = Display.currentScreen.objects;
			for(Unit u : list){
				if(u!=null){
					for(CardAbility ca : u.getAbilities()){
						if(ca!=null){
							ca.onPlayed(u, roomID);
							ca.onUnitDied(u, u, roomID);
						}
					}
					if(!Bank.isServer())Animation.createStaticAnimation(u, "fx10_blackExplosion", 2, 500);
				}
			}
		}
		if(this == fireblade){
			if(!Bank.isServer()){
				Util.drawParticleLine(caster.getPoint(), target.getPoint(), 14, true, "FIRE");
				Animation.createStaticAnimation(target, "fx2_swordFire", 2, 750);
			}
			target.addOrUpdateEffect(new Effect(EffectType.attack, 3, 1));
		}
		if(this == hoofstorm){
			ArrayList<Unit> uns = (Bank.isServer() ? Bank.server.getRoom(roomID).units : Display.currentScreen.objects);
			for(Unit unit : uns){
				unit.hasMoved = false;
				if(!Bank.isServer())Animation.createStaticAnimation(unit, "CHARGE", 2, 500);
			}
		}
		if(this == sealegs){
			target.getAbilities().add(CardAbility.amphibious);
			if(!Bank.isServer())
			Animation.createStaticAnimation(target, "WATERBIRD", 2, 800);
		}
		if(this == deadwoodpulse){
			caster.say("The Deadwood weeps...");
			ArrayList<Unit> list = null;
			if(Bank.isServer())list = Bank.server.getUnits(roomID);
			else list = Display.currentScreen.objects;
			for(Unit u : list){
				if(u.ownerID == caster.ownerID && u.getTemplate() == Card.root){
					u.setHealth(0);
					u.hurt(100, caster);
					for (int i = 0; i < 3; ++i) {
						for (int j = 0; j < 3; ++j) {
							int x1 = u.posX - 1 + i, y1 = u.posY - 1 + j;
							if(!Bank.isServer())
							for (int k = 0; k < 5; ++k) {
								AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + x1 * Grid.tileSize,
										Util.boardOffsetY + y1 * Grid.tileSize, Grid.tileSize, 600).addFrameSet("FELFIRE");
								Display.currentScreen.particles.add(p);
							}
							Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
							if (t != null && t != caster) {
								t.hurt(4, caster);
							}
						}
					}
					caster.addOrUpdateEffect(new Effect(EffectType.attack, 2, 999));
					caster.addOrUpdateEffect(new Effect(EffectType.health, 2, 999));
				}
			}
		}
		if (this == cower) {
			caster.Protect(target, 999);
			Animation.createStaticAnimation(caster, "HEALCOLUMN", 2, 500);
			caster.say("Protect me, worm!");
		}
		if (this == reload) {
			if (!Bank.isServer()) {
				Animation.createStaticAnimation(caster, "CHARGE", 2, 800);
				Animation.createStaticAnimation(caster, "BWNOVA", 1, 1200);
			}
			for (CardAbility a : caster.getAbilities()) {
				a.onPlayed(caster, roomID);
			}
		}
		if (this == releaseSouls) {
			if (target != null) {
				if (target.getTemplate() == Card.sandgolem) {
					target.setAttack(caster.getAttack());
					target.setHealth(caster.getHealth());
					target.setSpeed(3);
					if (!Bank.isServer()) {
						Util.drawParticleLine(caster.getPoint(), target.getPoint(), 10, true, "SKULLGAS");
						Animation.createStaticAnimation(target, "HEALCOLUMN", 2, 800);
						Animation.createStaticAnimation(target, "CHARGE", 2, 500);
					}
				}
			}
		}
		if (this == repair) {
			target.setEquipmentDurability((byte) (target.getEquipmentDurability() + 1));
			Util.drawParticleLine(caster.getPoint(), target.getPoint(), 7, true, "CHARGE");
			Animation.createStaticAnimation(target, "SLOW", 2, 800);
			caster.say("That should do the trick!");
		}
		if (this == crusade) {
			if (target.getFamily() == Family.eldritch || target.getFamily() == Family.demon
					|| target.getFamily() == Family.undead) {
				if (!Bank.isServer()) {
					caster.say("BURN, HEATHEN!");
					Util.drawParticleLine(caster.getPoint(), target.getTilePosition(), 10, true, "INCINIERATE");
					Animation.createStaticAnimation(target, "INCINERATION", 2, 1000);
				} else {
					ArrayList<Card> avail = new ArrayList<Card>();
					for (Card c : Card.all) {
						if (c != null) {
							if (c.isUnit()) {
								UnitTemplate unit = c.getUnit();
								if (unit.getFamily() == target.getFamily())
									avail.add(c);
							}
						}
					}
					ArrayList<Card> list = Util.createConjuringList(avail, 3);
					Bank.server.setPicks(list, roomID);
					Packet14ShowPicks pkt = new Packet14ShowPicks(caster.ownerID, caster.GUID, 0, list);
					pkt.write(Bank.server.getRoom(roomID));
				}
				target.hurt(3, caster);
			}
		}
		if (this == forge) {
			if (Bank.isServer()) {
				ArrayList<Card> avail = new ArrayList<Card>();
				for (Card c : Card.all) {
					if (c != null) {
						if (c.isEquipment())
							avail.add(c);
					}
				}
				ArrayList<Card> list = Util.createConjuringList(avail, 3);
				Bank.server.setPicks(list, roomID);
				Packet14ShowPicks pkt = new Packet14ShowPicks(caster.ownerID, caster.GUID, 0, list);
				pkt.write(Bank.server.getRoom(roomID));
			}
		}
		if (this == protect) {
			target.Protect(caster, 2);
			Animation.createStaticAnimation(target, "SHIELD", 2, 750);
		}
		if (this == devourprey) {
			if (target.getAttack() <= 3) {
				target.hurt(target.getHealth(), caster);
				Animation.createStaticAnimation(target, "BLOOD", 2.5f, 650);
			}
		}
		if (this == sprint) {
			caster.addOrUpdateEffect(new Effect(EffectType.speed, 4, 1));
			if (!Bank.isServer())
				Animation.createStaticAnimation(caster, "CHARGE", 0.8f, 500);
		}
		if (this == poisonspit) {
			int dmg = 1;
			Util.drawParticleLine(caster.getPoint(), target.getPoint(), 10, true, "BURST");
			target.hurt(dmg, caster);
			Animation.createStaticAnimation(target, "BLOOD", 2.5f, 650);
		}
		if (this == chomp) {
			int dmg = 1;
			if (caster.getHealth() < caster.getHealthMax())
				dmg++;
			target.hurt(dmg, caster);
			Animation.createStaticAnimation(target, "BLOOD", 2.5f, 650);
		}
		if (this == hatchSiren) {
			Unit comm = null;
			if (Bank.isServer())
				comm = Bank.server.getCommander(Bank.server.getClients(roomID).get(caster.ownerID));
			else
				comm = ((PanelBoard) Display.currentScreen).getCommanderForPlayer(caster.ownerID, roomID);
			comm.setTemplate((UnitTemplate) Card.commSiren);
			comm.resummon();
			caster.hurt(caster.getHealth(), caster);
			Animation.createStaticAnimation(caster, "SPLASH", 1.75f, 850);
			Animation.createStaticAnimation(comm, "WATERBIRD", 3.5f, 1250);
		}
		if (this == unequip) {
			if (caster.getEquipment() != null) {
				if (Bank.isServer()) {
					Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), caster.getEquipment().getId(), 1);
				}
				Animation.createStaticAnimation(caster, "CHARGE", 0.3f, 600);
				caster.getEquipment().equipUnit(caster, false);
			}
		}
		if (this == deathjaw) {
			target.addOrUpdateEffect(new Effect(EffectType.speed, -4, 2));
			Animation.createStaticAnimation(caster, "SPLASH", 1.75f, 550);
			Animation.createStaticAnimation(target, "BLOOD", 2.5f, 800);
		}
		if (this == unstablegateway) {
			int sx = caster.posX, sy = caster.posY;
			caster.posX = x;
			caster.posY = y;
			target.posX = sx;
			target.posY = sy;
			Util.drawParticleLine(caster.getPoint(), target.getPoint(), 10, true, "SWIRL");
			Animation.createStaticAnimation(caster, "RUNE", 1.8f, 800);
			Animation.createStaticAnimation(target, "RUNE", 1.8f, 800);

		}
		if (this == raiseEarth) {
			if (Bank.isServer()) {
				Bank.server.getGrid(roomID).setTileID(x, y, GridBlock.boulder.getID());
			} else {
				((PanelBoard) Display.currentScreen).getGrid().setTileID(x, y, GridBlock.boulder.getID());
				Animation.createStaticAnimation(caster, "EARTH", 3, 1200);
			}
		}
		if (this == scorchground) {
			if (Bank.isServer()) {
				Bank.server.getGrid(roomID).setTileID(x, y, GridBlock.scorched.getID());
			} else {
				((PanelBoard) Display.currentScreen).getGrid().setTileID(x, y, GridBlock.scorched.getID());
				Animation.createStaticAnimation(Util.boardOffsetX + x * Grid.tileSize + Grid.tileSize / 2, Util.boardOffsetY + y * Grid.tileSize + Grid.tileSize / 2, "fx4_sign_of_fire", 1, 700);
			}
		}
		if (this == corruptground) {
			if (Bank.isServer()) {
				Bank.server.getGrid(roomID).setTileID(x, y, GridBlock.voidtile.getID());
			} else {
				((PanelBoard) Display.currentScreen).getGrid().setTileID(x, y, GridBlock.voidtile.getID());
				Animation.createStaticAnimation(Util.boardOffsetX + x * Grid.tileSize + Grid.tileSize / 2, Util.boardOffsetY + y * Grid.tileSize + Grid.tileSize / 2, "VOIDFLAME", 1, 700);
			}
		}
		if (this == terraform) {
			if (Bank.isServer()) {
				Bank.server.getGrid(roomID).setTileID(x, y, GridBlock.ground.getID());
			} else {
				((PanelBoard) Display.currentScreen).getGrid().setTileID(x, y, GridBlock.ground.getID());
				Animation.createStaticAnimation(Util.boardOffsetX + x * Grid.tileSize + Grid.tileSize / 2, Util.boardOffsetY + y * Grid.tileSize + Grid.tileSize / 2, "EARTH", 1, 700);
			}
		}
		if (this == krattRitual) {
			if (target.getFamily() == Family.kratt) {
				for (CardAbility c : target.getAbilities()) {
					if (!caster.getAbilities().contains(c))
						caster.getAbilities().add(c);
				}
				if (Bank.isServer())
					Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), target.getTemplate().getId(), 1);
				target.hurt(target.getHealth(), caster);
			}
		}
		if (this == stormbolt) {
			int dmg = 2;
			int count = 0;
			Grid grid = null;
			if (Bank.isServer()) {
				grid = Bank.server.getGrid(roomID);
			} else {
				grid = ((PanelBoard) Display.currentScreen).getGrid();
			}
			for (int i = 0; i < grid.getCore()[0].length; ++i) {
				for (int j = 0; j < grid.getCore()[1].length; ++j) {
					if (grid.getCore()[i][j] == GridBlock.water.getID())
						++count;
				}
			}
			if (count >= 6)
				dmg = 4;
			target.hurt(dmg, caster);
			Util.drawParticleLine(caster.getPoint(), target.getPoint(), 10, true, "BUBBLE");
			Animation.createStaticAnimation(target, "THUNDER", 1.7f, 750);
		}
		if (this == waverider) {
			GridBlock tile = caster.getTileAtPosition(x, y);
			if (tile == GridBlock.water) {
				caster.posX = x;
				caster.posY = y;
				Animation.createStaticAnimation(caster, "SPLASH", 3, 600);
			}
		}
		if (this == stinkbomb) {
			int dmg = 7;
			int tiles = Util.distance(caster.getTilePosition(), target.getTilePosition());
			dmg -= tiles;
			target.hurt(dmg, caster);
			Util.drawParticleLine(caster.getPoint(), target.getPoint(), 14, true, "BURST");
		}
		if (this == deathpotion) {
			target.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
			if (target.getFamily() == Family.kratt) {
				target.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
			}
			Animation.createStaticAnimation(target, "SHIELD", 1.5f, 700);
			Animation.createStaticAnimation(target, "BURST", 2, 900);
		}
		if (this == wavecrash) {
			ArrayList<Unit> units = null;
			ArrayList<Unit> attackers = new ArrayList<Unit>();
			if (Bank.isServer()) {
				units = Bank.server.getUnits(roomID);
			} else {
				units = ((PanelBoard) Display.currentScreen).objects;
			}
			for (Unit u : units) {
				if (u.getAbilities().contains(CardAbility.amphibious)
						|| u.getAbilities().contains(CardAbility.aquatic)) {
					attackers.add(u);
					if (!Bank.isServer())
						Util.drawParticleLine(u.getPoint(), target.getPoint(), 20, false, "BUBBLE");
				}
			}
			for (Unit u : attackers) {
				target.hurt(1, u);
			}
			if (!Bank.isServer())
				Animation.createStaticAnimation(target, "SPLASH", 2, 800);
		}
		if (this == healingwaters) {
			Grid grid = null;
			if (Bank.isServer()) {
				grid = Bank.server.getGrid(roomID);
			} else {
				grid = ((PanelBoard) Display.currentScreen).getGrid();
			}
			for (int i = 0; i < grid.getCore()[0].length; ++i) {
				for (int j = 0; j < grid.getCore()[1].length; ++j) {
					if (grid.getCore()[i][j] == GridBlock.water.getID()) {
						Unit u = null;
						if (Bank.isServer()) {
							u = Bank.server.getUnitOnTile(i, j, roomID);
						} else {
							u = ((PanelBoard) Display.currentScreen).getUnitOnTile(i, j, roomID);
						}
						if (u != null) {
							if (u.ownerID == caster.ownerID) {
								u.heal(4);
								if (!Bank.isServer()) {
									Animation.createStaticAnimation(u, "HEALCOLUMN", 1.5f, 900);
									Animation.createStaticAnimation(u, "WATERBIRD", 1.5f, 700);
								}
							}
						}
					}
				}
			}
		}
		if (this == whirlpool) {
			Grid grid = null;
			if (Bank.isServer()) {
				grid = Bank.server.getGrid(roomID);
			} else {
				grid = ((PanelBoard) Display.currentScreen).getGrid();
			}
			for (int i = 0; i < grid.getCore()[0].length; ++i) {
				for (int j = 0; j < grid.getCore()[1].length; ++j) {
					if (grid.getCore()[i][j] == GridBlock.water.getID()) {
						Unit u = null;
						if (Bank.isServer()) {
							u = Bank.server.getUnitOnTile(i, j, roomID);
						} else {
							u = ((PanelBoard) Display.currentScreen).getUnitOnTile(i, j, roomID);
						}
						if (u != null) {
							if (u.ownerID != caster.ownerID) {
								u.hurt(3, caster);
								if (!Bank.isServer())
									Animation.createStaticAnimation(u, "WATERCOLUMN", 2, 700);
							}
						}
					}
				}
			}
		}
		if (this == agleanVision) {
			if (Bank.isServer()) {
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), -1, 1);
			} else {
				Animation.createStaticAnimation(caster, "BLUELIGHT", 1, 750);
			}
		}
		if (this == splash) {
			if (Bank.isServer()) {
				Bank.server.getGrid(roomID).setTileID(x, y, GridBlock.water.getID());
			} else {
				((PanelBoard) Display.currentScreen).getGrid().setTileID(x, y, GridBlock.water.getID());
				Animation.createStaticAnimation(caster, "POOL", 2, 900);
			}
		}
		if (this == uproot) {
			caster.setSpeed(4);
			caster.addOrUpdateEffect(new Effect(EffectType.attack, 4, 999));
			caster.addOrUpdateEffect(new Effect(EffectType.health, 3, 999));
			Animation.createStaticAnimation(caster, "LEAVES", 2, 700);
		}
		if (this == blacklotus) {
			if (Bank.isServer()) {
				for (PlayerMP pl : Bank.server.getClients(roomID)) {
					if (Bank.server.getClients(roomID).indexOf(pl) != caster.ownerID) {
						Bank.server.drawCard(pl, Card.deathlotus.getId(), 1);
					}
				}
			} else {
				Animation.createStaticAnimation(caster, "BURST", 2, 1000);
				Animation.createStaticAnimation(caster, "SKULLGAS", 2, 800);
			}
		}
		if (this == spore) {
			target.getAbilities().add(CardAbility.fungus);
			Util.drawParticleLine(caster.getPoint(), target.getPoint(), 10, true, "BLOOM");
			Animation.createStaticAnimation(target, "BURST", 1.75f, 800);
		}
		if (this == eldritchRitual) {
			ArrayList<Card> avail = new ArrayList<Card>();
			for (Card card : Card.all) {
				if (card != null)
					if (card instanceof UnitTemplate) {
						if (card.getUnit().getFamily() == Family.eldritch) {
							avail.add(card);
						}
					}
			}
			Card sel = avail.get(Util.rand.nextInt(avail.size()));
			if (Bank.isServer())
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), sel.getId(), 1);
			Animation.createStaticAnimation(target, "MAGIC", 0.75f, 1200);
			Animation.createStaticAnimation(target, "VOIDCAST", 2.5f, 1800);
		}
		if (this == eldritchMorph) {
			if (Bank.isServer()) {
				ArrayList<Card> avail = new ArrayList<Card>();
				for (Card card : Card.all) {
					if (card != null)
						if (card instanceof UnitTemplate) {
							if (card.getUnit().getFamily() == Family.eldritch) {
								avail.add(card);
							}
						}
				}
				Card sel = avail.get(Util.rand.nextInt(avail.size()));
				target.setTemplate((UnitTemplate) sel);
				wildmagic.sendComm(sel.getId() + "", target, null, 0, 0, roomID);
			}
			Animation.createStaticAnimation(target, "MOON", 2, 800);
			Animation.createStaticAnimation(target, "VOIDCAST", 1, 1000);
		}
		if (this == eldritchPower) {
			if (Bank.isServer()) {
				for (CardShell c : Bank.server.getClients(roomID).get(caster.ownerID).hand) {
					if (c.getCard().getUnit() != null) {
						if (c.getCard().getUnit().getFamily() == Family.eldritch) {
							for (CardAbility a : c.getCard().getUnit().getAbilities()) {
								if (!caster.getAbilities().contains(a)) {
									CardAbility.eldritchPower.sendComm((a != null ? a.getId() : 1) + "", caster, caster,
											caster.posX, caster.posY, roomID);
									caster.getAbilities().add(a);
									a.onPlayed(caster, roomID);
								}
							}
						}
					}
				}
			}
		}
		if (this == eldritchCorruption) {
			if (Bank.isServer()) {
				ArrayList<Card> avail = new ArrayList<Card>();
				for (Card card : Card.all) {
					if (card instanceof UnitTemplate) {
						if (card.getUnit().getFamily() == Family.eldritch) {
							avail.add(card);
						}
					}
				}
				Card sel = avail.get(Util.rand.nextInt(avail.size()));
				caster.setTemplate((UnitTemplate) sel);
				wildmagic.sendComm(sel.getId() + "", caster, null, 0, 0, roomID);
			}
			Animation.createStaticAnimation(caster, "RUNE", 2, 800);
			Animation.createStaticAnimation(caster, "VOIDCAST", 1.25f, 1200);
		}
		if (this == oblivion) {
			Animation.createStaticAnimation(caster, "VOIDCAST", 2, 800);
			if (Bank.isServer())
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), Card.rageofkholos.getId(), 1);
		}
		if (this == deepmurk) {
			Animation.createStaticAnimation(caster, "VOIDCAST", 2, 800);
			Animation.createStaticAnimation(caster, "POOL", 3, 3000);
			if (Bank.isServer())
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), Card.rlgybar.getId(), 1);
		}
		if (this == eldritchTorment) {
			if (Bank.isServer())
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), Card.eldritchvillager.getId(), 1);
		}
		if (this == eldritchCrackle) {
			caster.hurt(1, caster);
			target.hurt(1, caster);
			Util.drawParticleLine(target.getPoint(), caster.getPoint(), 25, false, "SWIRL");
			Animation.createStaticAnimation(target, "THUNDER", 1.5f, 800);
		}
		if (this == eldritchHeal) {
			target.hurt(3, caster);
			if (target.getHealth() > 0)
				target.heal(7);
			Animation.createStaticAnimation(target, "VOID", 2, 800);
			Animation.createStaticAnimation(target, "POOL", 2, 1250);
		}
		if (this == eldritchSlam) {
			int dmg = 0;
			if (Bank.isServer()) {
				for (CardShell c : Bank.server.getClients(roomID).get(caster.ownerID).hand) {
					if (c.getCard().getUnit() != null) {
						if (c.getCard().getUnit().getFamily() == Family.eldritch) {
							if (c.getCard().getUnit().getFamily() == Family.eldritch) {
								dmg++;
							}
						}
					}
				}
				target.hurt(dmg, caster);
				this.sendComm(dmg + "", caster, target, x, y, roomID);
			} else {
				Util.drawParticleLine(target.getPoint(), caster.getPoint(), 30, false, "SWIRL");
				Animation.createStaticAnimation(target, "MAGIC", 1.5f, 800);
			}
		}
		if (this == lunge) {
			new ClipShell("stomp.wav").start();
			Util.drawParticleLine(caster.getPoint(),
					new Point(Util.boardOffsetX + x * Grid.tileSize + Grid.tileSize / 2,
							Util.boardOffsetY + y * Grid.tileSize + Grid.tileSize / 2),
					10, false, "S");
			caster.posX = x;
			caster.posY = y;
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					int x1 = caster.posX - 1 + i, y1 = caster.posY - 1 + j;
					AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + x1 * Grid.tileSize,
							Util.boardOffsetY + y1 * Grid.tileSize, Grid.tileSize, 700).addFrameSet("SLASH");
					Display.currentScreen.particles.add(p);
					Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
					if (t != null && t != caster) {
						t.hurt(1, caster);
					}
				}
			}
		}
		if (this == legion) {
			if (Bank.isClient()) {
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit u = Display.currentScreen.objects.get(i);
					if (u != null)
						if (u.getFamily() == Family.werewolf) {
							Util.drawParticleLine(u.getPoint(), caster.getPoint(), 20, false, "BLOOD");
							caster.addEnergy1(1);
							caster.addOrUpdateEffect(new Effect(EffectType.speed, 1, 999));
							Animation.createStaticAnimation(caster, "HEALCOLUMN", 2, 1000);
						}
				}
			}
			if (Bank.isServer()) {
				for (int i = 0; i < Bank.server.getUnits(roomID).size(); ++i) {
					Unit u = Bank.server.getUnits(roomID).get(i);
					if (u != null)
						if (u.getFamily() == Family.werewolf) {
							caster.addEnergy1(1);
							caster.addOrUpdateEffect(new Effect(EffectType.speed, 1, 999));
						}
				}
			}
		}
		if (this == bite) {
			boolean is = target.getFamily() == Family.werewolf;
			target.hurt(2, caster);
			if (target.getHealth() > 0 && Bank.isServer() & !target.getIsCommander()) {
				ArrayList<Card> avail = new ArrayList<Card>();
				for (int i = 0; i < Card.all.length; ++i) {
					if (Card.all[i] != null) {
						if (Card.all[i].getUnit() != null) {
							if (Card.all[i].getUnit().getFamily() == Family.werewolf)
								avail.add(Card.all[i]);
						}
					}
				}
				if (avail.size() > 0) {
					UnitTemplate tid = (UnitTemplate) avail.get(Util.rand.nextInt(avail.size()));
					this.sendComm(tid.getId() + "", caster, target, 0, 0, roomID);
					target.setTemplate(tid);
					target.resummon();
				}
			}
			if (is)
				learn.onCast(target, target, 0, 0, roomID);
		}
		if (this == feast) {
			target.hurt(1, caster);
			if (target.getHealth() <= 0) {
				caster.heal(caster.getHealthMax());
				Animation.createStaticAnimation(caster, "HEALCOLUMN", 2, 800);
			}
		}
		if (this == hunt) {
			caster.hurt(1, caster);
			caster.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
			Animation.createStaticAnimation(caster, "CHARGE", 1, 300);
		}
		if (this == howl) {
			if (Bank.isClient()) {
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit u = Display.currentScreen.objects.get(i);
					if (u != null)
						if (u.getFamily() == Family.werewolf) {
							Animation.createStaticAnimation(u, "CHARGE", 2, 700);
							Animation.createStaticAnimation(u, "BLOOD", 2, 500);
							u.addOrUpdateEffect(new Effect(EffectType.attack, 1, 1));
						}
				}
			}
			if (Bank.isServer()) {
				for (int i = 0; i < Bank.server.getUnits(roomID).size(); ++i) {
					Unit u = Bank.server.getUnits(roomID).get(i);
					if (u != null)
						if (u.getFamily() == Family.werewolf)
							u.addOrUpdateEffect(new Effect(EffectType.attack, 1, 1));
				}
			}
		}
		if (this == pack) {
			if (Bank.isClient()) {
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit u = Display.currentScreen.objects.get(i);
					if (u != null)
						if (u.getFamily() == Family.werewolf) {
							Animation.createStaticAnimation(u, "HEAL", 2, 900);
							Animation.createStaticAnimation(u, "BLOOD", 2, 500);
							u.heal(2);
						}
				}
			}
			if (Bank.isServer()) {
				for (int i = 0; i < Bank.server.getUnits(roomID).size(); ++i) {
					Unit u = Bank.server.getUnits(roomID).get(i);
					if (u != null)
						if (u.getFamily() == Family.werewolf)
							u.heal(2);
				}
			}
		}
		if (this == guilt) {
			caster.getAbilities().remove(guilt);
			Animation.createStaticAnimation(caster, "FLASH", 3, 700);
		}
		if (this == fleshOfFire) {
			caster.getAbilities().remove(fleshOfFire);
			Animation.createStaticAnimation(caster, "POOL", 1.5f, 700);
		}
		if (this == kill) {
			if (Bank.isServer()) {
				for (Unit u : Bank.server.getUnits(roomID)) {
					if (u != null && u != caster) {
						if (u.getAttack() <= 3)
							u.hurt(4, caster);
					}
				}
			} else {
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit u = Display.currentScreen.objects.get(i);
					if (u != null && u != caster) {
						if (u.getAttack() <= 3) {
							u.hurt(4, caster);
							Animation.createStaticAnimation(u, "INCINERATE", 1, 800);
							Animation.createStaticAnimation(u, "BLOOD", 3, 600);
						}
					}
				}
			}
		}
		if (this == meme) {
			target.hurt(3, caster);
			target.getAbilities().add(confusion);
			Util.drawParticleLine(caster.getPoint(), target.getPoint(), 30, true, "MANABURN", 40, 600, false);
		}
		if (this == evermore) {
			Animation.createStaticAnimation(caster, "HEAL", 3, 1000);
			caster.heal(30);
		}
		if (this == forbiddenMagic) {
			if (target.getFamily() == Family.eldritch) {
				if (target != caster) {
					target.addOrUpdateEffect(new Effect(EffectType.attack, 3, 999));
					target.addOrUpdateEffect(new Effect(EffectType.health, 3, 999));
					target.addOrUpdateEffect(new Effect(EffectType.speed, 1, 999));
				}
			} else {
				target.hurt(3, caster);
				if (!Bank.isServer())
					Animation.createStaticAnimation(target, "SKULLGAS", 2, 900);
			}
			if (!Bank.isServer())
				Animation.createStaticAnimation(target, "VOIDCAST", 2, 1200);
		}
		if (this == deathwish) {
			caster.hurt(caster.getHealth(), caster);
			if (Bank.isServer())
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), -1, 1);
			Animation.createStaticAnimation(caster, "SKULLGAS", 2, 600);
		}
		if (this == persuade) {
			caster.hurt(target.getAttack(), target);
			target.ownerID = (target.ownerID == 0 ? 1 : 0);
			Animation.createStaticAnimation(target, "NOVA", 2, 750);
		}
		if (this == reshuffle) {
			if (Bank.isServer()) {
				Unit u = target;
				int total = (int) (u.getAttack() + u.getHealth() + u.getSpeed());
				int atk = 0;
				int hp = 0;
				int spd = 0;
				for (int i = 0; i < total; ++i) {
					int r = Util.rand.nextInt(3);
					if (r == 0)
						atk++;
					if (r == 1)
						hp++;
					if (r == 2)
						spd++;
				}
				this.sendComm(atk + "," + hp + "," + spd + "," + target.GUID, u, null, 0, 0, roomID);
				u.getEffects().clear();
				u.setHealth(hp);
				u.setAttack(atk);
				u.setSpeed(spd);
			}
			if (Bank.isClient()) {
				Animation.createStaticAnimation(caster, "RUNE", 2, 800);
			}
		}
		if (this == smite) {
			Util.drawParticleLine(caster.getPoint(), target.getPoint(), 40, true, "FLASH", 32, 400, false);
			Animation.createStaticAnimation(caster, "HOLY", 2, 1000);
			Animation.createStaticAnimation(caster, "FLASH", 2, 750);
			Animation.createStaticAnimation(target, "SHINE", 2, 1200);
			target.hurt((target.getFamily() == Family.undead ? 4 : 2), caster);
		}
		if (this == bodyslam) {
			Animation.createStaticAnimation(target, "EXPLOSION", 2, 750);
			Animation.createStaticAnimation(caster, "SHIELD", 2, 450);
			target.hurt(caster.getHealth(), caster);
		}
		if (this == conjure) {
			if (Bank.isServer()) {
				ArrayList<Card> usable = new ArrayList<Card>();
				for (Card c : Card.all) {
					if (c != null) {
						usable.add(c);
					}
				}
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID),
						usable.get(Util.rand.nextInt(usable.size())).getId(), 1);
			}
			if (Bank.isClient()) {
				Animation.createStaticAnimation(caster, "NOVA", 2, 800);
			}
		}
		if (this == disarm) {
			target.addOrUpdateEffect(new Effect(EffectType.attack, -1, 1));
			Animation.createStaticAnimation(target, "MANABURN", 2, 800);
		}
		if (this == warbanner) {
			if (Bank.isServer()) {
				Bank.server.addUnit((UnitTemplate) (Card.banner), x, y, caster.ownerID, roomID);
			}
			if (Bank.isClient()) {
				Animation.createStaticAnimation(caster, "CHARGE", 2, 700);
			}
		}
		if (this == hoofsmash) {
			Animation.createStaticAnimation(target, "EXPLOSION", 2, 600);
			Animation.createStaticAnimation(target, "EARTH", 2, 900);
			target.addOrUpdateEffect(new Effect(EffectType.attack, -2, 2));
			target.addOrUpdateEffect(new Effect(EffectType.speed, -2, 2));
			if (target.getTemplate() == Card.wall) {
				target.hurt(target.getHealth(), caster);
			}
		}
		if (this == trample) {
			Animation.createStaticAnimation(target, "EXPLOSION", 2, 600);
			Util.drawParticleLine(caster.getPoint(), target.getPoint(), 20, true, "EARTH");
			target.hurt((int) caster.getSpeed(), caster);
			caster.addOrUpdateEffect(new Effect(EffectType.speed, -2, 2));
		}
		if (this == fortify) {
			target.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
			Animation.createStaticAnimation(target, "HEALCOLUMN", 2, 900);
			Animation.createStaticAnimation(target, "SHIELD", 1, 700);
		}
		if (this == plant) {
			if (Bank.isServer()) {
				int r = Util.rand.nextInt(3);
				Bank.server
						.addUnit(
								(UnitTemplate) (r == 0 ? UnitTemplate.bloodcap
										: r == 1 ? UnitTemplate.pumpkin : UnitTemplate.firestalk),
								x, y, caster.ownerID, roomID);
			}
			if (Bank.isClient()) {
				Animation.createStaticAnimation(caster, "HEAL", 2, 500);
			}
		}
		if (this == plantTree) {
			if (Bank.isServer()) {
				Bank.server.addUnit((UnitTemplate) Card.tree, x, y, caster.ownerID, roomID);
			}
			if (Bank.isClient()) {
				Animation.createStaticAnimation(caster, "LEAVES", 2, 1000);
			}
		}
		if (this == bloodpuppet) {
			if (Bank.isServer()) {
				Bank.server.addUnit((UnitTemplate) Card.bloodpuppet, x, y, caster.ownerID, roomID);
			}
			if (Bank.isClient()) {
				Animation.createStaticAnimation(caster, "BLOOD", 2, 800);
			}
		}
		if (this == deadroot) {
			if (Bank.isServer()) {
				boolean can = true;
				ArrayList<Unit> units = Bank.server.getUnits(roomID);
				for(Unit u : units){
					if(u.getTemplate() == Card.root){
						if(Util.distance(u.getTilePosition(), new Point(x, y)) <= 3){
							can = false;
						}
					}
				}
				if(can)
				Bank.server.addUnit((UnitTemplate) Card.root, x, y, caster.ownerID, roomID);
			}
			if (Bank.isClient()) {
				Animation.createStaticAnimation(caster, "LEAVES", 2, 800);
			}
		}
		if (this == createGolem) {
			if (Bank.isServer()) {
				Bank.server.addUnit((UnitTemplate) Card.sandgolem, x, y, caster.ownerID, roomID);
			}
			if (Bank.isClient()) {
				Animation.createStaticAnimation(caster, "BLOOD", 2, 800);
			}
		}
		if (this == buildTower) {
			if (Bank.isServer()) {
				Bank.server.addUnit((UnitTemplate) Card.tower, x, y, caster.ownerID, roomID);
			}
			if (Bank.isClient()) {
				Animation.createStaticAnimation(caster, "EARTH", 2, 1500);
			}
		}
		if (this == spawnWarrior) {
			UnitTemplate card = (UnitTemplate) Card.militia0;
			CardList list = CardList.getListForCard(Display.currentScreen.getCommanderForPlayer(caster.ownerID, roomID).getTemplate());
			if(list == CardList.northgarde) {
				card = (UnitTemplate) Card.militia0;
			}
			if(list == CardList.orklad) {
				card = (UnitTemplate) Card.militia1;
			}
			if(list == CardList.eboncreed) {
				card = (UnitTemplate) Card.militia3;
			}
			if(list == CardList.steamguild) {
				card = (UnitTemplate) Card.militia2;
			}
			if(list == CardList.legion) {
				card = (UnitTemplate) Card.militia4;
			}
			if (Bank.isServer()) {
				Bank.server.addUnit(card, x, y, caster.ownerID, roomID);
			}
			if (Bank.isClient()) {
				Animation.createStaticAnimation(caster, "CHARGE", 2, 800);
			}
		}
		if (this == germinate) {
			if (Bank.isServer()) {
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), caster.getTemplate().getId(), 1);
			}
			if (Bank.isClient()) {
				Animation.createStaticAnimation(caster, "LEAVES", 1, 800);
			}
		}
		if (this == toxic) {
			target.hurt(caster.getHealth(), caster);
			Animation.createStaticAnimation(target, "BURST", 2, 800);
			caster.hurt(caster.getHealth(), caster);
		}
		if (this == feed) {
			target.heal(caster.getHealth());
			Animation.createStaticAnimation(target, "HEAL", 2, 1000);
			caster.hurt(caster.getHealth(), caster);
		}
		if (this == firepower) {
			target.addEnergy1(caster.getHealth());
			Animation.createStaticAnimation(target, "CHARGE", 2, 700);
			caster.hurt(caster.getHealth(), caster);
		}
		if (this == curse) {
			target.addOrUpdateEffect(new Effect(EffectType.attack, (1 - target.getAttack()), 2));
			Animation.createStaticAnimation(target, "VOIDCAST", 2, 750);
		}
		if (this == strike) {
			target.hurt(caster.getAttack(), caster);
		}
		if (this == liberate) {
			target.getEffects().clear();
			Animation.createStaticAnimation(target, "FLASH", 2, 1000);
		}
		if (this == teach) {
			for (CardAbility c : caster.getAbilities()) {
				if (c != CardAbility.attack && c != CardAbility.move) {
					target.getAbilities().add(c);
				}
			}
			Animation.createStaticAnimation(target, "MAGIC", 1, 800);
		}
		if (this == inspire) {
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					int x1 = caster.posX - 1 + i, y1 = caster.posY - 1 + j;
					Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
					if (t != null && t != caster) {
						t.addOrUpdateEffect(new Effect(EffectType.attack, 1, 1));
						Animation.createStaticAnimation(t, "BOON", 2, 1000);
						Animation.createStaticAnimation(t, "FLASH", 2, 800);
					}
				}
			}
		}
		if (this == curse) {
			target.addOrUpdateEffect(new Effect(EffectType.attack, 1 - target.getAttack(), 2));
			Animation.createStaticAnimation(target, "VOIDCAST", 2, 750);
		}
		if (this == mindburst) {
			Animation.createStaticAnimation(caster, "MAGIC", 2, 900);
			Animation.createStaticAnimation(target, "BURST", 2, 1200);
			target.hurt(caster.getAbilities().size(), caster);
		}
		if (this == evolve) {
			if (Bank.isServer()) {
				Card c = Card.filler;
				ArrayList<Card> avail = new ArrayList<Card>();
				for (Card card : Card.all) {
					if (card != null) {
						if (card.getCost() == caster.getEnergy() && card instanceof UnitTemplate)
							avail.add(card);
					}
				}
				if (avail.size() > 0) {
					c = avail.get(Util.rand.nextInt(avail.size()));
					int e = caster.getEnergy();
					UnitTemplate t = (UnitTemplate) c;
					caster.setTemplate(t);
					caster.resummon();
					caster.setEnergy1(e);
					this.sendComm((c != null ? c.getId() : caster.getTemplate().getId()) + "", caster, null, x, y, roomID);
				}
			}
		}
		if (this == tinker) {
			if (Bank.isServer()) {
				Card c = Card.steamgolem;
				ArrayList<Card> avail = new ArrayList<Card>();
				for (Card card : Card.all) {
					if (card != null) {
						if (card instanceof UnitTemplate) {
							if (card.getUnit().getFamily() == Family.mech)
								avail.add(card);
						}
					}
				}
				c = avail.get(Util.rand.nextInt(avail.size()));
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), c.getId(), 1);
			} else {
				Animation.createStaticAnimation(caster, "BOON", 2, 1200);
			}
		}

		if (this == krattHut) {
			if (Bank.isServer()) {
				Card c = Card.skrittRogue;
				ArrayList<Card> avail = new ArrayList<Card>();
				for (Card card : Card.all) {
					if (card != null) {
						if (card instanceof UnitTemplate) {
							if (card.getUnit().getFamily() == Family.kratt)
								avail.add(card);
						}
					}
				}
				c = avail.get(Util.rand.nextInt(avail.size()));
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), c.getId(), 1);

				for (Unit u : Bank.server.getUnits(roomID)) {
					if (u != null) {
						if (u.getFamily() == Family.kratt)
							u.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
					}
				}
			} else {
				Animation.createStaticAnimation(caster, "BOON", 2, 1200);
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit u = Display.currentScreen.objects.get(i);
					if (u != null) {
						if (u.getFamily() == Family.kratt) {
							u.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
							Animation.createStaticAnimation(u, "LEAVES", 1.5f, 700);
						}
					}
				}
			}
		}
		if (this == abyssaltemple) {
			if (Bank.isServer()) {
				for (Unit u : Bank.server.getUnits(roomID)) {
					if (u != null) {
						if (u.getFamily() == Family.demon) {
							u.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
							u.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
						}
					}
				}
			} else {
				Animation.createStaticAnimation(caster, "VOID", 2, 1200);
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit u = Display.currentScreen.objects.get(i);
					if (u != null) {
						if (u.getFamily() == Family.demon) {
							u.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
							u.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
							Animation.createStaticAnimation(u, "SKULLGAS", 2, 500);
						}
					}
				}
			}
		}
		if (this == omazvillage) {
			if (Bank.isServer()) {
				for (Unit u : Bank.server.getUnits(roomID)) {
					if (u != null) {
						if (u.ownerID == caster.ownerID) {
							u.addOrUpdateEffect(new Effect(EffectType.speed, 2, 999));
						}
					}
				}
			} else {
				Animation.createStaticAnimation(caster, "LEAVES", 2, 1200);
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit u = Display.currentScreen.objects.get(i);
					if (u != null) {
						if (u.ownerID == caster.ownerID) {
							u.addOrUpdateEffect(new Effect(EffectType.speed, 2, 999));
							Animation.createStaticAnimation(u, "BOON", 1.5f, 800);
						}
					}
				}
			}
		}
		if (this == dwarvenSmithy) {
			if (Bank.isServer()) {
				for (Unit u : Bank.server.getUnits(roomID)) {
					if (u != null) {
						if (u.ownerID == caster.ownerID && u.isStructure()) {
							u.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
							u.addOrUpdateEffect(new Effect(EffectType.health, 2, 999));
						}
					}
				}
			} else {
				Animation.createStaticAnimation(caster, "IRON", 2, 1200);
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit u = Display.currentScreen.objects.get(i);
					if (u != null) {
						if (u.ownerID == caster.ownerID && u.isStructure()) {
							u.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
							u.addOrUpdateEffect(new Effect(EffectType.health, 2, 999));
							Animation.createStaticAnimation(u, "IRON", 2, 800);
						}
					}
				}
			}
		}
		if (this == chapel) {
			if (Bank.isServer()) {
				for (Unit u : Bank.server.getUnits(roomID)) {
					if (u != null) {
						if (u.getFamily() == Family.demon || u.getFamily() == Family.undead)
							u.hurt(3, caster);
					}
				}
			} else {
				for (int i = 0; i < Display.currentScreen.objects.size(); ++i) {
					Unit u = Display.currentScreen.objects.get(i);
					if (u != null) {
						if (u.getFamily() == Family.demon || u.getFamily() == Family.undead) {
							u.hurt(3, caster);
							Animation.createStaticAnimation(u, "SHINE", 1.5f, 700);
						}
					}
				}
			}
		}
		if (this == learn) {
			if (Bank.isServer()) {
				CardAbility c = CardAbility.attack;
				ArrayList<CardAbility> avail = new ArrayList<CardAbility>();
				for (CardAbility card : CardAbility.all) {
					if (card != null) {
						avail.add(card);
					}
				}
				c = avail.get(Util.rand.nextInt(avail.size()));
				caster.getAbilities().add(c);
				this.sendComm((c != null ? c.getId() : 1) + "", caster, null, x, y, roomID);
			}
		}
		if (this == gamble) {
			if (Bank.isServer()) {
				int rand = Util.rand.nextInt(2);
				if (rand == 0) {
					caster.addOrUpdateEffect(new Effect(EffectType.health, 2, 999));
					caster.addOrUpdateEffect(new Effect(EffectType.attack, 2, 999));
				} else {
					caster.hurt(caster.getHealth(), caster);
				}
				this.sendComm(rand + "", caster, null, x, y, roomID);
			}
		}
		if (this == drink) {
			caster.addOrUpdateEffect(new Effect(EffectType.speed, -1, 999));
			caster.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
			Animation.createStaticAnimation(caster, "HEALCOLUMN", 2, 950);
		}
		if (this == share) {
			Animation.createStaticAnimation(target, "SPLASH", 2, 700);
			target.getAbilities().add(CardAbility.drink);
		}
		if (this == spiritnova) {
			AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + (x - 1) * Grid.tileSize,
					Util.boardOffsetY + (y - 1) * Grid.tileSize, Grid.tileSize * 3, 900).addFrameSet("NOVA");
			Display.currentScreen.particles.add(p);
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					int x1 = x - 1 + i, y1 = y - 1 + j;
					Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
					if (t != null && t != caster) {
						t.hurt(1, caster);
					}
				}
			}
		}
		if (this == slay) {
			if (target.getAttack() >= 5) {
				target.hurt(target.getHealth(), caster);
			}
		}
		if (this == mount) {
			caster.hurt(caster.getHealth(), caster);
			target.addOrUpdateEffect(new Effect(EffectType.speed, 4, 999));
		}
		if (this == snipe) {
			Util.drawParticleLine(caster.getPoint(), target.getPoint(), 40, false, "IRON");
			target.hurt(1, caster);
		}
		if (this == buildWall) {
			if (Bank.isServer()) {
				Bank.server.addUnit((UnitTemplate) UnitTemplate.wall, x, y, caster.ownerID, roomID);
				Animation.createStaticAnimation(caster, "BURST", 2, 500);
			}
		}
		if (this == buildCannon) {
			if (Bank.isServer()) {
				Bank.server.addUnit((UnitTemplate) UnitTemplate.cannonTower, x, y, caster.ownerID, roomID);
				Animation.createStaticAnimation(caster, "BURST", 2, 500);
			}
		}
		if (this == spelllock) {
			target.getAbilities().clear();
			target.getAbilities().add(CardAbility.attack);
			target.getAbilities().add(CardAbility.move);
			Animation.createStaticAnimation(target, "RUNE", 2, 700);
		}
		if (this == devour) {
			for (CardAbility c : target.getAbilities()) {
				if (c != CardAbility.attack && c != CardAbility.move) {
					target.hurt(1, caster);
				}
			}
			target.getAbilities().clear();
			target.getAbilities().add(CardAbility.attack);
			target.getAbilities().add(CardAbility.move);
			Animation.createStaticAnimation(target, "VOIDCAST", 2, 700);
			Animation.createStaticAnimation(target, "VOID", 2, 1100);
		}
		if (this == study) {
			for (CardAbility c : target.getAbilities()) {
				if (c != CardAbility.attack && c != CardAbility.move) {
					caster.getAbilities().add(c);
				}
			}
			Animation.createStaticAnimation(target, "LEAVES", 2, 800);
			Animation.createStaticAnimation(caster, "MAGIC", 1, 800);
		}
		if (this == spellsteal) {
			for (CardAbility c : target.getAbilities()) {
				if (c != CardAbility.attack && c != CardAbility.move) {
					caster.getAbilities().add(c);
				}
			}
			target.getAbilities().clear();
			target.getAbilities().add(CardAbility.attack);
			target.getAbilities().add(CardAbility.move);
			Animation.createStaticAnimation(target, "NOVA", 2, 900);
			Animation.createStaticAnimation(caster, "MAGIC", 1, 800);
		}
		if (this == mimic) {
			if (!target.getIsCommander()) {
				caster.setTemplate(target.getTemplate());
				caster.resummon();
				caster.setEnergy1(target.getEnergy());
				caster.setEffects(target.getEffects());
				caster.setHealth(target.getHealth());
				caster.setSpeed(target.getSpeed());
				caster.setAttack(target.getAttack());
				Animation.createStaticAnimation(caster, "NOVA", 2, 1200);
			}
		}
		if (this == supercharge) {
			target.addEnergy1(2);
			Animation.createStaticAnimation(target, "CHARGE", 2, 500);
		}
		if (this == overload) {
			target.hurt(target.getEnergy(), caster);
			Animation.createStaticAnimation(target, "THUNDER", 2, 800);
		}
		if (this == salvage) {
			if (target.getFamily() == Family.mech) {
				int draw = 1;
				if (target.getHealth() - 1 <= 0)
					++draw;
				if (Bank.isServer())
					Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), -1, draw);
				target.hurt(1, caster);
				Animation.createStaticAnimation(target, "EXPLOSION", 1, 750);
			}
		}
		if (this == combust) {
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					int x1 = caster.posX - 1 + i, y1 = caster.posY - 1 + j;
					AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + x1 * Grid.tileSize,
							Util.boardOffsetY + y1 * Grid.tileSize, Grid.tileSize, 300).addFrameSet("EXPLOSION");
					Display.currentScreen.particles.add(p);
					Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
					if (t != null) {
						t.hurt(1, caster);
					}
				}
			}
		}
		if (this == stomp) {
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					int x1 = caster.posX - 1 + i, y1 = caster.posY - 1 + j;
					AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + x1 * Grid.tileSize,
							Util.boardOffsetY + y1 * Grid.tileSize, Grid.tileSize, 700).addFrameSet("EARTH");
					Display.currentScreen.particles.add(p);
					Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
					if (t != null && t != caster) {
						t.hurt(2, caster);
					}
				}
			}
		}
		if (this == tigerfist) {
			target.hurt(1, caster);
			if (!target.getIsCommander())
				target.addOrUpdateEffect(new Effect(EffectType.attack, -1, 999));
			Animation.createStaticAnimation(target, "MAGIC", 2, 600);
			Animation.createStaticAnimation(target, "BLOOD", 2, 500);
		}
		if (this == dragonfist) {
			target.hurt(3, caster);
			Animation.createStaticAnimation(target, "EXPLOSION", 2, 500);
			Animation.createStaticAnimation(target, "INCINERATE", 2, 1000);
			if (target.getHealth() > 0) {
				target.heal(4);
				Animation.createStaticAnimation(target, "HEAL", 2, 800);
			}
		}
		if (this == monkeyfist) {
			target.hurt(1, caster);
			caster.heal(1);
			Animation.createStaticAnimation(caster, "HEAL", 2, 800);
			Animation.createStaticAnimation(target, "BLOOD", 2, 500);
		}
		if (this == mastersCall) {
			if (Bank.isServer())
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), Card.swine.getId(), 1);
			else
				Animation.createStaticAnimation(caster, "CHARGE", 2, 400);
			Animation.createStaticAnimation(caster, "BOON", 2, 800);
		}
		if (this == recruitMilitia) {
			if (Bank.isServer())
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), Card.barracksMilitia.getId(), 1);
			else
				Animation.createStaticAnimation(caster, "CHARGE", 2, 400);
		}
		if (this == recruitKnight) {
			if (Bank.isServer())
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), Card.barracksKnight.getId(), 1);
			else
				Animation.createStaticAnimation(caster, "CHARGE", 2, 400);
		}
		if (this == cannonShot) {
			Util.drawParticleLine(caster.getPoint(), target.getPoint(), 40, false, "EXPLOSION");
			target.hurt(caster.getAttack(), caster);
		}
		if (this == wildAxe) {
			target.hurt(3, caster);
			caster.hurt(3, caster);
			Animation.createStaticAnimation(target, "BLOOD", 2, 500);
			Animation.createStaticAnimation(caster, "BLOOD", 2, 500);
			Animation.createStaticAnimation(target, "SLASH", 2, 800);
		}
		if (this == farmFeed) {
			target.heal(2);
			Animation.createStaticAnimation(target, "BOON", 2, 500);
		}
		if (this == bloodCharge) {
			Animation.createStaticAnimation(target, "CHARGE", 2, 800);
			target.addOrUpdateEffect(new Effect(EffectType.speed, 1, 999));
		}
		if (this == limbripper) {
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					int x1 = caster.posX - 1 + i, y1 = caster.posY - 1 + j;
					Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
					if (t != null) {
						t.hurt(1, caster);
						Animation.createStaticAnimation(t, "BLOOD", 2, 1000);
					}
				}
			}
		}
		if (this == buzzsaw) {
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					int x1 = caster.posX - 1 + i, y1 = caster.posY - 1 + j;
					Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
					if (t != null) {
						t.hurt(1, caster);
						Animation.createStaticAnimation(t, "SLASH", 2, 1000);
					}
				}
			}
		}
		if (this == jadibrad) {
			target.hurt(1, caster);
			caster.heal(2);
			Animation.createStaticAnimation(caster, "HOLY", 2, 800);
			Animation.createStaticAnimation(target, "FLASH", 2, 500);
		}
		if (this == tentBuild) {
			caster.addOrUpdateEffect(new Effect(EffectType.health, 5, 999));
			if (caster.getHealth() >= 30) {
				caster.setTemplate((UnitTemplate) Card.outpost);
				caster.resummon();
				caster.getEffects().clear();
			}
			Animation.createStaticAnimation(caster, "fx6_eaterFire", 2, 1200);
		}
		if (this == outpostBuild) {
			caster.addOrUpdateEffect(new Effect(EffectType.health, 5, 999));
			if (caster.getHealth() >= 50) {
				caster.setTemplate((UnitTemplate) Card.stronghold);
				caster.resummon();
				caster.getEffects().clear();
			}
			Animation.createStaticAnimation(caster, "fx6_eaterFire", 2, 1200);
		}
		if (this == bloodlust) {
			caster.hasAttacked = false;
			Animation.createStaticAnimation(target, "CHARGE", 2, 500);
		}
		if (this == chill) {
			target.addOrUpdateEffect(new Effect(EffectType.chill, 1, 2));
			Animation.createStaticAnimation(target, "FREEZE", 2, 800);
		}
		if (this == voidgift) {
			target.getAbilities().add(CardAbility.blink);
			Animation.createStaticAnimation(target, "VOIDCAST", 2, 800);
		}
		if (this == dive) {
			Animation.createStaticAnimation(caster, "GALE", 2, 500);
			ArrayList<Point> tiles = new ArrayList<Point>();
			int xdif = Util.dif(caster.posX, x);
			int ydif = Util.dif(caster.posY, y);
			for (int i = 0; i < xdif; ++i) {
				tiles.add(new Point(caster.posX + (caster.posX > x ? (-i) : i), caster.posY));
			}
			for (int i = 0; i < ydif; ++i) {
				tiles.add(new Point(x, caster.posY + (caster.posY > y ? (-i) : i)));
			}
			for (int i = 0; i < tiles.size(); ++i) {
				int tx = tiles.get(i).x;
				int ty = tiles.get(i).y;
				Unit u = Display.currentScreen.getUnitOnTile(tx, ty, roomID);
				if (u != null && u != caster) {
					u.hurt(1, caster);
				}
				AnimatedParticle part = new AnimatedParticle(Util.boardOffsetX + tx * Grid.tileSize,
						Util.boardOffsetY + ty * Grid.tileSize, Grid.tileSize, 300 + i * 100).addFrameSet("SLASH");
				Display.currentScreen.particles.add(part);
			}
			caster.posX = x;
			caster.posY = y;
			Animation.createStaticAnimation(caster, "GALE", 2, 500);
		}
		if (this == darkcharge) {
			Animation.createStaticAnimation(caster, "fx10_blackExplosion", 2, 500);
			ArrayList<Point> tiles = new ArrayList<Point>();
			int xdif = Util.dif(caster.posX, x);
			int ydif = Util.dif(caster.posY, y);
			for (int i = 0; i < xdif; ++i) {
				tiles.add(new Point(caster.posX + (caster.posX > x ? (-i) : i), caster.posY));
			}
			for (int i = 0; i < ydif; ++i) {
				tiles.add(new Point(x, caster.posY + (caster.posY > y ? (-i) : i)));
			}
			for (int i = 0; i < tiles.size(); ++i) {
				int tx = tiles.get(i).x;
				int ty = tiles.get(i).y;
				Unit u = Display.currentScreen.getUnitOnTile(tx, ty, roomID);
				if (u != null && u != caster) {
					u.hurt(2, caster);
				}
				AnimatedParticle part = new AnimatedParticle(Util.boardOffsetX + tx * Grid.tileSize,
						Util.boardOffsetY + ty * Grid.tileSize, Grid.tileSize, 300 + i * 100).addFrameSet("VOID");
				Display.currentScreen.particles.add(part);
			}
			caster.posX = x;
			caster.posY = y;
			Animation.createStaticAnimation(caster, "fx10_blackExplosion", 2, 500);
		}
		if (this == infinityburst) {
			target.hurt(1, caster);
			Animation.createStaticAnimation(target, "VOID", 1.25f, 800);
			Animation.createStaticAnimation(target, "MAGIC", 0.75f, 400);
		}
		if (this == cleave) {
			target.hurt(2, caster);
			Animation.createStaticAnimation(target, "BLOOD", 1.75f, 900);
			Unit t1 = Display.currentScreen.getUnitOnTile(target.posX, target.posY - 1, roomID);
			if (t1 != null) {
				Animation.createStaticAnimation(t1, "BLOOD", 1, 500);
				Animation.createStaticAnimation(t1, "SLASH", 2, 600);
				t1.hurt(1, caster);
			}
			Unit t2 = Display.currentScreen.getUnitOnTile(target.posX, target.posY + 1, roomID);
			if (t2 != null) {
				Animation.createStaticAnimation(t2, "BLOOD", 1, 500);
				Animation.createStaticAnimation(t2, "SLASH", 2, 600);
				t2.hurt(1, caster);
			}
		}
		if (this == bash) {
			target.hurt(1, caster);
			target.setEnergy1(target.getEnergy() - 2);
			Animation.createStaticAnimation(target, "BLOOD", 1, 500);
			Animation.createStaticAnimation(target, "SLASH", 2, 700);
		}
		if (this == siphon) {
			caster.setEnergy1(caster.getEnergy() + target.getEnergy());
			target.setEnergy1(0);
			Animation.createStaticAnimation(target, "MANABURN", 1.5f, 600);
			Animation.createStaticAnimation(caster, "CHARGE", 2, 750);
		}
		if (this == consume) {
			if (caster.ownerID == target.ownerID) {
				if (target.getEnergy() > 0) {
					Animation.createStaticAnimation(target, "BURST", 2, 900);
					Animation.createStaticAnimation(caster, "VOIDCAST", 1.5f, 600);
					caster.addOrUpdateEffect(new Effect(EffectType.attack, target.getAttack(), 1));
					target.hurt(target.getHealth(), caster);
				}
			}
		}
		if (this == meditate) {
			if (Bank.isServer()) {
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), -1, 1);
			} else {
				Animation.createStaticAnimation(caster, "BOON", 2, 900);
			}
		}
		if (this == web) {
			Animation.createStaticAnimation(target, "BURST", 2, 800);
			Animation.createStaticAnimation(caster, "MAGIC", 2, 800);
			if (target.getFamily() == Family.insectoid) {
				target.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
				target.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
			} else
				target.addOrUpdateEffect(new Effect(EffectType.speed, -3, 1));
		}
		if (this == poison) {
			Animation.createStaticAnimation(caster, "BURST", 1, 800);
			target.addOrUpdateEffect(new Effect(EffectType.attack, 2, 1));
		}
		if (this == energize) {
			target.addEnergy1(1);
			Animation.createStaticAnimation(target, "GALE", 1.5f, 800);
		}
		if (this == summonWarrior) {
			if (Bank.server != null) {
				Bank.server.drawCard(Bank.server.getClients(roomID).get(caster.ownerID), Card.lifelessWarrior.getId(), 1);
			}
			Animation.createStaticAnimation(caster, "VOIDCAST", 3, 1200);
		}
		if (this == phoenix) {
			if (Bank.isServer()) {
				caster.setTemplate((UnitTemplate) Card.phoenix);
				caster.resummon();
			} else {
				for (int i = 0; i < 20; ++i) {
					AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + target.posX * Grid.tileSize,
							Util.boardOffsetY + target.posY * Grid.tileSize, 16 + Util.rand.nextInt(32),
							300 + Util.rand.nextInt(201)).addFrameSet("FIRE").setMotions(
									Util.rand.nextInt(30) - Util.rand.nextInt(30),
									Util.rand.nextInt(30) - Util.rand.nextInt(30));
					Display.currentScreen.particles.add(p);
				}
				caster.setTemplate((UnitTemplate) Card.phoenix);
				caster.resummon();
				Animation.createStaticAnimation(caster, "INCINERATE", 2, 1200);
			}
		}
		if (this == enchant) {
			target.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
			for (int i = 0; i < 20; ++i) {
				AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + target.posX * Grid.tileSize,
						Util.boardOffsetY + target.posY * Grid.tileSize, 16 + Util.rand.nextInt(32),
						300 + Util.rand.nextInt(201)).addFrameSet("SHATTER").setMotions(
								Util.rand.nextInt(30) - Util.rand.nextInt(30),
								Util.rand.nextInt(30) - Util.rand.nextInt(30));
				Display.currentScreen.particles.add(p);
			}
			Animation.createStaticAnimation(caster, "RUNE", 2, 500);
		}
		if (this == ascend) {
			caster.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
			caster.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
			for (int i = 0; i < 20; ++i) {
				AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + caster.posX * Grid.tileSize,
						Util.boardOffsetY + caster.posY * Grid.tileSize, 16 + Util.rand.nextInt(32),
						300 + Util.rand.nextInt(201)).addFrameSet("FIRE").setMotions(
								Util.rand.nextInt(30) - Util.rand.nextInt(30),
								Util.rand.nextInt(30) - Util.rand.nextInt(30));
				Display.currentScreen.particles.add(p);
			}
			Animation.createStaticAnimation(caster, "INCINERATE", 2, 900);
		}
		if (this == upgradeCannon) {
			if (caster.getAttack() < 5) {
				caster.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
				new ClipShell("mech.wav").start();
				for (int i = 0; i < 20; ++i) {
					AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + caster.posX * Grid.tileSize,
							Util.boardOffsetY + caster.posY * Grid.tileSize, 16 + Util.rand.nextInt(32),
							300 + Util.rand.nextInt(201)).addFrameSet("IRON").setMotions(
									Util.rand.nextInt(30) - Util.rand.nextInt(30),
									Util.rand.nextInt(30) - Util.rand.nextInt(30));
					Display.currentScreen.particles.add(p);
				}
			}
		}
		if (this == upgrade1) {
			caster.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
			//new ClipShell("mech.wav").start();
			for (int i = 0; i < 20; ++i) {
				AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + caster.posX * Grid.tileSize,
						Util.boardOffsetY + caster.posY * Grid.tileSize, 16 + Util.rand.nextInt(32),
						300 + Util.rand.nextInt(201)).addFrameSet("IRON").setMotions(
								Util.rand.nextInt(30) - Util.rand.nextInt(30),
								Util.rand.nextInt(30) - Util.rand.nextInt(30));
				Display.currentScreen.particles.add(p);
			}
		}
		if (this == upgrade2) {
			caster.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
			//new ClipShell("mech.wav").start();
			for (int i = 0; i < 20; ++i) {
				AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + caster.posX * Grid.tileSize,
						Util.boardOffsetY + caster.posY * Grid.tileSize, 16 + Util.rand.nextInt(32),
						300 + Util.rand.nextInt(201)).addFrameSet("IRON").setMotions(
								Util.rand.nextInt(30) - Util.rand.nextInt(30),
								Util.rand.nextInt(30) - Util.rand.nextInt(30));
				Display.currentScreen.particles.add(p);
			}
		}
		if (this == upgrade3) {
			caster.addOrUpdateEffect(new Effect(EffectType.speed, 1, 999));
			//new ClipShell("mech.wav").start();
			for (int i = 0; i < 20; ++i) {
				AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + caster.posX * Grid.tileSize,
						Util.boardOffsetY + caster.posY * Grid.tileSize, 16 + Util.rand.nextInt(32),
						300 + Util.rand.nextInt(201)).addFrameSet("IRON").setMotions(
								Util.rand.nextInt(30) - Util.rand.nextInt(30),
								Util.rand.nextInt(30) - Util.rand.nextInt(30));
				Display.currentScreen.particles.add(p);
			}
		}
		if (this == shadowburn) {
			target.hurt(3, caster);
			Util.drawParticleLine(caster.getPoint(), target.getPoint(), 10, true, "SKULL");
			Animation.createStaticAnimation(target, "VOID", 2, 800);
		}
		if (this == cannonball) {
			target.hurt(1, caster);
			Display.currentScreen.particles.add(new Animation(Util.boardOffsetX + target.posX * Grid.tileSize,
					Util.boardOffsetY + target.posY * Grid.tileSize, 1000, Animation.TAG_CANNONBALL)
							.setOrigin(caster.posX, caster.posY));
		}
		if (this == voidclaw) {
			target.hurt(2, caster);
			Util.drawParticleLine(caster.getPoint(), target.getPoint(), 10, false, "SWIRL");
			Animation.createStaticAnimation(target, "VOID", 2, 800);
		}
		if (this == execute) {
			if (target.getHealth() < target.getHealthMax()) {
				target.hurt(2, caster);
				Util.drawParticleLine(caster.getPoint(), target.getPoint(), 10, false, "SWIRL");
			}
		}
		if (this == behead) {
			if (target.getHealth() == 1)
				target.hurt(10, caster);
		}
		if (this == soulfeast) {
			if (target.getHealth() == 1) {
				target.hurt(1, caster);
				caster.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
				Animation.createStaticAnimation(caster, "VOID", 2, 500);
			}
		}
		if (this == melt) {
			Grid grid = null;
			if (Bank.isServer())
				grid = Bank.server.getGrid(roomID);
			else
				grid = Display.currentScreen.getGrid();
			grid.setTileID(x, y, GridBlock.air.getID());
			Animation.createStaticAnimation(Util.boardOffsetX + x * Grid.tileSize + Grid.tileSize / 2,
					Util.boardOffsetY + y * Grid.tileSize + Grid.tileSize / 2, "VOIDCAST", 2, 750);
		}
		if (this == darkvision) {
			for (CardAbility c : target.getAbilities()) {
				c.onPlayed(target, roomID);
				c.onUnitDied(target, target, roomID);
			}
			Animation.createStaticAnimation(target, "MAGIC", 1.5f, 750);
		}
		if (this == cannon) {
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					int x1 = x - 1 + i, y1 = y - 1 + j;
					Display.currentScreen.particles.add(new Animation(Util.boardOffsetX + x1 * Grid.tileSize,
							Util.boardOffsetY + y1 * Grid.tileSize, 1000, Animation.TAG_CANNONBALL));
					for (int k = 0; k < 5; ++k) {
						AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + x1 * Grid.tileSize,
								Util.boardOffsetY + y1 * Grid.tileSize, 16 + Util.rand.nextInt(32),
								500 + Util.rand.nextInt(801)).addFrameSet("FIRE").setMotions(
										Util.rand.nextInt(30) - Util.rand.nextInt(30),
										Util.rand.nextInt(30) - Util.rand.nextInt(30));
						Display.currentScreen.particles.add(p);
					}
					Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
					if (t != null) {
						t.hurt(1, caster);
					}
				}
			}
		}
		if (this == stormbreath) {
			int dmg = 1;
			if (caster.getTileAtPosition(caster.posX, caster.posY) == GridBlock.water)
				dmg++;
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					int x1 = x - 1 + i, y1 = y - 1 + j;
					Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
					if (t != null) {
						t.hurt(dmg, caster);
						Util.drawParticleLine(caster.getPoint(), t.getPoint(), 12, true, "SWIRL");
						Animation.createStaticAnimation(t, "THUNDER", 2, 500);
					}
				}
			}
		}
		if (this == cannonball1) {
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					int x1 = x - 1 + i, y1 = y - 1 + j;
					Display.currentScreen.particles.add(new Animation(Util.boardOffsetX + x1 * Grid.tileSize,
							Util.boardOffsetY + y1 * Grid.tileSize, 1000, Animation.TAG_CANNONBALL)
									.setOrigin(caster.posX, caster.posY));
					for (int k = 0; k < 5; ++k) {
						AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + x1 * Grid.tileSize,
								Util.boardOffsetY + y1 * Grid.tileSize, 16 + Util.rand.nextInt(32),
								500 + Util.rand.nextInt(801)).addFrameSet("FIRE").setMotions(
										Util.rand.nextInt(30) - Util.rand.nextInt(30),
										Util.rand.nextInt(30) - Util.rand.nextInt(30));
						Display.currentScreen.particles.add(p);
					}
					Unit t = Display.currentScreen.getUnitOnTile(x1, y1, roomID);
					if (t != null) {
						t.hurt(2, caster);
					}
				}
			}
		}
		if (this == scorch || this == dragonwrath) {
			target.hurt(2, caster);
			Util.drawParticleLine(caster.getPoint(), target.getPoint(), 10, false, "FIRE");
			Animation.createStaticAnimation(target, "INCINERATE", 1.8f, 800);
		}
		if (this == heal) {
			target.heal(1);
			Util.drawParticleLine(caster.getPoint(), target.getPoint(), 10, true, "BLOOM");
			Animation.createStaticAnimation(target, "HEAL", 2f, 900);
		}
		if (this == heal1) {
			target.heal(2);
			Animation.createStaticAnimation(target, "HEALCOLUMN", 2f, 900);
		}
		if (this == dawnlight) {
			target.heal(3);
			Animation.createStaticAnimation(caster, "SHINE", 2f, 750);
			Animation.createStaticAnimation(target, "HOLYCAST", 1.5f, 600);

			Animation.createStaticAnimation(target, "SHINE", 2f, 900);
		}
		if (this == heal2) {
			target.heal(3);
			Animation.createStaticAnimation(target, "SHINE", 2f, 900);
		}
		if (this == sacrifice) {
			target.heal(caster.getAttack());
			caster.hurt(caster.getHealth(), caster);
			Animation.createStaticAnimation(caster, "BLOOD", 3, 600);
			Animation.createStaticAnimation(target, "HEAL", 2f, 900);
		}
		if (this == leap) {
			// new ClipShell("stomp.wav").start();
			Util.drawParticleLine(caster.getPoint(),
					new Point(Util.boardOffsetX + x * Grid.tileSize + Grid.tileSize / 2,
							Util.boardOffsetY + y * Grid.tileSize + Grid.tileSize / 2),
					10, false, "FIRE");
			caster.posX = x;
			caster.posY = y;
			for (int i = 0; i < 20; ++i) {
				AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + caster.posX * Grid.tileSize,
						Util.boardOffsetY + caster.posY * Grid.tileSize, 16 + Util.rand.nextInt(32),
						300 + Util.rand.nextInt(201)).addFrameSet("IRON").setMotions(
								Util.rand.nextInt(30) - Util.rand.nextInt(30),
								Util.rand.nextInt(30) - Util.rand.nextInt(30));
				Display.currentScreen.particles.add(p);
			}
		}
		if (this == soar) {
			Util.drawParticleLine(caster.getPoint(),
					new Point(Util.boardOffsetX + x * Grid.tileSize + Grid.tileSize / 2,
							Util.boardOffsetY + y * Grid.tileSize + Grid.tileSize / 2),
					10, false, "FIRE");
			caster.posX = x;
			caster.posY = y;
			for (int i = 0; i < 20; ++i) {
				AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + caster.posX * Grid.tileSize,
						Util.boardOffsetY + caster.posY * Grid.tileSize, 16 + Util.rand.nextInt(32),
						300 + Util.rand.nextInt(201)).addFrameSet("FIRE").setMotions(
								Util.rand.nextInt(30) - Util.rand.nextInt(30),
								Util.rand.nextInt(30) - Util.rand.nextInt(30));
				Display.currentScreen.particles.add(p);
			}
		}
		if (this == blink) {
			Animation.createStaticAnimation(caster, "RUNE", 2, 600);
			for (int i = 0; i < 20; ++i) {
				AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + caster.posX * Grid.tileSize,
						Util.boardOffsetY + caster.posY * Grid.tileSize, 16 + Util.rand.nextInt(32),
						300 + Util.rand.nextInt(201)).addFrameSet("SWIRL").setMotions(
								Util.rand.nextInt(30) - Util.rand.nextInt(30),
								Util.rand.nextInt(30) - Util.rand.nextInt(30));
				Display.currentScreen.particles.add(p);
			}
			caster.posX = x;
			caster.posY = y;
			for (int i = 0; i < 20; ++i) {
				AnimatedParticle p = new AnimatedParticle(Util.boardOffsetX + caster.posX * Grid.tileSize,
						Util.boardOffsetY + caster.posY * Grid.tileSize, 16 + Util.rand.nextInt(32),
						300 + Util.rand.nextInt(201)).addFrameSet("SWIRL").setMotions(
								Util.rand.nextInt(30) - Util.rand.nextInt(30),
								Util.rand.nextInt(30) - Util.rand.nextInt(30));
				Display.currentScreen.particles.add(p);
			}
			Animation.createStaticAnimation(caster, "RUNE", 2, 900);
		}
		}
		Util.resolveUnits(roomID);
	}

	public void onUnitMoved(Unit u, Unit mover, int spaces, ArrayList<Point> tiles, int roomID) {
		if(!u.isStructure()){
			if (this == gaze) {
				mover.hurt(3, u);
				if (!Bank.isServer())
					Util.drawParticleLine(u.getPoint(), mover.getPoint(), 25, true, "FIRE");
			}
			if(this == scavenge){
				if(mover.ownerID != u.ownerID){
					int dmg = 0;
					for(Point p : tiles){
						if(Util.distance(p, u.getTilePosition()) <= 2){
							dmg += 3;
						}
					}
					if(dmg > 0){
						mover.hurt(dmg, u);
						if(!Bank.isServer())Animation.createStaticAnimation(u, "CHARGE", 1.5f, 750);
					}
				}
			}
			if (this == snipe1 && u == mover) {
				ArrayList<Unit> targets = new ArrayList<Unit>();
				if (Bank.isServer())
					targets = Bank.server.getUnits(roomID);
				else
					targets = Display.currentScreen.objects;
				ArrayList<Unit> newtargets = (ArrayList<Unit>) targets.clone();
				int enemy = (u.ownerID == 0 ? 1 : 0);
				for (int i = 0; i < targets.size(); ++i) {
					Unit t = targets.get(i);
					if (t != null)
						if (t.ownerID != enemy | !t.isPlayerUnit() || t.isStructure())
							newtargets.remove(t);
				}
				Unit tar = Util.getFurthestUnit(u, newtargets);
				if (tar != null) {
					Util.drawParticleLine(u.getPoint(), tar.getPoint(), 10, true, "NOVA", 20, 200 + Util.rand.nextInt(200),
							true);
					tar.hurt(2, u);
				}
			}
			if (this == movepower){
				if(u.ownerID == mover.ownerID){
					u.addOrUpdateEffect(new Effect(EffectType.attack, (int)mover.getSpeed(), 2));
					if(!Bank.isServer())
					Animation.createStaticAnimation(u, "CHARGE", 0.8f, 500);
				}
			}
			if (this == earthshaker && u == mover) {
				for (Point p : tiles) {
					Unit tar = Display.currentScreen.getUnitOnTile(p.x, p.y, roomID);
					if (tar != null && tar != u) {
						tar.hurt(2, mover);
						if (!Bank.isServer()) {
							Animation.createStaticAnimation(tar, "SLOW", 2, 600);
							Animation.createStaticAnimation(tar, "EARTH", 2, 750);
						}
					}
				}
			}
			if (this == voidwake && u == mover) {
				for (Point p : tiles) {
					if (Bank.isServer()) {
						Bank.server.getGrid(roomID).setTileID(p.x, p.y, GridBlock.voidtile.getID());
					} else {
						((PanelBoard) Display.currentScreen).getGrid().setTileID(p.x, p.y, GridBlock.voidtile.getID());
					}
				}
			}
		}
		// Util.resolveUnits();
	}

	public void onSpellCast(Unit unit, int x, int y, Unit target, int casterID, int roomID) {
		if(!unit.isStunned()){
		if (this == sandglaive && casterID == unit.ownerID) {
			Unit comm = null;
			int enemy = (casterID == 0 ? 1 : 0);
			if (Bank.isServer()) {
				comm = Bank.server.getCommander(Bank.server.getClients(roomID).get(enemy));
			} else {
				comm = ((PanelBoard) Display.currentScreen).getCommanderForPlayer(enemy, roomID);
				Util.drawParticleLine(unit.getPoint(), comm.getPoint(), 10, true, "RUNE");
				Animation.createStaticAnimation(comm, "SLASH", 2.5f, 700);
			}
			comm.hurt(2, unit);
		}

		if (this == spelldecay) {
			unit.addOrUpdateEffect(new Effect(EffectType.attack, -2, 999));
			unit.addOrUpdateEffect(new Effect(EffectType.health, -2, 999));
			if (!Bank.isServer())
				Animation.createStaticAnimation(unit, "EARTH", 2, 650);
		}

		if (this == kengpower) {
			unit.addOrUpdateEffect(new Effect(EffectType.attack, 2, 999));
			unit.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
			if (!Bank.isServer())
				Animation.createStaticAnimation(unit, "EARTH", 2, 650);
		}
		}
		// Util.resolveUnits();
	}

	public CardAbility linkCard(Card c) {
		linked = c;
		return this;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTargetType() {
		return targetType;
	}

	public void setTargetType(int targetType) {
		this.targetType = targetType;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isUnbound() {
		return unbound;
	}

	public void drawTooltip(Graphics g, int cw, int bx, int y) {
		g.setFont(Util.descFont);
		int w = g.getFontMetrics().stringWidth(getDesc()) + 30, h = 100;
		if (g.getFontMetrics(Util.cooldownBold).stringWidth(name) > w)
			w = g.getFontMetrics(Util.cooldownBold).stringWidth(name);
		int x = ((bx - w < Properties.width / 10 && cw > 0) ? (bx + cw) : bx - w);
		g.drawImage(Bank.paper, x, y, w, h, null);
		g.setFont(Util.cooldownBold);
		g.setColor(Color.BLACK);
		g.drawString(name, x + 10 - 2, y + 30 - 1);
		g.setColor(Color.WHITE);
		g.drawString(name, x + 10, y + 30);
		g.setColor(Color.BLACK);
		g.setFont(Util.descFont);
		g.drawString(getDesc(), x + 10, y + 50);
		g.setFont(new Font(Font.SERIF, Font.ITALIC, 14));
		if (targetType == TARGET_PASSIVE) {
			g.drawString("Passive", x + 10, y + 70);
		}
		if (unbound) {
			g.drawString("Unbound", x + 10, y + 85);
		}
		if (this.getCost() > 0) {
			Image costImg = Bank.energyFull;
			if (costType == COST_TYPE_MANA) {
				costImg = Bank.starFull;
			}
			if (costType == COST_TYPE_RESOURCE) {
				costImg = Bank.ball;
			}
			if (costType == COST_TYPE_SPECIAL) {
				costImg = Bank.holynova;
			}
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, h / 4));
			g.drawImage(costImg, x + w - 44, y + h - 26 - 5, 26, 26, null);
			g.drawString(this.getCost() + "", x + w - 60, y + h - 12);
		}
		if (this.getRange() > 0) {
			g.setFont(new Font(Font.MONOSPACED, Font.BOLD, h / 4));
			g.drawImage(Bank.target, x + w - 44 - 50, y + h - 26 - 5, 26, 26, null);
			g.drawString(this.getRange() + "", x + w - 60 - 50, y + h - 12);
		}
		Bank.drawOutlineOut(g, x, y, w, h, Color.BLACK, 3);
	}

	public void parseComm(String comm, Unit u, Unit target, int x, int y, int roomID) {
		if(!u.isStunned()){
		if (this == bite) {
			target.setTemplate((UnitTemplate) Card.all[Integer.parseInt(comm)]);
			target.resummon();
		}
		if (this == eldritchSlam) {
			target.hurt(Integer.parseInt(comm), u);
		}
		if (this == deckwormhold) {
			u.hurt(Integer.parseInt(comm), u);
			Animation.createStaticAnimation(u, "VOID", 1.25f, 700);
			Animation.createStaticAnimation(u, "BURST", 2, 900);
		}
		if (this == confusion) {
			int atk = Integer.parseInt(comm.split(",")[0]);
			int hp = Integer.parseInt(comm.split(",")[1]);
			int spd = Integer.parseInt(comm.split(",")[2]);
			if (u != null) {
				u.getEffects().clear();
				u.setHealth(hp);
				u.setAttack(atk);
				u.setSpeed(spd);
			}
		}
		if (this == elementalexplosion){
			Unit tar = Display.currentScreen.getUnitFromGUID(Integer.parseInt(comm), roomID);
			if (tar != null) {
				tar.hurt(4, u);
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 15, true, "fx8_lighteningBall", 40, 600, false);
				Animation.createStaticAnimation(tar, "fx7_energyBall", 3.5f, 800);
			}
		}
		if (this == reshuffle) {
			int atk = Integer.parseInt(comm.split(",")[0]);
			int hp = Integer.parseInt(comm.split(",")[1]);
			int spd = Integer.parseInt(comm.split(",")[2]);
			Unit unit = Display.currentScreen.getUnitFromGUID(Integer.parseInt(comm.split(",")[3]), roomID);
			unit.getEffects().clear();
			unit.setHealth(hp);
			unit.setAttack(atk);
			unit.setSpeed(spd);
		}
		if (this == wildmagic) {
			if (u != null) {
				UnitTemplate t = null;
				int e = u.getEnergy();
				t = (UnitTemplate) Card.all[Integer.parseInt(comm)];
				u.setTemplate(t);
				u.resummon();
				u.getAbilities().add(CardAbility.wildmagic);
				u.setEnergy1(e);
				Animation.createStaticAnimation(u, "BLUELIGHT", 2, 1000);
			}
		}
		if (this == erupt) {
			Unit tar = Display.currentScreen.getUnitFromGUID(Integer.parseInt(comm), roomID);
			if (tar != null) {
				tar.hurt(6, u);
				Animation.createStaticAnimation(tar, "EXPLOSION", 2.5f, 1200);
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 40, true, "INCINERATE", 64, 600, false);
			}
		}
		if (this == bouldertoss) {
			Unit tar = Display.currentScreen.getUnitFromGUID(Integer.parseInt(comm.split(",")[0]), roomID);
			if (tar != null) {
				tar.hurt(u.getAttack(), u);
				Util.drawParticleLine(u.getPoint(), tar.getPoint(), 40, true, "EARTH", 64, 600, false);
				Animation.createStaticAnimation(tar, "EARTH", 2.5f, 1200);
			}
		}
		if (this == gamble) {
			int rand = Integer.parseInt(comm);
			if (rand == 0) {
				u.addOrUpdateEffect(new Effect(EffectType.health, 2, 999));
				u.addOrUpdateEffect(new Effect(EffectType.attack, 2, 999));
				Animation.createStaticAnimation(u, "BOON", 2f, 900);
			} else {
				u.hurt(u.getHealth(), u);
				Animation.createStaticAnimation(u, "SKULLGAS", 2f, 1200);
			}
		}
		if (this == evolve) {
			UnitTemplate t = null;
			int e = u.getEnergy();
			t = (UnitTemplate) Card.all[Integer.parseInt(comm)];
			u.setTemplate(t);
			u.resummon();
			u.setEnergy1(e);
			Animation.createStaticAnimation(u, "NOVA", 2, 1000);
		}
		if (this == learn) {
			CardAbility c = all[Integer.parseInt(comm)];
			u.getAbilities().add(c);
			Animation.createStaticAnimation(u, "HEALCOLUMN", 2, 1000);
		}
		if (this == eldritchPower) {
			Unit tar = u;
			CardAbility c = all[Integer.parseInt(comm)];
			tar.getAbilities().add(c);
			c.onPlayed(tar, roomID);
			// System.out.println(c.getName()+" received");
			// u.getAbilities().add(c);
			// c.onPlayed(u);
			// u.getAbilityQueue().remove(ca);
		}
		}
		Util.resolveUnits(roomID);
	}

	public byte getCostType() {
		return costType;
	}

	public void onTurnChangeInHand(int playerID, int turn, ArrayList<CardShell> newHand, int roomID) {
		if (this == deckwormhold) {
			if (Bank.isServer()) {
				Unit hero = Bank.server.getCommander(Bank.server.getClients(roomID).get(playerID));
				hero.hurt(2, hero);
				this.sendComm("2", hero, hero, hero.posX, hero.posY, roomID);
			} else {

			}
		}
	}

	public String getDesc() {
		return desc;
	}

	public void onCardConjured(Unit unit, Unit src, Card card, int roomID) {
		if(!unit.isStunned()){
		if (this == gaze1) {
			Unit comm = Display.currentScreen.getCommanderForPlayer((unit.ownerID == 0 ? 1 : 0), roomID);
			comm.hurt(3, unit);
			if (Bank.isServer()) {
				Bank.server.drawCard(Bank.server.getClients(roomID).get(unit.ownerID), card.getId(), 1);
			} else {
				Util.drawParticleLine(unit.getPoint(), comm.getPoint(), 10, true, "MANABURN");
			}
		}
		if (this == runefur) {
			if (card != null && src == unit) {
				Packet02PlayCard pkt = new Packet02PlayCard(Util.clientID, card.getId(), src.posX, src.posY, 0);
				pkt.write(Bank.client);
			}
		}
		if (this == kidnap && Bank.isServer()) {
			if (card != null && src == unit) {
				for (CardShell c : Bank.server.getClients(roomID).get(src.ownerID).deck) {
					if (c.getCard().getId() == card.getId()) {
						Bank.server.getClients(roomID).get(src.ownerID).deck.remove(c);
						break;
					}
				}
			}
		}
		}
		Util.resolveUnits(roomID);
	}

	public void onOtherUnitPlayed(Unit other, Unit caster, int roomID) {
		if(!caster.isStunned()){
			if (this == elementalexplosion) {
				if (Bank.isServer()) {
					ArrayList<Unit> targets = new ArrayList<Unit>();
					for (int i = 0; i < Bank.server.getUnits(roomID).size(); ++i) {
						Unit t = Bank.server.getUnits(roomID).get(i);
						if (t != null) {
							if (t.ownerID != caster.ownerID && t.isPlayerUnit()) {
								targets.add(t);
							}
						}
					}
					int rand = Util.rand.nextInt(Bank.server.getUnits(roomID).size());
					Unit unit = targets.get(rand);
					if (unit != null) {
						unit.hurt(4, caster);
						this.sendComm(unit.GUID + "", caster, null, 0, 0, roomID);
					}
				}
			}
			if(this == tribalarrows){
				PersistentValue.addPersistentValue("GAME_DMGINCR", 1, roomID);
				if(!Bank.isServer()){
					Util.drawParticleLine(other.getPoint(), caster.getPoint(), 12, false, "IRON");
					Animation.createStaticAnimation(caster, "CHARGE", 1, 400);
				}
			}
			if(this == wardrums){
				other.addEnergy1(2);
				if(!Bank.isServer()){
					Animation.createStaticAnimation(other, "CHARGE", 1, 400);
				}
			}
			if(this == earthengrace && caster.getAbilities().contains(this)){
				other.addOrUpdateEffect(new Effect(EffectType.attack, 1, 999));
				other.addOrUpdateEffect(new Effect(EffectType.health, 1, 999));
				if(!Bank.isServer()){
					Animation.createStaticAnimation(other, "LEAVES", 2, 500);
				}
			}
			if(this == rakaj){
				if(other.getFamily() == Family.elemental){
					caster.addOrUpdateEffect(new Effect(EffectType.health, 2, 999));
					if(!Bank.isServer())Animation.createStaticAnimation(caster, "fx7_energyBall", 2, 600);
					if(caster.getHealth() >= 14){
						caster.say("INFINITE POWER!");
						caster.setTemplate((UnitTemplate) Card.rakaj);
						caster.resummon();
						if(!Bank.isServer())Animation.createStaticAnimation(caster, "fx4_sign_of_fire", 5, 800);
					}
				}
			}
		}
		Util.resolveUnits(roomID);
	}
}