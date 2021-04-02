package ecc.Points.Weierstrass;

import ecc.Curves.ECC_Curve_W;
import ecc.Points.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_W_Jacob extends ECC_Point {

    ECC_Curve_W curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;

    // Readdition variables
    BigInteger zz;
    BigInteger zzz;

    // Constructors
    public ECC_Point_W_Jacob(ECC_Curve_W curve, BigInteger x, BigInteger y, BigInteger z){
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ECC_Point_W_Jacob(ECC_Curve_W curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_W_Jacob pointAddition(ECC_Point p){

        // Convert point
        ECC_Point_W_Jacob point = (ECC_Point_W_Jacob) p;

        // Computations required 11M+5S
        if(!this.isAdded()){
            zz = square(this.z);
            zzz = multiple(this.z, zz);
            this.setAdded(true);
        }
        if(!point.isAdded()){
            point.zz = square(point.z);
            point.zzz = multiple(point.z, point.zz);
            point.setAdded(true);
        }
        BigInteger u1 = multiple(this.x, point.zz);
        BigInteger u2 = multiple(point.x, this.zz);
        BigInteger s1 = multiple(this.y, point.zzz);
        BigInteger s2 = multiple(point.y, this.zzz);
        BigInteger h = subtract(u2, u1);
        BigInteger i = square(multiple(BigInteger.TWO, h));
        BigInteger j = multiple(h, i);
        BigInteger r = multiple(BigInteger.TWO, subtract(s2, s1));
        BigInteger V = multiple(u1, i);
        BigInteger Rx = subtract(subtract(square(r), j), multiple(BigInteger.TWO, V));
        BigInteger Ry = subtract(multiple(r, subtract(V, Rx)), multiple(BigInteger.TWO, multiple(s1, j)));
        BigInteger Rz = multiple(subtract(subtract(square(add(this.z, point.z)), this.zz), point.zz), h);

        // Return the calculated value
        return new ECC_Point_W_Jacob(this.curve, Rx, Ry, Rz);
    }

    public ECC_Point_W_Jacob mixedAddition(ECC_Point p){

        // Convert point
        ECC_Point_W_Jacob point = (ECC_Point_W_Jacob) p;

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
        return new ECC_Point_W_Jacob(this.curve, Rx, Ry, Rz);
    }

    public ECC_Point_W_Jacob pointDoubling(){

        // Computations required
        BigInteger xx = square(this.x);
        BigInteger yy = square(this.y);
        BigInteger yyyy = square(yy);
        BigInteger zz = square(this.z);
        BigInteger s = multiple(BigInteger.TWO, subtract(subtract(square(add(this.x, yy)), xx), yyyy));
        BigInteger m = add(multiple(new BigInteger("3"), xx), multiple(curve.getA(), square(zz)));
        BigInteger t = subtract(square(m), multiple(BigInteger.TWO, s));
        BigInteger Rx = t;
        BigInteger Ry = subtract(multiple(m, subtract(s, t)), multiple(new BigInteger("8"), yyyy));
        BigInteger Rz = subtract(subtract(square(add(this.y, this.z)), yy), zz);

        // Return the calculated value
        return new ECC_Point_W_Jacob(this.curve, Rx, Ry, Rz);
    }

    public ECC_Point_W_Jacob unifiedAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_W_Jacob negate(){
        return new ECC_Point_W_Jacob(this.curve, this.x, this.y.negate(), this.z);
    }

    public ECC_Point_W_Jacob convertAffine(){
        BigInteger z1 = inverse(square(this.z));
        BigInteger z2 = inverse(multiple(this.z, square(this.z)));
        BigInteger x1 = multiple(z1, this.x);
        BigInteger y1 = multiple(z2, this.y);
        return new ECC_Point_W_Jacob(curve, x1, y1, BigInteger.ONE);
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
