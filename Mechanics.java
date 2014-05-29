////////////////////////
//Mechanics Static Class
//Written by Camtendo and Justinian
//
//
//Players in a battle are referred to by integers
//0=user
//1=enemy
//

import java.util.ArrayList;

public final class Mechanics
{
	public static enum Effective
	{
		NORMAL,DOUBLE,HALF,NONE
	}

	public static Effective effective=Effective.NORMAL;

	static int damage=0;
	static int expYield=0;
	static boolean participatedInBattle[]=new boolean[6];

	static int storedDamage[]=new int[2];
	static boolean isHighCritical[]=new boolean[2];
	static boolean charging[]=new boolean[2];
	static int turnsMultiTurn[]=new int[2];
	static int turnsAsleep[]=new int[2];
	static int turnsMist[]=new int[2];
	static int turnsConfused[]=new int[2];
	static boolean isConfused[]=new boolean[2];
	static int turnsDisabled[]=new int[2];
	static int turnsLightScreen[]=new int[2];
	static int turnsReflect[]=new int[2];
	static int moveDisabled[]=new int[2];
	static int substituteHealth[]=new int[2];
	static boolean hasSubstitute[]=new boolean[2];
	static boolean canAttack[]=new boolean[2];
	static boolean awayFromBattle[]=new boolean[2];

	//Initializes necessary variables
	public static void initialize()
	{
		for(int i=0; i<2; i++)
		{
			moveDisabled[i]=-1;
			storedDamage[i]=0;
			charging[i]=false;
			turnsMultiTurn[i]=0;
			turnsMist[i]=0;
			turnsConfused[i]=0;
			isConfused[i]=false;
			turnsDisabled[i]=0;
			turnsLightScreen[i]=0;
			turnsReflect[i]=0;
			substituteHealth[i]=0;
			hasSubstitute[i]=false;
			canAttack[i]=true;
			awayFromBattle[i]=false;
		}

		participationReset();
	}

	//Resets Participation booleans
	public static void participationReset()
	{
		participatedInBattle[0]=false;
		participatedInBattle[1]=false;
		participatedInBattle[2]=false;
		participatedInBattle[3]=false;
		participatedInBattle[4]=false;
		participatedInBattle[5]=false;
	}

	//Sets damage as an integer
	public static void calcDamage(int base_power, int lvl, int atk, int def, int target)
	{
		//TAS_ARCHIVES QUICK_DAMAGE ALGORITHM
		damage=(int)((0.4*lvl+2)*atk*base_power/(def*50)+2);
		storedDamage[target]+=damage;
	}

	//Returns if a Pokemon is deemed Legendary
	public static boolean isLegendary(Pokemon p)
	{
		switch(p.species)
		{
			case SNORLAX:
			case DRAGONITE:
			case ARTICUNO:
			case ZAPDOS:
			case MOLTRES:
			case MEWTWO:
			case MEW:
				return true;
			default:
				return false;
		}
	}

	//Returns if a person is worthy of a large text box or not.
	public static boolean isSpecialTrainer(Trainer t)
	{
		switch(t.type)
		{
			case JAVA:
				if(t.name.equals("BOSS"))
					return true;
				else
					return false;
			case BABB:
			case ELITE:
			case GYM_LEADER:
			case RIVAL:
				return true;
			default:
				return false;
		}
	}

	//Returns if Pokemon is caught. Takes in Pokemon to be caught and the ball being used
	public static boolean caughtPokemon(Pokemon p, Item item)
	{
		int catchRate=256;

		//Catch Rate Algorithm (Camtendo)
		//CR=256-(255(Base Stats/590.0))
		catchRate=(int)(256-(255*(p.getBaseStatTotal()/590.0)));
		//CR+=76(1-((HP/HPMAX)(LVL/100))
		catchRate+=76*(1-(((double)p.health/(double)p.healthMax)*(p.level/5.0)));
		//Mewtwo and Mew's Catch Rates are always 1.
		if(p.species==Pokemon.Species.MEW||p.species==Pokemon.Species.MEWTWO||catchRate<1)
			catchRate=1;
		if (p.species==Pokemon.Species.MEWTWO && p.nickname.equalsIgnoreCase("Mewtrix"))
			catchRate=0;
		//CR Multipliers
		//Status
		switch(p.status)
		{
			case SLP:
			case FRZ:
				catchRate*=1.5;
				break;
			case PAR:
				catchRate*=1.25;
				break;
			case BRN:
			case PSN:
				catchRate*=1.1;
				break;
			default:
				break;
		}

		//Ball Type
		switch(item.type)
		{
			case POKE_BALL:
				catchRate*=.75;
				break;
			case GREAT_BALL:
				catchRate*=1.5;
				break;
			case ULTRA_BALL:
				catchRate*=3;
				break;
			case MASTER_BALL:
			case OMEGA_BALL:
				catchRate*=256;
				break;
		}

		int randy=(int)(Math.random()*256);

		System.out.println("Odds of Catching: "+randy);
		System.out.println("Pokemon's Catch Rate: "+catchRate);

		return randy<catchRate;
	}

	//Returns the highest level Pokemon you have
	public static int getHighestLevel(Pokemon party[], Pokemon pc[])
	{
		int highest=0;

		for(int i=0; i<party.length; i++)
		{
			if(party[i]!=null)
			{
				if(party[i].level>highest)
					highest=party[i].level;
			}
			else
				break;
		}

		for(int i=0; i<pc.length; i++)
		{
			if(pc[i]!=null)
			{
				if(pc[i].level>highest)
					highest=pc[i].level;
			}
			else
				return highest;
		}

		return highest;
	}

	//Returns a Pokemon given a 5 species set, rarities as integers, and levels also as integers.
	//Preferably Rarities must sum to 100 and be greater than 5 or else the algorithm will break
	public static Pokemon randomEncounter(Pokemon.Species s1, int rare1, int lvl1, Pokemon.Species s2, int rare2, int lvl2,
		Pokemon.Species s3, int rare3, int lvl3,
	    Pokemon.Species s4, int rare4, int lvl4, Pokemon.Species s5, int rare5, int lvl5)
	{
		int randy=(int)(Math.random()*100)+1;

		if(randy<=rare1)
		{
			return new Pokemon(s1,lvl1);
		}
		else if(randy<=rare1+rare2)
		{
			return new Pokemon(s2,lvl2);
		}
		else if(randy<=rare1+rare2+rare3)
		{
			return new Pokemon(s3,lvl3);
		}
		else if(randy<=rare1+rare2+rare3+rare4)
		{
			return new Pokemon(s4,lvl4);
		}
		else
		{
			return new Pokemon(s5,lvl5);
		}

	}


	//Returns true if the pokemon has the move
	public static boolean hasMove(Pokemon pokemon, Pokemon.Move move)
	{
		boolean has = false;
		for (int i = 0; i<4; i++)
		{
			if (pokemon.move[i] == move)
			has = true;
		}
		return has;
	}

	//Checks party for HM01 Cut
	public static boolean hasCut(Pokemon[] pokemon)
	{
		for(int i=0; i<pokemon.length; i++)
		{
			if(pokemon[i]!=null)
			{
				for(int j=0; j<4; j++)
				{
					if(pokemon[i].move[j]==Pokemon.Move.CUT)
						return true;
				}
			}
		}

		return false;
	}

	//Checks party for HM02 Fly
	public static boolean hasFly(Pokemon[] pokemon)
	{
		for(int i=0; i<pokemon.length; i++)
		{
			if(pokemon[i]!=null)
			{
				for(int j=0; j<4; j++)
				{
					if(pokemon[i].move[j]==Pokemon.Move.FLY)
						return true;
				}
			}
		}

		return false;
	}

	//Checks party for HM03 Surf
	public static boolean hasSurf(Pokemon[] pokemon)
	{
		for(int i=0; i<pokemon.length; i++)
		{
			if(pokemon[i]!=null)
			{
				for(int j=0; j<4; j++)
				{
					if(pokemon[i].move[j]==Pokemon.Move.SURF)
						return true;
				}
			}
		}

		return false;
	}

	//Checks party for HM04 Strength
	public static boolean hasStrength(Pokemon[] pokemon)
	{
		for(int i=0; i<pokemon.length; i++)
		{
			if(pokemon[i]!=null)
			{
				for(int j=0; j<4; j++)
				{
					if(pokemon[i].move[j]==Pokemon.Move.STRENGTH)
						return true;
				}
			}
		}

		return false;
	}

	//Checks party for HM05 Flash
	public static boolean hasFlash(Pokemon[] pokemon)
	{
		for(int i=0; i<pokemon.length; i++)
		{
			if(pokemon[i]!=null)
			{
				for(int j=0; j<4; j++)
				{
					if(pokemon[i].move[j]==Pokemon.Move.CUT)
						return true;
				}
			}
		}

		return false;
	}

	//Creates a new Substitute
	public static void createSubstitute(int health,int target)
	{
		substituteHealth[target]=health;
		hasSubstitute[target]=true;
	}

	//Returns the possibility of creating a Substitute
	public static boolean canCreateSubstitute(Pokemon p)
	{
		return p.health>p.healthMax/4;
	}

	//Calculates Exp yielded given the defeated Pokemon
	public static void calcExp(Pokemon dead)
	{
		//(Level*Total_Base_Stats)/10=Exp
		expYield=(dead.level*dead.getBaseStatTotal())/8;
	}

	//Calculates a multiplier to make weaker Pokemon level up faster
	public static double getExpScale(Pokemon p)
	{
		return 590.0/p.getBaseStatTotal();
	}

	//Returns health recovery as an int
	public static int recover(Pokemon p, int amount)
	{
		p.health+=amount;
		if(p.health>p.healthMax)
			p.health=p.healthMax;
		return p.health;
	}

	//Checks for a critical hit and returns the boolean. Target in case is receiving damage
	public static boolean checkCritical(int speed, int target)
	{
		int check=(int)(Math.random()*100)+1;

		if(!isHighCritical[target])
			return check<(speed*100)/512;
		else
		{
			isHighCritical[target]=false;
			return check<(speed*100)/64;
		}
	}

	//Enacts the Smogon Sleep Clause(1) when a Pokemon is found to be asleep
	public static boolean sleepClauseActive(Pokemon[] p, int pokemonAvailable)
	{
		for(int i=0; i<pokemonAvailable; i++)
		{
			if(p[i].status==Pokemon.Status.SLP)
				return true;
		}

		return false;
	}

	//Checks if a move hit. Gets Pokemon and a move index and Enemy's Evasion
	public static boolean checkHit(Pokemon p, int index, double evade)
	{
		int randy=(int)(Math.random()*100);

		return randy<(p.move[index].accuracy*stageMultiplier(p.accuracyStage))/evade;
	}

	//Checks if a side effect hit
	public static boolean checkSideEffect(Pokemon.Move m)
	{
		int randy=(int)(Math.random()*100);

		return randy<m.sideEffectAcc;
	}

	//Checks if a Pokemon has any pp for any move
	public static boolean hasPPAtAll(Pokemon p)
	{
		for(int i=0; i<4; i++)
		{
			if(p.move[i]!=Pokemon.Move.NONE)
			{
				if(hasPP(p.move[i]))
				{
					return true;
				}
			}
		}

		return false;
	}

	//Returns the new calculation of a Pokemon's HP stat
	public static int calcHPStat(int base, int iv, int ev, int lvl)
	{
		return (int)((2*base+iv+(ev/255)/4)*(lvl/100.0)+lvl+10);
	}

	//Returns the new calculation of a Pokemon's non-HP stat
	public static int calcOtherStat(int base, int iv, int ev, int lvl)
	{
		return (int)(((2*base+iv+(ev/255)/4)*(lvl/100.0)+5)*1.1);
	}

	//Returns the Life Status of a Pokemon
	public static boolean isAlive(int hp)
	{
		return hp>0;
	}

	//Keeps Battle Loop Going
	public static boolean hasRemainingPokemon(Pokemon[] p, int numOfPokemon)
	{
		for(int i=0; i<numOfPokemon; i++)
		{
			if(isAlive(p[i].health))
			{
				return true;
			}
		}
		return false;
	}

	//Returns if a Pokemon has PP for a move
	public static boolean hasPP(Pokemon.Move m)
	{
		return m.pp>0;
	}

	//Returns if a Pokemon has PP for a move
	public static boolean enemyHasPP(Pokemon.Move m)
	{
		return m.pp>1;
	}

	//Returns number of Pokemon Remaining
	public static int remainingPokemon(Pokemon[] p, int numOfPokemon)
	{
		int temp=0;

		for(int i=0; i<numOfPokemon; i++)
		{
			if(isAlive(p[i].health))
			{
				temp++;
			}
		}
		return temp;
	}

	//Returns the value to be executed first of two Pokemon (0 being user, 1 being enemy)
	//Takes in Pokemon and index of move to be used on both sides
	public static int determinePriority(Pokemon u, int userInt, Pokemon e, int enemyInt)
	{
		if(userInt==4||userInt<0)
			return 0;
		else if(enemyInt==4||enemyInt<0)
			return 1;

		if(u.status==Pokemon.Status.PAR)
		{
			if(e.status!=Pokemon.Status.PAR)
			{
				if(u.move[userInt]==Pokemon.Move.QUICK_ATTACK&&e.move[userInt]!=Pokemon.Move.QUICK_ATTACK)
					return 0;
				else
					return 1;
			}
			else
			{
				if(u.speed>=e.speed)
				{
					if(u.move[userInt]!=Pokemon.Move.COUNTER)
					{
						if(e.move[enemyInt]==Pokemon.Move.QUICK_ATTACK)
						{
							if(u.move[userInt]!=Pokemon.Move.QUICK_ATTACK)
								return 1;
							else
								return 0;
						}
						else
							return 0;
					}
					else
						return 1;
				}
				else
				{
					if(e.move[enemyInt]!=Pokemon.Move.COUNTER)
					{
						if(u.move[userInt]==Pokemon.Move.QUICK_ATTACK)
						{
							if(e.move[enemyInt]!=Pokemon.Move.QUICK_ATTACK)
								return 0;
							else
								return 1;
						}
						else
							return 1;
					}
					else
						return 0;
				}
			}
		}
		else if(e.status==Pokemon.Status.PAR)
		{
			if(u.status!=Pokemon.Status.PAR)
			{
				if(e.move[enemyInt]==Pokemon.Move.QUICK_ATTACK&&u.move[userInt]!=Pokemon.Move.QUICK_ATTACK)
					return 1;
				else
					return 0;
			}
			else
			{
				if(u.speed>=e.speed)
				{
					if(u.move[userInt]!=Pokemon.Move.COUNTER)
					{
						if(e.move[enemyInt]==Pokemon.Move.QUICK_ATTACK)
						{
							if(u.move[userInt]!=Pokemon.Move.QUICK_ATTACK)
								return 1;
							else
								return 0;
						}
						else
							return 0;
					}
					else
						return 1;
				}
				else
				{
					if(e.move[enemyInt]!=Pokemon.Move.COUNTER)
					{
						if(u.move[userInt]==Pokemon.Move.QUICK_ATTACK)
						{
							if(e.move[enemyInt]!=Pokemon.Move.QUICK_ATTACK)
								return 0;
							else
								return 1;
						}
						else
							return 1;
					}
					else
						return 0;
				}
			}
		}
		else if(u.speed>=e.speed)
		{
			if(u.move[userInt]!=Pokemon.Move.COUNTER)
			{
				if(e.move[enemyInt]==Pokemon.Move.QUICK_ATTACK)
				{
					if(u.move[userInt]!=Pokemon.Move.QUICK_ATTACK)
						return 1;
					else
						return 0;
				}
				else
					return 0;
			}
			else
				return 1;
		}
		else
		{
			if(e.move[enemyInt]!=Pokemon.Move.COUNTER)
			{
				if(u.move[userInt]==Pokemon.Move.QUICK_ATTACK)
				{
					if(e.move[enemyInt]!=Pokemon.Move.QUICK_ATTACK)
						return 0;
					else
						return 1;
				}
				else
					return 1;
			}
			else
				return 0;
		}
	}

	//Returns a multiplier to apply to a stat given a stage
	public static double stageMultiplier(int stagee)
	{
		int stage=0;

		stage=checkStage(stagee);

		switch(stage)
		{
			case 6:
				return 4;
			case 5:
				return 3.5;
			case 4:
				return 3;
			case 3:
				return 2.5;
			case 2:
				return 2;
			case 1:
				return 1.5;
			case 0:
				return 1;
			case -1:
				return 2.0/3.0;
			case -2:
				return 0.5;
			case -3:
				return 0.4;
			case -4:
				return 1.0/3.0;
			case -5:
				return 2.0/7.0;
			case -6:
				return 0.25;
			default:
				return 1;
		}
	}

	//Makes sure that all stages are within [-6,6]
	public static int checkStage(int stage)
	{
		if(stage>6)
			return 6;
		else if(stage<-6)
			return -6;
		else
			return stage;
	}

	//Returns if a move has STAB or not
	public static boolean isSTAB(Pokemon.Type type1, Pokemon.Type type2, Pokemon.Type move)
	{
		if(type1==move)
			return true;
		else if(type2==move)
			return true;
		else
			return false;
	}

	//Checks if the move type is special or not
	public static boolean isSpecial(Pokemon.Type t)
	{
		switch(t)
		{
			case FIRE:
			case WATER:
			case ELECTRIC:
			case GRASS:
			case ICE:
			case PSYCHIC:
			case DRAGON:
				return true;
			default:
				return false;
		}
	}

	//Writes the String of the Multiplier
	public static String multiplierToString(double d)
	{
		if(d==0)
			return "It had no effect!";
		else if(d<1.0)
			return "It's not very effective...";
		else if(d>1.0)
			return "It's Super Effective!";

		return "The Attack Hit!";
	}

	//Returns the multiplier after factoring in the type of a move against the target's types
	public static double typeMultiplier(Pokemon.Type move, Pokemon.Type t1, Pokemon.Type t2)
	{
		double multiplier=1;

		switch(move)
		{
			case BUG:
				switch(t1)
				{
					case FIGHTING:
					case FIRE:
					case FLYING:
						multiplier*=0.5;
						effective=Effective.HALF;
						break;
					case GRASS:
					case POISON:
					case PSYCHIC:
						multiplier*=2;
						effective=Effective.DOUBLE;
						break;
					default:
						effective=Effective.NORMAL;
						break;
				}
				switch(t2)
				{
					case FIGHTING:
					case FIRE:
					case FLYING:
						multiplier*=0.5;
						if(effective==Effective.DOUBLE)
							effective=Effective.NORMAL;
						else
							effective=Effective.HALF;
						break;
					case GRASS:
					case POISON:
					case PSYCHIC:
						multiplier*=2;
						if(effective==Effective.HALF)
							effective=Effective.NORMAL;
						else
							effective=Effective.DOUBLE;
						break;
					default:
						break;
				}
				break;
			case DRAGON:
				switch(t1)
				{
					case DRAGON:
						multiplier*=2;
						effective=Effective.DOUBLE;
						break;
					default:
						effective=Effective.NORMAL;
						break;
				}
				switch(t2)
				{
					case DRAGON:
						multiplier*=2;
						effective=Effective.DOUBLE;
						break;
					default:
						break;
				}
				break;
			case ELECTRIC:
				switch(t1)
				{
					case DRAGON:
					case ELECTRIC:
					case GRASS:
						multiplier*=0.5;
						effective=Effective.HALF;
						break;
					case FLYING:
					case WATER:
						multiplier*=2;
						effective=Effective.DOUBLE;
						break;
					case GROUND:
						multiplier=0;
						effective=Effective.NONE;
					default:
						effective=Effective.NORMAL;
						break;
				}
				switch(t2)
				{
					case DRAGON:
					case ELECTRIC:
					case GRASS:
						multiplier*=0.5;
						if(effective==Effective.DOUBLE)
							effective=Effective.NORMAL;
						else if (effective!=Effective.NONE)
							effective=Effective.HALF;
						break;
					case FLYING:
					case WATER:
						multiplier*=2;
						if(effective==Effective.HALF)
							effective=Effective.NORMAL;
						else if (effective!=Effective.NONE)
							effective=Effective.DOUBLE;
						break;
					case GROUND:
						multiplier=0;
						effective=Effective.NONE;
					default:
						break;
				}
				break;
			case FIGHTING:
				switch(t1)
				{
					case BUG:
					case FLYING:
					case POISON:
					case PSYCHIC:
						multiplier*=0.5;
						effective=Effective.HALF;
						break;
					case ICE:
					case NORMAL:
					case ROCK:
						multiplier*=2;
						effective=Effective.DOUBLE;
						break;
					case GHOST:
						multiplier=0;
						effective=Effective.NONE;
					default:
						effective=Effective.NORMAL;
						break;
				}
				switch(t2)
				{
					case BUG:
					case FLYING:
					case POISON:
					case PSYCHIC:
						multiplier*=0.5;
						if(effective==Effective.DOUBLE)
							effective=Effective.NORMAL;
						else if (effective!=Effective.NONE)
							effective=Effective.HALF;
						break;
					case ICE:
					case NORMAL:
					case ROCK:
						multiplier*=2;
						if(effective==Effective.HALF)
							effective=Effective.NORMAL;
						else if (effective!=Effective.NONE)
							effective=Effective.DOUBLE;
						break;
					case GHOST:
						multiplier=0;
						effective=Effective.NONE;
					default:
						break;
				}
				break;
			case FIRE:
				switch(t1)
				{
					case DRAGON:
					case FIRE:
					case ROCK:
					case WATER:
						multiplier*=0.5;
						effective=Effective.HALF;
						break;
					case ICE:
					case BUG:
					case GRASS:
						multiplier*=2;
						effective=Effective.DOUBLE;
						break;
					default:
						effective=Effective.NORMAL;
						break;
				}
				switch(t2)
				{
					case DRAGON:
					case FIRE:
					case ROCK:
					case WATER:
						multiplier*=0.5;
						if(effective==Effective.DOUBLE)
							effective=Effective.NORMAL;
						else
							effective=Effective.HALF;
						break;
					case ICE:
					case BUG:
					case GRASS:
						multiplier*=2;
						if(effective==Effective.HALF)
							effective=Effective.NORMAL;
						else
							effective=Effective.DOUBLE;
						break;
					default:
						break;
				}
				break;
			case FLYING:
				switch(t1)
				{
					case BUG:
					case FIGHTING:
					case GRASS:
						multiplier*=2;
						effective=Effective.DOUBLE;
						break;
					case ELECTRIC:
					case ROCK:
						multiplier*=0.5;
						effective=Effective.HALF;
						break;
					default:
						effective=Effective.NORMAL;
						break;

				}
				switch(t2)
				{
					case BUG:
					case FIGHTING:
					case GRASS:
						multiplier*=2;
						if(effective==Effective.HALF)
							effective=Effective.NORMAL;
						else
							effective=Effective.DOUBLE;
						break;
					case ELECTRIC:
					case ROCK:
						multiplier*=0.5;
						if (effective==Effective.DOUBLE)
							effective=Effective.NORMAL;
						else
							effective=Effective.HALF;
						break;
					default:
						break;
				}
				break;
			case GHOST:
				switch(t1)
				{
					case GHOST:
						multiplier*=2;
						effective=Effective.DOUBLE;
						break;
					case NORMAL:
					case PSYCHIC:
						multiplier*=0;
						effective=Effective.NONE;
					default:
						effective=Effective.NORMAL;
						break;
				}
				switch(t2)
				{
					case GHOST:
						multiplier*=2;
						if (effective!=Effective.NONE)
							effective=Effective.DOUBLE;
						break;
					case NORMAL:
					case PSYCHIC:
						multiplier*=0;
						effective=Effective.NONE;
					default:
						break;
				}
				break;
			case GRASS:
				switch(t1)
				{
					case BUG:
					case DRAGON:
					case FIRE:
					case FLYING:
					case GRASS:
					case POISON:
						multiplier*=0.5;
						effective=Effective.HALF;
						break;
					case GROUND:
					case ROCK:
					case WATER:
						multiplier*=2;
						effective=Effective.DOUBLE;
						break;
					default:
						effective=Effective.NORMAL;
						break;

				}
				switch(t2)
				{
					case BUG:
					case DRAGON:
					case FIRE:
					case FLYING:
					case GRASS:
					case POISON:
						multiplier*=0.5;
						if (effective==Effective.DOUBLE)
							effective=Effective.NORMAL;
						else
							effective=Effective.HALF;
						break;
					case GROUND:
					case ROCK:
					case WATER:
						multiplier*=2;
						if (effective==Effective.HALF)
							effective=Effective.NORMAL;
						else
							effective=Effective.DOUBLE;
						break;
					default:
						break;
				}
				break;
			case GROUND:
				switch(t1)
				{
					case BUG:
					case GRASS:
						multiplier*=0.5;
						effective=Effective.HALF;
						break;
					case ELECTRIC:
					case ROCK:
					case FIRE:
					case POISON:
						multiplier*=2;
						effective=Effective.DOUBLE;
						break;
					case FLYING:
						multiplier=0;
						effective=Effective.NONE;
					default:
						effective=Effective.NORMAL;
						break;
				}
				switch(t2)
				{
					case BUG:
					case GRASS:
						multiplier*=0.5;
						effective=Effective.HALF;
						if(effective==Effective.DOUBLE)
							effective=Effective.NORMAL;
						break;
					case ELECTRIC:
					case ROCK:
					case FIRE:
					case POISON:
						multiplier*=2;
						effective=Effective.DOUBLE;
						if(effective==Effective.HALF)
							effective=Effective.NORMAL;
						break;
					case FLYING:
						multiplier=0;
						effective=Effective.NONE;
					default:
						break;
				}
				break;
			case ICE:
				switch(t1)
				{
					case DRAGON:
					case FLYING:
					case GRASS:
					case GROUND:
						multiplier*=2;
						effective=Effective.DOUBLE;
						break;
					case ICE:
					case WATER:
						multiplier*=0.5;
						effective=Effective.HALF;
						break;
					default:
						effective=Effective.NORMAL;
						break;
				}
				switch(t2)
				{
					case DRAGON:
					case FLYING:
					case GRASS:
					case GROUND:
						multiplier*=2;
						if(effective==Effective.HALF)
							effective=Effective.NORMAL;
						else
							effective=Effective.DOUBLE;
						break;
					case ICE:
					case WATER:
						multiplier*=0.5;
						if (effective==Effective.DOUBLE)
							effective=Effective.NORMAL;
						else
							effective=Effective.HALF;
						break;
					default:
						break;
				}
				break;
			case NORMAL:
				switch(t1)
				{
					case GHOST:
						multiplier*=0;
						effective=Effective.NONE;
						break;
					case ROCK:
						multiplier*=0.5;
						effective=Effective.HALF;
						break;
					default:
						effective=Effective.NORMAL;
						break;
				}
				switch(t2)
				{
					case GHOST:
						multiplier*=0;
						effective=Effective.NONE;
						break;
					case ROCK:
						multiplier*=0.5;
						if (effective!=Effective.NONE)
						effective=Effective.HALF;
						break;
					default:
						break;
				}
				break;
			case POISON:
				switch(t1)
				{
					case BUG:
					case GRASS:
						multiplier*=2;
						effective=Effective.DOUBLE;
						break;
					case GHOST:
					case GROUND:
					case POISON:
					case ROCK:
						multiplier*=0.5;
						effective=Effective.HALF;
						break;
					default:
						effective=Effective.NORMAL;
						break;

				}
				switch(t2)
				{
					case BUG:
					case GRASS:
						multiplier*=2;
						if (effective==Effective.HALF)
							effective=Effective.NORMAL;
						else
							effective=Effective.DOUBLE;
						break;
					case GHOST:
					case GROUND:
					case POISON:
					case ROCK:
						multiplier*=0.5;
						if (effective==Effective.DOUBLE)
							effective=Effective.NORMAL;
						else
							effective=Effective.HALF;
						break;
					default:
						break;
				}
				break;
			case PSYCHIC:
				switch(t1)
				{
					case FIGHTING:
					case POISON:
						multiplier*=2;
						effective=Effective.DOUBLE;
						break;
					case PSYCHIC:
						multiplier*=0.5;
						effective=Effective.HALF;
						break;
					default:
						effective=Effective.NORMAL;
						break;
				}
				switch(t2)
				{
					case FIGHTING:
					case POISON:
						multiplier*=2;
						if (effective==Effective.HALF)
							effective=Effective.NORMAL;
						else
							effective=Effective.DOUBLE;
						break;
					case PSYCHIC:
						multiplier*=0.5;
						if (effective==Effective.DOUBLE)
							effective=Effective.NORMAL;
						else
							effective=Effective.HALF;
						break;
					default:
						break;
				}
				break;
			case ROCK:
				switch(t1)
				{
					case BUG:
					case FIRE:
					case FLYING:
					case ICE:
						multiplier*=2.0;
						effective=Effective.DOUBLE;
						break;
					case FIGHTING:
					case GROUND:
						multiplier*=0.5;
						effective=Effective.HALF;
						break;
					default:
						effective=Effective.NORMAL;
						break;
				}
				switch(t2)
				{
					case BUG:
					case FIRE:
					case FLYING:
					case ICE:
						multiplier*=2.0;
						if (effective==Effective.HALF)
							effective=Effective.NORMAL;
						else
							effective=Effective.DOUBLE;
						break;
					case FIGHTING:
					case GROUND:
						multiplier*=0.5;
						if (effective==Effective.DOUBLE)
							effective=Effective.NORMAL;
						else
							effective=Effective.HALF;
						break;
					default:
						break;
				}
				break;
			case WATER:
				switch(t1)
				{
					case DRAGON:
					case GRASS:
					case WATER:
						multiplier*=0.5;
						effective=Effective.HALF;
						break;
					case FIRE:
					case GROUND:
					case ROCK:
						multiplier*=2.0;
						effective=Effective.DOUBLE;
						break;
					default:
						effective=Effective.NORMAL;
						break;
				}
				switch(t2)
				{
					case DRAGON:
					case GRASS:
					case WATER:
						multiplier*=0.5;
						if (effective==Effective.DOUBLE)
							effective=Effective.NORMAL;
						else
							effective=Effective.HALF;
						break;
					case FIRE:
					case GROUND:
					case ROCK:
						multiplier*=2.0;
						if (effective==Effective.HALF)
							effective = Effective.NORMAL;
						else
							effective=Effective.DOUBLE;
						break;
					default:
						break;
				}
				break;
		}

		return multiplier;
	}

	public static Pokemon.Move[] levelUpMove(Pokemon pokemon, boolean evolved)
	{
		ArrayList<Pokemon.Move> movesToLearn = new ArrayList<Pokemon.Move>(30);
		int holdLevel = pokemon.level;
		//Non evolved block
		do
		{
			switch(pokemon.species)
			{
				case BULBASAUR:
					switch(pokemon.level)
					{
						case 48:
							movesToLearn.add(Pokemon.Move.SOLARBEAM);
							break;
						case 41:
							movesToLearn.add(Pokemon.Move.SLEEP_POWDER);
							break;
						case 34:
							movesToLearn.add(Pokemon.Move.GROWTH);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.RAZOR_LEAF);
							break;
						case 20:
							movesToLearn.add(Pokemon.Move.POISONPOWDER);
							break;
						case 13:
							movesToLearn.add(Pokemon.Move.VINE_WHIP);
							break;
						case 7:
							movesToLearn.add(Pokemon.Move.LEECH_SEED);
							break;
					}
					break;
				case IVYSAUR:
					switch(pokemon.level)
					{
						case 54:
							movesToLearn.add(Pokemon.Move.SOLARBEAM);
							break;
						case 46:
							movesToLearn.add(Pokemon.Move.SLEEP_POWDER);
							break;
						case 38:
							movesToLearn.add(Pokemon.Move.GROWTH);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.RAZOR_LEAF);
							break;
						case 22:
							movesToLearn.add(Pokemon.Move.POISONPOWDER);
							break;
					}
					break;
				case VENUSAUR:
					switch(pokemon.level)
					{
						case 65:
							movesToLearn.add(Pokemon.Move.SOLARBEAM);
							break;
						case 55:
							movesToLearn.add(Pokemon.Move.SLEEP_POWDER);
							break;
						case 43:
							movesToLearn.add(Pokemon.Move.GROWTH);
							break;
					}
					break;
				case CHARMANDER:
					switch(pokemon.level)
					{
						case 46:
							movesToLearn.add(Pokemon.Move.FIRE_SPIN);
							break;
						case 38:
							movesToLearn.add(Pokemon.Move.FLAMETHROWER);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.SLASH);
							break;
						case 22:
							movesToLearn.add(Pokemon.Move.RAGE);
							break;
						case 15:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
						case 9:
							movesToLearn.add(Pokemon.Move.EMBER);
							break;
					}
					break;
				case CHARMELEON:
					switch(pokemon.level)
					{
						case 56:
							movesToLearn.add(Pokemon.Move.FIRE_SPIN);
							break;
						case 42:
							movesToLearn.add(Pokemon.Move.FLAMETHROWER);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.SLASH);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.RAGE);
							break;
					}
					break;
				case CHARIZARD:
					switch(pokemon.level)
					{
						case 55:
							movesToLearn.add(Pokemon.Move.FIRE_SPIN);
							break;
						case 46:
							movesToLearn.add(Pokemon.Move.FLAMETHROWER);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.SLASH);
							break;
					}
					break;
				case SQUIRTLE:
					switch(pokemon.level)
					{
						case 42:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 35:
							movesToLearn.add(Pokemon.Move.SKULL_BASH);
							break;
						case 28:
							movesToLearn.add(Pokemon.Move.WITHDRAW);
							break;
						case 22:
							movesToLearn.add(Pokemon.Move.BITE);
							break;
						case 15:
							movesToLearn.add(Pokemon.Move.WATER_GUN);
							break;
						case 8:
							movesToLearn.add(Pokemon.Move.BUBBLE);
							break;
					}
					break;
				case WARTORTLE:
					switch(pokemon.level)
					{
						case 47:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.SKULL_BASH);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.WITHDRAW);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.BITE);
							break;
					}
					break;
				case BLASTOISE:
					switch(pokemon.level)
					{
						case 52:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 42:
							movesToLearn.add(Pokemon.Move.SKULL_BASH);
							break;
					}
					break;
				case BUTTERFREE:
					switch(pokemon.level)
					{
						case 34:
							movesToLearn.add(Pokemon.Move.PSYBEAM);
							break;
						case 23:
							movesToLearn.add(Pokemon.Move.WHIRLWIND);
							break;
						case 18:
							movesToLearn.add(Pokemon.Move.SUPERSONIC);
							break;
						case 15:
							movesToLearn.add(Pokemon.Move.SLEEP_POWDER);
							break;
						case 14:
							movesToLearn.add(Pokemon.Move.STUN_SPORE);
							break;
						case 13:
							movesToLearn.add(Pokemon.Move.POISONPOWDER);
							break;
						case 10:
							movesToLearn.add(Pokemon.Move.CONFUSION);
							break;
					}
					break;
				case BEEDRILL:
					switch(pokemon.level)
					{
						case 35:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.PIN_MISSILE);
							break;
						case 25:
							movesToLearn.add(Pokemon.Move.RAGE);
							break;
						case 20:
							movesToLearn.add(Pokemon.Move.TWINEEDLE);
							break;
						case 16:
							movesToLearn.add(Pokemon.Move.FOCUS_ENERGY);
							break;
						case 12:
							movesToLearn.add(Pokemon.Move.FURY_ATTACK);
							break;
					}
					break;
				case PIDGEY:
					switch(pokemon.level)
					{
						case 44:
							movesToLearn.add(Pokemon.Move.MIRROR_MOVE);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 28:
							movesToLearn.add(Pokemon.Move.WING_ATTACK);
							break;
						case 19:
							movesToLearn.add(Pokemon.Move.WHIRLWIND);
							break;
						case 12:
							movesToLearn.add(Pokemon.Move.QUICK_ATTACK);
							break;
						case 5:
							movesToLearn.add(Pokemon.Move.SAND_ATTACK);
							break;
					}
					break;
				case PIDGEOTTO:
					switch(pokemon.level)
					{
						case 49:
							movesToLearn.add(Pokemon.Move.MIRROR_MOVE);
							break;
						case 40:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.WING_ATTACK);
							break;
						case 21:
							movesToLearn.add(Pokemon.Move.WHIRLWIND);
							break;
					}
					break;
				case PIDGEOT:
					switch(pokemon.level)
					{
						case 54:
							movesToLearn.add(Pokemon.Move.MIRROR_MOVE);
							break;
						case 44:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
					}
					break;
				case RATTATA:
					switch(pokemon.level)
					{
						case 34:
							movesToLearn.add(Pokemon.Move.SUPER_FANG);
							break;
						case 23:
							movesToLearn.add(Pokemon.Move.FOCUS_ENERGY);
							break;
						case 14:
							movesToLearn.add(Pokemon.Move.HYPER_FANG);
							break;
						case 9:
							movesToLearn.add(Pokemon.Move.QUICK_ATTACK);
							break;
					}
					break;
				case RATICATE:
					switch(pokemon.level)
					{
						case 41:
							movesToLearn.add(Pokemon.Move.SUPER_FANG);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.FOCUS_ENERGY);
							break;
					}
					break;
				case SPEAROW:
					switch(pokemon.level)
					{
						case 36:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 29:
							movesToLearn.add(Pokemon.Move.DRILL_PECK);
							break;
						case 22:
							movesToLearn.add(Pokemon.Move.MIRROR_MOVE);
							break;
						case 15:
							movesToLearn.add(Pokemon.Move.FURY_ATTACK);
							break;
						case 9:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
					}
					break;
				case FEAROW:
					switch(pokemon.level)
					{
						case 43:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 34:
							movesToLearn.add(Pokemon.Move.DRILL_PECK);
							break;
						case 25:
							movesToLearn.add(Pokemon.Move.MIRROR_MOVE);
							break;
					}
					break;
				case EKANS:
					switch(pokemon.level)
					{
						case 38:
							movesToLearn.add(Pokemon.Move.ACID);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.SCREECH);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.GLARE);
							break;
						case 17:
							movesToLearn.add(Pokemon.Move.BITE);
							break;
						case 10:
							movesToLearn.add(Pokemon.Move.POISON_STING);
							break;
					}
					break;
				case ARBOK:
					switch(pokemon.level)
					{
						case 47:
							movesToLearn.add(Pokemon.Move.ACID);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.SCREECH);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.GLARE);
							break;
					}
					break;
				case PIKACHU:
					switch(pokemon.level)
					{
						case 43:
							movesToLearn.add(Pokemon.Move.THUNDER);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 26:
							movesToLearn.add(Pokemon.Move.SWIFT);
							break;
						case 16:
							movesToLearn.add(Pokemon.Move.QUICK_ATTACK);
							break;
						case 9:
							movesToLearn.add(Pokemon.Move.THUNDER_WAVE);
							break;
					}
					break;
				case SANDSHREW:
					switch(pokemon.level)
					{
						case 38:
							movesToLearn.add(Pokemon.Move.FURY_SWIPES);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.SWIFT);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.POISON_STING);
							break;
						case 17:
							movesToLearn.add(Pokemon.Move.SLASH);
							break;
						case 10:
							movesToLearn.add(Pokemon.Move.SAND_ATTACK);
							break;
					}
					break;
				case SANDSLASH:
					switch(pokemon.level)
					{
						case 47:
							movesToLearn.add(Pokemon.Move.FURY_SWIPES);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.SWIFT);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.POISON_STING);
							break;
					}
					break;
				case NIDORAN_F:
					switch(pokemon.level)
					{
						case 43:
							movesToLearn.add(Pokemon.Move.DOUBLE_KICK);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.FURY_SWIPES);
							break;
						case 29:
							movesToLearn.add(Pokemon.Move.BITE);
							break;
						case 21:
							movesToLearn.add(Pokemon.Move.TAIL_WHIP);
							break;
						case 14:
							movesToLearn.add(Pokemon.Move.POISON_STING);
							break;
						case 8:
							movesToLearn.add(Pokemon.Move.SCRATCH);
							break;
					}
					break;
				case NIDORINA:
					switch(pokemon.level)
					{
						case 50:
							movesToLearn.add(Pokemon.Move.DOUBLE_KICK);
							break;
						case 41:
							movesToLearn.add(Pokemon.Move.FURY_SWIPES);
							break;
						case 32:
							movesToLearn.add(Pokemon.Move.BITE);
							break;
						case 23:
							movesToLearn.add(Pokemon.Move.TAIL_WHIP);
							break;
					}
					break;
				case NIDOQUEEN:
					switch(pokemon.level)
					{
						case 23:
							movesToLearn.add(Pokemon.Move.BODY_SLAM);
							break;
					}
					break;
				case NIDORAN_M:
					switch(pokemon.level)
					{
						case 43:
							movesToLearn.add(Pokemon.Move.DOUBLE_KICK);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.HORN_DRILL);
							break;
						case 29:
							movesToLearn.add(Pokemon.Move.FURY_ATTACK);
							break;
						case 21:
							movesToLearn.add(Pokemon.Move.FOCUS_ENERGY);
							break;
						case 14:
							movesToLearn.add(Pokemon.Move.POISON_STING);
							break;
						case 8:
							movesToLearn.add(Pokemon.Move.HORN_ATTACK);
							break;
					}
					break;
				case NIDORINO:
					switch(pokemon.level)
					{
						case 50:
							movesToLearn.add(Pokemon.Move.DOUBLE_KICK);
							break;
						case 41:
							movesToLearn.add(Pokemon.Move.HORN_DRILL);
							break;
						case 32:
							movesToLearn.add(Pokemon.Move.FURY_ATTACK);
							break;
						case 23:
							movesToLearn.add(Pokemon.Move.FOCUS_ENERGY);
							break;
					}
					break;
				case NIDOKING:
					switch(pokemon.level)
					{
						case 23:
							movesToLearn.add(Pokemon.Move.THRASH);
							break;
					}
					break;
				case CLEFAIRY:
					switch(pokemon.level)
					{
						case 48:
							movesToLearn.add(Pokemon.Move.LIGHT_SCREEN);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.DEFENSE_CURL);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.METRONOME);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.MINIMIZE);
							break;
						case 18:
							movesToLearn.add(Pokemon.Move.DOUBLESLAP);
							break;
						case 13:
							movesToLearn.add(Pokemon.Move.SING);
							break;
					}
					break;
				case CLEFABLE:
					switch(pokemon.level)
					{
						case 13:
							movesToLearn.add(Pokemon.Move.SING);
							break;
					}
					break;
				case VULPIX:
					switch(pokemon.level)
					{
						case 42:
							movesToLearn.add(Pokemon.Move.FIRE_SPIN);
							break;
						case 35:
							movesToLearn.add(Pokemon.Move.FLAMETHROWER);
							break;
						case 28:
							movesToLearn.add(Pokemon.Move.CONFUSE_RAY);
							break;
						case 21:
							movesToLearn.add(Pokemon.Move.ROAR);
							break;
						case 16:
							movesToLearn.add(Pokemon.Move.QUICK_ATTACK);
							break;
					}
					break;
				case JIGGLYPUFF:
					switch(pokemon.level)
					{
						case 39:
							movesToLearn.add(Pokemon.Move.DOUBLE_EDGE);
							break;
						case 34:
							movesToLearn.add(Pokemon.Move.BODY_SLAM);
							break;
						case 29:
							movesToLearn.add(Pokemon.Move.REST);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.DOUBLESLAP);
							break;
						case 19:
							movesToLearn.add(Pokemon.Move.DEFENSE_CURL);
							break;
						case 14:
							movesToLearn.add(Pokemon.Move.DISABLE);
							break;
						case 9:
							movesToLearn.add(Pokemon.Move.POUND);
							break;
					}
					break;
				case ZUBAT:
					switch(pokemon.level)
					{
						case 36:
							movesToLearn.add(Pokemon.Move.HAZE);
							break;
						case 28:
							movesToLearn.add(Pokemon.Move.WING_ATTACK);
							break;
						case 21:
							movesToLearn.add(Pokemon.Move.CONFUSE_RAY);
							break;
						case 15:
							movesToLearn.add(Pokemon.Move.BITE);
							break;
						case 10:
							movesToLearn.add(Pokemon.Move.SUPERSONIC);
							break;
					}
					break;
				case GOLBAT:
					switch(pokemon.level)
					{
						case 43:
							movesToLearn.add(Pokemon.Move.HAZE);
							break;
						case 32:
							movesToLearn.add(Pokemon.Move.WING_ATTACK);
							break;
					}
					break;
				case ODDISH:
					switch(pokemon.level)
					{
						case 46:
							movesToLearn.add(Pokemon.Move.SOLARBEAM);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.PETAL_DANCE);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.ACID);
							break;
						case 19:
							movesToLearn.add(Pokemon.Move.SLEEP_POWDER);
							break;
						case 17:
							movesToLearn.add(Pokemon.Move.STUN_SPORE);
							break;
						case 15:
							movesToLearn.add(Pokemon.Move.POISONPOWDER);
							break;
					}
					break;
				case GLOOM:
					switch(pokemon.level)
					{
						case 52:
							movesToLearn.add(Pokemon.Move.SOLARBEAM);
							break;
						case 38:
							movesToLearn.add(Pokemon.Move.PETAL_DANCE);
							break;
						case 28:
							movesToLearn.add(Pokemon.Move.ACID);
							break;
					}
					break;
				case PARAS:
					switch(pokemon.level)
					{
						case 41:
							movesToLearn.add(Pokemon.Move.GROWTH);
							break;
						case 34:
							movesToLearn.add(Pokemon.Move.SLASH);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.SPORE);
							break;
						case 20:
							movesToLearn.add(Pokemon.Move.LEECH_LIFE);
							break;
						case 13:
							movesToLearn.add(Pokemon.Move.STUN_SPORE);
							break;
					}
					break;
				case PARASECT:
					switch(pokemon.level)
					{
						case 48:
							movesToLearn.add(Pokemon.Move.GROWTH);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.SLASH);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.SPORE);
							break;
					}
					break;
				case VENONAT:
					switch(pokemon.level)
					{
						case 43:
							movesToLearn.add(Pokemon.Move.PSYCHIC);
							break;
						case 38:
							movesToLearn.add(Pokemon.Move.SLEEP_POWDER);
							break;
						case 35:
							movesToLearn.add(Pokemon.Move.PSYBEAM);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.STUN_SPORE);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.LEECH_LIFE);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.POISONPOWDER);
							break;
					}
					break;
				case VENOMOTH:
					switch(pokemon.level)
					{
						case 50:
							movesToLearn.add(Pokemon.Move.PSYCHIC);
							break;
						case 43:
							movesToLearn.add(Pokemon.Move.SLEEP_POWDER);
							break;
						case 38:
							movesToLearn.add(Pokemon.Move.PSYBEAM);
							break;
					}
					break;
				case DIGLETT:
					switch(pokemon.level)
					{
						case 40:
							movesToLearn.add(Pokemon.Move.EARTHQUAKE);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.SLASH);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.SAND_ATTACK);
							break;
						case 19:
							movesToLearn.add(Pokemon.Move.DIG);
							break;
					}
					break;
				case DUGTRIO:
					switch(pokemon.level)
					{
						case 47:
							movesToLearn.add(Pokemon.Move.EARTHQUAKE);
							break;
						case 35:
							movesToLearn.add(Pokemon.Move.SLASH);
							break;
					}
					break;
				case MEOWTH:
					switch(pokemon.level)
					{
						case 44:
							movesToLearn.add(Pokemon.Move.SLASH);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.FURY_SWIPES);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.SCREECH);
							break;
						case 17:
							movesToLearn.add(Pokemon.Move.PAY_DAY);
							break;
						case 12:
							movesToLearn.add(Pokemon.Move.BITE);
							break;
					}
					break;
				case PERSIAN:
					switch(pokemon.level)
					{
						case 51:
							movesToLearn.add(Pokemon.Move.SLASH);
							break;
						case 37:
							movesToLearn.add(Pokemon.Move.FURY_SWIPES);
							break;
					}
					break;
				case PSYDUCK:
					switch(pokemon.level)
					{
						case 52:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 43:
							movesToLearn.add(Pokemon.Move.FURY_SWIPES);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.CONFUSION);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.DISABLE);
							break;
						case 28:
							movesToLearn.add(Pokemon.Move.TAIL_WHIP);
							break;
					}
					break;
				case GOLDUCK:
					switch(pokemon.level)
					{
						case 59:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 48:
							movesToLearn.add(Pokemon.Move.FURY_SWIPES);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.CONFUSION);
							break;
					}
					break;
				case MANKEY:
					switch(pokemon.level)
					{
						case 39:
							movesToLearn.add(Pokemon.Move.THRASH);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.SEISMIC_TOSS);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.FOCUS_ENERGY);
							break;
						case 21:
							movesToLearn.add(Pokemon.Move.FURY_SWIPES);
							break;
						case 15:
							movesToLearn.add(Pokemon.Move.KARATE_CHOP);
							break;
					}
					break;
				case PRIMEAPE:
					switch(pokemon.level)
					{
						case 46:
							movesToLearn.add(Pokemon.Move.THRASH);
							break;
						case 37:
							movesToLearn.add(Pokemon.Move.SEISMIC_TOSS);
							break;
					}
					break;
				case GROWLITHE:
					switch(pokemon.level)
					{
						case 50:
							movesToLearn.add(Pokemon.Move.FLAMETHROWER);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.TAKE_DOWN);
							break;
						case 23:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
						case 18:
							movesToLearn.add(Pokemon.Move.EMBER);
							break;
					}
					break;
				case POLIWAG:
					switch(pokemon.level)
					{
						case 45:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 38:
							movesToLearn.add(Pokemon.Move.AMNESIA);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.BODY_SLAM);
							break;
						case 25:
							movesToLearn.add(Pokemon.Move.DOUBLESLAP);
							break;
						case 19:
							movesToLearn.add(Pokemon.Move.WATER_GUN);
							break;
						case 16:
							movesToLearn.add(Pokemon.Move.HYPNOSIS);
							break;
					}
					break;
				case POLIWHIRL:
					switch(pokemon.level)
					{
						case 49:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 41:
							movesToLearn.add(Pokemon.Move.AMNESIA);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.BODY_SLAM);
							break;
						case 26:
							movesToLearn.add(Pokemon.Move.DOUBLESLAP);
							break;
					}
					break;
				case KADABRA:
					switch(pokemon.level)
					{
						case 42:
							movesToLearn.add(Pokemon.Move.REFLECT);
							break;
						case 38:
							movesToLearn.add(Pokemon.Move.PSYCHIC);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.RECOVER);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.PSYBEAM);
							break;
						case 20:
							movesToLearn.add(Pokemon.Move.DISABLE);
							break;
						case 16:
							movesToLearn.add(Pokemon.Move.CONFUSION);
							break;
					}
					break;
				case ALAKAZAM:
					switch(pokemon.level)
					{
						case 42:
							movesToLearn.add(Pokemon.Move.REFLECT);
							break;
						case 38:
							movesToLearn.add(Pokemon.Move.PSYCHIC);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.RECOVER);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.PSYBEAM);
							break;
						case 20:
							movesToLearn.add(Pokemon.Move.DISABLE);
							break;
						case 16:
							movesToLearn.add(Pokemon.Move.CONFUSION);
							break;
					}
					break;
				case MACHOP:
					switch(pokemon.level)
					{
						case 46:
							movesToLearn.add(Pokemon.Move.SUBMISSION);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.SEISMIC_TOSS);
							break;
						case 32:
							movesToLearn.add(Pokemon.Move.FOCUS_ENERGY);
							break;
						case 25:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
						case 20:
							movesToLearn.add(Pokemon.Move.LOW_KICK);
							break;
					}
					break;
				case MACHOKE:
					switch(pokemon.level)
					{
						case 52:
							movesToLearn.add(Pokemon.Move.SUBMISSION);
							break;
						case 44:
							movesToLearn.add(Pokemon.Move.SEISMIC_TOSS);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.FOCUS_ENERGY);
							break;
					}
					break;
				case MACHAMP:
					switch(pokemon.level)
					{
						case 52:
							movesToLearn.add(Pokemon.Move.SUBMISSION);
							break;
						case 44:
							movesToLearn.add(Pokemon.Move.SEISMIC_TOSS);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.FOCUS_ENERGY);
							break;
					}
					break;
				case BELLSPROUT:
					switch(pokemon.level)
					{
						case 42:
							movesToLearn.add(Pokemon.Move.SLAM);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.RAZOR_LEAF);
							break;
						case 26:
							movesToLearn.add(Pokemon.Move.ACID);
							break;
						case 21:
							movesToLearn.add(Pokemon.Move.STUN_SPORE);
							break;
						case 18:
							movesToLearn.add(Pokemon.Move.SLEEP_POWDER);
							break;
						case 15:
							movesToLearn.add(Pokemon.Move.POISONPOWDER);
							break;
						case 13:
							movesToLearn.add(Pokemon.Move.WRAP);
							break;
					}
					break;
				case WEEPINBELL:
					switch(pokemon.level)
					{
						case 49:
							movesToLearn.add(Pokemon.Move.SLAM);
							break;
						case 38:
							movesToLearn.add(Pokemon.Move.RAZOR_LEAF);
							break;
						case 29:
							movesToLearn.add(Pokemon.Move.ACID);
							break;
						case 23:
							movesToLearn.add(Pokemon.Move.STUN_SPORE);
							break;
					}
					break;
				case TENTACOOL:
					switch(pokemon.level)
					{
						case 48:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 40:
							movesToLearn.add(Pokemon.Move.SCREECH);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.BARRIER);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.CONSTRICT);
							break;
						case 22:
							movesToLearn.add(Pokemon.Move.WATER_GUN);
							break;
						case 18:
							movesToLearn.add(Pokemon.Move.POISON_STING);
							break;
						case 13:
							movesToLearn.add(Pokemon.Move.WRAP);
							break;
						case 7:
							movesToLearn.add(Pokemon.Move.SUPERSONIC);
							break;
					}
					break;
				case TENTACRUEL:
					switch(pokemon.level)
					{
						case 50:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 43:
							movesToLearn.add(Pokemon.Move.SCREECH);
							break;
						case 35:
							movesToLearn.add(Pokemon.Move.BARRIER);
							break;
					}
					break;
				case GEODUDE:
					switch(pokemon.level)
					{
						case 36:
							movesToLearn.add(Pokemon.Move.EXPLOSION);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.EARTHQUAKE);
							break;
						case 26:
							movesToLearn.add(Pokemon.Move.HARDEN);
							break;
						case 21:
							movesToLearn.add(Pokemon.Move.SELFDESTRUCT);
							break;
						case 16:
							movesToLearn.add(Pokemon.Move.ROCK_THROW);
							break;
						case 11:
							movesToLearn.add(Pokemon.Move.DEFENSE_CURL);
							break;
					}
					break;
				case GRAVELER:
					switch(pokemon.level)
					{
						case 43:
							movesToLearn.add(Pokemon.Move.EXPLOSION);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.EARTHQUAKE);
							break;
						case 29:
							movesToLearn.add(Pokemon.Move.HARDEN);
							break;
					}
					break;
				case GOLEM:
					switch(pokemon.level)
					{
						case 43:
							movesToLearn.add(Pokemon.Move.EXPLOSION);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.EARTHQUAKE);
							break;
						case 29:
							movesToLearn.add(Pokemon.Move.HARDEN);
							break;
					}
					break;
				case PONYTA:
					switch(pokemon.level)
					{
						case 48:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 43:
							movesToLearn.add(Pokemon.Move.TAKE_DOWN);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.FIRE_SPIN);
							break;
						case 35:
							movesToLearn.add(Pokemon.Move.GROWL);
							break;
						case 32:
							movesToLearn.add(Pokemon.Move.STOMP);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.TAIL_WHIP);
							break;
					}
					break;
				case RAPIDASH:
					switch(pokemon.level)
					{
						case 55:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 47:
							movesToLearn.add(Pokemon.Move.TAKE_DOWN);
							break;
					}
					break;
				case SLOWPOKE:
					switch(pokemon.level)
					{
						case 48:
							movesToLearn.add(Pokemon.Move.PSYCHIC);
							break;
						case 40:
							movesToLearn.add(Pokemon.Move.AMNESIA);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.WATER_GUN);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.GROWL);
							break;
						case 22:
							movesToLearn.add(Pokemon.Move.HEADBUTT);
							break;
						case 18:
							movesToLearn.add(Pokemon.Move.DISABLE);
							break;
					}
					break;
				case SLOWBRO:
					switch(pokemon.level)
					{
						case 55:
							movesToLearn.add(Pokemon.Move.PSYCHIC);
							break;
						case 44:
							movesToLearn.add(Pokemon.Move.AMNESIA);
							break;
						case 37:
							movesToLearn.add(Pokemon.Move.WITHDRAW);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.WATER_GUN);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.GROWL);
							break;
					}
					break;
				case MAGNEMITE:
					switch(pokemon.level)
					{
						case 47:
							movesToLearn.add(Pokemon.Move.SCREECH);
							break;
						case 41:
							movesToLearn.add(Pokemon.Move.SWIFT);
							break;
						case 35:
							movesToLearn.add(Pokemon.Move.THUNDER_WAVE);
							break;
						case 29:
							movesToLearn.add(Pokemon.Move.SUPERSONIC);
							break;
						case 25:
							movesToLearn.add(Pokemon.Move.THUNDERSHOCK);
							break;
					}
					break;
				case MAGNETON:
					switch(pokemon.level)
					{
						case 54:
							movesToLearn.add(Pokemon.Move.SCREECH);
							break;
						case 46:
							movesToLearn.add(Pokemon.Move.SWIFT);
							break;
						case 38:
							movesToLearn.add(Pokemon.Move.THUNDER_WAVE);
							break;
					}
					break;
				case FARFETCH_D:
					switch(pokemon.level)
					{
						case 39:
							movesToLearn.add(Pokemon.Move.SLASH);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 23:
							movesToLearn.add(Pokemon.Move.SWORDS_DANCE);
							break;
						case 15:
							movesToLearn.add(Pokemon.Move.FURY_ATTACK);
							break;
						case 7:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
					}
					break;
				case DODUO:
					switch(pokemon.level)
					{
						case 44:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 40:
							movesToLearn.add(Pokemon.Move.TRI_ATTACK);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.RAGE);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.DRILL_PECK);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.FURY_ATTACK);
							break;
						case 20:
							movesToLearn.add(Pokemon.Move.GROWL);
							break;
					}
					break;
				case DODRIO:
					switch(pokemon.level)
					{
						case 51:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 45:
							movesToLearn.add(Pokemon.Move.TRI_ATTACK);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.RAGE);
							break;
					}
					break;
				case SEEL:
					switch(pokemon.level)
					{
						case 50:
							movesToLearn.add(Pokemon.Move.ICE_BEAM);
							break;
						case 45:
							movesToLearn.add(Pokemon.Move.TAKE_DOWN);
							break;
						case 40:
							movesToLearn.add(Pokemon.Move.REST);
							break;
						case 35:
							movesToLearn.add(Pokemon.Move.AURORA_BEAM);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.GROWL);
							break;
					}
					break;
				case DEWGONG:
					switch(pokemon.level)
					{
						case 56:
							movesToLearn.add(Pokemon.Move.ICE_BEAM);
							break;
						case 50:
							movesToLearn.add(Pokemon.Move.TAKE_DOWN);
							break;
						case 44:
							movesToLearn.add(Pokemon.Move.REST);
							break;
						case 35:
							movesToLearn.add(Pokemon.Move.AURORA_BEAM);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.GROWL);
							break;
					}
					break;
				case GRIMER:
					switch(pokemon.level)
					{
						case 55:
							movesToLearn.add(Pokemon.Move.ACID_ARMOR);
							break;
						case 48:
							movesToLearn.add(Pokemon.Move.SCREECH);
							break;
						case 42:
							movesToLearn.add(Pokemon.Move.HARDEN);
							break;
						case 37:
							movesToLearn.add(Pokemon.Move.SLUDGE);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.MINIMIZE);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.POISON_GAS);
							break;
					}
					break;
				case MUK:
					switch(pokemon.level)
					{
						case 60:
							movesToLearn.add(Pokemon.Move.ACID_ARMOR);
							break;
						case 53:
							movesToLearn.add(Pokemon.Move.SCREECH);
							break;
						case 45:
							movesToLearn.add(Pokemon.Move.HARDEN);
							break;
					}
					break;
				case SHELLDER:
					switch(pokemon.level)
					{
						case 50:
							movesToLearn.add(Pokemon.Move.ICE_BEAM);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.AURORA_BEAM);
							break;
						case 23:
							movesToLearn.add(Pokemon.Move.CLAMP);
							break;
						case 18:
							movesToLearn.add(Pokemon.Move.SUPERSONIC);
							break;
					}
					break;
				case CLOYSTER:
					switch(pokemon.level)
					{
						case 50:
							movesToLearn.add(Pokemon.Move.SPIKE_CANNON);
							break;
					}
					break;
				case GASTLY:
					switch(pokemon.level)
					{
						case 35:
							movesToLearn.add(Pokemon.Move.DREAM_EATER);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.HYPNOSIS);
							break;
					}
					break;
				case HAUNTER:
					switch(pokemon.level)
					{
						case 38:
							movesToLearn.add(Pokemon.Move.DREAM_EATER);
							break;
						case 29:
							movesToLearn.add(Pokemon.Move.HYPNOSIS);
							break;
					}
					break;
				case GENGAR:
					switch(pokemon.level)
					{
						case 38:
							movesToLearn.add(Pokemon.Move.DREAM_EATER);
							break;
						case 29:
							movesToLearn.add(Pokemon.Move.HYPNOSIS);
							break;
					}
					break;
				case ONIX:
					switch(pokemon.level)
					{
						case 43:
							movesToLearn.add(Pokemon.Move.HARDEN);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.SLAM);
							break;
						case 25:
							movesToLearn.add(Pokemon.Move.RAGE);
							break;
						case 19:
							movesToLearn.add(Pokemon.Move.ROCK_THROW);
							break;
						case 15:
							movesToLearn.add(Pokemon.Move.BIND);
							break;
					}
					break;
				case DROWZEE:
					switch(pokemon.level)
					{
						case 37:
							movesToLearn.add(Pokemon.Move.MEDITATE);
							break;
						case 32:
							movesToLearn.add(Pokemon.Move.PSYCHIC);
							break;
						case 29:
							movesToLearn.add(Pokemon.Move.POISON_GAS);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.HEADBUTT);
							break;
						case 17:
							movesToLearn.add(Pokemon.Move.CONFUSION);
							break;
						case 12:
							movesToLearn.add(Pokemon.Move.DISABLE);
							break;
					}
					break;
				case HYPNO:
					switch(pokemon.level)
					{
						case 43:
							movesToLearn.add(Pokemon.Move.MEDITATE);
							break;
						case 37:
							movesToLearn.add(Pokemon.Move.PSYCHIC);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.POISON_GAS);
							break;
					}
					break;
				case KRABBY:
					switch(pokemon.level)
					{
						case 40:
							movesToLearn.add(Pokemon.Move.HARDEN);
							break;
						case 35:
							movesToLearn.add(Pokemon.Move.CRABHAMMER);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.STOMP);
							break;
						case 25:
							movesToLearn.add(Pokemon.Move.GUILLOTINE);
							break;
						case 20:
							movesToLearn.add(Pokemon.Move.VICEGRIP);
							break;
					}
					break;
				case KINGLER:
					switch(pokemon.level)
					{
						case 49:
							movesToLearn.add(Pokemon.Move.HARDEN);
							break;
						case 42:
							movesToLearn.add(Pokemon.Move.CRABHAMMER);
							break;
						case 34:
							movesToLearn.add(Pokemon.Move.STOMP);
							break;
						case 25:
							movesToLearn.add(Pokemon.Move.GUILLOTINE);
							break;
					}
					break;
				case VOLTORB:
					switch(pokemon.level)
					{
						case 43:
							movesToLearn.add(Pokemon.Move.EXPLOSION);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.SWIFT);
							break;
						case 29:
							movesToLearn.add(Pokemon.Move.LIGHT_SCREEN);
							break;
						case 22:
							movesToLearn.add(Pokemon.Move.SELFDESTRUCT);
							break;
						case 17:
							movesToLearn.add(Pokemon.Move.SONIC_BOOM);
							break;
					}
					break;
				case ELECTRODE:
					switch(pokemon.level)
					{
						case 50:
							movesToLearn.add(Pokemon.Move.EXPLOSION);
							break;
						case 40:
							movesToLearn.add(Pokemon.Move.SWIFT);
							break;
						case 29:
							movesToLearn.add(Pokemon.Move.LIGHT_SCREEN);
							break;
						case 22:
							movesToLearn.add(Pokemon.Move.SELFDESTRUCT);
							break;
						case 17:
							movesToLearn.add(Pokemon.Move.SONIC_BOOM);
							break;
					}
					break;
				case EXEGGCUTE:
					switch(pokemon.level)
					{
						case 48:
							movesToLearn.add(Pokemon.Move.SLEEP_POWDER);
							break;
						case 42:
							movesToLearn.add(Pokemon.Move.SOLARBEAM);
							break;
						case 37:
							movesToLearn.add(Pokemon.Move.POISONPOWDER);
							break;
						case 32:
							movesToLearn.add(Pokemon.Move.STUN_SPORE);
							break;
						case 28:
							movesToLearn.add(Pokemon.Move.LEECH_SEED);
							break;
						case 25:
							movesToLearn.add(Pokemon.Move.REFLECT);
							break;
					}
					break;
				case EXEGGUTOR:
					switch(pokemon.level)
					{
						case 28:
							movesToLearn.add(Pokemon.Move.STOMP);
							break;
					}
					break;
				case CUBONE:
					switch(pokemon.level)
					{
						case 46:
							movesToLearn.add(Pokemon.Move.RAGE);
							break;
						case 43:
							movesToLearn.add(Pokemon.Move.BONEMERANG);
							break;
						case 38:
							movesToLearn.add(Pokemon.Move.THRASH);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.FOCUS_ENERGY);
							break;
						case 25:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
					}
					break;
				case MAROWAK:
					switch(pokemon.level)
					{
						case 55:
							movesToLearn.add(Pokemon.Move.RAGE);
							break;
						case 48:
							movesToLearn.add(Pokemon.Move.BONEMERANG);
							break;
						case 41:
							movesToLearn.add(Pokemon.Move.THRASH);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.FOCUS_ENERGY);
							break;
					}
					break;
				case HITMONLEE:
					switch(pokemon.level)
					{
						case 53:
							movesToLearn.add(Pokemon.Move.MEGA_KICK);
							break;
						case 48:
							movesToLearn.add(Pokemon.Move.HI_JUMP_KICK);
							break;
						case 43:
							movesToLearn.add(Pokemon.Move.FOCUS_ENERGY);
							break;
						case 38:
							movesToLearn.add(Pokemon.Move.JUMP_KICK);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.ROLLING_KICK);
							break;
					}
					break;
				case HITMONCHAN:
					switch(pokemon.level)
					{
						case 53:
							movesToLearn.add(Pokemon.Move.COUNTER);
							break;
						case 48:
							movesToLearn.add(Pokemon.Move.MEGA_PUNCH);
							break;
						case 43:
							movesToLearn.add(Pokemon.Move.THUNDERPUNCH);
							break;
						case 38:
							movesToLearn.add(Pokemon.Move.ICE_PUNCH);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.FIRE_PUNCH);
							break;
					}
					break;
				case LICKITUNG:
					switch(pokemon.level)
					{
						case 39:
							movesToLearn.add(Pokemon.Move.SCREECH);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.SLAM);
							break;
					}
					break;
				case KOFFING:
					switch(pokemon.level)
					{
						case 48:
							movesToLearn.add(Pokemon.Move.EXPLOSION);
							break;
						case 45:
							movesToLearn.add(Pokemon.Move.HAZE);
							break;
						case 40:
							movesToLearn.add(Pokemon.Move.SELFDESTRUCT);
							break;
						case 37:
							movesToLearn.add(Pokemon.Move.SMOKESCREEN);
							break;
						case 32:
							movesToLearn.add(Pokemon.Move.SLUDGE);
							break;
					}
					break;
				case WEEZING:
					switch(pokemon.level)
					{
						case 53:
							movesToLearn.add(Pokemon.Move.EXPLOSION);
							break;
						case 49:
							movesToLearn.add(Pokemon.Move.HAZE);
							break;
						case 43:
							movesToLearn.add(Pokemon.Move.SELFDESTRUCT);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.SMOKESCREEN);
							break;
					}
					break;
				case RHYHORN:
					switch(pokemon.level)
					{
						case 55:
							movesToLearn.add(Pokemon.Move.TAKE_DOWN);
							break;
						case 50:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
						case 45:
							movesToLearn.add(Pokemon.Move.HORN_DRILL);
							break;
						case 40:
							movesToLearn.add(Pokemon.Move.FURY_ATTACK);
							break;
						case 35:
							movesToLearn.add(Pokemon.Move.TAIL_WHIP);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.STOMP);
							break;
					}
					break;
				case RHYDON:
					switch(pokemon.level)
					{
						case 64:
							movesToLearn.add(Pokemon.Move.TAKE_DOWN);
							break;
						case 55:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
						case 48:
							movesToLearn.add(Pokemon.Move.HORN_DRILL);
							break;
					}
					break;
				case CHANSEY:
					switch(pokemon.level)
					{
						case 54:
							movesToLearn.add(Pokemon.Move.DOUBLE_EDGE);
							break;
						case 48:
							movesToLearn.add(Pokemon.Move.LIGHT_SCREEN);
							break;
						case 44:
							movesToLearn.add(Pokemon.Move.DEFENSE_CURL);
							break;
						case 38:
							movesToLearn.add(Pokemon.Move.MINIMIZE);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.GROWL);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.SING);
							break;
					}
					break;
				case TANGELA:
					switch(pokemon.level)
					{
						case 48:
							movesToLearn.add(Pokemon.Move.GROWTH);
							break;
						case 45:
							movesToLearn.add(Pokemon.Move.SLAM);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.SLEEP_POWDER);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.STUN_SPORE);
							break;
						case 32:
							movesToLearn.add(Pokemon.Move.POISONPOWDER);
							break;
						case 29:
							movesToLearn.add(Pokemon.Move.ABSORB);
							break;
					}
					break;
				case KANGASKHAN:
					switch(pokemon.level)
					{
						case 46:
							movesToLearn.add(Pokemon.Move.DIZZY_PUNCH);
							break;
						case 41:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.MEGA_PUNCH);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.TAIL_WHIP);
							break;
						case 26:
							movesToLearn.add(Pokemon.Move.BITE);
							break;
					}
					break;
				case HORSEA:
					switch(pokemon.level)
					{
						case 45:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 37:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.WATER_GUN);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
						case 19:
							movesToLearn.add(Pokemon.Move.SMOKESCREEN);
							break;
					}
					break;
				case SEADRA:
					switch(pokemon.level)
					{
						case 52:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 41:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.WATER_GUN);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
						case 19:
							movesToLearn.add(Pokemon.Move.SMOKESCREEN);
							break;
					}
					break;
				case GOLDEEN:
					switch(pokemon.level)
					{
						case 54:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 45:
							movesToLearn.add(Pokemon.Move.HORN_DRILL);
							break;
						case 37:
							movesToLearn.add(Pokemon.Move.WATERFALL);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.FURY_ATTACK);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.HORN_ATTACK);
							break;
						case 19:
							movesToLearn.add(Pokemon.Move.SUPERSONIC);
							break;
					}
					break;
				case SEAKING:
					switch(pokemon.level)
					{
						case 54:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 48:
							movesToLearn.add(Pokemon.Move.HORN_DRILL);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.WATERFALL);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.FURY_ATTACK);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.HORN_ATTACK);
							break;
					}
					break;
				case STARYU:
					switch(pokemon.level)
					{
						case 47:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 42:
							movesToLearn.add(Pokemon.Move.LIGHT_SCREEN);
							break;
						case 37:
							movesToLearn.add(Pokemon.Move.MINIMIZE);
							break;
						case 32:
							movesToLearn.add(Pokemon.Move.SWIFT);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.RECOVER);
							break;
						case 22:
							movesToLearn.add(Pokemon.Move.HARDEN);
							break;
						case 17:
							movesToLearn.add(Pokemon.Move.WATER_GUN);
							break;
					}
					break;
				case MR_MIME:
					switch(pokemon.level)
					{
						case 47:
							movesToLearn.add(Pokemon.Move.SUBSTITUTE);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.MEDITATE);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.DOUBLESLAP);
							break;
						case 23:
							movesToLearn.add(Pokemon.Move.LIGHT_SCREEN);
							break;
						case 15:
							movesToLearn.add(Pokemon.Move.CONFUSION);
							break;
					}
					break;
				case SCYTHER:
					switch(pokemon.level)
					{
						case 42:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 35:
							movesToLearn.add(Pokemon.Move.SWORDS_DANCE);
							break;
						case 29:
							movesToLearn.add(Pokemon.Move.SLASH);
							break;
						case 24:
							movesToLearn.add(Pokemon.Move.DOUBLE_TEAM);
							break;
					}
					break;
				case JYNX:
					switch(pokemon.level)
					{
						case 58:
							movesToLearn.add(Pokemon.Move.BLIZZARD);
							break;
						case 47:
							movesToLearn.add(Pokemon.Move.THRASH);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.BODY_SLAM);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.ICE_PUNCH);
							break;
					}
					break;
				case ELECTABUZZ:
					switch(pokemon.level)
					{
						case 54:
							movesToLearn.add(Pokemon.Move.THUNDER);
							break;
						case 49:
							movesToLearn.add(Pokemon.Move.LIGHT_SCREEN);
							break;
						case 42:
							movesToLearn.add(Pokemon.Move.THUNDERPUNCH);
							break;
						case 37:
							movesToLearn.add(Pokemon.Move.SCREECH);
							break;
						case 34:
							movesToLearn.add(Pokemon.Move.THUNDERSHOCK);
							break;
					}
					break;
				case MAGMAR:
					switch(pokemon.level)
					{
						case 55:
							movesToLearn.add(Pokemon.Move.FLAMETHROWER);
							break;
						case 52:
							movesToLearn.add(Pokemon.Move.SMOG);
							break;
						case 48:
							movesToLearn.add(Pokemon.Move.SMOKESCREEN);
							break;
						case 43:
							movesToLearn.add(Pokemon.Move.FIRE_PUNCH);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.CONFUSE_RAY);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
					}
					break;
				case PINSIR:
					switch(pokemon.level)
					{
						case 54:
							movesToLearn.add(Pokemon.Move.SWORDS_DANCE);
							break;
						case 49:
							movesToLearn.add(Pokemon.Move.SLASH);
							break;
						case 43:
							movesToLearn.add(Pokemon.Move.HARDEN);
							break;
						case 36:
							movesToLearn.add(Pokemon.Move.FOCUS_ENERGY);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.GUILLOTINE);
							break;
						case 25:
							movesToLearn.add(Pokemon.Move.SEISMIC_TOSS);
							break;
					}
					break;
				case TAUROS:
					switch(pokemon.level)
					{
						case 51:
							movesToLearn.add(Pokemon.Move.TAKE_DOWN);
							break;
						case 44:
							movesToLearn.add(Pokemon.Move.RAGE);
							break;
						case 35:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
						case 28:
							movesToLearn.add(Pokemon.Move.TAIL_WHIP);
							break;
					}
					break;
				case MAGIKARP:
					switch(pokemon.level)
					{
						case 15:
							movesToLearn.add(Pokemon.Move.TACKLE);
							break;
					}
					break;
				case GYARADOS:
					switch(pokemon.level)
					{
						case 52:
							movesToLearn.add(Pokemon.Move.HYPER_BEAM);
							break;
						case 41:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 32:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
						case 25:
							movesToLearn.add(Pokemon.Move.DRAGON_RAGE);
							break;
						case 20:
							movesToLearn.add(Pokemon.Move.BITE);
							break;
					}
					break;
				case LAPRAS:
					switch(pokemon.level)
					{
						case 46:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 38:
							movesToLearn.add(Pokemon.Move.ICE_BEAM);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.CONFUSE_RAY);
							break;
						case 25:
							movesToLearn.add(Pokemon.Move.BODY_SLAM);
							break;
						case 20:
							movesToLearn.add(Pokemon.Move.MIST);
							break;
						case 16:
							movesToLearn.add(Pokemon.Move.SING);
							break;
					}
					break;
				case EEVEE:
					switch(pokemon.level)
					{
						case 45:
							movesToLearn.add(Pokemon.Move.TAKE_DOWN);
							break;
						case 37:
							movesToLearn.add(Pokemon.Move.BITE);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.TAIL_WHIP);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.QUICK_ATTACK);
							break;
					}
					break;
				case VAPOREON:
					switch(pokemon.level)
					{
						case 52:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 48:
							movesToLearn.add(Pokemon.Move.MIST);
							break;
						case 44:
							movesToLearn.add(Pokemon.Move.HAZE);
							break;
						case 42:
							movesToLearn.add(Pokemon.Move.ACID_ARMOR);
							break;
						case 40:
							movesToLearn.add(Pokemon.Move.BITE);
							break;
						case 37:
							movesToLearn.add(Pokemon.Move.TAIL_WHIP);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.WATER_GUN);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.QUICK_ATTACK);
							break;
					}
					break;
				case JOLTEON:
					switch(pokemon.level)
					{
						case 52:
							movesToLearn.add(Pokemon.Move.THUNDER);
							break;
						case 48:
							movesToLearn.add(Pokemon.Move.PIN_MISSILE);
							break;
						case 44:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 42:
							movesToLearn.add(Pokemon.Move.DOUBLE_KICK);
							break;
						case 40:
							movesToLearn.add(Pokemon.Move.THUNDER_WAVE);
							break;
						case 37:
							movesToLearn.add(Pokemon.Move.TAIL_WHIP);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.THUNDERSHOCK);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.QUICK_ATTACK);
							break;
					}
					break;
				case FLAREON:
					switch(pokemon.level)
					{
						case 54:
							movesToLearn.add(Pokemon.Move.FLAMETHROWER);
							break;
						case 48:
							movesToLearn.add(Pokemon.Move.RAGE);
							break;
						case 44:
							movesToLearn.add(Pokemon.Move.FIRE_SPIN);
							break;
						case 42:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
						case 40:
							movesToLearn.add(Pokemon.Move.BITE);
							break;
						case 37:
							movesToLearn.add(Pokemon.Move.TAIL_WHIP);
							break;
						case 31:
							movesToLearn.add(Pokemon.Move.EMBER);
							break;
						case 27:
							movesToLearn.add(Pokemon.Move.QUICK_ATTACK);
							break;
					}
					break;
				case PORYGON:
					switch(pokemon.level)
					{
						case 42:
							movesToLearn.add(Pokemon.Move.TRI_ATTACK);
							break;
						case 35:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 28:
							movesToLearn.add(Pokemon.Move.RECOVER);
							break;
						case 23:
							movesToLearn.add(Pokemon.Move.PSYBEAM);
							break;
					}
					break;
				case OMANYTE:
					switch(pokemon.level)
					{
						case 53:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 46:
							movesToLearn.add(Pokemon.Move.SPIKE_CANNON);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
						case 34:
							movesToLearn.add(Pokemon.Move.HORN_ATTACK);
							break;
					}
					break;
				case OMASTAR:
					switch(pokemon.level)
					{
						case 49:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 44:
							movesToLearn.add(Pokemon.Move.SPIKE_CANNON);
							break;
					}
					break;
				case KABUTO:
					switch(pokemon.level)
					{
						case 49:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 44:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
						case 39:
							movesToLearn.add(Pokemon.Move.SLASH);
							break;
						case 34:
							movesToLearn.add(Pokemon.Move.ABSORB);
							break;
					}
					break;
				case KABUTOPS:
					switch(pokemon.level)
					{
						case 53:
							movesToLearn.add(Pokemon.Move.HYDRO_PUMP);
							break;
						case 46:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
					}
					break;
				case AERODACTYL:
					switch(pokemon.level)
					{
						case 54:
							movesToLearn.add(Pokemon.Move.HYPER_BEAM);
							break;
						case 45:
							movesToLearn.add(Pokemon.Move.TAKE_DOWN);
							break;
						case 38:
							movesToLearn.add(Pokemon.Move.BITE);
							break;
						case 33:
							movesToLearn.add(Pokemon.Move.SUPERSONIC);
							break;
					}
					break;
				case SNORLAX:
					switch(pokemon.level)
					{
						case 56:
							movesToLearn.add(Pokemon.Move.HYPER_BEAM);
							break;
						case 48:
							movesToLearn.add(Pokemon.Move.DOUBLE_EDGE);
							break;
						case 41:
							movesToLearn.add(Pokemon.Move.HARDEN);
							break;
						case 35:
							movesToLearn.add(Pokemon.Move.BODY_SLAM);
							break;
					}
					break;
				case ARTICUNO:
					switch(pokemon.level)
					{
						case 60:
							movesToLearn.add(Pokemon.Move.MIST);
							break;
						case 55:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 51:
							movesToLearn.add(Pokemon.Move.BLIZZARD);
							break;
					}
					break;
				case ZAPDOS:
					switch(pokemon.level)
					{
						case 60:
							movesToLearn.add(Pokemon.Move.LIGHT_SCREEN);
							break;
						case 55:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 51:
							movesToLearn.add(Pokemon.Move.THUNDER);
							break;
					}
					break;
				case MOLTRES:
					switch(pokemon.level)
					{
						case 60:
							movesToLearn.add(Pokemon.Move.SKY_ATTACK);
							break;
						case 55:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
						case 51:
							movesToLearn.add(Pokemon.Move.LEER);
							break;
					}
					break;
				case DRATINI:
					switch(pokemon.level)
					{
						case 50:
							movesToLearn.add(Pokemon.Move.HYPER_BEAM);
							break;
						case 40:
							movesToLearn.add(Pokemon.Move.DRAGON_RAGE);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.SLAM);
							break;
						case 20:
							movesToLearn.add(Pokemon.Move.AGILITY);
							break;
					}
					break;
				case DRAGONAIR:
					switch(pokemon.level)
					{
						case 55:
							movesToLearn.add(Pokemon.Move.HYPER_BEAM);
							break;
						case 45:
							movesToLearn.add(Pokemon.Move.DRAGON_RAGE);
							break;
						case 35:
							movesToLearn.add(Pokemon.Move.SLAM);
							break;
					}
					break;
				case DRAGONITE:
					switch(pokemon.level)
					{
						case 60:
							movesToLearn.add(Pokemon.Move.HYPER_BEAM);
							break;
					}
					break;
				case MEWTWO:
					switch(pokemon.level)
					{
						case 81:
							movesToLearn.add(Pokemon.Move.AMNESIA);
							break;
						case 75:
							movesToLearn.add(Pokemon.Move.MIST);
							break;
					}
					break;
				case MEW:
					switch(pokemon.level)
					{
						case 40:
							movesToLearn.add(Pokemon.Move.PSYCHIC);
							break;
						case 30:
							movesToLearn.add(Pokemon.Move.METRONOME);
							break;
						case 20:
							movesToLearn.add(Pokemon.Move.MEGA_PUNCH);
							break;
						case 10:
							movesToLearn.add(Pokemon.Move.TRANSFORM);
							break;
					}
					break;
			}

			if (evolved)
			pokemon.level--;
		}
		while (evolved && pokemon.level > 0);

		for (int i = 0; i<movesToLearn.size(); i++)
		{
			if (hasMove(pokemon,movesToLearn.get(i)))
			{
				movesToLearn.remove(i);
				i--;
			}
		}

		pokemon.level = holdLevel;
		int[] ppHold = new int[4];
        System.arraycopy(pokemon.TRUE_PP, 0, ppHold, 0, 4);

		while(movesToLearn.size() > 0)
		{
			boolean hasBlank = false;
			int blankId = 0;
			for (int i = 0; i<4; i++)
			{
				if (pokemon.move[i] == Pokemon.Move.NONE)
				{
					hasBlank = true;
					blankId = i;
					break;
				}

			}
			if (!hasBlank)
			{
				Inventory.selectWindow.openWindow(pokemon,"Replace what with " + movesToLearn.get(0).toString().replace('_',' '),
				"Learn Move",null);
				while (Inventory.selectWindow.visible)
				{}

				if (Inventory.selectWindow.isSelected)
				{
					pokemon.move[Inventory.selectWindow.moveNo] = movesToLearn.get(0);
					pokemon.createMoves();
					ppHold[Inventory.selectWindow.moveNo] = pokemon.TRUE_PPMAX[Inventory.selectWindow.moveNo];
				}
			}
			else
			{
				pokemon.move[blankId] = movesToLearn.get(0);
				pokemon.createMoves();
				ppHold[blankId] = pokemon.TRUE_PPMAX[blankId];
			}

			movesToLearn.remove(0);
		}
        System.arraycopy(ppHold, 0, pokemon.TRUE_PP, 0, 4);

		System.out.println(pokemon);

		return pokemon.move;
	}
	public static Pokemon teachMove(Pokemon pokemon, Item tm, int number)
	{
		if (tm.effect1 != Item.Effect.TM && tm.effect1 != Item.Effect.HM)
		{
			Inventory.errorWindow.addMessage(" The Item selected is not a TM or HM! ");
			return pokemon;
		}

		else if (canLearnMove(tm,pokemon))
		{
			pokemon.move[number] = tm.moveLearned;
			pokemon.createMoves();
		}
		else
		{
			Inventory.errorWindow.addMessage(" Couldn't learn TM or HM! ");
			Inventory.itemCancel = true;
		}
		return pokemon;

	}
	public static boolean canLearnMove(Item tm,Pokemon pokemon)
	{
		if (tm.type == Item.Type.REDBULL)
		return true;

		boolean canLearn = false;
		switch(pokemon.species)
		{
			case BULBASAUR:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 8:
					case 9:
					case 10:
					case 20:
					case 21:
					case 22:
					case 31:
					case 32:
					case 33:
					case 34:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
						canLearn = true;
				}
			}
			break;
			case IVYSAUR:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 8:
					case 9:
					case 10:
					case 20:
					case 21:
					case 22:
					case 31:
					case 32:
					case 33:
					case 34:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
						canLearn = true;
				}
			}
			break;
			case VENUSAUR:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 20:
					case 21:
					case 22:
					case 31:
					case 32:
					case 33:
					case 34:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
						canLearn = true;
				}
			}
			break;
			case CHARMANDER:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 3:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 17:
					case 18:
					case 19:
					case 20:
					case 23:
					case 28:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 4:
						canLearn = true;
				}
			}
			break;
			case CHARMELEON:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 3:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 17:
					case 18:
					case 19:
					case 20:
					case 23:
					case 28:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 4:
						canLearn = true;
				}
			}
			break;
			case CHARIZARD:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 3:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 17:
					case 18:
					case 19:
					case 20:
					case 23:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 2:
					case 4:
						canLearn = true;
				}
			}
			break;
			case SQUIRTLE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 17:
					case 18:
					case 19:
					case 20:
					case 28:
					case 31:
					case 32:
					case 33:
					case 34:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case WARTORTLE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 17:
					case 18:
					case 19:
					case 20:
					case 28:
					case 31:
					case 32:
					case 33:
					case 34:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case BLASTOISE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 33:
					case 34:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case CATERPIE:
			canLearn = false;
			break;
			case METAPOD:
			canLearn = false;
			break;
			case BUTTERFREE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 4:
					case 6:
					case 9:
					case 10:
					case 15:
					case 20:
					case 21:
					case 22:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 44:
					case 46:
					case 50:
						canLearn = true;
				}
			}
			break;
			case WEEDLE:
			canLearn = false;
			break;
			case KAKUNA:
			canLearn = false;
			break;
			case BEEDRILL:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 9:
					case 10:
					case 15:
					case 20:
					case 21:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
						canLearn = true;
				}
			}
			break;
			case PIDGEY:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 4:
					case 6:
					case 9:
					case 10:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 43:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 2:
						canLearn = true;
				}
			}
			break;
			case PIDGEOTTO:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 4:
					case 6:
					case 9:
					case 10:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 43:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 2:
						canLearn = true;
				}
			}
			break;
			case PIDGEOT:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 4:
					case 6:
					case 9:
					case 10:
					case 15:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 43:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 2:
						canLearn = true;
				}
			}
			break;
			case RATTATA:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 14:
					case 20:
					case 24:
					case 25:
					case 28:
					case 31:
					case 32:
					case 34:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case RATICATE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 14:
					case 15:
					case 20:
					case 24:
					case 25:
					case 28:
					case 31:
					case 32:
					case 34:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case SPEAROW:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 4:
					case 6:
					case 9:
					case 10:
					case 20:
					case 31:
					case 32:
					case 34:
					case 39:
					case 43:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 2:
						canLearn = true;
				}
			}
			break;
			case FEAROW:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 4:
					case 6:
					case 9:
					case 10:
					case 15:
					case 20:
					case 31:
					case 32:
					case 34:
					case 39:
					case 43:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 2:
						canLearn = true;
				}
			}
			break;
			case EKANS:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 20:
					case 21:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 34:
					case 40:
					case 44:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case ARBOK:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 20:
					case 21:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 34:
					case 40:
					case 44:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case PIKACHU:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 16:
					case 17:
					case 19:
					case 20:
					case 24:
					case 25:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 40:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 5:
						canLearn = true;
				}
			}
			break;
			case RAICHU:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 16:
					case 17:
					case 19:
					case 20:
					case 24:
					case 25:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 40:
					case 44:
					case 45:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 5:
						canLearn = true;
				}
			}
			break;
			case SANDSHREW:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 8:
					case 9:
					case 10:
					case 17:
					case 19:
					case 20:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 34:
					case 39:
					case 40:
					case 44:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 4:
						canLearn = true;
				}
			}
			break;
			case SANDSLASH:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 17:
					case 19:
					case 20:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 34:
					case 39:
					case 40:
					case 44:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 4:
						canLearn = true;
				}
			}
			break;
			case NIDORAN_F:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 14:
					case 20:
					case 24:
					case 25:
					case 31:
					case 32:
					case 33:
					case 34:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case NIDORINA:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 20:
					case 24:
					case 25:
					case 31:
					case 32:
					case 33:
					case 34:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case NIDOQUEEN:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 16:
					case 17:
					case 18:
					case 19:
					case 20:
					case 24:
					case 25:
					case 26:
					case 27:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 40:
					case 44:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case NIDORAN_M:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 14:
					case 20:
					case 24:
					case 25:
					case 31:
					case 32:
					case 33:
					case 34:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case NIDORINO:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 20:
					case 24:
					case 25:
					case 31:
					case 32:
					case 33:
					case 34:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case NIDOKING:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 16:
					case 17:
					case 18:
					case 19:
					case 20:
					case 24:
					case 25:
					case 26:
					case 27:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 40:
					case 44:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case CLEFAIRY:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 17:
					case 18:
					case 19:
					case 20:
					case 22:
					case 24:
					case 25:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 35:
					case 38:
					case 40:
					case 44:
					case 45:
					case 46:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
					case 5:
						canLearn = true;
				}
			}
			break;
			case CLEFABLE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 22:
					case 24:
					case 25:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 35:
					case 38:
					case 40:
					case 44:
					case 45:
					case 46:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
					case 5:
						canLearn = true;
				}
			}
			break;
			case VULPIX:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 20:
					case 28:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case NINETALES:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 20:
					case 28:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case JIGGLYPUFF:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 17:
					case 18:
					case 19:
					case 20:
					case 22:
					case 24:
					case 25:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 40:
					case 44:
					case 45:
					case 46:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
					case 5:
						canLearn = true;
				}
			}
			break;
			case WIGGLYTUFF:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 22:
					case 24:
					case 25:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 40:
					case 44:
					case 45:
					case 46:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
					case 5:
						canLearn = true;
				}
			}
			break;
			case ZUBAT:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 4:
					case 6:
					case 9:
					case 10:
					case 20:
					case 21:
					case 31:
					case 32:
					case 34:
					case 39:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case GOLBAT:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 4:
					case 6:
					case 9:
					case 10:
					case 15:
					case 20:
					case 21:
					case 31:
					case 32:
					case 34:
					case 39:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case ODDISH:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 9:
					case 10:
					case 20:
					case 21:
					case 22:
					case 31:
					case 32:
					case 33:
					case 34:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
						canLearn = true;
				}
			}
			break;
			case GLOOM:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 9:
					case 10:
					case 20:
					case 21:
					case 22:
					case 31:
					case 32:
					case 33:
					case 34:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
						canLearn = true;
				}
			}
			break;
			case VILEPLUME:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 9:
					case 10:
					case 15:
					case 20:
					case 21:
					case 22:
					case 31:
					case 32:
					case 33:
					case 34:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
						canLearn = true;
				}
			}
			break;
			case PARAS:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 9:
					case 10:
					case 20:
					case 21:
					case 22:
					case 28:
					case 31:
					case 32:
					case 33:
					case 34:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
						canLearn = true;
				}
			}
			break;
			case PARASECT:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 20:
					case 21:
					case 22:
					case 28:
					case 31:
					case 32:
					case 33:
					case 34:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
						canLearn = true;
				}
			}
			break;
			case VENONAT:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 9:
					case 10:
					case 20:
					case 21:
					case 22:
					case 29:
					case 31:
					case 32:
					case 33:
					case 34:
					case 44:
					case 46:
					case 50:
						canLearn = true;
				}
			}
			break;
			case VENOMOTH:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 4:
					case 6:
					case 9:
					case 10:
					case 15:
					case 20:
					case 21:
					case 22:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 44:
					case 46:
					case 50:
						canLearn = true;
				}
			}
			break;
			case DIGLETT:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 20:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 34:
					case 44:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			break;
			case DUGTRIO:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 20:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 34:
					case 44:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			break;
			case MEOWTH:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 16:
					case 20:
					case 24:
					case 25:
					case 31:
					case 32:
					case 34:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case PERSIAN:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 15:
					case 16:
					case 20:
					case 24:
					case 25:
					case 31:
					case 32:
					case 34:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case PSYDUCK:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 16:
					case 17:
					case 18:
					case 19:
					case 20:
					case 28:
					case 31:
					case 32:
					case 34:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
					case 5:
						canLearn = true;
				}
			}
			break;
			case GOLDUCK:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 16:
					case 17:
					case 18:
					case 19:
					case 20:
					case 28:
					case 31:
					case 32:
					case 34:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
					case 5:
						canLearn = true;
				}
			}
			break;
			case MANKEY:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 16:
					case 17:
					case 18:
					case 19:
					case 20:
					case 24:
					case 25:
					case 28:
					case 31:
					case 32:
					case 34:
					case 35:
					case 39:
					case 40:
					case 44:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case PRIMEAPE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 16:
					case 17:
					case 18:
					case 19:
					case 20:
					case 24:
					case 25:
					case 28:
					case 31:
					case 32:
					case 34:
					case 35:
					case 39:
					case 40:
					case 44:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case GROWLITHE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 20:
					case 23:
					case 28:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case ARCANINE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 20:
					case 23:
					case 28:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case POLIWAG:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 20:
					case 29:
					case 31:
					case 32:
					case 34:
					case 40:
					case 44:
					case 46:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
						canLearn = true;
				}
			}
			break;
			case POLIWHIRL:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 17:
					case 18:
					case 19:
					case 20:
					case 26:
					case 27:
					case 29:
					case 31:
					case 32:
					case 34:
					case 35:
					case 40:
					case 44:
					case 46:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case POLIWRATH:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 26:
					case 27:
					case 29:
					case 31:
					case 32:
					case 34:
					case 35:
					case 40:
					case 44:
					case 46:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case ABRA:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 26:
					case 27:
					case 29:
					case 31:
					case 32:
					case 34:
					case 35:
					case 40:
					case 44:
					case 45:
					case 46:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 5:
						canLearn = true;
				}
			}
			break;
			case KADABRA:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 17:
					case 18:
					case 19:
					case 20:
					case 28:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 35:
					case 40:
					case 44:
					case 45:
					case 46:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 5:
						canLearn = true;
				}
			}
			break;
			case ALAKAZAM:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 28:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 35:
					case 40:
					case 44:
					case 45:
					case 46:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 5:
						canLearn = true;
				}
			}
			break;
			case MACHOP:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 17:
					case 18:
					case 19:
					case 20:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 34:
					case 35:
					case 38:
					case 40:
					case 44:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case MACHOKE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 17:
					case 18:
					case 19:
					case 20:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 34:
					case 35:
					case 38:
					case 40:
					case 44:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case MACHAMP:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 34:
					case 35:
					case 38:
					case 40:
					case 44:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case BELLSPROUT:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 9:
					case 10:
					case 20:
					case 21:
					case 22:
					case 31:
					case 32:
					case 33:
					case 34:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
						canLearn = true;
				}
			}
			break;
			case WEEPINBELL:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 9:
					case 10:
					case 20:
					case 21:
					case 22:
					case 31:
					case 32:
					case 33:
					case 34:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
						canLearn = true;
				}
			}
			break;
			case VICTREEBEL:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 9:
					case 10:
					case 15:
					case 20:
					case 21:
					case 22:
					case 31:
					case 32:
					case 33:
					case 34:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
						canLearn = true;
				}
			}
			break;
			case TENTACOOL:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 20:
					case 21:
					case 31:
					case 32:
					case 33:
					case 34:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 3:
						canLearn = true;
				}
			}
			break;
			case TENTACRUEL:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 20:
					case 21:
					case 31:
					case 32:
					case 33:
					case 34:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 3:
						canLearn = true;
				}
			}
			break;
			case GEODUDE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 6:
					case 8:
					case 9:
					case 10:
					case 17:
					case 18:
					case 19:
					case 20:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 34:
					case 35:
					case 36:
					case 38:
					case 44:
					case 47:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case GRAVELER:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 6:
					case 8:
					case 9:
					case 10:
					case 17:
					case 18:
					case 19:
					case 20:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 34:
					case 35:
					case 36:
					case 38:
					case 44:
					case 47:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case GOLEM:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 34:
					case 35:
					case 36:
					case 38:
					case 44:
					case 47:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case PONYTA:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case RAPIDASH:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 15:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case SLOWPOKE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 16:
					case 20:
					case 26:
					case 27:
					case 28:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 40:
					case 44:
					case 45:
					case 46:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
					case 5:
						canLearn = true;
				}
			}
			break;
			case SLOWBRO:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 16:
					case 17:
					case 18:
					case 19:
					case 20:
					case 26:
					case 27:
					case 28:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 40:
					case 44:
					case 45:
					case 46:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
					case 5:
						canLearn = true;
				}
			}
			break;
			case MAGNEMITE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 9:
					case 10:
					case 20:
					case 24:
					case 25:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 44:
					case 45:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 5:
						canLearn = true;
				}
			}
			break;
			case MAGNETON:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 9:
					case 10:
					case 15:
					case 20:
					case 24:
					case 25:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 44:
					case 45:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 5:
						canLearn = true;
				}
			}
			break;
			case FARFETCH_D:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 3:
					case 4:
					case 6:
					case 8:
					case 9:
					case 10:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 2:
						canLearn = true;
				}
			}
			break;
			case DODUO:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 4:
					case 6:
					case 8:
					case 9:
					case 10:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 40:
					case 43:
					case 44:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 2:
						canLearn = true;
				}
			}
			break;
			case DODRIO:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 4:
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 40:
					case 43:
					case 44:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 2:
						canLearn = true;
				}
			}
			break;
			case SEEL:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 16:
					case 20:
					case 31:
					case 32:
					case 34:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case DEWGONG:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 16:
					case 20:
					case 31:
					case 32:
					case 34:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case GRIMER:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 20:
					case 21:
					case 24:
					case 25:
					case 31:
					case 32:
					case 34:
					case 36:
					case 38:
					case 44:
					case 47:
					case 50:
						canLearn = true;
				}
			}
			break;
			case MUK:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 15:
					case 20:
					case 21:
					case 24:
					case 25:
					case 31:
					case 32:
					case 34:
					case 36:
					case 38:
					case 44:
					case 47:
					case 50:
						canLearn = true;
				}
			}
			break;
			case SHELLDER:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 20:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 36:
					case 39:
					case 44:
					case 47:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
						canLearn = true;
				}
			}
			break;
			case CLOYSTER:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 20:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 36:
					case 39:
					case 44:
					case 47:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
						canLearn = true;
				}
			}
			break;
			case GASTLY:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 20:
					case 21:
					case 24:
					case 25:
					case 29:
					case 31:
					case 32:
					case 34:
					case 36:
					case 42:
					case 44:
					case 46:
					case 47:
					case 50:
						canLearn = true;
				}
			}
			break;
			case HAUNTER:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 20:
					case 21:
					case 24:
					case 25:
					case 29:
					case 31:
					case 32:
					case 34:
					case 36:
					case 42:
					case 44:
					case 46:
					case 47:
					case 50:
						canLearn = true;
				}
			}
			break;
			case GENGAR:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 21:
					case 24:
					case 25:
					case 29:
					case 31:
					case 32:
					case 34:
					case 35:
					case 36:
					case 40:
					case 42:
					case 44:
					case 46:
					case 47:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case ONIX:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 20:
					case 26:
					case 28:
					case 31:
					case 32:
					case 34:
					case 35:
					case 36:
					case 40:
					case 42:
					case 44:
					case 46:
					case 47:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case DROWZEE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 17:
					case 18:
					case 19:
					case 20:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 35:
					case 40:
					case 42:
					case 44:
					case 45:
					case 46:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 5:
						canLearn = true;
				}
			}
			break;
			case HYPNO:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 35:
					case 40:
					case 42:
					case 44:
					case 45:
					case 46:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 5:
						canLearn = true;
				}
			}
			break;
			case KRABBY:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 20:
					case 31:
					case 32:
					case 34:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case KINGLER:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 20:
					case 31:
					case 32:
					case 34:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case VOLTORB:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 9:
					case 20:
					case 24:
					case 25:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 36:
					case 39:
					case 44:
					case 45:
					case 47:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 5:
						canLearn = true;
				}
			}
			break;
			case ELECTRODE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 9:
					case 15:
					case 20:
					case 24:
					case 25:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 36:
					case 39:
					case 44:
					case 45:
					case 47:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 5:
						canLearn = true;
				}
			}
			break;
			case EXEGGCUTE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 9:
					case 10:
					case 20:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 36:
					case 37:
					case 44:
					case 46:
					case 47:
					case 50:
						canLearn = true;
				}
			}
			break;
			case EXEGGUTOR:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 9:
					case 10:
					case 15:
					case 20:
					case 21:
					case 22:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 36:
					case 37:
					case 44:
					case 46:
					case 47:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case CUBONE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 17:
					case 18:
					case 19:
					case 20:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 34:
					case 38:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case MAROWAK:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 34:
					case 38:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case HITMONLEE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 17:
					case 18:
					case 19:
					case 20:
					case 31:
					case 32:
					case 34:
					case 35:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case HITMONCHAN:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 17:
					case 18:
					case 19:
					case 20:
					case 31:
					case 32:
					case 34:
					case 35:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case LICKITUNG:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 3:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 24:
					case 25:
					case 26:
					case 27:
					case 31:
					case 32:
					case 34:
					case 38:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case KOFFING:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 20:
					case 24:
					case 25:
					case 31:
					case 32:
					case 34:
					case 36:
					case 38:
					case 44:
					case 47:
					case 50:
						canLearn = true;
				}
			}
			break;
			case WEEZING:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 15:
					case 20:
					case 24:
					case 25:
					case 31:
					case 32:
					case 34:
					case 36:
					case 38:
					case 44:
					case 47:
					case 50:
						canLearn = true;
				}
			}
			break;
			case RHYHORN:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 20:
					case 24:
					case 25:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 34:
					case 38:
					case 40:
					case 44:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case RHYDON:
			if (tm.type == Item.Type.TM)

			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 16:
					case 17:
					case 18:
					case 19:
					case 20:
					case 24:
					case 25:
					case 26:
					case 27:
					case 28:
					case 31:
					case 32:
					case 34:
					case 38:
					case 40:
					case 44:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case CHANSEY:
			if (tm.type == Item.Type.TM)

			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 22:
					case 24:
					case 25:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 35:
					case 37:
					case 38:
					case 40:
					case 41:
					case 44:
					case 45:
					case 46:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
					case 5:
						canLearn = true;
				}
			}
			break;
			case TANGELA:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 20:
					case 21:
					case 22:
					case 31:
					case 32:
					case 34:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
						canLearn = true;
				}
			}
			break;
			case KANGASKHAN:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 24:
					case 25:
					case 26:
					case 27:
					case 31:
					case 32:
					case 34:
					case 38:
					case 40:
					case 44:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case HORSEA:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 20:
					case 31:
					case 32:
					case 34:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
						canLearn = true;
				}
			}
			break;
			case SEADRA:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 20:
					case 31:
					case 32:
					case 34:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
						canLearn = true;
				}
			}
			break;
			case GOLDEEN:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 7:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 20:
					case 31:
					case 32:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
						canLearn = true;
				}
			}
			break;
			case SEAKING:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 7:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 20:
					case 31:
					case 32:
					case 34:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
						canLearn = true;
				}
			}
			break;
			case STARYU:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 20:
					case 24:
					case 25:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 40:
					case 44:
					case 45:
					case 46:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 5:
						canLearn = true;
				}
			}
			break;
			case STARMIE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 20:
					case 24:
					case 25:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 40:
					case 44:
					case 45:
					case 46:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 5:
						canLearn = true;
				}
			}
			break;
			case MR_MIME:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 22:
					case 24:
					case 25:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 35:
					case 40:
					case 44:
					case 45:
					case 46:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 5:
						canLearn = true;
				}
			}
			break;
			case SCYTHER:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 9:
					case 10:
					case 15:
					case 20:
					case 31:
					case 32:
					case 34:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
						canLearn = true;
				}
			}
			break;
			case JYNX:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 35:
					case 40:
					case 44:
					case 46:
					case 50:
						canLearn = true;
				}
			}
			break;
			case ELECTABUZZ:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 24:
					case 25:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 35:
					case 39:
					case 40:
					case 44:
					case 45:
					case 46:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
					case 5:
						canLearn = true;
				}
			}
			break;
			case MAGMAR:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 29:
					case 30:
					case 31:
					case 32:
					case 34:
					case 35:
					case 38:
					case 39:
					case 40:
					case 44:
					case 46:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case PINSIR:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 17:
					case 19:
					case 20:
					case 31:
					case 32:
					case 34:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 4:
						canLearn = true;
				}
			}
			break;
			case TAUROS:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 13:
					case 14:
					case 15:
					case 20:
					case 24:
					case 25:
					case 26:
					case 27:
					case 31:
					case 32:
					case 34:
					case 38:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
						canLearn = true;
				}
			}
			break;
			case MAGIKARP:
			canLearn = false;
			break;
			case GYARADOS:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 20:
					case 23:
					case 24:
					case 25:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case LAPRAS:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 20:
					case 22:
					case 23:
					case 24:
					case 25:
					case 29:
					case 31:
					case 32:
					case 33:
					case 34:
					case 40:
					case 44:
					case 46:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case DITTO:
			canLearn = false;
			break;
			case EEVEE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case VAPOREON:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
						canLearn = true;
				}
			}
			break;
			case JOLTEON:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 20:
					case 24:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 40:
					case 44:
					case 45:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 5:
						canLearn = true;
				}
			}
			break;
			case FLAREON:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 15:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			break;
			case PORYGON:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 9:
					case 10:
					case 13:
					case 14:
					case 15:
					case 20:
					case 24:
					case 25:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 40:
					case 44:
					case 45:
					case 46:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 5:
						canLearn = true;
				}
			}
			break;
			case OMANYTE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
						canLearn = true;
				}
			}
			break;
			case OMASTAR:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 17:
					case 19:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
						canLearn = true;
				}
			}
			break;
			case KABUTO:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
						canLearn = true;
				}
			}
			break;
			case KABUTOPS:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 3:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 17:
					case 19:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 40:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
						canLearn = true;
				}
			}
			break;
			case AERODACTYL:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 4:
					case 6:
					case 9:
					case 10:
					case 15:
					case 20:
					case 23:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 43:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 2:
						canLearn = true;
				}
			}
			break;
			case SNORLAX:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 16:
					case 17:
					case 18:
					case 19:
					case 20:
					case 22:
					case 24:
					case 25:
					case 26:
					case 27:
					case 29:
					case 31:
					case 32:
					case 33:
					case 34:
					case 35:
					case 36:
					case 38:
					case 40:
					case 44:
					case 46:
					case 48:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case ARTICUNO:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 4:
					case 6:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 43:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 2:
						canLearn = true;
				}
			}
			break;
			case ZAPDOS:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 4:
					case 6:
					case 9:
					case 10:
					case 15:
					case 20:
					case 24:
					case 25:
					case 31:
					case 32:
					case 33:
					case 34:
					case 39:
					case 43:
					case 44:
					case 45:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 5:
						canLearn = true;
				}
			}
			break;
			case MOLTRES:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 4:
					case 6:
					case 9:
					case 10:
					case 15:
					case 20:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 43:
					case 44:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 2:
						canLearn = true;
				}
			}
			break;
			case DRATINI:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 20:
					case 23:
					case 24:
					case 25:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 40:
					case 44:
					case 45:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
						canLearn = true;
				}
			}
			break;
			case DRAGONAIR:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 20:
					case 23:
					case 24:
					case 25:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 40:
					case 44:
					case 45:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
						canLearn = true;
				}
			}
			break;
			case DRAGONITE:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 2:
					case 6:
					case 7:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 20:
					case 23:
					case 24:
					case 25:
					case 31:
					case 32:
					case 33:
					case 34:
					case 38:
					case 39:
					case 40:
					case 44:
					case 45:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 3:
					case 4:
						canLearn = true;
				}
			}
			break;
			case MEWTWO:
			if (tm.type == Item.Type.TM)
			{
				switch(tm.mNo)
				{
					case 1:
					case 5:
					case 6:
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
					case 13:
					case 14:
					case 15:
					case 16:
					case 17:
					case 18:
					case 19:
					case 20:
					case 22:
					case 24:
					case 25:
					case 29:
					case 30:
					case 31:
					case 32:
					case 33:
					case 34:
					case 35:
					case 36:
					case 38:
					case 40:
					case 44:
					case 45:
					case 46:
					case 49:
					case 50:
						canLearn = true;
				}
			}
			else if (tm.type == Item.Type.HM)
			{
				switch(tm.mNo)
				{
					case 4:
					case 5:
						canLearn = true;
				}
			}
			break;
			case MEW:
			canLearn = true;
			break;
		}

		return canLearn;
	}
}