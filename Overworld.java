import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;

public class Overworld extends JPanel implements KeyListener, Runnable
{
	//FINAL TILE CONSTANTS
	public final int 	TREE=1,
						LOW_GRASS=2,
						W_LEFT_RIDGE=3,
						WATER=4,
						WATER_BARRIER=5,
						DOOR=6,
						WHITE=7,
						EXIT=8,
						BUILD_BLEFT=9,
						JOKE=10,
						BUILD_BRIGHT=11,
						BUILD_MAIN=12,
						BUILD_LEFT=13,
						BUILD_RIGHT=14,
						ORANGE_TOP=15;
						
	
	int x = 0, y = 0, superCount = 0;
	int dir = 0;
	int count = 0, countV1 = 16, countV2 = 4;
	int yDraw = -32, xDraw = -32;
	int cX = 167, cY = 450;			//Your current location on the tile array
	int[][] tile = new int[500][500];
	Scanner in = new Scanner(System.in);
	boolean atm = true, switchSide = false, bicycle = false, hack = false;
	byte bikeDir = 0, map = 0;
	Image cImg, jb;
	Image[] img = new Image[20];
	public Pokemon user[] = new Pokemon[6];
	public Pokemon enemy[] = new Pokemon[6];
	private JFrame jf;
	private Container c;

	public static void main(String[] args)
	{
		ItemTest t1 = new ItemTest();
		Overworld sb = new Overworld();
		sb.tileInit();
		sb.setupGUI();
		sb.run();
	}

	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		System.out.println(key);

		if(key == 37 && atm)
		{
				dir = 1;
				atm = false;
		}if(key == 38 && atm)
		{
				dir = 2;
				atm = false;
		}if(key == 39 && atm)
		{
				dir = 3;
				atm = false;
		}if(key == 40 && atm)
		{
				dir = 4;
				atm = false;
		}

		if(key == 66 && atm)
		{
			if(!bicycle){
				bicycle = true;
				countV1 = 8;
				countV2 = 8;
				if(bikeDir == 0)
					cImg = new ImageIcon("oSprites/cb1.png").getImage();
				else if(bikeDir == 1)
					cImg = new ImageIcon("oSprites/cb8.png").getImage();
				else if(bikeDir == 2)
					cImg = new ImageIcon("oSprites/cb9.png").getImage();
				else if(bikeDir == 3)
					cImg = new ImageIcon("oSprites/cb7.png").getImage();
			}else{
				bicycle = false;
				countV1 = 16;
				countV2 = 4;
				if(bikeDir == 0)
					cImg = new ImageIcon("oSprites/c3.png").getImage();
				else if(bikeDir == 1)
					cImg = new ImageIcon("oSprites/c2.png").getImage();
				else if(bikeDir == 2)
					cImg = new ImageIcon("oSprites/c4.png").getImage();
				else if(bikeDir == 3)
					cImg = new ImageIcon("oSprites/c1.png").getImage();
			}
		}
		if(key == 72 && atm && !hack)
		{
			hack = true;
		}
		else if(key == 72 && atm && hack)
		{
			hack = false;
		}
		if(key == 49 && atm)
		{
			cX = in.nextInt();
		}
		if(key == 50 && atm)
		{
			cY = in.nextInt();
		}

		if(key == 82 && atm)
		{

		}

		System.out.println(x);
		System.out.println(y);

	}
	public void keyReleased(KeyEvent e){int key = e.getKeyCode();}
	public void keyTyped(KeyEvent e){}

	public void randomBattle()
	{
		jf.setVisible(false);
		enemy[0]=Mechanics.randomEncounter(Pokemon.Species.ARTICUNO,5,42,Pokemon.Species.PARASECT,10,46,Pokemon.Species.TAUROS,20,52,
		Pokemon.Species.PIDGEOT,25,40,Pokemon.Species.PERSIAN,40,55);
		new BattleWindow(user,enemy,"WILD");

		for(int i=0; i<6; i++)
		{
			user[i]=Battle.getPokemon(i);
		}

		jf.setVisible(true);
	}

	public void setupGUI()
	{
		jf = new JFrame("Joe Kay Mon");
		jf.setSize(800,600);
		jf.setLayout(new GridLayout(1,1));
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		c = jf.getContentPane();


		img[1] = new ImageIcon("oSprites/tile1.png").getImage();
		img[2] = new ImageIcon("oSprites/tile2.png").getImage();
		img[3] = new ImageIcon("oSprites/tile3.png").getImage();
		img[4] = new ImageIcon("oSprites/tile4.png").getImage();
		img[5] = new ImageIcon("oSprites/tile5.png").getImage();
		img[6] = new ImageIcon("oSprites/tile6.png").getImage();
		img[7] = new ImageIcon("oSprites/tile7.png").getImage();
		img[8] = new ImageIcon("oSprites/tile8.png").getImage();
		img[9] = new ImageIcon("oSprites/tile9.png").getImage();
		img[10] = new ImageIcon("oSprites/tile10.png").getImage();
		img[11] = new ImageIcon("oSprites/tile11.png").getImage();
		img[12] = new ImageIcon("oSprites/tile12.png").getImage();
		img[13] = new ImageIcon("oSprites/tile13.png").getImage();
		img[14] = new ImageIcon("oSprites/tile14.png").getImage();
		cImg = new ImageIcon("oSprites/c1.png").getImage();

		this.setLayout(null);
		c.add(this);

		jf.addKeyListener(this);
		jf.setVisible(true);

		user[0]=new Pokemon(Pokemon.Species.MEWTWO,Pokemon.Move.PSYCHIC,Pokemon.Move.ICE_BEAM,Pokemon.Move.THUNDERBOLT,Pokemon.Move.RECOVER,50);
		user[1]=new Pokemon(Pokemon.Species.STARMIE,48);
		user[2]=new Pokemon(Pokemon.Species.DEWGONG,40);
		user[3]=new Pokemon(Pokemon.Species.GENGAR,52);
		user[4]=new Pokemon(Pokemon.Species.LAPRAS,49);
		user[5]=new Pokemon(Pokemon.Species.CHARIZARD,44);
	}
	
	public void tileInit()
	{
		//Stringville
		for(int i=0; i<50; i++)
		tile[167+i][495]=WATER;
		
		for(int i=0; i<50; i++)
		tile[167+i][496]=WATER;
		
		for(int i = 0; i < 500; i++)
		{
			for(int j = 0; j <500; j++)
			{
				tile[i][j] = LOW_GRASS;
			}
		}

//		tile[30][30] = 1;
//		tile[11][490] = 1;
//		tile[14][490] = 5;
//		tile[14][489] = 9;
//		tile[14][491] = 10;
//		tile[14][492] = 11;
//		tile[13][492] = 14;
//		tile[13][489] = 13;
//		tile[13][490] = 12;
//		tile[13][491] = 12;

		//Stringville
		for(int i=0; i<50; i++)
		tile[167+i][495]=WATER;
		
		for(int i=0; i<50; i++)
		tile[167+i][496]=WATER;
	}

	public void checkLocation()
	{
		if(tile[cY][cX] == 5 && map == 0)
		{
			cY = 245;
			cX = 245;
			map = 1;

			tile[244][246] = 6;
			tile[244][244] = 6;
			tile[244][245] = 6;
			tile[245][245] = 8;
			tile[245][244] = 6;
			tile[245][246] = 6;
			tile[246][245] = 7;
		}
		if(tile[cY][cX] == 7 && map == 1)
		{
			cY = 15;
			cX = 490;
			map = 0;
		}
	}

	public void moveCursor()
	{
		if(dir == 1)
		{
			if(tile[cY][cX-1] == 0 || tile[cY][cX-1] == 5 || tile[cY][cX-1] == 7 || tile[cY][cX-1] == 6 || tile[cY][cX-1] == 8 && cX % 500 > 7 || hack)
				x += countV2;
			bikeDir = 0;
			count++;
			if(!bicycle)
				cImg = new ImageIcon("oSprites/c7.png").getImage();
			else
				cImg = new ImageIcon("oSprites/cb2.png").getImage();
			if(count == countV1)
			{
				count = 0;
				if(tile[cY][cX-1] == 0 || tile[cY][cX-1] == 5 || tile[cY][cX-1] == 7 || tile[cY][cX-1] == 6 || tile[cY][cX-1] == 8 && cX % 500 > 7 || hack)
				{
					cX--;
					x=0;
				}
				atm = true;
				dir = 0;
				System.out.println("\t\t" + cX);
				System.out.println("\t\t" + cY);
				if(!bicycle)
					cImg = new ImageIcon("oSprites/c3.png").getImage();
				else
					cImg = new ImageIcon("oSprites/cb1.png").getImage();
			}
		}if(dir == 2)
		{
			if(tile[cY-1][cX] == 0 || tile[cY-1][cX] == 5 || tile[cY-1][cX] == 7 || tile[cY-1][cX] == 6 || tile[cY-1][cX] == 8 && cY % 500 > 5 || hack)
				y += countV2;
			bikeDir = 1;
			count++;
			if(switchSide && !bicycle)
				cImg = new ImageIcon("oSprites/c6.png").getImage();
			else if(!switchSide && !bicycle)
				cImg = new ImageIcon("oSprites/c10.png").getImage();
			else if(switchSide && bicycle)
				cImg = new ImageIcon("oSprites/cb5.png").getImage();
			else if(!switchSide && bicycle)
				cImg = new ImageIcon("oSprites/cb6.png").getImage();

			if(count == countV1)
			{
				if(switchSide)
					switchSide = false;
				else
					switchSide = true;
				count = 0;
				if(tile[cY-1][cX] == 0 || tile[cY-1][cX] == 5 || tile[cY-1][cX] == 7 || tile[cY-1][cX] == 6 || tile[cY-1][cX] == 8 && cY % 500 > 5 || hack){
					cY--;
					y=0;
				}
				atm = true;
				dir = 0;
				System.out.println("\t\t" + cX);
				System.out.println("\t\t" + cY);
				if(!bicycle)
					cImg = new ImageIcon("oSprites/c2.png").getImage();
				else
					cImg = new ImageIcon("oSprites/cb8.png").getImage();
			}
		}if(dir == 3)
		{
			if(tile[cY][cX+1] == 0 || tile[cY][cX+1] == 5 || tile[cY][cX+1] == 7 || tile[cY][cX+1] == 6 || tile[cY][cX+1] == 8 && cX % 500 < 492 || hack)
				x -= countV2;
			bikeDir = 2;
			count++;
			if(!bicycle)
				cImg = new ImageIcon("oSprites/c8.png").getImage();
			else
				cImg = new ImageIcon("oSprites/cb10.png").getImage();
			if(count == countV1)
			{
				count = 0;
				if(tile[cY][cX+1] == 0 || tile[cY][cX+1] == 5 || tile[cY][cX+1] == 7 || tile[cY][cX+1] == 6 || tile[cY][cX+1] == 8 && cX % 500 < 492 || hack){
					cX++;
					x=0;
				}
				atm = true;
				dir = 0;
				System.out.println("\t\t" + cX);
				System.out.println("\t\t" + cY);
				if(!bicycle)
					cImg = new ImageIcon("oSprites/c4.png").getImage();
				else
					cImg = new ImageIcon("oSprites/cb9.png").getImage();
				}
		}if(dir == 4)
		{
			if(tile[cY+1][cX] == 0 || tile[cY+1][cX] == 5 || tile[cY+1][cX] == 7 || tile[cY+1][cX] == 6 || tile[cY+1][cX] == 8 && cY % 500 < 494 || hack)
				y -= countV2;
			bikeDir = 3;
			count++;
			if(switchSide && !bicycle)
				cImg = new ImageIcon("oSprites/c5.png").getImage();
			else if(!switchSide && !bicycle)
				cImg = new ImageIcon("oSprites/c9.png").getImage();
			else if(switchSide && bicycle)
				cImg = new ImageIcon("oSprites/cb3.png").getImage();
			else if(!switchSide && bicycle)
				cImg = new ImageIcon("oSprites/cb4.png").getImage();

			if(count == countV1)
			{
				if(switchSide)
					switchSide = false;
				else
					switchSide = true;
				count = 0;
				if(tile[cY+1][cX] == 0 || tile[cY+1][cX] == 5 || tile[cY+1][cX] == 7 || tile[cY+1][cX] == 6 || tile[cY+1][cX] == 8 && cY % 500 < 494 || hack){
					cY++;
					y=0;
				}
				atm = true;
				dir = 0;
				System.out.println("\t\t" + cX);
				System.out.println("\t\t" + cY);
				if(!bicycle)
					cImg = new ImageIcon("oSprites/c1.png").getImage();
				else
					cImg = new ImageIcon("oSprites/cb7.png").getImage();
			}
		}
	}

	public void run()
	{
		while(true)
		{
			hack=true;
			moveCursor();
			checkLocation();
			try{Thread.sleep(10);}catch(Exception e){}
			repaint();
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		for(int i = -7; i <= 7; i++)
		{
			yDraw = -64;
			for(int f = -5; f <= 5; f++)
			{
				if(tile[cY + f][cX + i] == -1)
				{
					g.setColor(Color.BLACK);
					g.fillRect(xDraw + x, yDraw + y, 64, 64);
				}
				if(tile[cY + f][cX + i] == 0)
					g.drawImage(img[0], xDraw + x, yDraw + y, 64, 64, this);
				if(tile[cY + f][cX + i] == 1)
					g.drawImage(img[1], xDraw + x, yDraw + y, 64, 64, this);
				if(tile[cY + f][cX + i] == 2)
					g.drawImage(img[2], xDraw + x, yDraw + y, 64, 64, this);
				if(tile[cY + f][cX + i] == 3)
					g.drawImage(img[3], xDraw + x, yDraw + y, 64, 64, this);
				if(tile[cY + f][cX + i] == 4)
					g.drawImage(img[4], xDraw + x, yDraw + y, 64, 64, this);
				if(tile[cY + f][cX + i] == 5)
					g.drawImage(img[5], xDraw + x, yDraw + y, 64, 64, this);
				if(tile[cY + f][cX + i] == 6)
					g.drawImage(img[6], xDraw + x, yDraw + y, 64, 64, this);
				if(tile[cY + f][cX + i] == 7)
				{
					g.setColor(Color.BLACK);
					g.fillRect(xDraw + x, yDraw + y, 64, 64);
				}
				if(tile[cY + f][cX + i] == 8)
					g.drawImage(img[7], xDraw + x, yDraw + y, 64, 64, this);
				if(tile[cY + f][cX + i] == 9)
					g.drawImage(img[8], xDraw + x, yDraw + y, 64, 64, this);
				if(tile[cY + f][cX + i] == 10)
					g.drawImage(img[9], xDraw + x, yDraw + y, 64, 64, this);
				if(tile[cY + f][cX + i] == 11)
					g.drawImage(img[10], xDraw + x, yDraw + y, 64, 64, this);
				if(tile[cY + f][cX + i] == 12)
					g.drawImage(img[11], xDraw + x, yDraw + y, 64, 64, this);
				if(tile[cY + f][cX + i] == 13)
					g.drawImage(img[12], xDraw + x, yDraw + y, 64, 64, this);
				if(tile[cY + f][cX + i] == 14)
					g.drawImage(img[13], xDraw + x, yDraw + y, 64, 64, this);
				if(tile[cY + f][cX + i] == 15)
					g.drawImage(img[14], xDraw + x, yDraw + y, 64, 64, this);

				yDraw+=64;
			}
			xDraw+=64;
		}xDraw = -64;

		g.drawImage(cImg, 384, 256, 64, 64, this);
	}
}