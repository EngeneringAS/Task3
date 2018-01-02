package Ex2;
/**
 * class for the calculation of the first algorithm
 * @author Alexey Titov &   Shalom Weinberger
 * @version 2.0
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Ex1.Location;

public class ALGOoneCLASS {
	//variables
	private Map<String,ArrayList<LocSig>> DATAmac;
	private final int limit=4;
	//constructor
	public ALGOoneCLASS() 
	{
		this.DATAmac =new HashMap<String, ArrayList<LocSig>>();
	}
	/**
	 * Getter and Setter of map
        * @return map for first algorithm
	 */
	public Map<String, ArrayList<LocSig>> getDATAmac() {
		return DATAmac;
	}
	public void setDATAmac(String MAC,Location lla,int Signal) {
		ArrayList<LocSig> tmp=new ArrayList<>();
		LocSig tmpLS=new LocSig();
		tmpLS.setLla(lla);
		tmpLS.setSignal(Signal);
		tmp.add(tmpLS);
		if (this.DATAmac.isEmpty() || !this.DATAmac.containsKey(MAC))
		{	
			this.DATAmac.put(MAC,tmp);
			return;
		}
		if (this.DATAmac.containsKey(MAC))
		{
			boolean flag=true;
			tmp=this.DATAmac.get(MAC);
			//location is equals
			for (int i=0;i<tmp.size();i++)
				if (tmp.get(i).getLla().compareLLA(lla)==0)
					if (tmp.get(i).getSignal()>Signal)
					{
						tmp.get(i).setSignal(Signal);
						this.DATAmac.put(MAC,tmp);
						return;
					}
			//location is not equals
			for (int i=0;i<tmp.size();i++)
				if (tmp.get(i).getSignal()>Signal)				//signal is better
				{
					tmp.add(i,tmpLS);
					flag=false;
					break;
				}
			if (flag)
				tmp.add(tmpLS);
			//up to limit
			if (limit<tmp.size())
				tmp.remove(limit);
			this.DATAmac.put(MAC,tmp);
		}
	}
}
