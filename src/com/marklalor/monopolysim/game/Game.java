package com.marklalor.monopolysim.game;

import com.marklalor.monopolysim.Main;
import com.marklalor.monopolysim.game.card.CardAction;
import com.marklalor.monopolysim.game.data.Data;
import com.marklalor.monopolysim.game.display.Display;
import com.marklalor.monopolysim.game.display.DisplayHQ;
import com.marklalor.monopolysim.game.display.DisplayLQ;
import com.marklalor.monopolysim.game.space.Space;
import com.marklalor.monopolysim.game.space.special.Chance;
import com.marklalor.monopolysim.game.space.special.CommunityChest;
import com.marklalor.monopolysim.game.space.special.GoToJail;
import com.marklalor.monopolysim.game.space.special.Jail;
import com.marklalor.monopolysim.game.space.special.Railroad;
import com.marklalor.monopolysim.game.space.special.Utility;
import com.marklalor.monopolysim.roll.Roll;

/**
 * Manages the simulation of a monopoly game and the writing of CSV data to a specified file.
 * <p>
 * Uses an instance of the {@link Board} class to manage the number of times each space has been landed on.
 * <br/>
 * Uses an instance of the {@link Data} class to manage the saving of the CSV data to a file.
 * @author Mark Lalor
 */
public class Game
{
	/** All rolls. Those following up a doubles-turn are included. */
	private int rolls = 0;
	/** All moves. Doubles-turns count as one move. */
	private int moves = 0;
	/** Current position on the board, ranging from 0 to 39 ("Go" to "Boardwalk") */
	private int position = 0;
	/** The total number of doubles that have been rolled. */
	private int doubles = 0;
	/** The number of spaces that have been moved. */
	private int distanceMoved = 0;
	/** The number of times go has been passed. */
	private int goPasses = 0;
	/** The current number of doubles that have been rolled in a row (3 doubles in a row constitutes a trip to jail!) */
	private int doublesInARow = 0;
	/** The current number of turns in a row that the player has been in jail. */
	private int jailsInARow = 0;
	
	/** Game board space data wrapper. */
	private Board board;
	/** Game board image display instance. */
	private Display display;
	/** Game board CSV save instance. */
	private Data data;
	/** Roll logic instance. */
	private Roll roll;
	
	public Game()
	{
		this.board = new Board();
		this.display = Main.HIGH_QUALITY?new DisplayHQ():new DisplayLQ();
		this.data = new Data(this);
		this.roll = new Roll();
	}
	
	/**
	 * Executed repeatedly by the main method to simulate however many
	 * turns specified by the user. Main logic flow method.
	 */
	public void turn()
	{
		if (Main.DEBUG && board.getJail().isInJail()) System.out.println("In jail!");
		//Every time a roll of the dice occurs,
		addRoll();
		addMove();
		roll.roll();
		
		//Special case:
		boolean rolledDoublesToGetOutOfJail = false;
		
		//Checks for doubles, records them, and removes the player from jail if they are in jail.
		if (roll.isDouble())
		{
			doubles++;
			doublesInARow++;
			if (board.getJail().isInJail())
			{
				//The actual movement for this rolled double will occur below (where "if (!board.getJail().isInJail())" is).
				unjail();
				rolledDoublesToGetOutOfJail = true;
			}
		}
		//Manage non-doubles rolls.
		else
		{
			doublesInARow = 0;
			if (board.getJail().isInJail())
			{
				jailsInARow++;
			
				//Force the pay-your-way out of jail.
				if (jailsInARow == 3)
					unjail();
				else
					getBoard().getJail().addHit();
			}
		}
		
		//Move the player if they are not in jail.
		if (!board.getJail().isInJail())
		{
			if (doublesInARow < 3)
			{
				move(roll.getValue());
				//doubles moves recurse into more turns while subtracting the "move" that was incremented (because it was not actually completed).
				if (roll.isDouble() && !rolledDoublesToGetOutOfJail)
				{
					moves--;
					data.writeLine();
					turn();
					return;
				}
			}
			else
			{
				if (Main.DEBUG) System.out.println("3 DOUBLES!");
				board.getJail().addHit();
				jail();
			}
		}
		
		data.writeLine();
	}
	
	public void move(int distance)
	{
		Space lastSpace = board.getSpace(getPosition());
		changePosition(distance);
		Space landingSpace = board.getSpace(getPosition());
		
		if (Main.DEBUG) System.out.println("Moved from \"" + lastSpace.getName() + "\" to \"" + landingSpace.getName() + "\".");
		
		CardAction action = null;
		if (landingSpace instanceof Chance)
			action = board.getChance().getCardAction();
		else if (landingSpace instanceof CommunityChest)
			action = board.getCommunityChest().getCardAction();
		
		//Not a chance or community chest.
		if (action == null)
		{
			if (landingSpace instanceof GoToJail)
				jail();
			//Gets CURRENT space in case they were sent to jail.
			getCurrentSpace().addHit();
		}
		//Chance or community chest with a special action to take.
		else if (action != CardAction.DO_NOTHING)
			action.use(this);
		//Message for non-movement action cards. {}'s are important here...
		else
		{
			if (Main.DEBUG) System.out.println("Insignificant " + (landingSpace instanceof Chance ? "chance":"community chest") + " card.");
			landingSpace.addHit();
		}
	}
	
	/**
	 * Flips the <code>inJail</code> flag in the jail class to <code>true</code>, as well
	 * as moves to the space of the jail (10).
	 */
	public void jail()
	{
		if (Main.DEBUG) System.out.println("JAILED!");
		//sorry :(
		board.getJail().putInJail();
		doublesInARow = 0;
		//Can't use regular methods because we cannot pass go, etc.
		this.position = Jail.POSITION;
	}
	
	/**
	 * Flips the <code>inJail</code> flag in the jail class to <code>false</code>.
	 */
	public void unjail()
	{
		if (Main.DEBUG) System.out.println("FREED FROM JAIL!");
		board.getJail().freeFromJail();
		jailsInARow = 0;
	}
	
	public int getRolls()
	{
		return rolls;
	}
	
	public void setRolls(int rolls)
	{
		this.rolls = rolls;
	}
	
	public void addRoll()
	{
		this.rolls++;
	}
	
	public int getMoves()
	{
		return moves;
	}
	
	public void setMoves(int moves)
	{
		this.moves = moves;
	}
	
	public void addMove()
	{
		this.moves++;
	}
	
	public int getPosition()
	{
		return position;
	}
	
	public void setPosition(int position)
	{
		this.position = position;
	}
	
	public void changePosition(int amount)
	{
		distanceMoved += Math.abs(amount);
		this.position += amount;
		
		//If "Go" is reached.
		if (this.position >= 40)
		{
			this.position -= 40;
			this.goPasses++;
		}
		//Not really needed, as there are no chance cards within 3 spaces of "Go".
		else if (this.position < 0)
			this.position += 40;
	}
	
	public int getDoubles()
	{
		return doubles;
	}
	
	public void setDoubles(int doubles)
	{
		this.doubles = doubles;
	}
	
	public void addDouble()
	{
		this.doubles++;
	}
	
	public int getDistanceMoved()
	{
		return distanceMoved;
	}
	
	public void setDistanceMoved(int distanceMoved)
	{
		this.distanceMoved = distanceMoved;
	}
	
	public void addDistanceMoved(int distanceMoved)
	{
		this.distanceMoved += distanceMoved;
	}
	
	public int getGoPasses()
	{
		return goPasses;
	}
	
	public void setGoPasses(int goPasses)
	{
		this.goPasses = goPasses;
	}
	
	public void passGo()
	{
		this.goPasses++;
	}
	
	public int getDoublesInARow()
	{
		return doublesInARow;
	}
	
	public void setDoublesInARow(int doublesInARow)
	{
		this.doublesInARow = doublesInARow;
	}
	
	public void doubleInARow()
	{
		this.doublesInARow++;
	}
	
	public Board getBoard()
	{
		return board;
	}
	
	public void setBoard(Board board)
	{
		this.board = board;
	}
	
	public Display getDisplay()
	{
		return display;
	}
	
	public void setDisplay(Display display)
	{
		this.display = display;
	}
	
	public Data getData()
	{
		return data;
	}
	
	public void setData(Data data)
	{
		this.data = data;
	}

	public Space getCurrentSpace()
	{
		return board.getSpace(getPosition());
	}

	public void advanceTo(int position)
	{
		move(this.getCurrentSpace().distanceTo(position));
	}
	
	public void advanceTo(Space space)
	{
		advanceTo(space.getPosition());
	}

	public Space getNearest(Space[] spaces)
	{
		return board.getNearest(spaces, getPosition());
	}
	
	public Utility getNearestUtility()
	{
		return board.getNearestUtility(getPosition());
	}
	
	public Railroad getNearestRailroad()
	{
		return board.getNearestRailroad(getPosition());
	}

	//Statistics.
	//CSV format/example.
	//-------------------
	//[rolls],[moves],[position],[doubles],[distanceMoved],[goPasses],[doublesInARow],[HitsOnSpace0],[HitsOnSpace1],[HitsOnSpace2], ... [HitsOnSpace39]
	//5,5,8,0,58,1,0,0,0,0,1,0,2,0,0,0,1,0,1,0,1,......0,0,0,1,0.
	public String generateData()
	{
		final int specials = 9;
		
		Object[] items = new Object[40 + specials];
		items[0] = rolls;
		items[1] = moves;
		items[2] = position;
		items[3] = doubles;
		items[4] = goPasses;
		items[5] = distanceMoved;
		items[6] = roll.getDie1().getValue();
		items[7] = roll.getDie2().getValue();
		items[8] = getBoard().getJail().isInJail();
		
		for (int i = specials; i < 40 + specials; i++)
			items[i] = board.getSpace(i - specials).getHits();
		
		return commaSeparate(items);
	}
	
	
	private String commaSeparate(Object[] items)
	{
		StringBuffer b = new StringBuffer();
		for (Object item : items)
		{
			b.append(item);
			b.append(',');
		}
		b.substring(0, b.length() - 1);
		return b.toString();
	}
}
