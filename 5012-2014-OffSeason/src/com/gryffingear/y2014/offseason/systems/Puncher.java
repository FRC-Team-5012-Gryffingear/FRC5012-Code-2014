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
public class Puncher {

    private Solenoid puncherSolenoid = null;

    public Puncher(int motorPort, int puncherSol) {
        puncherSolenoid = new Solenoid(puncherSol);
    }

    public void setBlocker(boolean state) {
        puncherSolenoid.set(state);

    }
}
