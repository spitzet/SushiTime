package a4;

import javax.swing.Timer;


/*
 * GameWorldProxy implements IObservable and IGameWorld so that it
 * has the same apparent type as GameWorld. Because of this, the
 * GameWorldProxy can be passed to observers instead of GameWorld
 * itself, since GameWorld passes type IGameWorld to the observers.
 * This proxy restricts access to almost all of GameWorld's methods
 * except for map() and the newly added accessors that are needed
 * by the views. If the views attempt to use a method that they are
 * not allowed to access, nothing will happen.
 */
public class GameWorldProxy implements IObservable, IGameWorld {
	private GameWorld gw;
	private World world;
	public GameWorldProxy(GameWorld world){ gw = world; }

	public void expand(){}
	public void contract(){}
	public void scoop(){}
	public void right(){}
	public void left(){}
	public void up(){}
	public void down(){}
	public void fishDelivered(){}
	public void tick(int elapsed){}
	
	public int getFishPoints(){	return gw.getFishPoints(); }
	public int getNumSharksScooped(){ return gw.getNumSharksScooped(); }
	public int getTotalPoints(){ return gw.getTotalPoints(); }
	public int getNetPoints(){ return gw.getNetPoints(); }
	public int getTicks(){ return gw.getTicks(); }
	public boolean getSound(){ return gw.getSound(); }
	public void toggleSound(Clip bgm, Timer timer){}
	public World getWorld(){ return world;}

	public void addObserver(IObserver o){}
	public void notifyObservers(){}

}
