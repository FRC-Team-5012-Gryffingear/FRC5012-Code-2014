package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Kicker {

    public final int KICK_MODE = 0;
    public final int POS_MODE = 1;
    public final int MAN_MODE = 2;
    public int mode = POS_MODE;

    public final double MAX_POT_VALUE = 3.49; //maximum potentiometer value
    public final double MIN_POT_VALUE = 0.00; //minimum potentiometer value

    public final double KICKED_POS = 0.0; //kicked position value is 0
    //for these lines, the farther we kick the lower the potentiometer value
    public final double STOWED_POS = constrainAngle(2.58+ KICKED_POS); //sets value for the stowed position, which in this case is 3
    public final double NEAR_KICK_POS = constrainAngle(2.35 + KICKED_POS); //value for kicked position is 2
    public final double MED_KICK_POS = constrainAngle(1.8 + KICKED_POS); //value for medium position is 1.5
    public final double FAR_KICK_POS = constrainAngle(1 + KICKED_POS); //value for far kick is 1

    public double KICK_SPEED = 1; //kick speed will go at 100%
    public double MAX_POS_SPEED = 0.6; //position speed will go at 60%

    public final double direction = -1;

    public AnalogChannel kicker_input;
    public TwoMotorPIDOut kicker;

    public double kP, kI, kD;

    private double setpoint;
    private double error;
    private double propOut;
    private double integOut;
    private double derivOut;
    private double prevAngle;
    private double PIDOut;

    private double manualSpeed = 0;

     MultiGraphSender setpointGraph = new MultiGraphSender("Kicker Position and Set");
    
    public Kicker(SpeedController motorA, SpeedController motorB, AnalogChannel pot) {
        kicker = new TwoMotorPIDOut(motorA, motorB);
        kicker_input = pot;

    }

    public int getMode() {
        return mode;
    }
    
    public void setMode(int nmode)
    {
        mode = nmode;
    }

    public void setPID(double P, double I, double D) {
        kP = P;
        kI = I;
        kD = D;
    }

    public double getSetpoint() {
        return setpoint;
    }

    public double getAngle() {
        return kicker_input.getVoltage();
    }

    public double constrainAngle(double angle) {
        angle /= (MAX_POT_VALUE - MIN_POT_VALUE);
        angle -= (int) angle;
        angle *= (MAX_POT_VALUE - MIN_POT_VALUE);

        if (angle < MIN_POT_VALUE) {
            angle += (MAX_POT_VALUE - MIN_POT_VALUE);
        }

        return angle;
    }

    public void setAngle(double newSet) {

        setpoint = newSet;
        SmartDashboard.putNumber("Setpoint-Kicker", setpoint); //puts value in smart dashboard
    }

    public void updateDashboard() {
        kP = SmartDashboard.getNumber("kP", kP); //puts values in smart dashboard
        kI = SmartDashboard.getNumber("kI", kI); //''
        kD = SmartDashboard.getNumber("kD", kD);//''

        setpoint = SmartDashboard.getNumber("Setpoint-Kicker", setpoint);
        SmartDashboard.putNumber("Mode-Kicker", mode);  //puts the values in the smart dashboard
        SmartDashboard.putNumber("Position-Kicker", getAngle());
        SmartDashboard.putNumber("Error-Kicker", error);
        SmartDashboard.putNumber("Proportional-Kicker", propOut);
        SmartDashboard.putNumber("Integral-Kicker", integOut);
        SmartDashboard.putNumber("Derivative-Kicker", derivOut);
        SmartDashboard.putNumber("PIDOUT_Kicker", PIDOut);

        SmartDashboard.putNumber("Kick Mode Err", Math.abs(getAngle()-prevKick));
        
        
        setpointGraph.putNumber("Position-Kicker", getAngle());
        setpointGraph.putNumber("Setpoint-Kicker", setpoint);

    }

    public void calculate() {
        double angle = getAngle();
        error = setpoint - angle;

        if (Math.abs(error)
                > (MAX_POT_VALUE - MIN_POT_VALUE) / 2) {
            if (error > 0) {
                error = error - MAX_POT_VALUE + MIN_POT_VALUE;
            } else {
                error = error + MAX_POT_VALUE - MIN_POT_VALUE;
            }
        }

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
        kicker.pidWrite(direction * out);
    }

    double prevKick;

    private void kickMode() {

        double speed = KICK_SPEED;

        double kick = getAngle();
        
        
        /*
         if (Math.abs(kickerror)
         > (MAX_POT_VALUE - MIN_POT_VALUE) / 2) {
         if (kickerror > 0) {
         kickerror = kickerror - MAX_POT_VALUE + MIN_POT_VALUE;
         } else {
         kickerror = kickerror + MAX_POT_VALUE - MIN_POT_VALUE;
         }
         }
         */
        if (Math.abs(kick-prevKick) > 3) {
            mode = POS_MODE; // Stop kicking, and return to normal operation
            speed = 0;
            System.out.println("KICK DONE ");
        }
        System.out.println(Math.abs(kick-prevKick) + " diff");
        prevKick = kick;
        output(speed);
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
        if (mode == KICK_MODE) {
            System.out.println("KICK MODE!!!");
            kickMode();
        } else if (mode == POS_MODE) {
            System.out.println("POSITION MODE!!!!");
            positionMode();
        } else {
            System.out.println("MANUAL MODE!!!");
            manualMode();
        }

        updateDashboard();
    }

    public void setManual(boolean manual) {
        if (manual) {
            mode = MAN_MODE;
        }
    }

    public void kick() {
        mode = KICK_MODE;
    }
}
