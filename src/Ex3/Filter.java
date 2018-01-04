/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex3;
//libraries
import Ex1.DataWIFI;
import java.io.Serializable;
/**
 * interface class filter for Assignment 3
 * @author Alexey Titov   and   Shalom Weinberger
 * @version 2
 */
public interface Filter extends Serializable {
    static final long serialVersionUID = 1L;
    public boolean Compare(DataWIFI arg);
}