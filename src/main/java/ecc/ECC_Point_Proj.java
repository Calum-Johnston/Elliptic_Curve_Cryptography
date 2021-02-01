package ecc;

import java.math.BigInteger;

public class ECC_Point_Proj extends ECC_Point {

    BigInteger x;
    BigInteger y;
    BigInteger z;

    public ECC_Point_Proj(ECC_Curve curve, BigInteger x, BigInteger y, BigInteger z) {
        super(curve, false);
        this.x = x;
        this.y = y;
        this.z = z;
        infinity = false;
    }

    public ECC_Point_Proj(ECC_Curve curve){
        super(curve, true);
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
        if(this.infinity == true){ // P == O
            return point;
        } else if(point.infinity == true){ // Q == O
            return this;
        } else if(this.x.equals(point.x) && this.y.equals(point.y)) {  // P == Q
            return pointDoubling();
        } else if(this.x.equals(point.x) && this.y.equals(point.y.negate())){ // P == -Q
            return new ECC_Point_Proj(this.curve);
        }

        // Computations required
        BigInteger y2z1 = multiple(point.y, this.z);
        BigInteger y1z2 = multiple(this.y, point.z);
        BigInteger x2z1 = multiple(point.x, this.z);
        BigInteger x1z2 = multiple(this.x, point.z);
        BigInteger u = subtract(y2z1, y1z2);
        BigInteger v = subtract(x2z1, x1z2);
        BigInteger z1z2 = multiple(this.z, point.z);
        BigInteger u_2 = square(u);
        BigInteger v_2 = square(v);
        BigInteger v_3 = multiple(v_2, v);
        BigInteger u_2z1z2 = multiple(u_2, z1z2);
        BigInteger v_2x1z2 = multiple(v_2, x1z2);
        BigInteger A = subtract(subtract(u_2z1z2, v_3), multiple(BigInteger.TWO, v_2x1z2));

        // Calculate Rx, Ry, and Rz
        BigInteger x3 = multiple(v, A);
        BigInteger y3 = multiple(u, subtract(v_2x1z2, A)).subtract(multiple(v_3, y1z2));
        BigInteger z3 = multiple(v_3, z1z2);

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
        return new ECC_Point_Proj(this.curve, x3.mod(curve.p), y3.mod(curve.p), z3.mod(curve.p));
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
