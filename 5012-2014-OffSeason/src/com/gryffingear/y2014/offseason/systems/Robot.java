/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.systems;

import com.gryffingear.y2014.offseason.config.Ports;

/**
 * Class containing all robot subsystems.
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class Robot {

    private static Robot instance = null;

    public Drivetrain drive = null;

    private Robot() {
        drive = new Drivetrain(Ports.DRIVE_LEFT_A_PORT,
                Ports.DRIVE_LEFT_B_PORT,
                Ports.DRIVE_RIGHT_A_PORT,
                Ports.DRIVE_RIGHT_B_PORT);
    }

    public static Robot getInstance() {
        if (instance == null) {
            instance = new Robot();
        }
        return instance;
    }

}
