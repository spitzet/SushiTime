package a4;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.swing.*;
import javax.swing.border.*;
import java.util.Iterator;
import java.util.Vector;

/*
 * Within the MVC architecture, MapView is a view. Because it is an
 * observer of GameWorld, when GameWorld has been changed, GameWorld
 * automatically notifies MapView so that MapView can display the
 * new state of the world to the view. In addition, Game invokes
 * PaintComponent by calling repaint every time the timer generates
 * an event.
 * 
 * In version 4.0 of the game, the map view has many responsibilities
 * added to it. As explained in Game, It now has the responsibility of
 * both converting from world coordinates to normalized device coordinates
 * to screen coordinates and constructing and drawing the 4 bezier
 * curves. 
 */
public class MapView extends JPanel implements IObserver {
	World world;
	Net net;
	double pWidth, pHeight, winHeight, winWidth, winLeft, winBottom;
	AffineTransform worldToND, ndToScreen, VTM;
	Point cp;
	Vector<Point> cpv, cpv2, cpv3, cpv4;
	int netTime;
	
	
	public MapView(World world)
	{
		
		this.setBorder(new LineBorder(Color.red, 5));
		this.world = world;
		pWidth = 838;
		pHeight = 812;
		net = new Net();
		netTime = 0;
		
		cpv = new Vector<Point>();
		cpv2 = new Vector<Point>();
		cpv3 = new Vector<Point>();
		cpv4 = new Vector<Point>();
		
		cp = new Point(0, 200);
		cpv.add(cp);
		cp = new Point(350, 0);
		cpv.add(cp);
		cp = new Point(450, 500);
		cpv.add(cp);
		cp = new Point(701, 200);
		cpv.add(cp);
		
		cp = new Point(0, 500);
		cpv2.add(cp);
		cp = new Point(350, 200);
		cpv2.add(cp);
		cp = new Point(450, 700);
		cpv2.add(cp);
		cp = new Point(701, 500);
		cpv2.add(cp);
		
		cp = new Point(200, 0);
		cpv3.add(cp);
		cp = new Point(0, 350);
		cpv3.add(cp);
		cp = new Point(500, 450);
		cpv3.add(cp);
		cp = new Point(200, 695);
		cpv3.add(cp);
		
		cp = new Point(500, 0);
		cpv4.add(cp);
		cp = new Point(200, 350);
		cpv4.add(cp);
		cp = new Point(700, 450);
		cpv4.add(cp);
		cp = new Point(500, 695);
		cpv4.add(cp);
		
		
		GameObject go;
		Iterator worldIterator = world.getIterator();
		while(worldIterator.hasNext())
		{
			go = (GameObject)worldIterator.next();
			if (go instanceof Net)
			{
				net = (Net)go;
				break;
			}
		}
		
		
	}
	
	public void update(IObservable o, Object arg)
	{
		IGameWorld gw = (IGameWorld)o;
		this.repaint();
	}
	
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform saveAT = g2d.getTransform();
		
		winHeight = winWidth = net.getSize();
		winLeft = net.getX() - winWidth/2;
		winBottom = net.getY() - winWidth/2;
		
		worldToND = worldToND(winWidth, winHeight, winLeft, winBottom);
		ndToScreen = ndToScreen(pWidth, pHeight);
		VTM = (AffineTransform)ndToScreen.clone();
		VTM.concatenate(worldToND);
		g2d.transform(VTM);
		
		Iterator iter = world.getIterator();
		while(iter.hasNext())
		{
			GameObject go = (GameObject)iter.next();
			go.draw(g2d);
		}
		g2d.setTransform(saveAT);
		
		g2d.setColor(Color.BLACK);
		
		if(!net.scooping() && netTime <= 10)
		{
			drawBezierCurve(cpv, 1, g2d);
			drawBezierCurve(cpv2, 1, g2d);
			drawBezierCurve(cpv3, 1, g2d);
			drawBezierCurve(cpv4, 1, g2d);
			netTime++;
		}
		if(net.scooping() && netTime > 0) netTime = 0;

		g2d.setTransform(saveAT);
		
		
	}
	
	private AffineTransform worldToND(double winWidth, double winHeight, double winLeft, double winBottom)
	{
		AffineTransform worldToND = new AffineTransform();
		worldToND.scale(1/winWidth,1/winHeight);
		worldToND.translate(-winLeft,-winBottom);
		return worldToND;
	}
	
	private AffineTransform ndToScreen(double pWidth, double pHeight)
	{
		AffineTransform ndToScreen = new AffineTransform();
		ndToScreen.translate(0,pHeight);
		ndToScreen.scale(pWidth,-pHeight);
		return ndToScreen;
	}
	
	public AffineTransform getVTM()
	{
		return VTM;
	}

	public void drawBezierCurve (Vector<Point> cpv, int level, Graphics2D g2d)
	{
	    Vector<Point> leftSubVector = new Vector<Point>();
	    Vector<Point> rightSubVector = new Vector<Point>();
	    
	    if ((straightEnough(cpv))|| (level > 11))
	    {
	    	Point first = cpv.firstElement();
	    	Point last = cpv.lastElement();
	    	g2d.drawLine(first.x, first.y, last.x, last.y);
	    }
	    else
	    {
	    	subdivideCurve(cpv, leftSubVector, rightSubVector);
	    	drawBezierCurve(leftSubVector, level+1, g2d);
	    	drawBezierCurve(rightSubVector, level+1, g2d);
	    }
	    
	}
	
	public void subdivideCurve(Vector<Point> Q, Vector<Point> R, Vector<Point> S)
	{
		Point q0, q1, q2, q3, r1, r2, r3, s1, s2, q12;
		R.setSize(4);
		S.setSize(4);
		R.setElementAt(Q.elementAt(0),0); //R(0) = Q(0)
		q0 = Q.elementAt(0);
		q1 = Q.elementAt(1);
		q2 = Q.elementAt(2);
		q3 = Q.elementAt(3);

		r1 = middle(q0, q1);
		R.setElementAt(r1, 1);  //R(1) = (Q(0) + Q(1))/2
		q12 = middle(q1, q2);
		r2 = middle(r1, q12);
		R.setElementAt(r2, 2);  //R(2) = ((Q(1) + Q(2))/2 + R(1))/2
		S.setElementAt(q3, 3); //S(3) = Q(3)
		s2 = middle(q2, q3);
		S.setElementAt(s2, 2);  //S(2) = (Q(2) + Q(3))/2
		s1 = middle(q12, s2);
		S.setElementAt(s1, 1); //S(1) = ((Q(1) + Q(2))/2 + S(2))/2

		r3 = middle(r2, s1);
		R.setElementAt(r3, 3); //R(3) = (R(2) + S(1))/2
		S.setElementAt(r3, 0);
	    
	}
	
	public Point middle(Point p1, Point p2)
	{
		int x = (p1.x + p2.x)/2;
		int y = (p1.y + p2.y)/2;
		Point mid = new Point(x, y);
		return mid;
	}
	
	public boolean straightEnough(Vector<Point> cpv)
	{
		double d1 = ((Point2D)cpv.elementAt(0)).distance((Point2D)cpv.elementAt(1)) +
				((Point2D)cpv.elementAt(1)).distance((Point2D)cpv.elementAt(2)) +
				((Point2D)cpv.elementAt(2)).distance((Point2D)cpv.elementAt(3));

		double d2 = ((Point2D)cpv.elementAt(0)).distance((Point2D)cpv.elementAt(3));

		if(Math.abs(d1-d2) < 0.001) return true;
		else return false;
	}
}
