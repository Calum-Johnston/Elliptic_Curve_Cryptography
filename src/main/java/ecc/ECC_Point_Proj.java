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
        }

        // Computations required
        BigInteger y2z1 = multiple(point.y, this.z);
        BigInteger y1z2 = multiple(this.y, point.z);
        BigInteger x2z1 = multiple(point.x, this.z);
        BigInteger x1z2 = multiple(this.x, point.z);

        // Perform addition checks
        if(x2z1.equals(x1z2)) {
            if(y2z1.equals(y1z2)) {  // P == Q
                return pointDoubling();
            } else {
                return new ECC_Point_Proj(this.curve); // P == -Q (for basic curve)
            }
        }

        // Continue with computations
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
        BigInteger Rx = multiple(v, A);
        BigInteger Ry = multiple(u, subtract(v_2x1z2, A)).subtract(multiple(v_3, y1z2));
        BigInteger Rz = multiple(v_3, z1z2);

        // Return the calculated value
        return new ECC_Point_Proj(this.curve, Rx, Ry, Rz);
    }

    public ECC_Point_Proj pointDoubling(){

        // Perform required checks
        if(this.infinity == true) { // P = O
            return this;
        } else if(this.y.equals(BigInteger.ZERO)) { // P == -P
            return new ECC_Point_Proj(this.curve);
        }

        // Computations required
        BigInteger z1_2 = square(this.z);
        BigInteger x1_2 = square(this.x);
        BigInteger az1_2 = multiple(this.curve.a, z1_2);
        BigInteger w = add(az1_2, multiple(x1_2, new BigInteger("3")));
        BigInteger s = multiple(this.y, this.z);
        BigInteger B = multiple(this.x, multiple(this.y, s));
        BigInteger w_2 = square(w);
        BigInteger h = subtract(w_2, multiple(B, new BigInteger("8")));
        BigInteger y1_2s_2 = multiple(square(this.y), square(s));

        // Calculate Rx, Ry, and Rz
        BigInteger Rx = multiple(h, multiple(s, BigInteger.TWO));
        BigInteger Ry = subtract(multiple(w, subtract(multiple(B, new BigInteger("4")), h)), multiple(y1_2s_2, new BigInteger("8")));
        BigInteger Rz = multiple(multiple(square(s), s), new BigInteger("8"));

        // Return the calculated value
        return new ECC_Point_Proj(this.curve, Rx, Ry, Rz);
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
