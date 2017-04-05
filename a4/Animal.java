package a4;

/*
 * The abstract class Animal inherits all methods from its super class GameObject, because an
 * Animal object is a GameObject. Also, since all Animals must move, the IMoving interface is
 * inherited so that the move method can be polymorphically called in a loop with theWorld
 * vector. However, since Animal objects may move differently, the move() method is still
 * declared to be abstract. Since all Animal objects have a speed and direction, the data
 * fields, setters, and getters are defined. Additionally Animal's toString() method adds
 * GameObject's toString method to it's additional fields of direction and speed, so that
 * information common to all GameObjects as well as information specific to Animals can be
 * displayed to the screen.
 */
public abstract class Animal extends GameObject implements IMoving
{
	private int speed;
	private int direction;
	
	public int getSpeed() { return speed; }
	public int getDirection()
	{
		//int gameDirection = 360 - ((direction + 270) % 360);
		return direction;
	}
	
	public void setSpeed(int speed) { this.speed = speed; }
	public void setDirection(int direction)
	{
		//int gameDirection = 360 - ((direction + 270) % 360);
		//this.direction = gameDirection;
		this.direction = direction;
	}
	
	public String toString()
	{
		String desc = String.format("%s dir=%d speed=%d", super.toString(), direction, speed);
		return desc;
	}
	
}
