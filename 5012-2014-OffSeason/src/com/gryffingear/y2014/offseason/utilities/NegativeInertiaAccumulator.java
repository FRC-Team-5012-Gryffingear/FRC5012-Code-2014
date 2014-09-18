package com.gryffingear.y2014.offseason.utilities;

/**
 * Class that represents an accumulator to create a "negative inertia" effect on
 * motors. Where positive inertia would cause the output to ramp up to the
 * target value, Negative inertia will mimic a control loop's overshooting to
 * help make certain mechanisms more controllable. This is essentially a
 * software-configurable motor controller "brake" Useful for mechanisms that
 * tend to coast to a stop and this behavior is not acceptable.
 *
 * @author Jeremy
 *
 */
public class NegativeInertiaAccumulator {

    private double accumulator = 0.0;
    private double prevInput = 0.0;
    private double currInput = 0.0;
    private double scalar = 0.0;
    private double negInertia = 0.0;

    public NegativeInertiaAccumulator(double scalar) {
        this.scalar = scalar;
    }

    public void setScalar(double newScalar) {
        if (this.scalar != newScalar) {
            accumulator = 0;
        }
        this.scalar = newScalar;
    }

    public double update(double input) {
        prevInput = currInput;
        currInput = input;
        negInertia = currInput - prevInput;

        double power = negInertia * scalar;

        accumulator += power;

        double a = .1;

        if (accumulator > a) {
            accumulator -= a;
        } else if (accumulator < -a) {
            accumulator += a;
        } else {
            accumulator = 0;
        }

        return accumulator;

    }

}
