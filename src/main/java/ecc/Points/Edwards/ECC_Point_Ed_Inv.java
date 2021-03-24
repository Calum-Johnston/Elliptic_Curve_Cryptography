package ecc.Points.Edwards;

import ecc.Curves.ECC_Curve_Ed;
import ecc.Points.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_Ed_Inv extends ECC_Point {

    ECC_Curve_Ed curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;

    // Constructors
    public ECC_Point_Ed_Inv(ECC_Curve_Ed curve, BigInteger x, BigInteger y, BigInteger z) {
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ECC_Point_Ed_Inv(ECC_Curve_Ed curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_Ed_Inv pointAddition(ECC_Point p){

        // Convert point
        ECC_Point_Ed_Inv point = (ECC_Point_Ed_Inv) p;

        // Computations required 9M+1S
        BigInteger A = multiple(this.z, point.z);
        BigInteger B = multiple(curve.getD(), square(A));
        BigInteger C = multiple(this.x, point.x);
        BigInteger D = multiple(this.y, point.y);
        BigInteger E = multiple(C, D);
        BigInteger H = subtract(C, D);
        BigInteger I = subtract(subtract(multiple(add(this.x, this.y), add(point.x, point.y)), C), D);
        BigInteger x3 = multiple(add(E, B), H);
        BigInteger y3 = multiple(subtract(E, B), I);
        BigInteger z3 = multiple(A, multiple(H, I));

        // Return the calculated value
        return new ECC_Point_Ed_Inv(this.curve, x3, y3, z3);
    }

    public ECC_Point_Ed_Inv mixedAddition(ECC_Point p){

        // Convert point
        ECC_Point_Ed_Inv point = (ECC_Point_Ed_Inv) p;

        // Computations required 8M+1S
        BigInteger A = this.z;
        BigInteger B = multiple(curve.getD(), square(A));
        BigInteger C = multiple(this.x, point.x);
        BigInteger D = multiple(this.y, point.y);
        BigInteger E = multiple(C, D);
        BigInteger H = subtract(C, D);
        BigInteger I = subtract(subtract(multiple(add(this.x, this.y), add(point.x, point.y)), C), D);
        BigInteger x3 = multiple(add(E, B), H);
        BigInteger y3 = multiple(subtract(E, B), I);
        BigInteger z3 = multiple(A, multiple(H, I));

        // Return the calculated value
        return new ECC_Point_Ed_Inv(this.curve, x3, y3, z3);
    }

    public ECC_Point_Ed_Inv reAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_Ed_Inv pointDoubling(){

        // Computations required 3M+4S
        BigInteger A = square(this.x);
        BigInteger B = square(this.y);
        BigInteger C = add(A, B);
        BigInteger D = subtract(A, B);
        BigInteger E = subtract(square(add(this.x, this.y)), C);
        BigInteger z3 = multiple(D, E);
        BigInteger x3 = multiple(C, D);
        BigInteger y3 = multiple(E, subtract(C, multiple(BigInteger.TWO, multiple(curve.getD(), square(this.z)))));

        // Return the calculated value
        return new ECC_Point_Ed_Inv(this.curve, x3, y3, z3);
    }

    public ECC_Point_Ed_Inv unifiedAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_Ed_Inv negate(){
        return new ECC_Point_Ed_Inv(this.curve, this.x.negate(), this.y, this.z);
    }

    public ECC_Point_Ed_Inv convertAffine(){
        BigInteger x1 = divide(inverse(this.x), this.z);
        BigInteger y1 = divide(inverse(this.y), this.z);
        return new ECC_Point_Ed_Inv(curve, x1, y1, BigInteger.ONE);
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

}
