package com.marklalor.monopolysim;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JFileChooser;

import com.marklalor.monopolysim.game.Game;

public class Main
{
	public static boolean WAIT_FOR_ENTER = false;
	public static boolean DEBUG = false;
	public static boolean DEBUGCSV = true;
	
	/** Rolls of the dice to simulate. */
	public static int ROLLS;
	/** File to save simulation CSV data to. */
	public static File DATA_FILE;
	/** Saves data only every <code>DATA_INTERVAL</code> roll(s). */
	public static int DATA_INTERVAL;
	/** Folder to save simulation image data to. */
	public static File IMAGE_FILE;
	/** Saves image data only every <code>IMAGE_INTERVAL</code> roll(s). */
	public static int IMAGE_INTERVAL;
	/** True if the high quality display is being used. */
	public static boolean HIGH_QUALITY;
	/** True if a <code>JFrame</code> should be used to display the <code>BufferedImage<code> in real time. */
	public static boolean REAL_TIME;
	/** True if the console should be cleared constantly */
	public static boolean CLEAR;
	
	/** Default values for the seven above constants. */
	private static final String[] defaults = {"1000000","/Library/Application Support/Monopoly Simulator/data","10000","/Library/Application Support/Monopoly Simulator/images/","10","true","true","false"};
	
	/** @return <code>true</code> if <code>String val</code> equals (ignoring case) "default" or "def". */
	private static boolean isDef(String val)
	{
		return val.equalsIgnoreCase("default") || val.equalsIgnoreCase("def");
	}
	
	/** @return <code>true</code> if <code>String val</code> equals (ignoring case) "true", "yes", or "high". */
	private static boolean bool(String val)
	{
		return val.equalsIgnoreCase("true") || val.equalsIgnoreCase("yes") ||val.equalsIgnoreCase("high"); 
	}
	
	/** Simple implode, PHP-style **/
	public static String implode(String[] val)
	{
	    String ret = "";
	    for(int i=0;i<val.length;i++)
	        ret += (i != val.length - 1) ? val[i] + ',' : val[i];
	    return ret;
	}
	
	public final static void clearConsole()
	{
		//Works well on unix shell to clear the screen
		System.out.print("\033[H\033[2J");
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss");
		Date date = new Date();
		String timestamp = "-" + dateFormat.format(date) + ".csv";
		
		//Gather initial user input:
		System.out.println("Monopoly Simulator - Mark Lalor" + System.lineSeparator() + "Enter CSV input in the form of: [rolls],[dataURL/\"null\"],[dataSaveInterval],[imageURL/\"null\"],[imageSaveInterval],[highQuality],[displayRealTime]");
	    Scanner scanner = new Scanner(System.in);
	    
	    String input = args.length == 0 ? scanner.nextLine() : implode(args);
	    
	    //Get a pre-formatted input in CSV form. 
	    if (!input.isEmpty())
	    {
	    	//Allow for "default" to be entered to use "default" for everything.
		    if (isDef(input))
		    	input = "def,def,def,def,def,def,def,def";
		    //Split the CSV input into a String[] array.
		    String[] value = input.split(",");
		    
		    //Make sure there were enough values provided.
		    if (value.length != defaults.length)
		    {
		    	System.out.print(value.length + " input values given. " + defaults.length + " expected!");
		    	System.exit(1);
		    }
		    
		    //Replace "default"s with actual defaults.
		    for (int i = 0; i < value.length; i++)
		    	if (isDef(value[i]))
		    		value[i] = defaults[i];
		    
		    //Finally, set the static constants.
		    Main.ROLLS = Integer.parseInt(value[0]);
		    Main.DATA_FILE = value[1].equalsIgnoreCase("null")?null:new File(value[1] + timestamp);
		    Main.DATA_INTERVAL = Main.DATA_FILE == null?0:Integer.parseInt(value[2]);
		    Main.IMAGE_FILE = value[3].equalsIgnoreCase("null")?null:new File(value[3] + timestamp);
		    Main.IMAGE_INTERVAL = Main.IMAGE_FILE == null?0:Integer.parseInt(value[4]);
		    Main.HIGH_QUALITY = bool(value[5]);
		    Main.REAL_TIME = bool(value[6]);
		    Main.CLEAR = bool(value[7]);
	    }
	    //If left blank, prompt the user for each value.
	    else
	    {
	    	//Rolls.
	    	System.out.println("Enter the number of rolls to simulate:");
		    Main.ROLLS = Integer.valueOf(scanner.nextLine());
		    
		    //Data file and interval.
		    System.out.println("Choose a data file.");
		    JFileChooser dataFileChooser = new JFileChooser();
		    dataFileChooser.setDialogTitle("Choose a data file.");
		    
		    //Show dialog and collect result.
		    int dataFileChooserResult = dataFileChooser.showSaveDialog(null);
		    if (dataFileChooserResult == JFileChooser.APPROVE_OPTION)
		    {
		    	Main.DATA_FILE = dataFileChooser.getSelectedFile();
		    	System.out.println("Enter data save interval:");
		    	Main.DATA_INTERVAL = Integer.valueOf(scanner.nextLine());
		    }
		    else if (dataFileChooserResult == JFileChooser.CANCEL_OPTION)
		    {
		    	Main.DATA_FILE = null;
		    	Main.DATA_INTERVAL = 0;
		    }
		    else
		    {
		    	System.out.print("Error on selecting the data file.");
		    	System.exit(1);
		    }
		    
		    //Image file and interval.
		    System.out.println("Choose an image directory.");
		    JFileChooser imageFileChooser = new JFileChooser();
		    imageFileChooser.setDialogTitle("Choose an image directory.");
		    imageFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    imageFileChooser.setAcceptAllFileFilterUsed(false);
		    
		    //Show dialog and collect result.
		    //System.out.println("testtesttest111");
		    int imageFileChooserResult = imageFileChooser.showSaveDialog(null);
		    //System.out.println("testtesttest222");
		    if (imageFileChooserResult == JFileChooser.APPROVE_OPTION)
		    {
		    	Main.IMAGE_FILE = imageFileChooser.getCurrentDirectory();
		    	System.out.println("Enter image save interval:");
		    	Main.IMAGE_INTERVAL = Integer.valueOf(scanner.nextLine());
		    }
		    else if (imageFileChooserResult == JFileChooser.CANCEL_OPTION)
		    {
		    	Main.IMAGE_FILE = null;
		    	Main.IMAGE_INTERVAL = 0;
		    }
		    else
		    {
		    	System.out.print("Error on selecting the image file.");
		    	System.exit(1);
		    }
		    
		    //Whether to use high or low quality images:
		    System.out.println("High or low quality? (true/yes/high for high quality. false/no/low for low quality)");
		    Main.HIGH_QUALITY = bool(scanner.nextLine());
		    
  		    //Whether to show the images in a JFrame.
		    System.out.println("Show images in real time? (true/yes to show. false/no to not)");
		    Main.REAL_TIME = bool(scanner.nextLine());
		    
		    //Whether to clear the console often to increase readability.
		    System.out.println("Clear console after each turn? (true/yes to clear. false/no to not)");
		    Main.CLEAR = bool(scanner.nextLine());
	    }
	    
	    //Begin.
	    Game game = new Game();
	    DecimalFormat df = new DecimalFormat("#0.000");
	    
	    for (int i = 0; i < ROLLS; i = game.getRolls())
	    {
	    	game.turn();
	    	
	    	if (Main.CLEAR)
	    		clearConsole();
	    	
	    	if (!Main.DEBUG)
	    	{
	    		System.out.println("Roll " + game.getRolls() + "/" + ROLLS + " (" + df.format((double)game.getRolls() * 100 / ROLLS) + "%)");
	    		if (Main.CLEAR)
	    			System.out.println(game.getData().getLastLine());
	    	}

	    	System.out.flush();
	    }
	    
	    game.getData().close();
	    
	    scanner.close();
	    
	    //TODO: save serialized form of the game to be able to continue calculations.
	}
}
