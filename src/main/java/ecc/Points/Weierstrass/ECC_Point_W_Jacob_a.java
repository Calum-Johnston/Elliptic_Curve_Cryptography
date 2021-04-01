package ecc.Points.Weierstrass;

import ecc.Curves.ECC_Curve_W;
import ecc.Points.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_W_Jacob_a extends ECC_Point {

    ECC_Curve_W curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;

    // Constructors
    public ECC_Point_W_Jacob_a(ECC_Curve_W curve, BigInteger x, BigInteger y, BigInteger z){
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ECC_Point_W_Jacob_a(ECC_Curve_W curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_W_Jacob_a pointAddition(ECC_Point p){

        // Convert point
        ECC_Point_W_Jacob_a point = (ECC_Point_W_Jacob_a) p;

        // Computations required 11M+5S
        BigInteger z1z1 = square(this.z);
        BigInteger z2z2 = square(point.z);
        BigInteger u1 = multiple(this.x, z2z2);
        BigInteger u2 = multiple(point.x, z1z1);
        BigInteger s1 = multiple(this.y, multiple(point.z, z2z2));
        BigInteger s2 = multiple(point.y, multiple(this.z, z1z1));
        BigInteger h = subtract(u2, u1);
        BigInteger i = square(multiple(BigInteger.TWO, h));
        BigInteger j = multiple(h, i);
        BigInteger r = multiple(BigInteger.TWO, subtract(s2, s1));
        BigInteger V = multiple(u1, i);
        BigInteger Rx = subtract(subtract(square(r), j), multiple(BigInteger.TWO, V));
        BigInteger Ry = subtract(multiple(r, subtract(V, Rx)), multiple(BigInteger.TWO, multiple(s1, j)));
        BigInteger Rz = multiple(subtract(subtract(square(add(this.z, point.z)), z1z1), z2z2), h);

        // Return the calculated value
        return new ECC_Point_W_Jacob_a(this.curve, Rx, Ry, Rz);
    }

    public ECC_Point_W_Jacob_a mixedAddition(ECC_Point p){

        // Convert point
        ECC_Point_W_Jacob_a point = (ECC_Point_W_Jacob_a) p;

        // Computations required 7M+4S
        BigInteger z1z1 = square(this.z);
        BigInteger u2 = multiple(point.x, z1z1);
        BigInteger s2 = multiple(point.y, multiple(z1z1, this.z));
        BigInteger h = subtract(u2, this.x);
        BigInteger hh = square(h);
        BigInteger i = multiple(new BigInteger("4"), hh);
        BigInteger j = multiple(h, i);
        BigInteger r = multiple(BigInteger.TWO, subtract(s2, this.y));
        BigInteger v = multiple(this.x, i);
        BigInteger Rx = subtract(subtract(square(r), j), multiple(BigInteger.TWO, v));
        BigInteger Ry = subtract(multiple(r, subtract(v, Rx)), multiple(BigInteger.TWO, multiple(this.y, j)));
        BigInteger Rz = subtract(subtract(square(add(this.z, h)), z1z1), hh);

        // Return the calculated value
        return new ECC_Point_W_Jacob_a(this.curve, Rx, Ry, Rz);
    }

    public ECC_Point_W_Jacob_a reAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_W_Jacob_a pointDoubling(){

        // Computations required 3M+5S
        BigInteger zz = square(this.z);
        BigInteger yy = square(this.y);
        BigInteger B = multiple(yy, this.x);
        BigInteger A = multiple(new BigInteger("3"), multiple(subtract(this.x, zz), add(this.x, zz)));
        BigInteger x3 = subtract(square(A), multiple(new BigInteger("8"), B));
        BigInteger y3 = subtract(subtract(square(add(this.y, this.z)), yy), zz);
        BigInteger z3 = subtract(multiple(A, subtract(multiple(new BigInteger("4"), B), x3)), multiple(new BigInteger("8"), square(yy)));

        // Return the calculated value
        return new ECC_Point_W_Jacob_a(this.curve, x3, y3, z3);
    }

    public ECC_Point_W_Jacob_a unifiedAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_W_Jacob_a negate(){
        return new ECC_Point_W_Jacob_a(this.curve, this.x, this.y.negate(), this.z);
    }

    public ECC_Point_W_Jacob_a convertAffine(){
        BigInteger z1 = inverse(square(this.z));
        BigInteger z2 = inverse(multiple(this.z, square(this.z)));
        BigInteger x1 = multiple(z1, this.x);
        BigInteger y1 = multiple(z2, this.y);
        return new ECC_Point_W_Jacob_a(curve, x1, y1, BigInteger.ONE);
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
