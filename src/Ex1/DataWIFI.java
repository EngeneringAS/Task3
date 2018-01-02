package Ex1;
/**
 * class of WiFi network
 * @author Alexey Titov &   Shalom Weinberger
 * @version 11.2
 */
//library
import java.util.*;
public class DataWIFI 
{
	//variables
	private Time TIME;						//time when the data was measured
	private String ID;						//name of the cellular phone
	private Location lla;					//coordinate measurement where occurred
	private int WIFINetwork;				//number of networks
	private ArrayList<WIFI> WiFi;			//WIFI data
	//constructor
	public DataWIFI()
	{
		super();
		this.TIME =new Time();
		this.ID = "N\\A";
		this.lla = new Location();
		this.WIFINetwork = 0;
		this.WiFi =new ArrayList<WIFI>();
	}
	/**
	 * getting and setting for TIME
        * @return time validation
	 */
	public String getTIME() {				//returns the time as string
		return this.TIME.getFt();
	}
	public Date getTIMED() {				//returns the time as date
		return this.TIME.getFtD();
	}
	public boolean setTIME(String tIME) {	//returns true-if the input was successful, false-if the input was not successful
		if (this.TIME.setFt(tIME))
			return true;
		return false;
	}
	/**
	 * getting and setting for ID
        * @return ID of device
	 */
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	/**
	 * getting and setting for lla
        * @return place validation
	 */
	public Location getLla() {
		return lla;
	}
	public void setLla(Location lla) {
		this.lla = lla;
	}
	/**
	 * getting and setting for WIFINetwork
        * @return number of WIFI points
	 */
	public int getWIFINetwork() {
		return WIFINetwork;
	}
	public void setWIFINetwork(int wIFINetwork) {
		if (wIFINetwork>-1 && wIFINetwork<11)
			WIFINetwork = wIFINetwork;
	}
	/**
	 * getting and setting for WiFi
        * @return list of WIFI point
	 */
	public ArrayList<WIFI> getWiFi() {
		return WiFi;
	}
	public void setWiFi(WIFI wiFi)
	{
		if (this.WIFINetwork!=0)
		{
			if (CopyWiFi(wiFi))			//check if wifi is in wifi list 
				return;
			for (int i=0;i<this.WiFi.size();i++)
			{
				if (this.WiFi.get(i).getSignal()<wiFi.getSignal())
				{
					this.WiFi.add(i,wiFi);
					this.WIFINetwork++;
					if (this.WIFINetwork<11)
						return ;
					else
					{
						this.WiFi.remove(10);
						this.WIFINetwork--;
						return ;
					}
				}
	        }
		}
		this.WiFi.add(wiFi);
		this.WIFINetwork++;
		if (this.WIFINetwork>10)
		{
			this.WiFi.remove(10);
			this.WIFINetwork--;
		}
	}
	/**
	 * the function check if wifi is in wifi list 
	 * @param wifi-new wifi variables
	 * @return true -wifi is in wifi list, false- wifi is not in wifi list
	 */
	private boolean CopyWiFi(WIFI wifi)
	{
		for (int i=0;i<this.WiFi.size();i++)
			if (this.WiFi.get(i).getMAC().equals(wifi.getMAC()))
			{
				return true;
			}
		return false;
	}
	/**
	 * toString for we program
	 */
	@Override
	public String toString()
	{
		String str=this.TIME.getFt() + "," + ID + "," + lla.toString() + WIFINetwork + ",";
		for (int i=0;i<this.WiFi.size();i++)
			str+=this.WiFi.get(i).toString()+",";
		return str;
	}	
}
