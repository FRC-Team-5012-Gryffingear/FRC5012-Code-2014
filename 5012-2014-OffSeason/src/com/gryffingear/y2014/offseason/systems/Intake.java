/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.systems;

import com.gryffingear.y2014.offseason.config.Ports;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Class representing intake subsystem
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class Intake {

    private Victor intakeMotor = null;
    private Solenoid jawSolenoid = null;
    private DigitalInput bumpswitch;

    public Intake(int motorPort, int jawSol, int bump) {
        intakeMotor = new Victor(motorPort);
        jawSolenoid = new Solenoid(jawSol);
        bumpswitch = new DigitalInput(bump);
    }

    public void set(double value) {
        intakeMotor.set(value);
    }

    public void setJaw(boolean state) {
        jawSolenoid.set(state);
    }

    public boolean getBallPresent() {
        return !bumpswitch.get();
    }

}
