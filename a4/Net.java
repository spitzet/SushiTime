package a4;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/*
 * The Net object is responsible for keeping track of scooping and changing its size.
 * The Net object cannot be used to scoop if scooping is disabled. Net is set to the
 * bottom left of the screen inside the Sushibar when it is added to theWorld collection.
 * As explained in Game, it can now draw itself and handle collisions.
 */
public class Net extends Catcher implements ICollider
{
	private boolean scooping;
	private AffineTransform rotation, translation, scaler;
	
	public Net() {
		setSize(500);
		scooping = true;
		setColor(Color.gray);
		rotation = new AffineTransform();
		translation = new AffineTransform();
		scaler = new AffineTransform();
		
		setX(getSize()/2);
		setY(getSize()/2);
		translate(getX(), getY());
		
	}
	
	/*
	 * Net has setters and getters for the state of scooping and for
	 * enabling/disabling scooping. The net's size can change from a range of 50-500
	 * but must not go out of bounds when expanding. Since Net is of apparent type
	 * IGuided as well as it's parent Catcher, it inherits the ability to move left,
	 * right, up, and down from Catcher.
	 */
	public void rotate(double degrees){	rotation.rotate(Math.toRadians(-degrees));	}
	public void scale(double sx, double sy){ scaler.scale(sx, sy); }
	public void translate(double dx, double dy){ translation.translate(dx, dy); }
	public void resetTransforms()
	{
		rotation.setToIdentity();
		scaler.setToIdentity();
		translation.setToIdentity();
	}
	
	public void right()
	{
		double oldX = getX();
		super.right();
		translate(getX()-oldX, 0);
	}
	public void left()
	{
		double oldX = getX();
		super.left();
		translate(getX()-oldX, 0);
	}
	public void up() 
	{ 
		double oldY = getY();
		super.up();
		translate(0, getY()-oldY);
	}
	public void down()
	{ 
		double oldY = getY();
		super.down();
		translate(0, getY()-oldY);
	}
	
	public boolean scooping() { return scooping; }
	public void setScooping(boolean scoop) { scooping = scoop; }
	public void contract() { if(getSize() > 50) setSize(getSize() - 50); }
	
	public void expand() {
		if(getSize() < 500 && getX() >= getSize()/2 + 25 && getY() >= getSize()/2 + 25
			&& getX() + getSize()/2 + 25 <= GameWorld.SIZE && getY() + getSize()/2 + 25 <= GameWorld.SIZE)
		{
			setSize(getSize() + 50);
		}
	}
	
	//Size specific to Net objects is added to toString()
	public String toString()
	{
		String size = " size=" + getSize();
		String desc = super.toString() + size;
		return desc;
	}
	
	public void draw(Graphics2D g2d)
	{
		AffineTransform saveAT = g2d.getTransform();
		g2d.transform(translation);
		g2d.drawRect(-getSize()/2, -getSize()/2, getSize(), getSize());
		g2d.setTransform(saveAT);
	}
	
	public boolean collidesWith(ICollider otherObject)
	{
		boolean result = false;
		if(otherObject instanceof SushiBar)
		{
			double r1, l1, t1, b1, r2, l2, t2, b2;
			
			r1 = getX() + getSize()/2;
			l1 = getX() - getSize()/2;
			t1 = getY() + getSize()/2;
			b1 = getY() - getSize()/2;
			r2 = SushiBar.WIDTH;
			l2 = 0;
			t2 = 0;
			b2 = SushiBar.LENGTH;
			
			if(!( (r1<l2 || l1>r2) && (t2<b1 || t1<b2) )) result = true;
		}
		return result;
	}
	
	public boolean handleCollision(ICollider otherObject)
	{
		GameObject other = (GameObject)otherObject;
		boolean remove = false;
		if(other instanceof SushiBar) remove = true;
		return remove;
	}
}
