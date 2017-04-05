package a4;

import javax.swing.Timer;

/*
 * The purpose of the IGameWorld interface is so that any object
 * of type IGameWorld can be passed as a parameter, no matter
 * the implementation of the methods. This enables the use of
 * a proxy for GameWorld.
 */
public interface IGameWorld {
	public void expand();
	public void contract();
	public void scoop();
	public void right();
	public void left();
	public void up();
	public void down();
	public void fishDelivered();
	public void tick(int elapsed);
	
	public int getFishPoints();
	public int getNumSharksScooped();
	public int getTotalPoints();
	public int getNetPoints();
	public int getTicks();
	public boolean getSound();
	public void toggleSound(Clip bgm, Timer timer);
	public World getWorld();
}
