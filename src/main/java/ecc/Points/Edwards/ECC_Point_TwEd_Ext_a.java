package ecc.Points.Edwards;

import ecc.Curves.ECC_Curve_Ed;
import ecc.Points.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_TwEd_Ext_a extends ECC_Point {

    ECC_Curve_Ed curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;
    BigInteger t;

    // Constructors
    public ECC_Point_TwEd_Ext_a(ECC_Curve_Ed curve, BigInteger x, BigInteger y, BigInteger z, BigInteger t) {
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }

    public ECC_Point_TwEd_Ext_a(ECC_Curve_Ed curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_TwEd_Ext_a pointAddition(ECC_Point p){

        // Convert point
        ECC_Point_TwEd_Ext_a point = (ECC_Point_TwEd_Ext_a) p;

        // Computations required 8M
        BigInteger A = multiple(subtract(this.y, this.x), add(point.y, point.x));
        BigInteger B = multiple(add(this.x, this.y), subtract(point.y, point.x));
        BigInteger C = multiple(this.z, multiple(BigInteger.TWO, point.t));
        BigInteger D = multiple(this.t, multiple(BigInteger.TWO, point.z));
        BigInteger E = add(D, C);
        BigInteger F = subtract(B, A);
        BigInteger G = add(B, A);
        BigInteger H = subtract(D, C);
        BigInteger x3 = multiple(E, F);
        BigInteger y3 = multiple(G, H);
        BigInteger t3 = multiple(E, H);
        BigInteger z3 = multiple(F, G);

        // Return the calculated value
        return new ECC_Point_TwEd_Ext_a(this.curve, x3, y3, z3, t3);
    }

    public ECC_Point_TwEd_Ext_a mixedAddition(ECC_Point p){

        // Convert point
        ECC_Point_TwEd_Ext_a point = (ECC_Point_TwEd_Ext_a) p;

        // Computations required 7M
        BigInteger A = multiple(subtract(this.y, this.x), add(point.y, point.x));
        BigInteger B = multiple(add(this.x, this.y), subtract(point.y, point.x));
        BigInteger C = multiple(this.z, multiple(BigInteger.TWO, point.t));
        BigInteger D = multiple(this.t, BigInteger.TWO);
        BigInteger E = add(D, C);
        BigInteger F = subtract(B, A);
        BigInteger G = add(B, A);
        BigInteger H = subtract(D, C);
        BigInteger x3 = multiple(E, F);
        BigInteger y3 = multiple(G, H);
        BigInteger t3 = multiple(E, H);
        BigInteger z3 = multiple(F, G);

        // Return the calculated value
        return new ECC_Point_TwEd_Ext_a(this.curve, x3, y3, z3, t3);
    }

    public ECC_Point_TwEd_Ext_a pointDoubling(){

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
        return new ECC_Point_TwEd_Ext_a(this.curve, x3, y3, z3, t3);
    }

    public ECC_Point_TwEd_Ext_a unifiedAddition(ECC_Point p){

        // Convert point
        ECC_Point_TwEd_Ext_a point = (ECC_Point_TwEd_Ext_a) p;

        // Computations required 8M+1D
        BigInteger A = multiple(subtract(this.y, this.x), subtract(point.y, point.x));
        BigInteger B = multiple(add(this.x, this.y), add(point.y, point.x));
        BigInteger C = multiple(this.t, multiple(curve.getD(), multiple(BigInteger.TWO, point.t)));
        BigInteger D = multiple(this.z, multiple(BigInteger.TWO, point.z));
        BigInteger E = subtract(B, A);
        BigInteger F = subtract(D, C);
        BigInteger G = add(D, C);
        BigInteger H = add(B, A);
        BigInteger x3 = multiple(E, F);
        BigInteger y3 = multiple(G, H);
        BigInteger t3 = multiple(E, H);
        BigInteger z3 = multiple(F, G);

        // Return the calculated value
        return new ECC_Point_TwEd_Ext_a(this.curve, x3, y3, z3, t3);
    }

    public ECC_Point_TwEd_Ext_a negate(){
        return new ECC_Point_TwEd_Ext_a(this.curve, this.x.negate(), this.y, this.z, this.t.negate());
    }

    public ECC_Point_TwEd_Ext_a convertAffine(){
        BigInteger z1 = inverse(z);
        BigInteger x1 = multiple(z1, this.x);
        BigInteger y1 = multiple(z1, this.y);
        BigInteger xy1 = multiple(x1, y1);
        return new ECC_Point_TwEd_Ext_a(curve, x1, y1, BigInteger.ONE, xy1);
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
