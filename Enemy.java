/******************************************************
 *Programmer:	Derek Babb
 *File:			Enemy.java
 *
 *Purpose:		This is the player class for the game Escape!
 *				The player can move around the board moving one
 *				cell at a time using the keyboard.
 *****************************************************/

import java.awt.*;
import javax.swing.*;

public class Enemy extends Player
{

	public Enemy(int c)
	{
		super(c);
	}

	public Enemy(int c, int w, int h)
	{
		super(c, w, h);
	}

	public void setAlive(boolean a)
	{
		alive = a;
	}

	public void move(int pX, int pY)
	{
		if(alive)
		{
			if(pY < row)
				super.move(NORTH);
			else if(pY > row)
				super.move(SOUTH);
			if(pX < column)
				super.move(WEST);
			else if(pX > column)
				super.move(EAST);
		}
	}

	public void draw(Graphics g)
	{
		int x = width * column + width/10;
		int y = height * row +height/10 ;

		if(alive)
		{
			g.setColor(Color.BLUE);
			g.fillRect(x, y, width*17/20, height*17/20 );
		}
		else
		{
			x = x-width/10 - width/6;
			y = y - width/10 - width/6;
			int w = width/5;
			int h = height/5;
			int[] xp = {x+3*w, x+4*w, x+6*w, x+4*w, x+6*w, x+4*w, x+3*w, x+2*w, x, x+2*w, x, x+2*w};
			int[] yp = {y, y+2*h, y+2*h, y+3*h, y+4*h, y+4*h, y+6*h, y+4*h, y+4*h, y+3*h, y+2*h, y+2*h};
			g.setColor(Color.ORANGE);
			g.fillPolygon(xp, yp, 12);
			//g.fillRect(x, y, width*8/10, height*8/10 );
		}

	}
}