package a4;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/*
 * See the Plant class for description. Seaweed does not move and is instantiated with a size, color,
 * and location that is not out of bounds. As explained in Game, it can now draw itself and handle collisions.
 */
public class Seaweed extends Plant implements ICollider
{
	private AffineTransform rotation, translation, scaler;
	
	public Seaweed()
	{
		setSize(20);
		setColor(Color.orange);
		setX(rngBoundary());
		rotation = new AffineTransform();
		translation = new AffineTransform();
		scaler = new AffineTransform();
		
		if(getX() - getSize()/2 <= SushiBar.WIDTH) setY(rngBoundary(SushiBar.LENGTH, GameWorld.SIZE));
		else setY(rngBoundary());
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
	
	public void draw(Graphics2D g2d){
		AffineTransform saveAT = g2d.getTransform();
		g2d.transform(translation);
		g2d.setColor(getColor());
		//g2d.drawRect(getXCorner(), getYCorner(), getSize(), getSize());
		g2d.drawRect(-getSize()/2, -getSize()/2, getSize(), getSize());
		g2d.setTransform(saveAT);
	}
	
	public boolean collidesWith(ICollider otherObject)
	{
		boolean result = false;
		GameObject other = (GameObject)otherObject;
		double radius = getSize()/2;
		//if(otherObject instanceof Fish){
		double dx = getX() - other.getX();
		double dy = getY() - other.getY();
		double dCenterSqr = dx*dx + dy*dy;
		double otherRadius = other.getSize()/2;
		double radiiSqr = radius*radius + 2*radius*otherRadius + otherRadius*otherRadius;
		if(dCenterSqr <= radiiSqr) result = true;
		return result;
	}
	
	public boolean handleCollision(ICollider otherObject)
	{
		GameObject other = (GameObject)otherObject;
		boolean remove = false;
		if(other instanceof Fish) remove = true;
		return remove;
	}
}
