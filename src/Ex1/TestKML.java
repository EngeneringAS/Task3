package Ex1;
/**
 * main for question 3
 * @author Alexey Titov &   Shalom Weinberger
 * @version 11.2
 */
//libraries
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DateFormatter;
import javax.swing.text.NumberFormatter;
import com.csvreader.CsvReader;
import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LineStyle;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;
import de.micromata.opengis.kml.v_2_2_0.Icon;

public class TestKML extends JFrame {

    //global variables
    private static final long serialVersionUID = 1L;						//for class
    private static Map<String,Integer>  MaxSignal=new HashMap<>();				//map max signal of wifi
    private CompareDataWifi cdw;								//compare data wifi and filtering data wifi
    private JFormattedTextField ID;								//text field of user id		
    private JFormattedTextField FromDate;							//text field of from time
    private JFormattedTextField UNTILDate;							//text field of until time
    private JFormattedTextField X;								//text field of latitude
    private JFormattedTextField Y;								//text field of longitude
    private JFormattedTextField Radius;								//text field of radius
    private DataWIFI Filter1=new DataWIFI();							//from
    private DataWIFI Filter2=new DataWIFI();									//until
    //define
    private  final  String  BUTTON_NAME1 = "User ID"; 
    private  final  String  BUTTON_NAME2 = "Time";
    private  final  String  BUTTON_NAME3 = "Location"; 
    /**
     * create a selection panel
     */
    public TestKML()
    {
        super("Choose filter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //content panel
        Container container = getContentPane();
        //install the sequential location manager
        container.setLayout(new FlowLayout());
        //creating buttons that perform one action
        Action action = new SimpleAction();
        //define a mask and date entry field
     	//SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     	//formatting date object
     	DateFormatter dateFormatter = new DateFormatter(date);
     	dateFormatter.setAllowsInvalid(false);
     	dateFormatter.setOverwriteMode(true);
        //buttons
        JButton button1 = new JButton(action);
        button1.setName(BUTTON_NAME1);
        button1.setText("User ID");
        JButton button2 = new JButton(action);
        button2.setName(BUTTON_NAME2);
        button2.setText("Time");
        JButton button3 = new JButton(action);
        button3.setName(BUTTON_NAME3);
        button3.setText("Location");
        container.add(button1);
        container.add(button2);
        container.add(button3);
        //create a formatted id text field
        container.add(new JLabel("User ID:"));
        ID = new JFormattedTextField("User ID");
		ID.setColumns(20);
		container.add(ID);
        //create a formatted date text field
        FromDate = new JFormattedTextField(dateFormatter);
		FromDate.setColumns(20);
		FromDate.setValue(new Date());
		UNTILDate = new JFormattedTextField(dateFormatter);
		UNTILDate.setColumns(20);
		UNTILDate.setValue(new Date());
        container.add(new JLabel("Date from:"));
        container.add(FromDate);
        container.add(new JLabel("Date up to:"));
        container.add(UNTILDate);
        //create a formatted location text field
        NumberFormat number = new DecimalFormat("000.00000000");
        container.add(new JLabel("Location :"));
        X = new JFormattedTextField(new NumberFormatter(number));
        Y = new JFormattedTextField(new NumberFormatter(number));
        X.setColumns(5);
        Y.setColumns(5);
        container.add(new JLabel("Lat"));
        container.add(X);
        container.add(new JLabel("Lon"));
        container.add(Y);
        Radius = new JFormattedTextField(new NumberFormatter(number));
        Radius.setColumns(5);
        container.add(new JLabel("Radius= "));
        container.add(Radius);
        //display the window on the screen
		setContentPane(container);
        setSize(280, 300);						//size of window
        setVisible(true);						//make the panel visible
        setResizable(false);					//disallow resizing to panel
    }
    /**
     * the function setting max value of wifi signal to map
     * @param wf wifi network
     * @return true if replace was, false if replace was not (ONLY FOR TestKMLTest) 
     */
	public boolean setMAX(WIFI wf)
	{
		Integer WFsignal=wf.getSignal();
		if (!MaxSignal.containsKey(wf.getMAC()))				//there is no such MAC in the map
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
	public boolean Search(String mac,int signal)
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
	public ArrayList<DataWIFI> RemoveWorseSignal(ArrayList<DataWIFI> DWF)
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
	/**
	 * the function generates and set a placemark object, with the given statistical data
	 * @param document structure of the KML file
	 * @param folder to add wifi data
         * @param date-time
	 * @param place location of wifi mark
	 * @param dwf wifi data of wifi mark 
	 */
	public void createPlacemarkWithChart(Document document, Folder folder,String date,Location place,WIFI dwf)
	{
		try {
			Placemark placemark = folder.createAndAddPlacemark();
			//name of mark and style
			placemark.withName(dwf.getSSID()).withStyleUrl("#style_place")
			//description of mark
			.withDescription("MAC: "+dwf.getMAC()+"<br/>Frequency: <b>"+dwf.getFrequency()+"</b><br/>Signal: <b>"+dwf.getSignal()+"</b>");
			//set coordinates
			placemark.createAndSetPoint().addToCoordinates(place.getLon(),place.getLat(),place.getAlt());
			//set when
			date=date.replace(' ', 'T')+'Z';
			placemark.createAndSetTimeStamp().setWhen(date);
		}catch(NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Why null????");
		}
	}
	/**
	 * the function generates and set a placemark object, with the given statistical data
	 * @param document structure of the KML file
	 * @param folder to add time line
	 * @param dwf list wifi data of wifi mark 
	 */
	public void createLine(Document document, Folder folder,ArrayList<DataWIFI> dwf)
	{
		try {
			//variables
			Placemark placemark = folder.createAndAddPlacemark();
			Style style=new Style();
			LineStyle ls=new LineStyle();
			List<Coordinate> coordinates=new ArrayList<Coordinate>();
			//set style
			ls.setWidth(2);
			ls.setColor("ff0ff0ff");
			style.setLineStyle(ls);
			//set coordinates
			for(int i=0;i<dwf.size();i++)
			{
				Coordinate coordinate = new Coordinate(dwf.get(i).getLla().getLon(),dwf.get(i).getLla().getLat(),dwf.get(i).getLla().getAlt());
				coordinates.add(coordinate);
			}
			//add name
			placemark.withName("Time Line");
			//add style
			placemark.createAndAddStyle().setLineStyle(ls);
			//add line string
			placemark.createAndSetLineString().withTessellate(true).withCoordinates(coordinates).withAltitudeMode(AltitudeMode.CLAMP_TO_GROUND);
		}catch(NullPointerException e) {
			JOptionPane.showMessageDialog(null, "Why null????");
		}
	}
	/**
	 * the function write kml file
	 * @param dwf list of data for kml file
         * @throws java.io.IOException null
	 */
	public void WriteKML(ArrayList<DataWIFI> dwf) throws IOException
	{
            //variables
            String OUTkmlFile="";								//output kml file
            final Kml kml = new Kml();
            Document doc = kml.createAndSetDocument().withName("WIFI").withOpen(true);
            //create a Folder
            Folder folder = doc.createAndAddFolder();
            folder.withName("WiFi Signal").withOpen(true);
            //select the location of the file
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.KML","*.*");
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        if ( fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
            try {
            	//user wrote at the end kml
            	if (fc.getSelectedFile().getAbsolutePath().substring(fc.getSelectedFile().getAbsolutePath().length()-4).equals(".kml"))
            		OUTkmlFile=fc.getSelectedFile().getAbsolutePath();
            	else	//user did not wrote at the end kml
            		OUTkmlFile=fc.getSelectedFile().getAbsolutePath()+".kml";
            }
            catch (Exception e ) {
            	JOptionPane.showMessageDialog(null, "an error occurred, the file was not saved.\ngoodbye");
    	    	return;
            }
        }
        else	//user did not select a file to save
        {
        	JOptionPane.showMessageDialog(null, "you did not select a file to save,\ngoodbye");
	    	return;
        }
        //create placemark elements
        try {
        	Icon icon = new Icon()
        	.withHref("http://maps.google.com/mapfiles/ms/icons/green-dot.png");
        	Style style = doc.createAndAddStyle();
        	style.withId("style_place") // set the stylename to use this style from the placemark
        	.createAndSetIconStyle().withScale(1.0).withIcon(icon); // set size and icon
        	style.createAndSetLabelStyle().withColor("ff0ff0ff").withScale(1.0); // set color and size of the wifi name
        	//wifi data
	        for (int j=0;j<dwf.size();j++)
	        {
	        	int counter=dwf.get(j).getWIFINetwork();
	            int i=0;
	            while (counter!=0)
	            {
	            	//create placemark for wifi
	            	try {
	            		createPlacemarkWithChart(doc,folder,dwf.get(j).getTIME(),dwf.get(j).getLla(),dwf.get(j).getWiFi().get(i));
	            	}catch(IndexOutOfBoundsException e){
	                	System.out.println("Err");
	                }
	                counter--;
	                i++;
	            }
	        }
	        createLine(doc,folder,dwf);
        }catch (Exception e){
    		JOptionPane.showMessageDialog(null, "kml record not saved");
     		return;
    	}
        //print and save
		kml.marshal(new FileWriter(OUTkmlFile,false));
        JOptionPane.showMessageDialog(null, "kml record saved");
	}
	/**
	 * the function read csv file
	 * @param csvFile name cvs file
	 * @return list of data for kml file
	 */
	public ArrayList<DataWIFI> ReadCSV(String csvFile)
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
                int cnt=0;					//variables for check MAC,Signal
				int cnt2=0;					//variables for check latitude,longitude
				int max=0;					//number of networks in the list		
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
                //filtering
                if (!cdw.filter(tmpWIFI,Filter1,Filter2))
                	continue;
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
			JOptionPane.showMessageDialog(null, "kml record not saved\nincorrect file");
     		return dwf;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "kml record not saved\nincorrect file");
     		return dwf;
		}catch(IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "Why null????");
			return dwf;
		}
		//sorting to dwf according to cdw
		Collections.sort(dwf,cdw);
		return RemoveWorseSignal(dwf);
	}
	/**
	 * the function processes the data of csv file and stores in kml file
	 */
    private void Program()
    {
    	//variables
		ArrayList<DataWIFI> DWF=new ArrayList<DataWIFI>();		//data of WiFiNetwork
		int ret;							//flag for check a directory is selection
		JFileChooser fileopen = new JFileChooser();
		String csvFile ="";						//input csv file
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
		try {
			WriteKML(DWF);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * internal class
     */
    class SimpleAction extends AbstractAction {
       private static final long serialVersionUID = 1L;							//for class
        SimpleAction() {
                //command parameters
                putValue(SHORT_DESCRIPTION, "If you want so, then click");
        }
        /**
         * processing the button click event
         */
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            //user choose to filter by ID
            if (btn.getName().equalsIgnoreCase(BUTTON_NAME1)) 
            {
            	//check if text field is null
            	if (ID.getValue().equals(""))
            	{
            		JOptionPane.showMessageDialog(null, "ID field is null");
            		return;
            	}
            	//transference date from text field to string
            	Filter1.setID(ID.getValue().toString());
            	cdw=new CompareDataWifi(){
    				public int compare(DataWIFI arg1,DataWIFI arg2)						//the function sorting
    				{
    					if (arg1==null && arg2==null)
    						return 0;
    					if (arg1==null)
    						return -1;
    					if (arg2==null)
    						return 1;
    					return arg1.getID().compareTo(arg2.getID());
    				}
    				public boolean filter(DataWIFI arg1,DataWIFI arg2,DataWIFI arg3)	//the function filter
    				{
    					if (arg1.getID().equals(arg2.getID()))
    						return true;
    					return false;
    				}
    			};
            }
            else
            	if (btn.getName().equalsIgnoreCase(BUTTON_NAME2))	//user choose to filter by TIME
            	{
            		//time variables 
            		Date dt1=new Date();			//time from
            		Date dt2=new Date();			//time until
            		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            		//string variables and transference date from text field to string
            		String str1=dateformat.format(FromDate.getValue());
            		String str2=dateformat.format(UNTILDate.getValue());
            		try {
						dt1=dateformat.parse(str1);
						dt2=dateformat.parse(str2);
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(null, "Time is not correct");
            			return;
					}
            		if (dt2.compareTo(dt1)==0 || dt2.compareTo(dt1)==-1)		//from and until time are equals or until time before from time
            		{
            			JOptionPane.showMessageDialog(null, "Time is not correct");
            			return;
            		}
            		Filter1.setTIME(dateformat.format(dt1));
            		Filter2.setTIME(dateformat.format(dt2));
            		cdw=new CompareDataWifi(){
    					public int compare(DataWIFI arg1,DataWIFI arg2)						//the function sorting
    					{
    						if (arg1==null && arg2==null)
    							return 0;
    						if (arg1==null)
    							return -1;
    						if (arg2==null)
    							return 1;
    						return arg1.getTIMED().compareTo(arg2.getTIMED());
    					}
    					public boolean filter(DataWIFI arg1,DataWIFI arg2,DataWIFI arg3)	//the function filter
        				{
        					if (arg1.getTIMED().after(arg2.getTIMED()) && arg1.getTIMED().before(arg3.getTIMED()))
        						return true;
        					return false;
        				}
    				};
            	}
            	else								//user choose to filter by location
            	{
            		//local variables
            		Location l1=new Location();
            		double radius_location=0;
            		//transference date from text field to string
            		String x1=X.getValue().toString().replace(',', '.');			//latitude
            		String y1=Y.getValue().toString().replace(',', '.');			//longitude
            		String radius=Radius.getValue().toString().replace(',', '.');	//radius
            		int cnt=0;
            		try {
            			cnt+=l1.setLat(Double.parseDouble(x1));
            			cnt+=l1.setLon(Double.parseDouble(y1));
            			radius_location=Double.parseDouble(radius);
            			cnt++;
            		}catch(NumberFormatException ex){
            			JOptionPane.showMessageDialog(null, "Location is not correct");
            			return;
            		}
            		//check if 3 data is not correct
            		if (cnt!=3 || radius_location<0 || radius_location>=1)
            		{
            			JOptionPane.showMessageDialog(null, "Location or Radius is not correct");
            			return ;
            		}
            		//input 
            		Filter1.setLla(l1);
            		Location tmp=new Location();
            		tmp.setAlt(radius_location);
            		Filter2.setLla(tmp);
            		cdw=new CompareDataWifi(){
    					public int compare(DataWIFI arg1,DataWIFI arg2)							//the function sorting
    					{
    						if (arg1==null && arg2==null)
    							return 0;
    						if (arg1==null)
    							return -1;
    						if (arg2==null)
    							return 1;
    						return arg1.getLla().compareLLA(arg2.getLla());
    					}
    					public boolean filter(DataWIFI arg1,DataWIFI arg2,DataWIFI arg3)		//the function filter
        				{
        					double x=Math.pow((arg1.getLla().getLat()-arg2.getLla().getLat()), 2);
        					double y=Math.pow((arg1.getLla().getLon()-arg2.getLla().getLon()), 2);
    						if ((x+y)<=arg2.getLla().getAlt())
        						return true;
        					return false;
        				}
    				};
            	}
            setVisible(false);
            Program();
            System.exit(0);
        }  
    };
    //main
    public static void main(String[] args) {
        	new TestKML();
    }
}
