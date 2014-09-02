package com.marklalor.monopolysim.game.display;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.marklalor.monopolysim.game.Game;

/**
 * Contains a {@link BufferedImage} and {@link Graphics2D} object to generate and save a (low or high quality)
 * image from the data of the given {@link Game}
 * @author Mark Lalor
 */
public abstract class Display
{
	/**
	 * Constant derived from the {@link BufferedImage#getWidth()}/{@link BufferedImage#getHeight()}
	 * methods of the {@link BufferedImage} instance returned by the abstract {@link #createImage()}.  
	 */
	public static int WIDTH, HEIGHT;
	
	
	/** Whether the subclass is the high or low quality version. */
	protected boolean hiQ;
	/** For saving the Graphics2D data. */
	protected BufferedImage image;
	/** For drawing the data contained within the Game instance. */
	protected Graphics2D graphics;
	/** For providing the data needed to draw the image. */
	protected Game game;
	
	/**
	 * Creates a new Display with the image provided by {@link #createImage()} and
	 * sets the <code>WIDTH</code> and <code>HEIGHT</code> constants based on the {@link BufferedImage}
	 * returned.
	 */
	public Display()
	{
		image = createImage();
		WIDTH = image.getWidth();
		HEIGHT = image.getHeight();
	}
	
	public Graphics2D getGraphics()
	{
		return graphics;
	}
	
	public boolean isHighQuality()
	{
		return hiQ;
	}
	
	public boolean isLowQuality()
	{
		return !hiQ;
	}
	
	/**
	 * Draws the <code>Graphics</code> instance onto the <code>BufferedImage</code> instance 
	 * and then saves it to the given <code>File</code>
	 * @param file The file to save the generated image onto.
	 * @return <code>true</code> if the file saved successfully, <code>false</code> otherwise.
	 */
	public boolean save(File file)
	{
		try
		{
			//Draw the graphics into the BufferedImage.
			graphics.drawImage(image, null, 0, 0);
			//Save the BufferedImage into the file.
			//image.flush(); TODO: try flushing.
			ImageIO.write(image, "png", file);
		}
		catch(IOException e)
		{
			System.out.println("Could not write to the specified file!");
			e.printStackTrace();
			return false;
		}
		finally
		{
			//Make sure the graphics is always disposed of.
			graphics.dispose();
		}
		
		return true;
	}
	
	//Abstract methods.
	/**
	 * Generates a new {@link BufferedImage} for this <code>Display</code>.
	 * @return The {@link BufferedImage} to represent this <code>Display</code>.
	 */
	protected abstract BufferedImage createImage();
	/**
	 * Draws the current {@link Game} data onto the <code>Graphics2D graphics</code>.
	 */
	public abstract void draw();
	
	/**
	 * Draws the data on the display from the data in the given {@link Game}
	 * @param game {@link Game} instance with the data to render.
	 */
	public void draw(Game game)
	{
		this.game = game;
		this.graphics = image.createGraphics();
		this.draw();
	}
}
