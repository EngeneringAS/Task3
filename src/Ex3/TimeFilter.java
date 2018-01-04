/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex3;
//library
import Ex1.DataWIFI;
import Ex1.Time;
/**
 * Time filter for Assignment 3
 * @author Alexey Titov   and   Shalom Weinberger
 * @version 1
 */
public class TimeFilter implements Filter{
    private static final long serialVersionUID = 1L;
    private Time _from=new Time();;
    private Time _to=new Time();;
    /**
     * constructor for TimeFilter
     * @param start from
     * @param end   to
     */
    public TimeFilter(String start,String end)
    {
        this._from.setFt(start);
        this._to.setFt(end);
    }
    /**
     * the function check time
     * @param arg time we want to check
     * @return true if arg is between from and to 
     */
    @Override
    public boolean Compare(DataWIFI arg)
    {
        try{
            if (!this._from.getFtD().before(arg.getTIMED()))
                return false;
            if (!this._to.getFtD().after(arg.getTIMED()))
                return false;
            return true;
        }catch(NullPointerException e){
            return false;
        }
    }
    @Override
    public String toString() 
    {
        return "Time("+this._from.getFt()+"<data<"+this._to.getFt()+")";
    }
}
