package Ex2;
/**
 * class for algorithms, class for saving a place after calculations
 * @author Alexey Titov  and  Shalom Weinberger
 * @version 2.0
 */
public class WeightLLA 
{
	//variables
	private double wlat;					//latitude weight
	private double wlon;					//longitude weight
	private double walt;					//altitude weight
	//constructor
	public WeightLLA() 
	{
		super();
		this.wlat = 0;
		this.wlon = 0;
		this.walt = 0;
	}
	/**
	 * Getting and Setting for latitude weight
        * @return weight latitude
	 */
	public double getWlat() {
		return wlat;
	}
	public boolean setWlat(double wlat) {
		if (wlat>=0)
		{
			this.wlat = wlat;
			return true;
		}
		return false;
	}
	/**
	 * Getting and Setting for longitude weight
        * @return weight longtitude
	 */
	public double getWlon() {
		return wlon;
	}
	public boolean setWlon(double wlon) {
		if (wlon>=0)
		{
			this.wlon = wlon;
			return true;
		}
		return false;
	}
	/**
	 * Getting and Setting for altitude weight
        * @return weight altitude
	 */
	public double getWalt() {
		return walt;
	}
	public boolean setWalt(double walt) {
		if (walt>=0)
		{
			this.walt = walt;
			return true;
		}
		return false;
	}
}
