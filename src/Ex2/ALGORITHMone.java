package Ex2;
/**
 * class for first algorithm
 * @author Alexey Titov &   Shalom Weinberger
 * @version 2.0
 */
//libraries
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import Ex1.DataWIFI;
import Ex1.Location;
import Ex1.WIFI;

public class ALGORITHMone
{
    /**
     * the function read csv files
     * @param csvFiles list of names cvs files
     * @param MAC_NAME the MAC name whose data we need
     * @return up to limit data that we need
     */
	private static ALGOoneCLASS ReadCSV(ArrayList<String> csvFiles)
	{
		//variables
		ALGOoneCLASS dwf=new ALGOoneCLASS();
		String IDphone="";											//id of cellular phone
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
						int cnt=0;					//variables for check MAC,Signal
						int cnt2=0;					//variables for check latitude,longitude
						boolean flagtime;			//flag for check time
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
							cnt+=wf.setSignal((int) Double.parseDouble(row.get(5)));
						}catch(NumberFormatException e){
							continue;
						}
						flagtime=tmpWIFI.setTIME(row.get(3));
						//check if MAC,Signal,time and location are correct
						if (cnt!=3 || cnt2!=2 || !flagtime)   
							continue;
						dwf.setDATAmac(wf.getMAC(),place,wf.getSignal());
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
	 * the function write csv file
	 * @param dwf list of data for csv file
	 */
	private static void WriteCSV(ALGOoneCLASS dwf)
	{
		//variable
		String OUTcsvFile="";									//output csv file
		HelpFunctions hlp=new HelpFunctions();
		int index=1;											//counter of row
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
			csvOutput.write("?");
			csvOutput.write("latitude");	csvOutput.write("longitude");	csvOutput.write("altitude");
			csvOutput.write("MAC");			csvOutput.write("Signal");
			csvOutput.endRecord();
			//write out a few rows
			Map<String,ArrayList<LocSig>> tmp=dwf.getDATAmac();
			//write rows of center location
			for (Map.Entry<String,ArrayList<LocSig>> entry : tmp.entrySet())
			{
				ClassOfAlgorithm1 coa=new ClassOfAlgorithm1();			//data of MAC
				Location center=new Location();
				String str;
				if (!coa.setMAC(entry.getKey()))
					continue;
				for (int i=0;i<entry.getValue().size();i++)
					coa.setSignaList(entry.getValue().get(i).getSignal(),entry.getValue().get(i).getLla());
				center=hlp.WriteWeight(coa);
				str=index+","+center.toString()+coa.getMAC()+","+hlp.AverageSignal(coa);
				csvOutput.writeRecord(str.split(","));
				index++;
			}
			csvOutput.close();
			JOptionPane.showMessageDialog(null, "csv record saved");
		} catch (IOException e) {
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
    	ArrayList<String> files=new ArrayList<String>();		//list csv files
    	ALGOoneCLASS DWF=new ALGOoneCLASS();					//data of WiFiNetwork
    	int ret;
		//File chooser
		JFileChooser fileopen = new JFileChooser();
		//only directory
		fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileopen.setAcceptAllFileFilterUsed(false);
		//dialog box for determining the desired directory
		ret=fileopen.showDialog(null, "Open directory");
		//check if directory is not selected
		if (!(ret == JFileChooser.APPROVE_OPTION))
		{
			JOptionPane.showMessageDialog(null, "Directory is not selected\ngoodbye");
		    System.exit(2);
		}	
		File []fList;        
		File F = new File(fileopen.getSelectedFile().getAbsolutePath());  //the path to the directory
		//all files that are in the folder     
		fList = F.listFiles();
		//runs at the folder.
		for(int i=0; i<fList.length; i++)           
		{
			String mark=fList[i].getName();  
			//check if name is csv file
			if(fList[i].isFile() && mark.substring(mark.length()-3).equals("csv"))
				files.add(fileopen.getSelectedFile().getAbsolutePath()+"\\"+fList[i].getName());
		}
		//no files
		if (files.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "in the folder there are no csv files");
		    System.exit(2);
		}
		else
			DWF=ReadCSV(files);
		try {
			WriteCSV(DWF);
		}catch(NullPointerException e){
			System.out.println("NULL :(");
		}
    }
    public static void main(String[] args) 
    {
    	Program();
    }
}
