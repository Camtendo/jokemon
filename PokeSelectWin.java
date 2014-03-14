/**
 * @(#)PokeSelectWin.java
 *
 *
 * @author Justinian
 * @version 1.00 2011/2/16
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

public class PokeSelectWin extends JFrame implements ActionListener
{
	public boolean visible;
	private Container cp;
	private int pokeIndex = 0;
	private JButton[] poke = new JButton[6];
	private JLabel[] pokeInfo = new JLabel[6];
	private JButton[] moves = new JButton[4];
	private JLabel[] moveInfo = new JLabel[4];
	private JButton close;
	private JLabel messageDisplay;
	public boolean isSelected;
	private State state;
	public int moveNo = 0;
	Item item;

	public enum State
	{
		POKEMON,MOVE,LEVEL_MOVE;
	}
    public PokeSelectWin()
    {
    	this.setTitle("????");
    	this.setSize(300,400);
   		this.setResizable(false);
   		this.setLayout(null);
 		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
   		this.setVisible(false);
   		visible = false;
   		this.setLocationRelativeTo(null);
   		cp = this.getContentPane();


   		close = new JButton();
   		close.setText("Close Window");
   		close.addActionListener(this);
   		cp.add(close);
   		for (int i = 0; i<poke.length; i++)
   		{
			pokeInfo[i] = new JLabel();
   			poke[i] = new JButton();
   			poke[i].addActionListener(this);

   		}
   		for (int i = 0; i<moves.length; i++)
   		{
   			moveInfo[i] = new JLabel();
   			moves[i] = new JButton();
   			moves[i].addActionListener(this);
   		}


   		messageDisplay = new JLabel("");


    }
    public void setUpGUI()
    {
    	messageDisplay.setBounds(16,10,260,20);
   		messageDisplay.setHorizontalAlignment(JLabel.CENTER);
   		cp.add(messageDisplay);
    }
    public void openWindow(Pokemon[] selectable, String message, String boxName, Item iteme)
    {
    	cp.removeAll();
    	setUpGUI();
    	item = iteme;
    	this.setSize(300,250);
   		close.setBounds(16,this.getHeight()-60,260,20);
    	isSelected = false;
    	state = State.POKEMON;
    	this.setVisible(true);
    	visible = true;
    	this.setTitle(boxName);
    	messageDisplay.setText(message);
    	cp.add(messageDisplay);
    	int y = 35;
    	for (int i = 0; i<poke.length; i++)
    	{
    		if (i >= selectable.length)
    		{
    			poke[i].setText("Party Slot: " + (i+1) +  " EMPTY");
    			poke[i].setEnabled(false);
    		}
    		else if (selectable[i] == null)
    		{
    			poke[i].setText("Party Slot: " + (i+1) +  " EMPTY");
    			poke[i].setEnabled(false);
    		}
    		else
    		{
    			poke[i].setText("Party Slot: " + (i+1) +  " " + selectable[i].nickname);
    			poke[i].setEnabled(true);
    		}
    		
    		if (selectable[i] != null && (item.type == Item.Type.TM || item.type == Item.Type.HM))
    		{
    			if (Mechanics.hasMove(selectable[i],item.moveLearned))
    				poke[i].setEnabled(false);
    		}
    		poke[i].setBounds(15,y,260,20);
    		poke[i].setHorizontalAlignment(JLabel.LEFT);
    		if (poke[i].isEnabled())
    		{
    			y+=25;
    			this.setSize(300,this.getHeight()+25);
    			close.setBounds(16,this.getHeight()-60,260,20);
    			pokeInfo[i].setText("HP: " + selectable[i].health + "/" + selectable[i].healthMax + "       Status: " + selectable[i].status.toString());
    			pokeInfo[i].setBounds(15,y,260,20);
    			pokeInfo[i].setHorizontalAlignment(JLabel.LEFT);
    			cp.add(pokeInfo[i]);
    		}
    		cp.add(poke[i]);
    		y+=25;
    	}
    	cp.add(close);
    }
    public void openWindow(Pokemon pokemon, String message, String boxName, Item iteme, int pokeIndex)
    {
    	cp.removeAll();
    	setUpGUI();
    	item = iteme;
    	this.pokeIndex = pokeIndex;
    	this.setSize(300,200);
   		close.setBounds(16,this.getHeight()-60,260,20);
    	isSelected = false;
    	state = State.MOVE;
    	this.setVisible(true);
    	visible = true;

    	this.setTitle(boxName);
    	messageDisplay.setText(message);
    	cp.add(messageDisplay);
    	int y = 35;
    	for (int i = 0; i<4; i++)
    	{
    			moves[i].setText("Slot: " + (i+1) + " " + pokemon.move[i].toString().replace('_',' '));
    			moves[i].setEnabled(true);
    			moves[i].setBounds(15,y,260,20);
    			moves[i].setHorizontalAlignment(JLabel.LEFT);
    			if (pokemon.move[i] == Pokemon.Move.NONE&&item.type!=Item.Type.TM&&item.type!=Item.Type.HM)
    			{
    				moves[i].setEnabled(false);
    			}
    			cp.add(moves[i]);
    			if (iteme != null && moves[i].isEnabled())
    			{
    				this.setSize(300,this.getHeight()+25);
    				close.setBounds(16,this.getHeight()-60,260,20);
    				y+=25;
    				moveInfo[i].setText("PP: " + pokemon.TRUE_PP[i] + "/" + pokemon.TRUE_PPMAX[i]);
	    			moveInfo[i].setBounds(15,y,260,20);
	    			moveInfo[i].setHorizontalAlignment(JLabel.LEFT);
	    			cp.add(moveInfo[i]);
    			}
    		y+=25;
    	}
    	cp.add(close);
    }
    public void openWindow(Pokemon pokemon, String message, String boxName, Item iteme)
    {
    	cp.removeAll();
    	setUpGUI();
    	item = iteme;
    	this.pokeIndex = pokeIndex;
    	this.setSize(300,200);
   		close.setBounds(16,this.getHeight()-60,260,20);
    	isSelected = false;
    	state = State.LEVEL_MOVE;
    	this.setVisible(true);
    	visible = true;

    	this.setTitle(boxName);
    	messageDisplay.setText(message);
    	cp.add(messageDisplay);
    	int y = 35;
    	for (int i = 0; i<4; i++)
    	{
    			moves[i].setText("Slot: " + (i+1) + " " + pokemon.move[i].toString().replace('_',' '));
    			moves[i].setEnabled(true);
    			moves[i].setBounds(15,y,260,20);
    			moves[i].setHorizontalAlignment(JLabel.LEFT);
    			if (pokemon.move[i] == Pokemon.Move.NONE)
    			{
    				moves[i].setEnabled(false);
    			}
    			cp.add(moves[i]);
    			if (iteme != null && moves[i].isEnabled())
    			{
    				this.setSize(300,this.getHeight()+25);
    				close.setBounds(16,this.getHeight()-60,260,20);
    				y+=25;
    				moveInfo[i].setText("PP: " + pokemon.TRUE_PP[i] + "/" + pokemon.TRUE_PPMAX[i]);
	    			moveInfo[i].setBounds(15,y,260,20);
	    			moveInfo[i].setHorizontalAlignment(JLabel.LEFT);
	    			cp.add(moveInfo[i]);
    			}
    		y+=25;
    	}
    	cp.add(close);
    }
    public void closeWindow()
    {
    	this.setVisible(false);
    	visible = false;
    	Battle.forceToFront();
    }
    public void actionPerformed(ActionEvent e)
    {
    	if (e.getSource() == close)
    	{
    		closeWindow();
    		System.out.println(state.toString());
    	}
    	for (int i = 0; i<poke.length; i++)
    	{
    		if (e.getSource() == poke[i])
    		{
    			if (item.effect1 == Item.Effect.PP_HEAL && state != State.MOVE)
    			{
    				closeWindow();
    				openWindow(Battle.user[i],"Heal PP for...",item.getName(),item,i);
    			}

    			else if ((item.effect1 == Item.Effect.TM || item.effect1 == Item.Effect.HM) && state != State.MOVE)
    			{
    				openWindow(Battle.user[i],"Replace...",item.getName(),item,i);
    			}

    			else
    			{
	    			isSelected = true;
	    			closeWindow();
	    			Inventory.pokemonWasSelected(i,item);
    			}

    		}

    	}
    	for (int i=0; i<4; i++)
    	{
    		if (e.getSource() == moves[i] && item != null)
    		{
    			if (item.effect1 == Item.Effect.PP_HEAL && state == State.MOVE)
    			{
    				Inventory.moveNo = i;
    				isSelected = true;
	    			closeWindow();
	    			Inventory.pokemonWasSelected(pokeIndex,item);
    			}
    			else if ((item.effect1 == Item.Effect.TM || item.effect1 == Item.Effect.HM) && state == State.MOVE)
    			{
    				Inventory.moveNo = i;
    				isSelected = true;
	    			closeWindow();
	    			Inventory.pokemonWasSelected(pokeIndex,item);
    			}
    		}
    		else if (e.getSource() ==  moves[i] && item == null && state == State.LEVEL_MOVE)
    		{
    			moveNo = i;
    			isSelected = true;
				closeWindow();
    		}
    	}
    }


}