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
public class DoNothingAuton extends CommandGroup {

    public DoNothingAuton() {
        this.addSequential(new ArcadeDriveCommand(0, 0, 5));

    }

}
