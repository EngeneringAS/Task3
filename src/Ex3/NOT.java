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
public class NOT implements Filter{
    private static final long serialVersionUID = 6L;
    private Filter _notFilter;
    /**
     * constructor for NOT
     * @param f filter
     */
    public NOT(Filter f) 
    {
        this._notFilter=f;
    }
    /**
     * the function make not to filter
     * @param arg argument we want to check
     * @return true if filter is false, otherwise false 
     */
    @Override
    public boolean Compare(DataWIFI arg) 
    {
        return !(this._notFilter.Compare(arg));
    }
    @Override
    public String toString() 
    {
        return "!("+this._notFilter.toString()+")";
    }
}
