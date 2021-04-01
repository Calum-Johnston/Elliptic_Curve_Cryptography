package ecc.Points.Montgomery.Weierstrass;

import ecc.Curves.ECC_Curve_W;
import ecc.Points.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_M_xy extends ECC_Point {

    ECC_Curve_W curve;
    BigInteger x;
    BigInteger z;

    // Constructors
    public ECC_Point_M_xy(ECC_Curve_W curve, BigInteger x, BigInteger z){
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.z = z;
    }

    public ECC_Point_M_xy(ECC_Curve_W curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_M_xy pointDoubling(){

        // Computations required 2M+2S
        BigInteger A = add(this.x, this.z);
        BigInteger AA = square(A);
        BigInteger B = subtract(this.x, this.z);
        BigInteger BB = square(B);
        BigInteger C = subtract(AA, BB);
        BigInteger x3 = multiple(AA, BB);
        BigInteger z3 = multiple(C, add(BB, multiple(C, add(curve.getA(), BigInteger.TWO))));

        // Return the calculated value
        return new ECC_Point_M_xy(this.curve, x3, z3);
    }



    // Getters and setters
    public BigInteger getX(){
        return x;
    }

    public void setX(BigInteger x){
        this.x = x;
    }

    public BigInteger getZ() {
        return z;
    }

    public void setZ(BigInteger z) {
        this.z = z;
    }


}
