/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.gryffingear.y2014.offseason;

import com.gryffingear.y2014.offseason.auton.ShootOneBallAuton;
import com.gryffingear.y2014.offseason.config.Constants;
import com.gryffingear.y2014.offseason.config.Ports;
import com.gryffingear.y2014.offseason.systems.Robot;
import com.gryffingear.y2014.offseason.utilities.NegativeInertiaAccumulator;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Main extends IterativeRobot {

    Robot bot;

    //Joysticks
    Joystick leftstick = new Joystick(Ports.LEFT_JOY_PORT);
    Joystick rightstick = new Joystick(Ports.RIGHT_JOY_PORT);
    Joystick gamepad = new Joystick(Ports.OPERATOR_JOY_PORT);

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        bot = Robot.getInstance();

    }

    private CommandGroup currAuton = null;  // Object representing currently
    // selected autonomous mode

    public void autonomousInit() {

        // Cancel auton if it is currently running for safety.
        if (currAuton != null) {
            currAuton.cancel();
            currAuton = null;
        }

        // Initialize new auton.
        // Todo: make this selectable via smartdashboard or something.
        currAuton = new ShootOneBallAuton();

        // Add the currently selected auton to the scheduler for execution.
        Scheduler.getInstance().add(currAuton);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        // Run the scheduler.
        Scheduler.getInstance().run();
    }

    // Quickturn and quickstop neg inertia accumulators for driver.
    NegativeInertiaAccumulator throttleNia = new NegativeInertiaAccumulator(Constants.Drivetrain.QUICK_STOP);
    NegativeInertiaAccumulator turningNia = new NegativeInertiaAccumulator(Constants.Drivetrain.QUICK_TURN);

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

        // DRIVER //////////////////
        double slowMode = 1.0;
        if (leftstick.getRawButton(1) || rightstick.getRawButton(1)) {
            slowMode = 1.0;
            //throttleNia.setScalar(2.0);
        } else {
            //throttleNia.setScalar(1.0);
        }

        // Driver inputs converted to make quickstop and quickturn easier.
        double throttle = (leftstick.getRawAxis(2) + rightstick.getRawAxis(2)) / 2;
        double turning = (leftstick.getRawAxis(2) - rightstick.getRawAxis(2)) / 2;

        throttle += throttleNia.update(throttle);

        // Output drive inputs.
        bot.drive.tankDrive(throttle + turning, throttle - turning);

        // OPERATOR /////////////
        int armState = 1;

        // Operator control logic:
        // Todo: change these to manipulate states of shooter supersystem rather
        // than just the arm subsystem.
        // Todo: make these controls more intuitive.
        if (gamepad.getRawButton(3)) {
            // Todo: change these to reference constants rather than hard-coded
            // numbers.
            bot.shooter.arm.setTarget(2.23);
        } else if (gamepad.getRawButton(4)) {
            bot.shooter.arm.setTarget(2.73);
        } else {
            bot.shooter.arm.setTarget(3.17);
        }

        // Runs the control loops for shooter supersystem.
        bot.shooter.arm.run(armState);
        bot.shooter.puncher.setPuncher(gamepad.getRawButton(8));
        // Intake position and motor controls
        bot.shooter.intake.set(gamepad.getRawAxis(4));
        bot.shooter.intake.setJaw(gamepad.getRawButton(5));

    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        // Nothing in test yet.
        // Todo: do test stuff.
    }

    public void disabledPeriodic() {
        // Print out arm position for debugging
        System.out.println("Arm Position: " + bot.shooter.arm.getPosition());
    }

}
