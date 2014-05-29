//////////////////////////////////////
//Driver Main Class for Jokemon
//
//By Camtendo (logic), Justinian (cartography), and Patches (pre-build)
//
//

//When Editing Tilemaps Change:
//createCurrentMap
//saveTileSet
//switchMap

import java.awt.Image;
import java.awt.Point;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JOptionPane;
import javax.swing.JApplet;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.*;
import java.net.URI;
import java.applet.AudioClip;
import java.util.Scanner;

public class JokemonDriver extends JPanel implements Runnable,KeyListener
{
	public final boolean DEBUG=false;
	public final boolean DONATE=false;
	//Pick Correct Version and TitleScreen at compile time

	public final static String VERSION="Peaches";
	TitleScreen_Peaches title=new TitleScreen_Peaches();

	//public static final String VERSION="Cream";
	//TitleScreen_Cream title=new TitleScreen_Cream();

	static Image icon,loading;
	static File file=new File("savedata");
	static boolean titleScreen=true;
	static boolean makeTheArea = false;
	private townMap map = new townMap();

	//Location Enums
	public enum Area
	{
		//Towns and Cities
		Stringville, Args_Harbor, Mount_Java, Villa_Del_Joe, Streamreader_Hotel,
		Recursive_Hot_Springs, Polymorph_Town, Binary_City, Champions_Walk, Victory_Road,
		Peach_City, Cream_City, Nested_Village, Enumville,

		//Routes
		Route_0, Route_1, Route_2, Route_3, Route_4, Route_5, Route_6, Route_7,
		Route_8, Route_9, Route_10,Route_11,Route_12,Route_13, Route_14,
		Route_ARRAYINDEXOUTOFBOUNDSEXCEPTION,

		Null_Zone,Intville,Slipspace,Mountain_Dew_Paradise,

		Pokecenter,Mart,MegaMart,YourHouse,Babbs_Lab,Rival_House,H_Border,
		V_Border,

		Generic_1, Generic_2, Generic_3, Generic_4, Generic_5, Generic_6,

		Gym_1,Gym_2,Gym_3,Gym_4,Gym_5,Gym_6,Gym_7,Gym_8,

		J_Inc_Radio_Tower,J_Inc_Building,Hotel_Lobby,Elite_4,Battle_Tower,

		Hexadecimal_Forest,Class_Cave,Diglett_Cave,Moltres_Cave,Articuno_Cave,Power_Plant,Public_Cave,Primal_Cave,
		Java_Cave, Victory_Road_Cave, Challenge_Cave,

		Rocket_Right_Tower,Rocket_Left_Tower,Rocket_Central_Tower,

		Lighthouse, Lighthouse_F2,
	}

	static Area areasWithPCs[]={Area.Stringville,Area.Args_Harbor,Area.Mount_Java,Area.Villa_Del_Joe,Area.Streamreader_Hotel,
	Area.Recursive_Hot_Springs,Area.Polymorph_Town,Area.Binary_City,Area.Nested_Village,Area.Enumville, Area.Route_6, Area.Champions_Walk, Area.Elite_4,Area.Peach_City,Area.Cream_City};

	static int houseInt = 0;
	ErrorWindow messageBox;

	//Minigame Variables
	int minigameInt=0;
	boolean playingMinigame=false;
	boolean invokeMinigame=false;

	//Cartographer Vars
	boolean cartographer=false;
	int assignTileIntOutdoors=0;
	int assignTileIntIndoors=0;

	static boolean performingAction = false;

	//LAN Trade Vars
	TradeWindow tWin;
	static boolean CONNECTED=false;
	static int port=45897;
	static String ipAddress;
	static String netName;

	static private Socket connection;
	static private Scanner input;

	static private ServerSocket ss;
	static private InputStream sin;
	static private OutputStream sout;
	static private DataInputStream in;
	static private DataOutputStream out;
	static private InetAddress ipAddresss;
		//Friend Vars
		static String friendName="";
		static String[] friendPokemon={"","","","","",""};
		static boolean tradeConfirm=false;
		static boolean friendTradeConfirm=false;
		static int offerIndex;
		static int friendOfferIndex;


	//Tileset Values
	final byte OUTDOORS = 0;
	final byte INDOORS = 1;
	byte tileSet = OUTDOORS;

	//Area Vars
	static Area area=Area.Stringville;
	static int[][] currentArea;
	static AudioClip bgm;
	Mart mart = new Mart();

    //Tileset Variables
	Image tileImgOutdoors[]=new Image[246];
	Image tileImgIndoors[]=new Image[366];

	//Window Vars
	private JFrame jf;

    //Thread Vars
	Thread thread;

	//Frame Rate Vars
	String fps="1";
	long lastFPS;
	int frames, slp;

	//Location Vars
	public static Point location=new Point(15,12);
	static Point widthHeight = new Point(51,55);

	//Character Vars
	Image charStand[]=new Image[4];
	Image charWalk1[]=new Image[4];
	Image charWalk2[]=new Image[4];
	Image charSurf[]=new Image[4];
	Image charBike0[]=new Image[4];
	Image charBike1[]=new Image[4];
	Image charBike2[]=new Image[4];
	static int direction=270;
	boolean moving=false;
	int movingFrame=0;
	boolean surfing = false;
	boolean bicycling=false;
	boolean forceBike=false;
	boolean invokeBicycle=false;
	boolean invokeMap=false;
	static AudioClip surfSong, bikeSong;

	//NPC Trainer Vars
	int numTrainers=2;
	int trainerToFight;
	Trainer trainer[]=new Trainer[numTrainers];
	boolean trainerEnc=false;
	AudioClip maleEncounter,femaleEncounter,evilEncounter,rivalEncounter,leaderEncounter;

	//Item Vars
	Item mapItems[];
	int numItems=0;
	static boolean foundItem[];
	static Item rodType;

	//Transitioning Vars
	boolean encounter = false;
	static boolean transition=false;
	boolean alreadyBattled=false;
	boolean drawPoison=false;
	boolean flyMenu=false;
	Area flyAreas[];
	int flyMenuInt=0;
	static boolean fishing=false;
	static int repelSteps=0;
	boolean showCredits=false;

	//Point for leaving and entering buildings
	static Point returnPoint = new Point(5,38);
	static Area returnArea = Area.Stringville;
	static Point returnPoint2 = new Point(5,38);
	static Area returnArea2 = Area.Stringville;

	//Game Vars
	static String name="John";
	static String rivalName="Julian";
	static int timesBeatRival=0;
	static boolean justBeatRival=false;
	static int badges=0;
	static Image badgeImg[]=new Image[8];
	static int trainerIdNumber=(int)(Math.random()*90000)+10000;
	static String idString;
	static int playTimeSecs=1;
	//minutes is automatically incremented
	static int playTimeMins=-1;
	static int playTimeHours=0;
	static long launchTime=0;
	static boolean timeBoolean=false;
	static String timeString;
	static long encryptionKey;
		//Pokemon Vars
		static Pokemon partyPokemon[]=new Pokemon[6];
		static Pokemon pcPokemon[]=new Pokemon[120];
		static Pokemon enemy[]=new Pokemon[6];

	//Pause Menu Items and Such
	boolean paused=false;
	static String pauseMenu[]={"Pokedex","Pokemon","  Item",name,"  Save"};
	int pauseMenuInt=0;
	int pokemonMenuInt=0;
	Image playerImages[]=new Image[6];
	int toggleInt=0;
	int toggleIndexToSwitch=0;
	boolean togglingPokemon=false;
	static boolean recentlySaved=false;

	//Objective Vars
	static String currentObjective="Go talk to Babb!";
	static boolean objectiveComplete[]=new boolean[12];
	static boolean gotItem[]=new boolean[16];

	//Pokedex Vars
	Image pokedexImg;
	int pokedexInt=1;

	//Computer Vars
	int compBox;
	int compInt;
	String compMode="";
	boolean onComp=false;

	public static void main(String[] jokes)
	{
		JokemonDriver j=new JokemonDriver();
		j.initTiles();
		j.initCharSprites();
		j.loadAudio();
		Pokedex.makeArrays();
		j.setIdentification();
		j.setupGUI();
		j.beginThread();
	}

	public void beginThread()
	{
		if (thread == null)
		{
      		thread = new Thread(this,"JokemonMainThread");
      		thread.start();
		}
	}

	//Returns game version
	public static String getVersion()
	{
		return VERSION;
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if(titleScreen)
		{
			if(VERSION.equals("Peaches"))
			{
				g.setColor(Color.BLACK);
				g.fillRect(0,0,getWidth(),getHeight());
			}
			else
			{
				g.setColor(Color.CYAN);
				g.fillRect(0,0,getWidth(),getHeight());
			}
			title.paintTitle(g);

			return;
		}
		
		if(showCredits)
		{
			g.setColor(new Color(255,126,0,title.creditInt%256));
			g.fillRect(0,0,getWidth(),getHeight());
			title.paintCredits(g);
			return;
		}

		if(CONNECTED)
		{
			drawTrade(g);
			return;
		}

		g.setColor(Color.BLACK);
		g.fillRect(0,0,getWidth(),getHeight());

		if (!encounter&&!transition&&!trainerEnc)
		{
			drawMap(g);
			if(drawPoison)
			{
				g.setColor(Color.MAGENTA);
				g.fillRect(0,0,getWidth(),getHeight());
			}
		}

		if(transition)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Sanserif",Font.BOLD,36));
			String str=""+area;
			str=str.replace("_"," ");
			g.drawString(str,400-(str.length()*10),300);

			g.drawImage(loading,0,0,this);
			g.drawImage(loading,0,600-148,this);
			g.drawImage(loading,800-148,0,this);
			g.drawImage(loading,800-148,600-148,this);
		}

		if(cartographer)
		{
			g.setColor(new Color(0,0,200,100));
			g.fillRect(64*6,64*4,64,64);
			switch (tileSet)
			{
				case OUTDOORS:
					g.drawImage(tileImgOutdoors[assignTileIntOutdoors],64*6+16,64*4+16,32,32,this);
					break;
				case INDOORS:
					g.drawImage(tileImgIndoors[assignTileIntIndoors],64*6+16,64*4+16,32,32,this);
					break;
			}


			for(int i=0; i<5; i++)
			{
				try
				{
					g.setColor(new Color(50,50,50,50));
					g.fillRect(64*7,64*3,32,32*5);
					if(i!=2)
					{
						switch (tileSet)
						{
							case OUTDOORS:
								g.drawImage(tileImgOutdoors[assignTileIntOutdoors-(2-i)],64*7,64*3+36*i,32,32,this);
								break;
							case INDOORS:
								g.drawImage(tileImgIndoors[assignTileIntIndoors-(2-i)],64*7,64*3+36*i,32,32,this);
								break;
						}
					}
				}
				catch(Exception e){}
			}
		}
		else if(!transition)
			drawCharacter(g);

		if (!transition)
		{
			for(int i = 0; i<numTrainers; i++)
			{
				if (trainer[i] != null)
				trainer[i].drawTrainer(g);
			}

			for(int i = 0; i<numItems; i++)
			{
				if (mapItems[i] != null)
				mapItems[i].drawItem(g);
			}
		}

		if(paused)
			drawPauseMenu(g);
		else if(onComp)
			drawComputer(g);
		else if(flyMenu)
			drawFlyMenu(g);
		else if(playingMinigame)
			drawMinigameMenu(g);

	}

	//Draws Map
	public void drawMap(Graphics g)
	{
		for(int i=0; i<13; i++)
		{
			for(int j=0; j<12; j++)
			{
                int HEIGHT = 64;
                int WIDTH = 64;
                try
				{
					switch (tileSet)
						{
							case OUTDOORS:
								g.drawImage(tileImgOutdoors[currentArea[location.x-(6-i)][location.y-(4-j)]],64*i,64*j, WIDTH, HEIGHT,this);
								break;
							case INDOORS:
								g.drawImage(tileImgIndoors[currentArea[location.x-(6-i)][location.y-(4-j)]],64*i,64*j, WIDTH, HEIGHT,this);
								break;
						}
				}
				catch(Exception e)
				{
					g.drawImage(tileImgOutdoors[29],64*i,64*j, WIDTH, HEIGHT,this);
				}
			}
		}
	}

	public void drawMinigameMenu(Graphics g)
	{
		g.setColor(new Color(0,0,0,200));
		g.fillRect(0,0,getWidth(),getHeight());

		g.setFont(new Font("Sanserif",Font.BOLD,12));
		g.setColor(Color.WHITE);

		g.drawImage(icon,80,85,16,16,this);

		g.drawString("Pick a minigame (Data will be saved when game starts!!!)",100,70);
		
		String str="";
		
		switch(minigameInt)
		{
			case 0:
				str=Escape.getDescription();
				g.drawString("Game: "+Escape.getGameName(),100,100);
				g.drawString("By: "+Escape.getAuthor(),100,130);
				g.drawString(str.substring(0,115),100,160);
				g.drawString(str.substring(115,214),100,180);
				g.drawString(str.substring(214,350-20),100,200);
				g.drawString(str.substring(350-20),100,220);
				break;
			case 1:
				g.drawString("Game: "+"Frogger",100,100);
				g.drawString("By: "+"Sam Rohl",100,130);
				g.drawString("Cross the road and dodge the NERDY_BIKERS!",100,160);
				break;
			case 2:
				g.drawString("Game: "+"Tron",100,100);
				g.drawString("By: "+"Patches",100,130);
				g.drawString("Get on your lightcycle and have some fun! 2-Players",100,160);
				break;
		}

		g.drawString("Press Spacebar to play!",100,300);
		g.drawString("Press Arrow Keys to switch to another game!",100,320);
		g.drawString("Press Backspace to quit!",100,340);
	}

	//Draws all components of the computer
	public void drawComputer(Graphics g)
	{
		g.setColor(Color.GRAY);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(Color.BLACK);
		g.fillRect(10,10,getWidth()-20,getHeight()-20);

		g.setFont(new Font("Sanserif",Font.BOLD,18));
		g.setColor(Color.WHITE);

		g.drawString("Party Pokemon",50,70);
		try
		{
			for(int i=0; i<partyPokemon.length; i++)
			{
				if(partyPokemon[i]!=null)
				g.drawString(partyPokemon[i].nickname+" Lvl. "+partyPokemon[i].level,50,20*i+100);
				else
				g.drawString("--------",50,20*i+100);
			}
		}
		catch(Exception e){}

		g.drawString("Box "+(compBox+1),250,70);
		try
		{
			for(int i=0; i<12; i++)
			{
				if(pcPokemon[compBox*12+i]!=null)
				g.drawString(pcPokemon[compBox*12+i].nickname+" Lvl. "+pcPokemon[compBox*12+i].level,250,20*i+100);
				else
				g.drawString("--------",250,20*i+100);
			}
		}
		catch(Exception e){}

		if(!compMode.equals(""))
		{
			g.drawString("Mode: "+compMode,600,450);
		}

		if(compMode.equals("Deposit"))
		{
			g.drawImage(icon,30,compInt*20+85,16,16,this);

			if(partyPokemon[compInt]!=null)
			{
				g.setColor(Color.WHITE);
				g.drawString("Species: "+partyPokemon[compInt].species,500,100);
				g.drawString("Nickname: "+partyPokemon[compInt].nickname,500,120);
				g.drawString("Original Trainer: "+partyPokemon[compInt].originalTrainer,500,140);
				g.drawString("Status: "+partyPokemon[compInt].status,500,160);
				g.drawString("HP: "+partyPokemon[compInt].health+"/"+partyPokemon[compInt].healthMax,500,200);
				g.drawString("ATK: "+partyPokemon[compInt].atk,500,220);
				g.drawString("DEF: "+partyPokemon[compInt].def,500,240);
				g.drawString("SPCL: "+partyPokemon[compInt].spcl,500,260);
				g.drawString("SPEED: "+partyPokemon[compInt].speed,500,280);
				String str=""+partyPokemon[compInt].move[0];
				str=str.replace("_"," ");
				g.drawString("Move 1: "+str+" "+partyPokemon[compInt].TRUE_PP[0]+"/"+
					partyPokemon[compInt].TRUE_PPMAX[0],500,320);
				if(partyPokemon[compInt].move[1]!=Pokemon.Move.NONE)
				{
					str=""+partyPokemon[compInt].move[1];
					str=str.replace("_"," ");
					g.drawString("Move 2: "+str+" "+partyPokemon[compInt].TRUE_PP[1]+"/"+
						partyPokemon[compInt].TRUE_PPMAX[1],500,340);
				}
				if(partyPokemon[compInt].move[2]!=Pokemon.Move.NONE)
				{
					str=""+partyPokemon[compInt].move[2];
					str=str.replace("_"," ");
					g.drawString("Move 3: "+str+" "+partyPokemon[compInt].TRUE_PP[2]+"/"+
						partyPokemon[compInt].TRUE_PPMAX[2],500,360);
				}
				if(partyPokemon[compInt].move[3]!=Pokemon.Move.NONE)
				{
					str=""+partyPokemon[compInt].move[3];
					str=str.replace("_"," ");
					g.drawString("Move 4: "+str+" "+partyPokemon[compInt].TRUE_PP[3]+"/"+
						partyPokemon[compInt].TRUE_PPMAX[3],500,380);
				}
			}
		}
		else if(compMode.equals("Withdraw")||compMode.equals("Release"))
		{
			g.drawImage(icon,230,compInt*20+85,16,16,this);

			if(pcPokemon[compBox*12+compInt]!=null)
			{
				g.setColor(Color.WHITE);
				g.drawString("Species: "+pcPokemon[compBox*12+compInt].species,500,100);
				g.drawString("Nickname: "+pcPokemon[compBox*12+compInt].nickname,500,120);
				g.drawString("Original Trainer: "+pcPokemon[compBox*12+compInt].originalTrainer,500,140);
				g.drawString("Status: "+pcPokemon[compBox*12+compInt].status,500,160);
				g.drawString("HP: "+pcPokemon[compBox*12+compInt].health+"/"+pcPokemon[compBox*12+compInt].healthMax,500,200);
				g.drawString("ATK: "+pcPokemon[compBox*12+compInt].atk,500,220);
				g.drawString("DEF: "+pcPokemon[compBox*12+compInt].def,500,240);
				g.drawString("SPCL: "+pcPokemon[compBox*12+compInt].spcl,500,260);
				g.drawString("SPEED: "+pcPokemon[compBox*12+compInt].speed,500,280);
				String str=""+pcPokemon[compBox*12+compInt].move[0];
				str=str.replace("_"," ");
				g.drawString("Move 1: "+str+" "+pcPokemon[compBox*12+compInt].TRUE_PP[0]+"/"+
					pcPokemon[compBox*12+compInt].TRUE_PPMAX[0],500,320);
				if(pcPokemon[compBox*12+compInt].move[1]!=Pokemon.Move.NONE)
				{
					str=""+pcPokemon[compBox*12+compInt].move[1];
					str=str.replace("_"," ");
					g.drawString("Move 2: "+str+" "+pcPokemon[compBox*12+compInt].TRUE_PP[1]+"/"+
						pcPokemon[compBox*12+compInt].TRUE_PPMAX[1],500,340);
				}
				if(pcPokemon[compBox*12+compInt].move[2]!=Pokemon.Move.NONE)
				{
					str=""+pcPokemon[compBox*12+compInt].move[2];
					str=str.replace("_"," ");
					g.drawString("Move 3: "+str+" "+pcPokemon[compBox*12+compInt].TRUE_PP[2]+"/"+
						pcPokemon[compBox*12+compInt].TRUE_PPMAX[2],500,360);
				}
				if(pcPokemon[compBox*12+compInt].move[3]!=Pokemon.Move.NONE)
				{
					str=""+pcPokemon[compBox*12+compInt].move[3];
					str=str.replace("_"," ");
					g.drawString("Move 4: "+str+" "+pcPokemon[compBox*12+compInt].TRUE_PP[3]+"/"+
						pcPokemon[compBox*12+compInt].TRUE_PPMAX[3],500,380);
				}
			}
		}


		g.drawString("Withdraw: W",600,480);
		g.drawString("Deposit: D",600,500);
		g.drawString("Release: R",600,520);
		g.drawString("Log Off: Backspace",600,540);
	}

	//Draws all components of the trade process
	public void drawTrade(Graphics g)
	{
		g.setColor(new Color(255,126,0,200));
		g.fillRect(0,0,getWidth(),getHeight());

		g.setFont(new Font("Sanserif",Font.BOLD,18));
		g.setColor(Color.WHITE);

		g.drawString("You",50,70);
		try
		{
			for(int i=0; i<partyPokemon.length; i++)
			{
				if(partyPokemon[i]!=null)
				g.drawString(partyPokemon[i].nickname,50,20*i+100);
				else
				g.drawString("--------",50,20*i+100);
			}
		}
		catch(Exception e){}

		g.drawImage(icon,30,offerIndex*20+85,16,16,this);

		if(tradeConfirm)
		{
			g.drawString("Trade!",50,230);
		}

		g.drawString("Friend: "+friendName,550,70);
		try
		{
			for(int i=0; i<friendPokemon.length; i++)
			{
				if(!friendPokemon[i].equalsIgnoreCase("null")&&!friendPokemon[i].equals(""))
				g.drawString(friendPokemon[i],550,20*i+100);
				else
				g.drawString("--------",550,20*i+100);
			}
		}
		catch(Exception e){}

		g.drawImage(icon,530,friendOfferIndex*20+85,16,16,this);

		if(friendTradeConfirm)
		{
			g.drawString("Trade!",550,230);
		}
	}

	//Draws menu for flying
	public void drawFlyMenu(Graphics g)
	{
		g.setColor(new Color(0,0,0,200));
		g.fillRect(0,0,getWidth(),getHeight());

		g.setFont(new Font("Sanserif",Font.BOLD,18));
		g.setColor(Color.WHITE);

		g.drawImage(icon,330,flyMenuInt*20+85,16,16,this);

		g.drawString("Fly where? ('F' to cancel)",350,70);
		try
		{
			for(int i=0; i<flyAreas.length; i++)
			{
				g.drawString(""+flyAreas[i],350,20*i+100);
			}
		}
		catch(Exception e){e.printStackTrace();}
	}

	//Draws all components of the pause menu
	public void drawPauseMenu(Graphics g)
	{
		g.setColor(new Color(0,0,0,200));
		g.fillRect(0,0,getWidth(),getHeight());

		g.setColor(Color.WHITE);
		for(int i=0; i<pauseMenu.length; i++)
		{
			int strHeight=24;
			if(i==pauseMenuInt)
				strHeight=36;
			g.setFont(new Font("Sanserif",Font.BOLD,strHeight));
			g.drawString(pauseMenu[i],(i*800)/5,50);
		}

		switch(pauseMenuInt)
		{
			case 0:
				g.setFont(new Font("Sanserif",Font.BOLD,18));
				g.setColor(Color.WHITE);

				g.drawImage(icon,30,85,16,16,this);

				try
				{
					for(int i=0; i<23; i++)
					{
						if(Pokedex.seen[pokedexInt-1+i])
						g.drawString(""+(pokedexInt+i)+": "+Pokedex.getSpecies(pokedexInt+i),50,20*i+100);
						else
						g.drawString(""+(pokedexInt+i)+": "+"????????",50,20*i+100);
					}
				}
				catch(Exception e){}

				if(Pokedex.caught[pokedexInt-1])
				{
					int width = Pokedex.pokedexImg[pokedexInt-1].getWidth(this)*3;
					int height = Pokedex.pokedexImg[pokedexInt-1].getHeight(this)*3;
					g.drawImage(Pokedex.pokedexImg[pokedexInt-1],425-(width/2),275-(height/2),width,height,this);
					g.drawString(""+Pokedex.getSpecies(pokedexInt),600,100);
					g.drawString("Height: "+Pokedex.height[pokedexInt-1],600,130);
					g.drawString("Weight: "+Pokedex.weight[pokedexInt-1],600,150);
					g.drawString("Location ",620,180);
					String[] str=Pokedex.getAreas(pokedexInt-1);
					for(int i=0; i<str.length; i++)
					{
						g.drawString(str[i],600,i*20+200);
					}
					
					g.setFont(new Font("Sanserif",Font.BOLD,10));
					g.drawString(Pokedex.descriptions[pokedexInt-1],220,400);
				}
				else if(Pokedex.seen[pokedexInt-1])
				{
					int width = Pokedex.pokedexImg[pokedexInt-1].getWidth(this)*3;
					int height = Pokedex.pokedexImg[pokedexInt-1].getHeight(this)*3;
					g.drawImage(Pokedex.pokedexImg[pokedexInt-1],425-(width/2),275-(height/2),width,height,this);
					g.drawString(""+Pokedex.getSpecies(pokedexInt),600,100);
					g.drawString("Height: ???",600,130);
					g.drawString("Weight: ???",600,150);
					g.drawString("Location ",620,180);
					String[] str=Pokedex.getAreas(pokedexInt-1);
					for(int i=0; i<str.length; i++)
					{
						g.drawString(str[i],600,i*20+200);
					}
				}
				g.setFont(new Font("Sanserif",Font.BOLD,18));
				g.drawString("Total Seen: "+Pokedex.totalSeen,600,480);
				g.drawString("Total Caught: "+Pokedex.totalCaught,600,500);
				break;
			case 1:
		    	int Yzer = 70;
		    	for (int i = 0; i < 6 && partyPokemon[i] != null; i++)
				{
					g.drawImage(playerImages[i],100,Yzer+20-playerImages[i].getHeight(this)/2,this);
					g.setColor(Color.WHITE);
					g.setFont(new Font("Sanserif",Font.BOLD,18));
					g.drawString(partyPokemon[i].nickname+" ("+partyPokemon[i].getTypeString()+")",175,i*75+75);
					g.drawString("Lvl. "+partyPokemon[i].level,175,i*75+95);
					Yzer+=75;
				}
				g.drawImage(icon,35,pokemonMenuInt*75+70,32,32,this);

				if(togglingPokemon)
					g.drawImage(icon,45,toggleIndexToSwitch*75+75,16,16,this);

				g.setColor(Color.WHITE);
				g.drawString("Species: "+partyPokemon[pokemonMenuInt].species,500,100);
				g.drawString("Nickname: "+partyPokemon[pokemonMenuInt].nickname,500,120);
				g.drawString("Original Trainer: "+partyPokemon[pokemonMenuInt].originalTrainer,500,140);
				g.drawString("Status: "+partyPokemon[pokemonMenuInt].status,500,160);
				g.drawString("HP: "+partyPokemon[pokemonMenuInt].health+"/"+partyPokemon[pokemonMenuInt].healthMax,500,200);
				g.drawString("ATK: "+partyPokemon[pokemonMenuInt].atk,500,220);
				g.drawString("DEF: "+partyPokemon[pokemonMenuInt].def,500,240);
				g.drawString("SPCL: "+partyPokemon[pokemonMenuInt].spcl,500,260);
				g.drawString("SPEED: "+partyPokemon[pokemonMenuInt].speed,500,280);

				String str=""+partyPokemon[pokemonMenuInt].move[0];
				str=str.replace("_"," ");
				g.drawString("Move 1: "+str+" "+partyPokemon[pokemonMenuInt].TRUE_PP[0]+"/"+
					partyPokemon[pokemonMenuInt].TRUE_PPMAX[0],500,320);

				if(partyPokemon[pokemonMenuInt].move[1]!=Pokemon.Move.NONE)
				{
					str=""+partyPokemon[pokemonMenuInt].move[1];
					str=str.replace("_"," ");
					g.drawString("Move 2: "+str+" "+partyPokemon[pokemonMenuInt].TRUE_PP[1]+"/"+
						partyPokemon[pokemonMenuInt].TRUE_PPMAX[1],500,340);
				}

				if(partyPokemon[pokemonMenuInt].move[2]!=Pokemon.Move.NONE)
				{
					str=""+partyPokemon[pokemonMenuInt].move[2];
					str=str.replace("_"," ");
					g.drawString("Move 3: "+str+" "+partyPokemon[pokemonMenuInt].TRUE_PP[2]+"/"+
						partyPokemon[pokemonMenuInt].TRUE_PPMAX[2],500,360);
				}

				if(partyPokemon[pokemonMenuInt].move[3]!=Pokemon.Move.NONE)
				{
					str=""+partyPokemon[pokemonMenuInt].move[3];
					str=str.replace("_"," ");
					g.drawString("Move 4: "+str+" "+partyPokemon[pokemonMenuInt].TRUE_PP[3]+"/"+
						partyPokemon[pokemonMenuInt].TRUE_PPMAX[3],500,380);
				}
				g.drawString("Exp: "+partyPokemon[pokemonMenuInt].exp,500,420);
				int lev=partyPokemon[pokemonMenuInt].level;
				g.drawString("To Next: "+(lev*lev*lev-partyPokemon[pokemonMenuInt].exp),500,440);
				break;
			case 2:
				g.setFont(new Font("Sanserif",Font.BOLD,36));
				g.setColor(Color.WHITE);
				g.drawImage(icon,220,220,32,32,this);
				g.drawString("Activate Item Menu",275,250);

				//Hotkeys
				g.setFont(new Font("Sanserif",Font.BOLD,18));
				g.setColor(Color.WHITE);
				g.drawString("Hotkeys (Press on map to activate when able)",280,300);
				g.drawString("'B'- Use Bicycle",300,320);
				g.drawString("'F'- Use Fly",300,340);
				g.drawString("Spacebar- Use Strength, Fish",300,360);
				g.drawString("Arrow Keys- Use Surf (near water)",300,380);
				g.drawString("'M'- Use Town Map",300,400);
				break;
			case 3:
				g.setColor(Color.WHITE);
				g.setFont(new Font("Sanserif",Font.BOLD,24));
				g.drawString("Trainer ID: "+idString,100,150);
				g.drawString("Money: "+Inventory.money,100,180);
				g.drawString("Current Objective:",100,360);
				g.drawString(currentObjective,100,400);
				drawBadges(g);
				break;
			case 4:
				g.setFont(new Font("Sanserif",Font.BOLD,24));
				g.setColor(Color.WHITE);
				g.drawString("Would you like to save your progress?",150,100);
				g.drawImage(icon,220,150,32,32,this);
				g.drawString("Yes!",275,180);
				g.drawString(name,450,250);
				g.drawString("Time: "+timeString,450,275);
				g.drawString("Money: "+Inventory.money,450,300);
				if(recentlySaved)
					g.drawString("Saved!",275,350);
				break;
		}
	}

	//Draws Badges
	public void drawBadges(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.setFont(new Font("Sanserif",Font.BOLD,36));
		g.drawString("Badges",485,140);

		//Box
		//g.setColor(Color.BLUE);
		//g.fillRect(400,150,320,200);

		if(badges>0)
		{
			//Abstract Badge
			g.drawImage(badgeImg[0],420,170,64,64,this);
		}

		if(badges>1)
		{
			//Native Badge
			g.drawImage(badgeImg[1],490,170,64,64,this);
		}

		if(badges>2)
		{
			//Static Badge
			g.drawImage(badgeImg[2],560,170,64,64,this);
		}

		if(badges>3)
		{
			//Super Badge
			g.drawImage(badgeImg[3],630,170,64,64,this);
		}

		if(badges>4)
		{
			//Null Badge
			g.drawImage(badgeImg[4],420,250,64,64,this);
		}

		if(badges>5)
		{
			//Transient Badge
			g.drawImage(badgeImg[5],490,250,64,64,this);
		}

		if(badges>6)
		{
			//Interface Badge
			g.drawImage(badgeImg[6],560,250,64,64,this);
		}

		if(badges>7)
		{
			//Final Badge
			g.drawImage(badgeImg[7],630,250,64,64,this);
		}
	}

	//Draws Character
	public void drawCharacter(Graphics g)
	{
		if(!moving&&!surfing&&!bicycling)
			g.drawImage(charStand[direction/90],64*6,64*4,64,64,this);
		else if(bicycling&&!moving)
		{
			g.drawImage(charBike0[direction/90],64*6,64*4,64,64,this);
		}
		else if(bicycling&&moving)
		{
			if(movingFrame==0)
				g.drawImage(charBike1[direction/90],64*6,64*4,64,64,this);
			else
				g.drawImage(charBike2[direction/90],64*6,64*4,64,64,this);
		}
		else if(!surfing&&moving)
		{
			if(movingFrame==0)
				g.drawImage(charWalk1[direction/90],64*6,64*4,64,64,this);
			else
				g.drawImage(charWalk2[direction/90],64*6,64*4,64,64,this);
		}
		else if(surfing)
		{
			g.drawImage(charSurf[direction/90],64*6,64*4,64,64,this);
		}
	}

	//Initiates Tiles
	public void initTiles()
	{
		try
		{
			for(int i=0; i<tileImgOutdoors.length; i++)
			{
				URL url=JokemonDriver.class.getResource("MapSprites/Outdoors/"+i+".png");
				tileImgOutdoors[i]=Toolkit.getDefaultToolkit().getImage(url);
			}
			for(int i=0; i<tileImgIndoors.length; i++)
			{
				URL url=JokemonDriver.class.getResource("MapSprites/Indoors/"+i+".png");
				tileImgIndoors[i]=Toolkit.getDefaultToolkit().getImage(url);
			}
		}
		catch(Exception e){e.printStackTrace();}
		
		URL u=JokemonDriver.class.getResource("Sprites/Badges/bug.png");
		badgeImg[0]=Toolkit.getDefaultToolkit().getImage(u);
		
		u=JokemonDriver.class.getResource("Sprites/Badges/grass.png");
		badgeImg[1]=Toolkit.getDefaultToolkit().getImage(u);
		
		u=JokemonDriver.class.getResource("Sprites/Badges/electric.png");
		badgeImg[2]=Toolkit.getDefaultToolkit().getImage(u);
		
		u=JokemonDriver.class.getResource("Sprites/Badges/fighting.png");
		badgeImg[3]=Toolkit.getDefaultToolkit().getImage(u);
		
		u=JokemonDriver.class.getResource("Sprites/Badges/normal.png");
		badgeImg[4]=Toolkit.getDefaultToolkit().getImage(u);
		
		u=JokemonDriver.class.getResource("Sprites/Badges/ice.png");
		badgeImg[5]=Toolkit.getDefaultToolkit().getImage(u);
		
		u=JokemonDriver.class.getResource("Sprites/Badges/psychic.png");
		badgeImg[6]=Toolkit.getDefaultToolkit().getImage(u);
		
		u=JokemonDriver.class.getResource("Sprites/Badges/ghost.png");
		badgeImg[7]=Toolkit.getDefaultToolkit().getImage(u);
	}

	//Initiates Character Sprites
	public void initCharSprites()
	{
		for(int i=0; i<4; i++)
		{
			URL url=JokemonDriver.class.getResource("Sprites/Character/s"+i+".PNG");
			charStand[i]=Toolkit.getDefaultToolkit().getImage(url);

			url=JokemonDriver.class.getResource("Sprites/Character/w"+i+".PNG");
			charWalk1[i]=Toolkit.getDefaultToolkit().getImage(url);

			url=JokemonDriver.class.getResource("Sprites/Character/w1"+i+".PNG");
			charWalk2[i]=Toolkit.getDefaultToolkit().getImage(url);

			url=JokemonDriver.class.getResource("Sprites/Character/cb0"+i+".PNG");
			charBike0[i]=Toolkit.getDefaultToolkit().getImage(url);

			url=JokemonDriver.class.getResource("Sprites/Character/cb1"+i+".PNG");
			charBike1[i]=Toolkit.getDefaultToolkit().getImage(url);

			url=JokemonDriver.class.getResource("Sprites/Character/cb2"+i+".PNG");
			charBike2[i]=Toolkit.getDefaultToolkit().getImage(url);

			url=JokemonDriver.class.getResource("Sprites/Character/surf"+i+".png");
			charSurf[i]=Toolkit.getDefaultToolkit().getImage(url);
		}
	}

	//Launches Debug mode
	//For Developers only!
	public void debug()
	{
		new ItemTest();
		partyPokemon[0]=new Pokemon(Pokemon.Species.MEWTWO,Pokemon.Move.PSYCHIC,Pokemon.Move.ICE_BEAM,Pokemon.Move.THUNDERBOLT,Pokemon.Move.RECOVER,100);
		partyPokemon[1]=new Pokemon(Pokemon.Species.MEW,Pokemon.Move.SURF,Pokemon.Move.STRENGTH,Pokemon.Move.FLY,Pokemon.Move.CUT,100);
		partyPokemon[0].setMaxStats();
		partyPokemon[1].setMaxStats();
	}

	//Checks if the player can encounter a wild Pokemon
	public boolean canEncounter(int tile)
	{
		if (tileSet == OUTDOORS)
		{
			switch(tile)
			{
				case 19:
					if(!surfing)
						return false;
				case 90:
				case 187:
					return true;
				default:
					return false;
			}
		}
		else if (tileSet == INDOORS)
		{
			switch(tile)
			{
				case 244:
					if(!surfing)
						return false;
				case 231:
					return true;
				default:
					return false;
			}
		}
		return false;
	}

	//Assigns Pokemon when surfing
	public void defaultSurfEnc(int randy)
	{
		if(randy<=3)
			enemy[0]=new Pokemon(Pokemon.Species.MAGIKARP,10);
		else if(randy<=6)
			enemy[0]=new Pokemon(Pokemon.Species.TENTACOOL,5);
		else if(randy==7)
			enemy[0]=new Pokemon(Pokemon.Species.POLIWAG,10);
		else
			enemy[0]=new Pokemon(Pokemon.Species.SEEL,7);
	}

	//Checks for a wild encounter when necessary
	public void wildEncounter()
	{
		BattleWindow bwindow;

		Pokemon fish=null;

		if(fishing)
		{
			fish=enemy[0];
		}

		int noEnc=(int)(Math.random()*100);
		if(!fishing&&noEnc>10)
			return;

		encounter=true;
		repaint();

		try
		{
			Thread.sleep(2000);
		}
		catch(Exception e){e.printStackTrace();}

		int randy=(int)(Math.random()*10);

		switch(area)
		{
			//Rarity, Level
			case Args_Harbor:
			case Binary_City:
			case Enumville:
			case Mount_Java:
			case Nested_Village:
			case Peach_City:
			case Cream_City:
			case Polymorph_Town:
			case Recursive_Hot_Springs:
			case Streamreader_Hotel:
			case Stringville:
			case Intville:
			if(surfing)
				defaultSurfEnc(randy);
			else
				enemy[0]=Mechanics.randomEncounter(Pokemon.Species.CATERPIE,20,5,Pokemon.Species.WEEDLE,20,5,Pokemon.Species.PIDGEY,20,5,
				Pokemon.Species.RATTATA,30,5,Pokemon.Species.SPEAROW,10,5);
				break;
			case Route_0:
				enemy[0]=Mechanics.randomEncounter(Pokemon.Species.CATERPIE,20,5,Pokemon.Species.WEEDLE,20,5,Pokemon.Species.PIDGEY,20,5,
				Pokemon.Species.RATTATA,30,5,Pokemon.Species.SPEAROW,10,5);
				break;
			case Route_1:
				if(!surfing)
					enemy[0]=Mechanics.randomEncounter(Pokemon.Species.CATERPIE,20,7,Pokemon.Species.WEEDLE,20,8,Pokemon.Species.PIDGEY,20,10,
					Pokemon.Species.RATTATA,30,11,Pokemon.Species.SPEAROW,10,9);
				else
					enemy[0]=new Pokemon(Pokemon.Species.MAGIKARP);
				break;
			case Route_2:
				if(!surfing)
				{
					if(randy<=4)
					enemy[0]=Mechanics.randomEncounter(Pokemon.Species.PIDGEY,20,15,Pokemon.Species.METAPOD,20,9,Pokemon.Species.KAKUNA,20,10,
					Pokemon.Species.RATTATA,30,16,Pokemon.Species.CATERPIE,10,7);
					else
					{
						if(VERSION.equals("Peaches"))
							enemy[0]=Mechanics.randomEncounter(Pokemon.Species.NIDORAN_M,30,12,Pokemon.Species.NIDORAN_F,30,12,Pokemon.Species.DROWZEE,20,10,
							Pokemon.Species.PIKACHU,5,16,Pokemon.Species.EKANS,15,7);
						else
							enemy[0]=Mechanics.randomEncounter(Pokemon.Species.NIDORAN_M,30,12,Pokemon.Species.NIDORAN_F,30,12,Pokemon.Species.DROWZEE,20,10,
							Pokemon.Species.PIKACHU,5,16,Pokemon.Species.SANDSHREW,15,13);

					}
				}
				else
				{
					if (randy!=0)
					enemy[0]=new Pokemon(Pokemon.Species.MAGIKARP,10);
					else
					enemy[0]=new Pokemon(Pokemon.Species.LAPRAS,16);
				}
				break;
			case Route_3:
				if(!surfing)
				{
					if(randy<=2)
					enemy[0]=Mechanics.randomEncounter(Pokemon.Species.PIDGEOTTO,30,18,Pokemon.Species.PARAS,30,16,Pokemon.Species.TAUROS,20,18,
					Pokemon.Species.AERODACTYL,5,20,Pokemon.Species.TANGELA,15,23);
					else
					{
						if(VERSION.equals("Peaches"))
							enemy[0]=Mechanics.randomEncounter(Pokemon.Species.ODDISH,30,15,Pokemon.Species.PARAS,30,16,Pokemon.Species.PONYTA,20,17,
							Pokemon.Species.OMANYTE,5,23,Pokemon.Species.KABUTO,15,23);
						else
							enemy[0]=Mechanics.randomEncounter(Pokemon.Species.RATICATE,30,21,Pokemon.Species.PARAS,30,16,Pokemon.Species.PONYTA,20,17,
							Pokemon.Species.OMANYTE,5,23,Pokemon.Species.KABUTO,15,23);

					}
				}
				else
				{
					if (randy!=0)
					enemy[0]=new Pokemon(Pokemon.Species.MAGIKARP,15);
					else
					enemy[0]=new Pokemon(Pokemon.Species.POLIWAG,16);
				}
				break;
			case Route_4:
				if (currentArea[location.x][location.y] == 187)
				{
					System.out.println(randy);
					if (randy == 0)
					{
						enemy[0]=new Pokemon(Pokemon.Species.JIGGLYPUFF, Pokemon.Move.BUBBLEBEAM, Pokemon.Move.HYPER_BEAM, Pokemon.Move.SOLARBEAM, Pokemon.Move.FLAMETHROWER, 1, 255, 255, 255, 255, 255,
						"JIGGLYBEAMZ", Pokemon.Status.OK, -1, "JUSTINIANFTW");
					}
					else if (randy == 1)
					{
						enemy[0]=new Pokemon(Pokemon.Species.CATERPIE, Pokemon.Move.SING, Pokemon.Move.SELFDESTRUCT, Pokemon.Move.NONE, Pokemon.Move.NONE, 1, 255, 255, 255, 255, 255,
						"TROLLERPIE", Pokemon.Status.OK, -1, "JUSTINIANFTW");
					}
					else if (randy == 2)
					{
						enemy[0]=new Pokemon(Pokemon.Species.PORYGON, Pokemon.Move.TAIL_WHIP, Pokemon.Move.LEER, Pokemon.Move.SCREECH, Pokemon.Move.CONFUSION, 1, 255, 255, 255, 255, 255,
						"FAILRYGON", Pokemon.Status.OK, -1, "JUSTINIANFTW");
					}
					else if (randy == 3)
					{
						enemy[0]=new Pokemon(Pokemon.Species.SNORLAX, Pokemon.Move.KARATE_CHOP, Pokemon.Move.LOW_KICK, Pokemon.Move.MEGA_PUNCH, Pokemon.Move.HI_JUMP_KICK, 1, 255, 255, 255, 255, 255,
						"EPICLAX", Pokemon.Status.OK, -1, "JUSTINIANFTW");
					}
					else if (randy == 4)
					{
						enemy[0]=new Pokemon(Pokemon.Species.BUTTERFREE, Pokemon.Move.SPORE, Pokemon.Move.POISONPOWDER, Pokemon.Move.SING, Pokemon.Move.THUNDER_WAVE, 1, 255, 255, 255, 255, 255,
						"MARGARINEFREE", Pokemon.Status.OK, -1, "JUSTINIANFTW");
					}
					else if (randy == 5)
					{
						enemy[0]=new Pokemon(Pokemon.Species.DITTO, Pokemon.Move.FIRE_BLAST, Pokemon.Move.HYDRO_PUMP, Pokemon.Move.SOLARBEAM, Pokemon.Move.TRANSFORM, 1, 255, 255, 255, 255, 255,
						"DITTOR", Pokemon.Status.OK, -1, "JUSTINIANFTW");
					}
					else if (randy == 6)
					{
						enemy[0]=new Pokemon(Pokemon.Species.DRAGONITE, Pokemon.Move.FIRE_BLAST, Pokemon.Move.EARTHQUAKE, Pokemon.Move.RECOVER, Pokemon.Move.DRAGON_RAGE, 1, 255, 255, 255, 255, 255,
						"ULTYNITE", Pokemon.Status.OK, -1, "JUSTINIANFTW");
					}
					else if (randy == 7)
					{
						enemy[0]=new Pokemon(Pokemon.Species.LAPRAS, Pokemon.Move.FLY, Pokemon.Move.DIG, Pokemon.Move.NONE, Pokemon.Move.NONE, 1, 255, 255, 255, 255, 255,
						"WTF", Pokemon.Status.OK, -1, "JUSTINIANFTW");
					}
					else if (randy == 8)
					{
						enemy[0]=new Pokemon(Pokemon.Species.CATERPIE, Pokemon.Move.SING, Pokemon.Move.SELFDESTRUCT, Pokemon.Move.NONE, Pokemon.Move.NONE, 1, 255, 255, 255, 255, 255,
						"TROLLERPIE", Pokemon.Status.OK, -1, "JUSTINIANFTW");
					}
					else if (randy == 9)
					{
						enemy[0]=new Pokemon(Pokemon.Species.PORYGON, Pokemon.Move.TAIL_WHIP, Pokemon.Move.LEER, Pokemon.Move.SCREECH, Pokemon.Move.CONFUSION, 1, 255, 255, 255, 255, 255,
						"FAILRYGON", Pokemon.Status.OK, -1, "JUSTINIANFTW");
					}
				}
				else
				{
					if(!surfing)
					{
						if(randy<=2)
						enemy[0]=Mechanics.randomEncounter(Pokemon.Species.RATICATE,20,22,Pokemon.Species.RATICATE,20,20,Pokemon.Species.PIDGEOTTO,20,18,
						Pokemon.Species.RATTATA,30,16,Pokemon.Species.FEAROW,10,24);
						else
						{
							if(VERSION.equals("Peaches"))
								enemy[0]=Mechanics.randomEncounter(Pokemon.Species.CLEFAIRY,20,17,Pokemon.Species.JIGGLYPUFF,20,19,Pokemon.Species.ZUBAT,20,12,
								Pokemon.Species.RATICATE,20,26,Pokemon.Species.FEAROW,20,24);
							else
								enemy[0]=Mechanics.randomEncounter(Pokemon.Species.CLEFAIRY,30,18,Pokemon.Species.JIGGLYPUFF,30,21,Pokemon.Species.ZUBAT,30,15,
								Pokemon.Species.MAGMAR,5,25,Pokemon.Species.VULPIX,15,18);
						}
					}
					else
						defaultSurfEnc(randy);
				}
				break;
			case Route_5:
				if(!surfing)
				{
					if(randy<=5)
					enemy[0]=Mechanics.randomEncounter(Pokemon.Species.DIGLETT,20,22,Pokemon.Species.MACHOP,20,14,Pokemon.Species.GEODUDE,20,15,
					Pokemon.Species.RATICATE,30,26,Pokemon.Species.ONIX,10,7);
					else
					{
						if(VERSION.equals("Peaches"))
							enemy[0]=Mechanics.randomEncounter(Pokemon.Species.ZUBAT,30,12,Pokemon.Species.GEODUDE,30,19,Pokemon.Species.MACHOP,20,14,
							Pokemon.Species.RHYHORN,15,26,Pokemon.Species.EKANS,15,17);
						else
							enemy[0]=Mechanics.randomEncounter(Pokemon.Species.ZUBAT,30,12,Pokemon.Species.NIDORINA,30,20,Pokemon.Species.MACHOP,20,18,
							Pokemon.Species.RHYHORN,15,26,Pokemon.Species.SANDSHREW,15,19);
					}
				}
				else
					defaultSurfEnc(randy);
				break;
			case Route_6:
			case Route_7:
				if(!surfing)
				{
					if(randy<=7)
					enemy[0]=Mechanics.randomEncounter(Pokemon.Species.SLOWPOKE,20,22,Pokemon.Species.MAGNEMITE,20,18,Pokemon.Species.SHELLDER,20,25,
					Pokemon.Species.NIDORINO,30,26,Pokemon.Species.MR_MIME,10,7);
					else
					{
						if(VERSION.equals("Peaches"))
							enemy[0]=Mechanics.randomEncounter(Pokemon.Species.SLOWPOKE,30,18,Pokemon.Species.MAGNEMITE,30,19,Pokemon.Species.SHELLDER,20,14,
							Pokemon.Species.ABRA,15,12,Pokemon.Species.JYNX,15,28);
						else
							enemy[0]=Mechanics.randomEncounter(Pokemon.Species.MEOWTH,30,19,Pokemon.Species.NIDORINA,30,20,Pokemon.Species.SHELLDER,20,18,
							Pokemon.Species.ABRA,15,12,Pokemon.Species.JYNX,15,28);
					}
				}
				else
					defaultSurfEnc(randy);
				break;
			case Route_8:
				enemy[0]=new Pokemon(Pokemon.Species.HORSEA,15);
				break;
			case Route_9:
				if(!surfing)
				{
					if(randy<=7)
					enemy[0]=Mechanics.randomEncounter(Pokemon.Species.PSYDUCK,20,27,Pokemon.Species.KRABBY,20,21,Pokemon.Species.CUBONE,20,18,
					Pokemon.Species.PIDGEOTTO,30,25,Pokemon.Species.MAGNEMITE,10,7);
					else
					{
						if(VERSION.equals("Peaches"))
							enemy[0]=Mechanics.randomEncounter(Pokemon.Species.SLOWPOKE,30,18,Pokemon.Species.KRABBY,30,26,Pokemon.Species.GROWLITHE,20,19,
							Pokemon.Species.POLIWHIRL,15,32,Pokemon.Species.SLOWPOKE,15,28);
						else
							enemy[0]=Mechanics.randomEncounter(Pokemon.Species.MEOWTH,30,23,Pokemon.Species.CUBONE,30,20,Pokemon.Species.PARASECT,20,16,
							Pokemon.Species.POLIWHIRL,15,32,Pokemon.Species.SLOWPOKE,15,28);
					}
				}
				else
					defaultSurfEnc(randy);
				break;
			case Route_10:
				if(!surfing)
				{
					if(randy<=4)
					enemy[0]=Mechanics.randomEncounter(Pokemon.Species.VOLTORB,20,27,Pokemon.Species.VOLTORB,20,21,Pokemon.Species.KOFFING,20,18,
					Pokemon.Species.GRAVELER,30,25,Pokemon.Species.DODUO,10,27);
					else
					{
						if(VERSION.equals("Peaches"))
							enemy[0]=Mechanics.randomEncounter(Pokemon.Species.MANKEY,30,25,Pokemon.Species.KRABBY,30,26,Pokemon.Species.GROWLITHE,20,24,
							Pokemon.Species.POLIWHIRL,15,12,Pokemon.Species.DITTO,15,28);
						else
							enemy[0]=Mechanics.randomEncounter(Pokemon.Species.BELLSPROUT,30,21,Pokemon.Species.CUBONE,30,20,Pokemon.Species.PARASECT,20,16,
							Pokemon.Species.PINSIR,15,26,Pokemon.Species.DITTO,15,28);
					}
				}
				else
					defaultSurfEnc(randy);
				break;
			case Route_11:
				if(!surfing)
				{
					if(randy<=7)
					enemy[0]=Mechanics.randomEncounter(Pokemon.Species.FARFETCH_D,20,35,Pokemon.Species.GRIMER,20,27,Pokemon.Species.EXEGGCUTE,20,28,
					Pokemon.Species.PORYGON,30,25,Pokemon.Species.EEVEE,10,7);
					else
					{
						if(VERSION.equals("Peaches"))
							enemy[0]=Mechanics.randomEncounter(Pokemon.Species.MACHOKE,30,34,Pokemon.Species.KRABBY,30,26,Pokemon.Species.RATICATE,20,29,
							Pokemon.Species.KANGASKHAN,15,32,Pokemon.Species.ELECTABUZZ,15,30);
						else
							enemy[0]=Mechanics.randomEncounter(Pokemon.Species.MACHOKE,30,33,Pokemon.Species.CUBONE,30,20,Pokemon.Species.PARASECT,20,36,
							Pokemon.Species.KANGASKHAN,15,32,Pokemon.Species.PORYGON,15,31);
					}
				}
				else
					defaultSurfEnc(randy);
				break;
			case Route_12:
				if(!surfing)
					enemy[0]=new Pokemon(Pokemon.Species.RATICATE,40);
				else
					enemy[0]=new Pokemon(Pokemon.Species.TENTACRUEL,35);
				break;
			case Route_13:
				enemy[0]=Mechanics.randomEncounter(Pokemon.Species.VOLTORB,20,37,Pokemon.Species.VOLTORB,20,31,Pokemon.Species.KOFFING,20,38,
					Pokemon.Species.GRAVELER,30,35,Pokemon.Species.DODUO,10,37);
				break;
			case Champions_Walk:
			case Route_14:
				if(!surfing)
				{
					enemy[0]=Mechanics.randomEncounter(Pokemon.Species.VOLTORB,20,37,Pokemon.Species.MAGNEMITE,20,31,Pokemon.Species.VOLTORB,20,38,
					Pokemon.Species.MAGNETON,30,35,Pokemon.Species.PIKACHU,10,37);
				}
				else
					defaultSurfEnc(randy);
					break;
			case Victory_Road:
			case Victory_Road_Cave:
			case Challenge_Cave:
				if(!surfing)
				{
					if(randy<=7)
					enemy[0]=Mechanics.randomEncounter(Pokemon.Species.GASTLY,20,18,Pokemon.Species.HITMONCHAN,20,47,Pokemon.Species.HITMONLEE,20,48,
					Pokemon.Species.CHANSEY,5,45,Pokemon.Species.STARYU,10,50);
					else
					{
						if(VERSION.equals("Peaches"))
							enemy[0]=Mechanics.randomEncounter(Pokemon.Species.LICKITUNG,30,54,Pokemon.Species.KADABRA,30,46,Pokemon.Species.RATICATE,20,59,
							Pokemon.Species.DRATINI,15,25,Pokemon.Species.SCYTHER,15,50);
						else
							enemy[0]=Mechanics.randomEncounter(Pokemon.Species.LICKITUNG,30,53,Pokemon.Species.KADABRA,30,59,Pokemon.Species.GRAVELER,20,56,
							Pokemon.Species.DRATINI,15,25,Pokemon.Species.GOLBAT,15,55);
					}
				}
				else
					defaultSurfEnc(randy);
				break;
			case Hexadecimal_Forest:
				if(!surfing)
				{
					enemy[0]=Mechanics.randomEncounter(Pokemon.Species.METAPOD,20,9,Pokemon.Species.BUTTERFREE,20,14,Pokemon.Species.VENONAT,20,8,
					Pokemon.Species.KAKUNA,5,8,Pokemon.Species.BEEDRILL,10,17);
				}
				else
					defaultSurfEnc(randy);
				break;
			case Public_Cave:
			case Java_Cave:
			case Class_Cave:
			case Articuno_Cave:
			case Moltres_Cave:
			case Primal_Cave:
				if (!surfing)
				enemy[0]=Mechanics.randomEncounter(Pokemon.Species.ZUBAT,20,26,Pokemon.Species.ONIX,20,31,Pokemon.Species.GEODUDE,20,17,
					Pokemon.Species.GRAVELER,30,25,Pokemon.Species.CLEFAIRY,10,24);
				else if (randy != 0)
				defaultSurfEnc(randy);
				else
				enemy[0]=new Pokemon(Pokemon.Species.LAPRAS,25);
				break;
			case Diglett_Cave:
				if (!surfing)
				enemy[0]=Mechanics.randomEncounter(Pokemon.Species.DIGLETT,20,26,Pokemon.Species.DUGTRIO,20,31,Pokemon.Species.DIGLETT,20,17,
					Pokemon.Species.DIGLETT,30,25,Pokemon.Species.DUGTRIO,10,40);
				else
				defaultSurfEnc(randy);
				break;
			case Route_ARRAYINDEXOUTOFBOUNDSEXCEPTION:
				defaultSurfEnc(randy);
				break;
			default:
				enemy[0]=new Pokemon(Pokemon.Species.MISSINGNO,80);
				break;
		}

		if(fish!=null)
			enemy[0]=fish;

		if(enemy[0]==null)
			enemy[0]=new Pokemon(Pokemon.Species.MISSINGNO,80);

		//Forces other 5 slots to null
		for(int i=1; i<6; i++)
			enemy[i]=null;

		bgm.stop();
		surfSong.stop();
		bikeSong.stop();
		jf.setVisible(false);

		bwindow=new BattleWindow(partyPokemon,enemy,"WILD");

		for(int i=0; i<6; i++)
		{
			partyPokemon[i]=Battle.getPokemon(i);
		}

		alreadyBattled=true;
		moving=false;

		bwindow.thread.interrupt();

		bwindow=null;

		encounter=false;
		System.gc();
		jf.setVisible(true);
		jf.toFront();

		if(surfing)
			surfSong.loop();
		else if(bicycling&&!forceBike)
			bikeSong.loop();
		else
			bgm.loop();

		fishing=false;
		handleCapturedPokemon();
	}

	//Fishes for Pokemon
	public void fish(Item rod)
	{
		switch(rod.type)
		{
			case OLD_ROD:
				enemy[0]=Mechanics.randomEncounter(Pokemon.Species.MAGIKARP,20,5,Pokemon.Species.PSYDUCK,20,10,Pokemon.Species.GOLDEEN,20,10,
				Pokemon.Species.TENTACOOL,30,5,Pokemon.Species.KRABBY,10,10);
				break;
			case GOOD_ROD:
				enemy[0]=Mechanics.randomEncounter(Pokemon.Species.MAGIKARP,20,15,Pokemon.Species.SLOWPOKE,20,10,Pokemon.Species.SEAKING,20,24,
				Pokemon.Species.TENTACOOL,30,15,Pokemon.Species.POLIWAG,10,18);
				break;
			case SUPER_ROD:
				enemy[0]=Mechanics.randomEncounter(Pokemon.Species.SLOWPOKE,20,20,Pokemon.Species.HORSEA,20,20,Pokemon.Species.SEEL,20,15,
				Pokemon.Species.MAGIKARP,30,19,Pokemon.Species.STARYU,10,30);
				break;
		}

		fishing=true;
		wildEncounter();
	}

	//Returns the best rod you have
	public Item getRodType()
	{
		Item test=new Item(Item.Type.SUPER_ROD,1);

		if(Inventory.hasItem(test))
			return test;
		else
		{
			test=new Item(Item.Type.GOOD_ROD,1);
			if(Inventory.hasItem(test))
				return test;
			else
			{
				test=new Item(Item.Type.OLD_ROD,1);
				if(Inventory.hasItem(test))
					return test;
				else
					return null;
			}
		}
	}

	//Captured Pokemon Handling
	public void handleCapturedPokemon()
	{
		if(Battle.caughtPokemon!=null)
		{
			do
			{
				if(Battle.caughtPokemon.nickname==null||Battle.caughtPokemon.nickname.equals(""))
					Battle.caughtPokemon.nickname=""+Battle.caughtPokemon.species;

				Battle.caughtPokemon.nickname =(JOptionPane.showInputDialog(null, "Give "+Battle.caughtPokemon.nickname+" a Nickname? (<10 Chars)", Battle.caughtPokemon.nickname));
			}
			while(Battle.caughtPokemon.nickname==null||Battle.caughtPokemon.nickname.equals(""));

			Battle.caughtPokemon.setNickname(Battle.caughtPokemon.nickname);

			for(int i=0; i<partyPokemon.length; i++)
			{
				if(partyPokemon[i]==null)
				{
					partyPokemon[i]=Battle.caughtPokemon;
					return;
				}
			}

			for(int i=0; i<pcPokemon.length; i++)
			{
				if(pcPokemon[i]==null)
				{
					pcPokemon[i]=Battle.caughtPokemon;
					repaint();
					messageBox=new ErrorWindow();
					messageBox.addMessage(Battle.caughtPokemon.nickname+" was sent to Box "+((i/12)+1)+" in the PC!","Sent to PC!");

					messageBox.repaint();
					while(messageBox.isVisible())
					{
						try
						{
							Thread.sleep(10);
						}
						catch(Exception e){e.printStackTrace();}
					}

					return;
				}
			}

			messageBox=new ErrorWindow();
			messageBox.addMessage(Battle.caughtPokemon.nickname+" was released to the wild.","No more room!");

			messageBox.repaint();
			while(messageBox.isVisible())
			{
				try
				{
					Thread.sleep(10);
				}
				catch(Exception e){e.printStackTrace();}
			}

		}
	}

	//Returns Area Height
	public static int getAreaHeight()
	{
		return widthHeight.y-1;
	}

	//Returns Area Width
	public static int getAreaWidth()
	{
		return widthHeight.x-1;
	}

	//Returns next objective based on game progress
	public String getCurrentObjective()
	{
		if(objectiveComplete[11])
			return "Complete the Pokedex!";
		else if(objectiveComplete[10])
			return "Wasn't Babb said to be an awesome battler?";
		else if(objectiveComplete[9])
			return "Take on the Elite Four and Joe! It'll be tough!";
		else if(objectiveComplete[8])
			return "Stop the evil Team Java!";
		else if(objectiveComplete[7])
			return "Defeat Jessica at Binary City's Gym!";
		else if(objectiveComplete[6])
			return "Defeat James at Polymorph Town's Gym!";
		else if(objectiveComplete[5])
			return "Defeat Joy at the Gym in the Recursive Hot Springs!";
		else if(objectiveComplete[4])
			return "Defeat Jordan at Streamreader Hotel's Gym!";
		else if(objectiveComplete[3])
			return "Defeat Jin at Mount Java's Gym!";
		else if(objectiveComplete[2])
			return "Defeat Jace at the Gym in Villa Del Joe!";
		else if(objectiveComplete[1])
			return "Defeat Jimmy at Args Harbor's Gym!";
		else if(objectiveComplete[0])
			return "Defeat Jenny at Stringville's Gym!";
		else
			return "Go talk to Babb in his lab!";
	}

	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		recentlySaved=false;

		if(encounter||trainerEnc)
			return;
			
		if(showCredits)
			return;

		URL url = Pokemon.class.getResource("Sprites/pokemon/Right/" + Pokedex.speciesToString(Pokedex.getSpecies(pokedexInt)) + ".png");
		pokedexImg = Toolkit.getDefaultToolkit().createImage(url);

		if(playingMinigame)
		{
			switch(key)
			{
				//Down
				case 40:
					if(minigameInt<2)
						minigameInt++;
					break;
				//Up
				case 38:
					if(minigameInt>0)
						minigameInt--;
					break;
				//Spacebar
				case 32:
					invokeMinigame=true;
					performingAction=true;
					break;
				//Backspace
				case 8:
					playingMinigame=false;
					break;
			}

			return;
		}

		if(flyMenu)
		{
			switch(key)
			{
				//Down
				case 40:
					if(flyMenuInt<flyAreas.length-1)
						flyMenuInt++;
					break;
				//Up
				case 38:
					if(flyMenuInt>0)
						flyMenuInt--;
					break;
				//Spacebar
				case 32:
					performingAction=true;
					break;
				case 'f':
				case 'F':
					flyMenu=false;
					break;
			}
			return;
		}

		if(onComp)
		{
			switch(key)
			{
				case 'W':
				case 'w':
					compMode="Withdraw";
					compInt=0;
					break;
				case 'D':
				case 'd':
					compMode="Deposit";
					compInt=0;
					break;
				case 'R':
				case 'r':
					compMode="Release";
					compInt=0;
					break;
				case 8:
					onComp=false;
					return;
			}

			if(compMode.equals("Withdraw"))
			{
				switch(key)
				{
					//Down
					case 40:
						if(compInt<11)
							compInt++;
						break;
					//Up
					case 38:
						if(compInt>0)
							compInt--;
						break;
					//Left
					case 37:
						if(compBox>0)
							compBox--;
						break;
					//Right
					case 39:
						if(compBox<9)
							compBox++;
						break;
					//Spacebar
					case 32:
						boolean canWithdraw=true;
						int numPo=0;

						for(int i=0; i<6; i++)
						{
							if(partyPokemon[i]!=null)
								numPo++;
						}

						canWithdraw=numPo<6;

						if(canWithdraw&&pcPokemon[12*compBox+compInt]!=null)
						{
							for(int i=0; i<6; i++)
							{
								if(partyPokemon[i]==null)
								{
									partyPokemon[i]=pcPokemon[12*compBox+compInt];
									pcPokemon[12*compBox+compInt]=null;
								}
							}
						}
						break;
				}
			}
			else if(compMode.equals("Deposit"))
			{
				switch(key)
				{
					//Down
					case 40:
						if(compInt<5)
							compInt++;
						break;
					//Up
					case 38:
						if(compInt>0)
							compInt--;
						break;
					//Spacebar
					case 32:
						boolean canDeposit=true;
						int numPo=0;

						for(int i=0; i<6; i++)
						{
							if(partyPokemon[i]!=null)
								numPo++;
						}

						canDeposit=numPo>1;

						if(canDeposit&&partyPokemon[compInt]!=null)
						{
							for(compBox=0; compBox<10; compBox++)
							{
								for(int i=0; i<12; i++)
								{
									if(pcPokemon[12*compBox+i]==null)
									{
										pcPokemon[12*compBox+i]=partyPokemon[compInt];
										partyPokemon[compInt]=null;
										for(int j=1; j<6; j++)
										{
											if(partyPokemon[j-1]==null&&partyPokemon[j]!=null)
											{
												partyPokemon[j-1]=partyPokemon[j];
												partyPokemon[j]=null;
											}
										}
										return;
									}
								}
							}
						}
						break;
				}
			}
			else if(compMode.equals("Release"))
			{
				switch(key)
				{
					//Down
					case 40:
						if(compInt<11)
							compInt++;
						break;
					//Up
					case 38:
						if(compInt>0)
							compInt--;
						break;
					//Left
					case 37:
						if(compBox>0)
							compBox--;
						break;
					//Right
					case 39:
						if(compBox<9)
							compBox++;
						break;
					//Spacebar
					case 32:
						pcPokemon[12*compBox+compInt]=null;
						break;
				}
			}

			return;
		}

		if(CONNECTED)
		{
			switch(key)
			{
				//Down
					case 40:
						if(offerIndex<5&&partyPokemon[offerIndex+1]!=null)
							offerIndex++;
						break;
					//Up
					case 38:
						if(offerIndex>0)
							offerIndex--;
						break;
				//Spacebar
				case 32:
					tradeConfirm=!tradeConfirm;
					break;
			}
			return;
		}

		switch (key)
		{
			//Up
			case 38:
				if(paused)
				{
					switch(pauseMenuInt)
					{
						case 0:
							if(pokedexInt>1)
								pokedexInt--;
							url = Pokemon.class.getResource("Sprites/pokemon/Right/" + Pokedex.speciesToString(Pokedex.getSpecies(pokedexInt)) + ".png");
		    				pokedexImg = Toolkit.getDefaultToolkit().createImage(url);
							break;
						case 1:
							if(pokemonMenuInt>0)
								pokemonMenuInt--;
							break;
					}
				}
				else
				{
					//Change Direction
					direction=90;
					moving=true;
				}
				break;
			//Down
			case 40:
				if(paused)
				{
					switch(pauseMenuInt)
					{
						case 0:
							if(pokedexInt<151)
								pokedexInt++;
							url = Pokemon.class.getResource("Sprites/pokemon/Right/" + Pokedex.getSpecies(pokedexInt) + ".png");
		    				pokedexImg = Toolkit.getDefaultToolkit().createImage(url);
							break;
						case 1:
							if(pokemonMenuInt<5&&partyPokemon[pokemonMenuInt+1]!=null)
								pokemonMenuInt++;
							break;
					}
				}
				else
				{
					//Change Direction
					direction=270;
					moving=true;
				}
				break;
			//Left
			case 37:
				if(paused)
				{
					if(pauseMenuInt>0)
						pauseMenuInt--;
				}
				else
				{
					//Change Direction
					direction=180;
					moving=true;
				}
				break;
			//Right
			case 39:
				if(paused)
				{
					if(pauseMenuInt<pauseMenu.length-1)
						pauseMenuInt++;
				}
				else
				{
					//Change Direction
					direction=0;
					moving=true;
				}
				break;
			case 'S':
			case 's':
				if(DEBUG)
				{
					surfing=true;
				}
				break;
			case 'T':
			case 't':
					System.out.println("X: " + location.x);
					System.out.println("Y: " + location.y);
					System.out.println("Tile: "+currentArea[location.x][location.y]);
				break;
			case 'H':
			case 'h':
				if(cartographer)
				{
					horizontalFill();
				}
				break;
			case 'b':
			case 'B':
				if (cartographer)
				{
					int x = 0;
					switch(tileSet)
					{
						case INDOORS:
							x=assignTileIntIndoors;
							break;
						case OUTDOORS:
							x=assignTileIntOutdoors;
							break;
					}
					try
					{
						currentArea[location.x][location.y]=x;
						currentArea[location.x][location.y+1]=x;
						currentArea[location.x+1][location.y]=x;
						currentArea[location.x+1][location.y+1]=x;
					}
					catch(IndexOutOfBoundsException ee)
					{
						System.out.println("Can't go outside of boundary");
					}

				}
				else
				{
					if(Inventory.hasItem(new Item(Item.Type.BICYCLE,1))&&!surfing)
					{
						if(!bicycling)
						{
							performingAction=true;
							invokeBicycle=true;
						}
						else
						{
							bicycling=false;
							bikeSong.stop();
							bgm.loop();
						}
					}
				}
				break;
			case 'z':
			case 'Z':
				if(cartographer)
				{
					switch (tileSet)
					{
						case OUTDOORS:
							if(assignTileIntOutdoors>0)
							assignTileIntOutdoors--;
							break;
						case INDOORS:
							if(assignTileIntIndoors>0)
							assignTileIntIndoors--;
							break;
					}
				}
				break;
			case 'x':
			case 'X':
				if(cartographer)
				{
					switch (tileSet)
					{
						case OUTDOORS:
							if(assignTileIntOutdoors<tileImgOutdoors.length-1)
							assignTileIntOutdoors++;
							break;
						case INDOORS:
							if(assignTileIntIndoors<tileImgIndoors.length-1)
							assignTileIntIndoors++;
							break;
					}
				}
				break;
			case 'c':
			case 'C':
				if (cartographer)
				{
					int assignTileInt = 0;
					switch (tileSet)
					{
						case INDOORS:
							if (currentArea[location.x][location.y] < tileImgIndoors.length)
							assignTileIntIndoors=currentArea[location.x][location.y];
							break;
						case OUTDOORS:
							if (currentArea[location.x][location.y] < tileImgOutdoors.length)
							assignTileIntOutdoors=currentArea[location.x][location.y];
							break;
					}
				}
				break;
			case 'f':
			case 'F':
				if((Inventory.hasItem(new Item(Item.Type.HM,1,2)))&&!paused&&!surfing&&Mechanics.hasFly(partyPokemon)&&!flyMenu&&tileSet==OUTDOORS)
				{
					getFlyAreas();
					flyMenuInt=0;
					flyMenu=true;
				}
				else
				{
					flyMenu=false;
				}
				break;
			case 'm':
			case 'M':
				if (!invokeMap)
				{
					invokeMap=true;
					performingAction=true;
				}
				break;
			//Spacebar
			case 32:
				if(titleScreen)
				{
					titleScreen=false;
					return;
				}

				if(paused)
				{
					switch(pauseMenuInt)
					{
						case 0:
							break;
						case 1:
							if(togglingPokemon)
							{
								Pokemon temp=new Pokemon(Pokemon.Species.BULBASAUR);
								temp=partyPokemon[toggleIndexToSwitch];
								partyPokemon[toggleIndexToSwitch]=partyPokemon[pokemonMenuInt];
								partyPokemon[pokemonMenuInt]=temp;
								loadPokemonImages();
							}
							else
							{
								toggleIndexToSwitch=pokemonMenuInt;
							}
							togglingPokemon=!togglingPokemon;
							break;
						case 2:
							Battle.user=partyPokemon;
							InventoryWindow iWin=new InventoryWindow();
							iWin.openInventory(InventoryWindow.State.OVERWORLD);
							for(int i=0; i<6; i++)
							{
								partyPokemon[i]=Battle.getPokemon(i);
							}
							break;
						case 3:
							break;
						case 4:
							save();
							break;
					}
				}
				else
				{
					if (!cartographer)
					performingAction=true;

					else
					{
						switch(tileSet)
						{
							case INDOORS:
								currentArea[location.x][location.y] = assignTileIntIndoors;
								break;
							case OUTDOORS:
								currentArea[location.x][location.y] = assignTileIntOutdoors;
								break;
						}
					}
				}
				break;
			//Enter
      		case 10:
      			if(!onComp&&!transition&&!trainerEnc&&!cartographer&&!moving)
      			{
      				paused=!paused;
      				togglingPokemon=false;
      				currentObjective=getCurrentObjective();
      			}

      			loadPokemonImages();
      		 	url = Pokemon.class.getResource("Sprites/pokemon/Right/" + Pokedex.getSpecies(pokedexInt) + ".png");
		    	pokedexImg = Toolkit.getDefaultToolkit().createImage(url);
      			break;
      		//0
      		case 48:
      			if(DEBUG)
      			cartographer=!cartographer;
      			//saveTileSet();
				break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		moving=false;
		alreadyBattled=false;
		drawPoison=false;
		movingFrame=1;
	}

	public void keyTyped(KeyEvent e){}

	//Creates the Area that the player is currently on
	public void createCurrentArea()
	{
		if(Battle.IMPORTED)
		{
			bgm.stop();
		}

		bikeSong.stop();
		forceBike=false;

		int levelBoost=0;

		boolean omgBoolean = false;
		for (int i = 0; i<trainer.length; i++)
			trainer[i] = null;
		switch(area)
		{
			case Stringville:
				tileSet = OUTDOORS;
				widthHeight.x = 50;
				widthHeight.y = 50;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Stringville.tilemap");
				URL url=JokemonDriver.class.getResource("Music/Locations/1_stringville.mid");
				bgm=JApplet.newAudioClip(url);

				//Trainer Declaration
				numTrainers=8;
				trainer=new Trainer[numTrainers];

				trainer[0]=new Trainer(Trainer.TrainerType.BABE,"Jillian",Pokemon.Species.PIDGEY,Pokemon.Species.RATTATA,5);
				trainer[0].setHostile(false);
				trainer[0].setPostMessage("You must be another contest winner! Welcome to Joe's Island!");
				trainer[0].setLocation(11,12);
				trainer[0].setDirection(0);

				trainer[1]=new Trainer(Trainer.TrainerType.CODER,"Peter",Pokemon.Species.WEEDLE,Pokemon.Species.CATERPIE,5);
				trainer[1].setHostile(false);
				trainer[1].setLocation(13,21);
				trainer[1].setPostMessage("Joe is one rich kid. He bought this island and filled it with Pokemon.");
				trainer[1].setDirection(180);

				trainer[2]=new Trainer(Trainer.TrainerType.NEWB,"Billy",Pokemon.Species.WEEDLE,Pokemon.Species.CATERPIE,5);
				trainer[2].setHostile(false);
				trainer[2].setLocation(21,10);
				trainer[2].setPostMessage("I just came here too! I need to go see Babb, Joe's uncle. He'll help me get started!");
				trainer[2].setDirection(270);
				trainer[2].setViewRange(1);

				trainer[3]=new Trainer(Trainer.TrainerType.PROFESSOR,"Roderick III",true);
				trainer[3].setHostile(false);
				trainer[3].setLocation(20,18);
				trainer[3].setPostMessage("Welcome young one. Enjoy your time on the island. Take this fishing rod and you can catch Pokemon. It's a bit old, but who cares, it works.");

				trainer[4]=new Trainer(Trainer.TrainerType.DEBUGGER,"Percy",Pokemon.Species.WEEDLE,Pokemon.Species.CATERPIE,5);
				trainer[4].setHostile(false);
				trainer[4].setLocation(42,17);
				trainer[4].setPostMessage("This island is so big! I'm going to battle everyone to get a chance to fight Joe!");
				trainer[4].setDirection(270);

				if(badges<2)
				{
					trainer[5]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Jamal",Pokemon.Species.WEEDLE,Pokemon.Species.CATERPIE,5);
					trainer[5].setHostile(false);
					trainer[5].setLocation(10,1);
					trainer[5].setPostMessage("Watch out! We're having an epic battle!");
					trainer[5].setDirection(0);

					trainer[6]=new Trainer(Trainer.TrainerType.HACKER,"Javier",Pokemon.Species.WEEDLE,Pokemon.Species.CATERPIE,5);
					trainer[6].setHostile(false);
					trainer[6].setLocation(11,1);
					trainer[6].setPostMessage("Don't distract me! I'm busy battling!");
					trainer[6].setDirection(180);
				}
				else
				{
					trainer[5]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Jamal",Pokemon.Species.WEEDLE,Pokemon.Species.CATERPIE,5);
					trainer[5].setHostile(false);
					trainer[5].setLocation(8,4);
					trainer[5].setPostMessage("Don't talk to me, I just lost a battle...");
					trainer[5].setDirection(180);

					trainer[6]=new Trainer(Trainer.TrainerType.HACKER,"Javier",Pokemon.Species.WEEDLE,Pokemon.Species.CATERPIE,5);
					trainer[6].setHostile(false);
					trainer[6].setLocation(12,4);
					trainer[6].setPostMessage("See that guy over there? He's sad because I beat him! You can take the path now.");
					trainer[6].setDirection(270);
				}

				if(objectiveComplete[0]&&timesBeatRival==0)
				{
					trainer[7]=new Trainer(Trainer.TrainerType.RIVAL,rivalName,Pokemon.Species.PIKACHU,Mechanics.getHighestLevel(partyPokemon,pcPokemon));
					trainer[7].setHostile(true);
					trainer[7].setLocation(28,38);
					trainer[7].setPreMessage("Well, if it isn't "+name+". Nice to see you made it to the island. How about a battle my dearest rival? That would be exquisite.");
					trainer[7].setPostMessage("Wow. You got pretty lucky. Next time you won't be so lucky "+name+". I'd train some more if I were you.");
					trainer[7].setDirection(270);
				}
				else
				{
					trainer[7]=new Trainer(Trainer.TrainerType.CYPHER,"Jill",Pokemon.Species.WEEDLE,Pokemon.Species.CATERPIE,5);
					trainer[7].setHostile(false);
					trainer[7].setLocation(28,38);
					trainer[7].setPostMessage("This is a Pokemon Gym. If you win at each one on the island, you can go challenge strong trainers!");
					trainer[7].setDirection(270);
				}

				//Item Declaration
				numItems=2;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				mapItems[0]=new Item(Item.Type.POTION,new Point(37,4));
				mapItems[1]=new Item(Item.Type.ANTIDOTE,new Point(4,40));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Route_0:
				tileSet = OUTDOORS;
				widthHeight.x = 60;
				widthHeight.y = 50;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Route0.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes.mid");
				bgm=JApplet.newAudioClip(url);

				if (badges == 0)
				numTrainers=7;
				else
				numTrainers=5;
				trainer=new Trainer[numTrainers];
				numItems=1;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.NEWB,"Buford",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(true);
				trainer[0].setLocation(6,11);
				trainer[0].setPreMessage("Hey Trainer! When you see another trainer, you have to battle! Let's go!");
				trainer[0].setPostMessage("You just had beginner's luck, that's all.");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(2);

				trainer[1]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Todd",Pokemon.Species.CATERPIE,5);
				trainer[1].setHostile(false);
				trainer[1].setLocation(11,13);
				trainer[1].setPostMessage("Be careful walking in grass. Wild Pokemon are roaming around.");
				trainer[1].setDirection(180);

				trainer[2]=new Trainer(Trainer.TrainerType.KEYBOARDER,"Mac",Pokemon.Species.MAGIKARP,Pokemon.Species.MAGIKARP,5);
				trainer[2].setHostile(true);
				trainer[2].setLocation(14,30);
				trainer[2].setPreMessage("Hey kid, let's battle. I just caught some Pokemon.");
				trainer[2].setPostMessage("Darn it. Magikarp are weak.");
				trainer[2].setDirection(270);
				trainer[2].setViewRange(1);

				trainer[3]=new Trainer(Trainer.TrainerType.CYPHER,"Penelope",Pokemon.Species.MAGIKARP,10);
				trainer[3].setHostile(true);
				trainer[3].setLocation(23,39);
				trainer[3].setPreMessage("Girls like to fish too you know!");
				trainer[3].setPostMessage("I think I'll stick to tanning.");
				trainer[3].setDirection(180);
				trainer[3].setViewRange(1);

				trainer[4]=new Trainer(Trainer.TrainerType.ENGINEER,"Juan",Pokemon.Species.CATERPIE,5);
				trainer[4].setHostile(false);
				trainer[4].setLocation(43,11);
				trainer[4].setPostMessage("I caught a few Pokemon! Make sure you have Poke Balls and you can too!");
				trainer[4].setDirection(270);

				if(badges==0)
				{
					trainer[5]=new Trainer(Trainer.TrainerType.NEWB,"Timmy",Pokemon.Species.WEEDLE,Pokemon.Species.CATERPIE,5);
					trainer[5].setHostile(false);
					trainer[5].setLocation(14,23);
					trainer[5].setPostMessage("We're having a staring contest! Then afterwards we are leaving this island for Kanto. It's so much nicer there.");
					trainer[5].setDirection(0);

					trainer[6]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Tim",Pokemon.Species.WEEDLE,Pokemon.Species.CATERPIE,5);
					trainer[6].setHostile(false);
					trainer[6].setLocation(15,23);
					trainer[6].setPostMessage("Don't distract me! I must stare!");
					trainer[6].setDirection(180);
				}

				mapItems[0]=new Item(Item.Type.POTION,new Point(43,19));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Args_Harbor:
				tileSet = OUTDOORS;
				widthHeight.x = 60;
				widthHeight.y = 68;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("ArgsHarbor.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/2_args_harbor.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=5;
				trainer=new Trainer[numTrainers];
				numItems=1;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.BABE,"Nikki",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(6,32);
				//trainer[0].setPreMessage("Hey Trainer! When you see another trainer, you have to battle! Let's go!");
				trainer[0].setPostMessage("I just love Args Harbor. It's so beautiful.");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(2);

				trainer[1]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Noah",Pokemon.Species.CATERPIE,5);
				trainer[1].setHostile(false);
				trainer[1].setLocation(13,47);
				trainer[1].setPostMessage("You can see water for miles from here! This island must be in the middle of nowhere.");
				trainer[1].setDirection(270);

				trainer[2]=new Trainer(Trainer.TrainerType.NEWB,"Timmy",Pokemon.Species.MAGIKARP,Pokemon.Species.MAGIKARP,5);
				trainer[2].setHostile(false);
				trainer[2].setLocation(45,22);
				//trainer[2].setPreMessage("Hey kid, let's battle. I just caught some Pokemon.");
				trainer[2].setPostMessage("This is my special spot. It's really cozy. Don't try to take it from me!");
				trainer[2].setDirection(270);
				trainer[2].setViewRange(1);

				trainer[3]=new Trainer(Trainer.TrainerType.CYPHER,"Olivia",Pokemon.Species.MAGIKARP,10);
				trainer[3].setHostile(false);
				trainer[3].setLocation(16,21);
				//trainer[3].setPreMessage("Girls like to fish too you know!");
				trainer[3].setPostMessage("That lighthouse is so tall! I wonder what's in there?");
				trainer[3].setDirection(0);
				trainer[3].setViewRange(1);

				if(badges<1)
				{
					trainer[4]=new Trainer(Trainer.TrainerType.ENGINEER,"Scotty",Pokemon.Species.CATERPIE,5);
					trainer[4].setHostile(false);
					trainer[4].setLocation(38,24);
					trainer[4].setPostMessage("You should get the Stringville Badge first. Jimmy is too strong for you right now.");
					trainer[4].setDirection(270);
				}
				else
				{
					trainer[4]=new Trainer(Trainer.TrainerType.ENGINEER,"Scotty",Pokemon.Species.CATERPIE,5);
					trainer[4].setHostile(false);
					trainer[4].setLocation(28,25);
					trainer[4].setPostMessage("I wonder if Joe and Babb visit the lighthouse often.");
					trainer[4].setDirection(270);
				}

				mapItems[0]=new Item(Item.Type.SUPER_POTION,new Point(47,34));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Route_1:
				tileSet = OUTDOORS;
				widthHeight.x = 40;
				widthHeight.y = 60;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Route1.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes.mid");
				bgm=JApplet.newAudioClip(url);

				if(badges>7)
				{
					numTrainers=5;
					trainer=new Trainer[numTrainers];
				}
				else
				{
					numTrainers=7;
					trainer=new Trainer[numTrainers];

					trainer[5]=new Trainer(Trainer.TrainerType.JAVA,"ELITE",Pokemon.Species.CATERPIE,5);
					trainer[5].setHostile(false);
					trainer[5].setLocation(4,20);
					trainer[5].setPostMessage("Out of the way pissant!");
					trainer[5].setDirection(0);

					trainer[6]=new Trainer(Trainer.TrainerType.JAVA,"ELITE",Pokemon.Species.CATERPIE,5);
					trainer[6].setHostile(false);
					trainer[6].setLocation(4,21);
					trainer[6].setPostMessage("Scram!");
					trainer[6].setDirection(0);
				}
				numItems=2;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.DEBUGGER,"Ishmael",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(11,50);
				//trainer[0].setPreMessage("Hey Trainer! When you see another trainer, you have to battle! Let's go!");
				trainer[0].setPostMessage("See that ledge? You can jump over it. Only from one way though.");
				trainer[0].setDirection(180);
				//trainer[0].setViewRange(2);

				trainer[1]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Zeke",Pokemon.Species.PIDGEY,Pokemon.Species.RATTATA,15);
				trainer[1].setHostile(true);
				trainer[1].setLocation(11,13);
				trainer[1].setPreMessage("You have badges! You must be good!");
				trainer[1].setPostMessage("Too good for my taste.");
				trainer[1].setDirection(0);
				trainer[1].setViewRange(4);

				trainer[2]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Mike",Pokemon.Species.METAPOD,Pokemon.Species.BULBASAUR,15);
				trainer[2].setHostile(true);
				trainer[2].setLocation(22,36);
				trainer[2].setPreMessage("I like to ride my bicycle.");
				trainer[2].setPostMessage("Looks like I need a tune-up.");
				trainer[2].setDirection(180);
				trainer[2].setViewRange(4);

				trainer[3]=new Trainer(Trainer.TrainerType.BABE,"Isabella",Pokemon.Species.PIKACHU,18);
				trainer[3].setHostile(true);
				trainer[3].setLocation(11,33);
				trainer[3].setPreMessage("Check out my cuddly Pokemon!");
				trainer[3].setPostMessage("Ugh. You're no fun.");
				trainer[3].setDirection(180);
				trainer[3].setViewRange(3);

				trainer[4]=new Trainer(Trainer.TrainerType.CODER,"Jimbo",Pokemon.Species.SLOWPOKE,14);
				trainer[4].setHostile(true);
				trainer[4].setLocation(30,5);
				trainer[4].setPreMessage("Don't act like you didn't see me.");
				trainer[4].setPostMessage("Maybe I shouldn't have looked at you.");
				trainer[4].setDirection(270);
				trainer[4].setViewRange(4);

				mapItems[0]=new Item(Item.Type.POKE_BALL,new Point(25,50));
				mapItems[1]=new Item(Item.Type.BURN_HEAL,new Point(4,46));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Route_2:
				tileSet = OUTDOORS;
				widthHeight.x = 48;
				widthHeight.y = 60;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Route2.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes2.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=5;
				trainer=new Trainer[numTrainers];
				numItems=4;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.DEBUGGER,"Tobey",Pokemon.Species.PIDGEY,16);
				trainer[0].setHostile(true);
				trainer[0].setLocation(6,31);
				trainer[0].setPreMessage("My name really is Tobey.");
				trainer[0].setPostMessage("I'm calling my mom!");
				trainer[0].setDirection(180);
				trainer[0].setViewRange(2);

				trainer[1]=new Trainer(Trainer.TrainerType.NEWB,"Brian Healy",Pokemon.Species.FARFETCH_D,Pokemon.Species.FARFETCH_D,Pokemon.Species.FARFETCH_D,Pokemon.Species.FARFETCH_D,10);
				trainer[1].setHostile(true);
				trainer[1].setLocation(7,38);
				trainer[1].setPreMessage("Is there such a thing as beginner's luck?");
				trainer[1].setPostMessage("I guess not...");
				trainer[1].setDirection(90);
				trainer[1].setViewRange(2);

				trainer[2]=new Trainer(Trainer.TrainerType.JAVA,"GRUNT",Pokemon.Species.RATTATA,Pokemon.Species.BULBASAUR,15);
				trainer[2].setHostile(true);
				trainer[2].setLocation(23,38);
				trainer[2].setPreMessage("Have you heard of Team Java?");
				trainer[2].setPostMessage("You'd better fear us.");
				trainer[2].setDirection(90);
				trainer[2].setViewRange(2);

				trainer[3]=new Trainer(Trainer.TrainerType.CYPHER,"Bella",Pokemon.Species.RATTATA,Pokemon.Species.BELLSPROUT,18);
				trainer[3].setHostile(true);
				trainer[3].setLocation(28,31);
				trainer[3].setPreMessage("HEY KID! LET'S BATTLE!!!");
				trainer[3].setPostMessage("...You made me lose my voice.");
				trainer[3].setDirection(180);
				trainer[3].setViewRange(4);

				trainer[4]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Lancelot",Pokemon.Species.DODUO,Pokemon.Species.PIDGEY,14);
				trainer[4].setHostile(true);
				trainer[4].setLocation(44,43);
				trainer[4].setPreMessage("I almost got my bike stuck! Good thing you saw me.");
				trainer[4].setPostMessage("I think you're a cheater.");
				trainer[4].setDirection(180);
				trainer[4].setViewRange(5);

				mapItems[0]=new Item(Item.Type.GREAT_BALL,new Point(7,52));
				mapItems[1]=new Item(Item.Type.PARALYZE_HEAL,new Point(38,52));
				mapItems[2]=new Item(Item.Type.AWAKENING,new Point(43,37));
				mapItems[3]=new Item(Item.Type.ANTIDOTE,new Point(18,33));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Pokecenter:
				tileSet = INDOORS;
				widthHeight.x = 15;
				widthHeight.y = 9;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Pokecenter.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0b_pokemon_center.mid");
				bgm=JApplet.newAudioClip(url);
				break;
			case Nested_Village:
				tileSet = OUTDOORS;
				widthHeight.x = 50;
				widthHeight.y = 46;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("NestedVillage.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes2.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=5;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.KEYBOARDER,"Zell",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(25,32);
				//trainer[0].setPreMessage("Hey Trainer! When you see another trainer, you have to battle! Let's go!");
				trainer[0].setPostMessage("Welcome to Nested Village! It's nice and cozy here.");
				trainer[0].setDirection(270);
				//trainer[0].setViewRange(2);

				trainer[1]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Chaka",Pokemon.Species.CATERPIE,5);
				trainer[1].setHostile(false);
				trainer[1].setLocation(19,10);
				trainer[1].setPostMessage("I wonder what Joe looks like...");
				trainer[1].setDirection(90);

				trainer[2]=new Trainer(Trainer.TrainerType.CODER,"Bruce",Pokemon.Species.MAGIKARP,Pokemon.Species.MAGIKARP,5);
				trainer[2].setHostile(false);
				trainer[2].setLocation(37,18);
				//trainer[2].setPreMessage("Hey kid, let's battle. I just caught some Pokemon.");
				trainer[2].setPostMessage("I bet you could teach a Pokemon some moves that could be used outside of battle...");
				trainer[2].setDirection(180);
				trainer[2].setViewRange(1);

				trainer[3]=new Trainer(Trainer.TrainerType.CYPHER,"Penny",Pokemon.Species.MAGIKARP,10);
				trainer[3].setHostile(false);
				trainer[3].setLocation(30,16);
				//trainer[3].setPreMessage("Girls like to fish too you know!");
				trainer[3].setPostMessage("This village is handy for trainers passing by!");
				trainer[3].setDirection(270);
				trainer[3].setViewRange(1);

				if(timesBeatRival==1)
				{
					trainer[4]=new Trainer(Trainer.TrainerType.RIVAL,rivalName,Pokemon.Species.PIKACHU,Pokemon.Species.IVYSAUR,Pokemon.Species.CHARMELEON,Mechanics.getHighestLevel(partyPokemon,pcPokemon));
					trainer[4].setHostile(true);
					trainer[4].setLocation(1,22);
					trainer[4].setPreMessage(name+"? You again huh? How quaint. Have you taken my advice and leveled up your Pokemon like I told you? If not, I'm going to obliterate you. You silly goose. Let us dance!");
					trainer[4].setPostMessage("Well, well, well. You have definitely gotten stronger. You may have heard of a 'Team Java' on the island. They are old associates of Joe's. They've come looking for revenge or something. No matter. I'll crush them all.");
					trainer[4].setDirection(270);
					trainer[4].setViewRange(3);
				}
				else
				{
					trainer[4]=new Trainer(Trainer.TrainerType.HACKER,"Zachariah",Pokemon.Species.PIKACHU,5);
					trainer[4].setHostile(false);
					trainer[4].setLocation(1,22);
					trainer[4].setPreMessage("");
					trainer[4].setPostMessage("Hey dude, you're going to need a Pokemon that knows CUT if you want to keep going.");
					trainer[4].setDirection(270);
					trainer[4].setViewRange(3);
				}

				loadItemData();
				break;
			case Mart:
				tileSet = INDOORS;
				widthHeight.x = 11;
				widthHeight.y = 9;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Mart.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0c_shop.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=2;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.HACKER,"Marty",Pokemon.Species.MEOWTH,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(1,4);
				//trainer[0].setPreMessage("");
				trainer[0].setPostMessage("Welcome!");
				trainer[0].setDirection(0);
				trainer[0].setViewRange(1);

				trainer[1]=new Trainer(Trainer.TrainerType.NEWB,"Braxton",Pokemon.Species.CATERPIE,5);
				trainer[1].setHostile(false);
				trainer[1].setLocation(8,2);
				//trainer[0].setPreMessage("");
				trainer[1].setPostMessage("I shop all the time! I love this island!");
				trainer[1].setDirection(270);
				trainer[1].setViewRange(1);
				break;
			case Route_3:
				tileSet = OUTDOORS;
				widthHeight.x = 50;
				widthHeight.y = 48;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Route3.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes2.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=5;
				trainer=new Trainer[numTrainers];
				numItems=2;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.NEWB,"Tony",Pokemon.Species.TAUROS,Pokemon.Species.RATTATA,14);
				trainer[0].setHostile(true);
				trainer[0].setLocation(44,33);
				trainer[0].setPreMessage("Did that old guy give you HM 1 too?");
				trainer[0].setPostMessage("I'm scared of old people.");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(2);

				trainer[1]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Mateo",Pokemon.Species.CATERPIE,5);
				trainer[1].setHostile(false);
				trainer[1].setLocation(3,37);
				trainer[1].setPostMessage("You're going to need a Pokemon that can SURF to cross Route 11.");
				trainer[1].setDirection(90);

				trainer[2]=new Trainer(Trainer.TrainerType.KEYBOARDER,"Melvin",Pokemon.Species.PIKACHU,Pokemon.Species.PIDGEOTTO,18);
				trainer[2].setHostile(true);
				trainer[2].setLocation(14,27);
				trainer[2].setPreMessage("The sun is really bright here.");
				trainer[2].setPostMessage("Good thing I wore my sunscreen.");
				trainer[2].setDirection(180);
				trainer[2].setViewRange(4);

				trainer[3]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Hombre",Pokemon.Species.ODDISH,16);
				trainer[3].setHostile(true);
				trainer[3].setLocation(39,18);
				trainer[3].setPreMessage("I'm going to run you over!");
				trainer[3].setPostMessage("Drats! Stopped dead in my tracks.");
				trainer[3].setDirection(180);
				trainer[3].setViewRange(5);

				if (badges < 4)
				{
					trainer[4]=new Trainer(Trainer.TrainerType.ENGINEER,"Toto",Pokemon.Species.CATERPIE,5);
					trainer[4].setHostile(false);
					trainer[4].setLocation(37,6);
					trainer[4].setPostMessage("You're going to have to be careful if you're going into this cave.");
					trainer[4].setDirection(270);
				}
				else
				{
					trainer[4]=new Trainer(Trainer.TrainerType.ENGINEER,"Toto",Pokemon.Species.CATERPIE,5);
					trainer[4].setHostile(false);
					trainer[4].setLocation(36,6);
					trainer[4].setPostMessage("You're going to have to be careful if you're going into this cave.");
					trainer[4].setDirection(270);
				}


				mapItems[0]=new Item(Item.Type.SUPER_POTION,new Point(44,15));
				mapItems[1]=new Item(Item.Type.GREAT_BALL,new Point(11,2));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Villa_Del_Joe:
				tileSet = OUTDOORS;
				widthHeight.x = 76;
				widthHeight.y = 62;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("VillaDelJoe.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/4_joe_villa.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=5;
				trainer=new Trainer[numTrainers];
				numItems=1;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.CYPHER,"Nala",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(64,51);
				//trainer[0].setPreMessage("Hey Trainer! When you see another trainer, you have to battle! Let's go!");
				trainer[0].setPostMessage("This is Joe's own personal villa! He converted it into a trainer hot-spot!");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(2);

				trainer[1]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Kwame",Pokemon.Species.CATERPIE,5);
				trainer[1].setHostile(false);
				trainer[1].setLocation(48,58);
				trainer[1].setPostMessage("Everybody loves passing through Joe's Villa. It's so exciting!");
				trainer[1].setDirection(0);

				trainer[2]=new Trainer(Trainer.TrainerType.NEWB,"Dante",Pokemon.Species.MAGIKARP,Pokemon.Species.MAGIKARP,5);
				trainer[2].setHostile(false);
				trainer[2].setLocation(19,55);
				//trainer[2].setPreMessage("Hey kid, let's battle. I just caught some Pokemon.");
				trainer[2].setPostMessage("I'm going to battle Jace soon. I hope I win!");
				trainer[2].setDirection(90);
				trainer[2].setViewRange(1);

				trainer[3]=new Trainer(Trainer.TrainerType.CYPHER,"Trish",Pokemon.Species.MAGIKARP,10);
				trainer[3].setHostile(false);
				trainer[3].setLocation(30,9);
				//trainer[3].setPreMessage("Girls like to fish too you know!");
				trainer[3].setPostMessage("I heard that Jace trains by shocking himself!");
				trainer[3].setDirection(270);
				trainer[3].setViewRange(1);

				trainer[4]=new Trainer(Trainer.TrainerType.ENGINEER,"Ace",Pokemon.Species.CATERPIE,5);
				trainer[4].setHostile(false);
				trainer[4].setLocation(5,3);
				trainer[4].setPostMessage("Hey man, don't bother me. I'm busy.");
				trainer[4].setDirection(90);

				mapItems[0]=new Item(Item.Type.GREAT_BALL,new Point(19,60));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case YourHouse:
				tileSet = INDOORS;
				widthHeight.x = 12;
				widthHeight.y = 9;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("YourHouse.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/house.mid");
				bgm=JApplet.newAudioClip(url);
				break;
			case Route_4:
				tileSet = OUTDOORS;
				widthHeight.x = 40;
				widthHeight.y = 100;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Route4.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes3.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=5;
				trainer=new Trainer[numTrainers];
				numItems=2;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.NEWB,"Copycat",Pokemon.Species.DITTO,Pokemon.Species.DITTO,Pokemon.Species.DITTO,20);
				trainer[0].setHostile(true);
				trainer[0].setLocation(20,68);
				trainer[0].setPreMessage("Let's battle!");
				trainer[0].setPostMessage("...");
				trainer[0].setDirection(180);
				trainer[0].setViewRange(2);

				trainer[1]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Miguel",Pokemon.Species.GEODUDE,Pokemon.Species.TAUROS,20);
				trainer[1].setHostile(true);
				trainer[1].setLocation(4,58);
				trainer[1].setPreMessage("On your way to Mount Java?");
				trainer[1].setPostMessage("I was too, but now I have to heal my Pokemon.");
				trainer[1].setDirection(0);
				trainer[1].setViewRange(5);

				trainer[2]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Lucien",Pokemon.Species.RATICATE,Pokemon.Species.PIDGEOTTO,22);
				trainer[2].setHostile(true);
				trainer[2].setLocation(2,49);
				trainer[2].setPreMessage("I'm tired, but not too tired for a battle!");
				trainer[2].setPostMessage("Now I'm too tired to do anything.");
				trainer[2].setDirection(0);
				trainer[2].setViewRange(3);

				trainer[3]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Otto",Pokemon.Species.GLOOM,Pokemon.Species.FEAROW,24);
				trainer[3].setHostile(true);
				trainer[3].setLocation(17,35);
				trainer[3].setPreMessage("This bike definitely helps for long travels!");
				trainer[3].setPostMessage("Wipeout!");
				trainer[3].setDirection(90);
				trainer[3].setViewRange(3);

				trainer[4]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Melvin",Pokemon.Species.CATERPIE,5);
				trainer[4].setHostile(false);
				trainer[4].setLocation(15,4);
				trainer[4].setPostMessage("Mount Java is still a ways away. Keep going!");
				trainer[4].setDirection(270);

				mapItems[0]=new Item(Item.Type.IRON,new Point(36,54));
				mapItems[1]=new Item(Item.Type.REVIVE,new Point(32,42));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Mount_Java:
				tileSet = OUTDOORS;
				widthHeight.x = 50;
				widthHeight.y = 50;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("MountJava.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/3_mount_java.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=5;
				trainer=new Trainer[numTrainers];
				numItems=1;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.CYPHER,"Stephanie",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(15,43);
				//trainer[0].setPreMessage("Hey Trainer! When you see another trainer, you have to battle! Let's go!");
				trainer[0].setPostMessage("Mount Java is one of the island's most notable landmarks.");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);

				trainer[1]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Trent",Pokemon.Species.CATERPIE,5);
				trainer[1].setHostile(false);
				trainer[1].setLocation(39,22);
				trainer[1].setPostMessage("It looks like a fat Pokemon is sleeping over there...");
				trainer[1].setDirection(0);

				trainer[2]=new Trainer(Trainer.TrainerType.NEWB,"Nolan",Pokemon.Species.MAGIKARP,Pokemon.Species.MAGIKARP,5);
				trainer[2].setHostile(false);
				trainer[2].setLocation(32,11);
				//trainer[2].setPreMessage("Hey kid, let's battle. I just caught some Pokemon.");
				trainer[2].setPostMessage("Jin is one insane martial artist. He toppled me like nothing!");
				trainer[2].setDirection(270);
				trainer[2].setViewRange(1);

				trainer[3]=new Trainer(Trainer.TrainerType.BABE,"Veronica",Pokemon.Species.MAGIKARP,10);
				trainer[3].setHostile(false);
				trainer[3].setLocation(9,19);
				//trainer[3].setPreMessage("Girls like to fish too you know!");
				trainer[3].setPostMessage("I've heard stories of a mighty Pokemon that sleeps all day. It is fearsome.");
				trainer[3].setDirection(270);
				trainer[3].setViewRange(1);

				trainer[4]=new Trainer(Trainer.TrainerType.ENGINEER,"Zane",Pokemon.Species.CATERPIE,5);
				trainer[4].setHostile(false);
				trainer[4].setLocation(9,9);
				trainer[4].setPostMessage("Dude, I really hate caves.");
				trainer[4].setDirection(270);

				mapItems[0]=new Item(Item.Type.ULTRA_BALL,new Point(30,42));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Route_5:
				tileSet = OUTDOORS;
				widthHeight.x = 76;
				widthHeight.y = 40;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Route5.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes3.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=5;
				trainer=new Trainer[numTrainers];
				numItems=3;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.ENGINEER,"Trevor",Pokemon.Species.NINETALES,Pokemon.Species.PINSIR,Pokemon.Species.DRATINI,20);
				trainer[0].setHostile(true);
				trainer[0].setLocation(21,36);
				trainer[0].setPreMessage("Ahh...so we meet again my young padawan.");
				trainer[0].setPostMessage("Hmm...the force is strong in this one.");
				trainer[0].setDirection(0);
				trainer[0].setViewRange(4);

				trainer[1]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Bart",Pokemon.Species.RATTATA,Pokemon.Species.PIKACHU,20);
				trainer[1].setHostile(true);
				trainer[1].setLocation(14,22);
				trainer[1].setPreMessage("Mount Java is close! I'll beat you there!");
				trainer[1].setPostMessage("Darn it! Looks like you win this time.");
				trainer[1].setDirection(180);
				trainer[1].setViewRange(3);

				trainer[2]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Lucien",Pokemon.Species.RATICATE,Pokemon.Species.PIDGEOTTO,22);
				trainer[2].setHostile(true);
				trainer[2].setLocation(2,49);
				trainer[2].setPreMessage("I'm tired, but not too tired for a battle!");
				trainer[2].setPostMessage("Now I'm too tired to do anything.");
				trainer[2].setDirection(0);
				trainer[2].setViewRange(3);

				trainer[3]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Oscar",Pokemon.Species.WEEPINBELL,Pokemon.Species.PIDGEOTTO,Pokemon.Species.RATTATA,24);
				trainer[3].setHostile(true);
				trainer[3].setLocation(27,12);
				trainer[3].setPreMessage("I saw a spikey red-head nearby. You know him?");
				trainer[3].setPostMessage("That red-haired kid beat me too.");
				trainer[3].setDirection(270);
				trainer[3].setViewRange(3);

				if(timesBeatRival==2)
				{
					trainer[4]=new Trainer(Trainer.TrainerType.RIVAL,rivalName,Pokemon.Species.RAICHU,Pokemon.Species.VENUSAUR,Pokemon.Species.WARTORTLE,Pokemon.Species.CHARMELEON,Mechanics.getHighestLevel(partyPokemon,pcPokemon));
					trainer[4].setHostile(true);
					trainer[4].setLocation(60,7);
					trainer[4].setPreMessage("About time you showed up "+name+". You must be heading to Mount Java. I see you have a few badges now. Does that mean you have been training? Let's take a gander at your progress shall we?");
					trainer[4].setPostMessage("I shouldn't have expected anything less. You still have a lot of training to do though "+name+". I can only imagine our enemies will be stronger. I sure hope so. They are pathetic to me. Joe is my target.");
					trainer[4].setDirection(0);
					trainer[4].setViewRange(7);
				}
				else
				{
					trainer[4]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Jacob",Pokemon.Species.CHARMELEON,partyPokemon[0].level);
					trainer[4].setHostile(false);
					trainer[4].setLocation(60,7);
					trainer[4].setPostMessage("Mount Java scares me a little.");
					//trainer[4].setPostMessage("I shouldn't have expected anything less. You still have a lot of training to do though "+name+". I can only imagine our enemies will be stronger. I sure hope so. They are pathetic to me. Joe is my target.");
					trainer[4].setDirection(270);
					trainer[4].setViewRange(7);
				}

				mapItems[0]=new Item(Item.Type.PROTEIN,new Point(69,27));
				mapItems[1]=new Item(Item.Type.ULTRA_BALL,new Point(68,2));
				mapItems[2]=new Item(Item.Type.AWAKENING,new Point(46,4));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Route_6:
				tileSet = OUTDOORS;
				widthHeight.x = 60;
				widthHeight.y = 44;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Route6.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes4.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=5;
				trainer=new Trainer[numTrainers];
				numItems=3;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.ENGINEER,"Bradley S.",Pokemon.Species.MAGNETON,Pokemon.Species.DROWZEE,Pokemon.Species.DUGTRIO,25);
				trainer[0].setHostile(true);
				trainer[0].setLocation(57,11);
				trainer[0].setPreMessage("Whazup loser?");
				trainer[0].setPostMessage("Okay, best out of 3?");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(4);

				trainer[1]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Thomas",Pokemon.Species.RATICATE,Pokemon.Species.TAUROS,26);
				trainer[1].setHostile(true);
				trainer[1].setLocation(45,9);
				trainer[1].setPreMessage("Hey! Did you see? This route has a Pokemon Center!");
				trainer[1].setPostMessage("And now I have to use it...again.");
				trainer[1].setDirection(90);
				trainer[1].setViewRange(3);

				trainer[2]=new Trainer(Trainer.TrainerType.CODER,"Lando",Pokemon.Species.RATICATE,Pokemon.Species.PIDGEOTTO,27);
				trainer[2].setHostile(true);
				trainer[2].setLocation(29,7);
				trainer[2].setPreMessage("I'm on my way to battle Joe! Outta my way!");
				trainer[2].setPostMessage("Darn you! Maybe I should go home.");
				trainer[2].setDirection(0);
				trainer[2].setViewRange(1);

				trainer[3]=new Trainer(Trainer.TrainerType.HACKER,"Riley",Pokemon.Species.WEEPINBELL,Pokemon.Species.PIDGEOTTO,Pokemon.Species.RATICATE,24);
				trainer[3].setHostile(true);
				trainer[3].setLocation(28,20);
				trainer[3].setPreMessage("This sun is giving me a sunburn!");
				trainer[3].setPostMessage("Maybe I should get a hat like you.");
				trainer[3].setDirection(270);
				trainer[3].setViewRange(3);

				trainer[4]=new Trainer(Trainer.TrainerType.KEYBOARDER,"Huey",Pokemon.Species.CHARIZARD,38);
				trainer[4].setHostile(true);
				trainer[4].setLocation(31,29);
				trainer[4].setPreMessage("Check out my Pokemon!");
				trainer[4].setPostMessage("Perhaps I should have caught some more.");
				//trainer[4].setPostMessage("I shouldn't have expected anything less. You still have a lot of training to do though "+name+". I can only imagine our enemies will be stronger. I sure hope so. They are pathetic to me. Joe is my target.");
				trainer[4].setDirection(270);
				trainer[4].setViewRange(2);

				mapItems[0]=new Item(Item.Type.WATER_STONE,new Point(25,7));
				mapItems[1]=new Item(Item.Type.ULTRA_BALL,new Point(48,28));
				mapItems[2]=new Item(Item.Type.EXPERIENCE_ALL,new Point(35,10));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Streamreader_Hotel:
				tileSet = OUTDOORS;
				widthHeight.x = 50;
				widthHeight.y = 44;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("StreamReaderHotel.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/5_streamreader_hotel.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=3;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.CYPHER,"Bebe",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(21,7);
				//trainer[0].setPreMessage("Hey Trainer! When you see another trainer, you have to battle! Let's go!");
				trainer[0].setPostMessage("Jordan the gym leader is so kind to everyone!");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);

				trainer[1]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Yacob",Pokemon.Species.CATERPIE,5);
				trainer[1].setHostile(false);
				trainer[1].setLocation(19,26);
				trainer[1].setPostMessage("This place is so soothing...");
				trainer[1].setDirection(90);

				trainer[2]=new Trainer(Trainer.TrainerType.PROFESSOR,"Brutus",true);
				trainer[2].setHostile(false);
				trainer[2].setLocation(6,17);
				trainer[2].setPostMessage("Hello there son. Let me ask you a question. Have you ever wanted to push boulders out of the way? Or punch Pokemon in their stomachs? With STRENGTH you can.");
				trainer[2].setDirection(270);
				loadItemData();
				break;
			case Route_8:
				tileSet = OUTDOORS;
				widthHeight.x = 56;
				widthHeight.y = 64;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Route8.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes4.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];
				loadItemData();
				break;
			case Route_ARRAYINDEXOUTOFBOUNDSEXCEPTION:
				tileSet = OUTDOORS;
				widthHeight.x = 50;
				widthHeight.y = 30;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Articuno.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes4.mid");
				bgm=JApplet.newAudioClip(url);
				loadItemData();
				break;
			case Route_9:
				tileSet = OUTDOORS;
				widthHeight.x = 40;
				widthHeight.y = 90;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Route9.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes3.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=5;
				trainer=new Trainer[numTrainers];
				numItems=3;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.ENGINEER,"Smithy",Pokemon.Species.GEODUDE,Pokemon.Species.MEOWTH,Pokemon.Species.ABRA,25);
				trainer[0].setHostile(true);
				trainer[0].setLocation(9,20);
				trainer[0].setPreMessage("I jumped over Snorlax to get here. What about you?");
				trainer[0].setPostMessage("Maybe I should have caught it instead.");
				trainer[0].setDirection(90);
				trainer[0].setViewRange(2);

				trainer[1]=new Trainer(Trainer.TrainerType.JAVA,"GRUNT",Pokemon.Species.RATICATE,Pokemon.Species.EKANS,26);
				trainer[1].setHostile(true);
				trainer[1].setLocation(17,15);
				trainer[1].setPreMessage("You insolent brat! Bow to Team Java! We will take this island!");
				trainer[1].setPostMessage("Curses. It's only a matter of time now!");
				trainer[1].setDirection(0);
				trainer[1].setViewRange(3);

				trainer[2]=new Trainer(Trainer.TrainerType.CODER,"Luke",Pokemon.Species.PONYTA,Pokemon.Species.PIDGEOTTO,27);
				trainer[2].setHostile(true);
				trainer[2].setLocation(32,23);
				trainer[2].setPreMessage("I think that Pokemon battles are like chess matches.");
				trainer[2].setPostMessage("I'm much better at chess. We should play that instead.");
				trainer[2].setDirection(180);
				trainer[2].setViewRange(4);

				trainer[3]=new Trainer(Trainer.TrainerType.HACKER,"Rick",Pokemon.Species.WEEPINBELL,Pokemon.Species.PIDGEOTTO,Pokemon.Species.HORSEA,24);
				trainer[3].setHostile(true);
				trainer[3].setLocation(23,32);
				trainer[3].setPreMessage("Om nom nom. Do you eat sushi?");
				trainer[3].setPostMessage("You made me drop it!!!");
				trainer[3].setDirection(0);
				trainer[3].setViewRange(3);

				trainer[4]=new Trainer(Trainer.TrainerType.BABE,"Sandra",Pokemon.Species.HYPNO,Pokemon.Species.BEEDRILL,26);
				trainer[4].setHostile(true);
				trainer[4].setLocation(18,79);
				trainer[4].setPreMessage("Hey guess what?!?!");
				trainer[4].setPostMessage("Well now I'm not telling you!");
				//trainer[4].setPostMessage("I shouldn't have expected anything less. You still have a lot of training to do though "+name+". I can only imagine our enemies will be stronger. I sure hope so. They are pathetic to me. Joe is my target.");
				trainer[4].setDirection(180);
				trainer[4].setViewRange(4);

				mapItems[0]=new Item(Item.Type.FIRE_STONE,new Point(35,63));
				mapItems[1]=new Item(Item.Type.MAX_POTION,new Point(28,85));
				mapItems[2]=new Item(Item.Type.LEAF_STONE,new Point(4,78));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Recursive_Hot_Springs:
				tileSet = OUTDOORS;
				widthHeight.x = 40;
				widthHeight.y = 60;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Recursive.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/6_recursive_hot_springs.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=5;
				trainer=new Trainer[numTrainers];
				numItems=3;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.PROFESSOR,"Surfer",true);
				trainer[0].setHostile(false);
				trainer[0].setLocation(16,52);
				//trainer[0].setPreMessage("Ahh...so we meet again my young padawan.");
				trainer[0].setPostMessage("Hello there sonny. It looks like you have no way to heal your Pokemon in the hot springs. Here, take this HM for SURF so you can heal your Pokemon here. And you can SURF other places too!");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);

				trainer[1]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Hansel",Pokemon.Species.RATTATA,Pokemon.Species.PIKACHU,20);
				trainer[1].setHostile(false);
				trainer[1].setLocation(10,34);
				//trainer[1].setPreMessage("Mount Java is close! I'll beat you there!");
				trainer[1].setPostMessage("SURF is such a handy move to have! Prof. Surfer is too nice!");
				trainer[1].setDirection(270);
				trainer[1].setViewRange(3);

				trainer[2]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Lucas",Pokemon.Species.RATICATE,Pokemon.Species.PIDGEOTTO,22);
				trainer[2].setHostile(false);
				trainer[2].setLocation(21,20);
				//trainer[2].setPreMessage("I'm tired, but not too tired for a battle!");
				trainer[2].setPostMessage("I visit the hot springs every day to heal my Pokemon!");
				trainer[2].setDirection(180);
				trainer[2].setViewRange(0);

				trainer[3]=new Trainer(Trainer.TrainerType.BABE,"Lulu",Pokemon.Species.WEEPINBELL,Pokemon.Species.PIDGEOTTO,Pokemon.Species.RATTATA,24);
				trainer[3].setHostile(false);
				trainer[3].setLocation(27,12);
				//trainer[3].setPreMessage("I saw a spikey red-head nearby. You know him?");
				trainer[3].setPostMessage("They don't allow people in the hot spring. Only Pokemon.");
				trainer[3].setDirection(270);
				trainer[3].setViewRange(0);

				if(timesBeatRival==3)
				{
					trainer[4]=new Trainer(Trainer.TrainerType.RIVAL,rivalName,Pokemon.Species.RAICHU,Pokemon.Species.VENUSAUR,Pokemon.Species.BLASTOISE,Pokemon.Species.CHARMELEON,Pokemon.Species.LAPRAS,Mechanics.getHighestLevel(partyPokemon,pcPokemon));
					trainer[4].setHostile(true);
					trainer[4].setLocation(23,37);
					trainer[4].setPreMessage("Oh "+name+". You're so predictable. I knew you'd show up here. Everyone on this island is beginning to talk about us. They fear our awesome power. However, you seem to be more liked than me. Heh, no matter. Popularity doesn't win Pokemon battles!");
					trainer[4].setPostMessage("Confound it. How do you always best me "+name+"? Your Pokemon must really love you. There is no doubt about it. Well I'm going to find a legendary Pokemon. The next time we fight, you'll feel its awesome might!");
					trainer[4].setDirection(90);
					trainer[4].setViewRange(1);
				}
				else
				{
					trainer[4]=new Trainer(Trainer.TrainerType.NEWB,"Jacobin",Pokemon.Species.CHARMELEON,partyPokemon[0].level);
					trainer[4].setHostile(false);
					trainer[4].setLocation(23,37);
					trainer[4].setPostMessage("This icy gym gives me goosebumps! I'm not going in there anymore.");
					//trainer[4].setPostMessage("I shouldn't have expected anything less. You still have a lot of training to do though "+name+". I can only imagine our enemies will be stronger. I sure hope so. They are pathetic to me. Joe is my target.");
					trainer[4].setDirection(90);
					trainer[4].setViewRange(0);
				}

				mapItems[0]=new Item(Item.Type.FULL_RESTORE,new Point(7,12));
				mapItems[1]=new Item(Item.Type.REVIVE,new Point(31,25));
				mapItems[2]=new Item(Item.Type.FRESH_WATER,new Point(24,41));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Route_10:
				tileSet = OUTDOORS;
				widthHeight.x = 40;
				widthHeight.y = 50;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Route10.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes2.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=5;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.DEBUGGER,"Metro",Pokemon.Species.SEEL,Pokemon.Species.PERSIAN,Pokemon.Species.CLEFAIRY,32);
				trainer[0].setHostile(true);
				trainer[0].setLocation(20,14);
				trainer[0].setPreMessage("You say get 'em?");
				trainer[0].setPostMessage("...I would have said got 'em.");
				trainer[0].setDirection(180);
				trainer[0].setViewRange(2);

				trainer[1]=new Trainer(Trainer.TrainerType.JAVA,"GRUNT",Pokemon.Species.RATICATE,Pokemon.Species.WEEZING,34);
				trainer[1].setHostile(true);
				trainer[1].setLocation(17,18);
				trainer[1].setPreMessage("Team Java is getting this island back. Get out of our way!");
				trainer[1].setPostMessage("Our leader will know about this!!!");
				trainer[1].setDirection(0);
				trainer[1].setViewRange(2);

				trainer[2]=new Trainer(Trainer.TrainerType.CODER,"Lex",Pokemon.Species.HITMONLEE,Pokemon.Species.GRAVELER,36);
				trainer[2].setHostile(true);
				trainer[2].setLocation(32,23);
				trainer[2].setPreMessage("I'm your Kryptonite!");
				trainer[2].setPostMessage("You're not Superman are you?");
				trainer[2].setDirection(0);
				trainer[2].setViewRange(4);

				trainer[3]=new Trainer(Trainer.TrainerType.HACKER,"Wayne",Pokemon.Species.POLIWHIRL,Pokemon.Species.CHANSEY,Pokemon.Species.PINSIR,32);
				trainer[3].setHostile(true);
				trainer[3].setLocation(17,26);
				trainer[3].setPreMessage("Dude, we have to battle! ");
				trainer[3].setPostMessage("Dang it. Rematch?");
				trainer[3].setDirection(0);
				trainer[3].setViewRange(2);

				trainer[4]=new Trainer(Trainer.TrainerType.CYPHER,"Shelley",Pokemon.Species.GROWLITHE,Pokemon.Species.VENONAT,31);
				trainer[4].setHostile(true);
				trainer[4].setLocation(16,34);
				trainer[4].setPreMessage("I see you!!!");
				trainer[4].setPostMessage("Don't look at me like that!");
				//trainer[4].setPostMessage("I shouldn't have expected anything less. You still have a lot of training to do though "+name+". I can only imagine our enemies will be stronger. I sure hope so. They are pathetic to me. Joe is my target.");
				trainer[4].setDirection(0);
				trainer[4].setViewRange(5);

				loadItemData();
				break;
			case Route_11:
				tileSet = OUTDOORS;
				widthHeight.x = 70;
				widthHeight.y = 40;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Route11.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes5.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=4;
				trainer=new Trainer[numTrainers];
				numItems=3;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.PROFESSOR,"Smith",Pokemon.Species.DRATINI,35);
				trainer[0].setHostile(true);
				trainer[0].setLocation(10,2);
				trainer[0].setPreMessage("Would you like to see my rare Pokemon?");
				trainer[0].setPostMessage("I guess rarity and power aren't exactly correlated.");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(3);

				trainer[1]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Zimmer",Pokemon.Species.RATTATA,Pokemon.Species.PIKACHU,38);
				trainer[1].setHostile(true);
				trainer[1].setLocation(10,34);
				trainer[1].setPreMessage("Enumville is nearby. You're not stopping me!");
				trainer[1].setPostMessage("Grr. You sidetracked me.");
				trainer[1].setDirection(90);
				trainer[1].setViewRange(3);

				trainer[2]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Letty",Pokemon.Species.RATICATE,Pokemon.Species.PIDGEOTTO,34);
				trainer[2].setHostile(true);
				trainer[2].setLocation(21,20);
				trainer[2].setPreMessage("Hey kid, give me your money.");
				trainer[2].setPostMessage("Crap. Don't beat me up!");
				trainer[2].setDirection(180);
				trainer[2].setViewRange(3);

				trainer[3]=new Trainer(Trainer.TrainerType.BABE,"Lucy",Pokemon.Species.NIDORINO,Pokemon.Species.NIDORINA,Pokemon.Species.OMASTAR,36);
				trainer[3].setHostile(true);
				trainer[3].setLocation(62,26);
				trainer[3].setPreMessage("Hey there cutie. Let's battle.");
				trainer[3].setPostMessage("Darn it! I'm not giving you my phone number now!");
				trainer[3].setDirection(270);
				trainer[3].setViewRange(3);

				mapItems[0]=new Item(Item.Type.REVIVE,new Point(9,35));
				mapItems[1]=new Item(Item.Type.ANTIDOTE,new Point(3,10));
				mapItems[2]=new Item(Item.Type.NUGGET,new Point(19,3));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Enumville:
				tileSet = OUTDOORS;
				widthHeight.x = 50;
				widthHeight.y = 40;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Enumville.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes5.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=3;
				trainer=new Trainer[numTrainers];
				numItems=1;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.NEWB,"Smith",Pokemon.Species.DRATINI,35);
				trainer[0].setHostile(false);
				trainer[0].setLocation(8,17);
				trainer[0].setPreMessage("Would you like to see my rare Pokemon?");
				trainer[0].setPostMessage("I wonder what this town is named after...");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(3);

				trainer[1]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Zidane",Pokemon.Species.RATTATA,Pokemon.Species.PIKACHU,38);
				trainer[1].setHostile(false);
				trainer[1].setLocation(13,34);
				trainer[1].setPreMessage("Enumville is nearby. You're not stopping me!");
				trainer[1].setPostMessage("I think Polymorph Town is supposed to be here somewhere...");
				trainer[1].setDirection(90);
				trainer[1].setViewRange(3);

				trainer[2]=new Trainer(Trainer.TrainerType.ENGINEER,"Jasau",Pokemon.Species.RATICATE,Pokemon.Species.PIDGEOTTO,34);
				trainer[2].setHostile(true);
				trainer[2].setLocation(29,27);
				trainer[2].setPreMessage("Hey kid, give me your money.");
				trainer[2].setPostMessage("Hey dude. Look at these flowers. They're so pink.");
				trainer[2].setDirection(180);
				trainer[2].setViewRange(3);

				mapItems[0]=new Item(Item.Type.MAX_REPEL,new Point(20,9));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Route_12:
				tileSet = OUTDOORS;
				widthHeight.x = 50;
				widthHeight.y = 72;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Route12.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes4.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				loadItemData();
				break;
			case Polymorph_Town:
				tileSet = OUTDOORS;
				widthHeight.x = 50;
				widthHeight.y = 60;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Polymorph.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/7_polymorph_town.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=5;
				trainer=new Trainer[numTrainers];
				numItems=1;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.PROFESSOR,"Roderick Jr.",true);
				trainer[0].setHostile(false);
				trainer[0].setLocation(22,51);
				//trainer[0].setPreMessage("Ahh...so we meet again my young padawan.");
				trainer[0].setPostMessage("Hello hello! You look like you like to fish. Take this GOOD ROD. It will make you a pretty good fisherman, laddie.");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);

				trainer[1]=new Trainer(Trainer.TrainerType.DEBUGGER,"Perry",Pokemon.Species.RATTATA,Pokemon.Species.PIKACHU,20);
				trainer[1].setHostile(false);
				trainer[1].setLocation(30,44);
				//trainer[1].setPreMessage("Mount Java is close! I'll beat you there!");
				trainer[1].setPostMessage("You know, Polymorph Town always was my favorite town. No lie!");
				trainer[1].setDirection(270);
				trainer[1].setViewRange(3);

				trainer[2]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Bobbo",Pokemon.Species.RATICATE,Pokemon.Species.PIDGEOTTO,22);
				trainer[2].setHostile(false);
				trainer[2].setLocation(37,37);
				//trainer[2].setPreMessage("I'm tired, but not too tired for a battle!");
				trainer[2].setPostMessage("I flew here on my FEAROW to challenge James! I hear he's really good!");
				trainer[2].setDirection(90);
				trainer[2].setViewRange(0);

				trainer[3]=new Trainer(Trainer.TrainerType.CYPHER,"Ruth",Pokemon.Species.WEEPINBELL,Pokemon.Species.PIDGEOTTO,Pokemon.Species.RATTATA,24);
				trainer[3].setHostile(false);
				trainer[3].setLocation(27,9);
				//trainer[3].setPreMessage("I saw a spikey red-head nearby. You know him?");
				trainer[3].setPostMessage("I love smelling these wonderful flowers!");
				trainer[3].setDirection(270);
				trainer[3].setViewRange(0);

				trainer[4]=new Trainer(Trainer.TrainerType.NEWB,"Cody",Pokemon.Species.CHARMELEON,partyPokemon[0].level);
				trainer[4].setHostile(false);
				trainer[4].setLocation(21,31);
				trainer[4].setPostMessage("This is such an odd body of water...");
				//trainer[4].setPostMessage("I shouldn't have expected anything less. You still have a lot of training to do though "+name+". I can only imagine our enemies will be stronger. I sure hope so. They are pathetic to me. Joe is my target.");
				trainer[4].setDirection(0);
				trainer[4].setViewRange(0);

				mapItems[0]=new Item(Item.Type.MAX_REVIVE,new Point(13,28));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Binary_City:
				tileSet = OUTDOORS;
				widthHeight.x = 60;
				widthHeight.y = 50;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Binary.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/8_binary_city.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				loadItemData();
				break;
			case Route_13:
				tileSet = OUTDOORS;
				widthHeight.x = 60;
				widthHeight.y = 50;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Route13.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				loadItemData();
				break;
			case Route_7:
				tileSet = OUTDOORS;
				widthHeight.x = 60;
				widthHeight.y = 50;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Route7.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes3.mid");
				bgm=JApplet.newAudioClip(url);
				numTrainers=3;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				if(badges!=8)
				{
					numTrainers=3;
					trainer=new Trainer[numTrainers];
					trainer[0]=new Trainer(Trainer.TrainerType.JAVA,"GRUNT",Pokemon.Species.CHARMELEON,2);
					trainer[0].setHostile(false);
					trainer[0].setLocation(35,4);
					trainer[0].setPostMessage("Get out of here you blithering swine!");
					trainer[0].setDirection(0);
					trainer[0].setViewRange(0);

					trainer[1]=new Trainer(Trainer.TrainerType.JAVA,"GRUNT",Pokemon.Species.CHARMELEON,2);
					trainer[1].setHostile(false);
					trainer[1].setLocation(36,5);
					trainer[1].setPostMessage("Get out of here you blithering swine!");
					trainer[1].setDirection(90);
					trainer[1].setViewRange(0);

					trainer[2]=new Trainer(Trainer.TrainerType.JAVA,"GRUNT",Pokemon.Species.CHARMELEON,2);
					trainer[2].setHostile(false);
					trainer[2].setLocation(37,4);
					trainer[2].setPostMessage("Get out of here you blithering swine!");
					trainer[2].setDirection(180);
					trainer[2].setViewRange(0);
				}
				else if(!objectiveComplete[9])
				{
					numTrainers=2;
					trainer=new Trainer[numTrainers];

					numTrainers=3;
					trainer=new Trainer[numTrainers];
					trainer[0]=new Trainer(Trainer.TrainerType.JAVA,"GRUNT",Pokemon.Species.CHARMELEON,2);
					trainer[0].setHostile(false);
					trainer[0].setLocation(2,44);
					trainer[0].setPostMessage("Get out of here you blithering swine!");
					trainer[0].setDirection(0);
					trainer[0].setViewRange(0);

					trainer[1]=new Trainer(Trainer.TrainerType.JAVA,"GRUNT",Pokemon.Species.CHARMELEON,2);
					trainer[1].setHostile(false);
					trainer[1].setLocation(2,45);
					trainer[1].setPostMessage("Get out of here you blithering swine!");
					trainer[1].setDirection(0);
					trainer[1].setViewRange(0);

				}
				else
				{
					numTrainers=0;
					trainer=new Trainer[numTrainers];
				}

				loadItemData();
				break;
			case Null_Zone:
				tileSet = OUTDOORS;
				widthHeight.x = 25;
				widthHeight.y = 25;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Null.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes3.mid");
				bgm=JApplet.newAudioClip(url);
				loadItemData();
				break;
			case Champions_Walk:
				tileSet = OUTDOORS;
				widthHeight.x = 100;
				widthHeight.y = 40;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Champion.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0g_champions_walk.mid");
				bgm=JApplet.newAudioClip(url);
				loadItemData();
				break;
			case Victory_Road:
				tileSet = OUTDOORS;
				widthHeight.x = 40;
				widthHeight.y = 60;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("VictoryRoad.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0g_champions_walk.mid");
				bgm=JApplet.newAudioClip(url);

				if(timesBeatRival==4)
				{
					numTrainers=1;
					trainer=new Trainer[numTrainers];
					trainer[0]=new Trainer(Trainer.TrainerType.RIVAL,rivalName,Pokemon.Species.RAICHU,Pokemon.Species.VENUSAUR,Pokemon.Species.BLASTOISE,Pokemon.Species.CHARIZARD,Pokemon.Species.SNORLAX,Pokemon.Species.LAPRAS,Mechanics.getHighestLevel(partyPokemon,pcPokemon));
					trainer[0].setHostile(true);
					trainer[0].setLocation(14,20);
					trainer[0].setPreMessage(name+". You're always around, aren't you. I'm through playing games with you, you silly boy. I have a secret Pokemon with me now. "+name+"! Let this be our final battle!");
					trainer[0].setPostMessage("Fine. Take on Joe. You've earned it. I'll be rooting for you...in secret.");
					trainer[0].setDirection(0);
					trainer[0].setViewRange(2);
				}

				numItems=0;
				mapItems=new Item[numItems];

				loadItemData();
				break;
			case Peach_City:
				tileSet = OUTDOORS;
				widthHeight.x = 80;
				widthHeight.y = 48;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("VersionCityP.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/10_peachcream_city.mid");
				bgm=JApplet.newAudioClip(url);
				loadItemData();
				break;
			case Cream_City:
				tileSet = OUTDOORS;
				widthHeight.x = 80;
				widthHeight.y = 48;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("VersionCityC.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/10_peachcream_city.mid");
				bgm=JApplet.newAudioClip(url);
				loadItemData();
				break;
			case Babbs_Lab:
				tileSet = INDOORS;
				widthHeight.x = 10;
				widthHeight.y = 13;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Babb.tilemap");
				url=JokemonDriver.class.getResource("Music/oak-s-lab.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=3;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				if(!objectiveComplete[10])
				{
					trainer[0]=new Trainer(Trainer.TrainerType.BABB,"",Pokemon.Species.MEWTWO,105);
					trainer[0].setHostile(false);
					trainer[0].setLocation(5,1);
					//trainer[0].setPreMessage("");
					trainer[0].setPostMessage("Welcome to Joe's Island "+name+"! My name is Babb! I'm Joe's uncle. He is an entrepreneur that bought this"+
						" island and put Pokemon on it. Since you won a trip here like many others, your goals while here are to catch all 151 Pokemon, defeat"+
						" the eight Gym Leaders, defeat Joe's Elite 4, and ultimately defeat Joe. Good luck "+name+"!");
					trainer[0].setDirection(270);
					trainer[0].setViewRange(2);
				}
				else
				{
					trainer[0]=new Trainer(Trainer.TrainerType.BABB,"",Pokemon.Species.MEWTWO,Pokemon.Species.MEW,Pokemon.Species.MISSINGNO,Pokemon.Species.ARTICUNO,Pokemon.Species.ZAPDOS,Pokemon.Species.MOLTRES,105);
					trainer[0].setHostile(true);
					trainer[0].setLocation(5,1);
					trainer[0].setPreMessage(name+"! You've come a long way since I first spoke with you! You and your Pokemon must be very strong now! How about battling me? If you thought Joe was difficult, you haven't seen anything yet. Hahaha!");
					trainer[0].setPostMessage("Congratulations "+name+"! You've beaten me! Even with my incredible team, your team proved to be more incredible. Well, there's not much left for you to do, except complete the Pokedex. Ideally, you'd have that done by now! Slacking are you? Here, watch the credits!");
					trainer[0].setDirection(270);
					trainer[0].setViewRange(0);
				}

				trainer[1]=new Trainer(Trainer.TrainerType.CODER,"Ryan",Pokemon.Species.CATERPIE,5);
				trainer[1].setHostile(false);
				trainer[1].setLocation(7,9);
				//trainer[0].setPreMessage("");
				trainer[1].setPostMessage("I'm one of Babb's assistants! He's really smart!");
				trainer[1].setDirection(180);
				trainer[1].setViewRange(2);

				trainer[2]=new Trainer(Trainer.TrainerType.BABE,"Chelsey",Pokemon.Species.CATERPIE,5);
				trainer[2].setHostile(false);
				trainer[2].setLocation(1,11);
				//trainer[0].setPreMessage("");
				trainer[2].setPostMessage("Babb is the authority on Pokemon here on the island! He's also a tough battler!");
				trainer[2].setDirection(0);
				trainer[2].setViewRange(2);
				break;
			case Generic_1:
				tileSet = INDOORS;
				widthHeight.x = 8;
				widthHeight.y = 8;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Generic1.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/house.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=1;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				if(returnArea==Area.Villa_Del_Joe&&houseInt%5==0)
				{
					trainer[0]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Bike Enthusiast",true);
					trainer[0].setHostile(false);
					trainer[0].setLocation(3,2);
					//trainer[0].setPreMessage("");
					trainer[0].setPostMessage("Vroom vrooom!!! I love bicycles! If you've got 3 badges, you've got a bike too, dude!");
					trainer[0].setDirection(270);
					trainer[0].setViewRange(0);
				}
				else
				switch(houseInt%5)
				{
					default:
						trainer[0]=new Trainer(Trainer.TrainerType.CODER,"Krillin",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("How can Joe afford all of these houses?");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 0:
						trainer[0]=new Trainer(Trainer.TrainerType.NEWB,"Bud",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("I got this house for free! Babb gave it to me!");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 1:
						trainer[0]=new Trainer(Trainer.TrainerType.NEWB,"Timothy",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("Joe is my role model!");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 2:
						trainer[0]=new Trainer(Trainer.TrainerType.PROFESSOR,"Desmond",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("I've learned many things over the years, but Joe may have surpassed my knowledge.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 3:
						trainer[0]=new Trainer(Trainer.TrainerType.HACKER,"Kurt",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("I wonder if there is a Pokemon Olympics...");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
				}
				break;
			case Rival_House:
				tileSet = INDOORS;
				widthHeight.x = 12;
				widthHeight.y = 9;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("RivalHouse.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/house.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=1;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.CYPHER,rivalName+"'s Sister",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(5,5);
				//trainer[0].setPreMessage("");
				trainer[0].setPostMessage("Hey "+name+"! "+rivalName+" isn't here right now.");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);
				break;
			case Gym_1:
				tileSet = INDOORS;
				widthHeight.x = 15;
				widthHeight.y = 15;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Gym1.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0f_gym.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=4;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				if(objectiveComplete[10])
					levelBoost=50;

				trainer[0]=new Trainer(Trainer.TrainerType.ENGINEER,"Jeffery",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(8,12);
				//trainer[0].setPreMessage("");
				trainer[0].setPostMessage("Welcome to the Stringville Gym! You may need to train on Route 0 first.");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);

				trainer[1]=new Trainer(Trainer.TrainerType.DEBUGGER,"Dustin",Pokemon.Species.METAPOD,Pokemon.Species.KAKUNA,8+levelBoost);
				trainer[1].setHostile(true);
				trainer[1].setLocation(5,11);
				trainer[1].setPreMessage("Gyms are for hardcore trainers! Are you ready to battle?");
				trainer[1].setPostMessage("Looks like you're hardcore enough!");
				trainer[1].setDirection(90);
				trainer[1].setViewRange(3);

				trainer[2]=new Trainer(Trainer.TrainerType.DEBUGGER,"Rex",Pokemon.Species.WEEDLE,Pokemon.Species.CATERPIE,8+levelBoost);
				trainer[2].setHostile(true);
				trainer[2].setLocation(1,4);
				trainer[2].setPreMessage("Jenny is tough, but I'm tough too!");
				trainer[2].setPostMessage("You're tough as nails!");
				trainer[2].setDirection(0);
				trainer[2].setViewRange(1);

				if(objectiveComplete[10]||badges==0&&objectiveComplete[0])
				{
					trainer[3]=new Trainer(Trainer.TrainerType.GYM_LEADER,"Jenny",Pokemon.Species.BUTTERFREE,Pokemon.Species.BEEDRILL,Pokemon.Species.SCYTHER,Pokemon.Species.PINSIR,12+levelBoost);
					trainer[3].setHostile(true);
					trainer[3].setLocation(9,1);
					trainer[3].setPreMessage("Hello "+name+"! I've heard a lot about you! Apparently you're one of the better trainers to come to the island. Bug Pokemon are my specialty! They are very powerful. Do you have what it takes to win? Let's find out!");
					trainer[3].setPostMessage("Excellent job "+name+"! I award you the ABSTRACT BADGE and this TM containing RAZOR WIND! Head on to the next Gym! Good luck with your journey!");
					trainer[3].setDirection(270);
					trainer[3].setViewRange(0);
				}
				else
				{
					trainer[3]=new Trainer(Trainer.TrainerType.DEBUGGER,"Chauncey",Pokemon.Species.BUTTERFREE,Pokemon.Species.BEEDRILL,Pokemon.Species.SCYTHER,Pokemon.Species.PINSIR,12);
					trainer[3].setHostile(false);
					trainer[3].setLocation(9,1);
					//trainer[3].setPreMessage("");
					trainer[3].setPostMessage("Jenny went out to catch more bugs I think.");
					trainer[3].setDirection(270);
					trainer[3].setViewRange(3);
				}
				break;
			case Gym_2:
				tileSet = INDOORS;
				widthHeight.x = 25;
				widthHeight.y = 15;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Gym2.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0f_gym.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=4;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				if(objectiveComplete[10])
					levelBoost=50;

				trainer[0]=new Trainer(Trainer.TrainerType.ENGINEER,"Jeffery",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(11,12);
				//trainer[0].setPreMessage("");
				trainer[0].setPostMessage("Welcome to the Args Harbor Gym! The prefered type here is GRASS. Be careful!");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);

				trainer[1]=new Trainer(Trainer.TrainerType.KEYBOARDER,"William",Pokemon.Species.PARAS,Pokemon.Species.BELLSPROUT,15+levelBoost);
				trainer[1].setHostile(true);
				trainer[1].setLocation(10,10);
				trainer[1].setPreMessage("Grass Pokemon are superior! Don't you think so?");
				trainer[1].setPostMessage("I still think Grass Pokemon are better.");
				trainer[1].setDirection(0);
				trainer[1].setViewRange(3);

				trainer[2]=new Trainer(Trainer.TrainerType.HACKER,"Hal",Pokemon.Species.EXEGGCUTE,Pokemon.Species.PARAS,Pokemon.Species.BELLSPROUT,15+levelBoost);
				trainer[2].setHostile(true);
				trainer[2].setLocation(15,5);
				trainer[2].setPreMessage("You just had to come over here didn't you. Oh well, let's battle.");
				trainer[2].setPostMessage("Wowsers! You're too good.");
				trainer[2].setDirection(180);
				trainer[2].setViewRange(2);

				if(objectiveComplete[10]||badges==1)
				{
					trainer[3]=new Trainer(Trainer.TrainerType.GYM_LEADER,"Jimmy",Pokemon.Species.TANGELA,Pokemon.Species.PARAS,Pokemon.Species.EXEGGUTOR,Pokemon.Species.BELLSPROUT,Pokemon.Species.ODDISH,18+levelBoost);
					trainer[3].setHostile(true);
					trainer[3].setLocation(18,3);
					trainer[3].setPreMessage("Ah "+name+". Jenny told me that you are a formidable opponent. You bested her with Pokemon prowess. I won't be toppled so easily though. Grass Pokemon are very majestic. Let me show you!");
					trainer[3].setPostMessage("Congrats "+name+"! Here, take the NATIVE BADGE! It is a sign of your bond with your Pokemon! Take this TM containing SOLARBEAM as well. It should help you on the remainder of your quest.");
					trainer[3].setDirection(0);
					trainer[3].setViewRange(0);
				}
				else
				{
					trainer[3]=new Trainer(Trainer.TrainerType.DEBUGGER,"Chauncey",Pokemon.Species.BUTTERFREE,Pokemon.Species.BEEDRILL,Pokemon.Species.SCYTHER,Pokemon.Species.PINSIR,12);
					trainer[3].setHostile(false);
					trainer[3].setLocation(18,3);
					//trainer[3].setPreMessage("");
					trainer[3].setPostMessage("Jimmy went to sail around the island. He's not here.");
					trainer[3].setDirection(0);
					trainer[3].setViewRange(3);
				}
				break;
			case Gym_3:
				tileSet = INDOORS;
				widthHeight.x = 20;
				widthHeight.y = 20;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Gym3.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0f_gym.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=4;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				if(objectiveComplete[10])
					levelBoost=50;

				trainer[0]=new Trainer(Trainer.TrainerType.ENGINEER,"Jeffery",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(10,13);
				//trainer[0].setPreMessage("");
				trainer[0].setPostMessage("Welcome Joe's personal Gym! You need to be able to beat ELECTRIC Pokemon to win here!");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);

				trainer[1]=new Trainer(Trainer.TrainerType.HACKER,"Dusty",Pokemon.Species.PIKACHU,Pokemon.Species.VOLTORB,20+levelBoost);
				trainer[1].setHostile(true);
				trainer[1].setLocation(5,8);
				trainer[1].setPreMessage("I'm going to shock you up!");
				trainer[1].setPostMessage("Are you wearing rubber?");
				trainer[1].setDirection(0);
				trainer[1].setViewRange(2);

				trainer[2]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Rufus",Pokemon.Species.MAGNEMITE,Pokemon.Species.PIKACHU,20+levelBoost);
				trainer[2].setHostile(true);
				trainer[2].setLocation(14,4);
				trainer[2].setPreMessage("Wait! I'll battle you!");
				trainer[2].setPostMessage("You may have beaten me, but Jace will electrocute you!");
				trainer[2].setDirection(180);
				trainer[2].setViewRange(2);

				if(objectiveComplete[10]||badges==2)
				{
					trainer[3]=new Trainer(Trainer.TrainerType.GYM_LEADER,"Jace",Pokemon.Species.RAICHU,Pokemon.Species.ELECTABUZZ,Pokemon.Species.VOLTORB,Pokemon.Species.JOLTEON,Pokemon.Species.MAGNETON,24+levelBoost);
					trainer[3].setHostile(true);
					trainer[3].setLocation(10,1);
					trainer[3].setPreMessage(name+"! You're becoming a very popular trainer on this island! Many other trainers are beginning to talk about you! Some think you'll even get an opportunity to battle Joe! Not if I can help it! I'll put a shock to your system!");
					trainer[3].setPostMessage("You short circuited me "+name+"! Great job! I'll award you the STATIC BADGE and TM 24 with THUNDERBOLT. Use it well. Go all the way to Joe kiddo. I believe in you.");
					trainer[3].setDirection(270);
					trainer[3].setViewRange(0);
				}
				else
				{
					trainer[3]=new Trainer(Trainer.TrainerType.DEBUGGER,"Manchy",Pokemon.Species.RAICHU,12);
					trainer[3].setHostile(false);
					trainer[3].setLocation(10,1);
					//trainer[3].setPreMessage("");
					trainer[3].setPostMessage("Jace is rarely here now. He must be somewhere close though.");
					trainer[3].setDirection(270);
					trainer[3].setViewRange(3);
				}
				break;
			case Gym_4:
				tileSet = INDOORS;
				widthHeight.x = 20;
				widthHeight.y = 20;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Gym4.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0f_gym.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=4;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				if(objectiveComplete[10])
					levelBoost=50;

				trainer[0]=new Trainer(Trainer.TrainerType.ENGINEER,"Jeffery",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(9,16);
				//trainer[0].setPreMessage("");
				trainer[0].setPostMessage("Welcome to Mount Java Gym! Fists of fury and fast kicks rule here.");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);

				trainer[1]=new Trainer(Trainer.TrainerType.CODER,"Jackie",Pokemon.Species.MANKEY,Pokemon.Species.MACHOP,27+levelBoost);
				trainer[1].setHostile(true);
				trainer[1].setLocation(14,11);
				trainer[1].setPreMessage("Karate is my favorite fighting style. What's yours?");
				trainer[1].setPostMessage("I have brought dishonor to this gym.");
				trainer[1].setDirection(180);
				trainer[1].setViewRange(1);

				trainer[2]=new Trainer(Trainer.TrainerType.HACKER,"Chan",Pokemon.Species.POLIWHIRL,Pokemon.Species.MACHOKE,27+levelBoost);
				trainer[2].setHostile(true);
				trainer[2].setLocation(5,13);
				trainer[2].setPreMessage("My master will have to wait. Let's battle!");
				trainer[2].setPostMessage("I have failed you my master...");
				trainer[2].setDirection(270);
				trainer[2].setViewRange(4);

				if(objectiveComplete[10]||badges==3)
				{
					trainer[3]=new Trainer(Trainer.TrainerType.GYM_LEADER,"Jin",Pokemon.Species.PRIMEAPE,Pokemon.Species.MACHAMP,Pokemon.Species.HITMONLEE,Pokemon.Species.HITMONCHAN,Pokemon.Species.POLIWRATH,32+levelBoost);
					trainer[3].setHostile(true);
					trainer[3].setLocation(9,4);
					trainer[3].setPreMessage(name+"! Your reputation grows ever higher! Just like the grass after a long rain! I will show you the power of physical strength. My Pokemon's bodies have undergone heavy training. Bring it on! Hyaaah!!!");
					trainer[3].setPostMessage(name+"! You are very strong young one! I don't know what your training routine is, but it seems to work! Here take the SUPER BADGE and this TM containing SUBMISSION! Hyaaah!");
					trainer[3].setDirection(270);
					trainer[3].setViewRange(0);
				}
				else
				{
					trainer[3]=new Trainer(Trainer.TrainerType.DEBUGGER,"Tyrus",Pokemon.Species.RAICHU,12);
					trainer[3].setHostile(false);
					trainer[3].setLocation(9,4);
					//trainer[3].setPreMessage("");
					trainer[3].setPostMessage("I bet that Jin is out training right now. In fact, I know it.");
					trainer[3].setDirection(270);
					trainer[3].setViewRange(3);
				}
				break;
			case Gym_5:
				tileSet = INDOORS;
				widthHeight.x = 20;
				widthHeight.y = 20;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Gym5.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0f_gym.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=4;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				if(objectiveComplete[10])
					levelBoost=50;

				trainer[0]=new Trainer(Trainer.TrainerType.ENGINEER,"Jeffery",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(9,14);
				//trainer[0].setPreMessage("");
				trainer[0].setPostMessage("Welcome to Streamreader Gym! Jordan and her friends use NORMAL Pokemon here!");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);

				trainer[1]=new Trainer(Trainer.TrainerType.CYPHER,"Jenna",Pokemon.Species.JIGGLYPUFF,Pokemon.Species.CLEFAIRY,Pokemon.Species.MEOWTH,35+levelBoost);
				trainer[1].setHostile(true);
				trainer[1].setLocation(4,11);
				trainer[1].setPreMessage("My cute Pokemon will surprise you!");
				trainer[1].setPostMessage("Your Pokemon are ugly.");
				trainer[1].setDirection(180);
				trainer[1].setViewRange(2);

				trainer[2]=new Trainer(Trainer.TrainerType.BABE,"Lexi",Pokemon.Species.DITTO,Pokemon.Species.TAUROS,35+levelBoost);
				trainer[2].setHostile(true);
				trainer[2].setLocation(11,10);
				trainer[2].setPreMessage("I think my Pokemon are tough enough to take you on!");
				trainer[2].setPostMessage("Oh no! My Pokemon!");
				trainer[2].setDirection(180);
				trainer[2].setViewRange(4);

				if(objectiveComplete[10]||badges==4)
				{
					trainer[3]=new Trainer(Trainer.TrainerType.GYM_LEADER,"Jordan",Pokemon.Species.CLEFABLE,Pokemon.Species.WIGGLYTUFF,Pokemon.Species.PERSIAN,Pokemon.Species.LICKITUNG,Pokemon.Species.CHANSEY,39+levelBoost);
					trainer[3].setHostile(true);
					trainer[3].setLocation(9,6);
					trainer[3].setPreMessage("Is that you, "+name+"? Hehehe. You're cuter than I thought you'd be. I'm Jordan. I love NORMAL type Pokemon. They're so cuddly, but strong too! But enough talk, let's battle!");
					trainer[3].setPostMessage("Oh pooh. You beat me "+name+". Your Pokemon must have really strong bonds with you. I adore that! Well, here. Take the NULL BADGE and this TM for HYPER BEAM. Come see me again sometime cutie!");
					trainer[3].setDirection(270);
					trainer[3].setViewRange(0);
				}
				else
				{
					trainer[3]=new Trainer(Trainer.TrainerType.BABE,"Jordan?",Pokemon.Species.RAICHU,12);
					trainer[3].setHostile(false);
					trainer[3].setLocation(9,6);
					//trainer[3].setPreMessage("");
					trainer[3].setPostMessage("I'm not Jordan, but I sure do look like her huh?");
					trainer[3].setDirection(270);
					trainer[3].setViewRange(3);
				}
				break;
			case Gym_6:
				tileSet = INDOORS;
				widthHeight.x = 20;
				widthHeight.y = 20;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Gym6.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0f_gym.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=4;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				if(objectiveComplete[10])
					levelBoost=50;

				trainer[0]=new Trainer(Trainer.TrainerType.ENGINEER,"Jeffery",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(13,13);
				//trainer[0].setPreMessage("");
				trainer[0].setPostMessage("Welcome to the Recursive Gym! ICE is this gym's specialty!");
				trainer[0].setDirection(180);
				trainer[0].setViewRange(0);

				trainer[1]=new Trainer(Trainer.TrainerType.KEYBOARDER,"Stanley",Pokemon.Species.SEEL,Pokemon.Species.SHELLDER,Pokemon.Species.SEEL,38+levelBoost);
				trainer[1].setHostile(true);
				trainer[1].setLocation(12,0);
				trainer[1].setPreMessage("It's so slippery in here!");
				trainer[1].setPostMessage("I'm going to get some cleats for sure.");
				trainer[1].setDirection(270);
				trainer[1].setViewRange(1);

				trainer[2]=new Trainer(Trainer.TrainerType.BABE,"Martha",Pokemon.Species.DEWGONG,Pokemon.Species.SHELLDER,37+levelBoost);
				trainer[2].setHostile(true);
				trainer[2].setLocation(5,8);
				trainer[2].setPreMessage("Ouch! Did you just slide into me?");
				trainer[2].setPostMessage("Oh well. I kinda liked it...");
				trainer[2].setDirection(90);
				trainer[2].setViewRange(1);

				if(objectiveComplete[10]||badges==5)
				{
					trainer[3]=new Trainer(Trainer.TrainerType.GYM_LEADER,"Joy",Pokemon.Species.DEWGONG,Pokemon.Species.CLOYSTER,Pokemon.Species.LAPRAS,Pokemon.Species.JYNX,43+levelBoost);
					trainer[3].setHostile(true);
					trainer[3].setLocation(10,5);
					trainer[3].setPreMessage(name+". Hello. It's a pleasure to finally meet you. I heard you were very strong. Is that true? My ICE Pokemon are extremely strong. They will pose a challenge for you. We'll freeze you to the bone.");
					trainer[3].setPostMessage("Darn it. You really are as good as everyone says. I guess I should give you some rewards, huh? Ok. Here. Take the TRANSIENT BADGE and this TM for ICE BEAM. Ok. Be on your way.");
					trainer[3].setDirection(270);
					trainer[3].setViewRange(0);
				}
				else
				{
					trainer[3]=new Trainer(Trainer.TrainerType.NEWB,"Eric",Pokemon.Species.RAICHU,12);
					trainer[3].setHostile(false);
					trainer[3].setLocation(10,5);
					//trainer[3].setPreMessage("");
					trainer[3].setPostMessage("Joy isn't here. She got sick of the cold and decided to warm up.");
					trainer[3].setDirection(270);
					trainer[3].setViewRange(0);
				}
				break;
			case Gym_7:
				tileSet = INDOORS;
				widthHeight.x = 20;
				widthHeight.y = 20;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Gym7.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0f_gym.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=4;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				if(objectiveComplete[10])
					levelBoost=50;

				trainer[0]=new Trainer(Trainer.TrainerType.ENGINEER,"Jeffery",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(9,13);
				//trainer[0].setPreMessage("");
				trainer[0].setPostMessage("Welcome to Polymorph Town Gym! Beware the mental power here!");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);

				trainer[1]=new Trainer(Trainer.TrainerType.ENGINEER,"Bill",Pokemon.Species.DROWZEE,Pokemon.Species.KADABRA,Pokemon.Species.DROWZEE,43+levelBoost);
				trainer[1].setHostile(true);
				trainer[1].setLocation(4,12);
				trainer[1].setPreMessage("You want to match wits?");
				trainer[1].setPostMessage("I concede defeat.");
				trainer[1].setDirection(180);
				trainer[1].setViewRange(1);

				trainer[2]=new Trainer(Trainer.TrainerType.PROFESSOR,"Steve",Pokemon.Species.HYPNO,Pokemon.Species.KADABRA,43+levelBoost);
				trainer[2].setHostile(true);
				trainer[2].setLocation(11,10);
				trainer[2].setPreMessage("Professors are brilliant battlers. Let me show you.");
				trainer[2].setPostMessage("...It seems that you are more brilliant.");
				trainer[2].setDirection(180);
				trainer[2].setViewRange(2);

				if(objectiveComplete[10]||badges==6)
				{
					trainer[3]=new Trainer(Trainer.TrainerType.GYM_LEADER,"James",Pokemon.Species.MR_MIME,Pokemon.Species.HYPNO,Pokemon.Species.ALAKAZAM,Pokemon.Species.JYNX,48+levelBoost);
					trainer[3].setHostile(true);
					trainer[3].setLocation(9,4);
					trainer[3].setPreMessage("Wait. Don't say anything. You're "+name+". I can read your brain waves. You've come to battle me for a badge. Don't act surprised. When you train with PSYCHIC Pokemon as much as I do, you learn a thing or two. Anyhoo, let's battle. Mind will always prevail over matter.");
					trainer[3].setPostMessage("...I did not see that coming. Wow. Congratulations. Here is the INTERFACE BADGE and this TM for PSYCHIC. Let me leave you with one more prediction. I sense that you will beat the great Joe.");
					trainer[3].setDirection(270);
					trainer[3].setViewRange(0);
				}
				else
				{
					trainer[3]=new Trainer(Trainer.TrainerType.CYPHER,"Erika",Pokemon.Species.RAICHU,12);
					trainer[3].setHostile(false);
					trainer[3].setLocation(9,4);
					//trainer[3].setPreMessage("");
					trainer[3].setPostMessage("James is out 'mental training.' Or something like that.");
					trainer[3].setDirection(270);
					trainer[3].setViewRange(0);
				}
				break;
			case Gym_8:
				tileSet = INDOORS;
				widthHeight.x = 20;
				widthHeight.y = 20;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Gym8.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0f_gym.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=4;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				if(objectiveComplete[10])
					levelBoost=50;

				trainer[0]=new Trainer(Trainer.TrainerType.ENGINEER,"Jeffery",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(7,17);
				//trainer[0].setPreMessage("");
				trainer[0].setPostMessage("Welcome to Binary City Gym! GHOST and POISON Pokemon rule here! Invisible floors too!");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);

				trainer[1]=new Trainer(Trainer.TrainerType.HACKER,"Richard",Pokemon.Species.GASTLY,Pokemon.Species.GRIMER,Pokemon.Species.HAUNTER,45+levelBoost);
				trainer[1].setHostile(true);
				trainer[1].setLocation(7,14);
				trainer[1].setPreMessage("The undead are mighty. Can you stop them?");
				trainer[1].setPostMessage("You must already be dead.");
				trainer[1].setDirection(0);
				trainer[1].setViewRange(1);

				trainer[2]=new Trainer(Trainer.TrainerType.CODER,"Hercule",Pokemon.Species.NIDORINO,Pokemon.Species.KOFFING,45+levelBoost);
				trainer[2].setHostile(true);
				trainer[2].setLocation(12,12);
				trainer[2].setPreMessage("Bret Michaels hit it spot on.");
				trainer[2].setPostMessage("Poison Pokemon are still the best in my book.");
				trainer[2].setDirection(90);
				trainer[2].setViewRange(1);

				if(objectiveComplete[10]||badges==7)
				{
					trainer[3]=new Trainer(Trainer.TrainerType.GYM_LEADER,"Jessica",Pokemon.Species.NIDOKING,Pokemon.Species.NIDOQUEEN,Pokemon.Species.WEEZING,Pokemon.Species.ARBOK,Pokemon.Species.GENGAR,Pokemon.Species.MUK,52+levelBoost);
					trainer[3].setHostile(true);
					trainer[3].setLocation(9,2);
					trainer[3].setPreMessage(name+"! Your quest is drawing near its end! You now have seven of Joe's eight required badges! I hold the last one. You may feel that the other gym leaders posed little or no threat. Not me. If you want this badge, you're going to have to earn it!");
					trainer[3].setPostMessage("Incredible! You fainted all of my Pokemon! You really are something! I must give you the FINAL BADGE and the TM for TOXIC. Go now "+name+" to take on the Elite Four and Joe!...Or at least you should, but there's something about Team Java screwing stuff up.");
					trainer[3].setDirection(270);
					trainer[3].setViewRange(0);
				}
				else
				{
					trainer[3]=new Trainer(Trainer.TrainerType.NEWB,"Luther",Pokemon.Species.RAICHU,12);
					trainer[3].setHostile(false);
					trainer[3].setLocation(9,2);
					//trainer[3].setPreMessage("");
					trainer[3].setPostMessage("Jessica is out drinking snake venom. Yuck.");
					trainer[3].setDirection(270);
					trainer[3].setViewRange(0);
				}
				break;
			case J_Inc_Radio_Tower:
				tileSet = INDOORS;
				widthHeight.x = 18;
				widthHeight.y = 10;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Jincradiotower.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/house.mid");
				bgm=JApplet.newAudioClip(url);
				
				numTrainers=6;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				
				trainer[0]=new Trainer(Trainer.TrainerType.ENGINEER,"Hans",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(15,1);
				//trainer[0].setPreMessage("");
				trainer[0].setPostMessage("Sorry, but we've banned everyone from our broadcasting tower in case Team Java were to break in.");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);
				
				trainer[1]=new Trainer(Trainer.TrainerType.HACKER,"Plant Dude",Pokemon.Species.CATERPIE,5);
				trainer[1].setHostile(false);
				trainer[1].setLocation(10,1);
				//trainer[0].setPreMessage("");
				trainer[1].setPostMessage("These plants are so pretty!");
				trainer[1].setDirection(180);
				trainer[1].setViewRange(0);
				
				trainer[2]=new Trainer(Trainer.TrainerType.BABE,"Courtney",Pokemon.Species.CATERPIE,5);
				trainer[2].setHostile(false);
				trainer[2].setLocation(3,4);
				//trainer[0].setPreMessage("");
				trainer[2].setPostMessage("Welcome to the J.Inc Radio Tower! Sorry we aren't allowing anyone to be above this floor.");
				trainer[2].setDirection(270);
				trainer[2].setViewRange(0);
				
				trainer[3]=new Trainer(Trainer.TrainerType.CYPHER,"Janet",Pokemon.Species.CATERPIE,5);
				trainer[3].setHostile(false);
				trainer[3].setLocation(0,2);
				//trainer[0].setPreMessage("");
				trainer[3].setPostMessage("What girls can't use computers?");
				trainer[3].setDirection(90);
				trainer[3].setViewRange(0);
				
				trainer[4]=new Trainer(Trainer.TrainerType.BABE,"Janet",Pokemon.Species.CATERPIE,5);
				trainer[4].setHostile(false);
				trainer[4].setLocation(8,7);
				//trainer[0].setPreMessage("");
				trainer[4].setPostMessage("What girls can't use computers?");
				trainer[4].setDirection(90);
				trainer[4].setViewRange(0);
				
				trainer[5]=new Trainer(Trainer.TrainerType.BABE,"Janet",Pokemon.Species.CATERPIE,5);
				trainer[5].setHostile(false);
				trainer[5].setLocation(12,7);
				//trainer[0].setPreMessage("");
				trainer[5].setPostMessage("What girls can't use computers?");
				trainer[5].setDirection(90);
				trainer[5].setViewRange(0);
				
				break;
			case Intville:
				tileSet = OUTDOORS;
				widthHeight.x = 26;
				widthHeight.y = 26;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Intville.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/10_peachcream_city.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=4;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.NEWB,"Failsauce",Pokemon.Species.PIDGEY,Pokemon.Species.RATTATA,5);
				trainer[0].setLocation(6,9);
				trainer[0].setHostile(false);
				trainer[0].postBattleMSG = "You stupid hacker, your not supposed to be here!";

				trainer[1]=new Trainer(Trainer.TrainerType.DEBUGGER,"Cameron",Pokemon.Species.PIDGEY,Pokemon.Species.RATTATA,5);
				trainer[1].setLocation(14,12);
				trainer[1].setDirection(180);
				trainer[1].setHostile(false);
				trainer[1].postBattleMSG = "Dude, how the heck do I remove this?";

				trainer[2]=new Trainer(Trainer.TrainerType.PRODIGY,"Gold",Pokemon.Species.PIDGEY,Pokemon.Species.RATTATA,5);
				trainer[2].setLocation(13,8);
				trainer[2].setDirection(90);
				trainer[2].setHostile(false);
				trainer[2].postBattleMSG = "I can't get in my house?";

				trainer[3]=new Trainer(Trainer.TrainerType.ENGINEER,"Trollface",Pokemon.Species.PIDGEY,Pokemon.Species.RATTATA,5);
				trainer[3].setLocation(3,9);
				trainer[3].setDirection(270);
				trainer[3].setHostile(false);
				trainer[3].postBattleMSG = "Wow, a gold and silver rip off. How freaking unique.";
				loadItemData();
				break;
			case H_Border:
				tileSet = INDOORS;
				widthHeight.x = 10;
				widthHeight.y = 8;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("HBorder.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=1;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.ENGINEER,"Jeffery",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(6,2);
				//trainer[0].setPreMessage("");
				trainer[0].setPostMessage("Safe Travels!");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);
				loadItemData();
				break;
			case V_Border:
				tileSet = INDOORS;
				widthHeight.x = 10;
				widthHeight.y = 8;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("VBorder.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=2;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.ENGINEER,"Jeffery",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(0,3);
				//trainer[0].setPreMessage("");
				trainer[0].setPostMessage("Safe Travels!");
				trainer[0].setDirection(0);
				trainer[0].setViewRange(0);

				switch(houseInt)
				{
					case 0:
						trainer[1]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Percy",Pokemon.Species.CATERPIE,5);
						trainer[1].setHostile(false);
						trainer[1].setLocation(7,1);
						//trainer[0].setPreMessage("");
						trainer[1].setPostMessage("You're going to need a BICYCLE in order to go up to Mount Java!");
						trainer[1].setDirection(270);
						trainer[1].setViewRange(0);
						break;
					case 1:
						trainer[1]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Donny",Pokemon.Species.CATERPIE,5);
						trainer[1].setHostile(false);
						trainer[1].setLocation(7,1);
						//trainer[0].setPreMessage("");
						trainer[1].setPostMessage("I don't think I'm supposed to have my bike in here...");
						trainer[1].setDirection(270);
						trainer[1].setViewRange(0);
						break;
					case 2:
						trainer[1]=new Trainer(Trainer.TrainerType.HACKER,"Ronald",Pokemon.Species.CATERPIE,5);
						trainer[1].setHostile(false);
						trainer[1].setLocation(7,1);
						//trainer[0].setPreMessage("");
						trainer[1].setPostMessage("There is a rumor that legendary Pokemon exist on the island.");
						trainer[1].setDirection(270);
						trainer[1].setViewRange(0);
						break;
					default:
						trainer[1]=new Trainer(Trainer.TrainerType.ENGINEER,"Pepe",Pokemon.Species.CATERPIE,5);
						trainer[1].setHostile(false);
						trainer[1].setLocation(7,1);
						//trainer[0].setPreMessage("");
						trainer[1].setPostMessage("Some people say that Babb was more powerful than Joe.");
						trainer[1].setDirection(270);
						trainer[1].setViewRange(0);
						break;
				}
				loadItemData();
				break;
			case Slipspace:
				tileSet = INDOORS;
				widthHeight.x = 20;
				widthHeight.y = 20;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Slipspace.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes.mid");
				bgm=JApplet.newAudioClip(url);
				loadItemData();
				break;
			case Route_14:
				tileSet = OUTDOORS;
				widthHeight.x = 50;
				widthHeight.y = 30;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Route14.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0d_routes4.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];
				loadItemData();
				break;
			case Hexadecimal_Forest:
				tileSet = OUTDOORS;
				widthHeight.x = 40;
				widthHeight.y = 40;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("HexForest.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0e_cave.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=5;
				trainer=new Trainer[numTrainers];
				numItems=4;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.DEBUGGER,"Moose",Pokemon.Species.WEEDLE,16);
				trainer[0].setHostile(true);
				trainer[0].setLocation(20,30);
				trainer[0].setPreMessage("I love catching bugs, but I'll take a break to battle.");
				trainer[0].setPostMessage("...Back to bug catching.");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(3);

				trainer[1]=new Trainer(Trainer.TrainerType.NEWB,"Quincy",Pokemon.Species.WEEDLE,Pokemon.Species.CATERPIE,Pokemon.Species.METAPOD,10);
				trainer[1].setHostile(true);
				trainer[1].setLocation(28,28);
				trainer[1].setPreMessage("I think I'm lost...");
				trainer[1].setPostMessage("I knew I should have just stayed home.");
				trainer[1].setDirection(0);
				trainer[1].setViewRange(2);

				trainer[2]=new Trainer(Trainer.TrainerType.CODER,"Grant",Pokemon.Species.RATTATA,Pokemon.Species.BEEDRILL,15);
				trainer[2].setHostile(true);
				trainer[2].setLocation(24,22);
				trainer[2].setPreMessage("I bet I can beat you in a battle.");
				trainer[2].setPostMessage("Darn. Now where is the exit...");
				trainer[2].setDirection(0);
				trainer[2].setViewRange(2);

				trainer[3]=new Trainer(Trainer.TrainerType.NEWB,"Kid",Pokemon.Species.RATTATA,Pokemon.Species.METAPOD,17);
				trainer[3].setHostile(true);
				trainer[3].setLocation(16,7);
				trainer[3].setPreMessage("BOOOO!!!");
				trainer[3].setPostMessage("...Did I scare you?");
				trainer[3].setDirection(90);
				trainer[3].setViewRange(4);

				trainer[4]=new Trainer(Trainer.TrainerType.HACKER,"Benji",Pokemon.Species.EKANS,Pokemon.Species.PIDGEY,14);
				trainer[4].setHostile(true);
				trainer[4].setLocation(24,4);
				trainer[4].setPreMessage("I really hate this dumb forest.");
				trainer[4].setPostMessage("I'm going home.");
				trainer[4].setDirection(180);
				trainer[4].setViewRange(3);

				mapItems[0]=new Item(Item.Type.ANTIDOTE,new Point(12,15));
				mapItems[1]=new Item(Item.Type.X_SPECIAL,new Point(16,16));
				mapItems[2]=new Item(Item.Type.PROTEIN,new Point(25,18));
				mapItems[3]=new Item(Item.Type.RARE_CANDY,new Point(22,7));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Generic_2:
				tileSet = INDOORS;
				widthHeight.x = 8;
				widthHeight.y = 8;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Generic2.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/house.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=1;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				
				switch(houseInt%5)
				{
					default:
						trainer[0]=new Trainer(Trainer.TrainerType.CODER,"Krillin",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("How can Joe afford all of these houses?");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 0:
						trainer[0]=new Trainer(Trainer.TrainerType.CYPHER,"Torrie",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("Do you always just walk into random houses?");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 1:
						trainer[0]=new Trainer(Trainer.TrainerType.NEWB,"Nigel",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("I'm so sleepy, but there's no bed in here!");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 2:
						trainer[0]=new Trainer(Trainer.TrainerType.PROFESSOR,"Erwin",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("This island is a bit much on an old man like me.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 3:
						trainer[0]=new Trainer(Trainer.TrainerType.HACKER,"Bischoff",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("Joe is way too rich. I heard that he is only a kid too. So lucky.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 4:
						trainer[0]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Mr. Flashy",true);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("Take this so you can flash other people!");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
				}
				if (houseInt >= 9000)
				{
					trainer[0]=new Trainer(Trainer.TrainerType.HACKER,"Glitchmaster",Pokemon.Species.MISSINGNO,Pokemon.Species.MISSINGNO,Pokemon.Species.MISSINGNO,100);
					trainer[0].setHostile(true);
					trainer[0].setLocation(3,2);
					trainer[0].setPreMessage("I AM THE GLITCHMASTER");
					trainer[0].setPostMessage("Apparently I can also be defeated... sad day. Thought this action replay would make me stronger.");
					trainer[0].setDirection(270);
					trainer[0].setViewRange(9);
					return;
				}
				break;
			case Generic_3:
				tileSet = INDOORS;
				widthHeight.x = 8;
				widthHeight.y = 8;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Generic3.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/house.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=1;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				if(returnArea==Area.Nested_Village&&houseInt%5==0)
				{
						trainer[0]=new Trainer(Trainer.TrainerType.PROFESSOR,"Cutter",true);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("Hello sonny. I think you should take this HM. It contains CUT. Press SPACEBAR next to a small tree and your Pokemon will cut it down. It will help you go further. Whenever you feel that you are stuck on your journey, talk to everyone. Who knows what you'll find.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
				}
				else if(returnArea==Area.Mount_Java&&houseInt%5==0)
				{
						trainer[0]=new Trainer(Trainer.TrainerType.PROFESSOR,"Flyer",true);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("Hello sonny. Back in my day, I used to take to the skies with Pokemon. I want to share that with you. Here, take this HM. Press 'F' to activate the Fly Menu after teaching a Pokemon.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
				}
				else if(houseInt==14&&returnArea==Area.Route_9)
				{
						trainer[0]=new Trainer(Trainer.TrainerType.PROFESSOR,"Name Rater",true);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("Hello there lad. If the first Pokemon in your party was caught by you, I can change its nickname for you.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
				}
				else

				switch(houseInt%5)
				{
					case 3:
						trainer[0]=new Trainer(Trainer.TrainerType.CODER,"Krillin",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("How can Joe afford all of these houses?");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 2:
						trainer[0]=new Trainer(Trainer.TrainerType.CYPHER,"Torrie",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("Do you always just walk into random houses?");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 1:
						trainer[0]=new Trainer(Trainer.TrainerType.NEWB,"Nigel",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("I'm so sleepy, but there's no bed in here!");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 0:
						trainer[0]=new Trainer(Trainer.TrainerType.PROFESSOR,"Erwin",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("This island is a bit much on an old man like me.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					default:
						trainer[0]=new Trainer(Trainer.TrainerType.HACKER,"Bischoff",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("Joe is way too rich. I heard that he is only a kid too. So lucky.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
				}
				break;
			case J_Inc_Building:
				tileSet = INDOORS;
				widthHeight.x = 100;
				widthHeight.y = 100;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Jinc.tilemap");
				url=JokemonDriver.class.getResource("titlemusic.mid");
				bgm=JApplet.newAudioClip(url);
				numTrainers=13;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Slave",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(14,23);
				//trainer[0].setPreMessage("");
				trainer[0].setPostMessage("This is J Inc's building. Find a seat and press spacebar!.");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);

				trainer[1]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Slave too!",Pokemon.Species.CATERPIE,5);
				trainer[1].setHostile(false);
				trainer[1].setLocation(13,38);
				//trainer[1].setPreMessage("");
				trainer[1].setPostMessage("No newbs allowed up here man.");
				trainer[1].setDirection(270);
				trainer[1].setViewRange(0);
				
				trainer[2]=new Trainer(Trainer.TrainerType.ELITE,"Patches",Pokemon.Species.CATERPIE,5);
				trainer[2].setHostile(false);
				trainer[2].setLocation(13,45);
				//trainer[1].setPreMessage("");
				trainer[2].setPostMessage("Tron is so much better than this game. You should play it. NOW!");
				trainer[2].setDirection(0);
				trainer[2].setViewRange(0);
				
				trainer[3]=new Trainer(Trainer.TrainerType.HACKER,"John",Pokemon.Species.CATERPIE,5);
				trainer[3].setHostile(false);
				trainer[3].setLocation(10,43);
				//trainer[1].setPreMessage("");
				trainer[3].setPostMessage("Dang it, I can't hack into this.");
				trainer[3].setDirection(180);
				trainer[3].setViewRange(0);
				
				trainer[4]=new Trainer(Trainer.TrainerType.NEWB,"Beta Tester",Pokemon.Species.CATERPIE,5);
				trainer[4].setHostile(false);
				trainer[4].setLocation(7,45);
				//trainer[1].setPreMessage("");
				trainer[4].setPostMessage("Exploiting glitches before the game is even made is fun and annoying to the developers!");
				trainer[4].setDirection(0);
				trainer[4].setViewRange(0);
				
				trainer[5]=new Trainer(Trainer.TrainerType.ELITE,"Camtendo",Pokemon.Species.CATERPIE,5);
				trainer[5].setHostile(false);
				trainer[5].setLocation(8,26);
				//trainer[1].setPreMessage("");
				trainer[5].setPostMessage("It's so awesome that we finally got this game made!");
				trainer[5].setDirection(90);
				trainer[5].setViewRange(0);
				
				trainer[6]=new Trainer(Trainer.TrainerType.ELITE,"Justinian",Pokemon.Species.CATERPIE,5);
				trainer[6].setHostile(false);
				trainer[6].setLocation(8,29);
				//trainer[1].setPreMessage("");
				trainer[6].setPostMessage("I programmed our AI's to fight in the Elite 4 without us! Pretty genious huh?");
				trainer[6].setDirection(90);
				trainer[6].setViewRange(0);
				
				trainer[7]=new Trainer(Trainer.TrainerType.BABE,"Natalie",Pokemon.Species.CATERPIE,5);
				trainer[7].setHostile(false);
				trainer[7].setLocation(7,29);
				//trainer[1].setPreMessage("");
				trainer[7].setPostMessage("Justinian is always busy programming, but when he is I study Eevee's. Isn't he amazing?");
				trainer[7].setDirection(0);
				trainer[7].setViewRange(0);
				
				trainer[8]=new Trainer(Trainer.TrainerType.ELITE,"WasabiSauce",Pokemon.Species.CATERPIE,5);
				trainer[8].setHostile(false);
				trainer[8].setLocation(14,29);
				//trainer[1].setPreMessage("");
				trainer[8].setPostMessage("Why is Patches so obsessed with Tron?");
				trainer[8].setDirection(90);
				trainer[8].setViewRange(0);
				
				trainer[9]=new Trainer(Trainer.TrainerType.BABE,"WasabiSauce",Pokemon.Species.CATERPIE,5);
				trainer[9].setHostile(false);
				trainer[9].setLocation(10,8);
				//trainer[1].setPreMessage("");
				trainer[9].setPostMessage("Why is Patches so obsessed with Tron?");
				trainer[9].setDirection(270);
				trainer[9].setViewRange(0);
				
				trainer[10]=new Trainer(Trainer.TrainerType.BABE,"Nicole",Pokemon.Species.CATERPIE,5);
				trainer[10].setHostile(false);
				trainer[10].setLocation(6,11);
				//trainer[1].setPreMessage("");
				trainer[10].setPostMessage("Hello! Welcome to the J.Inc tower! Home and workplace of all the members of J.Inc!");
				trainer[10].setDirection(0);
				trainer[10].setViewRange(0);
				
				trainer[11]=new Trainer(Trainer.TrainerType.NEWB,"Nazi",Pokemon.Species.CATERPIE,5);
				trainer[11].setHostile(false);
				trainer[11].setLocation(15,11);
				//trainer[1].setPreMessage("");
				trainer[11].setPostMessage("ICH HABE EINE KATZE IN MEINE HOSE!");
				trainer[11].setDirection(0);
				trainer[11].setViewRange(0);
				
				trainer[12]=new Trainer(Trainer.TrainerType.DEBUGGER,"Hobo",Pokemon.Species.CATERPIE,5);
				trainer[12].setHostile(false);
				trainer[12].setLocation(15,10);
				//trainer[1].setPreMessage("");
				trainer[12].setPostMessage("This guy is obviously a Nazi since he's speaking German!");
				trainer[12].setDirection(270);
				trainer[12].setViewRange(0);


				loadItemData();
				break;
			case Generic_4:
				tileSet = INDOORS;
				widthHeight.x = 8;
				widthHeight.y = 8;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Generic4.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/house.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=1;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				if(returnArea==Area.Villa_Del_Joe&&houseInt%5==0)
				{
					trainer[0]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Bike Enthusiast",true);
					trainer[0].setHostile(false);
					trainer[0].setLocation(3,2);
					//trainer[0].setPreMessage("");
					trainer[0].setPostMessage("Vroom vrooom!!! I love bicycles! If you've got 3 badges, you've got a bike too, dude!");
					trainer[0].setDirection(270);
					trainer[0].setViewRange(0);
				}
				else
				switch(houseInt%5)
				{
					default:
						trainer[0]=new Trainer(Trainer.TrainerType.CODER,"Kobe",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("I play basketball more than Pokemon.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 0:
						trainer[0]=new Trainer(Trainer.TrainerType.NEWB,"Lebron",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("I think that CATERPIE is my favorite Pokemon.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 1:
						trainer[0]=new Trainer(Trainer.TrainerType.NEWB,"Stewie",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("I heard that Joe has a shady past. Is that true?");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 2:
						trainer[0]=new Trainer(Trainer.TrainerType.PROFESSOR,"Locke",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("If you give a Pokemon some vitamins, it will grow faster.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 3:
						trainer[0]=new Trainer(Trainer.TrainerType.HACKER,"Klark",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("My stomach hurts...");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
				}
				break;
			case Generic_5:
				tileSet = INDOORS;
				widthHeight.x = 8;
				widthHeight.y = 8;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Generic5.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/house.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=1;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				if(returnArea==Area.Villa_Del_Joe&&houseInt%5==0)
				{
					trainer[0]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Bike Enthusiast",true);
					trainer[0].setHostile(false);
					trainer[0].setLocation(3,2);
					//trainer[0].setPreMessage("");
					trainer[0].setPostMessage("Vroom vrooom!!! I love bicycles! If you've got 3 badges, you've got a bike too, dude!");
					trainer[0].setDirection(270);
					trainer[0].setViewRange(0);
				}
				else
				switch(houseInt%5)
				{
					default:
						trainer[0]=new Trainer(Trainer.TrainerType.CODER,"Rondell",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("I think I saw an ICE bird flying around somewhere.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 0:
						trainer[0]=new Trainer(Trainer.TrainerType.NEWB,"Eustace",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("Hey dude, get outta here!");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 1:
						trainer[0]=new Trainer(Trainer.TrainerType.NEWB,"Jimmeh",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("Joe is cooler than you dude.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 2:
						trainer[0]=new Trainer(Trainer.TrainerType.PROFESSOR,"Binji",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("Somewhere, there is a man who is crazy about bicycles.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 3:
						trainer[0]=new Trainer(Trainer.TrainerType.HACKER,"Koopa",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("I heard that there is a Battle Tower somewhere.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
				}
				break;
			case Generic_6:
				tileSet = INDOORS;
				widthHeight.x = 8;
				widthHeight.y = 8;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Generic6.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/house.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=1;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				if(returnArea==Area.Villa_Del_Joe&&houseInt%5==0)
				{
					trainer[0]=new Trainer(Trainer.TrainerType.NERDY_BIKER,"Bike Enthusiast",true);
					trainer[0].setHostile(false);
					trainer[0].setLocation(3,2);
					//trainer[0].setPreMessage("");
					trainer[0].setPostMessage("Vroom vrooom!!! I love bicycles! If you've got 3 badges, you've got a bike too, dude!");
					trainer[0].setDirection(270);
					trainer[0].setViewRange(0);
				}
				else
				switch(houseInt%5)
				{
					default:
						trainer[0]=new Trainer(Trainer.TrainerType.CODER,"Kris",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("I think I need a new Pokemon.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 0:
						trainer[0]=new Trainer(Trainer.TrainerType.NEWB,"Brucey",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("Babb is an esteemed programmer too!");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 1:
						trainer[0]=new Trainer(Trainer.TrainerType.NEWB,"Yancy",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("I think I want to go home.");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 2:
						trainer[0]=new Trainer(Trainer.TrainerType.PROFESSOR,"Sheruf",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("Do you see the door? Get out!");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
					case 3:
						trainer[0]=new Trainer(Trainer.TrainerType.HACKER,"Hans",Pokemon.Species.CATERPIE,5);
						trainer[0].setHostile(false);
						trainer[0].setLocation(3,2);
						//trainer[0].setPreMessage("");
						trainer[0].setPostMessage("It never rains here!");
						trainer[0].setDirection(270);
						trainer[0].setViewRange(0);
						break;
				}
				break;
			case Public_Cave:
				tileSet = INDOORS;
				widthHeight.x = 100;
				widthHeight.y = 100;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Public.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0e_cave.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=2;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				mapItems[0]=new Item(Item.Type.REDBULL,new Point(11,60));
				mapItems[1]=new Item(Item.Type.MOUNTAIN_DEW,new Point(12,60));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}

				break;
			case Class_Cave:
				tileSet = INDOORS;
				widthHeight.x = 30;
				widthHeight.y = 30;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Class.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0e_cave.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				loadItemData();
				break;
			case Moltres_Cave:
				tileSet = INDOORS;
				widthHeight.x = 20;
				widthHeight.y = 20;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Moltres.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0e_cave.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				loadItemData();
				break;
			case Articuno_Cave:
				tileSet = INDOORS;
				widthHeight.x = 20;
				widthHeight.y = 20;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("ArticunoCave.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0e_cave.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				loadItemData();
				break;
			case Primal_Cave:
				tileSet = INDOORS;
				widthHeight.x = 20;
				widthHeight.y = 20;
				currentArea = new int[widthHeight.x][widthHeight.y];
				if(VERSION.equals("Peaches"))
					createMap("PrimalP.tilemap");
				else
					createMap("PrimalC.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0e_cave.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				loadItemData();
				break;
			case Java_Cave:
				tileSet = INDOORS;
				widthHeight.x = 30;
				widthHeight.y = 30;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("JavaCave.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0e_cave.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				loadItemData();
				break;
			case Diglett_Cave:
				tileSet = INDOORS;
				widthHeight.x = 25;
				widthHeight.y = 80;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("DiglettCave.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0e_cave.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				loadItemData();
				break;
			case Victory_Road_Cave:
				tileSet = INDOORS;
				widthHeight.x = 90;
				widthHeight.y = 90;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("VictoryRoadCave.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0e_cave.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=10;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.ENGINEER,"Samuel",Pokemon.Species.CHARIZARD,Pokemon.Species.RAICHU,Pokemon.Species.PIDGEOTTO,Pokemon.Species.GOLDUCK,50);
				trainer[0].setHostile(true);
				trainer[0].setLocation(14,73);
				trainer[0].setPreMessage("What up right now? Are you finna finna?");
				trainer[0].setPostMessage("...Gonna sit up here and be rude to me?!");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(1);

				trainer[1]=new Trainer(Trainer.TrainerType.KEYBOARDER,"Jack",Pokemon.Species.HAUNTER,Pokemon.Species.ARCANINE,Pokemon.Species.KADABRA,Pokemon.Species.ARBOK,50);
				trainer[1].setHostile(true);
				trainer[1].setLocation(9,61);
				trainer[1].setPreMessage("Hey! Listen!");
				trainer[1].setPostMessage("Snake? Snake?! Snaaake!?!");
				trainer[1].setDirection(270);
				trainer[1].setViewRange(2);

				trainer[2]=new Trainer(Trainer.TrainerType.CODER,"Jeff",Pokemon.Species.ONIX,Pokemon.Species.HYPNO,Pokemon.Species.DRAGONAIR,Pokemon.Species.DRAGONITE,56);
				trainer[2].setHostile(true);
				trainer[2].setLocation(26,72);
				trainer[2].setPreMessage("Cower in fear at my Pokemon's power!");
				trainer[2].setPostMessage("It seems I underestimated your power...");
				trainer[2].setDirection(180);
				trainer[2].setViewRange(1);

				trainer[3]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Triixster",Pokemon.Species.CHARMELEON,Pokemon.Species.WARTORTLE,Pokemon.Species.IVYSAUR,50);
				trainer[3].setHostile(true);
				trainer[3].setLocation(31,62);
				trainer[3].setPreMessage("Whatever.");
				trainer[3].setPostMessage("Whatever.");
				trainer[3].setDirection(180);
				trainer[3].setViewRange(2);

				trainer[4]=new Trainer(Trainer.TrainerType.CODER,"Cloward",Pokemon.Species.NIDORINO,Pokemon.Species.PIDGEOT,Pokemon.Species.ARCANINE,Pokemon.Species.GYARADOS,50);
				trainer[4].setHostile(true);
				trainer[4].setLocation(22,49);
				trainer[4].setPreMessage("Get ready for the fight of your life.");
				trainer[4].setPostMessage("You beat me! Well some people get lucky.");
				trainer[4].setDirection(270);
				trainer[4].setViewRange(1);

				trainer[5]=new Trainer(Trainer.TrainerType.HACKER,"Devin F.",Pokemon.Species.MACHAMP,Pokemon.Species.GRAVELER,Pokemon.Species.MACHOKE,Pokemon.Species.GYARADOS,50);
				trainer[5].setHostile(true);
				trainer[5].setLocation(20,42);
				trainer[5].setPreMessage("Get ready to lose.");
				trainer[5].setPostMessage("I know we're in a cave, but the sun was in my eyes.");
				trainer[5].setDirection(270);
				trainer[5].setViewRange(1);

				trainer[6]=new Trainer(Trainer.TrainerType.CYPHER,"Bigge",Pokemon.Species.CLOYSTER,Pokemon.Species.CLEFAIRY,Pokemon.Species.NINETALES,Pokemon.Species.GASTLY,50);
				trainer[6].setHostile(true);
				trainer[6].setLocation(23,30);
				trainer[6].setPreMessage("Feel the dent in my head!");
				trainer[6].setPostMessage("...Well this is awkward, but you're still going to touch it!");
				trainer[6].setDirection(270);
				trainer[6].setViewRange(1);

				trainer[7]=new Trainer(Trainer.TrainerType.CODER,"Stevenson",Pokemon.Species.CUBONE,Pokemon.Species.MAROWAK,Pokemon.Species.KANGASKHAN,50);
				trainer[7].setHostile(true);
				trainer[7].setLocation(43,31);
				trainer[7].setPreMessage("Feel the rage!");
				trainer[7].setPostMessage("Maybe they needed more juice...");
				trainer[7].setDirection(90);
				trainer[7].setViewRange(1);

				trainer[8]=new Trainer(Trainer.TrainerType.ENGINEER,"Grey",Pokemon.Species.KABUTOPS,Pokemon.Species.HITMONLEE,Pokemon.Species.HAUNTER,Pokemon.Species.GYARADOS,50);
				trainer[8].setHostile(true);
				trainer[8].setLocation(43,22);
				trainer[8].setPreMessage("Leave.");
				trainer[8].setPostMessage("How?");
				trainer[8].setDirection(0);
				trainer[8].setViewRange(1);

				trainer[9]=new Trainer(Trainer.TrainerType.PROGRAMMER,"Scoticus",Pokemon.Species.IVYSAUR,Pokemon.Species.GROWLITHE,Pokemon.Species.CUBONE,Pokemon.Species.MAGNETON,Pokemon.Species.TAUROS,Pokemon.Species.AERODACTYL,55);
				trainer[9].setHostile(true);
				trainer[9].setLocation(36,19);
				trainer[9].setPreMessage("I've got 99 problems but a glitch ain't one!");
				trainer[9].setPostMessage("Well...now I have a bug to report...");
				trainer[9].setDirection(180);
				trainer[9].setViewRange(1);

				loadItemData();
				break;
			case Mountain_Dew_Paradise:
				tileSet = INDOORS;
				widthHeight.x = 20;
				widthHeight.y = 20;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Mountain_Dew_Paradise.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0e_cave.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=60;
				mapItems=new Item[numItems];

				int index=0;
				for(int i=0; i<6; i++)
				{
					for(int j=0; j<10; j++)
					{
						mapItems[index]=new Item(Item.Type.MOUNTAIN_DEW,new Point(9+i,5+j));
						index++;
					}
				}

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Rocket_Right_Tower:
				tileSet = INDOORS;
				widthHeight.x = 40;
				widthHeight.y = 40;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Rocket_Right_Tower.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0a_java_hideout.mid");
				bgm=JApplet.newAudioClip(url);

				if(!objectiveComplete[9])
				{
					numTrainers=5;
					trainer=new Trainer[numTrainers];

					trainer[0]=new Trainer(Trainer.TrainerType.JAVA,"GRUNT",Pokemon.Species.RATICATE,Pokemon.Species.EKANS,Pokemon.Species.KOFFING,30);
				trainer[0].setHostile(true);
				trainer[0].setLocation(17,4);
				trainer[0].setPreMessage("You snot nosed brat! Where was security?");
				trainer[0].setPostMessage("Don't get cocky. Our Boss will beat you!");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(2);

				trainer[1]=new Trainer(Trainer.TrainerType.JAVA,"GRUNT",Pokemon.Species.WEEZING,Pokemon.Species.RATTATA,40);
				trainer[1].setHostile(true);
				trainer[1].setLocation(12,32);
				trainer[1].setPreMessage("Why are you here little boy?");
				trainer[1].setPostMessage("Skedaddle before you are slaughtered!");
				trainer[1].setDirection(90);
				trainer[1].setViewRange(3);

				trainer[2]=new Trainer(Trainer.TrainerType.JAVA,"GRUNT",Pokemon.Species.MUK,Pokemon.Species.GRIMER,50);
				trainer[2].setHostile(true);
				trainer[2].setLocation(8,26);
				trainer[2].setPreMessage("Joe is having his island taken back!");
				trainer[2].setPostMessage("You'll never get out of here alive maggot!");
				trainer[2].setDirection(90);
				trainer[2].setViewRange(4);

				trainer[3]=new Trainer(Trainer.TrainerType.JAVA,"Karl",Pokemon.Species.MUK,Pokemon.Species.MUK,Pokemon.Species.MUK,40);
				trainer[3].setHostile(true);
				trainer[3].setLocation(7,4);
				trainer[3].setPreMessage("My Pokemon backwards are too cool.");
				trainer[3].setPostMessage("Oh no. I've made a mess...");
				trainer[3].setDirection(180);
				trainer[3].setViewRange(3);

				trainer[4]=new Trainer(Trainer.TrainerType.JAVA,"ELITE",Pokemon.Species.RATICATE,Pokemon.Species.WEEZING,50);
				trainer[4].setHostile(true);
				trainer[4].setLocation(11,13);
				trainer[4].setPreMessage("You're not getting out of this tower kiddy!");
				trainer[4].setPostMessage("Curses! No matter. Our boss knows you are coming!");
				trainer[4].setDirection(180);
				trainer[4].setViewRange(2);
				}
				else
				{
					numTrainers=0;
					trainer=new Trainer[numTrainers];
				}
				numItems=2;
				mapItems=new Item[numItems];

				mapItems[0]=new Item(Item.Type.MAX_REVIVE,new Point(12,6));
				mapItems[1]=new Item(Item.Type.SUPER_POTION,new Point(4,30));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}
				break;
			case Rocket_Left_Tower:
				tileSet = INDOORS;
				widthHeight.x = 40;
				widthHeight.y = 80;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Rocket_Left_Tower.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0a_java_hideout.mid");
				bgm=JApplet.newAudioClip(url);

				if(!objectiveComplete[9])
				{
					numTrainers=5;
					trainer=new Trainer[numTrainers];

					trainer[0]=new Trainer(Trainer.TrainerType.JAVA,"GRUNT",Pokemon.Species.RATICATE,Pokemon.Species.ARBOK,Pokemon.Species.KOFFING,30);
				trainer[0].setHostile(true);
				trainer[0].setLocation(5,12);
				trainer[0].setPreMessage("Joe was once a member of Team Java!");
				trainer[0].setPostMessage("He quit us! Now our boss wants this island!");
				trainer[0].setDirection(0);
				trainer[0].setViewRange(2);

				trainer[1]=new Trainer(Trainer.TrainerType.JAVA,"GRUNT",Pokemon.Species.WEEZING,Pokemon.Species.RATICATE,40);
				trainer[1].setHostile(true);
				trainer[1].setLocation(13,31);
				trainer[1].setPreMessage("Would you like to join us? Too bad!");
				trainer[1].setPostMessage("Pokemon are meant to be sold!!");
				trainer[1].setDirection(180);
				trainer[1].setViewRange(5);

				trainer[2]=new Trainer(Trainer.TrainerType.JAVA,"GRUNT",Pokemon.Species.MUK,Pokemon.Species.GRIMER,40);
				trainer[2].setHostile(true);
				trainer[2].setLocation(8,45);
				trainer[2].setPreMessage("Joe was soft! He isn't good enough for Java!");
				trainer[2].setPostMessage("You're not good enough either!!!");
				trainer[2].setDirection(180);
				trainer[2].setViewRange(1);

				trainer[3]=new Trainer(Trainer.TrainerType.JAVA,"GRUNT",Pokemon.Species.RAPIDASH,Pokemon.Species.MUK,Pokemon.Species.RATICATE,40);
				trainer[3].setHostile(true);
				trainer[3].setLocation(5,51);
				trainer[3].setPreMessage("You're not getting to Joe the traitor!");
				trainer[3].setPostMessage("Foolish little boy!");
				trainer[3].setDirection(270);
				trainer[3].setViewRange(2);

				trainer[4]=new Trainer(Trainer.TrainerType.JAVA,"ELITE",Pokemon.Species.MUK,Pokemon.Species.WEEZING,50);
				trainer[4].setHostile(true);
				trainer[4].setLocation(11,4);
				trainer[4].setPreMessage("Our boss will inflict pain on his enemies!");
				trainer[4].setPostMessage("You can't save Joe!");
				trainer[4].setDirection(270);
				trainer[4].setViewRange(1);
				}
				else
				{
					numTrainers=0;
					trainer=new Trainer[numTrainers];
				}
				numItems=2;
				mapItems=new Item[numItems];

				mapItems[0]=new Item(Item.Type.FULL_RESTORE,new Point(9,11));
				mapItems[1]=new Item(Item.Type.ELIXER,new Point(5,49));

				loadItemData();
				for(int i=0; i<foundItem.length; i++)
				{
					mapItems[i].found=foundItem[i];
				}

				loadItemData();
				break;
			case Rocket_Central_Tower:
				tileSet = INDOORS;
				widthHeight.x = 80;
				widthHeight.y = 80;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Rocket_Central_Tower.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0a_java_hideout.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=1;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				if(!objectiveComplete[9])
				{
					trainer[0]=new Trainer(Trainer.TrainerType.JAVA,"BOSS",Pokemon.Species.RATICATE,Pokemon.Species.ARBOK,Pokemon.Species.WEEZING,Pokemon.Species.MUK,60);
					trainer[0].setHostile(true);
					trainer[0].setLocation(12,40);
					trainer[0].setPreMessage("You repugnant slime. I'm here for Joe. He betrayed Team Java. He called the police on us for selling Pokemon. He claimed that our business of selling Pokemon is evil. Then he bought this island. Well let me tell you something, NO ONE will stop my revenge!");
					trainer[0].setPostMessage("INSOLENT SWINE! How dare you beat me! Fine. I see the error in our business. Perhaps we must change our ways. Cursed Joe. He always was good at making money. We'll unblock the building to Joe now. I'm sorry. Take this MASTER BALL too. Just because.");
					trainer[0].setDirection(270);
					trainer[0].setViewRange(0);
				}
				else
				{
					numTrainers=0;
					trainer=new Trainer[numTrainers];
				}
				loadItemData();
				break;
			case Hotel_Lobby:
				tileSet = INDOORS;
				widthHeight.x = 12;
				widthHeight.y = 12;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Lobby.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/house.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=4;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				foundItem=new boolean[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.BABE,"Melissa",Pokemon.Species.CATERPIE,5);
				trainer[0].setHostile(false);
				trainer[0].setLocation(2,8);
				//trainer[0].setPreMessage("");
				trainer[0].setPostMessage("They aren't taking reservations anymore!");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);

				trainer[1]=new Trainer(Trainer.TrainerType.ENGINEER,"Security Dude",Pokemon.Species.CATERPIE,5);
				trainer[1].setHostile(false);
				trainer[1].setLocation(0,1);
				trainer[1].setPostMessage("Sorry, you aren't allowed up here.");
				trainer[1].setDirection(270);
				trainer[1].setViewRange(0);

				trainer[2]=new Trainer(Trainer.TrainerType.ENGINEER,"Security Dude",Pokemon.Species.CATERPIE,5);
				trainer[2].setHostile(false);
				trainer[2].setLocation(11,1);
				trainer[2].setPostMessage("Sorry, you aren't allowed up here.");
				trainer[2].setDirection(270);
				trainer[2].setViewRange(0);

				trainer[3]=new Trainer(Trainer.TrainerType.CYPHER,"Security Dude",Pokemon.Species.CATERPIE,5);
				trainer[3].setHostile(false);
				trainer[3].setLocation(6,2);
				trainer[3].setPostMessage("HACKZZZZZ!!!!!");
				trainer[3].setDirection(90);
				trainer[3].setViewRange(0);

				loadItemData();
				break;
			case Lighthouse:
				tileSet = INDOORS;
				widthHeight.x = 12;
				widthHeight.y = 12;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Lighthouse.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/1_stringville.mid");
				bgm=JApplet.newAudioClip(url);
				break;
			case Lighthouse_F2:
				tileSet = INDOORS;
				widthHeight.x = 12;
				widthHeight.y = 12;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Lighthouse2.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/1_stringville.mid");
				bgm=JApplet.newAudioClip(url);
				break;
			case MegaMart:
				tileSet = INDOORS;
				widthHeight.x = 80;
				widthHeight.y = 80;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("MegaMart.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/1_stringville.mid");
				bgm=JApplet.newAudioClip(url);
				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				loadItemData();
				break;
			case Power_Plant:
				tileSet = INDOORS;
				widthHeight.x = 25;
				widthHeight.y = 20;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Power_Plant.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/1_stringville.mid");
				bgm=JApplet.newAudioClip(url);
				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				loadItemData();
				break;
			case Challenge_Cave:
				tileSet = INDOORS;
				widthHeight.x = 120;
				widthHeight.y = 120;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Challenge_Cave.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/0e_cave.mid");
				bgm=JApplet.newAudioClip(url);
				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				loadItemData();
				break;
			case Elite_4:
				tileSet = INDOORS;
				widthHeight.x = 120;
				widthHeight.y = 120;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Elite_4.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/9_hall_of_champions.mid");
				bgm=JApplet.newAudioClip(url);

				numTrainers=5;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];

				trainer[0]=new Trainer(Trainer.TrainerType.ELITE,"Camtendo",Pokemon.Species.STARMIE,Pokemon.Species.SLOWBRO,Pokemon.Species.CLOYSTER,Pokemon.Species.VAPOREON,Pokemon.Species.RHYDON,Pokemon.Species.LAPRAS,60);
				trainer[0].setHostile(true);
				trainer[0].setLocation(27,86);
				trainer[0].setPreMessage("Howdy there "+name+"! You've come quite a long way, haven't you? How are you enjoying the island so far? Hopefully you have. My name is Camtendo. I'm one of Joe's top trainers. Do you have what it takes to win 4 battles in a row to get to Joe? Let's find out shall we?");
				trainer[0].setPostMessage("I figured as much. You're definitely worth your weight in gold. Continue on to the next trainer! Be careful, the door locks behind you!");
				trainer[0].setDirection(270);
				trainer[0].setViewRange(0);

				trainer[1]=new Trainer(Trainer.TrainerType.ELITE,"WasabiSause",Pokemon.Species.ALAKAZAM,Pokemon.Species.ALAKAZAM,Pokemon.Species.EXEGGUTOR,Pokemon.Species.HYPNO,Pokemon.Species.ALAKAZAM,Pokemon.Species.ALAKAZAM,64);
				trainer[1].setHostile(true);
				trainer[1].setLocation(27,62);
				trainer[1].setPreMessage("Something I don't know. I haven't really thought of what to say.");
				trainer[1].setPostMessage("Oh darn. Kajigga!");
				trainer[1].setDirection(270);
				trainer[1].setViewRange(0);

				trainer[2]=new Trainer(Trainer.TrainerType.ELITE,"Patches",Pokemon.Species.CHARIZARD,Pokemon.Species.GYARADOS,Pokemon.Species.BUTTERFREE,Pokemon.Species.DRAGONITE,Pokemon.Species.PIDGEOT,Pokemon.Species.DODRIO,68);
				trainer[2].setHostile(true);
				trainer[2].setLocation(27,31);
				trainer[2].setPreMessage("Have you played Tron yet? I made it myself!");
				trainer[2].setPostMessage("Crap now go play Tron, it's so much better than this game.");
				trainer[2].setDirection(270);
				trainer[2].setViewRange(0);

				trainer[3]=new Trainer(Trainer.TrainerType.ELITE,"Justinian",Pokemon.Species.CHARIZARD,Pokemon.Species.GYARADOS,Pokemon.Species.DRAGONITE,Pokemon.Species.DRAGONITE,Pokemon.Species.DRAGONAIR,Pokemon.Species.ARCANINE,70);
				trainer[3].setHostile(true);
				trainer[3].setLocation(28,8);
				trainer[3].setPreMessage("How did you even get to me? Did you hack or something?");
				trainer[3].setPostMessage("Well... good luck against Joe. You'll need it.");
				trainer[3].setDirection(270);
				trainer[3].setViewRange(0);

				trainer[4]=new Trainer(Trainer.TrainerType.ELITE,"Joe",Pokemon.Species.ALAKAZAM,Pokemon.Species.STARMIE,Pokemon.Species.DRAGONITE,Pokemon.Species.CHANSEY,Pokemon.Species.LAPRAS,Pokemon.Species.SNORLAX,75);
				trainer[4].setHostile(true);
				trainer[4].setLocation(49,10);
				trainer[4].setPreMessage("Welcome challenger! You must be the great "+name+"! I'm honored to meet you. You have done well to get this far! Your Pokemon must be really strong now. Are you ready for a battle? This will be an epic showdown! May the best trainer win!");
				trainer[4].setPostMessage("You have trumped me! Congratulations! I hearby recognize your greatness by allowing you to "+VERSION+" City! You can continue your Pokemon enlightenment there. Just move behind me, "+name+". Job well done!");
				trainer[4].setDirection(270);
				trainer[4].setViewRange(0);

				loadItemData();
				break;
			case Battle_Tower:
				tileSet = INDOORS;
				widthHeight.x = 15;
				widthHeight.y = 15;
				currentArea = new int[widthHeight.x][widthHeight.y];
				createMap("Battle_Tower.tilemap");
				url=JokemonDriver.class.getResource("Music/Locations/9_hall_of_champions.mid");
				bgm=JApplet.newAudioClip(url);
				numTrainers=0;
				trainer=new Trainer[numTrainers];
				numItems=0;
				mapItems=new Item[numItems];
				loadItemData();
				break;
		}

		if(transition)
		{
			try
			{
				repaint();
				Thread.sleep(2000);
			}
			catch(Exception e){e.printStackTrace();}
		}

		if(tileSet!=OUTDOORS)
		{
			bicycling=false;
		}
		else if((area==Area.Route_4&&location.y<74)||area==Area.Route_5)
		{
			forceBike=true;
			bicycling=true;
		}

		if(surfing)
			surfSong.loop();
		else if(bicycling&&!forceBike)
		{
			bikeSong.loop();
		}
		else
		{
			bgm.loop();
		}

		checkLegendaries();

		transition=false;
		justBeatRival=false;
		loadPokemonImages();
		System.out.println();
		System.out.println("Loaded "+area);
		System.out.println();
	}

	public void loadAudio()
	{
		URL url=JokemonDriver.class.getResource("Music/maleEncounter.mid");
		maleEncounter=JApplet.newAudioClip(url);
		url=JokemonDriver.class.getResource("Music/femaleEncounter.mid");
		femaleEncounter=JApplet.newAudioClip(url);
		url=JokemonDriver.class.getResource("Music/evilEncounter.mid");
		evilEncounter=JApplet.newAudioClip(url);
		url=JokemonDriver.class.getResource("Music/rivalEncounter.mid");
		rivalEncounter=JApplet.newAudioClip(url);
		url=JokemonDriver.class.getResource("Music/leaderEncounter.mid");
		leaderEncounter=JApplet.newAudioClip(url);
		url=JokemonDriver.class.getResource("Music/Locations/z_surfing.mid");
		surfSong=JApplet.newAudioClip(url);
		url=JokemonDriver.class.getResource("Music/Locations/z_bicycle.mid");
		bikeSong=JApplet.newAudioClip(url);
	}

	//Begins Trainer Battle
	public void trainerBattle()
	{
		direction=(trainer[trainerToFight].direction+180)%360;

		repaint();
		messageBox=new ErrorWindow();

		if(!Mechanics.isSpecialTrainer(trainer[trainerToFight]))
			messageBox.addMessage(trainer[trainerToFight].getPreMessage(),trainer[trainerToFight].getNameAndType());
		else
			messageBox.addLargeMessage(trainer[trainerToFight].getPreMessage(),trainer[trainerToFight].getNameAndType());

		messageBox.repaint();
		while(messageBox.isVisible())
		{
			try
			{
				repaint();
				Thread.sleep(10);
			}
			catch(Exception e){e.printStackTrace();}
		}

		jf.toFront();

		evilEncounter.stop();
		maleEncounter.stop();
		femaleEncounter.stop();
		rivalEncounter.stop();
		leaderEncounter.stop();

		jf.setVisible(false);

		for(int i=0; i<6; i++)
		{
			enemy[i]=trainer[trainerToFight].getPokemon(i);
		}

		BattleWindow bwindow;

		if(trainer[trainerToFight].type==Trainer.TrainerType.GYM_LEADER)
		{
			healPokemonNoPrompt();
			bwindow=new BattleWindow(partyPokemon,enemy,"GYM",trainer[trainerToFight]);
		}
		else if(trainer[trainerToFight].type==Trainer.TrainerType.BABB||trainer[trainerToFight].type==Trainer.TrainerType.ELITE)
			bwindow=new BattleWindow(partyPokemon,enemy,"SUPER",trainer[trainerToFight]);
		else if(trainer[trainerToFight].type==Trainer.TrainerType.RIVAL)
			bwindow=new BattleWindow(partyPokemon,enemy,"RIVAL",trainer[trainerToFight]);
		else
			bwindow=new BattleWindow(partyPokemon,enemy,"TRAINER",trainer[trainerToFight]);

		trainerEnc=false;
		trainer[trainerToFight].hostile=false;

		for(int i=0; i<6; i++)
		{
			partyPokemon[i]=Battle.getPokemon(i);
		}

		alreadyBattled=true;
		moving=false;

		bwindow.thread.interrupt();

		bwindow=null;
		System.gc();
		jf.setVisible(true);
		jf.toFront();

		if(surfing)
			surfSong.loop();
		else if(bicycling&&!forceBike)
			bikeSong.loop();
		else
			bgm.loop();

		int qq = 0;
		for (int i = 0; i<partyPokemon.length; i++)
		{
			if (partyPokemon[i] != null)
			qq++;
		}

		if (!Mechanics.hasRemainingPokemon(partyPokemon,qq))
		{
			transition = true;
			createCurrentArea();
			return;
		}

		messageBox=new ErrorWindow();

		if(!Mechanics.isSpecialTrainer(trainer[trainerToFight]))
			messageBox.addMessage(trainer[trainerToFight].getPostMessage(),trainer[trainerToFight].getNameAndType());
		else
			messageBox.addLargeMessage(trainer[trainerToFight].getPostMessage(),trainer[trainerToFight].getNameAndType());

		messageBox.repaint();
		while(messageBox.isVisible())
		{
			try
			{
				repaint();
				Thread.sleep(10);
			}
			catch(Exception e){e.printStackTrace();}
		}

		gameEventHandler(trainer[trainerToFight]);

		jf.toFront();
	}

	//Heals all Party Pokemon
	public void healPokemon()
	{
		repaint();
		messageBox=new ErrorWindow();
		messageBox.addMessage("Hi! I'm going to heal your Pokemon for you!","Pokemon Center");

		messageBox.repaint();
		while(messageBox.isVisible())
		{
			try
			{
				repaint();
				Thread.sleep(10);
			}
			catch(Exception e){e.printStackTrace();}
		}

		jf.toFront();

		healPokemonNoPrompt();
	}

	//Heals all Party Pokemon with no message Prompt
	public void healPokemonNoPrompt()
	{
		for(int i=0; i<partyPokemon.length; i++)
		{
			if(partyPokemon[i]!=null)
			{
				partyPokemon[i].health=partyPokemon[i].healthMax;
				partyPokemon[i].status = Pokemon.Status.OK;
				partyPokemon[i].substatus = Pokemon.Substatus.OK;
				for(int j=0; j<4; j++)
				{
					partyPokemon[i].TRUE_PP[j]=partyPokemon[i].TRUE_PPMAX[j];
				}
			}
		}

		System.out.println("Pokemon Party Healed.");
	}

	//Handles Actions Safely from main thread
	public void actionHandler()
	{
		System.out.println("Requesting action handling...");

		if (makeTheArea)
		{
			System.out.println("Constructing area via game over...");
			createCurrentArea();
			makeTheArea=false;
		}

		if(invokeBicycle)
		{
			System.out.println("Invoking Bicycle...");
			rideBike();

			invokeBicycle=false;
			performingAction=false;
			return;
		}
		
		if(invokeMap)
		{
			System.out.println("Invoking Town Map...");
			map.displayMap();
			while(map.isVisible())
			{
				try
				{
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
			
			invokeMap=false;
			performingAction=false;
			return;
		}

		if(invokeMinigame)
		{
			System.out.println("Invoking true minigame...");
			save();
			bgm.stop();
			URL url=JokemonDriver.class.getResource("Music/minigame.mid");
			AudioClip mgm=JApplet.newAudioClip(url);
			mgm.loop();

			jf.setVisible(false);

			switch(minigameInt)
			{
				case 0:
					Escape minigame=new Escape();
					while(minigame.isVisible())
					{

					}
					break;
				case 1:
					Frogger minigame2=new Frogger();
					while(minigame2.isVisible())
					{

					}
					break;
				case 2:
					Tron minigame3=new Tron();
					while(minigame3.isVisible())
					{

					}
					break;
			}

			System.exit(0);
		}

		if(flyMenu)
		{
			performingAction=false;
			useFly();
		}

		if(tileSet==OUTDOORS&&getRodType()!=null&&canFish())
		{
			System.out.println("Invoking Fishing...");
			useRod(getRodType());
			performingAction=false;
			return;
		}

		try
		{
			talk();
		}
		catch(Exception e){}

		try
		{
			pickUp();
		}
		catch(Exception e){}

		try
		{
			readSign();
		}
		catch(Exception e){}

		try
		{
			if(Inventory.hasItem(new Item(Item.Type.HM,1,1)))
			useCut();
		}
		catch(Exception e){}

		try
		{
			if(Inventory.hasItem(new Item(Item.Type.HM,1,4)))
			useStrength();
		}
		catch(Exception e){}

		//Pokemon Center
		if((area==Area.Pokecenter&&location.x==4&&location.y==3&&direction==90)||(area==Area.Elite_4&&location.x==24&&location.y==109&&direction==90))
		{
			System.out.println("Invoking Pokemon Center Healing...");

			try
			{
				healPokemon();

				repaint();
				messageBox=new ErrorWindow();
				messageBox.addMessage("All healed! Have a good day!","Pokemon Center");

				messageBox.repaint();
				while(messageBox.isVisible())
				{
					try
					{
						repaint();
						Thread.sleep(10);
					}
					catch(Exception e){e.printStackTrace();}
				}

				jf.toFront();
			}
			catch(Exception e){e.printStackTrace();}
		}

		//Battle Tower
		if((area==Area.Battle_Tower&&location.x==7&&location.y==9&&direction==90))
		{
			System.out.println("Invoking BattleTower");

			try
			{
				healPokemonNoPrompt();

				repaint();
				messageBox=new ErrorWindow();
				messageBox.addMessage("Earn money and exp! Fight until you lose!","Battle Tower");

				messageBox.repaint();
				while(messageBox.isVisible())
				{
					try
					{
						repaint();
						Thread.sleep(10);
					}
					catch(Exception e){e.printStackTrace();}
				}

				jf.toFront();
				jf.setVisible(false);

				int levels=50;
				int wins=0;

				int numPo=0;
				for(int i=0; i<6; i++)
				{
					if(partyPokemon[i]!=null)
					{
						numPo++;
					}
				}

				while(Mechanics.hasRemainingPokemon(partyPokemon,numPo))
				{
					bgm.stop();
					Trainer btTrainer=new Trainer(Trainer.TrainerType.PROGRAMMER,"Battle Tower",Pokedex.getSpecies((int)(Math.random()*151+1)),Pokedex.getSpecies((int)(Math.random()*151+1)),Pokedex.getSpecies((int)(Math.random()*151+1)),Pokedex.getSpecies((int)(Math.random()*151+1)),Pokedex.getSpecies((int)(Math.random()*151+1)),Pokedex.getSpecies((int)(Math.random()*151+1)),levels);
					for(int i=0; i<6; i++)
					{
						enemy[i]=btTrainer.getPokemon(i);
					}

					BattleWindow bwin;

					bwin=new BattleWindow(partyPokemon,enemy,"TRAINER",btTrainer);

					for(int i=0; i<6; i++)
					{
						partyPokemon[i]=Battle.getPokemon(i);
					}

					jf.setVisible(true);
					jf.toFront();
					bgm.loop();

					wins++;

					messageBox=new ErrorWindow();
					messageBox.addMessage("Wins: "+wins+" Begin next battle!","Battle Tower");

					messageBox.repaint();
					while(messageBox.isVisible())
					{
						try
						{
							repaint();
							Thread.sleep(10);
						}
						catch(Exception e){e.printStackTrace();}
					}

					jf.toFront();

					levels+=5;
				}
			}
			catch(Exception e){e.printStackTrace();}
		}

		//Computer
		if(tileSet==INDOORS&&(currentArea[location.x][location.y-1]==141||currentArea[location.x][location.y-1]==19||currentArea[location.x][location.y-1]==350)&&direction==90)
		{
			System.out.println("Invoking Computer...");
			onComp=true;
			compMode="";
		}

		//Minigames
		if(area==Area.J_Inc_Building&&currentArea[location.x][location.y]==211)
		{
			System.out.println("Invoking Minigame initialization...");
			playingMinigame=true;
			minigameInt=0;
		}

		//LAN Trade
		if(area==Area.Pokecenter&&location.x==11&&location.y==3&&direction==90)
		{
			System.out.println("Invoking Trade...");
			if(tWin==null||!tWin.jf.isVisible())
			tradePokemon();
		}

		//Mart
		if((area==Area.Elite_4&&location.x==33&&location.y==109&&direction==90)||(area==Area.Mart&&location.x==3&&location.y==4&&direction==180)||(area==Area.MegaMart&&location.x==12&&location.y==8&&direction==90)||(area==Area.MegaMart&&location.x==14&&location.y==26&&direction==0)||(area==Area.MegaMart&&location.x==14&&location.y==39&&direction==180)||(area==Area.MegaMart&&location.x==14&&location.y==59&&direction==0))
		{
			System.out.println("Invoking Mart...");
			try
			{
				mart=new Mart();
				System.out.println(""+returnArea);
				if(area!=Area.Elite_4)
				mart.setUpMart(returnArea,1);
				else
				mart.setUpMart(Area.Elite_4,1);

				while (mart.visible)
				{
					thread.sleep(10);
					repaint();
				}
				jf.toFront();
			}
			catch(Exception e){e.printStackTrace();}
		}

		System.out.println("Ending action handling...");
		performingAction=false;
	}

	//Handles all major game events
	public void gameEventHandler(Trainer t)
	{
		if(!justBeatRival&&t.type==Trainer.TrainerType.RIVAL)
		{
			if(!t.hostile)
				timesBeatRival++;
			justBeatRival=true;

		}
		if(t.type==Trainer.TrainerType.BABB)
		{
			if(badges<1)
			{
				objectiveComplete[0]=true;
			}
			else if(objectiveComplete[10])
			{
				objectiveComplete[11]=true;
				bgm.stop();
				save();
				title.creditMusic.loop();
				showCredits=true;
			}
		}
		if(!objectiveComplete[10]&&t.type==Trainer.TrainerType.GYM_LEADER&&t.name.equals("Jenny"))
		{
			badges=1;
			objectiveComplete[1]=true;
			receiveItem(t.name);
		}
		if(!objectiveComplete[10]&&t.type==Trainer.TrainerType.GYM_LEADER&&t.name.equals("Jimmy"))
		{
			badges=2;
			objectiveComplete[2]=true;
			receiveItem(t.name);
		}
		if(!objectiveComplete[10]&&t.type==Trainer.TrainerType.GYM_LEADER&&t.name.equals("Jace"))
		{
			badges=3;
			objectiveComplete[3]=true;
			receiveItem(t.name);
		}
		if(!objectiveComplete[10]&&t.type==Trainer.TrainerType.GYM_LEADER&&t.name.equals("Jin"))
		{
			badges=4;
			objectiveComplete[4]=true;
			receiveItem(t.name);
		}
		if(!objectiveComplete[10]&&t.type==Trainer.TrainerType.GYM_LEADER&&t.name.equals("Jordan"))
		{
			badges=5;
			objectiveComplete[5]=true;
			receiveItem(t.name);
		}
		if(!objectiveComplete[10]&&t.type==Trainer.TrainerType.GYM_LEADER&&t.name.equals("Joy"))
		{
			badges=6;
			objectiveComplete[6]=true;
			receiveItem(t.name);
		}
		if(!objectiveComplete[10]&&t.type==Trainer.TrainerType.GYM_LEADER&&t.name.equals("James"))
		{
			badges=7;
			objectiveComplete[7]=true;
			receiveItem(t.name);
		}
		if(!objectiveComplete[10]&&t.type==Trainer.TrainerType.GYM_LEADER&&t.name.equals("Jessica"))
		{
			badges=8;
			objectiveComplete[8]=true;
			receiveItem(t.name);
		}

		if(!objectiveComplete[10]&&t.type==Trainer.TrainerType.PROFESSOR&&(t.name.equals("Cutter")||t.name.equals("Flyer")||t.name.equals("Surfer")||t.name.equals("Roderick Jr.")||t.name.equals("Roderick III")||t.name.equals("Brutus")))
		{
			receiveItem(t.name);
		}

		if(t.name.equals("Bike Enthusiast"))
		{
			receiveItem(t.name);
		}

		if(t.name.equalsIgnoreCase("Mr. Flashy"))
		{
			receiveItem(t.name);
		}
		
		if(t.name.equalsIgnoreCase("Name Rater"))
		{
			System.out.println("Attempting to rename Pokemon");
			if(partyPokemon[0].idNumber==trainerIdNumber||partyPokemon[0].originalTrainer.equals(name))
			{
				System.out.println("Beginning rename");
				
				do
				{
					partyPokemon[0].nickname=(JOptionPane.showInputDialog(null, "Rename "+partyPokemon[0].nickname+"? (<10 Chars)", partyPokemon[0].nickname));
				}
				while(partyPokemon[0].nickname==null);
		
				partyPokemon[0].setNickname(partyPokemon[0].nickname);
			}
		}

		if(!objectiveComplete[10]&&t.name.equalsIgnoreCase("BOSS"))
		{
			objectiveComplete[9]=true;
			receiveItem(t.name);
		}
		if(!objectiveComplete[10]&&t.name.equalsIgnoreCase("Joe"))
		{
			objectiveComplete[10]=true;
		}
	}

	//Returns the message from a sign
	public String getSignMessage(int x, int y)
	{
		String str="";

		switch(area)
		{
			case Stringville:
				if(x==16&&y==12)
					str=name+"'s House";
				else if(x==9&&y==5)
					str="To Route 1";
				else if(x==36&&y==9)
					str="Babb's Lab";
				else if(x==28&&y==18)
					str=rivalName+"'s House";
				else if(x==32&&y==38)
					str="Stringville Gym: Leader Jenny";
				break;
			case Route_0:
				if(x==10&&y==11)
					str="<=== To Stringville";
				else if(x==54&&y==11)
					str="To Args Harbor ===>";
				break;
			case Args_Harbor:
				if(x==40&&y==24)
					str="Args Harbor Gym: Leader Jimmy";
				break;
			case Route_1:
				if(x==34&&y==7)
					str="To Route 2";
				break;
			case Route_2:
				if(x==26&&y==30)
					str="Hexadecimal Forest ahead.";
				break;
			case Nested_Village:
				if(x==24&&y==23)
					str="Professor Cutter's House";
				break;
			case Intville:
				if(x==8&&y==11)
					str="nuf os si nomekoP";
				else if(x==11&&y==7)
					str="Glitch areas are awesome huh?";
				else if(x==3&&y==5)
					str="This signboard is over 9000";
				else if(x==9&&y==15)
					str="Who the heck reads these";
				else if(x==22&&y==22)
					str="Glitchmaster house";
				break;
			case Route_3:
				if(x==2&&y==34)
					str="To Route 11";
				else if(x==3&&y==7)
					str="<== Villa Del Joe";
				break;
			case Villa_Del_Joe:
				if(x==9&&y==6)
					str="Gym Del Joe: Leader Jace";
				else if(x==9&&y==52)
					str="J Incorporated Building";
				else if(x==26&&y==53)
					str="Bike Enthusiast's House";
				break;
			case Mount_Java:
				if(x==35&&y==9)
					str="Mount Java Gym: Leader Jin";
				break;
			case Streamreader_Hotel:
				if(x==23&&y==6)
					str="Streamreader Hotel Gym: Leader Jordan";
				break;
			case Route_9:
				if(x==17&&y==87)
					str="To Recursive Hot Springs";
				else if(x==7&&y==17)
					str="To Mount Java";
				else if(x==18&&y==7)
					str="Name Rater's House";
				break;
			case Recursive_Hot_Springs:
				if(x==16&&y==3)
					str="To Route 9";
				if(x==20&&y==58)
					str="To Route 10";
				if(x==25&&y==36)
					str="Recursive Hot Springs Gym: Leader Joy";
				break;
			case Route_10:
				if(x==20&&y==2)
					str="To Recursive Hot Springs";
				if(x==9&&y==34)
					str="Public Cave";
				break;
			case Route_11:
				if(x==2&&y==5)
					str="To Enumville";
				break;
			case Route_12:
				if(x==47&&y==60)
					str="To Enumville";
				break;
			case Polymorph_Town:
				if(x==23&&y==14)
					str="Polymorph Town Gym: Leader James";
				break;
			case Binary_City:
				if(x==57&&y==19)
					str="To Route 1";
				else if(x==2&&y==20)
					str="To Route 13";
				else if(x==23&&y==42)
					str="Binary City Gym: Leader Jessica";
				break;
			case Route_7:
				if(x==5&&y==44)
					str="To Joe and the Elite Four";
				break;
			case Victory_Road:
				if(x==17&&y==49)
					str="Only the strongest of trainers are allowed!";
				break;
			case Peach_City:
			case Cream_City:
				if(x==30&&y==17)
					str="Stringville Gym: Leader Jenny";
				else if(x==38&&y==17)
					str="Args Harbor Gym: Leader Jimmy";
				else if(x==46&&y==17)
					str="Gym Del Joe: Leader Jace";
				else if(x==54&&y==17)
					str="Mount Java Gym: Leader Jin";
				else if(x==30&&y==11)
					str="Streamreader Hotel Gym: Leader Jordan";
				else if(x==38&&y==11)
					str="Recursive Hot Springs Gym: Leader Joy";
				else if(x==46&&y==11)
					str="Polymorph Town Gym: Leader James";
				else if(x==54&&y==11)
					str="Binary City Gym: Leader Jessica";
				else if(x==30&&y==5)
					str="Camtendo's Gym";
				else if(x==38&&y==5)
					str="WasabiSause's Gym";
				else if(x==46&&y==5)
					str="Patches' Gym";
				else if(x==54&&y==5)
					str="Justinian's Gym";
				else if(x==54&&y==5)
					str="Justinian's Gym";
				else if(x==62&&y==5)
					str="Joseph Kalimari Mahn's Gym";
				else if(x==15&&y==24)
					str="Battle Tower: Fight until you lose!";
				break;
			case Slipspace:
				str="You found monneyz!";
				Inventory.money++;
				break;
		}

		if(str.equals(""))
		{
			switch(x%10)
			{
				case 0:
					str="Trainer Tip: Save often! Unsaved data cannot be recovered!";
					break;
				case 1:
					str="Trainer Tip: Weaken Pokemon before catching them!";
					break;
				case 2:
					str="Trainer Tip: Don't forget to spend your money at local marts!";
					break;
				case 3:
					str="Trainer Tip: TMs are reusable! HMs are too!";
					break;
				case 4:
					str="Trainer Tip: There is a Pokemon Center in every city and town! Make use of them!";
					break;
				case 5:
					str="Trainer Tip: The battle moves of Pok�mon are limited by their power points, PP.";
					break;
				case 6:
					str="Trainer Tip: Any Pok�mon that takes part in battle, however short, earns EXP!";
					break;
				case 7:
					str="Trainer Tip: Pokemon grow in strength just by battling. Not just from gaining EXP.";
					break;
				case 8:
					str="Trainer Tip: Vitamins will help your Pokemon grow to a stronger potential!";
					break;
				case 9:
					str="Trainer Tip: This game was made in about 5 months. Appreciate it!";
					break;
			}
		}

		return str;
	}

	//Returns areas you can fly to based on badges
	public void getFlyAreas()
	{
		int sum=0;

		for(int i=0; i<areasWithPCs.length; i++)
		{
			if(seenArea(areasWithPCs[i]))
				sum++;
		}

		flyAreas=new Area[sum];
		System.out.println("Number of potential fly zones: "+sum);

		int index=0;

		for(int i=0; i<areasWithPCs.length; i++)
		{
			if(seenArea(areasWithPCs[i]))
			{
				flyAreas[index]=areasWithPCs[i];
				index++;
			}
		}
	}

	//Assigns Tile Value to Your location
	public void assignTiles()
	{
		int assignTileInt = 0;
		switch (tileSet)
		{
			case INDOORS:
				assignTileInt = assignTileIntIndoors;
				break;
			case OUTDOORS:
				assignTileInt = assignTileIntOutdoors;
				break;
		}
		currentArea[location.x][location.y]=assignTileInt;
	}

	//Checks for Trainer encounters, then begins a battle against them
	public void checkTrainerEncounter()
	{
		if(cartographer)
			return;

		for(int i=0; i<numTrainers; i++)
		{
			if(trainer[i] != null && trainer[i].hostile)
				switch(trainer[i].direction)
				{
					case 0:
						if(location.x<=trainer[i].location.x+trainer[i].viewRange&&location.x>trainer[i].location.x&&location.y==trainer[i].location.y)
						{
							bgm.stop();
							surfSong.stop();
							bikeSong.stop();

							if(trainer[i].type==Trainer.TrainerType.RIVAL)
								rivalEncounter.loop();
							else if(trainer[i].type==Trainer.TrainerType.GYM_LEADER||trainer[i].type==Trainer.TrainerType.ELITE
								||trainer[i].type==Trainer.TrainerType.BABB)
								leaderEncounter.loop();
							else if(trainer[i].type==Trainer.TrainerType.PROFESSOR||trainer[i].type==Trainer.TrainerType.JAVA)
								evilEncounter.loop();
							else if(trainer[i].gender==1)
								femaleEncounter.loop();
							else
								maleEncounter.loop();

							trainerToFight=i;
							trainerEnc=true;

							try
							{
								repaint();
								Thread.sleep(2500);
							}
							catch(Exception e){}
						}
						break;
					case 90:
						if(location.y>=trainer[i].location.y-trainer[i].viewRange&&location.y<trainer[i].location.y&&location.x==trainer[i].location.x)
						{
							bgm.stop();
							surfSong.stop();
							bikeSong.stop();

							if(trainer[i].type==Trainer.TrainerType.RIVAL)
								rivalEncounter.loop();
							else if(trainer[i].type==Trainer.TrainerType.GYM_LEADER||trainer[i].type==Trainer.TrainerType.ELITE
								||trainer[i].type==Trainer.TrainerType.BABB)
								leaderEncounter.loop();
							else if(trainer[i].type==Trainer.TrainerType.PROFESSOR||trainer[i].type==Trainer.TrainerType.JAVA)
								evilEncounter.loop();
							else if(trainer[i].gender==1)
								femaleEncounter.loop();
							else
								maleEncounter.loop();

							trainerToFight=i;
							trainerEnc=true;

							try
							{
								repaint();
								Thread.sleep(2500);
							}
							catch(Exception e){}
						}
						break;
					case 180:
						if(location.x>=trainer[i].location.x-trainer[i].viewRange&&location.x<trainer[i].location.x&&location.y==trainer[i].location.y)
						{
							bgm.stop();
							surfSong.stop();
							bikeSong.stop();

							if(trainer[i].type==Trainer.TrainerType.RIVAL)
								rivalEncounter.loop();
							else if(trainer[i].type==Trainer.TrainerType.GYM_LEADER||trainer[i].type==Trainer.TrainerType.ELITE
								||trainer[i].type==Trainer.TrainerType.BABB)
								leaderEncounter.loop();
							else if(trainer[i].type==Trainer.TrainerType.PROFESSOR||trainer[i].type==Trainer.TrainerType.JAVA)
								evilEncounter.loop();
							else if(trainer[i].gender==1)
								femaleEncounter.loop();
							else
								maleEncounter.loop();

							trainerToFight=i;
							trainerEnc=true;

							try
							{
								repaint();
								Thread.sleep(2500);
							}
							catch(Exception e){}
						}
						break;
					case 270:
						if(location.y<=trainer[i].location.y+trainer[i].viewRange&&location.y>trainer[i].location.y&&location.x==trainer[i].location.x)
						{
							bgm.stop();
							surfSong.stop();
							bikeSong.stop();

							if(trainer[i].type==Trainer.TrainerType.RIVAL)
								rivalEncounter.loop();
							else if(trainer[i].type==Trainer.TrainerType.GYM_LEADER||trainer[i].type==Trainer.TrainerType.ELITE
								||trainer[i].type==Trainer.TrainerType.BABB)
								leaderEncounter.loop();
							else if(trainer[i].type==Trainer.TrainerType.PROFESSOR||trainer[i].type==Trainer.TrainerType.JAVA)
								evilEncounter.loop();
							else if(trainer[i].gender==1)
								femaleEncounter.loop();
							else
								maleEncounter.loop();

							trainerToFight=i;
							trainerEnc=true;

							try
							{
								repaint();
								Thread.sleep(2500);
							}
							catch(Exception e){}
						}
						break;
				}
		}
	}

	//Initiates Surf
	public void useSurf()
	{
		surfing=true;

		messageBox=new ErrorWindow();
		messageBox.addMessage("You ride on your Pokemon!","Hidden Move: Surf");
		messageBox.repaint();
		while(messageBox.isVisible())
		{
			try
			{
				repaint();
				Thread.sleep(10);
			}
			catch(Exception e){}
		}

		bgm.stop();
		bikeSong.stop();
		bicycling=false;
		surfSong.loop();

		jf.toFront();
		moving=false;
		performingAction=false;
	}

	//Initiates Bike Riding
	public void rideBike()
	{
		bicycling=true;

		messageBox=new ErrorWindow();
		messageBox.addMessage("You ride on your Bicycle!","I love to ride my...");
		messageBox.repaint();
		while(messageBox.isVisible())
		{
			try
			{
				repaint();
				Thread.sleep(10);
			}
			catch(Exception e){}
		}

		if(!forceBike)
		{
			bgm.stop();
			bikeSong.loop();
		}

		jf.toFront();
		moving=false;
		performingAction=false;
	}

	//Forces use of Bike on certain routes
	public void forceBike()
	{
		bicycling=true;
		moving=false;
	}

	//Uses Fly
	public void useFly()
	{
		flyMenu=false;
		bgm.stop();
		transition=true;
		direction=270;
		area=flyAreas[flyMenuInt];
		switch(area)
		{
			case Stringville:
				location.x=5;
				location.y=38;
				break;
			case Args_Harbor:
				location.x=3;
				location.y=28;
				break;
			case Villa_Del_Joe:
				location.x=25;
				location.y=32;
				break;
			case Mount_Java:
				location.x=13;
				location.y=30;
				break;
			case Streamreader_Hotel:
				location.x=22;
				location.y=26;
				break;
			case Recursive_Hot_Springs:
				location.x=18;
				location.y=51;
				break;
			case Polymorph_Town:
				location.x=31;
				location.y=20;
				break;
			case Binary_City:
				location.x=45;
				location.y=26;
				break;
			case Route_6:
				location.x=7;
				location.y=22;
				break;
			case Nested_Village:
				location.x=27;
				location.y=24;
				break;
			case Enumville:
				location.x=12;
				location.y=20;
				break;
			case Champions_Walk:
				location.x=15;
				location.y=14;
				break;
			case Elite_4:
				location.x=29;
				location.y=115;
				break;
			case Peach_City:
			case Cream_City:
				location.x=49;
				location.y=30;
				break;
			default:
				location.x=0;
				location.y=0;
				break;
		}
		createCurrentArea();
	}

	public void receiveItem(String person)
	{
		Item prize=new Item(Item.Type.MOUNTAIN_DEW,1);

		if(!gotItem[0]&&person.equals("Jenny"))
		{
			prize=new Item(Item.Type.TM,1,2);
			gotItem[0]=true;

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}
		if(!gotItem[1]&&person.equals("Jimmy"))
		{
			prize=new Item(Item.Type.TM,1,22);
			gotItem[1]=true;

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}
		if(!gotItem[2]&&person.equals("Jace"))
		{
			prize=new Item(Item.Type.TM,1,24);
			gotItem[2]=true;

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}
		if(!gotItem[3]&&person.equals("Jin"))
		{
			prize=new Item(Item.Type.TM,1,17);
			gotItem[3]=true;

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}
		if(!gotItem[4]&&person.equals("Jordan"))
		{
			prize=new Item(Item.Type.TM,1,15);
			gotItem[4]=true;

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}
		if(!gotItem[5]&&person.equals("Joy"))
		{
			prize=new Item(Item.Type.TM,1,13);
			gotItem[5]=true;

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}
		if(!gotItem[6]&&person.equals("James"))
		{
			prize=new Item(Item.Type.TM,1,29);
			gotItem[6]=true;

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}
		if(!gotItem[7]&&person.equals("Jessica"))
		{
			prize=new Item(Item.Type.TM,1,6);
			gotItem[7]=true;

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}

		if(!Inventory.hasItem(new Item(Item.Type.HM,1,1))&&person.equals("Cutter"))
		{
			prize=new Item(Item.Type.HM,1,1);
			gotItem[8]=true;

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}
		if(!Inventory.hasItem(new Item(Item.Type.HM,1,2))&&person.equals("Flyer"))
		{
			prize=new Item(Item.Type.HM,1,2);
			gotItem[9]=true;

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}
		if(!Inventory.hasItem(new Item(Item.Type.HM,1,3))&&person.equals("Surfer"))
		{
			prize=new Item(Item.Type.HM,1,3);
			gotItem[9]=true;

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}
		if(!Inventory.hasItem(new Item(Item.Type.HM,1,4))&&person.equals("Brutus"))
		{
			prize=new Item(Item.Type.HM,1,4);
			gotItem[9]=true;

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}
		if(!Inventory.hasItem(new Item(Item.Type.GOOD_ROD,1))&&person.equals("Roderick Jr."))
		{
			prize=new Item(Item.Type.GOOD_ROD,1);
			gotItem[9]=true;

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}
		if(!Inventory.hasItem(new Item(Item.Type.OLD_ROD,1))&&person.equals("Roderick III"))
		{
			prize=new Item(Item.Type.OLD_ROD,1);
			gotItem[9]=true;

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}
		if(badges>=3&&!Inventory.hasItem(new Item(Item.Type.BICYCLE,1))&&person.equals("Bike Enthusiast"))
		{
			prize=new Item(Item.Type.BICYCLE,1);
			gotItem[9]=true;

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}
		if(!Inventory.hasItem(new Item(Item.Type.HM,1,5))&&person.equalsIgnoreCase("Mr. Flashy"))
		{
			prize=new Item(Item.Type.HM,1,5);

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}
		if(!Inventory.hasItem(new Item(Item.Type.MASTER_BALL,1))&&person.equalsIgnoreCase("BOSS"))
		{
			prize=new Item(Item.Type.MASTER_BALL,1);

			if(prize.type==Item.Type.HM||prize.type==Item.Type.TM)
			messageBox.addMessage("You got "+prize.type+" "+prize.mNo+"!","Received Item!");
			else if(prize.startsWithVowel())
			messageBox.addMessage("You got an "+prize.type+"!","Received Item!");
			else
			messageBox.addMessage("You got a "+prize.type+"!","Received Item!");

			messageBox.repaint();
			Inventory.addItem(prize);
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}
		}

		jf.toFront();
	}

	//Progresses Trainer toward Player until Battle Starts
	public void beginTrainerBattle()
	{
		switch(trainer[trainerToFight].direction)
		{
			case 0:
				if(trainer[trainerToFight].location.x!=location.x-1)
				{
					trainer[trainerToFight].location.x++;

					try
					{
						Thread.sleep(300);
					}
					catch(Exception e){}
				}
				else
					trainerBattle();
				break;
			case 90:
				if(trainer[trainerToFight].location.y!=location.y+1)
				{
					trainer[trainerToFight].location.y--;

					try
					{
						Thread.sleep(300);
					}
					catch(Exception e){}
				}
				else
					trainerBattle();
				break;
			case 180:
				if(trainer[trainerToFight].location.x!=location.x+1)
				{
					trainer[trainerToFight].location.x--;

					try
					{
						Thread.sleep(300);
					}
					catch(Exception e){}
				}
				else
					trainerBattle();
				break;
			case 270:
				if(trainer[trainerToFight].location.y!=location.y-1)
				{
					trainer[trainerToFight].location.y++;

					try
					{
						Thread.sleep(300);
					}
					catch(Exception e){}
				}
				else
					trainerBattle();
				break;
		}
	}

	//Returns the possibility of fishing
	public static boolean canFish()
	{
		int test=0;

		switch(direction)
		{
			case 0:
				if(location.x<getAreaWidth())
				test=currentArea[location.x+1][location.y];
				else
					return false;
				break;
			case 90:
				if(location.y>0)
				test=currentArea[location.x][location.y-1];
				else
					return false;
				break;
			case 180:
				if(location.x>0)
				test=currentArea[location.x-1][location.y];
				else
					return false;
				break;
			case 270:
				if(location.y<getAreaHeight())
				test=currentArea[location.x][location.y+1];
				else
					return false;
				break;
		}

		switch(test)
		{
			case 19:
			case 1:
			case 10:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 8:
				return true;
			default:
				return false;

		}
	}

	//Uses a Fishing Rod
	public void useRod(Item rod)
	{
		int randy=(int)(Math.random()*10);

		paused=false;
		Inventory.inventoryWindow.closeInventory();

		messageBox=new ErrorWindow();
		messageBox.addMessage("You use the "+rod.type+"!","Fishing");

		messageBox.repaint();
		while(messageBox.isVisible())
		{
			try
			{
				Thread.sleep(10);
			}
			catch(Exception e){e.printStackTrace();}
		}

		if(randy<=3)
		{
			messageBox=new ErrorWindow();
			messageBox.addMessage("Oh! A bite!","Fishing");

			messageBox.repaint();
			while(messageBox.isVisible())
			{
				try
				{
					Thread.sleep(10);
				}
				catch(Exception e){e.printStackTrace();}
			}

			fish(rod);
		}
		else
		{
			messageBox=new ErrorWindow();
			messageBox.addMessage("Not even a nibble...","Fishing");

			messageBox.repaint();
			while(messageBox.isVisible())
			{
				try
				{
					Thread.sleep(10);
				}
				catch(Exception e){e.printStackTrace();}
			}
		}
	}

	//Allows user to cut bushes
	public void useCut()
	{
		boolean test=false;
		Point point=new Point(0,0);

		//Above Player
		if(direction==90&&currentArea[location.x][location.y-1]==3)
		{
			test=true;
			point.x=location.x;
			point.y=location.y-1;
		}
		//Below Player
		else if(direction==270&&currentArea[location.x][location.y+1]==3)
		{
			test=true;
			point.x=location.x;
			point.y=location.y+1;
		}
		//Left of Player
		else if(direction==180&&currentArea[location.x-1][location.y]==3)
		{
			test=true;
			point.x=location.x-1;
			point.y=location.y;
		}
		//Right of Player
		else if(direction==0&&currentArea[location.x+1][location.y]==3)
		{
			test=true;
			point.x=location.x+1;
			point.y=location.y;
		}

		if(tileSet!=OUTDOORS||!test||!Mechanics.hasCut(partyPokemon))
			return;

		currentArea[point.x][point.y]=6;
		messageBox=new ErrorWindow();
		messageBox.addMessage("You cut down the tree!","Hidden Move: Cut");
		messageBox.repaint();
		while(messageBox.isVisible())
		{
			try
			{
				repaint();
				Thread.sleep(10);
			}
			catch(Exception e){}
		}

		jf.toFront();
		performingAction=false;
	}

	//Allows user to punch legendaries and boulders
	public void useStrength()
	{
		Point point=new Point(0,0);

		if(Mechanics.hasStrength(partyPokemon))
		{
			System.out.println("Using HM 4 Strength");

			if(tileSet==OUTDOORS&&(checkSnorlax(point).x!=0&&checkSnorlax(point).y!=0))
			{
				messageBox=new ErrorWindow();
				messageBox.addMessage("You use Strength and punch Snorlax! It wakes in a grumpy rage...","Hidden Move: Strength");
				messageBox.repaint();
				while(messageBox.isVisible())
				{
					try
					{
						repaint();
						Thread.sleep(10);
					}
					catch(Exception e){}
				}

				fishing=true;
				enemy[0]=new Pokemon(Pokemon.Species.SNORLAX,45);
				wildEncounter();

				jf.toFront();
				performingAction=false;

				checkLegendaries();
				messageBox.addMessage("Snorlax is out of the way now!","Snorlax is gone!");
				messageBox.repaint();
				while(messageBox.isVisible())
				{
					try
					{
						repaint();
						Thread.sleep(10);
					}
					catch(Exception e){}
				}
				return;
			}
			else if(tileSet==INDOORS&&(checkUbers(point).x!=0&&checkUbers(point).y!=0))
			{
				messageBox=new ErrorWindow();
				switch(area)
				{
					case Moltres_Cave:
						messageBox.addMessage("You use Strength and punch Moltres! It flaps its wings in rage!","Hidden Move: Strength");
						enemy[0]=new Pokemon(Pokemon.Species.MOLTRES,50);
						break;
					case Articuno_Cave:
						messageBox.addMessage("You use Strength and punch Articuno! It flaps its wings in rage!","Hidden Move: Strength");
						enemy[0]=new Pokemon(Pokemon.Species.ARTICUNO,50);
						break;
					case Power_Plant:
						messageBox.addMessage("You use Strength and punch Zapdos! It flaps its wings in rage!","Hidden Move: Strength");
						enemy[0]=new Pokemon(Pokemon.Species.ZAPDOS,50);
						break;
					case Primal_Cave:
						if (VERSION.equals("Peaches"))
						{
							messageBox.addMessage("You use Strength and punch Mewtwo! He's really ticked!","Hidden Move: Strength");
							enemy[0]=new Pokemon(Pokemon.Species.MEWTWO,70);
						}
						else
						{
							messageBox.addMessage("You use Strength and punch Mew! You baby puncher!","Hidden Move: Strength");
							enemy[0]=new Pokemon(Pokemon.Species.MEW,70);
						}
						break;
					case Challenge_Cave:
						messageBox.addMessage("You use Strength and punch the Armored Mewtwo! Its armor is glowing...","Hidden Move: Strength");
						enemy[0]=new Pokemon(Pokemon.Species.MEWTWO,Pokemon.Move.PSYCHIC,Pokemon.Move.ICE_BEAM,Pokemon.Move.THUNDERBOLT,Pokemon.Move.REST,100);
						enemy[0].setNickname("Mewtrix");
						enemy[0].setMaxStats();
						break;
				}
				messageBox.repaint();
				while(messageBox.isVisible())
				{
					try
					{
						repaint();
						Thread.sleep(10);
					}
					catch(Exception e){}
				}

				fishing=true;
				wildEncounter();

				jf.toFront();
				performingAction=false;

				checkLegendaries();
				if(enemy[0].nickname.equals("Mewtrix"))
				{
					messageBox.addMessage("...It respects your power.","Legendary Pokemon?");
				}
				else
				messageBox.addMessage("Wow! That was a legendary Pokemon!","Legendary Pokemon!");
				messageBox.repaint();
				while(messageBox.isVisible())
				{
					try
					{
						repaint();
						Thread.sleep(10);
					}
					catch(Exception e){}
				}
				
				if(enemy[0].nickname.equals("Mewtrix"))
				{
					bgm.stop();
					save();
					title.creditMusic.loop();
					showCredits=true;
				}
				return;
			}

			moveBoulders();

		}
		else
			return;
	}

	//Checks for Snorlax on the map
	public Point checkSnorlax(Point point)
	{
		//Snorlax Test
		for(int i=179; i<183; i++)
		{
			//Above Player
			if(direction==90&&currentArea[location.x][location.y-1]==i)
			{
				point.x=location.x;
				point.y=location.y-1;
				return point;
			}
			//Below Player
			else if(direction==270&&currentArea[location.x][location.y+1]==i)
			{
				point.x=location.x;
				point.y=location.y+1;
				return point;
			}
			//Left of Player
			else if(direction==180&&currentArea[location.x-1][location.y]==i)
			{
				point.x=location.x-1;
				point.y=location.y;
				return point;
			}
			//Right of Player
			else if(direction==0&&currentArea[location.x+1][location.y]==i)
			{
				point.x=location.x+1;
				point.y=location.y;
				return point;
			}
		}

		return point;
	}

	//Checks for Other Ubers on the map
	public Point checkUbers(Point point)
	{
		//Int to check for Pokemon tile
		int i=-1;

		switch(area)
		{
			case Moltres_Cave:
				i=260;
				break;
			case Articuno_Cave:
				i=263;
				break;
			case Power_Plant:
				i=320;
				break;
			case Primal_Cave:
				if (VERSION.equals("Peaches"))
				i=267;
				else
				i=323;
				break;
			case Challenge_Cave:
				i=325;
				break;
				
		}

		//Above Player
		if(direction==90&&currentArea[location.x][location.y-1]==i)
		{
			point.x=location.x;
			point.y=location.y-1;
			return point;
		}
		//Below Player
		else if(direction==270&&currentArea[location.x][location.y+1]==i)
		{
			point.x=location.x;
			point.y=location.y+1;
			return point;
		}
		//Left of Player
		else if(direction==180&&currentArea[location.x-1][location.y]==i)
		{
			point.x=location.x-1;
			point.y=location.y;
			return point;
		}
		//Right of Player
		else if(direction==0&&currentArea[location.x+1][location.y]==i)
		{
			point.x=location.x+1;
			point.y=location.y;
			return point;
		}

		return point;
	}

	//Removes legendaries from the map if necessary
	public void checkLegendaries()
	{
		if(area==Area.Mount_Java&&Pokedex.seen[142])
		{
			currentArea[46][20]=9;
			currentArea[47][20]=9;
			currentArea[46][21]=9;
			currentArea[47][21]=9;
		}
		else if(area==Area.Moltres_Cave&&Pokedex.seen[145])
		{
			currentArea[9][8]=231;
			currentArea[10][8]=231;
			currentArea[10][9]=231;
			currentArea[11][8]=231;
		}
		else if(area==Area.Articuno_Cave&&Pokedex.seen[143])
		{
			currentArea[9][8]=231;
			currentArea[10][8]=231;
			currentArea[10][7]=231;
			currentArea[11][8]=231;
		}
		else if(area==Area.Power_Plant&&Pokedex.seen[144])
		{
			currentArea[11][11]=294;
			currentArea[12][11]=294;
			currentArea[13][11]=294;
		}
		else if(area==Area.Primal_Cave&&Pokedex.seen[149]&&VERSION.equals("Peaches"))
		{
			currentArea[10][8]=231;
			currentArea[11][8]=231;
			currentArea[10][7]=231;
		}
		else if(area==Area.Primal_Cave&&Pokedex.seen[150]&&VERSION.equals("Cream"))
		{
			currentArea[10][8]=231;
			currentArea[11][8]=231;
		}
	}

	//Allows user to move boulders
	public void moveBoulders()
	{
		boolean test=false;
		Point point=new Point(0,0);
		Point point2=new Point(0,0);

		//Above Player
		if(direction==90&&currentArea[location.x][location.y-1]==257||currentArea[location.x][location.y-1]==258)
		{
			test=true;
			point.x=location.x;
			point.y=location.y-1;
			point2.x=location.x;
			point2.y=location.y-2;
		}
		//Below Player
		else if(direction==270&&currentArea[location.x][location.y+1]==257||currentArea[location.x][location.y+1]==258)
		{
			test=true;
			point.x=location.x;
			point.y=location.y+1;
			point2.x=location.x;
			point2.y=location.y+2;
		}
		//Left of Player
		else if(direction==180&&currentArea[location.x-1][location.y]==257||currentArea[location.x-1][location.y]==258)
		{
			test=true;
			point.x=location.x-1;
			point.y=location.y;
			point2.x=location.x-2;
			point2.y=location.y;
		}
		//Right of Player
		else if(direction==0&&currentArea[location.x+1][location.y]==257||currentArea[location.x+1][location.y]==258)
		{
			test=true;
			point.x=location.x+1;
			point.y=location.y;
			point2.x=location.x+2;
			point2.y=location.y;
		}

		if(!test)
			return;

		Point temp=new Point(location.x,location.y);
		location.x=point.x;
		location.y=point.y;

		if(canMove())
		{
			location.x=temp.x;
			location.y=temp.y;
			currentArea[point2.x][point2.y]=257;
			currentArea[point.x][point.y]=231;
		}
		else
		{
			location.x=temp.x;
			location.y=temp.y;
			return;
		}

		messageBox=new ErrorWindow();
		messageBox.addMessage("You push the boulder!","Hidden Move: Strength");
		messageBox.repaint();
		while(messageBox.isVisible())
		{
			try
			{
				repaint();
				Thread.sleep(10);
			}
			catch(Exception e){}
		}

		jf.toFront();
		performingAction=false;
	}

	//Allows user to read signs
	public void readSign()
	{
		boolean test=false;
		Point point=new Point(0,0);

		//Above Player
		if(direction==90&&currentArea[location.x][location.y-1]==11)
		{
			test=true;
			point.x=location.x;
			point.y=location.y-1;
		}
		//Below Player
		else if(direction==270&&currentArea[location.x][location.y+1]==11)
		{
			test=true;
			point.x=location.x;
			point.y=location.y+1;
		}
		//Left of Player
		else if(direction==180&&currentArea[location.x-1][location.y]==11)
		{
			test=true;
			point.x=location.x-1;
			point.y=location.y;
		}
		//Right of Player
		else if(direction==0&&currentArea[location.x+1][location.y]==11)
		{
			test=true;
			point.x=location.x+1;
			point.y=location.y;
		}

		if(!test)
			return;

		messageBox=new ErrorWindow();
		messageBox.addMessage(getSignMessage(point.x,point.y),"Signboard");
		messageBox.repaint();
		while(messageBox.isVisible())
		{
			try
			{
				repaint();
				Thread.sleep(10);
			}
			catch(Exception e){}
		}

		jf.toFront();
		performingAction=false;
	}

	//Allows user to talk to trainers
	public void talk()
	{
		boolean test=false;
		int theTrainer=0;

		for(int i=0; i<numTrainers; i++)
		{
			if(trainer[i] != null && direction==180&&trainer[i].location.x==location.x-1&&trainer[i].location.y==location.y)
			{
				test=true;
				theTrainer=i;
				break;
			}
			else if(trainer[i] != null &&direction==0&&trainer[i].location.x==location.x+1&&trainer[i].location.y==location.y)
			{
				test=true;
				theTrainer=i;
				break;
			}
			else if(trainer[i] != null &&direction==270&&trainer[i].location.y==location.y+1&&trainer[i].location.x==location.x)
			{
				test=true;
				theTrainer=i;
				break;
			}
			else if(trainer[i] != null &&direction==90&&trainer[i].location.y==location.y-1&&trainer[i].location.x==location.x)
			{
				test=true;
				theTrainer=i;
				break;
			}
		}

		if(!test)
			return;

		if(!trainer[theTrainer].hostile)
		{
			trainer[theTrainer].direction=(direction+180)%360;
			repaint();

			messageBox=new ErrorWindow();

			if(!trainer[theTrainer].important&&!Mechanics.isSpecialTrainer(trainer[theTrainer]))
			messageBox.addMessage(trainer[theTrainer].getPostMessage(),trainer[theTrainer].getNameAndType());
			else
			messageBox.addLargeMessage(trainer[theTrainer].getPostMessage(),trainer[theTrainer].getNameAndType());

			messageBox.repaint();
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}

			gameEventHandler(trainer[theTrainer]);

			jf.toFront();
		}
		else
		{
			trainer[theTrainer].direction=(direction+180)%360;
			trainer[theTrainer].setViewRange(1);
		}

		performingAction=false;
	}

	//Allows User to pick up items
	public void pickUp()
	{
		boolean test=false;
		int theItem=0;

		for(int i=0; i<numItems; i++)
		{
			if(mapItems[i] != null && direction==180&&mapItems[i].location.x==location.x-1&&mapItems[i].location.y==location.y)
			{
				test=true;
				theItem=i;
				break;
			}
			else if(mapItems[i] != null &&direction==0&&mapItems[i].location.x==location.x+1&&mapItems[i].location.y==location.y)
			{
				test=true;
				theItem=i;
				break;
			}
			else if(mapItems[i] != null &&direction==270&&mapItems[i].location.y==location.y+1&&mapItems[i].location.x==location.x)
			{
				test=true;
				theItem=i;
				break;
			}
			else if(mapItems[i] != null &&direction==90&&mapItems[i].location.y==location.y-1&&mapItems[i].location.x==location.x)
			{
				test=true;
				theItem=i;
				break;
			}
		}

		if(!test||mapItems[theItem].found)
			return;

			repaint();

			messageBox=new ErrorWindow();

			if(mapItems[theItem].startsWithVowel())
				messageBox.addMessage("You found an "+mapItems[theItem].type+"!","Found Item!");
			else
				messageBox.addMessage("You found a "+mapItems[theItem].type+"!","Found Item!");

			messageBox.repaint();
			Inventory.addItem(mapItems[theItem]);
			mapItems[theItem].found=true;
			while(messageBox.isVisible())
			{
				try
				{
					repaint();
					Thread.sleep(10);
				}
				catch(Exception e){}
			}

			jf.toFront();

		for(int i=0; i<foundItem.length; i++)
		{
			foundItem[i]=mapItems[i].found;
		}

		saveItemData();
		performingAction=false;
	}

	//Super Method for trading Pokemon over Networks
	public void tradePokemon()
	{
		tWin=new TradeWindow();

		while(!CONNECTED)
		{

		}

		while(CONNECTED)
		{
			repaint();
			try
			{
				Thread.sleep(10);
			}
			catch(Exception ex){}
			manageTime();
			tWin.tradeLogic();
		}
	}

	//Handles time management for saving and such
	public static void manageTime()
	{
		long currentTime=System.currentTimeMillis();
		long time=currentTime-launchTime;
		int parsedTime=(int)(time/Math.pow(10,3));
		playTimeSecs=parsedTime%60;

		if(!timeBoolean&&playTimeSecs%60==0)
		{
			playTimeMins++;
			timeBoolean=true;
		}

		if(playTimeSecs%60==1)
			timeBoolean=false;

		if(playTimeMins<0)
			playTimeMins=0;

		if(playTimeMins>=60)
		{
			playTimeMins=0;
			playTimeHours++;

			try
			{
				Thread.sleep(1000);
			}
			catch(Exception e){}
		}
		String minStr,secStr;

		minStr=""+playTimeMins;
		secStr=""+playTimeSecs;
		if(playTimeMins<10)
			minStr="0"+minStr;
		if(playTimeSecs<10)
			secStr="0"+secStr;


		timeString=""+playTimeHours+":"+minStr+":"+secStr;

	}

	//Clunky fill tool
	public void fillTiles()
	{
		int assignTileInt = 0;
		switch (tileSet)
		{
			case INDOORS:
				assignTileInt = assignTileIntIndoors;
				break;
			case OUTDOORS:
				assignTileInt = assignTileIntOutdoors;
				break;
		}
		int tileNum = currentArea[location.x][location.y];

		for (int y = location.y; y<=getAreaHeight(); y++)
		{
			if (currentArea[location.x][y] == tileNum)
			{
				for (int i = location.x; i<=getAreaWidth(); i++)
				{
					if (currentArea[i][y] == tileNum)
					currentArea[i][y]=assignTileInt;
					else
					break;
				}
				for (int i = location.x-1; i>=0; i--)
				{
					if (currentArea[i][y] == tileNum)
					currentArea[i][y]=assignTileInt;
					else
					break;
				}
			}
			else
			break;
		}
		for (int y = location.y-1; y>=0; y--)
		{
			if (currentArea[location.x][y] == tileNum)
			{
				for (int i = location.x; i<=getAreaWidth(); i++)
				{
					if (currentArea[i][y] == tileNum)
					currentArea[i][y]=assignTileInt;
					else
					break;
				}
				for (int i = location.x-1; i>=0; i--)
				{
					if (currentArea[i][y] == tileNum)
					currentArea[i][y]=assignTileInt;
					else
					break;
				}
			}
			else
			break;
		}
	}
	public void horizontalFill()
	{
		int assignTileInt = 0;
		switch (tileSet)
		{
			case INDOORS:
				assignTileInt = assignTileIntIndoors;
				break;
			case OUTDOORS:
				assignTileInt = assignTileIntOutdoors;
				break;
		}
		int tileNum = currentArea[location.x][location.y];
		for (int i = location.x; i<=getAreaWidth(); i++)
		{
			if (currentArea[i][location.y] == tileNum)
			currentArea[i][location.y]=assignTileInt;
			else
			break;
		}
		for (int i = location.x-1; i>=0; i--)
		{
			if (currentArea[i][location.y] == tileNum)
			currentArea[i][location.y]=assignTileInt;
			else
			break;
		}

	}
	public void verticalFill()
	{
		int assignTileInt = 0;
		switch (tileSet)
		{
			case INDOORS:
				assignTileInt = assignTileIntIndoors;
				break;
			case OUTDOORS:
				assignTileInt = assignTileIntOutdoors;
				break;
		}
		int tileNum = currentArea[location.x][location.y];

		for (int y = location.y; y<=getAreaHeight(); y++)
		{
			if (currentArea[location.x][y] == tileNum)
			{
				currentArea[location.x][y]=assignTileInt;
			}
			else
			break;
		}
		for (int y = location.y-1; y>=0; y--)
		{
			if (currentArea[location.x][y] == tileNum)
			{
				currentArea[location.x][y]=assignTileInt;
			}
			else
			break;
		}
	}

	//Saves Tile Map to .tilemap
	public void saveTileSet()
	{
		if(2+2==4)
		return;
		String output = "NoMap.tilemap";
		switch(area)
		{
			case Stringville:
				output="Stringville.tilemap";
				break;
			case Route_0:
				output="Route0.tilemap";
				break;
			case Args_Harbor:
				output="ArgsHarbor.tilemap";
				break;
			case Route_1:
				output="Route1.tilemap";
				break;
			case Route_2:
				output="Route2.tilemap";
				break;
			case Pokecenter:
				output="Pokecenter.tilemap";
				break;
			case Nested_Village:
				output="NestedVillage.tilemap";
				break;
			case Mart:
				output="Mart.tilemap";
				break;
			case Route_4:
				output="Route4.tilemap";
				break;
		}
		output = "Tilemaps/" + output;
		try
		{
			PrintWriter fout=new PrintWriter(new FileWriter(output));

			for(int i=0; i<currentArea.length; i++)
				for(int j=0; j<currentArea[0].length; j++)
					fout.println(currentArea[i][j]);
			fout.close();
		}
		catch(Exception e){}

		System.out.println("Tile map saved");
	}

	//Encrypts a file and writes a key for it
	public static String encrypt(String filename)
	{
		String superString="";
		Scanner fin=new Scanner(System.in);

		try
    	{
    		fin=new Scanner(new FileReader(filename));
    	}
    	catch(Exception e){e.printStackTrace();}

    	while(fin.hasNextLine())
    	{
    		String line=fin.nextLine();

    		for(int i=0; i<line.length(); i++)
    		{
    			superString+=""+Integer.toHexString(Integer.valueOf(line.charAt(i)));
    		}
    	}

		return superString;
	}

	//Writes encryption key to the proper file
	public static void saveEncryptionKey(String filename, String key)
	{
		filename=filename.replace(".","_");
		filename=filename+".key";

		try
		{
			PrintWriter fout=new PrintWriter(new FileWriter(filename));
			fout.println(key);
			fout.close();
		}
		catch(Exception e){e.printStackTrace();}
	}

	//Tests the encryption of a given file
	//Exits if encryption fails
	public static void testEncryption(String filename)
	{
		String keyfile=filename.replace(".","_");
		String test="";
		keyfile=keyfile+".key";

		try
		{
			BufferedReader in = new BufferedReader(new FileReader(keyfile));
			test=in.readLine();
		}
		catch(Exception e){e.printStackTrace();}

		if(!test.equals(encrypt(filename)))
		{
			System.out.println("Invalid key.");
			System.exit(-1);
		}
	}

	//Saves all data
	public static void save()
	{
		System.out.println("Beginning file write...");
		if(!file.isDirectory())
		{
			file.mkdir();
			System.out.println("No save file found, constructing new savefile");
			pauseMenu[3]=name;
		}
		else
			System.out.println("Overwriting current savedata");
		saveTrainerID();
		saveGameData();
		savePokemon();
		saveObjectives();
		saveHouseInt();
		Inventory.save();
		Pokedex.saveFile();
		System.out.println("All data written to proper files.");
		System.out.println();
		recentlySaved=true;
	}

	//Saves TrainerID as a separate file for loading
	public static void saveTrainerID()
	{
		try
		{
			PrintWriter fout=new PrintWriter(new FileWriter("savedata/trainerData.id"));
			fout.println(idString);
			fout.close();
		}
		catch(Exception e){e.printStackTrace();}
	}

	//Loads TrainerID prior to other loads
	public void loadTrainerID()
	{
		try
		{
			BufferedReader in = new BufferedReader(new FileReader("savedata/trainerData.id"));
			trainerIdNumber=Integer.parseInt(in.readLine());
		}
		catch(Exception e){e.printStackTrace();}
		setIdentification();
		System.out.println("TrainerID loaded");
	}

	//Saves found item data
	public static void saveItemData()
	{
		String output ="savedata/"+idString+"_"+area+".itemData";

		try
		{
			PrintWriter fout=new PrintWriter(new FileWriter(output));

			if(VERSION.equals("Peaches"))
				fout.println("PDAE"+idString);
			else
				fout.println("CDAE"+idString);

			int length=foundItem.length;
			fout.println(length);
			for(int i=0; i<length; i++)
			{
				fout.println(foundItem[i]);
			}
			fout.close();

			saveEncryptionKey(output,encrypt(output));
			System.out.println("Item data saved.");
		}
		catch(Exception e){}
	}

	//Loads found item data
	public void loadItemData()
	{
		String input ="savedata/"+idString+"_"+area+".itemData";

		foundItem=new boolean[numItems];

		try
		{
			BufferedReader in = new BufferedReader(new FileReader(input));

			String test=in.readLine();
			int numTimes=Integer.parseInt(in.readLine());

			for(int i=0; i<numTimes; i++)
			{
				foundItem[i]=Boolean.parseBoolean(in.readLine());
			}
		}
		catch(Exception e)
		{
			saveItemData();
		}

		System.out.println("Items loaded successfully.");
	}

	//Loads Objectives
	public void loadObjectives()
	{
		String input ="savedata/"+idString+".objectives";

		try
		{
			BufferedReader in = new BufferedReader(new FileReader(input));

			String test=in.readLine();

			for(int i=0; i<objectiveComplete.length; i++)
			{
				objectiveComplete[i]=Boolean.parseBoolean(in.readLine());
			}
		}
		catch(Exception e){}

		testEncryption(input);

		System.out.println("Objectives loaded successfully.");
	}

	//Loads Objectives
	public void loadHouseInt()
	{
		String input ="savedata/"+idString+".houseaninteger";

		try
		{
			BufferedReader in = new BufferedReader(new FileReader(input));

			String test=in.readLine();

			houseInt=Integer.parseInt(in.readLine());
		}
		catch(Exception e){saveHouseInt();}

		System.out.println("House Int loaded successfully.");
	}

	//Saves other game data
	public static void saveObjectives()
	{
		String output ="savedata/"+idString+".objectives";

		try
		{
			PrintWriter fout=new PrintWriter(new FileWriter(output));

			if(VERSION.equals("Peaches"))
				fout.println("PDAE"+idString);
			else
				fout.println("CDAE"+idString);

			//Game Vars
			for(int i=0; i<objectiveComplete.length; i++)
			{
				fout.println(objectiveComplete[i]);
			}
			fout.close();

			saveEncryptionKey(output,encrypt(output));
			System.out.println("Objective data saved.");
		}
		catch(Exception e){}
	}

	//Saves House Int
	public static void saveHouseInt()
	{
		String output ="savedata/"+idString+".houseaninteger";

		try
		{
			PrintWriter fout=new PrintWriter(new FileWriter(output));

			if(VERSION.equals("Peaches"))
				fout.println("PDAE"+idString);
			else
				fout.println("CDAE"+idString);

			fout.println(houseInt);
			fout.close();

			System.out.println("House Integer saved.");
		}
		catch(Exception e){}
	}

	//Saves other game data
	public static void saveGameData()
	{
		String output ="savedata/"+idString+".jokes";

		try
		{
			PrintWriter fout=new PrintWriter(new FileWriter(output));

			if(VERSION.equals("Peaches"))
				fout.println("PDAE"+idString);
			else
				fout.println("CDAE"+idString);

			//Game Vars
			fout.println(name);
			fout.println(rivalName);
			fout.println(timesBeatRival);
			fout.println(badges);
			fout.println(playTimeSecs);
			fout.println(playTimeMins-1);
			fout.println(playTimeHours);
			fout.println(location.x);
			fout.println(location.y);
			fout.println(returnPoint.x);
			fout.println(returnPoint.y);
			fout.println(returnPoint2.x);
			fout.println(returnPoint2.y);
			fout.println(area);
			fout.println(returnArea);
			fout.println(returnArea2);
			fout.close();

			saveEncryptionKey(output,encrypt(output));
			System.out.println("Base data saved.");
		}
		catch(Exception e){}
	}

	//Loads GameData
	public void loadGameData()
	{
		String input ="savedata/"+idString+".jokes";

		try
		{
			BufferedReader in = new BufferedReader(new FileReader(input));

			String test=in.readLine();
			//Game Vars
			name=in.readLine();
			rivalName=in.readLine();
			timesBeatRival=Integer.parseInt(in.readLine());
			badges=Integer.parseInt(in.readLine());
			playTimeSecs=Integer.parseInt(in.readLine());
			playTimeMins=Integer.parseInt(in.readLine());
			playTimeHours=Integer.parseInt(in.readLine());
			location.x=Integer.parseInt(in.readLine());
			location.y=Integer.parseInt(in.readLine());
			returnPoint.x=Integer.parseInt(in.readLine());
			returnPoint.y=Integer.parseInt(in.readLine());
			returnPoint2.x=Integer.parseInt(in.readLine());
			returnPoint2.y=Integer.parseInt(in.readLine());
			area=Area.valueOf(in.readLine());
			returnArea=Area.valueOf(in.readLine());
			returnArea2=Area.valueOf(in.readLine());
		}
		catch(Exception e){}

		testEncryption(input);

		System.out.println("Game Data loaded successfully.");
	}

	//Method that explicitly saves Pokemon data
	public static void savePokemon()
	{
		String output ="savedata/"+idString+".pkmn";

		try
		{
			PrintWriter fout=new PrintWriter(new FileWriter(output));

			encryptionKey=0;

			if(VERSION.equals("Peaches"))
				fout.println("PDAE"+idString);
			else
				fout.println("CDAE"+idString);

			for(int i=0; i<partyPokemon.length; i++)
			{
				if(partyPokemon[i]==null)
				{
					fout.println("null");
				}
				else
				{
					//Header
					if(VERSION.equals("Peaches"))
						fout.println("PDAE"+partyPokemon[i].idNumber);
					else
						fout.println("CDAE"+partyPokemon[i].idNumber);

					//Strings
					fout.println(partyPokemon[i].species);
					fout.println(partyPokemon[i].nickname);
					fout.println(partyPokemon[i].originalTrainer);
					fout.println(partyPokemon[i].status);
					fout.println(partyPokemon[i].substatus);
					fout.println(partyPokemon[i].move[0]);
					fout.println(partyPokemon[i].move[1]);
					fout.println(partyPokemon[i].move[2]);
					fout.println(partyPokemon[i].move[3]);

					//Integers
					fout.println(Integer.toBinaryString(partyPokemon[i].level));
						encryptionKey+=partyPokemon[i].level;
					fout.println(Integer.toBinaryString(partyPokemon[i].exp));
						encryptionKey+=partyPokemon[i].exp;
					fout.println(Integer.toBinaryString(partyPokemon[i].health));
						encryptionKey+=partyPokemon[i].health;
					fout.println(Integer.toBinaryString(partyPokemon[i].healthMax));
						encryptionKey+=partyPokemon[i].healthMax;
					fout.println(Integer.toBinaryString(partyPokemon[i].HP_EV));
						encryptionKey+=partyPokemon[i].HP_EV;
					fout.println(Integer.toBinaryString(partyPokemon[i].ATK_EV));
						encryptionKey+=partyPokemon[i].ATK_EV;
					fout.println(Integer.toBinaryString(partyPokemon[i].DEF_EV));
						encryptionKey+=partyPokemon[i].DEF_EV;
					fout.println(Integer.toBinaryString(partyPokemon[i].SPCL_EV));
						encryptionKey+=partyPokemon[i].SPCL_EV;
					fout.println(Integer.toBinaryString(partyPokemon[i].SPEED_EV));
						encryptionKey+=partyPokemon[i].SPEED_EV;
					fout.println(Integer.toBinaryString(partyPokemon[i].HP_IV));
						encryptionKey+=partyPokemon[i].HP_IV;
					fout.println(Integer.toBinaryString(partyPokemon[i].ATK_IV));
						encryptionKey+=partyPokemon[i].ATK_IV;
					fout.println(Integer.toBinaryString(partyPokemon[i].DEF_IV));
						encryptionKey+=partyPokemon[i].DEF_IV;
					fout.println(Integer.toBinaryString(partyPokemon[i].SPCL_IV));
						encryptionKey+=partyPokemon[i].SPCL_IV;
					fout.println(Integer.toBinaryString(partyPokemon[i].SPEED_IV));
						encryptionKey+=partyPokemon[i].SPEED_IV;
					fout.println(Integer.toBinaryString(partyPokemon[i].TRUE_PP[0]));
						encryptionKey+=partyPokemon[i].TRUE_PP[0];
					fout.println(Integer.toBinaryString(partyPokemon[i].TRUE_PP[1]));
					fout.println(Integer.toBinaryString(partyPokemon[i].TRUE_PP[2]));
					fout.println(Integer.toBinaryString(partyPokemon[i].TRUE_PP[3]));
					fout.println(Integer.toBinaryString(partyPokemon[i].TRUE_PPMAX[0]));
						encryptionKey+=partyPokemon[i].TRUE_PPMAX[0];
					fout.println(Integer.toBinaryString(partyPokemon[i].TRUE_PPMAX[1]));
					fout.println(Integer.toBinaryString(partyPokemon[i].TRUE_PPMAX[2]));
					fout.println(Integer.toBinaryString(partyPokemon[i].TRUE_PPMAX[3]));

					//Booleans
					fout.println(partyPokemon[i].IS_TRADED);
				}
			}

			for(int i=0; i<pcPokemon.length; i++)
			{
				if(pcPokemon[i]==null)
				{
					fout.println("null");
				}
				else
				{
					//Header
					if(VERSION.equals("Peaches"))
						fout.println("PDAE"+pcPokemon[i].idNumber);
					else
						fout.println("CDAE"+pcPokemon[i].idNumber);

					//Strings
					fout.println(pcPokemon[i].species);
					fout.println(pcPokemon[i].nickname);
					fout.println(pcPokemon[i].originalTrainer);
					fout.println(pcPokemon[i].status);
					fout.println(pcPokemon[i].substatus);
					fout.println(pcPokemon[i].move[0]);
					fout.println(pcPokemon[i].move[1]);
					fout.println(pcPokemon[i].move[2]);
					fout.println(pcPokemon[i].move[3]);

					//Integers
					fout.println(Integer.toBinaryString(pcPokemon[i].level));
						encryptionKey+=pcPokemon[i].level;
					fout.println(Integer.toBinaryString(pcPokemon[i].exp));
						encryptionKey+=pcPokemon[i].exp;
					fout.println(Integer.toBinaryString(pcPokemon[i].health));
						encryptionKey+=pcPokemon[i].health;
					fout.println(Integer.toBinaryString(pcPokemon[i].healthMax));
						encryptionKey+=pcPokemon[i].healthMax;
					fout.println(Integer.toBinaryString(pcPokemon[i].HP_EV));
						encryptionKey+=pcPokemon[i].HP_EV;
					fout.println(Integer.toBinaryString(pcPokemon[i].ATK_EV));
						encryptionKey+=pcPokemon[i].ATK_EV;
					fout.println(Integer.toBinaryString(pcPokemon[i].DEF_EV));
						encryptionKey+=pcPokemon[i].DEF_EV;
					fout.println(Integer.toBinaryString(pcPokemon[i].SPCL_EV));
						encryptionKey+=pcPokemon[i].SPCL_EV;
					fout.println(Integer.toBinaryString(pcPokemon[i].SPEED_EV));
						encryptionKey+=pcPokemon[i].SPEED_EV;
					fout.println(Integer.toBinaryString(pcPokemon[i].HP_IV));
						encryptionKey+=pcPokemon[i].HP_IV;
					fout.println(Integer.toBinaryString(pcPokemon[i].ATK_IV));
						encryptionKey+=pcPokemon[i].ATK_IV;
					fout.println(Integer.toBinaryString(pcPokemon[i].DEF_IV));
						encryptionKey+=pcPokemon[i].DEF_IV;
					fout.println(Integer.toBinaryString(pcPokemon[i].SPCL_IV));
						encryptionKey+=pcPokemon[i].SPCL_IV;
					fout.println(Integer.toBinaryString(pcPokemon[i].SPEED_IV));
						encryptionKey+=pcPokemon[i].SPEED_IV;
					fout.println(Integer.toBinaryString(pcPokemon[i].TRUE_PP[0]));
						encryptionKey+=pcPokemon[i].TRUE_PP[0];
					fout.println(Integer.toBinaryString(pcPokemon[i].TRUE_PP[1]));
					fout.println(Integer.toBinaryString(pcPokemon[i].TRUE_PP[2]));
					fout.println(Integer.toBinaryString(pcPokemon[i].TRUE_PP[3]));
					fout.println(Integer.toBinaryString(pcPokemon[i].TRUE_PPMAX[0]));
						encryptionKey+=pcPokemon[i].TRUE_PPMAX[0];
					fout.println(Integer.toBinaryString(pcPokemon[i].TRUE_PPMAX[1]));
					fout.println(Integer.toBinaryString(pcPokemon[i].TRUE_PPMAX[2]));
					fout.println(Integer.toBinaryString(pcPokemon[i].TRUE_PPMAX[3]));

					//Booleans
					fout.println(pcPokemon[i].IS_TRADED);

				}
			}

			fout.println(""+encryptionKey);
			fout.close();

			saveEncryptionKey(output,encrypt(output));
			System.out.println("Pokemon File signed and exported.");
		}
		catch(Exception e){e.printStackTrace(); System.out.println("Error in writing Pokemon data.");}
	}

	//Loads Images for pause menu
	public void loadPokemonImages()
	{
		for (int i = 0; i < 6; i++)
    	{
    		if (partyPokemon[i] != null)
			{
				String pokemonType = partyPokemon[i].species.toString();
				char a;
			    if(pokemonType.equalsIgnoreCase("Mr_Mime"))
					pokemonType="Mr_Mime";
				else if(pokemonType.equalsIgnoreCase("Nidoran_M"))
					pokemonType="Nidoran_M";
				else if(pokemonType.equalsIgnoreCase("Nidoran_F"))
					pokemonType="Nidoran_F";
				else
				{
					pokemonType = pokemonType.toLowerCase();
				    pokemonType = pokemonType.replace("_"," ");
				    a = pokemonType.charAt(0);
				    a = Character.toUpperCase(a);
				    pokemonType = a + pokemonType.substring(1,pokemonType.length());
				}
				
		    	URL url = Pokemon.class.getResource("Sprites/pokemon/Right/" + pokemonType + ".png");
		    	playerImages[i] = Toolkit.getDefaultToolkit().createImage(url);
			}
    	}
	}

	//Loads all game data through necessary methods
	public void load()
	{
		System.out.println("Beginning data load...");
		loadTrainerID();
		loadGameData();
		loadObjectives();
		loadHouseInt();
		loadPokemon();
		Inventory.load();
		Pokedex.makeArrays();
		Pokedex.loadFile();
		pauseMenu[3]=name;
		System.out.println("All data loaded");
		System.out.println();
		System.out.println("Creating area...");
		createCurrentArea();
		bgm.loop();
	}

	//Method that loads Pokemon data and tests it for errors or cheats
	public void loadPokemon()
	{
		String input ="savedata/"+idString+".pkmn";

		try
		{
			BufferedReader in = new BufferedReader(new FileReader(input));

			encryptionKey=0;

			Pokemon temp=new Pokemon(Pokemon.Species.BULBASAUR);

			String test=in.readLine();

			if(VERSION.toString().charAt(0)!=test.charAt(0))
			{
				System.out.println("Invalid Pokemon data");
				System.exit(-1);
			}

			for(int i=0; i<partyPokemon.length; i++)
			{
				test=in.readLine();

				if(test.equals("null"))
				{
					test="";
				}
				else
				{
					//Header
					if(VERSION.toString().charAt(0)!=test.charAt(0))
					{
						System.out.println("Invalid Pokemon data");
						System.exit(-1);
					}

					temp=new Pokemon(Pokemon.Species.BULBASAUR);

					//Strings
					temp.species=Pokemon.Species.valueOf(in.readLine());
					temp.nickname=in.readLine();
					temp.originalTrainer=in.readLine();
					temp.status=Pokemon.Status.valueOf(in.readLine());
					temp.substatus=Pokemon.Substatus.valueOf(in.readLine());
					temp.move[0]=Pokemon.Move.valueOf(in.readLine());
					temp.move[1]=Pokemon.Move.valueOf(in.readLine());
					temp.move[2]=Pokemon.Move.valueOf(in.readLine());
					temp.move[3]=Pokemon.Move.valueOf(in.readLine());

					//Integers
					temp.level=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.level;
					temp.exp=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.exp;
					temp.health=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.health;
					temp.healthMax=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.healthMax;
					temp.HP_EV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.HP_EV;
					temp.ATK_EV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.ATK_EV;
					temp.DEF_EV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.DEF_EV;
					temp.SPCL_EV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.SPCL_EV;
					temp.SPEED_EV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.SPEED_EV;
					temp.HP_IV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.HP_IV;
					temp.ATK_IV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.ATK_IV;
					temp.DEF_IV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.DEF_IV;
					temp.SPCL_IV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.SPCL_IV;
					temp.SPEED_IV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.SPEED_IV;
					temp.TRUE_PP[0]=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.TRUE_PP[0];
					temp.TRUE_PP[1]=Integer.parseInt(in.readLine(),2);
					temp.TRUE_PP[2]=Integer.parseInt(in.readLine(),2);
					temp.TRUE_PP[3]=Integer.parseInt(in.readLine(),2);
					temp.TRUE_PPMAX[0]=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.TRUE_PPMAX[0];
					temp.TRUE_PPMAX[1]=Integer.parseInt(in.readLine(),2);
					temp.TRUE_PPMAX[2]=Integer.parseInt(in.readLine(),2);
					temp.TRUE_PPMAX[3]=Integer.parseInt(in.readLine(),2);

					//Booleans
					temp.IS_TRADED=Boolean.parseBoolean(in.readLine());

					partyPokemon[i]=new  Pokemon(temp.species, temp.move[0], temp.move[1], temp.move[2], temp.move[3], temp.level,
					temp.HP_IV,  temp.ATK_IV, temp.DEF_IV, temp.SPCL_IV, temp.SPEED_IV,
					temp.nickname, temp.status, temp.idNumber, temp.originalTrainer);

					partyPokemon[i].setEV("HP",temp.HP_EV);
					partyPokemon[i].setEV("ATK",temp.ATK_EV);
					partyPokemon[i].setEV("DEF",temp.DEF_EV);
					partyPokemon[i].setEV("SPCL",temp.SPCL_EV);
					partyPokemon[i].setEV("SPEED",temp.SPEED_EV);

					partyPokemon[i].health=temp.health;
					partyPokemon[i].healthMax=temp.healthMax;
					partyPokemon[i].exp=temp.exp;

					partyPokemon[i].TRUE_PP[0]=temp.TRUE_PP[0];
					partyPokemon[i].TRUE_PP[1]=temp.TRUE_PP[1];
					partyPokemon[i].TRUE_PP[2]=temp.TRUE_PP[2];
					partyPokemon[i].TRUE_PP[3]=temp.TRUE_PP[3];

					partyPokemon[i].TRUE_PPMAX[0]=temp.TRUE_PPMAX[0];
					partyPokemon[i].TRUE_PPMAX[1]=temp.TRUE_PPMAX[1];
					partyPokemon[i].TRUE_PPMAX[2]=temp.TRUE_PPMAX[2];
					partyPokemon[i].TRUE_PPMAX[3]=temp.TRUE_PPMAX[3];
				}
			}

			for(int i=0; i<pcPokemon.length; i++)
			{
				test=in.readLine();

				if(test.equals("null"))
				{
					test="";
				}
				else
				{
					//Header
					if(VERSION.toString().charAt(0)!=test.charAt(0))
					{
						System.out.println("Invalid Pokemon data");
						System.exit(-1);
					}

					temp=new Pokemon(Pokemon.Species.BULBASAUR);

					//Strings
					temp.species=Pokemon.Species.valueOf(in.readLine());
					temp.nickname=in.readLine();
					temp.originalTrainer=in.readLine();
					temp.status=Pokemon.Status.valueOf(in.readLine());
					temp.substatus=Pokemon.Substatus.valueOf(in.readLine());
					temp.move[0]=Pokemon.Move.valueOf(in.readLine());
					temp.move[1]=Pokemon.Move.valueOf(in.readLine());
					temp.move[2]=Pokemon.Move.valueOf(in.readLine());
					temp.move[3]=Pokemon.Move.valueOf(in.readLine());

					//Integers
					temp.level=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.level;
					temp.exp=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.exp;
					temp.health=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.health;
					temp.healthMax=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.healthMax;
					temp.HP_EV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.HP_EV;
					temp.ATK_EV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.ATK_EV;
					temp.DEF_EV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.DEF_EV;
					temp.SPCL_EV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.SPCL_EV;
					temp.SPEED_EV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.SPEED_EV;
					temp.HP_IV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.HP_IV;
					temp.ATK_IV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.ATK_IV;
					temp.DEF_IV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.DEF_IV;
					temp.SPCL_IV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.SPCL_IV;
					temp.SPEED_IV=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.SPEED_IV;
					temp.TRUE_PP[0]=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.TRUE_PP[0];
					temp.TRUE_PP[1]=Integer.parseInt(in.readLine(),2);
					temp.TRUE_PP[2]=Integer.parseInt(in.readLine(),2);
					temp.TRUE_PP[3]=Integer.parseInt(in.readLine(),2);
					temp.TRUE_PPMAX[0]=Integer.parseInt(in.readLine(),2);
						encryptionKey+=temp.TRUE_PPMAX[0];
					temp.TRUE_PPMAX[1]=Integer.parseInt(in.readLine(),2);
					temp.TRUE_PPMAX[2]=Integer.parseInt(in.readLine(),2);
					temp.TRUE_PPMAX[3]=Integer.parseInt(in.readLine(),2);

					//Booleans
					temp.IS_TRADED=Boolean.parseBoolean(in.readLine());

					pcPokemon[i]=new  Pokemon(temp.species, temp.move[0], temp.move[1], temp.move[2], temp.move[3], temp.level,
					temp.HP_IV,  temp.ATK_IV, temp.DEF_IV, temp.SPCL_IV, temp.SPEED_IV,
					temp.nickname, temp.status, temp.idNumber, temp.originalTrainer);

					pcPokemon[i].setEV("HP",temp.HP_EV);
					pcPokemon[i].setEV("ATK",temp.ATK_EV);
					pcPokemon[i].setEV("DEF",temp.DEF_EV);
					pcPokemon[i].setEV("SPCL",temp.SPCL_EV);
					pcPokemon[i].setEV("SPEED",temp.SPEED_EV);

					pcPokemon[i].health=temp.health;
					pcPokemon[i].healthMax=temp.healthMax;
					pcPokemon[i].exp=temp.exp;

					pcPokemon[i].TRUE_PP[0]=temp.TRUE_PP[0];
					pcPokemon[i].TRUE_PP[1]=temp.TRUE_PP[1];
					pcPokemon[i].TRUE_PP[2]=temp.TRUE_PP[2];
					pcPokemon[i].TRUE_PP[3]=temp.TRUE_PP[3];

					pcPokemon[i].TRUE_PPMAX[0]=temp.TRUE_PPMAX[0];
					pcPokemon[i].TRUE_PPMAX[1]=temp.TRUE_PPMAX[1];
					pcPokemon[i].TRUE_PPMAX[2]=temp.TRUE_PPMAX[2];
					pcPokemon[i].TRUE_PPMAX[3]=temp.TRUE_PPMAX[3];
				}
			}

			int keygen=Integer.parseInt(in.readLine());

			if(encryptionKey!=keygen)
			{
				System.out.println("Data has been manipulated. Exiting.");
				System.exit(-1);
			}
		}
		catch(Exception e)
		{
			System.out.println("Pokemon data has been manipulated or is not present.");
		}

		testEncryption(input);

		System.out.println("Pokemon loaded successfully.");
	}

	public void createMap(String file)
	{
		BufferedReader fin, in;
		file = "Tilemaps/" + file;
		
		try
		{
			fin=new BufferedReader(new FileReader(file));
			for(int i=0; i<currentArea.length; i++)
				for(int j=0; j<currentArea[0].length; j++)
					currentArea[i][j]=Integer.parseInt(fin.readLine());
		}
		catch(Exception e)
		{
			System.out.println("Bad tilemap");
			e.printStackTrace();
		}
	}

	//Checks if it is possible for player to move
	public boolean canMove()
	{
		if(cartographer)
		return true;

		//Begin Trainer Test

		switch(direction)
		{
			case 0:
				for(int i=0; i<numTrainers; i++)
				{
					if(trainer[i] != null && location.x+1==trainer[i].location.x&&location.y==trainer[i].location.y)
						return false;
				}
				break;
			case 90:
				for(int i=0; i<numTrainers; i++)
				{
					if(trainer[i] != null && location.y-1==trainer[i].location.y&&location.x==trainer[i].location.x)
						return false;
				}
				break;
			case 180:
				for(int i=0; i<numTrainers; i++)
				{
					if(trainer[i] != null && location.x-1==trainer[i].location.x&&location.y==trainer[i].location.y)
						return false;
				}
				break;
			case 270:
				for(int i=0; i<numTrainers; i++)
				{
					if(trainer[i] != null && location.y+1==trainer[i].location.y&&location.x==trainer[i].location.x)
						return false;
				}
				break;
		}

		//Begin Overworld Item Test

		switch(direction)
		{
			case 0:
				for(int i=0; i<numItems; i++)
				{
					if(!mapItems[i].found&&mapItems[i] != null && location.x+1==mapItems[i].location.x&&location.y==mapItems[i].location.y)
						return false;
				}
				break;
			case 90:
				for(int i=0; i<numItems; i++)
				{
					if(!mapItems[i].found&&mapItems[i] != null && location.y-1==mapItems[i].location.y&&location.x==mapItems[i].location.x)
						return false;
				}
				break;
			case 180:
				for(int i=0; i<numItems; i++)
				{
					if(!mapItems[i].found&&mapItems[i] != null && location.x-1==mapItems[i].location.x&&location.y==mapItems[i].location.y)
						return false;
				}
				break;
			case 270:
				for(int i=0; i<numItems; i++)
				{
					if(!mapItems[i].found&&mapItems[i] != null && location.y+1==mapItems[i].location.y&&location.x==mapItems[i].location.x)
						return false;
				}
				break;
		}

		int test=0;

		switch(direction)
		{
			case 0:
				if(location.x<getAreaWidth())
				test=currentArea[location.x+1][location.y];
				else
					return false;
				break;
			case 90:
				if(location.y>0)
				test=currentArea[location.x][location.y-1];
				else
					return false;
				break;
			case 180:
				if(location.x>0)
				test=currentArea[location.x-1][location.y];
				else
					return false;
				break;
			case 270:
				if(location.y<getAreaHeight())
				test=currentArea[location.x][location.y+1];
				else
					return false;
				break;

		}

		if (tileSet == OUTDOORS)
		{
			switch(test)
			{
				//Jump Over Cases
				//Recursion is used so that user doesn't
				//jump into objects
				case 137:
				case 84:
					if(direction==270)
					{
						location.y++;
						if(canMove())
						{
							try
							{
								Thread.sleep(250);
								repaint();
							}
							catch(Exception e){}
							return true;
						}
						else
						location.y--;
						return false;
					}
					else
						return false;
				case 139:
				case 87:
					if(direction==180)
					{
						location.x--;
						if(canMove())
						{
							try
							{
								Thread.sleep(250);
								repaint();
							}
							catch(Exception e){}
							return true;
						}
						else
						location.x++;
						return false;
					}
					else
						return false;
				case 141:
				case 89:
					if(direction==0)
					{
						location.x++;
						if(canMove())
						{
							try
							{
								Thread.sleep(250);
								repaint();
							}
							catch(Exception e){}
							return true;
						}
						else
						location.x--;
						return false;
					}
					else
						return false;
				//Standard Cases
				case 0:
				case 23:
				case 27:
				case 157:
				case 187:
				case 4:
				case 40:
				case 42:
				case 5:
				case 6:
				case 85:
				case 9:
				case 90:
				case 144:
				case 226:
				case 239:
				case 161:
					if(surfing)
					{
						surfSong.stop();
						bgm.loop();
					}
					surfing=false;
					return true;
				//Surfing Cases
				case 19:
				case 205:
					if(surfing)
						return true;
				case 1:
				case 10:
				case 12:
				case 13:
				case 14:
				case 15:
				case 16:
				case 8:
				case 204:
				case 206:
				case 207:
				case 208:
				case 209:
				case 210:
				case 211:
					if ((Inventory.hasItem(new Item(Item.Type.HM,1,3)))&&!surfing&&Mechanics.hasSurf(partyPokemon))
					{
						useSurf();
						return true;
					}
					else if(surfing)
						return true;
//					else if(surfing)
//					{
//						surfing=false;
//						surfSong.stop();
//						bgm.loop();
//						return true;
//					}
				default:
					return false;
			}
		}
		else if (tileSet == INDOORS)
		{
			switch(test)
			{
				//Ice Logic
				case 190:
					switch(direction)
					{
						case 0:
							while(location.x<getAreaWidth()-1&&currentArea[location.x+1][location.y]==190)
							{
								location.x++;
								try
								{
									repaint();
									Thread.sleep(100);
								}
								catch(Exception e){}
							}
							break;
						case 90:
							while(location.y>0&&currentArea[location.x][location.y-1]==190)
							{
								location.y--;
								try
								{
									repaint();
									Thread.sleep(100);
								}
								catch(Exception e){}
							}
							break;
						case 180:
							while(location.x>0&&currentArea[location.x-1][location.y]==190)
							{
								location.x--;
								try
								{
									repaint();
									Thread.sleep(100);
								}
								catch(Exception e){}
							}
							break;
						case 270:
							while(location.y<getAreaHeight()&&currentArea[location.x][location.y+1]==190)
							{
								location.y++;
								try
								{
									repaint();
									Thread.sleep(100);
								}
								catch(Exception e){}
							}
							break;
					}
					break;
				case 0:
				case 1:
				case 2:
				case 3:
				case 18:
				case 22:
				case 30:
				case 60:
				case 61:
				case 65:
				case 66:
				case 52:
				case 89:
				case 90:
				case 54:
				case 102:
				case 108:
				case 116:
				case 113:
				case 117:
				case 127:
				case 128:
				case 129:
				case 132:
				case 133:
				case 134:
				case 142:
				case 154:
				case 155:
				case 156:
				case 157:
				case 164:
				case 165:
				case 103:
				case 159:
				case 158:
				case 167:
				case 179:
				case 193:
				case 203:
				case 219:
				case 211:
				case 217:
				case 207:
				case 222:
				case 231:
				case 236:
				case 237:
				case 256:
				case 270:
				case 197:
				case 273:
				case 276:
				case 277:
				case 284:
				case 285:
				case 296:
				case 313:
				case 307:
				case 299:
				case 294:
				case 292:
				case 331:
				case 333:
				case 334:
				case 335:
				case 336:
				case 342:
				case 343:
				case 240:
				case 344:
				case 345:
				case 346:
				case 353:
				case 354:
				case 355:
				case 356:
				case 360:
				case 364:
					if(surfing)
					{
						surfSong.stop();
						bgm.loop();
					}
					surfing=false;
					return true;
				case 244:
					if (surfing)
					return true;
				case 241:
				case 242:
				case 243:
				case 245:
				case 246:
					if ((Inventory.hasItem(new Item(Item.Type.HM,1,1)))&&!surfing&&Mechanics.hasSurf(partyPokemon))
					{
						useSurf();
						return true;
					}
					else if(surfing)
						return true;
				default:
					return false;
			}
		}

		return false;
	}

	//Decrements HP of Poisoned Pokemon
	public void checkPoison()
	{
		int numPokes=0;

		for(int i=0; i<partyPokemon.length; i++)
		{
			if(partyPokemon[i]!=null&&partyPokemon[i].status==Pokemon.Status.PSN)
			{
				partyPokemon[i].health--;
				drawPoison=true;
				if(partyPokemon[i].health<=0)
				{
					partyPokemon[i].health=0;
					partyPokemon[i].status=Pokemon.Status.FNT;
				}
				numPokes++;
			}
			else if(partyPokemon[i]!=null)
				numPokes++;
		}


		if(!Mechanics.hasRemainingPokemon(partyPokemon,numPokes))
		{
			JokemonDriver.area = JokemonDriver.Area.Pokecenter;
			JokemonDriver.location.x = 4;
			JokemonDriver.location.y = 3;
			JokemonDriver.direction = 90;
			JokemonDriver.makeTheArea = true;
			JokemonDriver.performingAction = true;
			JokemonDriver.setTransition(true);
		}
	}

	//Checks Movement
	public void checkMovement()
	{
		if(forceBike)
		{
			bicycling=true;
		}

		try
		{
			if(!cartographer)
			{
				if(!bicycling)
					Thread.sleep(150);
				else
					Thread.sleep(50);

				movingFrame=(movingFrame+1)%2;

				if(movingFrame==0&&moving)
				{
					checkPoison();
				}
				else
				{
					drawPoison=false;
				}
			}
			else
				Thread.sleep(100);
		}
		catch(Exception e){e.printStackTrace();}


		switch(direction)
		{
			case 90:
				if(canMove())
					if(location.y>0)
						location.y--;
				break;
			case 270:
				if(canMove())
					if(location.y<getAreaHeight())
						location.y++;
				break;
			case 180:
				if(canMove())
					if(location.x>0)
						location.x--;
				break;
			case 0:
				if(canMove())
					if(location.x<getAreaWidth())
						location.x++;
				break;
		}

		if(surfing&&area==Area.Recursive_Hot_Springs)
			healPokemonNoPrompt();

		if(repelSteps>1)
		{
			repelSteps--;
		}
		else if(repelSteps==1)
		{
			repelSteps=0;
			moving=false;

			messageBox=new ErrorWindow();
			messageBox.addMessage("Your repel effect wore off!","Uh oh!");

			messageBox.repaint();
			while(messageBox.isVisible())
			{
				try
				{
					Thread.sleep(10);
				}
				catch(Exception e){e.printStackTrace();}
			}

			return;
		}
		else if(tileSet == OUTDOORS && !cartographer&&canEncounter(currentArea[location.x][location.y])&&!alreadyBattled)
			wildEncounter();
		else if(tileSet == INDOORS && !cartographer&&canEncounter(currentArea[location.x][location.y])&&!alreadyBattled)
			wildEncounter();

		if(!cartographer)
		switchMap();
	}

	//Switches Area arrays depending on location
	public void switchMap()
	{
		switch(area)
		{
			case Stringville:
				if (location.y==0 && direction == 90)
				{
					if(location.x==10)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_1;
						location.x=18;
						location.y=59;
						createCurrentArea();
					}
					if(location.x==11)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_1;
						location.x=19;
						location.y=59;
						createCurrentArea();
					}
				}
				if(location.x==49 && direction == 0)
				{
					if(location.y==14||location.y==15)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_0;
						location.x=0;
						if(location.y==14)
							location.y=12;
						else
							location.y=13;
						createCurrentArea();
					}
					if (location.y >= 27 && location.y <= 44 && surfing)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_0;
						location.x=0;
						location.y-=2;
						createCurrentArea();
					}
				}
				if (location.x==5 && location.y == 37)
				{
					pokeCenterTransfer();
				}
				if (location.x == 11 && location.y == 39)
				{
					pokeMartTransfer();
				}
				if (location.x == 15 && location.y == 11)
				{
					transition=false;
					bgm.stop();
					area=Area.YourHouse;
					location.x=5;
					location.y=8;
					createCurrentArea();
				}
				if (location.x == 27 && location.y == 17)
				{
					transition=false;
					bgm.stop();
					area=Area.Rival_House;
					location.x=5;
					location.y=8;
					createCurrentArea();
				}
				if (location.x == 32 && location.y == 9)
				{
					transition=false;
					area=Area.Babbs_Lab;
					location.x=4;
					location.y=12;
					bgm.stop();
					createCurrentArea();
				}
				if ((location.x == 23 && location.y == 7) || (location.x == 35 && location.y == 17))
				{
					if (location.x == 23 && location.y == 7)
					houseInt=0;
					else
					houseInt=1;
					transition=false;
					bgm.stop();
					returnArea = Area.Stringville;
					returnPoint.x = location.x;
					returnPoint.y = location.y+1;
					area=Area.Generic_1;
					location.x=2;
					location.y=7;
					createCurrentArea();
				}
				if (location.x == 30 && location.y == 37)
				{
					houseInt = 0;
					genericTransfer(Area.Gym_1,7,14);
				}
				break;
			case Args_Harbor:
				if (location.x==0 && direction == 180)
				{
					if(location.y==28)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_0;
						location.x=59;
						location.y=12;
						createCurrentArea();
					}
					if(location.y==29)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_0;
						location.x=59;
						location.y=13;
						createCurrentArea();
					}


					if (location.y >= 41 && location.y <= 64 && surfing)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_0;
						location.x=59;
						location.y-=16;
						createCurrentArea();
					}

				}
				if (location.x==3 && location.y == 27)
				{
					pokeCenterTransfer();
				}
				if (location.x==9 && location.y == 45)
				{
					pokeMartTransfer();
				}
				if (location.y == 0 && direction == 90)
				{
					if (location.x >= 3 && location.x <= 48 && surfing)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_8;
						location.y=63;
						createCurrentArea();
					}
				}
				if (location.x == 38 && location.y == 23)
				{
					houseInt=0;
					genericTransfer(Area.Gym_2,12,14);
				}
				if (location.x == 59 && direction == 0)
				{
					if (location.y == 16 || location.y == 17)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_14;
						location.x=0;
						location.y-=2;
						createCurrentArea();
					}
				}
				if (location.x == 25 && location.y == 29)
				{
					houseInt=0;
					genericTransfer(Area.Generic_2, 2, 7);
				}
				if (location.x == 35 && location.y == 31)
				{
					houseInt=1;
					genericTransfer(Area.Generic_2, 2, 7);
				}
				if (location.x == 21 && location.y == 19)
				{
					houseInt=2;
					genericTransfer(Area.Generic_2, 2, 7);
				}
				if (location.x == 17 && location.y == 33)
				{
					houseInt=3;
					genericTransfer(Area.Generic_2, 2, 7);
				}
				if (location.x == 29 && location.y == 17)
				{
					genericTransfer(Area.Lighthouse,5,11);
				}
				if (location.x == 50 && location.y == 57)
				{
					transition=true;
					bgm.stop();
					area=Area.Challenge_Cave;
					location.x=112;
					location.y=115;
					createCurrentArea();
				}
				break;
			case Route_0:
				if (location.x==0 && direction == 180)
				{
					if(location.y==12)
					{
						transition=true;
						bgm.stop();
						area=Area.Stringville;
						location.x=49;
						location.y=14;
						createCurrentArea();
					}
					else if(location.y==13)
					{
						transition=true;
						bgm.stop();
						area=Area.Stringville;
						location.x=49;
						location.y=15;
						createCurrentArea();
					}

					if (location.y >= 25 && location.y <= 42 && surfing)
					{
						transition=true;
						bgm.stop();
						area=Area.Stringville;
						location.x=49;
						location.y+=2;
						createCurrentArea();
					}
				}
				if (location.x==59 && direction == 0)
				{
					if(location.y==12)
					{
						transition=true;
						bgm.stop();
						area=Area.Args_Harbor;
						location.x=0;
						location.y=28;
						createCurrentArea();
					}
					else if(location.y==13)
					{
						transition=true;
						bgm.stop();
						area=Area.Args_Harbor;
						location.x=0;
						location.y=29;
						createCurrentArea();
					}

					if (location.y >= 25 && location.y <= 48 && surfing)
					{
						transition=true;
						bgm.stop();
						area=Area.Args_Harbor;
						location.x=0;
						location.y+=16;
						createCurrentArea();
					}
				}
				break;
			case Route_1:
				if (location.y==59 && direction == 270)
				{
					if(location.x==18)
					{
						transition=true;
						bgm.stop();
						area=Area.Stringville;
						location.x=10;
						location.y=0;
						createCurrentArea();
					}
					else if(location.x==19)
					{
						transition=true;
						bgm.stop();
						area=Area.Stringville;
						location.x=11;
						location.y=0;
						createCurrentArea();
					}
				}
				if (location.x==39 && direction == 0)
				{
					if (location.y == 8 || location.y == 9)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_2;
						location.x=0;
						if(location.y==8)
							location.y=28;
						else
							location.y=29;
						createCurrentArea();
					}
				}
				if (location.x == 4 && direction == 180)
				{
					if (location.y == 20 || location.y == 21)
					{
						transition=false;
						bgm.stop();
						area=Area.H_Border;
						houseInt=0;
						location.x=9;
						location.y-=16;
						createCurrentArea();
					}
				}
				if (location.x == 31 && location.y == 39)
				{
					houseInt=4;
					genericTransfer(Area.Generic_2, 2, 7);
				}
				break;
			case Route_2:
				if (location.x == 0 && direction == 180)
				{
					if (location.y == 28 || location.y == 29)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_1;
						location.x=39;
						if(location.y==28)
							location.y=8;
						else
							location.y=9;
						createCurrentArea();
					}
				}
				if (location.y == 0 && direction == 90)
				{
					if (location.x >= 24 && location.x <= 27)
					{
						transition=true;
						bgm.stop();
						area=Area.Nested_Village;
						location.y=45;
						createCurrentArea();
					}
					if (location.x >= 5 && location.x <= 22 && surfing)
					{
						transition=true;
						bgm.stop();
						area=Area.Nested_Village;
						location.y=45;
						createCurrentArea();
					}
					if (location.x >= 29 && location.x <= 42 && surfing)
					{
						transition=true;
						bgm.stop();
						area=Area.Nested_Village;
						location.y=45;
						createCurrentArea();
					}
				}
				if (location.x == 25 && location.y == 29)
				{
					transition=false;
					bgm.stop();
					area=Area.V_Border;
					houseInt=2;
					location.x=4;
					location.y=7;
					createCurrentArea();
				}
				if (location.y == 15 && direction == 270)
				{
					if (location.x == 25 || location.x == 26)
					{
						transition=false;
						bgm.stop();
						area=Area.V_Border;
						houseInt = 3;
						location.y=1;
						location.x-=21;
						createCurrentArea();
					}
				}
				if (location.x == 13 && location.y == 11)
				{
					houseInt=2;
					genericTransfer(Area.Generic_1, 2, 7);
				}
				break;
			case Pokecenter:
				if (location.y == 8 && direction == 270)
				{
					if (location.x == 6 || location.x == 7)
					{
						transition=false;
						bgm.stop();
						area=returnArea2;
						location.x=returnPoint2.x;
						location.y=returnPoint2.y;
						createCurrentArea();
					}
				}
				break;
			case Nested_Village:
				if (location.y == 45 && direction == 270)
				{
					if (location.x >= 24 && location.x <= 27)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_2;
						location.y=0;
						createCurrentArea();
					}
					if (location.x >= 5 && location.x <= 22 && surfing)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_2;
						location.y=0;
						createCurrentArea();
					}
					if (location.x >= 29 && location.x <= 42 && surfing)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_2;
						location.y=0;
						createCurrentArea();
					}
				}
				if (location.x == 27 && location.y == 23)
				{
					pokeCenterTransfer();
				}
				if (location.x == 33 && location.y == 23)
				{
					pokeMartTransfer();
				}
				if (location.x == 0 && direction == 180)
				{
					if (location.y >= 22 && location.y <= 27)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_3;
						location.x=49;
						location.y+=10;
						createCurrentArea();
					}
				}
				if (location.x == 49 && direction == 0)
				{
					if (location.y >= 37 && location.y <= 43)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_ARRAYINDEXOUTOFBOUNDSEXCEPTION;
						location.x=0;
						location.y-=18;
						createCurrentArea();
					}
				}
				if (location.x == 21 && location.y == 23)
				{
					houseInt=0;
					genericTransfer(Area.Generic_3,2,7);
				}
				if (location.x == 19 && location.y == 29)
				{
					houseInt=1;
					genericTransfer(Area.Generic_3,2,7);
				}
				if (location.x == 25 && location.y == 15)
				{
					houseInt=2;
					genericTransfer(Area.Generic_3,2,7);
				}
				break;
			case Mart:
				if (location.y == 8 && direction == 270)
				{
					if (location.x == 2 || location.x == 3)
					{
						transition=false;
						bgm.stop();
						area=returnArea;
						location.x=returnPoint.x;
						location.y=returnPoint.y;
						createCurrentArea();
					}
				}
				break;
			case Route_3:
				if (location.x == 49 && direction == 0)
				{
					if (location.y >= 32 && location.y <= 37)
					{
						transition=true;
						bgm.stop();
						area=Area.Nested_Village;
						location.x=0;
						location.y-=10;
						createCurrentArea();
					}
				}
				if (location.x == 0 && direction == 180)
				{
					if (location.y >= 8 && location.y <= 9)
					{
						transition=true;
						bgm.stop();
						area=Area.Villa_Del_Joe;
						location.x=75;
						location.y+=44;
						createCurrentArea();
					}
					if (location.y >= 34 && location.y <= 37)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_11;
						location.x=69;
						location.y-=28;
						createCurrentArea();
					}
				}
				if (location.x == 22 && location.y == 9)
				{
					transition=true;
					bgm.stop();
					area=Area.Public_Cave;
					location.x=8;
					location.y=90;
					createCurrentArea();
				}
				if (location.x == 37 && location.y == 5)
				{
					transition=true;
					bgm.stop();
					area=Area.Public_Cave;
					location.x=25;
					location.y=87;
					createCurrentArea();
				}
				break;
			case Public_Cave:
				if (location.x == 8 && location.y == 91)
				{
					transition=true;
					bgm.stop();
					area=Area.Route_3;
					location.x=22;
					location.y=10;
					createCurrentArea();
				}
				if (location.x == 25 && location.y == 88)
				{
					transition=true;
					bgm.stop();
					area=Area.Route_3;
					location.x=37;
					location.y=6;
					createCurrentArea();
				}
				if (location.x == 78 && location.y == 83)
				{
					transition=true;
					bgm.stop();
					area=Area.Route_6;
					location.x=8;
					location.y=10;
					createCurrentArea();
				}
				if (location.x == 50 && location.y == 64)
				{
					transition=true;
					bgm.stop();
					area=Area.Route_10;
					location.x=8;
					location.y=34;
					createCurrentArea();
				}
				if (location.x == 11 && direction == 90 && location.y == 70)
				{
					location.y=63;
				}
				if (location.x == 11 && direction == 270 && location.y == 63)
				{
					location.y=70;
				}
				break;
			case Villa_Del_Joe:
				if (location.x == 75 && direction == 0)
				{
					if (location.y == 52 || location.y == 53)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_3;
						location.x=0;
						location.y-=44;
						createCurrentArea();
					}
					if (location.y == 10 || location.y == 11)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_4;
						location.x=0;
						location.y+=78;
						createCurrentArea();
					}
				}
				if (location.x == 25 && location.y == 31)
				{
					pokeCenterTransfer();
				}
				if (location.x == 63 && location.y == 35)
				{
					houseInt=3;
					genericTransfer(Area.Generic_1, 2, 7);
				}
				if (location.x == 51 && location.y == 47)
				{
					houseInt=4;
					genericTransfer(Area.Generic_1, 2, 7);
				}
				if (location.x == 51 && location.y == 57)
				{
					houseInt=5;
					genericTransfer(Area.Generic_1, 2, 7);
				}
				if (location.x == 51 && location.y == 23)
				{
					houseInt=6;
					genericTransfer(Area.Generic_1, 2, 7);
				}
				if (location.x == 63 && location.y == 9)
				{
					houseInt=7;
					genericTransfer(Area.Generic_1, 2, 7);
				}
				if (location.x == 43 && location.y == 1)
				{
					houseInt=8;
					genericTransfer(Area.Generic_1, 2, 7);
				}
				if (location.x == 31 && location.y == 1)
				{
					houseInt=9;
					genericTransfer(Area.Generic_1, 2, 7);
				}
				if (location.x == 23 && location.y == 53)
				{
					houseInt=10;
					genericTransfer(Area.Generic_1, 2, 7);
				}
				if (location.x == 29 && location.y == 31)
				{
					houseInt=11;
					genericTransfer(Area.Generic_1, 2, 7);
				}
				if (location.x == 8 && location.y == 5)
				{
					houseInt = 0;
					genericTransfer(Area.Gym_3,9,19);
				}
				if (location.x == 5 && location.y == 49)
				{
					genericTransfer(Area.J_Inc_Radio_Tower, 4, 9);
				}
				if (location.x == 10 && location.y == 51)
				{
					genericTransfer(Area.J_Inc_Building, 9, 14);
				}
				if (location.x == 50 && location.y == 41)
				{
					genericTransfer(Area.MegaMart,11,13);
				}
				break;
			case YourHouse:
			case Rival_House:
				if (location.y == 8 && direction == 270)
				{
					if (location.x == 5 || location.x == 6)
					{
						if (area == Area.YourHouse)
						{
							location.x=15;
							location.y=12;
						}
						else
						{
							location.x=27;
							location.y=18;
						}
						area=Area.Stringville;
						bgm.stop();
						createCurrentArea();
					}
				}
				break;
			case Route_4:
				if (location.x == 0 && direction == 180)
				{
					if (location.y == 88 || location.y == 89)
					{
						transition=true;
						bgm.stop();
						area=Area.Villa_Del_Joe;
						location.x=75;
						location.y-=78;
						createCurrentArea();
					}

				}
				if (location.y == 0 && direction == 90)
				{
					if (location.x >= 18 && location.x <= 21)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_5;
						location.y=39;
						location.x+=4;
						createCurrentArea();
					}
				}
				if (location.x == 18 && location.y == 77)
				{
					transition=false;
					bgm.stop();
					area=Area.V_Border;
					houseInt=0;
					location.x=4;
					location.y=7;
					createCurrentArea();
				}
				if (location.y == 73 && direction == 270)
				{
					if (location.x == 18 || location.x == 19)
					{
						transition=false;
						bgm.stop();
						area=Area.V_Border;
						houseInt=0;
						location.x-=14;
						location.y=1;
						createCurrentArea();
					}
				}
				break;
			case Route_5:
				if (location.y == 39 && direction == 270)
				{
					if (location.x >= 22 && location.x <= 25)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_4;
						location.x-=4;
						location.y=0;
						createCurrentArea();
					}
				}
				if (location.y == 0 && direction == 90)
				{
					if (location.x >= 60 && location.x <= 65)
					{
						transition=true;
						bgm.stop();
						area=Area.Mount_Java;
						location.x-=50;
						location.y=49;
						createCurrentArea();
					}
				}
				if (location.x == 62 && location.y == 5)
				{
					transition=false;
					bgm.stop();
					area=Area.V_Border;
					houseInt=1;
					location.x=4;
					location.y=7;
					createCurrentArea();
				}
				if (location.y == 1 && direction == 270)
				{
					if (location.x == 62 || location.x == 63)
					{
						transition=false;
						bgm.stop();
						area=Area.V_Border;
						houseInt=1;
						location.x-=58;
						location.y=1;
						createCurrentArea();
					}
				}
				if (location.x == 10 && location.y == 5)
				{
					transition=true;
					bgm.stop();
					area=Area.Class_Cave;
					location.x=9;
					location.y=22;
					createCurrentArea();
				}
				if (location.x == 32 && location.y == 3)
				{
					transition=true;
					bgm.stop();
					area=Area.Class_Cave;
					location.x=22;
					location.y=24;
					createCurrentArea();
				}
				break;
			case Class_Cave:
				if (location.x == 9 && location.y == 23)
				{
					transition=true;
					bgm.stop();
					area=Area.Route_5;
					location.x=10;
					location.y=6;
					createCurrentArea();
				}
				if (location.x == 22 && location.y == 25)
				{
					transition=true;
					bgm.stop();
					area=Area.Route_5;
					location.x=32;
					location.y=4;
					createCurrentArea();
				}
				break;
			case Mount_Java:
				if (location.y == 49 && direction == 270)
				{
					if (location.x >= 10 && location.x <= 18)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_5;
						location.x+=50;
						location.y=0;
						createCurrentArea();
					}
				}
				if (location.x == 49 && direction == 0)
				{
					if (location.y == 20 || location.y == 21)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_9;
						location.x=0;
						location.y-=2;
						createCurrentArea();
					}
				}
				if (location.x == 13 && location.y == 29)
				{
					pokeCenterTransfer();
				}
				if (location.x == 19 && location.y == 29)
				{
					pokeMartTransfer();
				}
				if (location.x == 42 && direction == 180)
				{
					if (location.y == 20 || location.y == 21)
					{
						transition=false;
						bgm.stop();
						area=Area.H_Border;
						houseInt=1;
						location.x=9;
						location.y-=16;
						createCurrentArea();
					}
				}
				if (location.x == 37 && direction == 0)
				{
					if (location.y == 20 || location.y == 21)
					{
						transition=false;
						bgm.stop();
						area=Area.H_Border;
						houseInt=1;
						location.x=0;
						location.y-=16;
						createCurrentArea();
					}
				}
				if (location.x == 23 && location.y == 39)
				{
					houseInt=3;
					genericTransfer(Area.Generic_3,2,7);
				}
				if (location.x == 27 && location.y == 19)
				{
					houseInt=4;
					genericTransfer(Area.Generic_3,2,7);
				}
				if (location.x == 25 && location.y == 15)
				{
					houseInt=5;
					genericTransfer(Area.Generic_3,2,7);
				}
				if (location.x == 34 && location.y == 7)
				{
					houseInt = 0;
					genericTransfer(Area.Gym_4,9,19);
				}
				if (location.x == 6 && location.y == 7)
				{
					transition=true;
					bgm.stop();
					area=Area.Java_Cave;
					location.x=15;
					location.y=22;
					createCurrentArea();
				}
				if (location.x == 16 && location.y == 11)
				{
					transition=true;
					bgm.stop();
					area=Area.Java_Cave;
					location.x=25;
					location.y=21;
					createCurrentArea();
				}
				break;
			case Java_Cave:
				if (location.x == 15 && location.y == 23)
				{
					transition=true;
					bgm.stop();
					area=Area.Mount_Java;
					location.x=6;
					location.y=8;
					createCurrentArea();
				}
				if (location.x == 25 && location.y == 22)
				{
					transition=true;
					bgm.stop();
					area=Area.Mount_Java;
					location.x=16;
					location.y=12;
					createCurrentArea();
				}
				if (location.x == 6 && location.y == 16)
				{
					transition=true;
					bgm.stop();
					area=Area.Route_7;
					location.x=36;
					location.y=4;
					createCurrentArea();
				}
				break;
			case Route_6:
				if (location.x == 59 && direction == 0)
				{
					if ((location.y >= 12 && location.y <= 15) || (location.y >= 33 && location.y <= 39))
					{
						transition=true;
						bgm.stop();
						area=Area.Streamreader_Hotel;
						location.x=0;
						createCurrentArea();
					}
				}
				if (location.y == 43 && direction == 270)
				{
					if (location.x >= 26 && location.x <= 29)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_8;
						location.x+=22;
						location.y = 0;
						createCurrentArea();
					}
				}
				if (location.x == 0 && direction == 180)
				{
					if (location.y >= 33 && location.y <= 39)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_ARRAYINDEXOUTOFBOUNDSEXCEPTION;
						location.x=49;
						location.y-=14;
						createCurrentArea();
					}
				}
				if (location.x == 7 && location.y == 21)
				{
					pokeCenterTransfer();
				}
				if (location.x == 8 && location.y == 9)
				{
					transition=true;
					bgm.stop();
					area=Area.Public_Cave;
					location.x=78;
					location.y=82;
					createCurrentArea();
				}
				break;
			case Streamreader_Hotel:
				if (location.x == 0 &&  direction == 180)
				{
					if ((location.y >= 12 && location.y <= 15) || (location.y >= 33 && location.y <= 39))
					{
						transition=true;
						bgm.stop();
						area=Area.Route_6;
						location.x=59;
						createCurrentArea();
					}
				}
				if (location.x == 22 && location.y == 5)
				{
					houseInt = 0;
					genericTransfer(Area.Gym_5,9,19);
				}
				if (location.x == 15 && location.y == 27)
				{
					genericTransfer(Area.Generic_4,2,7);
					houseInt = 0;
				}
				if (location.x == 33 && location.y == 29)
				{
					genericTransfer(Area.Generic_4,2,7);
					houseInt = 1;
				}
				if (location.x == 25 && location.y == 25)
				{
					pokeCenterTransfer();
				}
				if (location.x == 20 && location.y == 25)
				{
					pokeMartTransfer();
				}
				if (location.x == 23 && location.y == 25)
				{
					genericTransfer(Area.Hotel_Lobby,5,11);
				}
				break;
			case Hotel_Lobby:
				if (location.y == 11 && direction == 270)
				{
					if (location.x == 5 || location.x == 6)
					{
						genericReturn();
					}
				}
				break;
			case Route_8:
				if (location.y == 0 && direction == 90)
				{
					if (location.x >= 48 && location.x <= 51)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_6;
						location.x-=22;
						location.y = 43;
						createCurrentArea();
					}
				}
				if (location.y == 63 && direction == 270)
				{
					if (location.x >= 3 && location.x <= 63 && surfing)
					{
						transition=true;
						bgm.stop();
						area=Area.Args_Harbor;
						location.y=0;
						createCurrentArea();
					}
				}
				if (location.x == 23 && location.y == 17)
				{
					houseInt=10;
					genericTransfer(Area.Generic_2, 2, 7);
				}
				if (location.x == 17 && location.y == 23)
				{
					houseInt=11;
					genericTransfer(Area.Generic_2, 2, 7);
				}
				break;
			case Route_ARRAYINDEXOUTOFBOUNDSEXCEPTION:
				if (location.x == 0 && direction == 180)
				{
					if (location.y >= 19 && location.y <= 25)
					{
						transition=true;
						bgm.stop();
						area=Area.Nested_Village;
						location.x=49;
						location.y+=18;
						createCurrentArea();
					}
				}
				if (location.x == 49 && direction == 0)
				{
					if (location.y >= 19 && location.y <= 25)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_6;
						location.x=0;
						location.y+=14;
						createCurrentArea();
					}
				}
				if (location.x == 24 && location.y == 13)
				{
					transition=true;
					bgm.stop();
					area=Area.Articuno_Cave;
					location.x=10;
					location.y=16;
					createCurrentArea();
				}
				break;
			case Articuno_Cave:
				if (location.x == 10 && location.y == 17)
				{
					transition=true;
					bgm.stop();
					area=Area.Route_ARRAYINDEXOUTOFBOUNDSEXCEPTION;
					location.x=24;
					location.y=14;
					createCurrentArea();
				}
				break;
			case Route_9:
				if (location.x == 0 && direction == 180)
				{
					if (location.y == 18 || location.y == 19)
					{
						transition=true;
						bgm.stop();
						area=Area.Mount_Java;
						location.x=49;
						location.y+=2;
						createCurrentArea();
					}
				}
				if (location.y == 89 && direction == 270)
				{
					if (location.x == 14 || location.x == 15)
					{
						transition=true;
						bgm.stop();
						area=Area.Recursive_Hot_Springs;
						location.y=0;
						createCurrentArea();
					}
				}
				if (location.x == 5 && location.y == 55)
				{
					houseInt=13;
					genericTransfer(Area.Generic_3,2,7);
				}
				if (location.x == 19 && location.y == 5)
				{
					houseInt=14;
					genericTransfer(Area.Generic_3,2,7);
				}
				break;
			case Recursive_Hot_Springs:
				if (location.y == 0 && direction == 90)
				{
					if (location.x == 14 || location.x == 15)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_9;
						location.y=89;
						createCurrentArea();
					}
				}
				if (location.y == 59 && direction == 270)
				{
					if (location.x >= 16 && location.x <= 21)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_10;
						location.y=0;
						createCurrentArea();
					}
				}
				if (location.x == 23 && location.y == 15)
				{
					pokeMartTransfer();
				}
				if (location.x == 9 && direction == 0)
				{
					if (location.y == 50 || location.y == 51)
					{
						transition=false;
						bgm.stop();
						area=Area.H_Border;
						houseInt=2;
						location.x=0;
						location.y-=46;
						createCurrentArea();
					}
				}
				if (location.x == 14 && direction == 180)
				{
					if (location.y == 50 || location.y == 51)
					{
						transition=false;
						bgm.stop();
						area=Area.H_Border;
						houseInt=2;
						location.x=9;
						location.y-=46;
						createCurrentArea();
					}
				}
				if (location.x == 23 && direction == 0)
				{
					if (location.y == 50 || location.y == 51)
					{
						transition=false;
						bgm.stop();
						area=Area.H_Border;
						houseInt=3;
						location.x=0;
						location.y-=46;
						createCurrentArea();
					}
				}
				if (location.x == 28 && direction == 180)
				{
					if (location.y == 50 || location.y == 51)
					{
						transition=false;
						bgm.stop();
						area=Area.H_Border;
						houseInt=3;
						location.x=9;
						location.y-=46;
						createCurrentArea();
					}
				}
				if (location.x == 24 && location.y == 35)
				{
					houseInt = 0;
					genericTransfer(Area.Gym_6,9,19);
				}
				if (location.x == 13 && location.y == 15)
				{
					houseInt=6;
					genericTransfer(Area.Generic_3,2,7);
				}
				if (location.x == 13 && location.y == 19)
				{
					houseInt=7;
					genericTransfer(Area.Generic_3,2,7);
				}
				if (location.x == 23 && location.y == 21)
				{
					houseInt=8;
					genericTransfer(Area.Generic_3,2,7);
				}
				break;
			case Route_10:
				if (location.y == 0 && direction == 90)
				{
					if (location.x >= 16 && location.x <= 21)
					{
						transition=true;
						bgm.stop();
						area=Area.Recursive_Hot_Springs;
						location.y=59;
						createCurrentArea();
					}
				}
				if (location.x == 8 && location.y == 33)
				{
					transition=true;
					bgm.stop();
					area=Area.Public_Cave;
					location.x=50;
					location.y=63;
					createCurrentArea();
				}
				break;
			case Route_11:
				if (location.x == 69 && direction == 0)
				{
					if (location.y >= 6 && location.y <= 9)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_3;
						location.x=0;
						location.y+=28;
						createCurrentArea();
					}
				}
				if (location.x == 0 && direction == 180)
				{
					if (location.y >= 2 && location.y <= 5)
					{
						transition=true;
						bgm.stop();
						area=Area.Enumville;
						location.x=49;
						createCurrentArea();
					}
				}
				break;
			case Enumville:
				if (location.x == 49 && direction == 0)
				{
					if (location.y >= 2 && location.y <= 5)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_11;
						location.x=0;
						createCurrentArea();
					}
				}
				if (location.x == 0 && direction == 180)
				{
					if (location.y == 18 || location.y == 19)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_12;
						location.y+=42;
						location.x=49;
						createCurrentArea();
					}
				}
				if (location.x == 5 && location.y == 15)
				{
					houseInt=5;
					genericTransfer(Area.Generic_2, 2, 7);
				}
				if (location.x == 31 && location.y == 15)
				{
					houseInt=6;
					genericTransfer(Area.Generic_2, 2, 7);
				}
				if (location.x == 12 && location.y == 19)
				{
					pokeCenterTransfer();
				}
				if (location.x == 18 && location.y == 19)
				{
					pokeMartTransfer();
				}
				break;
			case Route_12:
				if (location.x == 49 && direction == 0)
				{
					if (location.y == 60 || location.y == 61)
					{
						transition=true;
						bgm.stop();
						area=Area.Enumville;
						location.y-=42;
						location.x=0;
						createCurrentArea();
					}
				}
				if (location.y == 0 && direction == 90)
				{
					if (location.x == 22 || location.x == 23)
					{
						transition=true;
						bgm.stop();
						area=Area.Polymorph_Town;
						location.y=59;
						createCurrentArea();
					}
				}
				if (location.x == 0 && direction == 180)
				{
					if (location.y >= 19 && location.y <= 30)
					{
						transition=true;
						bgm.stop();
						if (VERSION.equalsIgnoreCase("Peaches"))
						area=Area.Peach_City;
						else
						area=Area.Cream_City;
						location.y-=2;
						location.x=79;
						createCurrentArea();
					}
					if (location.y == 35)
					{
						surfing=false;
						transition=true;
						bgm.stop();
						area=Area.Slipspace;
						location.y-=25;
						location.x=19;
						createCurrentArea();
					}
				}
				if (location.x == 12 && location.y == 26)
				{
					transition=true;
					bgm.stop();
					area=Area.Primal_Cave;
					location.x=10;
					location.y=16;
					createCurrentArea();
				}
				break;
			case Primal_Cave:
				if (location.x == 10 && location.y == 17)
				{
					transition=true;
					bgm.stop();
					area=Area.Route_12;
					location.x=12;
					location.y=27;
					createCurrentArea();
				}
				break;
			case Polymorph_Town:
				if (location.y == 59 && direction == 270)
				{
					if (location.x == 22 || location.x == 23)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_12;
						location.y=0;
						createCurrentArea();
					}
				}
				if (location.x == 31 && location.y == 19)
				{
					pokeCenterTransfer();
				}
				if (location.x == 27 && location.y == 17)
				{
					pokeMartTransfer();
				}
				if (location.x == 31 && location.y == 29)
				{
					houseInt=7;
					genericTransfer(Area.Generic_2, 2, 7);
				}
				if (location.x == 33 && location.y == 35)
				{
					houseInt=8;
					genericTransfer(Area.Generic_2, 2, 7);
				}
				if (location.x == 15 && location.y == 37)
				{
					houseInt=9;
					genericTransfer(Area.Generic_2, 2, 7);
				}
				if (location.x == 22 && location.y == 13)
				{
					houseInt=0;
					genericTransfer(Area.Gym_7, 9, 19);
				}
				if (location.x == 27 && location.y == 53)
				{
					transition=true;
					bgm.stop();
					area=Area.Diglett_Cave;
					location.x=8;
					location.y=11;
					createCurrentArea();
				}
				break;
			case Diglett_Cave:
				if (location.x == 8 && location.y == 12)
				{
					transition=true;
					bgm.stop();
					area=Area.Polymorph_Town;
					location.x=27;
					location.y=54;
					createCurrentArea();
				}
				if (location.x == 20 && location.y == 49)
				{
					transition=true;
					bgm.stop();
					area=Area.Route_13;
					location.x=18;
					location.y=8;
					createCurrentArea();
				}
				break;
			case Binary_City:
				if (location.x == 0 && direction == 180)
				{
					if (location.y == 20 || location.y == 21)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_13;
						location.x=59;
						createCurrentArea();
					}
				}
				if (location.x == 45 && location.y == 25)
				{
					pokeCenterTransfer();
				}
				if (location.x == 51 && location.y == 25)
				{
					pokeMartTransfer();
				}
				if (location.x == 59 && direction == 0)
				{
					if (location.y == 20 || location.y == 21)
					{
						transition=false;
						bgm.stop();
						area=Area.H_Border;
						houseInt=0;
						location.x=0;
						location.y-=16;
						createCurrentArea();
					}
				}
				if (location.x == 13 && location.y == 17)
				{
					houseInt=9;
					genericTransfer(Area.Generic_3,2,7);
				}
				if (location.x == 21 && location.y == 25)
				{
					houseInt=10;
					genericTransfer(Area.Generic_3,2,7);
				}
				if (location.x == 37 && location.y == 17)
				{
					houseInt=11;
					genericTransfer(Area.Generic_3,2,7);
				}
				if (location.x == 17 && location.y == 7)
				{
					houseInt=12;
					genericTransfer(Area.Generic_3,2,7);
				}
				if (location.x == 24 && location.y == 41)
				{
					houseInt=0;
					genericTransfer(Area.Gym_8, 9, 19);
				}
				break;
			case Route_13:
				if (location.x == 59 && direction == 0)
				{
					if (location.y == 20 || location.y == 21)
					{
						transition=true;
						bgm.stop();
						area=Area.Binary_City;
						location.x=0;
						createCurrentArea();
					}
				}
				if (location.x == 2 && location.y == 2 && direction == 180)
				{
					direction=0;
					transition=true;
					bgm.stop();
					area=Area.Intville;
					location.x=0;
					location.y=10;
					createCurrentArea();
				}
				if (location.x == 18 && location.y == 7)
				{
					transition=true;
					bgm.stop();
					area=Area.Diglett_Cave;
					location.x=20;
					location.y=48;
					createCurrentArea();
				}
				break;
			case Route_14:
				if (location.x == 0 && direction == 180)
				{
					if (location.y == 14 || location.y == 15)
					{
						transition=true;
						bgm.stop();
						area=Area.Args_Harbor;
						location.x=59;
						location.y+=2;
						createCurrentArea();
					}
				}
				if (location.x == 30 && location.y == 5)
				{
					genericTransfer(Area.Power_Plant,12,19);
				}
				break;
			case Power_Plant:
				if (location.y == 19 && direction == 270)
				{
					if (location.x == 12 || location.x == 13)
					{
						genericReturn();
					}
				}
				break;
			case Route_7:
				if (location.x == 3 && direction == 0)
				{
					if (location.y == 10 || location.y == 11)
					{
						transition=true;
						bgm.stop();
						area=Area.Null_Zone;
						location.x=0;
						location.y+=2;
						createCurrentArea();
					}
				}
				if (location.x == 0 && direction == 180)
				{
					if (location.y == 44 || location.y == 45)
					{
						transition=true;
						bgm.stop();
						area=Area.Champions_Walk;
						location.x=99;
						location.y-=10;
						createCurrentArea();
					}
				}
				if (location.x == 36 && location.y == 3)
				{
					transition=true;
					bgm.stop();
					area=Area.Java_Cave;
					location.x=6;
					location.y=15;
					createCurrentArea();
				}
				if (location.x == 32 && direction == 180)
				{
					if (location.y == 8 || location.y == 9)
					{
						transition=false;
						bgm.stop();
						area=Area.Rocket_Right_Tower;
						location.x=18;
						location.y-=3;
						createCurrentArea();
					}
				}
				if (location.x == 29 && location.y == 15)
				{
					transition=false;
					bgm.stop();
					area=Area.Rocket_Right_Tower;
					location.x=9;
					location.y=14;
					createCurrentArea();
				}
				if (location.x == 18 && direction == 180)
				{
					if (location.y == 18 || location.y == 19)
					{
						transition=false;
						bgm.stop();
						area=Area.Rocket_Left_Tower;
						location.x=14;
						location.y-=11;
						createCurrentArea();
					}
				}
				if (location.x == 15 && location.y == 21)
				{
					transition=false;
					bgm.stop();
					area=Area.Rocket_Left_Tower;
					location.x=8;
					location.y=15;
					createCurrentArea();
				}
				if (location.x == 24 && location.y == 19)
				{
					transition=false;
					bgm.stop();
					area=Area.Rocket_Central_Tower;
					location.x=12;
					location.y=20;
					createCurrentArea();
				}
				break;
			case Rocket_Right_Tower:
				if (location.x == 18 && direction == 0)
				{
					if (location.y == 5 || location.y == 6)
					{
						transition=false;
						bgm.stop();
						area=Area.Route_7;
						location.x=32;
						location.y+=3;
						createCurrentArea();
					}
				}
				if (location.y == 14 && direction == 270)
				{
					if (location.x == 9 || location.x == 10)
					{
						transition=false;
						bgm.stop();
						area=Area.Route_7;
						location.x=29;
						location.y=16;
						createCurrentArea();
					}
				}
				if (location.x == 17 && location.y == 11)
					location.y+=19;
				else if (location.x == 17 && location.y == 30)
					location.y-=19;
				else if (location.x == 4 && location.y == 3)
					location.y+=19;
				else if (location.x == 4 && location.y == 22)
					location.y-=19;
				break;
			case Rocket_Left_Tower:
				if (location.x == 14 && direction == 0)
				{
					if (location.y == 7 || location.y == 8)
					{
						transition=false;
						bgm.stop();
						area=Area.Route_7;
						location.x=18;
						location.y+=11;
						createCurrentArea();
					}
				}
				if (location.y == 15 && direction == 270)
				{
					if (location.x == 8 || location.x == 9)
					{
						transition=false;
						bgm.stop();
						area=Area.Route_7;
						location.x=15;
						location.y=22;
						createCurrentArea();
					}
				}
				if (location.x == 6 && location.y == 9)
					location.y+=20;
				else if (location.x == 6 && location.y == 29)
					location.y-=20;

				if (location.x == 3 && location.y == 14)
					location.y+=20;
				else if (location.x == 3 && location.y == 34)
					location.y-=20;

				if (location.x == 6 && location.y == 34)
					location.y+=19;
				else if (location.x == 6 && location.y == 53)
					location.y-=19;

				if (location.x == 13 && location.y == 34)
					location.y+=19;
				else if (location.x == 13 && location.y == 53)
					location.y-=19;

				break;
			case Rocket_Central_Tower:
				if (location.x == 5 && location.y == 5)
					location.y+=22;
				else if (location.x == 5 && location.y == 27)
					location.y-=22;
				if (location.y == 20 && direction == 270)
				{
					if (location.x == 12 || location.x == 13)
					{
						transition=false;
						bgm.stop();
						area=Area.Route_7;
						location.x=24;
						location.y=20;
						createCurrentArea();
					}
				}
				break;
			case Null_Zone:
				if (location.x == 0 && direction == 180)
				{
					if (location.y == 12 || location.y == 13)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_7;
						location.x=3;
						location.y-=2;
						createCurrentArea();
					}
				}
				if (location.x == 8 && location.y == 17)
				{
					pokeMartTransfer();
				}
				break;
			case Champions_Walk:
				if (location.x == 99 && direction == 0)
				{
					if (location.y == 34 || location.y == 35)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_7;
						location.x=0;
						location.y+=10;
						createCurrentArea();
					}
				}
				if (location.y == 0 && direction == 90)
				{
					if (location.x == 12 || location.x == 13)
					{
						transition=true;
						bgm.stop();
						area=Area.Victory_Road;
						location.x+=4;
						location.y=59;
						createCurrentArea();
					}
				}
				if (location.x == 50 && location.y == 19)
				{
					transition=true;
					bgm.stop();
					area=Area.Moltres_Cave;
					location.x=10;
					location.y=16;
					createCurrentArea();
				}
				if (location.x == 15 && location.y == 13)
				{
					pokeCenterTransfer();
				}
				if (location.x == 9 && location.y == 13)
				{
					pokeMartTransfer();
				}
				if (location.x == 91 && location.y == 33)
				{
					transition=false;
					bgm.stop();
					area=Area.V_Border;
					houseInt=4;
					location.x=4;
					location.y=7;
					createCurrentArea();
				}
				if (location.y == 29 && direction == 270)
				{
					if (location.x == 91 || location.x == 92)
					{
						transition=false;
						bgm.stop();
						area=Area.V_Border;
						houseInt=4;
						location.x-=87;
						location.y=1;
						createCurrentArea();
					}
				}
				break;
			case Moltres_Cave:
				if (location.x == 10 && location.y == 17)
				{
					transition=true;
					bgm.stop();
					area=Area.Champions_Walk;
					location.x=50;
					location.y=20;
					createCurrentArea();
				}
				break;
			case Victory_Road:
				if (location.y == 59 && direction == 270)
				{
					if (location.x == 16 || location.x == 17)
					{
						transition=true;
						bgm.stop();
						area=Area.Champions_Walk;
						location.x-=4;
						location.y=0;
						createCurrentArea();
					}
				}
				if (location.y == 33 && direction == 270)
				{
					if (location.x == 15 || location.x == 16)
					{
						transition=false;
						bgm.stop();
						area=Area.Victory_Road_Cave;
						location.x=35;
						location.y=16;
						createCurrentArea();
					}
				}
				if (location.x == 16 && location.y == 47)
				{
					transition=false;
					bgm.stop();
					area=Area.Victory_Road_Cave;
					location.x=15;
					location.y=82;
					createCurrentArea();
				}
				if (location.y == 19 && direction == 90)
				{
					if (location.x == 15 || location.x == 16)
					{
						transition=false;
						bgm.stop();
						area=Area.Elite_4;
						location.x+=13;
						location.y=116;
						createCurrentArea();
					}
				}
				break;
			case Elite_4:
				if (location.y == 116 && direction == 270)
				{
					if (location.x == 28 || location.x == 29)
					{
						transition=false;
						bgm.stop();
						area=Area.Victory_Road;
						location.x-=13;
						location.y=20;
						createCurrentArea();
					}
				}
				if (location.x == 38 && location.y == 101)
				{
					location.x =29;
					location.y = 96;
				}
				if (location.x == 27 || location.x == 28)
				{
					if (location.y == 77&&!trainer[0].hostile)
					location.y = 70;
					if (location.y == 51&&!trainer[1].hostile)
					location.y = 46;
					if (location.y == 26&&!trainer[2].hostile)
					location.y = 20;
					if (location.y == 3&&!trainer[3].hostile)
					{
						location.x += 22;
						location.y = 24;
					}
				}
				if (location.y == 7 && direction == 90&&objectiveComplete[10])
				{
					if (location.x == 49 || location.x == 50)
					{
						transition=false;
						bgm.stop();
						if (VERSION.equalsIgnoreCase("Peaches"))
						area=Area.Peach_City;
						else
						area=Area.Cream_City;
						location.x=49;
						location.y=30;
						createCurrentArea();
					}
				}
				break;
			case Peach_City:
			case Cream_City:
				if (location.x == 79 && direction == 0)
				{
					if (location.y >= 17 && location.y <= 28)
					{
						transition=true;
						bgm.stop();
						area=Area.Route_12;
						location.y+=2;
						location.x=0;
						createCurrentArea();
					}
				}
				if (location.x == 49 && location.y == 29)
				{
					pokeCenterTransfer();
				}
				if (location.x == 55 && location.y == 29)
				{
					pokeMartTransfer();
				}
				if (location.x == 15 && location.y == 39)
				{
					houseInt=12;
					genericTransfer(Area.Generic_1, 2, 7);
				}
				if (location.x == 27 && location.y == 39)
				{
					houseInt=13;
					genericTransfer(Area.Generic_1, 2, 7);
				}
				if (location.x == 39 && location.y == 39)
				{
					houseInt=14;
					genericTransfer(Area.Generic_1, 2, 7);
				}
				if (location.x == 51 && location.y == 39)
				{
					houseInt=15;
					genericTransfer(Area.Generic_1, 2, 7);
				}
				if (location.x == 26 && location.y == 17)
				{
					houseInt=1;
					genericTransfer(Area.Gym_1, 7, 14);
				}
				if (location.x == 34 && location.y == 17)
				{
					houseInt=1;
					genericTransfer(Area.Gym_2, 12, 14);
				}
				if (location.x == 42 && location.y == 17)
				{
					houseInt=1;
					genericTransfer(Area.Gym_3, 9, 19);
				}
				if (location.x == 50 && location.y == 17)
				{
					houseInt=1;
					genericTransfer(Area.Gym_4, 9, 19);
				}
				if (location.x == 26 && location.y == 11)
				{
					houseInt=1;
					genericTransfer(Area.Gym_5, 9, 19);
				}
				if (location.x == 34 && location.y == 11)
				{
					houseInt=1;
					genericTransfer(Area.Gym_6, 9, 19);
				}
				if (location.x == 42 && location.y == 11)
				{
					houseInt=1;
					genericTransfer(Area.Gym_7, 9, 19);
				}
				if (location.x == 50 && location.y == 11)
				{
					houseInt=1;
					genericTransfer(Area.Gym_8, 9, 19);
				}
				if (location.y == 23 && direction == 90)
				{
					if (location.x == 13 || location.x == 14)
					{
						genericTransfer(Area.Battle_Tower,location.x-6,14);
					}
				}
				break;
			case Battle_Tower:
				if (location.y == 14 && direction == 270)
				{
					if (location.x == 7 || location.x == 8)
					{
						genericReturn();
					}
				}
				break;
			case Babbs_Lab:
				if (location.y == 12 && direction == 270)
				{
					if (location.x == 4 || location.x == 5)
					{
						area=Area.Stringville;
						location.x=32;
						location.y=10;
						bgm.stop();
						createCurrentArea();
					}
				}
				break;
			case Generic_1:
				if (location.y == 7 && direction == 270)
				{
					if (location.x == 2 || location.x == 3)
					{
						transition=false;
						bgm.stop();
						area=returnArea;
						location.x=returnPoint.x;
						location.y=returnPoint.y;
						createCurrentArea();
					}
				}
				break;
			case Gym_1:
				if (location.y == 14 && direction == 270)
				{
					if (location.x == 7 || location.x == 8)
					{
						genericReturn();
					}
				}
				break;
			case Gym_2:
				if (location.y == 14 && direction == 270)
				{
					if (location.x == 12 || location.x == 13)
					{
						genericReturn();
					}
				}
				if (location.x == 5 && location.y == 7 && direction == 90)
				{
					location.x=19;
					location.y=5;
					direction=180;
				}
				if (location.x == 19 && location.y == 5 && direction == 0)
				{
					location.x=5;
					location.y=7;
				}
				break;
			case Intville:
				if (location.x == 0 && direction == 180)
				{
					if (location.y == 10 || location.y == 11)
					{
						direction=0;
						transition=true;
						bgm.stop();
						area=Area.Route_13;
						location.x=2;
						location.y=2;
						createCurrentArea();
					}
				}
				if (location.x == 19 && location.y == 23)
				{
					genericTransfer(Area.Generic_2,2,7);
					houseInt = 9001;
				}
				break;
			case H_Border:
				if (location.x == 9 && direction == 0)
				{
					if (location.y == 4 || location.y == 5)
					{
						transition=true;
						bgm.stop();
						if (houseInt == 0)
						{
							area=Area.Route_1;
							location.x=4;
							location.y+=16;
						}
						else if (houseInt == 1)
						{
							transition = false;
							area=Area.Mount_Java;
							location.x=42;
							location.y+=16;
						}
						else if (houseInt == 2)
						{
							transition = false;
							area=Area.Recursive_Hot_Springs;
							location.x=14;
							location.y+=46;
						}
						else if (houseInt == 3)
						{
							transition = false;
							area=Area.Recursive_Hot_Springs;
							location.x=28;
							location.y+=46;
						}
						createCurrentArea();
					}
				}
				if (location.x == 0 && direction == 180)
				{
					if (location.y == 4 || location.y == 5)
					{
						transition=true;
						bgm.stop();
						if (houseInt == 0)
						{
							area=Area.Binary_City;
							location.x=59;
							location.y+=16;
						}
						else if (houseInt == 1)
						{
							transition = false;
							area=Area.Mount_Java;
							location.x=37;
							location.y+=16;
						}
						else if (houseInt == 2)
						{
							transition = false;
							area=Area.Recursive_Hot_Springs;
							location.x=9;
							location.y+=46;
						}
						else if (houseInt == 3)
						{
							transition = false;
							area=Area.Recursive_Hot_Springs;
							location.x=23;
							location.y+=46;
						}
						createCurrentArea();
					}
				}
				break;
			case V_Border:
				if (location.y == 0 && direction == 90)
				{
					if (location.x == 4 || location.x == 5)
					{
						transition=true;
						bgm.stop();
						if (houseInt == 0)
						{
							if(Inventory.hasItem(new Item(Item.Type.BICYCLE,1)))
							{
								transition=false;
								area=Area.Route_4;
								location.x+=14;
								location.y=73;
							}
							else
							{
								transition=false;
								direction=270;
								location.y++;
							}
						}
						else if (houseInt == 1)
						{
							transition=false;
							area=Area.Route_5;
							location.x+=58;
							location.y=1;
						}
						else if (houseInt == 2)
						{
							transition=true;
							area=Area.Hexadecimal_Forest;
							location.x+=12;
							location.y=39;
						}
						else if (houseInt == 3)
						{
							transition=false;
							area=Area.Route_2;
							location.x+=21;
							location.y=15;
						}
						else if (houseInt == 4)
						{
							transition=false;
							area=Area.Champions_Walk;
							location.x+=87;
							location.y=29;
						}

						createCurrentArea();
					}
				}
				if (location.y == 7 && direction == 270)
				{
					if (location.x == 4 || location.x == 5)
					{
						transition=true;
						bgm.stop();
						if (houseInt == 0)
						{
							transition=false;
							area=Area.Route_4;
							location.x=18;
							location.y=78;
						}
						else if (houseInt == 1)
						{
							transition=false;
							area=Area.Route_5;
							location.x=62;
							location.y=6;
						}
						else if (houseInt == 2)
						{
							transition=false;
							area=Area.Route_2;
							location.x=25;
							location.y=30;
						}
						else if (houseInt == 3)
						{
							transition=true;
							area=Area.Hexadecimal_Forest;
							location.x+=16;
							location.y=0;
						}
						else if (houseInt == 4)
						{
							transition=false;
							area=Area.Champions_Walk;
							location.x=91;
							location.y=34;
						}
						createCurrentArea();
					}
				}
				break;
			case Slipspace:
				if (location.x == 19 && direction == 0)
				{
					if (location.y >= 6 && location.y <= 10)
					{
						surfing=true;
						transition=true;
						bgm.stop();
						area=Area.Route_12;
						location.y+=25;
						location.x=0;
						createCurrentArea();
					}
				}
				break;
			case Hexadecimal_Forest:
				if (location.y == 39 && direction == 270)
				{
					if (location.x == 16 || location.x == 17)
					{
						transition=false;
						houseInt=2;
						bgm.stop();
						area=Area.V_Border;
						location.y=1;
						location.x-=12;
						createCurrentArea();
					}
				}
				if (location.y == 0 && direction == 90)
				{
					if (location.x == 20 || location.x == 21)
					{
						transition=false;
						houseInt=3;
						bgm.stop();
						area=Area.V_Border;
						location.y=7;
						location.x-=16;
						createCurrentArea();
					}
				}
				break;
			case Generic_2:
			case Generic_3:
				if (location.y == 7 && direction == 270)
				{
					if (location.x == 2 || location.x == 3)
					{
						genericReturn();
					}
				}
				break;
			case Gym_8:
				if (location.x == 13 && location.y == 18)
					location.x--;
			case Gym_7:
			case Gym_3:
			case Gym_4:
			case Gym_5:
			case Gym_6:
				if (location.y == 19 && direction == 270)
				{
					if (location.x == 9 || location.x == 10)
					genericReturn();
				}
				break;
			case J_Inc_Radio_Tower:
				if (location.y == 9 && direction == 270)
				{
					if (location.x == 4 || location.x == 5)
					{
						genericReturn();
					}
				}
				break;
			case J_Inc_Building:
				if (location.y == 14 && direction == 270)
				{
					if (location.x == 9 || location.x == 10)
					{
						genericReturn();
					}
				}
				if (location.x == 13 && location.y == 5 && direction == 90)
				{
					direction = 270;
					location.y = 22;
				}
				if (location.x == 13 && location.y == 21 && direction == 90)
				{
					direction = 270;
					location.y = 6;
				}
				if (location.x == 15 && location.y == 21 && direction == 90)
				{
					direction = 270;
					location.y = 38;
				}
				if (location.x == 15 && location.y == 37 && direction == 90)
				{
					direction = 270;
					location.y = 22;
				}
				break;
			case Victory_Road_Cave:
				if (location.x == 89 && location.y == 59)
				{
					transition=true;
					bgm.stop();
					area=Area.Mountain_Dew_Paradise;
					location.x=0;
					location.y=9;
					createCurrentArea();
				}
				if (location.x == 15 && location.y == 83)
				{
					transition=false;
					bgm.stop();
					area=Area.Victory_Road;
					location.x=16;
					location.y=48;
					createCurrentArea();
				}
				if (location.x == 35 && location.y == 15)
				{
					transition=false;
					bgm.stop();
					area=Area.Victory_Road;
					location.x=15;
					location.y=33;
					createCurrentArea();
				}
				break;
			case Mountain_Dew_Paradise:
				if (location.x == 0 && location.y == 9)
				{
					transition=true;
					bgm.stop();
					area=Area.Victory_Road_Cave;
					location.x=89;
					location.y=59;
					createCurrentArea();
				}
				break;
			case Generic_4:
				if (location.y == 7 && direction == 270)
				{
					if (location.x == 2 || location.x == 3)
					{
						genericReturn();
					}
				}
				if (location.x == 7 && location.y == 7)
				{
					area=Area.Generic_5;
					createCurrentArea();
					direction = 180;
				}
				break;
			case Generic_5:
				if (location.x == 7 && location.y == 7)
				{
					area=Area.Generic_4;
					createCurrentArea();
					direction = 180;
				}
				if (location.x == 0 && location.y == 7)
				{
					area=Area.Generic_6;
					createCurrentArea();
					direction = 0;
				}
				break;
			case Generic_6:
				if (location.x == 0 && location.y == 7)
				{
					area=Area.Generic_5;
					createCurrentArea();
					direction = 0;
				}
				break;
			case Lighthouse:
				if (location.y == 11 && direction == 270)
				{
					bgm.stop();
					if (location.x == 5 || location.x == 6)
					{
						genericReturn();
					}
				}
			case Lighthouse_F2:
				if (location.x == 10 && location.y == 2)
				{
					bgm.stop();
					if (area == Area.Lighthouse)
						area = Area.Lighthouse_F2;
					else
						area = Area.Lighthouse;

					direction = 180;
					createCurrentArea();
				}
				break;
			case MegaMart:
				if (location.y == 13 && direction == 270)
				{
					if (location.x == 11 || location.x == 12)
					{
						genericReturn();
					}
				}
				if (location.x == 18 && location.y == 4)
				{
					location.y+=17;
					direction=270;
				}
				else if (location.x == 18 && location.y == 20)
				{
					location.y-=15;
					direction=270;
				}
				else if (location.x == 16 && location.y == 20)
				{
					location.y+=18;
					direction=270;
				}
				else if (location.x == 16 && location.y == 37)
				{
					location.y-=16;
					direction=270;
				}
				else if (location.x == 18 && location.y == 37)
				{
					location.y+=18;
					direction=270;
				}
				else if (location.x == 18 && location.y == 54)
				{
					location.y-=16;
					direction=270;
				}
				break;
			case Challenge_Cave:
				if (location.x == 112 && location.y == 111)
				{
					location.x=101;
					location.y=112;
				}
				else if(location.x == 101 && location.y == 112)
				{
					location.x=112;
					location.y=111;
				}
				if (location.x == 112 && location.y == 116)
				{
					transition=true;
					bgm.stop();
					area=Area.Args_Harbor;
					location.x=50;
					location.y=58;
					createCurrentArea();
				}
				break;

		}
	}

	//Checks if player has seen an area, then adds it to the possible fly zones
	public boolean seenArea(Area a)
	{
		File f=new File("savedata/"+idString+"_"+a+".itemData");

		if(f.exists())
		{
			System.out.println("Area "+a+" was seen.");
			return true;
		}

		return false;
	}

	public void pokeCenterTransfer()
	{
		transition=false;
		bgm.stop();
		returnArea2 = area;
		returnPoint2.x = location.x;
		returnPoint2.y = location.y+1;
		area=Area.Pokecenter;
		location.x=6;
		location.y=8;
		createCurrentArea();
	}
	public void pokeMartTransfer()
	{
		transition=false;
		bgm.stop();
		returnArea = area;
		returnPoint.x = location.x;
		returnPoint.y = location.y+1;
		area=Area.Mart;
		location.x=2;
		location.y=8;
		createCurrentArea();
	}
	public void genericTransfer(Area newArea, int x, int y)
	{
		transition=false;
		bgm.stop();
		returnArea = area;
		returnPoint.x = location.x;
		returnPoint.y = location.y+1;
		area=newArea;
		location.x=x;
		location.y=y;
		createCurrentArea();
	}
	public void genericReturn()
	{
		transition=false;
		bgm.stop();
		area=returnArea;
		location.x=returnPoint.x;
		location.y=returnPoint.y;
		createCurrentArea();
	}


	//Game Loop
	public void run()
	{
		title.titleMusic.loop();
		while(titleScreen)
		{
			repaint();
			frames++;
      		framerateManager();
      		try
      		{
      			Thread.sleep(slp);
      		}
      		catch(Exception e){}
		}

		title.titleMusic.stop();
		repaint();

		loadPokemonImages();
		launchTime=System.currentTimeMillis();
		if(file.isDirectory())
		load();
		else
		{
			createCurrentArea();
			do
			{
				name =(JOptionPane.showInputDialog(null, "What is your name? (<7 chars)", name));
			}
			while(name==null);

			if(name.length()>7)
				name=name.substring(0,7);

			do
			{
				rivalName =(JOptionPane.showInputDialog(null, "What is your RIVAL's name? (<7 chars)", rivalName));
			}
			while(rivalName==null);

			if(rivalName.length()>7)
				rivalName=rivalName.substring(0,7);

			String starter="Type your starter!";
			while(starter==null||(!starter.equalsIgnoreCase("Bulbasaur")&&!starter.equalsIgnoreCase("Charmander")&&!starter.equalsIgnoreCase("Squirtle")))
			{
				starter =(JOptionPane.showInputDialog(null, "Bulbasaur, Charmander, or Squirtle?", starter));
			}

			if(starter.equalsIgnoreCase("Bulbasaur"))
			{
				partyPokemon[0]=new Pokemon(Pokemon.Species.BULBASAUR);
				Pokedex.seen(partyPokemon[0].pokedexNumber-1);
				Pokedex.caught(partyPokemon[0].pokedexNumber-1);
			}
			else if(starter.equalsIgnoreCase("Charmander"))
			{
				partyPokemon[0]=new Pokemon(Pokemon.Species.CHARMANDER);
				Pokedex.seen(partyPokemon[0].pokedexNumber-1);
				Pokedex.caught(partyPokemon[0].pokedexNumber-1);
			}
			else if(starter.equalsIgnoreCase("Squirtle"))
			{
				partyPokemon[0]=new Pokemon(Pokemon.Species.SQUIRTLE);
				Pokedex.seen(partyPokemon[0].pokedexNumber-1);
				Pokedex.caught(partyPokemon[0].pokedexNumber-1);
			}

			do
			{
				partyPokemon[0].nickname=(JOptionPane.showInputDialog(null, "What is its nickname? (<10 Chars)", partyPokemon[0].nickname));
			}
			while(partyPokemon[0].nickname==null);

			partyPokemon[0].setNickname(partyPokemon[0].nickname);

			partyPokemon[0].originalTrainer=name;
			partyPokemon[0].idNumber=trainerIdNumber;

			Pokedex.seen(partyPokemon[0].pokedexNumber-1);
			Pokedex.caught(partyPokemon[0].pokedexNumber-1);
			
			Inventory.addItem(new Item(Item.Type.TOWN_MAP,1));
			
			if(DONATE)
			{
				Inventory.money=10000;
				Inventory.addItem(new Item(Item.Type.POTION,20));
				Inventory.addItem(new Item(Item.Type.POKE_BALL,20));
				Inventory.addItem(new Item(Item.Type.TM,1,1));
			}

			System.out.println("Could not find save data");
			System.out.println("Creating savedata directory");
			save();
			System.out.println("Starting game...");

		}

		if(DEBUG)
		debug();

		while(true)
		{
			manageTime();
			repaint();
			frames++;
      		framerateManager();

      		if(moving&&!paused&&!onComp)
      			checkMovement();

      		if(trainerEnc&&!paused&&!onComp)
      			beginTrainerBattle();
      		else if(!paused&&!onComp)
      			checkTrainerEncounter();

      		if(performingAction&&!paused&&!onComp)
      		actionHandler();

      		try
      		{
      			Thread.sleep(slp);
      		}
      		catch(Exception e){e.printStackTrace();}
		}
	}

	//Sets ID
	private void setIdentification()
	{
		if(trainerIdNumber>99999)
			trainerIdNumber=45897;
		else if(trainerIdNumber<10000)
			trainerIdNumber=45897;

		idString=""+trainerIdNumber;

		while(idString.length()<5)
		{
			idString="0"+idString;
		}
	}

	public static void setTransition(boolean boo)
	{
		transition=boo;
	}

	//Manages Frame Rate
	public void framerateManager()
	{
		if( lastFPS <=System.currentTimeMillis() )
		{
			fps=""+frames;
//			System.out.println(fps);
			lastFPS = System.currentTimeMillis() + 1000;
			if(frames<80&&slp>0)
			{
				//System.out.println("Low Frame Rate: "+fps);
				slp--;
			}
			else if(frames>110)
			{
				//System.out.println("High Frame Rate: "+fps);
				slp++;
			}
			frames=0;
		}
	}

	private void setupGUI()
	{
		jf = new JFrame("Jokemon: "+VERSION);

		URL iconU=JokemonDriver.class.getResource("icon.png");
    	icon=Toolkit.getDefaultToolkit().getImage(iconU);
    	iconU=JokemonDriver.class.getResource("Logos/charizard.gif");
    	loading=Toolkit.getDefaultToolkit().getImage(iconU);

    	jf.setIconImage(icon);

		jf.setSize(800,600);
		jf.setLayout(new GridLayout(1,1));
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
        Container c = jf.getContentPane();

		this.setLayout(null);
		c.add(this);

		jf.addKeyListener(this);
		jf.setVisible(true);
	}

	//Returns the Pokeball Icon
	public static Image getIcon()
	{
		URL iconU=JokemonDriver.class.getResource("Sprites/item.png");
    	icon=Toolkit.getDefaultToolkit().getImage(iconU);
		return icon;
	}
}