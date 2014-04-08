package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Picker {

    public final int POS_MODE = 0;
    public final int MAN_MODE = 1;
    public int mode;

    public final double MAX_POT_VALUE = 3.26; //maximum potentiometer value
    public final double MIN_POT_VALUE = 0.9 ;//minimum potentiometer value

    public final double LOAD_POS = 1.01; // Change to the value of the picker on the floor(with bumpers)
    public final double STOWED_POS = constrainAngle(2.07 + LOAD_POS); // Change the '3' to your stowed position
    public final double KICK_POS = constrainAngle(0.5 + LOAD_POS); // Change the '2' to your kicking position

    public double MAX_POS_SPEED = 1.00;

    public final double direction = -1;

    public AnalogChannel picker_input;
    public TwoMotorPIDOut picker;

    public double kP, kI, kD;

    private double setpoint;
    private double error;
    private double propOut;
    private double integOut;
    private double derivOut;
    private double prevAngle;
    private double PIDOut;

    private double manualSpeed = 0;
    
    MultiGraphSender setpointGraph = new MultiGraphSender("Picker Position and Set");

    public Picker(SpeedController motorA, SpeedController motorB, AnalogChannel pot) {
        picker = new TwoMotorPIDOut(motorA, motorB);
        picker_input = pot;

    }

    public int getMode() {
        return mode;
    }

    public void setPID(double P, double I, double D) {
        kP = P;
        kI = I;
        kD = D;
    }
    
    public double getSetpoint() { //remove me if i screw up your code and destroy your bot :)
        return setpoint;
    }
    
    
    public double getAngle() {
        return picker_input.getVoltage();
    }

    public double constrainAngle(double angle) {
        if (angle > MAX_POT_VALUE) {
            angle = MAX_POT_VALUE;
        } else if (angle < MIN_POT_VALUE) {
            angle = MIN_POT_VALUE;
        }

        return angle;
    }

    public void setAngle(double newSet) {

        setpoint = newSet;
        SmartDashboard.putNumber("Setpoint-Picker", setpoint);
    }

    public void updateDashboard() {
        kP = SmartDashboard.getNumber("pP", kP);
        kI = SmartDashboard.getNumber("pI", kI);
        kD = SmartDashboard.getNumber("pD", kD);

        setpoint = SmartDashboard.getNumber("Setpoint-Picker", setpoint);
        SmartDashboard.putNumber("Mode-Picker", mode);
        SmartDashboard.putNumber("Position-Picker", getAngle());
        SmartDashboard.putNumber("Error-Picker", error);
        SmartDashboard.putNumber("Proportional-Picker", propOut);
        SmartDashboard.putNumber("Integral-Picker", integOut);
        SmartDashboard.putNumber("Derivative-Picker", derivOut);
        SmartDashboard.putNumber("Setpoint - Picker", setpoint); // fdjkla
        
        
        setpointGraph.putNumber("Position-Picker", getAngle());
        setpointGraph.putNumber("Setpoint-Picker", setpoint);
    }

    public void calculate() {
        double angle = getAngle();
        error = setpoint - angle;
        propOut = kP * error;
        derivOut = kD * (angle - prevAngle);

        double sum = propOut - derivOut;
        if (Math.abs(sum + integOut + kI * error) > MAX_POS_SPEED) {
            sum = (sum < 0) ? -MAX_POS_SPEED : MAX_POS_SPEED;
        } else {
            integOut += kI * error;
            sum += integOut;
        }

        PIDOut = sum;
    }

    public void output(double out) {

        out *= direction;
        
        // Stop the picker motor from running if it is past the sensor's range
        /*if ((out > 0 && getAngle() <= MIN_POT_VALUE)
                || (out < 0 && getAngle() >= MAX_POT_VALUE)) {
            out = 0;
        }*/
        picker.pidWrite(out);
        SmartDashboard.putNumber("PIDOUT_Picker", out);
    }

    private void positionMode() {
        calculate();
        output(PIDOut);
    }

    private void manualMode() {
        output(manualSpeed);
    }

    public void setSpeed(double speed) {
        manualSpeed = speed;
    }

    public void run() {
        if (mode == POS_MODE) {
            positionMode();
        } else {
            manualMode();
        }

        updateDashboard();
    }

    public void setManual(boolean manual) {
        if (manual) {
            mode = MAN_MODE;
        } else {
            mode = POS_MODE;
        }
    }
}
