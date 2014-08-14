/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.auton;

import com.gryffingear.y2014.offseason.commands.ArcadeDriveCommand;
import com.gryffingear.y2014.offseason.commands.IntakeCommand;
import com.gryffingear.y2014.offseason.commands.SetArmPositionCommand;
import com.gryffingear.y2014.offseason.commands.WaitCommand;
import com.gryffingear.y2014.offseason.config.Constants;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class ShootOneBallAuton extends CommandGroup {

    public ShootOneBallAuton() {
        this.addSequential(new WaitCommand(5.0));
        this.addSequential(new ArcadeDriveCommand(0.5, 0.0, 3));
        this.addSequential(new SetArmPositionCommand(2.5, 2.0));
        this.addSequential(new IntakeCommand(1.0, Constants.Intake.JAW_OPEN));
    }

}
