package Ex2;
/**
 * class for algorithm 1, contains data for one MAC
 * @author Alexey Titov &   Shalom Weinberger
 * ID:     334063021    &   203179403
 * @version 2.0
 */
//libraries
import java.util.HashMap;
import java.util.Map;
import Ex1.Location;

public class ClassOfAlgorithm1 {
	//variables
	private String MAC;
  	private Map<Location,Integer>  SignaList;
  	//define
  	private static final int limit = 3;
  	//constructor
	public  ClassOfAlgorithm1 () 
	{
		this.MAC="N\\A";
		this.SignaList=new HashMap<>();
	}
	public String getMAC() {
		return MAC;
	}
	/**
	 * the function set string mac to variable MAC 
         * @param mac-Media Access Control address
	 * @return true-if mac is correct, false-if mac is incorrect
	 */
	public boolean setMAC(String mac) {
		if (mac.length()!=17)
			return false;
		String[] tmp = mac.split(":");
		if (tmp.length!=6)
			return false;
		for (int i=0;i<tmp.length;i++)
		{
			if (tmp[i].length()!=2 || CheckNumChar(tmp[i].charAt(0),tmp[i].charAt(1)))
				return false;
		}
		this.MAC = mac;
		return true;
	}
	/**
	 * checks that the letters or numbers
	 * @param ch1-first character
	 * @param ch2-second character
	 * @return true-if characters are numbers or letters (a,b,c,d,e,f), false-otherwise
	 */
	private boolean CheckNumChar(char ch1,char ch2)
	{
		if ((ch1<'a' || ch1>'f') && (ch1<'0' || ch1>'9'))
			return true;
		if ((ch2<'a' || ch2>'f') && (ch2<'0' || ch2>'9'))
			return true;
		return false;
	}
	/**
	 * the function set data for task2.a 
	 * @param Signal- WIFI signal
	 * @param LLA- location of WIFI
         * @return true made a signal and false-did not make a signal to the list
	 */
	public boolean setSignaList(Integer Signal, Location LLA) 
	{
		//wifi is in list
		for (Map.Entry<Location, Integer> entry : SignaList.entrySet())
		{
			//get key
			Location key = entry.getKey();
			if(key.compareLLA(LLA)==0)
				return false;
		}	
		SignaList.put(LLA, Signal);
		if (SignaList.size()>limit)
		{
			int max_value=Signal;
			Location max_LLA=LLA;
			for (Map.Entry<Location, Integer> entry : SignaList.entrySet())
			{
				//get key
				Location key = entry.getKey();
				//get value
				int value = entry.getValue();
				if (value<max_value)
				{
					max_LLA=key;
					max_value=value;
				}        
			}
			//remove max
			SignaList.remove(max_LLA);
		}
		return true;
	}
	/**
	 * the function return wifi data
	 * @return map of wifi data 
	 */
	public Map<Location, Integer> getSignaList() {
		return SignaList;
	}
}
