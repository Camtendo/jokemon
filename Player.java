/******************************************************
 *Programmer:	Derek Babb
 *File:			Player.java
 *
 *Purpose:		This is the player class for the game Escape!
 *				The player can move around the board moving one
 *				cell at a time using the keyboard.
 *****************************************************/

import java.awt.*;
import javax.swing.*;

public class Player
{
	int row, column; 	//row and column number for player
	int width, height; 	//width and height of each spot
	final int cells;			//number of cells in each row/column
	boolean alive;

	final static int NORTH = 1;
	final static int EAST = 2;
	final static int SOUTH = 3;
	final static int WEST = 4;
	final static int RANDOM_SPOT = 5;

	public Player(int c)
	{
		alive = true;
		cells = c;
		row = (int)(Math.random()*cells);
		column =(int)(Math.random()*cells);
	}

	public Player(int c, int w, int h)
	{
		setSize(w, h);
		alive = true;
		cells = c;
		row = (int)(Math.random()*cells);
		column =(int)(Math.random()*cells);
	}

	public void setSize(int w, int h)
	{
		width = w;
		height = h;
	}

	public boolean checkCollision(int r, int c)
	{
		if (row == r && column == c)
		{
			alive = false;
			return true;
		}
		return false;
	}


	public void move(int direction)
	{
		if(direction == NORTH)
		{
			if(row != 0)
				row--;
		}
		else if(direction == SOUTH)
		{
			if(row != cells-1)
				row++;
		}
		else if(direction == WEST)
		{
			if(column != 0)
				column--;
		}
		else if(direction == EAST)
		{
			if(column != cells-1)
				column++;
		}
		else if(direction == RANDOM_SPOT)
		{
			//check to see if they have safe jumps left.
			row = (int)(Math.random()*cells);
			column =(int)(Math.random()*cells);
		}
	}

	public void draw(Graphics g)
	{
		g.setColor(Color.RED);
		int x = width * column + width/10;
		int y = height * row +height/10 ;
		g.fillOval(x, y, width*8/10, height*8/10 );
	}
}