package Ex2;
/**
 * class for question 2, contains computational functions
 * @author Alexey Titov &   Shalom Weinberger
 * @version 2.0
 */
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import java.util.Map.Entry;
import Ex1.DataWIFI;
import Ex1.Location;
import Ex1.WIFI;

public class HelpFunctions {
	//define
	private final int LLAW=4;
	private final int power=2;
	private final int norm=10000;
	private final double sig_diff=0.4;
	private final double min_dif=3;
	private final int no_signal=-120;
	private final int no_mysignal=0;
	private final int diff_no_sig=100;
	private final int limit=4;
	//variables
	private List<ALGOtwoCLASS> points= new ArrayList<>();
	/**
	 * the function adds data to the list
        * @param algOtwoCLASS-location and weight
	 */
	public void addAll(ALGOtwoCLASS... algOtwoCLASS) { 
		Collections.addAll(this.points, algOtwoCLASS); 
	}
	/**
	 * the function calculates the weight according to the first algorithm 
	 * @param coa-locations and signals 
	 * @return location by weighted average
	 */
	public Location WriteWeight(ClassOfAlgorithm1 coa)
	{
		Location w_center=new Location();
		WeightOfAlgorithm1 woa=new WeightOfAlgorithm1();
		if (coa.getSignaList().isEmpty())
			return null;
		woa.setWeight(coa.getSignaList());
		double[] sum = new double[LLAW];
		for (int i=0;i<LLAW;i++)
			sum[i]=0;
		double[] w_sum = new double[LLAW-1];
		for (int i=0;i<LLAW-1;i++)
			w_sum[i]=0;
		for (Map.Entry<Double, WeightLLA> entry : woa.getWeight().entrySet())
		{
			//get value
			WeightLLA value = entry.getValue();
			//get key
			double key = entry.getKey();
			sum[0]+=value.getWlat();
			sum[1]+=value.getWlon();
			sum[2]+=value.getWalt();
			sum[3]+=key;
		}
		//calculate LLA
		w_sum[0]=sum[0]/sum[3];
		w_sum[1]=sum[1]/sum[3];
		w_sum[2]=sum[2]/sum[3];
		//set LLA
		w_center.setLat(w_sum[0]);
		w_center.setLon(w_sum[1]);
		w_center.setAlt(w_sum[2]);
		return w_center;
	}
	/**
	 * the function calculates the average signal
	 * @param coa array from csv files
	 * @return average signal
	 */
	public double AverageSignal(ClassOfAlgorithm1 coa)
	{
		double average=0;
		int count=0;
		if (coa.getSignaList().isEmpty())
			return average;
		for (Map.Entry<Location,Integer> entry : coa.getSignaList().entrySet())
		{
			//get key
			double key = entry.getValue();
			average+=key;
			count++;
		}
		return average/count;
	}
	/**
	 * the function calculates dif
	 * @param Sig_User- signal from user input
	 * @param Sig_csv- signal from csv files
	 * @return different between signals
	 */
	public double Dif(int Sig_User,int Sig_csv)
	{
		if (Sig_csv==no_signal || Sig_csv==no_mysignal)
			return diff_no_sig;
		return Math.abs(Sig_User-Sig_csv)>min_dif ?  Math.abs(Sig_User-Sig_csv):min_dif;
	}
	/**
	 * the function calculates pi(weight)
	 * @param input for the calculation of pi_weight
	 * @param data for the calculation of pi_weight
	 * @return pi(weight)
	 */
	public double weight_pi(ArrayList<WIFI> input,ArrayList<WIFI> data)
	{
		//variables
		int sig=0;
		double dif=0,w=1,out=1;
		try {
			for (int i=0;i<input.size();i++)
			{
				sig=input.get(i).getSignal();
				dif=Dif(sig,data.get(i).getSignal());
				w=norm/(Math.pow(dif,sig_diff)*Math.pow(sig, power));
				out*=w;
			}
		}catch(NullPointerException e) {
			JOptionPane.showMessageDialog(null,"not enough data");
			return -1;
		}
		return out;
	}
	/**
	 * Returns the limit rows with the highest weight values.
	 * Implemented using Streams.
         * @return list for second algorithm 
	 */
	public List<ALGOtwoCLASS> limitRowsWithHighestWeight() {
		return points
			.stream()
			.sorted(Comparator.comparing(points -> -points.getPi()))
			.limit(limit)
			.collect(Collectors.toList());
	}
	/**
	 * the function calculates the weight according to the second algorithm 
	 * @param items-locations and weight 
	 * @return location by weighted average
	 */
	public Location WriteWeight2(List<ALGOtwoCLASS> items)
	{
		Location w_center=new Location();
		double[] sum = new double[LLAW];
		for (int i=0;i<LLAW;i++)
			sum[i]=0;
		double[] w_sum = new double[LLAW-1];
		for (int i=0;i<LLAW-1;i++)
			w_sum[i]=0;
		if (items.isEmpty())
			return null;
		for(ALGOtwoCLASS item : items){
			double tmp_weight=item.getPi();
			sum[3]+=tmp_weight;
			sum[2]+=item.getLLA().getAlt()*tmp_weight;
			sum[0]+=item.getLLA().getLat()*tmp_weight;
			sum[1]+=item.getLLA().getLon()*tmp_weight;
		}
		//calculate LLA
		w_sum[0]=sum[0]/sum[3];
		w_sum[1]=sum[1]/sum[3];
		w_sum[2]=sum[2]/sum[3];
		//set LLA
		w_center.setLat(w_sum[0]);
		w_center.setLon(w_sum[1]);
		w_center.setAlt(w_sum[2]);
		return w_center;
	}
	/**
	 * the function calculates the space according to input and gives out the position according to the weight
	 * @param input for the calculation of pi_weight
	 * @param data for the calculation of pi_weight
	 * @return the position according to the weight
	 */
	public Location WeightAlgo2(ArrayList<WIFI> input,ArrayList<ALGOtwoCLASS> data)
	{
		HelpFunctions hlpf = new HelpFunctions();
		try{
			for (int i=0;i<data.size();i++) 
			{
				double weight=weight_pi(input,data.get(i).getWiFi());
				if (weight==-1)
					System.exit(2);
				data.get(i).setPi(weight);
				hlpf.addAll(data.get(i));
			}
			return WriteWeight2(hlpf.limitRowsWithHighestWeight());
		}catch(NullPointerException e) {
			return null;
		}
	}
	/**
	 * function exchanges the necessary data from the DataWIFI list into the ClassOfAlgorithm1
	 * @param dwf list with data networks
	 * @param MAC_NAME	MAC name that we need
	 * @return list with need data
	 */
	public ClassOfAlgorithm1 Exchange(ArrayList<DataWIFI> dwf,String MAC_NAME)
	{
		ClassOfAlgorithm1 coa=new ClassOfAlgorithm1();			//data of MAC
		try {
			coa.setMAC(MAC_NAME);
			for (int j=0;j<dwf.size();j++)
				coa.setSignaList(dwf.get(j).getWiFi().get(0).getSignal(), dwf.get(j).getLla());
		}catch(NullPointerException e){
			return null;
		}
		return coa;
	}
	/**
	 * function exchanges the necessary data from the String and DataWIFI map into the ClassOfAlgorithm1 list
	 * @param dwf	list with data networks and macs
	 * @return list with need data
	 */
	public ArrayList<ClassOfAlgorithm1> Exchange(Map<String,ArrayList<DataWIFI>> dwf)
	{
		ArrayList<ClassOfAlgorithm1> coa=new ArrayList<ClassOfAlgorithm1>();			//data of MAC
		try{
			for (Entry<String, ArrayList<DataWIFI>> entry : dwf.entrySet())
			{
				//get value
				ArrayList<DataWIFI> value = entry.getValue();
				//get key
				String key = entry.getKey();
				coa.add(Exchange(value,key));
			}
		}catch(NullPointerException e){
			return null;
		}
		return coa;
	}
	/**
	 * the function translates information from the map into one list
	 * @param dwf map of DataWIFI and MAC names data 
	 * @return list of DataWIFI
	 */
	public ArrayList<DataWIFI> MapToList(Map<String,ArrayList<DataWIFI>> dwf)
	{
		ArrayList<DataWIFI> out=new ArrayList<DataWIFI>();
		try {
			for (Entry<String, ArrayList<DataWIFI>> entry : dwf.entrySet())
			{
				//get value
				ArrayList<DataWIFI> value = entry.getValue();
				out.addAll(value);
			}
			return out;
		}catch(NullPointerException e) {
			return null;
		}
	}
	/**
	 * the function determines whether two lists have similar points
	 * @param WiFi1 list of which we must find
	 * @param WiFi2 list from the database
	 * @return true-similar points are, false-similar points are not 
	 */
	public boolean HelpToFind(ArrayList<WIFI> WiFi1,ArrayList<WIFI> WiFi2)
	{
		try {
			for (int i=0;i<WiFi1.size();i++)
				for (int j=0;j<WiFi2.size();j++)
					if (WiFi1.get(i).getMAC().equals(WiFi2.get(j).getMAC()))
						return true;
			return false;
		}catch(NullPointerException e) {
			System.out.println("Why NULL :(");
			return false;
		}
	}
	/**
	 * the function looks for a similar point in the list
	 * @param WiFi1 Wifi what we are looking for in the list
	 * @param WiFi2 WiFi list from the database
	 * @return similar point or point with signal -120
	 */
	public WIFI DataToFind(WIFI WiFi1,ArrayList<WIFI> WiFi2)
	{
		WIFI tmp=new WIFI();
		try{
			tmp.setSignal(-120);		tmp.setMAC(WiFi1.getMAC());		tmp.setFrequency(WiFi1.getFrequency());		tmp.setSSID(WiFi1.getSSID());
			for (int i=0;i<WiFi2.size();i++)
				if (WiFi1.getMAC().equals(WiFi2.get(i).getMAC()))
					return WiFi2.get(i);
			return tmp;
		}catch(NullPointerException e) {
			return null;
		}
	}
	/**
	 * the function finds the necessary data in the database for the second algorithm
	 * @param DWF database
	 * @param WiFi the list of which we must find
	 * @return the necessary data in the database for the second algorithm
	 */
	public ArrayList<ALGOtwoCLASS> Find(ArrayList<DataWIFI> DWF,ArrayList<WIFI> WiFi)
	{
            System.out.println(DWF.size()+" "+WiFi.size());
            ArrayList<ALGOtwoCLASS> tmp_atc=new ArrayList<ALGOtwoCLASS>();
		try {
			for (int i=0;i<DWF.size();i++)
				for (int j=0;j<DWF.get(i).getWiFi().size();j++)
				{
					ALGOtwoCLASS tmp=new ALGOtwoCLASS();
					tmp.setLLA(DWF.get(i).getLla());
					if (HelpToFind(WiFi,DWF.get(i).getWiFi()))
					{	
						for (int k=0;k<WiFi.size();k++)
							tmp.setWiFi(DataToFind(WiFi.get(k),DWF.get(i).getWiFi()));
						tmp_atc.add(tmp);
						break;
					}
				}
			return tmp_atc;
		}catch(NullPointerException e) {
			return null;
		}
	}
}