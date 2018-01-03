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
 * test for LocationFilter class
 * @author Alexey Titov   and   Shalom Weinberger
 * @version 1
 */
public class LocationFilterTest {
    /**
     * Test of Compare method, of class LocationFilter.
     */
    @Test
    public void testCompare() {
        DataWIFI arg = new DataWIFI();
        LocationFilter instance = new LocationFilter();
        boolean expResult = false;
        boolean result = instance.Compare(arg);
        assertEquals(expResult, result);
    }
    /**
     * Test of toString method, of class LocationFilter.
     */
    @Test
    public void testToString() {
        LocationFilter instance = new LocationFilter();
        String expResult = "Position(radius<0.0,center=(0.0,0.0)";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
