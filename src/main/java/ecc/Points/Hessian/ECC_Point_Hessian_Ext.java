package ecc.Points.Hessian;

import ecc.Curves.ECC_Curve_Hessian;
import ecc.Points.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_Hessian_Ext extends ECC_Point {

    ECC_Curve_Hessian curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;
    BigInteger xx;
    BigInteger yy;
    BigInteger zz;
    BigInteger xy;
    BigInteger yz;
    BigInteger xz;

    // Constructors
    public ECC_Point_Hessian_Ext(ECC_Curve_Hessian curve, BigInteger x, BigInteger y, BigInteger z,
                                 BigInteger xx, BigInteger yy, BigInteger zz, BigInteger xy,
                                 BigInteger xz, BigInteger yz) {
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xx = xx;
        this.yy = yy;
        this.zz = zz;
        this.xy = xy;
        this.xz = xz;
        this.yz = yz;
    }

    public ECC_Point_Hessian_Ext(ECC_Curve_Hessian curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    // Unified with slight variation on input
    public ECC_Point_Hessian_Ext pointAddition(ECC_Point p){

        // Convert point
        ECC_Point_Hessian_Ext point = (ECC_Point_Hessian_Ext) p;

        // Computations required 6M+6S
        BigInteger x3 = subtract(multiple(this.yy, point.xz), multiple(this.xz, point.yy));
        BigInteger y3 = subtract(multiple(this.xx, point.yz), multiple(this.yz, point.xx));
        BigInteger z3 = subtract(multiple(this.zz, point.xy), multiple(this.xy, point.zz));
        BigInteger xx3 = square(x3);
        BigInteger yy3 = square(y3);
        BigInteger zz3 = square(z3);
        BigInteger xy3 = subtract(subtract(square(add(x3, y3)), xx3), yy3);
        BigInteger xz3 = subtract(subtract(square(add(x3, z3)), xx3), zz3);
        BigInteger yz3 = subtract(subtract(square(add(y3, z3)), yy3), zz3);

        // Return the calculated value
        return new ECC_Point_Hessian_Ext(this.curve, x3, y3, z3, xx3, yy3, zz3, xy3, xz3, yz3);
    }

    public ECC_Point_Hessian_Ext mixedAddition(ECC_Point p){

        // Convert point
        ECC_Point_Hessian_Ext point = (ECC_Point_Hessian_Ext) p;

        // Computations required 5M+6S
        BigInteger x3 = subtract(multiple(this.yy, point.xz), multiple(this.xz, point.yy));
        BigInteger y3 = subtract(multiple(this.xx, point.yz), multiple(this.yz, point.xx));
        BigInteger z3 = subtract(multiple(this.zz, point.xy), this.xy);
        BigInteger xx3 = square(x3);
        BigInteger yy3 = square(y3);
        BigInteger zz3 = square(z3);
        BigInteger xy3 = subtract(subtract(square(add(x3, y3)), xx3), yy3);
        BigInteger xz3 = subtract(subtract(square(add(x3, z3)), xx3), zz3);
        BigInteger yz3 = subtract(subtract(square(add(y3, z3)), yy3), zz3);

        // Return the calculated value
        return new ECC_Point_Hessian_Ext(this.curve, x3, y3, z3, xx3, yy3, zz3, xy3, xz3, yz3);
    }

    public ECC_Point_Hessian_Ext reAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_Hessian_Ext pointDoubling(){

        // Computations required 7M+1S
        BigInteger x3 = multiple(subtract(this.xy, this.yz), add(this.xz, multiple(BigInteger.TWO, add(this.xx, this.zz))));
        BigInteger y3 = multiple(subtract(this.xz, this.xy), add(this.yz, multiple(BigInteger.TWO, add(this.yy, this.zz))));
        BigInteger z3 = multiple(subtract(this.yz, this.xz), add(this.xy, multiple(BigInteger.TWO, add(this.xx, this.yy))));
        BigInteger xx3 = square(x3);
        BigInteger yy3 = square(y3);
        BigInteger zz3 = square(z3);
        BigInteger xy3 = subtract(subtract(square(add(x3, y3)), xx3), yy3);
        BigInteger xz3 = subtract(subtract(square(add(x3, z3)), xx3), zz3);
        BigInteger yz3 = subtract(subtract(square(add(y3, z3)), yy3), zz3);

        // Return the calculated value
        return new ECC_Point_Hessian_Ext(this.curve, x3, y3, z3, xx3, yy3, zz3, xy3, xz3, yz3);
    }

    public ECC_Point_Hessian_Ext unifiedAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_Hessian_Ext negate(){
        return null;
       // return new ECC_Point_Hessian_Ext(this.curve, this.x.negate(), this.y, this.z);
    }

    public ECC_Point_Hessian_Ext convertAffine(){
        BigInteger z1 = inverse(z);
        BigInteger x1 = multiple(z1, this.x);
        BigInteger y1 = multiple(z1, this.y);
        BigInteger xx1 = multiple(x1, x1);
        BigInteger yy1 = multiple(y1, y1);
        BigInteger xy1 = multiple(x1, y1);
        return new ECC_Point_Hessian_Ext(curve, x1, y1, BigInteger.ONE, xx1, yy1, BigInteger.ONE, xy1, y1, x1);
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
