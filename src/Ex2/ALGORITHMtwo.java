package Ex2;
/**
 * class for second algorithm
 * @author Alexey Titov &   Shalom Weinberger
 * @version 2.0
 */
//libraries
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import Ex1.DataWIFI;
import Ex1.Location;
import Ex1.WIFI;

public class ALGORITHMtwo
{
    /**
	 * the function read csv file
	 * @param csvFiles names csv file
	 * @return list of data for csv file with '?'
	 */
	private static ArrayList<DataWIFI> ReadCSV(String csvFile)
	{
		//variables
		ArrayList<DataWIFI> dwf=new ArrayList<DataWIFI>();			//data for file csv
		int j=0;													//variables for list of dwf
		try {	
			CsvReader row = new CsvReader(csvFile);
			row.readHeaders();
			if (row.getHeaderCount()!=46)							//check that there are 46 headers
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
                		cnt+=tmpWF.setSignal((int) Double.parseDouble(row.get(9+i*4)));
                		cnt+=tmpWF.setFrequency(Integer.parseInt(row.get(8+i*4)));
                		if (cnt!=3)
                			continue;
                		dwf.get(j).setWiFi(tmpWF);
                	}catch(NumberFormatException e){
                		System.out.println("Err, WIFI");
                		continue;
                	}
                }
                j++;
			}
			row.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "csv record not saved\nincorrect file");
     		return dwf;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "csv record not saved\nincorrect file");
     		return dwf;
		}catch(IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "Why null????");
			return dwf;
		}
		return dwf;
	}
	/**
	 * the function read csv file
	 * @param csvFile names cvs files
	 * @param DWF
	 * @return list of data for csv file
	 */
	private static ArrayList<DataWIFI> ReadCSV(String csvFile,ArrayList<DataWIFI> DWF)
	{
		//variables
		ArrayList<DataWIFI> dwf=new ArrayList<DataWIFI>();			//data for file csv
		HelpFunctions hlp=new HelpFunctions();
		int j=0;													//variables for list of dwf
		try {	
			CsvReader row = new CsvReader(csvFile);
			row.readHeaders();
			if (row.getHeaderCount()!=46)							//check that there are 46 headers
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
                int cnt=0;					//variables for check MAC,Signal
				int max=0;			//number of networks in the list		
				boolean flagtime;		//flag for check time
                //location
                try{
                	max=Integer.parseInt(row.get(5));
                }catch(NumberFormatException e){
                	System.out.println("Err: #WiFi network is no correct");	
                	continue;
                }
                tmpWIFI.setLla(place);
                flagtime=tmpWIFI.setTIME(row.get(0));
                //check if time is correct
				if (!flagtime)   
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
                		cnt+=tmpWF.setSignal((int) Double.parseDouble(row.get(9+i*4)));
                		cnt+=tmpWF.setFrequency(Integer.parseInt(row.get(8+i*4)));
                		if (cnt!=3)
                			continue;
                		dwf.get(j).setWiFi(tmpWF);
                	}catch(NumberFormatException e){
                		System.out.println("Err,WIFI");
                		continue;
                	}
                }
                j++;
                ArrayList<ALGOtwoCLASS> data=hlp.Find(DWF,dwf.get(j-1).getWiFi());
                place= hlp.WeightAlgo2(dwf.get(j-1).getWiFi(),data);
                if (place!=null)
                	dwf.get(j-1).setLla(place);
                else
                {
                	dwf.remove(j-1);
                	j--;
                }
			}
			row.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "csv record not saved\nincorrect file");
     		return dwf;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "csv record not saved\nincorrect file");
     		return dwf;
		}catch(IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "Why null????");
			return dwf;
		}
		return dwf;
	}
	/**
	 * the function write csv file
	 * @param dwf list of data for csv file
	 */
	private static void WriteCSV(ArrayList<DataWIFI> dwf)
	{
		//variable
		String OUTcsvFile="";									//output csv file
		//select the location of the file
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.CSV","*.*");
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(filter);
		if ( fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
			try {
				//user wrote at the end csv
				if (fc.getSelectedFile().getAbsolutePath().substring(fc.getSelectedFile().getAbsolutePath().length()-4).equals(".csv"))
					OUTcsvFile=fc.getSelectedFile().getAbsolutePath();
				else 	//user did not wrote at the end csv
					OUTcsvFile=fc.getSelectedFile().getAbsolutePath()+".csv";
			}catch (Exception e ) {
				JOptionPane.showMessageDialog(null, "an error occurred, the file was not saved.\ngoodbye");
				return;
			}
		}
		else	//user did not select a file to save
		{
			JOptionPane.showMessageDialog(null, "you did not select a file to save,\ngoodbye");
		    return;
	    }	
		try {
			// use FileWriter constructor that specifies open for appending
			CsvWriter csvOutput = new CsvWriter(new FileWriter(OUTcsvFile, false), ',');	
     		//headers for first row
			csvOutput.write("TIME");		csvOutput.write("ID");
			csvOutput.write("latitude");	csvOutput.write("longitude");	csvOutput.write("altitude");
			csvOutput.write("#WiFi networks");
			for (int i=1;i<11;i++)
			{
				csvOutput.write("SSID"+i);
				csvOutput.write("MAC"+i);
				csvOutput.write("Frequency"+i);
				csvOutput.write("Signal"+i);
			}
			csvOutput.endRecord();
			//write out a few rows
			for(int i=0;i<dwf.size();i++)							//write rows of data wifi
			{
				String[] tmp=dwf.get(i).toString().split(",");
				csvOutput.writeRecord(tmp);
			}
			csvOutput.close();
			JOptionPane.showMessageDialog(null, "csv record saved");
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null, "csv record not saved");
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "csv record not saved");
		}
	}
    /**
	 * the function processes the data of csv file 
	 */
    private static void Program()
    {
    	//variables
    	ArrayList<DataWIFI> DWF=new ArrayList<DataWIFI>();		//data of WiFiNetwork
    	int ret;												//flag for check a directory is selection
		JFileChooser fileopen = new JFileChooser();
		String csvFile ="";										//input csv file
		FileNameExtensionFilter filter = new FileNameExtensionFilter("WIGLE-WIFI CSV FILES","csv");
		//open window
        fileopen.setFileFilter(filter);
		ret = fileopen.showDialog(null, "Open csv file");                
	    if (ret == JFileChooser.APPROVE_OPTION &&
	        fileopen.getSelectedFile().getAbsolutePath().substring(fileopen.getSelectedFile().getAbsolutePath().length()-3).equals("csv"))
	    {
	    	csvFile=fileopen.getSelectedFile().getAbsolutePath();
	    }
	    else
	    {
	    	JOptionPane.showMessageDialog(null, "file is not selected or the file type is not valid,\ngoodbye");
	    	System.exit(2);
	    }
	    DWF=ReadCSV(csvFile);
	    //open window for second file with '?'
        fileopen.setFileFilter(filter);
		ret = fileopen.showDialog(null, "Open csv file");                
	    if (ret == JFileChooser.APPROVE_OPTION &&
	        fileopen.getSelectedFile().getAbsolutePath().substring(fileopen.getSelectedFile().getAbsolutePath().length()-3).equals("csv"))
	    {
	    	csvFile=fileopen.getSelectedFile().getAbsolutePath();
	    }
	    else
	    {
	    	JOptionPane.showMessageDialog(null, "file is not selected or the file type is not valid,\ngoodbye");
	    	System.exit(2);
	    }
	    DWF=ReadCSV(csvFile,DWF);
		WriteCSV(DWF);
    } 
    //main
    public static void main(String[] args) 
    {
    	Program();
    }
}