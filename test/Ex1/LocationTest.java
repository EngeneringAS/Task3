/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex1;
//libraries
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for location
 * @author Alexey Titov &   Shalom Weinberger
 * @version 11.2
 */
public class LocationTest {
    @Test
    public void tearDown() throws Exception {
	Location loc=null;
    }
    @Test
    public void testLocationDoubleDoubleDouble() {
    Location loc=new Location(1.2,1.2,1.2);
    }
    @Test
    public void testLocation() {
	Location loc=new Location();
    }
    @Test
    public void testGetLat() {
    	Location loc=new Location(1.2,1.2,1.2);
        assertTrue(1.2==loc.getLat());
    }
    @Test
    public void testSetLat()
    {
        Location loc=new Location();
        assertEquals(0,loc.setLat(-91));		//the latitude should be between -90 and 90
        assertEquals(0,loc.setLat(91));			//the latitude should be between -90 and 90
	assertEquals(1,loc.setLat(0));
    }
    @Test
    public void testGetLon() {
	Location loc=new Location(1.2,1.2,1.2);
        assertTrue(1.2==loc.getLon());
    }
    @Test
    public final void testSetLon() {
    	Location loc=new Location();
	assertEquals(0,loc.setLon(-181));			//the longitude should be between -180 and 180
	assertEquals(0,loc.setLon(181));			//the longitude should be between -180 and 180
	assertEquals(1,loc.setLon(0));
    }
    @Test
    public void testGetAlt() {
	Location loc=new Location(1.2,1.2,1.2);
        assertTrue(1.2==loc.getAlt());
    }
    @Test
    public void testSetAlt() {
        Location loc;
	loc=new Location();
	loc.setAlt(-22.22);
        assertTrue(-22.22==loc.getAlt());
	loc.setAlt(22.22);
	assertTrue(22.22==loc.getAlt());
    }
    @Test
    public void testCompareLLA() {
	Location loc;
        loc=new Location();
	Location tmp=new Location();
	assertEquals(0,loc.compareLLA(tmp));			//equals
	loc.setLat(22.22);
	assertEquals(1,loc.compareLLA(tmp));			//loc is more than tmp
	tmp.setLat(22.22);
	tmp.setAlt(22.22);
	assertEquals(-1,loc.compareLLA(tmp));			//tmp is more than loc
    }
    @Test
    public void testToString() {
	Location loc;
        loc=new Location();
	assertEquals("0.0,0.0,0.0,",loc.toString());
	loc.setLat(11.11);	loc.setLon(-22.22);       loc.setAlt(33.33);
	assertEquals("11.11,-22.22,33.33,",loc.toString());
    }
}
