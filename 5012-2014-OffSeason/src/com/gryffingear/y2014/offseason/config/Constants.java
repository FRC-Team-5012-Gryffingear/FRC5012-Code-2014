/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, cho
 public static class Arm {

 public static double ARM_P = .50;
 public static double ARM_I = 0.0;
 public static double ARM_D = 0.0;

 public static double VOLTS_TO_DEGREES = 1.0;

 }ose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.config;

/**
 * Class containing commonly changed constants
 *
 * @author jeremy.germita@gmail.com (Jeremy Germita)
 */
public class Constants {

    /**
     * Class containing constants relevant to the drivetrain system.
     */
    public static class Drivetrain {

        public static double QUICK_STOP = 2.0;
        public static double QUICK_TURN = 2.0;
    }

    /**
     * Class containing constants relevant to the arm joint system.
     */
    public static class Arm {

        public static double ARM_P = .8;
        public static double ARM_I = 0.0;
        public static double ARM_D = 0.0;

        public static double VOLTS_TO_DEGREES = 1.0;

        public static double UPPER_LIMIT = 4.945;
        public static double LOWER_LIMIT = UPPER_LIMIT - 2.339;

        public static double STOW_POS = UPPER_LIMIT - .05;
        public static double INBOUND_POS = UPPER_LIMIT - 1.196;
        public static double PICKUP_POS = UPPER_LIMIT - 1.8384;
        public static double LOWGOAL_POS = UPPER_LIMIT - 0.2751;

    }

    /**
     * Class containing constants relevant to the intake system.
     */
    public static class Intake {

        public static boolean JAW_OPEN = true;
        public static boolean JAW_CLOSE = !JAW_OPEN;
    }

    /**
     * Class containing constants relevant to the puncher system.
     */
    public static class Puncher {

        public static boolean PUNCHER_PUNCHED = true;
    }

    /**
     * Class containing constants relevant to the shooter supersystem.
     */
    public static class Shooter {

    }

    public static class Blocker {

        public static boolean BLOCKER_RETRACT = true;
    }

}
