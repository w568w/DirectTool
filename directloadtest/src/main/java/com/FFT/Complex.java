package com.FFT;

/**
 * Created by Administrator on 16-12-11.
 */
public class Complex {

    private final double x;
    private final double y;

    /**
     Constructs the complex number z = u + i*v
     @param u Real part
     @param v Imaginary part
     */
    public Complex(double u,double v) {
        this.x =u;
        this.y =v;
    }

    /**
     Real part of this Complex number
     (the x-coordinate in rectangular coordinates).
     @return Re[z] where z is this Complex number.
     */
    public double real() {
        return this.x;
    }

    /**
     Imaginary part of this Complex number
     (the y-coordinate in rectangular coordinates).
     @return Im[z] where z is this Complex number.
     */
    public double imag() {
        return this.y;
    }

    /**
     Modulus of this Complex number
     (the distance from the origin in polar coordinates).
     @return |z| where z is this Complex number.
     */
    public double mod() {
        if (this.x !=0 || this.y !=0) {
            return Math.sqrt(this.x * this.x + this.y * this.y);
        } else {
            return 0d;
        }
    }

    /**
     Argument of this Complex number
     (the angle in radians with the x-axis in polar coordinates).
     @return arg(z) where z is this Complex number.
     */
    public double arg() {
        return Math.atan2(this.y, this.x);
    }

    /**
     Complex conjugate of this Complex number
     (the conjugate of x+i*y is x-i*y).
     @return z-bar where z is this Complex number.
     */
    public Complex conj() {
        return new Complex(this.x,-this.y);
    }

    /**
     Addition of Complex numbers (doesn't change this Complex number).
     <br>(x+i*y) + (s+i*t) = (x+s)+i*(y+t).
     @param w is the number to add.
     @return z+w where z is this Complex number.
     */
    public Complex plus(Complex w) {
        return new Complex(this.x +w.real(), this.y +w.imag());
    }

    /**
     Subtraction of Complex numbers (doesn't change this Complex number).
     <br>(x+i*y) - (s+i*t) = (x-s)+i*(y-t).
     @param w is the number to subtract.
     @return z-w where z is this Complex number.
     */
    public Complex minus(Complex w) {
        return new Complex(this.x -w.real(), this.y -w.imag());
    }

    /**
     Complex multiplication (doesn't change this Complex number).
     @param w is the number to multiply by.
     @return z*w where z is this Complex number.
     */
    public Complex times(Complex w) {
        return new Complex(this.x *w.real()- this.y *w.imag(), this.x *w.imag()+ this.y *w.real());
    }

    /**
     Division of Complex numbers (doesn't change this Complex number).
     <br>(x+i*y)/(s+i*t) = ((x*s+y*t) + i*(y*s-y*t)) / (s^2+t^2)
     @param w is the number to divide by
     @return new Complex number z/w where z is this Complex number
     */
    public Complex div(Complex w) {
        double den=Math.pow(w.mod(),2);
        return new Complex((this.x *w.real()+ this.y *w.imag())/den,(this.y *w.real()- this.x *w.imag())/den);
    }

    /**
     Complex exponential (doesn't change this Complex number).
     @return exp(z) where z is this Complex number.
     */
    public Complex exp() {
        return new Complex(Math.exp(this.x)*Math.cos(this.y),Math.exp(this.x)*Math.sin(this.y));
    }

    /**
     Principal branch of the Complex logarithm of this Complex number.
     (doesn't change this Complex number).
     The principal branch is the branch with -pi < arg <= pi.
     @return log(z) where z is this Complex number.
     */
    public Complex log() {
        return new Complex(Math.log(mod()), arg());
    }

    /**
     Complex square root (doesn't change this complex number).
     Computes the principal branch of the square root, which
     is the value with 0 <= arg < pi.
     @return sqrt(z) where z is this Complex number.
     */
    public Complex sqrt() {
        double r=Math.sqrt(mod());
        double theta= arg()/2;
        return new Complex(r*Math.cos(theta),r*Math.sin(theta));
    }

    // Real cosh function (used to compute complex trig functions)
    private double cosh(double theta) {
        return (Math.exp(theta)+Math.exp(-theta))/2;
    }

    // Real sinh function (used to compute complex trig functions)
    private double sinh(double theta) {
        return (Math.exp(theta)-Math.exp(-theta))/2;
    }

    /**
     Sine of this Complex number (doesn't change this Complex number).
     <br>sin(z) = (exp(i*z)-exp(-i*z))/(2*i).
     @return sin(z) where z is this Complex number.
     */
    public Complex sin() {
        return new Complex(this.cosh(this.y)*Math.sin(this.x), this.sinh(this.y)*Math.cos(this.x));
    }

    /**
     Cosine of this Complex number (doesn't change this Complex number).
     <br>cos(z) = (exp(i*z)+exp(-i*z))/ 2.
     @return cos(z) where z is this Complex number.
     */
    public Complex cos() {
        return new Complex(this.cosh(this.y)*Math.cos(this.x),-this.sinh(this.y)*Math.sin(this.x));
    }

    /**
     Hyperbolic sine of this Complex number
     (doesn't change this Complex number).
     <br>sinh(z) = (exp(z)-exp(-z))/2.
     @return sinh(z) where z is this Complex number.
     */
    public Complex sinh() {
        return new Complex(this.sinh(this.x)*Math.cos(this.y), this.cosh(this.x)*Math.sin(this.y));
    }

    /**
     Hyperbolic cosine of this Complex number
     (doesn't change this Complex number).
     <br>cosh(z) = (exp(z) + exp(-z)) / 2.
     @return cosh(z) where z is this Complex number.
     */
    public Complex cosh() {
        return new Complex(this.cosh(this.x)*Math.cos(this.y), this.sinh(this.x)*Math.sin(this.y));
    }

    /**
     Tangent of this Complex number (doesn't change this Complex number).
     <br>tan(z) = sin(z)/cos(z).
     @return tan(z) where z is this Complex number.
     */
    public Complex tan() {
        return sin().div(cos());
    }

    /**
     Negative of this complex number (chs stands for change sign).
     This produces a new Complex number and doesn't change
     this Complex number.
     <br>-(x+i*y) = -x-i*y.
     @return -z where z is this Complex number.
     */
    public Complex chs() {
        return new Complex(-this.x,-this.y);
    }

    /**
     String representation of this Complex number.
     @return x+i*y, x-i*y, x, or i*y as appropriate.
     */
    public String toString() {
        if (this.x !=0 && this.y >0) {
            return this.x +" + "+ this.y +"i";
        }
        if (this.x !=0 && this.y <0) {
            return this.x +" - "+ -this.y +"i";
        }
        if (this.y ==0) {
            return String.valueOf(this.x);
        }
        if (this.x ==0) {
            return this.y +"i";
        }
        // shouldn't get here (unless Inf or NaN)
        return this.x +" + i*"+ this.y;

    }
}

