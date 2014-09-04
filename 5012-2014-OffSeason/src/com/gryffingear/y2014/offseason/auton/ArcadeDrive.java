/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.auton;

import com.gryffingear.y2014.offseason.commands.WaitCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gryffingear.y2014.offseason.commands.ArcadeDriveCommand;

/**
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class ArcadeDrive extends CommandGroup {

    public ArcadeDrive() {
        this.addSequential(new WaitCommand(5));
        this.addSequential(new ArcadeDriveCommand(.5, .5, 5));

    }

}
