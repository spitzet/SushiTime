package a4;

import java.awt.Graphics2D;
import java.awt.Point;

public interface ISelectable {
	public void setSelected(boolean selected);
	public boolean isSelected();
	public boolean contains(Point p);
	public void draw(Graphics2D g2d);
}
