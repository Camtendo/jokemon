/*Anthony Balmer and Cameron Crockrom
 *
 *fields: int num for poke, array of poke, enum: PROGRAMMER, DEBUGGER,
 *		  PRODIGY, NEWB, KEYBOARDER, NERDY_ BIKER, PROFESSOR, GYM_LEADER, boolean canUseItems
 *
 *Cam: ground
 *Justin: dragon
 *me: psyc
 *Braunsen: flying
 */

 import java.net.URL;
 import java.awt.Point;
 import java.awt.Image;
 import java.awt.Toolkit;
 import java.awt.Graphics;

public class Trainer extends javax.swing.JPanel
{
	public int numPoke, baseLevel;
	public boolean canUseItems;
	public boolean important=false;
	public Pokemon[] pokeArray=new Pokemon[6];
	public final TrainerType type;
	public final String name;
    public String prebattleMSG;
    public String postBattleMSG;
	//0 for Male, 1 for Female
	int gender=0;
	public Point location=new Point(0,0);
	public int direction=270;
	public int viewRange=1;
	final Image[] trainerStand=new Image[4];
	boolean hostile=true;

	public enum TrainerType
	{
		PROGRAMMER, DEBUGGER, HACKER, CYPHER, CODER, ELITE, BABB,
		ENGINEER, BABE, PRODIGY, NEWB, KEYBOARDER, NERDY_BIKER, PROFESSOR, JAVA, GYM_LEADER,
		RIVAL
	}

	public Trainer(TrainerType t, String nm, boolean boo)
    {
    	type = t;
    	name = nm;
    	location = new Point(0,0);
    	important=true;
    	makeTrainer();
    }

    public Trainer(TrainerType t, String nm, Pokemon.Species[] spec, int baseLvl)
    {
    	type = t;
    	name = nm;
    	location = new Point(0,0);
    	baseLevel=baseLvl;
    	makeTrainer();
    }

    public Trainer(TrainerType t, String tName, Pokemon.Species s1, int baseLvl)
    {
    	type=t;
    	name=tName;
    	baseLevel=baseLvl;
    	makeTrainer();

    	pokeArray[0]=new Pokemon(s1,pickLvl());
    	pokeArray[1]=null;
    	pokeArray[2]=null;
    	pokeArray[3]=null;
    	pokeArray[4]=null;
    	pokeArray[5]=null;
    }

    public Trainer(TrainerType t, String tName, Pokemon.Species s1, Pokemon.Species s2, int baseLvl)
    {
    	type=t;
    	name=tName;
    	baseLevel=baseLvl;
    	makeTrainer();

    	pokeArray[0]=new Pokemon(s1,pickLvl());
    	pokeArray[1]=new Pokemon(s2,pickLvl());
    	pokeArray[2]=null;
    	pokeArray[3]=null;
    	pokeArray[4]=null;
    	pokeArray[5]=null;
    }

    public Trainer(TrainerType t, String tName, Pokemon.Species s1, Pokemon.Species s2,
    	Pokemon.Species s3, int baseLvl)
    {
    	type=t;
    	name=tName;
    	baseLevel=baseLvl;
    	makeTrainer();

    	pokeArray[0]=new Pokemon(s1,pickLvl());
    	pokeArray[1]=new Pokemon(s2,pickLvl());
    	pokeArray[2]=new Pokemon(s3,pickLvl());
    	pokeArray[3]=null;
    	pokeArray[4]=null;
    	pokeArray[5]=null;
    }

    public Trainer(TrainerType t, String tName, Pokemon.Species s1, Pokemon.Species s2,
    	Pokemon.Species s3, Pokemon.Species s4, int baseLvl)
    {
    	type=t;
    	name=tName;
    	baseLevel=baseLvl;
    	makeTrainer();

    	pokeArray[0]=new Pokemon(s1,pickLvl());
    	pokeArray[1]=new Pokemon(s2,pickLvl());
    	pokeArray[2]=new Pokemon(s3,pickLvl());
    	pokeArray[3]=new Pokemon(s4,pickLvl());
    	pokeArray[4]=null;
    	pokeArray[5]=null;
    }

    public Trainer(TrainerType t, String tName, Pokemon.Species s1, Pokemon.Species s2,
    	Pokemon.Species s3, Pokemon.Species s4, Pokemon.Species s5, int baseLvl)
    {
    	type=t;
    	name=tName;
    	baseLevel=baseLvl;
    	makeTrainer();

    	pokeArray[0]=new Pokemon(s1,pickLvl());
    	pokeArray[1]=new Pokemon(s2,pickLvl());
    	pokeArray[2]=new Pokemon(s3,pickLvl());
    	pokeArray[3]=new Pokemon(s4,pickLvl());
    	pokeArray[4]=new Pokemon(s5,pickLvl());
    	pokeArray[5]=null;
    }

    public Trainer(TrainerType t, String tName, Pokemon.Species s1, Pokemon.Species s2,
    	Pokemon.Species s3, Pokemon.Species s4, Pokemon.Species s5, Pokemon.Species s6, int baseLvl)
    {
    	type=t;
    	name=tName;
    	baseLevel=baseLvl;
    	makeTrainer();

    	pokeArray[0]=new Pokemon(s1,pickLvl());
    	pokeArray[1]=new Pokemon(s2,pickLvl());
    	pokeArray[2]=new Pokemon(s3,pickLvl());
    	pokeArray[3]=new Pokemon(s4,pickLvl());
    	pokeArray[4]=new Pokemon(s5,pickLvl());
    	pokeArray[5]=new Pokemon(s6,pickLvl());

    	if(type==TrainerType.ELITE||type==TrainerType.BABB||name.equalsIgnoreCase("Battle Tower"))
    	{
    		for(int i=0; i<6; i++)
    		{
    			if(pokeArray[i]!=null)
    			{
    				pokeArray[i].setMaxStats();
    			}
    		}
    	}
    }

    private void makeTrainer()
    {
    	switch(type)
    	{
    		case PROGRAMMER:
    			numPoke = 3;
    			canUseItems = false;
    			prebattleMSG = "Let's test out my Battle Algorithm!";
    			postBattleMSG = "Looks like I've got a Bug...";
    			for(int i=0; i<4; i++)
				{
					URL url=Trainer.class.getResource("Sprites/OverworldNPCs/blackhair"+i+".png");
					trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
				}
    			break;
    		case DEBUGGER:
    			numPoke = 3;
    			canUseItems = false;
    			prebattleMSG = "Do you like Bugs?";
    			postBattleMSG = "I hate bugs.";
    			for(int i=0; i<4; i++)
				{
					URL url=Trainer.class.getResource("Sprites/OverworldNPCs/male"+i+".png");
					trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
				}
    			break;
			case PRODIGY:
    			numPoke = 2;
    			canUseItems = false;
    			prebattleMSG = "I'm younger than you, but I'll still win!";
    			postBattleMSG = "I'm going to tell my mom!";
    			for(int i=0; i<4; i++)
				{
					URL url=Trainer.class.getResource("Sprites/OverworldNPCs/male"+i+".png");
					trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
				}
    			break;
    		case JAVA:
    			numPoke = 2;
    			canUseItems = false;
    			prebattleMSG = "This island belongs to Team Java!";
    			postBattleMSG = "You filthy little brat!";
    			for(int i=0; i<4; i++)
				{
					URL url=Trainer.class.getResource("Sprites/OverworldNPCs/java"+i+".png");
					trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
				}
    			break;
			case NEWB:
    			numPoke = 1;
    			canUseItems = false;
    			prebattleMSG = "Go easy on me, I'm new at this.";
    			postBattleMSG = "Guess I'd better find a new hobby.";
    			for(int i=0; i<4; i++)
				{
					URL url=Trainer.class.getResource("Sprites/OverworldNPCs/male"+i+".png");
					trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
				}
    			break;
			case KEYBOARDER:
    			numPoke = 4;
    			canUseItems = false;
    			prebattleMSG = "My typing skills are great with Pokemon too!";
    			postBattleMSG = "60 words a minute doesn't help me here.";
    			for(int i=0; i<4; i++)
				{
					URL url=Trainer.class.getResource("Sprites/OverworldNPCs/blackhair"+i+".png");
					trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
				}
				break;
			case NERDY_BIKER:
    			numPoke = 3;
    			canUseItems = false;
    			prebattleMSG = "Bicycling is both fun and exercise!";
    			postBattleMSG = "Walking is lame! Bicycling is in style!";
    			for(int i=0; i<4; i++)
				{
					URL url=Trainer.class.getResource("Sprites/OverworldNPCs/biker"+i+".png");
					trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
				}
    			break;
			case PROFESSOR:
    			numPoke = 5;
    			canUseItems = false;
    			prebattleMSG = "I've studied Pokemon all of my life.";
    			postBattleMSG = "Looks like you have studied more.";
    			for(int i=0; i<4; i++)
				{
					URL url=Trainer.class.getResource("Sprites/OverworldNPCs/prof"+i+".png");
					trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
				}
    			break;
			case GYM_LEADER:
    			numPoke = 6;
    			canUseItems = true;
    			prebattleMSG = "";
    			postBattleMSG = "";
    			setSpecificTrainerImages();
    			break;
    		case CYPHER:
    			numPoke = 3;
    			canUseItems = false;
    			prebattleMSG = "Girls like Pokemon too, ya know.";
    			postBattleMSG = "Time to go back to the kitchen...";
    			gender=1;
    			for(int i=0; i<4; i++)
				{
					URL url=Trainer.class.getResource("Sprites/OverworldNPCs/female"+i+".png");
					trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
				}
    			break;
    		case BABE:
    			numPoke = 3;
    			canUseItems = false;
    			prebattleMSG = "This island atmosphere is perfect for a battle!";
    			postBattleMSG = "You only beat me because the sun was in my eyes.";
    			gender=1;
    			for(int i=0; i<4; i++)
				{
					URL url=Trainer.class.getResource("Sprites/OverworldNPCs/female"+i+".png");
					trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
				}
    			break;
    		case HACKER:
    			numPoke = 3;
    			canUseItems = false;
    			prebattleMSG = "I'm going to hack your systems!";
    			postBattleMSG = "Firewalled!!!";
    			for(int i=0; i<4; i++)
				{
					URL url=Trainer.class.getResource("Sprites/OverworldNPCs/blackhair"+i+".png");
					trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
				}
    			break;
    		case CODER:
    			numPoke = 2;
    			canUseItems = false;
    			prebattleMSG = "Joe hired me to work on the island!";
    			postBattleMSG = "I don't get paid enough to lose.";
    			for(int i=0; i<4; i++)
				{
					URL url=Trainer.class.getResource("Sprites/OverworldNPCs/blackhair"+i+".png");
					trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
				}
    			break;
    		case ENGINEER:
    			numPoke = 3;
    			canUseItems = false;
    			prebattleMSG = "My innovation will help me prevail!";
    			postBattleMSG = "I've got to engineer a new way to battle you!";
    			for(int i=0; i<4; i++)
				{
					URL url=Trainer.class.getResource("Sprites/OverworldNPCs/male"+i+".png");
					trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
				}
				break;
			case RIVAL:
    			numPoke = 6;
    			canUseItems = false;
    			prebattleMSG = "";
    			postBattleMSG = "";
    			for(int i=0; i<4; i++)
				{
					URL url=Trainer.class.getResource("Sprites/OverworldNPCs/rival"+i+".png");
					trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
				}
				break;
    		case ELITE:
    			numPoke = 6;
    			canUseItems = true;
    			prebattleMSG = "";
    			postBattleMSG = "";

    			if(name.equals("Justinian"))
    			{
    				for(int i=0; i<4; i++)
					{
						URL url=Trainer.class.getResource("Sprites/OverworldNPCs/Justinian"+i+".png");
						trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
					}
    			}
    			else
    			{
    				for(int i=0; i<4; i++)
					{
						URL url=Trainer.class.getResource("Sprites/OverworldNPCs/blackhair"+i+".png");
						trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
					}
    			}
    			break;
    		case BABB:
    			numPoke = 6;
    			canUseItems = false;
    			prebattleMSG = "";
    			postBattleMSG = "";
    			for(int i=0; i<4; i++)
				{
					URL url=Trainer.class.getResource("Sprites/OverworldNPCs/Babb.png");
					trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
				}
    			break;
    	}
    }

    //Draws Trainer to Screen
	public void drawTrainer(Graphics g)
	{
		g.drawImage(trainerStand[direction/90],(64*6)+64*(location.x-JokemonDriver.location.x),64*4+64*(location.y-JokemonDriver.location.y),64,64,this);
	}

    //Returns Trainer's Pokemon
    public Pokemon getPokemon(int i)
    {
    	return pokeArray[i];
    }

    //Sets the Trainer's view for battle
    public void setViewRange(int i)
    {
    	viewRange=i;
    }

    //Sets the Trainer's location
    public void setLocation(int x, int y)
    {
    	location.x=x;
    	location.y=y;
    }

    //Sets Hostility of a Trainer
    public void setHostile(boolean boo)
    {
    	hostile=boo;
    }

    public String getPreMessage()
    {
    	return prebattleMSG;
    }

    public void setPreMessage(String str)
    {
    	prebattleMSG=str;
    }

    public String getPostMessage()
    {
    	return postBattleMSG;
    }

    public void setPostMessage(String str)
    {
    	postBattleMSG=str;
    }

    public String getNameAndType()
    {
    	return ""+type+" "+name;
    }

    //Sets the Trainer's direction
    public void setDirection(int x)
    {
    	direction=x;
    	if(direction%90!=0)
    		direction=0;
    }

    //Returns a level within a specific range for Pokemon
    public int pickLvl()
    {
    	int throwAway = (int)(Math.random()*4+baseLevel-2);

    	if(throwAway<5)
    		return 5;
    	else if(throwAway>100)
    		return 100;
    	else
    		return throwAway;
    }

    public void makePokeArray()
    {
    	pokeArray = new Pokemon[numPoke];
    	for(int i=0; i<numPoke; i++)
    	{
//    		pokeArray[i] = new Pokemon();
    	}
    }

    //Creates specific Trainer Images for Gym Leaders and such
    public void setSpecificTrainerImages()
    {
    	if(name.equals("James"))
    	{
    		for(int i=0; i<4; i++)
			{
				URL url=Trainer.class.getResource("Sprites/OverworldNPCs/prof"+i+".png");
				trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
			}
    	}
    	else if(name.equals("Jimmy")||name.equals("Jace")||name.equals("Jin"))
    	{
    		for(int i=0; i<4; i++)
			{
				URL url=Trainer.class.getResource("Sprites/OverworldNPCs/blackhair"+i+".png");
				trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
			}
    	}
    	else
    	{
    		for(int i=0; i<4; i++)
			{
				URL url=Trainer.class.getResource("Sprites/OverworldNPCs/female"+i+".png");
				trainerStand[i]=Toolkit.getDefaultToolkit().getImage(url);
			}
    	}
    }

	public Point move(String direction)
	{
		if(direction.equals("N"))
			location.setLocation(location.getX(), location.getY());

		if(direction.equals("S"))
			location.setLocation(location.getX(), location.getY());

		if(direction.equals("E"))
			location.setLocation(location.getX(), location.getY());

		if(direction.equals("W"))
			location.setLocation(location.getX(), location.getY());

		return location;
	}
}