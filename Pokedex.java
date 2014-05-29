/*		BULBASAUR,IVYSAUR,VENUSAUR,CHARMANDER,CHARMELEON,CHARIZARD,SQUIRTLE,
		WARTORTLE,BLASTOISE,CATERPIE,METAPOD,BUTTERFREE,WEEDLE,KAKUNA,BEEDRILL,
		PIDGEY,PIDGEOTTO,PIDGEOT,RATTATA,RATICATE,SPEAROW,FEAROW,EKANS,ARBOK,PIKACHU,
		RAICHU,SANDSHREW,SANDSLASH,NIDORAN_F,NIDORINA,NIDOQUEEN,NIDORAN_M,
		NIDORINO,NIDOKING,CLEFAIRY,CLEFABLE,VULPIX,NINETALES,JIGGLYPUFF,WIGGLYTUFF,ZUBAT,
		GOLBAT,ODDISH,GLOOM,VILEPLUME,PARAS,PARASECT,VENONAT,VENOMOTH,DIGLETT,DUGTRIO,
		MEOWTH,PERSIAN,PSYDUCK,GOLDUCK,MANKEY,PRIMEAPE,GROWLITHE,ARCANINE,POLIWAG,POLIWHIRL,
		POLIWRATH,ABRA,KADABRA,ALAKAZAM,MACHOP,MACHOKE,MACHAMP,BELLSPROUT,WEEPINBELL,
		VICTREEBEL,TENTACOOL,TENTACRUEL,GEODUDE,GRAVELER,GOLEM,PONYTA,RAPIDASH,SLOWPOKE,
		SLOWBRO,MAGNEMITE,MAGNETON,FARFETCH_D,DODUO,DODRIO,SEEL,DEWGONG,GRIMER,MUK,
		SHELLDER,CLOYSTER,GASTLY,HAUNTER,GENGAR,ONIX,DROWZEE,HYPNO,KRABBY,KINGLER,VOLTORB,
		ELECTRODE,EXEGGCUTE,EXEGGUTOR,CUBONE,MAROWAK,HITMONLEE,HITMONCHAN,LICKITUNG,KOFFING,
		WEEZING,RHYHORN,RHYDON,CHANSEY,TANGELA,KANGASKHAN,HORSEA,SEADRA,GOLDEEN,SEAKING,
		STARYU,STARMIE,MR_MIME,SCYTHER,JYNX,ELECTABUZZ,MAGMAR,PINSIR,TAUROS,MAGIKARP,
		GYARADOS,LAPRAS,DITTO,EEVEE,VAPOREON,JOLTEON,FLAREON,PORYGON,OMANYTE,OMASTAR,KABUTO,
		KABUTOPS,AERODACTYL,SNORLAX,ARTICUNO,ZAPDOS,MOLTRES,DRATINI,DRAGONAIR,DRAGONITE,
		MEWTWO,MEW,MISSINGNO

		areas:
		Route_0, Route_1, Route_2, Route_3, Route_4, Route_5, Route_6, Route_7,
		Route_8, Route_9, Route_12,Route_13, Route_14,
		Route_ARRAYINDEXOUTOFBOUNDSEXCEPTION,Stringville, Args_Harbor, Mount_Java,
		Villa_Del_Joe, Streamreader_Hotel,Recursive_Hot_Springs, Polymorph_Town, Binary_City,
		Champions_Walk, Victory_Road,Peach_City, Nested_Village, Enumville,
 */

import java.util.Scanner;
import java.io.*;
import java.awt.Image;
import java.net.URL;
import java.awt.Toolkit;

public class Pokedex
{
	static final String[] descriptions = new String[151];
	static final String[] weight = new String[151];
	static final String[] height = new String[151];
	static final String[] areasFound = new String[151];
	static final boolean[] seen = new boolean[151];
	static final boolean[] caught = new boolean[151];
	static int totalSeen, totalCaught;
	final static String fileName = "savedata/pDex.abomb";
	static final Image[] pokedexImg=new Image[151];
	
	public static void createImages()
    {
    	String pokemonType;
    	char a;
    	URL url;

    	for (int i = 0; i < pokedexImg.length; i++)
    	{
			pokemonType = getSpecies(i+1).toString();
			if(pokemonType.equalsIgnoreCase("Mr_Mime"))
				pokemonType="Mr_Mime";
			else if(pokemonType.equalsIgnoreCase("Nidoran_M"))
				pokemonType="Nidoran_M";
			else if(pokemonType.equalsIgnoreCase("Nidoran_F"))
				pokemonType="Nidoran_F";
			else
			{
				pokemonType = pokemonType.toLowerCase();
			    pokemonType = pokemonType.replace("_"," ");
			    a = pokemonType.charAt(0);
			    a = Character.toUpperCase(a);
			    pokemonType = a + pokemonType.substring(1,pokemonType.length());
			}
		    
		    url = Pokemon.class.getResource("Sprites/pokemon/Right/" + pokemonType + ".png");
		    pokedexImg[i] = Toolkit.getDefaultToolkit().createImage(url);
    	}
    }

	public static void makeArrays()
	{
		createImages();
		descriptions[0] = "This grass-type pokemon exhales sleeping powder.";
		descriptions[1] = "The flower that sprouts on its back is as hard as steel.";
		descriptions[2] = "This pokemon can be grown straight from the ground. This process is a closely guarded secret.";
		descriptions[3] = "This fire-type pokemon faints when the fire on its tail goes out.";
		descriptions[4] = "This pokemon's claws can cut through solid steel.";
		descriptions[5] = "This pokemon was mistaken for a legendary pokemon for many years.";
		descriptions[6] = "Most of these pokemon are H2O intolerant.";
		descriptions[7] = "This pokemon's shell is used in Mary Poppins as her purse.";
		descriptions[8] = "The cannons on its back can shoot out any liquid.";
		descriptions[9] = "This pokemon is so plentiful, they are used to keep fires going in the winter.";
		descriptions[10] = "No one knows how this pokemon can move around without limbs.";
		descriptions[11] = "This pokemon can't fly until a few weeks after its evolution.";
		descriptions[12] = "The barb on its head is used for primitive poison tip arrows.";
		descriptions[13] = "The skin on this pokemon can be used for leather.";
		descriptions[14] = "This pokemon can shoot its barbs at things and grow back.";
		descriptions[15] = "A pigeon.";
		descriptions[16] = "A bigger pigeon.";
		descriptions[17] = "The biggest pigeon.";
		descriptions[18] = "This rodent has fangs made out of wood.";
		descriptions[19] = "This rodent evolves from eating too much and becoming fat.";
		descriptions[20] = "A better pigeon.";
		descriptions[21] = "The best pigeon. Kinda.";
		descriptions[22] = "This snake-type pokemon eats its brothers and sisters when born.";
		descriptions[23] = "This pokemon uses its hood as a parachute.";
		descriptions[24] = "Meowths chase this mouse pokemon all over the country.";
		descriptions[25] = "Meowths get electrocuted by this pokemon all over the country.";
		descriptions[26] = "This pokemon constantly runs into walls because it is blind.";
		descriptions[27] = "This pokemon's claws are made out of hardened sand.";
		descriptions[28] = "This pokemon can't see in color, so it mixes up water with other liquids.";
		descriptions[29] = "Most of these pokemon can't find anything better to do than watch grass grow.";
		descriptions[30] = "Pokemon of this type can be found only in the imagination.";
		descriptions[31] = "These risky critters are contantly hurting themselves.";
		descriptions[32] = "We all ask ourselves, \"Why?\"";
		descriptions[33] = "When these pokemon are encountered, most people run away because of the smell.";
		descriptions[34] = "Some people think that these pokemon can grant wishes.";
		descriptions[35] = "These pokemon are the ones that can grant wishes.";
		descriptions[36] = "This pokemon starred in Fantastic Mr. Fox.";
		descriptions[37] = "People are inspired to art by this pokemon.";
		descriptions[38] = "Some people use this pokemon as an alarmclock...idiots.";
		descriptions[39] = "These pokemon are stuffed for decorations in some homes.";
		descriptions[40] = "One of these pokemon can drive an entire city nuts.";
		descriptions[41] = "The sonic pulses that can be emitted from this pokemon breaks glass.";
		descriptions[42] = "This pokemon is a mutated form of a flower bulb.";
		descriptions[43] = "This pokemon has problems with depression.";
		descriptions[44] = "The flower that is attached to its head is the size of a small bus.";
		descriptions[45] = "This forest crab is constantly high on shrooms.";
		descriptions[46] = "The mushroom on its back is a result of being constantly high.";
		descriptions[47] = "This bug pokemon has trouble seeing because it sees multiples of images.";
		descriptions[48] = "This pokemon is a ghetto version of a Butterfree.";
		descriptions[49] = "Most people mistake this pokemon as a piece of poop.";
		descriptions[50] = "People still mistake this pokemon as a group of poop.";
		descriptions[51] = "This cat pokemon spends half of its day cleaning itself.";
		descriptions[52] = "Once evolved, this pokemon cleans itself only once a month.";
		descriptions[53] = "This duck pokemon is so stupid that it can't figure out what its name is.";
		descriptions[54] = "This evolved form of Psyduck has an IQ over 200.";
		descriptions[55] = "This monkey pokemon starred in many of the Rocky movies.";
		descriptions[56] = "Most of these kind of pokemon can defeat a kangaroo at a fighting contest.";
		descriptions[57] = "Small dog pokemon, like this one, have no hearts because their trainers stole them. Jerks.";
		descriptions[58] = "When this pokemon walks around, it must be careful not to cause a fire under its foot.";
		descriptions[59] = "When this pokemon gets electrocuted, it starts to grow limbs.";
		descriptions[60] = "This pokemon tries to walk on water often. It fails every time.";
		descriptions[61] = "This pokemon is actually very calm, despite its name.";
		descriptions[62] = "It is often confused on where it is and just runs away. Imagine that...its only move is teleport.";
		descriptions[63] = "No one knows why it has the butt like it does. It is said to have mystery powers.";
		descriptions[64] = "This pokemon is continually depressed because it can't fix its spoons.";
		descriptions[65] = "This pokemon is completely made up of icing-covered chocolate.";
		descriptions[66] = "It loves to choke people. Duh.";
		descriptions[67] = "When this pokemon losses, it punches a hole into the ground and sometimes accidentally falls in.";
		descriptions[68] = "It suffers from dry mouth because it can never close its mouth and has no toothpaste.";
		descriptions[69] = "This pokemon must hop from place to place. It makes a sloshing sound because of it.";
		descriptions[70] = "The vines sprouting from this pokemon can't even reach its own back to scratch it. Fail.";
		descriptions[71] = "This pokemon likes to wear sunglasses because it makes it feel even cooler.";
		descriptions[72] = "The cruelty is known across the planet...in peace talks.";
		descriptions[73] = "A lump of German chocolate. With arms.";
		descriptions[74] = "When Geodude gets melted down and added with dark chocolate, this pokemon is made.";
		descriptions[75] = "It is a lizard that has a hardened chocolate shell for tasty snacking.";
		descriptions[76] = "This pokemon's hooves are delicacies in many foreign countries.";
		descriptions[77] = "Sometimes when this pokemon runs fast, it can put out the flames and put make itself faint.";
		descriptions[78] = "When playing chess, this pokemon takes 20 minutes to have one thought about a move.";
		descriptions[79] = "The thing on its head gives it another brain making it twice as fast as its ancestor. Its still very slow.";
		descriptions[80] = "An EMP, despite what many think, actually makes these pokemon.";
		descriptions[81] = "Before it evolves, many have to get together and headbutt each other 10 times. No one knows why.";
		descriptions[82] = "These bird pokemon pick up a random stick they find in the forest. It becomes their beat-stick.";
		descriptions[83] = "It resembles a dodo bird.";
		descriptions[84] = "This dodo-like bird pokemon gets three times dumber since it has three heads.";
		descriptions[85] = "This pokemon is made up of ice cream. It lives in cold to keep from melting.";
		descriptions[86] = "It is still made up of ice cream but has a layer of frosting so that it can resist some extra warmth.";
		descriptions[87] = "Black people are constantly looking for these pokemon because they are made of purple drank.";
		descriptions[88] = "They evolve when they drink too much of purple drank. They just grow larger. Or fatter.";
		descriptions[89] = "This pokemon is deathly afraid of chowders. Mainly ones made of clams.";
		descriptions[90] = "The ball in the middle of this pokemon is its brain. The problem is that it is hollow.";
		descriptions[91] = "Very few can see the little wires holding up this pokemon. No one knows who controls the wires though.";
		descriptions[92] = "It can finally move on its own. Although it can fall over sometimes tripping on the air.";
		descriptions[93] = "This is the smartest of all three versions because it finally figured out that feet are good.";
		descriptions[94] = "This rock-type pokemon can only see in the dark. Thats why its only found in caves.";
		descriptions[95] = "This pokemon has trouble staying awake in the day. If it falls asleep, everyone must beware of its snore attack.";
		descriptions[96] = "The item that it is holding are just a string and a washer that it finds along the routes.";
		descriptions[97] = "When it eats, it prefers cakes. Although, it isn't always aware of what kind of meat it is.";
		descriptions[98] = "These are very rare in the wild because there are only one per cave. They rule the other crabs";
		descriptions[99] = "Many people mistake this pokemon as a pokeball and try to pick it up. Ouch.";
		descriptions[100] = "People also mistake this pokemon as a pokeball...idiots.";
		descriptions[101] = "The cracks on each of the eggs are battle damage of previous experiences. Hardcore.";
		descriptions[102] = "How a group of eggs turns into a tree with legs keeps scientists baffled.";
		descriptions[103] = "The bone this pokemon carries is actually puchased from Walmart. And its plastic.";
		descriptions[104] = "The maternal instincts of this pokemon are so strong, even males have them.";
		descriptions[105] = "This pokemon gets the honor of training with Bruce Lee every summer.";
		descriptions[106] = "This pokemon gets to train with Jackie Chan every winter. That's so much better.";
		descriptions[107] = "The tongue on this pokemon can be used as a landing platform for airplanes.";
		descriptions[108] = "They suffer from allergies.";
		descriptions[109] = "The multiple heads on this pokemon constantly fight on what to eat.";
		descriptions[110] = "Although the skin looks tough, it can be broken with a toothpick.";
		descriptions[111] = "Once evolved, the skin hardens to where it can be broken only by a doughnut.";
		descriptions[112] = "This pokemon has magical healing powers. Chuck Norris is jealous.";
		descriptions[113] = "It is a collection of many colored Twizzlers.";
		descriptions[114] = "Even newborns of this pokemon have younglings in its pouch. How weird.";
		descriptions[115] = "This pokemon has trouble staying in one spot. It isn't intellegent enough to use its tail.";
		descriptions[116] = "When it evolves, it can stab its food with the spikes on its back.";
		descriptions[117] = "It has the attention span of a goldfish. A total of three seconds. Nice.";
		descriptions[118] = "Despite the name, this pokemon is the one that does all the work to keep the ocean clean. Sucks for them.";
		descriptions[119] = "The stone in the middle is made out of hardened candy. Cherry flavored.";
		descriptions[120] = "This pokemon spends its free days walking on the beach, having candle-lit dinners, and many other things.";
		descriptions[121] = "This is the original mime. Every mime has learned its ways to amuse crowds.";
		descriptions[122] = "This is the pet of the Grim Reaper.";
		descriptions[123] = "Wherever this pokemon roams, people shove it away because it brings very bad luck.";
		descriptions[124] = "This is the spawn of batteries from power plants.";
		descriptions[125] = "These pokemon are created when a volcano erupts.";
		descriptions[126] = "The vice grips on its head can barely hold a leaf because they are so weak.";
		descriptions[127] = "This pokemon is the original buffalo from Africa. It then moved to Kanto for a vacation.";
		descriptions[128] = "Don't buy this piece of crap pokemon for 500. Just catch one and train it.";
		descriptions[129] = "This is one of the best pokemon in the game. It is a BEAST.";
		descriptions[130] = "This mystical pokemon is extremely rare because they are killed for their shells.";
		descriptions[131] = "This pokemon is made out of grape jello.";
		descriptions[132] = "This is the basic fox. Boring.";
		descriptions[133] = "This water type fox is part mermaid. Or merman.";
		descriptions[134] = "This electric type fox is so pointy that it can make a kebab with its hair.";
		descriptions[135] = "The hair of this pokemon is ice cold because it lacks a soul.";
		descriptions[136] = "This pokemon came from a horrible accident dealing with a geometry problem.";
		descriptions[137] = "The water that this pokemon is raised in is always filled with many elements to make the shell.";
		descriptions[138] = "The teeth of this pokemon can tear through leather.";
		descriptions[139] = "When the world began, this pokemon said hi to the sand hills not knowing they were inanimate.";
		descriptions[140] = "This is also a common pet of the Grim Reaper. It is prefered actually.";
		descriptions[141] = "This ancient pokemon is thought to poof into dust anytime because of its age. They shave their beards.";
		descriptions[142] = "This fat pokemon spends its day eating, sleeping, and repeating.";
		descriptions[143] = "Many believe that this pokemon caused the ice age. It was actually the fire version.";
		descriptions[144] = "The tips of this pokemon's wings can cut the tips of trees off with lightning.";
		descriptions[145] = "This pokemon likes to hang out in the cold mountains. It seems to keep it relaxed.";
		descriptions[146] = "The tail of this pokemon is used as a toothpick and to pick its nose. Gross.";
		descriptions[147] = "The little wings that this pokemon has can help it carry anything up to 20 times its weight.";
		descriptions[148] = "When the world was created, this pokemon almost died off from boredom.";
		descriptions[149] = "This pokemon was created from getting a mew to eat a ditto. Which is its favorite food.";
		descriptions[150] = "This pokemon is extremely rare and curious. They say that curiosity killed the cat.";

		weight[0] = "15 lbs";
		weight[1] = "29 lbs";
		weight[2] = "221 lbs";
		weight[3] = "19 lbs";
		weight[4] = "42 lbs";
		weight[5] = "200 lbs";
		weight[6] = "20 lbs";
		weight[7] = "50 lbs";
		weight[8] = "189 lbs";
		weight[9] = "6 lbs";
		weight[10] = "22 lbs";
		weight[11] = "71 lbs";
		weight[12] = "7 lbs";
		weight[13] = "22 lbs";
		weight[14] = "65 lbs";
		weight[15] = "4 lbs";
		weight[16] = "66 lbs";
		weight[17] = "87 lbs";
		weight[18] = "8 lbs";
		weight[19] = "41 lbs";
		weight[20] = "4 lbs";
		weight[21] = "84 lbs";
		weight[22] = "15 lbs";
		weight[23] = "143 lbs";
		weight[24] = "13 lbs";
		weight[25] = "66 lbs";
		weight[26] = "26 lbs";
		weight[27] = "65 lbs";
		weight[28] = "15 lbs";
		weight[29] = "44 lbs";
		weight[30] = "132 lbs";
		weight[31] = "20 lbs";
		weight[32] = "43 lbs";
		weight[33] = "137 lbs";
		weight[34] = "17 lbs";
		weight[35] = "88 lbs";
		weight[36] = "22 lbs";
		weight[37] = "44 lbs";
		weight[38] = "12 lbs";
		weight[39] = "26 lbs";
		weight[40] = "17 lbs";
		weight[41] = "121 lbs";
		weight[42] = "12 lbs";
		weight[43] = "19 lbs";
		weight[44] = "41 lbs";
		weight[45] = "12 lbs";
		weight[46] = "65 lbs";
		weight[47] = "66 lbs";
		weight[48] = "28 lbs";
		weight[49] = "2 lbs";
		weight[50] = "73 lbs";
		weight[51] = "9 lbs";
		weight[52] = "71 lbs";
		weight[53] = "43 lbs";
		weight[54] = "169 lbs";
		weight[55] = "62 lbs";
		weight[56] = "71 lbs";
		weight[57] = "42 lbs";
		weight[58] = "342 lbs";
		weight[59] = "27 lbs";
		weight[60] = "44 lbs";
		weight[61] = "119 lbs";
		weight[62] = "43 lbs";
		weight[63] = "125 lbs";
		weight[64] = "106 lbs";
		weight[65] = "43 lbs";
		weight[66] = "155 lbs";
		weight[67] = "287 lbs";
		weight[68] = "9 lbs";
		weight[69] = "14 lbs";
		weight[70] = "34 lbs";
		weight[71] = "100 lbs";
		weight[72] = "121 lbs";
		weight[73] = "44 lbs";
		weight[74] = "232 lbs";
		weight[75] = "622 lbs";
		weight[76] = "66 lbs";
		weight[77] = "209 lbs";
		weight[78] = "79 lbs";
		weight[79] = "173 lbs";
		weight[80] = "13 lbs";
		weight[81] = "132 lbs";
		weight[82] = "33 lbs";
		weight[83] = "86 lbs";
		weight[84] = "188 lbs";
		weight[85] = "198 lbs";
		weight[86] = "265 lbs";
		weight[87] = "66 lbs";
		weight[88] = "66 lbs";
		weight[89] = "9 lbs";
		weight[90] = "292 lbs";
		weight[91] = "0.2 lbs";
		weight[92] = "0.2 lbs";
		weight[93] = "89 lbs";
		weight[94] = "463 lbs";
		weight[95] = "71 lbs";
		weight[96] = "167 lbs";
		weight[97] = "14 lbs";
		weight[98] = "132 lbs";
		weight[99] = "23 lbs";
		weight[100] = "147 lbs";
		weight[101] = "6 lbs";
		weight[102] = "265 lbs";
		weight[103] = "14 lbs";
		weight[104] = "99 lbs";
		weight[105] = "110 lbs";
		weight[106] = "111 lbs";
		weight[107] = "144 lbs";
		weight[108] = "2 lbs";
		weight[109] = "21 lbs";
		weight[110] = "254 lbs";
		weight[111] = "265 lbs";
		weight[112] = "76 lbs";
		weight[113] = "77 lbs";
		weight[114] = "176 lbs";
		weight[115] = "18 lbs";
		weight[116] = "55 lbs";
		weight[117] = "33 lbs";
		weight[118] = "86 lbs";
		weight[119] = "276 lbs";
		weight[120] = "176 lbs";
		weight[121] = "120 lbs";
		weight[122] = "123 lbs";
		weight[123] = "90 lbs";
		weight[124] = "66 lbs";
		weight[125] = "98 lbs";
		weight[126] = "121 lbs";
		weight[127] = "195 lbs";
		weight[128] = "22 lbs";
		weight[129] = "518 lbs";
		weight[130] = "485 lbs";
		weight[131] = "9 lbs";
		weight[132] = "14 lbs";
		weight[133] = "64 lbs";
		weight[134] = "54 lbs";
		weight[135] = "55 lbs";
		weight[136] = "80 lbs";
		weight[137] = "17 lbs";
		weight[138] = "77 lbs";
		weight[139] = "25 lbs";
		weight[140] = "89 lbs";
		weight[141] = "130 lbs";
		weight[142] = "1014 lbs";
		weight[143] = "122 lbs";
		weight[144] = "116 lbs";
		weight[145] = "132 lbs";
		weight[146] = "7 lbs";
		weight[147] = "36 lbs";
		weight[148] = "463 lbs";
		weight[149] = "269 lbs";
		weight[150] = "25 lbs";

		height[0] = "2\' 4\"";
		height[1] = "3\' 3\"";
		height[2] = "6\' 7\"";
		height[3] = "2\' 0\"";
		height[4] = "3\' 7\"";
		height[5] = "5\' 7\"";
		height[6] = "1\' 8\"";
		height[7] = "3\' 3\"";
		height[8] = "5\' 3\"";
		height[9] = "1\' 0\"";
		height[10] = "2\' 4\"";
		height[11] = "3\' 7\"";
		height[12] = "1\' 0\"";
		height[13] = "2\' 0\"";
		height[14] = "3\' 3\"";
		height[15] = "1\' 0\"";
		height[16] = "3\' 7\"";
		height[17] = "4\' 11\"";
		height[18] = "1\' 0\"";
		height[19] = "2\' 4\"";
		height[20] = "1\' 0\"";
		height[21] = "3\' 11\"";
		height[22] = "6\' 7\"";
		height[23] = "11\' 6\"";
		height[24] = "1\' 4\"";
		height[25] = "2\' 7\"";
		height[26] = "2\' 0\"";
		height[27] = "3\' 3\"";
		height[28] = "1\' 4\"";
		height[29] = "2\' 7\"";
		height[30] = "4\' 3\"";
		height[31] = "1\' 8\"";
		height[32] = "2\' 11\"";
		height[33] = "4\' 7\"";
		height[34] = "2\' 0\"";
		height[35] = "4\' 3\"";
		height[36] = "2\' 0\"";
		height[37] = "3\' 7\"";
		height[38] = "1\' 8\"";
		height[39] = "3\' 3\"";
		height[40] = "2\' 7\"";
		height[41] = "5\' 3\"";
		height[42] = "1\' 8\"";
		height[43] = "2\' 7\"";
		height[44] = "3\' 11\"";
		height[45] = "1\' 0\"";
		height[46] = "3\' 3\"";
		height[47] = "3\' 3\"";
		height[48] = "4\' 11\"";
		height[49] = "0\' 8\"";
		height[50] = "2\' 4\"";
		height[51] = "1\' 4\"";
		height[52] = "3\' 3\"";
		height[53] = "2\' 7\"";
		height[54] = "5\' 7\"";
		height[55] = "1\' 8\"";
		height[56] = "3\' 3\"";
		height[57] = "2\' 4\"";
		height[58] = "6\' 3\"";
		height[59] = "2\' 0\"";
		height[60] = "3\' 3\"";
		height[61] = "4\' 3\"";
		height[62] = "2\' 11\"";
		height[63] = "4\' 3\"";
		height[64] = "4\' 11\"";
		height[65] = "2\' 7\"";
		height[66] = "4\' 1\"";
		height[67] = "5\' 3\"";
		height[68] = "2\' 4\"";
		height[69] = "3\' 3\"";
		height[70] = "5\' 7\"";
		height[71] = "2\' 11\"";
		height[72] = "5\' 3\"";
		height[73] = "1\' 4\"";
		height[74] = "3\' 3\"";
		height[75] = "4\' 7\"";
		height[76] = "3\' 3\"";
		height[77] = "5\' 7\"";
		height[78] = "3\' 11\"";
		height[79] = "5\' 3\"";
		height[80] = "1\' 0\"";
		height[81] = "3\' 3\"";
		height[82] = "2\' 7\"";
		height[83] = "4\' 7\"";
		height[84] = "5\' 11\"";
		height[85] = "3\' 7\"";
		height[86] = "5\' 7\"";
		height[87] = "2\' 11\"";
		height[88] = "3\' 11\"";
		height[89] = "1\' 0\"";
		height[90] = "4\' 11\"";
		height[91] = "4\' 3\"";
		height[92] = "5\' 3\"";
		height[93] = "4\' 11\"";
		height[94] = "28\' 10\"";
		height[95] = "3\' 3\"";
		height[96] = "5\' 3\"";
		height[97] = "1\' 4\"";
		height[98] = "4\' 3\"";
		height[99] = "1\' 8\"";
		height[100] = "3\' 11\"";
		height[101] = "1\' 4\"";
		height[102] = "6\' 7\"";
		height[103] = "1\' 4\"";
		height[104] = "3\' 3\"";
		height[105] = "4\' 11\"";
		height[106] = "4\' 7\"";
		height[107] = "3\' 11\"";
		height[108] = "2\' 0\"";
		height[109] = "3\' 11\"";
		height[110] = "3\' 3\"";
		height[111] = "6\' 3\"";
		height[112] = "3\' 7\"";
		height[113] = "3\' 3\"";
		height[114] = "7\' 3\"";
		height[115] = "1\' 4\"";
		height[116] = "3\' 11\"";
		height[117] = "2\' 3\"";
		height[118] = "4\' 3\"";
		height[119] = "2\' 7\"";
		height[120] = "3\' 7\"";
		height[121] = "4\' 3\"";
		height[122] = "4\' 11\"";
		height[123] = "4\' 7\"";
		height[124] = "3\' 7\"";
		height[125] = "4\' 3\"";
		height[126] = "4\' 1\"";
		height[127] = "4\' 7\"";
		height[128] = "2\' 11\"";
		height[129] = "21\' 4\"";
		height[130] = "8\' 2\"";
		height[131] = "1\' 0\"";
		height[132] = "1\' 0\"";
		height[133] = "3\' 3\"";
		height[134] = "2\' 7\"";
		height[135] = "2\' 1\"";
		height[136] = "2\' 7\"";
		height[137] = "1\' 4\"";
		height[138] = "3\' 3\"";
		height[139] = "1\' 8\"";
		height[140] = "4\' 3\"";
		height[141] = "5\' 11\"";
		height[142] = "6\' 11\"";
		height[143] = "5\' 7\"";
		height[144] = "5\' 3\"";
		height[145] = "6\' 7\"";
		height[146] = "5\' 11\"";
		height[147] = "13\' 1\"";
		height[148] = "7\' 3\"";
		height[149] = "6\' 7\"";
		height[150] = "1\' 8\"";

		areasFound[0] = "";
		areasFound[1] = "";
		areasFound[2] = "";
		areasFound[3] = "";
		areasFound[4] = "";
		areasFound[5] = "";
		areasFound[6] = "";
		areasFound[7] = "";
		areasFound[8] = "";
		areasFound[9] = "01020305";
		areasFound[10] = "0333";
		areasFound[11] = "0533";
		areasFound[12] = "0102";
		areasFound[13] = "0333";
		areasFound[14] = "33";
		areasFound[15] = "010203";
		areasFound[16] = "040510";
		areasFound[17] = "";
		areasFound[18] = "01020305";
		areasFound[19] = "04050612132539";
		areasFound[20] = "0102";
		areasFound[21] = "05";
		areasFound[22] = "0306";
		areasFound[23] = "";
		areasFound[24] = "0315";
		areasFound[25] = "";
		areasFound[26] = "0306";
		areasFound[27] = "";
		areasFound[28] = "03";
		areasFound[29] = "0607";
		areasFound[30] = "";
		areasFound[31] = "03";
		areasFound[32] = "07";
		areasFound[33] = "";
		areasFound[34] = "05";
		areasFound[35] = "";
		areasFound[36] = "05";
		areasFound[37] = "";
		areasFound[38] = "05";
		areasFound[39] = "";
		areasFound[40] = "050634363738";
		areasFound[41] = "39";
		areasFound[42] = "04";
		areasFound[43] = "";
		areasFound[44] = "";
		areasFound[45] = "04";
		areasFound[46] = "10";
		areasFound[47] = "33";
		areasFound[48] = "";
		areasFound[49] = "0635";
		areasFound[50] = "35";
		areasFound[51] = "0710";
		areasFound[52] = "";
		areasFound[53] = "10";
		areasFound[54] = "";
		areasFound[55] = "11";
		areasFound[56] = "";
		areasFound[57] = "1011";
		areasFound[58] = "";
		areasFound[59] = "04";
		areasFound[60] = "1011";
		areasFound[61] = "";
		areasFound[62] = "072539";
		areasFound[63] = "2539";
		areasFound[64] = "";
		areasFound[65] = "06";
		areasFound[66] = "12";
		areasFound[67] = "";
		areasFound[68] = "11";
		areasFound[69] = "";
		areasFound[70] = "";
		areasFound[71] = "";
		areasFound[72] = "13";
		areasFound[73] = "0634363738";
		areasFound[74] = "111439";
		areasFound[75] = "";
		areasFound[76] = "04";
		areasFound[77] = "";
		areasFound[78] = "0710";
		areasFound[79] = "";
		areasFound[80] = "071015";
		areasFound[81] = "15";
		areasFound[82] = "12";
		areasFound[83] = "1114";
		areasFound[84] = "";
		areasFound[85] = "";
		areasFound[86] = "";
		areasFound[87] = "12";
		areasFound[88] = "";
		areasFound[89] = "08";
		areasFound[90] = "";
		areasFound[91] = "39";
		areasFound[92] = "";
		areasFound[93] = "";
		areasFound[94] = "0637363438";
		areasFound[95] = "03";
		areasFound[96] = "";
		areasFound[97] = "101112";
		areasFound[98] = "";
		areasFound[99] = "111415";
		areasFound[100] = "";
		areasFound[101] = "12";
		areasFound[102] = "";
		areasFound[103] = "1011";
		areasFound[104] = "";
		areasFound[105] = "39";
		areasFound[106] = "39";
		areasFound[107] = "39";
		areasFound[108] = "1114";
		areasFound[109] = "";
		areasFound[110] = "06";
		areasFound[111] = "";
		areasFound[112] = "39";
		areasFound[113] = "04";
		areasFound[114] = "12";
		areasFound[115] = "09";
		areasFound[116] = "";
		areasFound[117] = "";
		areasFound[118] = "";
		areasFound[119] = "39";
		areasFound[120] = "";
		areasFound[121] = "0708";
		areasFound[122] = "39";
		areasFound[123] = "0708";
		areasFound[124] = "12";
		areasFound[125] = "05";
		areasFound[126] = "11";
		areasFound[127] = "04";
		areasFound[128] = "0203";
		areasFound[129] = "";
		areasFound[130] = "033637";
		areasFound[131] = "0511";
		areasFound[132] = "12";
		areasFound[133] = "";
		areasFound[134] = "";
		areasFound[135] = "";
		areasFound[136] = "12";
		areasFound[137] = "04";
		areasFound[138] = "";
		areasFound[139] = "04";
		areasFound[140] = "";
		areasFound[141] = "04";
		areasFound[142] = "";
		areasFound[143] = "";
		areasFound[144] = "";
		areasFound[145] = "";
		areasFound[146] = "39";
		areasFound[147] = "";
		areasFound[148] = "";
		areasFound[149] = "";
		areasFound[150] = "";

		seen[0] = false;
		seen[1] = false;
		seen[2] = false;
		seen[3] = false;
		seen[4] = false;
		seen[5] = false;
		seen[6] = false;
		seen[7] = false;
		seen[8] = false;
		seen[9] = false;
		seen[10] = false;
		seen[11] = false;
		seen[12] = false;
		seen[13] = false;
		seen[14] = false;
		seen[15] = false;
		seen[16] = false;
		seen[17] = false;
		seen[18] = false;
		seen[19] = false;
		seen[20] = false;
		seen[21] = false;
		seen[22] = false;
		seen[23] = false;
		seen[24] = false;
		seen[25] = false;
		seen[26] = false;
		seen[27] = false;
		seen[28] = false;
		seen[29] = false;
		seen[30] = false;
		seen[31] = false;
		seen[32] = false;
		seen[33] = false;
		seen[34] = false;
		seen[35] = false;
		seen[36] = false;
		seen[37] = false;
		seen[38] = false;
		seen[39] = false;
		seen[40] = false;
		seen[41] = false;
		seen[42] = false;
		seen[43] = false;
		seen[44] = false;
		seen[45] = false;
		seen[46] = false;
		seen[47] = false;
		seen[48] = false;
		seen[49] = false;
		seen[50] = false;
		seen[51] = false;
		seen[52] = false;
		seen[53] = false;
		seen[54] = false;
		seen[55] = false;
		seen[56] = false;
		seen[57] = false;
		seen[58] = false;
		seen[59] = false;
		seen[60] = false;
		seen[61] = false;
		seen[62] = false;
		seen[63] = false;
		seen[64] = false;
		seen[65] = false;
		seen[66] = false;
		seen[67] = false;
		seen[68] = false;
		seen[69] = false;
		seen[70] = false;
		seen[71] = false;
		seen[72] = false;
		seen[73] = false;
		seen[74] = false;
		seen[75] = false;
		seen[76] = false;
		seen[77] = false;
		seen[78] = false;
		seen[79] = false;
		seen[80] = false;
		seen[81] = false;
		seen[82] = false;
		seen[83] = false;
		seen[84] = false;
		seen[85] = false;
		seen[86] = false;
		seen[87] = false;
		seen[88] = false;
		seen[89] = false;
		seen[90] = false;
		seen[91] = false;
		seen[92] = false;
		seen[93] = false;
		seen[94] = false;
		seen[95] = false;
		seen[96] = false;
		seen[97] = false;
		seen[98] = false;
		seen[99] = false;
		seen[100] = false;
		seen[101] = false;
		seen[102] = false;
		seen[103] = false;
		seen[104] = false;
		seen[105] = false;
		seen[106] = false;
		seen[107] = false;
		seen[108] = false;
		seen[109] = false;
		seen[110] = false;
		seen[111] = false;
		seen[112] = false;
		seen[113] = false;
		seen[114] = false;
		seen[115] = false;
		seen[116] = false;
		seen[117] = false;
		seen[118] = false;
		seen[119] = false;
		seen[120] = false;
		seen[121] = false;
		seen[122] = false;
		seen[123] = false;
		seen[124] = false;
		seen[125] = false;
		seen[126] = false;
		seen[127] = false;
		seen[128] = false;
		seen[129] = false;
		seen[130] = false;
		seen[131] = false;
		seen[132] = false;
		seen[133] = false;
		seen[134] = false;
		seen[135] = false;
		seen[136] = false;
		seen[137] = false;
		seen[138] = false;
		seen[139] = false;
		seen[140] = false;
		seen[141] = false;
		seen[142] = false;
		seen[143] = false;
		seen[144] = false;
		seen[145] = false;
		seen[146] = false;
		seen[147] = false;
		seen[148] = false;
		seen[149] = false;
		seen[150] = false;

		caught[0] = false;
		caught[1] = false;
		caught[2] = false;
		caught[3] = false;
		caught[4] = false;
		caught[5] = false;
		caught[6] = false;
		caught[7] = false;
		caught[8] = false;
		caught[9] = false;
		caught[10] = false;
		caught[11] = false;
		caught[12] = false;
		caught[13] = false;
		caught[14] = false;
		caught[15] = false;
		caught[16] = false;
		caught[17] = false;
		caught[18] = false;
		caught[19] = false;
		caught[20] = false;
		caught[21] = false;
		caught[22] = false;
		caught[23] = false;
		caught[24] = false;
		caught[25] = false;
		caught[26] = false;
		caught[27] = false;
		caught[28] = false;
		caught[29] = false;
		caught[30] = false;
		caught[31] = false;
		caught[32] = false;
		caught[33] = false;
		caught[34] = false;
		caught[35] = false;
		caught[36] = false;
		caught[37] = false;
		caught[38] = false;
		caught[39] = false;
		caught[40] = false;
		caught[41] = false;
		caught[42] = false;
		caught[43] = false;
		caught[44] = false;
		caught[45] = false;
		caught[46] = false;
		caught[47] = false;
		caught[48] = false;
		caught[49] = false;
		caught[50] = false;
		caught[51] = false;
		caught[52] = false;
		caught[53] = false;
		caught[54] = false;
		caught[55] = false;
		caught[56] = false;
		caught[57] = false;
		caught[58] = false;
		caught[59] = false;
		caught[60] = false;
		caught[61] = false;
		caught[62] = false;
		caught[63] = false;
		caught[64] = false;
		caught[65] = false;
		caught[66] = false;
		caught[67] = false;
		caught[68] = false;
		caught[69] = false;
		caught[70] = false;
		caught[71] = false;
		caught[72] = false;
		caught[73] = false;
		caught[74] = false;
		caught[75] = false;
		caught[76] = false;
		caught[77] = false;
		caught[78] = false;
		caught[79] = false;
		caught[80] = false;
		caught[81] = false;
		caught[82] = false;
		caught[83] = false;
		caught[84] = false;
		caught[85] = false;
		caught[86] = false;
		caught[87] = false;
		caught[88] = false;
		caught[89] = false;
		caught[90] = false;
		caught[91] = false;
		caught[92] = false;
		caught[93] = false;
		caught[94] = false;
		caught[95] = false;
		caught[96] = false;
		caught[97] = false;
		caught[98] = false;
		caught[99] = false;
		caught[100] = false;
		caught[101] = false;
		caught[102] = false;
		caught[103] = false;
		caught[104] = false;
		caught[105] = false;
		caught[106] = false;
		caught[107] = false;
		caught[108] = false;
		caught[109] = false;
		caught[110] = false;
		caught[111] = false;
		caught[112] = false;
		caught[113] = false;
		caught[114] = false;
		caught[115] = false;
		caught[116] = false;
		caught[117] = false;
		caught[118] = false;
		caught[119] = false;
		caught[120] = false;
		caught[121] = false;
		caught[122] = false;
		caught[123] = false;
		caught[124] = false;
		caught[125] = false;
		caught[126] = false;
		caught[127] = false;
		caught[128] = false;
		caught[129] = false;
		caught[130] = false;
		caught[131] = false;
		caught[132] = false;
		caught[133] = false;
		caught[134] = false;
		caught[135] = false;
		caught[136] = false;
		caught[137] = false;
		caught[138] = false;
		caught[139] = false;
		caught[140] = false;
		caught[141] = false;
		caught[142] = false;
		caught[143] = false;
		caught[144] = false;
		caught[145] = false;
		caught[146] = false;
		caught[147] = false;
		caught[148] = false;
		caught[149] = false;
		caught[150] = false;
	}

	public static void seen(int index)
	{
		try
		{
			if(seen[index] == false)
			{
				seen[index] = true;
				totalSeen++;
			}
		}
		catch(Exception e){e.printStackTrace();}
	}

	public static void caught(int index)
	{
		if(index>=151)
		{
			System.out.println("MISSINGNO caught; system crash");
			System.exit(-1);
		}
		if(caught[index] == false)
		{
			caught[index] = true;
			totalCaught++;
		}
	}

	public void evolve(int index)
	{
		if(caught[index] == false)
		{
			caught[index] = true;
			totalCaught++;
		}
	}

	public static void saveFile()
	{
		System.out.println("Pokedex save started.");

		PrintWriter fout = null;

		try{
			fout = new PrintWriter(new FileWriter(fileName));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Pokedex save file not found. Making new one");
			try{
				fout = new PrintWriter(new FileWriter(new File(fileName)));
			}
			catch(IOException ignored){}
		}

		for(int i=0; i<151; i++)
			fout.println(seen[i] + "");
		for(int i=0; i<151; i++)
			fout.println(caught[i]);
		fout.println(totalSeen + "\n" + totalCaught);

		fout.close();
		JokemonDriver.saveEncryptionKey(fileName, JokemonDriver.encrypt(fileName));

		System.out.println("Pokedex save complete.");
	}

	public static void loadFile()
	{
		Scanner in = null;

		try{
			in = new Scanner(new FileReader(fileName));
		}
		catch(Exception e){System.out.println("File Doesn't Exist");}

		for(int i=0; i<151; i++)
			seen[i] = in.nextBoolean();
		for(int i=0; i<151; i++)
			caught[i] = in.nextBoolean();

		totalSeen = Integer.parseInt(in.next());
		totalCaught = Integer.parseInt(in.next());

		JokemonDriver.testEncryption(fileName);

		System.out.println("Pokedex load complete.");
	}
	
	public static String[] getAreas(int index)
	{
		if(!areasFound[index].equals(""))
		{
			String[] a = new String[areasFound[index].length()/2];

			int k = 0;
			int value=0;
			
			try
			{
				value = Integer.parseInt(areasFound[index].substring(k,k+2));
			}
			catch(Exception e)
			{
				value = Integer.parseInt(areasFound[index].substring(k));
			}
			
			for(int i=0; i<a.length; i++)
			{
				switch(value)
				{
					case 1:
						a[i] = "Route 0";
						break;
					case 2:
						a[i] = "Route 1";
						break;
					case 3:
						a[i] = "Route 2";
						break;
					case 4:
						a[i] = "Route 3";
						break;
					case 5:
						a[i] = "Route 4";
						break;
					case 6:
						a[i] = "Route 5";
						break;
					case 7:
						a[i] = "Route 6";
						break;
					case 8:
						a[i] = "Route 7";
						break;
					case 9:
						a[i] = "Route 8";
						break;
					case 10:
						a[i] = "Route 9";
						break;
					case 11:
						a[i] = "Route 10";
						break;
					case 12:
						a[i] = "Route 11";
						break;
					case 13:
						a[i] = "Route 12";
						break;
					case 14:
						a[i] = "Route 13";
						break;
					case 15:
						a[i] = "Route 14";
						break;
					case 16:
						a[i] = "Route ARRAYINDEXOUTOFBOUNDSEXCEPTION";
						break;
					case 17:
						a[i] = "Stringville";
						break;
					case 18:
						a[i] = "Args Harbor";
						break;
					case 19:
						a[i] = "Mount Java";
						break;
					case 20:
						a[i] = "Villa Del Joe";
						break;
					case 21:
						a[i] = "Streamreader Hotel";
						break;
					case 22:
						a[i] = "Recursive Hot Springs";
						break;
					case 23:
						a[i] = "Polymorph Town";
						break;
					case 24:
						a[i] = "Binary City";
						break;
					case 25:
						a[i] = "Champions Walk";
						break;
					case 26:
						a[i] = "Peach City";
						break;
					case 27:
						a[i] = "Victory Road";
						break;
					case 28:
						a[i] = "Nested Village";
						break;
					case 29:
						a[i] = "Enumville";
						break;
					case 30:
						a[i] = "Null Zone";
						break;
					case 31:
						a[i] = "Intville";
						break;
					case 32:
						a[i] = "Slipspace";
						break;
					case 33:
						a[i] = "Hexadecimal Forest";
						break;
					case 34:
						a[i] = "Class Cave";
						break;
					case 35:
						a[i] = "Diglett Cave";
						break;
					case 36:
						a[i] = "Public Cave";
						break;
					case 37:
						a[i] = "Primal Cave";
						break;
					case 38:
						a[i] = "Java Cave";
						break;
					case 39:
						a[i] = "Victory Road Cave";
						break;
				}
				k+=2;
				try
				{
					value = Integer.parseInt(areasFound[index].substring(k,k+2));
				}
				catch(NumberFormatException e)
				{
					break;
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					value = Integer.parseInt(areasFound[index].substring(k));
				}
				catch(StringIndexOutOfBoundsException e)
				{
					try
					{
						value = Integer.parseInt(areasFound[index].substring(k));
					}
					catch(Exception ex){break;}
				}
			}
			return a;
		}
		else
		{
			String[] str = {"Unknown"};
			return str;
		}
	}
	
	//Creates a proper string out of a given species
	public static String speciesToString(Pokemon.Species s)
	{
		String str=""+s;
		String firstLetter=str.substring(0,1);
		str=firstLetter.toUpperCase()+str.substring(1).toLowerCase();
		return str;
	}

	//Returns Species of a Pokemon based on its Pokedex number
	public static Pokemon.Species getSpecies(int num)
	{
		switch(num)
		{
			case 1:
				return Pokemon.Species.BULBASAUR;
			case 2:
				return Pokemon.Species.IVYSAUR;
			case 3:
				return Pokemon.Species.VENUSAUR;
			case 4:
				return Pokemon.Species.CHARMANDER;
			case 5:
				return Pokemon.Species.CHARMELEON;
			case 6:
				return Pokemon.Species.CHARIZARD;
			case 7:
				return Pokemon.Species.SQUIRTLE;
			case 8:
				return Pokemon.Species.WARTORTLE;
			case 9:
				return Pokemon.Species.BLASTOISE;
			case 10:
				return Pokemon.Species.CATERPIE;
			case 11:
				return Pokemon.Species.METAPOD;
			case 12:
				return Pokemon.Species.BUTTERFREE;
			case 13:
				return Pokemon.Species.WEEDLE;
			case 14:
				return Pokemon.Species.KAKUNA;
			case 15:
				return Pokemon.Species.BEEDRILL;
			case 16:
				return Pokemon.Species.PIDGEY;
			case 17:
				return Pokemon.Species.PIDGEOTTO;
			case 18:
				return Pokemon.Species.PIDGEOT;
			case 19:
				return Pokemon.Species.RATTATA;
			case 20:
				return Pokemon.Species.RATICATE;
			case 21:
				return Pokemon.Species.SPEAROW;
			case 22:
				return Pokemon.Species.FEAROW;
			case 23:
				return Pokemon.Species.EKANS;
			case 24:
				return Pokemon.Species.ARBOK;
			case 25:
				return Pokemon.Species.PIKACHU;
			case 26:
				return Pokemon.Species.RAICHU;
			case 27:
				return Pokemon.Species.SANDSHREW;
			case 28:
				return Pokemon.Species.SANDSLASH;
			case 29:
				return Pokemon.Species.NIDORAN_F;
			case 30:
				return Pokemon.Species.NIDORINA;
			case 31:
				return Pokemon.Species.NIDOQUEEN;
			case 32:
				return Pokemon.Species.NIDORAN_M;
			case 33:
				return Pokemon.Species.NIDORINO;
			case 34:
				return Pokemon.Species.NIDOKING;
			case 35:
				return Pokemon.Species.CLEFAIRY;
			case 36:
				return Pokemon.Species.CLEFABLE;
			case 37:
				return Pokemon.Species.VULPIX;
			case 38:
				return Pokemon.Species.NINETALES;
			case 39:
				return Pokemon.Species.JIGGLYPUFF;
			case 40:
				return Pokemon.Species.WIGGLYTUFF;
			case 41:
				return Pokemon.Species.ZUBAT;
			case 42:
				return Pokemon.Species.GOLBAT;
			case 43:
				return Pokemon.Species.ODDISH;
			case 44:
				return Pokemon.Species.GLOOM;
			case 45:
				return Pokemon.Species.VILEPLUME;
			case 46:
				return Pokemon.Species.PARAS;
			case 47:
				return Pokemon.Species.PARASECT;
			case 48:
				return Pokemon.Species.VENONAT;
			case 49:
				return Pokemon.Species.VENOMOTH;
			case 50:
				return Pokemon.Species.DIGLETT;
			case 51:
				return Pokemon.Species.DUGTRIO;
			case 52:
				return Pokemon.Species.MEOWTH;
			case 53:
				return Pokemon.Species.PERSIAN;
			case 54:
				return Pokemon.Species.PSYDUCK;
			case 55:
				return Pokemon.Species.GOLDUCK;
			case 56:
				return Pokemon.Species.MANKEY;
			case 57:
				return Pokemon.Species.PRIMEAPE;
			case 58:
				return Pokemon.Species.GROWLITHE;
			case 59:
				return Pokemon.Species.ARCANINE;
			case 60:
				return Pokemon.Species.POLIWAG;
			case 61:
				return Pokemon.Species.POLIWHIRL;
			case 62:
				return Pokemon.Species.POLIWRATH;
			case 63:
				return Pokemon.Species.ABRA;
			case 64:
				return Pokemon.Species.KADABRA;
			case 65:
				return Pokemon.Species.ALAKAZAM;
			case 66:
				return Pokemon.Species.MACHOP;
			case 67:
				return Pokemon.Species.MACHOKE;
			case 68:
				return Pokemon.Species.MACHAMP;
			case 69:
				return Pokemon.Species.BELLSPROUT;
			case 70:
				return Pokemon.Species.WEEPINBELL;
			case 71:
				return Pokemon.Species.VICTREEBEL;
			case 72:
				return Pokemon.Species.TENTACOOL;
			case 73:
				return Pokemon.Species.TENTACRUEL;
			case 74:
				return Pokemon.Species.GEODUDE;
			case 75:
				return Pokemon.Species.GRAVELER;
			case 76:
				return Pokemon.Species.GOLEM;
			case 77:
				return Pokemon.Species.PONYTA;
			case 78:
				return Pokemon.Species.RAPIDASH;
			case 79:
				return Pokemon.Species.SLOWPOKE;
			case 80:
				return Pokemon.Species.SLOWBRO;
			case 81:
				return Pokemon.Species.MAGNEMITE;
			case 82:
				return Pokemon.Species.MAGNETON;
			case 83:
				return Pokemon.Species.FARFETCH_D;
			case 84:
				return Pokemon.Species.DODUO;
			case 85:
				return Pokemon.Species.DODRIO;
			case 86:
				return Pokemon.Species.SEEL;
			case 87:
				return Pokemon.Species.DEWGONG;
			case 88:
				return Pokemon.Species.GRIMER;
			case 89:
				return Pokemon.Species.MUK;
			case 90:
				return Pokemon.Species.SHELLDER;
			case 91:
				return Pokemon.Species.CLOYSTER;
			case 92:
				return Pokemon.Species.GASTLY;
			case 93:
				return Pokemon.Species.HAUNTER;
			case 94:
				return Pokemon.Species.GENGAR;
			case 95:
				return Pokemon.Species.ONIX;
			case 96:
				return Pokemon.Species.DROWZEE;
			case 97:
				return Pokemon.Species.HYPNO;
			case 98:
				return Pokemon.Species.KRABBY;
			case 99:
				return Pokemon.Species.KINGLER;
			case 100:
				return Pokemon.Species.VOLTORB;
			case 101:
				return Pokemon.Species.ELECTRODE;
			case 102:
				return Pokemon.Species.EXEGGCUTE;
			case 103:
				return Pokemon.Species.EXEGGUTOR;
			case 104:
				return Pokemon.Species.CUBONE;
			case 105:
				return Pokemon.Species.MAROWAK;
			case 106:
				return Pokemon.Species.HITMONLEE;
			case 107:
				return Pokemon.Species.HITMONCHAN;
			case 108:
				return Pokemon.Species.LICKITUNG;
			case 109:
				return Pokemon.Species.KOFFING;
			case 110:
				return Pokemon.Species.WEEZING;
			case 111:
				return Pokemon.Species.RHYHORN;
			case 112:
				return Pokemon.Species.RHYDON;
			case 113:
				return Pokemon.Species.CHANSEY;
			case 114:
				return Pokemon.Species.TANGELA;
			case 115:
				return Pokemon.Species.KANGASKHAN;
			case 116:
				return Pokemon.Species.HORSEA;
			case 117:
				return Pokemon.Species.SEADRA;
			case 118:
				return Pokemon.Species.GOLDEEN;
			case 119:
				return Pokemon.Species.SEAKING;
			case 120:
				return Pokemon.Species.STARYU;
			case 121:
				return Pokemon.Species.STARMIE;
			case 122:
				return Pokemon.Species.MR_MIME;
			case 123:
				return Pokemon.Species.SCYTHER;
			case 124:
				return Pokemon.Species.JYNX;
			case 125:
				return Pokemon.Species.ELECTABUZZ;
			case 126:
				return Pokemon.Species.MAGMAR;
			case 127:
				return Pokemon.Species.PINSIR;
			case 128:
				return Pokemon.Species.TAUROS;
			case 129:
				return Pokemon.Species.MAGIKARP;
			case 130:
				return Pokemon.Species.GYARADOS;
			case 131:
				return Pokemon.Species.LAPRAS;
			case 132:
				return Pokemon.Species.DITTO;
			case 133:
				return Pokemon.Species.EEVEE;
			case 134:
				return Pokemon.Species.VAPOREON;
			case 135:
				return Pokemon.Species.JOLTEON;
			case 136:
				return Pokemon.Species.FLAREON;
			case 137:
				return Pokemon.Species.PORYGON;
			case 138:
				return Pokemon.Species.OMANYTE;
			case 139:
				return Pokemon.Species.OMASTAR;
			case 140:
				return Pokemon.Species.KABUTO;
			case 141:
				return Pokemon.Species.KABUTOPS;
			case 142:
				return Pokemon.Species.AERODACTYL;
			case 143:
				return Pokemon.Species.SNORLAX;
			case 144:
				return Pokemon.Species.ARTICUNO;
			case 145:
				return Pokemon.Species.ZAPDOS;
			case 146:
				return Pokemon.Species.MOLTRES;
			case 147:
				return Pokemon.Species.DRATINI;
			case 148:
				return Pokemon.Species.DRAGONAIR;
			case 149:
				return Pokemon.Species.DRAGONITE;
			case 150:
				return Pokemon.Species.MEWTWO;
			case 151:
				return Pokemon.Species.MEW;
		}

		return Pokemon.Species.MISSINGNO;
	}
}