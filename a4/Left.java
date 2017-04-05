package a4;

import javax.swing.AbstractAction;
import java.awt.event.*;

public class Left extends AbstractAction
{
	GameWorld gw;
	public Left(GameWorld world)
	{
		super("move Left");
		gw = world;
	}
	
	public void actionPerformed(ActionEvent e){	gw.left(); }

}
