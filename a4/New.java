package a4;

import javax.swing.AbstractAction;
import java.awt.event.*;

/*
 * For now, the New command does nothing except indicate that it was invoked.
 */
public class New extends AbstractAction
{
	public New()
	{
		super("New");
	}
	
	public void actionPerformed(ActionEvent e)
	{	
		System.out.println(this.getClass().getName().substring(3) + " Command invoked by " + e.getSource().getClass());
	}

}
