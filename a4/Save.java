package a4;

import javax.swing.AbstractAction;
import java.awt.event.*;

/*
 * For now, the Save command does nothing except indicate that it was invoked.
 */
public class Save extends AbstractAction
{
	public Save()
	{
		super("Save");
	}
	
	public void actionPerformed(ActionEvent e)
	{	
		System.out.println(this.getClass().getName().substring(3) + " Command invoked by " + e.getSource().getClass());
	}

}
