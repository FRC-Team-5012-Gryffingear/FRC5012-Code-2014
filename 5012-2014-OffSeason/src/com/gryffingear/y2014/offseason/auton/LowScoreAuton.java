/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.auton;

import com.gryffingear.y2014.offseason.commands.WaitCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gryffingear.y2014.offseason.commands.ArcadeDriveCommand;
import com.gryffingear.y2014.offseason.commands.IntakeCommand;
import com.gryffingear.y2014.offseason.commands.SetArmPositionCommand;
import com.gryffingear.y2014.offseason.commands.StopArmPositionCommand;
import com.gryffingear.y2014.offseason.config.Constants;

/**
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class LowScoreAuton extends CommandGroup {

    public LowScoreAuton() {
        this.addSequential(new WaitCommand(1.5));
        this.addSequential(new ArcadeDriveCommand(.65, -0.0171875, 5));
        this.addSequential(new SetArmPositionCommand(Constants.Arm.LOWGOAL_POS, 2.0));
        this.addSequential(new StopArmPositionCommand());
        this.addSequential(new IntakeCommand(Constants.Intake.OUTTAKE_SPEED,
                Constants.Intake.JAW_CLOSE));
        this.addSequential(new WaitCommand(1.0));
        this.addSequential(new IntakeCommand(0,
                Constants.Intake.JAW_CLOSE));
    }

    public LowScoreAuton(double turnTrim) {
        this.addSequential(new WaitCommand(1.5));
        this.addSequential(new ArcadeDriveCommand(.65, turnTrim, 5));
        this.addSequential(new SetArmPositionCommand(Constants.Arm.LOWGOAL_POS, 2.0));
        this.addSequential(new StopArmPositionCommand());
        this.addSequential(new IntakeCommand(Constants.Intake.OUTTAKE_SPEED,
                Constants.Intake.JAW_CLOSE));
        this.addSequential(new WaitCommand(1.0));
        this.addSequential(new IntakeCommand(0,
                Constants.Intake.JAW_CLOSE));
    }

}
