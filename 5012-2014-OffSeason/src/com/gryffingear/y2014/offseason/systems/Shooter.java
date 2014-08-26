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
        puncher = new Puncher(Ports.PUNCHER_PORT);
        arm.setTarget(Constants.Arm.STOW_POS);
    }

    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }

        return instance;
    }

    public void setJawPosition(boolean pos) {

    }

    public void setIntakeSpeed(double speed) {

    }

    public void setWantShoot() {

    }

    public void run(int state) {

    }
    private boolean prevShot = false;
    private long shotStart = 0;

    public void shoot(boolean state) {
        //puncher.setPuncher(state);
        intake.setJaw(state);
        long timeForshot = 250;
        if ((state != prevShot) && state) {
            shotStart = System.currentTimeMillis();
        }

        puncher.shoot(System.currentTimeMillis() - shotStart > timeForshot && state);

        //setPuncher(System.currentTimeMillis() - shotStart < timeForShot);
        prevShot = state;
    }

    public static class States {

        public static int OFF = -1;
        public static int COLLECT_BALL = 1;
        public static int READY_FOR_SHOT = 2;
    }

}
