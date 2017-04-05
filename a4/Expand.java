package a4;

import javax.swing.AbstractAction;
import java.awt.event.*;

public class Expand extends AbstractAction
{
	GameWorld gw;
	public Expand(GameWorld world)
	{
		super("Expand Net");
		gw = world;
	}
	
	public void actionPerformed(ActionEvent e){	gw.expand(); }

}
