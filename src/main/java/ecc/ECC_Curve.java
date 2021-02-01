package ecc;

import java.math.BigInteger;

public class ECC_Curve {

    BigInteger p; // prime specifying the base field GF(p)
    BigInteger a; // a coefficient of the equation
    BigInteger b; // b coefficient of the equation
    ECC_Point_Aff G; // Base point
    BigInteger n; // prime order n of the point G (i.e. n.G = O, where O is point at infinity)
    BigInteger h; // the cofactor of the curve (h = #E(Fp)/n)

    public ECC_Curve(BigInteger p, BigInteger a, BigInteger b, BigInteger x, BigInteger y, BigInteger n, BigInteger h){
        this.p = p;
        this.a = a;
        this.b = b;
        this.G = new ECC_Point_Aff(this, x, y);
        this.n = n;
        this.h = h;
    }

    public BigInteger getP(){
        return p;
    }

    public void setP(String p){
        this.p = new BigInteger(p, 16);
    }

    public BigInteger getA(){
        return a;
    }

    public void setA(String a){
        this.a = new BigInteger(a, 16);
    }

    public BigInteger getB(){
        return b;
    }

    public void setB(String b){
        this.b = new BigInteger(b, 16);
    }

    public ECC_Point_Aff getG(){
        return G;
    }

    public void setG(ECC_Point_Aff G){
        this.G = G;
    }

    public BigInteger getN(){
        return n;
    }

    public void setN(String n){
        this.n = new BigInteger(n, 16);
    }

    public BigInteger getH(){
        return h;
    }

    public void setH(String h){
        this.h = new BigInteger(h, 16);
    }

}
