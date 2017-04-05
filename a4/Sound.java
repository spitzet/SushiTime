package a4;

import javax.swing.AbstractAction;
import javax.swing.Timer;
import java.awt.event.*;


public class Sound extends AbstractAction
{
	GameWorld gw;
	Clip bgm;
	Timer timer;
	
	public Sound(GameWorld world, Timer clock, Clip music)
	{
		super("Sound");
		gw = world;
		bgm = music;
		timer = clock;
	}
	
	public void actionPerformed(ActionEvent e){	gw.toggleSound(bgm, timer); }

}