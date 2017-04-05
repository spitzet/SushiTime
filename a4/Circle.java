package a4;

/*
 * This new class as of version 4.0 is a piece of the shark's body, as explained in Game.
 * It is rotated, scaled, and translated to the appropriate position on the shark's
 * body in the shark Class.
 */

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Circle {

	int size;
	private AffineTransform rotation, translation, scaler;

	public Circle(int size)
	{
		rotation = new AffineTransform();
		translation = new AffineTransform();
		scaler = new AffineTransform();
		this.size = size;
	}
	
	public void draw(Graphics2D g2d)
	{
		AffineTransform saveAT = g2d.getTransform();
		g2d.transform(translation);
		g2d.transform(rotation);
		g2d.transform(scaler);
		
		g2d.drawOval(-size/2, -size/2, size, size);
		
		g2d.setTransform(saveAT);
	}
	
	public void rotate(double degrees){	rotation.rotate(Math.toRadians(-degrees));	}
	public void scale(double sx, double sy){ scaler.scale(sx, sy); }
	public void translate(double dx, double dy){ translation.translate(dx, dy); }
	public void resetTransforms()
	{
		rotation.setToIdentity();
		scaler.setToIdentity();
		translation.setToIdentity();
	}
}
