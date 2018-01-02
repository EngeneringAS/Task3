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
 * Test class for interface CompareDataWifi
 * @author Alexey Titov &   Shalom Weinberger
 * @version 11.2
 */
public class CompareDataWifiTest {
    private CompareDataWifi cdw;
    @After
    public void tearDown() throws Exception {
	cdw=null;
    }
    @Test
    public void testCompareDataWIFIDataWIFI() {
	//user choose to filter by ID
	cdw=new CompareDataWifi(){
	public int compare(DataWIFI arg1,DataWIFI arg2)				//the function sorting
	{
            if (arg1==null && arg2==null)
		return 0;
            if (arg1==null)
		return -1;
            if (arg2==null)
		return 1;
            return arg1.getID().compareTo(arg2.getID());
	}
	public boolean filter(DataWIFI arg1,DataWIFI arg2,DataWIFI arg3)	//the function filter
	{
            if (arg1.getID().equals(arg2.getID()))
		return true;
            return false;
	}};
	//user choose to filter by TIME
	cdw=new CompareDataWifi(){
	public int compare(DataWIFI arg1,DataWIFI arg2)				//the function sorting
	{
            if (arg1==null && arg2==null)
		return 0;
            if (arg1==null)
		return -1;
            if (arg2==null)
		return 1;
            return arg1.getTIMED().compareTo(arg2.getTIMED());
	}
	public boolean filter(DataWIFI arg1,DataWIFI arg2,DataWIFI arg3)	//the function filter
	{
            if (arg1.getTIMED().after(arg2.getTIMED()) && arg1.getTIMED().before(arg3.getTIMED()))
		return true;
            return false;
	}};
        //user choose to filter by location
	cdw=new CompareDataWifi(){
	public int compare(DataWIFI arg1,DataWIFI arg2)				//the function sorting
	{
            if (arg1==null && arg2==null)
                return 0;
            if (arg1==null)
		return -1;
            if (arg2==null)
		return 1;
            return arg1.getLla().compareLLA(arg2.getLla());
	}
	public boolean filter(DataWIFI arg1,DataWIFI arg2,DataWIFI arg3)	//the function filter
        {
            if (arg1.getLla().getLat()>arg2.getLla().getLat() && arg1.getLla().getLat()<arg3.getLla().getLat()
                && arg1.getLla().getLon()>arg2.getLla().getLon() && arg1.getLla().getLon()<arg3.getLla().getLon())
		return true;
            return false;
        }};
    }
    @Test
    public void testFilterID() {
	DataWIFI tmp1=new DataWIFI();
	DataWIFI tmp2=new DataWIFI();
	DataWIFI tmp3=new DataWIFI();
	//user choose to filter by ID
	cdw=new CompareDataWifi(){
	public int compare(DataWIFI arg1,DataWIFI arg2)				//the function sorting
	{
            if (arg1==null && arg2==null)
		return 0;
            if (arg1==null)
		return -1;
            if (arg2==null)
		return 1;
            return arg1.getID().compareTo(arg2.getID());
	}
	public boolean filter(DataWIFI arg1,DataWIFI arg2,DataWIFI arg3)	//the function filter
	{
            if (arg1.getID().equals(arg2.getID()))
		return true;
            return false;
	}};
	tmp1.setID("1");		tmp2.setID("1"); 		tmp3.setID("2");
	assertFalse(cdw.filter(tmp1,tmp3,tmp2));			//tmp1=1 and tmp3=2, their values are not equal
	assertTrue(cdw.filter(tmp1,tmp2,tmp3));				//tmp1=1 and tmp2=1, their values are equal
	assertEquals(0,cdw.compare(null, null));			//arg1 and arg2 are null
	assertEquals(1,cdw.compare(tmp1, null));			//arg2 is null
	assertEquals(-1,cdw.compare(null, tmp2));			//arg1 is null
	assertEquals(0,cdw.compare(tmp1, tmp2));			//arg1 and arg2 are equals
	assertEquals(-1,cdw.compare(tmp1, tmp3));			//arg1 is less than arg2
	assertEquals(1,cdw.compare(tmp3, tmp1));			//arg2 is less than arg1
    }
    @Test
    public void testFilterTime() {
	DataWIFI tmp1=new DataWIFI();
	DataWIFI tmp2=new DataWIFI();
	DataWIFI tmp3=new DataWIFI();
	//user choose to filter by Time
	cdw=new CompareDataWifi(){
	public int compare(DataWIFI arg1,DataWIFI arg2)				//the function sorting
	{
            if (arg1==null && arg2==null)
		return 0;
            if (arg1==null)
		return -1;
            if (arg2==null)
		return 1;
            return arg1.getTIMED().compareTo(arg2.getTIMED());
	}
	public boolean filter(DataWIFI arg1,DataWIFI arg2,DataWIFI arg3)	//the function filter
        {
            if (arg1.getTIMED().after(arg2.getTIMED()) && arg1.getTIMED().before(arg3.getTIMED()))
		return true;
            return false;
	}};
	tmp1.setTIME("2017-01-01 01:01:01");		tmp2.setTIME("2017-01-01 01:01:00"); 		tmp3.setTIME("2017-01-01 02:02:02");
	assertFalse(cdw.filter(tmp1,tmp3,tmp2));			//tmp1 is not between tmp3 and tmp2
	assertTrue(cdw.filter(tmp1,tmp2,tmp3));				//tmp1 is between tmp2 and tmp3
	assertEquals(0,cdw.compare(null, null));			//arg1 and arg2 are null
	assertEquals(1,cdw.compare(tmp1, null));			//arg2 is null
	assertEquals(-1,cdw.compare(null, tmp2));			//arg1 is null
	tmp2.setTIME("2017-01-01 01:01:01"); 
	assertEquals(0,cdw.compare(tmp1, tmp2));			//arg1 and arg2 are equals
	assertEquals(-1,cdw.compare(tmp1, tmp3));			//arg1 is before arg2
	assertEquals(1,cdw.compare(tmp3, tmp1));			//arg2 is before arg1
    }
    @Test
    public void testFilterLocation() {
	DataWIFI tmp1=new DataWIFI();
	DataWIFI tmp2=new DataWIFI();
	DataWIFI tmp3=new DataWIFI();
	//user choose to filter by location
        cdw=new CompareDataWifi(){
        public int compare(DataWIFI arg1,DataWIFI arg2)				//the function sorting
        {
            if (arg1==null && arg2==null)
                return 0;
            if (arg1==null)
                return -1;
            if (arg2==null)
                return 1;
            return arg1.getLla().compareLLA(arg2.getLla());
        }
	public boolean filter(DataWIFI arg1,DataWIFI arg2,DataWIFI arg3)	//the function filter
	{
            if (arg1.getLla().getLat()>arg2.getLla().getLat() && arg1.getLla().getLat()<arg3.getLla().getLat()
                && arg1.getLla().getLon()>arg2.getLla().getLon() && arg1.getLla().getLon()<arg3.getLla().getLon())
                return true;
            return false;
	}};
	tmp1.setLla(new Location(2.2,2.2,2.2));		tmp2.setLla(new Location(1.2,1.2,1.2)); 		tmp3.setLla(new Location(3.2,3.2,3.2));
	assertFalse(cdw.filter(tmp1,tmp3,tmp2));	//tmp1 is not between tmp3 and tmp2
	assertTrue(cdw.filter(tmp1,tmp2,tmp3));		//tmp1 is between tmp2 and tmp3
	assertEquals(0,cdw.compare(null, null));	//arg1 and arg2 are null
	assertEquals(1,cdw.compare(tmp1, null));	//arg2 is null
	assertEquals(-1,cdw.compare(null, tmp2));	//arg1 is null
	tmp2.setLla(new Location(2.2,2.2,2.2));
	assertEquals(0,cdw.compare(tmp1, tmp2));	//arg1 and arg2 are equals
	assertEquals(-1,cdw.compare(tmp1, tmp3));	//arg1 is before arg2
	assertEquals(1,cdw.compare(tmp3, tmp1));	//arg2 is before arg1
    }
}
