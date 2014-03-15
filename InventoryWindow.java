import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

public class InventoryWindow extends JFrame implements ActionListener
{
	//Variables
	private Container cp;


	public boolean visible;
	public State state;
	private int page=1;
	private boolean canSelect;
	public boolean itemUsed = false;
	private boolean infoB = false;
	private JButton close;
	private JButton information;
	private JButton[] pages = new JButton[6];
	private JButton[] pockets = new JButton[6];
	private JButton[] display = new JButton[10];
	private int[] displayNum = new int[10];

	public enum State
	{
		BATTLE,OVERWORLD;
	}

	//Constructor
	public InventoryWindow()
	{
		page = 1;
		this.setTitle("Inventory");
    	this.setSize(300,500);
   		this.setResizable(false);
   		this.setLayout(null);
 		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
   		this.setVisible(false);
   		visible = false;
   		this.setLocationRelativeTo(null);
   		cp = this.getContentPane();
   		int x = 16;
   		int y = this.getHeight()-90;
   		for (int i = 0; i < pages.length; i++)
   		{
   			pages[i] = new JButton("Page " + (i+1));
   			pages[i].setBounds(x,y,80,20);
   			pages[i].addActionListener(this);
   			cp.add(pages[i]);
   			x+=90;
   			if (x > this.getWidth()-20)
   			{
   				x=16;
   				y+=30;
   			}
   		}
   		x = 16;
   		y = 35;
   		for (int i = 0; i<pockets.length; i++)
   		{
   			String messager = "";
   			switch(i)
   			{
   				case 0:
   					messager = "Items";
   					break;
   				case 1:
   					messager = "Medicine";
   					break;
   				case 2:
   					messager = "Pokeballs";
   					break;
   				case 3:
   					messager = "TMs/HMs";
   					break;
   				case 4:
   					messager = "Battle Items";
   					break;
   				case 5:
   					messager = "Key Items";
   					break;
   			}
   			pockets[i] = new JButton(messager);
   			pockets[i].setBounds(x,y,120,20);
   			pockets[i].addActionListener(this);
   			cp.add(pockets[i]);
   			x+=140;
   			if (x>this.getWidth()-120)
   			{
   				x=16;
   				y+=30;
   			}
   		}
   		close = new JButton("Close Window");
   		close.setBounds(16,10,260,20);
   		close.addActionListener(this);
   		cp.add(close);
   		for (int i = 0; i < display.length; i++)
   		{
   			display[i] = new JButton();
   			display[i].setHorizontalAlignment(JLabel.LEFT);
   			display[i].addActionListener(this);
   		}
   		information = new JButton("Information");
   		information.setBounds(16,this.getHeight()-120,260,20);
   		information.addActionListener(this);
   		cp.add(information);
   		for (int ii = 0; ii<display.length; ii++)
		{
			cp.add(display[ii]);
		}
	}

	//Action Performed
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == information)
		{
			infoB = !(infoB);
			if (infoB)
				information.setText("Name");
			else
				information.setText("Information");
		}
		if (e.getSource() == pockets[0])
		{
			page = 1;
			Inventory.changePocket(Item.Pocket.ITEM);
		}
		if (e.getSource() == pockets[1])
		{
			page = 1;
			Inventory.changePocket(Item.Pocket.MEDICINE);
		}
		if (e.getSource() == pockets[2])
		{
			page = 1;
			Inventory.changePocket(Item.Pocket.POKEBALL);
		}
		if (e.getSource() == pockets[3])
		{
			page = 1;
			Inventory.changePocket(Item.Pocket.TMHM);
		}
		if (e.getSource() == pockets[4])
		{
			page = 1;
			Inventory.changePocket(Item.Pocket.BATTLE);
		}
		if (e.getSource() == pockets[5])
		{
			page = 1;
			Inventory.changePocket(Item.Pocket.KEY);
		}
		if (e.getSource() == close)
			closeInventory();
		if (e.getSource() == pages[0])
			page = 1;
		if (e.getSource() == pages[1])
			page = 2;
		if (e.getSource() == pages[2])
			page = 3;
		if (e.getSource() == pages[3])
			page = 4;
		if (e.getSource() == pages[4])
			page = 5;
		if (e.getSource() == pages[5])
			page = 6;
		for (int i = 0; i<display.length; i++)
		{
			if (e.getSource() == display[i])
			{
				if(Inventory.currentPocket==Item.Pocket.POKEBALL)
				{
					Inventory.pokemonWasSelected(Battle.b1.userSelected,Inventory.currentDisplay.get(displayNum[i]));
				}
				else if(Inventory.currentPocket==Item.Pocket.KEY)
				{
					Battle.user=JokemonDriver.partyPokemon;
						
					Inventory.pokemonWasSelected(1,Inventory.currentDisplay.get(displayNum[i]));
				}
				else if (Inventory.currentDisplay.get(displayNum[i]).effect1 == Item.Effect.BATTLE_BOOST && state == State.BATTLE)
				{
					//isSelected = true;
	    			//closeWindow();
	    			Inventory.pokemonWasSelected(Battle.b1.userSelected,Inventory.currentDisplay.get(displayNum[i]));
				}
				else
				{
					if(Inventory.currentDisplay.get(displayNum[i]).effect1==Item.Effect.REPEL)
						Inventory.pokemonWasSelected(1,Inventory.currentDisplay.get(displayNum[i]));
					else
					Inventory.selectWindow.openWindow(Battle.user,"Use " + Inventory.currentDisplay.get(displayNum[i]).type.toString().replace('_',' ') + " on who?",
					"Applying Item",Inventory.currentDisplay.get(displayNum[i]));
				}


			}
		}
		updateSelection();
	}

	public void openInventory(State newState)
	{
		if (Inventory.currentPocket == null)
		Inventory.changePocket(Item.Pocket.ITEM);

		Inventory.changePocket(Inventory.currentPocket);
		itemUsed = false;
		visible = true;
		state = newState;
		this.setVisible(true);

		updateSelection();
	}
	public void closeInventory()
	{
		visible = false;
		this.setVisible(false);
		Battle.forceToFront();
	}
	public static void main(String[] args)
	{
		ItemTest i = new ItemTest();
		InventoryWindow iw1 = new InventoryWindow();
		iw1.openInventory(State.BATTLE);
		while (iw1.visible)
		{
			try
			{
				Thread.sleep(15);
			}
			catch(Exception e)
			{

			}
		}
		System.exit(-1);

	}
	public void updateSelection()
	{
		Inventory.changePocket(Inventory.currentPocket);

		int num = (page-1)*10;

		int y = 125;
		for (int i = 0; i < display.length; i++)
		{
			displayNum[i] = num+i;
			if (!infoB)
			{
				if (Inventory.currentDisplay.size() != 0 && num+i  < Inventory.currentDisplay.size())
				{
					display[i].setText("" + (num+i+1) + ": " + Inventory.currentDisplay.get(num+i).getName());
					display[i].setEnabled(true);
				}
				else
				{
					display[i].setText("" + (num+i+1) + ": " + "EMPTY");
					display[i].setEnabled(false);
				}
			}
			else
			{
				if (Inventory.currentDisplay.size() != 0 && num+i  < Inventory.currentDisplay.size())
				{
					display[i].setText("" + (num+i+1) + ": " + Inventory.currentDisplay.get(num+i).message);
					display[i].setEnabled(true);
				}

				else
				{
					display[i].setText("" + (num+i+1) + ": " + "EMPTY");
					display[i].setEnabled(false);
				}

			}
			display[i].setBounds(15,y,260,20);

			if (display[i].isEnabled() && state == State.BATTLE)
			{
				Item item = Inventory.currentDisplay.get(num+i);
				if (item.pocket == Item.Pocket.TMHM || item.pocket == Item.Pocket.ITEM || item.pocket == Item.Pocket.KEY || item.effect1 == Item.Effect.PERMA_STAT_BOOST)
				display[i].setEnabled(false);
			}
			else if (display[i].isEnabled() && state == State.OVERWORLD)
			{
				Item item = Inventory.currentDisplay.get(num+i);
				if (item.pocket == Item.Pocket.BATTLE || item.pocket == Item.Pocket.POKEBALL)
				display[i].setEnabled(false);
			}

			y+=25;

		}

		if (Inventory.currentDisplay.size() < 51)
			pages[5].setEnabled(false);
		else
			pages[5].setEnabled(true);

		if (Inventory.currentDisplay.size() < 41)
			pages[4].setEnabled(false);
		else
			pages[4].setEnabled(true);

		if (Inventory.currentDisplay.size() < 31)
			pages[3].setEnabled(false);
		else
			pages[3].setEnabled(true);

		if (Inventory.currentDisplay.size() < 21)
			pages[2].setEnabled(false);
		else
			pages[2].setEnabled(true);

		if (Inventory.currentDisplay.size() < 11)
			pages[1].setEnabled(false);
		else
			pages[1].setEnabled(true);


	}
}