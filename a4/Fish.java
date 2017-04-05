package a4;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.Vector;
import java.util.Iterator;
//import java.applet.*;
//import java.io.File;

/*
 * The Fish class is a concrete GameObject that can be instantiated and manipulated in theWorld
 * vector. A fish has a real size as well as an abstract size. The abstract size is updated when
 * the eat() method is invoked, so that the fish grows in size and gives more points. The Fish's
 * location and direction are randomly generated using the random number generator.
 */
public class Fish extends Animal implements ICollider, ISelectable
{
	private int abstractSize;
	private int lifeTime;
	private boolean baby;
	private boolean selected;
	private Vector<Fish> fishList;
	private AffineTransform rotation, translation, scaler;
	
	public Fish()
	{
		abstractSize = 1;
		fishList = new Vector<Fish>();
		baby = false;
		lifeTime = 0;
		rotation = new AffineTransform();
		translation = new AffineTransform();
		scaler = new AffineTransform();

		setSize(20);
		setColor(Color.blue);
		setSpeed(rng(20, 100));
		setDirection(rng(359));
		setX(rngBoundary());
		
		if(getX() - getSize()/2 <= SushiBar.WIDTH) setY(rngBoundary(SushiBar.LENGTH, GameWorld.SIZE));
		else setY(rngBoundary());
		translate(getX(), getY());
		rotate(getDirection());
	}
	
	public int getAbstractSize() { return abstractSize; }
	public void setAbstractSize(int size) { abstractSize = size; }
	public void rotate(double degrees){	rotation.rotate(Math.toRadians(-degrees));	}
	public void scale(double sx, double sy){ scaler.scale(sx, sy); }
	public void translate(double dx, double dy){ translation.translate(dx, dy); }
	public void resetTransforms()
	{
		rotation.setToIdentity();
		scaler.setToIdentity();
		translation.setToIdentity();
	}
	
	/*
	 * Once the move() method is invoked, the fish moves in its set direction with its set speed.
	 * Small random values are added to the direction so that it does not travel in a straight line.
	 * However, if the fish goes out of bounds or into the sushi bar, its direction is reversed.
	 * This method must be instantiated as defined by the IMovable interface. 
	 */
	public void move(int elapsed)
	{
		double newX = getX() + Math.sin(Math.toRadians(getDirection())) * getSpeed() * (((double)elapsed)/1000);
		double newY = getY() + Math.cos(Math.toRadians(getDirection())) * getSpeed() * (((double)elapsed)/1000);
		if(newX <= getSize()/2 || newX >= GameWorld.SIZE - getSize()/2
				|| newY <= getSize()/2 || newY >= GameWorld.SIZE - getSize()/2
				|| (newX <= SushiBar.WIDTH + getSize()/2 && newY <= SushiBar.LENGTH + getSize()/2))
		{
			setDirection((getDirection()+180)%360);
			newX = getX() + Math.sin(Math.toRadians(getDirection())) * getSpeed() * (((double)elapsed)/1000);
			newY = getY() + Math.cos(Math.toRadians(getDirection())) * getSpeed() * (((double)elapsed)/1000);
			rotate(180);
		}
		translate(newX-getX(), newY-getY());
		setX(newX);
		setY(newY);
		if(baby)
		{
			lifeTime += elapsed;
			if(lifeTime >= 5000) baby = false;
		}
	}
	
	/*
	 * A new baby Fish is made when two Fish collide. If the parent is on the top half of the
	 * world, the baby is created below the parent, and if the baby is in the bottom half
	 * of the world, the baby is created above the parent. This is done to ensure the baby
	 * is not created out of bounds. Once the baby Fish object has its x and y set, it is
	 * returned to be added to theWorld vector.
	 */
	public Fish reproduce()
	{
		Fish baby = new Fish();
		baby.baby = true;
		baby.setX(getX());
		baby.setY(getY());
		//if (getY() < GameWorld.SIZE/2) baby.setY(getY() + getSize()*2);
		//else baby.setY(getY() - getSize()*2);
		baby.resetTransforms();
		baby.translate(getX(), getY());
		baby.rotate(baby.getDirection());
		
		return baby;
	}
	
	
	/*
	 * If a Fish collides with a Plant object, its abstract size will increase by 1 unless
	 * its abstract size is already 4.
	 */
	public void eat()
	{
		if(getAbstractSize() < 4)
		{
			setAbstractSize(getAbstractSize() + 1);
			setSize(getSize()+10);
		}
	}
	
	/*
	 * @see a1.Animal#toString()
	 * Further fields are added that are specific to Fish objects only (the abstract size).
	 */
	public String toString()
	{
		String size = " size=" + getAbstractSize();
		String desc = super.toString() + size;
		return desc;
	}
	
	public void draw(Graphics2D g2d)
	{
		AffineTransform saveAT = g2d.getTransform();
		g2d.transform(translation);
		g2d.transform(rotation);
		g2d.setColor(getColor());
		
		//if(isSelected()) g2d.fillOval(getXCorner(), getYCorner(), getSize(), getSize());
		//else g2d.drawOval(getXCorner(), getYCorner(), getSize(), getSize());
		if(isSelected()) g2d.fillOval(-(getSize()/2), -(getSize()/2), getSize(), getSize());
		else g2d.drawOval(-(getSize()/2), -(getSize()/2), getSize(), getSize());
		
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
		else if(other instanceof Fish && fishList.contains(other))
		{
			Fish otherFish = (Fish)other;
			fishList.remove(otherFish);
			Iterator<Fish> otherFishList = otherFish.getFishList();
			while(otherFishList.hasNext())
			{
				if(this == otherFishList.next())
				{
					otherFishList.remove();
					break;
				}
			}
			
		}
		return result;
	}
	
	public boolean handleCollision(ICollider otherObject)
	{
		GameObject other = (GameObject)otherObject;
		boolean remove = false;
		if(other instanceof Fish && !fishList.contains(other))
		{
			Fish otherFish = (Fish)other;
			this.addToFishList(otherFish);
			otherFish.addToFishList(this);
			//water.play();
		}
		if(other instanceof Shark) remove = true;
		if(other instanceof Seaweed) eat();
		return remove;
	}
	
	public Iterator<Fish> getFishList()
	{
		return fishList.iterator();
	}
	
	public void addToFishList(Fish fish)
	{
		fishList.add(fish);
	}
	
	/*
	private void baby()
	{
		baby = true;
	}
	*/
	
	public boolean isBaby() { return baby; }
	
	public void setSelected(boolean set)
	{
		if(set) selected = true;
		else selected = false;
	}
	
	public boolean isSelected() { return selected; }
	
	public boolean contains(Point p)
	{
		if(p.getX() >= getX() - getSize()/2
				&& p.getX() <= getX() + getSize()/2
				&& p.getY() >= getY() - getSize()/2
				&& p.getY() <= getY() + getSize()/2)
			return true;
		else return false;
	}
}

