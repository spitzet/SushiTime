package a4;

import javax.swing.AbstractAction;
import java.awt.event.*;

public class Tick extends AbstractAction
{
	private final int DELAY;
	GameWorld gw;
	
	public Tick(GameWorld world, final int DELAY)
	{
		super("Tick");
		gw = world;
		this.DELAY = DELAY;
	}
	
	public void actionPerformed(ActionEvent e) { gw.tick(DELAY); }

}
