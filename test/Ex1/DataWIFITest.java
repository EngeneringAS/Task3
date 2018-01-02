/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex1;
//libraries
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for DataWIFI
 * @author Alexey Titov &   Shalom Weinberger
 * @version 11.2
 */
public class DataWIFITest {
    private DataWIFI dwf;
    @Test
    public void tearDown() throws Exception {
        dwf=null;
    }
    @Test
    public void testDataWIFI() {
        dwf=new DataWIFI();
    }
    @Test
    public void testGetTIME() {
	dwf=new DataWIFI();
	Time time=new Time();
	assertEquals(time.getFt(),dwf.getTIME());
	time.setFt("1970-01-01 01:01:01");
	dwf.setTIME("1970-01-01 01:01:01");
	assertEquals(time.getFt(),dwf.getTIME());
    }
    @Test
    public void testGetTIMED() {
	dwf=new DataWIFI();
	Time time=new Time();
	assertEquals(time.getFtD(),dwf.getTIMED());
	time.setFt("1970-01-01 01:01:01");
	dwf.setTIME("1970-01-01 01:01:01");
	assertEquals(time.getFtD(),dwf.getTIMED());
    }
    @Test
    public void testSetTIME() {
	dwf=new DataWIFI();
	assertTrue(dwf.setTIME("1970-01-01 01:01:01"));
        assertFalse(dwf.setTIME("1970-25-25 25:25"));			//incorrect number of subsections
    }
    @Test
    public void testGetID() {
        dwf=new DataWIFI();
	assertEquals("N\\A", dwf.getID());
	dwf.setID("1");
	assertEquals("1", dwf.getID());
    }
    @Test
    public void testSetID() {
	dwf=new DataWIFI();
	dwf.setID("1");
	dwf.setID("22");
	assertEquals("22", dwf.getID());
    }
    @Test
    public void testGetLla() {
	dwf=new DataWIFI();
	Location loc=new Location();
	assertEquals(0,loc.compareLLA(dwf.getLla()));
	loc.setAlt(11);  loc.setLat(-22);  	loc.setLon(0);
        dwf.setLla(loc);
	assertEquals(0,loc.compareLLA(dwf.getLla()));
    }
    @Test
    public void testSetLla() {
	dwf=new DataWIFI();
	Location loc=new Location();
	dwf.setLla(loc);
	assertEquals(0,loc.compareLLA(dwf.getLla()));
	loc.setAlt(11);  loc.setLat(-22);  	loc.setLon(0);
	dwf.setLla(loc);
        assertEquals(0,loc.compareLLA(dwf.getLla()));
    }
    @Test
    public void testGetWIFINetwork() {
        dwf=new DataWIFI();
        assertEquals(0,dwf.getWIFINetwork());
        dwf.setWIFINetwork(8);
        assertEquals(8,dwf.getWIFINetwork());
    }
    @Test
    public void testSetWIFINetwork() {
	dwf=new DataWIFI();
	dwf.setWIFINetwork(8);
	assertEquals(8,dwf.getWIFINetwork());
	dwf.setWIFINetwork(-1);						//the WIFINetwork should be between 0 and 10
	assertEquals(8,dwf.getWIFINetwork());
	dwf.setWIFINetwork(11);						//the WIFINetwork should be between 0 and 10
	assertEquals(8,dwf.getWIFINetwork());
    }
    /**
     * the function creates array list of WIFI
     * @return array list of WIFI of two variables
     */
    public ArrayList<WIFI> addWIFI()
    {
        ArrayList<WIFI> wifi=new ArrayList<WIFI>();
        WIFI tmp1=new WIFI();
        WIFI tmp2=new WIFI();
        tmp1.setSignal(-1);				tmp1.setFrequency(1);
        tmp1.setMAC("11:11:11:11:11:11");		tmp1.setSSID("1");
        wifi.add(tmp1);
        tmp2.setSignal(-2);				tmp2.setFrequency(2);
        tmp2.setMAC("22:22:22:22:22:22");		tmp2.setSSID("2");
        wifi.add(tmp2);
        return wifi;
    }
    @Test
    public void testGetWiFi() {
	dwf=new DataWIFI();
	ArrayList<WIFI> wifi=new ArrayList<WIFI>();
	wifi=addWIFI();
	assertNotNull(dwf.getWiFi());
	dwf.setWiFi(wifi.get(0));		dwf.setWiFi(wifi.get(1));
	assertEquals(wifi,dwf.getWiFi());
    }
    @Test
    public void testSetWiFi() {
	dwf=new DataWIFI();
	WIFI tmp=new WIFI();
	ArrayList<WIFI> wifi=new ArrayList<WIFI>();
	tmp.setSignal(-3);				tmp.setFrequency(3);
	tmp.setMAC("33:33:33:33:33:33");		tmp.setSSID("3");
	wifi=addWIFI();
	dwf.setWiFi(wifi.get(0));		dwf.setWiFi(wifi.get(1));
	assertEquals(wifi,dwf.getWiFi());
	wifi.add(tmp);					dwf.setWiFi(tmp);
	assertEquals(wifi,dwf.getWiFi());
    }
    @Test
    public void testCopyWiFi() {
	dwf=new DataWIFI();
	WIFI tmp=new WIFI();
	ArrayList<WIFI> wifi=new ArrayList<WIFI>();
	wifi=addWIFI();
	tmp.setSignal(-1);				tmp.setFrequency(1);
	tmp.setMAC("11:11:11:11:11:11");		tmp.setSSID("1");
	dwf.setWiFi(wifi.get(0));   		       dwf.setWiFi(wifi.get(1));
	dwf.setWiFi(tmp);			//tmp copy of wifi.get(0)
	assertEquals(wifi,dwf.getWiFi());
    }
    @Test
    public void testToString() {
	dwf=new DataWIFI();
	ArrayList<WIFI> wifi=new ArrayList<WIFI>();
	dwf.setTIME("1970-01-01 01:01:01");
	assertEquals("1970-01-01 01:01:01,N\\A,0.0,0.0,0.0,0,",dwf.toString());
	dwf.setLla(new Location(1,1,1));		dwf.setID("1");
	wifi=addWIFI();
	dwf.setWiFi(wifi.get(0));				dwf.setWiFi(wifi.get(1));
	assertEquals("1970-01-01 01:01:01,1,1.0,1.0,1.0,2,1,11:11:11:11:11:11,1,-1,2,22:22:22:22:22:22,2,-2,",dwf.toString());
    }
}
