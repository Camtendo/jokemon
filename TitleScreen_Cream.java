/////////////////////////////////////
//Jokemon Title Screen
//Cream Version
//
//By Camtendo
//
//
//
//

import javax.swing.JPanel;
import javax.swing.JApplet;
import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Point;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.*;
import java.net.URL;
import java.applet.AudioClip;

public class TitleScreen_Cream extends JPanel implements Runnable
{
	Thread thread;
	AudioClip titleMusic;
    final AudioClip creditMusic;
	Image icon,logo,mascot;
	String fps="1";
	long lastFPS;
	int frames, slp=8;
	final Point[] randStars=new Point[300];
	final Font font=new Font("Sanserif", Font.BOLD, 60);
	int creditInt;
	final Font smallerMenuFont=new Font("Sanserif", Font.BOLD, 14);
	final Image camtendo;
    final Image justinian;
    final Image jinc;

	public static void main(String[] peaches)
	{
		TitleScreen_Cream t=new TitleScreen_Cream();
		t.titleMusic.stop();
		t.creditMusic.play();
		t.setupGUI();
		t.beginThread();
	}
	
	public TitleScreen_Cream()
	{
		URL introU=TitleScreen_Cream.class.getResource("titlemusic.mid");
    	titleMusic=JApplet.newAudioClip(introU);
    	
    	URL url=TitleScreen_Cream.class.getResource("Music/credits.mid");
    	creditMusic=JApplet.newAudioClip(url);

		URL iconU=TitleScreen_Cream.class.getResource("icon.png");
    	icon=Toolkit.getDefaultToolkit().getImage(iconU);

    	iconU=TitleScreen_Cream.class.getResource("cream.png");
    	logo=Toolkit.getDefaultToolkit().getImage(iconU);

    	iconU=TitleScreen_Cream.class.getResource("mew.gif");
    	mascot=Toolkit.getDefaultToolkit().getImage(iconU);
    	
    	iconU=TitleScreen_Peaches.class.getResource("Logos/Justinian.png");
    	justinian=Toolkit.getDefaultToolkit().getImage(iconU);
    	
    	iconU=TitleScreen_Peaches.class.getResource("Logos/Jinc.png");
    	jinc=Toolkit.getDefaultToolkit().getImage(iconU);
    	
    	iconU=TitleScreen_Peaches.class.getResource("Logos/Camtendo.png");
    	camtendo=Toolkit.getDefaultToolkit().getImage(iconU);

    	for(int i=0; i<300; i++)
		{
			randStars[i]=new Point(0,0);
			randStars[i].x=(int)(Math.random()*800);
			randStars[i].y=(int)(Math.random()*600);
		}
		
		titleMusic.loop();
		
	}

	public void paintTitle(Graphics g)
	{	
		g.setColor(Color.CYAN);
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(Color.YELLOW);
		g.fillOval(650,-100,300,300);

		for(int i=0; i<300; i++)
		{
			g.setColor(new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
			if(i%20==0&&randStars[i].y<400)
			{
				g.setColor(Color.BLACK);
				g.fillOval(randStars[i].x-1,randStars[i].y-1,42,63);
				g.drawLine(randStars[i].x+20,randStars[i].y+60,randStars[i].x+20,randStars[i].y+110);

				g.setColor(Color.RED);
				g.fillOval(randStars[i].x,randStars[i].y,40,60);
			}
			else
				g.fillRect(randStars[i].x,randStars[i].y,5,5);

			randStars[i].x--;
			if(randStars[i].x<0)
				randStars[i].x=800;
			if(i%20!=0)
			randStars[i].y++;
			else
			{
				if(Math.abs(randStars[0].x)%50>=10)
				randStars[i].y--;
			}
			if(randStars[i].y>500)
				randStars[i].y=0;
			if(randStars[i].y<0)
				randStars[i].y=500;
		}

		g.setColor(Color.GREEN);
		g.fillRect(0,500,800,100);

		for(int i=0; i<300; i++)
		{
			g.drawLine(randStars[i].x-10,500,randStars[i].x,490);
			g.drawLine(randStars[i].x-5,500,randStars[i].x,490);
			g.drawLine(randStars[i].x,500,randStars[i].x,490);
			g.drawLine(randStars[i].x+5,500,randStars[i].x,490);
			g.drawLine(randStars[i].x+10,500,randStars[i].x,490);
			if(randStars[i].x<0)
				randStars[i].x=800;
		}

		g.drawImage(mascot,325,275,66*2,41*2,this);
		g.drawImage(logo,165,10,this);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Cream",270,220);
		g.setColor(Color.WHITE);
		if(Math.abs(randStars[0].x)%50>=10)
		g.drawString("Press Spacebar",170,470);
	}
	
	public void paintCredits(Graphics g)
	{
		g.setColor(new Color(255,126,0,creditInt%256));
		g.fillRect(0,0,getWidth(),getHeight());
			
		g.setFont(smallerMenuFont);
		g.setColor(Color.WHITE);
		
		g.drawImage(mascot,0,500,this);
		g.drawImage(mascot,725,500,this);
		g.drawImage(logo,180,600-creditInt/3,this);
		g.drawString("Director-------------------------Cameron Crockrom 'Camtendo'",150,800-creditInt/3);
		g.drawString("Graphic Organizer----------------Justin Begeman 'Justinian'",150,900-creditInt/3);
		g.drawString("Character Design-----------------Camtendo",150,1000-creditInt/3);
		g.drawString("Pokedex Design-------------------Anthony Balmer 'Wasabi Sause'",150,1100-creditInt/3);
		g.drawString("Music----------------------------Camtendo and Justinian",150,1200-creditInt/3);
		g.drawString("Sound Effects--------------------Camtendo",150,1300-creditInt/3);
		g.drawString("Overworld Design-----------------Justinian",150,1400-creditInt/3);
		g.drawString("Overworld Programming------------Braunsen Cottingham 'Patches'",150,1500-creditInt/3);
		g.drawString("Overworld Programming------------Camtendo",150,1600-creditInt/3);
		g.drawString("Overworld Programming------------Justinian",150,1700-creditInt/3);
		g.drawString("Pokemon Programming--------------Camtendo and Justinian",150,1800-creditInt/3);
		g.drawString("Game Scenario--------------------Camtendo, Justinian, and Patches",150,1900-creditInt/3);
		g.drawString("Map Design-----------------------Justinian",150,2000-creditInt/3);
		g.drawString("Battle Engine--------------------Camtendo and Justinian",150,2100-creditInt/3);
		g.drawString("Item Design----------------------Justinian",150,2200-creditInt/3);
		g.drawString("Game Mechanics-------------------Camtendo",150,2300-creditInt/3);
		g.drawString("Data Security--------------------Camtendo",150,2400-creditInt/3);
		g.drawString("Window Mechanics-----------------Justinian",150,2500-creditInt/3);
		g.drawString("Minigames------------------------Mr. Babb, Sam Rohl, and Patches",150,2600-creditInt/3);
		g.drawString("Badge Design---------------------Natalie C. Aka Justinian's Girlfriend",150,2700-creditInt/3);
		g.drawString("Lead Testers---------------------Chris Hand and Patches",150,2800-creditInt/3);
		g.drawString("Beta Testers------------------Babb's 3rd Period Comp Sci AP Class",150,2900-creditInt/3);
		g.drawString("Battle Engine Testers------------Sam Eiserman, Karl Smith, Trevor Jackson, Kevin Hanna",150,3000-creditInt/3);
		g.drawString("Battle Engine Testers------------Brian Healy, Jedd Patterson, Devin Freese",150,3100-creditInt/3);
		g.drawString("Donors-----Ryan Dalton, Jeff Harris, Trogdor Jackson, That guy that made Frogger, Scoticus",150,3200-creditInt/3);
		g.drawString("Special Thanks-------------------Mr. Babb, Nintendo, Oracle",150,3500-creditInt/3);
		g.drawString("Special Thanks-------------------Guys who made midis, Sprite Rippers",150,3600-creditInt/3);
		g.drawString("The End (Game Data has been saved!)",150,4000-creditInt/3);
			
		creditInt++;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		paintCredits(g);
	}

	public void setupGUI()
	{
		URL introU=TitleScreen_Cream.class.getResource("titlemusic.mid");
    	titleMusic=JApplet.newAudioClip(introU);

		URL iconU=TitleScreen_Cream.class.getResource("icon.png");
    	icon=Toolkit.getDefaultToolkit().getImage(iconU);

    	iconU=TitleScreen_Cream.class.getResource("cream.png");
    	logo=Toolkit.getDefaultToolkit().getImage(iconU);

    	iconU=TitleScreen_Cream.class.getResource("mew.gif");
    	mascot=Toolkit.getDefaultToolkit().getImage(iconU);

    	for(int i=0; i<300; i++)
		{
			randStars[i]=new Point(0,0);
			randStars[i].x=(int)(Math.random()*800);
			randStars[i].y=(int)(Math.random()*600);
		}

		JFrame jf=new JFrame("Jokemon: Cream");
		Container cp=jf.getContentPane();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(800,600);
		jf.setLayout(new GridLayout(1,1));
		cp.add(this);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setIconImage(icon);
		jf.setVisible(true);
		//jf.addKeyListener(this);
		//jf.addMouseListener(this);
		//jf.addMouseMotionListener(this);
	}

	public void run()
	{
		while(Integer.parseInt(fps)<80||Integer.parseInt(fps)>110)
		{
			frames++;
			frameRateManager();
			creditMusic.stop();
			try
			{
				Thread.sleep(slp);
			}
			catch(Exception ignored){}
		}
		
		creditMusic.loop();
		while(true)
		{
			repaint();
			frames++;
			frameRateManager();

			try
			{
				Thread.sleep(slp);
			}
			catch(Exception ignored){}
		}
	}

	public void beginThread()
	{
		if (thread == null)
		{
      		thread = new Thread(this);
      		thread.start();
		}
	}

	public void frameRateManager()
	{
		if( lastFPS <=System.currentTimeMillis() )
		{
			fps=""+frames;
			//System.out.println(fps);
			lastFPS = System.currentTimeMillis() + 1000;
			if(frames<80&&slp>0)
			{
				System.out.println("Low Frame Rate: "+fps);
				slp--;
			}
			else if(frames>110)
			{
				System.out.println("High Frame Rate: "+fps);
				slp++;
			}
			frames=0;
		}
	}
}