package a4;
import java.util.Random;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/*
 * The GameWorld class is responsible for holding the collection of
 * GameObjects for the entire game. It is also responsible for
 * manipulating the objects when a command is invoked by the user or
 * the views. The collection of GameObjects is able to hold all
 * subclasses of GameObjects, and by doing so, enables the use of
 * polymorphism to read or manipulate any GameObject in theWorld.
 * The constant SIZE enables the ability to change the size of the
 * game world if desired at a later point without having to change
 * every single class which uses this size.
 * 
 * The addition of "implements IObservable" means that GameWorld is
 * now an observable. GameWorld is responsible for keeping track of
 * its registered observers and notifying observers when there is a
 * change in theWorld collection. The addition of "implements
 * IGameWorld allows a proxy to be passed to observers instead of
 * the actual GameWorld so that the observers are not able to use
 * mutator methods defined by GameWorld. notifyObservers() is
 * added to the end of each method which alters the data of the
 * collection of GameObjects. In the MVC architecture, GameWorld is
 * the model.
 */

public class GameWorld implements IObservable, IGameWorld
{
	private World theWorld;
	private Vector<IObserver> observers; //keeps track of observers
	public final static int SIZE = 700;
	private int fishPoints; //keeps track of points delivered to the Sushi bar
	private int ticks; //keeps track of how much time has passed
	private int totalPoints; //keeps track of total points
	private int numSharksScooped; //keeps track of the number of sharks scooped
	private int netPoints; //keeps track of the total points for the fish in the net
	private int timeElapsed;
	private boolean sound; //the sound flag
	private Clip gulp, chomp, water, swipe;
	
	/*
	 * The GameWorld constructor is passed the number of fish and
	 * food objects to be populated into theWorld. First the
	 * SushiBar and Net classes are populated, then the appropriate
	 * amount of fish and food are added to theWorld. If no fish
	 * are passed, the game cannot be played, and so the program
	 * closes.
	 */
	public GameWorld(int numfish, int numfood)
	{
		fishPoints = 0;
		ticks = 0;
		numSharksScooped = 0;
		totalPoints = 0;
		netPoints = 0;
		timeElapsed = 0;
		sound = true;
		gulp = new Clip("gulp.wav");
		chomp = new Clip("chomp.wav");
		water = new Clip("water.wav");
		swipe = new Clip("swipe.wav");
		observers = new Vector<IObserver>();
		theWorld = new World();
		theWorld.add(new SushiBar());
		theWorld.add(new Net());
		for(int i=0; i<numfish; i++) theWorld.add(new Fish());
		for(int i=0; i<numfood; i++) theWorld.add(new Seaweed());
		
		/*
		boolean fish = false;
		Iterator worldIterator = theWorld.getIterator();
		while(worldIterator.hasNext())
		{
			if (worldIterator.next() instanceof Fish)
			{
				fish = true;
				break;
			}
		}
		if(!fish)
		{
			System.out.println("No fish to catch!");
			System.exit(0);
		}
		*/
	}
	
	//registers an observer
	public void addObserver(IObserver obs){	observers.add(obs); }
	
	//passes a proxy to observers
	public void notifyObservers()
	{
		GameWorldProxy proxy = new GameWorldProxy(this);
		for(IObserver obs : observers) obs.update((IObservable)proxy, null);
	}
	
	
	/*
	 * fish() finds the first instance of a Fish object in
	 * theWorld collection by stepping through the iterator and
	 * checking to see if the GameObject obtained is a fish. It
	 * then type casts the GameObject to the concrete
	 * type of Fish so its methods can be invoked. The reference
	 * to the Fish object inside theWorld collection is passed back
	 * for reading or manipulation by other methods in GameWorld.
	 */
	/*
	private Fish fish()
	{
		Fish fish = new Fish();
		GameObject go;
		Iterator worldIterator = theWorld.getIterator();
		while(worldIterator.hasNext())
		{
			go = (GameObject)worldIterator.next();
			if (go instanceof Fish)
			{
				fish = (Fish)go;
				break;
			}
		}
		return fish;
	}
	*/
	
	/*
	 * The net() method is identical to fish() except that it
	 * instead returns a concrete reference to the only Net
	 * object in theWorld vector.
	 */
	public Net net()
	{
		Net net = new Net();
		GameObject go;
		Iterator worldIterator = theWorld.getIterator();
		while(worldIterator.hasNext())
		{
			go = (GameObject)worldIterator.next();
			if (go instanceof Net)
			{
				net = (Net)go;
				break;
			}
		}
		return net;
	}
	
	/*
	 * Using the reference passed back from net(), the net
	 * can be manipulated in any way that its public
	 * interface defines. The next can be expanded, contracted,
	 * moved right, left, up, or down when told to do so by
	 * Game.
	 */
	public void expand()
	{
		net().expand();
		notifyObservers();
	}
	
	public void contract()
	{
		net().contract(); 
		notifyObservers();
	}
	
	public void right()
	{
		net().right();
		notifyObservers();
	}
	public void left()
	{
		net().left(); 
		notifyObservers();
	}
	public void up()
	{
		net().up();
		notifyObservers();
	}
	public void down()
	{
		net().down();
		notifyObservers();
	}
	
	//reproduce() adds a new Fish object to the world for the time being
	/*
	public void reproduce()
	{
		theWorld.add(fish().reproduce());
		notifyObservers(); 
	}
	*/
	
	/*
	 * When a shark and fish collide, a fish object is removed
	 * from theWorld collection using the iterator's 
	 * remove() method. For the time being, the first instance
	 * of a Fish object inside theWorld collection is found and deleted.
	 */
	/*
	public void killFish()
	{
		Iterator worldIterator = theWorld.getIterator();
		while(worldIterator.hasNext())
		{
			if (worldIterator.next() instanceof Fish)
			{
				worldIterator.remove();
				break;
			}
		}
		notifyObservers();
	}
	*/
	
	/*
	 * First, the eat() method is invoked using the fish() method
	 * which returns a reference to the first fish found. The size
	 * of this fish is increased by 1. Next, the first Plant Object
	 * found is deleted, as if the fish had eaten it.
	 */
	/*
	public void eat()
	{
		Iterator worldIterator = theWorld.getIterator();
		fish().eat();
		while(worldIterator.hasNext())
		{
			if (worldIterator.next() instanceof Plant)
			{
				worldIterator.remove();
				break;
			}
		}
		notifyObservers();
	}
	*/
	
	/*
	 * This method controls what happens as time flows in the GameWorld
	 * class. Each tick is added to the total amount of time, because
	 * once 60 ticks have passed, the game ends and the final score is
	 * displayed. Also, if there are no more fish in theWorld, then
	 * the game can no longer be played because there is nothing to
	 * catch. Next, the apparent type IMoving is polymorphically searched
	 * for, and when an object of apparent type IMoving is found, it is
	 * casted so that its move() method can be invoked. The actual type
	 * Shark is then searched for in theWorld vector and casted so it
	 * can be determined if the Shark went out of bounds. If this
	 * occurred, the Shark object is removed from theWorld collection.
	 * Lastly, with each tick there is a 1/2 chance of a Seaweed object
	 * being added to theWorld collection, and a 1/4 chance of a shark
	 * being added. This is accomplished by using the Random built-in
	 * class.
	 * 
	 * The tick method has now been separated into three different methods
	 * so that it can be more easily understood. As explained in Game, the
	 * checkCollisions() method handles collisions between objects, and
	 * moving is now accomplished using the elapsed time. Once 1000ms have
	 * passed, the tick count increments.
	 */
	public void tick(int elapsed)
	{
		countTime(elapsed);
		moveObjects(elapsed);
		checkCollisions();
		notifyObservers();
	}
	
	private void countTime(int elapsed)
	{
		timeElapsed += elapsed;
		if(timeElapsed % 1000 == 0)
		{
			ticks++;
			Random rand = new Random();
			if (rand.nextInt(3)==0)	theWorld.add(new Shark());
			if (rand.nextInt(1)==0) theWorld.add(new Seaweed());
		}
		if(ticks >= 60)
		{
			JOptionPane.showMessageDialog(
					null,
					"Time's up!\nFinal Score: " + totalPoints,
					"Game Over",
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		
		boolean fish = false;
		Iterator worldIterator = theWorld.getIterator();
		while(worldIterator.hasNext())
		{
			if (worldIterator.next() instanceof Fish)
			{
				fish = true;
				break;
			}
		}
		if(!fish)
		{
			JOptionPane.showMessageDialog(
					null,
					"No more fish to catch!\nFinal Score: " + totalPoints,
					"Game Over",
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}
	
	private void moveObjects(int elapsed)
	{
		Iterator worldIterator = theWorld.getIterator();
		while(worldIterator.hasNext()) {
			GameObject go = (GameObject)worldIterator.next();
			if (go instanceof IMoving) {
				IMoving moveable = (IMoving)go;
				moveable.move(elapsed);
				if (moveable instanceof Shark)
				{
					Shark shark = (Shark)moveable;
					if(shark.getX() >= SIZE || shark.getY() >= SIZE || shark.getX() <= 0 || shark.getY() <= 0)
					{
						worldIterator.remove();
					}
				}
			}
		}
	}
	private void checkCollisions()
	{
		Iterator iter = theWorld.getIterator();
		boolean remove = false;
		boolean onFishList = false;
		Vector condemned = new Vector();
		//GameObject removal;
		
		while(iter.hasNext())
		{
			//condemned = new Vector();
			GameObject go = (GameObject)iter.next();
			if(go instanceof ICollider)
			{
				ICollider current = (ICollider)go;
				Iterator iter2 = theWorld.getIterator();
				while(iter2.hasNext())
				{
					GameObject go2 = (GameObject)iter2.next();
					if(go2 instanceof ICollider)
					{
						ICollider other = (ICollider)go2;
						if(other != current && current.collidesWith(other))
						{
							if(current instanceof Fish && other instanceof Fish)
							{
								Fish currentFish = (Fish)current;
								Fish otherFish = (Fish)other;
								Iterator<Fish> fishList = currentFish.getFishList();
								while(fishList.hasNext())
								{
									Fish listedFish = fishList.next();
									if(otherFish == listedFish)
									{
										onFishList = true;
										break;
									}
								}
								if(!onFishList && !currentFish.isBaby() && !otherFish.isBaby())
								{
									Fish baby = currentFish.reproduce();
									theWorld.add(baby);
									if(sound) water.play();
								}
							}
							remove = current.handleCollision(other);
							if(remove)
							{
								if(current instanceof Net) fishDelivered();
								else
								{
									if(sound)
									{
										if(current instanceof Fish) chomp.play();
										if(current instanceof Seaweed) gulp.play();
									}
								//iter.remove();
								condemned.addElement(current);
								}
								remove = false;
								break;
							}
						}
					}
				}
			}
		}
		iter = theWorld.getIterator();
		while(iter.hasNext())
		{
			GameObject go = (GameObject)iter.next();
			for(int i=0; i < condemned.size(); i++)
			{
				if(go == condemned.elementAt(i)) iter.remove();
			}
		}
	}
	
	/*
	 * A while loop is used to polymorphically invoke the
	 * toString method of every type of GameObject inside
	 * the collection by stepping through the iterator.
	 */
	/*
	public void map()
	{
		Iterator worldIterator = theWorld.getIterator();
		while(worldIterator.hasNext()) System.out.println(worldIterator.next());
		System.out.println();
	}
	*/
	
	/*
	 * First, the reference to the only Net object in theWorld is passed
	 * to net for purposes of invoking Net methods. If scooping is currently
	 * enabled, theWorld collection is checked for any object of abstract type
	 * Animal. If found, the reference to the Animal is cast from abstract type
	 * GameObject to abstract type Animal. The Animal object's location is
	 * checked against the Net object to see if it is fully enclosed within
	 * the size of the net. If so, the Animal then must be determined to be
	 * either a Fish object or Shark object. If a Fish is found, points are
	 * added to the points for fish in the net, and scooping is no longer enabled.
	 * The Fish is then removed from theWorld collection, and the search will
	 * continue for more Animals in the net's size. If a Shark object is
	 * found, the amount of sharks scooped by accident is incremented,
	 * the shark is removed from the collection, and the total points decreased
	 * by 10.
	 */
	public void scoop()
	{
		Net net = net();
		if (net.scooping())
		{
			Iterator worldIterator = theWorld.getIterator();
			while(worldIterator.hasNext()) {
				GameObject go = (GameObject)worldIterator.next();
				if (go instanceof Animal)
				{
					Animal animal = (Animal)go;
					if (animal.getX()-animal.getSize()/2 >= net.getX()-net.getSize()/2 
							&& animal.getX()+animal.getSize()/2 <= net.getX()+net.getSize()/2 
							&& animal.getY()-animal.getSize()/2 >= net.getY()-net.getSize()/2 
							&& animal.getY()+animal.getSize()/2 <= net.getY()+net.getSize()/2)
					{
						if(animal instanceof Fish)
						{
							Fish fish = (Fish)animal;
							netPoints += fish.getAbstractSize();
							net.setScooping(false);
							worldIterator.remove();
						}
						if(animal instanceof Shark)
						{
							worldIterator.remove();
							numSharksScooped++;
							totalPoints -= 10;
						}
					}
				}
				if(!net.scooping()) swipe.play();
			}
			notifyObservers();
		}
	}
	
	/*
	 * Once the Net reaches the Sushibar, the points from the net are
	 * transfered to the delivered points as well as the total points
	 * and the Net's points are reset. Once a delivery is complete,
	 * scooping is once again enabled.
	 */
	public void fishDelivered()
	{
		Net net = net();
		fishPoints += netPoints;
		totalPoints += netPoints;
		netPoints = 0;
		
		net.setScooping(true);
		notifyObservers();
	}
	
	/*
	 * In this version of the game, accessors are needed for the GameWorld
	 * object so that the views can update the information to the screen
	 * accordingly. These accessors are needed so that a GameWorldProxy
	 * can be passed to the observers instead of letting the observers
	 * obtain the data by having access to the entire GameWorld. Only
	 * accessors that are needed are passed.
	 */
	public int getFishPoints(){	return fishPoints; }
	public int getNumSharksScooped(){ return numSharksScooped; }
	public int getTotalPoints(){ return totalPoints; }
	public int getNetPoints(){ return netPoints; }
	public int getTicks(){ return ticks; }
	public boolean getSound(){ return sound; }
	public World getWorld(){ return theWorld; }
	
	/*
	 * The toggleSound() method toggles the flag sound between true
	 * and false, and checks to see whether the game is paused or not.
	 */
	public void toggleSound(Clip bgm, Timer timer) 
	{
		sound = !sound;
		if(sound && timer.isRunning()) bgm.play();
		else bgm.stop();
		notifyObservers();
	}
	
	
}