/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author FRC
 */
public class TwoMotorPIDOut implements PIDOutput {

    private final SpeedController motorA;
    private final SpeedController motorB;

    public TwoMotorPIDOut(SpeedController A, SpeedController B) {
        motorA = A;
        motorB = B;
    }

    public void pidWrite(double d) {
        motorA.set(-d);
        motorB.set(-d);
    }

}
