/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex3;
//libraries
import Ex1.DataWIFI;
import Ex1.Location;
import Ex1.WIFI;
import Ex2.ClassOfAlgorithm1;
import com.csvreader.CsvReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
/**
 * The read functions for help function of Assignment 3
 * @author Alexey Titov   and   Shalom Weinberger
 * @version 1
 */
public class ReadFunctions{
    //variables
    private final String pathDataBase="database//database.csv";     //path to database
    private final String pathUNDO="database//undo.csv";             //path to UNDO database
    private final String pathFilter="database//filter.txt";         //path to filter
    private final String pathOldFilter="database//undofilter.txt";      //path to filter
    private static Map<String,Integer> MaxSignal=new HashMap<>();   //map max signal of wifi
    /**
     * the function setting max value of wifi signal to map
     * @param wf wifi network
     * @return true if replace was, false if replace was not (ONLY FOR TestKMLTest) 
     */
     private boolean setMAX(WIFI wf)
    {
        Integer WFsignal=wf.getSignal();
	if (!MaxSignal.containsKey(wf.getMAC()))    //there is no such MAC in the map
            MaxSignal.put(wf.getMAC(),WFsignal);
	else
	{
            if (wf.getSignal()>MaxSignal.get(wf.getMAC()))		//replace the signal with a new value
            {
                MaxSignal.put(wf.getMAC(),WFsignal);
		return true;
            }
	}
	return false;
    }
    /**
     * The function compares the maximum signal with the signal that was received at the MAC
     * @param mac MAC of WiFi what need check
     * @param signal Signal of WiFi what need check
     * @return true the signal is weak, false the signal is equal to the maximum value of the MAC
     */
    private boolean Search(String mac,int signal)
    {
	if (MaxSignal.get(mac)>signal)
            return true;
	return false;
    }
    /**
     * the function remove worse signals from list
     * @param DWF list with data wifi
     * @return list of data wifi without worse signal 
     */
    private ArrayList<DataWIFI> RemoveWorseSignal(ArrayList<DataWIFI> DWF)
    {
	try {
            for(int k=0;k<DWF.size();k++)
            {
                for(int m=0;m<DWF.get(k).getWiFi().size();m++)
		{
                    if (Search(DWF.get(k).getWiFi().get(m).getMAC(),DWF.get(k).getWiFi().get(m).getSignal()))		//signal is worse
                    {
                        DWF.get(k).getWiFi().remove(m);
			m--;
			DWF.get(k).setWIFINetwork(DWF.get(k).getWiFi().size());
                    }
		}
            }
	}catch(NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Why null????");
	}
	return DWF;
    }
    //the function read database
    public ArrayList<DataWIFI> ReadDataBase()
    {
        //variables
        ArrayList<DataWIFI> dwf=new ArrayList<>();          //data for file csv
        int j=0;                                            //variables for list of dwf
        try {	
            CsvReader row = new CsvReader(pathDataBase);
            row.readHeaders();
            if (row.getHeaderCount()!=46)		//check that there are 46 headers
            {
                JOptionPane.showMessageDialog(null, "File is not correct");
                row.close();
                return null;
            }
            while (row.readRecord())
            {		
                if (row.getColumnCount()<10 || row.getColumnCount()>46)
                    continue;
                DataWIFI tmpWIFI=new DataWIFI();
                Location place=new Location();
                int cnt=0;		//variables for check MAC,Signal
                int cnt2=0;		//variables for check latitude,longitude
                int max=0;              //number of networks in the list
                boolean flagtime;	//flag for check time
                //location
                try{
                    cnt2+=place.setLat(Double.parseDouble(row.get(2)));
                    cnt2+=place.setLon(Double.parseDouble(row.get(3)));
                    place.setAlt(Double.parseDouble(row.get(4)));
                    max=Integer.parseInt(row.get(5));
                }catch(NumberFormatException e){
                    System.out.println("Err: lat,lon, alt or #WiFi network is no correct");	
                    continue;
                }
                tmpWIFI.setLla(place);
                flagtime=tmpWIFI.setTIME(row.get(0));
                //check if time and location are correct
                if (cnt2!=2 || !flagtime)   
                    continue;
                tmpWIFI.setID(row.get(1));
                //read WiFi data
                for (int i=0;i<max;cnt=0,i++)
                {
                    WIFI tmpWF=new WIFI();
                    try {
                        cnt+=tmpWF.setMAC(row.get(7+i*4));
                        tmpWF.setSSID(row.get(6+i*4));
                        cnt+=tmpWF.setSignal(Integer.parseInt(row.get(9+i*4)));
                        cnt+=tmpWF.setFrequency(Integer.parseInt(row.get(8+i*4)));
                        if (cnt!=3)
                            continue;
                        tmpWIFI.setWiFi(tmpWF);
                        setMAX(tmpWF);
                    }catch(NumberFormatException e){
                        continue;
                    }
                }
            dwf.add(tmpWIFI);
            j++;
        }
        row.close();
        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "kml record not saved\nincorrect file");
            return dwf;
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "kml record not saved\nincorrect file");
            return dwf;
        }catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, "Why null????");
            return dwf;
        }
        return dwf;
    }
    /**
     * the function read csv files
     * @param csvFiles list of names cvs files
     * @return list of data for kml file
     */
    public ArrayList<DataWIFI> ReadCSV(ArrayList<String> csvFiles)
    {
            //variables
            ArrayList<DataWIFI> dwf=new ArrayList<DataWIFI>();		//data of WiFiNetwork
            String IDphone="";						//id of cellular phone
            CsvReader row =null;
            //read file csv and filtering data
            for (int i=0;i<csvFiles.size();i++)
            {
                try {
                    row=new CsvReader(new FileReader(csvFiles.get(i)));
                    //save ID of phone in to IDphone
                    if (row.readRecord())
                    {
                        if (row.getColumnCount()==11 || row.getColumnCount()==8)  //if were is less or more then 8 it's not our format
                        {
                            try {
                                IDphone=row.get(5).substring(8); 
                            }catch(StringIndexOutOfBoundsException e) {
                                JOptionPane.showMessageDialog(null, csvFiles.get(i)+"\nID is incorrect.");
                                row.close();
                                continue;
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,csvFiles.get(i)+"\nFirst row is incorrect.");
                            row.close();
                            continue;
                        }
                    }
                    //read line with names of columns
                    if (!row.readRecord() || row.getColumnCount()!=11)
                    {
                        JOptionPane.showMessageDialog(null, csvFiles.get(i)+"\nRow with column names is incorrect.");
                        row.close();
                        continue;
                    }	
                    //read data from columns
                    while (row.readRecord())
                    {
                        if (row.getColumnCount()==11 && row.get(10).equals("WIFI")) //check that 11 columns are in the line and type is wi-fi network
                        {
                            DataWIFI tmpWIFI=new DataWIFI();
                            WIFI wf=new WIFI();
                            int cnt=0;			//variables for check MAC,Signal
                            int cnt2=0;			//variables for check latitude,longitude
                            boolean flagtime;		//flag for check time
                            tmpWIFI.setID(IDphone);
                            //location
                            Location place=new Location();
                            try{
                                place.setAlt(Double.parseDouble(row.get(8)));
                                cnt2+=place.setLat(Double.parseDouble(row.get(6)));
                                cnt2+=place.setLon(Double.parseDouble(row.get(7)));
                            }catch(NumberFormatException e){
                                continue;
                            }
                            //WIFI data
                            try {
                                cnt+=wf.setFrequency(Integer.parseInt(row.get(4)));
                                cnt+=wf.setMAC(row.get(0));
                                wf.setSSID(row.get(1));
                                cnt+=wf.setSignal(Integer.parseInt(row.get(5)));
                            }catch(NumberFormatException e){
                                continue;
                            }	
                            tmpWIFI.setWiFi(wf);
                            tmpWIFI.setLla(place);
                            flagtime=tmpWIFI.setTIME(row.get(3));
                            tmpWIFI.setWIFINetwork(1);
                            //check if MAC,Signal,time and location are correct
                            if (cnt!=3 || cnt2!=2 || !flagtime)   
                                continue;
                            //first wifi
                            if (dwf.size()==0)
                                dwf.add(tmpWIFI);
                            else
                            {
                                int j;
                                for (j=0;j<dwf.size();j++)                      //time and location are equals
                                    if ((dwf.get(j).getTIMED().compareTo(tmpWIFI.getTIMED())==0)
                                        && (dwf.get(j).getLla().compareLLA(tmpWIFI.getLla())==0))
                                    {
                                        dwf.get(j).setWiFi(wf);
                                        break;
                                    }
                                //time or location are not equals
                                if (j==dwf.size())
                                    dwf.add(tmpWIFI);
                            }
                        }
                    }
                row.close();
            }catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, csvFiles.get(i)+"\nFile not found.");
                continue;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, csvFiles.get(i)+"\nInput or Output are incorrect.");
                continue;
            }
        }
    return dwf;
    }
    /**
    * the function read csv file
    * @param csvFile name cvs file
    * @return list of data for kml file
    */
    public ArrayList<DataWIFI> ReadCSV(String csvFile)
    {
        //variables
        MaxSignal.clear();
        ArrayList<DataWIFI> dwf=new ArrayList<>();			//data for file csv
        int j=0;							//variables for list of dwf
        try {	
            CsvReader row = new CsvReader(csvFile);
            row.readHeaders();
            if (row.getHeaderCount()!=46)				//check that there are 46 headers
            {
                JOptionPane.showMessageDialog(null, "File is not correct");
                row.close();
                System.exit(1);
            }
            while (row.readRecord())
            {		
                if (row.getColumnCount()<10 || row.getColumnCount()>46)
                    continue;
                DataWIFI tmpWIFI=new DataWIFI();
                Location place=new Location();
                int cnt=0;		//variables for check MAC,Signal
                int cnt2=0;		//variables for check latitude,longitude
                int max=0;              //number of networks in the list
                boolean flagtime;	//flag for check time
                //location
                try{
                    cnt2+=place.setLat(Double.parseDouble(row.get(2)));
                    cnt2+=place.setLon(Double.parseDouble(row.get(3)));
                    place.setAlt(Double.parseDouble(row.get(4)));
                    max=Integer.parseInt(row.get(5));
                }catch(NumberFormatException e){
                    System.out.println("Err: lat,lon, alt or #WiFi network is no correct");	
                    continue;
                }
                tmpWIFI.setLla(place);
                flagtime=tmpWIFI.setTIME(row.get(0));
                //check if time and location are correct
                if (cnt2!=2 || !flagtime)   
                    continue;
                tmpWIFI.setID(row.get(1));
                dwf.add(tmpWIFI);
                //read WiFi data
                for (int i=0;i<max;cnt=0,i++)
                {
                    WIFI tmpWF=new WIFI();
                    try {
                        cnt+=tmpWF.setMAC(row.get(7+i*4));
                        tmpWF.setSSID(row.get(6+i*4));
                        cnt+=tmpWF.setSignal(Integer.parseInt(row.get(9+i*4)));
                        cnt+=tmpWF.setFrequency(Integer.parseInt(row.get(8+i*4)));
                        if (cnt!=3)
                            continue;
                        dwf.get(j).setWiFi(tmpWF);
                        setMAX(tmpWF);
                    }catch(NumberFormatException e){
                        continue;
                    }
                }
            j++;
        }
        row.close();
        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "kml record not saved\nincorrect file");
            return dwf;
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "kml record not saved\nincorrect file");
            return dwf;
        }catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, "Why null????");
            return dwf;
        }
        return RemoveWorseSignal(dwf);
    }
    //the functon return data about number of WiFi and rows in database
    public String ReadShowData()
    {
        MaxSignal.clear();
        //variables
        ArrayList<DataWIFI> dwf=new ArrayList<>();			//data for file csv
        int j=0;							//variables for list of dwf
        try {	
            CsvReader row = new CsvReader(pathDataBase);
            row.readHeaders();
            if (row.getHeaderCount()!=46)							//check that there are 46 headers
            {
                JOptionPane.showMessageDialog(null, "File is not correct");
                row.close();
                return "Data:";
            }
            while (row.readRecord())
            {		
                if (row.getColumnCount()<10 || row.getColumnCount()>46)
                    continue;
                DataWIFI tmpWIFI=new DataWIFI();
                Location place=new Location();
                int cnt=0;				//variables for check MAC,Signal
                int cnt2=0;				//variables for check latitude,longitude
                int max=0;				//number of networks in the list		
                boolean flagtime;			//flag for check time
                //location
                try{
                    cnt2+=place.setLat(Double.parseDouble(row.get(2)));
                    cnt2+=place.setLon(Double.parseDouble(row.get(3)));
                    place.setAlt(Double.parseDouble(row.get(4)));
                    max=Integer.parseInt(row.get(5));
                }catch(NumberFormatException e){
                    System.out.println("Err: lat,lon, alt or #WiFi network is no correct");	
                    continue;
                }
                tmpWIFI.setLla(place);
                flagtime=tmpWIFI.setTIME(row.get(0));
                //check if time and location are correct
                if (cnt2!=2 || !flagtime)   
                    continue;
                tmpWIFI.setID(row.get(1));
                dwf.add(tmpWIFI);
                //read WiFi data
                for (int i=0;i<max;cnt=0,i++)
                {
                    WIFI tmpWF=new WIFI();
                    try {
                        cnt+=tmpWF.setMAC(row.get(7+i*4));
                        tmpWF.setSSID(row.get(6+i*4));
                        cnt+=tmpWF.setSignal(Integer.parseInt(row.get(9+i*4)));
                        cnt+=tmpWF.setFrequency(Integer.parseInt(row.get(8+i*4)));
                        if (cnt!=3)
                            continue;
                        dwf.get(j).setWiFi(tmpWF);
                        setMAX(tmpWF);
                    }catch(NumberFormatException e){
                        continue;
                    }
                }
            j++;
        }
        row.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "incorrect file");
            return "Data:";
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "incorrect file");
            return "Data:";
        }catch(IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Why null????");
            return "Data:";
        }
        return "Data: Rows= "+dwf.size()+" ,MACs= "+MaxSignal.size()+" ";
    }
    //the function read data for first algorithm 
    public ClassOfAlgorithm1 ReadFirstAlgo(String mac)
    {
        //variables
        ClassOfAlgorithm1 coa=new ClassOfAlgorithm1();
        coa.setMAC(mac);
        try {	
            CsvReader row = new CsvReader(pathDataBase);
            row.readHeaders();
            if (row.getHeaderCount()!=46)		//check that there are 46 headers
            {
                JOptionPane.showMessageDialog(null, "File is not correct");
                row.close();
                return null;
            }
            while (row.readRecord())
            {		
                if (row.getColumnCount()<10 || row.getColumnCount()>46)
                    continue;
                Location place=new Location();
                int cnt2=0;		//variables for check latitude,longitude
                int max=0;              //number of networks in the list
                //location
                try{
                    cnt2+=place.setLat(Double.parseDouble(row.get(2)));
                    cnt2+=place.setLon(Double.parseDouble(row.get(3)));
                    place.setAlt(Double.parseDouble(row.get(4)));
                    max=Integer.parseInt(row.get(5));
                }catch(NumberFormatException e){
                    System.out.println("Err: lat,lon, alt or #WiFi network is no correct");	
                    continue;
                }
                //check if location are correct
                if (cnt2!=2)   
                    continue;
                //read WiFi data
                for (int i=0;i<max;i++)
                {
                    try {
                        if (row.get(7+i*4).equals(mac.toLowerCase()))
                            coa.setSignaList(Integer.parseInt(row.get(9+i*4)), place);
                    }catch(NumberFormatException e){
                        continue;
                    }
                }
        }
        row.close();
        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "incorrect file");
            return coa;
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "incorrect file");
            return coa;
        }catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, "Why null????");
            return coa;
        }
        return coa;
    }
    //the functio read filter from text file
    public String ReadShowFilter()
    {
        try{
            String filter = "";
            filter = new String(Files.readAllBytes(Paths.get(pathFilter)));
            return filter;
        }catch(IOException ex){
            return "No found filter file";
        }
    }
    //the function read database
    public ArrayList<DataWIFI> ReadUNDO()
    {
        //variables
        ArrayList<DataWIFI> dwf=new ArrayList<>();          //data for file csv
        int j=0;                                            //variables for list of dwf
        try {	
            CsvReader row = new CsvReader(pathUNDO);
            row.readHeaders();
            if (row.getHeaderCount()!=46)		//check that there are 46 headers
            {
                JOptionPane.showMessageDialog(null, "File is not correct");
                row.close();
                return null;
            }
            while (row.readRecord())
            {		
                if (row.getColumnCount()<10 || row.getColumnCount()>46)
                    continue;
                DataWIFI tmpWIFI=new DataWIFI();
                Location place=new Location();
                int cnt=0;		//variables for check MAC,Signal
                int cnt2=0;		//variables for check latitude,longitude
                int max=0;              //number of networks in the list
                boolean flagtime;	//flag for check time
                //location
                try{
                    cnt2+=place.setLat(Double.parseDouble(row.get(2)));
                    cnt2+=place.setLon(Double.parseDouble(row.get(3)));
                    place.setAlt(Double.parseDouble(row.get(4)));
                    max=Integer.parseInt(row.get(5));
                }catch(NumberFormatException e){
                    System.out.println("Err: lat,lon, alt or #WiFi network is no correct");	
                    continue;
                }
                tmpWIFI.setLla(place);
                flagtime=tmpWIFI.setTIME(row.get(0));
                //check if time and location are correct
                if (cnt2!=2 || !flagtime)   
                    continue;
                tmpWIFI.setID(row.get(1));
                //read WiFi data
                for (int i=0;i<max;cnt=0,i++)
                {
                    WIFI tmpWF=new WIFI();
                    try {
                        cnt+=tmpWF.setMAC(row.get(7+i*4));
                        tmpWF.setSSID(row.get(6+i*4));
                        cnt+=tmpWF.setSignal(Integer.parseInt(row.get(9+i*4)));
                        cnt+=tmpWF.setFrequency(Integer.parseInt(row.get(8+i*4)));
                        if (cnt!=3)
                            continue;
                        tmpWIFI.setWiFi(tmpWF);
                        setMAX(tmpWF);
                    }catch(NumberFormatException e){
                        continue;
                    }
                }
            dwf.add(tmpWIFI);
            j++;
        }
        row.close();
        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "kml record not saved\nincorrect file");
            return dwf;
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "kml record not saved\nincorrect file");
            return dwf;
        }catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, "Why null????");
            return dwf;
        }
        return dwf;
    }
    //read old filter
    public String ReadOldFilter()
    {
        try{
            String filter = "";
            filter = new String(Files.readAllBytes(Paths.get(pathOldFilter)));
            return filter;
        }catch(IOException ex){
            return "No found undo filter file";
        }
    }
}