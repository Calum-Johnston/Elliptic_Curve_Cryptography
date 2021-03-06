package ecc.Points;

import java.math.BigInteger;

public class ECC_Point {

    boolean infinity;
    boolean added;
    BigInteger p;

    public ECC_Point(boolean infinity, BigInteger p){
        this.infinity = infinity;
        this.p = p;
        added = false;
    }

    // Point Operations
    public ECC_Point pointAddition(ECC_Point n){return null;}

    public ECC_Point mixedAddition(ECC_Point n){return null;}

    public ECC_Point unifiedAddition(ECC_Point n){return null;}

    public ECC_Point pointDoubling(){return null;}

    public ECC_Point negate(){return null;}

    public ECC_Point convertAffine(){return null;}


    // Field arithmetic
    public BigInteger add(BigInteger x, BigInteger y){
        return x.add(y).mod(p);
    }

    public BigInteger subtract(BigInteger x, BigInteger y){
        return x.subtract(y).mod(p);
    }

    public BigInteger multiple(BigInteger x, BigInteger y){
        return x.multiply(y).mod(p);
    }

    public BigInteger divide(BigInteger x, BigInteger y){
        BigInteger inverseY = y.modInverse(p);
        return x.multiply(inverseY).mod(p);
    }

    public BigInteger inverse(BigInteger x){
        return x.modInverse(p);
    }

    public BigInteger square(BigInteger x){
        return x.pow(2).mod(p);
    }


    // Getters and setters
    public boolean isInfinity() {
        return infinity;
    }

    public void setInfinity(boolean infinity) {
        this.infinity = infinity;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }
}
