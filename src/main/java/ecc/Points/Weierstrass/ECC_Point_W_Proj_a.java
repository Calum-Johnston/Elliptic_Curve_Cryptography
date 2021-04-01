package ecc.Points.Weierstrass;

import ecc.Curves.ECC_Curve_W;
import ecc.Points.ECC_Point;

import java.lang.management.MemoryUsage;
import java.math.BigInteger;

public class ECC_Point_W_Proj_a extends ECC_Point {

    ECC_Curve_W curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;

    // Constructors
    public ECC_Point_W_Proj_a(ECC_Curve_W curve, BigInteger x, BigInteger y, BigInteger z) {
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ECC_Point_W_Proj_a(ECC_Curve_W curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_W_Proj_a pointAddition(ECC_Point p){

        // Convert point
        ECC_Point_W_Proj_a point = (ECC_Point_W_Proj_a) p;

        // Computations required 12m+2s
        BigInteger Y1Z2 = multiple(this.y, point.z);
        BigInteger X1Z2 = multiple(this.x, point.z);
        BigInteger Z1Z2 = multiple(this.z, point.z);
        BigInteger u = subtract(multiple(point.y, this.z), Y1Z2);
        BigInteger uu = square(u);
        BigInteger v = subtract(multiple(point.x, this.z), X1Z2);
        BigInteger vv = square(v);
        BigInteger vvv = multiple(v, vv);
        BigInteger R = multiple(vv, X1Z2);
        BigInteger A = subtract(subtract(multiple(uu, Z1Z2), vvv), multiple(BigInteger.TWO, R));
        BigInteger Rx = multiple(v, A);
        BigInteger Ry = subtract(multiple(u, subtract(R, A)), multiple(vvv, Y1Z2));
        BigInteger Rz = multiple(vvv, Z1Z2);

        // Return the calculated value
        return new ECC_Point_W_Proj_a(this.curve, Rx, Ry, Rz);
    }

    public ECC_Point_W_Proj_a mixedAddition(ECC_Point p){

        // Convert point
        ECC_Point_W_Proj_a point = (ECC_Point_W_Proj_a) p;

        // Computations required 9m+2s
        BigInteger u = subtract(multiple(point.y, this.z), this.y);
        BigInteger uu = square(u);
        BigInteger v = subtract(multiple(point.x, this.z), this.x);
        BigInteger vv = square(v);
        BigInteger vvv = multiple(v, vv);
        BigInteger R = multiple(vv, this.x);
        BigInteger A = subtract(subtract(multiple(uu, this.z), vvv), multiple(BigInteger.TWO, R));
        BigInteger Rx = multiple(v, A);
        BigInteger Ry = subtract(multiple(u, subtract(R, A)), multiple(vvv, this.y));
        BigInteger Rz = multiple(vvv, this.z);

        // Return the calculated value
        return new ECC_Point_W_Proj_a(this.curve, Rx, Ry, Rz);
    }

    public ECC_Point_W_Proj_a reAddition(ECC_Point p){
        return pointAddition(p);
    }

    public ECC_Point_W_Proj_a pointDoubling(){

        // Computations required 7M+3S
        BigInteger w = multiple(new BigInteger("3"), multiple(subtract(this.x, this.z), add(this.x, this.z)));
        BigInteger s = multiple(BigInteger.TWO, multiple(this.y, this.z));
        BigInteger ss = square(s);
        BigInteger sss = multiple(s, ss);
        BigInteger R = multiple(this.y, s);
        BigInteger RR = square(R);
        BigInteger B = multiple(BigInteger.TWO, multiple(this.x, R));
        BigInteger h = subtract(square(w), multiple(BigInteger.TWO, B));
        BigInteger Rx = multiple(h, s);
        BigInteger Ry = subtract(multiple(w, subtract(B, h)), multiple(BigInteger.TWO, RR));
        BigInteger Rz = sss;

        // Return the calculated value
        return new ECC_Point_W_Proj_a(this.curve, Rx, Ry, Rz);
    }

    public ECC_Point_W_Proj_a unifiedAddition(ECC_Point p){

        // Convert point
        ECC_Point_W_Proj_a point = (ECC_Point_W_Proj_a) p;

        // Computations required 11M+6S+1D
        BigInteger U1 = multiple(this.x, point.z);
        BigInteger U2 = multiple(point.x, this.z);
        BigInteger S1 = multiple(this.y, point.z);
        BigInteger S2 = multiple(point.y, this.z);
        BigInteger ZZ = multiple(this.z, point.z);
        BigInteger T = add(U1, U2);
        BigInteger TT = square(T);
        BigInteger M = add(S1, S2);
        BigInteger R = add(subtract(TT, multiple(U1, U2)), multiple(curve.getA(), square(ZZ)));
        BigInteger F = multiple(ZZ, M);
        BigInteger L = multiple(M, F);
        BigInteger LL = square(L);
        BigInteger G = subtract(subtract(square(add(T, L)), TT), LL);
        BigInteger W = subtract(multiple(BigInteger.TWO, square(R)), G);
        BigInteger Rx = multiple(BigInteger.TWO, multiple(F, W));
        BigInteger Ry = subtract(multiple(R, subtract(G, multiple(BigInteger.TWO, W))), multiple(BigInteger.TWO, LL));
        BigInteger Rz = multiple(new BigInteger("4"), multiple(F, square(F)));

        // Return the calculated value
        return new ECC_Point_W_Proj_a(this.curve, Rx, Ry, Rz);

    }

    public ECC_Point_W_Proj_a negate(){
        return new ECC_Point_W_Proj_a(this.curve, this.x, this.y.negate().mod(curve.getP()), this.z);
    }

    public ECC_Point_W_Proj_a convertAffine(){
        BigInteger z1 = inverse(z);
        BigInteger x1 = multiple(z1, this.x);
        BigInteger y1 = multiple(z1, this.y);
        return new ECC_Point_W_Proj_a(curve, x1, y1, BigInteger.ONE);
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
