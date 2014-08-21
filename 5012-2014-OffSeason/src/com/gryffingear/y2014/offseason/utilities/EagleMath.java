/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gryffingear.y2014.offseason.utilities;

import com.sun.squawk.util.MathUtils;

/**
 * Class of various math utilities.
 *
 * @author robotics
 */
public class EagleMath {

    /**
     * Scales an input of a range between istart and istop to a range between
     * ostart and ostop
     *
     * @param value input value
     * @param istart input value's lower limit
     * @param istop input value's upper limit
     * @param ostart output value's lower limit
     * @param ostop output value's upper limit
     * @return the scaled value
     */
    public static float map(float value, float istart, float istop, float ostart, float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
    }

    /**
     * Checks to see if input is between low and high
     *
     * @param input input value
     * @param low lower threshold
     * @param high upper threshold
     * @return true if low < input < high
     */
    public static boolean isInBand(double input, double low, double high) {
        return input > low && input < high;
    }

    /**
     * Creates a deadband.
     *
     * @param in
     * @param width
     * @return
     */
    public static double deadband(double in, double width) {
        if (Math.abs(in) < width) {
            in = 0;
        }
        return in;
    }

    /**
     * Collapse number down to +1 0 or -1 depending on sign. Typically used in
     * compare routines to collapse a difference of two longs to an int. This is
     * much faster than Sun's Long.signum under Java.exe.
     *
     * @param diff number to be collapsed to an int preserving sign and
     * zeroness. usually represents the difference of two long.
     * @return true signum of diff, +1, 0 or -1.
     */
    public static int signum(double diff) {
        if (diff > 0) {
            return 1;
        }
        if (diff < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Truncates an input(value) to specified decimal places
     *
     * @param value
     * @param places
     * @return
     */
    public static double truncate(double value, double places) {
        double multiplier = MathUtils.pow(10, places);
        return Math.floor(multiplier * value) / multiplier;
    }

    public static double fMod(double value, double x) {
        // Negate if and only if base is negative.
        // (Java's modulo isn't mathematically pretty in this way.)
        double sign = (value < 0) ? -1 : 1;
        return sign * (Math.abs(x) % Math.abs(value));
    }

    public static double cap(double in, double low, double high) {
        if (in < low) {
            in = low;
        }
        if (in > high) {
            in = high;
        }
        return in;
    }

    /**
     * Returns a curve similar to the square curve, but the negative range is
     * inverted K is a scaling constant. default to 1 if no scaling needed
     *
     * @param in
     * @param k
     * @return
     */
    public static double signedSquare(double in, double k) {
        return in * Math.abs(in) * k;
    }

    /**
     * Constrains an infinitely boundless angle(-inf to inf) to lower and higher
     * bounds
     *
     * @param angle input angle
     * @param minAngle lower bound angle
     * @param maxAngle higher bound for angle
     * @return Angle constrained between min and max
     */
    public static double constrainAngle(double angle, double minAngle, double maxAngle) {
        double range = maxAngle - minAngle;
        double offset = minAngle;
        angle /= range;
        angle -= (int) angle;
        angle *= range;
        if (angle < offset) {
            angle += range;
        }
        return angle;
    }
}
