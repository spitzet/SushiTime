package a4;

import java.awt.Graphics2D;

public interface ITransformable {
	public void translate(double dx, double dy);
	public void rotate(double degrees);
	public void scale(double sx, double sy);
	public void resetTransforms();
	public void draw(Graphics2D g2d);
}
