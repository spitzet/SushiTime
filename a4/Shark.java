package a4;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

/*
 * The Shark object is unique in that its initial location must be on the side of the world. A random
 * number between 0-3 is generated with the random number generator, and a side is picked.
 * Its direction is set perpendicular to the side of the world so that it does not immediately
 * go out of bounds and get deleted. As explained in Game, it can now draw itself and handle collisions.
 * It is now also comprised of two triangle classes and a circle class, as it is now a hierarchal object.
 */
public class Shark extends Animal implements ICollider
{
	private AffineTransform rotation, translation, scaler;
	private Triangle fin, tail;
	private Circle body;

	public Shark()
	{
		setColor(Color.red);
		setSpeed(rng(20, 100));
		setSize(50);
		rotation = new AffineTransform();
		translation = new AffineTransform();
		scaler = new AffineTransform();
		fin = new Triangle(getSize()/2);
		tail = new Triangle(getSize()/2);
		body = new Circle(getSize());
		
		fin.translate(getSize()*0.6, 0);
		fin.rotate(90);
		tail.translate(0, -getSize());
		body.scale(0.6, 1.4);
		
		int whichSide = rng(4);
		
		if(whichSide == 0) //left
		{
			setDirection(90);
			//setDirection(0);
			setX(getSize()/2 + 1);
			setY(rngBoundary(SushiBar.LENGTH, GameWorld.SIZE));
			translate(getX(), getY());
			rotate(getDirection());
			
		}
		else if(whichSide == 1) //bottom
		{
			setDirection(0);
			//setDirection(90);
			setX(rngBoundary(SushiBar.LENGTH, GameWorld.SIZE));
			setY(getSize()/2 + 1);
			translate(getX(), getY());
			rotate(getDirection());
		}
		else if(whichSide == 2) //right
		{
			setDirection(270);
			//setDirection(180);
			setX(GameWorld.SIZE - getSize()/2 - 1);
			setY(rngBoundary());
			translate(getX(), getY());
			rotate(getDirection());
		}
		else //top
		{
			setDirection(180);
			//setDirection(270);
			setX(rngBoundary());
			setY(GameWorld.SIZE - getSize()/2 - 1);
			translate(getX(), getY());
			rotate(getDirection());
		}
	}
	
	/*
	 * A Shark object moves in the same way as a Fish object, except that if it goes
	 * out of bounds, it is removed from theWorld collection instead of switching direction
	 * like a Fish object. A Shark object will still switch direction if colliding with
	 * the Sushibar.
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
	
	public void move(int elapsed)
	{
		double newX = getX() + Math.sin(Math.toRadians(getDirection())) * getSpeed() * (((double)elapsed)/1000);
		double newY = getY() + Math.cos(Math.toRadians(getDirection())) * getSpeed() * (((double)elapsed)/1000);
		if(newX <= SushiBar.WIDTH + getSize()/2 && newY <= SushiBar.LENGTH + getSize()/2)
		{
			setDirection((getDirection()+180)%360);
			newX = getX() + Math.sin(Math.toRadians(getDirection())) * getSpeed() * (((double)elapsed)/1000);
			newY = getY() + Math.cos(Math.toRadians(getDirection())) * getSpeed() * (((double)elapsed)/1000);
			rotate(180);
		}
		translate(newX-getX(), newY-getY());
		setX(newX);
		setY(newY);

	}
	
	public void draw(Graphics2D g2d){
		AffineTransform saveAT = g2d.getTransform();
		//Polygon p = new Polygon();
		g2d.transform(translation);
		g2d.transform(rotation);
		
		g2d.setColor(getColor());
		body.draw(g2d);
		fin.draw(g2d);
		tail.draw(g2d);
		
		g2d.setTransform(saveAT);
	}
	
	public boolean collidesWith(ICollider otherObject)
	{
		boolean result = false;
		GameObject other = (GameObject)otherObject;
		double radius = getSize()/2;
		double dx = getX() - other.getX();
		double dy = getY() - other.getY();
		double dCenterSqr = dx*dx + dy*dy;
		double otherRadius = other.getSize()/2;
		double radiiSqr = radius*radius + 2*radius*otherRadius + otherRadius*otherRadius;
		if(dCenterSqr <= radiiSqr) result = true;
		return result;		
	}
	
	public boolean handleCollision(ICollider otherObject) {	return false; }
}
