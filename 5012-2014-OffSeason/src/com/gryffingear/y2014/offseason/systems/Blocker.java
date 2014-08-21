/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.systems;

import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class Blocker {

    private Solenoid blockerSolenoid = null;

    public Blocker(int motorPort, int blockerSol) {
        blockerSolenoid = new Solenoid(blockerSol);
    }

    public void setBlocker(boolean state) {
        blockerSolenoid.set(state);
    }

}
