package Ex2;
/**
 * class for algorithm 2, was created to assist in the calculations
 * @author Alexey Titov &   Shalom Weinberger
 * @version 2.0
 */
import Ex1.Location;

public class LocSig {
	//variables
	private Location lla;
	private int signal;
	//constructor
	public LocSig() {
		this.lla=new Location();
		this.signal=-120;
	}
	/**
	 * Getters and Setters location
        * @return location of point
	 */
	public Location getLla() {
		return lla;
	}
	public void setLla(Location lla) {
		this.lla = lla;
	}
	/**
	 * Getters and Setters signal
        * @return signal of WIFI point
	 */
	public int getSignal() {
		return signal;
	}
	public void setSignal(int signal) {
		this.signal = signal;
	}
}
