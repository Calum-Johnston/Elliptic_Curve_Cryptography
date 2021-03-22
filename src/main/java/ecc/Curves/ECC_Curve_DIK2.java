package ecc.Curves;

import ecc.Doche_Icart_Kohel.ECC_Point_DIK2;

import java.math.BigInteger;

public class ECC_Curve_DIK2  {

    BigInteger p; // field size
    BigInteger a; // a coefficient of the equation
    ECC_Point_DIK2 G; // Base point
    BigInteger n; // prime order n of the point G (i.e. n.G = O, where O is point at infinity)
    BigInteger h; // the cofactor of the curve (h = #E(Fp)/n)

    // Curve y^2 = x^3 + ax^2 + 16ax
    public ECC_Curve_DIK2(BigInteger p, BigInteger a,  BigInteger x, BigInteger y, BigInteger n, BigInteger h){
        this.p = p;
        this.a = a;
        this.G = new ECC_Point_DIK2(this, x, y, BigInteger.ONE, BigInteger.ONE);
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

    public ECC_Point_DIK2 getG(){
        return G;
    }

    public void setG(ECC_Point_DIK2 G){
        this.G = G;
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
