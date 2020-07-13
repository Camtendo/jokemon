//////////////////////////////////
//GUI Application to check the Pokemon battle system
//
//
//
//
//
//
//
//

public final class BattleTest
{
	static final Pokemon[] user=new Pokemon[6];
	static final Pokemon[] enemy=new Pokemon[6];

	public static void main(String args[])
	{
		new ItemTest();
		user[0]=new Pokemon(Pokemon.Species.EEVEE,Pokemon.Move.FLAMETHROWER,Pokemon.Move.SELFDESTRUCT,Pokemon.Move.PSYCHIC,Pokemon.Move.RECOVER,4);
		//user[0].setNickname("Scrappy");
		user[1]=new Pokemon(Pokemon.Species.MISSINGNO,68);
		user[2]=new Pokemon(Pokemon.Species.MEW,80);
		user[3]=new Pokemon(Pokemon.Species.GENGAR,82);
		user[4]=new Pokemon(Pokemon.Species.RAICHU,79);
		user[5]=new Pokemon(Pokemon.Species.SNORLAX,84);
		
		enemy[0]=new Pokemon(Pokemon.Species.CATERPIE,7);
		new BattleWindow(user,enemy,"WILD");

		Trainer testTrainer=new Trainer(Trainer.TrainerType.JAVA,"GRUNT",Pokemon.Species.RATICATE,Pokemon.Species.GOLBAT,Pokemon.Species.MUK,60);
		for(int i=0; i<6; i++)
		{
			enemy[i]=testTrainer.getPokemon(i);
		}
		new BattleWindow(user,enemy,"TRAINER",testTrainer);
		
		testTrainer=new Trainer(Trainer.TrainerType.PROFESSOR,"James",Pokemon.Species.LAPRAS,Pokemon.Species.JOLTEON,Pokemon.Species.ALAKAZAM,60);
		for(int i=0; i<6; i++)
		{
			enemy[i]=testTrainer.getPokemon(i);
		}
		new BattleWindow(user,enemy,"GYM",testTrainer);
		
		testTrainer=new Trainer(Trainer.TrainerType.RIVAL,"Juan",Pokemon.Species.GENGAR,Pokemon.Species.RHYDON,Pokemon.Species.DRAGONITE,75);
		for(int i=0; i<6; i++)
		{
			enemy[i]=testTrainer.getPokemon(i);
		}
		new BattleWindow(user,enemy,"RIVAL",testTrainer);
		
		enemy[0]=new Pokemon(Pokemon.Species.MEWTWO,70);
		new BattleWindow(user,enemy,"WILD");	
	}
}