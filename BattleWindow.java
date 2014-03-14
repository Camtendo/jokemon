/**
 * @(#)BattleWindow.java
 *
 *
 * @author Justin Begeman
 * @version 1.00 2010/12/13
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

public class BattleWindow extends JComponent implements KeyListener, MouseListener, MouseMotionListener, WindowListener, Runnable {

	boolean DONE_IMPORTING=false;
	private JFrame jf, storedJFrame;
	private Container cp;
	JTextArea text;
	JScrollPane scroll;
	private String battleMessage;
	private boolean duder = false;
	public boolean cursorLock = true;
	private boolean tryingToCatch=false;

	private int superHappyFunTime = 0;
	private Image[] playerImages = new Image[6];
	private Image[] enemyImages = new Image[6];
	private Image[] battleImages = new Image[18];
	private boolean keyBoolean = true;
	Point mouse = new Point(0,0);

	private Pokemon[] playerPokemon;
	private Pokemon[] enemyPokemon;

	JScrollBar jsb;
	public int userSelected = 0;
	public int enemySelected = 0;

	private HealthBar playerHb = HealthBar.GREEN;
	private HealthBar enemyHb = HealthBar.GREEN;
	boolean loopLowHealthTone=false;

	private CursorLocation cursorLocation = CursorLocation.TOP_LEFT;
	MenuSetting menuSetting = MenuSetting.MAIN;
	private BattleState battleState = BattleState.STAND_BY;

	Thread thread;
	long lastFPS = System.currentTimeMillis() + 1000;
	boolean timing=false;
	short frames;
	String fps;
	int slp = 5;
	int pNum;
	int eNum;

	boolean flicker[]=new boolean[2];
	int flickerInt[]=new int[2];
	boolean allowedToPaintExp=false;
	boolean showingTrainer=true;

	Trainer trainer;
	private Image enemyTrainerImage;
	private Image userTrainerImage;
	private Image[] pokeBalls = new Image[5];
	Image icon;


	//Constructors
	//Battle Type Strings are either WILD, TRAINER, or GYM
    public BattleWindow(Pokemon[] userPoke, Pokemon[] enemyPoke, String battleType) {
    	battleMessage = "=======Battle Initiated=======";

    	if(!battleType.equals("WILD"))
    	{
    		System.out.println("");
    		System.out.println("Improper Battle Window Constructor");
    		System.out.println("Now Exiting...");
    		System.exit(-1);
    	}
    	else
    	{
    		for(int i=1; i<6; i++)
    			enemyPoke[i]=null;
    	}
    	flicker[0]=false;
    	flickerInt[0]=0;
    	flicker[1]=false;
    	flickerInt[1]=0;
    	allowedToPaintExp=false;
    	boolean showingTrainer=true;

		setUpGUI(battleType,enemyPoke[0]);

		playerPokemon = userPoke;
		enemyPokemon = enemyPoke;

		createPokemonImages();

		createTrainerImages();

		createBattleImages();

		beginThread();

		for (int i = 0; i < 6; i++)
		{
			if (userPoke[i] != null)
			pNum++;
		}

		for (int i = 0; i < 6; i++)
		{
			if (enemyPoke[i] != null)
			eNum++;
		}
		Battle.startBattle(userPoke,pNum,enemyPoke,eNum,battleType,this);
    }

    //Second Constructor
    public BattleWindow(Pokemon[] userPoke, Pokemon[] enemyPoke, String battleType, Trainer train) {
    	battleMessage = "=======Battle Initiated=======";

    	trainer=train;
    	flicker[0]=false;
    	flickerInt[0]=0;
    	flicker[1]=false;
    	flickerInt[1]=0;
    	allowedToPaintExp=false;

    	boolean showingTrainer=true;

		setUpGUI(battleType,trainer);

		playerPokemon = userPoke;
		enemyPokemon = enemyPoke;

		createPokemonImages();

		createTrainerImages();

		createBattleImages();

		beginThread();

		for (int i = 0; i < 6; i++)
		{
			if (userPoke[i] != null)
			pNum++;
		}

		for (int i = 0; i < 6; i++)
		{
			if (enemyPoke[i] != null)
			eNum++;
		}

		Battle.startBattle(userPoke,pNum,enemyPoke,eNum,battleType,this);
    }

	public void updateUserPoke(Pokemon[] userPoke)
	{
		playerPokemon = userPoke;
	}

	public void setFlicker(int i)
	{
		flicker[i]=true;
	}

	public void stopFlicker(int i)
	{
		flicker[i]=false;
		flickerInt[i]=0;
	}

	public void run()
	{
		while (true)
		{
			repaint();
			JokemonDriver.manageTime();
			frames++;
			framerateManager();
			try
			{
				Thread.sleep(slp);
			}
			catch(Exception e){}
		}
	}
	public void framerateManager()
	{
		if( lastFPS <=System.currentTimeMillis() )
		{
			fps=""+frames;
			//System.out.println(fps);
			lastFPS = System.currentTimeMillis() + 1000;
			if(frames<80&&slp>0)
				slp--;
			else if(frames>110)
				slp++;
			frames=0;
		}
	}

	public void setScroll()
	{
		//jsb.setValue(jsb.getMaximum());
	}
    public void setUpGUI(String battletype, Pokemon p)
    {
    	jf = new JFrame("Wild "+p.nickname);

    	URL iconU=JokemonDriver.class.getResource("icon.png");
    	icon=Toolkit.getDefaultToolkit().getImage(iconU);
    	jf.setIconImage(icon);

    	storedJFrame=jf;
    	cp = jf.getContentPane();

    	jf.setSize(804,635);
 		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		jf.setResizable(false);
 		jf.setLayout(null);
 		jf.setLocationRelativeTo(null);
		jf.setResizable(false);

		text=new JTextArea();
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);

		this.setBounds(0,0,600,600);

    	text.append(battleMessage);

    	scroll=new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(600,0,193,600);
		jsb = scroll.getVerticalScrollBar();
		jsb.setFocusable(false);

    	cp.add(scroll);
    	cp.add(this);

    	this.addKeyListener(this);
    	this.addMouseListener(this);
    	this.addMouseMotionListener(this);
		this.setFocusable(true);

		jf.setVisible(true);

		cursorLocation.move = 1;
		cursorLocation.pokemon = 1;

    }

    public void setUpGUI(String battletype, Trainer t)
    {
    	jf= new JFrame("Vs. "+t.type+" "+t.name);

    	URL iconU=JokemonDriver.class.getResource("icon.png");
    	icon=Toolkit.getDefaultToolkit().getImage(iconU);
    	jf.setIconImage(icon);

    	storedJFrame=jf;
    	cp = jf.getContentPane();

    	jf.setSize(804,635);
 		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		jf.setResizable(false);
 		jf.setLayout(null);
 		jf.setLocationRelativeTo(null);
		jf.setResizable(false);

		text=new JTextArea();
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);

		this.setBounds(0,0,600,600);

    	text.append(battleMessage);

    	scroll=new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(600,0,193,600);
		jsb = scroll.getVerticalScrollBar();
		jsb.setFocusable(false);


    	cp.add(scroll);
    	cp.add(this);


    	this.addKeyListener(this);
    	this.addMouseListener(this);
    	this.addMouseMotionListener(this);
		this.setFocusable(true);

		jf.setVisible(true);

		cursorLocation.move = 1;
		cursorLocation.pokemon = 1;

    }

	public void createBattleImages()
	{
		URL url = Pokemon.class.getResource("Sprites/BattleSprites/textBox.png");
		battleImages[0] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/BattleSprites/longArrow.png");
		battleImages[1] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/BattleSprites/longArrow2.png");
		battleImages[2] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/BattleSprites/Slash.png");
		battleImages[3] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/BattleSprites/healthbar.png");
		battleImages[4] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/BattleSprites/HP.png");
		battleImages[5] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/BattleSprites/Level.png");
		battleImages[6] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/BattleSprites/Fight.png");
		battleImages[7] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/BattleSprites/item.png");
		battleImages[8] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/BattleSprites/pkmn.png");
		battleImages[9] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/BattleSprites/run.png");
		battleImages[10] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/BattleSprites/pokeballCursor.png");
		battleImages[11] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/BattleSprites/TypeBox.png");
		battleImages[12] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/BattleSprites/switchBox.png");
		battleImages[13] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/balls/poke.png");
		pokeBalls[0] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/balls/great.png");
		pokeBalls[1] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/balls/ultra.png");
		pokeBalls[2] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/balls/master.png");
		pokeBalls[3] = Toolkit.getDefaultToolkit().getImage(url);

		url = Pokemon.class.getResource("Sprites/balls/omega.png");
		pokeBalls[4] = Toolkit.getDefaultToolkit().getImage(url);

		DONE_IMPORTING=true;
	}
    public void createPokemonImages()
    {
    	String pokemonType;
    	char a;
    	URL url;

    	for (int i = 0; i < 6; i++)
    	{
    		if (playerPokemon[i] != null)
			{
				pokemonType = playerPokemon[i].species.toString();
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

		    	url = Pokemon.class.getResource("Sprites/pokemon/Right/" + pokemonType + ".png");
		    	playerImages[i] = Toolkit.getDefaultToolkit().createImage(url);
			}
    	}
		for (int i = 0; i < 6; i++)
		{
			if (enemyPokemon[i] != null)
			{
				pokemonType = enemyPokemon[i].species.toString();
				if(pokemonType.equalsIgnoreCase("Mewtwo")&&enemyPokemon[0].nickname.equals("Mewtrix"))
					pokemonType="Mewtrix";
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

		    	url = Pokemon.class.getResource("Sprites/pokemon/Left/" + pokemonType + ".png");
		    	enemyImages[i] = Toolkit.getDefaultToolkit().createImage(url);
			}
		}
    }

    public void createTrainerImages()
    {
    	URL url;
    	if(trainer!=null)
    	{
    		if(trainer.type==Trainer.TrainerType.GYM_LEADER||trainer.type==Trainer.TrainerType.ELITE)
    		{
    			url = Pokemon.class.getResource("Sprites/trainers/"+ trainer.name + ".png");
    			enemyTrainerImage=Toolkit.getDefaultToolkit().createImage(url);
    		}
    		else
    		{
    			url = Pokemon.class.getResource("Sprites/trainers/"+ trainer.type + ".png");
    			enemyTrainerImage=Toolkit.getDefaultToolkit().createImage(url);
    		}
    	}

    	url = Pokemon.class.getResource("Sprites/trainers/userTrainer.png");
    	userTrainerImage=Toolkit.getDefaultToolkit().createImage(url);
    }

    public void endBattleWindow()
    {
    	jf=storedJFrame;
    	jf.setSize(804,635);
 		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		jf.setResizable(false);
 		jf.setLayout(null);
 		jf.setLocationRelativeTo(null);
		jf.setResizable(false);

		text=new JTextArea();
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);

		this.setBounds(0,0,600,600);

    	text.append(battleMessage);

    	scroll=new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(600,0,193,600);
		jsb = scroll.getVerticalScrollBar();
		jsb.setFocusable(false);


    	cp.add(scroll);
    	cp.add(this);


    	this.addKeyListener(this);
    	this.addMouseListener(this);
    	this.addMouseMotionListener(this);
		this.setFocusable(true);

		jf.setVisible(false);

		cursorLocation.move = 1;
		cursorLocation.pokemon = 1;

		trainer=null;
    }

	public void beginThread()
	{
		if (thread == null)
		{
      		thread = new Thread(this, "JokemonBattleWindowThread");
      		thread.start();
		}
	}
    //Cursor Location on the menu
    /*
     *Move - Move number selected on attack menu
     *Pokemon - Pokemon number selected on Switch menu
     */
    private enum CursorLocation
    {
    	TOP_RIGHT,TOP_LEFT,BOTTOM_RIGHT,BOTTOM_LEFT;
    	int move;
		int pokemon = 1;
    }
    //Settings for stand by
    enum MenuSetting
    {
    	MAIN,ATTACK,SWITCH,ITEM;
    }
    private enum BattleState
    {
    	TRAINER,MOVE,STAND_BY,END,TRANSITION_1,TRANSITION_2,TRANSITION_3,ATTACKING;
    }
    private enum HealthBar
    {
    	GREEN,ORANGE,RED;
    	double percent;
    	int length;
    }

    //Adds Text to the side of the screen
    public void addText(String str)
    {
    	str = str.replace('_',' ');
    	text.append("\n" + str);
    	System.out.println("Added Text: "+str);
    	//jsb.setValue(jsb.getMaximum());
    	text.setCaretPosition(text.getCaretPosition()+str.length());
    	jsb.repaint();
		text.repaint();
		scroll.repaint();
    }
    public void tooFront()
    {
    	jf.toFront();
    }

    //Creates the illusion that you are trying to catch a Pokemon
    public void tryToCatch()
    {
    	tryingToCatch=true;
    	repaint();

    	try
    	{
    		Thread.sleep(4000);
    	}
    	catch(Exception e){}
    }

    //Called when a Pokemon is failed to be caught
    public void failedCatch()
    {
    	tryingToCatch=false;
    }

	public void healthCorrect()
	{
		if (playerPokemon[userSelected].health < 0)
			playerPokemon[userSelected].health = 0;
		else if (playerPokemon[userSelected].health > playerPokemon[userSelected].healthMax)
			playerPokemon[userSelected].health = playerPokemon[userSelected].healthMax;

		if (enemyPokemon[enemySelected].health < 0)
			enemyPokemon[enemySelected].health = 0;
		else if (enemyPokemon[enemySelected].health > enemyPokemon[enemySelected].healthMax)
			enemyPokemon[enemySelected].health = enemyPokemon[enemySelected].healthMax;

		System.out.println("Dude it's fixed?");
	}
    public void keyPressed(KeyEvent e)
	{
		String dir = e.getKeyText(e.getKeyCode());
		//System.out.println(dir);

		if(Battle.BATTLE_OVER)
			Battle.TERMINATE=true;

		if (keyBoolean&&!cursorLock)
		keyMeasure(dir);
	}
	public void windowDeactivated(WindowEvent e)
	{

	}
	public void windowActivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e)
	{

	}
	public void windowIconified(WindowEvent e)
	{

	}
	public void windowClosed(WindowEvent e)
	{
	}
	public void windowClosing(WindowEvent e)
	{
	}
	public void windowOpened(WindowEvent e)
	{

	}
	public void keyMeasure(String dir)
	{
		if (cursorLock)
		{
			System.out.println("Cursor is locked");
			return;
		}
		keyBoolean = false;
		if (dir.equalsIgnoreCase("UP"))//If Up Arrow is pressed
		{
			switch(menuSetting)
			{
				case MAIN:
					switch(cursorLocation)
					{
						case BOTTOM_RIGHT :
							cursorLocation = CursorLocation.TOP_RIGHT;
							break;
						case BOTTOM_LEFT :
							cursorLocation = CursorLocation.TOP_LEFT;
							break;
						default:
							break;
					}
					break;
				case ATTACK:
					if (cursorLocation.move>1)
					cursorLocation.move--;
					break;
				case SWITCH:
					if (cursorLocation.pokemon>1)
					cursorLocation.pokemon--;
					break;
			}

		}
		else if (dir.equalsIgnoreCase("DOWN"))//If Down Arrow is pressed
		{
			switch(menuSetting)
			{
				case MAIN:
					switch(cursorLocation)
					{
						case TOP_RIGHT :
							cursorLocation = CursorLocation.BOTTOM_RIGHT;
							break;
						case TOP_LEFT :
							cursorLocation = CursorLocation.BOTTOM_LEFT;
							break;
						default:
							break;
					}
					break;
				case ATTACK:
					if (cursorLocation.move<4 && playerPokemon[userSelected].move[cursorLocation.move] != Pokemon.Move.NONE)
					cursorLocation.move++;
					break;
				case SWITCH:
					if (cursorLocation.pokemon<6 && playerPokemon[cursorLocation.pokemon] != null)
					cursorLocation.pokemon++;
					break;
			}

		}
		else if (dir.equalsIgnoreCase("LEFT"))//If Left Arrow is pressed
		{
			switch(menuSetting)
			{
				case MAIN:
					switch(cursorLocation)
					{
						case TOP_RIGHT :
							cursorLocation = CursorLocation.TOP_LEFT;
							break;
						case BOTTOM_RIGHT :
							cursorLocation = CursorLocation.BOTTOM_LEFT;
							break;
						default:
							break;
					}
					break;
			}
			//playerPokemon[cursorLocation.pokemon-1].health--;
		}
		else if (dir.equalsIgnoreCase("RIGHT"))//If Right Arrow is pressed
		{
			switch(menuSetting)
			{
				case MAIN:
					switch(cursorLocation)
					{
						case TOP_LEFT :
							cursorLocation = CursorLocation.TOP_RIGHT;
							break;
						case BOTTOM_LEFT :
							cursorLocation = CursorLocation.BOTTOM_RIGHT;
							break;
						default:
							break;
					}
					break;
			}
			//playerPokemon[cursorLocation.pokemon-1].health++;
		}
		else if (dir.equalsIgnoreCase("SPACE"))//If Space Bar is pressed
		{
			switch(menuSetting)
			{
				case MAIN:
				switch(cursorLocation)
				{
					case TOP_LEFT:
						if (Mechanics.hasPPAtAll(playerPokemon[userSelected]))
						menuSetting = MenuSetting.ATTACK;
						else
						{
							Battle.setUserCommand(0);
							Inventory.inventoryWindow.closeInventory();
							System.out.println("Use struggle?");
						}
						return;
					case TOP_RIGHT:
						menuSetting = MenuSetting.SWITCH;
						cursorLocation.pokemon = 1;
						Inventory.inventoryWindow.closeInventory();
						Battle.setUserCommand(4);
						return;
					case BOTTOM_LEFT:
						Inventory.inventoryWindow.openInventory(InventoryWindow.State.BATTLE);
						break;
					case BOTTOM_RIGHT:
						if(!Battle.BATTLE_OVER)
						{
							Battle.runFromBattle();
							Inventory.inventoryWindow.closeInventory();
							Battle.setUserCommand(-1);
						}
						return;
				}
				break;
				case ATTACK:
				{
					if (playerPokemon[userSelected].move[cursorLocation.move-1].pp > 0)
					{
						//System.out.println(cursorLocation.move-1);
						Inventory.inventoryWindow.closeInventory();
						Battle.setUserCommand(cursorLocation.move-1);

						if (Mechanics.moveDisabled[0] != cursorLocation.move-1)
						menuSetting = MenuSetting.MAIN;
					}
					else
					{
						Inventory.errorWindow.addMessage(" No PP for this move! ");
					}

				}
				break;
				case SWITCH:
				{
					if (playerPokemon[cursorLocation.pokemon - 1].health > 0 && cursorLocation.pokemon-1 != userSelected)
					{
						cursorLocation.move = 1;
						menuSetting = MenuSetting.MAIN;
						Inventory.inventoryWindow.closeInventory();
						Battle.setUserCommand(cursorLocation.pokemon-1);
						userSelected = cursorLocation.pokemon-1;
						//System.out.println(cursorLocation.pokemon-1);
					}


				}
				break;
			}
		}
		else if (dir.equalsIgnoreCase("BACKSPACE"))
		{
			switch(menuSetting)
			{
				case SWITCH:
					if(Battle.user[Battle.userIndex].health>0)
					{
						menuSetting = MenuSetting.MAIN;
						cursorLocation.pokemon = userSelected + 1;
						Battle.setUserCommand(-1);
						Inventory.inventoryWindow.closeInventory();
					}
					break;
				default:
					menuSetting = MenuSetting.MAIN;
					break;
			}
		}
	}
	public void keyReleased(KeyEvent e){keyBoolean = true;} //Not Needed
	public void keyTyped(KeyEvent e){} //Not Needed

	//Mouse Events to be later removed
	public void mouseExited(MouseEvent e){}//Finished Method
	public void mouseClicked(MouseEvent e)
	{
		//System.out.println("Mouse Location: " + mouse.x + "," + mouse.y);
		try
		{
			this.requestFocus();
		}
		catch(Exception eeeee){}
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseMoved(MouseEvent e)
	{
		mouse.setLocation(e.getX(),e.getY());
	}
	public void mouseDragged(MouseEvent e){}
	public void setSwitch()
	{
		menuSetting = MenuSetting.SWITCH;
	}
	//Paint methods
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if(!DONE_IMPORTING)
		{
			if(frames%2==0)
			g.setColor(Color.BLACK);
			else
			g.setColor(Color.WHITE);

			g.fillRect(0,0,getWidth(),getHeight());
			return;
		}

		if (!jsb.getValueIsAdjusting() && !duder)
		jsb.setValue(Integer.MAX_VALUE);
		else if (!duder)
		duder = true;


		userSelected = Battle.userIndex;
		enemySelected = Battle.enemyIndex;

		g.setColor(Color.WHITE);
		g.fillRect(0,0,600,800);

		//paintPokeFont(g,"Pokemon Peach Version",50,100,16);
		switch (battleState)
		{
			case STAND_BY:
				try
				{
					paintStandBy(g);
				}
				catch(Exception e){}
				break;
		}
		paintHud(g);

	}
	public void paintHud(Graphics g)
	{
		if (menuSetting == MenuSetting.SWITCH)
		return;

		g.drawImage(battleImages[2],15,90,200,50,this); //Draws Enemy Arrow

		//Paints Pokeball designating a Pokemon that has been caught
		try
		{
			if(Pokedex.caught[enemyPokemon[enemySelected].pokedexNumber-1])
			{
				g.drawImage(this.icon,15,50,16,16,this);
			}
		}
		catch(Exception e){}

		g.drawImage(battleImages[1],385,350,200,50,this); //Draws Player Arrow
		g.drawImage(battleImages[0],0,450,600,150,this); //Draws Box

	}
	public void paintStandBy(Graphics g)
	{

		paintOptions(g);

		if (menuSetting == MenuSetting.SWITCH)
		return;

		if (menuSetting == MenuSetting.ATTACK)
		{
			if (playerPokemon[userSelected].move[cursorLocation.move-1] == Pokemon.Move.NONE)
			{
				cursorLocation.move--;
			}
		}



		paintHealthBars(g);
		paintExpBar(g);

		if (playerPokemon[userSelected].status == Pokemon.Status.OK)
		{
			g.drawImage(battleImages[6],465,315,33,24,this); //Draws L:
			paintPokeFont(g,"" + playerPokemon[userSelected].level,495,315,20);//Draws Level
		}
		else
		{
			paintPokeFont(g,playerPokemon[userSelected].status.toString(),465,315,20);
		}
		paintPokeFont(g,playerPokemon[userSelected].nickname,345,285,19);//Draws Nickname

		if (enemyPokemon[enemySelected].status == Pokemon.Status.OK)
		{
			g.drawImage(battleImages[6],70,80,33,24,this); //Draws L:
			paintPokeFont(g,"" + enemyPokemon[enemySelected].level,100,80,20);//Draws Level
		}
		else
		{
			paintPokeFont(g,enemyPokemon[enemySelected].status.toString(),70,80,20);
		}
		paintPokeFont(g,enemyPokemon[enemySelected].nickname,60,50,19);//Draws Nickname

		int width;
		int height;

		if (!Mechanics.awayFromBattle[0]&&playerPokemon[userSelected].health > 0&&!showingTrainer)
		{
			width = playerImages[userSelected].getWidth(this)*3;
			height = playerImages[userSelected].getHeight(this)*3; //Gets height and width info to paint

			if(!flicker[0])
			g.drawImage(playerImages[userSelected], //Paints Pokemon
			 180-(width/2),335-(height/2),width,height,this);
			else
			{
				if(flickerInt[0]%3==0)
					g.drawImage(playerImages[userSelected], //Paints Pokemon
			 180-(width/2),335-(height/2),width,height,this);

			 flickerInt[0]++;

			 if(flickerInt[0]>=100)
			 {
			 	flickerInt[0]=0;
			 	flicker[0]=false;
			 }
			}
		}
		else if(showingTrainer)
		{
			width = userTrainerImage.getWidth(this)*3;
			height = userTrainerImage.getHeight(this)*3; //Gets height and width info to paint

			if(!flicker[0])
			g.drawImage(userTrainerImage, //Paints Pokemon
			 180-(width/2),335-(height/2),width,height,this);
		}

		if(Battle.BATTLE_TYPE.equals("WILD"))
		{
			//Draws Pokeball instead of Pokemon
			if(tryingToCatch)
			{
				switch(Battle.pokeball.type)
				{
					case POKE_BALL:
						g.drawImage(pokeBalls[0],420,100,50,50,this);
						break;
					case GREAT_BALL:
						g.drawImage(pokeBalls[1],420,100,50,50,this);
						break;
					case ULTRA_BALL:
						g.drawImage(pokeBalls[2],420,100,50,50,this);
						break;
					case MASTER_BALL:
						g.drawImage(pokeBalls[3],420,100,50,50,this);
						break;
					case OMEGA_BALL:
						g.drawImage(pokeBalls[4],420,100,50,50,this);
						break;

				}

			}
			else if (!Mechanics.awayFromBattle[1]&&enemyPokemon[enemySelected].health >0&&!tryingToCatch)
			{
				//Draws Enemy Pokemon
				width = enemyImages[enemySelected].getWidth(this)*3;
				height = enemyImages[enemySelected].getHeight(this)*3;
				if(!flicker[1])
				g.drawImage(enemyImages[enemySelected], //Paints Pokemon
				 423-(width/2),122-(height/2),width,height,this);
				else
				{
					if(flickerInt[1]%3==0)
						g.drawImage(enemyImages[enemySelected], //Paints Pokemon
				 423-(width/2),122-(height/2),width,height,this);

				 flickerInt[1]++;

				 if(flickerInt[1]>=100)
				 {
				 	flickerInt[1]=0;
				 	flicker[1]=false;
				 }
				}
			}
		}

		else if (enemyPokemon[enemySelected].health > 0&&!showingTrainer)
		{
			//Draws Enemy Pokemon
			width = enemyImages[enemySelected].getWidth(this)*3;
			height = enemyImages[enemySelected].getHeight(this)*3;
			if(!flicker[1])
			g.drawImage(enemyImages[enemySelected], //Paints Pokemon
			 423-(width/2),122-(height/2),width,height,this);
			else
			{
				if(flickerInt[1]%3==0)
					g.drawImage(enemyImages[enemySelected], //Paints Pokemon
			 423-(width/2),122-(height/2),width,height,this);

			 flickerInt[1]++;

			 if(flickerInt[1]>=100)
			 {
			 	flickerInt[1]=0;
			 	flicker[1]=false;
			 }
			}
		}
		else if(!trainer.equals(null))
		{
			width = enemyTrainerImage.getWidth(this)*3;
			height = enemyTrainerImage.getHeight(this)*3;

			g.drawImage(enemyTrainerImage, //Paints Pokemon
			 423-(width/2),122-(height/2),width,height,this);
		}
	}
	public void paintHealthBars(Graphics g)
	{
		g.drawImage(battleImages[3],480,360,20,20,this);//Draws Slash

		//Health Draw
		if (playerPokemon[userSelected].health < 10)
		paintPokeFont(g,"" + playerPokemon[userSelected].health,456,360,18);
		else if (playerPokemon[userSelected].health < 100)
		paintPokeFont(g,"" + playerPokemon[userSelected].health,438,360,18);
		else
		paintPokeFont(g,"" + playerPokemon[userSelected].health,416,360,18);

		paintPokeFont(g,"" + playerPokemon[userSelected].healthMax,500,360,18);
		//End Health Draw

		g.drawImage(battleImages[4],415,345,150,12,this);//Draws Health Bar
		g.drawImage(battleImages[4],70,110,150,12,this);//Draws Enemy Health Bar

		playerHb = getHealthBarLength(playerHb, //Does the calculations
					playerPokemon[userSelected].health,
					playerPokemon[userSelected].healthMax);


		switch(playerHb) //This switch sets the color of the health bar
		{
			case GREEN:
				{
					g.setColor(Color.GREEN);
					if(loopLowHealthTone)
						Battle.stopLoopLowHP();
				}
				break;
			case ORANGE:
			{
				g.setColor(Color.ORANGE);
				if(loopLowHealthTone)
					Battle.stopLoopLowHP();
			}
				break;
			case RED:
			{
				g.setColor(Color.RED);
				if(!loopLowHealthTone&&!Battle.BATTLE_OVER&&playerPokemon[userSelected].health>0)
					Battle.loopLowHP();
				else if(playerPokemon[userSelected].health<1&&loopLowHealthTone)
				{
					Battle.stopLoopLowHP();
				}
			}
				break;
		}
		if (playerHb.length == 0 && playerPokemon[userSelected].health > 0)
		playerHb.length = 1;
		g.fillRect(418,348,playerHb.length,6); //Draws the Health inside the health bar

		if(enemyPokemon[enemySelected]!=null)
		{
			enemyHb = getHealthBarLength(enemyHb, //Does the calculations
			enemyPokemon[enemySelected].health,
			enemyPokemon[enemySelected].healthMax);
		}

		switch(enemyHb) //This switch sets the color of the health bar
		{
			case GREEN:
				g.setColor(Color.GREEN);
				break;
			case ORANGE:
				g.setColor(Color.ORANGE);
				break;
			case RED:
				g.setColor(Color.RED);
				break;
		}
		if (enemyHb.length == 0 && enemyPokemon[enemySelected].health > 0)
		enemyHb.length = 1;
		g.fillRect(73,113,enemyHb.length,6); //Draws the Health inside the health bar

		g.drawImage(battleImages[5],375,345,39,12,this); //Draws HP:
		g.drawImage(battleImages[5],30,110,39,12,this); //Draws Enemy HP:
	}
	public void paintOptions(Graphics g)
	{
		switch(menuSetting)
		{
			case MAIN:
				g.drawImage(battleImages[7],100,485,117,21,this);//Fight
				g.drawImage(battleImages[8],100,535,90,21,this);//Item
				g.drawImage(battleImages[9],350,485,48,21,this);//Pkmn
				g.drawImage(battleImages[10],350,535,69,21,this);//Run
				if(!cursorLock)
				switch(cursorLocation) //Paints the Cursor on the selected item
				{
					case TOP_LEFT:
						g.drawImage(battleImages[11],75,485,21,21,this);
						break;
					case TOP_RIGHT:
						g.drawImage(battleImages[11],325,485,21,21,this);
						break;
					case BOTTOM_LEFT:
						g.drawImage(battleImages[11],75,535,21,21,this);
						break;
					case BOTTOM_RIGHT:
						g.drawImage(battleImages[11],325,535,21,21,this);
						break;
				}
				break;
			case ATTACK:
				g.setColor(new Color(255,0,0,100));

				if (JokemonDriver.name.equalsIgnoreCase("Red")||JokemonDriver.name.equalsIgnoreCase("Wasabi"))
					g.setColor(Color.RED);
				if (JokemonDriver.name.equalsIgnoreCase("Yellow"))
					g.setColor(Color.YELLOW);
				if (JokemonDriver.name.equalsIgnoreCase("Green")||JokemonDriver.name.equalsIgnoreCase("Justinian"))
					g.setColor(Color.GREEN);
				if (JokemonDriver.name.equalsIgnoreCase("Blue")||JokemonDriver.name.equalsIgnoreCase("Camtendo"))
					g.setColor(Color.BLUE);
				if (JokemonDriver.name.equalsIgnoreCase("Gold"))
					g.setColor(Color.ORANGE);
				if (JokemonDriver.name.equalsIgnoreCase("Silver"))
					g.setColor(Color.LIGHT_GRAY);
				if (JokemonDriver.name.equalsIgnoreCase("Black"))
					g.setColor(Color.BLACK);
				if (JokemonDriver.name.equalsIgnoreCase("Pink"))
					g.setColor(Color.PINK);
				if (JokemonDriver.name.equalsIgnoreCase("Magenta"))
					g.setColor(Color.MAGENTA);
				if (JokemonDriver.name.equalsIgnoreCase("Cyan")||JokemonDriver.name.equalsIgnoreCase("Patches"))
					g.setColor(Color.CYAN);
				if (JokemonDriver.name.equalsIgnoreCase("Rainbow")||JokemonDriver.name.equalsIgnoreCase("Joe"))
					g.setColor(new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));


				switch(cursorLocation.move)
				{
					case 1:
						g.fillRect(30,470,295,25);
						break;
					case 2:
						g.fillRect(30,495,295,25);
						break;
					case 3:
						g.fillRect(30,520,295,25);
						break;
					case 4:
						g.fillRect(30,545,295,25);
						break;
				}
				
				
				if (Mechanics.moveDisabled[0] != 0)
				paintPokeFont(g,playerPokemon[userSelected].toString(playerPokemon[userSelected].move[0]),30,475,18);
				else
				paintPokeFont(g,"DISABLED",30,475,18);

				if (playerPokemon[userSelected].move[1] != Pokemon.Move.NONE)
				{
					if (Mechanics.moveDisabled[0] != 1)
					paintPokeFont(g,playerPokemon[userSelected].toString(playerPokemon[userSelected].move[1]),30,500,18);
					else
					paintPokeFont(g,"DISABLED",30,500,18);
				}

				if (playerPokemon[userSelected].move[2] != Pokemon.Move.NONE)
				{
					if (Mechanics.moveDisabled[0] != 2)
					paintPokeFont(g,playerPokemon[userSelected].toString(playerPokemon[userSelected].move[2]),30,525,18);
					else
					paintPokeFont(g,"DISABLED",30,525,18);
				}


				if (playerPokemon[userSelected].move[3] != Pokemon.Move.NONE)
				{
					if (Mechanics.moveDisabled[0] != 3)
					paintPokeFont(g,playerPokemon[userSelected].toString(playerPokemon[userSelected].move[3]),30,550,18);
					else
					paintPokeFont(g,"DISABLED",30,550,18);
				}


				g.drawImage(battleImages[12],335,465,250,120,this);
				paintPokeFont(g,playerPokemon[userSelected].move[cursorLocation.move-1].moveType.toString(),365,510,18);

				int pp = playerPokemon[userSelected].TRUE_PP[cursorLocation.move-1];
				if (pp > 9)
				paintPokeFont(g,pp + "",450,540,18);
				else
				paintPokeFont(g,pp + "",470,540,18);

				paintPokeFont(g,playerPokemon[userSelected].TRUE_PPMAX[cursorLocation.move-1] + "",510,540,18);

				break;
			case SWITCH:
				g.drawImage(battleImages[13],0,0,600,600,this);

				int Yzer = 50;
				for (int i = 0; i < 6 && playerPokemon[i] != null; i++)
				{
					paintPokeFont(g,playerPokemon[i].nickname,55,Yzer,18);
					paintHealth(g,playerPokemon[i],200,Yzer+20);

					if (playerPokemon[i].health > 0)
					g.drawImage(playerImages[i],320-playerImages[i].getWidth(this)/2,Yzer+20-playerImages[i].getHeight(this)/2,this);

					if (playerPokemon[i].health > 0)
					paintPokeFont(g,playerPokemon[i].status.toString(),370,Yzer+20,18);
					else
					paintPokeFont(g,"FNT",370,Yzer+20,18);

					g.drawImage(battleImages[6],52,Yzer+22,22,16,this); //Draws L:
					paintPokeFont(g,"" + playerPokemon[i].level,80,Yzer+22,14);//Draws Level

					Yzer+=90;
				}


				switch(cursorLocation.pokemon)
				{
					case 1:
						g.drawImage(battleImages[11],30,50,21,21,this);
						break;
					case 2:
						g.drawImage(battleImages[11],30,140,21,21,this);
						break;
					case 3:
						g.drawImage(battleImages[11],30,230,21,21,this);
						break;
					case 4:
						g.drawImage(battleImages[11],30,320,21,21,this);
						break;
					case 5:
						g.drawImage(battleImages[11],30,410,21,21,this);
						break;
					case 6:
						g.drawImage(battleImages[11],30,500,21,21,this);
						break;
				}
				break;

		}
	}

	public void paintHealth(Graphics g, Pokemon pokemon, int CenterX, int CenterY)
	{
		//g.drawImage(battleImages[3],480,360,20,20,this);//Draws Slash
		g.drawImage(battleImages[3],CenterX,CenterY,20,20,this);//Draws Slash
		//Health Draw
		if (pokemon.health < 0)
		paintPokeFont(g,"0",CenterX - 24,CenterY,18);
		if (pokemon.health < 10)
		paintPokeFont(g,"" + pokemon.health,CenterX - 24,CenterY,18);
		else if (pokemon.health < 100)
		paintPokeFont(g,"" + pokemon.health,CenterX - 42,CenterY,18);
		else
		paintPokeFont(g,"" + pokemon.health,CenterX - 64,CenterY,18);

		paintPokeFont(g,"" + pokemon.healthMax,CenterX + 20,CenterY,18);
		//End Health Draw

		g.drawImage(battleImages[4],CenterX - 75,CenterY + 25,150,12,this);//Draws Health Bar
		//HealthBar h1 = HealthBar.GREEN;
		//h1 = getHealthBarLength(h1, //Does the calculations
					//pokemon.health,
					//pokemon.healthMax);

		double percent = (double) pokemon.health / (double) pokemon.healthMax;


		if (percent > .5)
		g.setColor(Color.GREEN);
		else if (percent > .2)
		g.setColor(Color.ORANGE);
		else
		g.setColor(Color.RED);

		double placeHolder = percent * 144.0;
		int length = (int) placeHolder;
		if (pokemon.health > 0 && length == 0)
		length = 1;

		g.fillRect(CenterX + 3 - 75,CenterY + 28,length,6); //Draws the Health inside the health bar
	}

	//Paints a String in "PokeFont"
	public void paintPokeFont(Graphics g , String str, int x, int y, int size)
	{
		char[] chars = str.toCharArray();
		Image[] letters = new Image[chars.length];

		for (int i = 0; i<chars.length; i++)
		{
			URL url = Pokemon.class.getResource("Sprites/PokeFont/space.png");

			if (Character.isLowerCase(chars[i]) && Character.isLetter(chars[i]))
			{
				url = Pokemon.class.getResource("Sprites/PokeFont/L" + Character.toUpperCase(chars[i]) + ".png");
			}
			else if (Character.isLetter(chars[i]))
			{
				url = Pokemon.class.getResource("Sprites/PokeFont/C" + chars[i] + ".png");
			}
			else if (Character.isDigit(chars[i]))
			{
				url = Pokemon.class.getResource("Sprites/PokeFont/" + chars[i] + ".png");
			}
			else if (chars[i] == '!')
			{
				url = Pokemon.class.getResource("Sprites/PokeFont/EX.png");
			}
			else if (chars[i] == '.')
			{
				url = Pokemon.class.getResource("Sprites/PokeFont/period.png");
			}
			letters[i] = Toolkit.getDefaultToolkit().getImage(url);

			int adder = size/2;
			g.drawImage(letters[i],x,y,size,size,this);
			x+=adder*2.5;
		}
	}
	//This method does all the healthbar calculations
	public HealthBar getHealthBarLength(HealthBar h8, int health, int maxHealth)
	{

		if (health < 0)
		health = 0;

		h8.percent = (double) health / (double) maxHealth;
		double placeHolder = h8.percent * 144.0;
		h8.length = (int) placeHolder;


		if (h8.percent > .5)
		h8 = HealthBar.GREEN;
		else if (h8.percent > .2)
		h8 = HealthBar.ORANGE;
		else
		h8 = HealthBar.RED;

		return h8;
	}

	//Paints Exp Bar
	public void paintExpBar(Graphics g)
	{
		g.setColor(Color.CYAN);
		//g.drawImage(battleImages[4],415,385,150,12,this);

		if(allowedToPaintExp)
		{
			int prevLev=Battle.getLevel(userSelected)-1;
			int nextLev=prevLev+1;
			if(nextLev>100)
				nextLev=100;

			int expOver=Battle.getExp(userSelected)-(prevLev*prevLev*prevLev);
			int expBetween=(nextLev*nextLev*nextLev)-(prevLev*prevLev*prevLev);

			double percent = (double) expOver / (double) expBetween;
			double placeHolder = percent * 174.0;
			int lengthBar = (int) placeHolder;

			if(lengthBar>174)
				lengthBar=174;

			g.fillRect(406,391,lengthBar,6);
		}
	}

	public static void main(String[] args)
	{
		ItemTest t1 = new ItemTest();
		Pokemon[] user=new Pokemon[6];
		Pokemon[] enemy=new Pokemon[6];
		int i = 0;
		user[i]=new Pokemon(Pokemon.Species.TAUROS,100);
		enemy[i]=new Pokemon(Pokemon.Species.MEWTWO,30);
		i++;
		user[i]=new Pokemon(Pokemon.Species.GYARADOS,100);
		enemy[i]=new Pokemon(Pokemon.Species.MEWTWO,30);
		i++;
		user[i]=new Pokemon(Pokemon.Species.MAGIKARP,1);
		enemy[i]=new Pokemon(Pokemon.Species.MEWTWO,30);
		i++;
		//user[i]=new Pokemon(Pokemon.Species.MAGIKARP,1);
		enemy[i]=new Pokemon(Pokemon.Species.MEWTWO,48);
		i++;
		//user[i]=new Pokemon(Pokemon.Species.MAGIKARP,1);
		enemy[i]=new Pokemon(Pokemon.Species.MEWTWO,48);
		i++;
	}



}