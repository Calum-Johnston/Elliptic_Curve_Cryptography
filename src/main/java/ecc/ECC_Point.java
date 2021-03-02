package ecc;

import ecc.Weierstrass.ECC_Curve_Weierstrass;

import java.math.BigInteger;

public abstract class ECC_Point {

    ECC_Curve_Weierstrass curve;
    boolean infinity;

    public ECC_Point(ECC_Curve_Weierstrass curve, boolean infinity){
        this.curve = curve;
        this.infinity = infinity;
    }

    // Abstract methods
    public abstract ECC_Point doubleAndAdd(BigInteger n);

    public abstract ECC_Point Montgomery(BigInteger n);

    public abstract ECC_Point pointAddition(ECC_Point point);


    // Field arithmetic
    public BigInteger add(BigInteger x, BigInteger y){
        return x.add(y).mod(curve.getP());
    }

    public BigInteger subtract(BigInteger x, BigInteger y){
        return x.subtract(y).mod(curve.getP());
    }

    public BigInteger multiple(BigInteger x, BigInteger y){
        return x.multiply(y).mod(curve.getP());
    }

    public BigInteger divide(BigInteger x, BigInteger y){
        BigInteger inverseY = y.modInverse(curve.getP());
        return x.multiply(inverseY).mod(curve.getP());
    }

    public BigInteger inverse(BigInteger x){
        return x.modInverse(curve.getP());
    }

    public BigInteger square(BigInteger x){
        return x.pow(2).mod(curve.getP());
    }


    // Getters and setters
    public ECC_Curve_Weierstrass getCurve() {
        return curve;
    }

    public void setCurve(ECC_Curve_Weierstrass curve) {
        this.curve = curve;
    }

    public boolean isInfinity() {
        return infinity;
    }

    public void setInfinity(boolean infinity) {
        this.infinity = infinity;
    }
}
