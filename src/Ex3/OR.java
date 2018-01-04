/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex3;
//library
import Ex1.DataWIFI;
/**
 * OR filter for Assignment 3
 * @author Alexey Titov   and   Shalom Weinberger
 * @version 1
 */
public class OR implements Filter{
    private static final long serialVersionUID = 7L;
    private Filter first_f;
    private Filter second_f;
    /**
     * constructor
     * @param f1    first filter
     * @param f2    second filter
     */
    public OR(Filter f1, Filter f2)
    {
        this.first_f= f1;
        this.second_f= f2;
    }
    /**
     * the function does OR on the filters
     * @param arg argument for filter checking
     * @return true if arg is correct, otherwise false
     */
    @Override
    public boolean Compare(DataWIFI arg) 
    {
        return this.first_f.Compare(arg) || this.second_f.Compare(arg);
    }
    @Override
    public String toString() 
    {
        return "("+this.first_f.toString()+" || "+this.second_f.toString()+")";
    }
}
