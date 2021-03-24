package ecc.Points.Hessian;

import ecc.Curves.ECC_Curve_Hessian;
import ecc.Points.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_Hessian_Proj extends ECC_Point {

    ECC_Curve_Hessian curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;

    // Constructors
    public ECC_Point_Hessian_Proj(ECC_Curve_Hessian curve, BigInteger x, BigInteger y, BigInteger z) {
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ECC_Point_Hessian_Proj(ECC_Curve_Hessian curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_Hessian_Proj pointAddition(ECC_Point p){

        // Convert point
        ECC_Point_Hessian_Proj point = (ECC_Point_Hessian_Proj) p;

        // Computations required 12m+2s
        BigInteger y1x2 = multiple(this.y, point.x);
        BigInteger y1y2 = multiple(this.y, point.y);
        BigInteger z1y2 = multiple(this.z, point.y);
        BigInteger z1z2 = multiple(this.z, point.z);
        BigInteger x1z2 = multiple(this.x, point.z);
        BigInteger x1x2 = multiple(this.x, point.x);
        BigInteger x3 = subtract(multiple(z1z2, z1y2), multiple(x1x2, y1x2));
        BigInteger y3 = subtract(multiple(y1y2, y1x2), multiple(z1z2, x1z2));
        BigInteger z3 = subtract(multiple(x1x2, x1z2), multiple(y1y2, z1y2));

        // Return the calculated value
        return new ECC_Point_Hessian_Proj(this.curve, x3, y3, z3);
    }

    public ECC_Point_Hessian_Proj mixedAddition(ECC_Point p){

        // Convert point
        ECC_Point_Hessian_Proj point = (ECC_Point_Hessian_Proj) p;

        // Computations required 10M
        BigInteger x1y2 = multiple(this.x, point.y);
        BigInteger y1x2 = multiple(this.y, point.x);
        BigInteger z1x2 = multiple(this.z, point.x);
        BigInteger z1y2 = multiple(this.z, point.y);
        BigInteger x3 = subtract(multiple(y1x2, this.y), multiple(z1y2, x1y2));
        BigInteger y3 = subtract(multiple(this.x, x1y2), multiple(y1x2, z1x2));
        BigInteger z3 = subtract(multiple(z1y2, z1x2), multiple(this.x, this.y));

        // Return the calculated value
        return new ECC_Point_Hessian_Proj(this.curve, x3, y3, z3);
    }

    public ECC_Point_Hessian_Proj reAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_Hessian_Proj pointDoubling(){

        // Computations required 7M+1S
        BigInteger A = square(this.x);
        BigInteger B = multiple(this.y, add(this.x, this.y));
        BigInteger C = add(A, B);
        BigInteger D = multiple(this.z, add(this.z, this.x));
        BigInteger E = add(A, D);
        BigInteger F = multiple(C, subtract(this.x, this.y));
        BigInteger G = multiple(E, subtract(this.z, this.x));
        BigInteger z3 = multiple(F, this.z);
        BigInteger y3 = multiple(add(F, G).negate(), this.x);
        BigInteger x3 = multiple(G, this.y);

        // Return the calculated value
        return new ECC_Point_Hessian_Proj(this.curve, x3, y3, z3);
    }

    public ECC_Point_Hessian_Proj unifiedAddition(ECC_Point p){
        // Permutation of input coordinates??
        return pointAddition(p);
    }

    public ECC_Point_Hessian_Proj negate(){
        return new ECC_Point_Hessian_Proj(this.curve, this.x.negate(), this.y, this.z);
    }

    public ECC_Point_Hessian_Proj convertAffine(){
        BigInteger z1 = inverse(z);
        BigInteger x1 = multiple(z1, this.x);
        BigInteger y1 = multiple(z1, this.y);
        return new ECC_Point_Hessian_Proj(curve, x1, y1, BigInteger.ONE);
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
