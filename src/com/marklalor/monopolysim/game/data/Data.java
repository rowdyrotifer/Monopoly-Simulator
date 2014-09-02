package com.marklalor.monopolysim.game.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.marklalor.monopolysim.Main;
import com.marklalor.monopolysim.game.Game;


/**
 * Utility for outputting lines of CSV data to the data file specified by the user. 
 * @author Mark Lalor
 * TODO: Do not use Main.DATA_FILE, have that File be passed through the constructor instead.
 */
public class Data
{
	private String lastLine = "";
	private Game game;
	private BufferedWriter dataOut = null;
	private int dataCount = 0;
	
	/**
	 * Links the given {@link Game} and opens the data output writer.
	 * <p>
	 * The first line is written with the proper headings for the CSV data.
	 * @param game The game to draw the data from.
	 */
	public Data(Game game)
	{
		if (Main.DATA_FILE == null)
			return;
		this.game = game;
	    
	    try
		{
			dataOut = new BufferedWriter(new FileWriter(Main.DATA_FILE));
			dataOut.write("Rolls,Moves,Position,Doubles,Go Passes,Distance Moved,Die1,Die2,In Jail?,");
			for (int i = 0; i < 40; i++)
				dataOut.write(i + " (" + game.getBoard().getSpace(i).getName() + ")" + (i!=39?",":""));
			dataOut.write(System.lineSeparator());
		}
	    catch(IOException e)
		{
	    	System.out.println("Could not open data output file!");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Draws CSV data from the given {@link Game} and writes a line of it to end of the data output writer.
	 */
	public void writeLine()
	{
		if (Main.DATA_FILE == null)
			return;
		//Save a line of CSV data.
		dataCount ++;
		if (dataCount == Main.DATA_INTERVAL)
    	{
    		dataCount = 0;
    		try
			{
    			lastLine = game.generateData();
    			String val = lastLine + System.lineSeparator();
				dataOut.write(val);
				if (Main.DEBUGCSV) System.out.print(val);
			    dataOut.flush();
			}
    		catch(IOException e)
			{
    			System.out.println("Failed to write to file!");
				e.printStackTrace();
				System.exit(1);
			}
    	}
	}

	/**
	 * Closes the data output writer.
	 */
	public void close()
	{
		if (Main.DATA_FILE == null)
			return;
		try
		{
			dataOut.close();
		}
	    catch(IOException e)
		{
			System.out.println("Could not close the data output stream!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the {@link BufferedWriter} being used to output CSV data.
	 * @return a {@link BufferedWriter}.
	 */
	public BufferedWriter getDataOut()
	{
		return dataOut;
	}
	
	/**
	 * Gets the last line that was written to the {@link BufferedWriter}.
	 * @return The most recently written line.
	 */
	public String getLastLine()
	{
		return lastLine;
	}
}
