package a4;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/*
 * Within the MVC architecture, PointsView is a view. Because it is an
 * observer of GameWorld, when GameWorld has been changed
 * GameWorld automatically notifies PointsView so that PointsView
 * can update the points that are displayed on the screen. PointsView first
 * creates labels for the points and initializes them. When update() is
 * invoked, it uses the accessors in GameWorld to update the labels with the
 * new current amount of points.
 */

public class PointsView extends JPanel implements IObserver {
	private JLabel totalPoints, fishPoints, sharksCaught, netPoints, sound, time;
	private int tp, fp, sc, np, ticks;
	private boolean soundState;
	
	public PointsView(GameWorld gw)
	{
		this.setLayout(new GridLayout(1,6));
		this.setBorder(new LineBorder(Color.blue, 3));
		this.setPreferredSize(new Dimension(1000, 50));
		
		tp = 0;
		fp = 0;
		sc = 0;
		np = 0;
		ticks = 0;
		soundState = true;
		
		totalPoints = new JLabel("Total Points: " + tp, JLabel.CENTER);
		fishPoints = new JLabel("points for Fish: " + fp, JLabel.CENTER);
		sharksCaught = new JLabel("Sharks caught: " + sc, JLabel.CENTER);
		netPoints = new JLabel("points for Fish in net: " + np, JLabel.CENTER);
		sound = new JLabel("Sound: ON", JLabel.CENTER);
		time = new JLabel("Time: " + ticks, JLabel.CENTER);
		
		this.add(totalPoints);
		this.add(fishPoints);
		this.add(sharksCaught);
		this.add(netPoints);
		this.add(sound);
		this.add(time);
		
		setVisible(true);
		
	}
	
	public void update(IObservable o, Object arg) {
		IGameWorld gw = (IGameWorld)o;
		tp = gw.getTotalPoints();
		fp = gw.getFishPoints();
		sc = gw.getNumSharksScooped();
		np = gw.getNetPoints();
		soundState = gw.getSound();
		ticks = gw.getTicks();
		
		totalPoints.setText("Total Points: " + tp);
		fishPoints.setText("points for Fish: " + fp);
		sharksCaught.setText("Sharks caught: " + sc);
		netPoints.setText("points for Fish in net: " + np);
		if(soundState) sound.setText("Sound: ON");
		else sound.setText("Sound: OFF");
		time.setText("Time: " + ticks);
	}

}
