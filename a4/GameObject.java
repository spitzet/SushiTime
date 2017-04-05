package a4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.util.Random;

/*
 * The abstract GameObject class defines all data fields, setters, and getters that are common to
 * every single subclass of GameObject. All GameObjects must have an x and y location, a Color
 * (as defined by the built-in Color class), and a size. Also, a random number generator is created
 * using the built-in Random class, so that GameObjects can more easily determine relevant random
 * numbers. For example, a random number from 0 to the game world's size can be generated, and
 * random numbers can easily be generated for a GameObject to be instantiated into theWorld vector
 * based on its initial size so that it is not created out of bounds. The toString() method has
 * been replaced with the map view, but can still be used for debugging purposes.
 */
public abstract class GameObject implements IDrawable, ITransformable
{
	private double x, y;
	private Color color;
	private int size;
	private Random rng = new Random();
	
	public double getX() { return x; }
	public double getY() { return y; }
	public Color getColor() { return color; }
	public int getSize() { return size; }
	public int getXCorner() { return (int)x - size/2; }
	public int getYCorner() { return (int)y - size/2; }
	
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	public void setColor(Color color) { this.color = color; }
	public void setSize(int size) { this.size = size; }
	
	public int rng() { return (rng.nextInt(GameWorld.SIZE)); }
	public int rng(int max) { return (rng.nextInt(max)); }
	public int rng(int min, int max) { return min + rng.nextInt(max-min); }	
	
	public int rngBoundary() { return (1 + getSize()/2 + rng.nextInt(GameWorld.SIZE - getSize() - 2)); }
//	public int rngBoundary(int max) { return (1 + getSize()/2 + rng.nextInt(max - getSize() - 1)); }
	public int rngBoundary(int min, int max) { return 1 + min + getSize()/2 + rng.nextInt(max - min - getSize() - 2); }

	
	public String toString()
	{
		String name = this.getClass().getName().substring(3);
		double x = getX();
		double y = getY();
		int r = getColor().getRed();
		int g = getColor().getGreen();
		int b = getColor().getBlue();
		String desc = String.format("%s: loc=%.1f,%.1f color=[%d,%d,%d]", name, x, y, r, g, b);
		
		return desc;
	}
	/*
	public void rotate(double radians){	at.rotate(radians);	}
	public void scale(double sx, double sy){ at.scale(sx, sy); }
	public void translate(double dx, double dy){ at.translate(dx, dy); }
	*/
}
