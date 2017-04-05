package a4;

import javax.swing.AbstractAction;
import java.awt.event.*;

public class Scoop extends AbstractAction
{
	GameWorld gw;
	public Scoop(GameWorld world)
	{
		super("Scoop");
		gw = world;
	}
	
	public void actionPerformed(ActionEvent e){	gw.scoop(); }

}
