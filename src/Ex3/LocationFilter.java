/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex3;
//library
import Ex1.DataWIFI;
import Ex1.Location;
/**
 * Location filter for Assignment 3
 * @author Alexey Titov   and   Shalom Weinberger
 * @version 2
 */
public class LocationFilter implements Filter{
    private static final long serialVersionUID = 2L;
    private double radius;
    private Location lla;
    /**
     * the constructor for LoactionFilter
     * @param Rarg argument of radius
     * @param arg argument for location
     */
    public LocationFilter(double Rarg,Location arg)
    {
        this.radius=Rarg;
        this.lla=arg;
    }
    /**
     * the constructor for LoactionFilter
     */
    public LocationFilter() {
        this.radius=0;
        this.lla=new Location();
    }
    /**
     * the function check location
     * @param arg-location we want to check
     * @return true if the argument is in a radius, otherwise false
     */
    @Override
    public boolean Compare(DataWIFI arg)
    {
        try{
            double x=Math.pow(this.lla.getLat()-arg.getLla().getLat(),2);
            double y=Math.pow(this.lla.getLon()-arg.getLla().getLon(),2);
            if ((x+y)<this.radius)
                return true;
            return false;
        }catch(NullPointerException e){
            return false;
        }
    }
    @Override
    public String toString()
    {
        return "Position(radius<"+this.radius+",center=("+this.lla.getLat()+","+this.lla.getLon()+")";
    }
}
