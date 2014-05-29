//Jokemon Mart Class
//Made by Justinian

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;

public class Mart extends JFrame implements ActionListener
{
    private JButton buyButton;
	private JButton sellButton;
	private JTextField amountField;
	private JButton information;
	private JButton closeButton;
	private JButton buySellButton;
	private JButton[] display = new JButton[10];
	private JButton[] pockets = new JButton[5];
	private JButton[] pages = new JButton[6];
	private JLabel moneyDisplay;
	private JLabel amountDisplay;
	private JLabel totalAmount;
	public boolean visible = false;
	private boolean infoB = false;
	private int page = 1;

	//Inventory Variables
	public boolean itemSelected = false;
	private long amount;
	private int selectedItemId;
	ArrayList<Item> fullInventory = new ArrayList<Item>(200);
	ArrayList<Item> currentDisplay = new ArrayList<Item>(60);
	Item.Pocket pocket = Item.Pocket.ITEM;
	State state = State.BUY;

    public enum State
	{
		BUY,SELL;
	}

	//Sets up the mart and opens it
	public void setUpMart(JokemonDriver.Area area, int storeNum)
	{
        String martName = "" + area + " Mart";
		if(area==JokemonDriver.Area.MegaMart)
			martName ="Villa Del Joe Mart";
		martName = martName.replace("_", " ");

		switch(area)
		{
			case Stringville:
				fullInventory.add(new Item(Item.Type.ANTIDOTE,1));
				fullInventory.add(new Item(Item.Type.AWAKENING,1));
				fullInventory.add(new Item(Item.Type.PARALYZE_HEAL,1));
				fullInventory.add(new Item(Item.Type.POTION,1));
				fullInventory.add(new Item(Item.Type.POKE_BALL,1));
				fullInventory.add(new Item(Item.Type.TM,1,3));
				fullInventory.add(new Item(Item.Type.TM,1,4));
				fullInventory.add(new Item(Item.Type.TM,1,5));
				fullInventory.add(new Item(Item.Type.TM,1,7));
				fullInventory.add(new Item(Item.Type.TM,1,8));
				break;
			case Args_Harbor:
				fullInventory.add(new Item(Item.Type.POKE_BALL,1));
				fullInventory.add(new Item(Item.Type.POTION,1));
				fullInventory.add(new Item(Item.Type.ANTIDOTE,1));
				fullInventory.add(new Item(Item.Type.BURN_HEAL,1));
				fullInventory.add(new Item(Item.Type.AWAKENING,1));
				fullInventory.add(new Item(Item.Type.PARALYZE_HEAL,1));
				fullInventory.add(new Item(Item.Type.TM,1,9));
				fullInventory.add(new Item(Item.Type.TM,1,10));
				fullInventory.add(new Item(Item.Type.TM,1,11));
				fullInventory.add(new Item(Item.Type.TM,1,12));
				fullInventory.add(new Item(Item.Type.TM,1,14));
				break;
			case Villa_Del_Joe:
			case MegaMart:
				fullInventory.add(new Item(Item.Type.POKE_BALL,1));
				fullInventory.add(new Item(Item.Type.SUPER_POTION,1));
				fullInventory.add(new Item(Item.Type.ICE_HEAL,1));
				fullInventory.add(new Item(Item.Type.AWAKENING,1));
				fullInventory.add(new Item(Item.Type.PARALYZE_HEAL,1));
				fullInventory.add(new Item(Item.Type.REPEL,1));
				fullInventory.add(new Item(Item.Type.CALCIUM,1));
				fullInventory.add(new Item(Item.Type.CARBOS,1));
				fullInventory.add(new Item(Item.Type.FIRE_STONE,1));
				fullInventory.add(new Item(Item.Type.FRESH_WATER,1));
				fullInventory.add(new Item(Item.Type.GUARD_SPECIAL,1));
				fullInventory.add(new Item(Item.Type.HP_UP,1));
				fullInventory.add(new Item(Item.Type.IRON,1));
				fullInventory.add(new Item(Item.Type.LEAF_STONE,1));
				fullInventory.add(new Item(Item.Type.LEMONADE,1));
				fullInventory.add(new Item(Item.Type.PROTEIN,1));
				fullInventory.add(new Item(Item.Type.SODA_POP,1));
				fullInventory.add(new Item(Item.Type.THUNDER_STONE,1));
				fullInventory.add(new Item(Item.Type.WATER_STONE,1));
				fullInventory.add(new Item(Item.Type.X_ACCURACY,1));
				fullInventory.add(new Item(Item.Type.X_ATTACK,1));
				fullInventory.add(new Item(Item.Type.X_DEFEND,1));
				fullInventory.add(new Item(Item.Type.X_SPECIAL,1));
				fullInventory.add(new Item(Item.Type.X_SPEED,1));
				fullInventory.add(new Item(Item.Type.TM,1,16));
				fullInventory.add(new Item(Item.Type.TM,1,18));
				fullInventory.add(new Item(Item.Type.TM,1,19));
				fullInventory.add(new Item(Item.Type.TM,1,20));
				fullInventory.add(new Item(Item.Type.TM,1,21));
				fullInventory.add(new Item(Item.Type.OMEGA_BALL,1));
				fullInventory.add(new Item(Item.Type.SUPER_ROD,1));
				break;
			case Mount_Java:
				fullInventory.add(new Item(Item.Type.GREAT_BALL,1));
				fullInventory.add(new Item(Item.Type.SUPER_POTION,1));
				fullInventory.add(new Item(Item.Type.REVIVE,1));
				//fullInventory.add(new Item(Item.Type.SUPER_REPEL,1));
				fullInventory.add(new Item(Item.Type.ANTIDOTE,1));
				fullInventory.add(new Item(Item.Type.BURN_HEAL,1));
				fullInventory.add(new Item(Item.Type.ICE_HEAL,1));
				fullInventory.add(new Item(Item.Type.PARALYZE_HEAL,1));
				fullInventory.add(new Item(Item.Type.TM,1,23));
				fullInventory.add(new Item(Item.Type.TM,1,25));
				fullInventory.add(new Item(Item.Type.TM,1,26));
				fullInventory.add(new Item(Item.Type.TM,1,27));
				fullInventory.add(new Item(Item.Type.TM,1,28));
				break;
			case Streamreader_Hotel:
				fullInventory.add(new Item(Item.Type.GREAT_BALL,1));
				fullInventory.add(new Item(Item.Type.HYPER_POTION,1));
				fullInventory.add(new Item(Item.Type.MAX_REPEL,1));
				fullInventory.add(new Item(Item.Type.REVIVE,1));
				fullInventory.add(new Item(Item.Type.FULL_HEAL,1));
				fullInventory.add(new Item(Item.Type.TM,1,30));
				fullInventory.add(new Item(Item.Type.TM,1,31));
				fullInventory.add(new Item(Item.Type.TM,1,32));
				fullInventory.add(new Item(Item.Type.TM,1,33));
				fullInventory.add(new Item(Item.Type.TM,1,34));
				break;
			case Recursive_Hot_Springs:
				fullInventory.add(new Item(Item.Type.ULTRA_BALL,1));
				fullInventory.add(new Item(Item.Type.GREAT_BALL,1));
				fullInventory.add(new Item(Item.Type.SUPER_POTION,1));
				fullInventory.add(new Item(Item.Type.REVIVE,1));
				fullInventory.add(new Item(Item.Type.FULL_HEAL,1));
				fullInventory.add(new Item(Item.Type.TM,1,35));
				fullInventory.add(new Item(Item.Type.TM,1,36));
				fullInventory.add(new Item(Item.Type.TM,1,37));
				fullInventory.add(new Item(Item.Type.TM,1,38));
				fullInventory.add(new Item(Item.Type.TM,1,39));
				break;
			case Polymorph_Town:
				fullInventory.add(new Item(Item.Type.ULTRA_BALL,1));
				fullInventory.add(new Item(Item.Type.HYPER_POTION,1));
				fullInventory.add(new Item(Item.Type.MAX_REPEL,1));
				fullInventory.add(new Item(Item.Type.REVIVE,1));
				fullInventory.add(new Item(Item.Type.FULL_HEAL,1));
				fullInventory.add(new Item(Item.Type.TM,1,40));
				fullInventory.add(new Item(Item.Type.TM,1,41));
				fullInventory.add(new Item(Item.Type.TM,1,42));
				fullInventory.add(new Item(Item.Type.TM,1,43));
				fullInventory.add(new Item(Item.Type.TM,1,44));
				break;
			case Binary_City:
				fullInventory.add(new Item(Item.Type.ULTRA_BALL,1));
				fullInventory.add(new Item(Item.Type.FULL_RESTORE,1));
				fullInventory.add(new Item(Item.Type.MAX_POTION,1));
				fullInventory.add(new Item(Item.Type.MAX_REPEL,1));
				fullInventory.add(new Item(Item.Type.REVIVE,1));
				fullInventory.add(new Item(Item.Type.FULL_HEAL,1));
				fullInventory.add(new Item(Item.Type.EXPERIENCE_ALL,1));
				fullInventory.add(new Item(Item.Type.TM,1,45));
				fullInventory.add(new Item(Item.Type.TM,1,46));
				fullInventory.add(new Item(Item.Type.TM,1,47));
				fullInventory.add(new Item(Item.Type.TM,1,48));
				fullInventory.add(new Item(Item.Type.TM,1,49));
				fullInventory.add(new Item(Item.Type.TM,1,50));
				break;
			case Elite_4:
			case Peach_City:
			case Cream_City:
				fullInventory.add(new Item(Item.Type.ULTRA_BALL,1));
				fullInventory.add(new Item(Item.Type.FULL_RESTORE,1));
				fullInventory.add(new Item(Item.Type.MAX_POTION,1));
				fullInventory.add(new Item(Item.Type.ULTIMA_REPEL,1));
				fullInventory.add(new Item(Item.Type.MAX_REVIVE,1));
				fullInventory.add(new Item(Item.Type.FULL_HEAL,1));
				break;
			case Null_Zone:
				fullInventory.add(new Item(Item.Type.MOUNTAIN_DEW,1));
				fullInventory.add(new Item(Item.Type.BEER,1));
				fullInventory.add(new Item(Item.Type.ROTTEN_APPLE,1));
				fullInventory.add(new Item(Item.Type.ULTIMA_REPEL,1));
				fullInventory.add(new Item(Item.Type.SILVER_NUGGET,1));
				break;
			default:
				fullInventory.add(new Item(Item.Type.ANTIDOTE,1));
				fullInventory.add(new Item(Item.Type.AWAKENING,1));
				fullInventory.add(new Item(Item.Type.PARALYZE_HEAL,1));
				fullInventory.add(new Item(Item.Type.POTION,1));
				fullInventory.add(new Item(Item.Type.POKE_BALL,1));
				break;
		}

		this.setVisible(true);
		this.setTitle(martName);
		visible = true;
		changeSelection(Item.Pocket.ITEM);
		updateGui();
	}

	//Sets up the window
	private void setUpGui()
	{
		this.setTitle("Mart");
    	this.setSize(300,550);
   		this.setResizable(false);
   		this.setLayout(null);
   		URL iconU=Mart.class.getResource("icon.png");
    	Image icon=Toolkit.getDefaultToolkit().getImage(iconU);
    	this.setIconImage(icon);
 		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
   		this.setVisible(false);
   		this.setLocationRelativeTo(null);

        Container cp = this.getContentPane();

   		int x = 16;
   		int y = 35;
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
   			pockets[i].setVisible(false);
   			cp.add(pockets[i]);
   			x+=140;
   			if (x>this.getWidth()-120)
   			{
   				x=16;
   				y+=30;
   			}
   		}

   		amountDisplay = new JLabel("Amount: ");
   		amountDisplay.setHorizontalAlignment(JLabel.LEFT);
   		amountDisplay.setBounds(36,150,130,20);
   		amountDisplay.setVisible(false);
   		cp.add(amountDisplay);

   		totalAmount = new JLabel("Enter Amount");
   		totalAmount.setHorizontalAlignment(JLabel.CENTER);
   		totalAmount.setBounds(16,180,260,20);
   		totalAmount.setVisible(false);
   		cp.add(totalAmount);

   		amountField = new JTextField();
   		amountField.setHorizontalAlignment(JLabel.CENTER);
   		amountField.setBounds(96,150,180,20);
   		amountField.setVisible(false);
   		amountField.addActionListener(this);
   		cp.add(amountField);

   		buyButton = new JButton("Buy Amount");
   		buyButton.addActionListener(this);
   		buyButton.setBounds(16,210,260,20);
   		buyButton.setVisible(false);
   		buyButton.setEnabled(false);
   		cp.add(buyButton);

   		sellButton = new JButton("Sell Amount");
   		sellButton.addActionListener(this);
   		sellButton.setBounds(16,210,260,20);
   		sellButton.setVisible(false);
   		sellButton.setEnabled(false);
   		cp.add(sellButton);

   		buySellButton = new JButton("Buying");
   		buySellButton.setVisible(false);
   		buySellButton.addActionListener(this);
   		buySellButton.setBounds(16,5,260,20);
   		cp.add(buySellButton);

   		y = 125;
   		for (int i = 0; i < display.length; i++)
   		{
   			display[i] = new JButton();
   			display[i].setHorizontalAlignment(JLabel.LEFT);
   			display[i].addActionListener(this);
   			display[i].setBounds(15,y,260,20);
   			display[i].setVisible(false);
   			y+=25;
   			cp.add(display[i]);
   		}

   		x = 16;
   		y = this.getHeight()-120;
   		for (int i = 0; i < pages.length; i++)
   		{
   			pages[i] = new JButton("Page " + (i+1));
   			pages[i].setBounds(x,y,80,20);
   			pages[i].addActionListener(this);
   			pages[i].setVisible(false);
   			cp.add(pages[i]);
   			x+=90;
   			if (x > this.getWidth()-20)
   			{
   				x=16;
   				y+=30;
   			}
   		}

   		x = 16;
   		y = this.getHeight()-180;
   		moneyDisplay = new JLabel("Money: $" + Inventory.money);
   		moneyDisplay.setVisible(false);
   		moneyDisplay.setHorizontalAlignment(JLabel.CENTER);
   		moneyDisplay.setBounds(x,y,260,20);
   		cp.add(moneyDisplay);

   		information = new JButton("Information");
   		information.setBounds(16,this.getHeight()-150,260,20);
   		information.addActionListener(this);
   		information.setVisible(false);
   		cp.add(information);

   		closeButton = new JButton("Close Window");
   		closeButton.setBounds(16,this.getHeight()-60,260,20);
   		closeButton.addActionListener(this);
   		closeButton.setVisible(false);
   		cp.add(closeButton);

   		visible = false;
	}

	//Updates the Buttons and what is visible and what isn't
	private void updateGui()
	{
		updateDisplay();
		if (!itemSelected)
		{
			for (int i = 0; i<pockets.length; i++)
			{
				pockets[i].setVisible(true);
				pages[i].setVisible(true);
			}
			for (int i = 0; i<display.length; i++)
			{
				display[i].setVisible(true);
			}
			information.setVisible(true);
			closeButton.setVisible(true);
			moneyDisplay.setVisible(true);
			buySellButton.setVisible(true);
			buyButton.setVisible(false);
			amountField.setVisible(false);
			amountDisplay.setVisible(false);
			totalAmount.setVisible(false);
			buyButton.setVisible(false);
			sellButton.setVisible(false);
			moneyDisplay.setText("Money: $" + Inventory.money);
			updatePageButtons();
		}
		else
		{
			if (state == State.BUY)
			{
				buyButton.setVisible(true);
				sellButton.setVisible(false);
			}
			else
			{
				buyButton.setVisible(false);
				sellButton.setVisible(true);
			}
			amountDisplay.setVisible(true);
			totalAmount.setVisible(true);
			buySellButton.setVisible(false);
			for (int i = 1; i<display.length; i++)
			{
				display[i].setVisible(false);
			}
			amountField.setVisible(true);
			for (int i = 0; i<pockets.length; i++)
			{
				pockets[i].setVisible(false);
				pages[i].setVisible(false);
			}
			moneyDisplay.setText("Money: $" + Inventory.money);
		}

	}

	//Closes window
	private void closeWindow()
	{
		this.setVisible(false);
		visible = false;
		page = 1;
		itemSelected = false;
		removeInventory();
		totalAmount.setText("Enter Amount");
		buyButton.setEnabled(false);
		sellButton.setEnabled(false);
		state = State.BUY;
	}

	//Checks to see if you can purchase
	private void updateBuyButton()
	{
		if (Inventory.money >= amount && amount > 0)
		buyButton.setEnabled(true);
		else
		buyButton.setEnabled(false);
	}

	//Checks to see if you can sell
	private void updateSellButton()
	{
		if (totalAmount.getText().equalsIgnoreCase("Error"))
		{
			sellButton.setEnabled(false);
		}
		else
		{
			sellButton.setEnabled(true);
		}
	}

	//Changes what is being displayed such as Items, Medicine, etc
	private void changeSelection(Item.Pocket newPocket)
	{
		if (state == State.BUY)
		{
			pocket = newPocket;
			currentDisplay.clear();

			for (int i = 0; i < fullInventory.size(); i++)
			{
				if (fullInventory.get(i).pocket == newPocket)
				currentDisplay.add(fullInventory.get(i));
			}
		}
		else
		{
			pocket = newPocket;
			currentDisplay.clear();

			for (int i = 0; i < Inventory.fullInventory.size(); i++)
			{
				if (Inventory.fullInventory.get(i).pocket == newPocket)
				{
					currentDisplay.add(Inventory.fullInventory.get(i));
				}
			}
		}

	}

	//Changes what is displayed on the buttons
	private void updateDisplay()
	{
		if (!itemSelected)
		{
			int num = (page-1)*10;
			for (int i = 0; i<display.length; i++)
			{
				if (!infoB)
				{
					if (currentDisplay.size() != 0 && num+i  < currentDisplay.size())
					{
						if (state == State.BUY)
						display[i].setText(currentDisplay.get(num+i).getNameMoney());
						else
						display[i].setText(currentDisplay.get(num+i).getName() + " $" + currentDisplay.get(num+i).storeCost/2);

						display[i].setEnabled(true);
					}
					else
					{
						display[i].setText("EMPTY");
						display[i].setEnabled(false);
					}
				}
				else
				{
					if (currentDisplay.size() != 0 && num+i  < currentDisplay.size())
					{
						if (state == State.BUY)
						display[i].setText(currentDisplay.get(num+i).message + " $" + currentDisplay.get(num+i).storeCost);
						else
						display[i].setText(currentDisplay.get(num+i).message + " x" + currentDisplay.get(num+i).amount + " $" + currentDisplay.get(num+i).storeCost/2);

						display[i].setEnabled(true);
					}

					else
					{
						display[i].setText("EMPTY");
						display[i].setEnabled(false);
					}

				}
			}
		}
		else
		{
			if (!infoB)
			{
				if(state==State.BUY)
				display[0].setText(currentDisplay.get(selectedItemId).getNameMoney());
				else
					display[0].setText(currentDisplay.get(selectedItemId).getNameMoney(1));
				display[0].setEnabled(true);
			}
			else
			{
				if(state==State.BUY)
				display[0].setText(currentDisplay.get(selectedItemId).message + " $" + currentDisplay.get(selectedItemId).storeCost);
				else
					display[0].setText(currentDisplay.get(selectedItemId).message + " $" + currentDisplay.get(selectedItemId).storeCost/2);
				display[0].setEnabled(true);

			}
		}
	}

	//Removes everything and is only called when the window closes
	private void removeInventory()
	{
		fullInventory.clear();
		currentDisplay.clear();
	}

	//Handles all the buttons and crap
	public void actionPerformed(ActionEvent e)
	{
		for (int i = 0; i<pages.length; i++)
		{
			if (e.getSource() == pages[i])
			{
				page=i+1;
			}
		}
		if (e.getSource() == pockets[0])
		{
			page = 1;
			changeSelection(Item.Pocket.ITEM);
		}
		if (e.getSource() == pockets[1])
		{
			page = 1;
			changeSelection(Item.Pocket.MEDICINE);
		}
		if (e.getSource() == pockets[2])
		{
			page = 1;
			changeSelection(Item.Pocket.POKEBALL);
		}
		if (e.getSource() == pockets[3])
		{
			page = 1;
			changeSelection(Item.Pocket.TMHM);
		}
		if (e.getSource() == pockets[4])
		{
			page = 1;
			changeSelection(Item.Pocket.BATTLE);
		}
		if (e.getSource() == closeButton)
		{
			closeWindow();
		}
		if (e.getSource() == information)
		{
			infoB=!infoB;
		}
		if (e.getSource() == buySellButton)
		{
			if (state == State.BUY)
			{
				state = State.SELL;
				buySellButton.setText("Selling");
				changeSelection(Item.Pocket.ITEM);
			}
			else
			{
				state = State.BUY;
				buySellButton.setText("Buying");
				changeSelection(Item.Pocket.ITEM);
			}


		}
		int num = (page-1)*10;
		for (int i = 0; i<display.length; i++)
		{
			if (e.getSource() == display[i] && !itemSelected)
			{
				itemSelected = true;
				selectedItemId = num+i;
			}
			else if (e.getSource() == display[i])
			{
				totalAmount.setText("Enter Amount");
				itemSelected = false;
				buyButton.setEnabled(false);
				sellButton.setEnabled(false);
			}
		}
		if (e.getSource() == amountField && itemSelected)
		{
			try
			{
				amount = Integer.parseInt(amountField.getText());
				amount*=currentDisplay.get(selectedItemId).storeCost;
				if (amount < 0 || (state == State.SELL && amount/currentDisplay.get(selectedItemId).storeCost > currentDisplay.get(selectedItemId).amount) && (state != State.SELL && !currentDisplay.get(selectedItemId).reUsable))
				{
					totalAmount.setText("Error");
				}
				else if (state == State.SELL && Inventory.getItemInfo(currentDisplay.get(selectedItemId)).amount <  amount/currentDisplay.get(selectedItemId).storeCost)
				{
					totalAmount.setText("Error");
				}
				else
				{
					if(state==State.BUY)
					totalAmount.setText("Total of $" + amount);
					else
					totalAmount.setText("Total of $" + amount/2);
				}
				updateBuyButton();
				updateSellButton();
			}
			catch(Exception ee)
			{
				totalAmount.setText("Error");
			}
		}
		if (e.getSource() == buyButton)
		{
			Inventory.money-=amount;

			if(currentDisplay.get(selectedItemId).type!=Item.Type.TM||currentDisplay.get(selectedItemId).type!=Item.Type.HM)
				Inventory.addItem(new Item(currentDisplay.get(selectedItemId).type,amount/currentDisplay.get(selectedItemId).storeCost));
			else
				Inventory.addItem(new Item(currentDisplay.get(selectedItemId).type,amount/currentDisplay.get(selectedItemId).storeCost,currentDisplay.get(selectedItemId).mNo));

			itemSelected = false;
			totalAmount.setText("Enter Amount");
			buyButton.setEnabled(false);
		}
		if (e.getSource() == sellButton)
		{
			updateSellButton();

			if (!sellButton.isEnabled())
			return;
			Inventory.money+=amount/2;
			amount = amount/currentDisplay.get(selectedItemId).storeCost;
			while (amount > 0)
			{
				Inventory.decrementItem(currentDisplay.get(selectedItemId),true);
				amount--;
			}
			totalAmount.setText("Enter Amount");
			sellButton.setEnabled(false);
			itemSelected = false;
			changeSelection(pocket);
		}

		updateGui();
	}

	//Constructor that just calls setupGui
	public Mart()
	{
		setUpGui();
	}

	private void updatePageButtons()
	{
		if (currentDisplay.size() < 51)
			pages[5].setEnabled(false);
		else
			pages[5].setEnabled(true);

		if (currentDisplay.size() < 41)
			pages[4].setEnabled(false);
		else
			pages[4].setEnabled(true);

		if (currentDisplay.size() < 31)
			pages[3].setEnabled(false);
		else
			pages[3].setEnabled(true);

		if (currentDisplay.size() < 21)
			pages[2].setEnabled(false);
		else
			pages[2].setEnabled(true);

		if (currentDisplay.size() < 11)
			pages[1].setEnabled(false);
		else
			pages[1].setEnabled(true);
	}

	public static void main(String[] args)
	{
		Inventory.money = 200;
		ItemTest t1 = new ItemTest();
		Mart m1 = new Mart();
		m1.setUpMart(JokemonDriver.Area.Stringville,1);
		Inventory.inventoryWindow.openInventory(InventoryWindow.State.OVERWORLD);
	}
}