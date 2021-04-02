package ecc.Points.Montgomery.Weierstrass;

import ecc.Curves.ECC_Curve_W;
import ecc.Points.ECC_Point;
import ecc.Points.Weierstrass.ECC_Point_W_xy;

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

    public ECC_Point_M_xy diffAddition(ECC_Point p, ECC_Point d){

        // Convert point
        ECC_Point_M_xy point = (ECC_Point_M_xy) p;
        ECC_Point_M_xy diff = (ECC_Point_M_xy) d;

        // Computations required 4M+2S
        BigInteger A = add(point.x, point.z);
        BigInteger B = subtract(point.x, point.z);
        BigInteger C = add(diff.x, diff.z);
        BigInteger D = subtract(diff.x, diff.z);
        BigInteger DA = multiple(D, A);
        BigInteger CB = multiple(C, B);
        BigInteger x4 = multiple(this.z, square(add(DA, CB)));
        BigInteger z4 = multiple(this.x, square(subtract(DA, CB)));

        // Return the calculated value
        return new ECC_Point_M_xy(this.curve, x4, z4);
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
