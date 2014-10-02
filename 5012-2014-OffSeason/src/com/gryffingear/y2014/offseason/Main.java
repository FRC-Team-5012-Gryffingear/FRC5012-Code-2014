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
import com.gryffingear.y2014.offseason.utilities.NegativeInertiaAccumulator;
import com.gryffingear.y2014.offseason.utilities.ThrottledPrinter;
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

    // Throttled printer for robot phase change debugging.
    ThrottledPrinter status = new ThrottledPrinter(0.250);
    private long beginPhase = 0;    // Variable holding the start time of each phase

    //Arm State
    int armState = 1;
    //CheesyVision
//    public static CheesyVisionServer server = CheesyVisionServer.getInstance();
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        beginPhase = System.currentTimeMillis();
        bot = Robot.getInstance();
        System.out.println("[STATUS] Robot initialized! Time elapsed: "
                + (System.currentTimeMillis() - beginPhase));
    }

    private CommandGroup currAuton = null;  // Object representing currently
    // selected autonomous mode

    public void autonomousInit() {
        beginPhase = System.currentTimeMillis();
        // Cancel auton if it is currently running for safety.
        if (currAuton != null) {
            System.out.println("[STATUS] Auton was running at this time. Cancelling...");
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
        System.out.println("[STATUS] Auton initialized! Time elapsed: "
                + (System.currentTimeMillis() - beginPhase));
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        // Run the scheduler.
        Scheduler.getInstance().run();
        status.println("[STATUS] Auton running for "
                + (System.currentTimeMillis() - beginPhase) + "ms");
    }

    // Quickturn and quickstop neg inertia accumulators for driver.
    NegativeInertiaAccumulator throttleNia = new NegativeInertiaAccumulator(Constants.Drivetrain.QUICK_STOP);
    NegativeInertiaAccumulator turningNia = new NegativeInertiaAccumulator(Constants.Drivetrain.QUICK_TURN);

    /**
     * This function is called periodically during operator control
     */
    public void teleopInit() {
        beginPhase = System.currentTimeMillis();
        //server.stop();
        if (currAuton != null) {
            System.out.println("[STATUS] Auton was running at this time. Cancelling...");
            currAuton.cancel();
            currAuton = null;

        }
        bot.reset();
        System.out.println("[STATUS] Teleop initialized! Time elapsed: "
                + (System.currentTimeMillis() - beginPhase));
        armState = 1;
    }

    public void teleopPeriodic() {

        //BLOCKER/////////////////////////
        //bot.blocker.setBlocker(true);///
        // DRIVER ////////////////////////
        // Driver inputs converted to make quickstop and quickturn easier.
        double throttle = (leftstick.getRawAxis(2) + rightstick.getRawAxis(2)) / 2;
        double turning = (leftstick.getRawAxis(2) - rightstick.getRawAxis(2)) / 2;

        double slowMode = 1.0;
        if (leftstick.getRawButton(1) || rightstick.getRawButton(1)) {
            //    slowMode = 1.0;
            throttle = 0;
            throttleNia.setScalar(Constants.Drivetrain.QUICK_STOP * 2);
        } else {

            throttleNia.setScalar(Constants.Drivetrain.QUICK_STOP);
        }

        //turning = turning * Math.abs(turning) * 2.0;
        // Process throttle(fwd/rev movement) input for quickstop
        throttle -= throttleNia.update(throttle);
        turning -= turningNia.update(turning);

        // Output drive inputs.
        bot.drive.tankDrive(throttle + turning, throttle - turning);

        // OPERATOR /////////////
        double intakeOut = 0.0;

        if (gamepad.getRawButton(9) & gamepad.getRawButton(10)) {
            armState = 0;
        }
        // Operator control logic:
        if (gamepad.getRawButton(3)) {
            bot.shooter.arm.setTarget(Constants.Arm.LOWGOAL_POS);
        } else if (gamepad.getRawButton(2)) {
            bot.shooter.arm.setTarget(Constants.Arm.PICKUP_POS);
        } else if (gamepad.getRawButton(1)) {
            bot.shooter.arm.setTarget(Constants.Arm.STOW_POS);
        }
        bot.shooter.arm.setManual(gamepad.getRawAxis(4));

        //Roller intake controls.
        if (gamepad.getRawAxis(2) > 0.3) {
            intakeOut = -1;
        } else if (gamepad.getRawAxis(2) < -0.3) {
            intakeOut = .5;
        } else {
            intakeOut = 0;
        }

        // Runs the control loops for shooter supersystem.
        bot.shooter.arm.run(armState);
        //bot.shooter.puncher.shoot(gamepad.getRawButton(8));
        bot.shooter.intake.set(intakeOut);

        bot.shooter.run(gamepad.getRawButton(8), gamepad.getRawButton(7), gamepad.getRawButton(5));

        status.println("[STATUS] Teleop running for "
                + (System.currentTimeMillis() - beginPhase) + "ms");
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        // Nothing in test yet.
        // Todo: do test stuff.
    }

    public void disabledInit() {
        beginPhase = System.currentTimeMillis();
    }

    public void disabledPeriodic() {

        status.println("[STATUS] Disabled running for "
                + (System.currentTimeMillis() - beginPhase) + "ms");
        // Print out arm position for debugging
        System.out.println("Arm Position: " + bot.shooter.arm.getPosition());
        System.out.println("Arm Offset: " + bot.shooter.arm.getOffset());

    }

}
