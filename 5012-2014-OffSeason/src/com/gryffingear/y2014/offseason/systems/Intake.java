/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.systems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/**
 * Class representing intake subsystem
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class Intake {

    private Victor intakeMotor = null;
    private Solenoid jawSolenoid = null;

    public Intake(int motorPort, int jawSol) {
        intakeMotor = new Victor(motorPort);
        jawSolenoid = new Solenoid(jawSol);
    }

    public void set(double value) {
        intakeMotor.set(value);
    }

    public void setJaw(boolean state) {
        jawSolenoid.set(state);
    }

}
