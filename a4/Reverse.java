package a4;
import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.*;

public class Reverse extends AbstractAction {
	World world;
	
	public Reverse(World world)
	{
		super("Reverse");
		this.world = world;
		this.setEnabled(false);
	}

	public void actionPerformed(ActionEvent e)
	{
		Iterator iter = world.getIterator();
		while(iter.hasNext())
		{
			GameObject go = (GameObject)iter.next();
			if(go instanceof Fish)
			{
				Fish fish = (Fish)go;
				if(fish.isSelected())
				{
					fish.setDirection((fish.getDirection()+180)%360);
					fish.setSelected(false);
				}
			}
			
		}
	}

}
