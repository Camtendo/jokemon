//////////////////
//Pokemon Class
//By Camtendo and Justinian
//
//
//
//

public class Pokemon
{
	public boolean IS_TRADED=false;

	public int HP_IV=(int)(Math.random()*31)+1;
	public int ATK_IV=(int)(Math.random()*31)+1;
	public int DEF_IV=(int)(Math.random()*31)+1;
	public int SPCL_IV=(int)(Math.random()*31)+1;
	public int SPEED_IV=(int)(Math.random()*31)+1;

	public int HP_EV=0;
	public int ATK_EV=0;
	public int DEF_EV=0;
	public int SPCL_EV=0;
	public int SPEED_EV=0;

	public enum Species
	{
		BULBASAUR,IVYSAUR,VENUSAUR,CHARMANDER,CHARMELEON,CHARIZARD,SQUIRTLE,
		WARTORTLE,BLASTOISE,CATERPIE,METAPOD,BUTTERFREE,WEEDLE,KAKUNA,BEEDRILL,
		PIDGEY,PIDGEOTTO,PIDGEOT,RATTATA,RATICATE,SPEAROW,FEAROW,EKANS,ARBOK,PIKACHU,
		RAICHU,SANDSHREW,SANDSLASH,NIDORAN_F,NIDORINA,NIDOQUEEN,NIDORAN_M,
		NIDORINO,NIDOKING,CLEFAIRY,CLEFABLE,VULPIX,NINETALES,JIGGLYPUFF,WIGGLYTUFF,ZUBAT,
		GOLBAT,ODDISH,GLOOM,VILEPLUME,PARAS,PARASECT,VENONAT,VENOMOTH,DIGLETT,DUGTRIO,
		MEOWTH,PERSIAN,PSYDUCK,GOLDUCK,MANKEY,PRIMEAPE,GROWLITHE,ARCANINE,POLIWAG,POLIWHIRL,
		POLIWRATH,ABRA,KADABRA,ALAKAZAM,MACHOP,MACHOKE,MACHAMP,BELLSPROUT,WEEPINBELL,
		VICTREEBEL,TENTACOOL,TENTACRUEL,GEODUDE,GRAVELER,GOLEM,PONYTA,RAPIDASH,SLOWPOKE,
		SLOWBRO,MAGNEMITE,MAGNETON,FARFETCH_D,DODUO,DODRIO,SEEL,DEWGONG,GRIMER,MUK,
		SHELLDER,CLOYSTER,GASTLY,HAUNTER,GENGAR,ONIX,DROWZEE,HYPNO,KRABBY,KINGLER,VOLTORB,
		ELECTRODE,EXEGGCUTE,EXEGGUTOR,CUBONE,MAROWAK,HITMONLEE,HITMONCHAN,LICKITUNG,KOFFING,
		WEEZING,RHYHORN,RHYDON,CHANSEY,TANGELA,KANGASKHAN,HORSEA,SEADRA,GOLDEEN,SEAKING,
		STARYU,STARMIE,MR_MIME,SCYTHER,JYNX,ELECTABUZZ,MAGMAR,PINSIR,TAUROS,MAGIKARP,
		GYARADOS,LAPRAS,DITTO,EEVEE,VAPOREON,JOLTEON,FLAREON,PORYGON,OMANYTE,OMASTAR,KABUTO,
		KABUTOPS,AERODACTYL,SNORLAX,ARTICUNO,ZAPDOS,MOLTRES,DRATINI,DRAGONAIR,DRAGONITE,
		MEWTWO,MEW,MISSINGNO
	}

	public enum Type
	{
		BUG,DRAGON,ELECTRIC,FIGHTING,FIRE,FLYING,GHOST,GRASS,GROUND,ICE,
		NORMAL,POISON,PSYCHIC,ROCK,WATER,NONE,BIRD
	}

	public enum Status
	{
		OK,SLP,PSN,PAR,BRN,FRZ,FNT
	}

	public enum Substatus
	{
		OK,CONFU,FLINCH,SEED
	}

	public enum Move
	{
		ABSORB,ACID,ACID_ARMOR,AGILITY,AMNESIA,AURORA_BEAM,BARRAGE,BARRIER,
		BIDE,BIND,BITE,BLIZZARD,BODY_SLAM,BONE_CLUB,BONEMERANG,BUBBLE,BUBBLEBEAM,
		CLAMP,COMET_PUNCH,CONFUSE_RAY,CONFUSION,CONSTRICT,CONVERSION,COUNTER,
		CRABHAMMER,CUT,DEFENSE_CURL,DIG,DISABLE,DIZZY_PUNCH,DOUBLE_KICK,
		DOUBLE_TEAM,DOUBLE_EDGE,DOUBLESLAP,DRAGON_RAGE,DREAM_EATER,DRILL_PECK,
		EARTHQUAKE,EGG_BOMB,EMBER,EXPLOSION,FIRE_BLAST,FIRE_PUNCH,FIRE_SPIN,
		FISSURE,FLAMETHROWER,FLASH,FLY,FOCUS_ENERGY,FURY_ATTACK,FURY_SWIPES,GLARE,
		GROWL,GROWTH,GUILLOTINE,GUST,HARDEN,HAZE,HEADBUTT,HI_JUMP_KICK,HORN_ATTACK,
		HORN_DRILL,HYDRO_PUMP,HYPER_BEAM,HYPER_FANG,HYPNOSIS,ICE_BEAM,ICE_PUNCH,
		JUMP_KICK,KARATE_CHOP,KINESIS,LEECH_LIFE,LEECH_SEED,LEER,LICK,LIGHT_SCREEN,
		LOVELY_KISS,LOW_KICK,MEDITATE,MEGA_DRAIN,MEGA_KICK,MEGA_PUNCH,METRONOME,
		MIMIC,MINIMIZE,MIRROR_MOVE,MIST,NIGHT_SHADE,PAY_DAY,PECK,PETAL_DANCE,
		PIN_MISSILE,POISON_GAS,POISON_STING,POISONPOWDER,POUND,PSYBEAM,PSYCHIC,
		PSYWAVE,QUICK_ATTACK,RAGE,RAZOR_LEAF,RAZOR_WIND,RECOVER,REFLECT,REST,ROAR,
		ROCK_SLIDE,ROCK_THROW,ROLLING_KICK,SAND_ATTACK,SCRATCH,SCREECH,SEISMIC_TOSS,
		SELFDESTRUCT,SHARPEN,SING,SKULL_BASH,SKY_ATTACK,SLAM,SLASH,SLEEP_POWDER,
		SLUDGE,SMOG,SMOKESCREEN,SOFTBOILED,SOLARBEAM,SONIC_BOOM,SPIKE_CANNON,SPLASH,
		SPORE,STOMP,STRENGTH,STRING_SHOT,STRUGGLE,STUN_SPORE,SUBMISSION,SUBSTITUTE,
		SUPER_FANG,SUPERSONIC,SURF,SWIFT,SWORDS_DANCE,TACKLE,TAIL_WHIP,TAKE_DOWN,
		TELEPORT,THRASH,THUNDER,THUNDER_WAVE,THUNDERBOLT,THUNDERPUNCH,THUNDERSHOCK,
		TOXIC,TRANSFORM,TRI_ATTACK,TWINEEDLE,VICEGRIP,VINE_WHIP,WATER_GUN,WATERFALL,
		WHIRLWIND,WING_ATTACK,WITHDRAW,WRAP,NONE;

		int accuracy;
		Primary_Effect mainEffect;
		Side_Effect sideEffect;
		Status status;
		Substatus substatus;
		int sideEffectAcc;
		int basePower;
		int pp,ppMax;
		Type moveType;
	}

	int TRUE_PP[]={0,0,0,0}, TRUE_PPMAX[]={0,0,0,0};

	public enum Side_Effect
	{
		NONE, STAT, STATUS, SUBSTATUS, MULTI_HIT, MULTI_TURN, ABSORB, OHKO, HIGH_CRIT, CHARGE,
		FAINT, RECOIL, SUBSTITUTE, DISABLE, RECOVERY ,COLLECT_DAMAGE, TRANSFORM,
		CONVERSION, FIXED_DAMAGE, METRONOME, MIMIC, MIRROR_MOVE, QUICK_ATTACK, SWIFT, ROAR, HIDE, FURY

	}

	public enum Primary_Effect
	{
		DAMAGE, RAISE_STAT, LOWER_STAT, INFLICT_STATUS, SPECIAL, NONE
	}

	String nickname,originalTrainer;
	int idNumber,pokedexNumber;
	int level,exp;
	int health,atk,def,speed,spcl,
		healthMax,atkStage,defStage,speedStage,spclStage;
	double eva=1;
	int evaStage,accuracyStage;

	int baseHP,baseATK,baseDEF,baseSPEED,baseSPCL;

	Type type1,type2;
	Species species;
	Status status=Status.OK;
	Substatus substatus=Substatus.OK;
	Move move[]=new Move[4];

	//Create a brand new Pokemon
	public Pokemon(Species s)
	{
		level=5;
		exp=100;
		createPokemon(s,level);
		createMoves();
	}

	//Create a brand new Pokemon with a specified level
	public Pokemon(Species s, int lvl)
	{
		level=lvl;
		exp=(lvl-1)*(lvl-1)*(lvl-1);
		exp+=lvl;
		createPokemon(s,level);
		createMoves();
	}

	//Creates a Pokemon for Trainers with moveset and level
	public Pokemon(Species s, Move m1, int lvl)
	{
		level=lvl;
		exp=(lvl-1)*(lvl-1)*(lvl-1);
		exp+=lvl;
		createPokemon(s,level);
		move[0]=m1;
		createMoves();
	}

	//Creates a Pokemon for Trainers with moveset and level
	public Pokemon(Species s, Move m1, Move m2, int lvl)
	{
		level=lvl;
		exp=(lvl-1)*(lvl-1)*(lvl-1);
		exp+=lvl;
		createPokemon(s,level);
		move[0]=m1;
		move[1]=m2;
		createMoves();
	}

	//Creates a Pokemon for Trainers with moveset and level
	public Pokemon(Species s, Move m1, Move m2, Move m3, int lvl)
	{
		level=lvl;
		exp=(lvl-1)*(lvl-1)*(lvl-1);
		exp+=lvl;
		createPokemon(s,level);
		move[0]=m1;
		move[1]=m2;
		move[2]=m3;
		createMoves();
	}

	//Creates a Pokemon for Trainers with moveset and level
	public Pokemon(Species s, Move m1, Move m2, Move m3, Move m4, int lvl)
	{
		level=lvl;
		exp=(lvl-1)*(lvl-1)*(lvl-1);
		exp+=lvl;
		createPokemon(s,level);
		move[0]=m1;
		move[1]=m2;
		move[2]=m3;
		move[3]=m4;
		createMoves();
	}

	//Most verbose way to create a Pokemon
	public Pokemon(Species s, Move m1, Move m2, Move m3, Move m4, int lvl, int hpiv, int atkiv, int defiv, int spcliv, int speediv,
					String nick, Status stat, int idNo, String ot)
	{
		level = lvl;
		HP_IV=hpiv;
		ATK_IV=atkiv;
		DEF_IV=defiv;
		SPCL_IV=spcliv;
		SPEED_IV=speediv;

		createPokemon(s,lvl);
		move[0]=m1;
		move[1]=m2;
		move[2]=m3;
		move[3]=m4;
		createMoves();
		status=stat;
		nickname=nick;
		idNumber=idNo;
		originalTrainer=ot;
	}

	public String toString()
	{
		return "Name: "+nickname+" Species: "+toString(species)+" Level:"+level+"\n"+
			"Atk: "+atk+" Def: "+def+" Spcl: "+spcl+" Speed: "+speed+" Exp: "+exp+"\n"+
			"HP:"+health+"/"+healthMax+"\n"+
			move[0]+"\n"+move[1]+"\n"+move[2]+"\n"+move[3];
	}

	public String toBattleString()
	{
		return nickname+" Lvl: "+level+" HP:"+health+"/"+healthMax+" Status: "+status+"\n"+"\n"+
				"0:"+move[0]+" PP: "+move[0].pp+"/"+move[0].ppMax+"\n"+
				"1:"+move[1]+" PP: "+move[1].pp+"/"+move[1].ppMax+"\n"+
				"2:"+move[2]+" PP: "+move[2].pp+"/"+move[2].ppMax+"\n"+
				"3:"+move[3]+" PP: "+move[3].pp+"/"+move[3].ppMax;
	}

	//Checks and Sets if a Pokemon is traded given an ID
	public void checkTraded(int idToBeChecked)
	{
		IS_TRADED=(idNumber==idToBeChecked);
	}
	
	//Sets this Pokemon's stats TO DA MAX!!!
	public void setMaxStats()
	{
		HP_IV=31;
		HP_EV=256*256;
		ATK_IV=31;
		ATK_EV=256*256;
		DEF_IV=31;
		DEF_EV=256*256;
		SPCL_IV=31;
		SPCL_EV=256*256;
		SPEED_IV=31;
		SPEED_EV=256*256;
		
		healthMax=Mechanics.calcHPStat(baseHP,HP_IV, HP_EV,level);
		health=healthMax;

		atk=Mechanics.calcOtherStat(baseATK,ATK_IV,ATK_EV,level);
		def=Mechanics.calcOtherStat(baseDEF,DEF_IV, DEF_EV,level);
		spcl=Mechanics.calcOtherStat(baseSPCL,SPCL_IV,SPCL_EV,level);
		speed=Mechanics.calcOtherStat(baseSPEED,SPEED_IV,SPEED_EV,level);
	}

	//Sets Pokemon EVs to the given stat and value
	public void setEV(String str, int ev)
	{
		if(str.equals("HP"))
			HP_EV=ev;
		else if(str.equals("ATK"))
			ATK_EV=ev;
		else if(str.equals("DEF"))
			DEF_EV=ev;
		else if(str.equals("SPCL"))
			SPCL_EV=ev;
		else if(str.equals("SPEED"))
			SPEED_EV=ev;

		if(HP_EV>256*256)
			HP_EV=256*256;
		if(ATK_EV>256*256)
			ATK_EV=256*256;
		if(DEF_EV>256*256)
			DEF_EV=256*256;
		if(SPCL_EV>256*256)
			SPCL_EV=256*256;
		if(SPEED_EV>256*256)
			SPEED_EV=256*256;

		healthMax=Mechanics.calcHPStat(baseHP,HP_IV, HP_EV,level);
		health=healthMax;

		atk=Mechanics.calcOtherStat(baseATK,ATK_IV,ATK_EV,level);
		def=Mechanics.calcOtherStat(baseDEF,DEF_IV, DEF_EV,level);
		spcl=Mechanics.calcOtherStat(baseSPCL,SPCL_IV,SPCL_EV,level);
		speed=Mechanics.calcOtherStat(baseSPEED,SPEED_IV,SPEED_EV,level);
	}

	//Sets Pokemon nickname given a String
	public void setNickname(String nick)
	{
		nickname=nick;
		
		if(nickname.length()>10)
			setNickname(nickname.substring(0,10));
			
		if(nickname.equals("")||nickname.equals(" ")||nickname.equals("  ")||nickname.equals("   ")
			||nickname.equals("    ")||nickname.equals("     ")||nickname.equals("      ")||nickname.equals("       ")
			||nickname.equals("        ")||nickname.equals("         ")||nickname.equals("          "))
			nickname=toString(species);
	}

	public String toHPOnlyString()
	{
		return nickname+" HP:"+health+"/"+healthMax+" Status: "+status;
	}

	public String toString(Species s)
	{
		return ""+s;
	}

	public String toString(Move m)
	{
		String str=""+m;
		str.replace('_',' ');
		return str;
	}

	//Returns the species a Pokemon will evolve into
	//Level up version
	public Species evolve(Pokemon p)
	{
		switch(p.species)
		{
			case BULBASAUR:
				if(level>=16)
					return Species.IVYSAUR;
				break;
			case IVYSAUR:
				if(level>=32)
					return Species.VENUSAUR;
				break;
			case CHARMANDER:
				if(level>=16)
					return Species.CHARMELEON;
				break;
			case CHARMELEON:
				if(level>=36)
					return Species.CHARIZARD;
				break;
			case SQUIRTLE:
				if(level>=16)
					return Species.WARTORTLE;
				break;
			case WARTORTLE:
				if(level>=36)
					return Species.BLASTOISE;
				break;
			case CATERPIE:
				if(level>=7)
					return Species.METAPOD;
				break;
			case METAPOD:
				if(level>=10)
					return Species.BUTTERFREE;
				break;
			case WEEDLE:
				if(level>=7)
					return Species.KAKUNA;
				break;
			case KAKUNA:
				if(level>=10)
					return Species.BEEDRILL;
				break;
			case PIDGEY:
				if(level>=18)
					return Species.PIDGEOTTO;
				break;
			case PIDGEOTTO:
				if(level>=36)
					return Species.PIDGEOT;
				break;
			case RATTATA:
				if(level>=20)
					return Species.RATICATE;
				break;
			case SPEAROW:
				if(level>=20)
					return Species.FEAROW;
				break;
			case EKANS:
				if(level>=22)
					return Species.ARBOK;
				break;
			case SANDSHREW:
				if(level>=22)
					return Species.SANDSLASH;
				break;
			case NIDORAN_F:
				if(level>=16)
					return Species.NIDORINA;
				break;
			case NIDORAN_M:
				if(level>=16)
					return Species.NIDORINO;
				break;
			case ZUBAT:
				if(level>=22)
					return Species.GOLBAT;
				break;
			case ODDISH:
				if(level>=21)
					return Species.GLOOM;
				break;
			case PARAS:
				if(level>=24)
					return Species.PARASECT;
				break;
			case VENONAT:
				if(level>=31)
					return Species.VENOMOTH;
				break;
			case DIGLETT:
				if(level>=26)
					return Species.DUGTRIO;
				break;
			case MEOWTH:
				if(level>=28)
					return Species.PERSIAN;
				break;
			case PSYDUCK:
				if(level>=33)
					return Species.GOLDUCK;
				break;
			case MANKEY:
				if(level>=28)
					return Species.PRIMEAPE;
				break;
			case POLIWAG:
				if(level>=25)
					return Species.POLIWHIRL;
				break;
			case ABRA:
				if(level>=16)
					return Species.KADABRA;
				break;
			case MACHOP:
				if(level>=28)
					return Species.MACHOKE;
				break;
			case BELLSPROUT:
				if(level>=21)
					return Species.WEEPINBELL;
				break;
			case TENTACOOL:
				if(level>=30)
					return Species.TENTACRUEL;
				break;
			case GEODUDE:
				if(level>=25)
					return Species.GRAVELER;
				break;
			case PONYTA:
				if(level>=40)
					return Species.RAPIDASH;
				break;
			case SLOWPOKE:
				if(level>=37)
					return Species.SLOWBRO;
				break;
			case MAGNEMITE:
				if(level>=30)
					return Species.MAGNETON;
				break;
			case DODUO:
				if(level>=31)
					return Species.DODRIO;
				break;
			case SEEL:
				if(level>=34)
					return Species.DEWGONG;
				break;
			case GRIMER:
				if(level>=38)
					return Species.MUK;
				break;
			case GASTLY:
				if(level>=25)
					return Species.HAUNTER;
				break;
			case DROWZEE:
				if(level>=26)
					return Species.HYPNO;
				break;
			case KRABBY:
				if(level>=28)
					return Species.KINGLER;
				break;
			case VOLTORB:
				if(level>=30)
					return Species.ELECTRODE;
				break;
			case CUBONE:
				if(level>=28)
					return Species.MAROWAK;
				break;
			case KOFFING:
				if(level>=35)
					return Species.WEEZING;
				break;
			case RHYHORN:
				if(level>=42)
					return Species.RHYDON;
				break;
			case HORSEA:
				if(level>=32)
					return Species.SEADRA;
				break;
			case GOLDEEN:
				if(level>=33)
					return Species.SEAKING;
				break;
			case MAGIKARP:
				if(level>=20)
					return Species.GYARADOS;
				break;
			case OMANYTE:
				if(level>=40)
					return Species.OMASTAR;
				break;
			case KABUTO:
				if(level>=40)
					return Species.KABUTOPS;
				break;
			case DRATINI:
				if(level>=30)
					return Species.DRAGONAIR;
				break;
			case DRAGONAIR:
				if(level>=55)
					return Species.DRAGONITE;
				break;
			case MISSINGNO:
				if(level>=80)
					return Species.KANGASKHAN;
				break;
		}

		return p.species;
	}

	//Evolutionary Stone version
	public Species evolve(Pokemon p, Item.Type t)
	{
		boolean willWork = true;
		switch(p.species)
		{
			case PIKACHU:
				if(t==Item.Type.THUNDER_STONE)
					return Species.RAICHU;
				else
					willWork = false;
				break;
			case NIDORINA:
				if(t==Item.Type.MOON_STONE)
					return Species.NIDOQUEEN;
				else
					willWork = false;
				break;
			case NIDORINO:
				if(t==Item.Type.MOON_STONE)
					return Species.NIDOKING;
				else
					willWork = false;
				break;
			case CLEFAIRY:
				if(t==Item.Type.MOON_STONE)
					return Species.CLEFABLE;
				else
					willWork = false;
				break;
			case VULPIX:
				if(t==Item.Type.FIRE_STONE)
					return Species.NINETALES;
				else
					willWork = false;
				break;
			case JIGGLYPUFF:
				if(t==Item.Type.MOON_STONE)
					return Species.WIGGLYTUFF;
				else
					willWork = false;
				break;
			case GLOOM:
				if(t==Item.Type.LEAF_STONE)
					return Species.VILEPLUME;
				else
					willWork = false;
				break;
			case GROWLITHE:
				if(t==Item.Type.FIRE_STONE)
					return Species.ARCANINE;
				else
					willWork = false;
				break;
			case POLIWHIRL:
				if(t==Item.Type.WATER_STONE)
					return Species.POLIWRATH;
				else
					willWork = false;
				break;
			case WEEPINBELL:
				if(t==Item.Type.LEAF_STONE)
					return Species.VICTREEBEL;
				else
					willWork = false;
				break;
			case SHELLDER:
				if(t==Item.Type.WATER_STONE)
					return Species.CLOYSTER;
				else
					willWork = false;
				break;
			case EXEGGCUTE:
				if(t==Item.Type.LEAF_STONE)
					return Species.EXEGGUTOR;
				else
					willWork = false;
				break;
			case STARYU:
				if(t==Item.Type.WATER_STONE)
					return Species.STARMIE;
				else
					willWork = false;
				break;
			case EEVEE:
				if(t==Item.Type.WATER_STONE)
					return Species.VAPOREON;
				else if(t==Item.Type.THUNDER_STONE)
					return Species.JOLTEON;
				else if(t==Item.Type.FIRE_STONE)
					return Species.FLAREON;
				else
					willWork = false;
				break;
			default:
				willWork = false;
				break;
		}
		if (!willWork)
		{
			Inventory.errorWindow.addMessage(t.toString().replace('_',' ') + " will have no effect!");
		}

		return p.species;
	}

	//Trade version
	public Species evolve(Pokemon p, int idToCheck)
	{
		if(idToCheck!=p.idNumber)
		{
			switch(p.species)
			{
				case KADABRA:
					return Species.ALAKAZAM;
				case MACHOKE:
					return Species.MACHAMP;
				case GRAVELER:
					return Species.GOLEM;
				case HAUNTER:
					return Species.GENGAR;
			}
		}

		return p.species;
	}

	//Constructs a similar Pokemon that has evolved
	public Pokemon evolution(Pokemon p, Species s)
	{
		Pokemon newP=null;

		newP=new Pokemon(s, p.move[0], p.move[1], p.move[2], p.move[3],p.level);

		newP.HP_EV=p.HP_EV;
		newP.ATK_EV=p.ATK_EV;
		newP.DEF_EV=p.DEF_EV;
		newP.SPEED_EV=p.SPEED_EV;
		newP.SPCL_EV=p.SPCL_EV;

		newP.HP_IV=p.HP_IV;
		newP.ATK_IV=p.ATK_IV;
		newP.DEF_IV=p.DEF_IV;
		newP.SPEED_IV=p.SPEED_IV;
		newP.SPCL_IV=p.SPCL_IV;

		newP.IS_TRADED=p.IS_TRADED;
		newP.idNumber=p.idNumber;
		if(!p.nickname.equals(p.species.toString()))
		newP.nickname=p.nickname;
		newP.originalTrainer=p.originalTrainer;

		for(int i=0; i<4; i++)
		{
			newP.TRUE_PP[i]=p.TRUE_PP[i];
		}

		return newP;
	}

	//Returns Sum of Base Stats
	public int getBaseStatTotal()
	{
		return HP_IV+ATK_IV+DEF_IV+SPCL_IV+SPEED_IV;
	}
	
	//Returns a string of this Pokemon's types
	public String getTypeString()
	{
		String str="";
		
		switch(type1)
		{
			case BIRD:
				str+="Bird";
				break;
			case BUG:
				str+="Bug";
				break;
			case DRAGON:
				str+="Drag";
				break;
			case ELECTRIC:
				str+="Elec";
				break;
			case FIGHTING:
				str+="Fght";
				break;
			case FIRE:
				str+="Fire";
				break;
			case FLYING:
				str+="Fly";
				break;
			case GHOST:
				str+="Ghst";
				break;
			case GRASS:
				str+="Grss";
				break;
			case GROUND:
				str+="Grd";
				break;
			case ICE:
				str+="Ice";
				break;
			case NORMAL:
				str+="Nrm";
				break;
			case POISON:
				str+="Psn";
				break;
			case PSYCHIC:
				str+="Psy";
				break;
			case ROCK:
				str+="Rock";
				break;
			case WATER:
				str+="Wtr";
				break;
			default:
				break;
		}
		if(type2!=Type.NONE)
		{
			str+="/";
			switch(type2)
			{
				case BIRD:
					str+="Bird";
					break;
				case BUG:
					str+="Bug";
					break;
				case DRAGON:
					str+="Drag";
					break;
				case ELECTRIC:
					str+="Elec";
					break;
				case FIGHTING:
					str+="Fght";
					break;
				case FIRE:
					str+="Fire";
					break;
				case FLYING:
					str+="Fly";
					break;
				case GHOST:
					str+="Ghst";
					break;
				case GRASS:
					str+="Grss";
					break;
				case GROUND:
					str+="Grd";
					break;
				case ICE:
					str+="Ice";
					break;
				case NORMAL:
					str+="Nrm";
					break;
				case POISON:
					str+="Psn";
					break;
				case PSYCHIC:
					str+="Psy";
					break;
				case ROCK:
					str+="Rock";
					break;
				case WATER:
					str+="Wtr";
					break;
				default:
					break;
			}	
		}
		
		return str;
	}

	//Takes in 0 - 162 and returns metronome when it's not between 0 and 162
	public Move randomMove(int num)
	{
		switch(num)
		{
			case 0:
				return Move.ABSORB;
			case 1:
				return Move.ACID;
			case 2:
				return Move.ACID_ARMOR;
			case 3:
				return Move.AGILITY;
			case 4:
				return Move.AMNESIA;
			case 5:
				return Move.AURORA_BEAM;
			case 6:
				return Move.BARRAGE;
			case 7:
				return Move.BARRIER;
			case 8:
			case 9:
			case 10:
				return Move.BITE;
			case 11:
				return Move.BLIZZARD;
			case 12:
				return Move.BODY_SLAM;
			case 13:
				return Move.BONE_CLUB;
			case 14:
				return Move.BONEMERANG;
			case 15:
				return Move.BUBBLE;
			case 16:
				return Move.BUBBLEBEAM;
			case 17:
			case 18:
				return Move.COMET_PUNCH;
			case 19:
				return Move.CONFUSE_RAY;
			case 20:
				return Move.CONFUSION;
			case 21:
				return Move.CONSTRICT;
			case 22:
				return Move.CONVERSION;
			case 23:
				return Move.COUNTER;
			case 24:
				return Move.CRABHAMMER;
			case 25:
				return Move.CUT;
			case 26:
				return Move.DEFENSE_CURL;
			case 27:
			case 28:
				return Move.DISABLE;
			case 29:
				return Move.DIZZY_PUNCH;
			case 30:
				return Move.DOUBLE_KICK;
			case 31:
				return Move.DOUBLE_TEAM;
			case 32:
				return Move.DOUBLE_EDGE;
			case 33:
				return Move.DOUBLESLAP;
			case 34:
				return Move.DRAGON_RAGE;
			case 35:
				return Move.DREAM_EATER;
			case 36:
				return Move.DRILL_PECK;
			case 37:
				return Move.EARTHQUAKE;
			case 38:
				return Move.EGG_BOMB;
			case 39:
				return Move.EMBER;
			case 40:
				return Move.EXPLOSION;
			case 41:
				return Move.FIRE_BLAST;
			case 42:
				return Move.FIRE_PUNCH;
			case 43:
			case 44:
				return Move.FISSURE;
			case 45:
				return Move.FLAMETHROWER;
			case 46:
				return Move.FLASH;
			case 47:
			case 48:
				return Move.FOCUS_ENERGY;
			case 49:
				return Move.FURY_ATTACK;
			case 50:
				return Move.FURY_SWIPES;
			case 51:
				return Move.GLARE;
			case 52:
				return Move.GROWL;
			case 53:
				return Move.GROWTH;
			case 54:
				return Move.GUILLOTINE;
			case 55:
				return Move.GUST;
			case 56:
				return Move.HARDEN;
			case 57:
				return Move.HAZE;
			case 58:
				return Move.HEADBUTT;
			case 59:
				return Move.HI_JUMP_KICK;
			case 60:
				return Move.HORN_ATTACK;
			case 61:
				return Move.HORN_DRILL;
			case 62:
				return Move.HYDRO_PUMP;
			case 63:
			case 64:
				return Move.HYPER_FANG;
			case 65:
				return Move.HYPNOSIS;
			case 66:
				return Move.ICE_BEAM;
			case 67:
				return Move.ICE_PUNCH;
			case 68:
				return Move.JUMP_KICK;
			case 69:
				return Move.KARATE_CHOP;
			case 70:
				return Move.KINESIS;
			case 71:
				return Move.LEECH_LIFE;
			case 72:
				return Move.LEECH_SEED;
			case 73:
				return Move.LEER;
			case 74:
				return Move.LICK;
			case 75:
				return Move.LIGHT_SCREEN;
			case 76:
				return Move.LOVELY_KISS;
			case 77:
				return Move.LOW_KICK;
			case 78:
				return Move.MEDITATE;
			case 79:
				return Move.MEGA_DRAIN;
			case 80:
				return Move.MEGA_KICK;
			case 81:
				return Move.MEGA_PUNCH;
			case 82:
				return Move.MIMIC;
			case 83:
				return Move.MINIMIZE;
			case 84:
				return Move.MIRROR_MOVE;
			case 85:
				return Move.MIST;
			case 86:
				return Move.NIGHT_SHADE;
			case 87:
				return Move.PAY_DAY;
			case 88:
				return Move.PECK;
			case 89:
			case 90:
				return Move.PIN_MISSILE;
			case 91:
				return Move.POISON_GAS;
			case 92:
				return Move.POISON_STING;
			case 93:
				return Move.POISONPOWDER;
			case 94:
				return Move.POUND;
			case 95:
				return Move.PSYBEAM;
			case 96:
				return Move.PSYCHIC;
			case 97:
				return Move.PSYWAVE;
			case 98:
				return Move.QUICK_ATTACK;
			case 99:
			case 100:
				return Move.RAZOR_LEAF;
			case 101:
			case 102:
				return Move.RECOVER;
			case 103:
				return Move.REFLECT;
			case 104:
				return Move.REST;
			case 105:
				return Move.ROAR;
			case 106:
				return Move.ROCK_SLIDE;
			case 107:
				return Move.ROCK_THROW;
			case 108:
				return Move.ROLLING_KICK;
			case 109:
				return Move.SAND_ATTACK;
			case 110:
				return Move.SCRATCH;
			case 111:
				return Move.SCREECH;
			case 112:
				return Move.SEISMIC_TOSS;
			case 113:
				return Move.SELFDESTRUCT;
			case 114:
				return Move.SHARPEN;
			case 115:
				return Move.SING;
			case 116:
			case 117:
			case 118:
				return Move.SLAM;
			case 119:
				return Move.SLASH;
			case 120:
				return Move.SLEEP_POWDER;
			case 121:
				return Move.SLUDGE;
			case 122:
				return Move.SMOG;
			case 123:
				return Move.SMOKESCREEN;
			case 124:
				return Move.SOFTBOILED;
			case 125:
			case 126:
				return Move.SONIC_BOOM;
			case 127:
				return Move.SPIKE_CANNON;
			case 128:
				return Move.SPLASH;
			case 129:
				return Move.SPORE;
			case 130:
				return Move.STOMP;
			case 131:
				return Move.STRENGTH;
			case 132:
				return Move.STRING_SHOT;
			case 133:
				return Move.STUN_SPORE;
			case 134:
				return Move.SUBMISSION;
			case 135:
				return Move.SUBSTITUTE;
			case 136:
				return Move.SUPER_FANG;
			case 137:
				return Move.SUPERSONIC;
			case 138:
				return Move.SURF;
			case 139:
				return Move.SWIFT;
			case 140:
				return Move.SWORDS_DANCE;
			case 141:
				return Move.TACKLE;
			case 142:
				return Move.TAIL_WHIP;
			case 143:
				return Move.TAKE_DOWN;
			case 144:
				return Move.TELEPORT;
			case 145:
			case 146:
				return Move.THUNDER;
			case 147:
				return Move.THUNDER_WAVE;
			case 148:
				return Move.THUNDERBOLT;
			case 149:
				return Move.THUNDERPUNCH;
			case 150:
				return Move.THUNDERSHOCK;
			case 151:
				return Move.TOXIC;
			case 152:
				return Move.TRANSFORM;
			case 153:
				return Move.TRI_ATTACK;
			case 154:
				return Move.TWINEEDLE;
			case 155:
				return Move.VICEGRIP;
			case 156:
				return Move.VINE_WHIP;
			case 157:
				return Move.WATER_GUN;
			case 158:
				return Move.WATERFALL;
			case 159:
				return Move.WHIRLWIND;
			case 160:
				return Move.WING_ATTACK;
			case 161:
				return Move.WITHDRAW;
			case 162:
			default :
				return Move.METRONOME;
		}
	}

	//Actually creates a Pokemon and assigns moves and stats
	public void createPokemon(Species s, int lvl)
	{
		int m=0;
		for(int i=0; i<4; i++)
			move[i]=Move.NONE;


		species=s;

		//Note for evolutions the moves were made basically the same for the pre evolved
		//versions except a level higher since it is still possible to obtain them without
		//having to use a tm
		switch(s)
		{
			case BULBASAUR:
				nickname=toString(s);
				type1=Type.GRASS;
				type2=Type.POISON;
				pokedexNumber =1;
				baseHP=45; baseATK=49; baseDEF=49; baseSPEED=45; baseSPCL=65;
				//Move Block
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.SOLARBEAM;
						m++;
					}
					if (lvl >= 41 && m < 4)
					{
						move[m]=Move.SLEEP_POWDER;
						m++;
					}
					if (lvl >= 34 && m < 4)
					{
						move[m]=Move.GROWTH;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.RAZOR_LEAF;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.POISONPOWDER;
						m++;
					}
					if (lvl >= 13 && m < 4)
					{
						move[m]=Move.VINE_WHIP;
						m++;
					}
					if (lvl >= 7 && m < 4)
					{
						move[m]=Move.LEECH_SEED;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
				break;
			case IVYSAUR:
				nickname = toString(s);
				type1=Type.GRASS;
				type2=Type.POISON;
				pokedexNumber =2;
				baseHP=60; baseATK=62; baseDEF=63; baseSPCL=80; baseSPEED=60;
					if (lvl >= 49 && m < 4)
					{
						move[m]=Move.SOLARBEAM;
						m++;
					}
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.SLEEP_POWDER;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.GROWTH;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.RAZOR_LEAF;
						m++;
					}
					if (lvl >= 21 && m < 4)
					{
						move[m]=Move.POISONPOWDER;
						m++;
					}
					if (lvl >= 14 && m < 4)
					{
						move[m]=Move.VINE_WHIP;
						m++;
					}
					if (lvl >= 8 && m < 4)
					{
						move[m]=Move.LEECH_SEED;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
				break;
			case VENUSAUR:
				nickname = toString(s);
				type1=Type.GRASS;
				type2=Type.POISON;
				pokedexNumber =3;
				baseHP=80; baseATK=82; baseDEF=83; baseSPCL=100; baseSPEED=80;
					if (lvl >= 50 && m < 4)
					{
						move[m]=Move.SOLARBEAM;
						m++;
					}
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.SLEEP_POWDER;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.GROWTH;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.RAZOR_LEAF;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.POISONPOWDER;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.VINE_WHIP;
						m++;
					}
					if (lvl >= 9 && m < 4)
					{
						move[m]=Move.LEECH_SEED;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
				break;
			case CHARMANDER:
				nickname = toString(s);
				type1=Type.FIRE;
				type2=Type.NONE;
				pokedexNumber =4;
				baseHP=39; baseATK=52; baseDEF=43; baseSPEED=65; baseSPCL=50;
					if (lvl >= 46 && m < 4)
					{
						move[m]=Move.FIRE_SPIN;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.FLAMETHROWER;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.SLASH;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.RAGE;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 9 && m < 4)
					{
						move[m]=Move.EMBER;
						m++;
					}
					if ( m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if ( m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}

				break;
			case CHARMELEON:
				nickname = toString(s);
				type1=Type.FIRE;
				type2=Type.NONE;
				pokedexNumber =5;
				baseHP=58; baseATK=64; baseDEF=58; baseSPEED=80; baseSPCL=65;
					if (lvl >= 47 && m < 4)
					{
						move[m]=Move.FIRE_SPIN;
						m++;
					}
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.FLAMETHROWER;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.SLASH;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.RAGE;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 10 && m < 4)
					{
						move[m]=Move.EMBER;
						m++;
					}
					if ( m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if ( m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
				break;
			case CHARIZARD:
				nickname = toString(s);
				type1=Type.FIRE;
				type2=Type.FLYING;
				pokedexNumber =6;
				baseHP=78; baseATK=84; baseDEF=78; baseSPEED=100; baseSPCL=85;
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.FIRE_SPIN;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.FLAMETHROWER;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.SLASH;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.RAGE;
						m++;
					}
					if (lvl >= 17 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 11 && m < 4)
					{
						move[m]=Move.EMBER;
						m++;
					}
					if ( m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if ( m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
				break;
			case SQUIRTLE:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =7;
				baseHP=44; baseATK=48; baseDEF=65; baseSPEED=43; baseSPCL=64;
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.SKULL_BASH;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.WITHDRAW;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
					if (lvl >= 8 && m < 4)
					{
						move[m]=Move.BUBBLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case WARTORTLE:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =8;
				baseHP=59; baseATK=63; baseDEF=80; baseSPEED=58; baseSPCL=65;
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.SKULL_BASH;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.WITHDRAW;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
					if (lvl >= 9 && m < 4)
					{
						move[m]=Move.BUBBLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case BLASTOISE:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =9;
				baseHP=79; baseATK=83; baseDEF=100; baseSPEED=78; baseSPCL=85;
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.SKULL_BASH;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.WITHDRAW;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (lvl >= 17 && m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
					if (lvl >= 10 && m < 4)
					{
						move[m]=Move.BUBBLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case CATERPIE:
				nickname = toString(s);
				type1=Type.BUG;
				type2=Type.NONE;
				pokedexNumber =10;
				baseHP=45; baseATK=30; baseDEF=35; baseSPEED=45; baseSPCL=20;
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.STRING_SHOT;
						m++;
					}
				break;
			case METAPOD:
				nickname = toString(s);
				type1=Type.BUG;
				type2=Type.NONE;
				pokedexNumber =11;
				baseHP=50; baseATK=20; baseDEF=55; baseSPEED=30; baseSPCL=25;
					if (m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.STRING_SHOT;
						m++;
					}
				break;
			case BUTTERFREE:
				nickname = toString(s);
				type1=Type.BUG;
				type2=Type.FLYING;
				pokedexNumber =12;
				baseHP=60; baseATK=45; baseDEF=50; baseSPEED=70; baseSPCL=80;
					if (lvl >= 34 && m < 4)
					{
						move[m]=Move.PSYBEAM;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.WHIRLWIND;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.SUPERSONIC;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.SLEEP_POWDER;
						m++;
					}
					if (lvl >= 14 && m < 4)
					{
						move[m]=Move.STUN_SPORE;
						m++;
					}
					if (lvl >= 13 && m < 4)
					{
						move[m]=Move.POISONPOWDER;
						m++;
					}
					if (lvl >= 10 && m < 4)
					{
						move[m]=Move.CONFUSION;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.STRING_SHOT;
						m++;
					}
				break;
			case WEEDLE:
				nickname = toString(s);
				type1=Type.BUG;
				type2=Type.POISON;
				pokedexNumber =13;
				baseHP=40; baseATK=35; baseDEF=30; baseSPEED=50; baseSPCL=20;
					if (m < 4)
					{
						move[m]=Move.POISON_STING;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.STRING_SHOT;
						m++;
					}
				break;
			case KAKUNA:
				nickname = toString(s);
				type1=Type.BUG;
				type2=Type.POISON;
				pokedexNumber =14;
				baseHP=45; baseATK=25; baseDEF=50; baseSPEED=35; baseSPCL=25;
					if (m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.POISON_STING;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.STRING_SHOT;
						m++;
					}
				break;
			case BEEDRILL:
				nickname = toString(s);
				type1=Type.BUG;
				type2=Type.POISON;
				pokedexNumber =15;
				baseHP=65; baseATK=80; baseDEF=40; baseSPEED=75; baseSPCL=45;
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.PIN_MISSILE;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.RAGE;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.TWINEEDLE;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.FOCUS_ENERGY;
						m++;
					}
					if (lvl >= 12 && m < 4)
					{
						move[m]=Move.FURY_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.POISON_STING;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.STRING_SHOT;
						m++;
					}
				break;
			case PIDGEY:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.FLYING;
				pokedexNumber =16;
				baseHP=40; baseATK=45; baseDEF=40; baseSPEED=56; baseSPCL=35;
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.MIRROR_MOVE;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.WING_ATTACK;
						m++;
					}
					if (lvl >= 19 && m < 4)
					{
						move[m]=Move.WHIRLWIND;
						m++;
					}
					if (lvl >= 12 && m < 4)
					{
						move[m]=Move.QUICK_ATTACK;
						m++;
					}
					if (lvl >= 5 && m < 4)
					{
						move[m]=Move.SAND_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GUST;
						m++;
					}
				break;
			case PIDGEOTTO:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.FLYING;
				pokedexNumber =17;
				baseHP=63; baseATK=60; baseDEF=55; baseSPEED=71; baseSPCL=50;
					if (lvl >= 45 && m < 4)
					{
						move[m]=Move.MIRROR_MOVE;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.WING_ATTACK;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.WHIRLWIND;
						m++;
					}
					if (lvl >= 13 && m < 4)
					{
						move[m]=Move.QUICK_ATTACK;
						m++;
					}
					if (lvl >= 6 && m < 4)
					{
						move[m]=Move.SAND_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GUST;
						m++;
					}
				break;
			case PIDGEOT:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.FLYING;
				pokedexNumber =18;
				baseHP=83; baseATK=80; baseDEF=75; baseSPEED=91; baseSPCL=70;
					if (lvl >= 46 && m < 4)
					{
						move[m]=Move.MIRROR_MOVE;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.WING_ATTACK;
						m++;
					}
					if (lvl >= 21 && m < 4)
					{
						move[m]=Move.WHIRLWIND;
						m++;
					}
					if (lvl >= 14 && m < 4)
					{
						move[m]=Move.QUICK_ATTACK;
						m++;
					}
					if (lvl >= 7 && m < 4)
					{
						move[m]=Move.SAND_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GUST;
						m++;
					}
				break;
			case RATTATA:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.NONE;
				pokedexNumber =19;
				baseHP=30; baseATK=56; baseDEF=35; baseSPEED=72; baseSPCL=25;
					if (lvl >= 34 && m < 4)
					{
						move[m]=Move.SUPER_FANG;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.FOCUS_ENERGY;
						m++;
					}
					if (lvl >= 14 && m < 4)
					{
						move[m]=Move.HYPER_FANG;
						m++;
					}
					if (lvl >= 9 && m < 4)
					{
						move[m]=Move.QUICK_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case RATICATE:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.NONE;
				pokedexNumber =20;
				baseHP=55; baseATK=81; baseDEF=60; baseSPEED=97; baseSPCL=50;
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.SUPER_FANG;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.FOCUS_ENERGY;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.HYPER_FANG;
						m++;
					}
					if (lvl >= 10 && m < 4)
					{
						move[m]=Move.QUICK_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case SPEAROW:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.FLYING;
				pokedexNumber =21;
				baseHP=40; baseATK=60; baseDEF=30; baseSPEED=70; baseSPCL=31;
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.DRILL_PECK;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.MIRROR_MOVE;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.FURY_ATTACK;
						m++;
					}
					if (lvl >= 9 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.PECK;
						m++;
					}
				break;
			case FEAROW:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.FLYING;
				pokedexNumber =22;
				baseHP=65; baseATK=90; baseDEF=65; baseSPEED=100; baseSPCL=61;
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.DRILL_PECK;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.MIRROR_MOVE;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.FURY_ATTACK;
						m++;
					}
					if (lvl >= 10 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.PECK;
						m++;
					}
				break;
			case EKANS:
				nickname = toString(s);
				type1=Type.POISON;
				type2=Type.NONE;
				pokedexNumber =23;
				baseHP=35; baseATK=60; baseDEF=44; baseSPEED=55; baseSPCL=40;
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.ACID;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.SCREECH;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.GLARE;
						m++;
					}
					if (lvl >= 17 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (lvl >= 10 && m < 4)
					{
						move[m]=Move.POISON_STING;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.WRAP;
						m++;
					}
				break;
			case ARBOK:
				nickname = toString(s);
				type1=Type.POISON;
				type2=Type.NONE;
				pokedexNumber =24;
				baseHP=60; baseATK=85; baseDEF=69; baseSPEED=80; baseSPCL=65;
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.ACID;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.SCREECH;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.GLARE;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (lvl >= 11 && m < 4)
					{
						move[m]=Move.POISON_STING;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.WRAP;
						m++;
					}
				break;
			case PIKACHU:
				nickname = toString(s);
				type1=Type.ELECTRIC;
				type2=Type.NONE;
				pokedexNumber =25;
				baseHP=35; baseATK=55; baseDEF=30; baseSPEED=90; baseSPCL=50;
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.THUNDER;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 26 && m < 4)
					{
						move[m]=Move.SWIFT;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.QUICK_ATTACK;
						m++;
					}
					if (lvl >= 9 && m < 4)
					{
						move[m]=Move.THUNDER_WAVE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.THUNDERSHOCK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
				break;
			case RAICHU:
				nickname = toString(s);
				type1=Type.ELECTRIC;
				type2=Type.NONE;
				pokedexNumber =26;
				baseHP=60; baseATK=90; baseDEF=55; baseSPEED=100; baseSPCL=90;
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.THUNDER;
						m++;
					}
					if (lvl >= 34 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.SWIFT;
						m++;
					}
					if (lvl >= 17 && m < 4)
					{
						move[m]=Move.QUICK_ATTACK;
						m++;
					}
					if (lvl >= 10 && m < 4)
					{
						move[m]=Move.THUNDER_WAVE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.THUNDERSHOCK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
				break;
			case SANDSHREW:
				nickname = toString(s);
				type1=Type.GROUND;
				type2=Type.NONE;
				pokedexNumber =27;
				baseHP=50; baseATK=75; baseDEF=85; baseSPEED=40; baseSPCL=30;
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.FURY_SWIPES;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.SWIFT;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.POISON_STING;
						m++;
					}
					if (lvl >= 17 && m < 4)
					{
						move[m]=Move.SLASH;
						m++;
					}
					if (lvl >= 10 && m < 4)
					{
						move[m]=Move.SAND_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
				break;
			case SANDSLASH:
				nickname = toString(s);
				type1=Type.GROUND;
				type2=Type.NONE;
				pokedexNumber =28;
				baseHP=75; baseATK=100; baseDEF=110; baseSPEED=65; baseSPCL=55;
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.FURY_SWIPES;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.SWIFT;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.POISON_STING;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.SLASH;
						m++;
					}
					if (lvl >= 11 && m < 4)
					{
						move[m]=Move.SAND_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
				break;
			case NIDORAN_F:
				nickname = toString(s);
				type1=Type.POISON;
				type2=Type.NONE;
				pokedexNumber =29;
				baseHP=55; baseATK=47; baseDEF=52; baseSPEED=41; baseSPCL=40;
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.DOUBLE_KICK;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.FURY_SWIPES;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (lvl >= 21 && m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (lvl >= 14 && m < 4)
					{
						move[m]=Move.POISON_STING;
						m++;
					}
					if (lvl >= 8 && m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
				break;
			case NIDORINA:
				nickname = toString(s);
				type1=Type.POISON;
				type2=Type.NONE;
				pokedexNumber =30;
				baseHP=70; baseATK=62; baseDEF=67; baseSPEED=56; baseSPCL=55;
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.DOUBLE_KICK;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.FURY_SWIPES;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.POISON_STING;
						m++;
					}
					if (lvl >= 9 && m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
				break;
			case NIDOQUEEN:
				nickname = toString(s);
				type1=Type.POISON;
				type2=Type.GROUND;
				pokedexNumber =31;
				baseHP=90; baseATK=82; baseDEF=87; baseSPEED=76; baseSPCL=75;
					if (lvl >= 45 && m < 4)
					{
						move[m]=Move.DOUBLE_KICK;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.FURY_SWIPES;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.BODY_SLAM;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.POISON_STING;
						m++;
					}
					if (lvl >= 10 && m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
				break;
			case NIDORAN_M:
				nickname = toString(s);
				type1=Type.POISON;
				type2=Type.NONE;
				pokedexNumber =32;
				baseHP=46; baseATK=57; baseDEF=40; baseSPEED=50; baseSPCL=40;
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.DOUBLE_KICK;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.HORN_DRILL;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.FURY_ATTACK;
						m++;
					}
					if (lvl >= 21 && m < 4)
					{
						move[m]=Move.FOCUS_ENERGY;
						m++;
					}
					if (lvl >= 14 && m < 4)
					{
						move[m]=Move.POISON_STING;
						m++;
					}
					if (lvl >= 8 && m < 4)
					{
						move[m]=Move.HORN_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
				break;
			case NIDORINO:
				nickname = toString(s);
				type1=Type.POISON;
				type2=Type.NONE;
				pokedexNumber =33;
				baseHP=61; baseATK=72; baseDEF=57; baseSPEED=65; baseSPCL=55;
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.DOUBLE_KICK;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.HORN_DRILL;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.FURY_ATTACK;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.FOCUS_ENERGY;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.POISON_STING;
						m++;
					}
					if (lvl >= 9 && m < 4)
					{
						move[m]=Move.HORN_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
				break;
			case NIDOKING:
				nickname = toString(s);
				type1=Type.POISON;
				type2=Type.GROUND;
				pokedexNumber =34;
				baseHP=81; baseATK=92; baseDEF=77; baseSPEED=85; baseSPCL=75;
					if (lvl >= 45 && m < 4)
					{
						move[m]=Move.DOUBLE_KICK;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.HORN_DRILL;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.FURY_ATTACK;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.FOCUS_ENERGY;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.THRASH;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.POISON_STING;
						m++;
					}
					if (lvl >= 10 && m < 4)
					{
						move[m]=Move.HORN_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
				break;
			case CLEFAIRY:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.NONE;
				pokedexNumber =35;
				baseHP=70; baseATK=45; baseDEF=48; baseSPEED=35; baseSPCL=60;
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.LIGHT_SCREEN;
						m++;
					}
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.DEFENSE_CURL;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.METRONOME;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.MINIMIZE;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.DOUBLESLAP;
						m++;
					}
					if (lvl >= 13 && m < 4)
					{
						move[m]=Move.SING;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.POUND;
						m++;
					}
				break;
			case CLEFABLE:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.NONE;
				pokedexNumber =36;
				baseHP=95; baseATK=70; baseDEF=73; baseSPEED=60; baseSPCL=85;
					if (lvl >= 49 && m < 4)
					{
						move[m]=Move.LIGHT_SCREEN;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.DEFENSE_CURL;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.METRONOME;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.MINIMIZE;
						m++;
					}
					if (lvl >= 19 && m < 4)
					{
						move[m]=Move.DOUBLESLAP;
						m++;
					}
					if (lvl >= 14 && m < 4)
					{
						move[m]=Move.SING;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.POUND;
						m++;
					}
				break;
			case VULPIX:
				nickname = toString(s);
				type1=Type.FIRE;
				type2=Type.NONE;
				pokedexNumber =37;
				baseHP=38; baseATK=41; baseDEF=40; baseSPEED=65; baseSPCL=65;
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.FIRE_SPIN;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.FLAMETHROWER;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.CONFUSE_RAY;
						m++;
					}
					if (lvl >= 21 && m < 4)
					{
						move[m]=Move.ROAR;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.QUICK_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.EMBER;
						m++;
					}
				break;
			case NINETALES:
				nickname = toString(s);
				type1=Type.FIRE;
				type2=Type.NONE;
				pokedexNumber =38;
				baseHP=73; baseATK=76; baseDEF=75; baseSPEED=100; baseSPCL=100;
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.FIRE_SPIN;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.FLAMETHROWER;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.CONFUSE_RAY;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.ROAR;
						m++;
					}
					if (lvl >= 17 && m < 4)
					{
						move[m]=Move.QUICK_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.EMBER;
						m++;
					}
				break;
			case JIGGLYPUFF:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.NONE;
				pokedexNumber =39;
				baseHP=115; baseATK=45; baseDEF=20; baseSPEED=20; baseSPCL=25;
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.DOUBLE_EDGE;
						m++;
					}
					if (lvl >= 34 && m < 4)
					{
						move[m]=Move.BODY_SLAM;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.REST;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.DOUBLESLAP;
						m++;
					}
					if (lvl >= 19 && m < 4)
					{
						move[m]=Move.DEFENSE_CURL;
						m++;
					}
					if (lvl >= 14 && m < 4)
					{
						move[m]=Move.DISABLE;
						m++;
					}
					if (lvl >= 9 && m < 4)
					{
						move[m]=Move.POUND;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SING;
						m++;
					}
				break;
			case WIGGLYTUFF:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.NONE;
				pokedexNumber =40;
				baseHP=140; baseATK=70; baseDEF=45; baseSPEED=45; baseSPCL=50;
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.DOUBLE_EDGE;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.BODY_SLAM;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.REST;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.DOUBLESLAP;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.DEFENSE_CURL;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.DISABLE;
						m++;
					}
					if (lvl >= 10 && m < 4)
					{
						move[m]=Move.POUND;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SING;
						m++;
					}
				break;
			case ZUBAT:
				nickname = toString(s);
				type1=Type.POISON;
				type2=Type.FLYING;
				pokedexNumber =41;
				baseHP=40; baseATK=45; baseDEF=35; baseSPEED=55; baseSPCL=40;
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.HAZE;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.WING_ATTACK;
						m++;
					}
					if (lvl >= 21 && m < 4)
					{
						move[m]=Move.CONFUSE_RAY;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (lvl >= 10 && m < 4)
					{
						move[m]=Move.SUPERSONIC;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LEECH_LIFE;
						m++;
					}
				break;
			case GOLBAT:
				nickname = toString(s);
				type1=Type.POISON;
				type2=Type.FLYING;
				pokedexNumber =42;
				baseHP=75; baseATK=80; baseDEF=70; baseSPEED=90; baseSPCL=75;
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.HAZE;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.WING_ATTACK;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.CONFUSE_RAY;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (lvl >= 11 && m < 4)
					{
						move[m]=Move.SUPERSONIC;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LEECH_LIFE;
						m++;
					}
				break;
			case ODDISH:
				nickname = toString(s);
				type1=Type.GRASS;
				type2=Type.POISON;
				pokedexNumber =43;
				baseHP=45; baseATK=50; baseDEF=55; baseSPEED=30; baseSPCL=75;
					if (lvl >= 46 && m < 4)
					{
						move[m]=Move.SOLARBEAM;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.PETAL_DANCE;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.ACID;
						m++;
					}
					if (lvl >= 19 && m < 4)
					{
						move[m]=Move.SLEEP_POWDER;
						m++;
					}
					if (lvl >= 17 && m < 4)
					{
						move[m]=Move.STUN_SPORE;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.POISONPOWDER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.ABSORB;
						m++;
					}
				break;
			case GLOOM:
				nickname = toString(s);
				type1=Type.GRASS;
				type2=Type.POISON;
				pokedexNumber =44;
				baseHP=60; baseATK=65; baseDEF=70; baseSPEED=40; baseSPCL=85;
					if (lvl >= 47 && m < 4)
					{
						move[m]=Move.SOLARBEAM;
						m++;
					}
					if (lvl >= 34 && m < 4)
					{
						move[m]=Move.PETAL_DANCE;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.ACID;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.SLEEP_POWDER;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.STUN_SPORE;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.POISONPOWDER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.ABSORB;
						m++;
					}
				break;
			case VILEPLUME:
				nickname = toString(s);
				type1=Type.GRASS;
				type2=Type.POISON;
				pokedexNumber =45;
				baseHP=75; baseATK=80; baseDEF=85; baseSPEED=50; baseSPCL=100;
				if (lvl >= 48 && m < 4)
					{
						move[m]=Move.SOLARBEAM;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.PETAL_DANCE;
						m++;
					}
					if (lvl >= 26 && m < 4)
					{
						move[m]=Move.ACID;
						m++;
					}
					if (lvl >= 21 && m < 4)
					{
						move[m]=Move.SLEEP_POWDER;
						m++;
					}
					if (lvl >= 19 && m < 4)
					{
						move[m]=Move.STUN_SPORE;
						m++;
					}
					if (lvl >= 17 && m < 4)
					{
						move[m]=Move.POISONPOWDER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.ABSORB;
						m++;
					}
				break;
			case PARAS:
				nickname = toString(s);
				type1=Type.BUG;
				type2=Type.GRASS;
				pokedexNumber =46;
				baseHP=35; baseATK=70; baseDEF=55; baseSPEED=25; baseSPCL=55;
					if (lvl >= 41 && m < 4)
					{
						move[m]=Move.GROWTH;
						m++;
					}
					if (lvl >= 34 && m < 4)
					{
						move[m]=Move.SLASH;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.SPORE;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.LEECH_LIFE;
						m++;
					}
					if (lvl >= 13 && m < 4)
					{
						move[m]=Move.STUN_SPORE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
				break;
			case PARASECT:
				nickname = toString(s);
				type1=Type.BUG;
				type2=Type.GRASS;
				pokedexNumber =47;
				baseHP=60; baseATK=95; baseDEF=80; baseSPEED=30; baseSPCL=80;
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.GROWTH;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.SLASH;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.SPORE;
						m++;
					}
					if (lvl >= 21 && m < 4)
					{
						move[m]=Move.LEECH_LIFE;
						m++;
					}
					if (lvl >= 14 && m < 4)
					{
						move[m]=Move.STUN_SPORE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
				break;
			case VENONAT:
				nickname = toString(s);
				type1=Type.BUG;
				type2=Type.POISON;
				pokedexNumber =48;
				baseHP=60; baseATK=55; baseDEF=50; baseSPEED=45; baseSPCL=40;
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.PSYCHIC;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.SLEEP_POWDER;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.PSYBEAM;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.STUN_SPORE;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.LEECH_LIFE;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.POISONPOWDER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.DISABLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case VENOMOTH:
				nickname = toString(s);
				type1=Type.BUG;
				type2=Type.POISON;
				pokedexNumber =49;
				baseHP=70; baseATK=65; baseDEF=60; baseSPEED=90; baseSPCL=90;
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.PSYCHIC;
						m++;
					}
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.SLEEP_POWDER;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.PSYBEAM;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.STUN_SPORE;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.LEECH_LIFE;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.POISONPOWDER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.DISABLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case DIGLETT:
				nickname = toString(s);
				type1=Type.GROUND;
				type2=Type.NONE;
				pokedexNumber =50;
				baseHP=10; baseATK=55; baseDEF=25; baseSPEED=95; baseSPCL=45;
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.EARTHQUAKE;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.SLASH;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.SAND_ATTACK;
						m++;
					}
					if (lvl >= 19 && m < 4)
					{
						move[m]=Move.DIG;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
				break;
			case DUGTRIO:
				nickname = toString(s);
				type1=Type.GROUND;
				type2=Type.NONE;
				pokedexNumber =51;
				baseHP=35; baseATK=80; baseDEF=50; baseSPEED=120; baseSPCL=70;
					if (lvl >= 41 && m < 4)
					{
						move[m]=Move.EARTHQUAKE;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.SLASH;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.SAND_ATTACK;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.DIG;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
				break;
			case MEOWTH:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.NONE;
				pokedexNumber =52;
				baseHP=40; baseATK=45; baseDEF=35; baseSPEED=90; baseSPCL=40;
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.SLASH;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.FURY_SWIPES;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.SCREECH;
						m++;
					}
					if (lvl >= 17 && m < 4)
					{
						move[m]=Move.PAY_DAY;
						m++;
					}
					if (lvl >= 12 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
				break;
			case PERSIAN:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.NONE;
				pokedexNumber =53;
				baseHP=65; baseATK=70; baseDEF=60; baseSPEED=115; baseSPCL=65;
					if (lvl >= 45 && m < 4)
					{
						move[m]=Move.SLASH;
						m++;
					}
					if (lvl >= 34 && m < 4)
					{
						move[m]=Move.FURY_SWIPES;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.SCREECH;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.PAY_DAY;
						m++;
					}
					if (lvl >= 13 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
				break;
			case PSYDUCK:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =54;
				baseHP=50; baseATK=52; baseDEF=48; baseSPEED=55; baseSPCL=50;
					if (lvl >= 52 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.FURY_SWIPES;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.CONFUSION;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.DISABLE;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
				break;
			case GOLDUCK:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =55;
				baseHP=80; baseATK=82; baseDEF=78; baseSPEED=85; baseSPCL=80;
					if (lvl >= 53 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.FURY_SWIPES;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.CONFUSION;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.DISABLE;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
				break;
			case MANKEY:
				nickname = toString(s);
				type1=Type.FIGHTING;
				type2=Type.NONE;
				pokedexNumber =56;
				baseHP=40; baseATK=80; baseDEF=35; baseSPEED=70; baseSPCL=35;
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.THRASH;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.SEISMIC_TOSS;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.FOCUS_ENERGY;
						m++;
					}
					if (lvl >= 21 && m < 4)
					{
						move[m]=Move.FURY_SWIPES;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.KARATE_CHOP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
				break;
			case PRIMEAPE:
				nickname = toString(s);
				type1=Type.FIGHTING;
				type2=Type.NONE;
				pokedexNumber =57;
				baseHP=65; baseATK=105; baseDEF=60; baseSPEED=95; baseSPCL=60;
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.THRASH;
						m++;
					}
					if (lvl >= 34 && m < 4)
					{
						move[m]=Move.SEISMIC_TOSS;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.FOCUS_ENERGY;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.FURY_SWIPES;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.KARATE_CHOP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
				break;
			case GROWLITHE:
				nickname = toString(s);
				type1=Type.FIRE;
				type2=Type.NONE;
				pokedexNumber =58;
				baseHP=55; baseATK=70; baseDEF=45; baseSPEED=60; baseSPCL=50;
					if (lvl >= 50 && m < 4)
					{
						move[m]=Move.FLAMETHROWER;
						m++;
					}
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.TAKE_DOWN;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.EMBER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.ROAR;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
				break;
			case ARCANINE:
				nickname = toString(s);
				type1=Type.FIRE;
				type2=Type.NONE;
				pokedexNumber =59;
				baseHP=90; baseATK=110; baseDEF=80; baseSPEED=95; baseSPCL=80;
					if (lvl >= 51 && m < 4)
					{
						move[m]=Move.FLAMETHROWER;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.TAKE_DOWN;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 19 && m < 4)
					{
						move[m]=Move.EMBER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.ROAR;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
				break;
			case POLIWAG:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =60;
				baseHP=40; baseATK=50; baseDEF=40; baseSPEED=90; baseSPCL=40;
					if (lvl >= 45 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.AMNESIA;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.BODY_SLAM;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.DOUBLESLAP;
						m++;
					}
					if (lvl >= 19 && m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.HYPNOSIS;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.BUBBLE;
						m++;
					}
				break;
			case POLIWHIRL:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =61;
				baseHP=65; baseATK=65; baseDEF=65; baseSPEED=90; baseSPCL=50;
					if (lvl >= 46 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.AMNESIA;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.BODY_SLAM;
						m++;
					}
					if (lvl >= 26 && m < 4)
					{
						move[m]=Move.DOUBLESLAP;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
					if (lvl >= 17 && m < 4)
					{
						move[m]=Move.HYPNOSIS;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.BUBBLE;
						m++;
					}
				break;
			case POLIWRATH:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.FIGHTING;
				pokedexNumber =62;
				baseHP=90; baseATK=85; baseDEF=95; baseSPEED=70; baseSPCL=70;
					if (lvl >= 47 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.AMNESIA;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.BODY_SLAM;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.DOUBLESLAP;
						m++;
					}
					if (lvl >= 21 && m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.HYPNOSIS;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.BUBBLE;
						m++;
					}
				break;
			case ABRA:
				nickname = toString(s);
				type1=Type.PSYCHIC;
				type2=Type.NONE;
				pokedexNumber =63;
				baseHP=25; baseATK=20; baseDEF=15; baseSPEED=90; baseSPCL=105;
					if (m < 4)
					{
						move[m]=Move.TELEPORT;
						m++;
					}
				break;
			case KADABRA:
				nickname = toString(s);
				type1=Type.PSYCHIC;
				type2=Type.NONE;
				pokedexNumber =64;
				baseHP=40; baseATK=35; baseDEF=30; baseSPEED=105; baseSPCL=120;
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.REFLECT;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.PSYCHIC;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.RECOVER;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.PSYBEAM;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.DISABLE;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.CONFUSION;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.KINESIS;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TELEPORT;
						m++;
					}
				break;
			case ALAKAZAM:
				nickname = toString(s);
				type1=Type.PSYCHIC;
				type2=Type.NONE;
				pokedexNumber =65;
				baseHP=55; baseATK=50; baseDEF=45; baseSPEED=120; baseSPCL=135;
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.REFLECT;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.PSYCHIC;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.RECOVER;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.PSYBEAM;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.DISABLE;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.CONFUSION;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.KINESIS;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TELEPORT;
						m++;
					}
				break;
			case MACHOP:
				nickname = toString(s);
				type1=Type.FIGHTING;
				type2=Type.NONE;
				pokedexNumber =66;
				baseHP=70; baseATK=80; baseDEF=50; baseSPEED=35; baseSPCL=35;
					if (lvl >= 46 && m < 4)
					{
						move[m]=Move.SUBMISSION;
						m++;
					}
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.SEISMIC_TOSS;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.FOCUS_ENERGY;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.LOW_KICK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.KARATE_CHOP;
						m++;
					}
				break;
			case MACHOKE:
				nickname = toString(s);
				type1=Type.FIGHTING;
				type2=Type.NONE;
				pokedexNumber =67;
				baseHP=80; baseATK=100; baseDEF=70; baseSPEED=45; baseSPCL=50;
					if (lvl >= 47 && m < 4)
					{
						move[m]=Move.SUBMISSION;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.SEISMIC_TOSS;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.FOCUS_ENERGY;
						m++;
					}
					if (lvl >= 26 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 21 && m < 4)
					{
						move[m]=Move.LOW_KICK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.KARATE_CHOP;
						m++;
					}
				break;
			case MACHAMP:
				nickname = toString(s);
				type1=Type.FIGHTING;
				type2=Type.NONE;
				pokedexNumber =68;
				baseHP=90; baseATK=130; baseDEF=80; baseSPEED=55; baseSPCL=65;
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.SUBMISSION;
						m++;
					}
					if (lvl >= 41 && m < 4)
					{
						move[m]=Move.SEISMIC_TOSS;
						m++;
					}
					if (lvl >= 34 && m < 4)
					{
						move[m]=Move.FOCUS_ENERGY;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.LOW_KICK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.KARATE_CHOP;
						m++;
					}
				break;
			case BELLSPROUT:
				nickname = toString(s);
				type1=Type.GRASS;
				type2=Type.POISON;
				pokedexNumber =69;
				baseHP=50; baseATK=75; baseDEF=35; baseSPEED=40; baseSPCL=70;
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.SLAM;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.RAZOR_LEAF;
						m++;
					}
					if (lvl >= 26 && m < 4)
					{
						move[m]=Move.ACID;
						m++;
					}
					if (lvl >= 21 && m < 4)
					{
						move[m]=Move.STUN_SPORE;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.SLEEP_POWDER;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.POISONPOWDER;
						m++;
					}
					if (lvl >= 13 && m < 4)
					{
						move[m]=Move.WRAP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWTH;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.VINE_WHIP;
						m++;
					}
				break;
			case WEEPINBELL:
				nickname = toString(s);
				type1=Type.GRASS;
				type2=Type.POISON;
				pokedexNumber =70;
				baseHP=65; baseATK=90; baseDEF=50; baseSPEED=55; baseSPCL=85;
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.SLAM;
						m++;
					}
					if (lvl >= 34 && m < 4)
					{
						move[m]=Move.RAZOR_LEAF;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.ACID;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.STUN_SPORE;
						m++;
					}
					if (lvl >= 19 && m < 4)
					{
						move[m]=Move.SLEEP_POWDER;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.POISONPOWDER;
						m++;
					}
					if (lvl >= 14 && m < 4)
					{
						move[m]=Move.WRAP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWTH;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.VINE_WHIP;
						m++;
					}
				break;
			case VICTREEBEL:
				nickname = toString(s);
				type1=Type.GRASS;
				type2=Type.POISON;
				pokedexNumber =71;
				baseHP=80; baseATK=105; baseDEF=65; baseSPEED=70; baseSPCL=100;
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.SLAM;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.RAZOR_LEAF;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.ACID;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.STUN_SPORE;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.SLEEP_POWDER;
						m++;
					}
					if (lvl >= 17 && m < 4)
					{
						move[m]=Move.POISONPOWDER;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.WRAP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWTH;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.VINE_WHIP;
						m++;
					}
				break;
			case TENTACOOL:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.POISON;
				pokedexNumber =72;
				baseHP=40; baseATK=40; baseDEF=35; baseSPEED=70; baseSPCL=100;
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.SCREECH;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.BARRIER;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.CONSTRICT;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.POISON_STING;
						m++;
					}
					if (lvl >= 13 && m < 4)
					{
						move[m]=Move.WRAP;
						m++;
					}
					if (lvl >= 7 && m < 4)
					{
						move[m]=Move.SUPERSONIC;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.ACID;
						m++;
					}
				break;
			case TENTACRUEL:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.POISON;
				pokedexNumber =73;
				baseHP=80; baseATK=70; baseDEF=65; baseSPEED=100; baseSPCL=120;
					if (lvl >= 49 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 41 && m < 4)
					{
						move[m]=Move.SCREECH;
						m++;
					}
					if (lvl >= 34 && m < 4)
					{
						move[m]=Move.BARRIER;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.CONSTRICT;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
					if (lvl >= 19 && m < 4)
					{
						move[m]=Move.POISON_STING;
						m++;
					}
					if (lvl >= 14 && m < 4)
					{
						move[m]=Move.WRAP;
						m++;
					}
					if (lvl >= 8 && m < 4)
					{
						move[m]=Move.SUPERSONIC;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.ACID;
						m++;
					}
				break;
			case GEODUDE:
				nickname = toString(s);
				type1=Type.ROCK;
				type2=Type.GROUND;
				pokedexNumber =74;
				baseHP=40; baseATK=80; baseDEF=100; baseSPEED=20; baseSPCL=30;
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.EXPLOSION;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.EARTHQUAKE;
						m++;
					}
					if (lvl >= 26 && m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (lvl >= 21 && m < 4)
					{
						move[m]=Move.SELFDESTRUCT;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.ROCK_THROW;
						m++;
					}
					if (lvl >= 11 && m < 4)
					{
						move[m]=Move.DEFENSE_CURL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case GRAVELER:
				nickname = toString(s);
				type1=Type.ROCK;
				type2=Type.GROUND;
				pokedexNumber =75;
				baseHP=55; baseATK=95; baseDEF=115; baseSPEED=35; baseSPCL=45;
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.EXPLOSION;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.EARTHQUAKE;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.SELFDESTRUCT;
						m++;
					}
					if (lvl >= 17 && m < 4)
					{
						move[m]=Move.ROCK_THROW;
						m++;
					}
					if (lvl >= 12 && m < 4)
					{
						move[m]=Move.DEFENSE_CURL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case GOLEM:
				nickname = toString(s);
				type1=Type.ROCK;
				type2=Type.GROUND;
				pokedexNumber =76;
				baseHP=80; baseATK=110; baseDEF=130; baseSPEED=45; baseSPCL=55;
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.EXPLOSION;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.EARTHQUAKE;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.SELFDESTRUCT;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.ROCK_THROW;
						m++;
					}
					if (lvl >= 13 && m < 4)
					{
						move[m]=Move.DEFENSE_CURL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case PONYTA:
				nickname = toString(s);
				type1=Type.FIRE;
				type2=Type.NONE;
				pokedexNumber =77;
				baseHP=50; baseATK=85; baseDEF=55; baseSPEED=90; baseSPCL=65;
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.TAKE_DOWN;
						m++;
					}
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.FIRE_SPIN;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.STOMP;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.EMBER;
						m++;
					}
				break;
			case RAPIDASH:
				nickname = toString(s);
				type1=Type.FIRE;
				type2=Type.NONE;
				pokedexNumber =78;
				baseHP=65; baseATK=100; baseDEF=70; baseSPEED=105; baseSPCL=80;
					if (lvl >= 49 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.TAKE_DOWN;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.FIRE_SPIN;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.STOMP;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.EMBER;
						m++;
					}
				break;
			case SLOWPOKE:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.PSYCHIC;
				pokedexNumber =79;
				baseHP=90; baseATK=65; baseDEF=65; baseSPEED=15; baseSPCL=40;
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.PSYCHIC;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.AMNESIA;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.HEADBUTT;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.DISABLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.CONFUSION;
						m++;
					}
				break;
			case SLOWBRO:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.PSYCHIC;
				pokedexNumber =80;
				baseHP=95; baseATK=75; baseDEF=110; baseSPEED=30; baseSPCL=80;
					if (lvl >= 49 && m < 4)
					{
						move[m]=Move.PSYCHIC;
						m++;
					}
					if (lvl >= 41 && m < 4)
					{
						move[m]=Move.AMNESIA;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.HEADBUTT;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.DISABLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.CONFUSION;
						m++;
					}
				break;
			case MAGNEMITE:
				nickname = toString(s);
				type1=Type.ELECTRIC;
				type2=Type.NONE;
				pokedexNumber =81;
				baseHP=25; baseATK=35; baseDEF=70; baseSPEED=45; baseSPCL=95;
					if (lvl >= 47 && m < 4)
					{
						move[m]=Move.SCREECH;
						m++;
					}
					if (lvl >= 41 && m < 4)
					{
						move[m]=Move.SWIFT;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.THUNDER_WAVE;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.SUPERSONIC;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.THUNDERSHOCK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SONIC_BOOM;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case MAGNETON:
				nickname = toString(s);
				type1=Type.ELECTRIC;
				type2=Type.NONE;
				pokedexNumber =82;
				baseHP=50; baseATK=60; baseDEF=95; baseSPEED=70; baseSPCL=120;
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.SCREECH;
						m++;
					}
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.SWIFT;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.THUNDER_WAVE;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.SUPERSONIC;
						m++;
					}
					if (lvl >= 26 && m < 4)
					{
						move[m]=Move.THUNDERSHOCK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SONIC_BOOM;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case FARFETCH_D:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.FLYING;
				pokedexNumber =83;
				baseHP=52; baseATK=65; baseDEF=55; baseSPEED=60; baseSPCL=58;
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.SLASH;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.SWORDS_DANCE;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.FURY_ATTACK;
						m++;
					}
					if (lvl >= 7 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SAND_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.PECK;
						m++;
					}
				break;
			case DODUO:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.FLYING;
				pokedexNumber =84;
				baseHP=35; baseATK=85; baseDEF=45; baseSPEED=75; baseSPCL=35;
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.TRI_ATTACK;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.RAGE;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.DRILL_PECK;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.FURY_ATTACK;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.PECK;
						m++;
					}
				break;
			case DODRIO:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.FLYING;
				pokedexNumber =85;
				baseHP=60; baseATK=110; baseDEF=70; baseSPEED=100; baseSPCL=60;
					if (lvl >= 45 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 41 && m < 4)
					{
						move[m]=Move.TRI_ATTACK;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.RAGE;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.DRILL_PECK;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.FURY_ATTACK;
						m++;
					}
					if (lvl >= 21 && m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.PECK;
						m++;
					}
				break;
			case SEEL:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =86;
				baseHP=65; baseATK=45; baseDEF=55; baseSPEED=45; baseSPCL=70;
					if (lvl >= 50 && m < 4)
					{
						move[m]=Move.ICE_BEAM;
						m++;
					}
					if (lvl >= 45 && m < 4)
					{
						move[m]=Move.TAKE_DOWN;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.REST;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.AURORA_BEAM;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.HEADBUTT;
						m++;
					}
				break;
			case DEWGONG:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.ICE;
				pokedexNumber =87;
				baseHP=90; baseATK=70; baseDEF=80; baseSPEED=70; baseSPCL=95;
					if (lvl >= 51 && m < 4)
					{
						move[m]=Move.ICE_BEAM;
						m++;
					}
					if (lvl >= 46 && m < 4)
					{
						move[m]=Move.TAKE_DOWN;
						m++;
					}
					if (lvl >= 41 && m < 4)
					{
						move[m]=Move.REST;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.AURORA_BEAM;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.HEADBUTT;
						m++;
					}
				break;
			case GRIMER:
				nickname = toString(s);
				type1=Type.POISON;
				type2=Type.NONE;
				pokedexNumber =88;
				baseHP=80; baseATK=80; baseDEF=50; baseSPEED=25; baseSPCL=40;
					if (lvl >= 55 && m < 4)
					{
						move[m]=Move.ACID_ARMOR;
						m++;
					}
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.SCREECH;
						m++;
					}
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.SLUDGE;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.MINIMIZE;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.POISON_GAS;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.DISABLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.POUND;
						m++;
					}
				break;
			case MUK:
				nickname = toString(s);
				type1=Type.POISON;
				type2=Type.NONE;
				pokedexNumber =89;
				baseHP=105; baseATK=105; baseDEF=75; baseSPEED=50; baseSPCL=65;
					if (lvl >= 56 && m < 4)
					{
						move[m]=Move.ACID_ARMOR;
						m++;
					}
					if (lvl >= 49 && m < 4)
					{
						move[m]=Move.SCREECH;
						m++;
					}
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.SLUDGE;
						m++;
					}
					if (lvl >= 34 && m < 4)
					{
						move[m]=Move.MINIMIZE;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.POISON_GAS;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.DISABLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.POUND;
						m++;
					}
				break;
			case SHELLDER:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =90;
				baseHP=30; baseATK=65; baseDEF=100; baseSPEED=40; baseSPCL=45;
					if (lvl >= 50 && m < 4)
					{
						move[m]=Move.ICE_BEAM;
						m++;
					}
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.AURORA_BEAM;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.CLAMP;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.SUPERSONIC;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.WITHDRAW;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case CLOYSTER:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.ICE;
				pokedexNumber =91;
				baseHP=50; baseATK=95; baseDEF=180; baseSPEED=70; baseSPCL=85;
					if (lvl >= 51 && m < 4)
					{
						move[m]=Move.ICE_BEAM;
						m++;
					}
					if (lvl >= 50 && m < 4)
					{
						move[m]=Move.SPIKE_CANNON;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.AURORA_BEAM;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.CLAMP;
						m++;
					}
					if (lvl >= 19 && m < 4)
					{
						move[m]=Move.SUPERSONIC;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.WITHDRAW;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case GASTLY:
				nickname = toString(s);
				type1=Type.GHOST;
				type2=Type.POISON;
				pokedexNumber =92;
				baseHP=30; baseATK=35; baseDEF=30; baseSPEED=80; baseSPCL=100;
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.DREAM_EATER;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.HYPNOSIS;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.NIGHT_SHADE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.CONFUSE_RAY;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LICK;
						m++;
					}
				break;
			case HAUNTER:
				nickname = toString(s);
				type1=Type.GHOST;
				type2=Type.POISON;
				pokedexNumber =93;
				baseHP=45; baseATK=50; baseDEF=45; baseSPEED=95; baseSPCL=115;
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.DREAM_EATER;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.HYPNOSIS;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.NIGHT_SHADE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.CONFUSE_RAY;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LICK;
						m++;
					}
				break;
			case GENGAR:
				nickname = toString(s);
				type1=Type.GHOST;
				type2=Type.POISON;
				pokedexNumber =94;
				baseHP=60; baseATK=65; baseDEF=60; baseSPEED=110; baseSPCL=130;
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.DREAM_EATER;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.HYPNOSIS;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.NIGHT_SHADE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.CONFUSE_RAY;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LICK;
						m++;
					}
				break;
			case ONIX:
				nickname = toString(s);
				type1=Type.ROCK;
				type2=Type.GROUND;
				pokedexNumber =95;
				baseHP=35; baseATK=45; baseDEF=160; baseSPEED=70; baseSPCL=30;
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.SLAM;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.RAGE;
						m++;
					}
					if (lvl >= 19 && m < 4)
					{
						move[m]=Move.ROCK_THROW;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.BIND;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCREECH;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case DROWZEE:
				nickname = toString(s);
				type1=Type.PSYCHIC;
				type2=Type.NONE;
				pokedexNumber =96;
				baseHP=60; baseATK=48; baseDEF=45; baseSPEED=42; baseSPCL=90;
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.MEDITATE;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.PSYCHIC;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.POISON_GAS;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.HEADBUTT;
						m++;
					}
					if (lvl >= 17 && m < 4)
					{
						move[m]=Move.CONFUSION;
						m++;
					}
					if (lvl >= 12 && m < 4)
					{
						move[m]=Move.DISABLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.HYPNOSIS;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.POUND;
						m++;
					}
				break;
			case HYPNO:
				nickname = toString(s);
				type1=Type.PSYCHIC;
				type2=Type.NONE;
				pokedexNumber =97;
				baseHP=85; baseATK=73; baseDEF=70; baseSPEED=67; baseSPCL=115;
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.MEDITATE;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.PSYCHIC;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.POISON_GAS;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.HEADBUTT;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.CONFUSION;
						m++;
					}
					if (lvl >= 13 && m < 4)
					{
						move[m]=Move.DISABLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.HYPNOSIS;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.POUND;
						m++;
					}
				break;
			case KRABBY:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =98;
				baseHP=30; baseATK=105; baseDEF=90; baseSPEED=50; baseSPCL=25;
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.CRABHAMMER;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.STOMP;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.GUILLOTINE;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.VICEGRIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.BUBBLE;
						m++;
					}
				break;
			case KINGLER:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =99;
				baseHP=55; baseATK=130; baseDEF=115; baseSPEED=75; baseSPCL=50;
					if (lvl >= 41 && m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.CRABHAMMER;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.STOMP;
						m++;
					}
					if (lvl >= 26 && m < 4)
					{
						move[m]=Move.GUILLOTINE;
						m++;
					}
					if (lvl >= 21 && m < 4)
					{
						move[m]=Move.VICEGRIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.BUBBLE;
						m++;
					}
				break;
			case VOLTORB:
				nickname = toString(s);
				type1=Type.ELECTRIC;
				type2=Type.NONE;
				pokedexNumber =100;
				baseHP=40; baseATK=30; baseDEF=50; baseSPEED=100; baseSPCL=55;
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.EXPLOSION;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.SWIFT;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.LIGHT_SCREEN;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.SELFDESTRUCT;
						m++;
					}
					if (lvl >= 17 && m < 4)
					{
						move[m]=Move.SONIC_BOOM;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCREECH;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case ELECTRODE:
				nickname = toString(s);
				type1=Type.ELECTRIC;
				type2=Type.NONE;
				pokedexNumber =101;
				baseHP=60; baseATK=50; baseDEF=70; baseSPEED=140; baseSPCL=80;
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.EXPLOSION;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.SWIFT;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.LIGHT_SCREEN;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.SELFDESTRUCT;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.SONIC_BOOM;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCREECH;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case EXEGGCUTE:
				nickname = toString(s);
				type1=Type.GRASS;
				type2=Type.PSYCHIC;
				pokedexNumber =102;
				baseHP=60; baseATK=40; baseDEF=80; baseSPEED=40; baseSPCL=60;
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.SLEEP_POWDER;
						m++;
					}
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.SOLARBEAM;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.POISONPOWDER;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.STUN_SPORE;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.LEECH_SEED;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.REFLECT;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.HYPNOSIS;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.BARRAGE;
						m++;
					}
				break;
			case EXEGGUTOR:
				nickname = toString(s);
				type1=Type.GRASS;
				type2=Type.PSYCHIC;
				pokedexNumber =103;
				baseHP=95; baseATK=95; baseDEF=85; baseSPEED=55; baseSPCL=125;
					if (lvl >= 49 && m < 4)
					{
						move[m]=Move.SLEEP_POWDER;
						m++;
					}
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.SOLARBEAM;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.POISONPOWDER;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.STUN_SPORE;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.LEECH_SEED;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.STOMP;
						m++;
					}
					if (lvl >= 26 && m < 4)
					{
						move[m]=Move.REFLECT;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.HYPNOSIS;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.BARRAGE;
						m++;
					}
				break;
			case CUBONE:
				nickname = toString(s);
				type1=Type.GROUND;
				type2=Type.NONE;
				pokedexNumber =104;
				baseHP=50; baseATK=50; baseDEF=95; baseSPEED=35; baseSPCL=40;
					if (lvl >= 46 && m < 4)
					{
						move[m]=Move.RAGE;
						m++;
					}
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.BONEMERANG;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.THRASH;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.FOCUS_ENERGY;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.BONE_CLUB;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
				break;
			case MAROWAK:
				nickname = toString(s);
				type1=Type.GROUND;
				type2=Type.NONE;
				pokedexNumber =105;
				baseHP=60; baseATK=80; baseDEF=110; baseSPEED=45; baseSPCL=50;
					if (lvl >= 47 && m < 4)
					{
						move[m]=Move.RAGE;
						m++;
					}
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.BONEMERANG;
						m++;
					}
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.THRASH;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.FOCUS_ENERGY;
						m++;
					}
					if (lvl >= 26 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.BONE_CLUB;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
				break;
			case HITMONLEE:
				nickname = toString(s);
				type1=Type.FIGHTING;
				type2=Type.NONE;
				pokedexNumber =106;
				baseHP=50; baseATK=120; baseDEF=53; baseSPEED=87; baseSPCL=35;
					if (lvl >= 53 && m < 4)
					{
						move[m]=Move.MEGA_KICK;
						m++;
					}
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.HI_JUMP_KICK;
						m++;
					}
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.FOCUS_ENERGY;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.JUMP_KICK;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.ROLLING_KICK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.MEDITATE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.DOUBLE_KICK;
						m++;
					}
				break;
			case HITMONCHAN:
				nickname = toString(s);
				type1=Type.FIGHTING;
				type2=Type.NONE;
				pokedexNumber =107;
				baseHP=50; baseATK=105; baseDEF=79; baseSPEED=76; baseSPCL=35;
					if (lvl >= 53 && m < 4)
					{
						move[m]=Move.COUNTER;
						m++;
					}
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.MEGA_PUNCH;
						m++;
					}
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.THUNDERPUNCH;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.ICE_PUNCH;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.FIRE_PUNCH;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.COMET_PUNCH;
						m++;
					}
				break;
			case LICKITUNG:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.NONE;
				pokedexNumber =108;
				baseHP=90; baseATK=55; baseDEF=75; baseSPEED=30; baseSPCL=60;
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.SCREECH;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.SLAM;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.DEFENSE_CURL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.DISABLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.STOMP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SUPERSONIC;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.WRAP;
						m++;
					}
				break;
			case KOFFING:
				nickname = toString(s);
				type1=Type.POISON;
				type2=Type.NONE;
				pokedexNumber =109;
				baseHP=40; baseATK=65; baseDEF=95; baseSPEED=35; baseSPCL=60;
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.EXPLOSION;
						m++;
					}
					if (lvl >= 45 && m < 4)
					{
						move[m]=Move.HAZE;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.SELFDESTRUCT;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.SMOKESCREEN;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.SLUDGE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SMOG;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case WEEZING:
				nickname = toString(s);
				type1=Type.POISON;
				type2=Type.NONE;
				pokedexNumber =110;
				baseHP=65; baseATK=90; baseDEF=120; baseSPEED=60; baseSPCL=85;
					if (lvl >= 49 && m < 4)
					{
						move[m]=Move.EXPLOSION;
						m++;
					}
					if (lvl >= 46 && m < 4)
					{
						move[m]=Move.HAZE;
						m++;
					}
					if (lvl >= 41 && m < 4)
					{
						move[m]=Move.SELFDESTRUCT;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.SMOKESCREEN;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.SLUDGE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SMOG;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case RHYHORN:
				nickname = toString(s);
				type1=Type.GROUND;
				type2=Type.ROCK;
				pokedexNumber =111;
				baseHP=80; baseATK=85; baseDEF=95; baseSPEED=25; baseSPCL=30;
					if (lvl >= 55 && m < 4)
					{
						move[m]=Move.TAKE_DOWN;
						m++;
					}
					if (lvl >= 50 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 45 && m < 4)
					{
						move[m]=Move.HORN_DRILL;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.FURY_ATTACK;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.STOMP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.HORN_ATTACK;
						m++;
					}
				break;
			case RHYDON:
				nickname = toString(s);
				type1=Type.GROUND;
				type2=Type.ROCK;
				pokedexNumber =112;
				baseHP=105; baseATK=130; baseDEF=120; baseSPEED=40; baseSPCL=45;
					if (lvl >= 56 && m < 4)
					{
						move[m]=Move.TAKE_DOWN;
						m++;
					}
					if (lvl >= 51 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 46 && m < 4)
					{
						move[m]=Move.HORN_DRILL;
						m++;
					}
					if (lvl >= 41 && m < 4)
					{
						move[m]=Move.FURY_ATTACK;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.STOMP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.HORN_ATTACK;
						m++;
					}
				break;
			case CHANSEY:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.NONE;
				pokedexNumber =113;
				baseHP=250; baseATK=5; baseDEF=5; baseSPEED=50; baseSPCL=105;
					if (lvl >= 54 && m < 4)
					{
						move[m]=Move.DOUBLE_EDGE;
						m++;
					}
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.LIGHT_SCREEN;
						m++;
					}
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.DEFENSE_CURL;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.MINIMIZE;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.SING;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.DOUBLESLAP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.POUND;
						m++;
					}
				break;
			case TANGELA:
				nickname = toString(s);
				type1=Type.GRASS;
				type2=Type.NONE;
				pokedexNumber =114;
				baseHP=65; baseATK=55; baseDEF=115; baseSPEED=60; baseSPCL=100;
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.GROWTH;
						m++;
					}
					if (lvl >= 45 && m < 4)
					{
						move[m]=Move.SLAM;
						m++;
					}
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.SLEEP_POWDER;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.STUN_SPORE;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.POISONPOWDER;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.ABSORB;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.BIND;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.CONSTRICT;
						m++;
					}
				break;
			case KANGASKHAN:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.NONE;
				pokedexNumber =115;
				baseHP=105; baseATK=95; baseDEF=80; baseSPEED=90; baseSPCL=40;
					if (lvl >= 46 && m < 4)
					{
						move[m]=Move.DIZZY_PUNCH;
						m++;
					}
					if (lvl >= 41 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.MEGA_PUNCH;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (lvl >= 26 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.RAGE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.COMET_PUNCH;
						m++;
					}
				break;
			case HORSEA:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =116;
				baseHP=30; baseATK=40; baseDEF=70; baseSPEED=60; baseSPCL=70;
					if (lvl >= 45 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 19 && m < 4)
					{
						move[m]=Move.SMOKESCREEN;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.BUBBLE;
						m++;
					}
				break;
			case SEADRA:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =117;
				baseHP=55; baseATK=65; baseDEF=95; baseSPEED=85; baseSPCL=95;
					if (lvl >= 46 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.SMOKESCREEN;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.BUBBLE;
						m++;
					}
				break;
			case GOLDEEN:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =118;
				baseHP=45; baseATK=67; baseDEF=60; baseSPEED=63; baseSPCL=50;
					if (lvl >= 54 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 45 && m < 4)
					{
						move[m]=Move.HORN_DRILL;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.WATERFALL;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.FURY_ATTACK;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.HORN_ATTACK;
						m++;
					}
					if (lvl >= 19 && m < 4)
					{
						move[m]=Move.SUPERSONIC;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.PECK;
						m++;
					}
				break;
			case SEAKING:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =119;
				baseHP=80; baseATK=92; baseDEF=65; baseSPEED=68; baseSPCL=80;
					if (lvl >= 55 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 46 && m < 4)
					{
						move[m]=Move.HORN_DRILL;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.WATERFALL;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.FURY_ATTACK;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.HORN_ATTACK;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.SUPERSONIC;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.PECK;
						m++;
					}
				break;
			case STARYU:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =120;
				baseHP=30; baseATK=45; baseDEF=55; baseSPEED=85; baseSPCL=70;
					if (lvl >= 47 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.LIGHT_SCREEN;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.MINIMIZE;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.SWIFT;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.RECOVER;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (lvl >= 17 && m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case STARMIE:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.PSYCHIC;
				pokedexNumber =121;
				baseHP=60; baseATK=75; baseDEF=85; baseSPEED=115; baseSPCL=100;
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.LIGHT_SCREEN;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.MINIMIZE;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.SWIFT;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.RECOVER;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (lvl >= 18 && m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case MR_MIME:
				nickname = toString(s);
				type1=Type.PSYCHIC;
				type2=Type.NONE;
				pokedexNumber =122;
				baseHP=40; baseATK=45; baseDEF=65; baseSPEED=90; baseSPCL=100;
					if (lvl >= 47 && m < 4)
					{
						move[m]=Move.SUBSTITUTE;
						m++;
					}
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.MEDITATE;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.DOUBLESLAP;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.LIGHT_SCREEN;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.CONFUSION;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.BARRIER;
						m++;
					}
				break;
			case SCYTHER:
				nickname = toString(s);
				type1=Type.BUG;
				type2=Type.FLYING;
				pokedexNumber =123;
				baseHP=70; baseATK=110; baseDEF=80; baseSPEED=105; baseSPCL=55;
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.SWORDS_DANCE;
						m++;
					}
					if (lvl >= 29 && m < 4)
					{
						move[m]=Move.SLASH;
						m++;
					}
					if (lvl >= 24 && m < 4)
					{
						move[m]=Move.DOUBLE_TEAM;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.FOCUS_ENERGY;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.QUICK_ATTACK;
						m++;
					}
				break;
			case JYNX:
				nickname = toString(s);
				type1=Type.ICE;
				type2=Type.PSYCHIC;
				pokedexNumber =124;
				baseHP=65; baseATK=50; baseDEF=35; baseSPEED=95; baseSPCL=95;
					if (lvl >= 58 && m < 4)
					{
						move[m]=Move.BLIZZARD;
						m++;
					}
					if (lvl >= 47 && m < 4)
					{
						move[m]=Move.THRASH;
						m++;
					}
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.BODY_SLAM;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.ICE_PUNCH;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.DOUBLESLAP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LICK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LOVELY_KISS;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.POUND;
						m++;
					}
				break;
			case ELECTABUZZ:
				nickname = toString(s);
				type1=Type.ELECTRIC;
				type2=Type.NONE;
				pokedexNumber =125;
				baseHP=65; baseATK=83; baseDEF=57; baseSPEED=105; baseSPCL=85;
					if (lvl >= 54 && m < 4)
					{
						move[m]=Move.THUNDER;
						m++;
					}
					if (lvl >= 49 && m < 4)
					{
						move[m]=Move.LIGHT_SCREEN;
						m++;
					}
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.THUNDERPUNCH;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.SCREECH;
						m++;
					}
					if (lvl >= 34 && m < 4)
					{
						move[m]=Move.THUNDERSHOCK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.QUICK_ATTACK;
						m++;
					}
				break;
			case MAGMAR:
				nickname = toString(s);
				type1=Type.FIRE;
				type2=Type.NONE;
				pokedexNumber =126;
				baseHP=65; baseATK=95; baseDEF=57; baseSPEED=93; baseSPCL=85;
					if (lvl >= 55 && m < 4)
					{
						move[m]=Move.FLAMETHROWER;
						m++;
					}
					if (lvl >= 52 && m < 4)
					{
						move[m]=Move.SMOG;
						m++;
					}
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.SMOKESCREEN;
						m++;
					}
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.FIRE_PUNCH;
						m++;
					}
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.CONFUSE_RAY;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.EMBER;
						m++;
					}
				break;
			case PINSIR:
				nickname = toString(s);
				type1=Type.BUG;
				type2=Type.NONE;
				pokedexNumber =127;
				baseHP=65; baseATK=125; baseDEF=100; baseSPEED=85; baseSPCL=55;
					if (lvl >= 54 && m < 4)
					{
						move[m]=Move.SWORDS_DANCE;
						m++;
					}
					if (lvl >= 49 && m < 4)
					{
						move[m]=Move.SLASH;
						m++;
					}
					if (lvl >= 43 && m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (lvl >= 36 && m < 4)
					{
						move[m]=Move.FOCUS_ENERGY;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.GUILLOTINE;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.SEISMIC_TOSS;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.VICEGRIP;
						m++;
					}
				break;
			case TAUROS:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.NONE;
				pokedexNumber =128;
				baseHP=75; baseATK=100; baseDEF=95; baseSPEED=110; baseSPCL=70;
					if (lvl >= 51 && m < 4)
					{
						move[m]=Move.TAKE_DOWN;
						m++;
					}
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.RAGE;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.STOMP;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case MAGIKARP:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =129;
				baseHP=20; baseATK=10; baseDEF=55; baseSPEED=80; baseSPCL=20;
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SPLASH;
						m++;
					}
				break;
			case GYARADOS:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.FLYING;
				pokedexNumber =130;
				baseHP=95; baseATK=125; baseDEF=79; baseSPEED=81; baseSPCL=100;
					if (lvl >= 52 && m < 4)
					{
						move[m]=Move.HYPER_BEAM;
						m++;
					}
					if (lvl >= 41 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.DRAGON_RAGE;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (lvl >= 15 && m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SPLASH;
						m++;
					}
				break;
			case LAPRAS:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.ICE;
				pokedexNumber =131;
				baseHP=130; baseATK=85; baseDEF=80; baseSPEED=60; baseSPCL=95;
					if (lvl >= 46 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.ICE_BEAM;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.CONFUSE_RAY;
						m++;
					}
					if (lvl >= 25 && m < 4)
					{
						move[m]=Move.BODY_SLAM;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.MIST;
						m++;
					}
					if (lvl >= 16 && m < 4)
					{
						move[m]=Move.SING;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.GROWL;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
				break;
			case DITTO:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.NONE;
				pokedexNumber =132;
				baseHP=48; baseATK=48; baseDEF=48; baseSPEED=48; baseSPCL=48;
					if (m < 4)
					{
						move[m]=Move.TRANSFORM;
						m++;
					}
				break;
			case EEVEE:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.NONE;
				pokedexNumber =133;
				baseHP=55; baseATK=55; baseDEF=50; baseSPEED=55; baseSPCL=65;
					if (lvl >= 45 && m < 4)
					{
						move[m]=Move.TAKE_DOWN;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.QUICK_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SAND_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case VAPOREON:
				nickname = toString(s);
				type1=Type.WATER;
				type2=Type.NONE;
				pokedexNumber =134;
				baseHP=130; baseATK=65; baseDEF=60; baseSPEED=65; baseSPCL=110;
					if (lvl >= 52 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.MIST;
						m++;
					}
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.HAZE;
						m++;
					}
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.ACID_ARMOR;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.QUICK_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SAND_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case JOLTEON:
				nickname = toString(s);
				type1=Type.ELECTRIC;
				type2=Type.NONE;
				pokedexNumber =135;
				baseHP=65; baseATK=65; baseDEF=60; baseSPEED=130; baseSPCL=110;
					if (lvl >= 52 && m < 4)
					{
						move[m]=Move.THUNDER;
						m++;
					}
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.PIN_MISSILE;
						m++;
					}
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.DOUBLE_KICK;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.THUNDER_WAVE;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.THUNDERSHOCK;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.QUICK_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SAND_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case FLAREON:
				nickname = toString(s);
				type1=Type.FIRE;
				type2=Type.NONE;
				pokedexNumber =136;
				baseHP=65; baseATK=130; baseDEF=60; baseSPEED=65; baseSPCL=110;
					if (lvl >= 54 && m < 4)
					{
						move[m]=Move.FLAMETHROWER;
						m++;
					}
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.RAGE;
						m++;
					}
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.FIRE_SPIN;
						m++;
					}
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (lvl >= 37 && m < 4)
					{
						move[m]=Move.TAIL_WHIP;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.EMBER;
						m++;
					}
					if (lvl >= 27 && m < 4)
					{
						move[m]=Move.QUICK_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SAND_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case PORYGON:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.NONE;
				pokedexNumber =137;
				baseHP=65; baseATK=60; baseDEF=70; baseSPEED=40; baseSPCL=75;
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.TRI_ATTACK;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 28 && m < 4)
					{
						move[m]=Move.RECOVER;
						m++;
					}
					if (lvl >= 23 && m < 4)
					{
						move[m]=Move.PSYBEAM;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.CONVERSION;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SHARPEN;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.TACKLE;
						m++;
					}
				break;
			case OMANYTE:
				nickname = toString(s);
				type1=Type.ROCK;
				type2=Type.WATER;
				pokedexNumber =138;
				baseHP=35; baseATK=40; baseDEF=100; baseSPEED=35; baseSPCL=90;
					if (lvl >= 53 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 46 && m < 4)
					{
						move[m]=Move.SPIKE_CANNON;
						m++;
					}
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 34 && m < 4)
					{
						move[m]=Move.HORN_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.WITHDRAW;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
				break;
			case OMASTAR:
				nickname = toString(s);
				type1=Type.ROCK;
				type2=Type.WATER;
				pokedexNumber =139;
				baseHP=70; baseATK=60; baseDEF=125; baseSPEED=55; baseSPCL=115;
					if (lvl >= 49 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.SPIKE_CANNON;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.HORN_ATTACK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.WITHDRAW;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
				break;
			case KABUTO:
				nickname = toString(s);
				type1=Type.ROCK;
				type2=Type.WATER;
				pokedexNumber =140;
				baseHP=30; baseATK=80; baseDEF=90; baseSPEED=55; baseSPCL=45;
					if (lvl >= 49 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 44 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 39 && m < 4)
					{
						move[m]=Move.SLASH;
						m++;
					}
					if (lvl >= 34 && m < 4)
					{
						move[m]=Move.ABSORB;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
				break;
			case KABUTOPS:
				nickname = toString(s);
				type1=Type.ROCK;
				type2=Type.WATER;
				pokedexNumber =141;
				baseHP=60; baseATK=115; baseDEF=105; baseSPEED=80; baseSPCL=70;
					if (lvl >= 50 && m < 4)
					{
						move[m]=Move.HYDRO_PUMP;
						m++;
					}
					if (lvl >= 45 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.SLASH;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.ABSORB;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SCRATCH;
						m++;
					}
				break;
			case AERODACTYL:
				nickname = toString(s);
				type1=Type.ROCK;
				type2=Type.FLYING;
				pokedexNumber =142;
				baseHP=80; baseATK=105; baseDEF=65; baseSPEED=130; baseSPCL=60;
					if (lvl >= 54 && m < 4)
					{
						move[m]=Move.HYPER_BEAM;
						m++;
					}
					if (lvl >= 45 && m < 4)
					{
						move[m]=Move.TAKE_DOWN;
						m++;
					}
					if (lvl >= 38 && m < 4)
					{
						move[m]=Move.BITE;
						m++;
					}
					if (lvl >= 33 && m < 4)
					{
						move[m]=Move.SUPERSONIC;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.WING_ATTACK;
						m++;
					}
				break;
			case SNORLAX:
				nickname = toString(s);
				type1=Type.NORMAL;
				type2=Type.NONE;
				pokedexNumber =143;
				baseHP=160; baseATK=110; baseDEF=65; baseSPEED=30; baseSPCL=65;
					if (lvl >= 56 && m < 4)
					{
						move[m]=Move.HYPER_BEAM;
						m++;
					}
					if (lvl >= 48 && m < 4)
					{
						move[m]=Move.DOUBLE_EDGE;
						m++;
					}
					if (lvl >= 41 && m < 4)
					{
						move[m]=Move.HARDEN;
						m++;
					}
					if (lvl >= 35 && m < 4)
					{
						move[m]=Move.BODY_SLAM;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.REST;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.AMNESIA;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.HEADBUTT;
						m++;
					}
				break;
			case ARTICUNO:
				nickname = toString(s);
				type1=Type.ICE;
				type2=Type.FLYING;
				pokedexNumber =144;
				baseHP=90; baseATK=85; baseDEF=100; baseSPEED=85; baseSPCL=125;
					if (lvl >= 60 && m < 4)
					{
						move[m]=Move.MIST;
						m++;
					}
					if (lvl >= 55 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 51 && m < 4)
					{
						move[m]=Move.BLIZZARD;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.ICE_BEAM;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.PECK;
						m++;
					}
				break;
			case ZAPDOS:
				nickname = toString(s);
				type1=Type.ELECTRIC;
				type2=Type.FLYING;
				pokedexNumber =145;
				baseHP=90; baseATK=90; baseDEF=85; baseSPEED=100; baseSPCL=125;
					if (lvl >= 60 && m < 4)
					{
						move[m]=Move.LIGHT_SCREEN;
						m++;
					}
					if (lvl >= 55 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 51 && m < 4)
					{
						move[m]=Move.THUNDER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.DRILL_PECK;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.THUNDERSHOCK;
						m++;
					}
				break;
			case MOLTRES:
				nickname = toString(s);
				type1=Type.FIRE;
				type2=Type.FLYING;
				pokedexNumber =146;
				baseHP=90; baseATK=100; baseDEF=90; baseSPEED=90; baseSPCL=125;
					if (lvl >= 60 && m < 4)
					{
						move[m]=Move.SKY_ATTACK;
						m++;
					}
					if (lvl >= 55 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (lvl >= 51 && m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.FIRE_SPIN;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.PECK;
						m++;
					}
				break;
			case DRATINI:
				nickname = toString(s);
				type1=Type.DRAGON;
				type2=Type.NONE;
				pokedexNumber =147;
				baseHP=41; baseATK=64; baseDEF=45; baseSPEED=50; baseSPCL=50;
					if (lvl >= 50 && m < 4)
					{
						move[m]=Move.HYPER_BEAM;
						m++;
					}
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.DRAGON_RAGE;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.SLAM;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.THUNDER_WAVE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.WRAP;
						m++;
					}
				break;
			case DRAGONAIR:
				nickname = toString(s);
				type1=Type.DRAGON;
				type2=Type.NONE;
				pokedexNumber=148;
				baseHP=61; baseATK=84; baseDEF=65; baseSPEED=70; baseSPCL=70;
					if (lvl >= 51 && m < 4)
					{
						move[m]=Move.HYPER_BEAM;
						m++;
					}
					if (lvl >= 41 && m < 4)
					{
						move[m]=Move.DRAGON_RAGE;
						m++;
					}
					if (lvl >= 31 && m < 4)
					{
						move[m]=Move.SLAM;
						m++;
					}
					if (lvl >= 21 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.THUNDER_WAVE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.WRAP;
						m++;
					}
				break;
			case DRAGONITE:
				nickname = toString(s);
				type1=Type.DRAGON;
				type2=Type.FLYING;
				pokedexNumber =149;
				baseHP=91; baseATK=134; baseDEF=95; baseSPEED=80; baseSPCL=100;
					if (lvl >= 52 && m < 4)
					{
						move[m]=Move.HYPER_BEAM;
						m++;
					}
					if (lvl >= 42 && m < 4)
					{
						move[m]=Move.DRAGON_RAGE;
						m++;
					}
					if (lvl >= 32 && m < 4)
					{
						move[m]=Move.SLAM;
						m++;
					}
					if (lvl >= 22 && m < 4)
					{
						move[m]=Move.AGILITY;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.THUNDER_WAVE;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.LEER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.WRAP;
						m++;
					}
				break;
			case MEWTWO:
				nickname = toString(s);
				type1=Type.PSYCHIC;
				type2=Type.NONE;
				pokedexNumber =150;
				baseHP=106; baseATK=110; baseDEF=90; baseSPEED=130; baseSPCL=154;
					if (lvl >= 81 && m < 4)
					{
						move[m]=Move.AMNESIA;
						m++;
					}
					if (lvl >= 75 && m < 4)
					{
						move[m]=Move.MIST;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.RECOVER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.PSYCHIC;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.BARRIER;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.SWIFT;
						m++;
					}
				break;
			case MEW:
				nickname = toString(s);
				type1=Type.PSYCHIC;
				type2=Type.NONE;
				pokedexNumber =151;
				baseHP=100; baseATK=100; baseDEF=100; baseSPEED=100; baseSPCL=100;
					if (lvl >= 40 && m < 4)
					{
						move[m]=Move.PSYCHIC;
						m++;
					}
					if (lvl >= 30 && m < 4)
					{
						move[m]=Move.METRONOME;
						m++;
					}
					if (lvl >= 20 && m < 4)
					{
						move[m]=Move.MEGA_PUNCH;
						m++;
					}
					if (lvl >= 10 && m < 4)
					{
						move[m]=Move.TRANSFORM;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.POUND;
						m++;
					}
				break;
			case MISSINGNO:
				nickname = toString(s);
				type1=Type.BIRD;
				type2=Type.NORMAL;
				pokedexNumber =256;
				baseHP=256; baseATK=256; baseDEF=256; baseSPEED=16; baseSPCL=256;
					if (lvl >= 10 && m < 4)
					{
						move[m]=Move.FISSURE;
						m++;
					}
					if (lvl >= 7 && m < 4)
					{
						move[m]=Move.SKY_ATTACK;
						m++;
					}
					if (lvl >= 5 && m < 4)
					{
						move[m]=Move.WATER_GUN;
						m++;
					}
					if (m < 4)
					{
						move[m]=Move.HYPER_BEAM;
						m++;
					}
				break;
			default:
				System.out.println("Illegal Pokemon Creation Attempt");
				System.exit(0);
				break;
		}

		healthMax=Mechanics.calcHPStat(baseHP,HP_IV, HP_EV,level);
		health=healthMax;

		atk=Mechanics.calcOtherStat(baseATK,ATK_IV,ATK_EV,level);
		def=Mechanics.calcOtherStat(baseDEF,DEF_IV, DEF_EV,level);
		spcl=Mechanics.calcOtherStat(baseSPCL,SPCL_IV,SPCL_EV,level);
		speed=Mechanics.calcOtherStat(baseSPEED,SPEED_IV,SPEED_EV,level);

		System.out.println("New "+this.species+" created successfully.");
	}

	public void createMoves()
	{
		for(int i=0; i<4; i++)
		{
			switch(move[i])
			{
				case TACKLE:
					move[i].accuracy=95;
					move[i].basePower=35;
					move[i].pp=35;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case GROWL:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=40;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.LOWER_STAT;
					break;
				case ABSORB:
					move[i].accuracy=100;
					move[i].basePower=20;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.ABSORB;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.GRASS;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case ACID:
					move[i].accuracy=100;
					move[i].basePower=40;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.POISON;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case ACID_ARMOR:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=40;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.POISON;
					move[i].mainEffect=Primary_Effect.RAISE_STAT;
					break;
				case AGILITY:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.PSYCHIC;
					move[i].mainEffect=Primary_Effect.RAISE_STAT;
					break;
				case AMNESIA:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.PSYCHIC;
					move[i].mainEffect=Primary_Effect.RAISE_STAT;
					break;
				case AURORA_BEAM:
					move[i].accuracy=100;
					move[i].basePower=65;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.ICE;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.FRZ;
					break;
				case BARRAGE:
					move[i].accuracy=85;
					move[i].basePower=15;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.MULTI_HIT;
					move[i].sideEffectAcc=85;
					move[i].moveType=Type.PSYCHIC;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case BARRIER:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.PSYCHIC;
					move[i].mainEffect=Primary_Effect.RAISE_STAT;
					break;
				case BIDE:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.COLLECT_DAMAGE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case BIND:
					move[i].accuracy=75;
					move[i].basePower=15;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.MULTI_TURN;
					move[i].sideEffectAcc=75;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case BITE:
					move[i].accuracy=100;
					move[i].basePower=60;
					move[i].pp=25;
					move[i].sideEffect=Side_Effect.SUBSTATUS;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].substatus=Substatus.FLINCH;
					break;
				case BLIZZARD:
					move[i].accuracy=90;
					move[i].basePower=120;
					move[i].pp=5;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.ICE;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.FRZ;
					break;
				case BODY_SLAM:
					move[i].accuracy=100;
					move[i].basePower=85;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=30;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.PAR;
					break;
				case BONE_CLUB:
					move[i].accuracy=85;
					move[i].basePower=65;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.SUBSTATUS;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.GROUND;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].substatus=Substatus.FLINCH;
					break;
				case BONEMERANG:
					move[i].accuracy=90;
					move[i].basePower=50;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.MULTI_HIT;
					move[i].sideEffectAcc=90;
					move[i].moveType=Type.GROUND;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case BUBBLE:
					move[i].accuracy=100;
					move[i].basePower=20;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.WATER;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case BUBBLEBEAM:
					move[i].accuracy=100;
					move[i].basePower=65;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.WATER;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case CLAMP:
					move[i].accuracy=75;
					move[i].basePower=35;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.MULTI_TURN;
					move[i].sideEffectAcc=75;
					move[i].moveType=Type.WATER;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case COMET_PUNCH:
					move[i].accuracy=85;
					move[i].basePower=18;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.MULTI_HIT;
					move[i].sideEffectAcc=85;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case CONFUSE_RAY:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.SUBSTATUS;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.GHOST;
					move[i].mainEffect=Primary_Effect.INFLICT_STATUS;
					move[i].substatus=Substatus.CONFU;
					break;
				case CONFUSION:
					move[i].accuracy=100;
					move[i].basePower=50;
					move[i].pp=25;
					move[i].sideEffect=Side_Effect.SUBSTATUS;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.PSYCHIC;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].substatus=Substatus.CONFU;
					break;
				case CONSTRICT:
					move[i].accuracy=100;
					move[i].basePower=10;
					move[i].pp=35;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case CONVERSION:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.CONVERSION;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case COUNTER:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.COLLECT_DAMAGE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.FIGHTING;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case CRABHAMMER:
					move[i].accuracy=85;
					move[i].basePower=90;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.HIGH_CRIT;
					move[i].sideEffectAcc=85;
					move[i].moveType=Type.WATER;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case CUT:
					move[i].accuracy=95;
					move[i].basePower=50;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case DEFENSE_CURL:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=40;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.RAISE_STAT;
					break;
				case DIG:
					move[i].accuracy=100;
					move[i].basePower=100;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.HIDE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.GROUND;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case DISABLE:
					move[i].accuracy=55;
					move[i].basePower=0;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.DISABLE;
					move[i].sideEffectAcc=55;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case DIZZY_PUNCH:
					move[i].accuracy=100;
					move[i].basePower=70;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case DOUBLE_KICK:
					move[i].accuracy=100;
					move[i].basePower=30;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.MULTI_HIT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.FIGHTING;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case DOUBLE_TEAM:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.RAISE_STAT;
					break;
				case DOUBLE_EDGE:
					move[i].accuracy=100;
					move[i].basePower=100;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.RECOIL;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case DOUBLESLAP:
					move[i].accuracy=85;
					move[i].basePower=15;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.MULTI_HIT;
					move[i].sideEffectAcc=85;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case DRAGON_RAGE:
					move[i].accuracy=100;
					move[i].basePower=40;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.FIXED_DAMAGE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.DRAGON;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case DREAM_EATER:
					move[i].accuracy=100;
					move[i].basePower=100;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.ABSORB;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.PSYCHIC;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case DRILL_PECK:
					move[i].accuracy=80;
					move[i].basePower=100;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.FLYING;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case EARTHQUAKE:
					move[i].accuracy=100;
					move[i].basePower=100;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.GROUND;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case EGG_BOMB:
					move[i].accuracy=75;
					move[i].basePower=100;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case EMBER:
					move[i].accuracy=100;
					move[i].basePower=40;
					move[i].pp=25;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.FIRE;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.BRN;
					break;
				case EXPLOSION:
					move[i].accuracy=100;
					move[i].basePower=170;
					move[i].pp=5;
					move[i].sideEffect=Side_Effect.FAINT;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case FIRE_BLAST:
					move[i].accuracy=85;
					move[i].basePower=120;
					move[i].pp=5;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=30;
					move[i].moveType=Type.FIRE;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.BRN;
					break;
				case FIRE_PUNCH:
					move[i].accuracy=100;
					move[i].basePower=75;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.FIRE;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.BRN;
					break;
				case FIRE_SPIN:
					move[i].accuracy=70;
					move[i].basePower=15;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.MULTI_TURN;
					move[i].sideEffectAcc=70;
					move[i].moveType=Type.FIRE;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case FISSURE:
					move[i].accuracy=30;
					move[i].basePower=0;
					move[i].pp=5;
					move[i].sideEffect=Side_Effect.OHKO;
					move[i].sideEffectAcc=30;
					move[i].moveType=Type.GROUND;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case FLAMETHROWER:
					move[i].accuracy=100;
					move[i].basePower=95;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.FIRE;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.BRN;
					break;
				case FLASH:
					move[i].accuracy=70;
					move[i].basePower=0;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=70;
					move[i].moveType=Type.ELECTRIC;
					move[i].mainEffect=Primary_Effect.LOWER_STAT;
					break;
				case FLY:
					move[i].accuracy=95;
					move[i].basePower=70;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.HIDE;
					move[i].sideEffectAcc=95;
					move[i].moveType=Type.FLYING;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case FOCUS_ENERGY:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case FURY_SWIPES:
					move[i].accuracy=80;
					move[i].basePower=18;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.MULTI_HIT;
					move[i].sideEffectAcc=80;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case FURY_ATTACK:
					move[i].accuracy=85;
					move[i].basePower=15;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.MULTI_HIT;
					move[i].sideEffectAcc=85;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case GLARE:
					move[i].accuracy=75;
					move[i].basePower=0;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=75;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.INFLICT_STATUS;
					move[i].status=Status.PAR;
					break;
				case GROWTH:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=40;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.GRASS;
					move[i].mainEffect=Primary_Effect.RAISE_STAT;
					break;
				case GUILLOTINE:
					move[i].accuracy=30;
					move[i].basePower=0;
					move[i].pp=5;
					move[i].sideEffect=Side_Effect.OHKO;
					move[i].sideEffectAcc=30;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case GUST:
					move[i].accuracy=100;
					move[i].basePower=40;
					move[i].pp=35;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.FLYING;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case HARDEN:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.RAISE_STAT;
					break;
				case HAZE:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.ICE;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case HEADBUTT:
					move[i].accuracy=100;
					move[i].basePower=70;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.SUBSTATUS;
					move[i].sideEffectAcc=30;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].substatus=Substatus.FLINCH;
					break;
				case HI_JUMP_KICK:
					move[i].accuracy=90;
					move[i].basePower=85;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.RECOIL;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.FIGHTING;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case HORN_ATTACK:
					move[i].accuracy=100;
					move[i].basePower=65;
					move[i].pp=25;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case HORN_DRILL:
					move[i].accuracy=30;
					move[i].basePower=0;
					move[i].pp=5;
					move[i].sideEffect=Side_Effect.OHKO;
					move[i].sideEffectAcc=30;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case HYDRO_PUMP:
					move[i].accuracy=80;
					move[i].basePower=120;
					move[i].pp=5;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.WATER;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case HYPER_BEAM:
					move[i].accuracy=90;
					move[i].basePower=150;
					move[i].pp=5;
					move[i].sideEffect=Side_Effect.CHARGE;
					move[i].sideEffectAcc=90;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case HYPER_FANG:
					move[i].accuracy=90;
					move[i].basePower=80;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.SUBSTATUS;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].substatus=Substatus.FLINCH;
					break;
				case HYPNOSIS:
					move[i].accuracy=60;
					move[i].basePower=0;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=60;
					move[i].moveType=Type.PSYCHIC;
					move[i].mainEffect=Primary_Effect.INFLICT_STATUS;
					move[i].status=Status.SLP;
					break;
				case ICE_BEAM:
					move[i].accuracy=100;
					move[i].basePower=95;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.ICE;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.FRZ;
					break;
				case ICE_PUNCH:
					move[i].accuracy=100;
					move[i].basePower=75;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.ICE;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.FRZ;
					break;
				case JUMP_KICK:
					move[i].accuracy=95;
					move[i].basePower=70;
					move[i].pp=25;
					move[i].sideEffect=Side_Effect.RECOIL;
					move[i].sideEffectAcc=95;
					move[i].moveType=Type.FIGHTING;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case KARATE_CHOP:
					move[i].accuracy=100;
					move[i].basePower=50;
					move[i].pp=25;
					move[i].sideEffect=Side_Effect.HIGH_CRIT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.FIGHTING;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case KINESIS:
					move[i].accuracy=80;
					move[i].basePower=0;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=80;
					move[i].moveType=Type.PSYCHIC;
					move[i].mainEffect=Primary_Effect.LOWER_STAT;
					break;
				case LEECH_LIFE:
					move[i].accuracy=100;
					move[i].basePower=20;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.ABSORB;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.BUG;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].substatus=Substatus.SEED;
					break;
				case LEECH_SEED:
					move[i].accuracy=90;
					move[i].basePower=0;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.SUBSTATUS;
					move[i].sideEffectAcc=90;
					move[i].moveType=Type.GRASS;
					move[i].mainEffect=Primary_Effect.INFLICT_STATUS;
					move[i].substatus=Substatus.SEED;
					break;
				case LEER:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.LOWER_STAT;
					break;
				case LICK:
					move[i].accuracy=100;
					move[i].basePower=20;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=30;
					move[i].moveType=Type.GHOST;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.PAR;
					break;
				case LIGHT_SCREEN:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.PSYCHIC;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case LOVELY_KISS:
					move[i].accuracy=75;
					move[i].basePower=0;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=75;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.INFLICT_STATUS;
					move[i].status=Status.SLP;
					break;
				case LOW_KICK:
					move[i].accuracy=90;
					move[i].basePower=50;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.FIGHTING;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case MEDITATE:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=40;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.PSYCHIC;
					move[i].mainEffect=Primary_Effect.RAISE_STAT;
					break;
				case MEGA_DRAIN:
					move[i].accuracy=100;
					move[i].basePower=40;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.ABSORB;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.GRASS;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case MEGA_KICK:
					move[i].accuracy=75;
					move[i].basePower=120;
					move[i].pp=5;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=75;
					move[i].moveType=Type.FIGHTING;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case MEGA_PUNCH:
					move[i].accuracy=85;
					move[i].basePower=80;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=85;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case METRONOME:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.METRONOME;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case MIMIC:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.MIMIC;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case MINIMIZE:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.RAISE_STAT;
					break;
				case MIRROR_MOVE:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.MIRROR_MOVE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.FLYING;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case MIST:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.MULTI_TURN;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.ICE;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case NIGHT_SHADE:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.FIXED_DAMAGE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.GHOST;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case PAY_DAY:
					move[i].accuracy=100;
					move[i].basePower=40;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case PECK:
					move[i].accuracy=100;
					move[i].basePower=35;
					move[i].pp=35;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.FLYING;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case PETAL_DANCE:
					move[i].accuracy=100;
					move[i].basePower=70;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.FURY;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.GRASS;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case PIN_MISSILE:
					move[i].accuracy=85;
					move[i].basePower=14;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.MULTI_HIT;
					move[i].sideEffectAcc=85;
					move[i].moveType=Type.BUG;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case POISON_GAS:
					move[i].accuracy=55;
					move[i].basePower=0;
					move[i].pp=40;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=55;
					move[i].moveType=Type.POISON;
					move[i].mainEffect=Primary_Effect.INFLICT_STATUS;
					move[i].status=Status.PSN;
					break;
				case POISON_STING:
					move[i].accuracy=100;
					move[i].basePower=15;
					move[i].pp=35;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=20;
					move[i].moveType=Type.POISON;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.PSN;
					break;
				case POISONPOWDER:
					move[i].accuracy=75;
					move[i].basePower=0;
					move[i].pp=35;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=75;
					move[i].moveType=Type.POISON;
					move[i].mainEffect=Primary_Effect.INFLICT_STATUS;
					move[i].status=Status.PSN;
					break;
				case POUND:
					move[i].accuracy=100;
					move[i].basePower=40;
					move[i].pp=35;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case PSYBEAM:
					move[i].accuracy=100;
					move[i].basePower=65;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.SUBSTATUS;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.PSYCHIC;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].substatus=Substatus.CONFU;
					break;
				case PSYCHIC:
					move[i].accuracy=100;
					move[i].basePower=90;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=30;
					move[i].moveType=Type.PSYCHIC;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case PSYWAVE:
					move[i].accuracy=80;
					move[i].basePower=0;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.FIXED_DAMAGE;
					move[i].sideEffectAcc=80;
					move[i].moveType=Type.PSYCHIC;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case QUICK_ATTACK:
					move[i].accuracy=100;
					move[i].basePower=40;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.QUICK_ATTACK;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case RAGE:
					move[i].accuracy=100;
					move[i].basePower=20;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.FURY;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case RAZOR_LEAF:
					move[i].accuracy=95;
					move[i].basePower=55;
					move[i].pp=25;
					move[i].sideEffect=Side_Effect.HIGH_CRIT;
					move[i].sideEffectAcc=95;
					move[i].moveType=Type.GRASS;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case RAZOR_WIND:
					move[i].accuracy=75;
					move[i].basePower=80;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.CHARGE;
					move[i].sideEffectAcc=75;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case RECOVER:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.RECOVERY;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case REFLECT:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.PSYCHIC;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case REST:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.RECOVERY;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case ROAR:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.ROAR;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case ROCK_SLIDE:
					move[i].accuracy=90;
					move[i].basePower=75;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.ROCK;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case ROCK_THROW:
					move[i].accuracy=90;
					move[i].basePower=50;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case ROLLING_KICK:
					move[i].accuracy=85;
					move[i].basePower=60;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.SUBSTATUS;
					move[i].sideEffectAcc=30;
					move[i].moveType=Type.FIGHTING;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].substatus=Substatus.FLINCH;
					break;
				case SAND_ATTACK:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.GROUND;
					move[i].mainEffect=Primary_Effect.LOWER_STAT;
					break;
				case SCRATCH:
					move[i].accuracy=100;
					move[i].basePower=40;
					move[i].pp=35;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case SCREECH:
					move[i].accuracy=85;
					move[i].basePower=0;
					move[i].pp=40;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=85;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.LOWER_STAT;
					break;
				case SEISMIC_TOSS:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.FIXED_DAMAGE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.FIGHTING;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case SELFDESTRUCT:
					move[i].accuracy=100;
					move[i].basePower=130;
					move[i].pp=5;
					move[i].sideEffect=Side_Effect.FAINT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case SHARPEN:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.RAISE_STAT;
					break;
				case SING:
					move[i].accuracy=55;
					move[i].basePower=0;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=55;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.INFLICT_STATUS;
					move[i].status=Status.SLP;
					break;
				case SKULL_BASH:
					move[i].accuracy=100;
					move[i].basePower=100;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.CHARGE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case SKY_ATTACK:
					move[i].accuracy=90;
					move[i].basePower=140;
					move[i].pp=5;
					move[i].sideEffect=Side_Effect.CHARGE;
					move[i].sideEffectAcc=90;
					move[i].moveType=Type.FLYING;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case SLAM:
					move[i].accuracy=80;
					move[i].basePower=75;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=80;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case SLASH:
					move[i].accuracy=100;
					move[i].basePower=70;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.HIGH_CRIT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case SLEEP_POWDER:
					move[i].accuracy=75;
					move[i].basePower=0;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=75;
					move[i].moveType=Type.GRASS;
					move[i].mainEffect=Primary_Effect.INFLICT_STATUS;
					move[i].status=Status.SLP;
					break;
				case SLUDGE:
					move[i].accuracy=100;
					move[i].basePower=65;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=30;
					move[i].moveType=Type.POISON;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.PSN;
					break;
				case SMOG:
					move[i].accuracy=70;
					move[i].basePower=20;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=40;
					move[i].moveType=Type.POISON;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.PSN;
					break;
				case SMOKESCREEN:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.LOWER_STAT;
					break;
				case SOFTBOILED:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.RECOVERY;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case SOLARBEAM:
					move[i].accuracy=100;
					move[i].basePower=120;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.CHARGE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.GRASS;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case SONIC_BOOM:
					move[i].accuracy=90;
					move[i].basePower=20;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.FIXED_DAMAGE;
					move[i].sideEffectAcc=90;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case SPIKE_CANNON:
					move[i].accuracy=100;
					move[i].basePower=20;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.MULTI_HIT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case SPLASH:
					move[i].accuracy=0;
					move[i].basePower=0;
					move[i].pp=40;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=0;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case SPORE:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.GRASS;
					move[i].mainEffect=Primary_Effect.INFLICT_STATUS;
					move[i].status=Status.SLP;
					break;
				case STOMP:
					move[i].accuracy=100;
					move[i].basePower=65;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.SUBSTATUS;
					move[i].sideEffectAcc=30;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].substatus=Substatus.FLINCH;
					break;
				case STRENGTH:
					move[i].accuracy=100;
					move[i].basePower=80;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case STRING_SHOT:
					move[i].accuracy=95;
					move[i].basePower=0;
					move[i].pp=40;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=95;
					move[i].moveType=Type.BUG;
					move[i].mainEffect=Primary_Effect.LOWER_STAT;
					break;
				case NONE:
				case STRUGGLE:
					move[i].accuracy=100;
					move[i].basePower=50;
					move[i].pp=256;
					move[i].sideEffect=Side_Effect.RECOIL;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case STUN_SPORE:
					move[i].accuracy=75;
					move[i].basePower=0;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=75;
					move[i].moveType=Type.GRASS;
					move[i].mainEffect=Primary_Effect.INFLICT_STATUS;
					move[i].status=Status.PAR;
					break;
				case SUBMISSION:
					move[i].accuracy=80;
					move[i].basePower=80;
					move[i].pp=25;
					move[i].sideEffect=Side_Effect.RECOIL;
					move[i].sideEffectAcc=80;
					move[i].moveType=Type.FIGHTING;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case SUBSTITUTE:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.SUBSTITUTE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case SUPER_FANG:
					move[i].accuracy=90;
					move[i].basePower=0;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.FIXED_DAMAGE;
					move[i].sideEffectAcc=90;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case SUPERSONIC:
					move[i].accuracy=55;
					move[i].basePower=0;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.SUBSTATUS;
					move[i].sideEffectAcc=55;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.INFLICT_STATUS;
					move[i].substatus=Substatus.CONFU;
					break;
				case SURF:
					move[i].accuracy=100;
					move[i].basePower=95;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.WATER;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case SWIFT:
					move[i].accuracy=100;
					move[i].basePower=60;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.SWIFT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case SWORDS_DANCE:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.RAISE_STAT;
					break;
				case TAIL_WHIP:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.LOWER_STAT;
					break;
				case TAKE_DOWN:
					move[i].accuracy=85;
					move[i].basePower=90;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.RECOIL;
					move[i].sideEffectAcc=85;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case TELEPORT:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.PSYCHIC;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case THRASH:
					move[i].accuracy=100;
					move[i].basePower=90;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.FURY;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case THUNDER:
					move[i].accuracy=70;
					move[i].basePower=120;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.ELECTRIC;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.PAR;
					break;
				case THUNDER_WAVE:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.ELECTRIC;
					move[i].mainEffect=Primary_Effect.INFLICT_STATUS;
					move[i].status=Status.PAR;
					break;
				case THUNDERBOLT:
					move[i].accuracy=100;
					move[i].basePower=95;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.ELECTRIC;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.PAR;
					break;
				case THUNDERPUNCH:
					move[i].accuracy=100;
					move[i].basePower=75;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.ELECTRIC;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.PAR;
					break;
				case THUNDERSHOCK:
					move[i].accuracy=100;
					move[i].basePower=40;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=10;
					move[i].moveType=Type.ELECTRIC;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					move[i].status=Status.PAR;
					break;
				case TOXIC:
					move[i].accuracy=85;
					move[i].basePower=0;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.STATUS;
					move[i].sideEffectAcc=85;
					move[i].moveType=Type.POISON;
					move[i].mainEffect=Primary_Effect.INFLICT_STATUS;
					move[i].status=Status.PSN;
					break;
				case TRANSFORM:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.TRANSFORM;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case TRI_ATTACK:
					move[i].accuracy=100;
					move[i].basePower=80;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case TWINEEDLE:
					move[i].accuracy=100;
					move[i].basePower=25;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.MULTI_HIT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.BUG;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case VICEGRIP:
					move[i].accuracy=100;
					move[i].basePower=55;
					move[i].pp=30;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case VINE_WHIP:
					move[i].accuracy=100;
					move[i].basePower=35;
					move[i].pp=10;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.GRASS;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case WATER_GUN:
					move[i].accuracy=100;
					move[i].basePower=40;
					move[i].pp=25;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.WATER;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case WATERFALL:
					move[i].accuracy=100;
					move[i].basePower=80;
					move[i].pp=15;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.WATER;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case WHIRLWIND:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.ROAR;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.SPECIAL;
					break;
				case WING_ATTACK:
					move[i].accuracy=100;
					move[i].basePower=35;
					move[i].pp=35;
					move[i].sideEffect=Side_Effect.NONE;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.FLYING;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
				case WITHDRAW:
					move[i].accuracy=100;
					move[i].basePower=0;
					move[i].pp=40;
					move[i].sideEffect=Side_Effect.STAT;
					move[i].sideEffectAcc=100;
					move[i].moveType=Type.WATER;
					move[i].mainEffect=Primary_Effect.RAISE_STAT;
					break;
				case WRAP:
					move[i].accuracy=85;
					move[i].basePower=15;
					move[i].pp=20;
					move[i].sideEffect=Side_Effect.MULTI_TURN;
					move[i].sideEffectAcc=85;
					move[i].moveType=Type.NORMAL;
					move[i].mainEffect=Primary_Effect.DAMAGE;
					break;
			}

			move[i].ppMax=move[i].pp;
			TRUE_PP[i]=move[i].pp;
			TRUE_PPMAX[i]=move[i].ppMax;
		}

		System.out.println(nickname+"'s moves created successfully");
	}
}