package ecc.Points.Edwards;

import ecc.Curves.ECC_Curve_Ed;
import ecc.Points.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_Ed_Proj extends ECC_Point {

    ECC_Curve_Ed curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;

    // Constructors
    public ECC_Point_Ed_Proj(ECC_Curve_Ed curve, BigInteger x, BigInteger y, BigInteger z) {
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ECC_Point_Ed_Proj(ECC_Curve_Ed curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_Ed_Proj pointAddition(ECC_Point p){

        // Convert point
        ECC_Point_Ed_Proj point = (ECC_Point_Ed_Proj) p;

        // Computations required 12m+2s
        BigInteger A = multiple(this.z, point.z);
        BigInteger B = square(A);
        BigInteger C = multiple(this.x, point.x);
        BigInteger D = multiple(this.y, point.y);
        BigInteger E = multiple(curve.getD(), multiple(C, D));
        BigInteger F = subtract(B, E);
        BigInteger G = add(B, E);
        BigInteger x3 = multiple(A, multiple(F, subtract(subtract(multiple(add(this.x, this.y), add(point.x, point.y)), C), D)));
        BigInteger y3 = multiple(A, multiple(G, subtract(D, C)));
        BigInteger z3 = multiple(F, G);

        // Return the calculated value
        return new ECC_Point_Ed_Proj(this.curve, x3, y3, z3);
    }

    public ECC_Point_Ed_Proj mixedAddition(ECC_Point p){

        // Convert point
        ECC_Point_Ed_Proj point = (ECC_Point_Ed_Proj) p;

        // Computations required 9m
        BigInteger A = this.x;
        BigInteger B = this.y;
        BigInteger C = multiple(this.z, point.x);
        BigInteger D = multiple(this.z, point.y);
        BigInteger E = multiple(A, B);
        BigInteger F = multiple(C, D);
        BigInteger G = add(E, F);
        BigInteger H = subtract(E, F);
        BigInteger J = subtract(multiple(subtract(A, C), add(B, D)), H);
        BigInteger K = subtract(multiple(add(A, D), add(B, C)), G);
        BigInteger x3 = multiple(G, J);
        BigInteger y3 = multiple(H, K);
        BigInteger z3 = multiple(J, K);

        // Return the calculated value
        return new ECC_Point_Ed_Proj(this.curve, x3, y3, z3);
    }

    public ECC_Point_Ed_Proj pointDoubling(){

        // Computations required 3M+4S
        BigInteger B = square(add(this.x, this.y));
        BigInteger C = square(this.x);
        BigInteger D = square(this.y);
        BigInteger E = add(C, D);
        BigInteger H = square(this.z);
        BigInteger J = subtract(E, multiple(BigInteger.TWO, H));
        BigInteger x3 = multiple(subtract(B, E), J);
        BigInteger y3 = multiple(E, subtract(C, D));
        BigInteger z3 = multiple(E, J);

        // Return the calculated value
        return new ECC_Point_Ed_Proj(this.curve, x3, y3, z3);
    }

    public ECC_Point_Ed_Proj unifiedAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_Ed_Proj negate(){
        return new ECC_Point_Ed_Proj(this.curve, this.x.negate(), this.y, this.z);
    }

    public ECC_Point_Ed_Proj convertAffine(){
        BigInteger z1 = inverse(z);
        BigInteger x1 = multiple(z1, this.x);
        BigInteger y1 = multiple(z1, this.y);
        return new ECC_Point_Ed_Proj(curve, x1, y1, BigInteger.ONE);
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
