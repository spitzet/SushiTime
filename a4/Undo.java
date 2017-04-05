package a4;

import javax.swing.AbstractAction;
import java.awt.event.*;

/*
 * For now, the Undo command does nothing except indicate that it was invoked.
 */
public class Undo extends AbstractAction
{
	public Undo()
	{
		super("Undo");
	}
	
	public void actionPerformed(ActionEvent e)
	{	
		System.out.println(this.getClass().getName().substring(3) + " Command invoked by " + e.getSource().getClass());
	}

}
