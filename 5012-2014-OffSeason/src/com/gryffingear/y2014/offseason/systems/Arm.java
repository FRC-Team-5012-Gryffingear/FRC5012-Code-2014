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
    private double upperLimit = 3.926;
    private double lowerLimit = 2.022;

    /**
     * Constructor
     *
     * @param motor_Port
     * @param pot_Port
     */
    public Arm(int motor_Port, int pot_Port) {
        arm_Motor = new Victor(motor_Port);
        pot = new AnalogChannel(pot_Port);
    }

    /**
     * Gets current position
     *
     * @return
     */
    public double getPosition() {
        double answer = pot.getVoltage();
        answer = answer * Constants.Arm.VOLTS_TO_DEGREES;
        return answer;

    }

    /**
     * Sets motor controller output
     *
     * @param value
     */
    private void set(double value) {

        if (value >= 0 && getPosition() >= upperLimit) {
            value = 0;
        } else if (value < 0 && getPosition() <= lowerLimit) {
            value = 0;
        }
        arm_Motor.set(value);

    }

    /**
     * Sets the target setpoint for closed loop mode
     *
     * @param target
     */
    public void setTarget(double target) {
        this.target = target;
    }

    /**
     * Sets the power for manual mode
     *
     * @param manual
     */
    public void setManual(double manual) {
        this.manual = manual;
    }

    /**
     * Finite state machine logic.
     *
     * @param state
     */
    public void run(int state) {
        double output = 0.0;

        if (state == States.MANUAL) {           // Manual mode

            output = manual;

        } else if (state == States.CLOSED_LOOP) {    // Closed loop mode

            output = (target - getPosition()) * Constants.Arm.ARM_P;

        } else if (state == States.OFF) {

            output = 0;

        }

        this.set(output);
    }

    public static class States {

        public static int MANUAL = 0;
        public static int CLOSED_LOOP = 1;
        public static int OFF = -1;
    }

}
