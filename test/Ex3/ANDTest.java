/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex3;
//libraries
import Ex1.DataWIFI;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * test for AND class
 * @author Alexey Titov   and   Shalom Weinberger
 * @version 1
 */
public class ANDTest {
    /**
     * Test of Compare method, of class AND.
     */
    @Test
    public void testCompare() {
        DataWIFI arg = new DataWIFI();
        arg.setTIME("2017-11-02 09:19:07");
        arg.setID("new");
        AND instance = new AND(new TimeFilter("2017-11-02 09:19:00","2017-11-02 09:19:09"),new DeviceFilter("new"));
        boolean expResult = true;
        boolean result = instance.Compare(arg);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class AND.
     */
    @Test
    public void testToString() {
        AND instance = new AND(new TimeFilter("2017-11-02 09:19:00","2017-11-02 09:19:09"),new DeviceFilter("new"));
        String expResult = "(Time(2017-11-02 09:19:00<data<2017-11-02 09:19:09) && Device(name==new))";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
