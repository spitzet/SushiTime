package a4;

import java.util.Collection;
import java.util.Scanner;
import java.util.Vector;
import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Iterator;


/*
 * Version 2.0
 * This is the primary class which represents and executes control
 * for the game once it is instantiated. In this version of the
 * game, Game is now the main JFrame for the GUI, and contains not
 * only the GameWorld, but also the MapView, the PointsView, and
 * the ButtonPanel. In addition, this version of the game now uses
 * the Model-View-Controller architecture. Game is the controller,
 * which creates the model (GameWorld) and the views (MapView and
 * PointsView), and then registers the views as observers of
 * GameWorld. Game also creates commands to be used in the command
 * pattern and assigns them to the GUI. Instead of Game invoking
 * methods in the model directly, the GUI components that the
 * commands have been assigned to now invoke GameWorld's methods.
 * The views invoke the methods in GameWorld that have been granted
 * access to them as well.
 * 
 * Once Game is created by Starter, the user must enter the amount
 * of fish and food to be used in the game, then the setting up of
 * the GUI begins after the GameWorld is created. The frame is set
 * up and a layout manager is added so that the views appear
 * correctly on the screen. Next, the views and button panel are
 * created and added to the frame at the appropriate positions on
 * the screen. Buttons are then created and added to the ButtonPanel.
 * Commands are created and added to the buttons, and the menu is
 * built. Menu items are created directly by adding the commands
 * into the different menus. Finally, keystrokes are added to the
 * input map of the MapView, and commands are added to MapView's
 * action map. By doing this, key bindings are created which
 * invoke commands automatically when a key is pressed, without
 * needing to hit the enter key. Once everything has been set up,
 * the frame is made visible so that it can be seen on the screen.
 * 
 * In the previous version of the game, the getCommand() method would
 * accept user input into the console and then execute a method in
 * GameWorld directly. In this version, each method which manipulates
 * data in GameWorld's collection has now been made into a class to
 * be used in the command pattern. Game creates each command class
 * and passes the GameWorld object to it. Next, Game assigns these
 * commands to the appropriate GUI components using the setAction
 * method of the GUI components and passes the command objects,
 * which are all of type AbstractAction by extension. setAction
 * takes an AbstractAction object and automatically makes the
 * AbstractAction object passed to it a listener of the invoking
 * GUI component. By doing this, when the GUI component is clicked,
 * the actionPerformed method in the command object that was set
 * as a listener is invoked. In a similar fashion, commands are
 * also added to the key bindings so that when a key is pressed,
 * the command is invoked. When a command object is created, the
 * constructor calls AbstractAction's constructor with the name
 * that is to be displayed on the component. If GameWorld is
 * passed to the command object because it invokes a method in
 * GameWorld, the GameWorld object is assigned so that the appropriate
 * method can be called in the command object's actionPerformed
 * method.
 */

/*
 * Version 3.0
 * In this version, the console is never printed to anymore. A
 * timer has been added to the game with Game itself as a listener.
 * Each time timer generates an event (using the delay of 20 milliseconds)
 * the game increments 20 milliseconds in time by invoking the tick command.
 * The tick command updates timer, moves the GameObjects, and checks
 * for collisions between GameObjects. Objects are now moved 50 times a
 * second because of the timer generating events every 20ms, and because
 * of this, they are moved by 1/50th of what they were moved in version 2.0.
 * The tick command invokes the tick method in GameWorld and passes the
 * elapsed time since the last action event. GameWorld then iterates
 * through the GameObjects and calls move(elapsed). The objects then
 * use this elapsed time to move this fraction of time that has passed, as
 * opposed to an entire second. Once the GameObjects have all moved, they
 * then check for collisions with other GameObjects using the new
 * ICollider interface. If a collision is detected, each object handles
 * the collision to the best of its ability, since it cannot delete
 * itself from the world. The GameObjects return a flag indicating what
 * is to be done in the game world, as well as internally modifying
 * themselves where necessary.
 * 
 * Objects in this version of the game are displayed graphically
 * in the map view. All objects know how to draw themselves so that the
 * map view can iterate through all GameObjects and polymorphically invoke
 * their draw method by overriding the PaintComponent method. Once the timer ticks,
 * mv.repaint() is called, and thereby invoking PaintComponent,  so that each
 * change in their position is redisplayed every 20ms. The effect of
 * this is that the objects look like they are moving, or if they have been
 * removed, they are no longer displayed on the screen. Also, if a fish eats,
 * it will grow larger in size.
 * 
 * Another feature added to this version of the game is that now sound effects
 * and a background music have been added. If certain objects collide, sound
 * effects are played. However, if the sound flag inside GameWorld is false,
 * no sounds will be heard, including the bgm. GameWorld handles the sound
 * effects, and they are played once a collision has been detected. Game
 * initially begins the bgm loop.
 * 
 * The game can now also be paused. Once paused, all sounds are muted, even
 * if the sound flag is true. Once unpaused, sound will resume only if
 * the sound flag is on. the Pause and Sound commands both must check to
 * make sure sound isn't playing when it shouldn't be, and sound is playing
 * when it should be. In addition, if the pause command is invoked, all
 * commands that are used to play the game are now disabled.
 * 
 * Lastly, the game now has a reverse command. Besides the play command,
 * this is the only command that is enabled when the game is paused.
 * Fish can now be selected, and if the reverse command is invoked
 * while these fish are selected, they will begin to move in the opposite
 * direction. When the game is unpaused, the reverse command is disabled,
 * and fish objects are not selectable. 
 */

/*
 * Version 4.0
 * In this version of the game, the net is used as a window through which
 * to see the world. Also, objects are now mapped using local coordinates.
 * World coordinates and local coordinates are now separate. Instead of
 * drawing objects directly using world coordinates, affine transforms
 * are used to translate, rotate, and scale objects. In addition, a
 * screen transform is used so that the world is now right side up as
 * intended. Since the inside of the net is now all that the player can
 * see, expanding and contracting the net now zooms out and zooms in
 * respectively. the expand and contract commands have been bound to
 * the mouse wheel for ease of use. Also, panning the net now moves the
 * view of the world, and this has been bound to mouse dragging for
 * better playability. In order to translate, scale, and rotate all
 * game objects, GameObject now implements the ITransformable interface,
 * which requires all GameObjects to know how to translate, scale, and
 * rotate themselves, as well as clearing all transforms.
 * 
 * Since objects are in local coordinates, hierarchal objects can now
 * be implemented. Complex objects are made using more simple objects,
 * and body parts can be rotated then translated from the body's coordinate
 * system to the correct spot. This version uses this ability to make a
 * more detailed shark.
 * 
 * Lastly, a net is flashed on the screen for a moment when the user
 * invokes the scoop command. The net is comprised of 4 cubic bezier curves
 * with 4 control points each.
 */

public class Game extends JFrame implements ActionListener, MouseListener, MouseWheelListener, MouseMotionListener
{
	private GameWorld gw;
	private MapView mv;
	private PointsView pv;
	private ButtonPanel buttonPanel;
	private Timer timer;
	private int DELAY;
	private Clip bgm;
	private JButton expand, contract, scoop, right, left, up, down, reproduce, killFish, eat, deliver, pause, reverse;
	private Vector<Action> commands;
	private Tick tickCommand;
	private Point oldP;
	private Point newP;
	
	
	/*
	 * This constructor now picks an amount of fish and food
	 * to be initially added to the world, as opposed to before
	 * when the user could pick the amount. 
	 */
	public Game()
	{
//create the world
		int fish = 30;
		int food = 20;
		gw = new GameWorld(fish, food);
		
//set up the frame
		setTitle("Sushi Time");
		setSize(838, 812);
		setLocation(500, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
				
//build the layout for the frame	
		this.setLayout(new BorderLayout());
				
//create views for the frame
		buttonPanel = new ButtonPanel();
		pv = new PointsView(gw);
		mv = new MapView(gw.getWorld());
		
//set locations and layouts of views on the frame
		this.add(pv, BorderLayout.NORTH);
		this.add(buttonPanel, BorderLayout.WEST);
		this.add(mv, BorderLayout.CENTER);

//sounds
		bgm = new Clip("bgm.wav");
		bgm.loop();		

//create the timer
		DELAY = 20;
		timer = new Timer(DELAY, this);
		timer.start();
		
//create buttons for the buttonPanel view
		expand = new JButton("Expand Net");
		contract = new JButton("Contract Net");
		scoop = new JButton("Scoop");
		right = new JButton("move Right");
		left = new JButton("move Left");
		up = new JButton("move Up");
		down = new JButton("move Down");
		reproduce = new JButton("fish baby");
		killFish = new JButton("shark eat fish");
		eat = new JButton("fish eat food");
		deliver = new JButton("deliver");
		//tick = new JButton("Tick");
		pause = new JButton("Play");
		reverse = new JButton("Reverse");
		
//add buttons to the buttonPanel view
		buttonPanel.add(expand);
		buttonPanel.add(contract);
		buttonPanel.add(scoop);
		buttonPanel.add(right);
		buttonPanel.add(left);
		buttonPanel.add(up);
		buttonPanel.add(down);
		buttonPanel.add(reverse);
		buttonPanel.add(pause);
		
//add observers
		gw.addObserver(mv);
		gw.addObserver(pv);
		
		


//create commands
		Expand expandCommand = new Expand(gw);
		Contract contractCommand = new Contract(gw);
		Scoop scoopCommand = new Scoop(gw);
		Right rightCommand = new Right(gw);
		Left leftCommand = new Left(gw);
		Up upCommand = new Up(gw);
		Down downCommand = new Down(gw);
		tickCommand = new Tick(gw, DELAY);
		File fileCommand = new File();
		New newCommand = new New();
		Save saveCommand = new Save();
		Undo undoCommand = new Undo();
		Sound soundCommand = new Sound(gw, timer, bgm);
		About aboutCommand = new About();
		Quit quitCommand = new Quit();
		Pause pauseCommand = new Pause(gw, timer, bgm);
		Reverse reverseCommand = new Reverse(gw.getWorld());
		
//add JButtons to the JButton vector to be passed to pause
		commands = new Vector<Action>();
		commands.addElement(expandCommand);
		commands.addElement(contractCommand);
		commands.addElement(scoopCommand);
		commands.addElement(rightCommand);
		commands.addElement(leftCommand);
		commands.addElement(upCommand);
		commands.addElement(downCommand);
		commands.addElement(reverseCommand);
		
//set commands for buttons
		expand.setAction(expandCommand);
		contract.setAction(contractCommand);
		scoop.setAction(scoopCommand);
		right.setAction(rightCommand);
		left.setAction(leftCommand);
		up.setAction(upCommand);
		down.setAction(downCommand);
		pause.setAction(pauseCommand);
		reverse.setAction(reverseCommand);
		
//build menu
		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);
	
		JMenu file = new JMenu("File");
		bar.add(file);
			file.add(fileCommand);
			file.addSeparator();
			file.add(newCommand);
			file.add(saveCommand);
			file.add(undoCommand);
			file.addSeparator();
			JCheckBoxMenuItem sound = new JCheckBoxMenuItem("Sound", true);
			file.add(sound);
			sound.setAction(soundCommand);
			file.add(aboutCommand);
			file.addSeparator();
			file.add(quitCommand);

		JMenu commands = new JMenu("Commands");
		bar.add(commands);
			commands.add(expandCommand);
			commands.add(contractCommand);
			
//Key Bindings
		InputMap imap = mv.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);			
		KeyStroke rkey = KeyStroke.getKeyStroke("R");
		KeyStroke lkey = KeyStroke.getKeyStroke("L");
		KeyStroke ukey = KeyStroke.getKeyStroke("U");
		KeyStroke dkey = KeyStroke.getKeyStroke("D");
		KeyStroke rightkey = KeyStroke.getKeyStroke("RIGHT");
		KeyStroke leftkey = KeyStroke.getKeyStroke("LEFT");
		KeyStroke upkey = KeyStroke.getKeyStroke("UP");
		KeyStroke downkey = KeyStroke.getKeyStroke("DOWN");
		KeyStroke skey = KeyStroke.getKeyStroke("S");
		KeyStroke ekey = KeyStroke.getKeyStroke("E");
		KeyStroke ckey = KeyStroke.getKeyStroke("C");
		
		imap.put(rkey, "r");
		imap.put(lkey, "l");
		imap.put(ukey, "u");
		imap.put(dkey, "d");
		imap.put(rightkey, "r");
		imap.put(leftkey, "l");
		imap.put(upkey, "u");
		imap.put(downkey, "d");
		imap.put(skey, "scoop");
		imap.put(ekey, "expand");
		imap.put(ckey, "contract");
		
		ActionMap amap = mv.getActionMap();
		amap.put("r", rightCommand);
		amap.put("l", leftCommand);
		amap.put("d", downCommand);
		amap.put("u", upCommand);
		amap.put("scoop", scoopCommand);
		amap.put("expand", expandCommand);
		amap.put("contract", contractCommand);
		
//add listeners
		mv.addMouseListener(this);
		mv.addMouseWheelListener(this);
		mv.addMouseMotionListener(this);
		pause.addActionListener(this);
		
//initialize points
		oldP = new Point();
		newP = new Point();
		
		setVisible(true);
	}
	
	/*
	 * As explained above, objects can now be selected when the
	 * game is paused by clicking on them.
	 */
	public void mousePressed(MouseEvent e)
	{
		oldP = e.getPoint();
		if(!timer.isRunning())
		{
			AffineTransform VTM = new AffineTransform();
			try{ VTM = mv.getVTM().createInverse(); }
			catch(NoninvertibleTransformException ex){}
			Point p = e.getPoint();
			Point wp = new Point();
			VTM.transform(p, wp);
			
			Iterator iter = gw.getWorld().getIterator();
			while(iter.hasNext())
			{
				GameObject go = (GameObject)iter.next();
				if(go instanceof ISelectable)
				{
					ISelectable sel = (ISelectable)go;
					if(sel.contains(wp)) sel.setSelected(true);
					else if(!e.isControlDown()) sel.setSelected(false);
				}
			}
			mv.repaint();
		}
	}

	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if(e.getWheelRotation() > 0) gw.net().expand();
		else if(e.getWheelRotation() < 0) gw.net().contract();
	}
	public void mouseMoved(MouseEvent e){}
	public void mouseDragged(MouseEvent e)
	{
		newP = e.getPoint();
		Net net = gw.net();
		if(newP.x > oldP.x) net.left();
		if(newP.x < oldP.x) net.right();
		if(newP.y > oldP.y) net.up();
		if(newP.y < oldP.y) net.down();
		oldP = newP;
	}
	
	/*
	 * The actionPerformed method for the timer. As explained above, when
	 * the pause button is pressed, commands used to play the game are
	 * disabled, while reverse is enabled. This also called the tick
	 * command and repaints the map view.
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == pause)
		{
			Iterator<Action> iter = commands.iterator();
			while(iter.hasNext())
			{
				Action current = iter.next();
				current.setEnabled(!current.isEnabled());
			}
		}
		//gw.tick(DELAY);
		tickCommand.actionPerformed(e);
		mv.repaint();
	}
}

	