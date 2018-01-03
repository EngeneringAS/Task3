/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex3;
//library
import Ex1.DataWIFI;
/**
 * Location filter for Assignment 3
 * @author Alexey Titov   and   Shalom Weinberger
 * @version 1
 */
public class DeviceFilter implements Filter{
    private String NameDevice;
    /**
     * the constructor for DeviceFilter
     * @param name 
     */
    public DeviceFilter(String name)
    {
        this.NameDevice=name;
    }
    /**
     * the constructor for DeviceFilter
     */
    public DeviceFilter() {
        this.NameDevice="";
    }
    /**
     * the function check device name
     * @param arg name we want to check
     * @return true if names are equals' otherwise false
     */
    @Override
    public boolean Compare(DataWIFI arg)
    {
        try{
            return this.NameDevice.equals(arg.getID());
        }catch(NullPointerException e){
            return false;
        }
    }
    @Override
    public String toString()
    {
        return "Device(name=="+this.NameDevice+")";
    }
}
