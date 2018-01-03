/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex3;
//libraries
import Ex1.DataWIFI;
import Ex1.Location;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * test for HelpFunctionEx3 class
 * @author Alexey Titov   and   Shalom Weinberger
 * @version 1
 */
public class HelpFunctionEx3Test {
    /**
     * Test of ShowData method, of class HelpFunctionEx3.
     */
    @Test
    public void testShowData() {
        HelpFunctionEx3 instance = new HelpFunctionEx3();
        String result = instance.ShowData();
        assertNotNull(result);
    }
    /**
     * Test of FirstAlgorithm method, of class HelpFunctionEx3.
     */
    @Test
    public void testFirstAlgorithm() {
        String mac = "";
        HelpFunctionEx3 instance = new HelpFunctionEx3();
        Location expResult = new Location();
        Location result = instance.FirstAlgorithm(mac);
        assertTrue(expResult.compareLLA(result)==0);
    }
    /**
     * Test of ThreeMac method, of class HelpFunctionEx3.
     */
    @Test
    public void testThreeMac() {
        ArrayList<DataWIFI> DWF = null;
        DataWIFI three = null;
        HelpFunctionEx3 instance = new HelpFunctionEx3();
        Location expResult = new Location();
        Location result = instance.ThreeMac(DWF, three);
        assertTrue(expResult.compareLLA(result)==0);
    }
    /**
     * Test of RowTS method, of class HelpFunctionEx3.
     */
    @Test
    public void testRowTS() {
        ArrayList<DataWIFI> DWF = null;
        String rowTS = "";
        HelpFunctionEx3 instance = new HelpFunctionEx3();
        Location expResult = new Location();
        Location result = instance.RowTS(DWF, rowTS);
        assertTrue(expResult.compareLLA(result)==0);
    }
    /**
     * Test of ShowFilter method, of class HelpFunctionEx3.
     */
    @Test
    public void testShowFilter() {
        HelpFunctionEx3 instance = new HelpFunctionEx3();
        String result = instance.ShowFilter();
        assertNotNull(result);
    }
}
