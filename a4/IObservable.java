package a4;

public interface IObservable {
	public void addObserver(IObserver o);
	public void notifyObservers();
}
