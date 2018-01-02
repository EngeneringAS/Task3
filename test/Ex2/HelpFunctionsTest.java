/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex2;
//libraries
import Ex1.DataWIFI;
import Ex1.Location;
import Ex1.WIFI;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * test for HelpFunctions class
 * @author Alexey Titov &   Shalom Weinberger
 * @version 2.0
 */
public class HelpFunctionsTest {
    //check for WriteWeight
    @Test
    public void testWriteWeight() {
	HelpFunctions hf=new HelpFunctions();
        String MAC="10:5a:f7:0f:d5:32";
	Location l1=new Location(32.103,35.208,650);
	Location l2=new Location(32.105,35.205,660);
	Location l3=new Location(32.103,35.307,680);
	Location check=new Location(32.10322468793343,35.2164507628294,653.7864077669904);
	int sig1=-30,sig2=-80,sig3=-90;
	ClassOfAlgorithm1 tmp=new  ClassOfAlgorithm1();
	tmp.setMAC(MAC);
	tmp.setSignaList(sig1,l1);
	tmp.setSignaList(sig2,l2);
	tmp.setSignaList(sig3,l3);
	assertTrue(check.compareLLA(hf.WriteWeight(tmp))==0);
    }
    //check for WriteWeight2
    @Test
    public void testWriteWeight2() {
	HelpFunctions hf=new HelpFunctions();
	Location l1=new Location(32.103,35.208,650);
	Location l2=new Location(32.105,35.205,660);
	Location l3=new Location(32.103,35.307,680);
	double w1=0.476989,w2=0.173813,w3=0.158379;
	Location check=new Location(32.103429602276876,35.22673262224397,658.0198373417072);
	ALGOtwoCLASS a1=new ALGOtwoCLASS();
	a1.setLLA(l1);			a1.setPi(w1);
	ALGOtwoCLASS a2=new ALGOtwoCLASS();
	a2.setLLA(l2);			a2.setPi(w2);
	ALGOtwoCLASS a3=new ALGOtwoCLASS();
	a3.setLLA(l3);			a3.setPi(w3);
	hf.addAll(a1,a2,a3);
	assertTrue(check.compareLLA(hf.WriteWeight2(hf.limitRowsWithHighestWeight()))==0);
    }
    //check for Dif
    @Test
    public void testDif() 
    {
	HelpFunctions hf=new HelpFunctions();
        assertTrue(100==hf.Dif(-50, -120));
	assertTrue(100==hf.Dif(-50, 0));
	assertTrue(3==hf.Dif(-50, -51));
        assertTrue(12==hf.Dif(-50, -62));
    }
    //check for weight_pi
    @Test
    public void testweight_pi() 
    {
	HelpFunctions hf=new HelpFunctions();
	ArrayList<WIFI> input=new ArrayList<WIFI>();
	ArrayList<WIFI> output=new ArrayList<WIFI>();
	WIFI tmp1=new WIFI();		WIFI tmp2=new WIFI();		WIFI tmp3=new WIFI();
	WIFI Otmp1=new WIFI();		WIFI Otmp2=new WIFI();		WIFI Otmp3=new WIFI();
	//initialization of input
	tmp1.setSignal(-50);		input.add(tmp1);
	tmp2.setSignal(-70);		input.add(tmp2);
	tmp3.setSignal(-90);		input.add(tmp3);
	//first initialization of output
	Otmp1.setSignal(-62);		output.add(Otmp1);
	Otmp2.setSignal(-79);		output.add(Otmp2);
	Otmp3.setSignal(-71);		output.add(Otmp3);
	assertTrue(0.4769885454073238==hf.weight_pi(input, output));
	//second initialization of output
	output.removeAll(output);
	Otmp1.setSignal(-82);		output.add(Otmp1);
	Otmp2.setSignal(-120);		output.add(Otmp2);
	Otmp3.setSignal(-82);		output.add(Otmp3);
	assertTrue(0.17381326045114823==hf.weight_pi(input, output));
	//third initialization of output
	output.removeAll(output);
	Otmp1.setSignal(-120);	output.add(Otmp1);
	Otmp2.setSignal(-120);	output.add(Otmp2);
	Otmp3.setSignal(-120);	output.add(Otmp3);
	assertTrue(0.0401216599197276==hf.weight_pi(input, output));
	assertTrue(-1==hf.weight_pi(input, null));
	assertTrue(-1==hf.weight_pi(null, output));
    }
    //check for WeightAlgo2
    @Test
    public void testWeightAlgo2() {
	HelpFunctions hf=new HelpFunctions();
	ArrayList<WIFI> input=new ArrayList<WIFI>();
	ArrayList<ALGOtwoCLASS> data=new ArrayList<ALGOtwoCLASS>();
	String MAC1="10:5a:f7:0f:d5:32";	String MAC2="10:5a:f7:0f:d5:31"; 	String MAC3="10:5a:f7:0f:d5:30";
	Location l1=new Location(32.103,35.208,650);
	Location l2=new Location(32.105,35.205,660);
	Location l3=new Location(32.103,35.307,680);
	Location check=new Location(32.10342960296812,35.226732636153926,658.0198453273013);
	WIFI tmp1=new  WIFI();		WIFI tmp2=new  WIFI();		WIFI tmp3=new  WIFI();
	WIFI Dtmp1=new  WIFI();		WIFI Dtmp2=new  WIFI();		WIFI Dtmp3=new  WIFI();
	WIFI Dtmp4=new  WIFI();		WIFI Dtmp5=new  WIFI();		WIFI Dtmp6=new  WIFI();
	WIFI Dtmp7=new  WIFI();		WIFI Dtmp8=new  WIFI();		WIFI Dtmp9=new  WIFI();
	//initialization of input
	tmp1.setMAC(MAC1);		tmp2.setMAC(MAC2);		tmp3.setMAC(MAC3);
	tmp1.setSignal(-50);		tmp2.setSignal(-70);		tmp3.setSignal(-90);
	input.add(tmp1);		input.add(tmp2);		input.add(tmp3);
	assertNull(hf.WeightAlgo2(null,null));
	assertNull(hf.WeightAlgo2(input,data));
        //initialization of data
	Dtmp1.setMAC(MAC1);		Dtmp2.setMAC(MAC2);		Dtmp3.setMAC(MAC3);		
	Dtmp1.setSignal(-62);		Dtmp2.setSignal(-79);		Dtmp3.setSignal(-71);
	Dtmp4.setMAC(MAC1);		Dtmp5.setMAC(MAC2);		Dtmp6.setMAC(MAC3);		
	Dtmp4.setSignal(-82);		Dtmp5.setSignal(-120);		Dtmp6.setSignal(-82);
	Dtmp7.setMAC(MAC1);		Dtmp8.setMAC(MAC2);		Dtmp9.setMAC(MAC3);		
	Dtmp7.setSignal(-120);		Dtmp8.setSignal(-89);		Dtmp9.setSignal(-73);
	ALGOtwoCLASS a1=new ALGOtwoCLASS();
	a1.setLLA(l1);			a1.setWiFi(Dtmp1);		a1.setWiFi(Dtmp2);		a1.setWiFi(Dtmp3);
	ALGOtwoCLASS a2=new ALGOtwoCLASS();
	a2.setLLA(l2);			a2.setWiFi(Dtmp4);		a2.setWiFi(Dtmp5);		a2.setWiFi(Dtmp6);
	ALGOtwoCLASS a3=new ALGOtwoCLASS();
	a3.setLLA(l3);			a3.setWiFi(Dtmp7);		a3.setWiFi(Dtmp8);		a3.setWiFi(Dtmp9);
	data.add(a1);			data.add(a2);			data.add(a3);
	assertTrue(check.compareLLA(hf.WeightAlgo2(input,data))==0);
    }
    //check for two function Exchange
    @Test
    public void testExchange() {
	HelpFunctions hf=new HelpFunctions();
	String str="no";
	assertNull(hf.Exchange(null,null));
	assertNull(hf.Exchange(null,str));
	assertNull(hf.Exchange(null));
    }
    //check for MapToList
    @Test
    public void testMapToList(){
	HelpFunctions hf=new HelpFunctions();
    	assertNull(hf.MapToList(null));
    }
    //check for HelpToFind
    @Test
    public void testHelpToFind(){
	HelpFunctions hf=new HelpFunctions();
	ArrayList<WIFI> WiFi1=new ArrayList<WIFI>();
	ArrayList<WIFI> WiFi2=new ArrayList<WIFI>();
	String MAC1="10:5a:f7:0f:d5:32";	String MAC2="10:5a:f7:0f:d5:31"; 	String MAC3="10:5a:f7:0f:d5:30";
	WIFI tmp1=new  WIFI();		WIFI tmp2=new  WIFI();		WIFI tmp3=new  WIFI();
	WIFI tmp4=new  WIFI();		WIFI tmp5=new  WIFI();	
	//initialization of WiFi1
	tmp1.setMAC(MAC1);		tmp2.setMAC(MAC2);		tmp3.setMAC(MAC3);
	tmp1.setSignal(-50);		tmp2.setSignal(-70);    	tmp3.setSignal(-90);
	WiFi1.add(tmp1);		WiFi1.add(tmp2);		WiFi1.add(tmp3);
	//initialization of WiFi1
	tmp4.setMAC(MAC1); 		tmp4.setSignal(-50);
	tmp5.setMAC("10:5a:f7:0f:d5:33");				tmp5.setSignal(-50);
	WiFi2.add(tmp5);
	assertFalse(hf.HelpToFind(WiFi1,WiFi2));
	WiFi2.add(tmp4);
	assertTrue(hf.HelpToFind(WiFi1,WiFi2));
	assertFalse(hf.HelpToFind(WiFi1,null));
    }
    //check for DataToFind
    @Test
    public void testDataToFind(){
	HelpFunctions hf=new HelpFunctions();
	ArrayList<WIFI> WiFi_list=new ArrayList<WIFI>();
	WIFI WiFi=new WIFI();
	String MAC1="10:5a:f7:0f:d5:32";	String MAC2="10:5a:f7:0f:d5:31"; 	String MAC3="10:5a:f7:0f:d5:30";
	WIFI tmp1=new  WIFI();		WIFI tmp2=new  WIFI();  		WIFI tmp3=new  WIFI();	
	//initialization of WiFi_list
	tmp1.setMAC(MAC1);		tmp2.setMAC(MAC2);			tmp3.setMAC(MAC3);
	tmp1.setSignal(-50);		tmp2.setSignal(-70);			tmp3.setSignal(-90);
	WiFi_list.add(tmp1);		WiFi_list.add(tmp2);			WiFi_list.add(tmp3);
	//initialization of WiFi
	WiFi.setMAC("10:5a:f7:0f:d5:33");
	WiFi.setSignal(-11);
	assertEquals(-120,hf.DataToFind(WiFi,WiFi_list).getSignal());
	WiFi.setMAC(MAC1);
	assertEquals(-50,hf.DataToFind(WiFi,WiFi_list).getSignal());
	assertNull(hf.DataToFind(WiFi,null));
    }
    //check for Find
    @Test
    public void Find(){
	HelpFunctions hf=new HelpFunctions();
	ArrayList<DataWIFI> DWF=new ArrayList<DataWIFI>(); 
	ArrayList<WIFI> WiFi=new ArrayList<WIFI>();
	assertNull(hf.Find(null,null));
	assertTrue(hf.Find(DWF,null).isEmpty());
	assertTrue(hf.Find(DWF,WiFi).isEmpty());
    }
}
