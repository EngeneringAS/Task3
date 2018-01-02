/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex1;
//libraries
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for WIFI
 * @author Alexey Titov &   Shalom Weinberger
 * @version 11.2
 */
public class WIFITest {
    private WIFI wifi;
    @Before
    public void testWIFI() {
	wifi=new WIFI();
    }
    @After
    public void tearDown() { 
        wifi = null;
    }
    @Test
    public void testGetSSID() {
	wifi=new WIFI();
	assertEquals("N\\A", wifi.getSSID());
	wifi.setSSID("Good");
	assertSame("Good", wifi.getSSID());
    }
    @Test
    public void testSetSSID() {
	wifi=new WIFI();
	wifi.setSSID("Good");
	assertEquals("Good",wifi.getSSID());
	wifi.setSSID("Bad");
	wifi.setSSID("VeryBad");
	assertEquals("VeryBad", wifi.getSSID());
    }
    @Test
    public void testGetMAC() {
	wifi=new WIFI();
	assertEquals("N\\A", wifi.getMAC());
	wifi.setMAC("ec:8c:a2:26:ae:28");
	assertSame("ec:8c:a2:26:ae:28", wifi.getMAC());
    }
    @Test
    public void testCheckNumChar() {
	wifi=new WIFI();
	assertEquals(1,wifi.setMAC("01:23:45:67:89:99"));
	assertEquals(1,wifi.setMAC("aa:bb:cc:dd:ee:ff"));
	assertEquals(1,wifi.setMAC("11:bb:f4:22:cc:ff"));
	assertEquals(0,wifi.setMAC("aa:bb:f4:22:22:vv"));		//incorrect vv
    }
    @Test
    public void testSetMAC() {
	wifi=new WIFI();
	assertEquals(0,wifi.setMAC("01:23:45:67:89"));			//incorrect length
	assertEquals(0,wifi.setMAC("aa:bb:cc:dd:eevff"));		//incorrect number of subsections
    }
    @Test
    public void testGetFrequency() {
	wifi=new WIFI();
	assertEquals(0,wifi.getFrequency());
	wifi.setFrequency(22);
	assertEquals(22,wifi.getFrequency());
    }
    @Test
    public void testSetFrequency() {
	wifi=new WIFI();
	assertEquals(1,wifi.setFrequency(22));
	assertEquals(0,wifi.setFrequency(0));					//the frequency must be greater than zero
        assertEquals(0,wifi.setFrequency(-11));					//the frequency must be greater than zero
    }
    @Test
    public void testGetSignal() {
	wifi=new WIFI();
	assertEquals(0,wifi.getSignal());
	wifi.setSignal(-22);
	assertEquals(-22,wifi.getSignal());
    }
    @Test
    public void testSetSignal() {
	wifi=new WIFI();
	assertEquals(1,wifi.setSignal(-22));
	assertEquals(0,wifi.setSignal(0));					//the signal must be less than zero
	assertEquals(0,wifi.setSignal(22));					//the signal must be less than zero
    }
    @Test
    public void testToString() {
	wifi=new WIFI();
        assertEquals("N\\A,N\\A,0,0",wifi.toString());
	wifi.setSignal(-1);						wifi.setFrequency(1);
	wifi.setMAC("11:11:11:11:11:11");		wifi.setSSID("1");
	assertEquals("1,11:11:11:11:11:11,1,-1",wifi.toString());
    }
    
}
