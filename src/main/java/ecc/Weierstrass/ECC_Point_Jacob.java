package ecc.Weierstrass;

import ecc.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_Jacob extends ECC_Point {

    BigInteger x;
    BigInteger y;
    BigInteger z;

    // Constructors

    public ECC_Point_Jacob(ECC_Curve_Weierstrass curve, BigInteger x, BigInteger y, BigInteger z){
        super(curve, false);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ECC_Point_Jacob(ECC_Curve_Weierstrass curve){
        super(curve, true);
    }

    public ECC_Point_Jacob(ECC_Point_Aff point){
        super(point.curve, false);
        this.x = point.getX();
        this.y = point.getY();
        this.z = BigInteger.ONE;
    }

    public ECC_Point_Jacob(ECC_Point_Proj point){
        super(point.curve, false);
        this.x = multiple(point.x, point.z);
        this.y = multiple(point.y, square(point.z));
        this.z = point.z;
    }

    public ECC_Point_Jacob(ECC_Point_Chud point){
        super(point.curve, false);
        this.x = point.x;
        this.y = point.y;
        this.z = point.z;
    }

    public ECC_Point_Jacob(ECC_Point_ModJacob point){
        super(point.curve, false);
        this.x = point.x;
        this.y = point.y;
        this.z = point.z;
    }


    // Point Multiplication

    public ECC_Point_Jacob doubleAndAdd(BigInteger n){
        ECC_Point_Jacob N = this;
        ECC_Point_Jacob Q = new ECC_Point_Jacob(this.curve);
        String d = n.toString(2);
        for(int i = d.length() - 1; i >= 0; i--){
            if(d.charAt(i) == '1'){
                Q = Q.pointAddition(N);
            }
            N = N.pointDoubling();
        }
        return Q;
    }

    public ECC_Point_Jacob Montgomery(BigInteger n){
        ECC_Point_Jacob R1 = this;
        ECC_Point_Jacob R0 = new ECC_Point_Jacob(this.curve);
        String d = n.toString(2);
        for(int i = d.length() - 1; i >= 0; i--){
            if(d.charAt(i) == '0'){
                R1 = R0.pointAddition(R1);
                R0 = R0.pointDoubling();
            }else{
                R0 = R0.pointAddition(R1);
                R1 = R1.pointDoubling();
            }
        }
        return R0;
    }


    public ECC_Point_Jacob pointAddition(ECC_Point_Jacob point){

        // Perform required checks
        if(this.infinity == true){ // P == O
            return point;
        } else if(point.infinity == true){ // Q == O
            return this;
        }

        // Computations required
        BigInteger z2_2 = square(point.z);
        BigInteger u1 = multiple(this.x, z2_2);
        BigInteger z1_2 = square(this.z);
        BigInteger u2 = multiple(point.x, z1_2);
        BigInteger s1 = multiple(this.y, multiple(z2_2, point.z));
        BigInteger s2 = multiple(point.y, multiple(z1_2, this.z));

        // Perform addition checks
        if(u1.equals(u2)) {
            if(s1.equals(s2)) {  // P == Q
                return pointDoubling();
            } else {
                return new ECC_Point_Jacob(this.curve); // P == -Q (for basic curve)
            }
        }

        // Continue with computations
        BigInteger H = subtract(u2, u1);
        BigInteger H_2 = square(H);
        BigInteger H_3 = multiple(H_2, H);
        BigInteger r = subtract(s2, s1);
        BigInteger r_2 = square(r);
        BigInteger u1H_2 = multiple(u1, H_2);
        BigInteger s1H_3 = multiple(s1, H_3);

        // Calculate Rx, Ry, and Rz
        BigInteger Rx = subtract(subtract(r_2, H_3), multiple(BigInteger.TWO, u1H_2));
        BigInteger Ry = subtract(multiple(r, subtract(u1H_2, Rx)), s1H_3);
        BigInteger Rz = multiple(this.z, multiple(point.z, H));

        // Return the calculated value
        return new ECC_Point_Jacob(this.curve, Rx, Ry, Rz);
    }

    public ECC_Point_Jacob pointDoubling(){

        // Perform required checks
        if(this.infinity == true) { // P = O
            return this;
        } else if(this.y.equals(BigInteger.ZERO)) { // P == -P
            return new ECC_Point_Jacob(this.curve);
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
        BigInteger Rz = this.y.multiply(this.z.multiply(BigInteger.TWO));

        // Return the calculated value
        return new ECC_Point_Jacob(this.curve, Rx, Ry, Rz);
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
