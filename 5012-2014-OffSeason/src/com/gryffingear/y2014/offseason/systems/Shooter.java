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

    private Shooter() {
        arm = new Arm(Ports.ARM_PORT,
                Ports.ARM_POT);
        intake = new Intake(Ports.INTAKE_PORT,
                Ports.INTAKE_JAW_PORT);
    }

    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }

        return instance;
    }

    public void run(int state) {

        if (state == States.COLLECT_BALL) {
            arm.run(Arm.States.CLOSED_LOOP);
            arm.setTarget(1.86);

            intake.set(1.0);
            intake.setJaw(Constants.Intake.JAW_CLOSE);
        } else if (state == States.READY_FOR_SHOT) {
            arm.run(Arm.States.CLOSED_LOOP);
            arm.setTarget(2.5);

            intake.set(0.0);
            intake.setJaw(Constants.Intake.JAW_OPEN);
        } else if (state == States.OFF) {

            arm.run(Arm.States.OFF);
            arm.setManual(0.0);
            intake.set(0.0);

        }

    }

    public static class States {

        public static int OFF = -1;
        public static int COLLECT_BALL = 1;
        public static int READY_FOR_SHOT = 2;
    }

}
