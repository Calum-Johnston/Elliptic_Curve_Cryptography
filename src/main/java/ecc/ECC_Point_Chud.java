package ecc;

import java.math.BigInteger;

public class ECC_Point_Chud extends ECC_Point{

    BigInteger x;
    BigInteger y;
    BigInteger z;
    BigInteger z2;
    BigInteger z3;

    public ECC_Point_Chud(ECC_Curve curve, BigInteger x, BigInteger y, BigInteger z,
                          BigInteger z2, BigInteger z3){
        super(curve, false);
        this.x = x;
        this.y = y;
        this.z = z;
        this.z2 = z2;
        this.z3 = z3;
    }

    public ECC_Point_Chud(ECC_Curve curve){
        super(curve, true);
    }

    public ECC_Point_Chud pointMultiplication(BigInteger n){
        ECC_Point_Chud N = this;
        ECC_Point_Chud Q = new ECC_Point_Chud(this.curve);
        String d = n.toString(2);
        for(int i = d.length() - 1; i >= 0; i--){
            if(d.charAt(i) == '1'){
                Q = Q.pointAddition(N);
            }
            N = N.pointDoubling();
        }
        return Q;
    }

    public ECC_Point_Chud pointAddition(ECC_Point_Chud point){

        // Perform required checks
        if(this.infinity == true){ // P == O
            return point;
        } else if(point.infinity == true){ // Q == O
            return this;
        }

        // Computations required
        BigInteger u1 = multiple(this.x, point.z2);
        BigInteger u2 = multiple(point.x, this.z2);
        BigInteger s1 = multiple(this.y, point.z3);
        BigInteger s2 = multiple(point.y, this.z3);

        // Perform addition checks
        if(u1.equals(u2)) {
            if(s1.equals(s2)) {  // P == Q
                return pointDoubling();
            } else {
                return new ECC_Point_Chud(this.curve); // P == -Q (for basic curve)
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

        // Calculate Rx, Ry, Rz, Rz2, and Rz3
        BigInteger Rx = subtract(subtract(r_2, H_3), multiple(BigInteger.TWO, u1H_2));
        BigInteger Ry = subtract(multiple(r, subtract(u1H_2, Rx)), s1H_3);
        BigInteger Rz = multiple(this.z, multiple(point.z, H));
        BigInteger Rz2 = square(Rz);
        BigInteger Rz3 = multiple(Rz2, Rz);

        // Return the calculated value
        return new ECC_Point_Chud(this.curve, Rx, Ry, Rz, Rz2, Rz3);
    }

    public ECC_Point_Chud pointDoubling(){

        // Perform required checks
        if(this.infinity == true) { // P = O
            return this;
        } else if(this.y.equals(BigInteger.ZERO)) { // P == -P
            return new ECC_Point_Chud(this.curve);
        }

        // Computations required
        BigInteger y1_2 = square(this.y);
        BigInteger y1_4 = square(y1_2);
        BigInteger x1y1_2 = multiple(this.x, y1_2);
        BigInteger S = multiple(x1y1_2, new BigInteger("4"));
        BigInteger x1_2 = square(this.x);
        BigInteger z1_4 = square(this.z2);
        BigInteger az1_4 = multiple(this.curve.a, z1_4);
        BigInteger M = add(az1_4, multiple(x1_2, new BigInteger("3")));
        BigInteger M_2 = square(M);
        BigInteger T = add(M_2, (multiple(S, BigInteger.TWO).negate()));

        // Calculate Rx, Ry, Rz, Rz2, and Rz3
        BigInteger Rx = T;
        BigInteger Ry = subtract(multiple(M, subtract(S, T)), multiple(y1_4, new BigInteger("8")));
        BigInteger Rz = this.y.multiply(this.z.multiply(BigInteger.TWO));
        BigInteger Rz2 = Rz.pow(2);
        BigInteger Rz3 = Rz2.multiply(Rz);

        // Return the calculated value
        return new ECC_Point_Chud(this.curve, Rx, Ry, Rz, Rz2, Rz3);
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

    public BigInteger getZ2() {
        return z2;
    }

    public void setZ2(BigInteger z2) {
        this.z2 = z2;
    }

    public BigInteger getZ3() {
        return z3;
    }

    public void setZ3(BigInteger z3) {
        this.z3 = z3;
    }

}
