package a4;

import javax.swing.AbstractAction;
import java.awt.event.*;

import javax.swing.*;

public class Pause extends AbstractAction {
	GameWorld gw;
	Timer timer;
	Clip bgm;

	public Pause(GameWorld world, Timer timer, Clip bgm)
	{
		super("Pause");
		this.timer = timer;
		this.bgm = bgm;
		gw = world;	
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JButton label = (JButton)e.getSource();
		
		if(timer.isRunning())
		{
			timer.stop();
			bgm.stop();
			label.setText("Play");
		}
		else
		{
			timer.start();
			if(gw.getSound())bgm.loop();
			label.setText("Pause");
		}
	}

}
