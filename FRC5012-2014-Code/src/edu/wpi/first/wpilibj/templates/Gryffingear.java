package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotDrive;

public class Gryffingear extends IterativeRobot {

    //drivetrain
    Victor leftDrive1 = new Victor(1);
    Victor leftDrive2 = new Victor(9);
    Victor rightDrive1 = new Victor(3);
    Victor rightDrive2 = new Victor(4);
    RobotDrive maindrive = new RobotDrive(leftDrive1, leftDrive2, rightDrive1, rightDrive2);

    // Kicker
    Victor kicker1 = new Victor(5);
    Victor kicker2 = new Victor(6);
    AnalogChannel kickerPot = new AnalogChannel(1);
    Kicker kicker = new Kicker(kicker1, kicker2, kickerPot);

    //picker
    Victor picker1 = new Victor(7);
    Victor picker2 = new Victor(8);
    AnalogChannel pickerPot = new AnalogChannel(2);
    Picker picker = new Picker(picker1, picker2, pickerPot);

    //joysticks
    Joystick gamepad = new Joystick(1);
    Joystick leftstick = new Joystick(2);
    Joystick rightstick = new Joystick(3);

    // KICKER PID Values
    // Modify these with your found values
    double kP = 1.00; //
    double kI = 0.00;
    double kD = 0.00;

    // PICKER PID Values
    // Modify these with your found values
    double pP = 0.00;//
    double pI = 0.00;
    double pD = 0.00;

    Ultrasonic distSensor = new Ultrasonic(7);

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        SmartDashboard.putNumber("kP", kP);//puts values in smartDashboard
        SmartDashboard.putNumber("kI", kI);
        SmartDashboard.putNumber("kD", kD);

        kicker.setPID(kP, kI, kD);
        kicker.setAngle(kicker.STOWED_POS);
        kicker.setManual(false);

        SmartDashboard.putNumber("pP", pP);//puts values in smartDashboard
        SmartDashboard.putNumber("pI", pI);
        SmartDashboard.putNumber("pD", pD);
        picker.setPID(pP, pI, pD);
        picker.setManual(false);
    }

    int autoMode = 0; //change value for autonomous or just access with buttons

    public void disabledPeriodic() {

        SmartDashboard.putNumber("Ultrasonic", distSensor.getDistanceIN());

        kicker.updateDashboard();
        picker.updateDashboard();
//Will help choose autonomous mode depending on which button is pressed
        if (DriverStation.getInstance().isAutonomous()) {
            if (gamepad.getRawButton(1)) {
                autoMode = 0;
            } else if (gamepad.getRawButton(2)) {
                autoMode = 1;
            } else if (gamepad.getRawButton(3)) {
                autoMode = 2;
            } else if (gamepad.getRawButton(4)) {
                autoMode = 3;
            }
        }
    }

    public void teleopInit() {
        kP = SmartDashboard.getNumber("kP", kP);
        kI = SmartDashboard.getNumber("kI", kI);
        kD = SmartDashboard.getNumber("kD", kD);
        kicker.setPID(kP, kI, kD);
        pP = SmartDashboard.getNumber("pP", pP);
        pI = SmartDashboard.getNumber("pI", pI);
        pD = SmartDashboard.getNumber("pD", pD);
        picker.setPID(pP, pI, pD);
//this will keep the picker and the kicker from interfering w/ each other in the beginning of the match
        kicker.setAngle(kicker.STOWED_POS); //sets kicker in stowed position
        picker.setAngle(picker.LOAD_POS); //sets picker in a load position

        kicker.setMode(kicker.POS_MODE);
    }

    /**
     * This function is called periodically (~50 times a second) during operator
     * control
     */
    boolean prevKick = false;

    public void teleopPeriodic() {
        SmartDashboard.putNumber("Ultrasonic", distSensor.getDistanceIN());
        //drive
        maindrive.tankDrive(leftstick.getRawAxis(2), rightstick.getRawAxis(2));

        /* This is effectively the same code in RobotC
         motor[left1] = leftstick.getRawAxis(2);
         motor[left2] = leftstick.getRawAxis(2);

         motor[right1] = rightstick.getRawAxis(2);
         motor[right2] = rightstick.getRawAxis(2);
         */
        // if button 6 is not held, then do setpoint mode. Hold a position
        boolean manualMode = gamepad.getRawButton(5);//gamepad.getRawButton(6); //when button 6(R1) is pressed and held it will go to manual mode

        picker.setManual(true);

        if (gamepad.getRawButton(8)) {
            if (gamepad.getRawButton(8) && !prevKick
                    && kicker.getMode() != kicker.KICK_MODE) {
                kicker.kick();
                prevKick = true;
            }
        } else if (!manualMode) { // if not in manual mode then these buttons will determine setpoint
            prevKick = false;
            System.out.println("Teleop manual state");
            kicker.setSpeed(0.0);
            kicker.setMode(kicker.POS_MODE);
            double kickerSetpoint = kicker.getSetpoint();

            // Determine the position to hold
            if (gamepad.getRawButton(1)) {
                kickerSetpoint = kicker.STOWED_POS;
            } else if (gamepad.getRawButton(2)) {
                kickerSetpoint = kicker.NEAR_KICK_POS;
            } else if (gamepad.getRawButton(3)) {
                kickerSetpoint = kicker.MED_KICK_POS;
            } else if (gamepad.getRawButton(4)) {
                kickerSetpoint = kicker.FAR_KICK_POS;
            }

            kicker.setAngle(kickerSetpoint);

            // Determine if we want to kick
        } else { //is in manual mode
            prevKick = false;
            kicker.setManual(true);

            if (gamepad.getRawButton(7)) {
                kicker.setSpeed(.45);
                System.out.println("Kiss passing!!!");
            } else {
                kicker.setSpeed(0);
                manualMode = false;
                System.out.println("going back to auto mode!");
            }
        }

        picker.setSpeed(gamepad.getRawAxis(2) * .75); //code to set speed of the picker, updated at Inland
        picker.run();
        kicker.run();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {

    }

    long startTime;
    long elapsedTime;

    public void autonomousInit() {

        kP = SmartDashboard.getNumber("kP", kP);
        kI = SmartDashboard.getNumber("kI", kI);
        kD = SmartDashboard.getNumber("kD", kD);
        kicker.setPID(kP, kI, kD);
        pP = SmartDashboard.getNumber("pP", pP);
        pI = SmartDashboard.getNumber("pI", pI);
        pD = SmartDashboard.getNumber("pD", pD);
        picker.setPID(pP, pI, pD);

        startTime = System.currentTimeMillis();
        elapsedTime = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        kicker.run();
        picker.run();

        elapsedTime = System.currentTimeMillis() - startTime;
        //will switch autonomous modes
        switch (autoMode) {
            case 1:
                AutoMode1();
                break;
            case 2:
                AutoMode2();
                break;
            case 3:
                AutoMode4();
                break;
            case 0:
            default:
                AutoMode0();
                break;
        }
    }

    /*
     Drive forward for 5 seconds
     */
    private void AutoDrive(long startTime) {
        //this autonomous will drive forward at 30% speed for 5 seconds
        // picker.setManual(true);
        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed < 3250 && elapsed >= 0) {
            maindrive.arcadeDrive(-0.6, 0);
            //picker.setSpeed(.45);
        } else {
            // picker.setSpeed(0);
            maindrive.arcadeDrive(0.0, 0);
        }

        //  picker.run();
    }

    boolean atSet = false;
    boolean pAtSet = false;

    private void AutoKick() {
        if (!atSet) {
            kicker.setAngle(kicker.FAR_KICK_POS);
            picker.setAngle(picker.KICK_POS);

            atSet = Math.abs(kicker.getAngle() - kicker.FAR_KICK_POS) < 0.02;
        } else if (!pAtSet) {
            pAtSet = true;
            kicker.kick();
        }
    }

    // Drive for 5 seconds, starting immediately
    private void AutoMode0() {
        AutoDrive(startTime);
    }

    // Does nothing
    private void AutoMode1() {
        maindrive.arcadeDrive(0.0, 0);
    }

    // Drive for 5 seconds, then kick
    private void AutoMode2() {
        AutoDrive(startTime);
        if (elapsedTime > 5000) {
            AutoKick();
        }
    }

    // Drive for 5 seconds, then kick
    private void AutoMode4() {
        picker.run();
        kicker.run();
        picker.setManual(true);

        long END_DRIVE_PHASE = 3250;
        long END_STAGE_PHASE = 4250;
        long END_SHOOT_PHASE = 5500;

        double KICK_SPEED_STAGE = .25;
        double KICK_SPEED_SHOOT = .75;
        double PICK_SPEED = 0;

        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed < END_DRIVE_PHASE && elapsed >= 0) {
            kicker.setManual(true); //kicker is in manual mode
            if (elapsed < 250 && elapsed > 0) {
                kicker.setSpeed(0.25); //kicker will drop picker
            } else {
                kicker.setSpeed(0); //kicker does nothing
            }
            maindrive.arcadeDrive(-0.6, 0); //drives forward
        } else if (elapsed > END_DRIVE_PHASE && elapsed <= END_STAGE_PHASE) {
            kicker.setManual(true); //kicker still in manual mode
            maindrive.arcadeDrive(0.0, 0);
            kicker.setSpeed(-0.35); //kicker will rest on top of ball
        } else if (elapsed > END_STAGE_PHASE && elapsed <= END_SHOOT_PHASE) {
            //kicker.setManual(false);
            maindrive.arcadeDrive(0.0, 0);
            picker.setSpeed(0);
            kicker.kick(); //kicker will kick
        } else {
            kicker.setManual(true);
            maindrive.arcadeDrive(0.0, 0);
            picker.setSpeed(0);
            kicker.setSpeed(0);
        }

    }

    // Kick, then drive forward for 5 seconds
    private void AutoMode3() {
        AutoKick();
        AutoDrive(startTime + 5000);
    }

}
