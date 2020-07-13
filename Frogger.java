import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.applet.*;
import java.net.URL;

public class Frogger extends JPanel implements KeyListener
{
	final Image[] run=new Image[9];
	JLabel label;
	private static Container cp;
	JLabel ppic=new JLabel();
	int x=500, y=632;
	int frogx=1;
	int frogy=1;
	int car1x=1;
	int car2x=1;
	int car3x=1;
	int car4x=1;
	int count=0;
	int i;
	final int end = 1000;
	int car1dx = 7;
	int car2dx = 4;
	int car3dx = 6;
	int car4dx = 5;
	int bulb=0;
	int ivy=0;
	int vena=0;
	int picnum=5;
	boolean space=false;
	boolean moving=false, swing=false,up=false,down=false;
	final int LEFT = 1, RIGHT = 2, UP = 3, DOWN = 4, STOP = 5;
	int direction = STOP;

		public static void main(String[] args)
	{
		Frogger ff = new Frogger();
		ff.setupGUI();
		ff.run();
	}
	
	public Frogger()
	{
		setupGUI();
		run();
	}
	public void setupGUI()
	{

		if(count > 2 && count <=4)
			frogx=6;
		if(count>4)
			frogx=7;
			//   //
		run[1]=new ImageIcon("NERDY_BIKER.PNG").getImage();
		run[2]=new ImageIcon("NERDY_BIKER.PNG").getImage();
		run[3]=new ImageIcon("NERDY_BIKER.PNG").getImage();
		run[4]=new ImageIcon("NERDY_BIKER.PNG").getImage();
		run[5]=new ImageIcon("bulbasaur.PNG").getImage();
		run[6]=new ImageIcon("ivysaur.PNG").getImage();
		run[7]=new ImageIcon("venusaur.PNG").getImage();
		JFrame jf = new JFrame("Don't Die");
		Container cp = jf.getContentPane();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		jf.setSize(1000, 710);
		jf.setLocationRelativeTo(null);
		jf.setLayout(new GridLayout(1,1));
		cp.add(this);
		jf.setVisible(true);
		jf.addKeyListener(this);
	}
		public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(count<7 && count>0)
		{

			g.setColor(Color.YELLOW);
			g.fillRect(1,200,200,20);
			g.fillRect(250,200,200,20);
			g.fillRect(500,200,200,20);
			g.fillRect(750,200,200,20);
			g.fillRect(1,450,200,20);
			g.fillRect(250,450,200,20);
			g.fillRect(500,450,200,20);
			g.fillRect(750,450,200,20);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Serif", Font.BOLD, 70));
			g.drawString("Level  "+ count, 450,350);
			g.drawImage(run[picnum],x,y,this);
			g.drawImage(run[1],car1x,100,this);
			g.drawImage(run[4],car4x,255,this);
			g.drawImage(run[3],car3x,355,this);
			g.drawImage(run[2],car2x,500,this);
		}
		if(count>6)
		{
			g.setColor(Color.BLACK);
			g.fillRect(0,0,1000,800);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Serif", Font.BOLD, 100));
			g.drawString("WINNER!",300,350);
			g.setFont(new Font("Serif", Font.BOLD, 40));
			g.drawString("Press any Spacebar to Continue",300,450);
			if(space==true)
			{
				count=0;
				space=false;
				car1dx = 7;
				car2dx = 4;
				car3dx = 6;
				car4dx = 5;
				x=500;
				y=632;


			}
		}
		if(count==0)
		{
			g.setColor(Color.BLACK);
			g.fillRect(0,0,1000,800);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Serif", Font.BOLD, 40));
			g.drawImage(run[7],225,415,this);
			g.drawImage(run[7],725,415,this);
			g.drawString("Press Spacebar to Start",300,450);
			if(space==true)
			{
				count=1;
				space=false;
			}


		}

	}
	public void move(int dir)
	{
		if(dir == LEFT)
			x -=5 + count;
		if(dir == RIGHT)
			x +=5 + count;
		if(dir == UP)
			y-=5 + count;
		if(dir == DOWN)
			y+=5 + count;
		repaint();
	}
	public void keyReleased(KeyEvent e)
		{
			direction = STOP;
		}
	public void keyPressed(KeyEvent e)
		{

			if(count>=3 && count<5)
				picnum=6;
			if(count>4)
				picnum=7;
			if(count<3)
				picnum=5;
		int key = e.getKeyCode();
		if(key == 37)
			direction = LEFT;
		else if(key == 38)
			direction = UP;
		else if(key == 39)
			direction = RIGHT;
		else if (key == 40)
			direction = DOWN;
		else if(key==32)
			space=true;
		}
	public void keyTyped(KeyEvent e){}
	public void run()
	{
		while(true)
		{
			move(direction);
			move();
			repaint();
			try{Thread.sleep(10);}catch(Exception ignored){}
		}
	}
	public void move()
	{
		car1x+=car1dx;
		car2x+=car2dx;
		car3x+=car3dx;
		car4x+=car4dx;
		if(car1x>end)
			car1x=-177;
		if(car2x>end)
			car2x=-177;
		if(car3x>end)
			car3x=-177;
		if(car4x>end)
			car4x=-177;
		if(y<0)
		{
			y=632;
			count++;
			if(count>=3 && count<4)
				frogx++;
			if(count>=5)
				frogx++;
			car1dx += count;
			car2dx += count;
			car3dx += count;
			car4dx += count;
			try{Thread.sleep(1000);}catch(Exception ignored){}
		}
		if(x<-81)
			x=940;
		if(x>1000)
			x=-80;
		if(y>632)
			y=632;
		if(x+47>=car1x && x<=car1x+58 && y+48>=100 && y<=148)
		{
			try{Thread.sleep(900);}catch(Exception ignored){}
			count=1;
			car1dx = 7;
			car2dx = 4;
			car3dx = 6;
			car4dx = 5;
			x=500;
			y=632;
		}
		if(x+47>=car2x && x<=car2x+58 && y+48>=500 && y<=548)
		{
			try{Thread.sleep(900);}catch(Exception ignored){}
			count=1;
			car1dx = 7;
			car2dx = 4;
			car3dx = 6;
			car4dx = 5;
			x=500;
			y=632;
		}
		if(x+47>=car3x && x<=car3x+58 && y+48>=355 && y<=403)
		{
			try{Thread.sleep(900);}catch(Exception ignored){}
			count=1;
			car1dx = 7;
			car2dx = 4;
			car3dx = 6;
			car4dx = 5;
			x=500;
			y=632;
		}
		if(x+47>=car4x && x<=car4x+58 && y+48>=255 && y<=303)
		{
			try{Thread.sleep(900);}catch(Exception ignored){}
			count=1;
			car1dx = 7;
			car2dx = 4;
			car3dx = 6;
			car4dx = 5;
			x=500;
			y=632;
		}
	}
}










