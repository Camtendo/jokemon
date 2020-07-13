//////////////////
//Battle Class
//
//Jamtendian Battle Engine
//
//Initiates a Battle
//Written by Camtendo, Implemented by Justinian
//
//Players in a battle are referred to by integers
//0=User
//1=enemy

import java.net.URL;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.swing.JApplet;
import java.applet.AudioClip;

public final class Battle
{
	public static String BATTLE_TYPE;
	public static boolean TERMINATE=false;
	public static boolean BATTLE_OVER=false;
	public static boolean IMPORTED=false;

	//User Variables
	public static Pokemon[] user;
	private static int userNumOfPokemon;
	public static int userIndex=0, userCmd=0, userLockedMove=-1;
	public static boolean userFainted=false;
	private static final Pokemon[] revert=new Pokemon[6];
	private static final Pokemon.Species[] revertSpecies=new Pokemon.Species[6];

	//User Command Boolean
	private static boolean superDuperBoolean = false;
	private static boolean dudeCancel = false;
	private static boolean switchCancel = false;

	//Enemy Variables
	public static Pokemon[] enemy;
	private static int enemyNumOfPokemon;
	public static int enemyIndex=0, enemyCmd=0, enemyLockedMove=-1;
	public static boolean enemyFainted=false;

	//Independent Vars
	public static Pokemon caughtPokemon;
	public static Item pokeball;
	private static boolean hit, crit, sideHit, hasPP=true;
	public static int timesHit;
	public static int turn=0;
	private static final boolean[] nonPostingCase=new boolean[2];

	//Music Vars
	private static Sequencer sequencer;
	static final URL wildURL=Battle.class.getResource("Music/wildBattle.mid");
	static final URL trainerURL=Battle.class.getResource("Music/trainerBattle.mid");
	static final URL gymURL=Battle.class.getResource("Music/gymBattle.mid");
	static final URL rivalURL=Battle.class.getResource("Music/rivalBattle.mid");
	static final URL rocketURL=Battle.class.getResource("Music/javaBattle.mid");
	static final URL fatefulURL=Battle.class.getResource("Music/fatefulEncounter.mid");
	static final URL battleOverURL=Battle.class.getResource("Music/battleOver.mid");
	
	static URL eliteURL;
	static URL gameOver;

	//Sound Effect Vars
	static AudioClip brn,decStat,enemyHit,frz,incStat,par,psn,slp,special,userHit,levelUp,ko,switchPkmn,lowHP;
	static URL SEURL;

	public static BattleWindow b1;
	public static YesNoWindow ynWin;

    public static void startBattle(Pokemon u[], int userInt, Pokemon e[], int enemyInt, String battleType, BattleWindow b2)
	{
		TERMINATE=false;
		caughtPokemon=null;

		for(int i=0; i<revert.length; i++)
		{
			revert[i]=null;
		}

		System.out.println();
		System.out.println("Starting Pokemon Battle...");
		BATTLE_TYPE=battleType;
		BATTLE_OVER=false;

		if(!IMPORTED)
    	setSoundEffects();

		b1=b2;
		
		if(BATTLE_TYPE.equals("SUPER"))
		{
			if(b1.trainer.type==Trainer.TrainerType.ELITE)
			{
				eliteURL=Battle.class.getResource("Music/PowerTrainers/" + b1.trainer.name + ".mid");
			}
			else
			{
				eliteURL=Battle.class.getResource("Music/PowerTrainers/Babb.mid");
			}
		}
		b1.cursorLock=true;
		turn=0;
		user=u;
		userNumOfPokemon=userInt;
		enemy=e;
		enemyNumOfPokemon=enemyInt;

		for(int i=0; i<6; i++)
		{
			if(user[i]!=null)
			{
				revertSpecies[i]=user[i].species;
				user[i].atkStage=0;
				user[i].defStage=0;
				user[i].speedStage=0;
				user[i].spclStage=0;
				user[i].evaStage=0;
				user[i].accuracyStage=0;
			}
		}

		loopMusic();

		Mechanics.canAttack[0]=true;
		Mechanics.canAttack[1]=true;
		userLockedMove=-1;
		enemyLockedMove=-1;

		enemyIndex=0;

		userCmd=0;
		enemyCmd=0;

		for(int i=0; i<userNumOfPokemon; i++)
		{
			if(user[i].health>0)
			{
				userIndex=i;
				if(user[i].health<user[i].healthMax/5)
				{
			    	stopMusic();
				}
				break;
			}

		}

		Mechanics.initialize();
		runBattle();
	}

	public static void setSoundEffects()
	{
		System.out.println("Importing audio and sound effects...");
		try
		{
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
		}
		catch (Exception ignored){}

		SEURL=Battle.class.getResource("SE/brn.wav");
		brn=JApplet.newAudioClip(SEURL);

		SEURL=Battle.class.getResource("SE/decStat.wav");
		decStat=JApplet.newAudioClip(SEURL);

		SEURL=Battle.class.getResource("SE/enemyHit.wav");
		enemyHit=JApplet.newAudioClip(SEURL);

		SEURL=Battle.class.getResource("SE/frz.wav");
		frz=JApplet.newAudioClip(SEURL);

		SEURL=Battle.class.getResource("SE/incStat.wav");
		incStat=JApplet.newAudioClip(SEURL);

		SEURL=Battle.class.getResource("SE/par.wav");
		par=JApplet.newAudioClip(SEURL);

		SEURL=Battle.class.getResource("SE/psn.wav");
		psn=JApplet.newAudioClip(SEURL);

		SEURL=Battle.class.getResource("SE/slp.wav");
		slp=JApplet.newAudioClip(SEURL);

		SEURL=Battle.class.getResource("SE/special.wav");
		special=JApplet.newAudioClip(SEURL);

		SEURL=Battle.class.getResource("SE/userHit.wav");
		userHit=JApplet.newAudioClip(SEURL);

		SEURL=Battle.class.getResource("SE/levelUp.mid");
		levelUp=JApplet.newAudioClip(SEURL);

		SEURL=Battle.class.getResource("SE/ko.wav");
		ko=JApplet.newAudioClip(SEURL);

		SEURL=Battle.class.getResource("SE/switchPkmn.wav");
		switchPkmn=JApplet.newAudioClip(SEURL);

		SEURL=Battle.class.getResource("SE/lowHP.wav");
		lowHP=JApplet.newAudioClip(SEURL);

		gameOver=Battle.class.getResource("Music/gameOver.mid");

		IMPORTED=true;
		System.out.println("Finished importing audio and sound effects.");
	}

	public static void runBattle()
	{
		b1.cursorLock = true;
		if(!BATTLE_TYPE.equals("WILD"))
		{
			b1.addText(b1.trainer.type+" "+b1.trainer.name+" wants to fight!");
			try
			{
				Thread.sleep(3000);
			}
			catch(Exception ignored){}

			b1.showingTrainer=false;
			b1.addText(b1.trainer.type+" "+b1.trainer.name+" sent out "+enemy[enemyIndex].nickname+"!");
		}
		else
		{
			b1.addText("Wild "+enemy[enemyIndex].nickname+" appeared!");

			try
			{
				Thread.sleep(2000);
			}
			catch(Exception ignored){}

			b1.showingTrainer=false;
		}

		try
		{
			Thread.sleep(1000);
		}
		catch(Exception ignored){}

		b1.addText("GO! "+user[userIndex].nickname+"!");
		switchPkmn.play();

		System.out.println("Battle Type: "+BATTLE_TYPE);
		do
		{
			turn++;

			if (userLockedMove == -1)
			userCmd=0;
			else
				userCmd=userLockedMove;
			if (enemyLockedMove == -1)
			enemyCmd=0;
			else
				enemyCmd=enemyLockedMove;

			fixPPBug();
			while(b1.flicker[0] || b1.flicker[1])
			{

			}

			for(int i=0; i<userNumOfPokemon; i++)
			{
				if(user[i].originalTrainer==null||(!user[i].originalTrainer.equals(JokemonDriver.name)&&user[i].idNumber!=JokemonDriver.trainerIdNumber))
					user[i].IS_TRADED=true;
			}

			b1.addText("=========================");
			b1.addText("Turn: "+turn);

			b1.jsb.repaint();
			b1.text.repaint();
			b1.scroll.repaint();

			Inventory.throwingPokeBall=false;

			dudeCancel=false;
			if(userLockedMove==-1)
			b1.cursorLock = false;

			b1.updateUserPoke(user);

			b1.setScroll();

			b1.tooFront();

			Mechanics.participatedInBattle[userIndex]=true;

			userFainted=false;
			enemyFainted=false;
			if(Mechanics.turnsMultiTurn[1]<=0||user[userIndex].move[userCmd].sideEffect==Pokemon.Side_Effect.CHARGE)
			Mechanics.canAttack[0]=true;
			if(Mechanics.turnsMultiTurn[0]<=0||enemy[enemyIndex].move[enemyCmd].sideEffect==Pokemon.Side_Effect.CHARGE)
			Mechanics.canAttack[1]=true;

			if (userLockedMove == -1)
			userCmd=-5;

			//Print Current Battle Progress and Choose Command
			do
			{
				Pokedex.seen(enemy[enemyIndex].pokedexNumber-1);
				b1.updateUserPoke(user);
				if (userCmd==-1&&turn>1)
				{
					System.out.println("Dude");
					break;
				}
				if(userLockedMove==-1)
				printBattleProgress();
				else
				{
					try
					{
						Thread.sleep(3000);
					}
					catch(Exception ignored){}
				}

				if(userCmd==4)
				{
					//System.out.println("Return "+user[userIndex].nickname+"!");

					int randPhrase=(int)(Math.random()*3);

					if(randPhrase==0)
						b1.addText("Return "+user[userIndex].nickname+"!");
					else if(randPhrase==1)
						b1.addText("Good job, "+user[userIndex].nickname+"!");
					else if(randPhrase==2)
						b1.addText("That's enough "+user[userIndex].nickname+"!");

					userIndex=userSwitchPokemon();

					//System.out.println("GO! "+user[userIndex].nickname+"!");
					if(!switchCancel)
					{
						b1.addText("GO! "+user[userIndex].nickname+"!");

						try
						{
							Thread.sleep(1000);
						}
						catch(Exception ignored){}
					}
					else
					{
						b1.addText("You cancelled the switch!");
						b1.cursorLock=false;
						turn--;
					}
				}
				if(userCmd>-1&&userCmd<4)
				{
					if(Mechanics.hasPPAtAll(user[userIndex]))
					{
						if(!Mechanics.hasPP(user[userIndex].move[userCmd])||Mechanics.moveDisabled[0]==userCmd)
						userCmd=5;
					}
				}
			}
			while(userCmd<-1||userCmd>4);
			System.out.println("User Command Chosen");

			//Chooses enemy command
			if(BATTLE_TYPE.equals("WILD"))
				enemyCmd=run_AI_BASIC();
			else
				enemyCmd=run_AI_SMART();
			System.out.println("Enemy Command Chosen");

			b1.cursorLock = true;

			if(Inventory.throwingPokeBall)
				catchPokemon();

			//Checks who will move first
			if (!switchCancel&&!BATTLE_OVER)
			{
				b1.allowedToPaintExp=true;
				switch(Mechanics.determinePriority(user[userIndex],userCmd,enemy[enemyIndex],enemyCmd))
				{
					//User is first
					case 0:
						System.out.println("User outpaced Enemy");
						if (!dudeCancel)
						{
							checkPreMoveStatus(0);
							if(userCmd>=0&&userCmd<4&&Mechanics.canAttack[0]&&!userFainted&&Mechanics.hasRemainingPokemon(user,userNumOfPokemon))
							{

								if(user[userIndex].move[userCmd].mainEffect==Pokemon.Primary_Effect.RAISE_STAT||
									user[userIndex].move[userCmd].mainEffect==Pokemon.Primary_Effect.LOWER_STAT&&!nonPostingCase[0])
								{
									if(!Mechanics.awayFromBattle[1])
									hit=true;
									postAttackInfo(0);
								}

								useMove(0,userCmd);

								if(user[userIndex].move[userCmd].mainEffect==Pokemon.Primary_Effect.DAMAGE||
									user[userIndex].move[userCmd].mainEffect==Pokemon.Primary_Effect.INFLICT_STATUS||
									user[userIndex].move[userCmd].mainEffect==Pokemon.Primary_Effect.SPECIAL&&!nonPostingCase[0])
								{
									postAttackInfo(0);
								}
							}
							checkPostMoveStatus(0);

							hit=false;
							sideHit=false;
							crit=false;
						}

						//Forces a Switch of Fainted Pokemon
						if(!Mechanics.isAlive(user[userIndex].health)&&Mechanics.hasRemainingPokemon(user,userNumOfPokemon))
						{
							try
							{
								Thread.sleep(2000);
								b1.addText("");
							}
							catch(Exception ignored){}
							b1.addText(user[userIndex].nickname+" fainted!");
							ko.play();
							userIndex=userSwitchPokemon();
							b1.addText("GO! "+user[userIndex].nickname+"!");

							userFainted=true;
							Mechanics.canAttack[1]=false;

							try
							{
								Thread.sleep(2000);
							}
							catch(Exception ignored){}
						}
						if(!Mechanics.isAlive(enemy[enemyIndex].health)&&Mechanics.hasRemainingPokemon(enemy,enemyNumOfPokemon))
						{
							try
							{
								Thread.sleep(2000);
								b1.addText("");
							}
							catch(Exception ignored){}
							b1.addText("Enemy "+enemy[enemyIndex].nickname+" fainted!");
							ko.play();

							Mechanics.calcExp(enemy[enemyIndex]);
							awardExp();

							enemyIndex=enemySwitchPokemon();
							b1.addText(b1.trainer.type+" "+b1.trainer.name+" sent out "+enemy[enemyIndex].nickname);
							enemyFainted=true;

							try
							{
								Thread.sleep(2000);
							}
							catch(Exception ignored){}
						}

						checkPreMoveStatus(1);
						if(enemyCmd>=0&&enemyCmd<4&&Mechanics.canAttack[1]&&!enemyFainted&&Mechanics.hasRemainingPokemon(enemy,enemyNumOfPokemon))
						{
							try
							{
								Thread.sleep(3000);
								b1.addText("");
							}
							catch(Exception ignored){}

							if(hasPP&&enemy[enemyIndex].move[enemyCmd].mainEffect==Pokemon.Primary_Effect.RAISE_STAT||
								enemy[enemyIndex].move[enemyCmd].mainEffect==Pokemon.Primary_Effect.LOWER_STAT&&!nonPostingCase[1])
							{
								if(!Mechanics.awayFromBattle[0])
								hit=true;
								postAttackInfo(1);
							}

							useMove(1,enemyCmd);

							if(hasPP&&enemy[enemyIndex].move[enemyCmd].mainEffect==Pokemon.Primary_Effect.DAMAGE||
								enemy[enemyIndex].move[enemyCmd].mainEffect==Pokemon.Primary_Effect.INFLICT_STATUS||
								enemy[enemyIndex].move[enemyCmd].mainEffect==Pokemon.Primary_Effect.SPECIAL&&!nonPostingCase[1])
							{
								postAttackInfo(1);
							}
						}
						checkPostMoveStatus(1);

						hit=false;
						sideHit=false;
						crit=false;

						break;
					//Enemy is first
					case 1:
						System.out.println("Enemy outpaced User");
						checkPreMoveStatus(1);
						if(enemyCmd>=0&&enemyCmd<4&&Mechanics.canAttack[1]&&!enemyFainted&&Mechanics.hasRemainingPokemon(enemy,enemyNumOfPokemon))
						{
							if(hasPP&&enemy[enemyIndex].move[enemyCmd].mainEffect==Pokemon.Primary_Effect.RAISE_STAT||
								enemy[enemyIndex].move[enemyCmd].mainEffect==Pokemon.Primary_Effect.LOWER_STAT&&!nonPostingCase[1])
							{
								if(!Mechanics.awayFromBattle[0])
								hit=true;
								postAttackInfo(1);
							}

							useMove(1,enemyCmd);

							if(hasPP&&enemy[enemyIndex].move[enemyCmd].mainEffect==Pokemon.Primary_Effect.DAMAGE||
								enemy[enemyIndex].move[enemyCmd].mainEffect==Pokemon.Primary_Effect.INFLICT_STATUS||
								enemy[enemyIndex].move[enemyCmd].mainEffect==Pokemon.Primary_Effect.SPECIAL&&!nonPostingCase[1])
							{
								postAttackInfo(1);
							}

						}
						checkPostMoveStatus(1);

						hit=false;
						sideHit=false;
						crit=false;

						//Forces a Switch of Fainted Pokemon
						if(!Mechanics.isAlive(user[userIndex].health)&&Mechanics.hasRemainingPokemon(user,userNumOfPokemon))
						{
							try
							{
								Thread.sleep(2000);
								b1.addText("");
							}
							catch(Exception ignored){}

							b1.addText(user[userIndex].nickname+" fainted!");
							ko.play();
							userIndex=userSwitchPokemon();
							b1.addText("GO! "+user[userIndex].nickname+"!");

							userFainted=true;

							try
							{
								Thread.sleep(2000);
							}
							catch(Exception ignored){}
						}
						if(!Mechanics.isAlive(enemy[enemyIndex].health)&&Mechanics.hasRemainingPokemon(enemy,enemyNumOfPokemon))
						{
							try
							{
								Thread.sleep(2000);
								b1.addText("");
							}
							catch(Exception ignored){}

							b1.addText("Enemy "+enemy[enemyIndex].nickname+" fainted!");
							ko.play();

							Mechanics.calcExp(enemy[enemyIndex]);
							awardExp();

							enemyIndex=enemySwitchPokemon();
							b1.addText(b1.trainer.type+" "+b1.trainer.name+" sent out "+enemy[enemyIndex].nickname);

							enemyFainted=true;
							Mechanics.canAttack[0]=false;

							try
							{
								Thread.sleep(2000);
							}
							catch(Exception ignored){}

						}
						if (!dudeCancel)
						{
							try
							{
								Thread.sleep(3000);
								b1.addText("");
							}
							catch(Exception ignored){}

							checkPreMoveStatus(0);
							if(userCmd>=0&&userCmd<4&&Mechanics.canAttack[0]&&!userFainted&&Mechanics.hasRemainingPokemon(user,userNumOfPokemon))
							{
								if(user[userIndex].move[userCmd].mainEffect==Pokemon.Primary_Effect.RAISE_STAT||
									user[userIndex].move[userCmd].mainEffect==Pokemon.Primary_Effect.LOWER_STAT&&!nonPostingCase[0])
								{
									if(!Mechanics.awayFromBattle[1])
									hit=true;
									postAttackInfo(0);
								}

								useMove(0,userCmd);

								if(user[userIndex].move[userCmd].mainEffect==Pokemon.Primary_Effect.DAMAGE||
									user[userIndex].move[userCmd].mainEffect==Pokemon.Primary_Effect.INFLICT_STATUS||
									user[userIndex].move[userCmd].mainEffect==Pokemon.Primary_Effect.SPECIAL&&!nonPostingCase[0])
								{
									postAttackInfo(0);
								}
							}
							checkPostMoveStatus(0);

							hit=false;
							sideHit=false;
							crit=false;
						}

						break;
				}
			}

			if(Mechanics.isAlive(user[userIndex].health)&&Mechanics.hasRemainingPokemon(user,userNumOfPokemon)&&!switchCancel
				&&Mechanics.isAlive(enemy[enemyIndex].health)&&Mechanics.hasRemainingPokemon(enemy,enemyNumOfPokemon))
			{
				try
				{
					Thread.sleep(2000);
				}
				catch(Exception ignored){}
			}

			if(switchCancel)
				switchCancel = false;

			dudeCancel = false;
			b1.updateUserPoke(user);

			//Forces a Switch of Fainted Pokemon
			if(!Mechanics.isAlive(user[userIndex].health)&&Mechanics.hasRemainingPokemon(user,userNumOfPokemon))
			{
				b1.addText(user[userIndex].nickname+" fainted!");
				ko.play();
				userIndex=userSwitchPokemon();
				b1.addText("GO! "+user[userIndex].nickname+"!");

				userFainted=true;

				try
				{
					Thread.sleep(2000);
					b1.addText("");
				}
				catch(Exception ignored){}
			}
			if(!Mechanics.isAlive(enemy[enemyIndex].health)&&Mechanics.hasRemainingPokemon(enemy,enemyNumOfPokemon))
			{
				b1.addText("Enemy "+enemy[enemyIndex].nickname+" fainted!");
				ko.play();

				Mechanics.calcExp(enemy[enemyIndex]);
				awardExp();

				enemyIndex=enemySwitchPokemon();
				b1.addText(b1.trainer.type+" "+b1.trainer.name+" sent out "+enemy[enemyIndex].nickname);

				enemyFainted=true;

				try
				{
					Thread.sleep(2000);
					b1.addText("");
				}
				catch(Exception ignored){}
			}

			nonPostingCase[0]=false;
			nonPostingCase[1]=false;
			checkFainted();
			effectDecrementor(0);
			effectDecrementor(1);

		}
		while(!BATTLE_OVER&&Mechanics.hasRemainingPokemon(user,userNumOfPokemon)&&Mechanics.hasRemainingPokemon(enemy,enemyNumOfPokemon));

		b1.cursorLock = true;
		b1.addText("");

		try
		{
			Thread.sleep(2000);
		}
		catch(Exception ignored){}

		if(BATTLE_OVER)
		{
			System.out.println("Battle Ended Unnaturally");
		}
		else
		{
			if(Mechanics.hasRemainingPokemon(user,userNumOfPokemon))
			{
				b1.addText("Enemy "+enemy[enemyIndex].nickname+" fainted!");
				ko.play();
				try
				{
					Thread.sleep(3000);
				}
				catch(Exception ignored){}

				Mechanics.calcExp(enemy[enemyIndex]);
				awardExp();
				checkEvolutions();
				b1.addText("You Win!");
				if(!BATTLE_TYPE.equals("WILD"))
				{
					int moneyGain=(int)(Math.random()*enemy[0].getBaseStatTotal())+10*enemy[0].level;
					Inventory.money+=moneyGain;
					b1.addText("Received $"+moneyGain+" for winning!");
				}
			}
			else
			{
				b1.addText(user[userIndex].nickname+" fainted!");
				ko.play();
				b1.addText("You have no usable Pokemon!");
				try
				{
					Thread.sleep(3000);
				}
				catch(Exception ignored){}
				b1.addText("You black out!");
				try
				{
					Thread.sleep(3000);
				}
				catch(Exception ignored){}

				b1.addText("Warped to last Pokemon Center!");
				//gameOver.play();
				loopAudioAsset(gameOver);
				loopMusic();

				stopMusic();

				JokemonDriver.area = JokemonDriver.Area.Pokecenter;
				JokemonDriver.location.x = 4;
				JokemonDriver.location.y = 3;
				JokemonDriver.direction = 90;
				JokemonDriver.makeTheArea = true;
				JokemonDriver.performingAction = true;
				JokemonDriver.setTransition(true);
			}
		}

		b1.addText("========Battle Over========");

		b1.allowedToPaintExp=true;
		b1.updateUserPoke(user);
		fixPPBug();
		//stopLoopLowHP();

		if(!BATTLE_OVER)
		{
			stopMusic();
	    	if(Mechanics.hasRemainingPokemon(user,userNumOfPokemon))
	    	loopAudioAsset(battleOverURL);
	    	BATTLE_OVER=true;
	    	lowHP.stop();
		}

		try
		{
			Thread.sleep(1000);
		}
		catch(Exception ignored){}

		b1.addText("==Press Any Key to Continue==");

		for(int i=0; i<6; i++)
		{
			if(revert[i]!=null)
			{
				revert[i].status=user[i].status;
				revert[i].exp=user[i].exp;
				user[i]=revert[i];
				user[i].species=revertSpecies[i];
				System.out.println(user[i]);
			}
		}
		
		for(int i=0; i<6; i++)
		{
			if(user[i]!=null)
			{
				user[i].atkStage=0;
				user[i].defStage=0;
				user[i].speedStage=0;
				user[i].spclStage=0;
				user[i].evaStage=0;
				
				user[i].atk=Mechanics.calcOtherStat(user[i].baseATK,user[i].ATK_IV,user[i].ATK_EV,user[i].level);
				user[i].def=Mechanics.calcOtherStat(user[i].baseDEF,user[i].DEF_IV, user[i].DEF_EV,user[i].level);
				user[i].spcl=Mechanics.calcOtherStat(user[i].baseSPCL,user[i].SPCL_IV,user[i].SPCL_EV,user[i].level);
				user[i].speed=Mechanics.calcOtherStat(user[i].baseSPEED,user[i].SPEED_IV,user[i].SPEED_EV,user[i].level);
			}
		}

		b1.createPokemonImages();

		while(!TERMINATE)
		{
			try
			{
				Thread.sleep(10);
			}
			catch(Exception ignored){}
		}

		/*gameOver.stop();
		battleOver.stop();*/
		lowHP.stop();
		stopMusic();

		System.out.println("Battle Ended");
		System.out.println();

		b1.endBattleWindow();
	}

	//Method to change Pokemon
	public static int userSwitchPokemon()
	{
		int temp=0;
		b1.cursorLock=false;

		System.out.println("User Initiated Pokemon Switch");
		b1.updateUserPoke(user);
		b1.menuSetting = BattleWindow.MenuSetting.SWITCH;
		for(int i=0; i<userNumOfPokemon; i++)
		{
			if(i!=userIndex)
				System.out.println(""+i+": "+user[i].toHPOnlyString());
		}

		do
		{
			System.out.print("Switch to:");
			while (!superDuperBoolean)
			{	try
				{
					Thread.sleep(15);
				}
				catch(Exception ignored)
				{

				}
			}
			superDuperBoolean = false;
			if (userCmd == -1&&Mechanics.isAlive(user[userIndex].health))
			{
				System.out.println("Cancelling switch...");
				switchCancel = true;
				userCmd=4;
				switchPkmn.play();
				temp = userCmd;
				return userIndex;
			}
			else if(userCmd==-1)
			{
				temp=userIndex;
			}
			else
			{
				switchPkmn.play();
				temp = userCmd;
			}
		}
		while(!Mechanics.isAlive(user[temp].health));

		//Restores Stages to Neutral
		user[userIndex].atkStage=0;
		user[userIndex].defStage=0;
		user[userIndex].speedStage=0;
		user[userIndex].spclStage=0;
		user[userIndex].evaStage=0;

		user[userIndex].substatus=Pokemon.Substatus.OK;
		Mechanics.hasSubstitute[0]=false;
		Mechanics.substituteHealth[0]=0;

		//Unlocks move for user
		userLockedMove=-1;
		Mechanics.charging[0]=false;
		Mechanics.awayFromBattle[0]=false;
		Mechanics.turnsDisabled[0]=0;
		Mechanics.moveDisabled[0]=-1;
		Mechanics.turnsMultiTurn[0]=0;

		Mechanics.turnsMultiTurn[1]=0;

		//Restores enemy move ONLY if the user has fainted
		if(userFainted)
		{
			Mechanics.charging[1]=false;
			if(enemy[enemyIndex].move[enemyCmd]!=Pokemon.Move.RAGE)
			enemyLockedMove=-1;
		}
		userCmd = 0;
		dudeCancel = true;

		nonPostingCase[0]=false;
		userFainted=false;

		Mechanics.participatedInBattle[temp]=true;
		b1.stopFlicker(0);
		b1.cursorLock=true;
		return temp;

	}

	//Method to change Pokemon
	public static int enemySwitchPokemon()
	{
		System.out.println("Enemy Initiated Pokemon Switch");
		int temp=0;

		do
		{
			temp=(int)(Math.random()*enemyNumOfPokemon);
		}
		while(!Mechanics.isAlive(enemy[temp].health));

		enemy[enemyIndex].atkStage=0;
		enemy[enemyIndex].defStage=0;
		enemy[enemyIndex].speedStage=0;
		enemy[enemyIndex].spclStage=0;
		enemy[enemyIndex].evaStage=0;

		enemy[enemyIndex].substatus=Pokemon.Substatus.OK;
		Mechanics.hasSubstitute[1]=false;
		Mechanics.substituteHealth[1]=0;

		Mechanics.awayFromBattle[1]=false;
		Mechanics.turnsDisabled[1]=0;
		Mechanics.moveDisabled[1]=-1;


		//Restores user move ONLY if the enemy has fainted
		if(enemyFainted)
		{
			Mechanics.charging[0]=false;
			if(user[userIndex].move[userCmd]!=Pokemon.Move.RAGE)
			userLockedMove=-1;
		}

		Mechanics.turnsMultiTurn[0]=0;

		Mechanics.participationReset();

		//Unlocks enemy move
		Mechanics.turnsMultiTurn[1]=0;
		enemyLockedMove=-1;
		Mechanics.charging[1]=false;
		b1.enemySelected = enemyIndex;

		switchPkmn.play();
		b1.stopFlicker(1);
		return temp;

	}

	//Awards Exp to Pokemon that participated in battle
	public static void awardExp()
	{
		System.out.println("Experience Points Awarded");
		if(!BATTLE_TYPE.equals("WILD"))
			Mechanics.expYield*=1.8;

		int divisor=0;

		//Exp All
		if(Inventory.hasItem(new Item(Item.Type.EXPERIENCE_ALL,1)))
		{
			b1.addText("Experience All spread out all the experience!");

			for(int i=0; i<userNumOfPokemon; i++)
			{
				if(user[i].status!=Pokemon.Status.FNT)
				{
					Mechanics.participatedInBattle[i]=true;
				}
			}
		}
		//Fixes Exp Yield based on Pokemon that participated
		for(int i=0; i<userNumOfPokemon; i++)
		{
			if(Mechanics.participatedInBattle[i]&&user[i].health>0)
				divisor++;
		}

		if(divisor<1)
			divisor=1;

		Mechanics.expYield/=divisor;

		b1.addText("");

		//Actually awards Exp
		for(int i=0; i<userNumOfPokemon; i++)
		{
			if(Mechanics.participatedInBattle[i]&&user[i].health>0)
			{
				user[i].exp+=Mechanics.expYield;
				//Awards Effort Values
				user[i].HP_EV+=enemy[enemyIndex].baseHP;
				user[i].ATK_EV+=enemy[enemyIndex].baseATK;
				user[i].DEF_EV+=enemy[enemyIndex].baseDEF;
				user[i].SPCL_EV+=enemy[enemyIndex].baseSPCL;
				user[i].SPEED_EV+=enemy[enemyIndex].baseSPEED;

				if(user[i].HP_EV>256*256)
					user[i].HP_EV=256*256;
				if(user[i].ATK_EV>256*256)
					user[i].ATK_EV=256*256;
				if(user[i].DEF_EV>256*256)
					user[i].DEF_EV=256*256;
				if(user[i].SPCL_EV>256*256)
					user[i].SPCL_EV=256*256;
				if(user[i].SPEED_EV>256*256)
					user[i].SPEED_EV=256*256;

				if(user[i].IS_TRADED)
				{
					Mechanics.expYield*=1.5;
					b1.addText(user[i].nickname+" gained a boosted "+(int)Mechanics.expYield+" Exp. Points!");
					Mechanics.expYield/=1.5;
				}
				else
				b1.addText(user[i].nickname+" gained "+(int)Mechanics.expYield+" Exp. Points!");

				try
				{
					Thread.sleep(3000);
				}
				catch(Exception ignored){}

				checkLevel();
			}
		}
	}

	//Method to catch Pokemon
	public static void catchPokemon()
	{
		b1.tryToCatch();

		if(Mechanics.caughtPokemon(Battle.enemy[0],pokeball))
		{
			caughtPokemon=Battle.enemy[0];
			b1.addText("You caught "+Battle.caughtPokemon.nickname+"!");
			b1.addText("Pokedex data was added for "+Battle.caughtPokemon.nickname+"!");
			caughtPokemon.originalTrainer=JokemonDriver.name;
			caughtPokemon.idNumber=JokemonDriver.trainerIdNumber;
			Pokedex.caught(enemy[0].pokedexNumber-1);

			BATTLE_OVER=true;
		}
		else
		{
			b1.failedCatch();
			b1.addText("Aww! It appeared to be caught!");
		}
	}

	public static int getLevel(int i)
	{
		return user[i].level;
	}

	public static int getExp(int i)
	{
		return user[i].exp;
	}

	//Checks all Pokemon Levels and Awards Stats
	public static void checkLevel()
	{
		System.out.println("User Pokemon Levels signed and exported");

		for(int i=0; i<userNumOfPokemon; i++)
		{
			if(user[i].level<100&&Mechanics.participatedInBattle[i])
			{
				while(user[i].exp>=user[i].level*user[i].level*user[i].level&&user[i].level<100)
				{
					levelUp.play();
					user[i].level++;
					int hpGain=user[i].healthMax;
					user[i].healthMax=Mechanics.calcHPStat(user[i].baseHP,user[i].HP_IV,user[i].HP_EV,user[i].level);
					hpGain=user[i].healthMax-hpGain;
					user[i].health+=hpGain;
					user[i].atk=Mechanics.calcOtherStat(user[i].baseATK,user[i].ATK_IV,user[i].ATK_EV,user[i].level);
					user[i].def=Mechanics.calcOtherStat(user[i].baseDEF,user[i].DEF_IV,user[i].DEF_EV,user[i].level);
					user[i].spcl=Mechanics.calcOtherStat(user[i].baseSPCL,user[i].SPCL_IV,user[i].SPCL_EV,user[i].level);
					user[i].speed=Mechanics.calcOtherStat(user[i].baseSPEED,user[i].SPEED_IV,user[i].SPEED_EV,user[i].level);

					b1.addText("");
					b1.addText(user[i].nickname+" grew to LV. "+user[i].level+"!");
					b1.addText("HP MAX: "+user[i].healthMax);
					b1.addText("ATK: "+user[i].atk);
					b1.addText("DEF: "+user[i].def);
					b1.addText("SPCL: "+user[i].spcl);
					b1.addText("SPEED: "+user[i].speed);
					b1.addText("");
					Pokemon.Move[] moves = new Pokemon.Move[4];
                    System.arraycopy(user[i].move, 0, moves, 0, 4);
					user[i].move = Mechanics.levelUpMove(user[i],false);
					for (int j = 0; j<4; j++)
					{
						if (user[i].move[j] != moves[j])
						{
							b1.addText(user[i].nickname + " learned " + user[i].move[j].toString() + "!");
						}
					}

					try
					{
						Thread.sleep(3000);
					}
					catch(Exception ignored){}
				}
			}
			else if(user[i].level>=100)
			{
				user[i].level=100;
				int hpGain=user[i].healthMax;
				user[i].healthMax=Mechanics.calcHPStat(user[i].baseHP,user[i].HP_IV,user[i].HP_EV,user[i].level);
				hpGain=user[i].healthMax-hpGain;
				user[i].health+=hpGain;
				user[i].atk=Mechanics.calcOtherStat(user[i].baseATK,user[i].ATK_IV,user[i].ATK_EV,user[i].level);
				user[i].def=Mechanics.calcOtherStat(user[i].baseDEF,user[i].DEF_IV,user[i].DEF_EV,user[i].level);
				user[i].spcl=Mechanics.calcOtherStat(user[i].baseSPCL,user[i].SPCL_IV,user[i].SPCL_EV,user[i].level);
				user[i].speed=Mechanics.calcOtherStat(user[i].baseSPEED,user[i].SPEED_IV,user[i].SPEED_EV,user[i].level);

				if(user[i].health>user[i].healthMax)
					user[i].health=user[i].healthMax;
			}
		}
	}

	//Checks for potential Evolutions
	public static void checkEvolutions()
	{
		for(int i=0; i<userNumOfPokemon; i++)
		{
			if(user[i].status!=Pokemon.Status.FNT&&Mechanics.participatedInBattle[i]&&(user[i].evolve(user[i])!=user[i].species||user[i].evolve(user[i],JokemonDriver.trainerIdNumber)!=user[i].species))
			{
				ynWin=new YesNoWindow();
				ynWin.addMessage("What? "+user[i].nickname+" is evolving!","Allow Evolution?");
				while(ynWin.isVisible())
				{
					System.out.println("Wating for evolution decision...");
					try
					{
						Thread.sleep(1000);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
				}
				if(ynWin.getYes())
				{
					b1.addText(user[i].nickname+" evolved into ");
					if(user[i].evolve(user[i])!=user[i].species)
					{
						System.out.println(user[i].evolve(user[i]));
						user[i]=user[i].evolution(user[i],user[i].evolve(user[i]));
						user[i].move = Mechanics.levelUpMove(user[i],true);
						b1.addText(user[i].toString(user[i].species)+"!");
					}
					else
					{
						System.out.println(user[i].evolve(user[i],JokemonDriver.trainerIdNumber));
						user[i]=user[i].evolution(user[i],user[i].evolve(user[i],JokemonDriver.trainerIdNumber));
						user[i].move = Mechanics.levelUpMove(user[i],true);
						b1.addText(user[i].toString(user[i].species)+"!");
					}
					b1.createPokemonImages();
					Pokedex.caught(user[i].pokedexNumber-1);
					Pokedex.seen(user[i].pokedexNumber-1);
				}
				else
				{
					b1.addText("You cancelled the evolution...");
				}
			}
		}
	}

	//Returns true or false. Simple.
	public static void confirmEvolution(boolean boo)
	{
        boolean allowEvolve = boo;
	}

	//Lowers count for certain effects and prints information when necessary
	public static void effectDecrementor(int index)
	{
		System.out.println("Effects decremented Var: "+index);
		switch(index)
		{
			case 0:
				if(Mechanics.turnsLightScreen[index]==1)
					b1.addText("Your Light Screen wore off!");
				if(Mechanics.turnsReflect[index]==1)
					b1.addText("Your Reflect wore off!");
				if(Mechanics.turnsMist[index]==1)
					b1.addText("Your Mist evaporated!");
				if(Mechanics.turnsDisabled[index]==1)
				{
					b1.addText("You are no longer disabled!");
					Mechanics.moveDisabled[index]=-1;
				}

				if(Mechanics.turnsLightScreen[index]>0)
				Mechanics.turnsLightScreen[index]--;
				if(Mechanics.turnsReflect[index]>0)
				Mechanics.turnsReflect[index]--;
				if(Mechanics.turnsMist[index]>0)
				Mechanics.turnsMist[index]--;
				if(Mechanics.turnsDisabled[index]>0)
				Mechanics.turnsDisabled[index]--;
				break;
			case 1:
				if(Mechanics.turnsLightScreen[index]==1)
					b1.addText("The enemy's Light Screen wore off!");
				if(Mechanics.turnsReflect[index]==1)
					b1.addText("The enemy's Reflect wore off!");
				if(Mechanics.turnsMist[index]==1)
					b1.addText("The enemy's Mist evaporated!");
				if(Mechanics.turnsDisabled[index]==1)
				{
					b1.addText("You are no longer disabled!");
					Mechanics.moveDisabled[index]=-1;
				}

				if(Mechanics.turnsLightScreen[index]>0)
				Mechanics.turnsLightScreen[index]--;
				if(Mechanics.turnsReflect[index]>0)
				Mechanics.turnsReflect[index]--;
				if(Mechanics.turnsMist[index]>0)
				Mechanics.turnsMist[index]--;
				if(Mechanics.turnsDisabled[index]>0)
				Mechanics.turnsDisabled[index]--;
				break;
		}
	}

	//Random AI for choosing a move
	public static int run_AI_BASIC()
	{
		System.out.println("BASIC AI Initiated");

		int temp=(int)(Math.random()*4);

		if(enemyLockedMove!=-1)
			temp=enemyLockedMove;

		//while(!Mechanics.enemyHasPP(enemy[enemyIndex].move[temp])&&(temp<0||temp>3))
		while((temp<0||temp>3)||enemy[enemyIndex].move[temp]==Pokemon.Move.NONE)
		{
			temp=(int)(Math.random()*4);

			if(temp>-1&&temp<4)
			{
				if(temp==Mechanics.moveDisabled[1])
					temp=5;
			}
		}

		return temp;
	}

	//More Intelligent AI for move choice
	public static int run_AI_SMART()
	{
		System.out.println("SMART AI Initiated");

		int bestMove=0;

		if(enemyLockedMove!=-1)
			return enemyLockedMove;

		for(int i=0; i<4; i++)
		{
			if(enemy[enemyIndex].move[i]!=Pokemon.Move.NONE&&i!=Mechanics.moveDisabled[1])
			{
				if(enemy[enemyIndex].move[i].mainEffect==Pokemon.Primary_Effect.DAMAGE&&
					Mechanics.typeMultiplier(enemy[enemyIndex].move[i].moveType,user[userIndex].type1,user[userIndex].type2)>=2)
				{
					System.out.println("Enemy sees opportunity for Super Effective hit with "+enemy[enemyIndex].move[i]+"!");
					if(enemy[enemyIndex].move[i]!=Pokemon.Move.DREAM_EATER)
					{
						bestMove=i;
						return bestMove;
					}
				}
				else if(user[userIndex].health<=user[userIndex].healthMax*.5&&
						enemy[enemyIndex].move[i].mainEffect==Pokemon.Primary_Effect.DAMAGE&&
						Mechanics.typeMultiplier(enemy[enemyIndex].move[i].moveType,user[userIndex].type1,user[userIndex].type2)>=1)
				{
					System.out.println("Enemy is going to try to faint the user!");
					bestMove=i;
					return bestMove;
				}
				else if(enemy[enemyIndex].health>=enemy[enemyIndex].healthMax*5&&
						enemy[enemyIndex].move[i].mainEffect!=Pokemon.Primary_Effect.DAMAGE)
				{
					System.out.println("Enemy is considering using a support move.");
					bestMove=i;
				}
			}
		}

		if(bestMove==0)
		{
			System.out.println("Enemy is shuffling for move choice.");

			bestMove=(int)(Math.random()*4);

			while((bestMove<0||bestMove>3)||enemy[enemyIndex].move[bestMove]==Pokemon.Move.NONE)
			{
				bestMove=(int)(Math.random()*4);

				if(bestMove>-1&&bestMove<4)
				{
					if(bestMove==Mechanics.moveDisabled[1])
						bestMove=5;
				}
			}

			System.out.println("Enemy chooses move index "+bestMove);
		}

		return bestMove;
	}

	//Method to try to run from battle
	public static void runFromBattle()
	{
		b1.addText("You try to run away...");

		if(!BATTLE_TYPE.equals("WILD"))
		{
			b1.addText("No! There's no running from a Trainer battle!");
		}
		else
		{
			if(user[userIndex].speed>enemy[enemyIndex].speed)
			{
				b1.addText("Got away safely!");
				BATTLE_OVER=true;
			}
			else
			{
				if((int)(Math.random()*10)<5)
				{
					b1.addText("Can't escape!");
				}
				else
				{
					b1.addText("Got away safely!");
					BATTLE_OVER=true;
				}
			}
		}
	}

	//Fixes PP referencing from stupid enums
	public static void fixPPBug()
	{
		System.out.println("Power Point Bug Fixed");

		for(int i=0; i<4; i++)
		{
			//if(enemy[enemyIndex].move[enemyCmd]==user[userIndex].move[i]&&user[userIndex].move[i].pp<user[userIndex].move[i].ppMax)
				//user[userIndex].move[i].pp++;

			user[userIndex].move[i].pp=user[userIndex].TRUE_PP[i];
			user[userIndex].move[i].ppMax=user[userIndex].TRUE_PPMAX[i];

			if(user[userIndex].TRUE_PP[i]<0)
				user[userIndex].TRUE_PP[i]=0;
		}
	}

	//Loops correct music
	public static void loopMusic()
	{
		if(BATTLE_TYPE.equals("WILD"))
		{
			if(!Mechanics.isLegendary(enemy[0]))
			//wildMusic.loop();
			loopAudioAsset(wildURL);
			else
			//fatefulMusic.loop();
			loopAudioAsset(fatefulURL);

		}
	    else if(BATTLE_TYPE.equals("TRAINER"))
	    {
	    	if(b1.trainer.type==Trainer.TrainerType.JAVA)
	    		//javaMusic.loop();
	    		loopAudioAsset(rocketURL);
	    	else
	    		//trainerMusic.loop();
	    	loopAudioAsset(trainerURL);
	    }
	    else if(BATTLE_TYPE.equals("SUPER"))
	    {
	    	//eliteMusic.loop();
	    	loopAudioAsset(eliteURL);
	    	//elite1M.loop();
	    }
	    else if(BATTLE_TYPE.equals("GYM"))
	    //gymMusic.loop();
	    	loopAudioAsset(gymURL);
	    else if(BATTLE_TYPE.equals("RIVAL"))
    	//rivalMusic.loop();
	    	loopAudioAsset(rivalURL);
	}

	//Stops all music
	public static void stopMusic()
	{
		stopAudioAsset();
	}

	//Loops the low HP tone
	public static void loopLowHP()
	{
		stopMusic();
		lowHP.loop();
		b1.loopLowHealthTone=true;
	}

	//Stops the low HP tone
	public static void stopLoopLowHP()
	{
		lowHP.stop();
		b1.loopLowHealthTone=false;
		if(!BATTLE_OVER)
		loopMusic();
	}

	//Applies Status effects before attempting to use a move
	public static void checkPreMoveStatus(int index)
	{
		System.out.println();
		System.out.print("Checking Preliminary Pokemon Status...");
		int randy=0;

		//User Cases
		if(index==0)
		{
			switch(user[userIndex].status)
			{
				case SLP:
					System.out.println("Sleep.");
					Mechanics.turnsAsleep[0]--;
					Mechanics.canAttack[0]=false;
					if(Mechanics.turnsAsleep[0]<=0)
					{
						Mechanics.turnsAsleep[0]=0;
						user[userIndex].status=Pokemon.Status.OK;
						b1.addText(user[userIndex].nickname+" woke up!");
					}
					else
					{
						b1.addText(user[userIndex].nickname+" is fast asleep!");
						slp.play();
						return;
					}
					break;
				case PAR:
					System.out.println("Paralyzed.");
					randy=(int)(Math.random()*4);
					if(randy==0)
					{
						b1.addText(user[userIndex].nickname+" is fully paralyzed!");
						par.play();
						Mechanics.canAttack[0]=false;
						return;
					}
					break;
				case FRZ:
					System.out.println("Frozen.");
					b1.addText(user[userIndex].nickname+" is frozen rock solid!");
					Mechanics.canAttack[0]=false;
					frz.play();
					return;
				default:
					System.out.println("Done.");
					//if(Mechanics.turnsMultiTurn[1]<1)
					//Mechanics.canAttack[0]=true;
					break;
			}
			switch(user[userIndex].substatus)
			{
				case CONFU:
					System.out.println("Confusion detected.");
					Mechanics.turnsConfused[0]--;
					if(Mechanics.turnsConfused[0]<=0)
					{
						Mechanics.turnsConfused[0]=0;
						Mechanics.isConfused[0]=false;
						user[userIndex].substatus=Pokemon.Substatus.OK;
						b1.addText(user[userIndex].nickname+" snapped out of its confusion!");
					}
					else
					{
						b1.addText(user[userIndex].nickname+" is confused!");
						randy=(int)(Math.random()*2);
						if(randy==0)
						{
							b1.addText("It hurt itself in its confusion!");
							user[userIndex].health-=user[userIndex].healthMax/8;
							Mechanics.canAttack[0]=false;
							userHit.play();
							b1.setFlicker(0);
							userLockedMove=-1;
							Mechanics.charging[0]=false;
							Mechanics.awayFromBattle[0]=false;
							Mechanics.turnsDisabled[0]=0;
							Mechanics.moveDisabled[0]=-1;
							Mechanics.turnsMultiTurn[0]=0;
							return;
						}
					}
					break;
				case FLINCH:
					System.out.println("Flinch detected.");
					b1.addText(user[userIndex].nickname+" flinched!");
					Mechanics.canAttack[0]=false;
					user[userIndex].substatus=Pokemon.Substatus.OK;
					return;
				default:
					System.out.println("No BAD Substatus found.");
					//if(Mechanics.turnsMultiTurn[1]<1)
					//Mechanics.canAttack[0]=true;
					return;
			}
		}
		//Enemy Cases
		else
		{
			switch(enemy[enemyIndex].status)
			{
				case SLP:
					System.out.println("Sleep");
					Mechanics.turnsAsleep[1]--;
					Mechanics.canAttack[1]=false;
					if(Mechanics.turnsAsleep[1]<=0)
					{
						Mechanics.turnsAsleep[1]=0;
						enemy[enemyIndex].status=Pokemon.Status.OK;
						b1.addText("Enemy "+enemy[enemyIndex].nickname+" woke up!");
					}
					else
					{
						b1.addText("Enemy "+enemy[enemyIndex].nickname+" is fast asleep!");
						slp.play();
						return;
					}
					break;
				case PAR:
					System.out.println("Paralyzed.");
					randy=(int)(Math.random()*4);
					if(randy==0)
					{
						b1.addText("Enemy "+enemy[enemyIndex].nickname+" is fully paralyzed!");
						Mechanics.canAttack[1]=false;
						par.play();
						return;
					}
					break;
				case FRZ:
					System.out.println("Frozen.");
					b1.addText("Enemy "+enemy[enemyIndex].nickname+" is frozen rock solid!");
					Mechanics.canAttack[1]=false;
					frz.play();
					return;
				default:
					System.out.println("Done.");
					//if(Mechanics.turnsMultiTurn[0]<1)
					//Mechanics.canAttack[1]=true;
					break;
			}
			switch(enemy[enemyIndex].substatus)
			{
				case CONFU:
					System.out.println("Confusion detected.");
					Mechanics.turnsConfused[1]--;
					if(Mechanics.turnsConfused[1]<=0)
					{
						Mechanics.turnsConfused[1]=0;
						Mechanics.isConfused[1]=false;
						enemy[enemyIndex].substatus=Pokemon.Substatus.OK;
						b1.addText("Enemy "+enemy[enemyIndex].nickname+" snapped out of its confusion!");
					}
					else
					{
						b1.addText("Enemy "+enemy[enemyIndex].nickname+" is confused!");
						randy=(int)(Math.random()*2);
						if(randy==0)
						{
							b1.addText("It hurt itself in its confusion!");
							enemy[enemyIndex].health-=enemy[enemyIndex].healthMax/8;
							Mechanics.canAttack[1]=false;
							enemyHit.play();
							b1.setFlicker(1);
							return;
						}
					}
					break;
				case FLINCH:
					System.out.println("Flinch detected.");
					b1.addText("Enemy "+enemy[enemyIndex].nickname+" flinched!");
					Mechanics.canAttack[1]=false;
					enemy[enemyIndex].substatus=Pokemon.Substatus.OK;
					return;
				default:
					System.out.println("No BAD enemy substatus.");
					//if(Mechanics.turnsMultiTurn[0]<1)
					//Mechanics.canAttack[1]=true;
					break;
			}
		}
	}

	//Assigns proper HP and FNT Status to fainted Pokemon
	public static void checkFainted()
	{
		System.out.print("Checking for fainted Pokemon...");

		for(int i=0; i<userNumOfPokemon; i++)
			if(user[i].health<=0)
			{
				user[i].health=0;
				user[i].status=Pokemon.Status.FNT;
			}

		for(int i=0; i<enemyNumOfPokemon; i++)
			if(enemy[i].health<=0)
			{
				enemy[i].health=0;
				enemy[i].status=Pokemon.Status.FNT;
			}
		System.out.println("Done.");
	}

	//Checks the status of a substitute and reports it
	public static void checkSubstitute(int index)
	{
		System.out.println("Substitute detected");

		if(Mechanics.hasSubstitute[index])
		{
			if(Mechanics.substituteHealth[index]<=0)
			{
				Mechanics.substituteHealth[index]=0;
				Mechanics.hasSubstitute[index]=false;

				if(index==0)
					b1.addText("Your substitute broke!");
				else
					b1.addText("The enemy's substitute broke!");
			}
		}
	}

	//Checks and applies status effects that occur after using a move
	public static void checkPostMoveStatus(int index)
	{
		System.out.println();
		System.out.print("Checking Pokemon Status Post Facto...");
		if(index==0)
		{
			switch(user[userIndex].substatus)
			{
				case SEED:
					System.out.println("Seeded.");
					if(enemy[enemyIndex].health<1)
						return;
					try
					{
						Thread.sleep(2000);
						b1.addText("");
					}
					catch(Exception ignored){}

					b1.addText(user[userIndex].nickname+"'s health was sapped by LEECH SEED!");
					Mechanics.damage=(user[userIndex].healthMax/16)+1;
					user[userIndex].health-=Mechanics.damage;
					enemy[enemyIndex].health=Mechanics.recover(enemy[enemyIndex],Mechanics.damage/2);
					try
					{
						Thread.sleep(2000);
					}
					catch(Exception ignored){}
					break;
				default:
					System.out.println("Done.");
					break;
			}
			switch(user[userIndex].status)
			{
				case PSN:
					System.out.println("User Poisoned.");

					try
					{
						Thread.sleep(2000);
						b1.addText("");
					}
					catch(Exception ignored){}

					b1.addText(user[userIndex].nickname+" suffers from poison!");
					Mechanics.damage=(user[userIndex].healthMax/16)+1;
					user[userIndex].health-=Mechanics.damage;
					psn.play();

					try
					{
						Thread.sleep(2000);
					}
					catch(Exception ignored){}

					break;
				case BRN:
					System.out.println("User Burned.");

					try
					{
						Thread.sleep(2000);
						b1.addText("");
					}
					catch(Exception ignored){}

					b1.addText(user[userIndex].nickname+" is hurt from its burn!");
					Mechanics.damage=user[userIndex].healthMax/16;
					user[userIndex].health-=Mechanics.damage;
					brn.play();

					try
					{
						Thread.sleep(2000);
					}
					catch(Exception ignored){}

					break;
			}
		}
		else
		{
			switch(enemy[enemyIndex].substatus)
			{
				case SEED:
					System.out.println("Seeded.");
					if(user[userIndex].health<1)
						return;

					try
					{
						Thread.sleep(2000);
						b1.addText("");
					}
					catch(Exception ignored){}

					b1.addText("Enemy "+enemy[enemyIndex].nickname+"'s health was sapped by LEECH SEED!");
					Mechanics.damage=enemy[enemyIndex].healthMax/16;
					enemy[enemyIndex].health-=Mechanics.damage;
					user[userIndex].health=Mechanics.recover(user[userIndex],Mechanics.damage/2);

					try
					{
						Thread.sleep(2000);
					}
					catch(Exception ignored){}

					break;
				default:
					System.out.println("Done.");
					break;
			}
			switch(enemy[enemyIndex].status)
			{
				case PSN:
					System.out.println("Enemy Poisoned.");
					b1.addText("Enemy "+enemy[enemyIndex].nickname+" suffers from poison!");
					Mechanics.damage=enemy[enemyIndex].healthMax/16;
					enemy[enemyIndex].health-=Mechanics.damage;
					psn.play();
					break;
				case BRN:
					System.out.println("Enemy Burned.");
					b1.addText("Enemy "+enemy[enemyIndex].nickname+" is hurt from its burn!");
					Mechanics.damage=enemy[enemyIndex].healthMax/16;
					enemy[enemyIndex].health-=Mechanics.damage;
					brn.play();
					break;
			}
		}
	}

	//Prints Battle Progress and Your Commands
	public static void printBattleProgress()
	{
		b1.allowedToPaintExp=true;
		//System.out.println(enemy[enemyIndex].toHPOnlyString()+" Remaining Pokemon:"+
		//Mechanics.remainingPokemon(enemy, enemyNumOfPokemon));
		//System.out.println(user[userIndex].toBattleString());
		System.out.print("Waiting for command...");
		if(Mechanics.moveDisabled[0]>-1&&Mechanics.moveDisabled[0]<4)
		{
			b1.addText(user[userIndex].move[Mechanics.moveDisabled[0]]+" is disabled!");
		}
		userCmd=-1;
		while (!superDuperBoolean)
		{	try
			{
				Thread.sleep(15);
			}
			catch(Exception ignored)
			{

			}
		}
		superDuperBoolean = false;
	}

	public static void setUserCommand(int i)
	{
		userCmd = i;
		superDuperBoolean = true;
		System.out.println("Command Set");
	}

	//Returns the updated Pokemon from the battle instance
	public static Pokemon getPokemon(int index)
	{
		return user[index];
	}

	//Uses a move. Needs Pokemon using the move and index of move
	//0 for user, 1 for CPU
	public static void useMove(int theUser, int index)
	{
		if(theUser==0)
			System.out.println("Move used: "+user[userIndex].move[index]);
		else
			System.out.println("Move used: "+enemy[enemyIndex].move[index]);

		//User Set
		if(theUser==0)
		{
			switch(user[userIndex].move[index].mainEffect)
			{
				case DAMAGE:
					if(user[userIndex].move[index].pp>0)
					{
						hasPP=true;
						//Checks if move is Special or not, then uses correct formula to calculate
						//damage.
						if(Mechanics.isSpecial(user[userIndex].move[index].moveType))
							Mechanics.calcDamage(user[userIndex].move[index].basePower,
							user[userIndex].level,user[userIndex].spcl,enemy[enemyIndex].spcl,1);
						else
							Mechanics.calcDamage(user[userIndex].move[index].basePower,
							user[userIndex].level,user[userIndex].atk,enemy[enemyIndex].def,1);

						//Adds type multiplier to damage
						Mechanics.damage*=Mechanics.typeMultiplier(user[userIndex].move[index].moveType,
						enemy[enemyIndex].type1,enemy[enemyIndex].type2);

						//Multiplies damage by 1.5 for Same Type Attack Bonus
						if(Mechanics.isSTAB(user[userIndex].type1,user[userIndex].type2,user[userIndex].move[index].moveType))
							Mechanics.damage*=1.5;

						//Multiplies damage by 2 for critical hit
						if(user[userIndex].move[index].sideEffect==Pokemon.Side_Effect.HIGH_CRIT)
							Mechanics.isHighCritical[0]=true;
						if(Mechanics.checkCritical(user[userIndex].baseSPEED,1)&&Mechanics.effective!=Mechanics.Effective.NONE)
						{
							crit=true;
							Mechanics.damage*=2;
						}

						//Reduces physical damage due to burn
						if(!Mechanics.isSpecial(user[userIndex].move[index].moveType)&&
							user[userIndex].status==Pokemon.Status.BRN)
							{
								Mechanics.damage*=0.75;
							}

						//Reduces damage when Light Screen or Reflect are up
						if(!Mechanics.isSpecial(user[userIndex].move[index].moveType)&&Mechanics.turnsReflect[1]>0)
						{
							Mechanics.damage/=2;
						}
						else if(Mechanics.isSpecial(user[userIndex].move[index].moveType)&&Mechanics.turnsLightScreen[1]>0)
						{
							Mechanics.damage/=2;
						}

						//Checks if move hit or not
						if(!Mechanics.checkHit(user[userIndex],index,enemy[enemyIndex].eva)||
							(Mechanics.awayFromBattle[1]&&user[userIndex].move[index]!=Pokemon.Move.SWIFT)&&Mechanics.turnsMultiTurn[0]<1)
						{
							Mechanics.damage=0;
							hit=false;
						}
						else
						{
							hit=true;
							if(Mechanics.damage<1&&Mechanics.effective!=Mechanics.Effective.NONE)
								Mechanics.damage=1;
						}

						if(hit&&Mechanics.hasSubstitute[1])
						{
							Mechanics.substituteHealth[1]-=Mechanics.damage;
							Mechanics.damage=0;
						}
						if (Mechanics.damage > enemy[enemyIndex].health)
						{
							Mechanics.damage = enemy[enemyIndex].health;
						}

						switch(user[userIndex].move[index].sideEffect)
						{
							case HIGH_CRIT:
							case QUICK_ATTACK:
							case SWIFT:
							case NONE:
								enemy[enemyIndex].health-=Mechanics.damage;
								  user[userIndex].TRUE_PP[index]--;
								break;
							case ABSORB:
								if(user[userIndex].move[index]==Pokemon.Move.DREAM_EATER)
								{
									if(enemy[enemyIndex].status!=Pokemon.Status.SLP)
									{
										Mechanics.damage=0;
										hit=false;
									}
								}
								user[userIndex].health=Mechanics.recover(user[userIndex],Mechanics.damage/2);
								enemy[enemyIndex].health-=Mechanics.damage;
								  user[userIndex].TRUE_PP[index]--;
								break;
							case STAT:
								if(Mechanics.checkSideEffect(user[userIndex].move[index])&&Mechanics.damage>0)
									sideHit=true;
								if(sideHit)
								{
									switch(user[userIndex].move[index])
									{
										case ACID:
											enemy[enemyIndex].defStage--;
											enemy[enemyIndex].def=(int)((Mechanics.calcOtherStat(enemy[enemyIndex].baseDEF,enemy[enemyIndex].DEF_IV,enemy[enemyIndex].DEF_EV,enemy[enemyIndex].level))*
											Mechanics.stageMultiplier(enemy[enemyIndex].defStage));
											break;
										case BUBBLE:
										case BUBBLEBEAM:
										case CONSTRICT:
											enemy[enemyIndex].speedStage--;
											enemy[enemyIndex].speed=(int)((Mechanics.calcOtherStat(enemy[enemyIndex].baseSPEED,enemy[enemyIndex].SPEED_IV,enemy[enemyIndex].SPEED_EV,enemy[enemyIndex].level))*
											Mechanics.stageMultiplier(enemy[enemyIndex].speedStage));
											break;
										case PSYCHIC:
											enemy[enemyIndex].spclStage--;
											enemy[enemyIndex].spcl=(int)((Mechanics.calcOtherStat(enemy[enemyIndex].baseSPCL,enemy[enemyIndex].SPCL_IV,enemy[enemyIndex].SPCL_EV,enemy[enemyIndex].level))*
											Mechanics.stageMultiplier(enemy[enemyIndex].spclStage));
											break;
									}
								}
								enemy[enemyIndex].health-=Mechanics.damage;
								  user[userIndex].TRUE_PP[index]--;
								break;
							case STATUS:
								if(Mechanics.checkSideEffect(user[userIndex].move[index])
									&&enemy[enemyIndex].status==Pokemon.Status.OK&&Mechanics.damage>0
										&&!Mechanics.awayFromBattle[1])
									{
										enemy[enemyIndex].status=user[userIndex].move[index].status;
										sideHit=true;
									}
								enemy[enemyIndex].health-=Mechanics.damage;
								  user[userIndex].TRUE_PP[index]--;
								break;
							case SUBSTATUS:
								if(Mechanics.checkSideEffect(user[userIndex].move[index])
									&&enemy[enemyIndex].substatus==Pokemon.Substatus.OK&&Mechanics.damage>0
										&&!Mechanics.awayFromBattle[1])
								{
									enemy[enemyIndex].substatus=user[userIndex].move[index].substatus;
									sideHit=true;
								}
								enemy[enemyIndex].health-=Mechanics.damage;
								  user[userIndex].TRUE_PP[index]--;
								break;
							case MULTI_HIT:
								int rand=(int)(Math.random()*3)+2;
								Mechanics.damage*=rand;
								timesHit=rand;
								enemy[enemyIndex].health-=Mechanics.damage;
								  user[userIndex].TRUE_PP[index]--;
								break;
							case MULTI_TURN:
								if(Mechanics.turnsMultiTurn[0]==0&&hit)
								{
									int rand2=(int)(Math.random()*4)+2;
									Mechanics.turnsMultiTurn[0]=rand2;
									enemy[enemyIndex].health-=Mechanics.damage;
									Mechanics.canAttack[1]=false;
									userLockedMove=index;
									  user[userIndex].TRUE_PP[index]--;
								}
								else if(Mechanics.turnsMultiTurn[0]==1)
								{
									enemy[enemyIndex].health-=Mechanics.damage;
									Mechanics.turnsMultiTurn[0]=0;
									Mechanics.canAttack[1]=true;
									userLockedMove=-1;
									hit=true;
								}
								else if(Mechanics.turnsMultiTurn[0]>1)
								{
									hit=true;
									enemy[enemyIndex].health-=Mechanics.damage;
									Mechanics.turnsMultiTurn[0]--;
								}
								break;
							case OHKO:
								crit=false;
								if(Mechanics.damage!=0)
								{
									if(user[userIndex].speed>enemy[enemyIndex].speed)
									{
										Mechanics.damage=Integer.MAX_VALUE;
										//Mechanics.damage=enemy[enemyIndex].health;
									}
									else
									{
										hit=false;
										Mechanics.damage=0;
									}
								}
								enemy[enemyIndex].health-=Mechanics.damage;
								  user[userIndex].TRUE_PP[index]--;
								break;
							case CHARGE:
								switch(user[userIndex].move[index])
								{
									case RAZOR_WIND:
									case SKY_ATTACK:
									case SKULL_BASH:
									case SOLARBEAM:
										if(!Mechanics.charging[0])
										{
											Mechanics.charging[0]=true;
											Mechanics.damage=0;

											userLockedMove=index;
										}
										else
										{
											Mechanics.charging[0]=false;
											enemy[enemyIndex].health-=Mechanics.damage;
											userLockedMove=-1;
											  user[userIndex].TRUE_PP[index]--;
										}
										break;
									case HYPER_BEAM:
										if(!Mechanics.charging[0])
										{
											Mechanics.charging[0]=true;
											enemy[enemyIndex].health-=Mechanics.damage;
											userLockedMove=index;
											  user[userIndex].TRUE_PP[index]--;
										}
										else
										{
											Mechanics.charging[0]=false;
											userLockedMove=-1;
											Mechanics.damage=0;
										}
										break;

								}
								break;
							case FAINT:
								enemy[enemyIndex].health-=Mechanics.damage;
								  user[userIndex].TRUE_PP[index]--;
								user[userIndex].health=0;
								break;
							case RECOIL:
								switch(user[userIndex].move[index])
								{
									case STRUGGLE:
										enemy[enemyIndex].health-=Mechanics.damage;
										user[userIndex].health-=Mechanics.damage/2;
										break;
									default:
										enemy[enemyIndex].health-=Mechanics.damage;
										user[userIndex].health-=Mechanics.damage/4;
										  user[userIndex].TRUE_PP[index]--;
										break;
								}
								break;
							case COLLECT_DAMAGE:
								switch(user[userIndex].move[index])
								{
									case BIDE:
										if(Mechanics.turnsMultiTurn[0]==0)
										{
											int rand2=(int)(Math.random()*2)+2;
											Mechanics.turnsMultiTurn[0]=rand2;
											Mechanics.storedDamage[0]=0;
											userLockedMove=index;
											Mechanics.damage=0;
										}
										else if(Mechanics.turnsMultiTurn[0]==1)
										{
											enemy[enemyIndex].health-=Mechanics.storedDamage[0];
											Mechanics.turnsMultiTurn[0]=0;
											Mechanics.storedDamage[0]=0;
											userLockedMove=-1;
											Mechanics.damage=0;
											  user[userIndex].TRUE_PP[index]--;
										}
										else if(Mechanics.turnsMultiTurn[0]>1)
										{
											Mechanics.turnsMultiTurn[0]--;
										}
										break;
									case COUNTER:
										if(enemy[enemyIndex].move[enemyCmd].moveType==Pokemon.Type.NORMAL||
											enemy[enemyIndex].move[enemyCmd].moveType==Pokemon.Type.FIGHTING)
										{
											enemy[enemyIndex].health-=Mechanics.damage*2;
										}
										else
											hit=false;
										  user[userIndex].TRUE_PP[index]--;
										break;
								}
								break;
							case FIXED_DAMAGE:
								if(!Mechanics.awayFromBattle[1])
									hit=true;
								if(hit)
								switch(user[userIndex].move[index])
								{
									case DRAGON_RAGE:
										enemy[enemyIndex].health-=40;
										  user[userIndex].TRUE_PP[index]--;
										break;
									case SONIC_BOOM:
										enemy[enemyIndex].health-=20;
										  user[userIndex].TRUE_PP[index]--;
										break;
									case NIGHT_SHADE:
									case SEISMIC_TOSS:
										enemy[enemyIndex].health-=user[userIndex].level;
										  user[userIndex].TRUE_PP[index]--;
										break;
									case PSYWAVE:
										int rand3=(int)(Math.random()*1.5*user[userIndex].level)+1;
										//Ensures that damage is at least 1
										if(rand3<1)
											rand3=1;
										enemy[enemyIndex].health-=rand3;
										  user[userIndex].TRUE_PP[index]--;
										break;
									case SUPER_FANG:
										enemy[enemyIndex].health/=2;
										  user[userIndex].TRUE_PP[index]--;
										break;
								}
								else
									  user[userIndex].TRUE_PP[index]--;
								break;
							case HIDE:
								if(!Mechanics.awayFromBattle[0])
								{
									Mechanics.awayFromBattle[0]=true;
									userLockedMove=index;
								}
								else
								{
									Mechanics.awayFromBattle[0]=false;
									enemy[enemyIndex].health-=Mechanics.damage;
									userLockedMove=-1;
									  user[userIndex].TRUE_PP[index]--;
								}
								break;
							case FURY:
								if(Mechanics.turnsMultiTurn[0]==0&&hit)
								{
									int rand2=(int)(Math.random()*2)+2;
									if(user[userIndex].move[userCmd]==Pokemon.Move.RAGE)
										rand2=Integer.MAX_VALUE;
									Mechanics.turnsMultiTurn[0]=rand2;
									enemy[enemyIndex].health-=Mechanics.damage;
									userLockedMove=index;
									  user[userIndex].TRUE_PP[index]--;
								}
								else if(Mechanics.turnsMultiTurn[0]==1)
								{
									enemy[enemyIndex].health-=Mechanics.damage;
									Mechanics.turnsMultiTurn[0]=0;
									userLockedMove=-1;
									hit=true;
									if(user[userIndex].move[userCmd]!=Pokemon.Move.RAGE)
										user[userIndex].substatus=Pokemon.Substatus.CONFU;
								}
								else if(Mechanics.turnsMultiTurn[0]>1)
								{
									hit=true;
									enemy[enemyIndex].health-=Mechanics.damage;
									Mechanics.turnsMultiTurn[0]--;
								}
						}
					}
					else
					{
						hasPP=false;

						if(!Mechanics.hasPPAtAll(user[userIndex]))
						{
							//Stores Current PP
							int pp1=user[userIndex].move[0].pp;
							int pp2=user[userIndex].move[1].pp;
							int pp3=user[userIndex].move[2].pp;
							int pp4=user[userIndex].move[3].pp;

							Pokemon.Move savedMove=user[userIndex].move[index];
							user[userIndex].move[index]=Pokemon.Move.STRUGGLE;

							//Initializes move
							user[userIndex].createMoves();
							//Uses move
							useMove(0,index);
							//Writes Move Info
							postAttackInfo(0);
							//Reassigns Moves
							user[userIndex].move[index]=savedMove;
							user[userIndex].createMoves();

							//Assigns Previous PP
							user[userIndex].move[0].pp=pp1;
							user[userIndex].move[1].pp=pp2;
							user[userIndex].move[2].pp=pp3;
							user[userIndex].move[3].pp=pp4;
						}
					}
					break;
			case RAISE_STAT:
				hit=true;

				if(user[userIndex].move[index].pp>0)
				{
					hasPP=true;
					switch(user[userIndex].move[index])
					{
						case SHARPEN:
						case MEDITATE:
							user[userIndex].atkStage--;
						case SWORDS_DANCE:
							user[userIndex].atkStage+=2;
							user[userIndex].atk=(int)((Mechanics.calcOtherStat(user[userIndex].baseATK,user[userIndex].ATK_IV,user[userIndex].ATK_EV,user[userIndex].level))*
							Mechanics.stageMultiplier(user[userIndex].atkStage));
							  user[userIndex].TRUE_PP[index]--;
							break;
						case DEFENSE_CURL:
						case HARDEN:
						case WITHDRAW:
							user[userIndex].defStage--;
						case ACID_ARMOR:
						case BARRIER:
							user[userIndex].defStage+=2;
							user[userIndex].def=(int)((Mechanics.calcOtherStat(user[userIndex].baseDEF,user[userIndex].DEF_IV,user[userIndex].DEF_EV,user[userIndex].level))*
							Mechanics.stageMultiplier(user[userIndex].defStage));
							  user[userIndex].TRUE_PP[index]--;
							break;
						case AGILITY:
							user[userIndex].speedStage+=2;
							user[userIndex].speed=(int)((Mechanics.calcOtherStat(user[userIndex].baseSPEED,user[userIndex].SPEED_IV,user[userIndex].SPEED_EV,user[userIndex].level))*
							Mechanics.stageMultiplier(user[userIndex].speedStage));
							  user[userIndex].TRUE_PP[index]--;
							break;
						case GROWTH:
							user[userIndex].spclStage--;
						case AMNESIA:
							user[userIndex].spclStage+=2;
							user[userIndex].spcl=(int)((Mechanics.calcOtherStat(user[userIndex].baseSPCL,user[userIndex].SPCL_IV,user[userIndex].SPCL_EV,user[userIndex].level))*
							Mechanics.stageMultiplier(user[userIndex].spclStage));
							  user[userIndex].TRUE_PP[index]--;
							break;
						case DOUBLE_TEAM:
						case MINIMIZE:
							user[userIndex].evaStage++;
							user[userIndex].eva=1*Mechanics.stageMultiplier(user[userIndex].evaStage);
							  user[userIndex].TRUE_PP[index]--;
							break;
					}
				}
				else
				{
					hasPP=false;
					if(!Mechanics.hasPPAtAll(user[userIndex]))
						{
							//Stores Current PP
							int pp1=user[userIndex].move[0].pp;
							int pp2=user[userIndex].move[1].pp;
							int pp3=user[userIndex].move[2].pp;
							int pp4=user[userIndex].move[3].pp;

							Pokemon.Move savedMove=user[userIndex].move[index];
							user[userIndex].move[index]=Pokemon.Move.STRUGGLE;

							//Initializes move
							user[userIndex].createMoves();
							//Uses move
							useMove(0,index);
							//Writes Move Info
							postAttackInfo(0);
							//Reassigns Moves
							user[userIndex].move[index]=savedMove;
							user[userIndex].createMoves();

							//Assigns Previous PP
							user[userIndex].move[0].pp=pp1;
							user[userIndex].move[1].pp=pp2;
							user[userIndex].move[2].pp=pp3;
							user[userIndex].move[3].pp=pp4;
						}
				}
				break;
			case LOWER_STAT:
				if(user[userIndex].move[index].pp>0)
				{
					if(!Mechanics.awayFromBattle[1])
						hit=true;
					if(Mechanics.turnsMist[1]>0)
						hit=false;

					if(hit)
					{
					switch(user[userIndex].move[index])
					{
						case GROWL:
							enemy[enemyIndex].atkStage--;
							enemy[enemyIndex].atk=(int)((Mechanics.calcOtherStat(enemy[enemyIndex].baseATK,enemy[enemyIndex].ATK_IV,enemy[enemyIndex].ATK_EV,enemy[enemyIndex].level))*
							Mechanics.stageMultiplier(enemy[enemyIndex].atkStage));
							  user[userIndex].TRUE_PP[index]--;
							break;
						case SCREECH:
							enemy[enemyIndex].defStage--;
						case LEER:
						case TAIL_WHIP:
							enemy[enemyIndex].defStage--;
							enemy[enemyIndex].def=(int)((Mechanics.calcOtherStat(enemy[enemyIndex].baseDEF,enemy[enemyIndex].DEF_IV,enemy[enemyIndex].DEF_EV,enemy[enemyIndex].level))*
							Mechanics.stageMultiplier(enemy[enemyIndex].defStage));
							  user[userIndex].TRUE_PP[index]--;
							break;
						case FLASH:
						case KINESIS:
						case SAND_ATTACK:
							enemy[enemyIndex].accuracyStage--;
							  user[userIndex].TRUE_PP[index]--;
							break;
						case STRING_SHOT:
							enemy[enemyIndex].speedStage--;
							enemy[enemyIndex].speed=(int)((Mechanics.calcOtherStat(enemy[enemyIndex].baseSPEED,enemy[enemyIndex].SPEED_IV,enemy[enemyIndex].SPEED_EV,enemy[enemyIndex].level))*
							Mechanics.stageMultiplier(enemy[enemyIndex].speedStage));
							  user[userIndex].TRUE_PP[index]--;
							break;
					}
				}
					else
						  user[userIndex].TRUE_PP[index]--;
				}
				else
				{
					hasPP=false;
					if(!Mechanics.hasPPAtAll(user[userIndex]))
						{
							//Stores Current PP
							int pp1=user[userIndex].move[0].pp;
							int pp2=user[userIndex].move[1].pp;
							int pp3=user[userIndex].move[2].pp;
							int pp4=user[userIndex].move[3].pp;

							Pokemon.Move savedMove=user[userIndex].move[index];
							user[userIndex].move[index]=Pokemon.Move.STRUGGLE;

							//Initializes move
							user[userIndex].createMoves();
							//Uses move
							useMove(0,index);
							//Writes Move Info
							postAttackInfo(0);
							//Reassigns Moves
							user[userIndex].move[index]=savedMove;
							user[userIndex].createMoves();

							//Assigns Previous PP
							user[userIndex].move[0].pp=pp1;
							user[userIndex].move[1].pp=pp2;
							user[userIndex].move[2].pp=pp3;
							user[userIndex].move[3].pp=pp4;
						}
				}
				break;
			case INFLICT_STATUS:
				if(user[userIndex].move[index].pp>0)
				{
					hasPP=true;
					//Checks if move hit or not
					if(Mechanics.checkHit(user[userIndex],index,enemy[enemyIndex].eva)&&!Mechanics.awayFromBattle[1])
						hit=true;
					else
						hit=false;

					boolean sleep=false;

					switch(user[userIndex].move[index])
					{
						case LEECH_SEED:
						case CONFUSE_RAY:
						case SUPERSONIC:
							if(hit&&enemy[enemyIndex].substatus==Pokemon.Substatus.OK&&!Mechanics.awayFromBattle[1])
							{
								Mechanics.turnsConfused[1]=(int)(Math.random()*4)+1;
								Mechanics.isConfused[1]=true;
								enemy[enemyIndex].substatus=user[userIndex].move[index].substatus;
								sideHit=true;
							}
							  user[userIndex].TRUE_PP[index]--;
							break;
						case HYPNOSIS:
						case LOVELY_KISS:
						case SING:
						case SLEEP_POWDER:
						case SPORE:
							sleep=true;
							Mechanics.turnsAsleep[1]=(int)(Math.random()*5)+1;
						case POISON_GAS:
						case POISONPOWDER:
						case TOXIC:
						case GLARE:
						case STUN_SPORE:
						case THUNDER_WAVE:
							if(hit&&enemy[enemyIndex].status==Pokemon.Status.OK&&!Mechanics.awayFromBattle[1])
							{
								if(sleep)
								{
									if(!Mechanics.sleepClauseActive(user,userNumOfPokemon))
									{
										enemy[enemyIndex].status=user[userIndex].move[index].status;
										sideHit=true;
									}
								}
								else
								{
									enemy[enemyIndex].status=user[userIndex].move[index].status;
									sideHit=true;
								}
							}
							  user[userIndex].TRUE_PP[index]--;
							break;
					}
				}
				else
				{
					hasPP=false;

					if(!Mechanics.hasPPAtAll(user[userIndex]))
						{
							//Stores Current PP
							int pp1=user[userIndex].move[0].pp;
							int pp2=user[userIndex].move[1].pp;
							int pp3=user[userIndex].move[2].pp;
							int pp4=user[userIndex].move[3].pp;

							Pokemon.Move savedMove=user[userIndex].move[index];
							user[userIndex].move[index]=Pokemon.Move.STRUGGLE;

							//Initializes move
							user[userIndex].createMoves();
							//Uses move
							useMove(0,index);
							//Writes Move Info
							postAttackInfo(0);
							//Reassigns Moves
							user[userIndex].move[index]=savedMove;
							user[userIndex].createMoves();

							//Assigns Previous PP
							user[userIndex].move[0].pp=pp1;
							user[userIndex].move[1].pp=pp2;
							user[userIndex].move[2].pp=pp3;
							user[userIndex].move[3].pp=pp4;
						}
				}
				break;
			case SPECIAL:
				if(user[userIndex].move[index].pp>0)
				{
					hasPP=true;

					switch(user[userIndex].move[index])
					{
						case CONVERSION:
							hit=true;
							user[userIndex].type1=enemy[enemyIndex].type1;
							user[userIndex].type2=enemy[enemyIndex].type2;
							  user[userIndex].TRUE_PP[index]--;
							break;
						case DISABLE:
							if(Mechanics.turnsDisabled[1]==0&&Mechanics.checkHit(user[userIndex],index,enemy[enemyIndex].eva)
								&&!Mechanics.awayFromBattle[1])
								hit=true;
							if(hit)
							{
								Mechanics.turnsDisabled[1]=(int)(Math.random()*3)+2;
								Mechanics.moveDisabled[1]=(int)(Math.random()*4);
							}
							  user[userIndex].TRUE_PP[index]--;
							break;
						case FOCUS_ENERGY:
							  user[userIndex].TRUE_PP[index]--;
							break;
						case HAZE:
							user[userIndex].status=Pokemon.Status.OK;
							user[userIndex].substatus=Pokemon.Substatus.OK;
							enemy[enemyIndex].status=Pokemon.Status.OK;
							enemy[enemyIndex].substatus=Pokemon.Substatus.OK;
							  user[userIndex].TRUE_PP[index]--;
							break;
						case LIGHT_SCREEN:
							Mechanics.turnsLightScreen[0]=5;
							  user[userIndex].TRUE_PP[index]--;
							break;
						case REFLECT:
							Mechanics.turnsReflect[0]=5;
							  user[userIndex].TRUE_PP[index]--;
							break;
						case METRONOME:
							//Declares Move Use Here
							b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
							  user[userIndex].TRUE_PP[index]--;

							//Stores Current PP
							int pp1=user[userIndex].TRUE_PP[0];
							int pp2=user[userIndex].TRUE_PP[1];
							int pp3=user[userIndex].TRUE_PP[2];
							int pp4=user[userIndex].TRUE_PP[3];

							//Chooses a random move
							int randy=(int)(Math.random()*163);
							user[userIndex].move[index]=user[userIndex].randomMove(randy);


							//Initializes move
							user[userIndex].createMoves();
							//Uses move
							useMove(0,index);
							//Writes Move Info
							postAttackInfo(0);
							//Reassigns Moves
							user[userIndex].move[index]=Pokemon.Move.METRONOME;
							user[userIndex].createMoves();

							//Assigns Previous PP
							user[userIndex].TRUE_PP[0]=pp1;
							user[userIndex].TRUE_PP[1]=pp2;
							user[userIndex].TRUE_PP[2]=pp3;
							user[userIndex].TRUE_PP[3]=pp4;
							break;
						case MIMIC:
							b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
							//Stores Current PP
							int pp11=user[userIndex].TRUE_PP[0];
							int pp22=user[userIndex].TRUE_PP[1];
							int pp33=user[userIndex].TRUE_PP[2];
							int pp44=user[userIndex].TRUE_PP[3];

							revert[userIndex]=user[userIndex];

							//Chooses a random move
							int randy2=(int)(Math.random()*4);
							user[userIndex].move[index]=enemy[enemyIndex].move[randy2];

							//Initializes moves
							user[userIndex].createMoves();

							//Assigns Previous PP
							if(index!=0)
							user[userIndex].TRUE_PP[0]=pp11;
							if(index!=1)
							user[userIndex].TRUE_PP[1]=pp22;
							if(index!=2)
							user[userIndex].TRUE_PP[2]=pp33;
							if(index!=3)
							user[userIndex].TRUE_PP[3]=pp44;

							b1.addText(user[userIndex].nickname+" learned "+user[userIndex].move[userCmd]+"!");
							nonPostingCase[0]=true;
							break;
						case MIRROR_MOVE:
							//Declares Move Use Here
							b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
							  user[userIndex].TRUE_PP[index]--;

							//Uses move if move is not Mirror Move (Infinite Recursion) or out of range(ArrayIndexOutOfBounds)
							if(enemyCmd<0||enemyCmd>3)
							{
								b1.addText("The move failed!");
							}
							else if(enemy[enemyIndex].move[enemyCmd]!=Pokemon.Move.MIRROR_MOVE)
							{
								//Stores Current PP
								int pp111=user[userIndex].TRUE_PP[0];
								int pp222=user[userIndex].TRUE_PP[1];
								int pp333=user[userIndex].TRUE_PP[2];
								int pp444=user[userIndex].TRUE_PP[3];

								//Chooses most recent enemy move
								if(enemyCmd>=0&&enemyCmd<4)
								user[userIndex].move[index]=enemy[enemyIndex].move[enemyCmd];

								//Initializes move
								user[userIndex].createMoves();

								useMove(0,index);

								//Writes Move Info
								postAttackInfo(0);

								//Reassigns Moves
								user[userIndex].move[index]=Pokemon.Move.MIRROR_MOVE;
								user[userIndex].createMoves();

								//Assigns Previous PP
								user[userIndex].TRUE_PP[0]=pp111;
								user[userIndex].TRUE_PP[1]=pp222;
								user[userIndex].TRUE_PP[2]=pp333;
								user[userIndex].TRUE_PP[3]=pp444;
							}
							else
							{
								b1.addText("The move failed!");
							}
							break;
						case MIST:
							if(Mechanics.turnsMist[0]==0)
							{
								Mechanics.turnsMist[0]=5;
								hit=true;
							}
							else
								hit=false;
							  user[userIndex].TRUE_PP[index]--;
							break;
						case RECOVER:
						case SOFTBOILED:
							if(user[userIndex].health<user[userIndex].healthMax)
							{
								hit=true;
								user[userIndex].health=Mechanics.recover(user[userIndex],user[userIndex].healthMax/2);
							}
							else
								hit=false;
							  user[userIndex].TRUE_PP[index]--;
							break;
						case REST:
							if(user[userIndex].health<user[userIndex].healthMax)
							{
								hit=true;
								user[userIndex].health=Mechanics.recover(user[userIndex],user[userIndex].healthMax);
								user[userIndex].status=Pokemon.Status.SLP;
								Mechanics.turnsAsleep[0]=2;
							}
							else
								hit=false;
							  user[userIndex].TRUE_PP[index]--;
							break;
						case SPLASH:
							hit=true;
							  user[userIndex].TRUE_PP[index]--;
							break;
						case SUBSTITUTE:
							  user[userIndex].TRUE_PP[index]--;
							if(Mechanics.canCreateSubstitute(user[userIndex])&&!Mechanics.hasSubstitute[0])
							{
								Mechanics.createSubstitute(user[userIndex].healthMax/4,0);
								user[userIndex].health-=user[userIndex].healthMax/4;
								hit=true;
							}
							else
								hit=false;
							break;
						case TRANSFORM:
							b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
							user[userIndex].TRUE_PP[index]--;

							Pokemon.Species s=user[userIndex].species;

							//Stores data for reference
							String str=user[userIndex].nickname;
							revert[userIndex]=user[userIndex];
							revert[userIndex].species=s;

							//Assigns Pokemon Species
							user[userIndex].species=enemy[enemyIndex].species;

							//Creates Pokemon with enemy moves
							user[userIndex]=new Pokemon(user[userIndex].species,enemy[enemyIndex].move[0],enemy[enemyIndex].move[1],
							enemy[enemyIndex].move[2],enemy[enemyIndex].move[3],user[userIndex].level);

							b1.createPokemonImages();

							//Assigns 5 PP to all moves
							for(int i=0; i<4; i++)
							{
								user[userIndex].move[i].pp=5;
								user[userIndex].TRUE_PP[i]=5;
							}
							//Assigns former nickname
							user[userIndex].nickname=str;

							b1.addText(user[userIndex].nickname+" transformed into "+user[userIndex].species);

							nonPostingCase[0]=true;
							break;
						case ROAR:
						case TELEPORT:
						case WHIRLWIND:
							if(!BATTLE_TYPE.equals("WILD")&&!Mechanics.awayFromBattle[1])
								hit=false;
							else
							{
								hit=true;
								BATTLE_OVER=true;
								Mechanics.canAttack[1]=false;
							}
							  user[userIndex].TRUE_PP[index]--;
							break;
					}
				}
				else
				{
					hasPP=false;

					if(!Mechanics.hasPPAtAll(user[userIndex]))
						{
							//Stores Current PP
							int pp1=user[userIndex].move[0].pp;
							int pp2=user[userIndex].move[1].pp;
							int pp3=user[userIndex].move[2].pp;
							int pp4=user[userIndex].move[3].pp;

							Pokemon.Move savedMove=user[userIndex].move[index];
							user[userIndex].move[index]=Pokemon.Move.STRUGGLE;

							//Initializes move
							user[userIndex].createMoves();
							//Uses move
							useMove(0,index);
							//Writes Move Info
							postAttackInfo(0);
							//Reassigns Moves
							user[userIndex].move[index]=savedMove;
							user[userIndex].createMoves();

							//Assigns Previous PP
							user[userIndex].move[0].pp=pp1;
							user[userIndex].move[1].pp=pp2;
							user[userIndex].move[2].pp=pp3;
							user[userIndex].move[3].pp=pp4;
						}
				}
				break;
			}
		}
		//Enemy Set/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else
		{
			switch(enemy[enemyIndex].move[index].mainEffect)
			{
				case DAMAGE:
					if(enemy[enemyIndex].move[index].pp>0)
					{
						hasPP=true;
						//Checks if move is Special or not, then uses correct formula to calculate
						//damage.
						if(Mechanics.isSpecial(enemy[enemyIndex].move[index].moveType))
							Mechanics.calcDamage(enemy[enemyIndex].move[index].basePower,
							enemy[enemyIndex].level,enemy[enemyIndex].spcl,user[userIndex].spcl,0);
						else
							Mechanics.calcDamage(enemy[enemyIndex].move[index].basePower,
							enemy[enemyIndex].level,enemy[enemyIndex].atk,user[userIndex].def,0);

						//Adds type multiplier to damage
						Mechanics.damage*=Mechanics.typeMultiplier(enemy[enemyIndex].move[index].moveType,
						user[userIndex].type1,user[userIndex].type2);

						//Multiplies damage by 1.5 for Same Type Attack Bonus
						if(Mechanics.isSTAB(enemy[enemyIndex].type1,enemy[enemyIndex].type2,enemy[enemyIndex].move[index].moveType))
							Mechanics.damage*=1.5;

						//Multiplies damage by 2 for critical hit
						if(enemy[enemyIndex].move[index].sideEffect==Pokemon.Side_Effect.HIGH_CRIT)
							Mechanics.isHighCritical[1]=true;
						if(Mechanics.checkCritical(enemy[enemyIndex].baseSPEED,1)&&Mechanics.effective!=Mechanics.Effective.NONE)
						{
							crit=true;
							Mechanics.damage*=2;
						}

						//Reduces physical damage due to burn
						if(!Mechanics.isSpecial(enemy[enemyIndex].move[index].moveType)&&
							enemy[enemyIndex].status==Pokemon.Status.BRN)
							{
								Mechanics.damage*=0.75;
							}

						//Reduces damage when Light Screen or Reflect are up
						if(!Mechanics.isSpecial(enemy[enemyIndex].move[index].moveType)&&Mechanics.turnsReflect[0]>0)
						{
							Mechanics.damage/=2;
						}
						else if(Mechanics.isSpecial(enemy[enemyIndex].move[index].moveType)&&Mechanics.turnsLightScreen[0]>0)
						{
							Mechanics.damage/=2;
						}

						//Checks if move hit or not
						if(!Mechanics.checkHit(enemy[enemyIndex],index,user[userIndex].eva)||
							(Mechanics.awayFromBattle[0]&&enemy[enemyIndex].move[index]!=Pokemon.Move.SWIFT)&&Mechanics.turnsMultiTurn[1]<1)
						{
							Mechanics.damage=0;
							hit=false;
						}
						else
						{
							hit=true;
							if(Mechanics.damage<1&&Mechanics.effective!=Mechanics.Effective.NONE)
								Mechanics.damage=1;
						}

						if(hit&&Mechanics.hasSubstitute[0])
						{
							Mechanics.substituteHealth[0]-=Mechanics.damage;
							Mechanics.damage=0;
						}
						if (Mechanics.damage > user[userIndex].health)
						{
							Mechanics.damage = user[userIndex].health;
						}

						switch(enemy[enemyIndex].move[index].sideEffect)
						{
							case HIGH_CRIT:
							case QUICK_ATTACK:
							case SWIFT:
							case NONE:
								user[userIndex].health-=Mechanics.damage;
								enemy[enemyIndex].move[index].pp--;
								break;
							case ABSORB:
								if(enemy[enemyIndex].move[index]==Pokemon.Move.DREAM_EATER)
								{
									if(user[userIndex].status!=Pokemon.Status.SLP)
									{
										Mechanics.damage=0;
										hit=false;
									}
								}
								enemy[enemyIndex].health=Mechanics.recover(enemy[enemyIndex],Mechanics.damage/2);
								user[userIndex].health-=Mechanics.damage;
								enemy[enemyIndex].move[index].pp--;
								break;
							case STAT:
								if(Mechanics.checkSideEffect(enemy[enemyIndex].move[index])&&Mechanics.damage>0)
									sideHit=true;
								if(sideHit)
								{
									switch(enemy[enemyIndex].move[index])
									{
										case ACID:
											user[userIndex].defStage--;
											user[userIndex].def=(int)((Mechanics.calcOtherStat(user[userIndex].baseDEF,user[userIndex].DEF_IV,user[userIndex].DEF_EV,user[userIndex].level))*
											Mechanics.stageMultiplier(user[userIndex].defStage));
											break;
										case BUBBLE:
										case BUBBLEBEAM:
										case CONSTRICT:
											user[userIndex].speedStage--;
											user[userIndex].speed=(int)((Mechanics.calcOtherStat(user[userIndex].baseSPEED,user[userIndex].SPEED_IV,user[userIndex].SPEED_EV,user[userIndex].level))*
											Mechanics.stageMultiplier(user[userIndex].speedStage));
											break;
										case PSYCHIC:
											user[userIndex].spclStage--;
											user[userIndex].spcl=(int)((Mechanics.calcOtherStat(user[userIndex].baseSPCL,user[userIndex].SPCL_IV,user[userIndex].SPCL_EV,user[userIndex].level))*
											Mechanics.stageMultiplier(user[userIndex].spclStage));
											break;
									}
								}
								user[userIndex].health-=Mechanics.damage;
								enemy[enemyIndex].move[index].pp--;
								break;
							case STATUS:
								if(Mechanics.checkSideEffect(enemy[enemyIndex].move[index])
									&&user[userIndex].status==Pokemon.Status.OK&&Mechanics.damage>0
										&&!Mechanics.awayFromBattle[0])
									{
										user[userIndex].status=enemy[enemyIndex].move[index].status;
										sideHit=true;
									}
								user[userIndex].health-=Mechanics.damage;
								enemy[enemyIndex].move[index].pp--;
								break;
							case SUBSTATUS:
								if(Mechanics.checkSideEffect(enemy[enemyIndex].move[index])
									&&user[userIndex].substatus==Pokemon.Substatus.OK&&Mechanics.damage>0
										&&!Mechanics.awayFromBattle[0])
								{
									user[userIndex].substatus=enemy[enemyIndex].move[index].substatus;
									sideHit=true;
								}
								user[userIndex].health-=Mechanics.damage;
								enemy[enemyIndex].move[index].pp--;
								break;
							case MULTI_HIT:
								int rand=(int)(Math.random()*3)+2;
								Mechanics.damage*=rand;
								timesHit=rand;
								user[userIndex].health-=Mechanics.damage;
								enemy[enemyIndex].move[index].pp--;
								break;
							case MULTI_TURN:
								if(Mechanics.turnsMultiTurn[1]==0&&hit)
								{
									int rand2=(int)(Math.random()*4)+2;
									Mechanics.turnsMultiTurn[1]=rand2;
									user[userIndex].health-=Mechanics.damage;
									Mechanics.canAttack[0]=false;
									enemyLockedMove=index;
									enemy[enemyIndex].move[index].pp--;
								}
								else if(Mechanics.turnsMultiTurn[1]==1)
								{
									user[userIndex].health-=Mechanics.damage;
									Mechanics.turnsMultiTurn[1]=0;
									Mechanics.canAttack[0]=true;
									enemyLockedMove=-1;
									hit=true;
								}
								else if(Mechanics.turnsMultiTurn[1]>1)
								{
									hit=true;
									user[userIndex].health-=Mechanics.damage;
									Mechanics.turnsMultiTurn[1]--;
								}
								break;
							case OHKO:
								crit=false;
								if(Mechanics.damage!=0)
								{
									if(enemy[enemyIndex].speed>user[userIndex].speed)
									{
										Mechanics.damage=Integer.MAX_VALUE;
										//Mechanics.damage=user[userIndex].health;
									}
									else
									{
										hit=false;
										Mechanics.damage=0;
									}
								}
								user[userIndex].health-=Mechanics.damage;
								enemy[enemyIndex].move[index].pp--;
								break;
							case CHARGE:
								switch(enemy[enemyIndex].move[index])
								{
									case RAZOR_WIND:
									case SKY_ATTACK:
									case SKULL_BASH:
									case SOLARBEAM:
										if(!Mechanics.charging[1])
										{
											Mechanics.charging[1]=true;
											Mechanics.damage=0;

											enemyLockedMove=index;
										}
										else
										{
											Mechanics.charging[1]=false;
											user[userIndex].health-=Mechanics.damage;
											enemyLockedMove=-1;
											enemy[enemyIndex].move[index].pp--;
										}
										break;
									case HYPER_BEAM:
										if(!Mechanics.charging[1])
										{
											Mechanics.charging[1]=true;
											user[userIndex].health-=Mechanics.damage;
											enemyLockedMove=index;
											enemy[enemyIndex].move[index].pp--;
										}
										else
										{
											Mechanics.charging[1]=false;
											Mechanics.damage=0;
											enemyLockedMove=-1;
										}
										break;

								}
								break;
							case FAINT:
								user[userIndex].health-=Mechanics.damage;
								enemy[enemyIndex].move[index].pp--;
								enemy[enemyIndex].health=0;
								break;
							case RECOIL:
								switch(enemy[enemyIndex].move[index])
								{
									case STRUGGLE:
										user[userIndex].health-=Mechanics.damage;
										enemy[enemyIndex].health-=Mechanics.damage/2;
										enemy[enemyIndex].move[index].pp--;
										break;
									default:
										user[userIndex].health-=Mechanics.damage;
										enemy[enemyIndex].health-=Mechanics.damage/4;
										enemy[enemyIndex].move[index].pp--;
										break;
								}
								break;
							case COLLECT_DAMAGE:
								switch(enemy[enemyIndex].move[index])
								{
									case BIDE:
										if(Mechanics.turnsMultiTurn[1]==0)
										{
											int rand2=(int)(Math.random()*2)+2;
											Mechanics.turnsMultiTurn[1]=rand2;
											Mechanics.storedDamage[1]=0;
											Mechanics.damage=0;
											enemyLockedMove=index;
										}
										else if(Mechanics.turnsMultiTurn[1]==1)
										{
											user[userIndex].health-=Mechanics.storedDamage[1];
											Mechanics.turnsMultiTurn[1]=0;
											Mechanics.storedDamage[1]=0;
											enemyLockedMove=-1;
											enemy[enemyIndex].move[index].pp--;
										}
										else if(Mechanics.turnsMultiTurn[1]>1)
										{
											Mechanics.turnsMultiTurn[1]--;
											Mechanics.damage=0;
										}
										break;
									case COUNTER:
										if(user[userIndex].move[userCmd].moveType==Pokemon.Type.NORMAL||
											user[userIndex].move[userCmd].moveType==Pokemon.Type.FIGHTING)
										{
											user[userIndex].health-=Mechanics.damage*2;
										}
										else
											hit=false;
										enemy[enemyIndex].move[index].pp--;
										break;
								}
								break;
							case FIXED_DAMAGE:
								if(!Mechanics.awayFromBattle[0])
									hit=true;
								if(hit)
								switch(enemy[enemyIndex].move[index])
								{
									case DRAGON_RAGE:
										user[userIndex].health-=40;
										enemy[enemyIndex].move[index].pp--;
										break;
									case SONIC_BOOM:
										user[userIndex].health-=20;
										enemy[enemyIndex].move[index].pp--;
										break;
									case NIGHT_SHADE:
									case SEISMIC_TOSS:
										user[userIndex].health-=enemy[enemyIndex].level;
										enemy[enemyIndex].move[index].pp--;
										break;
									case PSYWAVE:
										int rand3=(int)(Math.random()*1.5*enemy[enemyIndex].level)+1;
										//Ensures that damage is at least 1
										if(rand3<1)
											rand3=1;
										user[userIndex].health-=rand3;
										enemy[enemyIndex].move[index].pp--;
										break;
									case SUPER_FANG:
										user[userIndex].health/=2;
										enemy[enemyIndex].move[index].pp--;
										break;
								}
								else
									enemy[enemyIndex].move[index].pp--;
								break;
							case HIDE:
								if(!Mechanics.awayFromBattle[1])
								{
									Mechanics.awayFromBattle[1]=true;
									enemyLockedMove=index;
								}
								else
								{
									Mechanics.awayFromBattle[1]=false;
									user[userIndex].health-=Mechanics.damage;
									enemyLockedMove=-1;
									enemy[enemyIndex].move[index].pp--;
								}
								break;
							case FURY:
								if(Mechanics.turnsMultiTurn[1]==0&&hit)
								{
									int rand2=(int)(Math.random()*2)+2;
									if(enemy[enemyIndex].move[enemyCmd]==Pokemon.Move.RAGE)
										rand2=Integer.MAX_VALUE;
									Mechanics.turnsMultiTurn[1]=rand2;
									user[userIndex].health-=Mechanics.damage;
									enemyLockedMove=index;
									enemy[enemyIndex].move[index].pp--;
								}
								else if(Mechanics.turnsMultiTurn[1]==1)
								{
									user[userIndex].health-=Mechanics.damage;
									Mechanics.turnsMultiTurn[1]=0;
									enemyLockedMove=-1;
									hit=true;
									if(enemy[enemyIndex].move[enemyCmd]!=Pokemon.Move.RAGE)
										enemy[enemyIndex].substatus=Pokemon.Substatus.CONFU;
								}
								else if(Mechanics.turnsMultiTurn[1]>1)
								{
									hit=true;
									user[userIndex].health-=Mechanics.damage;
									Mechanics.turnsMultiTurn[1]--;
								}
						}
					}
					else
					{
						hasPP=false;

						if(!Mechanics.hasPPAtAll(enemy[enemyIndex]))
						{
							//Stores Current PP
							int pp1=enemy[enemyIndex].move[0].pp;
							int pp2=enemy[enemyIndex].move[1].pp;
							int pp3=enemy[enemyIndex].move[2].pp;
							int pp4=enemy[enemyIndex].move[3].pp;

							Pokemon.Move savedMove=enemy[enemyIndex].move[index];
							enemy[enemyIndex].move[index]=Pokemon.Move.STRUGGLE;

							//Initializes move
							enemy[enemyIndex].createMoves();
							//Uses move
							useMove(1,index);
							//Writes Move Info
							postAttackInfo(0);
							//Reassigns Moves
							enemy[enemyIndex].move[index]=savedMove;
							enemy[enemyIndex].createMoves();

							//Assigns Previous PP
							enemy[enemyIndex].move[0].pp=pp1;
							enemy[enemyIndex].move[1].pp=pp2;
							enemy[enemyIndex].move[2].pp=pp3;
							enemy[enemyIndex].move[3].pp=pp4;
						}
					}
					break;
			case RAISE_STAT:
				hit=true;

				if(enemy[enemyIndex].move[index].pp>0)
				{
					hasPP=true;
					switch(enemy[enemyIndex].move[index])
					{
						case SHARPEN:
						case MEDITATE:
							enemy[enemyIndex].atkStage--;
						case SWORDS_DANCE:
							enemy[enemyIndex].atkStage+=2;
							enemy[enemyIndex].atk=(int)((Mechanics.calcOtherStat(enemy[enemyIndex].baseATK,enemy[enemyIndex].ATK_IV,enemy[enemyIndex].ATK_EV,enemy[enemyIndex].level))*
							Mechanics.stageMultiplier(enemy[enemyIndex].atkStage));
							enemy[enemyIndex].move[index].pp--;
							break;
						case DEFENSE_CURL:
						case HARDEN:
						case WITHDRAW:
							enemy[enemyIndex].defStage--;
						case ACID_ARMOR:
						case BARRIER:
							enemy[enemyIndex].defStage+=2;
							enemy[enemyIndex].def=(int)((Mechanics.calcOtherStat(enemy[enemyIndex].baseDEF,enemy[enemyIndex].DEF_IV,enemy[enemyIndex].DEF_EV,enemy[enemyIndex].level))*
							Mechanics.stageMultiplier(enemy[enemyIndex].defStage));
							enemy[enemyIndex].move[index].pp--;
							break;
						case AGILITY:
							enemy[enemyIndex].speedStage+=2;
							enemy[enemyIndex].speed=(int)((Mechanics.calcOtherStat(enemy[enemyIndex].baseSPEED,enemy[enemyIndex].SPEED_IV,enemy[enemyIndex].SPEED_EV,enemy[enemyIndex].level))*
							Mechanics.stageMultiplier(enemy[enemyIndex].speedStage));
							enemy[enemyIndex].move[index].pp--;
							break;
						case GROWTH:
							enemy[enemyIndex].spclStage--;
						case AMNESIA:
							enemy[enemyIndex].spclStage+=2;
							enemy[enemyIndex].spcl=(int)((Mechanics.calcOtherStat(enemy[enemyIndex].baseSPCL,enemy[enemyIndex].SPCL_IV,enemy[enemyIndex].SPCL_EV,enemy[enemyIndex].level))*
							Mechanics.stageMultiplier(enemy[enemyIndex].spclStage));
							enemy[enemyIndex].move[index].pp--;
							break;
						case DOUBLE_TEAM:
						case MINIMIZE:
							enemy[enemyIndex].evaStage++;
							enemy[enemyIndex].eva=1*Mechanics.stageMultiplier(enemy[enemyIndex].evaStage);
							enemy[enemyIndex].move[index].pp--;
							break;
					}
				}
				else
				{
					hasPP=false;

						if(!Mechanics.hasPPAtAll(enemy[enemyIndex]))
						{
							//Stores Current PP
							int pp1=enemy[enemyIndex].move[0].pp;
							int pp2=enemy[enemyIndex].move[1].pp;
							int pp3=enemy[enemyIndex].move[2].pp;
							int pp4=enemy[enemyIndex].move[3].pp;

							Pokemon.Move savedMove=enemy[enemyIndex].move[index];
							enemy[enemyIndex].move[index]=Pokemon.Move.STRUGGLE;

							//Initializes move
							enemy[enemyIndex].createMoves();
							//Uses move
							useMove(1,index);
							//Writes Move Info
							postAttackInfo(0);
							//Reassigns Moves
							enemy[enemyIndex].move[index]=savedMove;
							enemy[enemyIndex].createMoves();

							//Assigns Previous PP
							enemy[enemyIndex].move[0].pp=pp1;
							enemy[enemyIndex].move[1].pp=pp2;
							enemy[enemyIndex].move[2].pp=pp3;
							enemy[enemyIndex].move[3].pp=pp4;
						}
				}
				break;
			case LOWER_STAT:
				if(enemy[enemyIndex].move[index].pp>0)
				{
					if(!Mechanics.awayFromBattle[0])
						hit=true;
					if(Mechanics.turnsMist[0]>0)
						hit=false;
					if(hit)
					switch(enemy[enemyIndex].move[index])
					{
						case GROWL:
							user[userIndex].atkStage--;
							user[userIndex].atk=(int)((Mechanics.calcOtherStat(user[userIndex].baseATK,user[userIndex].ATK_IV,user[userIndex].ATK_EV,user[userIndex].level))*
							Mechanics.stageMultiplier(user[userIndex].atkStage));
							enemy[enemyIndex].move[index].pp--;
							break;
						case SCREECH:
							user[userIndex].defStage--;
						case LEER:
						case TAIL_WHIP:
							user[userIndex].defStage--;
							user[userIndex].def=(int)((Mechanics.calcOtherStat(user[userIndex].baseDEF,user[userIndex].DEF_IV,user[userIndex].DEF_EV,user[userIndex].level))*
							Mechanics.stageMultiplier(user[userIndex].defStage));
							enemy[enemyIndex].move[index].pp--;
							break;
						case FLASH:
						case KINESIS:
						case SAND_ATTACK:
							user[userIndex].accuracyStage--;
							enemy[enemyIndex].move[index].pp--;
							break;
						case STRING_SHOT:
							user[userIndex].speedStage--;
							user[userIndex].speed=(int)((Mechanics.calcOtherStat(user[userIndex].baseSPEED,user[userIndex].SPEED_IV,user[userIndex].SPEED_EV,user[userIndex].level))*
							Mechanics.stageMultiplier(user[userIndex].speedStage));
							enemy[enemyIndex].move[index].pp--;
							break;
					}
					else
						enemy[enemyIndex].move[index].pp--;
				}
				else
				{
					hasPP=false;

						if(!Mechanics.hasPPAtAll(enemy[enemyIndex]))
						{
							//Stores Current PP
							int pp1=enemy[enemyIndex].move[0].pp;
							int pp2=enemy[enemyIndex].move[1].pp;
							int pp3=enemy[enemyIndex].move[2].pp;
							int pp4=enemy[enemyIndex].move[3].pp;

							Pokemon.Move savedMove=enemy[enemyIndex].move[index];
							enemy[enemyIndex].move[index]=Pokemon.Move.STRUGGLE;

							//Initializes move
							enemy[enemyIndex].createMoves();
							//Uses move
							useMove(1,index);
							//Writes Move Info
							postAttackInfo(0);
							//Reassigns Moves
							enemy[enemyIndex].move[index]=savedMove;
							enemy[enemyIndex].createMoves();

							//Assigns Previous PP
							enemy[enemyIndex].move[0].pp=pp1;
							enemy[enemyIndex].move[1].pp=pp2;
							enemy[enemyIndex].move[2].pp=pp3;
							enemy[enemyIndex].move[3].pp=pp4;
						}
				}
				break;
			case INFLICT_STATUS:
				if(enemy[enemyIndex].move[index].pp>0)
				{
					hasPP=true;
					//Checks if move hit or not
					if(Mechanics.checkHit(enemy[enemyIndex],index,user[userIndex].eva)&&!Mechanics.awayFromBattle[0])
						hit=true;
					else
						hit=false;

					boolean sleep=false;

					switch(enemy[enemyIndex].move[index])
					{
						case LEECH_SEED:
						case CONFUSE_RAY:
						case SUPERSONIC:
							if(hit&&user[userIndex].substatus==Pokemon.Substatus.OK)
							{
								Mechanics.turnsConfused[0]=(int)(Math.random()*4)+1;
								Mechanics.isConfused[0]=true;
								user[userIndex].substatus=enemy[enemyIndex].move[index].substatus;
								sideHit=true;
							}
							enemy[enemyIndex].move[index].pp--;
							break;
						case HYPNOSIS:
						case LOVELY_KISS:
						case SING:
						case SLEEP_POWDER:
						case SPORE:
							sleep=true;
							Mechanics.turnsAsleep[0]=(int)(Math.random()*5)+1;
						case POISON_GAS:
						case POISONPOWDER:
						case TOXIC:
						case GLARE:
						case STUN_SPORE:
						case THUNDER_WAVE:
							if(hit&&user[userIndex].status==Pokemon.Status.OK)
							{
								if(sleep)
								{
									if(!Mechanics.sleepClauseActive(enemy,enemyNumOfPokemon))
									{
										user[userIndex].status=enemy[enemyIndex].move[index].status;
										sideHit=true;
									}
								}
								else
								{
									user[userIndex].status=enemy[enemyIndex].move[index].status;
									sideHit=true;
								}
							}
							enemy[enemyIndex].move[index].pp--;
							break;
					}
				}
				else
				{
					hasPP=false;

						if(!Mechanics.hasPPAtAll(enemy[enemyIndex]))
						{
							//Stores Current PP
							int pp1=enemy[enemyIndex].move[0].pp;
							int pp2=enemy[enemyIndex].move[1].pp;
							int pp3=enemy[enemyIndex].move[2].pp;
							int pp4=enemy[enemyIndex].move[3].pp;

							Pokemon.Move savedMove=enemy[enemyIndex].move[index];
							enemy[enemyIndex].move[index]=Pokemon.Move.STRUGGLE;

							//Initializes move
							enemy[enemyIndex].createMoves();
							//Uses move
							useMove(1,index);
							//Writes Move Info
							postAttackInfo(0);
							//Reassigns Moves
							enemy[enemyIndex].move[index]=savedMove;
							enemy[enemyIndex].createMoves();

							//Assigns Previous PP
							enemy[enemyIndex].move[0].pp=pp1;
							enemy[enemyIndex].move[1].pp=pp2;
							enemy[enemyIndex].move[2].pp=pp3;
							enemy[enemyIndex].move[3].pp=pp4;
						}
				}
				break;
			case SPECIAL:
				if(enemy[enemyIndex].move[index].pp>0)
				{
					hasPP=true;

					switch(enemy[enemyIndex].move[index])
					{
						case CONVERSION:
							hit=true;
							enemy[enemyIndex].type1=user[userIndex].type1;
							enemy[enemyIndex].type2=user[userIndex].type2;
							enemy[enemyIndex].move[index].pp--;
							break;
						case DISABLE:
							if(Mechanics.turnsDisabled[0]==0&&Mechanics.checkHit(enemy[enemyIndex],index,user[userIndex].eva)
								&&!Mechanics.awayFromBattle[0])
								hit=true;
							if(hit)
							{
								Mechanics.turnsDisabled[0]=(int)(Math.random()*3)+2;
								Mechanics.moveDisabled[0]=(int)(Math.random()*4);
							}
							enemy[enemyIndex].move[index].pp--;
							break;
						case FOCUS_ENERGY:
							enemy[enemyIndex].move[index].pp--;
							break;
						case HAZE:
							user[userIndex].status=Pokemon.Status.OK;
							user[userIndex].substatus=Pokemon.Substatus.OK;
							enemy[enemyIndex].status=Pokemon.Status.OK;
							enemy[enemyIndex].substatus=Pokemon.Substatus.OK;
							enemy[enemyIndex].move[index].pp--;
							break;
						case LIGHT_SCREEN:
							Mechanics.turnsLightScreen[1]=5;
							enemy[enemyIndex].move[index].pp--;
							break;
						case REFLECT:
							Mechanics.turnsReflect[1]=5;
							enemy[enemyIndex].move[index].pp--;
							break;
						case METRONOME:
							//Declares Move Use Here
							b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
							enemy[enemyIndex].move[index].pp--;

							//Stores Current PP
							int pp1=enemy[enemyIndex].move[0].pp;
							int pp2=enemy[enemyIndex].move[1].pp;
							int pp3=enemy[enemyIndex].move[2].pp;
							int pp4=enemy[enemyIndex].move[3].pp;

							//Chooses a random move
							int randy=(int)(Math.random()*163);
							enemy[enemyIndex].move[index]=enemy[enemyIndex].randomMove(randy);


							//Initializes move
							enemy[enemyIndex].createMoves();
							//Uses move
							useMove(1,index);
							//Writes Move Info
							postAttackInfo(1);
							//Reassigns Moves
							enemy[enemyIndex].move[index]=Pokemon.Move.METRONOME;
							enemy[enemyIndex].createMoves();

							//Assigns Previous PP
							enemy[enemyIndex].move[0].pp=pp1;
							enemy[enemyIndex].move[1].pp=pp2;
							enemy[enemyIndex].move[2].pp=pp3;
							enemy[enemyIndex].move[3].pp=pp4;
							break;
						case MIMIC:
							b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
							//Stores Current PP
							int pp11=enemy[enemyIndex].move[0].pp;
							int pp22=enemy[enemyIndex].move[1].pp;
							int pp33=enemy[enemyIndex].move[2].pp;
							int pp44=enemy[enemyIndex].move[3].pp;

							//Chooses a random move
							int randy2=(int)(Math.random()*4);
							enemy[enemyIndex].move[index]=user[userIndex].move[randy2];

							//Initializes moves
							enemy[enemyIndex].createMoves();

							//Assigns Previous PP
							if(index!=0)
							enemy[enemyIndex].move[0].pp=pp11;
							if(index!=1)
							enemy[enemyIndex].move[1].pp=pp22;
							if(index!=2)
							enemy[enemyIndex].move[2].pp=pp33;
							if(index!=3)
							enemy[enemyIndex].move[3].pp=pp44;

							b1.addText("Enemy "+enemy[enemyIndex].nickname+" learned "+enemy[enemyIndex].move[enemyCmd]+"!");
							nonPostingCase[1]=true;
							break;
						case MIRROR_MOVE:
							//Declares Move Use Here
							b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
							enemy[enemyIndex].move[index].pp--;

							//Uses move if move is not Mirror Move (Infinite Recursion)
							if(userCmd<0||userCmd>3)
							{
								b1.addText("The move failed!");
							}
							else if(enemy[enemyIndex].move[enemyCmd]!=Pokemon.Move.MIRROR_MOVE)
							{
								//Stores Current PP
								int pp111=enemy[enemyIndex].move[0].pp;
								int pp222=enemy[enemyIndex].move[1].pp;
								int pp333=enemy[enemyIndex].move[2].pp;
								int pp444=enemy[enemyIndex].move[3].pp;

								//Chooses most recent user move
								if(userCmd>=0&&userCmd<4)
								enemy[enemyIndex].move[index]=user[userIndex].move[userCmd];

								//Initializes move
								enemy[enemyIndex].createMoves();

								useMove(1,index);

								//Writes Move Info
								postAttackInfo(1);

								//Reassigns Moves
								enemy[enemyIndex].move[index]=Pokemon.Move.MIRROR_MOVE;
								enemy[enemyIndex].createMoves();

								//Assigns Previous PP
								enemy[enemyIndex].move[0].pp=pp111;
								enemy[enemyIndex].move[1].pp=pp222;
								enemy[enemyIndex].move[2].pp=pp333;
								enemy[enemyIndex].move[3].pp=pp444;
							}
							else
							{
								b1.addText("The move failed!");
							}
							break;
						case MIST:
							if(Mechanics.turnsMist[1]==0)
							{
								Mechanics.turnsMist[1]=5;
								hit=true;
							}
							else
								hit=false;
							enemy[enemyIndex].move[index].pp--;
							break;
						case RECOVER:
						case SOFTBOILED:
							if(enemy[enemyIndex].health<enemy[enemyIndex].healthMax)
							{
								hit=true;
								enemy[enemyIndex].health=Mechanics.recover(enemy[enemyIndex],enemy[enemyIndex].healthMax/2);
							}
							else
								hit=false;
							enemy[enemyIndex].move[index].pp--;
							break;
						case REST:
							if(enemy[enemyIndex].health<enemy[enemyIndex].healthMax)
							{
								hit=true;
								enemy[enemyIndex].health=Mechanics.recover(enemy[enemyIndex],enemy[enemyIndex].healthMax);
								enemy[enemyIndex].status=Pokemon.Status.SLP;
								Mechanics.turnsAsleep[1]=2;
							}
							else
								hit=false;
							  user[userIndex].TRUE_PP[index]--;
							break;
						case SPLASH:
							hit=true;
							enemy[enemyIndex].move[index].pp--;
							break;
						case SUBSTITUTE:
							enemy[enemyIndex].move[index].pp--;
							if(Mechanics.canCreateSubstitute(enemy[enemyIndex])&&!Mechanics.hasSubstitute[1])
							{
								Mechanics.createSubstitute(enemy[enemyIndex].healthMax/4,1);
								enemy[enemyIndex].health-=enemy[enemyIndex].healthMax/4;
								hit=true;
							}
							else
								hit=false;
							break;
						case TRANSFORM:
							b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);

							//Stores nickname for reference
							String str=enemy[enemyIndex].nickname;

							//Assigns Pokemon Species
							enemy[enemyIndex].species=user[userIndex].species;

							//Creates Pokemon with user moves
							enemy[enemyIndex]=new Pokemon(enemy[enemyIndex].species,user[userIndex].move[0],user[userIndex].move[1],
							user[userIndex].move[2],user[userIndex].move[3],enemy[enemyIndex].level);

							b1.createPokemonImages();

							//Assigns 5 PP to all moves
							for(int i=0; i<4; i++)
								enemy[enemyIndex].move[i].pp=5;
							//Assigns former nickname
							enemy[enemyIndex].nickname=str;

							b1.addText("Enemy "+enemy[enemyIndex].nickname+" transformed into "+enemy[enemyIndex].species);

							nonPostingCase[1]=true;
							break;
						case ROAR:
						case TELEPORT:
						case WHIRLWIND:
							if(!BATTLE_TYPE.equals("WILD")&&!Mechanics.awayFromBattle[0])
								hit=false;
							else
							{
								BATTLE_OVER=true;
								hit=true;
								Mechanics.canAttack[0]=false;
							}
							enemy[enemyIndex].move[index].pp--;
							break;
					}
				}
				else
				{
					hasPP=false;

						if(!Mechanics.hasPPAtAll(enemy[enemyIndex]))
						{
							//Stores Current PP
							int pp1=enemy[enemyIndex].move[0].pp;
							int pp2=enemy[enemyIndex].move[1].pp;
							int pp3=enemy[enemyIndex].move[2].pp;
							int pp4=enemy[enemyIndex].move[3].pp;

							Pokemon.Move savedMove=enemy[enemyIndex].move[index];
							enemy[enemyIndex].move[index]=Pokemon.Move.STRUGGLE;

							//Initializes move
							enemy[enemyIndex].createMoves();
							//Uses move
							useMove(1,index);
							//Writes Move Info
							postAttackInfo(0);
							//Reassigns Moves
							enemy[enemyIndex].move[index]=savedMove;
							enemy[enemyIndex].createMoves();

							//Assigns Previous PP
							enemy[enemyIndex].move[0].pp=pp1;
							enemy[enemyIndex].move[1].pp=pp2;
							enemy[enemyIndex].move[2].pp=pp3;
							enemy[enemyIndex].move[3].pp=pp4;
						}
				}
				break;
			}
		}

		if(hit)
			System.out.println("Accuracy successful.");
		else
			System.out.println("Accuracy failure.");
		if(crit)
			System.out.println("Critical Hit");

		checkFainted();
	}

	//Adds text from attack to JTextfield
	public static void postAttackInfo(int userOfMove)
	{
		switch(userOfMove)
		{
			case 0:
				switch(user[userIndex].move[userCmd].mainEffect)
				{
					case DAMAGE:
						if(!nonPostingCase[0]&&hasPP)
						{
							if(!Mechanics.awayFromBattle[0]&&hit&&Mechanics.damage>0||user[userIndex].move[userCmd].sideEffect==Pokemon.Side_Effect.FIXED_DAMAGE)
							{
								enemyHit.play();
								b1.setFlicker(1);
							}

							boolean fixed=false;

							switch(user[userIndex].move[userCmd].sideEffect)
							{
								case FIXED_DAMAGE:
									crit=false;
									fixed=true;
								case FAINT:
								case SWIFT:
								case HIGH_CRIT:
								case OHKO:
								case QUICK_ATTACK:
								case NONE:
									b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										if(!fixed)
										b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(user[userIndex].move[userCmd].moveType,
										enemy[enemyIndex].type1,enemy[enemyIndex].type2)));

										if(user[userIndex].move[userCmd].sideEffect==Pokemon.Side_Effect.OHKO&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("It's a One-Hit-KO!");

										if(Mechanics.hasSubstitute[1])
										{
											b1.addText("The substitute took the hit for "+"Enemy "+enemy[enemyIndex].nickname+"!");
											checkSubstitute(1);
										}
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									break;
								case ABSORB:
									b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(user[userIndex].move[userCmd].moveType,
										enemy[enemyIndex].type1,enemy[enemyIndex].type2)));

										if(Mechanics.hasSubstitute[1])
										{
											b1.addText("The substitute took the hit for "+"Enemy "+enemy[enemyIndex].nickname+"!");
											checkSubstitute(1);
										}
										b1.addText("Absorbed health from "+"Enemy "+enemy[enemyIndex].nickname+"!");
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									break;
								case STAT:
									b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(user[userIndex].move[userCmd].moveType,
										enemy[enemyIndex].type1,enemy[enemyIndex].type2)));

										if(Mechanics.hasSubstitute[1])
										{
											b1.addText("The substitute took the hit for "+"Enemy "+enemy[enemyIndex].nickname+"!");
											checkSubstitute(1);
										}

										if(sideHit)
										{
											decStat.play();
											switch(user[userIndex].move[userCmd])
											{
												case ACID:
													b1.addText("The enemy's Defense fell!");
													break;
												case BUBBLE:
												case BUBBLEBEAM:
												case CONSTRICT:
													b1.addText("The enemy's Speed fell!");
													break;
												case PSYCHIC:
													b1.addText("The enemy's Special fell!");
													break;
											}
										}
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									break;
								case STATUS:
									b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(user[userIndex].move[userCmd].moveType,
										enemy[enemyIndex].type1,enemy[enemyIndex].type2)));

										if(Mechanics.hasSubstitute[1])
										{
											b1.addText("The substitute took the hit for "+"Enemy "+enemy[enemyIndex].nickname+"!");
											checkSubstitute(1);
										}

										if(sideHit)
										{
											switch(user[userIndex].move[userCmd])
											{
												case AURORA_BEAM:
												case BLIZZARD:
												case ICE_BEAM:
												case ICE_PUNCH:
													b1.addText("The enemy is frozen solid!");
													frz.play();
													break;
												case BODY_SLAM:
												case LICK:
												case THUNDER:
												case THUNDERPUNCH:
												case THUNDERBOLT:
												case THUNDERSHOCK:
													b1.addText("The enemy is paralyzed! It may not attack!");
													par.play();
													break;
												case EMBER:
												case FIRE_BLAST:
												case FIRE_PUNCH:
												case FLAMETHROWER:
													b1.addText("The enemy was burned!");
													brn.play();
													break;
												case POISON_STING:
												case SLUDGE:
												case SMOG:
													b1.addText("The enemy was poisoned!");
													psn.play();
													break;
											}
										}
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									break;
								case SUBSTATUS:
									b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(user[userIndex].move[userCmd].moveType,
										enemy[enemyIndex].type1,enemy[enemyIndex].type2)));

										if(Mechanics.hasSubstitute[1])
										{
											b1.addText("The substitute took the hit for "+"Enemy "+enemy[enemyIndex].nickname+"!");
											checkSubstitute(1);
										}

										if(sideHit)
										{
											switch(user[userIndex].move[userCmd])
											{
												case BITE:
												case BONE_CLUB:
												case HEADBUTT:
												case HYPER_FANG:
												case ROLLING_KICK:
													b1.addText("The enemy flinched!");
													break;
												case CONFUSION:
												case PSYBEAM:
													b1.addText("The enemy became confused!");
													break;
											}
										}
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									break;
								case CHARGE:
									switch(user[userIndex].move[userCmd])
									{
										case RAZOR_WIND:
										case SKY_ATTACK:
										case SKULL_BASH:
										case SOLARBEAM:
											if(Mechanics.charging[0])
											{
												b1.addText(user[userIndex].nickname+" is charging!");
											}
											else
											{
												b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);

												if(hit)
												{
													if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
														b1.addText("Critical Hit!");

													b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(user[userIndex].move[userCmd].moveType,
													enemy[enemyIndex].type1,enemy[enemyIndex].type2)));

													if(Mechanics.hasSubstitute[1])
													{
														b1.addText("The substitute took the hit for "+"Enemy "+enemy[enemyIndex].nickname+"!");
														checkSubstitute(1);
													}
												}
												else
												{
													b1.addText("The Attack Missed!");
												}
											}
											break;
										case HYPER_BEAM:
											if(Mechanics.charging[0])
											{
												b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);

												if(hit)
												{
													if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
														b1.addText("Critical Hit!");

													b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(user[userIndex].move[userCmd].moveType,
													enemy[enemyIndex].type1,enemy[enemyIndex].type2)));

													if(Mechanics.hasSubstitute[1])
													{
														b1.addText("The substitute took the hit for "+"Enemy "+enemy[enemyIndex].nickname+"!");
														checkSubstitute(1);
													}
												}
												else
												{
													b1.addText("The Attack Missed!");
												}
											}
											else
											{
												b1.addText(user[userIndex].nickname+" has to recharge!");
											}
											break;
									}
									break;
								case RECOIL:
									if(user[userIndex].move[userCmd]!=Pokemon.Move.NONE)
										b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
									else
										b1.addText(user[userIndex].nickname+" used STRUGGLE");

									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(user[userIndex].move[userCmd].moveType,
										enemy[enemyIndex].type1,enemy[enemyIndex].type2)));

										if(Mechanics.damage>0&&Mechanics.effective!=Mechanics.Effective.NONE)
										b1.addText(user[userIndex].nickname+" was hit with recoil!");

										if(Mechanics.hasSubstitute[1])
										{
											b1.addText("The substitute took the hit for "+"Enemy "+enemy[enemyIndex].nickname+"!");
											checkSubstitute(1);
										}
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									break;
								case COLLECT_DAMAGE:
									switch(user[userIndex].move[userCmd])
									{
										case BIDE:
											if(Mechanics.turnsMultiTurn[0]==0)
											{
												b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
												if(hit)
												{
													if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
														b1.addText("Critical Hit!");

													b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(user[userIndex].move[userCmd].moveType,
													enemy[enemyIndex].type1,enemy[enemyIndex].type2)));

													if(Mechanics.hasSubstitute[1])
													{
														b1.addText("The substitute took the hit for "+"Enemy "+enemy[enemyIndex].nickname+"!");
														checkSubstitute(1);
													}
												}
												else
												{
													b1.addText("The Attack Missed!");
												}
											}
											else
											{
												b1.addText(user[userIndex].nickname+" is biding its time!");
											}
											break;
										case COUNTER:
											b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
											if(hit&&Mechanics.storedDamage[0]>0)
											{
												if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
													b1.addText("Critical Hit!");

												b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(user[userIndex].move[userCmd].moveType,
												enemy[enemyIndex].type1,enemy[enemyIndex].type2)));

												if(Mechanics.hasSubstitute[1])
												{
													b1.addText("The substitute took the hit for "+"Enemy "+enemy[enemyIndex].nickname+"!");
													checkSubstitute(1);
												}
											}
											else
											{
												b1.addText("The Attack Missed!");
											}
											break;
									}
									break;
								case MULTI_HIT:
									b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(user[userIndex].move[userCmd].moveType,
										enemy[enemyIndex].type1,enemy[enemyIndex].type2)));
										if(Mechanics.damage>0)
										b1.addText("Hit "+timesHit+" times!");

										if(Mechanics.hasSubstitute[1])
										{
											b1.addText("The substitute took the hit for "+"Enemy "+enemy[enemyIndex].nickname+"!");
											checkSubstitute(1);
										}
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									break;
								case MULTI_TURN:
									b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										if(Mechanics.hasSubstitute[1])
										{
											b1.addText("The substitute took the hit for "+"Enemy "+enemy[enemyIndex].nickname+"!");
											checkSubstitute(1);
										}

										if(Mechanics.turnsMultiTurn[0]>0)
										{
											b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(user[userIndex].move[userCmd].moveType,
											enemy[enemyIndex].type1,enemy[enemyIndex].type2)));

											b1.addText("The enemy can't move!");
										}
										else
										{
											b1.addText("The attack wore off!");
										}
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									break;
								case FURY:
									b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
									if(user[userIndex].move[userCmd]!=Pokemon.Move.RAGE)
									b1.addText(user[userIndex].nickname+" is attacking violently!");
									else
									{
										b1.addText(user[userIndex].nickname+"'s RAGE is building!");

										//Attack Boost
										if(user[userIndex].atkStage<6)
										user[userIndex].atkStage++;
										user[userIndex].atk=(int)((Mechanics.calcOtherStat(user[userIndex].baseATK,user[userIndex].ATK_IV,user[userIndex].ATK_EV,user[userIndex].level))*
										Mechanics.stageMultiplier(user[userIndex].atkStage));
										b1.addText(user[userIndex].nickname+"'s ATTACK rose!");
									}
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(user[userIndex].move[userCmd].moveType,
										enemy[enemyIndex].type1,enemy[enemyIndex].type2)));

										if(Mechanics.hasSubstitute[1])
										{
											b1.addText("The substitute took the hit for "+"Enemy "+enemy[enemyIndex].nickname+"!");
											checkSubstitute(1);
										}
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									if(Mechanics.turnsMultiTurn[0]==0&&user[userIndex].move[userCmd]!=Pokemon.Move.RAGE)
										b1.addText(user[userIndex].nickname+" became confused due to fatigue!");
									break;
								case HIDE:
									if(!Mechanics.awayFromBattle[0])
									{
										b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
										if(hit)
										{
											if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

											b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(user[userIndex].move[userCmd].moveType,
											enemy[enemyIndex].type1,enemy[enemyIndex].type2)));

											if(Mechanics.hasSubstitute[1])
											{
												b1.addText("The substitute took the hit for "+"Enemy "+enemy[enemyIndex].nickname+"!");
												checkSubstitute(1);
											}
										}
										else
										{
											b1.addText("The Attack Missed!");
										}
									}
									else
									{
										if(user[userIndex].move[userCmd]==Pokemon.Move.DIG)
											b1.addText(user[userIndex].nickname+" burrowed underground!");
										else
											b1.addText(user[userIndex].nickname+" flew up high!");
									}
									break;
							}
						}
						break;
					case RAISE_STAT:
						b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);

						incStat.play();

						switch(user[userIndex].move[userCmd])
						{
							case SHARPEN:
							case MEDITATE:
							case SWORDS_DANCE:
								if(user[userIndex].atkStage>=6)
									b1.addText(user[userIndex].nickname+"'s Attack won't go any higher!");
								else
									b1.addText(user[userIndex].nickname+"'s Attack Rose!");
								break;
							case DEFENSE_CURL:
							case HARDEN:
							case WITHDRAW:
							case ACID_ARMOR:
							case BARRIER:
								if(user[userIndex].defStage>=6)
									b1.addText(user[userIndex].nickname+"'s Defense won't go any higher!");
								else
									b1.addText(user[userIndex].nickname+"'s Defense Rose!");
								break;
							case AGILITY:
								if(user[userIndex].speedStage>=6)
									b1.addText(user[userIndex].nickname+"'s Speed won't go any higher!");
								else
									b1.addText(user[userIndex].nickname+"'s Speed Rose!");
								break;
							case GROWTH:
							case AMNESIA:
								if(user[userIndex].spclStage>=6)
									b1.addText(user[userIndex].nickname+"'s Special won't go any higher!");
								else
									b1.addText(user[userIndex].nickname+"'s Special Rose!");
								break;
							case DOUBLE_TEAM:
							case MINIMIZE:
								if(user[userIndex].evaStage>=6)
									b1.addText(user[userIndex].nickname+"'s Evasiveness won't go any higher!");
								else
									b1.addText(user[userIndex].nickname+"'s Evasiveness Rose!");
								break;

						}
						break;
					case LOWER_STAT:
						b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);

						if(hit)
						decStat.play();

						if(hit){
						switch(user[userIndex].move[userCmd])
						{
							case GROWL:
								if(enemy[enemyIndex].atkStage<=-6)
									b1.addText("The enemy's Attack won't fall anymore!");
								else
									b1.addText("The enemy's Attack fell!");
								break;
							case SCREECH:
							case LEER:
							case TAIL_WHIP:
								if(enemy[enemyIndex].defStage<=-6)
									b1.addText("The enemy's Defense won't fall anymore!");
								else
									b1.addText("The enemy's Defense fell!");
								break;
							case FLASH:
							case KINESIS:
							case SAND_ATTACK:
							case SMOKESCREEN:
								if(enemy[enemyIndex].accuracyStage<=-6)
									b1.addText("The enemy's Accuracy won't fall anymore!");
								else
									b1.addText("The enemy's Accuracy fell!");
								break;
							case STRING_SHOT:
								if(enemy[enemyIndex].speedStage<=-6)
									b1.addText("The enemy's Speed won't fall anymore!");
								else
									b1.addText("The enemy's Speed fell!");
								break;
						}
						}
						else
							b1.addText("The move failed!");
						break;
					case INFLICT_STATUS:
						b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
						switch(user[userIndex].move[userCmd])
						{
							case CONFUSE_RAY:
							case SUPERSONIC:
								if(sideHit)
								{
									b1.addText("The enemy became confused!");
									special.play();
								}
								else
									b1.addText("The move failed!");
								break;
							case GLARE:
							case STUN_SPORE:
							case THUNDER_WAVE:
								if(sideHit)
								{
									b1.addText("The enemy is paralyzed! It may not attack!");
									par.play();
								}
								else
									b1.addText("The move failed!");
								break;
							case HYPNOSIS:
							case LOVELY_KISS:
							case SING:
							case SLEEP_POWDER:
							case SPORE:
								if(sideHit)
								{
									b1.addText("The enemy fell asleep!");
									slp.play();
								}
								else
									b1.addText("The move failed!");
								break;
							case LEECH_SEED:
								if(sideHit)
								{
									b1.addText("The enemy was seeded!");
									special.play();
								}
								else
									b1.addText("The move failed!");
								break;
							case POISON_GAS:
							case POISONPOWDER:
							case TOXIC:
								if(sideHit)
								{
									b1.addText("The enemy was poisoned!");
									psn.play();
								}
								else
									b1.addText("The move failed!");
								break;
						}
						break;
					case SPECIAL:

						special.play();

						switch(user[userIndex].move[userCmd])
						{
							case CONVERSION:
								b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
								b1.addText(user[userIndex].nickname+" is now the same type as the enemy!");
								break;
							case DISABLE:
								b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
								if(hit)
								{
									b1.addText("Enemy "+enemy[enemyIndex].nickname+"'s "+enemy[enemyIndex].move[Mechanics.moveDisabled[1]]+
										" was disabled!");
								}
								else
								{
									b1.addText("The move failed!");
								}
								break;
							case FOCUS_ENERGY:
								Mechanics.isHighCritical[0]=true;
								b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
								b1.addText(user[userIndex].nickname+" is getting pumped!");
								break;
							case HAZE:
								b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
								b1.addText("All status changes are eliminated!");
								break;
							case LIGHT_SCREEN:
							case REFLECT:
								b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
								b1.addText("A protective shield was created!");
								break;
							case METRONOME:
							case MIMIC:
							case MIRROR_MOVE:
							case TRANSFORM:
								break;
							case MIST:
								b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
								if(hit)
								{
									b1.addText("A protective mist surrounded the area!");
								}
								else
									b1.addText("The move failed!");
								break;
							case RECOVER:
							case SOFTBOILED:
							case REST:
								b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);

								if(user[userIndex].move[userCmd]==Pokemon.Move.REST&&hit)
									b1.addText(user[userIndex].nickname+" started sleeping!");

								if(hit)
									b1.addText(user[userIndex].nickname+" recovered health!");
								else
									b1.addText("The move failed!");
								break;
							case SPLASH:
								b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
								if(hit)
								b1.addText("Nothing Happened!");
								break;
							case SUBSTITUTE:
								b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
								if(hit)
								{
									b1.addText(user[userIndex].nickname+" created a substitute!");
								}
								else
									b1.addText("The move failed!");
								break;
							case ROAR:
							case TELEPORT:
							case WHIRLWIND:
								b1.addText(user[userIndex].nickname+" used "+user[userIndex].move[userCmd]);
								if(!hit)
									b1.addText("The move failed!");
								else
								{
									if(user[userIndex].move[userCmd]==Pokemon.Move.TELEPORT)
										b1.addText(user[userIndex].nickname+" teleported to safety!");
									else
										b1.addText(user[userIndex].nickname+" sent the enemy away!");
								}
								break;
						}
						break;
				}

				crit=false;
				hit=false;
				sideHit=false;
				break;
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		case 1:
	{
		switch(enemy[enemyIndex].move[enemyCmd].mainEffect)
				{
					case DAMAGE:
						if(!nonPostingCase[1]&&hasPP)
						{
							boolean fixed=false;

							if(!Mechanics.awayFromBattle[1]&&hit&&Mechanics.damage>0)
							{
								userHit.play();
								b1.setFlicker(0);
							}

							switch(enemy[enemyIndex].move[enemyCmd].sideEffect)
							{
								case FIXED_DAMAGE:
									crit=false;
									fixed=true;
								case FAINT:
								case SWIFT:
								case HIGH_CRIT:
								case OHKO:
								case QUICK_ATTACK:
								case NONE:
									b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										if(!fixed)
										b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(enemy[enemyIndex].move[enemyCmd].moveType,
										user[userIndex].type1,user[userIndex].type2)));

										if(enemy[enemyIndex].move[enemyCmd].sideEffect==Pokemon.Side_Effect.OHKO&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("It's a One-Hit-KO!");

										if(Mechanics.hasSubstitute[0])
										{
											b1.addText("The substitute took the hit for "+user[userIndex].nickname+"!");
											checkSubstitute(0);
										}
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									break;
								case ABSORB:
									b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(enemy[enemyIndex].move[enemyCmd].moveType,
										user[userIndex].type1,user[userIndex].type2)));

										if(Mechanics.hasSubstitute[0])
										{
											b1.addText("The substitute took the hit for "+user[userIndex].nickname+"!");
											checkSubstitute(0);
										}

										b1.addText("Absorbed health from "+user[userIndex].nickname+"!");
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									break;
								case STAT:
									b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(enemy[enemyIndex].move[enemyCmd].moveType,
										user[userIndex].type1,user[userIndex].type2)));

										if(Mechanics.hasSubstitute[0])
										{
											b1.addText("The substitute took the hit for "+user[userIndex].nickname+"!");
											checkSubstitute(0);
										}

										if(sideHit)
										{
											decStat.play();

											switch(enemy[enemyIndex].move[enemyCmd])
											{
												case ACID:
													b1.addText(user[userIndex].nickname+"'s Defense fell!");
													break;
												case BUBBLE:
												case BUBBLEBEAM:
												case CONSTRICT:
													b1.addText(user[userIndex].nickname+"'s Speed fell!");
													break;
												case PSYCHIC:
													b1.addText(user[userIndex].nickname+"'s Special fell!");
													break;
											}
										}
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									break;
								case STATUS:
									b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(enemy[enemyIndex].move[enemyCmd].moveType,
										user[userIndex].type1,user[userIndex].type2)));

										if(Mechanics.hasSubstitute[0])
										{
											b1.addText("The substitute took the hit for "+user[userIndex].nickname+"!");
											checkSubstitute(0);
										}

										if(sideHit)
										{
											switch(enemy[enemyIndex].move[enemyCmd])
											{
												case AURORA_BEAM:
												case BLIZZARD:
												case ICE_BEAM:
												case ICE_PUNCH:
													b1.addText(user[userIndex].nickname+" is frozen solid!");
													frz.play();
													break;
												case BODY_SLAM:
												case LICK:
												case THUNDER:
												case THUNDERPUNCH:
												case THUNDERBOLT:
												case THUNDERSHOCK:
													b1.addText(user[userIndex].nickname+" is paralyzed! It may not attack!");
													par.play();
													break;
												case EMBER:
												case FIRE_BLAST:
												case FIRE_PUNCH:
												case FLAMETHROWER:
													b1.addText(user[userIndex].nickname+" was burned!");
													brn.play();
													break;
												case POISON_STING:
												case SLUDGE:
												case SMOG:
													b1.addText(user[userIndex].nickname+" was poisoned!");
													psn.play();
													break;
											}
										}
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									break;
								case SUBSTATUS:
									b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(enemy[enemyIndex].move[enemyCmd].moveType,
										user[userIndex].type1,user[userIndex].type2)));

										if(Mechanics.hasSubstitute[0])
										{
											b1.addText("The substitute took the hit for "+user[userIndex].nickname+"!");
											checkSubstitute(0);
										}

										if(sideHit)
										{
											switch(enemy[enemyIndex].move[enemyCmd])
											{
												case BITE:
												case BONE_CLUB:
												case HEADBUTT:
												case HYPER_FANG:
												case ROLLING_KICK:
													b1.addText(user[userIndex].nickname+" flinched!");
													break;
												case CONFUSION:
												case PSYBEAM:
													b1.addText(user[userIndex].nickname+" became confused!");
													break;
											}
										}
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									break;
								case CHARGE:
									switch(enemy[enemyIndex].move[enemyCmd])
									{
										case RAZOR_WIND:
										case SKY_ATTACK:
										case SKULL_BASH:
										case SOLARBEAM:
											if(Mechanics.charging[1])
											{
												b1.addText("Enemy "+enemy[enemyIndex].nickname+" is charging!");
											}
											else
											{
												b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);

												if(hit)
												{
													if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
														b1.addText("Critical Hit!");

													b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(enemy[enemyIndex].move[enemyCmd].moveType,
													user[userIndex].type1,user[userIndex].type2)));

													if(Mechanics.hasSubstitute[0])
													{
														b1.addText("The substitute took the hit for "+user[userIndex].nickname+"!");
														checkSubstitute(0);
													}
												}
												else
												{
													b1.addText("The Attack Missed!");
												}
											}
											break;
										case HYPER_BEAM:
											if(Mechanics.charging[1])
											{
												b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);

												if(hit)
												{
													if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
														b1.addText("Critical Hit!");

													b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(enemy[enemyIndex].move[enemyCmd].moveType,
													user[userIndex].type1,user[userIndex].type2)));

													if(Mechanics.hasSubstitute[0])
													{
														b1.addText("The substitute took the hit for "+user[userIndex].nickname+"!");
														checkSubstitute(0);
													}
												}
												else
												{
													b1.addText("The Attack Missed!");
												}
											}
											else
											{
												b1.addText("Enemy "+enemy[enemyIndex].nickname+" has to recharge!");
											}
											break;
									}
									break;
								case RECOIL:
									b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(enemy[enemyIndex].move[enemyCmd].moveType,
										user[userIndex].type1,user[userIndex].type2)));

										if(Mechanics.hasSubstitute[0])
										{
											b1.addText("The substitute took the hit for "+user[userIndex].nickname+"!");
											checkSubstitute(0);
										}

										if(Mechanics.damage>0&&Mechanics.effective!=Mechanics.Effective.NONE)
										b1.addText("Enemy "+enemy[enemyIndex].nickname+" was hit with recoil!");
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									break;
								case COLLECT_DAMAGE:
									switch(enemy[enemyIndex].move[enemyCmd])
									{
										case BIDE:
											if(Mechanics.turnsMultiTurn[1]==0)
											{
												b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
												if(hit)
												{
													if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
														b1.addText("Critical Hit!");

													b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(enemy[enemyIndex].move[enemyCmd].moveType,
													user[userIndex].type1,user[userIndex].type2)));

													if(Mechanics.hasSubstitute[0])
													{
														b1.addText("The substitute took the hit for "+user[userIndex].nickname+"!");
														checkSubstitute(0);
													}
												}
												else
												{
													b1.addText("The Attack Missed!");
												}
											}
											else
											{
												b1.addText("Enemy "+enemy[enemyIndex].nickname+" is biding its time!");
											}
											break;
										case COUNTER:
											b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
											if(hit&&Mechanics.storedDamage[1]>0)
											{
												if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
													b1.addText("Critical Hit!");

												b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(enemy[enemyIndex].move[enemyCmd].moveType,
												user[userIndex].type1,user[userIndex].type2)));

												if(Mechanics.hasSubstitute[0])
												{
													b1.addText("The substitute took the hit for "+user[userIndex].nickname+"!");
													checkSubstitute(0);
												}
											}
											else
											{
												b1.addText("The Attack Missed!");
											}
											break;
									}
									break;
								case MULTI_HIT:
									b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(enemy[enemyIndex].move[enemyCmd].moveType,
										user[userIndex].type1,user[userIndex].type2)));

										if(Mechanics.hasSubstitute[0])
										{
											b1.addText("The substitute took the hit for "+user[userIndex].nickname+"!");
											checkSubstitute(0);
										}

										if(Mechanics.damage>0)
										b1.addText("Hit "+timesHit+" times!");
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									break;
								case MULTI_TURN:
									b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										if(Mechanics.hasSubstitute[0])
										{
											b1.addText("The substitute took the hit for "+user[userIndex].nickname+"!");
											checkSubstitute(0);
										}

										if(Mechanics.turnsMultiTurn[1]>0)
										{
											b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(enemy[enemyIndex].move[enemyCmd].moveType,
											user[userIndex].type1,user[userIndex].type2)));

											b1.addText(user[userIndex].nickname+" can't move!");
										}
										else
										{
											b1.addText("The attack wore off!");
										}
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									break;
								case FURY:
									b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
									if(enemy[enemyIndex].move[enemyCmd]!=Pokemon.Move.RAGE)
									b1.addText("Enemy "+enemy[enemyIndex].nickname+" is attacking violently!");
									else
									{
										b1.addText("Enemy "+enemy[enemyIndex].nickname+"'s RAGE is building!");

										//Attack Boost
										if(enemy[enemyIndex].atkStage<6)
										enemy[enemyIndex].atkStage++;
										enemy[enemyIndex].atk=(int)((Mechanics.calcOtherStat(enemy[enemyIndex].baseATK,enemy[enemyIndex].ATK_IV,enemy[enemyIndex].ATK_EV,enemy[enemyIndex].level))*
										Mechanics.stageMultiplier(enemy[enemyIndex].atkStage));
										b1.addText("Enemy "+enemy[enemyIndex].nickname+"'s ATTACK rose!");
									}
									if(hit)
									{
										if(crit&&Mechanics.effective!=Mechanics.Effective.NONE)
											b1.addText("Critical Hit!");

										b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(enemy[enemyIndex].move[enemyCmd].moveType,
										user[userIndex].type1,user[userIndex].type2)));

										if(Mechanics.hasSubstitute[0])
										{
											b1.addText("The substitute took the hit for "+user[userIndex].nickname+"!");
											checkSubstitute(0);
										}
									}
									else
									{
										b1.addText("The Attack Missed!");
									}
									if(Mechanics.turnsMultiTurn[1]==0&&enemy[enemyIndex].move[enemyCmd]!=Pokemon.Move.RAGE)
										b1.addText("Enemy "+enemy[enemyIndex].nickname+" became confused due to fatigue!");
									break;
								case HIDE:
									if(!Mechanics.awayFromBattle[1])
									{
										b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
										if(hit)
										{
											if(crit)
												b1.addText("Critical Hit!");

											b1.addText(Mechanics.multiplierToString(Mechanics.typeMultiplier(enemy[enemyIndex].move[enemyCmd].moveType,
											user[userIndex].type1,user[userIndex].type2)));

											if(Mechanics.hasSubstitute[0])
											{
												b1.addText("The substitute took the hit for "+user[userIndex].nickname+"!");
												checkSubstitute(0);
											}
										}
										else
										{
											b1.addText("The Attack Missed!");
										}
									}
									else
									{
										if(enemy[enemyIndex].move[enemyCmd]==Pokemon.Move.DIG)
											b1.addText("Enemy "+enemy[enemyIndex].nickname+" burrowed underground!");
										else
											b1.addText("Enemy "+enemy[enemyIndex].nickname+" flew up high!");
									}
									break;
							}
						}
						break;
					case RAISE_STAT:
						b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
						incStat.play();
						switch(enemy[enemyIndex].move[enemyCmd])
						{
							case SHARPEN:
							case MEDITATE:
							case SWORDS_DANCE:
								if(enemy[enemyIndex].atkStage>=6)
									b1.addText("Enemy "+enemy[enemyIndex].nickname+"'s Attack won't go any higher!");
								else
									b1.addText("Enemy "+enemy[enemyIndex].nickname+"'s Attack Rose!");
								break;
							case DEFENSE_CURL:
							case HARDEN:
							case WITHDRAW:
							case ACID_ARMOR:
							case BARRIER:
								if(enemy[enemyIndex].defStage>=6)
									b1.addText("Enemy "+enemy[enemyIndex].nickname+"'s Defense won't go any higher!");
								else
									b1.addText("Enemy "+enemy[enemyIndex].nickname+"'s Defense Rose!");
								break;
							case AGILITY:
								if(enemy[enemyIndex].speedStage>=6)
									b1.addText("Enemy "+enemy[enemyIndex].nickname+"'s Speed won't go any higher!");
								else
									b1.addText("Enemy "+enemy[enemyIndex].nickname+"'s Speed Rose!");
								break;
							case GROWTH:
							case AMNESIA:
								if(enemy[enemyIndex].spclStage>=6)
									b1.addText("Enemy "+enemy[enemyIndex].nickname+"'s Special won't go any higher!");
								else
									b1.addText("Enemy "+enemy[enemyIndex].nickname+"'s Special Rose!");
								break;
							case DOUBLE_TEAM:
							case MINIMIZE:
								if(enemy[enemyIndex].evaStage>=6)
									b1.addText("Enemy "+enemy[enemyIndex].nickname+"'s Evasiveness won't go any higher!");
								else
									b1.addText("Enemy "+enemy[enemyIndex].nickname+"'s Evasiveness Rose!");
								break;

						}
						break;
					case LOWER_STAT:
						b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);

						if(hit)
							decStat.play();

						if(hit)
						switch(enemy[enemyIndex].move[enemyCmd])
						{
							case GROWL:
								if(user[userIndex].atkStage<=-6)
									b1.addText(user[userIndex].nickname+"'s Attack won't fall anymore!");
								else
									b1.addText(user[userIndex].nickname+"'s Attack fell!");
								break;
							case SCREECH:
							case LEER:
							case TAIL_WHIP:
								if(user[userIndex].defStage<=-6)
									b1.addText(user[userIndex].nickname+"'s Defense won't fall anymore!");
								else
									b1.addText(user[userIndex].nickname+"'s Defense fell!");
								break;
							case FLASH:
							case KINESIS:
							case SAND_ATTACK:
							case SMOKESCREEN:
								if(user[userIndex].accuracyStage<=-6)
									b1.addText(user[userIndex].nickname+"'s Accuracy won't fall anymore!");
								else
									b1.addText(user[userIndex].nickname+"'s Accuracy fell!");
								break;
							case STRING_SHOT:
								if(user[userIndex].speedStage<=-6)
									b1.addText(user[userIndex].nickname+"'s Speed won't fall anymore!");
								else
									b1.addText(user[userIndex].nickname+"'s Speed fell!");
								break;
						}
						else
							b1.addText("The move failed!");
						break;
					case INFLICT_STATUS:
						b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
						switch(enemy[enemyIndex].move[enemyCmd])
						{
							case CONFUSE_RAY:
							case SUPERSONIC:
								if(sideHit)
								{
									b1.addText(user[userIndex].nickname+" became confused!");
									special.play();
								}
								else
									b1.addText("The move failed!");
								break;
							case GLARE:
							case STUN_SPORE:
							case THUNDER_WAVE:
								if(sideHit)
								{
									b1.addText(user[userIndex].nickname+" is paralyzed! It may not attack!");
									par.play();
								}
								else
									b1.addText("The move failed!");
								break;
							case HYPNOSIS:
							case LOVELY_KISS:
							case SING:
							case SLEEP_POWDER:
							case SPORE:
								if(sideHit)
								{
									b1.addText(user[userIndex].nickname+" fell asleep!");
									slp.play();
								}
								else
									b1.addText("The move failed!");
								break;
							case LEECH_SEED:
								if(sideHit)
								{
									b1.addText(user[userIndex].nickname+" was seeded!");
									special.play();
								}
								else
									b1.addText("The move failed!");
								break;
							case POISON_GAS:
							case POISONPOWDER:
							case TOXIC:
								if(sideHit)
								{
									b1.addText(user[userIndex].nickname+" was poisoned!");
									psn.play();
								}
								else
									b1.addText("The move failed!");
								break;
						}
						break;
					case SPECIAL:

						special.play();

						switch(enemy[enemyIndex].move[enemyCmd])
						{
							case CONVERSION:
								b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
								b1.addText("Enemy "+enemy[enemyIndex].nickname+" is now the same type as "+user[userIndex].nickname+"!");
								break;
							case DISABLE:
								b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
								if(hit)
								{
									b1.addText(user[userIndex].nickname+"'s "+user[userIndex].move[Mechanics.moveDisabled[0]]+
										" was disabled!");
								}
								else
								{
									b1.addText("The move failed!");
								}
								break;
							case FOCUS_ENERGY:
								Mechanics.isHighCritical[1]=true;
								b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
								b1.addText("Enemy "+enemy[enemyIndex].nickname+" is getting pumped!");
								break;
							case HAZE:
								b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
								b1.addText("All status changes are eliminated!");
								break;
							case LIGHT_SCREEN:
							case REFLECT:
								b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
								b1.addText("A protective shield was created!");
								break;
							case METRONOME:
							case MIMIC:
							case MIRROR_MOVE:
							case TRANSFORM:
								break;
							case MIST:
								b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
								if(hit)
								{
									b1.addText("A protective mist surrounded the area!");
								}
								else
									b1.addText("The move failed!");
								break;
							case RECOVER:
							case SOFTBOILED:
							case REST:
								b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);

								if(enemy[enemyIndex].move[enemyCmd]==Pokemon.Move.REST&&hit)
									b1.addText(enemy[enemyIndex].nickname+" started sleeping!");

								if(hit)
									b1.addText("It regenerated health!");
								else
									b1.addText("The move failed!");
								break;
							case SPLASH:
								b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
								if(hit)
								b1.addText("Nothing Happened!");
								break;
							case SUBSTITUTE:
								b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
								if(hit)
								{
									b1.addText("Enemy "+enemy[enemyIndex].nickname+" created a substitute!");
								}
								else
									b1.addText("The move failed!");
								break;
							case ROAR:
							case TELEPORT:
							case WHIRLWIND:
								b1.addText("Enemy "+enemy[enemyIndex].nickname+" used "+enemy[enemyIndex].move[enemyCmd]);
								if(!hit)
									b1.addText("The move failed!");
								else
								{
									if(enemy[enemyIndex].move[enemyCmd]==Pokemon.Move.TELEPORT)
										b1.addText(enemy[enemyIndex].nickname+" teleported away!");
									else
										b1.addText(enemy[enemyIndex].nickname+" sent you away!");
								}
								break;
						}
						break;
				}
				break;
		}
	}

		crit=false;
		hit=false;
		sideHit=false;
	}

	public static void forceToFront()
	{
		if (b1 != null)
		b1.tooFront();
	}

	public static void loopAudioAsset(URL url)
	{
		try
		{
			sequencer.open();
			sequencer.setSequence(MidiSystem.getSequence(url));
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
		}
		catch(Exception e)
		{
			System.out.println("Error while trying to play asset!");
		}
	}

	public static void stopAudioAsset()
	{
		sequencer.stop();
	}
}