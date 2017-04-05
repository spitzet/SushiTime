package a4;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/*
 * The Sushibar acts as a boundary for all objects except Catcher objects, and so its width
 * and length are used in many other GameObjects. For purposes of making changes to the width
 * and length without altering all other classes, Sushibar declares a constant WIDTH and LENGTH
 * that can easily be changed. It's actual x y location is in the middle of it's width and length.
 * As explained in Game, it can now draw itself and handle collisions.
 */
public class SushiBar extends Bar implements ICollider
{
	public final static int WIDTH = 100;
	public final static int LENGTH = 200;
	private AffineTransform rotation, translation, scaler;
	
	public SushiBar()
	{
		setColor(Color.black);
		rotation = new AffineTransform();
		translation = new AffineTransform();
		scaler = new AffineTransform();
		
		setX(WIDTH/2 + WIDTH%2);
		setY(LENGTH/2 + LENGTH%2);
		translate(getX(), getY());
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
	
	public void draw(Graphics2D g2d)
	{
		AffineTransform saveAT = g2d.getTransform();
		g2d.transform(translation);
		g2d.setColor(getColor());
		g2d.drawRect(-WIDTH/2, -LENGTH/2, WIDTH, LENGTH);
		g2d.setTransform(saveAT);
	}
	
	public boolean collidesWith(ICollider otherObject)
	{
		boolean result = false;
		/*
		if(otherObject instanceof Net)
		{
			Net other = (Net)otherObject;
			double r1, l1, t1, b1, r2, l2, t2, b2;
			r1 = WIDTH;
			l1 = 0;
			t1 = 0;
			b1 = LENGTH;
			r2 = other.getX() + other.getSize()/2;
			l2 = other.getX() - other.getSize()/2;
			t2 = other.getY() + other.getSize()/2;
			b2 = other.getY() - other.getSize()/2;
			
			if(!( (r1<l2 || l1>r2) && (t2<b1 || t1<b2) )) result = true;
		}
		*/
		return result;
	}
	
	public boolean handleCollision(ICollider otherObject)
	{
		boolean remove = false;
		return remove;
	}
}
