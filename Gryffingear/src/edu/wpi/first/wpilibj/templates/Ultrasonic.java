package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 *
 * @author FRC
 */
public class Ultrasonic {
    
    AnalogChannel input;
    
    
    public Ultrasonic(int channel)
    {
        this( new AnalogChannel(channel));
    }
    
    public Ultrasonic(AnalogChannel channel)
    {
        input = channel;
    }
    
    public double getDistanceCM()
    {
        double dist = input.getAverageVoltage() * 1024 / 5;
        
        return dist;
    }
    
    public double getDistanceIN()
    {
        return getDistanceCM() / 2.54;
    }
}

