package ecc.Points.Hessian;

import ecc.Curves.ECC_Curve_Hessian;
import ecc.Points.ECC_Point;

import javax.swing.plaf.BorderUIResource;
import java.math.BigInteger;

public class ECC_Point_TwHessian_Proj extends ECC_Point {

    ECC_Curve_Hessian curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;

    // Constructors
    public ECC_Point_TwHessian_Proj(ECC_Curve_Hessian curve, BigInteger x, BigInteger y, BigInteger z) {
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ECC_Point_TwHessian_Proj(ECC_Curve_Hessian curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_TwHessian_Proj pointAddition(ECC_Point p){

        // Convert point
        ECC_Point_TwHessian_Proj point = (ECC_Point_TwHessian_Proj) p;

        // Computations required 12m+1D
        BigInteger x1z2 = multiple(this.x, point.z);
        BigInteger z1z2 = multiple(this.z, point.z);
        BigInteger y1x2 = multiple(this.y, point.x);
        BigInteger y1y2 = multiple(this.y, point.y);
        BigInteger z1y2 = multiple(this.z, point.y);
        BigInteger ax1x2 = multiple(multiple(curve.getA(), this.x), point.x);
        BigInteger x3 = subtract(multiple(x1z2, z1y2), multiple(y1x2, y1y2));
        BigInteger y3 = subtract(multiple(y1y2, z1y2), multiple(ax1x2, x1z2));
        BigInteger z3 = subtract(multiple(ax1x2, y1x2), multiple(z1z2, z1y2));

        // Return the calculated value
        return new ECC_Point_TwHessian_Proj(this.curve, x3, y3, z3);
    }

    public ECC_Point_TwHessian_Proj mixedAddition(ECC_Point p){

        // Convert point
        ECC_Point_TwHessian_Proj point = (ECC_Point_TwHessian_Proj) p;

        // Computations required 10m+1D
        BigInteger y1x2 = multiple(this.y, point.x);
        BigInteger y1y2 = multiple(this.y, point.y);
        BigInteger z1y2 = multiple(this.z, point.y);
        BigInteger ax1x2 = multiple(multiple(curve.getA(), this.x), point.x);
        BigInteger x3 = subtract(multiple(this.x, z1y2), multiple(y1x2, y1y2));
        BigInteger y3 = subtract(multiple(y1y2, z1y2), multiple(ax1x2, this.x));
        BigInteger z3 = subtract(multiple(ax1x2, y1x2), multiple(this.z, z1y2));

        // Return the calculated value
        return new ECC_Point_TwHessian_Proj(this.curve, x3, y3, z3);
    }

    public ECC_Point_TwHessian_Proj reAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_TwHessian_Proj pointDoubling(){

        // Computations required 7M+1S
        BigInteger P = multiple(this.y, this.z);
        BigInteger P2 = multiple(BigInteger.TWO, P);
        BigInteger S = add(this.y, this.z);
        BigInteger A = subtract(square(S), P);
        BigInteger C = multiple(subtract(A, P2), S);
        BigInteger D = multiple(A, subtract(this.z, this.y));
        BigInteger E = subtract(multiple(new BigInteger("3"), C), multiple(curve.getD(), multiple(this.x, P2)));
        BigInteger x3 = multiple(new BigInteger("-2"), multiple(this.x, D));
        BigInteger y3 = multiple(subtract(D, E), this.z);
        BigInteger z3 = multiple(add(D, E), this.y);

        // Return the calculated value
        return new ECC_Point_TwHessian_Proj(this.curve, x3, y3, z3);
    }

    public ECC_Point_TwHessian_Proj unifiedAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_TwHessian_Proj negate(){
        return new ECC_Point_TwHessian_Proj(this.curve, this.y, this.x, this.z);
    }

    public ECC_Point_TwHessian_Proj convertAffine(){
        BigInteger z1 = inverse(z);
        BigInteger x1 = multiple(z1, this.x);
        BigInteger y1 = multiple(z1, this.y);
        return new ECC_Point_TwHessian_Proj(curve, x1, y1, BigInteger.ONE);
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
