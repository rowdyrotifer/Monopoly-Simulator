package com.marklalor.monopolysim.game.space.special;

import com.marklalor.monopolysim.game.space.Space;

/**
 * Space class that also contains a flag denoting whether the player is in jail or not.
 * @author Mark Lalor
 */
public class Jail extends Space
{
	public static final int POSITION = 10;
	
	private boolean inJail = false;
	
	public boolean isInJail()
	{
		return this.inJail;
	}
	
	public void putInJail()
	{
		this.inJail = true;
	}
	
	public void freeFromJail()
	{
		this.inJail = false;
	}
}
