package a4;

import javax.swing.AbstractAction;
import java.awt.event.*;

/*
 * For now, the File command does nothing except indicate that it was invoked.
 */
public class File extends AbstractAction
{
	public File()
	{
		super("File");
	}
	
	public void actionPerformed(ActionEvent e)
	{	
		System.out.println(this.getClass().getName().substring(3) + " Command invoked by " + e.getSource().getClass());
	}

}
