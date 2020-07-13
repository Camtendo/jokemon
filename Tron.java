import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;
import java.net.*;

public class Tron extends JPanel implements KeyListener, Minigame
{
	long startTime;
	double time;
	final int timer = 4;
	int rMove;
	byte dir = 0, dir1 = 0;
	final int[][] map = new int[50][50];
	final int[] x = new int[151];
    final int[] y = new int[151];
	final int[] x1 = new int[151];
    final int[] y1 = new int[151];
	final Font gameFont = new Font("Agency FB", Font.BOLD, 40);
	final Font tronFont = new Font("Agency FB", Font.BOLD, 60);
	boolean move = false, dead = false, gameStart = false, dead1 = false, firstMove = false;
	boolean solo = false;
	int rScore = 0, bScore = 0;

	public String getDescription()
	{
		return "Get on your lightcycle and have some fun! 2-Players";
	}
    public String getName()
    {
    	return "Tron";
    }
    public String getAuthor()
    {
    	return "Patches";
    }

    public static void main(String[] args)
   	{
     	new Tron();
   	}
	public Tron()
   	{
     	this.setupGUI();
     	this.run();
   	}

   	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

		switch(key)
		{
			case 65:
				if((dir == 0 || dir == 2) && !solo)
					dir = 3;
				break;
			case 68:
				if((dir == 0 || dir == 2) && !solo)
					dir = 1;
				break;
			case 83:
				if((dir == 1 || dir == 3) && !solo)
					dir = 2;
				break;
			case 87:
				if((dir == 1 || dir == 3) && !solo)
					dir = 0;
				break;
			case 37:
				if(dir1 == 0 || dir1 == 2)
					dir1 = 3;
				break;
			case 39:
				if(dir1 == 0 || dir1 == 2)
					dir1 = 1;
				break;
			case 40:
				if(dir1 == 1 || dir1 == 3)
					dir1 = 2;
				break;
			case 38:
				if(dir1 == 1 || dir1 == 3)
					dir1 = 0;
				break;
			case 32:
				if(!gameStart)
				{
					gameStart = true;
					for(int i = 0; i < 50; i++)
					{
						for(int f = 0; f < 50; f++)
						{
							map[i][f] = 0;
						}
					}
					for(int i = 0; i < x.length-1; i++)
					{
						x[i] = 24;
						y[i] = 49;
						map[y[i]][x[i]] = 1;
						dir = 0;
					}
					for(int i = 0; i < x1.length-1; i++)
					{
						x1[i] = 24;
						y1[i] = 0;
						map[y1[i]][x1[i]] = 2;
						dir1 = 2;
					}
				}
				if(!firstMove)
				{
					solo = true;
				}
				if(dead || dead1)
				{
					for(int i = 0; i < 50; i++)
					{
						for(int f = 0; f < 50; f++)
						{
							map[i][f] = 0;
						}
					}
					for(int i = 0; i < x.length-1; i++)
					{
						x[i] = 24;
						y[i] = 49;
						map[y[i]][x[i]] = 1;
						dir = 0;
					}
					for(int i = 0; i < x1.length-1; i++)
					{
						x1[i] = 24;
						y1[i] = 0;
						map[y1[i]][x1[i]] = 2;
						dir1 = 2;
					}
					dead = false;
					dead1 = false;
				}
				break;
			case 77:
				if(!gameStart && !firstMove)
				{
					gameStart = true;
					solo = false;
					for(int i = 0; i < 50; i++)
					{
						for(int f = 0; f < 50; f++)
						{
							map[i][f] = 0;
						}
					}
					for(int i = 0; i < x.length-1; i++)
					{
						x[i] = 24;
						y[i] = 49;
						map[y[i]][x[i]] = 1;
						dir = 0;
					}
					for(int i = 0; i < x1.length-1; i++)
					{
						x1[i] = 24;
						y1[i] = 0;
						map[y1[i]][x1[i]] = 2;
						dir1 = 2;
					}
				}
				break;
			case 27:
				if(firstMove)
				{
					rScore = 0;
					bScore = 0;
					firstMove = false;
					gameStart = false;
					move = false;
					solo = false;
					dead = false;
					dead1 = false;
				}
				break;
		}

		System.out.println(key);
	}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}

   	public void setupGUI()
   	{
   		JFrame jf = new JFrame("--TRON--");
   		jf.setSize(550, 550);
		jf.setLayout(new GridLayout(1,1));
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		this.setLayout(null);
		Container cp = jf.getContentPane();

		for(int i = 0; i < x.length-1; i++)
		{
			x[i] = 24;
			y[i] = 49;
			map[y[i]][x[i]] = 1;
		}
		for(int i = 0; i < x1.length-1; i++)
		{
			x1[i] = 24;
			y1[i] = 0;
			map[y1[i]][x1[i]] = 2;
			dir1 = 2;
		}

		jf.addKeyListener(this);
		cp.add(this);
		jf.setVisible(true);
   	}

   	public void runEnemy()
   	{
   		if(solo)
	   	{
	   		if(dir == 3)
	   		{
	   			if(x[0] > 0)
   				{
		   			if(map[y[0]][x[0]-1] > 0)
		   			{
		   				rMove = (int)(Math.random()*2);
		   				if(rMove == 0)
		   					dir = 0;
		   				else
		   					dir = 2;
		   			}
   				}else{
   					if(map[y[0]][49] > 0)
		   			{
		   				rMove = (int)(Math.random()*2);
		   				if(rMove == 0)
		   					dir = 0;
		   				else
		   					dir = 2;
		   			}
   				}
	   		}
	   		else if(dir == 1)
	   		{
	   			if(x[0] < 49)
   				{
		   			if(map[y[0]][x[0]+1] > 0)
		   			{
		   				rMove = (int)(Math.random()*2);
		   				if(rMove == 0)
		   					dir = 0;
		   				else
		   					dir = 2;
		   			}
   				}else{
   					if(map[y[0]][0] > 0)
		   			{
		   				rMove = (int)(Math.random()*2);
		   				if(rMove == 0)
		   					dir = 0;
		   				else
		   					dir = 2;
		   			}
   				}
	   		}
	   		else if(dir == 2)
	   		{
	   			if(y[0] < 49)
   				{
		   			if(map[y[0]+1][x[0]] > 0)
		   			{
		   				rMove = (int)(Math.random()*2);
		   				if(rMove == 0)
		   					dir = 1;
		   				else
		   					dir = 3;
		   			}
   				}else{
   					if(map[0][x[0]] > 0)
		   			{
		   				rMove = (int)(Math.random()*2);
		   				if(rMove == 0)
		   					dir = 1;
		   				else
		   					dir = 3;
		   			}
   				}
	   		}
	   		else if(dir == 0)
	   		{
	   			if(y[0] > 0)
   				{
		   			if(map[y[0]-1][x[0]] > 0)
		   			{
		   				rMove = (int)(Math.random()*2);
		   				if(rMove == 0)
		   					dir = 1;
		   				else
		   					dir = 3;
		   			}
   				}else{
   					if(map[49][x[0]] > 0)
		   			{
		   				rMove = (int)(Math.random()*2);
		   				if(rMove == 0)
		   					dir = 1;
		   				else
		   					dir = 3;
		   			}
   				}
	   		}
   		}
   	}

   	public void updateGame()
   	{
   		if(gameStart && !firstMove)
   		{
   			startTime = System.currentTimeMillis();
   			gameStart = false;
   			firstMove = true;
   		}
   		if(move && !dead && !dead1)
   		{
   			for(int i = x.length - 1; i > 0; i--)
   			{
   				x[i] = x[i-1];
   				y[i] = y[i-1];
   				map[y[i]][x[i]] = 1;
   				if(i == x.length - 2)
   				{
   					map[y[i+1]][x[i+1]] = 0;
   				}
   				x1[i] = x1[i-1];
   				y1[i] = y1[i-1];
   				map[y1[i]][x1[i]] = 2;
   				if(i == x1.length - 2)
   				{
   					map[y1[i+1]][x1[i+1]] = 0;
   				}
   			}
   			switch(dir)
   			{
   				case 0:
   					if(y[0] > 0)
   					{
   						if(map[y[0]-1][x[0]] > 0)
   						{
   							bScore++;
   							dead = true;
   						}else{
   							y[0]--;
   						}
   					}else if(y[0] == 0){
						if(map[49][x[0]] > 0)
						{
							bScore++;
							dead = true;
						}else
							y[0] = 49;
   					}
   					break;
   				case 1:
   					if(x[0] < 49)
   					{
   						if(map[y[0]][x[0]+1] > 0)
   						{
   							bScore++;
   							dead = true;
   						}else{
   							x[0]++;
   						}
   					}else if(x[0] == 49){
						if(map[y[0]][0] > 0)
						{
							bScore++;
							dead = true;
						}else
   							x[0] = 0;
   					}
   					break;
   				case 2:
   					if(y[0] < 49)
   					{
   						if(map[y[0]+1][x[0]] > 0)
   						{
   							bScore++;
   							dead = true;
   						}else{
   							y[0]++;
   						}
   					}else if(y[0] == 49){
   						if(map[0][x[0]] > 0)
						{
							bScore++;
							dead = true;
						}else
   						 y[0] = 0;
   					}
   					break;
   				case 3:
   					if(x[0] > 0)
   					{
   						if(map[y[0]][x[0]-1] > 0)
   						{
   							bScore++;
   							dead = true;
   						}else{
   							x[0]--;
   						}
   					}else if(x[0] == 0){
   						if(map[y[0]][49] > 0)
						{
							bScore++;
							dead = true;
						}else
   							x[0] = 49;
   					}
   					break;
   			}
   			switch(dir1)
   			{
   				case 0:
   					if(y1[0] > 0)
   					{
   						if(map[y1[0]-1][x1[0]] > 0)
   						{
   							rScore++;
   							dead1 = true;
   						}else{
   							y1[0]--;
   						}
   					}else if(y1[0] == 0){
						if(map[49][x1[0]] > 0)
						{
							rScore++;
							dead1 = true;
						}else
							y1[0] = 49;
   					}
   					break;
   				case 1:
   					if(x1[0] < 49)
   					{
   						if(map[y1[0]][x1[0]+1] > 0)
   						{
   							rScore++;
   							dead1 = true;
   						}else{
   							x1[0]++;
   						}
   					}else if(x1[0] == 49){
						if(map[y1[0]][0] > 0)
						{
							rScore++;
							dead1 = true;
						}else
   							x1[0] = 0;
   					}
   					break;
   				case 2:
   					if(y1[0] < 49)
   					{
   						if(map[y1[0]+1][x1[0]] > 0)
   						{
   							rScore++;
   							dead1 = true;
   						}else{
   							y1[0]++;
   						}
   					}else if(y1[0] == 49){
   						if(map[0][x1[0]] > 0)
						{
							rScore++;
							dead1 = true;
						}else
   						 y1[0] = 0;
   					}
   					break;
   				case 3:
   					if(x1[0] > 0)
   					{
   						if(map[y1[0]][x1[0]-1] > 0)
   						{
   							rScore++;
   							dead1 = true;
   						}else{
   							x1[0]--;
   						}
   					}else if(x1[0] == 0){
   						if(map[y1[0]][49] > 0)
						{
							rScore++;
							dead1 = true;
						}else
   							x1[0] = 49;
   					}
   					break;
   			}

   			move = false;
   		}

   		if(firstMove)
   		{
	   		time = (double)((int)((double)(System.currentTimeMillis() - startTime) / 1D) / 10D);
	   		if(time >= timer)
	   		{
	   			move = true;
	   			startTime = System.currentTimeMillis();
	   		}
   		}
   	}

   	public void run()
   	{
   		while(true)
   		{
   			runEnemy();
   			updateGame();
   			try{Thread.sleep(1);}catch(Exception ignored){}
			repaint();
   		}
   	}

   	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		g.setFont(gameFont);

		g.setColor(Color.gray);
		g.fillRect(0, 0, 600, 600);

		for(int i = 0; i < 50; i++)
		{
			for(int f = 0; f < 50; f++)
			{
				switch(map[i][f])
				{
					case 0:
						g.setColor(Color.black);
						break;
					case 1:
						g.setColor(Color.red);
						break;
					case 2:
						g.setColor(Color.blue);
						break;
				}

				g.fillRect((f + 9 * f) + (25 - getRootPane().getX()), (i + 9 * i) + (35 - getRootPane().getY()), 10, 10);
			}
		}

		g.setColor(Color.white);
		g.fillRect((x[0] + 9 * x[0]) + (25 - getRootPane().getX()), (y[0] + 9 * y[0]) + (35 - getRootPane().getY()), 10, 10);
		g.fillRect((x1[0] + 9 * x1[0]) + (25 - getRootPane().getX()), (y1[0] + 9 * y1[0]) + (35 - getRootPane().getY()), 10, 10);
		g.setColor(Color.red);
		g.fillRect((x[0] + 9 * x[0]) + (25 - getRootPane().getX()) + 1, (y[0] + 9 * y[0]) + (35 - getRootPane().getY()) + 1, 8, 8);
		g.setColor(Color.blue);
		g.fillRect((x1[0] + 9 * x1[0]) + (25 - getRootPane().getX()) + 1, (y1[0] + 9 * y1[0]) + (35 - getRootPane().getY()) + 1, 8, 8);

		if(!firstMove)
		{
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(0, 0, 550, 550);
			g.setColor(Color.green);
			g.setFont(tronFont);
			g.drawString("TRON", 224, 200);
			g.setFont(gameFont);
			g.drawString("SPACEBAR TO PLAY SOLO", 108, 275);
			g.drawString("'M' TO PLAY 2-PLAYER", 123, 355);
		}

		if(dead || dead1)
		{
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(0, 0, 550, 550);
		}
		if(dead && !dead1)
		{
			g.setColor(Color.blue);
			g.drawString("GAME OVER", 190, 190);
			g.drawString("BLUE WINS!", 190, 250);
			g.drawString("BLUE:" + bScore, 380, 80);
			g.drawString("RED:" + rScore, 60, 80);
			g.drawString("SPACEBAR TO REPLAY", 115, 455);
			g.drawString("'ESC' TO GO TO MENU", 120, 495);
		}else if(dead1 && !dead){
			g.setColor(Color.red);
			g.drawString("GAME OVER", 190, 190);
			g.drawString("RED WINS!", 195, 250);
			g.drawString("BLUE:" + bScore, 380, 80);
			g.drawString("RED:" + rScore, 60, 80);
			g.drawString("SPACEBAR TO REPLAY", 115, 455);
			g.drawString("'ESC' TO GO TO MENU", 120, 495);
		}else if(dead1 && dead){
			g.setColor(Color.green);
			g.drawString("GAME OVER", 190, 190);
			g.drawString("TIE!", 244, 250);
			g.drawString("BLUE:" + bScore, 380, 80);
			g.drawString("RED:" + rScore, 60, 80);
			g.drawString("SPACEBAR TO REPLAY", 115, 455);
			g.drawString("'ESC' TO GO TO MENU", 120, 495);
		}
	}
}