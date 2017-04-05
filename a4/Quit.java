package a4;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import java.awt.event.*;

/*
 * If the Quit command is invoked, the user is prompted with a confirm dialog to
 * make sure they want to exit the game. If the user clicks yes, the program is
 * terminated. If the user clicks no, game play is resumed.
 */

public class Quit extends AbstractAction
{
	public Quit()
	{
		super("Quit");
	}
	
	public void actionPerformed(ActionEvent e)
	{
		int result = JOptionPane.showConfirmDialog
				(null,  "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(result == JOptionPane.YES_NO_OPTION) System.exit(0);
	}
}