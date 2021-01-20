package ecc;

import java.math.BigInteger;

public class ECC_Point_Proj {

    boolean infinity;
    ECC_Curve curve;
    BigInteger x;
    BigInteger y;
    BigInteger z;

    public ECC_Point_Proj(ECC_Curve curve, BigInteger x, BigInteger y, BigInteger z){
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.z = z;
        infinity = false;
    }

    public ECC_Point_Proj(ECC_Curve curve){
        this.curve = curve;
        this.infinity = true;
    }

    public ECC_Point_Proj pointMultiplication(BigInteger n){
        ECC_Point_Proj N = this;
        ECC_Point_Proj Q = new ECC_Point_Proj(this.curve);
        String d = n.toString(2);
        for(int i = d.length() - 1; i >= 0; i--){
            if(d.charAt(i) == '1'){
                Q = Q.pointAddition(N);
            }
            N = N.pointDoubling();
        }
        return Q;
    }

    public ECC_Point_Proj pointAddition(ECC_Point_Proj point){

        // Perform required checks
        if(this.x.equals(point.x) && this.y.equals(point.y)) {  // P == Q
            return pointDoubling();
        } else if(this.x.equals(point.x) && this.y.equals(point.y.negate())){ // P == -Q
            return new ECC_Point_Proj(this.curve);
        }else if(this.infinity == true){ // P == O
            return point;
        } else if(point.infinity == true){ // Q == O
            return this;
        }

        // Perform required multiplications
        BigInteger y2z1 = point.y.multiply(this.z);
        BigInteger y1z2 = this.y.multiply(point.z);
        BigInteger x2z1 = point.x.multiply(this.z);
        BigInteger x1z2 = this.x.multiply(point.z);
        BigInteger z1z2 = this.z.multiply(point.z);

        // Calculate u, v
        BigInteger u = y2z1.subtract(y1z2);
        BigInteger v = x2z1.subtract(x1z2);

        // Perform required squaring
        BigInteger u_2 = u.pow(2);
        BigInteger v_2 = v.pow(2);
        BigInteger v_3 = v_2.multiply(v);

        // Calculate A
        BigInteger u_2z1z2 = u_2.multiply(z1z2);
        BigInteger v_2x1z2 = v_2.multiply(x1z2);
        BigInteger A = u_2z1z2.subtract(v_3).subtract(v_2x1z2.multiply(BigInteger.TWO));

        // Calculate x3, y3, and z3
        BigInteger x3 = v.multiply(A);
        BigInteger y3 = u.multiply(v_2x1z2.subtract(A));
        BigInteger z3 = v_3.multiply(z1z2);

        // Return the calculated value
        return new ECC_Point_Proj(this.curve, x3, y3, z3);
    }

    public ECC_Point_Proj pointDoubling(){

        // Perform required checks
        if(this.y.equals(this.y.negate())) { // P == -P
            return new ECC_Point_Proj(this.curve);
        }else if (this.infinity == true){ // P = O
            return this;
        }

        // Calculate w
        BigInteger z1_2 = this.z.pow(2);
        BigInteger x1_2 = this.x.pow(2);
        BigInteger w = (this.curve.a.multiply(z1_2)).add(x1_2.multiply(new BigInteger("3")));

        // Calculate s
        BigInteger s = this.y.multiply(this.z);

        // Calculate B
        BigInteger B = this.x.multiply(this.y.multiply(s));

        // Calculate h
        BigInteger w_2 = w.pow(2);
        BigInteger h = w_2.subtract(B.multiply(new BigInteger("8")));

        // Calculate x3, y3, z3
        BigInteger x3 = h.multiply(s.multiply(BigInteger.TWO));
        BigInteger y3_1 = w.multiply(B.multiply(new BigInteger("4")).subtract(h));
        BigInteger y3_2 = (this.y.pow(2)).multiply(s.pow(2)).multiply(new BigInteger("8"));
        BigInteger y3 = y3_1.subtract(y3_2);
        BigInteger z3 = s.pow(3).multiply(new BigInteger("8"));

        // Return the calculated value
        return new ECC_Point_Proj(this.curve, x3, y3, z3);
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

    public ECC_Curve getCurve(){
        return curve;
    }

    public void setCurve(ECC_Curve curve){
        this.curve = curve;
    }
}
