package ecc.Points.Weierstrass;

import ecc.Curves.ECC_Curve_W;
import ecc.Points.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_W_ModJacob extends ECC_Point {

    ECC_Curve_W curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;
    BigInteger t;

    // Readdition variables
    BigInteger zz;
    BigInteger zzz;

    // Constructors
    public ECC_Point_W_ModJacob(ECC_Curve_W curve, BigInteger x, BigInteger y, BigInteger z, BigInteger t){
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }

    public ECC_Point_W_ModJacob(ECC_Curve_W curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_W_ModJacob pointAddition(ECC_Point p){

        // Convert point
        ECC_Point_W_ModJacob point = (ECC_Point_W_ModJacob) p;

        // Computations required 11M+7S (10M+6S readdition)
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
        BigInteger H = subtract(u2, u1);
        BigInteger I = square(multiple(BigInteger.TWO, H));
        BigInteger J = multiple(H, I);
        BigInteger r = multiple(BigInteger.TWO, subtract(s2, s1));
        BigInteger V = multiple(u1, I);
        BigInteger x3 = subtract(subtract(square(r), J), multiple(BigInteger.TWO, V));
        BigInteger y3 = subtract(multiple(r, subtract(V, x3)), multiple(BigInteger.TWO, multiple(s1, J)));
        BigInteger z3 = multiple(subtract(subtract(square(add(this.z, point.z)), this.zz), point.zz), H);
        BigInteger zz3 = square(z3);
        BigInteger t3 = multiple(curve.getA(), square(zz3));

        // Return the calculated value
        return new ECC_Point_W_ModJacob(this.curve, x3, y3, z3, t3);
    }

    public ECC_Point_W_ModJacob mixedAddition(ECC_Point p){

        // Convert point
        ECC_Point_W_ModJacob point = (ECC_Point_W_ModJacob) p;

        // Computations required 7M+6S
        BigInteger zz1 = square(this.z);
        BigInteger H = subtract(multiple(point.x, zz1), this.x);
        BigInteger HH = square(H);
        BigInteger I = multiple(new BigInteger("4"), HH);
        BigInteger J = multiple(H, I);
        BigInteger r = multiple(BigInteger.TWO, subtract(multiple(point.y, multiple(this.z, zz1)), this.y));
        BigInteger V = multiple(this.x, I);
        BigInteger x3 = subtract(subtract(square(r), J), multiple(BigInteger.TWO, V));
        BigInteger y3 = subtract(multiple(r, subtract(V, x3)), multiple(BigInteger.TWO, multiple(this.y, J)));
        BigInteger z3 = subtract(subtract(square(add(this.z, H)), zz1), HH);
        BigInteger zz3 = square(z3);
        BigInteger t3 = multiple(curve.getA(), square(zz3));

        // Return the calculated value
        return new ECC_Point_W_ModJacob(this.curve, x3, y3, z3, t3);
    }

    public ECC_Point_W_ModJacob pointDoubling(){

        // Computations required
        BigInteger xx = square(this.x);
        BigInteger A = multiple(BigInteger.TWO, square(this.y));
        BigInteger AA = square(A);
        BigInteger U = multiple(BigInteger.TWO, AA);
        BigInteger S = subtract(subtract(square(add(this.x, A)), xx), AA);
        BigInteger M = add(multiple(new BigInteger("3"), xx), this.t);
        BigInteger x3 = subtract(square(M), multiple(BigInteger.TWO, S));
        BigInteger y3 = subtract(multiple(M, subtract(S, x3)), U);
        BigInteger z3 = multiple(BigInteger.TWO, multiple(this.y, this.z));
        BigInteger t3 = multiple(BigInteger.TWO, multiple(U, this.t));

        // Return the calculated value
        return new ECC_Point_W_ModJacob(this.curve, x3, y3, z3, t3);
    }

    public ECC_Point_W_ModJacob unifiedAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_W_ModJacob negate(){
        return new ECC_Point_W_ModJacob(this.curve, this.x, this.y.negate(), this.z, this.t.negate());
    }

    public ECC_Point_W_ModJacob convertAffine(){
        BigInteger z1 = inverse(square(this.z));
        BigInteger z2 = inverse(multiple(this.z, square(this.z)));
        BigInteger x1 = multiple(z1, this.x);
        BigInteger y1 = multiple(z2, this.y);
        BigInteger xy1 = multiple(x1, y1);
        return new ECC_Point_W_ModJacob(curve, x1, y1, BigInteger.ONE, xy1);
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
