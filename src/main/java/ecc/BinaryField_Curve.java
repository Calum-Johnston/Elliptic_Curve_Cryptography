package ecc;

import java.math.BigInteger;
import java.util.Vector;

public class BinaryField_Curve {

    // Equation: y^2 + xy = x^3 + ax^2 + b
    // Random curves B-m, Koblitz curve K-m

    BigInteger m; // degree of the field
    Vector f;// f gotta go here
    BigInteger a; // a coefficient of the equation
    BigInteger b; // b coefficient of the equation
    ECC_Point G; // Base point
    BigInteger n; // prime order n of the point G (i.e. n.G = O, where O is point at infinity)
    BigInteger h; // the cofactor of the curve (h = #E(Fp)/n)

    public BinaryField_Curve(String m, Vector f, String a, String b, String x, String y, String n, String h){
        this.m = new BigInteger(m, 16);
        this.f = f;
        this.a = new BigInteger(a, 16);
        this.b = new BigInteger(b, 16);
        //this.G = new BinaryField_Point(this, new BigInteger(x, 16), new BigInteger(y, 16));
        this.n = new BigInteger(n, 16);
        this.h = new BigInteger(h, 16);
    }

    public BigInteger getM(){
        return m;
    }

    public void setM(String m){
        this.m = new BigInteger(m, 16);
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

    public ECC_Point getG(){
        return G;
    }

    public void setG(ECC_Point G){
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

