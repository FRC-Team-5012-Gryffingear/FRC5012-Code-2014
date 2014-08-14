/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.commands;

import com.gryffingear.y2014.offseason.systems.Arm;
import com.gryffingear.y2014.offseason.systems.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class SetArmPositionCommand extends Command {

    private double target = 0.0;

    public SetArmPositionCommand(double target, double timeout) {
        this.target = target;
        this.setTimeout(timeout);
    }

    protected void initialize() {
        Robot.getInstance().shooter.arm.setTarget(target);
    }

    protected void execute() {
        Robot.getInstance().shooter.arm.run(Arm.States.CLOSED_LOOP);
    }

    protected void interrupted() {

    }

    protected void end() {

    }

    protected boolean isFinished() {
        return isTimedOut()
                || (Math.abs(target - Robot.getInstance().shooter.arm.getPosition()) < 0.01);
    }
}
