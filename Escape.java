/******************************************************
 *Programmer:	Derek Babb
 *File:			Escape.java
 *
 *Purpose:		This is the main game class for the Escape
 *				game.
 *****************************************************/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Escape extends JPanel implements KeyListener, Minigame
{

	private int cellH, cellW;
	private Player p1;
	private final Enemy[] enemy = new Enemy[100];
	private final int cells;
	private int numEnemy;
	private LeftPanel lp;
	private boolean gameOver = false;
	private JFrame jf;


	public static void main(String[] args)
	{
		Escape e = new Escape();
	}

	public Escape()
	{
		numEnemy = 10;
		cells = 20;
		p1 = new Player(cells);
		for(int i = 0; i<numEnemy; i++)
			enemy[i] = new Enemy(cells);
		setupGUI();
	}

	public void reset()
	{
		repaint();
		try{Thread.sleep(1000);}
		catch(Exception ignored){}
		p1 = new Player(cells, cellW, cellH);
		lp.safeJumps = 3;
		for(int i = 0; i<numEnemy; i++)
			enemy[i] = new Enemy(cells, cellW, cellH);
		repaint();
		lp.updateLabels();
	}


	public void setupGUI()
	{
		Container cp;
		jf = new JFrame("Escape!");
		cp = jf.getContentPane();
		jf.setSize(500,433);
		jf.setLocationRelativeTo(null);
		jf.setLayout(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		this.setBounds(0,0,401,401);
		cp.add(this);
		lp = new LeftPanel();
		lp.setBounds(401,0, jf.getWidth()-407, getHeight());
		cp.add(lp);
		jf.setVisible(true);

		cellH = (int)(getHeight()/cells);
		cellW = (int)(getWidth()/cells);

		p1.setSize(cellW, cellH);
		for(int i = 0; i<numEnemy; i++)
			enemy[i].setSize(cellW, cellH);

		jf.addKeyListener(this);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);


		//draw the background
		for(int h = 0; h <= 400; h+=cellH)
		{
			g.drawLine(0, h, 400, h);
		}
		for(int w = 0; w <= 400; w+=cellW)
		{
			g.drawLine(w, 0, w, 400);
		}


		for(int i = 0; i<numEnemy; i++)
			enemy[i].draw(g);

		p1.draw(g);

		if(gameOver)
		{
			Font f = new Font("Sans Serif", Font.BOLD, 120);
			g.setFont(f);
			g.setColor(new Color(255, 0, 0, 128));
			g.drawString("Game",50, 150);
			g.drawString("Over",50, 270);
		}
	}

	public void checkCol()
	{
		//check player first
		for(int i = 0; i<numEnemy; i++)
			p1.checkCollision(enemy[i].row, enemy[i].column);

		if(!p1.alive)
		{
			lp.lives--;
			lp.updateLabels();
			gameOver = lp.lives<=0;
			if(!gameOver)
				reset();
			else
				repaint();
		}
		//check the enemies against themselves
		for(int i = 0; i<numEnemy; i++)
		{
			for(int j = 0; j<numEnemy; j++)
			{
				if(j != i)
					enemy[i].checkCollision(enemy[j].row, enemy[j].column);
			}
		}
	}

	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		if(!gameOver)
		{
			boolean jump = false;
			if(key == 69 || key == 68 || key == 67)
				p1.move(Player.EAST);
			if(key == 81 || key == 87 || key == 69)
				p1.move(Player.NORTH);
			if(key == 65 || key == 81 || key == 90)
				p1.move(Player.WEST);
			if(key == 90 || key == 88 || key == 67)
				p1.move(Player.SOUTH);
			if(key == 32)
			{
				jump = true;
				if(lp.safeJumps > 0)
				{
					lp.safeJumps--;
					do
					{
						p1.move(Player.RANDOM_SPOT);
						for(int i = 0; i<numEnemy; i++)
							p1.checkCollision(enemy[i].row, enemy[i].column);
					}while(!p1.alive);
					p1.alive=true;
					lp.updateLabels();
				}
				else
					p1.move(Player.RANDOM_SPOT);

			}

			if(!jump)
				for(int i = 0; i<numEnemy; i++)
					enemy[i].move(p1.column, p1.row);

			repaint();
		}

	}

	public void checkWin()
	{
		boolean win = true;

		for(int i = 0; i<numEnemy; i++)
			if(enemy[i].alive)
				win = false;
		if(win)
		{
			lp.level++;
			lp.updateLabels();
			numEnemy+=2;
			reset();
		}
	}

	public void keyReleased(KeyEvent e)
	{
		if(!gameOver)
		{
			checkCol();
			checkWin();
		}

	}
	public void keyTyped(KeyEvent e){}

	//Return a description of the minigame
    public static String getDescription()
    {
    	return "Escape is a fast paced, non-stop thriller where you (red circle) must evade the dumb, but dangerous, blue squares. Force the evil blue squares to collide with each other until all blue squares have been destroyed. Jump to get out of a jam but be careful, you only have so many \"safe\" jumps, beyond that you may jump into an enemy. Good luck brave red circle.";
    }

    //Return the name of the minigame
    public static String getGameName()
    {
    	return "Escape!";
    }

    //Return the author of the minigame
    public static String getAuthor()
    {
    	return "Mr. Babb";
    }
    
    public boolean getVisible()
    {
    	return jf.isVisible();
    }
}