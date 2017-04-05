package a4;

public interface ICollider {
	public boolean collidesWith(ICollider otherObject);
	public boolean handleCollision(ICollider otherObject);
}
