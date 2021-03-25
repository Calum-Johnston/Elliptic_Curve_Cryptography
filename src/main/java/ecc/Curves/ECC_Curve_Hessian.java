package ecc.Curves;

import java.math.BigInteger;

public class ECC_Curve_Hessian {

    BigInteger p; // field size
    BigInteger a; // a coefficient of equation (a==1 normal hessian)
    BigInteger d; // b coefficient of the equation
    BigInteger x; // Base point (x)
    BigInteger y; // Base point (y)
    BigInteger n; // prime order n of the point G (i.e. n.G = O, where O is point at infinity)
    BigInteger h; // the cofactor of the curve (h = #E(Fp)/n)

    // Curve ax^3+y^3 = 1 + 3dxy
    public ECC_Curve_Hessian(BigInteger p, BigInteger a, BigInteger d, BigInteger x, BigInteger y, BigInteger n, BigInteger h){
        this.p = p;
        this.a = a;
        this.d = d;
        this.x = x;
        this.y = y;
        this.n = n;
        this.h = h;
    }

    public BigInteger getP(){
        return p;
    }

    public void setP(BigInteger p){
        this.p = p;
    }

    public BigInteger getA() {
        return a;
    }

    public void setA(BigInteger a) {
        this.a = a;
    }

    public BigInteger getD(){
        return d;
    }

    public void setD(BigInteger d){
        this.d = d;
    }

    public BigInteger getX() {
        return x;
    }

    public void setX(BigInteger x) {
        this.x = x;
    }

    public BigInteger getY() {
        return y;
    }

    public void setY(BigInteger y) {
        this.y = y;
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
