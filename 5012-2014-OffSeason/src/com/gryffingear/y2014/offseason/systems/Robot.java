/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.systems;

import com.gryffingear.y2014.offseason.config.Ports;
import edu.wpi.first.wpilibj.Compressor;

/**
 * Class containing all robot subsystems.
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class Robot {

    private static Robot instance = null;

    public Drivetrain drive = null;
    public Shooter shooter = null;

    Compressor compressor = null;

    private Robot() {
        drive = new Drivetrain(Ports.DRIVE_LEFT_A_PORT,
                Ports.DRIVE_LEFT_B_PORT,
                Ports.DRIVE_RIGHT_A_PORT,
                Ports.DRIVE_RIGHT_B_PORT);
        shooter = Shooter.getInstance();

        compressor = new Compressor(Ports.COMPRESSOR_SWITCH_PORT,
                Ports.COMPRESSOR_RELAY_PORT);
        compressor.start();

    }

    public static Robot getInstance() {
        if (instance == null) {
            instance = new Robot();
        }
        return instance;
    }

}
