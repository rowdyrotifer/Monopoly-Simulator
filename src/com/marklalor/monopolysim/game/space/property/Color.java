package com.marklalor.monopolysim.game.space.property;

import java.awt.image.IndexColorModel;
import java.lang.reflect.Field;

/**
 * Manages the monopoly colors as well as the {@link java.awt.Color} behind them.
 * <p>
 * Also creates a {@link java.awt.image.IndexColorModel} of all the colors.
 * @author Mark Lalor
 */
public final class Color
{
	/** Total number of different property colors in Monopoly. */
	public static final int amount = 10;
	
	public static final java.awt.Color
		SADDLE_BROWN = new java.awt.Color(0x8B4513),
		SKY_BLUE = new java.awt.Color(0x7EC0EE),
		DARK_ORCHID = new java.awt.Color(0x9932CC),
		ORANGE = new java.awt.Color(0xFFA500),
		RED = new java.awt.Color(0xFF0000),
		YELLOW = new java.awt.Color(0xFFFF00),
		GREEN = new java.awt.Color(0x008000),
		BLUE = new java.awt.Color(0x0000FF),
		BLACK = new java.awt.Color(0x000000),
		WHITE = new java.awt.Color(0xFFFFFF); 
	
	/**
	 * Gets a {@link java.awt.Color} by String name.
	 * @param name name of the color to retrieve.
	 * @return <code>public static final</code> {@link java.awt.Color} of the
	 * {@link com.marklalor.monopolysim.game.space.property.Color} class matching the name.
	 */
	public static java.awt.Color get(String name)
	{
		try
		{
			Field f = Color.class.getField(name);
			return (java.awt.Color)f.get(null);
		}
		catch(NoSuchFieldException e)
		{
			System.out.println("No color " + name + " exists!");
			e.printStackTrace();
		}
		catch(SecurityException e)
		{
			e.printStackTrace();
		}
		catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 10-color model for saving low-quality PNG images using an indexed color palette. Reducing the possible colors from 256^3 (16.777216 million colors) to only 10.
	 */
	public static IndexColorModel model;
	//Creates the static IndexColorModel.
	static
	{
		//Store some RGB values.
	    byte[] reds = new byte[amount];
	    byte[] greens = new byte[amount];
	    byte[] blues = new byte[amount];
	    
	    //Colors to use.
	    java.awt.Color[] colors = new java.awt.Color[] { SADDLE_BROWN, SKY_BLUE, DARK_ORCHID, ORANGE, RED, YELLOW, GREEN, BLUE, BLACK, WHITE };

	    //Get the RGBs from java.awt.Color.
	    for (int i = 0; i < amount; i++)
	    {
	    	reds[i] = (byte) colors[i].getRed();
	    	greens[i] = (byte) colors[i].getGreen();
	    	blues[i] = (byte) colors[i].getBlue();
	    }
	    
	    //Create the color model from the RGB data.
	    model = new IndexColorModel(3, amount, reds, greens, blues);
	}
}
