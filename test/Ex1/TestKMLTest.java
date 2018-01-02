/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex1;

import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for TestKML
 * @author Alexey Titov &   Shalom Weinberger
 * @version 11.2
 */
public class TestKMLTest {
    @Test
    public void testSetMAX() {
	//variables
	TestKML test_kml=new TestKML();
	WIFI wf=new WIFI();
	//initialization
	wf.setSignal(-10);			wf.setFrequency(10);
	wf.setSSID("MY_WIFI");			wf.setMAC("10:0d:7f:99:41:ae");
	assertFalse(test_kml.setMAX(wf));								//first data
	//change signal to better signal
	wf.setSignal(-1);
	assertTrue(test_kml.setMAX(wf));								//data with better signal 
    }
    @Test
    public void testSearch() {
	//variables
	TestKML test_kml=new TestKML();
	WIFI wf1=new WIFI();
	WIFI wf2=new WIFI();
	String mac1="10:0d:7f:99:41:ae";
	String mac2="10:0d:7f:99:41:11";
	int signal1=-11;
	int signal2=-22;
	//initialization
	wf1.setSignal(-10);			wf1.setFrequency(10);
	wf1.setSSID("MY_WIFI");			wf1.setMAC(mac1);
	test_kml.setMAX(wf1);	
	wf2.setSignal(-22);			wf2.setFrequency(10);
	wf2.setSSID("MY_WIFI");			wf2.setMAC(mac2);
	test_kml.setMAX(wf2);
	assertTrue(test_kml.Search(mac1,signal1));				//weak signal for the mac 
	assertFalse(test_kml.Search(mac2,signal2));				//max signal for the mac 
    }
    @Test
    public void testRemoveWorseSignal() {
	//variables
	TestKML test_kml=new TestKML();
	ArrayList<DataWIFI> test_list=new ArrayList<DataWIFI>();
	test_list=test_kml.RemoveWorseSignal(test_list);				//list is empty
	test_list=test_kml.RemoveWorseSignal(null);						//null
    }
    @Test
    public void testCreatePlacemarkWithChart() {
	//variables
	TestKML test_kml=new TestKML();
	test_kml.createPlacemarkWithChart(null, null, null, null, null);
    }
    @Test
    public void testCreateLine() {
	//variables
	TestKML test_kml=new TestKML();
	ArrayList<DataWIFI> test_list=new ArrayList<DataWIFI>();
        test_kml.createLine(null, null, test_list);
    }
    @Test
    public void testWriteKML() throws IOException {
	//variables
	TestKML test_kml=new TestKML();
	ArrayList<DataWIFI> test_list=new ArrayList<DataWIFI>();
	test_kml.WriteKML(test_list);
    }
    @Test
    public void testReadCSV() {
	//variable
	TestKML test_kml=new TestKML();
	test_kml.ReadCSV("noo");			//file does not exist
	test_kml.ReadCSV(null);				//null
    }
}
