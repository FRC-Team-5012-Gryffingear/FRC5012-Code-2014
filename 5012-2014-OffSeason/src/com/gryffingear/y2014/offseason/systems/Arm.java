/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.systems;

import com.gryffingear.y2014.offseason.config.Constants;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class Arm {

    private Victor arm_Motor = null;
    private AnalogChannel pot;
    private double target = 0;
    private double manual = 0;

    public Arm(int motor_Port, int pot_Port) {

        arm_Motor = new Victor(motor_Port);
        pot = new AnalogChannel(pot_Port);

    }

    public double getPosition() {

        double answer = pot.getVoltage();
        return answer;
    }

    private void set(double value) {

        /*if (value > .3) {
         value = .3;
         } else if (value < -.3) {
         value = -.3;
         }*/
        arm_Motor.set(value);
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public void setManual(double manual) {
        this.manual = manual;
    }

    public void run(int state) {
        double output = 0.0;

        if (state == 0) {
            output = manual;
        } else if (state == 1) {
            output = (target - getPosition()) * Constants.Arm.ARM_P;
        }

        this.set(output);
    }

}
