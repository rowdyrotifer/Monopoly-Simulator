package com.marklalor.monopolysim.game.space.property;

import com.marklalor.monopolysim.game.space.Space;


/**
 * Space that also includes a color and a price.
 * @author Mark Lalor
 */
public class Property extends Space
{
	private java.awt.Color color;
	private int price;
	
	public java.awt.Color getColor()
	{
		return color;
	}
	
	public int getPrice()
	{
		return price;
	}
	
	public void setColor(java.awt.Color color)
	{
		this.color = color;
	}
	
	public void setPrice(int price)
	{
		this.price = price;
	}
}