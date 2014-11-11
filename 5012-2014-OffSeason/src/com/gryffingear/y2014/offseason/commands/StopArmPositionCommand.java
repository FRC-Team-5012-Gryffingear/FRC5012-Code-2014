/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.commands;

import com.gryffingear.y2014.offseason.config.Constants;
import com.gryffingear.y2014.offseason.systems.Arm;
import com.gryffingear.y2014.offseason.systems.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for setting
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class StopArmPositionCommand extends Command {

    private double target = Constants.Arm.LOWER_LIMIT;

    public StopArmPositionCommand() {

    }

    protected void initialize() {
        Robot.getInstance().shooter.arm.run(Arm.States.OFF);
    }

    protected void execute() {
        Robot.getInstance().shooter.arm.run(Arm.States.OFF);
    }

    protected void interrupted() {

    }

    protected void end() {

    }

    protected boolean isFinished() {
        return true;
    }
}
