package ecc.Points.Jacobi;

import ecc.Curves.ECC_Curve_JacInt;
import ecc.Points.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_JacInt extends ECC_Point {

    ECC_Curve_JacInt curve;
    BigInteger s;
    BigInteger c;
    BigInteger d;
    BigInteger z;
    BigInteger sc;
    BigInteger dz;

    // Constructors
    public ECC_Point_JacInt(ECC_Curve_JacInt curve, BigInteger s, BigInteger c, BigInteger d,
                            BigInteger z, BigInteger sc, BigInteger dz) {
        super(false, curve.getP());
        this.curve = curve;
        this.s = s;
        this.c = c;
        this.d = d;
        this.z = z;
        this.sc = sc;
        this.dz = dz;
    }

    public ECC_Point_JacInt(ECC_Curve_JacInt curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_JacInt pointAddition(ECC_Point p){

        // Convert point
        ECC_Point_JacInt point = (ECC_Point_JacInt) p;

        // Computations required 11M+1S+2D
        BigInteger E = multiple(this.s, point.d);
        BigInteger F = multiple(this.c, point.z);
        BigInteger G = multiple(this.d, point.s);
        BigInteger H = multiple(this.z, point.c);
        BigInteger J = multiple(this.sc, point.dz);
        BigInteger K = multiple(this.dz, point.sc);
        BigInteger s3 = subtract(subtract(multiple(add(H, F), add(E, G)), J), K);
        BigInteger c3 = add(subtract(multiple(add(H, E), subtract(F, G)), J), K);
        BigInteger d3 = subtract(add(multiple(subtract(this.dz, multiple(curve.getA(), this.sc)), add(point.sc, point.dz)), multiple(curve.getA(), J)), K);
        BigInteger z3 = subtract(square(add(H, G)), multiple(BigInteger.TWO, K));
        BigInteger sc3 = multiple(s3, c3);
        BigInteger dz3 = multiple(d3, z3);

        // Return the calculated value
        return new ECC_Point_JacInt(this.curve, s3, c3, d3, z3, sc3, dz3);
    }

    public ECC_Point_JacInt mixedAddition(ECC_Point p){

        // Convert point
        ECC_Point_JacInt point = (ECC_Point_JacInt) p;

        // Computations required 10M+1S+2D
        BigInteger E = multiple(this.s, point.d);
        BigInteger G = multiple(this.d, point.s);
        BigInteger H = multiple(this.z, point.c);
        BigInteger J = multiple(this.sc, point.d);
        BigInteger K = multiple(this.dz, point.sc);
        BigInteger s3 = subtract(subtract(multiple(add(H, this.c), add(E, G)), J), K);
        BigInteger c3 = add(subtract(multiple(add(H, E), subtract(this.c, G)), J), K);
        BigInteger d3 = subtract(add(multiple(subtract(this.dz, multiple(curve.getA(), this.sc)), add(point.sc, point.d)), multiple(curve.getA(), J)), K);
        BigInteger z3 = subtract(square(add(H, G)), multiple(BigInteger.TWO, K));
        BigInteger sc3 = multiple(s3, c3);
        BigInteger dz3 = multiple(d3, z3);

        // Return the calculated value
        return new ECC_Point_JacInt(this.curve, s3, c3, d3, z3, sc3, dz3);
    }

    public ECC_Point_JacInt reAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_JacInt pointDoubling(){

        // Computations required 7M+1S
        BigInteger E = square(this.dz);
        BigInteger F = square(this.sc);
        BigInteger G = multiple(curve.getA(), F);
        BigInteger z3 = add(E, G);
        BigInteger d3 = subtract(E, G);
        BigInteger cc1 = square(this.c);
        BigInteger c3 = subtract(multiple(BigInteger.TWO, add(F, square(cc1))), z3);
        BigInteger s3 = subtract(subtract(square(add(this.sc, this.dz)), E), F);
        BigInteger sc3 = multiple(s3, c3);
        BigInteger dz3 = multiple(d3, z3);

        // Return the calculated value
        return new ECC_Point_JacInt(this.curve, s3, c3, d3, z3, sc3, dz3);
    }

    public ECC_Point_JacInt unifiedAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_JacInt negate(){
        return null;
    }

    public ECC_Point_JacInt convertAffine(){
        BigInteger z1 = inverse(z);
        BigInteger s1 = multiple(z1, this.s);
        BigInteger c1 = multiple(z1, this.c);
        BigInteger d1 = multiple(z1, this.d);
        BigInteger sc1 = multiple(s, c);
        return new ECC_Point_JacInt(curve, s1, c1, d1, BigInteger.ONE, sc1, d1);
    }



    // Getters and setters
    public BigInteger getS(){
        return s;
    }

    public void setS(BigInteger s){
        this.s = s;
    }

    public BigInteger getC() {
        return c;
    }

    public void setC(BigInteger c) {
        this.c = c;
    }

    public BigInteger getD() {
        return d;
    }

    public void setD(BigInteger d) {
        this.d = d;
    }

    public BigInteger getZ() {
        return z;
    }

    public void setZ(BigInteger z) {
        this.z = z;
    }

}
