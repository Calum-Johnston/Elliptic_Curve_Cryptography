package ecc;

import java.math.BigInteger;

public class ECC_Point {

    ECC_Curve curve;
    boolean infinity;

    public ECC_Point(ECC_Curve curve, boolean infinity){
        this.curve = curve;
        this.infinity = infinity;
    }

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
        return x.multiply(y).mod(curve.getP());
    }

    public BigInteger square(BigInteger x){
        return x.pow(2).mod(curve.getP());
    }

}
