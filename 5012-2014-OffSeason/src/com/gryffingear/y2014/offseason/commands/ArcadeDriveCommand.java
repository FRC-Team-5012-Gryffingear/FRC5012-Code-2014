/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.commands;

import com.gryffingear.y2014.offseason.systems.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for arcade driving.
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class ArcadeDriveCommand extends Command {

    private double speed = 0.0;
    private double turn = 0.0;
    private double timeout = 0.0;

    public ArcadeDriveCommand(double speed, double turn, double timeout) {
        this.speed = -speed;
        this.speed = -turn;
        this.speed = timeout;
        this.setTimeout(timeout);
    }

    protected void initialize() {
        Robot.getInstance().drive.tankDrive(speed + turn, speed - turn);
    }

    protected boolean isFinished() {
        return this.isTimedOut();
    }

    protected void execute() {

    }

    protected void end() {
        Robot.getInstance().drive.tankDrive(0.0, 0.0);
    }

    protected void interrupted() {
        Robot.getInstance().drive.tankDrive(0.0, 0.0);
    }
}
