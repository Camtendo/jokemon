////////////////////////////
//Item Class
//Created by Justinian
//

import java.awt.Point;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class Item extends javax.swing.JPanel
{
	//Fields
	public Type type;
	public Effect effect1;
	public Effect effect2;
	public Pocket pocket;
	//If storeCost is -1 you can't buy it
	public int storeCost;
	public int amount;
	public boolean reUsable;
	public int mNo;
	public String message;
	public int alphabet;
	//If no specific power, it is automatically 100
	public int power1;
	public int power2;
	//Heals this status if STATUS_HEAL and if OK
	//it heals all statuses
	public Pokemon.Status healStatus = null;
	//If tm or Hm, the Move the Pokemon learns
	public Pokemon.Move moveLearned = null;

	public Point location=new Point(0,0);
	public boolean found=true;
	final static Image itemImg = Toolkit.getDefaultToolkit().getImage(Item.class.getResource("Sprites/item.png"));

	//Enum Fields
	public enum Type
	{
		ANTIDOTE,AWAKENING,BICYCLE,BIKE_VOUCHER,BURN_HEAL,
		CALCIUM,CARD_KEY,CARBOS,COIN_CASE,DIRE_HIT,DOME_FOSSIL,
		ELIXER,ESCAPE_ROPE,ETHER,EXPERIENCE_ALL,FIRE_STONE,
		FRESH_WATER,FULL_HEAL,FULL_RESTORE,GOLD_TEETH,
		GOOD_ROD,GREAT_BALL,GUARD_SPECIAL,HELIX_FOSSIL,
		HP_UP,HYPER_POTION,ICE_HEAL,IRON,ITEM_FINDER,
		LEAF_STONE,LEMONADE,LIFT_KEY,MASTER_BALL,MAX_ELIXER,
		MAX_ETHER,MAX_POTION,MAX_REPEL,MAX_REVIVE,MOON_STONE,
		NUGGET,OLD_AMBER,OLD_ROD,PARALYZE_HEAL,POKE_BALL,
		POKE_FLUTE,POTION,PP_UP,PROTEIN,RARE_CANDY,REPEL,
		REVIVE,SS_TICKET,SAFARI_BALL,SECRET_KEY,SILPH_SCOPE,
		SODA_POP,SUPER_POTION,SUPER_ROD,THUNDER_STONE,TOWN_MAP,
		ULTRA_BALL,WATER_STONE,X_ACCURACY,X_ATTACK,X_DEFEND,
		X_SPECIAL,X_SPEED,TM,HM,

		OMEGA_BALL,MOUNTAIN_DEW,BEER,ROTTEN_APPLE,ULTIMA_REPEL,SILVER_NUGGET,REDBULL;

		//Unused Items
		//
		//Card Key, Bike Voucher, Card Key, Coin Case, Dome Fossil, Gold Teeth, Helix Fossil,
		//Itemfinder, Lift Key, Old Amber, PP Up, Poke Flute, SS Ticket, Safari Ball, Secret Key, Silph Scope,

	}
	public enum Effect
	{
		STATUS_HEAL,HP_HEAL,POKEBALL,PP_HEAL,KEY_ITEM,TM,HM,
		BATTLE_BOOST,PERMA_STAT_BOOST,EVO_STONE,POKE_FLUTE,
		GUARD_SPECIAL,ITEM_FINDER,REPEL,FOSSIL,RARE_CANDY,
		MAP,BIKE,ELIXER,EXPERIENCE_ALL,ESCAPE_ROPE,ROD,PP_UP,NONE;
	}
	public enum Pocket
	{
		ITEM,MEDICINE,POKEBALL,TMHM,BATTLE,KEY;
	}
	//End of Enum Fields

	//Constructors only take name
	public Item(Type name, long amount2)
	{
		type = name;
		amount = (int)amount2;
		createItem();
	}

	//Constructors only take name
	public Item(Type name, int amount2)
	{
		type = name;
		amount = (int)amount2;
		createItem();
	}
	//Constructor for Overworld item
	public Item(Type name, Point point)
	{
		type = name;
		amount = 1;
		location.x=point.x;
		location.y=point.y;
		found=false;
		createItem();
	}
	//This helps if you are creating a TM
	public Item(Type name, long amount2, int tmNo)
	{
		type = name;
		mNo = tmNo;
		amount = (int)amount2;
		createItem();
	}

	//This helps if you are creating a TM
	public Item(Type name, int amount2, int tmNo)
	{
		type = name;
		mNo = tmNo;
		amount = (int)amount2;
		createItem();
	}

	//Creates all the information from the name of the item
	private void createItem()
	{
		switch(type)
		{
			case ANTIDOTE:
				effect1 = Effect.STATUS_HEAL;
				power1 = 100;
				healStatus = Pokemon.Status.PSN;
				effect2 = Effect.NONE;
				power2 = 0;
				storeCost = 100;
				pocket = Pocket.MEDICINE;
				reUsable = false;
				message = "Cures poison";
				alphabet = 1;
				break;
			case AWAKENING:
				effect1 = Effect.STATUS_HEAL;
				power1 = 100;
				healStatus = Pokemon.Status.SLP;
				effect2 = Effect.NONE;
				power2 = 0;
				storeCost = 250;
				pocket = Pocket.MEDICINE;
				reUsable = false;
				message = "Wakes up your Pokemon";
				alphabet = 1;
				break;
			case BICYCLE:
				effect1 = Effect.BIKE;
				power1 = 100;
				effect2 = Effect.KEY_ITEM;
				power2 = 100;
				storeCost = 10000000;
				reUsable = true;
				pocket = Pocket.KEY;
				amount = 1;
				message = "Increases movement speed";
				alphabet = 2;
				break;
			case BIKE_VOUCHER:
				effect1 = Effect.KEY_ITEM;
				power1 = 100;
				effect2 = Effect.NONE;
				power2 = 0;
				storeCost = -1;
				reUsable = false;
				amount = 1;
				pocket = Pocket.KEY;
				message = "Use this to purchase a bike";
				alphabet = 2;
				break;
			case CALCIUM:
				effect1 = Effect.PERMA_STAT_BOOST;
				power1 = 10;
				effect2 = Effect.NONE;
				power2 = 0;
				storeCost = 9800;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				message = "Increases Special stat permanently";
				alphabet = 3;
				break;
			case CARBOS:
				effect1 = Effect.PERMA_STAT_BOOST;
				power1 = 10;
				effect2 = Effect.NONE;
				power2 = 0;
				storeCost = 9800;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				message = "Increases Speed stat permanently";
				alphabet = 3;
				break;
			case CARD_KEY:
				effect1 = Effect.KEY_ITEM;
				power1 = 100;
				effect2 = Effect.NONE;
				power2 = 0;
				storeCost = -1;
				reUsable = true;
				amount = 1;
				pocket = Pocket.KEY;
				message = "A card that unlocks a specific door";
				alphabet = 3;
				break;
			case COIN_CASE:
				effect1 = Effect.KEY_ITEM;
				power1 = 100;
				effect2 = Effect.NONE;
				power2 = 0;
				storeCost = -1;
				reUsable = true;
				amount = 1;
				pocket = Pocket.KEY;
				message = "A case that contains all of your coins";
				alphabet = 3;
				break;
			case DIRE_HIT: //Raises Critical Chance
				effect1 = Effect.BATTLE_BOOST;
				power1 = 100;
				effect2 = Effect.NONE;
				power2 = 0;
				storeCost = 650;
				reUsable = false;
				pocket = Pocket.BATTLE;
				message = "Raises critical chance in battle";
				alphabet = 4;
				break;
			case DOME_FOSSIL:
				effect1 = Effect.FOSSIL;
				power1 = 100;
				effect2 = Effect.KEY_ITEM;
				power2 = 0;
				storeCost = -1;
				reUsable = false;
				pocket = Pocket.KEY;
				message = "A mysterious fossil that contains the remains of an extinct Pokemon";
				alphabet = 4;
				break;
			case ELIXER:
				effect1 = Effect.ELIXER;
				power1 = 10;
				effect2 = Effect.NONE;
				power2 = 0;
				storeCost = 20000;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				message = "Restores 10PP to all moves";
				alphabet = 5;
				break;
			case ESCAPE_ROPE:
				effect1 = Effect.ESCAPE_ROPE;
				power1 = 100;
				effect2 = Effect.NONE;
				power2 = 0;
				storeCost = 550;
				reUsable = false;
				pocket = Pocket.ITEM;
				message = "Returns you to a cave entrance. Must be in a cave";
				alphabet = 5;
				break;
			case ETHER:
				effect1 = Effect.PP_HEAL;
				power1 = 10;
				effect2 = Effect.NONE;
				power2 = 0;
				storeCost = 5000;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				message = "Restores 10PP to one move";
				alphabet = 5;
				break;
			case EXPERIENCE_ALL:
				effect1 = Effect.EXPERIENCE_ALL;
				power1 = 6;
				effect2 = Effect.NONE;
				power2 = 0;
				storeCost = 50000;
				reUsable = true;
				pocket = Pocket.ITEM;
				message = "Splits EXP recieved in battle between all pokemon in your party";
				alphabet = 5;
				break;
			case FIRE_STONE:
				effect1 = Effect.EVO_STONE;
				power1 = 100;
				effect2 = Effect.NONE;
				storeCost = 2100;
				reUsable = false;
				pocket = Pocket.ITEM;
				message = "Evolves certain fire type Pokemon";
				alphabet = 6;
				break;
			case FRESH_WATER:
				effect1 = Effect.HP_HEAL;
				power1 = 50;
				effect2 = Effect.NONE;
				storeCost = 200;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 6;
				message = "Heals 50HP";
				break;
			case FULL_HEAL:
				effect1 = Effect.STATUS_HEAL;
				power1 = 100;
				healStatus = Pokemon.Status.OK;
				effect2 = Effect.NONE;
				storeCost = 600;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 6;
				message = "Cures any condition";
				break;
			case FULL_RESTORE:
				effect1 = Effect.HP_HEAL;
				power1 = 99999;
				effect2 = Effect.STATUS_HEAL;
				power2 = 100;
				healStatus = Pokemon.Status.OK;
				storeCost = 3000;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 6;
				message = "Heals all HP and cures any condition";
				break;
			case GOLD_TEETH:
				effect1 = Effect.KEY_ITEM;
				power1 = 100;
				effect2 = Effect.NONE;
				storeCost = -1;
				reUsable = true;
				pocket = Pocket.KEY;
				message = "Gold colored dentures";
				alphabet = 7;
				break;
			case GOOD_ROD:
				effect1 = Effect.ROD;
				power1 =2;
				effect2 = Effect.KEY_ITEM;
				storeCost = -1;
				reUsable = true;
				pocket = Pocket.KEY;
				message = "A better fishing rod";
				alphabet = 7;
				break;
			case GREAT_BALL:
				effect1 = Effect.POKEBALL;
				power1 = 2;
				effect2 = Effect.NONE;
				storeCost = 600;
				reUsable = false;
				pocket = Pocket.POKEBALL;
				message = "A pokeball with a higher catch rate than a pokeball";
				alphabet = 7;
				break;
			case GUARD_SPECIAL:
				effect1 = Effect.BATTLE_BOOST;
				power1 = 100;
				effect2 = Effect.NONE;
				storeCost = 700;
				reUsable = false;
				pocket = Pocket.BATTLE;
				message = "Protects one Pokemon in battle from special attacks";
				alphabet = 7;
				break;
			case HELIX_FOSSIL:
				effect1 = Effect.FOSSIL;
				power1 = 100;
				effect2 = Effect.KEY_ITEM;
				storeCost = -1;
				reUsable = false;
				pocket = Pocket.KEY;
				message = "A mysterious fossil that contains the remains of an extinct Pokemon";
				alphabet = 8;
				break;
			case HP_UP:
				effect1 = Effect.PERMA_STAT_BOOST;
				power1 = 10;
				effect2 = Effect.NONE;
				storeCost = 9800;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 8;
				message = "Raises Health stat permanently";
				break;
			case HYPER_POTION:
				effect1 = Effect.HP_HEAL;
				power1 = 200;
				effect2 = Effect.NONE;
				storeCost = 1500;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 8;
				message = "Heals 200HP";
				break;
			case ICE_HEAL:
				effect1 = Effect.STATUS_HEAL;
				power1 = 100;
				healStatus = Pokemon.Status.FRZ;
				effect2 = Effect.NONE;
				storeCost = 250;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 9;
				message = "Defrosts one Pokemon";
				break;
			case IRON:
				effect1 = Effect.PERMA_STAT_BOOST;
				power1 = 10;
				effect2 = Effect.NONE;
				storeCost = 9800;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				message = "Raises Defense stat permanently";
				alphabet = 9;
				break;
			case ITEM_FINDER:
				effect1 = Effect.ITEM_FINDER;
				power1 = 100;
				effect2 = Effect.KEY_ITEM;
				storeCost = -1;
				reUsable = true;
				pocket = Pocket.KEY;
				message = "Reveals hidden items";
				alphabet = 9;
				break;
			case LEAF_STONE:
				effect1 = Effect.EVO_STONE;
				power1 = 100;
				effect2 = Effect.NONE;
				storeCost = 2100;
				reUsable = false;
				pocket = Pocket.ITEM;
				alphabet = 12;
				message = "Evolves certain grass type Pokemon";
				break;
			case LEMONADE:
				effect1 = Effect.HP_HEAL;
				power1 = 80;
				effect2 = Effect.NONE;
				storeCost = 350;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 12;
				message = "Heals 80HP";
				break;
			case LIFT_KEY:
				effect1 = Effect.KEY_ITEM;
				effect2 = Effect.NONE;
				storeCost = -1;
				reUsable = true;
				pocket = Pocket.KEY;
				alphabet = 12;
				message = "Unlocks a certain elevator";
				break;
			case MASTER_BALL:
				effect1 = Effect.POKEBALL;
				power1 = 255;
				effect2 = Effect.NONE;
				storeCost = 9999999;
				reUsable = false;
				pocket = Pocket.POKEBALL;
				alphabet = 13;
				message = "A pokeball that wont fail";
				break;
			case MAX_ELIXER:
				effect1 = Effect.ELIXER;
				power1 = 255;
				effect2 = Effect.NONE;
				storeCost = 40000;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 13;
				message = "Resores max PP to all moves";
				break;
			case MAX_ETHER:
				effect1 = Effect.PP_HEAL;
				power1 = 255;
				effect2 = Effect.NONE;
				storeCost = 10000;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 13;
				message = "Restores max PP to one move";
				break;
			case MAX_POTION:
				effect1 = Effect.HP_HEAL;
				power1 = 99999;
				effect2 = Effect.NONE;
				storeCost = 2500;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 13;
				message = "Restores max HP";
				break;
			case MAX_REPEL:
				effect1 = Effect.REPEL;
				power1 = 250;
				effect2 = Effect.NONE;
				storeCost = 700;
				reUsable = false;
				pocket = Pocket.ITEM;
				alphabet = 13;
				message = "Repels for 250 steps";
				break;
			case MAX_REVIVE:
				effect1 = Effect.STATUS_HEAL;
				power1 = 100;
				healStatus = Pokemon.Status.FNT;
				effect2 = Effect.NONE;
				power2 = 0;
				storeCost = 5000;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 13;
				message = "Revives one Pokemon to full health";
				break;
			case MOON_STONE:
				effect1 = Effect.EVO_STONE;
				power1 = 100;
				effect2 = Effect.NONE;
				storeCost = 2100;
				reUsable = false;
				pocket = Pocket.ITEM;
				alphabet = 13;
				message = "Evolves certain normal type Pokemon";
				break;
			case NUGGET:
				effect1 = Effect.NONE;
				effect2 = Effect.NONE;
				storeCost = 10000;
				reUsable = true;
				pocket = Pocket.ITEM;
				alphabet = 14;
				message = "A valuable item";
				break;
			case OLD_AMBER:
				effect1 = Effect.FOSSIL;
				effect2 = Effect.KEY_ITEM;
				storeCost = -1;
				reUsable = false;
				pocket = Pocket.KEY;
				alphabet = 15;
				message = "An amber containing Pokemon DNA";
				break;
			case OLD_ROD:
				effect1 = Effect.ROD;
				power1 = 1;
				effect2 = Effect.KEY_ITEM;
				storeCost = -1;
				reUsable = true;
				pocket = Pocket.KEY;
				alphabet = 15;
				message = "A basic rod";
				break;
			case PARALYZE_HEAL:
				effect1 = Effect.STATUS_HEAL;
				power1 = 100;
				healStatus = Pokemon.Status.PAR;
				effect2 = Effect.NONE;
				storeCost = 200;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 16;
				message = "Cures paralysis";
				break;
			case POKE_BALL:
				effect1 = Effect.POKEBALL;
				power1 = 1;
				effect2 = Effect.NONE;
				storeCost = 200;
				reUsable = false;
				pocket = Pocket.POKEBALL;
				alphabet = 16;
				message = "A basic pokeball";
				break;
			case POKE_FLUTE:
				effect1 = Effect.POKE_FLUTE;
				effect2 = Effect.KEY_ITEM;
				storeCost = -1;
				reUsable = true;
				pocket = Pocket.KEY;
				alphabet = 16;
				message = "A mysterious flute";
				break;
			case POTION:
				effect1 = Effect.HP_HEAL;
				power1 = 20;
				effect2 = Effect.NONE;
				storeCost = 300;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 16;
				message = "Heals 20HP";
				break;
			case PP_UP:
				effect1 = Effect.PP_UP;
				power1 = 20;
				effect2 = Effect.NONE;
				storeCost = 20000;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 16;
				message = "Raises PP by 20% for one move";
				break;
			case PROTEIN:
				effect1 = Effect.PERMA_STAT_BOOST;
				power1 = 10;
				effect2 = Effect.NONE;
				storeCost = 9800;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 16;
				message = "Raises Attack stat permanently";
				break;
			case RARE_CANDY:
				effect1 = Effect.RARE_CANDY;
				effect2 = Effect.NONE;
				storeCost = 8000;
				reUsable = false;
				pocket = Pocket.ITEM;
				alphabet = 18;
				message = "Raises one level";
				break;
			case REPEL:
				effect1 = Effect.REPEL;
				power1 = 100;
				effect2 = Effect.NONE;
				storeCost = 350;
				reUsable = false;
				pocket = Pocket.ITEM;
				alphabet = 18;
				message = "Repels for 100 steps";
				break;
			case REVIVE:
				effect1 = Effect.STATUS_HEAL;
				power1 = 100;
				healStatus = Pokemon.Status.FNT;
				effect2 = Effect.NONE;
				storeCost = 1500;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 18;
				message = "Revives one pokemon to half health";
				break;
			case SS_TICKET:
				effect1 = Effect.KEY_ITEM;
				effect2 = Effect.NONE;
				storeCost = -1;
				reUsable = true;
				pocket = Pocket.KEY;
				alphabet = 19;
				message = "A ticket to get on the S.S. Anne";
				break;
			case SAFARI_BALL:
				effect1 = Effect.POKEBALL;
				power1 = 2;
				effect2 = Effect.NONE;
				storeCost = 0;
				reUsable = false;
				pocket = Pocket.POKEBALL;
				alphabet = 19;
				message = "A pokeball used in the Safari Zone";
				break;
			case SECRET_KEY:
				effect1 = Effect.KEY_ITEM;
				effect2 = Effect.NONE;
				storeCost = -1;
				reUsable = true;
				pocket = Pocket.KEY;
				alphabet = 19;
				message = "A secretive key";
				break;
			case SILPH_SCOPE:
				effect1 = Effect.KEY_ITEM;
				effect2 = Effect.NONE;
				storeCost = -1;
				reUsable = true;
				pocket = Pocket.KEY;
				alphabet = 19;
				message = "Allows you to see ghosts";
				break;
			case SODA_POP:
				effect1 = Effect.HP_HEAL;
				power1 = 60;
				effect2 = Effect.NONE;
				storeCost= 300;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 19;
				message = "Heals 60HP";
				break;
			case SUPER_POTION:
				effect1 = Effect.HP_HEAL;
				power1 = 70;
				effect2 = Effect.NONE;
				storeCost = 700;
				reUsable = false;
				pocket = Pocket.MEDICINE;
				alphabet = 19;
				message = "Heals 70HP";
				break;
			case SUPER_ROD:
				effect1 = Effect.ROD;
				power1 = 3;
				effect2 = Effect.KEY_ITEM;
				storeCost = 7500;
				reUsable = true;
				pocket = Pocket.KEY;
				alphabet = 19;
				message = "The best rod";
				break;
			case THUNDER_STONE:
				effect1 = Effect.EVO_STONE;
				effect2 = Effect.NONE;
				storeCost = 2100;
				reUsable = false;
				pocket = Pocket.ITEM;
				alphabet = 20;
				message = "Evolves certain thunder type Pokemon";
				break;
			case TOWN_MAP:
				effect1 = Effect.MAP;
				effect2 = Effect.KEY_ITEM;
				storeCost = -1;
				reUsable = true;
				pocket = Pocket.KEY;
				alphabet = 20;
				message = "Map of the entire region";
				break;
			case ULTRA_BALL:
				effect1 = Effect.POKEBALL;
				power1 = 3;
				effect2 = Effect.NONE;
				storeCost = 1500;
				reUsable = false;
				pocket = Pocket.POKEBALL;
				alphabet = 21;
				message = "A stronger pokeball than great ball";
				break;
			case WATER_STONE:
				effect1 = Effect.EVO_STONE;
				effect2 = Effect.NONE;
				storeCost = 2100;
				reUsable = false;
				pocket = Pocket.ITEM;
				alphabet = 22;
				message = "Evolves certain water type Pokemon";
				break;
			case X_ACCURACY:
				effect1 = Effect.BATTLE_BOOST;
				power1 = 100;
				effect2 = Effect.NONE;
				storeCost = 950;
				reUsable = false;
				pocket = Pocket.BATTLE;
				alphabet = 23;
				message = "Temporarily raises accuracy in battle";
				break;
			case X_ATTACK:
				effect1 = Effect.BATTLE_BOOST;
				power1 = 100;
				effect2 = Effect.NONE;
				storeCost = 500;
				reUsable = false;
				pocket = Pocket.BATTLE;
				alphabet = 23;
				message = "Temporarily raises attack in battle";
				break;
			case X_DEFEND:
				effect1 = Effect.BATTLE_BOOST;
				power1 = 100;
				effect2 = Effect.NONE;
				storeCost = 550;
				reUsable = false;
				pocket = Pocket.BATTLE;
				alphabet = 23;
				message = "Temporarily raises defense in battle";
				break;
			case X_SPECIAL:
				effect1 = Effect.BATTLE_BOOST;
				power1 = 100;
				effect2 = Effect.NONE;
				storeCost = 550;
				reUsable = false;
				pocket = Pocket.BATTLE;
				alphabet = 23;
				message = "Temporarily raises special in battle";
				break;
			case X_SPEED:
				effect1 = Effect.BATTLE_BOOST;
				power1 = 100;
				effect2 = Effect.NONE;
				storeCost = 550;
				reUsable = false;
				pocket = Pocket.BATTLE;
				alphabet = 23;
				message = "Temporarily raises speed in battle";
				break;
			//For both Tm and Hm their messages and
			//their move learned is depends on machine number
			case TM:
			{
				effect1 = Effect.TM;
				effect2 = Effect.NONE;
				reUsable = true;
				alphabet = 20;
				pocket = Pocket.TMHM;
				if (mNo <= 0 || mNo > 50)
					mNo = 1;
				switch (mNo)
				{
					case 1:
						moveLearned = Pokemon.Move.MEGA_PUNCH;
						message = "Teaches one Pokemon Mega Punch";
						storeCost = 3000;
						break;
					case 2:
						moveLearned = Pokemon.Move.RAZOR_WIND;
						message = "Teaches one Pokemon Razor Wind";
						storeCost = 1000;
						break;
					case 3:
						moveLearned = Pokemon.Move.SWORDS_DANCE;
						message = "Teaches one Pokemon Sword's Dance";
						storeCost = 4000;
						break;
					case 4:
						moveLearned = Pokemon.Move.WHIRLWIND;
						message = "Teaches one Pokemon Whirlwind";
						storeCost = 1000;
						break;
					case 5:
						moveLearned = Pokemon.Move.MEGA_KICK;
						message = "Teaches one Pokemon Mega Kick";
						storeCost = 2000;
						break;
					case 6:
						moveLearned = Pokemon.Move.TOXIC;
						message = "Teaches one Pokemon Toxic";
						storeCost = 5000;
						break;
					case 7:
						moveLearned = Pokemon.Move.HORN_DRILL;
						message = "Teaches one Pokemon Horn Drill";
						storeCost = 3000;
						break;
					case 8:
						moveLearned = Pokemon.Move.BODY_SLAM;
						message = "Teaches one Pokemon Body Slam";
						storeCost = 4000;
						break;
					case 9:
						moveLearned = Pokemon.Move.TAKE_DOWN;
						message = "Teaches one Pokemon Take Down";
						storeCost = 3000;
						break;
					case 10:
						moveLearned = Pokemon.Move.DOUBLE_EDGE;
						message = "Teaches one Pokemon Double Edge";
						storeCost = 3000;
						break;
					case 11:
						moveLearned = Pokemon.Move.BUBBLEBEAM;
						message = "Teaches one Pokemon Bubblebeam";
						storeCost = 3000;
						break;
					case 12:
						moveLearned = Pokemon.Move.WATER_GUN;
						message = "Teaches one Pokemon Water Gun";
						storeCost = 1500;
						break;
					case 13:
						moveLearned = Pokemon.Move.ICE_BEAM;
						message = "Teaches one Pokemon Ice Beam";
						storeCost = 5000;
						break;
					case 14:
						moveLearned = Pokemon.Move.BLIZZARD;
						message = "Teaches one Pokemon Blizzard";
						storeCost = 5000;
						break;
					case 15:
						moveLearned = Pokemon.Move.HYPER_BEAM;
						message = "Teaches one Pokemon Hyper Beam";
						storeCost = 5000;
						break;
					case 16:
						moveLearned = Pokemon.Move.PAY_DAY;
						message = "Teaches one Pokemon Pay Day";
						storeCost = 1000;
						break;
					case 17:
						moveLearned = Pokemon.Move.SUBMISSION;
						message = "Teaches one Pokemon Submission";
						storeCost = 5000;
						break;
					case 18:
						moveLearned = Pokemon.Move.COUNTER;
						message = "Teaches one Pokemon Counter";
						storeCost = 3500;
						break;
					case 19:
						moveLearned = Pokemon.Move.SEISMIC_TOSS;
						message = "Teaches one Pokemon Seismic Toss";
						storeCost = 4000;
						break;
					case 20:
						moveLearned = Pokemon.Move.RAGE;
						message = "Teaches one Pokemon Rage";
						storeCost = 2500;
						break;
					case 21:
						moveLearned = Pokemon.Move.MEGA_DRAIN;
						message = "Teaches one Pokemon Mega Drain";
						storeCost = 4000;
						break;
					case 22:
						moveLearned = Pokemon.Move.SOLARBEAM;
						message = "Teaches one Pokemon Solarbeam";
						storeCost = 5000;
						break;
					case 23:
						moveLearned = Pokemon.Move.DRAGON_RAGE;
						message = "Teaches one Pokemon Dragon Rage";
						storeCost = 5000;
						break;
					case 24:
						moveLearned = Pokemon.Move.THUNDERBOLT;
						message = "Teaches one Pokemon Thunderbolt";
						storeCost = 5000;
						break;
					case 25:
						moveLearned = Pokemon.Move.THUNDER;
						message = "Teaches one Pokemon Thunder";
						storeCost = 5000;
						break;
					case 26:
						moveLearned = Pokemon.Move.EARTHQUAKE;
						message = "Teaches one Pokemon Earthquake";
						storeCost = 10000;
						break;
					case 27:
						moveLearned = Pokemon.Move.FISSURE;
						message = "Teaches one Pokemon Fissure";
						storeCost = 2500;
						break;
					case 28:
						moveLearned = Pokemon.Move.DIG;
						message = "Teaches one Pokemon Dig";
						storeCost = 3000;
						break;
					case 29:
						moveLearned = Pokemon.Move.PSYCHIC;
						message = "Teaches one Pokemon Psychic";
						storeCost = 5000;
						break;
					case 30:
						moveLearned = Pokemon.Move.TELEPORT;
						message = "Teaches one Pokemon Teleport";
						storeCost = 500;
						break;
					case 31:
						moveLearned = Pokemon.Move.MIMIC;
						message = "Teaches one Pokemon Mimic";
						storeCost = 5000;
						break;
					case 32:
						moveLearned = Pokemon.Move.DOUBLE_TEAM;
						message = "Teaches one Pokemon Double Team";
						storeCost = 2000;
						break;
					case 33:
						moveLearned = Pokemon.Move.REFLECT;
						message = "Teaches one Pokemon Reflect";
						storeCost = 2000;
						break;
					case 34:
						moveLearned = Pokemon.Move.BIDE;
						message = "Teaches one Pokemon Bide";
						storeCost = 1500;
						break;
					case 35:
						moveLearned = Pokemon.Move.METRONOME;
						message = "Teaches one Pokemon Metronome";
						storeCost = 7500;
						break;
					case 36:
						moveLearned = Pokemon.Move.SELFDESTRUCT;
						message = "Teaches one Pokemon Self Destruct";
						storeCost = 2000;
						break;
					case 37:
						moveLearned = Pokemon.Move.EGG_BOMB;
						message = "Teaches one Pokemon Egg Bomb";
						storeCost = 1500;
						break;
					case 38:
						moveLearned = Pokemon.Move.FIRE_BLAST;
						message = "Teaches one Pokemon Fire Blast";
						storeCost = 5000;
						break;
					case 39:
						moveLearned = Pokemon.Move.SWIFT;
						message = "Teaches one Pokemon Swift";
						storeCost = 3500;
						break;
					case 40:
						moveLearned = Pokemon.Move.SKULL_BASH;
						message = "Teaches one Pokemon Skull Bash";
						storeCost = 2000;
						break;
					case 41:
						moveLearned = Pokemon.Move.SOFTBOILED;
						message = "Teaches one Pokemon Softboiled";
						storeCost = 4000;
						break;
					case 42:
						moveLearned = Pokemon.Move.DREAM_EATER;
						message = "Teaches one Pokemon Dream Eater";
						storeCost = 4000;
						break;
					case 43:
						moveLearned = Pokemon.Move.SKY_ATTACK;
						message = "Teaches one Pokemon Sky Attack";
						storeCost = 3000;
						break;
					case 44:
						moveLearned = Pokemon.Move.REST;
						message = "Teaches one Pokemon Rest";
						storeCost = 5000;
						break;
					case 45:
						moveLearned = Pokemon.Move.THUNDER_WAVE;
						message = "Teaches one Pokemon Thunder Wave";
						storeCost = 4000;
						break;
					case 46:
						moveLearned = Pokemon.Move.PSYWAVE;
						message = "Teaches one Pokemon Psywave";
						storeCost = 1500;
						break;
					case 47:
						moveLearned = Pokemon.Move.EXPLOSION;
						message = "Teaches one Pokemon Explosion";
						storeCost = 5000;
						break;
					case 48:
						moveLearned = Pokemon.Move.ROCK_SLIDE;
						message = "Teaches one Pokemon Rock Slide";
						storeCost = 5000;
						break;
					case 49:
						moveLearned = Pokemon.Move.TRI_ATTACK;
						message = "Teaches one Pokemon Tri Attack";
						storeCost = 2500;
						break;
					case 50:
						moveLearned = Pokemon.Move.SUBSTITUTE;
						message = "Teaches one Pokemon Substitute";
						storeCost = 10000;
						break;
					default:
						moveLearned = Pokemon.Move.TACKLE;
						message = "Invalid Tm";
						storeCost = 7000;
						break;
				}
			}
			break;
			case HM:
			{
				effect1 = Effect.HM;
				effect2 = Effect.NONE;
				reUsable = true;
				storeCost = -1;
				alphabet = 8;
				pocket = Pocket.TMHM;
				switch(mNo)
				{
					case 1:
						moveLearned = Pokemon.Move.CUT;
						message = "Teaches one Pokemon Cut";
						break;
					case 2:
						moveLearned = Pokemon.Move.FLY;
						message = "Teaches one Pokemon Fly";
						break;
					case 3:
						moveLearned = Pokemon.Move.SURF;
						message = "Teaches one Pokemon Surf";
						break;
					case 4:
						moveLearned = Pokemon.Move.STRENGTH;
						message = "Teaches one Pokemon Strength";
						break;
					case 5:
						moveLearned = Pokemon.Move.FLASH;
						message = "Teaches one Pokemon Flash";
						break;
				}
			}
			break;




			//Custom Made Items from here on down
			case OMEGA_BALL:
				effect1 = Effect.POKEBALL;
				power1 = 255;
				effect2 = Effect.NONE;
				storeCost = 99999999;
				reUsable = true;
				pocket = Pocket.POKEBALL;
				message = "A perfect ball that can be reused";
				alphabet = 15;
				break;
			case MOUNTAIN_DEW:
				effect1 = Effect.HP_HEAL;
				power1 = 200;
				effect2 = Effect.BATTLE_BOOST;
				power2 = 100;
				reUsable = false;
				storeCost = 2000;
				pocket = Pocket.MEDICINE;
				message = "Heals 200HP and raises speed";
				alphabet = 13;
				break;
			case BEER:
				effect1 = Effect.BATTLE_BOOST;
				power1 = -100;
				effect2 = Effect.NONE;
				reUsable = false;
				storeCost = 1;
				pocket = Pocket.MEDICINE;
				message = "Lowers accuracy";
				alphabet = 2;
				break;
			case ROTTEN_APPLE:
				effect1 = Effect.HP_HEAL;
				power1 = -2;
				effect2 = Effect.NONE;
				reUsable = true;
				storeCost = 1;
				pocket = Pocket.MEDICINE;
				message = "Heals -2HP";
				alphabet = 18;
				break;
			case ULTIMA_REPEL:
				effect1 = Effect.REPEL;
				power1 = 250;
				effect2 = Effect.NONE;
				reUsable = true;
				storeCost = 99999999;
				pocket = Pocket.ITEM;
				alphabet = 21;
				message = "Repels for 250 steps. Reusable";
				break;
			case SILVER_NUGGET:
				effect1 = Effect.NONE;
				effect2 = Effect.NONE;
				storeCost = 100000;
				reUsable = true;
				pocket = Pocket.ITEM;
				alphabet = 19;
				message = "A valuable item";
				break;
			case REDBULL:
				effect1 = Effect.TM;
				effect2 = Effect.NONE;
				storeCost = 1000;
				reUsable = true;
				pocket = Pocket.TMHM;
				alphabet = 18;
				message = "So RedBull does give you wings.";
				moveLearned = Pokemon.Move.FLY;
				break;

		}
		if (effect1 == Effect.KEY_ITEM)
		amount = 1;
	}

	public String toString()
	{
		if (type != Type.TM && type != Type.HM)
		return "\n" + type.toString() + "\nAMOUNT: " + amount + "\n" + message + "\n" + pocket.toString() + "\n";
		else
		return "\n" + type.toString() + " " + mNo + "\nAMOUNT: " + amount +  "\n" + message + "\n" + pocket.toString() + "\n";
	}
	public String getName()
	{
		String returnString = type.toString();
		returnString = returnString.replace('_',' ');
		if ((effect1 == Effect.TM || effect1 == Effect.HM) && type != Type.REDBULL)
		{
			returnString = returnString + " " + mNo;
		}
		returnString = returnString + " x" + amount;
		return returnString;
	}
	public String getNameMoney()
	{
		String returnString = type.toString();
		returnString = returnString.replace('_',' ');
		if ((effect1 == Effect.TM || effect1 == Effect.HM) && type != Type.REDBULL)
		{
			returnString = returnString + " " + mNo;
			System.out.println(moveLearned);
		}
		returnString = returnString + " $" + storeCost;
		return returnString;
	}
	public String getNameMoney(int i)
	{
		String returnString = type.toString();
		returnString = returnString.replace('_',' ');
		if (effect1 == Effect.TM || effect1 == Effect.HM)
		{
			returnString = returnString + " " + mNo;
		}
		returnString = returnString + " $" + storeCost/2;
		return returnString;
	}

	//Draws Item on map
	public void drawItem(Graphics g)
	{
		if(found)
			return;
		g.drawImage(itemImg,(64*6)+64*(location.x-JokemonDriver.location.x),64*4+64*(location.y-JokemonDriver.location.y),64,64,this);
	}

	//Returns if item starts with a vowel
	public boolean startsWithVowel()
	{
		char c=this.type.toString().charAt(0);

		switch(c)
		{
			case 'A':
			case 'E':
			case 'I':
			case 'O':
			case 'U':
				return true;
			default:
				return false;
		}
	}
}