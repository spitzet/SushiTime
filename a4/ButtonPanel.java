package a4;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/*
 * The ButtonPanel class is a JPanel which contains all the buttons that Game added
 * commands to. Game has already taken care of care of most of the work setting up
 * the ButtonPanel JPanel, and all it need do is set a GridLayout for the buttons
 * to appear properly on the screen as well as set a border and make itself visible.
 */

public class ButtonPanel extends JPanel
{
	public ButtonPanel()
	{
		this.setLayout(new GridLayout(10,1));
		this.setBorder(new TitledBorder("Commands:"));
		
		setVisible(true);		
	}
	


}
