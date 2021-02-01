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
        } else if(this.x.equals(point.x) && this.y.equals(point.y)) {  // P == Q
            return pointDoubling();
        } else if(this.x.equals(point.x) && this.y.equals(point.y.negate())){ // P == -Q
            return new ECC_Point_Chud(this.curve);
        }

        // Computations required
        BigInteger u1 = multiple(this.x, point.z2);
        BigInteger u2 = multiple(point.x, this.z2);
        BigInteger s1 = multiple(this.y, point.z3);
        BigInteger s2 = multiple(point.y, this.z3);
        BigInteger H = subtract(u2, u1);
        BigInteger H_2 = square(H);
        BigInteger H_3 = multiple(H_2, H);
        BigInteger r = subtract(s2, s1);
        BigInteger r_2 = square(r);
        BigInteger u1H_2 = multiple(u1, H_2);
        BigInteger s1H_3 = multiple(s1, H_3);


        // Calculate x
        BigInteger Rx = add(subtract(H_3.negate(), multiple(BigInteger.TWO, u1H_2)),  r_2);

        // Calculate y
        BigInteger Ry = add(s1H_3.negate(), multiple(r, subtract(u1H_2, Rx)));

        // Calculate z
        BigInteger Rz = multiple(this.z, multiple(point.z, H));

        // Calculate z2
        BigInteger Rz2 = square(Rz);

        // Calculate z3
        BigInteger Rz3 = multiple(Rz2, Rz);

        // Return the calculated value
        return new ECC_Point_Chud(this.curve, Rx, Ry, Rz, Rz2, Rz3);
    }

    public ECC_Point_Chud pointDoubling(){

        // Perform required checks
        if (this.infinity == true){ // P = O
            return this;
        } else if(this.y.equals(this.y.negate())) { // P == -P
            return new ECC_Point_Chud(this.curve);
        }

        // Computations required
        BigInteger y1_2 = this.y.pow(2);
        BigInteger y1_4 = y1_2.pow(2);
        BigInteger x1y1_2 = this.x.multiply(y1_2);
        BigInteger S = x1y1_2.multiply(new BigInteger("4"));
        BigInteger x1_2 = this.x.pow(2);
        BigInteger z1_4 = this.z2.pow(2);
        BigInteger az1_4 = this.curve.a.multiply(z1_4);
        BigInteger M = x1_2.multiply(new BigInteger("3")).add(az1_4);
        BigInteger M_2 = M.pow(2);
        BigInteger T = ((S.multiply(BigInteger.TWO)).negate()).add(M_2);

        // Calculate x
        BigInteger Rx = T;

        // Calculate y3
        BigInteger Ry = ((y1_4.multiply(new BigInteger("8"))).negate()).add(M.multiply(S.subtract(T)));

        // Calculate z
        BigInteger Rz = this.y.multiply(this.z.multiply(BigInteger.TWO));

        // Calculate z2
        BigInteger Rz2 = Rz.pow(2);

        // Calculate z3
        BigInteger Rz3 = Rz2.multiply(Rz);

        // Return the calculated value
        return new ECC_Point_Chud(this.curve, Rx.mod(curve.getP()), Ry.mod(curve.getP()), Rz.mod(curve.getP()),
                Rz2.mod(curve.getP()), Rz3.mod(curve.getP()));
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

    public ECC_Curve getCurve(){
        return curve;
    }

    public void setCurve(ECC_Curve curve){
        this.curve = curve;
    }
}
