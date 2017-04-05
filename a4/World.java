package a4;
import java.util.Vector;
import java.util.Iterator;

/*
 * This is the class that holds collection of GameObjects. It uses a
 * Vector to hold them, but the outside world cannot see how the
 * GameObjects are being stored within World. World's methods
 * provide an interface for adding objects to the collection and
 * getting an Iterator for the collection. The WorldIterator class
 * is a private class within World, so that World is the only class
 * which can return a new WorldIterator.
 */

public class World implements ICollection {
	private Vector theWorld;
	
	public World() {
		theWorld = new Vector();		
	}

	public void add(Object o){
		theWorld.add(o);
	}
	
	public Iterator getIterator(){
		return new WorldIterator();
	}

/*
 * WorldIterator is responsible for determining whether there
 * are more objects in the collection as well as returning the
 * next object in the collection and removing objects from the
 * collection. It keeps track of the current index of the
 * Iterator, and when the next object in the collection is
 * needed, it increments the current position by one, then
 * returns the object. If an object is removed from the collection,
 * the current position is decremented to ensure that the current
 * object is actually in the collection.
 */
	private class WorldIterator implements Iterator {
		private int current;
	
		public WorldIterator() {
			current = -1;
		}
	
		public boolean hasNext() {
			if(theWorld.size() <= 0) return false;
			if(current >= theWorld.size() - 1) return false;
			return true;
		}
	
		public Object next() {
			current++;
			return theWorld.elementAt(current);
		}
	
		public void remove() {
			if(theWorld.size() >= 1){
				theWorld.removeElementAt(current);
				current--;
			}
		}
	
	}
}
