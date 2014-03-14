public class ItemTest
{
	public ItemTest()
	{
		Inventory.addItem(new Item(Item.Type.THUNDER_STONE,5));
		//Inventory.addItem(new Item(Item.Type.EXPERIENCE_ALL,1));
		Inventory.addItem(new Item(Item.Type.TOWN_MAP,1));
		Inventory.addItem(new Item(Item.Type.OLD_ROD,1));
		Inventory.addItem(new Item(Item.Type.GOOD_ROD,1));
		Inventory.addItem(new Item(Item.Type.SUPER_ROD,1));
		Inventory.addItem(new Item(Item.Type.MAX_REVIVE,2));
		Inventory.addItem(new Item(Item.Type.MOUNTAIN_DEW,10));
		Inventory.addItem(new Item(Item.Type.AWAKENING,1));
		Inventory.addItem(new Item(Item.Type.X_DEFEND,1));
		Inventory.addItem(new Item(Item.Type.X_ATTACK,1));
		Inventory.addItem(new Item(Item.Type.OLD_ROD,1));
		Inventory.addItem(new Item(Item.Type.HP_UP,11));
		Inventory.addItem(new Item(Item.Type.OMEGA_BALL,1));
		Inventory.addItem(new Item(Item.Type.POKE_BALL,10));
		Inventory.addItem(new Item(Item.Type.ULTRA_BALL,10));
		Inventory.addItem(new Item(Item.Type.GREAT_BALL,10));
		Inventory.addItem(new Item(Item.Type.MASTER_BALL,1));
		Inventory.addItem(new Item(Item.Type.SILVER_NUGGET,1));
		Inventory.addItem(new Item(Item.Type.ULTIMA_REPEL,1));
		Inventory.addItem(new Item(Item.Type.ROTTEN_APPLE,1));
		Inventory.addItem(new Item(Item.Type.CALCIUM,1));
		Inventory.addItem(new Item(Item.Type.THUNDER_STONE,1));
		//Inventory.addItem( new Item(Item.Type.TM,1,50));
		//Inventory.useItem(new Pokemon(Pokemon.Species.MEW),new Item(Item.Type.MOUNTAIN_DEW,1),false);
		Inventory.changePocket(Item.Pocket.TMHM);
		//System.out.println(Inventory.tooString());
		Pokemon pokemon = new Pokemon(Pokemon.Species.IVYSAUR,100);
		//System.out.println(pokemon);
		//for (int i = 1; i < 51; i++)
		//{
		//	Inventory.addItem(new Item(Item.Type.TM,1,i));
		//}
		//System.out.println(Inventory.tooString());
		//pokemon.move = Mechanics.levelUpMove(pokemon,true);

	}
	public static void main(String[] args)
	{
		ItemTest i = new ItemTest();
	}
}