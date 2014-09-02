package com.marklalor.monopolysim.roll;

import java.util.Random;

public class Die
{
	private int min, max;
	private int value;
	
	public Die(int sides)
	{
		setSides(sides);
		value = min;
	}

	public Die(int min, int max)
	{
		setRange(min, max);
		value = min;
	}
	
	public void setSides(int sides)
	{
		setRange(1, sides);
	}
	
	public void setRange(int min, int max)
	{
		this.min = min;
		this.max = max;
	}
	
	public int getSides()
	{
		return max - min + 1;
	}
	
	public int getMax()
	{
		return this.max;
	}
	
	public int getMin()
	{
		return min;
	}
	
	public int roll()
	{
		value = (new Random()).nextInt(max - min + 1) + min;
		return value;
	}
	
	public int getValue()
	{
		return value;
	}
}
