package com.marklalor.monopolysim.game.space;

import com.marklalor.monopolysim.game.Board;

/**
 * Data class to hold information on a space's position, name, and number of times it has been landed on. 
 * @author Mark Lalor
 */
public class Space
{
	private int position = -1;
	private int hits = 0;
	private String name = "Unnamed";
	
	/**
	 * Gets integer position of the space on the board. 
	 * @return an integer ranging from 0-39.
	 */
	public int getPosition()
	{
		return position;
	}
	
	/**
	 * Gets the name of the space.
	 * @return Name (e.g. "Boardwalk").
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets the number of times this space has been landed on.
	 * @return times the space has been landed on.
	 */
	public int getHits()
	{
		return hits;
	}
	
	
	/**
	 * Sets the position of the space.
	 * @param position an integer ranging from 0-39.
	 */
	public void setPosition(int position)
	{
		this.position = position;
	}
	
	/**
	 * Sets the name of the space.
	 * @param name name of the space.
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Sets the number of times the space has been landed on.
	 * @param hits number of times the space has been landed on.
	 */
	public void setHits(int hits)
	{
		this.hits = hits;
	}
	
	/**
	 * Increments the number of times this space has been landed on by 1.
	 */
	public void addHit()
	{
		this.hits++;
	}
	
	/**
	 * Calculates how many spaces forward the space is to the given position.
	 * <p>
	 * Note that this ALWAYS goes forward. The only time you may move backwards in
	 * monopoly is the "Move back 3 spaces" chance card.
	 * @param otherPosition The position to compare this space's position to.
	 * @return Spaces ahead this position is.
	 */
	public int distanceTo(int otherPosition)
	{
		if (otherPosition < position)
			otherPosition += Board.DEFAULT_LAYOUT.length;
		
		return otherPosition - position;
	}
	
	/**
	 * Calculates how many spaces forward the space is to the given space.
	 * <p>
	 * Note that this ALWAYS goes forward. The only time you may move backwards in
	 * monopoly is the "Move back 3 spaces" chance card.
	 * @param otherSpace The space to compare this space's position to.
	 * @return Spaces ahead this space is.
	 */
	public int distanceTo(Space otherSpace)
	{
		return distanceTo(otherSpace.getPosition());
	}
}
