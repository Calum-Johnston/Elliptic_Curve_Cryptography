package ecc.Curves;

import java.math.BigInteger;

public class ECC_Curve_JacInt {

    BigInteger p; // field size
    BigInteger a; // a coefficient of the equation
    BigInteger s; // Base point (s)
    BigInteger c; // Base point (c)
    BigInteger d; // Base point (d)
    BigInteger n; // prime order n of the point G (i.e. n.G = O, where O is point at infinity)
    BigInteger h; // the cofactor of the curve (h = #E(Fp)/n)

    // Curve s^2+c^2=1, as^2+d^2=1
    public ECC_Curve_JacInt(BigInteger p, BigInteger a, BigInteger s, BigInteger c, BigInteger d, BigInteger n, BigInteger h){
        this.p = p;
        this.a = a;
        this.s = s;
        this.c = c;
        this.d = d;
        this.n = n;
        this.h = h;
    }

    public BigInteger getP(){
        return p;
    }

    public void setP(BigInteger p){
        this.p = p;
    }

    public BigInteger getA(){
        return a;
    }

    public void setA(BigInteger a){
        this.a = a;
    }

    public BigInteger getS() {
        return s;
    }

    public void setS(BigInteger s) {
        this.s = s;
    }

    public BigInteger getC() {
        return c;
    }

    public void setC(BigInteger c) {
        this.c = c;
    }

    public BigInteger getD() {
        return d;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }

    public BigInteger getN(){
        return n;
    }

    public void setN(BigInteger n){
        this.n = n;
    }

    public BigInteger getH(){
        return h;
    }

    public void setH(BigInteger h){
        this.h = h;
    }

}
