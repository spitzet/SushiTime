package a4;

import javax.swing.AbstractAction;
import java.awt.event.*;

public class Up extends AbstractAction
{
	GameWorld gw;
	public Up(GameWorld world)
	{
		super("move Up");
		gw = world;
	}
	
	public void actionPerformed(ActionEvent e){	gw.up(); }

}
