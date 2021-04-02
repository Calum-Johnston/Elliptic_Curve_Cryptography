package ecc.Points.Weierstrass;

import ecc.Curves.ECC_Curve_W;
import ecc.Points.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_W_Proj extends ECC_Point {

    ECC_Curve_W curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;

    // Constructors
    public ECC_Point_W_Proj(ECC_Curve_W curve, BigInteger x, BigInteger y, BigInteger z) {
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ECC_Point_W_Proj(ECC_Curve_W curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_W_Proj pointAddition(ECC_Point p){

        // Convert point
        ECC_Point_W_Proj point = (ECC_Point_W_Proj) p;

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
        return new ECC_Point_W_Proj(this.curve, Rx, Ry, Rz);
    }

    public ECC_Point_W_Proj mixedAddition(ECC_Point p){

        // Convert point
        ECC_Point_W_Proj point = (ECC_Point_W_Proj) p;

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
        return new ECC_Point_W_Proj(this.curve, Rx, Ry, Rz);
    }

    public ECC_Point_W_Proj pointDoubling(){

        // Computations required 5M+6S
        BigInteger xx = square(this.x);
        BigInteger zz = square(this.z);
        BigInteger w = add(multiple(curve.getA(), zz), multiple(new BigInteger("3"), xx));
        BigInteger s = multiple(BigInteger.TWO, multiple(this.y, this.z));
        BigInteger ss = square(s);
        BigInteger sss = multiple(s, ss);
        BigInteger r = multiple(this.y, s);
        BigInteger rr = square(r);
        BigInteger b = subtract(subtract(square(add(this.x, r)), xx), rr);
        BigInteger h = subtract(square(w), multiple(BigInteger.TWO, b));
        BigInteger Rx = multiple(h, s);
        BigInteger Ry = subtract(multiple(w, subtract(b, h)), multiple(BigInteger.TWO, rr));
        BigInteger Rz = sss;

        // Return the calculated value
        return new ECC_Point_W_Proj(this.curve, Rx, Ry, Rz);
    }

    public ECC_Point_W_Proj unifiedAddition(ECC_Point p){

        // Convert point
        ECC_Point_W_Proj point = (ECC_Point_W_Proj) p;

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
        return new ECC_Point_W_Proj(this.curve, Rx, Ry, Rz);

    }

    public ECC_Point_W_Proj negate(){
        return new ECC_Point_W_Proj(this.curve, this.x, this.y.negate().mod(curve.getP()), this.z);
    }

    public ECC_Point_W_Proj convertAffine(){
        BigInteger z1 = inverse(z);
        BigInteger x1 = multiple(z1, this.x);
        BigInteger y1 = multiple(z1, this.y);
        return new ECC_Point_W_Proj(curve, x1, y1, BigInteger.ONE);
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
