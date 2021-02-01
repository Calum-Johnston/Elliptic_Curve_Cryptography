package ecc;

import java.math.BigInteger;

public class ECC_Point_Jacob extends ECC_Point {

    BigInteger x;
    BigInteger y;
    BigInteger z;

    public ECC_Point_Jacob(ECC_Curve curve, BigInteger x, BigInteger y, BigInteger z){
        super(curve, false);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ECC_Point_Jacob(ECC_Curve curve){
        super(curve, true);
    }

    public ECC_Point_Jacob pointMultiplication(BigInteger n){
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

    public ECC_Point_Jacob pointAddition(ECC_Point_Jacob point){

        // Perform required checks
        if(this.infinity == true){ // P == O
            return point;
        } else if(point.infinity == true){ // Q == O
            return this;
        } else if(this.x.equals(point.x) && this.y.equals(point.y)) {  // P == Q
            return pointDoubling();
        } else if(this.x.equals(point.x) && this.y.equals(point.y.negate())){ // P == -Q
            return new ECC_Point_Jacob(this.curve);
        }

        // Computations required
        BigInteger z2_2 = square(point.z);
        BigInteger u1 = multiple(this.x, z2_2);
        BigInteger z1_2 = square(this.z);
        BigInteger u2 = multiple(point.x, z1_2);
        BigInteger s1 = multiple(this.y, multiple(z2_2, point.z));
        BigInteger s2 = multiple(point.y, multiple(z1_2, this.z));
        BigInteger H = subtract(u2, u1);
        BigInteger H_2 = square(H);
        BigInteger H_3 = multiple(H_2, H);
        BigInteger r = subtract(s2, s1);
        BigInteger r_2 = square(r);
        BigInteger u1H_2 = multiple(u1, H_2);
        BigInteger s1H_3 = multiple(s1, H_3);

        // Calculate x3
        BigInteger x3 = add(subtract(H_3.negate(), multiple(BigInteger.TWO, u1H_2)),  r_2);

        // Calculate y3
        BigInteger y3 = add(s1H_3.negate(), multiple(r, subtract(u1H_2, x3)));

        // Calculate z3
        BigInteger z3 = multiple(this.z, multiple(point.z, H));

        // Return the calculated value
        return new ECC_Point_Jacob(this.curve, x3, y3, z3);
    }

    public ECC_Point_Jacob pointDoubling(){

        // Perform required checks
        if (this.infinity == true){ // P = O
            return this;
        } else if(this.y.equals(this.y.negate())) { // P == -P
            return new ECC_Point_Jacob(this.curve);
        }

        // Computations required
        BigInteger y1_2 = this.y.pow(2);
        BigInteger y1_4 = y1_2.pow(2);
        BigInteger x1y1_2 = this.x.multiply(y1_2);
        BigInteger S = x1y1_2.multiply(new BigInteger("4"));
        BigInteger x1_2 = this.x.pow(2);
        BigInteger z1_4 = this.z.pow(4);
        BigInteger az1_4 = this.curve.a.multiply(z1_4);
        BigInteger M = x1_2.multiply(new BigInteger("3")).add(az1_4);
        BigInteger M_2 = M.pow(2);
        BigInteger T = ((S.multiply(BigInteger.TWO)).negate()).add(M_2);

        // Calculate x3
        BigInteger x3 = T;

        // Calculate y3
        BigInteger y3 = ((y1_4.multiply(new BigInteger("8"))).negate()).add(M.multiply(S.subtract(T)));

        // Calculate z3
        BigInteger z3 = this.y.multiply(this.z.multiply(BigInteger.TWO));


        // Return the calculated value
        return new ECC_Point_Jacob(this.curve, x3.mod(curve.p), y3.mod(curve.p), z3.mod(curve.p));
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

    public ECC_Curve getCurve(){
        return curve;
    }

    public void setCurve(ECC_Curve curve){
        this.curve = curve;
    }
}
