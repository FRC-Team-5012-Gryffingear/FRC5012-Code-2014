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

    /**
     * Robot's instance of drivetrain
     */
    public Drivetrain drive = null;
    /**
     * Robot's instance of shooter supersystem
     */
    public Shooter shooter = null;
    /**
     * Robot's instance of compressor supersystem
     */
    public Compressor compressor = null;

    public Blocker blocker = null;

    /**
     * Robot's instance of blocker system
     */
    private Robot() {

        // Initialize all robot subsystems.
        drive = new Drivetrain(Ports.DRIVE_LEFT_A_PORT,
                Ports.DRIVE_LEFT_B_PORT,
                Ports.DRIVE_RIGHT_A_PORT,
                Ports.DRIVE_RIGHT_B_PORT);
        shooter = Shooter.getInstance();

        compressor = new Compressor(Ports.COMPRESSOR_SWITCH_PORT,
                Ports.COMPRESSOR_RELAY_PORT);
        compressor.start();

        blocker = new Blocker(Ports.BLOCKER_PORT);

    }

    /**
     * Get current instance of robot supersystem and all associated subsystems.
     */
    public static Robot getInstance() {
        if (instance == null) {
            instance = new Robot();
        }
        return instance;
    }

    /**
     * Zeros and resets all robot motors.
     */
    public void reset() {
        if (instance != null) {
            drive.tankDrive(0, 0);
            shooter.arm.run(Arm.States.OFF);
            shooter.intake.set(0);
            System.out.println("Robot motors reset!");
        } else {
            getInstance();
            drive.tankDrive(0, 0);
            shooter.arm.run(Arm.States.OFF);
            shooter.intake.set(0);
            System.out.println("Robot wasn't initialized! "
                    + "Reinitialized and reset!");
        }
    }

}
