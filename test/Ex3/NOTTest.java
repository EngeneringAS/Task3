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
 * test for NOT class
 * @author Alexey Titov   and   Shalom Weinberger
 * @version 1
 */
public class NOTTest {
    /**
     * Test of Compare method, of class NOT.
     */
    @Test
    public void testCompare() {
        DataWIFI arg = new DataWIFI();
        arg.setTIME("2017-11-02 09:19:07");
        arg.setID("new");
        NOT instance = new NOT(new TimeFilter("2017-11-02 09:19:00","2017-11-02 09:19:09"));
        boolean expResult = false;
        boolean result = instance.Compare(arg);
        assertEquals(expResult, result);
    }
    /**
     * Test of toString method, of class NOT.
     */
    @Test
    public void testToString() {
        NOT instance = new NOT(new TimeFilter("2017-11-02 09:19:00","2017-11-02 09:19:09"));
        String expResult = "!(Time(2017-11-02 09:19:00<data<2017-11-02 09:19:09))";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
