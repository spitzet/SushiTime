package a4;

import javax.swing.AbstractAction;
import java.awt.event.*;

public class Contract extends AbstractAction
{
	GameWorld gw;
	public Contract(GameWorld world)
	{
		super("Contract Net");
		gw = world;
	}
	
	public void actionPerformed(ActionEvent e){	gw.contract(); }

}
