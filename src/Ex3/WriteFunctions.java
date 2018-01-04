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
import com.csvreader.CsvWriter;
import de.micromata.opengis.kml.v_2_2_0.AltitudeMode;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LineStyle;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;
import java.awt.HeadlessException;
import java.io.BufferedOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * The write functions for help function of Assignment 3
 * @author Alexey Titov   and   Shalom Weinberger
 * @version 1
 */
public class WriteFunctions {
    //variable
    private final String pathDataBase="database//database.csv";         //path to database
    private final String pathUNDO="database//UNDO.csv";                 //path to UNDO database
    private final String pathFilter="database//filter.txt";             //path to filter
    private final String pathOldFilter="database//undofilter.txt";      //path to filter
    /**
     * the function generates and set a placemark object, with the given statistical data
     * @param document structure of the KML file
     * @param folder to add wifi data
     * @param date time when done check
     * @param place location of wifi mark
     * @param dwf wifi data of wifi mark 
     */
    private void createPlacemarkWithChart(Document document, Folder folder,String date,Location place,WIFI dwf)
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
    private void createLine(Document document, Folder folder,ArrayList<DataWIFI> dwf)
    {
        try {
            //variables
            Placemark placemark = folder.createAndAddPlacemark();
            Style style=new Style();
            LineStyle ls=new LineStyle();
            List<Coordinate> coordinates=new ArrayList<>();
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
     * @return path of kml file
     * @throws java.io.IOException dwf is null
     */
    public String WriteKML(ArrayList<DataWIFI> dwf) throws IOException
    {
        //variables
        String OUTkmlFile="";		//output kml file
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
    	    	return "KML File";
            }
        }
        else	//user did not select a file to save
        {
            JOptionPane.showMessageDialog(null, "you did not select a file to save,\ngoodbye");
            return  "KML File";
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
            return "KML File";
    	}
        //print and save
        kml.marshal(new FileWriter(OUTkmlFile,false));
        JOptionPane.showMessageDialog(null, "kml record saved");
        return OUTkmlFile;
    }
    /**
    * the function write csv file
    * @param dwf list of data for csv file
    */
    public void WriteCSV(ArrayList<DataWIFI> dwf)
    {
        try {
            //use FileWriter constructor that specifies open for appending
            CsvWriter csvOutput = new CsvWriter(new FileWriter(pathDataBase, true), ',');	
            //write out a few rows
            for(int i=0;i<dwf.size();i++)              //write rows of data wifi
            {
                String[] tmp=dwf.get(i).toString().split(",");
                csvOutput.writeRecord(tmp);
            }
            csvOutput.close();
            JOptionPane.showMessageDialog(null, "csv record saved");
        }catch(IOException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "csv record not saved");
        }
    }
    //save filter
    public void WriteFilter(Filter filter)
    {
        String s;
        // Convert the string to a byte array
        if (filter==null)
            s="null";
        else
            s = filter.toString();
        byte data[] = s.getBytes();
        Path p = Paths.get(pathFilter);
        try (OutputStream out = new BufferedOutputStream(
             Files.newOutputStream(p))) 
        {
             out.write(data, 0, data.length);
        }catch(IOException x){
            JOptionPane.showMessageDialog(null, "filter.txt record not saved");
        }
    }
    /**
    * the function write csv file
     * @param dwf list of data for csv file
     */
    public void WriteClearCSV(ArrayList<DataWIFI> dwf)
    {
        try {
            // use FileWriter constructor that specifies open for appending
            CsvWriter csvOutput = new CsvWriter(new FileWriter(pathDataBase, false), ',');	
            //headers for first row
            csvOutput.write("TIME");	csvOutput.write("ID");
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
            for(int i=0;i<dwf.size();i++)               //write rows of data wifi
            {
                String[] tmp=dwf.get(i).toString().split(",");
                csvOutput.writeRecord(tmp);
            }
            csvOutput.close();
            JOptionPane.showMessageDialog(null, "UNDO finish");
        } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "UNDO no finish");
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null, "UNDO no finish");
            }
    }
    /**
    * the function write csv file
     * @param dwf list of data for csv file
     */
    public void WriteUNDO(ArrayList<DataWIFI> dwf)
    {
        try {
            // use FileWriter constructor that specifies open for appending
            CsvWriter csvOutput = new CsvWriter(new FileWriter(pathUNDO, false), ',');	
            //headers for first row
            csvOutput.write("TIME");	csvOutput.write("ID");
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
            for(int i=0;i<dwf.size();i++)               //write rows of data wifi
            {
                String[] tmp=dwf.get(i).toString().split(",");
                csvOutput.writeRecord(tmp);
            }
            csvOutput.close();
        } catch (IOException e) {
                return;
            }catch (Exception e) {
                return;
            }
    }
    //write old filter
    public void WriteOldFilter(String old_filter)
    {
        // Convert the string to a byte array
        byte data[] = old_filter.getBytes();
        Path p = Paths.get(pathOldFilter);
        try (OutputStream out = new BufferedOutputStream(
             Files.newOutputStream(p))) 
        {
             out.write(data, 0, data.length);
        }catch(IOException x){
            return;
        }
    }
    //
    public void WriteFilter(String filter)
    {
        // Convert the string to a byte array
        byte data[] = filter.getBytes();
        Path p = Paths.get(pathOldFilter);
        try (OutputStream out = new BufferedOutputStream(
             Files.newOutputStream(p))) 
        {
             out.write(data, 0, data.length);
        }catch(IOException x){
            return;
        }
    }
}
