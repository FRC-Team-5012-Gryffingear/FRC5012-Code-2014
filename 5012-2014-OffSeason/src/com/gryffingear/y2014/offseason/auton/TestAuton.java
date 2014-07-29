/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.auton;

import com.gryffingear.y2014.offseason.commands.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Autonomous mode to demonstrate wait and arcade drive commands.
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class TestAuton extends CommandGroup {

    public TestAuton() {
        this.addSequential(new WaitCommand(5.0));
        this.addSequential(new ArcadeDriveCommand(0.5, 0.0, 3));
    }

}
