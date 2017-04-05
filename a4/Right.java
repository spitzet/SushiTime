package a4;

import javax.swing.AbstractAction;
import java.awt.event.*;

public class Right extends AbstractAction
{
	GameWorld gw;
	public Right(GameWorld world)
	{
		super("move Right");
		gw = world;
	}
	
	public void actionPerformed(ActionEvent e){	gw.right(); }

}
