package ecc.Weierstrass;

import ecc.Curves.ECC_Curve_W;
import ecc.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_W_Jacob extends ECC_Point {

    ECC_Curve_W curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;

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
    public ECC_Point_W_Jacob pointAddition(ECC_Point_W_Jacob point){

        // Perform required checks
        if(this.isInfinity() == true){ // P == O
            return point;
        } else if(point.isInfinity() == true){ // Q == O
            return this;
        }

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
        return new ECC_Point_W_Jacob(this.curve, Rx, Ry, Rz);
    }

    public ECC_Point_W_Jacob mixedAddition(ECC_Point_W_Jacob point){

        // Perform required checks
        if(this.isInfinity() == true){ // P == O
            return point;
        } else if(point.isInfinity() == true){ // Q == O
            return this;
        }

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
        return new ECC_Point_W_Jacob(this.curve, Rx, Ry, Rz);
    }

    public ECC_Point_W_Jacob reAddition(ECC_Point_W_Jacob point){
        return pointAddition(point);
    }

    public ECC_Point_W_Jacob pointDoubling(){

        // Perform required checks
        if(this.isInfinity() == true) { // P = O
            return this;
        } else if(this.y.equals(BigInteger.ZERO)) { // P == -P
            return new ECC_Point_W_Jacob(this.curve);
        }

        // Computations required
        BigInteger y1_2 = square(this.y);
        BigInteger y1_4 = square(y1_2);
        BigInteger x1y1_2 = multiple(this.x, y1_2);
        BigInteger S = multiple(x1y1_2, new BigInteger("4"));
        BigInteger x1_2 = square(this.x);
        BigInteger z1_4 = square(square(this.z));
        BigInteger az1_4 = multiple(this.curve.getA(), z1_4);
        BigInteger M = add(az1_4, multiple(x1_2, new BigInteger("3")));
        BigInteger M_2 = square(M);
        BigInteger T = add(M_2, (multiple(S, BigInteger.TWO).negate()));

        // Calculate Rx, Ry, and Rz
        BigInteger Rx = T;
        BigInteger Ry = subtract(multiple(M, subtract(S, T)), multiple(y1_4, new BigInteger("8")));
        BigInteger Rz = multiple(this.y, multiple(this.z, BigInteger.TWO));

        // Return the calculated value
        return new ECC_Point_W_Jacob(this.curve, Rx, Ry, Rz);
    }

    public ECC_Point_W_Jacob unifiedAddition(ECC_Point_W_Jacob point){

        // Perform required checks
        if(this.isInfinity() == true){ // P == O
            return point;
        } else if(point.isInfinity() == true){ // Q == O
            return this;
        }

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
        return new ECC_Point_W_Jacob(this.curve, Rx, Ry, Rz);

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
