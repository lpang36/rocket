/*Lawrence Pang
Nov 23 2014
ICS203
Rocket program*/

//Add the directory of JLayer.jar to preferences for the audio to work

// The "PL_Rocket" class.
import java.awt.*;
import hsa.Console;
import javax.imageio.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.text.*;
import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class PL_Rocket
{
    static Console c;           // The output console

    //Loads image from file
    public static Image loadImage (String name)
    {
	Image img = null;
	try
	{
	    img = ImageIO.read (new File (name));
	}
	catch (IOException e)
	{
	}
	return img;
    }


    //For ascending sequence, calculates y-coordinates such that the ship accelerates
    public static int coordinatesUp (int base, int i)
    {
	int y = (int) (base - Math.pow (1.5, i));
	return y;
    }


    //For descending sequence, calculates pause time such that the ship decelerates
    public static int timeDown (int i)
    {
	int y = (int) (Math.pow (1.2, i));
	return y;
    }


    public static void main (String[] args)
    {
	c = new Console ();
	//Load BufferedImage and AudioPlayer
	BufferedImage image = new BufferedImage (c.getWidth (), c.getHeight (), BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2d = image.createGraphics ();
	//Plays music
	AudioPlayer var = new AudioPlayer ("Darude - Sandstorm.mp3");
	var.loop ();

	//Opening screen
	c.setColor (Color.white);
	Font f = new Font ("Courier", Font.BOLD, 25); // font name, style, point size
	c.setFont (f);
	//Displays background
	Image nightsky = loadImage ("nightsky.jpg");
	g2d.drawImage (nightsky, 0, 0, null);
	Image earth = loadImage ("earth1.jpg");
	g2d.drawImage (earth, 0, 350, null);
	Image sun = loadImage ("sun1.jpg");
	g2d.drawImage (sun, -100, 0, null);
	//Displays image on console
	c.drawImage (image, 0, 0, null);
	//Displays message
	c.drawString ("WELCOME TO SPACE SHOOTER IV", 100, 150);
	c.drawString ("PRESS ANY KEY TO CONTINUE", 120, 250);
	c.getChar ();
	var.stop ();

	//Countdown
	//Plays sound
	var.load ("countdown.mp3");
	var.play ();
	for (int i = 10 ; i > 0 ; i--)
	{
	    //Displays background
	    g2d.drawImage (nightsky, 0, 0, null);
	    g2d.drawImage (earth, 0, 350, null);
	    g2d.drawImage (sun, -100, 0, null);
	    Image rocket = loadImage ("rocket.png");
	    g2d.drawImage (rocket, 300, 160, null);
	    //Displays image on console
	    c.drawImage (image, 0, 0, null);
	    //Displays numbers
	    f = new Font ("Courier", Font.BOLD, 200); // font name, style, point size
	    c.setFont (f);
	    String iString = Integer.toString (i); // converts integer to string so it works in c.drawString ()
	    c.drawString (iString, 230, 150);
	    //Delay
	    try
	    {
		Thread.currentThread ().sleep (1000);
	    }
	    catch (Exception e)
	    {
	    }
	}
	var.stop ();

	//Ascension
	//Plays sound
	var.load ("takeoff.mp3");
	var.play ();
	for (int i = 0 ; i < 22 ; i++)
	{
	    //Displays background
	    g2d.drawImage (nightsky, 0, 0, null);
	    g2d.drawImage (earth, 0, 350, null);
	    g2d.drawImage (sun, -100, 0, null);
	    //Displays rocket at some y-coordinate determined by coordinatesUp
	    Image rocket = loadImage ("rocket.png");
	    g2d.drawImage (rocket, 300, coordinatesUp (160, i), null);
	    //Displays image on console
	    c.drawImage (image, 0, 0, null);
	    //Delay
	    try
	    {
		Thread.currentThread ().sleep (2);
	    }
	    catch (Exception e)
	    {
	    }
	}
	var.stop ();

	//Journey
	Image obstacle = loadImage ("nightsky.jpg"); //this is done to avoid errors, doesn't actually change the program
	//Loops following code until user enters a character (stops when the whole loop is finished)
	do
	{
	    //Plays sound
	    var.load ("shooting.mp3");
	    var.play ();
	    for (int i = 1 ; i < 30 ; i++)
	    {
		//Displays background
		nightsky = loadImage ("nightsky.jpg");
		g2d.drawImage (nightsky, 0, 0, null);
		//Displays message
		g2d.setColor (Color.white);
		Font g = new Font ("Courier", Font.BOLD, 12); // font name, style, point size
		g2d.setFont (g);
		g2d.drawString ("Press any key to continue", 12, 12);
		//Sets image to alien ship
		if (i == 1)
		{
		    obstacle = loadImage ("alien.png");
		}
		//Displays alien ship descending
		else if (i <= 20 && i != 1)
		{
		    g2d.drawImage (obstacle, 240, 5 * (i), null);
		}
		//Displays explosion and message
		else if (i == 21 || i == 22 || i == 23)
		{
		    Image explosion = loadImage ("explosion.png");
		    g2d.drawImage (explosion, 230, 150, null);
		    Font h = new Font ("Courier", Font.BOLD, 80); // font name, style, point size
		    g2d.setFont (h);
		    g2d.drawString ("VICTORY", 150, 100);
		    //Plays sound
		    var.load ("explosion.mp3");
		    var.play ();
		    if (i == 23)
		    {
			var.stop ();
		    }
		}
		//Displays rocket
		int k = (int) (Math.random () * 30);
		Image rocket = loadImage ("rocket.png");
		g2d.drawImage (rocket, 170 + k, 300, null);
		//Displays shots
		if (i % 2 == 0)
		{
		    g2d.setColor (Color.white);
		    for (int l = 0 ; l <= 8 ; l++)
		    {
			g2d.fillRect (330 + k, 40 * l, 5, 20);
		    }
		}
		//Displays health bar
		if (i <= 20)
		{
		    g2d.setColor (Color.green);
		    g2d.fillRect (50, 50, 100, 5);
		    for (int m = 0 ; m < (i / 2) ; m++)
		    {
			g2d.setColor (Color.red);
			g2d.fillRect (50 + 10 * m, 50, 10, 5);
		    }
		}
		//Displays image on console
		c.drawImage (image, 0, 0, null);
		//Delay
		try
		{
		    Thread.currentThread ().sleep (10);
		}
		catch (Exception e)
		{
		}
	    }
	}
	while (c.isCharAvail () == false);
	var.stop ();

	//Descension
	//Plays sound
	var.load ("shutdown.mp3");
	var.play ();
	for (int i = 0 ; i < 22 ; i++)
	{
	    //Displays background
	    g2d.drawImage (nightsky, 0, 0, null);
	    g2d.drawImage (earth, 0, 350, null);
	    g2d.drawImage (sun, -100, 0, null);
	    //Displays rocket descending
	    Image rocket = loadImage ("rocket.png");
	    g2d.drawImage (rocket, 300, 10 * i, null);
	    //Displays image on console
	    c.drawImage (image, 0, 0, null);
	    //Delay
	    try
	    {
		Thread.currentThread ().sleep (timeDown (i));
	    }
	    catch (Exception e)
	    {
	    }
	}
	var.stop ();

	//Final message
	//Plays sound
	var.load ("Darude - Sandstorm.mp3");
	var.play ();
	//Displays message
	c.setColor (Color.white);
	Font i = new Font ("Courier", Font.BOLD, 80); // font name, style, point size
	c.setFont (i);
	c.drawString ("GAME OVER!", 100, 250);
	i = new Font ("Courier", Font.BOLD, 12); // font name, style, point size
	c.setFont (i);
	c.drawString ("Press enter to continue", 12, 24);
	c.readChar (); // it's c.readChar because a character was already entered during the journey, so it's still here and only needs to be entered
	var.stop ();

	//Termination message
	c.clear ();
	c.println ("The program has been terminated.");
    } // main method
} // PL_Rocket class

//Audio
class AudioPlayer implements Runnable
{
    private Player player;
    private Thread thread;
    private boolean loop = false;
    private String fileName;
    public AudioPlayer (String s)
    {
	load (s);
    }


    public void load (String s)
    {
	fileName = s;
	try
	{
	    player = new Player (new FileInputStream (s));
	    thread = new Thread (this);
	}
	catch (Exception e)
	{
	    e.printStackTrace ();
	}
    }


    public void play ()
    {
	if (thread != null)
	    thread = null;
	if (thread == null)
	    load (fileName);
	thread.start ();
    }


    public void loop ()
    {
	loop = true;
	play ();
    }


    public void stop ()
    {
	loop = false;
	thread = null;
	player.close ();
    }


    public void run ()
    {
	do
	{
	    try
	    {
		player.play ();
	    }
	    catch (Exception e)
	    {
		e.printStackTrace ();
	    }
	    if (loop)
		load (fileName);
	}
	while (loop);
    }
}
