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

    public Puncher(int puncherSol) {
        puncherSolenoid = new Solenoid(puncherSol);
    }

    public void setPuncher(boolean state) {
        puncherSolenoid.set(state);

    }

    private boolean prevShot = false;
    private long shotStart = 0;

    public void shoot(boolean state, double power) {
        power = Math.min(Math.abs(power), 1.0);

        long timeForShot = 500;
        long timeToShoot = (long) (timeForShot * power);

        if ((state != prevShot) && state) {
            shotStart = System.currentTimeMillis();
        }

        setPuncher(System.currentTimeMillis() - shotStart < timeToShoot);

        prevShot = state;
    }

    public void shoot(boolean state) {
        shoot(state, 1.0);
    }
}
