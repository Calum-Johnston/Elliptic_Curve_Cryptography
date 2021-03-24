package ecc.Points.Jacobi;

import ecc.Curves.ECC_Curve_JacInt;
import ecc.Points.ECC_Point;
import ecc.Points.Weierstrass.ECC_Point_W_Proj;

import java.math.BigInteger;

public class ECC_Point_JacQ extends ECC_Point {

    ECC_Curve_JacInt curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;
    BigInteger xx;
    BigInteger zz;
    BigInteger r; //2xz

    // Constructors
    public ECC_Point_JacQ(ECC_Curve_JacInt curve, BigInteger x, BigInteger y, BigInteger z,
                          BigInteger xx, BigInteger zz, BigInteger r) {
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xx = xx;
        this.zz = zz;
        this.r = r;
    }

    public ECC_Point_JacQ(ECC_Curve_JacInt curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_JacQ pointAddition(ECC_Point p){

        // Convert point
        ECC_Point_JacQ point = (ECC_Point_JacQ) p;

        // Computations required 7M+3S
        BigInteger A = multiple(BigInteger.TWO, multiple(this.xx, point.xx));
        BigInteger B = multiple(BigInteger.TWO, multiple(this.zz, point.zz));
        BigInteger C = multiple(this.r, point.r);
        BigInteger D = multiple(this.y, point.y);
        BigInteger x3 = subtract(subtract(multiple(add(this.r, this.y), add(point.r, point.y)), C), D);
        BigInteger z3 = subtract(B, A);
        BigInteger xx3 = square(x3);
        BigInteger zz3 = square(z3);
        BigInteger F = add(A, add(B, C));
        // k = a-1???
        BigInteger G = add(multiple(BigInteger.TWO, multiple(add(this.xx, this.zz), multiple(add(point.xx, point.zz), D))), multiple(C, subtract(curve.getA(), BigInteger.ONE)));
        BigInteger H = add(xx3, zz3);
        BigInteger y3 = subtract(multiple(F, G), H);
        BigInteger r3 = subtract(square(add(x3, z3)), H);

        // Return the calculated value
        return new ECC_Point_JacQ(this.curve, x3, y3, z3, xx3, zz3, r3);
    }

    public ECC_Point_JacQ mixedAddition(ECC_Point p){

        // Convert point
        ECC_Point_JacQ point = (ECC_Point_JacQ) p;

        // Computations required 6M+3S
        BigInteger A = multiple(BigInteger.TWO, multiple(this.xx, point.xx));
        BigInteger B = multiple(BigInteger.TWO, this.zz);
        BigInteger C = multiple(this.r, point.r);
        BigInteger D = multiple(this.y, point.y);
        BigInteger x3 = subtract(subtract(multiple(add(this.r, this.y), add(point.r, point.y)), C), D);
        BigInteger z3 = subtract(B, A);
        BigInteger xx3 = square(x3);
        BigInteger zz3 = square(z3);
        BigInteger F = add(A, add(B, C));
        // k = a-1???
        BigInteger G = add(multiple(BigInteger.TWO, add(multiple(add(this.xx, this.zz), add(point.xx, BigInteger.ONE)), D)), multiple(C, subtract(curve.getA(), BigInteger.ONE)));
        BigInteger H = add(xx3, zz3);
        BigInteger y3 = subtract(multiple(F, G), H);
        BigInteger r3 = subtract(square(add(x3, z3)), H);

        // Return the calculated value
        return new ECC_Point_JacQ(this.curve, x3, y3, z3, xx3, zz3, r3);
    }

    public ECC_Point_JacQ reAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_JacQ pointDoubling(){

        // Computations required 2M+5S
        BigInteger yy1 = square(this.y);
        BigInteger x3 = multiple(this.y, this.r);
        BigInteger z3 = multiple(subtract(this.zz, this.xx), add(this.zz, this.xx));
        BigInteger xx3 = square(x3);
        BigInteger zz3 = square(z3);
        BigInteger r3 = subtract(subtract(square(add(x3, z3)), xx3), zz3);
        BigInteger y3 = subtract(subtract(multiple(BigInteger.TWO, square(yy1)), multiple(curve.getA(), xx3)), zz3);

        // Return the calculated value
        return new ECC_Point_JacQ(this.curve, x3, y3, z3, xx3, zz3, r3);
    }

    public ECC_Point_JacQ unifiedAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_JacQ negate(){
        return null;
    }

    public ECC_Point_JacQ convertAffine(){
        BigInteger z1 = inverse(z);
        BigInteger x1 = multiple(z1, this.x);
        BigInteger y1 = multiple(z1, this.y);
        BigInteger xx1 = square(x1);
        BigInteger r1 = multiple(BigInteger.TWO, x1);
        return new ECC_Point_JacQ(curve, x1, y1, BigInteger.ONE, xx1, BigInteger.ONE, r1);
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
