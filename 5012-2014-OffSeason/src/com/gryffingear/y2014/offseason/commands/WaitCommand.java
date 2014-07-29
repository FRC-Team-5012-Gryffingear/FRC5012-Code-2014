/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.commands;

import com.gryffingear.y2014.offseason.systems.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to wait.
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class WaitCommand extends Command {

    private double timeout = 0.0;

    public WaitCommand(double timeout) {
        this.timeout = timeout;
    }

    protected void initialize() {
        this.setTimeout(timeout);
    }

    protected void execute() {

    }

    protected void interrupted() {

    }

    protected void end() {

    }

    protected boolean isFinished() {
        return this.isTimedOut();
    }

}
