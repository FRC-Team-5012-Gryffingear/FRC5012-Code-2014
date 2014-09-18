/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.gryffingear.y2014.offseason;

//import com.gryffingear.y2014.offseason.auton.CheesyVisionMobility;
import com.gryffingear.y2014.offseason.auton.ArcadeDrive;
import com.gryffingear.y2014.offseason.config.Constants;
import com.gryffingear.y2014.offseason.config.Ports;
import com.gryffingear.y2014.offseason.systems.Robot;
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

    //CheesyVision
//    public static CheesyVisionServer server = CheesyVisionServer.getInstance();
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
        //
        // Cancel auton if it is currently running for safety.
        if (currAuton != null) {
            currAuton.cancel();
            currAuton = null;

        }

//        server.setPort(1180);
//        server.start();
        // Initialize new auton.
        // Todo: make this selectable via smartdashboard or something.
        currAuton = new ArcadeDrive();

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
    //NegativeInertiaAccumulator throttleNia = new NegativeInertiaAccumulator(Constants.Drivetrain.QUICK_STOP);
    //Ne`gativeInertiaAccumulator turningNia = new NegativeInertiaAccumulator(Constants.Drivetrain.QUICK_TURN);
    /**
     * This function is called periodically during operator control
     */
    public void teleopInit() {
        //server.stop();
        if (currAuton != null) {
            currAuton.cancel();
            currAuton = null;

        }
        bot.shooter.intake.set(0);
        bot.drive.tankDrive(0, 0);
        bot.shooter.arm.run(-1);
    }

    public void teleopPeriodic() {

        //BLOCKER/////////////////////////
        //bot.blocker.setBlocker(true);///
        // DRIVER ////////////////////////
        // Driver inputs converted to make quickstop and quickturn easier.
        double throttle = (leftstick.getRawAxis(2) + rightstick.getRawAxis(2)) / 2;
        double turning = (leftstick.getRawAxis(2) - rightstick.getRawAxis(2)) / 2;

        /*  double slowMode = 1.0;
         if (leftstick.getRawButton(1) || rightstick.getRawButton(1)) {
         //    slowMode = 1.0;
         throttle = 0;
         throttleNia.setScalar(Constants.Drivetrain.QUICK_STOP * 2);
         } else {

         throttleNia.setScalar(Constants.Drivetrain.QUICK_STOP);
         }

         //turning = turning * Math.abs(turning) * 2.0;
         // Process throttle(fwd/rev movement) input for quickstop
         throttle += throttleNia.update(throttle);
         turning += turningNia.update(turning);
         */
        // Output drive inputs.
        bot.drive.tankDrive(throttle + turning, throttle - turning);

        // OPERATOR /////////////
        int armState = 1;
        double intakeOut = 0.0;

        // Operator control logic:
        if (gamepad.getRawButton(3)) {
            bot.shooter.arm.setTarget(Constants.Arm.LOWGOAL_POS);
        } else if (gamepad.getRawButton(2)) {
            bot.shooter.arm.setTarget(Constants.Arm.PICKUP_POS);
        } else if (gamepad.getRawButton(1)) {
            bot.shooter.arm.setTarget(Constants.Arm.STOW_POS);
        }
        //Roller intake controls.
        if (gamepad.getRawAxis(2) > 0.3) {
            intakeOut = 1.0;
        } else if (gamepad.getRawAxis(2) < -0.3) {
            intakeOut = -1.0;
        } else {
            intakeOut = 0;
        }

        // Runs the control loops for shooter supersystem.
        bot.shooter.arm.run(armState);
        //bot.shooter.puncher.shoot(gamepad.getRawButton(8));
        bot.shooter.intake.set(intakeOut);

        bot.shooter.run(gamepad.getRawButton(8), gamepad.getRawButton(7), gamepad.getRawButton(5));

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
        System.out.println("Arm Offset: " + bot.shooter.arm.getOffset());

    }

}
