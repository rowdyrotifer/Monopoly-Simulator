package com.marklalor.monopolysim.game;

import java.util.Arrays;
import java.util.List;

import com.marklalor.monopolysim.Main;
import com.marklalor.monopolysim.game.card.ChanceCard;
import com.marklalor.monopolysim.game.card.CommunityChestCard;
import com.marklalor.monopolysim.game.space.Space;
import com.marklalor.monopolysim.game.space.property.Color;
import com.marklalor.monopolysim.game.space.property.Property;
import com.marklalor.monopolysim.game.space.special.Chance;
import com.marklalor.monopolysim.game.space.special.CommunityChest;
import com.marklalor.monopolysim.game.space.special.FreeParking;
import com.marklalor.monopolysim.game.space.special.Go;
import com.marklalor.monopolysim.game.space.special.GoToJail;
import com.marklalor.monopolysim.game.space.special.IncomeTax;
import com.marklalor.monopolysim.game.space.special.Jail;
import com.marklalor.monopolysim.game.space.special.LuxuryTax;
import com.marklalor.monopolysim.game.space.special.Railroad;
import com.marklalor.monopolysim.game.space.special.Utility;

/**
 * Manages the 40 spaces of a Monopoly board as well as the number of times they've
 * been landed on by the simulation.
 * @author Mark Lalor
 */
public class Board
{
	public static final List<String> SPECIAL = Arrays.asList(new String[] { "FreeParking", "Go", "GoToJail", "IncomeTax", "Jail", "LuxuryTax", "Railroad", "Utility" } );

	private static final String PACKAGE = "com.marklalor.monopolysim.game.space.";
	
	/**
	 * Array of String[] containing data of the default en-us layout of monopoly.
	 * <p>
	 * For each String[], the data is in the following format:
	 * <ul>
	 * <li>0: name of the class to use (a subclass of {@link Space})</li>
	 * <li>1: displays name of the space.</li>
	 * <li>2+: additional information, for example, for Properties, 2 for color and 3 for rent price.</li>
	 * </ul>
	 */
	public static String[][] DEFAULT_LAYOUT =
		{
			// From "Go" (inclusive) To "Jail" (exclusive)
			/*0*/{"special.Go","Go"},
			/*1*/{"property.Property","Mediterranean Avenue","SADDLE_BROWN","60"},
			/*2*/{"special.CommunityChest", "Community Chest"},
			/*3*/{"property.Property","Baltic Avenue","SADDLE_BROWN","60"},
			/*4*/{"special.IncomeTax", "Income Tax"},
			/*5*/{"special.Railroad","Reading Railroad"},
			/*6*/{"property.Property","Oriental Avenue","SKY_BLUE","100"},
			/*7*/{"special.Chance", "Chance"},
			/*8*/{"property.Property","Vermont Avenue","SKY_BLUE","100"},
			/*9*/{"property.Property","Connecticut Avenue","SKY_BLUE","120"},
			// From "Jail" (inclusive) to "Free Parking" (exclusive)
			/*10*/{"special.Jail", "Jail / Just Visiting"},
			/*11*/{"property.Property","St. Charles Place","DARK_ORCHID","140"},
			/*12*/{"special.Utility","Electric Company"},
			/*13*/{"property.Property","States Avenue","DARK_ORCHID","140"},
			/*14*/{"property.Property","Virginia Avenue","DARK_ORCHID","160"},
			/*15*/{"special.Railroad","Pennsylvania Railroad"},
			/*16*/{"property.Property","St. James Place","ORANGE","180"},
			/*17*/{"special.CommunityChest", "Community Chest"},
			/*18*/{"property.Property","Tennessee Avenue","ORANGE","180"},
			/*19*/{"property.Property","New York Avenue","ORANGE","200"},
			// From "Free Parking" (inclusive) to "Go To Jail" (exclusive)
			/*20*/{"special.FreeParking", "Free Parking"},
			/*21*/{"property.Property","Kentucky Avenue","RED","220"},
			/*22*/{"special.Chance", "Chance"},
			/*23*/{"property.Property","Indiana Avenue","RED","220"},
			/*24*/{"property.Property","Illinois Avenue","RED","240"},
			/*25*/{"special.Railroad","B&O Railroad"},
			/*26*/{"property.Property","Atlantic Avenue","YELLOW","260"},
			/*27*/{"property.Property","Ventnor Avenue","YELLOW","260"},
			/*28*/{"special.Utility","Water Works"},
			/*29*/{"property.Property","Marvin Gardens","YELLOW","280"},
			// From "Go To Jail" (inclusive) to "Go" (exclusive)
			/*30*/{"special.GoToJail", "Go To Jail"},
			/*31*/{"property.Property","Pacific Avenue","GREEN","300"},
			/*32*/{"property.Property","North Carolina Avenue","GREEN","300"},
			/*33*/{"special.CommunityChest", "Community Chest"},
			/*34*/{"property.Property","Pennsylvania Avenue","GREEN","320"},
			/*35*/{"special.Railroad","Short Line"},
			/*36*/{"special.Chance", "Chance"},
			/*37*/{"property.Property","Park Place","BLUE","350"},
			/*38*/{"special.LuxuryTax", "Luxury Tax"},
			/*39*/{"property.Property","Boardwalk","BLUE","400"},
		};

	/**
	 * List of spaces in numerical order.
	 */
	private Space[] spaces;
	private ChanceCard chance;
	private CommunityChestCard communityChest;
	
	/**
	 * Creates a new board object based on the <code>public static String[][] defaultLayout</code> data.
	 */
	public Board()
	{
		//Load the spaces.
		spaces = new Space[DEFAULT_LAYOUT.length];
		for (int i = 0; i < spaces.length; i++)
		{
			String[] space = DEFAULT_LAYOUT[i];
			String className = space[0];
			
			try
			{
				//Create a new instance of the specified class.
				spaces[i] = (Space) Class.forName(PACKAGE + className).newInstance();
				
				//All spaces have a position / name.
				spaces[i].setPosition(i);
				spaces[i].setName(space[1]);
				
				//Set more variables for a property space (this info is not currently used, but
				//could be used if another program referenced this one)
				if (spaces[i] instanceof Property)
				{
					Property p = (Property) spaces[i];
					p.setColor(Color.get(space[2]));
					p.setPrice(Integer.parseInt(space[3]));
					spaces[i] = p;
				}
			}
			catch(InstantiationException | IllegalAccessException e)
			{
				System.out.println("Could not instantiate/access the class! Exiting...");
				e.printStackTrace();
				System.exit(1);
			}
			catch(ClassNotFoundException e)
			{
				System.out.println("Could not find the class! Exiting...");
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		//Instantiate the ChanceCard and ComminutyChestCard class instances.
		this.chance = new ChanceCard();
		this.communityChest = new CommunityChestCard();
	}
	
	//Many utility methods for getting different Spaces.
	
	public ChanceCard getChance()
	{
		return chance;
	}
	
	public CommunityChestCard getCommunityChest()
	{
		return communityChest;
	}

	public Space getSpace(int i)
	{
		return this.spaces[i];
	}
	
	public Chance[] getChances()
	{
		Chance[] chances = new Chance[Chance.POSITIONS.length];
		for (int i = 0; i < chances.length; i++)
			chances[i] = (Chance) spaces[Chance.POSITIONS[i]];
		return chances;
	}
	
	public CommunityChest[] getCommunityChests()
	{
		CommunityChest[] communityChests = new CommunityChest[CommunityChest.POSITIONS.length];
		for (int i = 0; i < communityChests.length; i++)
			communityChests[i] = (CommunityChest) spaces[CommunityChest.POSITIONS[i]];
		return communityChests;
	}
	
	public FreeParking getFreeParking()
	{
		return (FreeParking) spaces[FreeParking.POSITION];
	}
	
	public Go getGo()
	{
		return (Go) spaces[Go.POSITION];
	}
	
	public GoToJail getGoToJail()
	{
		return (GoToJail) spaces[GoToJail.POSITION];
	}
	
	public IncomeTax getIncomeTax()
	{
		return (IncomeTax) spaces[IncomeTax.POSITION];
	}
	
	public Jail getJail()
	{
		return (Jail) spaces[Jail.POSITION];
	}
	
	public LuxuryTax getLuxuryTax()
	{
		return (LuxuryTax) spaces[LuxuryTax.POSITION];
	}
	
	public Railroad[] getRailroads()
	{
		Railroad[] railroads = new Railroad[Railroad.POSITIONS.length];
		for (int i = 0; i < railroads.length; i++)
			railroads[i] = (Railroad) spaces[Railroad.POSITIONS[i]];
		return railroads;
	}
	
	public Railroad getNearestRailroad(int position)
	{
		return (Railroad) getNearest(getRailroads(), position);
	}
	
	public Utility[] getUtilities()
	{
		Utility[] utilities = new Utility[Utility.POSITIONS.length];
		for (int i = 0; i < utilities.length; i++)
			utilities[i] = (Utility) spaces[Utility.POSITIONS[i]];
		return utilities;
	}
	
	public Utility getNearestUtility(int position)
	{
		return (Utility) getNearest(getUtilities(), position);
	}
	
	public Space getNearest(Space[] spaces, int position)
	{
		Space startingSpace = getSpace(position);
		Space nearest = null;
		for (Space space : spaces)
		{
			if (nearest == null)
				nearest = space;
			else
			{
				if (startingSpace.distanceTo(space) < startingSpace.distanceTo(nearest))
					nearest = space;
			}
			
			if (Main.DEBUG) System.out.println(space.getName() + " is " + startingSpace.distanceTo(space) + " spaces from position " + position + ".");
		}
		return nearest;
	}
	
	public Property getIllinoisAvenue()
	{
		return (Property) spaces[24];
	}
	
	public Property getStCharlesPlace()
	{
		return (Property) spaces[11];
	}
	
	public Railroad getReadingRailroad()
	{
		return (Railroad) spaces[5];
	}
	
	public Property getBoardwalk()
	{
		return (Property) spaces[39];
	}
}