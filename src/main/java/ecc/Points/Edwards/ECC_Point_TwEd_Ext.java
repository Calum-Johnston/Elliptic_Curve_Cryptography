package ecc.Points.Edwards;

import ecc.Curves.ECC_Curve_Ed;
import ecc.Points.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_TwEd_Ext extends ECC_Point {

    ECC_Curve_Ed curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;
    BigInteger t;

    // Constructors
    public ECC_Point_TwEd_Ext(ECC_Curve_Ed curve, BigInteger x, BigInteger y, BigInteger z, BigInteger t) {
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }

    public ECC_Point_TwEd_Ext(ECC_Curve_Ed curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_TwEd_Ext pointAddition(ECC_Point p){

        // Convert point
        ECC_Point_TwEd_Ext point = (ECC_Point_TwEd_Ext) p;

        // Computations required 9M+1D
        BigInteger A = multiple(this.x, point.x);
        BigInteger B = multiple(this.y, point.y);
        BigInteger C = multiple(this.z, point.t);
        BigInteger D = multiple(this.t, point.z);
        BigInteger E = add(D, C);
        BigInteger F = subtract(add(multiple(subtract(this.x, this.y), add(point.x, point.y)), B), A);
        BigInteger G = add(B, multiple(curve.getA(), A));
        BigInteger H = subtract(D, C);
        BigInteger x3 = multiple(E, F);
        BigInteger y3 = multiple(G, H);
        BigInteger t3 = multiple(E, H);
        BigInteger z3 = multiple(F, G);

        // Return the calculated value
        return new ECC_Point_TwEd_Ext(this.curve, x3, y3, z3, t3);
    }

    public ECC_Point_TwEd_Ext mixedAddition(ECC_Point p){

        // Convert point
        ECC_Point_TwEd_Ext point = (ECC_Point_TwEd_Ext) p;

        // Computations required 8M+1D
        BigInteger A = multiple(this.x, point.x);
        BigInteger B = multiple(this.y, point.y);
        BigInteger C = multiple(this.z, point.t);
        BigInteger D = this.t;
        BigInteger E = add(D, C);
        BigInteger F = subtract(add(multiple(subtract(this.x, this.y), add(point.x, point.y)), B), A);
        BigInteger G = add(B, multiple(curve.getA(), A));
        BigInteger H = subtract(D, C);
        BigInteger x3 = multiple(E, F);
        BigInteger y3 = multiple(G, H);
        BigInteger t3 = multiple(E, H);
        BigInteger z3 = multiple(F, G);

        // Return the calculated value
        return new ECC_Point_TwEd_Ext(this.curve, x3, y3, z3, t3);
    }

    public ECC_Point_TwEd_Ext reAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_TwEd_Ext pointDoubling(){

        // Computations required 4M+4S
        BigInteger A = square(this.x);
        BigInteger B = square(this.y);
        BigInteger C = multiple(BigInteger.TWO, square(this.z));
        BigInteger D = multiple(curve.getA(), A);
        BigInteger E = subtract(subtract(square(add(this.x, this.y)), A),B);
        BigInteger G = add(D, B);
        BigInteger F = subtract(G, C);
        BigInteger H = subtract(D, B);
        BigInteger x3 = multiple(E, F);
        BigInteger y3 = multiple(G, H);
        BigInteger t3 = multiple(E, H);
        BigInteger z3 = multiple(F, G);

        // Return the calculated value
        return new ECC_Point_TwEd_Ext(this.curve, x3, y3, z3, t3);
    }

    public ECC_Point_TwEd_Ext unifiedAddition(ECC_Point p){

        // Convert point
        ECC_Point_TwEd_Ext point = (ECC_Point_TwEd_Ext) p;

        // Computations required 9M+1D
        BigInteger A = multiple(this.x, point.x);
        BigInteger B = multiple(this.y, point.y);
        BigInteger C = multiple(this.t, multiple(curve.getD(), point.t));
        BigInteger D = multiple(this.z, point.z);
        BigInteger E = subtract(add(multiple(subtract(this.x, this.y), add(point.x, point.y)), B), A);
        BigInteger F = subtract(D, C);
        BigInteger G = add(D, C);
        BigInteger H = subtract(B, multiple(curve.getA(), A));
        BigInteger x3 = multiple(E, F);
        BigInteger y3 = multiple(G, H);
        BigInteger t3 = multiple(E, H);
        BigInteger z3 = multiple(F, G);

        // Return the calculated value
        return new ECC_Point_TwEd_Ext(this.curve, x3, y3, z3, t3);
    }

    public ECC_Point_TwEd_Ext negate(){
        return new ECC_Point_TwEd_Ext(this.curve, this.x.negate(), this.y, this.z, this.t);
    }

    public ECC_Point_TwEd_Ext convertAffine(){
        BigInteger z1 = inverse(z);
        BigInteger x1 = multiple(z1, this.x);
        BigInteger y1 = multiple(z1, this.y);
        BigInteger xy1 = multiple(x1, y1);
        return new ECC_Point_TwEd_Ext(curve, x1, y1, BigInteger.ONE, xy1);
    }



    // Getters and setters
    public BigInteger getX(){
        return x;
    }

    public void setX(BigInteger x){
        this.x = x;
    }

    public BigInteger getY(){
        return y;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }

    public BigInteger getZ() {
        return z;
    }

    public void setZ(BigInteger z) {
        this.z = z;
    }

    public BigInteger getT() {
        return t;
    }

    public void setT(BigInteger t) {
        this.t = t;
    }
}
