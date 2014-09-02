package com.marklalor.monopolysim.roll;

import com.marklalor.monopolysim.Main;

public class Roll
{
	private Die die1, die2;
	private int value;
	
	public Roll()
	{
		this.die1 = new Die(6);
		this.die2 = new Die(6);
	}
	
	public int roll()
	{	
		this.value = die1.roll() + die2.roll();
		if (Main.DEBUG) System.out.println("Roll: " + die1.getValue() + " + " + die2.getValue() + " = " + this.value + (this.isDouble()?" (double)":""));
		return this.value;
	}
	
	public Die getDie1()
	{
		return die1;
	}
	
	public Die getDie2()
	{
		return die2;
	}
	
	public boolean isDouble()
	{
		return die1.getValue() == die2.getValue();
	}
	
	public int getValue()
	{
		return value;
	}
}
