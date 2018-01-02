/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex2;
//libraries
import Ex1.Location;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * test for ClassOfAlgorithm1 class
 * @author Alexey Titov &   Shalom Weinberger
 * @version 1.0
 */
public class ClassOfAlgorithm1Test {
    //check get MAC
    @Test
    public void testGetMAC() {
	ClassOfAlgorithm1 tmp=new ClassOfAlgorithm1();
	assertEquals("N\\A", tmp.getMAC());
	tmp.setMAC("ec:8c:a2:26:ae:28");
	assertSame("ec:8c:a2:26:ae:28", tmp.getMAC());
    }
    //check set MAC
    @Test
    public void testSetMAC() {
	ClassOfAlgorithm1 tmp=new ClassOfAlgorithm1();
	assertEquals("N\\A", tmp.getMAC());
	tmp.setMAC("ec:8c:a2:26:ae:28");
	assertSame("ec:8c:a2:26:ae:28", tmp.getMAC());
    }
    //check set SignaList
    @Test
    public void testSetSignaList() {
	ClassOfAlgorithm1 tmp=new ClassOfAlgorithm1();
	assertTrue(tmp.setSignaList(-10, new Location(1,2,3)));
	assertFalse(tmp.setSignaList(-10, new Location(1,2,3)));
	assertTrue(tmp.setSignaList(-10, new Location(4,5,6)));
	assertTrue(tmp.setSignaList(-10, new Location(7,8,9)));
	assertTrue(3==tmp.getSignaList().size());
    }
    //check get SignaList
    @Test
    public void testGetSignaList() {
	ClassOfAlgorithm1 tmp=new ClassOfAlgorithm1();
	tmp.setSignaList(-10, new Location(1,2,3));
	tmp.setSignaList(-10, new Location(4,5,6));
	assertTrue(2==tmp.getSignaList().size());
    }
}
