package a4;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import java.awt.event.*;

/*
 * The About command displays information about the program in a message dialog.
 */
public class About extends AbstractAction
{
	public About()
	{
		super("About");
	}
	
	public void actionPerformed(ActionEvent e)
	{	
		JOptionPane.showMessageDialog(
				null,
				"Author: Travis Spitze\nClass: CSC 133\nVersion Number: 4.0",
				"About",
				JOptionPane.INFORMATION_MESSAGE);
	}

}
