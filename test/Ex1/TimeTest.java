/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex1;
//libraries
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Time
 * @author Alexey Titov &   Shalom Weinberger
 * ID:     334063021    &   203179403
 * @version 11.2
 */
public class TimeTest {
    private Time time;
    @Test
    public void setUp() throws Exception {
	time=new Time();
    }
    @After
    public void tearDown() { 
         time = null;
    }
    @Test
    public void testSetFt() {
        time=new Time();
	assertTrue(time.setFt("1970-01-01 01:01:01"));
	assertFalse(time.setFt("1970-25-25 25:25"));			//incorrect number of subsections
	assertTrue(time.setFt("1970-25-25 25:25:66"));			//incorrect time
    }
    @Test
    public void testGetFt() {
        time=new Time();
    	Time tmp=new Time();
	assertEquals(tmp.getFt(),time.getFt());
    }
    @Test
    public void testGetFtD() {
        time=new Time();
	Time tmp=new Time();
	assertEquals(tmp.getFtD(),time.getFtD());
    }  
}
