package a4;

/*
 * Since all Catcher objects must implement the IGuided interface in the same way,
 * the abstract Catcher class implements IGuided for all subclasses to inherit.
 * Catcher objects move half their size in the direction that is input, as long
 * as they will not go out of bounds. If they are to go out of bounds but are not
 * touching the edge, each respective IGuided method will move the Catcher object
 * so that it is touching the edge.
 */
public abstract class Catcher extends GameObject implements IGuided
{
	
	public void right()
	{
		if (getX() + getSize()/2 + 5 <= GameWorld.SIZE) setX(getX()+5);
		else setX(GameWorld.SIZE - getSize()/2);
	}
	public void left()
	{ 
		if (getX() - getSize()/2 - 5 >= 0) setX(getX()-5);
		else setX(getSize()/2);
	}
	public void up() 
	{ 
		if (getY() + getSize()/2 + 5 <= GameWorld.SIZE) setY(getY()+5);
		else setY(GameWorld.SIZE - getSize()/2);
	}
	public void down()
	{ 
		if (getY() - getSize()/2 - 5 >= 0) setY(getY()-5);
		else setY(getSize()/2);
	}
	/*
	public void right()
	{
		setX(getX()+5);
	}
	public void left()
	{ 
		setX(getX()-5);
	}
	public void up() 
	{ 
		setY(getY()+5);
	}
	public void down()
	{ 
		setY(getY()-5);
	}
	*/
}
