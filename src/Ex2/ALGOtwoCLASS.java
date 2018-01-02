package Ex2;
/**
 * class for the calculation of the second algorithm
 * @author Alexey Titov &   Shalom Weinberger
 * @version 2.0
 */
import java.util.ArrayList;
import Ex1.Location;
import Ex1.WIFI;

public class ALGOtwoCLASS {
	//variables
	private double pi;
	private Location LLA;					//coordinate measurement where occurred
	private ArrayList<WIFI> WiFi;			//WIFI data
	//constructor
	public ALGOtwoCLASS()
	{
		this.pi = 0;
		this.LLA = new Location();
		this.WiFi = new ArrayList<WIFI>();
	}
	/**
	 *  Getting and Setting of pi
        * @return pi for second algorithm
	 */
	public double getPi() {
		return pi;
	}
	public void setPi(double pi) {
		this.pi = pi;
	}
	/**
	 * Getting and Setting of location
        * @return  location of validation
	 */
	public Location getLLA() {
		return LLA;
	}
	public void setLLA(Location lLA) {
		LLA = lLA;
	}
	/**
	 * getting and setting for WiFi
         * @return list of WIFI points
	 */
	public ArrayList<WIFI> getWiFi() {
		return WiFi;
	}
	public void setWiFi(WIFI wiFi)
	{
		this.WiFi.add(wiFi);
	}
	@Override
	public String toString() {
		return "ALGOtwoCLASS [pi=" + pi + ", LLA=" + LLA + ", WiFi=" + WiFi + "]";
	}
	
}
