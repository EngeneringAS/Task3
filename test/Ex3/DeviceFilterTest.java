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
 * test for DeviceFilter class
 * @author Alexey Titov   and   Shalom Weinberger
 * @version 1
 */
public class DeviceFilterTest {
    /**
     * Test of Compare method, of class DeviceFilter.
     */
    @Test
    public void testCompare() {
        DataWIFI arg = new DataWIFI();
        arg.setID("new");
        DeviceFilter instance = new DeviceFilter("old");
        boolean expResult = false;
        boolean result = instance.Compare(arg);
        assertEquals(expResult, result);
    }
    /**
     * Test of toString method, of class DeviceFilter.
     */
    @Test
    public void testToString() {
        DeviceFilter instance = new DeviceFilter("old");
        String expResult = "Device(name==old)";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
