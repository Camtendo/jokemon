import java.awt.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class Inventory
{
	//All items in the inventory holds enough for 1 of every single item in the game
	static ArrayList<Item> fullInventory = new ArrayList<Item>(200);
	static ArrayList<Item> currentDisplay = new ArrayList<Item>(60);
	public static Item.Pocket currentPocket;
	public static ErrorWindow errorWindow = new ErrorWindow();
	public static YesNoWindow yesNoWindow = new YesNoWindow();
	public static InventoryWindow inventoryWindow = new InventoryWindow();
	public static PokeSelectWin selectWindow = new PokeSelectWin();
	public static int money = 2500;
	public static int moveNo = 0;
	public static boolean itemCancel = false;
	public static boolean throwingPokeBall=false;


	public static void changePocket(Item.Pocket newPocket)
	{
		currentPocket = newPocket;
		while (currentDisplay.size()!= 0)
			currentDisplay.remove(0);

        for (Item aFullInventory : fullInventory) {
            if (aFullInventory.pocket == newPocket)
                currentDisplay.add(aFullInventory);
        }
	}
	//Adds an item to the inventory
	public static void addItem(Item addedItem)
	{
		if (hasItem(addedItem))
		{
			int itemID = getItemId(addedItem);
			Item holdItem = fullInventory.get(itemID);
			holdItem.amount+=addedItem.amount;
			fullInventory.remove(itemID);
			fullInventory.add(itemID,holdItem);
		}
		else
		{
			fullInventory.add(addedItem);
		}
		alphabetizeBag();
	}
	public static boolean hasItem(Item newItem)
	{
		boolean hasItem = false;
        for (Item aFullInventory : fullInventory) {
            if (aFullInventory.type == newItem.type) {
                if (newItem.type == Item.Type.TM || newItem.type == Item.Type.HM) {
                    if (newItem.type == Item.Type.TM && aFullInventory.type == Item.Type.TM && newItem.mNo == aFullInventory.mNo)
                        hasItem = true;
                    else if (newItem.type == Item.Type.HM && aFullInventory.type == Item.Type.HM && newItem.mNo == aFullInventory.mNo)
                        hasItem = true;
                } else {
                    hasItem = true;
                }
            }
        }
		return hasItem;
	}
	public static Item getItemInfo(Item newItem)
	{
		if (!hasItem(newItem))
		return null;
        for (Item aFullInventory : fullInventory) {
            if (aFullInventory.type == newItem.type) {
                if (newItem.type == Item.Type.TM || newItem.type == Item.Type.HM) {
                    if (newItem.type == Item.Type.TM && aFullInventory.type == Item.Type.TM && newItem.mNo == aFullInventory.mNo)
                        return aFullInventory;
                    else if (newItem.type == Item.Type.HM && aFullInventory.type == Item.Type.HM && newItem.mNo == aFullInventory.mNo)
                        return aFullInventory;
                } else {
                    return aFullInventory;
                }
            }
        }
		return null;
	}
	//Item index is the item currently selected out of the array of items in the inventory
	public static Pokemon useTMHM(Pokemon selectedPokemon, Item item)
	{
		if (!hasItem(item))
		return selectedPokemon;

		if (item.effect1 == Item.Effect.TM || item.effect1 == Item.Effect.HM)
		{
			selectedPokemon = Mechanics.teachMove(selectedPokemon,item,moveNo);
			if (!itemCancel)
			decrementItem(item);
			else
			itemCancel = false;
		}

		return selectedPokemon;
	}

	public static void useRod(Item item)
	{
		if (!hasItem(item))
		{
			errorWindow.addMessage(" You don't have this item in your inventory to use! ");
			errorWindow.toFront();
			return;
		}

		if(JokemonDriver.canFish())
		{
			inventoryWindow.closeInventory();
			JokemonDriver.fishing=true;
			JokemonDriver.performingAction=true;
			JokemonDriver.rodType=item;
		}
		else
		{
			errorWindow.addMessage("You hear Babb laughing in the distance...");
			errorWindow.toFront();
		}
	}

	public static void useRepel(Item item)
	{
		if (!hasItem(item))
		{
			errorWindow.addMessage(" You don't have this item in your inventory to use! ");
			errorWindow.toFront();
			return;
		}
		else
		{
			errorWindow.addMessage("No encounters for "+item.power1+" more steps!",""+item.type);
			errorWindow.toFront();
			JokemonDriver.repelSteps+=item.power1;
			if (!itemCancel)
			decrementItem(item);
			return;
		}
	}

	public static void usePokeBall(Item item)
	{
		if (!hasItem(item))
		{
			errorWindow.addMessage(" You don't have this item in your inventory to use! ");
			errorWindow.toFront();
			return;
		}

		if (!Battle.BATTLE_TYPE.equals("WILD"))
		{
			errorWindow.addMessage("You can't steal a Trainer's Pokemon!");
			errorWindow.toFront();
			itemCancel = false;

			if (!itemCancel)
			decrementItem(item);

			return;
		}
		else
		{
			throwingPokeBall=true;
			Battle.b1.addText("You throw a "+item.type+"!");
			Battle.pokeball=item;

			itemCancel=false;

			if (!itemCancel)
			decrementItem(item);
		}
	}

	public static Pokemon useItem(Pokemon selectedPokemon, Item item, boolean npc)
	{
		if (!hasItem(item) && !npc)
		{
			errorWindow.addMessage(" You don't have this item in your inventory to use! ");
			errorWindow.toFront();
			return selectedPokemon;
		}


		selectedPokemon = itemEffect(selectedPokemon,item.type,item.effect1,item.power1,item.healStatus);
		selectedPokemon = itemEffect(selectedPokemon,item.type,item.effect2,item.power2,item.healStatus);

		if (!itemCancel && !npc)
		decrementItem(item);

		return selectedPokemon;
	}
	public static Pokemon itemEffect(Pokemon selectedPokemon, Item.Type type, Item.Effect effect, int power, Pokemon.Status healStatus)
	{
		switch(effect)
		{
			case HP_HEAL:
				if (selectedPokemon.health == selectedPokemon.healthMax && power > 0)
				{
					errorWindow.addMessage(selectedPokemon.nickname + " is already at max health!");
					errorWindow.toFront();
					itemCancel = true;
					break;
				}
				if (selectedPokemon.status == Pokemon.Status.FNT)
				{
					errorWindow.addMessage(type.toString().replace('_',' ') + " will have no effect!");
					errorWindow.toFront();
					itemCancel = true;
					break;
				}
//				if (amountHealed > power)
//				amountHealed = power;
				selectedPokemon.health=Mechanics.recover(selectedPokemon,power);
				if (inventoryWindow.state == InventoryWindow.State.BATTLE)
				Battle.b1.addText(type.toString().replace('_',' ') + " healed " + power + " HP");
				break;
			case STATUS_HEAL:
				boolean cancel = false;
				switch (selectedPokemon.status)
				{
					case OK:
						cancel = true;
						System.out.println("Pokemon's status is OK");
						break;
					case FNT:
						if (healStatus != Pokemon.Status.FNT)
						cancel = true;
						break;
					case SLP:
						if (healStatus != Pokemon.Status.OK && healStatus != Pokemon.Status.SLP)
						cancel = true;
						break;
					case PSN:
						if (healStatus != Pokemon.Status.OK && healStatus != Pokemon.Status.PSN)
						cancel = true;
						break;
					case PAR:
						if (healStatus != Pokemon.Status.OK && healStatus != Pokemon.Status.PAR)
						cancel = true;
						break;
					case BRN:
						if (healStatus != Pokemon.Status.OK && healStatus != Pokemon.Status.BRN)
						cancel = true;
						break;
					case FRZ:
						if (healStatus != Pokemon.Status.OK && healStatus != Pokemon.Status.FRZ)
						cancel = true;
						break;
				}
				if (selectedPokemon.status == Pokemon.Status.OK)
					cancel = true;
				if (cancel)
				{
					System.out.println("Heal Status: "+healStatus);
					System.out.println(selectedPokemon.nickname+": "+selectedPokemon.status);
					errorWindow.addMessage(type.toString().replace('_',' ') + " will have no effect!");
					errorWindow.toFront();
					itemCancel = true;
					break;
				}
				Pokemon.Status holdStatus = selectedPokemon.status;
				if (holdStatus == Pokemon.Status.FNT)
				{
					if (type == Item.Type.REVIVE)
					selectedPokemon.health = selectedPokemon.healthMax/2;
					else
					selectedPokemon.health = selectedPokemon.healthMax;
				}
				if (healStatus == Pokemon.Status.OK)
				{
					selectedPokemon.status = Pokemon.Status.OK;
				}
				else if (healStatus == selectedPokemon.status)
				{
					selectedPokemon.status = Pokemon.Status.OK;
				}
				if (inventoryWindow.state == InventoryWindow.State.BATTLE)
				{

					Battle.b1.addText(type.toString().replace('_',' ') + " was used!");
					if (selectedPokemon.status == Pokemon.Status.OK && holdStatus != Pokemon.Status.OK)
					{
						String message = selectedPokemon.nickname;
						switch(holdStatus)
						{
							case SLP:
								message = message + " was awakened!";
								break;
							case PSN:
								message = message + " was cured!";
								break;
							case PAR:
								message = message + " was healed from paralysis!";
								break;
							case BRN:
								message = message + "'s burn was healed!";
								break;
							case FRZ:
								message = message + " was defrosted!";
								break;
							case FNT:
								message = message + " was revived!";
								break;
						}
						Battle.b1.addText(message);
					}
				}
				else
				{
					if (selectedPokemon.status == Pokemon.Status.OK && holdStatus != Pokemon.Status.OK)
					{
						String message = selectedPokemon.nickname;
						switch(holdStatus)
						{
							case SLP:
								message = message + " was awakened!";
								break;
							case PSN:
								message = message + " was cured!";
								break;
							case PAR:
								message = message + " was healed from paralysis!";
								break;
							case BRN:
								message = message + "'s burn was healed!";
								break;
							case FRZ:
								message = message + " was defrosted!";
								break;
							case FNT:
								message = message + " was revived!";
								break;
						}
						errorWindow.addMessage(message,type.toString().replace('_',' ') + " was used!");
						errorWindow.toFront();
					}
				}
				break;
			case PP_HEAL:
				if (selectedPokemon.TRUE_PP[moveNo] >= selectedPokemon.TRUE_PPMAX[moveNo])
				{
					errorWindow.addMessage(selectedPokemon.move[moveNo].toString().replace('_',' ') + " already has max PP!");
					errorWindow.toFront();
					itemCancel = true;
					break;
				}
				int amountHealedd = selectedPokemon.TRUE_PPMAX[moveNo]- selectedPokemon.TRUE_PP[moveNo];
				if (amountHealedd > power)
				amountHealedd = power;


				selectedPokemon.TRUE_PP[moveNo]+=amountHealedd;

				if (inventoryWindow.state == InventoryWindow.State.BATTLE)
				Battle.b1.addText(selectedPokemon.move[moveNo].toString().replace('_',' ') + " healed " + amountHealedd + " PP");
				break;
			case ELIXER:
				if (selectedPokemon.TRUE_PP[0] == selectedPokemon.TRUE_PPMAX[0] &&
					selectedPokemon.TRUE_PP[1] == selectedPokemon.TRUE_PPMAX[1] &&
					selectedPokemon.TRUE_PP[2] == selectedPokemon.TRUE_PPMAX[2] &&
					selectedPokemon.TRUE_PP[3] == selectedPokemon.TRUE_PPMAX[3])
					{
						errorWindow.addMessage(type.toString().replace('_',' ') + " will have no effect!");
						errorWindow.toFront();
						itemCancel = true;
						break;
					}
				for (int i = 0; i<4; i++)
				{
					int heal = selectedPokemon.TRUE_PPMAX[i] - selectedPokemon.TRUE_PP[i];
					if (heal > power)
					heal = power;
					selectedPokemon.TRUE_PP[i]+=heal;
				}
				if (inventoryWindow.state == InventoryWindow.State.BATTLE)
				Battle.b1.addText(type.toString().replace('_',' ') + " restored PP!");
				break;
			case BATTLE_BOOST:
				switch(type)
				{
					case X_ATTACK:
						if (selectedPokemon.atkStage >= 6)
						{
							errorWindow.addMessage(type.toString().replace('_',' ') + " will have no effect!");
							errorWindow.toFront();
							itemCancel = true;
							break;
						}
						selectedPokemon.atkStage++;
						selectedPokemon.atk=(int)((Mechanics.calcOtherStat(selectedPokemon.baseATK,selectedPokemon.ATK_IV,selectedPokemon.ATK_EV,selectedPokemon.level))*
							Mechanics.stageMultiplier(selectedPokemon.atkStage));

						if (inventoryWindow.state == InventoryWindow.State.BATTLE)
						Battle.b1.addText(selectedPokemon.nickname + "'s Attack Rose!");
						break;
					case X_DEFEND:
						if (selectedPokemon.defStage >= 6)
						{
							errorWindow.addMessage(type.toString().replace('_',' ') + " will have no effect!");
							errorWindow.toFront();
							itemCancel = true;
							break;
						}
						selectedPokemon.defStage++;
						selectedPokemon.def=(int)((Mechanics.calcOtherStat(selectedPokemon.baseDEF,selectedPokemon.DEF_IV,selectedPokemon.DEF_EV,selectedPokemon.level))*
							Mechanics.stageMultiplier(selectedPokemon.defStage));

						if (inventoryWindow.state == InventoryWindow.State.BATTLE)
						Battle.b1.addText(selectedPokemon.nickname + "'s Defense Rose!");
						break;
					case X_SPECIAL:
						if (selectedPokemon.spclStage >= 6)
						{
							errorWindow.addMessage(type.toString().replace('_',' ') + " will have no effect!");
							errorWindow.toFront();
							itemCancel = true;
							break;
						}
						selectedPokemon.spclStage++;
						selectedPokemon.spcl=(int)((Mechanics.calcOtherStat(selectedPokemon.baseSPCL,selectedPokemon.SPCL_IV,selectedPokemon.SPCL_EV,selectedPokemon.level))*
							Mechanics.stageMultiplier(selectedPokemon.spclStage));

						if (inventoryWindow.state == InventoryWindow.State.BATTLE)
						Battle.b1.addText(selectedPokemon.nickname + "'s Special Rose!");
						break;
					case X_SPEED:
						if (selectedPokemon.speedStage >= 6)
						{
							errorWindow.addMessage(type.toString().replace('_',' ') + " will have no effect!");
							errorWindow.toFront();
							itemCancel = true;
							break;
						}
						selectedPokemon.speedStage++;
						selectedPokemon.speed=(int)((Mechanics.calcOtherStat(selectedPokemon.baseSPEED,selectedPokemon.SPEED_IV,selectedPokemon.SPEED_EV,selectedPokemon.level))*
							Mechanics.stageMultiplier(selectedPokemon.speedStage));

						if (inventoryWindow.state == InventoryWindow.State.BATTLE)
						Battle.b1.addText(selectedPokemon.nickname + "'s Speed Rose!");
						break;
					case X_ACCURACY:
						if (selectedPokemon.accuracyStage >= 6)
						{
							errorWindow.addMessage(type.toString().replace('_',' ') + " will have no effect!");
							errorWindow.toFront();
							itemCancel = true;
							break;
						}
						selectedPokemon.accuracyStage++;

						if (inventoryWindow.state == InventoryWindow.State.BATTLE)
						Battle.b1.addText(selectedPokemon.nickname + "'s Accuracy Rose!");
						break;
					case MOUNTAIN_DEW:
						if (selectedPokemon.speedStage >= 6)
						{
							errorWindow.addMessage(type.toString().replace('_',' ') + " had no status boosting effect!");
							errorWindow.toFront();
							break;
						}
						selectedPokemon.speedStage++;
						selectedPokemon.speed=(int)((Mechanics.calcOtherStat(selectedPokemon.baseSPEED,selectedPokemon.SPEED_IV,selectedPokemon.SPEED_EV,selectedPokemon.level))*
							Mechanics.stageMultiplier(selectedPokemon.speedStage));

						if (inventoryWindow.state == InventoryWindow.State.BATTLE)
						Battle.b1.addText(selectedPokemon.nickname + "'s Speed Rose!");
						break;
					case BEER:
						if (selectedPokemon.accuracyStage <= -6)
						{
							errorWindow.addMessage(type.toString().replace('_',' ') + " will have no effect!");
							errorWindow.toFront();
							itemCancel = true;
							break;
						}
						selectedPokemon.accuracyStage--;

						if (inventoryWindow.state == InventoryWindow.State.BATTLE)
						Battle.b1.addText(selectedPokemon.nickname + "'s Accuracy Rose!");
						break;
				}
				break;
			case RARE_CANDY:
				if (selectedPokemon.level<100)
				{
					selectedPokemon.level++;
					selectedPokemon.healthMax=Mechanics.calcHPStat(selectedPokemon.baseHP,selectedPokemon.HP_IV, selectedPokemon.HP_EV,selectedPokemon.level);
					selectedPokemon.atk=Mechanics.calcOtherStat(selectedPokemon.baseATK,selectedPokemon.ATK_IV, selectedPokemon.ATK_EV,selectedPokemon.level);
					selectedPokemon.def =Mechanics.calcOtherStat(selectedPokemon.baseDEF,selectedPokemon.DEF_IV, selectedPokemon.DEF_EV,selectedPokemon.level);
					selectedPokemon.speed=Mechanics.calcOtherStat(selectedPokemon.baseSPEED,selectedPokemon.SPEED_IV, selectedPokemon.SPEED_EV,selectedPokemon.level);
					selectedPokemon.spcl=Mechanics.calcOtherStat(selectedPokemon.baseSPCL,selectedPokemon.SPCL_IV, selectedPokemon.SPCL_EV,selectedPokemon.level);

					selectedPokemon.exp=(selectedPokemon.level-1)*(selectedPokemon.level-1)*(selectedPokemon.level-1);
					selectedPokemon.exp+=selectedPokemon.level;
				}
				else
				{
					errorWindow.addMessage(type.toString().replace('_',' ') + " will have no effect!");
					errorWindow.toFront();
					itemCancel = true;
					break;
				}
				errorWindow.addMessage("Level Up!","Rare Candy!");
				errorWindow.toFront();
				break;
			case PERMA_STAT_BOOST:
			{
				switch(type)
				{
					case HP_UP:
					{
						if (selectedPokemon.HP_EV <= 25600)
						{
							selectedPokemon.HP_EV+=2560;
							int hpGain=selectedPokemon.healthMax;
							selectedPokemon.healthMax=Mechanics.calcHPStat(selectedPokemon.baseHP,selectedPokemon.HP_IV, selectedPokemon.HP_EV,selectedPokemon.level);
							hpGain=selectedPokemon.healthMax-hpGain;
							selectedPokemon.health+=hpGain;
						}
						else
						{
							errorWindow.addMessage(type.toString().replace('_',' ') + " will have no effect!");
							errorWindow.toFront();
							itemCancel = true;
							break;
						}
					}
					errorWindow.addMessage("Max Hp Rose!","Stat Raised");
					errorWindow.toFront();
					break;

					case PROTEIN:
					{
						if (selectedPokemon.ATK_EV <=  25600)
						{
							selectedPokemon.ATK_EV+=2560;
							selectedPokemon.atk=(int)((Mechanics.calcOtherStat(selectedPokemon.baseATK,selectedPokemon.ATK_IV,selectedPokemon.ATK_EV,selectedPokemon.level))*
							Mechanics.stageMultiplier(selectedPokemon.atkStage));
						}
						else
						{
							errorWindow.addMessage(type.toString().replace('_',' ') + " will have no effect!");
							errorWindow.toFront();
							itemCancel = true;
							break;
						}
					}
					errorWindow.addMessage("Max Attack Rose!","Stat Raised");
					errorWindow.toFront();
					break;

					case IRON:
					{
						if (selectedPokemon.DEF_EV <=  25600)
						{
							selectedPokemon.DEF_EV+=2560;
							selectedPokemon.def=(int)((Mechanics.calcOtherStat(selectedPokemon.baseDEF,selectedPokemon.DEF_IV,selectedPokemon.DEF_EV,selectedPokemon.level))*
							Mechanics.stageMultiplier(selectedPokemon.defStage));
						}
						else
						{
							errorWindow.addMessage(type.toString().replace('_',' ') + " will have no effect!");
							errorWindow.toFront();
							itemCancel = true;
							break;
						}
					}
					errorWindow.addMessage("Max Defense Rose!","Stat Raised");
					errorWindow.toFront();
					break;

					case CALCIUM:
					{
						if (selectedPokemon.SPCL_EV <=  25600)
						{
							selectedPokemon.SPCL_EV+=2560;
							selectedPokemon.spcl=(int)((Mechanics.calcOtherStat(selectedPokemon.baseSPCL,selectedPokemon.SPCL_IV,selectedPokemon.SPCL_EV,selectedPokemon.level))*
							Mechanics.stageMultiplier(selectedPokemon.spclStage));
						}
						else
						{
							errorWindow.addMessage(type.toString().replace('_',' ') + " will have no effect!");
							errorWindow.toFront();
							itemCancel = true;
							break;
						}
					}
					errorWindow.addMessage("Max Special Rose!","Stat Raised");
					errorWindow.toFront();
					break;

					case CARBOS:
					{
						if (selectedPokemon.SPEED_EV <=  25600)
						{
							selectedPokemon.SPEED_EV+=2560;
							selectedPokemon.speed=(int)((Mechanics.calcOtherStat(selectedPokemon.baseSPEED,selectedPokemon.SPEED_IV,selectedPokemon.SPEED_EV,selectedPokemon.level))*
							Mechanics.stageMultiplier(selectedPokemon.speedStage));
						}
						else
						{
							errorWindow.addMessage(type.toString().replace('_',' ') + " will have no effect!");
							errorWindow.toFront();
							itemCancel = true;
							break;
						}
					}
					errorWindow.addMessage("Max Speed Rose!","Stat Raised");
					errorWindow.toFront();
					break;

					default:
					if (effect == Item.Effect.NONE)
					break;
					errorWindow.addMessage(type.toString().replace('_',' ') + "'s " + effect.toString());
					errorWindow.addMessage(" effect has not yet been implemented!");
					errorWindow.toFront();
					itemCancel = true;
					break;
				}
			}
			break;
			case EVO_STONE:
			{
				Pokemon.Species oldSpecies = selectedPokemon.species;
				selectedPokemon.species = selectedPokemon.evolve(selectedPokemon,type);
				if (oldSpecies == selectedPokemon.species)
				{
					errorWindow.addMessage(type.toString().replace('_',' ') + " will have no effect!");
					errorWindow.toFront();
					itemCancel = true;
					break;
				}
				else
				{
					selectedPokemon=selectedPokemon.evolution(selectedPokemon,selectedPokemon.species);
					//selectedPokemon.move = Mechanics.levelUpMove(selectedPokemon,true);
					if (oldSpecies.toString().equals(selectedPokemon.nickname))
					selectedPokemon.setNickname(selectedPokemon.species.toString());
					errorWindow.addMessage("Your "+oldSpecies+" evolved into "+selectedPokemon.species+"!","Evolution!");
					errorWindow.toFront();
					Pokedex.seen(selectedPokemon.pokedexNumber-1);
					Pokedex.caught(selectedPokemon.pokedexNumber-1);
					//selectedPokemon.move = Mechanics.levelUpMove(selectedPokemon,true);
				}
			}
			break;
			default:
				if (effect == Item.Effect.NONE)
				break;
				errorWindow.addMessage(type.toString().replace('_',' ') + "'s " + effect.toString());
				errorWindow.addMessage(" effect has not yet been implemented!");
				errorWindow.toFront();
				itemCancel = true;
				break;
		}
		return selectedPokemon;
	}
	public static void alphabetizeBag()
	{
		int x = 1;
		while (x > 0)
		{
			for (int i = 0; i<fullInventory.size()-1; i++)
			{
				if (fullInventory.get(i).alphabet > fullInventory.get(i+1).alphabet)
				{
					Item holdItem = fullInventory.get(i);
					fullInventory.remove(i);
					fullInventory.add(i+1,holdItem);
					x++;
				}
				else if (i != 0 && fullInventory.get(i).alphabet < fullInventory.get(i-1).alphabet)
				{
					Item holdItem = fullInventory.get(i);
					fullInventory.remove(i);
					fullInventory.add(i-1,holdItem);
					x++;
				}
				else if (fullInventory.get(i).type == Item.Type.TM || fullInventory.get(i).type == Item.Type.HM)
				{
					if (fullInventory.get(i).mNo > fullInventory.get(i+1).mNo && fullInventory.get(i).type == fullInventory.get(i+1).type)
					{
						Item holdItem = fullInventory.get(i);
						fullInventory.remove(i);
						fullInventory.add(i+1,holdItem);
						x++;
					}
					else if (i != 0 && fullInventory.get(i).mNo < fullInventory.get(i-1).mNo  && fullInventory.get(i).type == fullInventory.get(i-1).type)
					{
						Item holdItem = fullInventory.get(i);
						fullInventory.remove(i);
						fullInventory.add(i-1,holdItem);
						x++;
					}
				}
			}
		x--;
		}

	}
	public static int getItemId(Item addedItem)
	{
		int itemID = -1;
		for (int i = 0; i<fullInventory.size(); i++)
			{
				Item item = fullInventory.get(i);
				if (item.type == addedItem.type)
				{
					if (item.type == Item.Type.TM || item.type == Item.Type.HM)
					{
						if (item.mNo == addedItem.mNo)
						{
							itemID = i;
							break;
						}
					}
					else
					{
						itemID = i;
						break;
					}

				}
			}
		return itemID;
	}
	//Helps when the item is passed out into current display so it can still access it's location
	//in the full inventory.


	public static void load()//Method that loads inventory from file
	{
		String input="savedata/"+JokemonDriver.getVersion()+".inventory";

		try
		{
			BufferedReader in = new BufferedReader(new FileReader(input));
			String test=in.readLine();

			int runs=Integer.parseInt(in.readLine());

			for (int i = 0; i<runs; i++)
			{
				Item.Type itemType=Item.Type.valueOf(in.readLine());

				if(itemType==Item.Type.TM||itemType==Item.Type.HM)
				{
					int itemNum=Integer.parseInt(in.readLine());
					int tmNo=Integer.parseInt(in.readLine());
					Inventory.addItem(new Item(itemType,(long)itemNum,tmNo));
				}
				else
				{
					int itemNum=Integer.parseInt(in.readLine());
					Inventory.addItem(new Item(itemType,(long)itemNum));
				}

				System.out.println(""+itemType+" added to inventory");
			}
			money=Integer.parseInt(in.readLine());
		}
		catch(Exception e){}

		JokemonDriver.testEncryption(input);

		System.out.println("Inventory loaded successfully.");
	}

	public static void save()//Method that saves to a file with the path name given
	{
		String output ="savedata/"+JokemonDriver.getVersion()+".inventory";

		try
		{
			PrintWriter fout=new PrintWriter(new FileWriter(output));

			if(JokemonDriver.getVersion().equals("Peaches"))
				fout.println("PDAE"+JokemonDriver.idString);
			else
				fout.println("CDAE"+JokemonDriver.idString);

			fout.println(fullInventory.size());

            for (Item aFullInventory : fullInventory) {
                if (aFullInventory.type == Item.Type.TM || aFullInventory.type == Item.Type.HM) {
                    fout.println("" + aFullInventory.type);
                    fout.println("" + aFullInventory.amount);
                    fout.println("" + aFullInventory.mNo);
                } else {
                    fout.println("" + aFullInventory.type);
                    fout.println("" + aFullInventory.amount);
                }
            }
			fout.println(money);
			fout.close();

			JokemonDriver.saveEncryptionKey(output,JokemonDriver.encrypt(output));
			System.out.println("Inventory saved.");
		}
		catch(Exception e){}
	}
	public static String tooString()
	{
		return fullInventory.toString();
	}
	public static String currentString()
	{
		return currentDisplay.toString();
	}
	public static void decrementItem(Item item)
	{
		int itemIndex = getItemId(item);
		if (!fullInventory.get(itemIndex).reUsable)
		{
			fullInventory.set(itemIndex,fullInventory.get(itemIndex)).amount = fullInventory.get(itemIndex).amount-1;
		}

		if (fullInventory.get(itemIndex).amount == 0)
		fullInventory.remove(itemIndex);
		changePocket(currentPocket);
	}
	public static void decrementItem(Item item,boolean test)
	{
		int itemIndex = getItemId(item);

		fullInventory.set(itemIndex,fullInventory.get(itemIndex)).amount = fullInventory.get(itemIndex).amount-1;

		if (fullInventory.get(itemIndex).amount == 0)
		fullInventory.remove(itemIndex);
		changePocket(currentPocket);
	}
	public static void pokemonWasSelected(int pokeIndex, Item item)
	{
		if(item.effect1==Item.Effect.POKEBALL)
		{
			usePokeBall(item);
		}
		else if(item.effect1==Item.Effect.ROD)
		{
			useRod(item);
		}
		else if(item.effect1==Item.Effect.REPEL)
		{
			useRepel(item);
		}
		else if (item.effect1 != Item.Effect.TM && item.effect1 != Item.Effect.HM)
		{
			Battle.user[pokeIndex] = useItem(Battle.user[pokeIndex],fullInventory.get(getItemId(item)),false);
		}
		else
		{
			Battle.user[pokeIndex] = useTMHM(Battle.user[pokeIndex],fullInventory.get(getItemId(item)));
		}

		if (!itemCancel)
		inventoryWindow.itemUsed = true;
		else
		itemCancel = false;

		inventoryWindow.updateSelection();
		inventoryWindow.closeInventory();

		if (inventoryWindow.state == InventoryWindow.State.BATTLE && inventoryWindow.itemUsed)
		Battle.setUserCommand(-1);
	}
}