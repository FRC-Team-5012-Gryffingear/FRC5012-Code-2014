/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.systems;

import com.gryffingear.y2014.offseason.config.Constants;
import com.gryffingear.y2014.offseason.config.Ports;

/**
 * Class representing the arm, intake, and puncher subsystems as one system.
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class Shooter {

    private static Shooter instance = null;

    public Arm arm = null;
    public Intake intake = null;
    public Puncher puncher = null;

    private Shooter() {
        arm = new Arm(Ports.ARM_PORT,
                Ports.ARM_POT);
        intake = new Intake(Ports.INTAKE_PORT,
                Ports.INTAKE_JAW_PORT);
        puncher = new Puncher(Ports.PUNCHER_PORTA);
        arm.setTarget(Constants.Arm.STOW_POS);
    }

    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }

        return instance;
    }

    private boolean prevShot = false, prevTwitch = false;
    private long shotStart = 0, twitchStart = 0;

    /**
     * Auto timed shoot sequence for teleop.
     *
     * @param wantShoot flag indicating operator shoot input
     * @param wantJaw flag indicating operator jaw movement input
     */
    public void run(boolean wantShoot, boolean wantJaw, boolean wantTwitch) {

        boolean twitchOut = false;

        if (wantTwitch && (wantTwitch != prevTwitch)) {
            twitchStart = System.currentTimeMillis();
        }

        if ((System.currentTimeMillis() - twitchStart < 75)
                || (System.currentTimeMillis() - twitchStart > 150 && System.currentTimeMillis() - twitchStart < 225)
                || (System.currentTimeMillis() - twitchStart > 300 && System.currentTimeMillis() - twitchStart < 375)) {
            twitchOut = true;
        } else {
            twitchOut = false;
        }

        // operator command.
        // auto open if shoot
        long timeForshot = 250; // Time delay to wait between opening jaw and shooting.
        if ((wantShoot != prevShot) && wantShoot) { // Oneshot logic for delay.
            shotStart = System.currentTimeMillis();
        }

        // Tell the puncher to shoot after time delay and only if a shot is requested
        intake.setJaw((wantShoot || wantJaw || twitchOut) && (arm.getOffset() < 1.5));    // Set jaw solenoid to open on
        puncher.shoot(System.currentTimeMillis() - shotStart > timeForshot && wantShoot);

        prevTwitch = wantTwitch;
        prevShot = wantShoot;
    }

}
