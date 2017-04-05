package a4;

import javax.swing.AbstractAction;
import java.awt.event.*;

public class Down extends AbstractAction
{
	GameWorld gw;
	public Down(GameWorld world)
	{
		super("move Down");
		gw = world;
	}
	
	public void actionPerformed(ActionEvent e){	gw.down(); }

}
