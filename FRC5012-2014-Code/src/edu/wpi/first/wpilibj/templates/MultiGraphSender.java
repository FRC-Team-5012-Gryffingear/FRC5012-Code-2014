/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 *
 * @author Justin
 */
public class MultiGraphSender{
    ITable table;
    
    public MultiGraphSender(String name)
    {
        table = NetworkTable.getTable(name);
    }
    
    public void putNumber(String name, double value)
    {
        table.putNumber(name, value);
    }
    
}
